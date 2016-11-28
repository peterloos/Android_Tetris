package de.peterloos.anothertetris.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 18.10.2015.
 */
public class TetrisBoard implements ITetrisBoard {

    private int numRows;
    private int numColumns;
    private TetrisCell[][] board;
    private List<IBoardListener> listeners;

    public TetrisBoard(int rows, int cols) {
        this.numRows = rows;
        this.numColumns = cols;
        this.AllocateBoard(rows, cols);
        this.listeners = new ArrayList<IBoardListener>();
    }

    // getter/setter
    public int getNumRows() {
        return this.numRows;
    }

    public int getNumColumns() {
        return this.numColumns;
    }

    public TetrisCell getCell(int row, int col) {
        return this.board[row][col];
    }

    public void setCell(int row, int col, TetrisCell cell) {
        this.board[row][col] = cell;
    }

    // public interface
    public void Clear() {
        // reset internal state of board
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numColumns; j++) {
                this.board[i][j].setColor(CellColor.LightGray);
                this.board[i][j].setState(CellState.Free);
            }
        }

        // update view
        ViewCellList list = new ViewCellList();
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numColumns; j++) {
                ViewCell cell = new ViewCell(CellColor.LightGray, new CellPoint(j, i));
                list.Add(cell);
            }
        }
        this.OnBoardChanged(list);
    }

    public void PostChanges(ViewCellList list) {
        this.OnBoardChanged(list);
    }

    public boolean IsBottomRowComplete() {
        boolean isComplete = true;
        for (int j = 0; j < this.numColumns; j++) {
            if (this.board[this.numRows - 1][j].getState() != CellState.Used) {
                isComplete = false;
                break;
            }
        }

        return isComplete;
    }

    public void MoveNonEmptyRowsDown() {
        ViewCellList list = new ViewCellList();

        int startRow = this.numRows - 1;
        while (!this.IsRowEmpty(startRow))
            startRow--;

        for (int i = this.numRows - 2; i >= startRow; i--) {
            this.CopySingleRow(list, i);
        }

        this.OnBoardChanged(list);
    }

    // handling of IBoardListener clients
    public void registerBoardListener(IBoardListener listener) {
        this.listeners.add(listener);
    }

    public void unregisterBoardListener(IBoardListener listener) {
        this.listeners.remove(listener);
    }

    private void OnBoardChanged(ViewCellList cells) {
        for (IBoardListener listener : this.listeners) {
            listener.boardChanged(cells);
        }
    }

    // private helper methods
    private void AllocateBoard(int rows, int cols) {
        this.board = new TetrisCell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.board[i][j] = new TetrisCell();
            }
        }
    }

    private void CopySingleRow(ViewCellList list, int row) {
        for (int j = 0; j < this.numColumns; j++) {
            // create cell to update any view
            CellColor color = this.board[row][j].getColor();
            CellPoint point = new CellPoint(j, row + 1);
            ViewCell cell = new ViewCell(color, point);
            list.Add(cell);

            // Finally copy block one row down ...
            // Note: TetrisCell is a 'struct', so '=' works fine --
            // In case of a reference type: Clone needed (deep copy) !!!
            this.board[row + 1][j] = this.board[row][j];
        }
    }

    private boolean IsRowEmpty(int index) {
        boolean isEmpty = true;
        for (int j = 0; j < this.numColumns; j++) {
            if (this.board[index][j].getState() == CellState.Used) {
                isEmpty = false;
                break;
            }
        }

        return isEmpty;
    }

    // private test tools
    private void DumpBoard(String indent) {
        String s = String.format("%s", "Dump of Tetris Board:");
        Log.i(Globals.LogTag, s);

        for (int i = 0; i < this.numRows; i++) {
            s = String.format("%s", indent);
            Log.i(Globals.LogTag, s);

            s = "";
            for (int j = 0; j < this.numColumns; j++) {
                s += String.format("%s\t", this.board[i][j].getState() == CellState.Free ? "O" : "X");
            }
            Log.i(Globals.LogTag, s);
        }
        Log.i(Globals.LogTag, "");
    }
}
