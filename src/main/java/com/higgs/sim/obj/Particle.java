package com.higgs.sim.obj;

import com.higgs.sim.Universe;
import com.higgs.sim.utils.Constants;
import com.higgs.sim.utils.Utils;
import com.higgs.staged.Animation;
import com.higgs.staged.SimpleAnimation;
import com.higgs.staged.StageUtils;
import com.higgs.staged.StagedActor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class Particle extends StagedActor {
    private int acts;

    private double mass;
    private final Material material;
    private final double omega;

    private double xVel;
    private double yVel;

    public Particle(final double xVel, final double yVel, final double omega, final double mass, final Material material) {
        this.xVel = xVel;
        this.yVel = yVel;
        this.omega = omega;
        this.mass = mass;
        this.material = material;

        System.out.println("hello! i am " + this);
    }

    private void init() {
        this.createImage();
    }

    @Override
    public void act() {
        if (this.acts == 0) {
            this.init();
        }

        this.acts++;
    }

    public void collide() {
//        this.collideWindow();
//        elasticCollide();
        this.inelasticCollide();
    }

    public void fourForces() {
        this.gravity();
    }

    public void inelasticCollide() {
        final List<StagedActor> actors = this.getStage().getActorsInRange(this, this.getRadius() * 2);
        if (!actors.isEmpty()) {
            for (final StagedActor actor : actors) {
                if (actor instanceof Particle) {
                    final Particle p = (Particle) actor;
                    if (p != this && !this.isMarkedForDelete() && !p.isMarkedForDelete()) {
                        final double d = Utils.dist(this.getX(), this.getY(), p.getX(), p.getY());
                        if (d < this.getRadius() + p.getRadius()) {
                            final double nm = this.getMass() + p.getMass();
                            // following the formula for conservation of momentum in an inelastic collision: (m1v1x + m2v2x) / (m1 + m2), (m1v1y + m2v2y) / (m1 + m2)
                            final double resultantXVel = ((this.getMass() * this.xVel) + (p.getMass() * p.getXVel())) / nm;
                            final double resultantYVel = ((this.getMass() * this.yVel) + (p.getMass() * p.getYVel())) / nm;

//                            final Particle amassed = new Particle(resultantXVel, resultantYVel, 0, nm, this.getMaterial());

                            // use equation for center of mass between two discrete objects to find new particle's position
                            final double newX = ((this.getMass() * this.getX()) + (p.getMass() * p.getX())) / nm;
                            final double newY = ((this.getMass() * this.getY()) + (p.getMass() * p.getY())) / nm;

                            this.getStage().markForDelete(actor);
                            this.setMass(nm);
                            this.xVel = resultantXVel;
                            this.yVel = resultantYVel;
                            this.setX(newX);
                            this.setY(newY);
                        }
                    }
                }
            }
        }
    }

    public void collideWindow() {
        if (this.getX() <= 0 || this.getX() >= this.getStage().getWidth()) {
            this.xVel = -this.xVel;
            this.setX((int) Math.round(StageUtils.bound(this.getX(), 0, this.getStage().getWidth())));
        }
        if (this.getY() <= 0 || this.getY() >= this.getStage().getHeight()) {
            this.yVel = -this.yVel;
            this.setY((int) Math.round(StageUtils.bound(this.getY(), 0, this.getStage().getHeight())));
        }
    }

    private void gravity() {
        for (final StagedActor a : this.getStage().getActors()) {
            if (a instanceof Particle) {
                if (a != this && !a.isMarkedForDelete()) {
                    this.applyGravitationalForce((Particle) a);
                }
            }
        }
    }

    private void rotate(final double omega) {
        this.setAngle(this.getAngle() + omega);
    }

    private void applyGravitationalForce(final Particle p) {
        final double r = Utils.dist(this.getX(), this.getY(), p.getX(), p.getY());
        final long dir = (int) (r / Math.abs(r));
        final double gmm = Constants.BIG_G * this.mass * p.getMass();
        this.impartForce(dir * gmm / Math.pow(r, 2), Math.atan2(p.getY() - this.getY(), p.getX() - this.getX()));
    }

    private void impartForce(final double force, final double angle) {
        this.xVel += (force * Math.cos(angle)) / this.mass;
        this.yVel += (force * Math.sin(angle)) / this.mass;
    }

    private void accelerate(final double xForce, final double yForce) {
        this.xVel = xForce / this.mass;
        this.yVel = yForce / this.mass;
    }

    public double getMass() {
        return this.mass;
    }

    public void setMass(final double mass) {
        this.mass = mass;
        this.createImage();
    }

    public double getRotInertia() {
        return this.mass * Math.pow(this.getRadius(), 2);
    }

    public double getKineticEnergy() {
        return (0.5 * this.mass * Math.pow(this.getSpeed(), 2));
    }

    private double getSpeed() {
        return Math.sqrt(Math.pow(this.xVel, 2) + Math.pow(this.yVel, 2));
    }

    public void addKineticEnergy(final double ke) {
        final double newSpeed = Math.sqrt((2 * ke) / this.mass);
        this.xVel = newSpeed * Math.cos(this.omega);
        this.yVel = newSpeed * Math.sin(this.omega);
    }

    public double getTemperature() {
        return ((2.0 / 3.0) * this.getKineticEnergy()) / Constants.BIG_R;
    }

    public double getArea() {
        return this.mass / this.material.density;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void removeMass(final double mass) {
        this.mass -= mass;
        this.createImage();
    }

    @Override
    public int getRadius() {
        return (int) Math.sqrt(this.getArea() / Math.PI);
    }

    public void createImage() {
        final BufferedImage image = new BufferedImage((2 * this.getRadius()) + 1, (2 * this.getRadius()) + 1, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillOval(0, 0, image.getWidth(), image.getHeight());
        g.dispose();
        this.setAnimation(new SimpleAnimation(Animation.NO_TRANS, image));
    }

    public double getXVel() {
        return this.xVel;
    }

    public double getYVel() {
        return this.yVel;
    }

    public void updateLocation() {
        this.setLocation(this.getX() + this.xVel, this.getY() + this.yVel);
    }

    public void updateRotation() {
        this.rotate(this.omega);
    }
}
