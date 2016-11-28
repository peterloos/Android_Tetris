package de.peterloos.anothertetris.model;

/**
 * Created by Peter on 08.10.2015.
 */
public interface ITetrimino {

    // predicates
    boolean CanSetToTop();

    boolean CanMoveLeft();

    boolean CanMoveRight();

    boolean CanMoveDown();

    boolean CanRotate();

    // movement specific methods
    void SetToTop();

    void MoveLeft();

    void MoveRight();

    boolean MoveDown();

    void Rotate();

    // board specific methods
    void Set();

    void Delete();

    void Update(CellState state);

    void UpdateModifiedCellList(ViewCellList list, CellColor color);
}
