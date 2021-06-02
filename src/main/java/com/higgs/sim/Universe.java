package com.higgs.sim;

import com.higgs.sim.graphics.Viewport;
import com.higgs.sim.obj.Actor;
import com.higgs.sim.obj.Material;
import com.higgs.sim.obj.Particle;
import com.higgs.sim.utils.Logger;
import com.higgs.sim.utils.Utils;
import com.higgs.sim.utils.Vector;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Universe {
    private final ArrayList<Actor> actors = new ArrayList<>();
    private static Universe INSTANCE;
    private Viewport VIEWPORT;

    private double displayFPS = 0;

    Universe() {
        this.init();
    }

    public void init() {
        Universe.INSTANCE = this;
        this.VIEWPORT = new Viewport();
        this.createMaterials();
        this.addBodies();
        this.loop();
    }

    private void addBodies() {
//        spawnTwoBody();
//        spawnTest();
        this.spawnRandom();
    }

    private void loop() {
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        while (true) {
            final long start = System.nanoTime();
            long end;
            long elapsed;
            final long delta;

            this.logic();
            this.render();

            end = System.nanoTime();
            elapsed = end - start;
            delta = OPTIMAL_TIME - elapsed;

            if (delta >= 0) {
                this.displayFPS = TARGET_FPS;
                try {
                    Thread.sleep(delta / 1000000);
                } catch (final InterruptedException ignored) { }
            } else {
                final DecimalFormat df = new DecimalFormat(".##");
                this.displayFPS = Double.parseDouble(df.format(1 / ((double) elapsed / 1000000000)));
                try {
                    Thread.sleep(0);
                } catch (final InterruptedException ignored) { }
            }
        }
    }

    private void logic() {
        final Map<Actor, Integer> myMap = new ConcurrentHashMap<>();
        for (int i = 0; i < this.actors.size(); i++) {
            myMap.put(this.actors.get(i), i);
        }

        int index = 0;
        final Iterator<Actor> iterator = myMap.keySet().iterator();
        while (iterator.hasNext()) {
            if (index < this.actors.size()) {
                if (this.actors.get(index) != null) {
                    iterator.next().act();
                }
            } else {
                break;
            }
            index++;
        }
    }

    private void render() {
        this.VIEWPORT.render();
    }

    private void spawnRandom() {
        final int numObj = 500;
        final double totalMass = 1000.0;
        final double totalKineticEnergy = 1000;
        final double maxMass = 10;
        final double maxKineticEnergy = 10;

        for (int i = 0; i < numObj; i++) {
            Logger.log("Creating particle: " + i + "/" + numObj);
            final Vector pos = this.getRandomCoord();
            final double range = 3;
            final double mass = 1;//Math.random() * maxMass;
            final double ke = Math.random() * maxKineticEnergy;
//            double vx = Math.random() * range;
//            double vy = Math.random() * range;
//            addObject(new Particle(new Vector(vx - (range / 2), vy - (range / 2), 0), 0, mass, Material.MATERIAL_REGISTRY.get(0)), pos);
            final Particle p = new Particle(new Vector(1, Math.random() * (2 * Math.PI)), 0, mass, Material.MATERIAL_REGISTRY.get(0));
            this.addObject(p, pos);
            p.addKineticEnergy(ke);
        }
        Logger.log("Starting mass: " + this.getTotalMass());
    }

    private void spawnTest() {
        this.addObject(new Particle(new Vector(1, 1, 0), 0, 5, Material.MATERIAL_REGISTRY.get(0)), new Vector(130, 130, 0));
        this.addObject(new Particle(new Vector(-1, -1, 0), 0, 5, Material.MATERIAL_REGISTRY.get(0)), new Vector(630, 630, 0));
        this.addObject(new Particle(new Vector(1, -1, 0), 0, 5, Material.MATERIAL_REGISTRY.get(0)), new Vector(130, 630, 0));
        this.addObject(new Particle(new Vector(-1, 1, 0), 0, 5, Material.MATERIAL_REGISTRY.get(0)), new Vector(630, 130, 0));
        this.addObject(new Particle(new Vector(0, 1, 0), 0, 10, Material.MATERIAL_REGISTRY.get(0)), new Vector(683, 300, 0));
        this.addObject(new Particle(new Vector(0, -1, 0), 0, 10, Material.MATERIAL_REGISTRY.get(0)), new Vector(683, 500, 0));
        this.addObject(new Particle(new Vector(1, 0, 0), 0, 10, Material.MATERIAL_REGISTRY.get(0)), new Vector(583, 400, 0));
        this.addObject(new Particle(new Vector(-2, 0, 0), 0, 10, Material.MATERIAL_REGISTRY.get(0)), new Vector(783, 400, 0));
    }

    private void spawnTwoBody() {
        this.addObject(new Particle(new Vector(0.75, 0, 0), 0, 5, Material.MATERIAL_REGISTRY.get(0)), new Vector(480, 200, 0));
        this.addObject(new Particle(new Vector(0, 0, 0), 0, 100, Material.MATERIAL_REGISTRY.get(0)), new Vector(480, 400, 0));
    }

    private void spawnSolarSystem() {

    }

    public void addObject(final Actor actor, final Vector pos) {
        actor.setPosition(pos);
        this.actors.add(actor);
    }

    public void removeObject(final Actor actor) {
        if (actor != null) {
            if (this.actors != null) {
                if (!this.actors.isEmpty()) {
                    //                        Logger.log("Goodbye " + actor.toString() + "!");
                    this.actors.remove(actor);
                }
            }
        }
    }

    public ArrayList<Actor> getActors() {
        return this.actors;
    }

    public ArrayList<Actor> getActorsAt(final double x, final double y) {
        final ArrayList<Actor> result = new ArrayList<>();
        for (final Actor actor : this.actors) {
            if (actor.getX() == x && actor.getY() == y) {
                result.add(actor);
            }
        }
        return result;
    }

    public ArrayList<Actor> getActorsInRange(final Vector pos, final double range) {
        final ArrayList<Actor> result = new ArrayList<>();
        for (final Actor actor : this.actors) {
            if (actor != null) {
                if (Utils.dist(pos, actor.getPos()) <= range) {
                    result.add(actor);
                }
            }
        }
        return result;
    }

    public ArrayList<Actor> getActorsInRange(final Actor posA, final double range) {
        final ArrayList<Actor> result = new ArrayList<>();
        for (final Actor actor : this.actors) {
            if (actor != null) {
                if (actor != posA) {
                    if (Utils.dist(posA.getPos(), actor.getPos()) <= range) {
                        result.add(actor);
                    }
                }
            }
        }
        return result;
    }

    private Vector getRandomCoord() {
        return new Vector(Math.random() * this.VIEWPORT.getWidth(), Math.random() * this.VIEWPORT.getHeight(), 0);
    }

    public static Universe getInstance() {
        return Universe.INSTANCE;
    }

    public double getDisplayFPS() {
        return this.displayFPS;
    }

    private void createMaterials() {
        final Material hydrogen = new Material();

        hydrogen.density = (1 / Math.PI);
    }

    public double getTotalMass() {
        double tm = 0.0;
        for (final Actor actor : new ArrayList<>(Universe.getInstance().getActors())) {
            if (actor instanceof Particle) {
                final Particle p = (Particle) actor;
                tm += p.getMass();
            }
        }
        return tm;
    }
}
