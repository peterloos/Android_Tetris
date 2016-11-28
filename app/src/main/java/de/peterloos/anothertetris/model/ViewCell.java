package de.peterloos.anothertetris.model;

/**
 * Created by Peter on 08.10.2015.
 */
public class ViewCell {

    private CellColor color;
    private CellPoint point;

    // c'tors
    public ViewCell() {
        this.color = CellColor.LightGray;
        this.point = new CellPoint();
    }

    public ViewCell(CellColor color, CellPoint point) {
        this.color = color;
        this.point = new CellPoint(point);
    }

    // getter/setter
    public CellColor getColor() {
        return color;
    }

    public void setColor(CellColor color) {
        this.color = color;
    }

    public CellPoint getPoint() {
        return point;
    }

    public void setPoint(CellPoint point) {
        this.point = point;
    }
}
