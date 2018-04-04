package main.java.com.higgs.obj;

import main.java.com.higgs.utils.Vector;

import java.awt.image.BufferedImage;

public abstract class Actor {
    protected Vector pos; //position vector from origin (0, 0, 0)
    protected Vector vel; //velocity vector (i, j, k)
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

    @Override
    public String toString() {
        String s = super.toString();
        return "Pos: " + pos.toString() + " Vel: " +vel.toString() + " ID: " + s.substring(s.length() - 8);
    }
}
