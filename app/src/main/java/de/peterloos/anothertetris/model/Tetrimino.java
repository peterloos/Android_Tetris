package de.peterloos.anothertetris.model;

/**
 * Created by Peter on 08.10.2015.
 */
public abstract class Tetrimino implements ITetrimino {

    protected enum RotationAngle {Degrees_0, Degrees_90, Degrees_180, Degrees_270}

    protected ITetrisBoard board;
    protected CellPoint anchorPoint;
    protected RotationAngle rotation;
    protected CellColor color;

    public Tetrimino(ITetrisBoard board, CellColor color) {
        this.board = board;
        this.rotation = RotationAngle.Degrees_0;
        this.color = color;

        this.anchorPoint = null;
    }

    // abstract interface
    public abstract boolean CanSetToTop();

    public abstract boolean CanMoveLeft();

    public abstract boolean CanMoveRight();

    public abstract boolean CanMoveDown();

    public abstract boolean CanRotate();

    // public interface (movement specific methods)
    public void SetToTop() {
        this.Set();
        ViewCellList list = new ViewCellList();
        this.UpdateModifiedCellList(list, this.color);
        this.board.PostChanges(list);
    }

    public void MoveLeft() {
        ViewCellList list = new ViewCellList();
        this.Delete();
        this.UpdateModifiedCellList(list, CellColor.LightGray);
        this.MoveAnchorLeft();
        this.Set();
        this.UpdateModifiedCellList(list, this.color);
        this.board.PostChanges(list);
    }

    public void MoveRight() {
        ViewCellList list = new ViewCellList();
        this.Delete();
        this.UpdateModifiedCellList(list, CellColor.LightGray);
        this.MoveAnchorRight();
        this.Set();
        this.UpdateModifiedCellList(list, this.color);
        this.board.PostChanges(list);
    }

    public boolean MoveDown() {
        if (!this.CanMoveDown())
            return false;

        ViewCellList list = new ViewCellList();
        this.Delete();
        this.UpdateModifiedCellList(list, CellColor.LightGray);
        this.MoveAnchorDown();
        this.Set();
        this.UpdateModifiedCellList(list, this.color);
        this.board.PostChanges(list);

        return true;
    }

    public void Rotate() {
        ViewCellList list = new ViewCellList();
        this.Delete();
        this.UpdateModifiedCellList(list, CellColor.LightGray);
        this.RotateTetrimino();
        this.Set();
        this.UpdateModifiedCellList(list, this.color);
        this.board.PostChanges(list);
    }

    // public interface (board specific methods)
    public void Set() {
        this.Update(CellState.Used);
    }

    public void Delete() {
        this.Update(CellState.Free);
    }

    public abstract void Update(CellState state);

    public abstract void UpdateModifiedCellList(ViewCellList list, CellColor color);

    // private (protected) helper methods
    protected void MoveAnchorLeft() {
        this.anchorPoint.setX(this.anchorPoint.getX() - 1);
    }

    protected void MoveAnchorRight() {
        this.anchorPoint.setX(this.anchorPoint.getX() + 1);
    }

    protected void MoveAnchorDown() {
        this.anchorPoint.setY(this.anchorPoint.getY() + 1);
    }

    protected void RotateTetrimino() {

        if (this.rotation == RotationAngle.Degrees_0) {
            this.rotation = RotationAngle.Degrees_90;
        } else if (this.rotation == RotationAngle.Degrees_90) {
            this.rotation = RotationAngle.Degrees_180;
        } else if (this.rotation == RotationAngle.Degrees_180) {
            this.rotation = RotationAngle.Degrees_270;
        } else if (this.rotation == RotationAngle.Degrees_270) {
            this.rotation = RotationAngle.Degrees_0;
        }
    }
}
