package com.higgs.sim.obj;

import com.higgs.sim.Universe;
import com.higgs.sim.graphics.Viewport;
import com.higgs.sim.utils.Constants;
import com.higgs.sim.utils.Logger;
import com.higgs.sim.utils.Utils;
import com.higgs.sim.utils.Vector;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Particle extends Actor {
    private double mass;
    private Material material;

    public Particle(Vector vel, double omega, double mass, Material material) {
        this.vel = vel;
        this.omega = omega;
        this.mass = mass;
        this.material = material;

//        Logger.log("Mass/diameter: " + mass);
//        Logger.log("Rotational velocity: " + omega);
//        Logger.log("Velocity: " + vel);
        String s = ((Object)this).toString();
        Logger.log("Hello! I am " + s.substring(s.length() - 8));

        createImage();
    }

    @Override
    public void act() {
        collide();
        fourForces();
        setPosition(Vector.add(pos, vel));
        rotate(omega);
    }

    @Override
    void collide() {
        collideWindow();
//        elasticCollide();
        inelasticCollide();
    }

    private void fourForces() {
        gravity();
//        orbit();
    }

    private void elasticCollide() {
        ArrayList<Actor> actors = Universe.getInstance().getActorsInRange(this, getRadius() * 2);
        if(!actors.isEmpty()) {
//            for(Actor actor : actors) {
            Actor actor = actors.get(0);
                if(actor instanceof Particle) {
                    Particle p = (Particle)actor;
                    double d = Utils.dist(getPos(), p.getPos());
                    if(d < getRadius() + p.getRadius()) {
                        double xdist = p.getX() - getX();
                        double ydist = p.getY() - getY();

                        double m1 = getMass();
                        double m2 = p.getMass();
                        double nm = getMass() + p.getMass();

                        Vector v1 = getVel();
                        Vector v2 = p.getVel();

                        double phi = Math.atan2(ydist, xdist);

//                        double rvx1 = (((v1.getLength()*Math.cos(v1.getTheta()-phi)*(m1-m2)) + (2*m2*v2.getLength()*Math.cos(v2.getTheta()-phi))) / nm)*Math.cos(phi)
//                                - v1.getLength()*Math.sin(v1.getTheta()-phi)*Math.sin(phi);
//                        double rvy1 = (((v1.getLength()*Math.cos(v1.getTheta()-phi)*(m1-m2)) + (2*m2*v2.getLength()*Math.cos(v2.getTheta()-phi))) / nm)*Math.sin(phi)
//                                + v1.getLength()*Math.sin(v1.getTheta()-phi)*Math.cos(phi);
//
//                        double rvx2 = (((v2.getLength()*Math.cos(v2.getTheta()-phi)*(m2-m1)) + (2*m1*v1.getLength()*Math.cos(v1.getTheta()-phi))) / nm)*Math.cos(phi)
//                                - v2.getLength()*Math.sin(v2.getTheta()-phi)*Math.sin(phi);
//                        double rvy2 = (((v2.getLength()*Math.cos(v2.getTheta()-phi)*(m2-m1)) + (2*m1*v1.getLength()*Math.cos(v1.getTheta()-phi))) / nm)*Math.sin(phi)
//                                + v2.getLength()*Math.sin(v2.getTheta()-phi)*Math.cos(phi);
//                        Vector g1 = getGravitationalForce(p);
//                        Vector g2 = getGravitationalForce(p);

//                        Vector gravForce1 = new Vector(g1.getLength(), g1.getTheta() + Math.PI);
//                        Vector gravForce2 = new Vector(-g2.x, -g2.y, -g2.z);
//                        impartForce(gravForce1.getLength(), gravForce1.getTheta());
//                        impartForce(gravForce2.getLength(), gravForce2.getTheta());
                    }
                }
//            }
        }
    }

//    private void inelasticCollide() {
//        ArrayList<Actor> actors = Universe.getInstance().getActorsInRange(this, getRadius() * 2);
//        double massBefore = Universe.getInstance().getTotalMass();
//        if(!actors.isEmpty()) {
//            for(Actor actor : actors) {
//                if(actor instanceof Particle) {
//                    Particle p = (Particle)actor;
//                    if(p != this) {
//                        double d = Utils.dist(getPos(), p.getPos());
//                        if(d < getRadius() + p.getRadius()) {
//                            double xDist = p.getX() - getX();
//                            double yDist = p.getY() - getY();
//
//                            //vector of line between the two circles' centers of mass
//                            Vector u = new Vector(xDist, yDist, 0);
//                            u = Vector.setLength(u ,1);
//                            //vector for line tangent to both circles
//                            Vector v = Vector.perpendicular(u);
//
//                            Vector v1 = getVel();
//                            Vector v2 = p.getVel();
//
////                            double theta1 = Vector.angleBetween(u, Vector.i);
////                            double theta2 = Vector.angleBetween(v, Vector.i);
//                            double theta1 = Vector.angleBetween(v1, u);
//                            double theta2 = Vector.angleBetween(v1, v);
//
//                            double m1 = getMass();
//                            double m2 = p.getMass();
//                            double nm = getMass() + p.getMass();
//
//                            double percentOfVel1ParaU = Vector.lengthAlong(v1, u); //to be converted into linear velocity
//                            double percentOfVel2ParaU = Vector.lengthAlong(v2, u); //to be converted into linear velocity
//                            double percentOfVel1ParaV = Vector.lengthAlong(v1, v); //to be converted into rotational velocity
//                            double percentOfVel2ParaV = Vector.lengthAlong(v2, v); //to be converted into rotational velocity
//
//                            Vector momentumAlongU = new Vector((percentOfVel1ParaU + percentOfVel2ParaU) / ((m1 > m2) ? m1 : m2), theta1);
//                            Vector momentumAlongV = new Vector(-(percentOfVel1ParaV + percentOfVel2ParaV) / ((m1 > m2) ? m1 : m2), theta2);
//
//                            double rvx = Vector.lengthAlong(Vector.scale(momentumAlongU, 1), Vector.i);
//                            double rvy = Vector.lengthAlong(Vector.scale(momentumAlongV, 1), Vector.i);
//
//                            Vector fVel = new Vector(rvx, rvy, 0);
//
//                            double rVel = getOmega() - p.getOmega() + percentOfVel1ParaV - percentOfVel2ParaV;
//
//                            Particle amassed = new Particle(fVel, rVel, nm, material);
//
//                            Vector npos = new Vector(((getMass() * getX()) + (p.getMass() * p.getX())) / nm, ((getMass() * getY()) + (p.getMass() * p.getY())) / nm, 0);
//
//                            Universe.getInstance().removeObject(this);
//                            Universe.getInstance().removeObject(actor);
//                            Universe.getInstance().addObject(amassed, npos);
//
//                            amassed.removeMass(Universe.getInstance().getTotalMass() - massBefore);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
    private void inelasticCollide() {
        ArrayList<Actor> actors = Universe.getInstance().getActorsInRange(this, getRadius() * 2);
        double massBefore = Universe.getInstance().getTotalMass();
        if(!actors.isEmpty()) {
            for(Actor actor : actors) {
                if(actor instanceof Particle) {
                    Particle p = (Particle)actor;
                    if(p != this) {
                        double d = Utils.dist(getPos(), p.getPos());
                        if(d < getRadius() + p.getRadius()) {
                            double nm = getMass() + p.getMass();
                            //following the formula for conservation of momentum in an inelastic collision: (m1v1x + m2v2x) / (m1 + m2), (m1v1y + m2v2y) / (m1 + m2)
                            Vector resultantV = new Vector(((getMass() * getXVel()) + (p.getMass() * p.getXVel())) / nm, ((getMass() * getYVel()) + (p.getMass() * p.getYVel())) / nm, 0);

                            Particle amassed = new Particle(resultantV, 0, nm, getMaterial());

                            //use equation for center of mass between two discrete objects to find new particle's position
                            Vector npos = new Vector(((getMass() * getX()) + (p.getMass() * p.getX())) / nm, ((getMass() * getY()) + (p.getMass() * p.getY())) / nm, 0);

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

//        private void inelasticCollide() {
//        ArrayList<Actor> actors = Universe.getInstance().getActorsInRange(this, getRadius() * 2);
//        double massBefore = Universe.getInstance().getTotalMass();
//        if(!actors.isEmpty()) {
//            for(Actor actor : actors) {
//                if(actor instanceof Particle) {
//                    Particle p = (Particle)actor;
//                    if(p != this) {
//                        double d = Utils.dist(getPos(), p.getPos());
//                        if(d < getRadius() + p.getRadius()) {
//                            double xDist = p.getX() - getX();
//                            double yDist = p.getY() - getY();
//
//                            //vector of line between the two circles' centers of mass
//                            Vector u = new Vector(xDist, yDist, 0);
//                            u = Vector.setLength(u ,1);
//                            //vector for line tangent to both circles
//                            Vector v = Vector.perpendicular(u);
//
//                            Vector v1 = getVel();
//                            Vector v2 = p.getVel();
//
//                            double m1 = getMass();
//                            double m2 = p.getMass();
//                            double nm = getMass() + p.getMass();
//
//                            double nv1 = Vector.lengthAlong(v1, u);
//                            double nv2 = Vector.lengthAlong(v2, u);
//                            double na1 = Vector.lengthAlong(v1, v);
//                            double na2 = Vector.lengthAlong(v2, v);
//
//                            //vFLC = velocityForLinearCalculation
//                            Vector vFLC1 = new Vector(nv1, -v.getTheta());
//                            Vector vFLC2 = new Vector(nv2, -v.getTheta());
//
//                          //Vector fVel = new Vector(    ((getMass() * getXVel()) + (p.getMass() * p.getXVel())) / nm,                        ((getMass() * getYVel()) + (p.getMass() * p.getYVel())) / nm, 0);
//                            Vector fVel = new Vector(((m1 * vFLC1.x) + (m2 * vFLC2.x)) / nm, ((m1 * vFLC1.y) + (m2 * vFLC2.y)) / nm, 0);
//
//                            double aVel = getOmega() + p.getOmega() + na1 + na2;
//
//                            Particle amassed = new Particle(fVel, aVel, nm, material);
//
//                            Vector npos = new Vector(((m1 * getX()) + (m2 * p.getX())) / nm, ((m1 * getY()) + (m2 * p.getY())) / nm, 0);
//
//                            Universe.getInstance().removeObject(this);
//                            Universe.getInstance().removeObject(actor);
//                            Universe.getInstance().addObject(amassed, npos);
//
//                            amassed.removeMass(Universe.getInstance().getTotalMass() - massBefore);
//                        }
//                    }
//                }
//            }
//        }
//    }

    private void collideWindow() {
        double vx = vel.x, vy = vel.y;

        if(getX() <= 0 || getX() >= Viewport.SIZE.getWidth()) vx = -vx;
        if(getY() <= 0 || getY() >= Viewport.SIZE.getHeight()) vy = -vy;

        vel = new Vector(vx, vy, 0);
    }

    private void orbit() {
        Particle p = new Particle(new Vector(0, 0, 0), 0, 100, Material.MATERIAL_REGISTRY.get(0));
        p.setPosition(new Vector(Viewport.SIZE.getWidth() / 2, Viewport.SIZE.getHeight() / 2, 0));
        applyGravitationalForce(p);
    }

    private void gravity() {
        for(Actor a : Universe.getInstance().getActors()) {
            if(a instanceof Particle) {
                if(a != this) {
                    Particle p = (Particle)a;
                    applyGravitationalForce(p);
//                    Vector gforce = getGravitationalForce(p);
//                    impartForce(gforce.getLength(), gforce.getTheta());
                }
            }
        }
    }

//    public Vector getGravitationalForce(Particle p) {
//        double r = Utils.dist(pos, p.getPos());
//        double gmm = Constants.BIG_G * mass * p.getMass();
//        return new Vector(gmm / Math.pow(r, 2), Math.atan2(p.getX() - getX(), p.getY() - getY()));
//    }
//
//    private void impartForce(double force, double angle) {
//        double vx = vel.x;
//        double vy = vel.y;
//        vx += (force*Math.sin(angle)) / mass;
//        vy += (force*Math.cos(angle)) / mass;
//        vel = new Vector(vx, vy, 0);
//    }

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

    public double getRotInertia() {
        return mass * Math.pow(getRadius(), 2);
    }

    public double getKineticEnergy() {
        return (0.5 * mass * Math.pow(getSpeed(), 2));
    }

    public void addKineticEnergy(double ke) {
        vel = Vector.setLength(vel, Math.sqrt((2 * ke) / mass));
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
