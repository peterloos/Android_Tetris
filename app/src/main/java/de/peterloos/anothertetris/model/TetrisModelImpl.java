package de.peterloos.anothertetris.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Peter on 08.10.2015.
 */
public class TetrisModelImpl extends TetrisModel implements IBoardListener {

    public static final int IntervalNormal = 750;
    public static final int IntervalAccelerated = 75;

    private static final int MaxTetriminos = 7;

    private enum TetrisState {Normal, Accelerated, AtTop, AtBottom, Idle, GameOver}

    private ITetrisBoard board;  // tetris game area
    private ITetrimino curr;     // current tetrimino
    private TetrisState state;   // current state of model

    private Random random;

    // private timer tools
    private Timer timer;
    private TimerTask task;

    // miscellaneous
    private List<IBoardListener> boardlisteners;
    private List<IGameListener> gamelisteners;
    private int testTetriCounter = 0;     // TODO: Just for testing !!!

    // c'tor
    public TetrisModelImpl() {

        this.board = new TetrisBoard(this.getNumRows(), this.getNumColumns());
        this.board.registerBoardListener(this);

        this.setState(TetrisState.AtTop);

        // setup timer task
        this.timer = new Timer();

        // miscellaneous
        this.random = new Random();
        this.boardlisteners = new ArrayList<>();
        this.gamelisteners = new ArrayList<>();
    }

    // getter/setter
    public TetrisState getState() {
        return this.state;
    }

    public void setState(TetrisState state) {
        this.state = state;
        Log.i("Tetris", String.format("state ==> %s", state.toString()));
    }

    // tetrimino management
    public ITetrimino AttachTetrimino() {
        // choose tetrimino by random
        int which = Math.abs(random.nextInt()) % MaxTetriminos;

        Log.v("PeLo", "which ==> " + which);

        // or do not :-) choose tetrimino by random
        // int which = this.testTetriCounter++;

        if (which % MaxTetriminos == 0)
            this.curr = new Tetrimino_I(this.board);
        else if (which % MaxTetriminos == 1)
            this.curr = new Tetrimino_J(this.board);
        else if (which % MaxTetriminos == 2)
            this.curr = new Tetrimino_L(this.board);
        else if (which % MaxTetriminos == 3)
            this.curr = new Tetrimino_O(this.board);
        else if (which % MaxTetriminos == 4)
            this.curr = new Tetrimino_S(this.board);
        else if (which % MaxTetriminos == 5)
            this.curr = new Tetrimino_T(this.board);
        else if (which % MaxTetriminos == 6)
            this.curr = new Tetrimino_Z(this.board);

        return this.curr;
    }

    public void DetachTetrimino() {
        this.curr = null;
    }

    /*
     * animation commands
     */

    @Override
    public void Start() {
        this.OnGameChanged(GameState.GameStarted);

        this.board.Clear();
        this.setState(TetrisState.AtTop);

        this.task = this.AttachTimerTask();
        this.timer.schedule(this.task, 0, IntervalNormal);
    }

    @Override
    public void Stop() {
        this.task.cancel();

        this.OnGameChanged(GameState.GameOver);
    }

    /*
     * controller requests (internal and external event driven - UI dispatcher queue)
     */
    public void DoAction(TetrisAction action) {
        switch (action) {
            case Left:
                this.DoActionMoveLeft();
                break;
            case Right:
                this.DoActionMoveRight();
                break;
            case Rotate:
                this.DoActionRotate();
                break;
            case BeginAllWayDown:
                this.DoActionBeginAllWayDown();
                break;
        }
    }

    private void TimerMethod() {
        // method usually doesn't run in the same thread as UI !!!
        switch (this.getState()) {
            case AtTop:
                this.DoActionSetToTop();
                break;

            case Normal:
            case Accelerated:
                this.DoActionMoveDown();
                break;

            case AtBottom:
                this.DoActionAtBottom();
                break;

            case GameOver:
                this.DoActionGameOver();
                break;

            default:
                Log.i(Globals.LogTag, "Internal ERROR: Should never be reached");
                break;
        }
    }

    /*
     * actions methods
     */

    private void DoActionSetToTop() {
        synchronized (this) {
            assert (this.getState() == TetrisState.AtTop);

            // create new tetrimino
            this.curr = this.AttachTetrimino();

            if (this.curr.CanSetToTop()) {
                this.curr.SetToTop();
                this.setState(TetrisState.Normal);
            } else {
                this.setState(TetrisState.GameOver);
            }
        }
    }

    private void DoActionAtBottom() {
        synchronized (this) {
            // tetrimino has reached bottom of field, release it
            this.DetachTetrimino();

            // rearrange field, if possible
            while (this.board.IsBottomRowComplete()) {
                this.board.MoveNonEmptyRowsDown();
            }

            // schedule next tetrimino
            this.setState(TetrisState.AtTop);

            //  cancel and reschedule the TimerTask with normal timer period
            // (in case of any preceding 'DoBeginAllWayDown')
            this.task.cancel();
            this.task = this.AttachTimerTask();
            this.timer.schedule(this.task, 0, IntervalNormal);
        }
    }

    private void DoActionGameOver() {
        this.Stop();
    }

    private void DoActionMoveRight() {
        synchronized (this) {
            if (this.getState() != TetrisState.Normal)
                return;

            if (this.curr.CanMoveRight())
                this.curr.MoveRight();
        }
    }

    private void DoActionMoveLeft() {
        synchronized (this) {
            if (this.getState() != TetrisState.Normal)
                return;

            if (this.curr.CanMoveLeft())
                this.curr.MoveLeft();
        }
    }

    private void DoActionRotate() {
        synchronized (this) {
            if (this.getState() != TetrisState.Normal)
                return;

            if (this.curr.CanRotate())
                this.curr.Rotate();
        }
    }

    private void DoActionMoveDown() {
        synchronized (this) {
            assert ((this.getState() == TetrisState.Normal) || (this.getState() == TetrisState.Accelerated));

            if (this.curr.CanMoveDown()) {
                this.curr.MoveDown();
            } else {
                this.setState(TetrisState.AtBottom);
            }
        }
    }

    private void DoActionBeginAllWayDown() {
        synchronized (this) {
            if (this.getState() != TetrisState.Normal)
                return;

            this.setState(TetrisState.Accelerated);   // only 'MoveDown' actions are now allowed
        }

        //  cancel and reschedule the TimerTask with faster timer period
        this.task.cancel();
        this.task = this.AttachTimerTask();
        this.timer.schedule(this.task, 0, IntervalAccelerated);
    }

    // implementation of interface 'IBoardListener'
    public void boardChanged(ViewCellList cells) {
        this.OnBoardChanged(cells);
    }

    // support handling of interface 'IBoardListener'
    public void registerBoardListener(IBoardListener listener) {
        this.boardlisteners.add(listener);
    }

    public void unregisterBoardListener(IBoardListener listener) {
        this.boardlisteners.remove(listener);
    }

    private void OnBoardChanged(ViewCellList cells) {
        for (IBoardListener boardlistener : this.boardlisteners) {
            boardlistener.boardChanged(cells);
        }
    }

    // support handling of interface 'IGameListener'
    @Override
    public void registerGameListener(IGameListener listener) {
        this.gamelisteners.add(listener);
    }

    @Override
    public void unregisterGameListener(IGameListener listener) {
        this.gamelisteners.remove(listener);
    }

    private void OnGameChanged(GameState state) {
        for (IGameListener gamelistener : this.gamelisteners) {
            gamelistener.stateChanged(state);
        }
    }

    // private helper methods
    private TimerTask AttachTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                TetrisModelImpl.this.TimerMethod();
            }
        };
    }
}
