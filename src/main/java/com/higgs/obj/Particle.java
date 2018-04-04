package main.java.com.higgs.obj;

import main.java.com.higgs.Universe;
import main.java.com.higgs.graphics.Viewport;
import main.java.com.higgs.utils.Constants;
import main.java.com.higgs.utils.Logger;
import main.java.com.higgs.utils.Utils;
import main.java.com.higgs.utils.Vector;

import javax.rmi.CORBA.Util;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Particle extends Actor {
    private double mass;
    private Material material;

    public Particle(Vector vel, double mass, Material material) {
        this.vel = vel;
        this.mass = mass;
        this.material = material;

        Logger.log("Mass/diameter: " + mass);

        createImage();
    }

    @Override
    public void act() {
        setPosition(Vector.add(pos, vel));
        collide();
        gravity();
    }

    @Override
    void collide() {
        collideWindow();
//        elasticCollide();
        inelasticCollide();
    }

    private void inelasticCollide() {
        ArrayList<Actor> actors = Universe.getInstance().getActorsInRange(this, mass + 1);
        double massBefore = Universe.getInstance().getTotalMass();
        if(!actors.isEmpty()) {
            for(Actor actor : actors) {
                if(actor instanceof Particle) {
                    Particle p = (Particle)actor;
                    if(p != this) {
                        double d = Utils.dist(getPos(), p.getPos());
                        if(d < getRadius() + p.getRadius()) {
                            double nm = getMass() + p.getMass();
                            //following the formula: (m1v1x + m2v2x) / (m1 + m2), (m1v1y + m2v2y) / (m1 + m2)
                            Vector resultantV = new Vector(((getMass() * getXVel()) + (p.getMass() * p.getXVel())) / nm, ((getMass() * getYVel()) + (p.getMass() * p.getYVel())) / nm, 0);

                            Particle amassed = new Particle(resultantV, nm, getMaterial());

                            Vector npos = getMass() >= p.getMass() ? getPos() : p.getPos();

                            Universe.getInstance().removeObject(this);
                            Universe.getInstance().removeObject(actor);
                            Universe.getInstance().addObject(amassed, npos);

                            amassed.removeMass(Universe.getInstance().getTotalMass() - massBefore);
                        }
                    }
                }
            }


        }
    }

    private void collideWindow() {
        double vx = vel.x, vy = vel.y;

        if(getX() <= 0 || getX() >= Viewport.SIZE.getWidth()) vx = -vx;
        if(getY() <= 0 || getY() >= Viewport.SIZE.getHeight()) vy = -vy;

        vel = new Vector(vx, vy, 0);
    }

    private void gravity() {
        for(Actor a : Universe.getInstance().getActors()) {
            if(a instanceof Particle) {
                if(a != this) {
                    Particle p = (Particle) a;
                    applyGravitationalForce(p);
                }
            }
        }
    }

    private void applyGravitationalForce(Particle p) {
        double r = Utils.dist(pos, p.getPos());
        double gmm = Constants.BIG_G * mass * p.getMass();
        impartForce(gmm / Math.pow(r, 2), Math.atan2(p.getX() - getX(), p.getY() - getY()));
    }

    private void impartForce(double force, double angle) {
        double vx = vel.x;
        double vy = vel.y;
        vx += (force*Math.sin(angle)) / mass;
        vy += (force*Math.cos(angle)) / mass;
        vel = new Vector(vx, vy, 0);
    }

    private void accelerate(Vector force) {
        setVelocity(new Vector(force.x / mass, force.y / mass, 0));
    }

    public double getMass() {
        return mass;
    }

    public double getKineticEnergy() {
        return (0.5 * mass * Math.pow(getSpeed(), 2));
    }

    public double getTemperature() {
        return ((2.0/3.0) * getKineticEnergy()) / Constants.BIG_R;
    }

    public double getArea() {
        return mass / material.density;
    }

    public double getRadius() {
        return Math.sqrt(getArea() / Math.PI);
    }

    public Material getMaterial() {
        return material;
    }

    public void removeMass(double mass) {
        this.mass -= mass;
        createImage();
    }

    public void createImage() {
        BufferedImage image = new BufferedImage((int)((2*getRadius()) + 1), (int)((2*getRadius()) + 1), BufferedImage.TYPE_INT_ARGB);
//        BufferedImage image = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        //for showing image box:
//        g.setColor(Color.BLUE);
//        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(Color.WHITE);
        //randomizing color to see result of collisions
//        g.setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
        g.fillOval(0, 0, image.getWidth(), image.getHeight());
        g.dispose();
        setImage(image);
    }
}
