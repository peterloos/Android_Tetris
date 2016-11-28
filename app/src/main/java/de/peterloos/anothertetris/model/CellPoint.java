package de.peterloos.anothertetris.model;

/**
 * Created by Peter on 08.10.2015.
 */
public class CellPoint {

    private int x;
    private int y;

    // c'tors
    public CellPoint() {
        this(0, 0);
    }

    public CellPoint(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public CellPoint(CellPoint point) {
        this(point.getX(), point.getY());
    }

    // getter/setter
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof CellPoint))
            return false;

        CellPoint point = (CellPoint) obj;
        return (this.getX() == point.getX()) && (this.getY() == point.getY());
    }

    @Override
    public String toString() {
        return String.format("X=%d, Y=%d", this.getX(), this.getY());
    }
}
