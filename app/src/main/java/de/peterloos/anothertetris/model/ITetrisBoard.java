package de.peterloos.anothertetris.model;

/**
 * Created by Peter on 19.05.2016.
 */
public interface ITetrisBoard {

    // properties
    int getNumRows();

    int getNumColumns();

    TetrisCell getCell(int row, int col);

    void setCell(int row, int col, TetrisCell cell);

    // methods
    void Clear();

    void PostChanges(ViewCellList list);

    boolean IsBottomRowComplete();

    void MoveNonEmptyRowsDown();

    // event handling
    void registerBoardListener(IBoardListener listener);

    void unregisterBoardListener(IBoardListener listener);
}
