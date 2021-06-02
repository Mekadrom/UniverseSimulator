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
        return pos.x;
    }

    public double getY() {
        return pos.y;
    }

    public Vector getPos() {
        return pos;
    }

    public double getXVel() {
        return vel.x;
    }

    public double getYVel() {
        return vel.y;
    }

    public Vector getVel() {
        return vel;
    }

    public double getSpeed() {
        return vel.getLength();
    }

    public double getAngle() {
        return vel.getTheta();
    }

    public double getOmega() {
        return omega;
    }

    public double getRotation() {
        return rotation;
    }

    public Vector getCenterOfMass() {
        return cm;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setPosition(Vector pos) {
        this.pos = pos;
    }

    public void setVelocity(Vector vel) {
        this.vel = vel;
    }

    public void rotate(double rotation) {
        setRotation(getRotation() + rotation);
    }

    public void setOmega(double omega) {
        this.omega = omega;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setCenterofMass(Vector cm) {
        this.cm = cm;
    }

    public String toString() {
        String s = super.toString();
//        return "Pos: " + pos.toString() + " Vel: " + vel.toString() + " ID: " + s.substring(s.length() - 8);
        return s.substring(s.length() - 8);
    }
}
