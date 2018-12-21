package com.luminiscental.aoc.util;

public class Point<T extends Comparable<T>> implements Comparable<Point<T>> {

    public T x, y;

    public Point() {}

    public Point(T x, T y) {

        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point<T> other) {

        int xCompare = x.compareTo(other.x);
        int yCompare = y.compareTo(other.y);

        if ((xCompare < 0) || (xCompare == 0) && (yCompare < 0)) {

            return -1;
        }

        return yCompare == 0 ? 0 : 1;
    }

    @Override
    public boolean equals(Object other) {

        Class<? extends Point> pointType = getClass();

        if (pointType.isInstance(other)) {

            Point casted = pointType.cast(other);
            return (x == casted.x) && (y == casted.y);
        }

        return false;
    }

    @Override
    public int hashCode() {

        return x.hashCode() * 131 + y.hashCode() * 17;
    }

    @Override
    public String toString() {

        return "(" + x + ", " + y + ")";
    }

    public T getX() {

        return x;
    }

    public T getY() {

        return y;
    }
}
