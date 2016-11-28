package de.peterloos.anothertetris.model;

/**
 * Created by Peter on 08.10.2015.
 */
public class TetrisCell {

    private CellState state;
    private CellColor color;

    // c'tors
    public TetrisCell() {
        this(CellState.Free, CellColor.LightGray);
    }

    public TetrisCell(CellState state, CellColor color) {
        this.state = state;
        this.color = color;
    }

    // getter/setter
    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public CellColor getColor() {
        return color;
    }

    public void setColor(CellColor color) {
        this.color = color;
    }

    @Override
    protected Object clone() {
        return new TetrisCell(this.state, this.color);
    }

    @Override
    public String toString() {
        return String.format("State=%s, Color=%s", this.state.toString(), this.color.toString());
    }
}
