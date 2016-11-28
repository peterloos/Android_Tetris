package de.peterloos.anothertetris.model;

import android.os.Debug;

/**
 * Created by Peter on 09.10.2015.
 */
public class Tetrimino_O extends Tetrimino {

    // c'tor
    public Tetrimino_O(ITetrisBoard board) {
        super(board, CellColor.Yellow);
        this.anchorPoint = new CellPoint(5, 1);
    }

    /*
     * predicates
     */

    @Override
    public boolean CanSetToTop() {

        assert (this.rotation == RotationAngle.Degrees_0);

        return !(this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX()).getState() == CellState.Used ||
                this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX() + 1).getState() == CellState.Used ||
                this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX()).getState() == CellState.Used ||
                this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX() + 1).getState() == CellState.Used);
    }

    @Override
    public boolean CanMoveLeft() {
        if (this.anchorPoint.getX() == 0)
            return false;
        if (this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX() - 1).getState() == CellState.Used ||
                this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX() - 1).getState() == CellState.Used)
            return false;

        return true;
    }

    @Override
    public boolean CanMoveRight() {
        if (this.anchorPoint.getX() >= this.board.getNumColumns() - 2)
            return false;
        if (this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX() + 2).getState() == CellState.Used ||
                this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX() + 2).getState() == CellState.Used)
            return false;

        return true;
    }

    @Override
    public boolean CanMoveDown() {
        if (this.anchorPoint.getY() >= this.board.getNumRows() - 2)
            return false;
        if (this.board.getCell(this.anchorPoint.getY() + 2, this.anchorPoint.getX()).getState() == CellState.Used ||
                this.board.getCell(this.anchorPoint.getY() + 2, this.anchorPoint.getX() + 1).getState() == CellState.Used)
            return false;

        return true;
    }

    @Override
    public boolean CanRotate() {
        return true;
    }

     /*
      * board specific methods
      */

    @Override
    public void Update(CellState state) {
        CellColor color = (state == CellState.Free) ? CellColor.LightGray : super.color;
        TetrisCell cell = new TetrisCell(state, color);

        // update model
        this.board.setCell(this.anchorPoint.getY(), this.anchorPoint.getX(), cell);
        this.board.setCell(this.anchorPoint.getY(), this.anchorPoint.getX() + 1, cell);
        this.board.setCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX(), cell);
        this.board.setCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX() + 1, cell);
    }

    @Override
    public void UpdateModifiedCellList(ViewCellList list, CellColor color) {
        list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX(), this.anchorPoint.getY())));
        list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX() + 1, this.anchorPoint.getY())));
        list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX(), this.anchorPoint.getY() + 1)));
        list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX() + 1, this.anchorPoint.getY() + 1)));
    }
}
