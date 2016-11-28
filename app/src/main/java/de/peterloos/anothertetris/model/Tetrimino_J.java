package de.peterloos.anothertetris.model;

/**
 * Created by Peter on 14.11.2016.
 */

public class Tetrimino_J extends Tetrimino {

    // c'tor
    public Tetrimino_J(ITetrisBoard board) {
        super(board, CellColor.Blue);
        this.anchorPoint = new CellPoint(5, 2);
    }

    /*
     * predicates
     */

    @Override
    public boolean CanSetToTop() {
        assert (this.rotation == RotationAngle.Degrees_0);

        return !(this.board.getCell(this.anchorPoint.getY() - 1, this.anchorPoint.getX() + 1).getState() == CellState.Used ||
                this.board.getCell(this.anchorPoint.getY() - 1, this.anchorPoint.getX()).getState() == CellState.Used ||
                this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX()).getState() == CellState.Used ||
                this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX()).getState() == CellState.Used);
    }

    @Override
    public boolean CanMoveLeft() {
        // check fields to the left of the tetrimino
        switch (this.rotation) {
            case Degrees_0:
                if (this.anchorPoint.getX() == 0)
                    return false;
                if (this.board.getCell(this.anchorPoint.getY() - 1, this.anchorPoint.getX() - 1).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX() - 1).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX() - 1).getState() == CellState.Used)
                    return false;
                break;

            case Degrees_90:
                if (this.anchorPoint.getX() == 1)
                    return false;
                if (this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX() - 2).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX()).getState() == CellState.Used)
                    return false;
                break;

            case Degrees_180:
                if (this.anchorPoint.getX() == 1)
                    return false;
                if (this.board.getCell(this.anchorPoint.getY() - 1, this.anchorPoint.getX() - 1).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX() - 1).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX() - 2).getState() == CellState.Used)
                    return false;
                break;

            case Degrees_270:
                if (this.anchorPoint.getX() == 1)
                    return false;
                if (this.board.getCell(this.anchorPoint.getY() - 1, this.anchorPoint.getX() - 2).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX() - 2).getState() == CellState.Used)
                    return false;
                break;
        }

        return true;
    }

    @Override
    public boolean CanMoveRight() {
        // check fields to the right of the tetrimino
        switch (this.rotation) {
            case Degrees_0:
                if (this.anchorPoint.getX() == this.board.getNumColumns() - 2)
                    return false;
                if (this.board.getCell(this.anchorPoint.getY() - 1, this.anchorPoint.getX() + 2).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX() + 1).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX() + 1).getState() == CellState.Used)
                    return false;
                break;

            case Degrees_90:
                if (this.anchorPoint.getX() == this.board.getNumColumns() - 2)
                    return false;
                if (this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX() + 2).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX() + 2).getState() == CellState.Used)
                    return false;
                break;

            case Degrees_180:
                if (this.anchorPoint.getX() == this.board.getNumColumns() - 1)
                    return false;
                if (this.board.getCell(this.anchorPoint.getY() - 1, this.anchorPoint.getX() + 1).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX() + 1).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX() + 1).getState() == CellState.Used)
                    return false;
                break;

            case Degrees_270:
                if (this.anchorPoint.getX() == this.board.getNumColumns() - 2)
                    return false;
                if (this.board.getCell(this.anchorPoint.getY() - 1, this.anchorPoint.getX()).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX() + 2).getState() == CellState.Used)
                    return false;
                break;
        }

        return true;
    }

    @Override
    public boolean CanMoveDown() {
        // check for bottom line & check fields below tetrimino
        switch (this.rotation) {
            case Degrees_0:
                if (this.anchorPoint.getY() >= this.board.getNumRows() - 2)
                    return false;
                if (this.board.getCell(this.anchorPoint.getY() + 2, this.anchorPoint.getX()).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX() + 1).getState() == CellState.Used)
                    return false;
                break;

            case Degrees_90:
                if (this.anchorPoint.getY() >= this.board.getNumRows() - 2)
                    return false;
                if (this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX() - 1).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX()).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY() + 2, this.anchorPoint.getX() + 1).getState() == CellState.Used)
                    return false;
                break;

            case Degrees_180:
                if (this.anchorPoint.getY() >= this.board.getNumRows() - 2)
                    return false;
                if (this.board.getCell(this.anchorPoint.getY() + 2, this.anchorPoint.getX() - 1).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY() + 2, this.anchorPoint.getX()).getState() == CellState.Used)
                    return false;
                break;

            case Degrees_270:
                if (this.anchorPoint.getY() >= this.board.getNumRows() - 1)
                    return false;
                if (this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX() - 1).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX()).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX() + 1).getState() == CellState.Used)
                    return false;
                break;
        }

        return true;
    }

    @Override
    public boolean CanRotate() {
        if (this.anchorPoint.getX() == 0 || this.anchorPoint.getX() == this.board.getNumColumns() - 1)
            return false;

        switch (this.rotation) {
            case Degrees_0:
                if (this.anchorPoint.getY() >= this.board.getNumRows() - 1)
                    return false;
                if (this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX() - 1).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX() + 1).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX() + 1).getState() == CellState.Used)
                    return false;
                break;

            case Degrees_90:
                if (this.anchorPoint.getY() >= this.board.getNumRows() - 2)
                    return false;
                if (this.board.getCell(this.anchorPoint.getY() - 1, this.anchorPoint.getX()).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX() - 1).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX()).getState() == CellState.Used)
                    return false;
                break;

            case Degrees_180:
                if (this.anchorPoint.getY() >= this.board.getNumRows() - 2)
                    return false;
                if (this.board.getCell(this.anchorPoint.getY() - 1, this.anchorPoint.getX() - 1).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX() - 1).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY(), this.anchorPoint.getX() + 1).getState() == CellState.Used)
                    return false;
                break;

            case Degrees_270:
                if (this.anchorPoint.getY() >= this.board.getNumRows() - 2)
                    return false;
                if (this.board.getCell(this.anchorPoint.getY() - 1, this.anchorPoint.getX() + 1).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY() - 1, this.anchorPoint.getX()).getState() == CellState.Used ||
                        this.board.getCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX()).getState() == CellState.Used)
                    return false;
                break;
        }

        return true;
    }


    /*
     * board specific methods
     */

    @Override
    public void Update(CellState state) {
        CellColor color = (state == CellState.Free) ? CellColor.LightGray : this.color;
        TetrisCell cell = new TetrisCell(state, color);

        // update model
        if (this.rotation == RotationAngle.Degrees_0) {
            this.board.setCell(this.anchorPoint.getY() - 1, this.anchorPoint.getX() + 1, cell);
            this.board.setCell(this.anchorPoint.getY() - 1, this.anchorPoint.getX(), cell);
            this.board.setCell(this.anchorPoint.getY(), this.anchorPoint.getX(), cell);
            this.board.setCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX(), cell);
        } else if (this.rotation == RotationAngle.Degrees_90) {
            this.board.setCell(this.anchorPoint.getY(), this.anchorPoint.getX() - 1, cell);
            this.board.setCell(this.anchorPoint.getY(), this.anchorPoint.getX(), cell);
            this.board.setCell(this.anchorPoint.getY(), this.anchorPoint.getX() + 1, cell);
            this.board.setCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX() + 1, cell);
        } else if (this.rotation == RotationAngle.Degrees_180) {
            this.board.setCell(this.anchorPoint.getY() - 1, this.anchorPoint.getX(), cell);
            this.board.setCell(this.anchorPoint.getY(), this.anchorPoint.getX(), cell);
            this.board.setCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX(), cell);
            this.board.setCell(this.anchorPoint.getY() + 1, this.anchorPoint.getX() - 1, cell);
        } else if (this.rotation == RotationAngle.Degrees_270) {
            this.board.setCell(this.anchorPoint.getY() - 1, this.anchorPoint.getX() - 1, cell);
            this.board.setCell(this.anchorPoint.getY(), this.anchorPoint.getX() - 1, cell);
            this.board.setCell(this.anchorPoint.getY(), this.anchorPoint.getX(), cell);
            this.board.setCell(this.anchorPoint.getY(), this.anchorPoint.getX() + 1, cell);
        }
    }

    @Override
    public void UpdateModifiedCellList(ViewCellList list, CellColor color) {
        if (this.rotation == RotationAngle.Degrees_0) {
            list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX() + 1, this.anchorPoint.getY() - 1)));
            list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX(), this.anchorPoint.getY() - 1)));
            list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX(), this.anchorPoint.getY())));
            list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX(), this.anchorPoint.getY() + 1)));
        } else if (this.rotation == RotationAngle.Degrees_90) {
            list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX() - 1, this.anchorPoint.getY())));
            list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX(), this.anchorPoint.getY())));
            list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX() + 1, this.anchorPoint.getY())));
            list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX() + 1, this.anchorPoint.getY() + 1)));
        } else if (this.rotation == RotationAngle.Degrees_180) {
            list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX(), this.anchorPoint.getY() - 1)));
            list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX(), this.anchorPoint.getY())));
            list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX(), this.anchorPoint.getY() + 1)));
            list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX() - 1, this.anchorPoint.getY() + 1)));
        } else if (this.rotation == RotationAngle.Degrees_270) {
            list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX() - 1, this.anchorPoint.getY() - 1)));
            list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX() - 1, this.anchorPoint.getY())));
            list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX(), this.anchorPoint.getY())));
            list.Add(new ViewCell(color, new CellPoint(this.anchorPoint.getX() + 1, this.anchorPoint.getY())));
        }
    }
}
