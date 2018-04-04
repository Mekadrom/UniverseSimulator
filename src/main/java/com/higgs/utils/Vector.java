package main.java.com.higgs.utils;

public class Vector {
    public double x;
    public double y;
    public double z; //only stored for cross multiplication

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getLength() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public double getTheta() {
        return Math.atan2(y, x);
    }

    public void scale(double a) {
        x *= a;
        y *= a;
    }

    public static double dot(Vector u, Vector v) {
        double theta = u.getTheta() - v.getTheta();
        theta *= (u.getTheta() > v.getTheta() ? 1 : -1);

        return u.getLength()*v.getLength()*Math.cos(theta);
    }

    public static Vector cross(Vector u, Vector v) {
        return new Vector((u.y * v.z) - (u.z * v.y), (u.x * v.z) - (u.z * v.x), (u.x * v.y) - (u.y * v.x));
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

    @Override
    public String toString() {
        return "<" + x + ", " + y + ", " + z + ">";
    }
}
