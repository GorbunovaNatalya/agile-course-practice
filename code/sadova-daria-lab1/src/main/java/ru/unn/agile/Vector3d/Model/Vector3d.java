package ru.unn.agile.vectors.model;

public class Vector3d {
private double x;
private double y;
private double z;

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

public Vector3d(final double x, final double y, final double z) {
   this.x = x;
   this.y = y;
   this.z = z;
    }

public double vectorLength() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
    }

public Vector3d normalize() {
    double len = vectorLength();
    return new Vector3d(x / len, y / len, z / len);
    }

public double dotProduct(final Vector3d first) {
        return (x * first.x + y * first.y + z * first.z);
    }

public Vector3d crossProduct(final Vector3d first) {
    double newX = y * first.z - z * first.y;
    double newY = -(x * first.z - z * first.x);
    double newZ = x * first.y - y * first.x;
    return new Vector3d(newX, newY, newZ);
    }

    private boolean isVectorValid() {
        return (getX() != 0) && (getY() != 0) && (getZ() != 0);
    }

    public boolean equalComplitely(final Vector3d first) {
        return (getX() == first.getX()) && (getY() == first.getY()) && (getZ() == first.getZ());
    }
    public boolean equalNormalized(final Vector3d first) {
        return normalize().equalComplitely(first.normalize());
    }
}

