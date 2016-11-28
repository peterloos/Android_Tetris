package de.peterloos.anothertetris.model;

/**
 * Created by Peter on 08.10.2015.
 */
public interface ITetris {

    enum TetrisAction {None, Left, Right, Rotate, BeginAllWayDown}

    // properties
    int getNumRows();

    int getNumColumns();

    // game commands
    void Start();

    void Stop();

    // controller requests
    void DoAction(TetrisAction action);

    // event handling
    void registerBoardListener(IBoardListener listener);

    void unregisterBoardListener(IBoardListener listener);

    void registerGameListener(IGameListener listener);

    void unregisterGameListener(IGameListener listener);
}
