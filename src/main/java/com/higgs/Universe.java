package main.java.com.higgs;

import main.java.com.higgs.graphics.Viewport;
import main.java.com.higgs.obj.Actor;
import main.java.com.higgs.obj.Material;
import main.java.com.higgs.obj.Particle;
import main.java.com.higgs.utils.Logger;
import main.java.com.higgs.utils.Utils;
import main.java.com.higgs.utils.Vector;

import java.text.DecimalFormat;
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
        addBodies();
        loop();
    }

    private void addBodies() {
//        spawnTwoBody();
//        spawnTest();
        spawnRandom();
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
                DecimalFormat df = new DecimalFormat(".##");
                displayFPS = Double.parseDouble(df.format(1 / ((double)elapsed / 1000000000)));
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

        int index = 0;
        Iterator<Actor> iterator = myMap.keySet().iterator();
        while(iterator.hasNext()) {
            if(index < actors.size()) {
                if(actors.get(index) != null) {
                    iterator.next().act();
                }
            } else {
                break;
            }
            index++;
        }
    }

    private void render() {
        VIEWPORT.render();
    }

    private void spawnRandom() {
        int numObj = 500;
        double totalMass = 1000.0;
        double totalKineticEnergy = 1000;
        double maxMass = 10;
        double maxKineticEnergy = 10;

        for(int i = 0; i < numObj; i++) {
            Logger.log("Creating particle: " + i + "/" + numObj);
            Vector pos = getRandomCoord();
            double range = 3;
            double mass = 1;//Math.random() * maxMass;
            double ke = Math.random() * maxKineticEnergy;
//            double vx = Math.random() * range;
//            double vy = Math.random() * range;
//            addObject(new Particle(new Vector(vx - (range / 2), vy - (range / 2), 0), 0, mass, Material.MATERIAL_REGISTRY.get(0)), pos);
            Particle p = new Particle(new Vector(1, Math.random() * (2*Math.PI)), 0, mass, Material.MATERIAL_REGISTRY.get(0));
            addObject(p, pos);
            p.addKineticEnergy(ke);
        }
        Logger.log("Starting mass: " + getTotalMass());
    }

    private void spawnTest() {
        addObject(new Particle(new Vector(1, 1, 0), 0, 5, Material.MATERIAL_REGISTRY.get(0)), new Vector(130, 130, 0));
        addObject(new Particle(new Vector(-1, -1, 0), 0, 5, Material.MATERIAL_REGISTRY.get(0)), new Vector(630, 630, 0));
        addObject(new Particle(new Vector(1, -1, 0), 0, 5, Material.MATERIAL_REGISTRY.get(0)), new Vector(130, 630, 0));
        addObject(new Particle(new Vector(-1, 1, 0), 0, 5, Material.MATERIAL_REGISTRY.get(0)), new Vector(630, 130, 0));
        addObject(new Particle(new Vector(0, 1, 0), 0, 10, Material.MATERIAL_REGISTRY.get(0)), new Vector(683, 300, 0));
        addObject(new Particle(new Vector(0, -1, 0), 0, 10, Material.MATERIAL_REGISTRY.get(0)), new Vector(683, 500, 0));
        addObject(new Particle(new Vector(1, 0, 0), 0, 10, Material.MATERIAL_REGISTRY.get(0)), new Vector(583, 400, 0));
        addObject(new Particle(new Vector(-2, 0, 0), 0, 10, Material.MATERIAL_REGISTRY.get(0)), new Vector(783, 400, 0));
    }

    private void spawnTwoBody() {
        addObject(new Particle(new Vector(0.75, 0, 0), 0, 5, Material.MATERIAL_REGISTRY.get(0)), new Vector(480, 200, 0));
        addObject(new Particle(new Vector(0 , 0, 0), 0, 100, Material.MATERIAL_REGISTRY.get(0)), new Vector(480, 400, 0));
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
//                        Logger.log("Goodbye " + actor.toString() + "!");
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
