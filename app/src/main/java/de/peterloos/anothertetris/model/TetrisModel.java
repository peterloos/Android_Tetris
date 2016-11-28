package de.peterloos.anothertetris.model;

/**
 * Created by Peter on 03.11.2015.
 */
public abstract class TetrisModel implements ITetris {

    private static final int Columns = 10;
    private static final int Rows = 20;

    @Override
    public int getNumRows() {
        return Rows;
    }

    @Override
    public int getNumColumns() {
        return Columns;
    }
}
