package com.higgs.sim.utils;

public class Vector {
    public double x;
    public double y;
    public double z; //only stored for cross multiplication

    public static final Vector i = new Vector(1, 0, 0);
    public static final Vector j = new Vector(0, 1, 0);
    public static final Vector k = new Vector(0, 0, 1);

    public Vector(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(final double length, final double theta) {
        this(length * Math.cos(theta), length * Math.sin(theta), 0);
    }

    public double getLength() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
    }

    public double getTheta() {
        final double p;
        if (this.x == 0.0) {
            p = 0.0;
        } else {
            p = this.y / this.x;
        }
        return p;
    }

    public static Vector setLength(final Vector u, final double length) {
        return new Vector(length, u.getTheta());
    }

    public static Vector scale(final Vector u, final double a) {
        return new Vector(u.x * a, u.y * a, u.z * a);
    }

    public static double dot(final Vector u, final Vector v) {
//        double theta = u.getTheta() - v.getTheta();
//        theta *= (u.getTheta() > v.getTheta() ? 1 : -1);
//
//        return u.getLength()*v.getLength()*Math.cos(theta);
        return (u.x * v.x) + (u.y * v.y) + (u.z * v.z);
    }

    public static Vector cross(final Vector u, final Vector v) {
        return new Vector((u.y * v.z) - (u.z * v.y), (u.x * v.z) - (u.z * v.x), (u.x * v.y) - (u.y * v.x));
    }

    public static double angleBetween(final Vector u, final Vector v) {
//        return v.getTheta() - u.getTheta();
        return Math.cos((Vector.dot(u, v)) / (u.getLength() * v.getLength()));
    }

    /**
     * Adds one vector to the other.
     *
     * @param u first vector
     * @param v second vector
     * @return u+v
     */
    public static Vector add(final Vector u, final Vector v) {
        return Vector.add(u, v, false);
    }

    /**
     * Subtracts one vector from the other.
     *
     * @param u first vector
     * @param v second vector
     * @return u-v
     */
    public static Vector subtract(final Vector u, final Vector v) {
        return Vector.add(u, v, true);
    }

    /**
     * @param u vector to get the length of along v
     * @param v vector (typically unit vector) to get the length of u along
     * @return
     */
    public static double lengthAlong(final Vector u, final Vector v) {
        return Vector.dot(u, v) * u.getLength();
    }

    /**
     * Adds or subtracts the second vector to or from the first vector.
     *
     * @param u first vector
     * @param v second vector
     * @param i whether or not to invert the second vector for subtraction
     * @return either u+v or u-v depending on the value of i
     */
    private static Vector add(final Vector u, final Vector v, final boolean i) {
        final int n = (i ? -1 : 1);
        return new Vector(u.x + (v.x * n), u.y + (v.y * n), u.z + (v.z * n));
    }

    /**
     * @param u vector the result should be perpendicular to
     * @return a vector perpendicular to u and intersecting at the origin
     */
    public static Vector perpendicular(final Vector u) {
        if (u.y != 0) {
            return new Vector(-u.y, u.x, 0);
        } else {
            if (u.x != 0) {
                return new Vector(u.y, -u.x, 0);
            } else {
                return new Vector(0, 0, 0);
            }
        }
    }

    @Override
    public String toString() {
        return "<" + this.x + ", " + this.y + ", " + this.z + ">";
    }
}
