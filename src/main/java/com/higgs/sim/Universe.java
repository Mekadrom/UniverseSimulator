package com.higgs.sim;

import com.higgs.sim.obj.Material;
import com.higgs.sim.obj.Particle;
import com.higgs.sim.utils.Logger;
import com.higgs.sim.utils.Utils;
import com.higgs.staged.Animation;
import com.higgs.staged.SimpleAnimation;
import com.higgs.staged.Stage;
import com.higgs.staged.StagedActor;
import org.apache.commons.math3.util.Pair;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Universe extends Stage {
    private int acts = 0;

    Universe(final JPanel parentPanel) {
        super(parentPanel);
    }

    public void init() {
        this.initImage();
        this.createMaterials();
        this.addBodies();
    }

    private void initImage() {
        final BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        final Graphics g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.dispose();
        this.setAnimation(new SimpleAnimation(Animation.NO_TRANS, image));
    }

    private void addBodies() {
//        this.spawnTwoEqualBody();
//        spawnTest();
        this.spawnRandom();
    }

    private void spawnRandom() {
        final int numObj = 500;
        final double maxMass = 10;

        for (int i = 0; i < numObj; i++) {
            Logger.log("Creating particle: " + i + "/" + numObj);
            final Point pos = this.getRandomCoord();
            final double mass = Math.random() * maxMass;
            final Pair<Double, Double> startVelocity = Utils.getCartesianVelocity(1, Math.random() * (2 * Math.PI));
            final Particle p = new Particle(startVelocity.getFirst(), startVelocity.getSecond(), 0, mass, Material.MATERIAL_REGISTRY.get(0));
            this.addActor(p, pos.x, pos.y);
        }
        Logger.log("Starting mass: " + this.getTotalMass());
    }

    private void spawnTest() {
        this.addActor(new Particle(1, 1, 0, 5, Material.MATERIAL_REGISTRY.get(0)), 130, 130);
        this.addActor(new Particle(-1, -1, 0, 5, Material.MATERIAL_REGISTRY.get(0)),630, 630);
        this.addActor(new Particle(1, -1, 0, 5, Material.MATERIAL_REGISTRY.get(0)), 130, 630);
        this.addActor(new Particle(-1, 1, 0, 5, Material.MATERIAL_REGISTRY.get(0)), 630, 130);
        this.addActor(new Particle(0, 1, 0, 10, Material.MATERIAL_REGISTRY.get(0)), 683, 300);
        this.addActor(new Particle(0, -1, 0, 10, Material.MATERIAL_REGISTRY.get(0)), 683, 500);
        this.addActor(new Particle(1, 0, 0, 10, Material.MATERIAL_REGISTRY.get(0)), 583, 400);
        this.addActor(new Particle(-2, 0, 0, 10, Material.MATERIAL_REGISTRY.get(0)), 783, 400);
    }

    private void spawnTwoBody() {
        this.addActor(new Particle(0.75, 0, 0, 5, Material.MATERIAL_REGISTRY.get(0)), 480, 200);
        this.addActor(new Particle(0, 0, 0, 100, Material.MATERIAL_REGISTRY.get(0)), 480, 400);
    }

    private void spawnTwoEqualBody() {
        this.addActor(new Particle(0, 0, 0, 200, Material.MATERIAL_REGISTRY.get(0)), 240, 400);
        this.addActor(new Particle(0, 0, 0, 200, Material.MATERIAL_REGISTRY.get(0)), 640, 400);
    }

    private void spawnSolarSystem() {

    }

    @Override
    public void act() {
        if (this.acts == 0) {
            this.init();
        }

        this.updateParticles();

        this.acts++;
    }

    private void updateParticles() {
        final Map<StagedActor, Integer> tempMap = new ConcurrentHashMap<>();
        for (int i = 0; i < this.getActors().size(); i++) {
            final StagedActor actor = this.getActors().get(i);
            if (actor != null && !actor.isMarkedForDelete()) {
                tempMap.put(actor, i);
            }
        }
        tempMap.keySet().stream().filter(Particle.class::isInstance).map(Particle.class::cast).forEach(Particle::collide);
        tempMap.keySet().stream().filter(Particle.class::isInstance).map(Particle.class::cast).forEach(Particle::fourForces);
        tempMap.keySet().stream().filter(Particle.class::isInstance).map(Particle.class::cast).forEach(Particle::updateLocation);
        tempMap.keySet().stream().filter(Particle.class::isInstance).map(Particle.class::cast).forEach(Particle::updateRotation);
    }

    private Point getRandomCoord() {
        return new Point((int) (Math.random() * this.getWidth()), (int) (Math.random() * this.getHeight()));
    }

    private void createMaterials() {
        final Material hydrogen = new Material();

        hydrogen.density = (1 / Math.PI);
    }

    public double getTotalMass() {
        double tm = 0.0;
        for (final StagedActor actor : this.getActors()) {
            if (actor instanceof Particle) {
                final Particle p = (Particle) actor;
                tm += p.getMass();
            }
        }
        return tm;
    }
}
