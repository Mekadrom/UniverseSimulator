package com.higgs.sim.utils;

public class Vector {
    public double x;
    public double y;
    public double z; //only stored for cross multiplication

    public static final Vector i = new Vector(1, 0, 0);
    public static final Vector j = new Vector(0, 1, 0);
    public static final Vector k = new Vector(0, 0, 1);

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(double length, double theta) {
        this(length * Math.cos(theta), length * Math.sin(theta), 0);
    }

    public double getLength() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public double getTheta() {
        double p;
        if (x == 0.0) {
            p = 0.0;
        } else {
            p = y / x;
        }
        return p;
    }

    public static Vector setLength(Vector u, double length) {
        return new Vector(length, u.getTheta());
    }

    public static Vector scale(Vector u, double a) {
        return new Vector(u.x * a, u.y * a, u.z * a);
    }

    public static double dot(Vector u, Vector v) {
//        double theta = u.getTheta() - v.getTheta();
//        theta *= (u.getTheta() > v.getTheta() ? 1 : -1);
//
//        return u.getLength()*v.getLength()*Math.cos(theta);
        return (u.x * v.x) + (u.y * v.y) + (u.z * v.z);
    }

    public static Vector cross(Vector u, Vector v) {
        return new Vector((u.y * v.z) - (u.z * v.y), (u.x * v.z) - (u.z * v.x), (u.x * v.y) - (u.y * v.x));
    }

    public static double angleBetween(Vector u, Vector v) {
//        return v.getTheta() - u.getTheta();
        return Math.cos((Vector.dot(u, v)) / (u.getLength() * v.getLength()));
    }

    /**
     * Adds one vector to the other.
     * @param u first vector
     * @param v second vector
     * @return u+v
     */
    public static Vector add(Vector u, Vector v) {
        return add(u, v, false);
    }

    /**
     * Subtracts one vector from the other.
     * @param u first vector
     * @param v second vector
     * @return u-v
     */
    public static Vector subtract(Vector u, Vector v) {
        return add(u, v, true);
    }

    /**
     *
     * @param u vector to get the length of along v
     * @param v vector (typically unit vector) to get the length of u along
     * @return
     */
    public static double lengthAlong(Vector u, Vector v) {
        return Vector.dot(u, v) * u.getLength();
    }

    /**
     * Adds or subtracts the second vector to or from the first vector.
     * @param u first vector
     * @param v second vector
     * @param i whether or not to invert the second vector for subtraction
     * @return either u+v or u-v depending on the value of i
     */
    private static Vector add(Vector u, Vector v, boolean i) {
        int n = (i ? -1 : 1);
        return new Vector(u.x + (v.x * n), u.y + (v.y * n), u.z + (v.z * n));
    }

    /**
     *
     * @param u vector the result should be perpendicular to
     * @return a vector perpendicular to u and intersecting at the origin
     */
    public static Vector perpendicular(Vector u) {
        if(u.y != 0) {
            return new Vector(-u.y, u.x, 0);
        } else {
            if(u.x != 0) {
                return new Vector(u.y, -u.x, 0);
            } else {
                return new Vector(0, 0, 0);
            }
        }
    }

    @Override
    public String toString() {
        return "<" + x + ", " + y + ", " + z + ">";
    }
}
