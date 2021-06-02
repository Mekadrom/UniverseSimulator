package com.higgs.sim.obj;

import com.higgs.sim.utils.Vector;

import java.awt.image.BufferedImage;

public abstract class Actor {
    protected Vector pos; //position vector from origin (0, 0, 0)
    protected Vector vel; //velocity vector (i, j, k)
    protected Vector cm = new Vector(0, 0, 0); //center of mass from the origin of the object
    protected double rotation;
    protected double omega; //rotational velocity about the center of mass
    protected BufferedImage image;


    public abstract void act();

    abstract void collide();


    public double getX() {
        return this.pos.x;
    }

    public double getY() {
        return this.pos.y;
    }

    public Vector getPos() {
        return this.pos;
    }

    public double getXVel() {
        return this.vel.x;
    }

    public double getYVel() {
        return this.vel.y;
    }

    public Vector getVel() {
        return this.vel;
    }

    public double getSpeed() {
        return this.vel.getLength();
    }

    public double getAngle() {
        return this.vel.getTheta();
    }

    public double getOmega() {
        return this.omega;
    }

    public double getRotation() {
        return this.rotation;
    }

    public Vector getCenterOfMass() {
        return this.cm;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void setImage(final BufferedImage image) {
        this.image = image;
    }

    public void setPosition(final Vector pos) {
        this.pos = pos;
    }

    public void setVelocity(final Vector vel) {
        this.vel = vel;
    }

    public void rotate(final double rotation) {
        this.setRotation(this.getRotation() + rotation);
    }

    public void setOmega(final double omega) {
        this.omega = omega;
    }

    public void setRotation(final double rotation) {
        this.rotation = rotation;
    }

    public void setCenterofMass(final Vector cm) {
        this.cm = cm;
    }

    public String toString() {
        final String s = super.toString();
//        return "Pos: " + pos.toString() + " Vel: " + vel.toString() + " ID: " + s.substring(s.length() - 8);
        return s.substring(s.length() - 8);
    }
}
