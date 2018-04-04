package main.java.com.higgs;

import main.java.com.higgs.graphics.Viewport;
import main.java.com.higgs.obj.Actor;
import main.java.com.higgs.obj.Material;
import main.java.com.higgs.obj.Particle;
import main.java.com.higgs.utils.Logger;
import main.java.com.higgs.utils.Utils;
import main.java.com.higgs.utils.Vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Universe {
    private ArrayList<Actor> actors = new ArrayList<>();
    private static Universe INSTANCE;
    private Viewport VIEWPORT;

    private double displayFPS = 0;

    Universe() {
        init();
    }

    public void init() {
        INSTANCE = this;
        VIEWPORT = new Viewport();
        createMaterials();
//        spawnTwoBody();
//        spawnTest();
        spawnRandom();
        loop();
    }

    private void loop() {
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        while(true) {
            long start = System.nanoTime(), end, elapsed, delta;

            logic();
            render();

            end = System.nanoTime();
            elapsed = end - start;
            delta = OPTIMAL_TIME - elapsed;

            if(delta >= 0) {
                displayFPS = TARGET_FPS;
                try {
                    Thread.sleep(delta / 1000000);
                } catch(InterruptedException ignored) { }
            } else {
                displayFPS = Math.abs(1 / (double)(delta / 1000000));
                try {
                    Thread.sleep(0);
                } catch(InterruptedException ignored) { }
            }
        }
    }

    private void logic() {
        Map<Actor, Integer> myMap = new ConcurrentHashMap<>();
        for(int i = 0; i < actors.size(); i++) {
            myMap.put(actors.get(i), i);
        }

        Iterator<Actor> iterator = myMap.keySet().iterator();
        while(iterator.hasNext()) {
            iterator.next().act();
        }
    }

    private void render() {
        VIEWPORT.render();
    }

    private void spawnRandom() {
        int numObj = 1000;
        for(int i = 0; i < numObj; i++) {
            Logger.log("Creating particle: " + i + "/" + numObj);
            Vector pos = getRandomCoord();
            double range = 3;
            double vx = Math.random() * range;
            double vy = Math.random() * range;
            addObject(new Particle(new Vector(vx - (range / 2), vy - (range / 2), 0), 1, Material.MATERIAL_REGISTRY.get(0)), pos);
        }
        Logger.log("Starting mass: " + getTotalMass());
    }

    private void spawnTest() {
        addObject(new Particle(new Vector(1, 1, 0), 5, Material.MATERIAL_REGISTRY.get(0)), new Vector(130, 130, 0));
        addObject(new Particle(new Vector(-1, -1, 0), 5, Material.MATERIAL_REGISTRY.get(0)), new Vector(630, 630, 0));
        addObject(new Particle(new Vector(1, -1, 0), 5, Material.MATERIAL_REGISTRY.get(0)), new Vector(130, 630, 0));
        addObject(new Particle(new Vector(-1, 1, 0), 5, Material.MATERIAL_REGISTRY.get(0)), new Vector(630, 130, 0));
    }

    private void spawnTwoBody() {
        addObject(new Particle(new Vector(0.75, 0, 0), 5, Material.MATERIAL_REGISTRY.get(0)), new Vector(480, 200, 0));
        addObject(new Particle(new Vector(0 , 0, 0), 100, Material.MATERIAL_REGISTRY.get(0)), new Vector(480, 400, 0));
    }

    private void spawnSolarSystem() {

    }

    public void addObject(Actor actor, Vector pos) {
        actor.setPosition(pos);
        actors.add(actor);
    }

    public void removeObject(Actor actor) {
        if(actor != null) {
            if(actors != null) {
                if(!actors.isEmpty()) {
                    if(actors.contains(actor)) {
                        actors.remove(actor);
                    }
                }
            }
        }
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public ArrayList<Actor> getActorsAt(double x, double y) {
        ArrayList<Actor> result = new ArrayList<>();
        for(Actor actor : actors) {
            if(actor.getX() == x && actor.getY() == y) {
                result.add(actor);
            }
        }
        return result;
    }

    public ArrayList<Actor> getActorsInRange(Vector pos, double range) {
        ArrayList<Actor> result = new ArrayList<>();
        for(Actor actor : actors) {
            if(actor != null) {
                if(Utils.dist(pos, actor.getPos()) <= range) {
                    result.add(actor);
                }
            }
        }
        return result;
    }

    public ArrayList<Actor> getActorsInRange(Actor posA, double range) {
        ArrayList<Actor> result = new ArrayList<>();
        for(Actor actor : actors) {
            if(actor != null) {
                if(actor != posA) {
                    if(Utils.dist(posA.getPos(), actor.getPos()) <= range) {
                        result.add(actor);
                    }
                }
            }
        }
        return result;
    }

    private Vector getRandomCoord() {
        return new Vector(Math.random() * VIEWPORT.getWidth(), Math.random() * VIEWPORT.getHeight(), 0);
    }

    public static Universe getInstance() {
        return INSTANCE;
    }

    public double getDisplayFPS() {
        return displayFPS;
    }

    private void createMaterials() {
        Material hydrogen = new Material();

        hydrogen.density = (1 / Math.PI);
    }

    public double getTotalMass() {
        double tm = 0.0;
        for(Actor actor : new ArrayList<>(Universe.getInstance().getActors())) {
            if(actor instanceof Particle) {
                Particle p = (Particle)actor;
                tm += p.getMass();
            }
        }
        return tm;
    }
}
