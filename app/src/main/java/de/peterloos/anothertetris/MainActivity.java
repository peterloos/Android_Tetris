package de.peterloos.anothertetris;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import de.peterloos.anothertetris.model.CellColor;
import de.peterloos.anothertetris.model.GameState;
import de.peterloos.anothertetris.model.IBoardListener;
import de.peterloos.anothertetris.model.IGameListener;
import de.peterloos.anothertetris.model.ITetris;
import de.peterloos.anothertetris.model.Globals;
import de.peterloos.anothertetris.model.TetrisModelImpl;
import de.peterloos.anothertetris.model.ViewCell;
import de.peterloos.anothertetris.model.ViewCellList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IBoardListener, IGameListener {

    // layout constants
    public static int MarginLeft = 5;
    public static int MarginRight = 5;
    public static int MarginTop = 5;
    public static int MarginBottom = 5;
    public static int MarginInside = 2;

    private ITetris tetrisModel;

    private TableLayout tableLayout;
    private Button buttonStart;
    private Button buttonStop;
    private Button buttonLeft;
    private Button buttonRight;
    private Button buttonDown;
    private Button buttonRotate;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup model
        this.tetrisModel = new TetrisModelImpl();
        this.tetrisModel.registerBoardListener(this);
        this.tetrisModel.registerGameListener(this);

        // retrieve for all child views with the given id
        this.tableLayout = (TableLayout) findViewById(R.id.tablelayout_board);
        this.buttonStart = (Button) this.findViewById(R.id.button_start);
        this.buttonStop = (Button) this.findViewById(R.id.button_stop);
        this.buttonLeft = (Button) this.findViewById(R.id.button_left);
        this.buttonRight = (Button) this.findViewById(R.id.button_right);
        this.buttonDown = (Button) this.findViewById(R.id.button_down);
        this.buttonRotate = (Button) this.findViewById(R.id.button_rotate);

        // setup event handlers
        this.buttonStart.setOnClickListener(this);
        this.buttonStop.setOnClickListener(this);
        this.buttonLeft.setOnClickListener(this);
        this.buttonRight.setOnClickListener(this);
        this.buttonDown.setOnClickListener(this);
        this.buttonRotate.setOnClickListener(this);

        // need a ViewTreeObserver on the View to wait for the first layout phase
        ViewTreeObserver viewTreeObserver = this.tableLayout.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new TableLayoutHelper());
        }

        // need a dialog box
        this.builder = new AlertDialog.Builder(this);
        this.builder.setTitle("Another Tetris");
        this.builder.setMessage("Do you want to play a new Game?");
        this.builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // restart a new game ....
                MainActivity.this.tetrisModel.Start();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // close dialog and just go back to the previous activity
                dialog.dismiss();
                MainActivity.this.finish();
            }
        });
    }

    @Override
    public void boardChanged(final ViewCellList cells) {

        // NOTE: NO runOnUIThread, ONLY post !!!
        this.tableLayout.post(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.tetrisModelBoardChanged(cells);
            }
        });
    }

    @Override
    public void stateChanged(GameState state) {
        if (state == GameState.GameStarted) {
            Log.i(Globals.LogTag, "started new game ...");
        } else if (state == GameState.GameOver) {
            Log.i(Globals.LogTag, "exit Another Tetris");
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {

        if (v == this.buttonStart) {
            this.tetrisModel.Start();
        } else if (v == this.buttonStop) {
            this.tetrisModel.Stop();
        } else if (v == this.buttonLeft) {
            this.tetrisModel.DoAction(ITetris.TetrisAction.Left);
        } else if (v == this.buttonRight) {
            this.tetrisModel.DoAction(ITetris.TetrisAction.Right);
        } else if (v == this.buttonDown) {
            this.tetrisModel.DoAction(ITetris.TetrisAction.BeginAllWayDown);
        } else if (v == this.buttonRotate) {
            this.tetrisModel.DoAction(ITetris.TetrisAction.Rotate);
        }
    }

    // private helper methods
    private void tetrisModelBoardChanged(ViewCellList list) {

        for (int i = 0; i < list.getLength(); i++) {

            ViewCell cell = list.getAt(i);
            int row = cell.getPoint().getY();
            int column = cell.getPoint().getX();

            LinearLayout tableRow = (LinearLayout) this.tableLayout.getChildAt(row);
            TextView textView = (TextView) tableRow.getChildAt(column);

            int colorInt =
                    (cell.getColor() == CellColor.LightGray) ? Color.LTGRAY :
                            (cell.getColor() == CellColor.Cyan) ? Color.CYAN :
                                    (cell.getColor() == CellColor.Blue) ? Color.BLUE :
                                            (cell.getColor() == CellColor.Ocker) ? Color.BLACK :
                                                    (cell.getColor() == CellColor.Yellow) ? Color.YELLOW :
                                                            (cell.getColor() == CellColor.Green) ? Color.GREEN :
                                                                    (cell.getColor() == CellColor.Magenta) ? Color.MAGENTA :
                                                                            Color.RED;

            textView.setBackgroundColor(colorInt);
        }
    }

    private void setupTableLayout(TableLayout tableLayout, double totalHeight) {

        // calculate height of single cell
        int allMargins = MarginTop + MarginBottom + (this.tetrisModel.getNumRows() - 1) * 2 * MarginInside;
        int cellHeight = (int) ((totalHeight - allMargins) / (double) this.tetrisModel.getNumRows());
        Log.i(Globals.LogTag, "Calculated single cell height: " + cellHeight);

        for (int row = 0; row < this.tetrisModel.getNumRows(); row++) {
            LinearLayout tableRow = new LinearLayout(MainActivity.this);
            tableRow.setOrientation(LinearLayout.HORIZONTAL);

            for (int column = 0; column < this.tetrisModel.getNumColumns(); column++) {

                TextView view = new TextView(MainActivity.this);
                view.setHeight((int) cellHeight);
                view.setBackgroundColor(Color.LTGRAY);

                int leftMargin = MarginInside;
                int rightMargin = MarginInside;
                int topMargin = MarginInside;
                int bottomMargin = MarginInside;

                if (row == 0)
                    topMargin = MarginTop;
                else if (row == this.tetrisModel.getNumRows() - 1)
                    bottomMargin = MarginBottom;

                if (column == 0)
                    leftMargin = MarginLeft;
                else if (column == this.tetrisModel.getNumColumns() - 1)
                    rightMargin = MarginRight;

                LinearLayout.LayoutParams itemParams =
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT, 1f);

                itemParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
                view.setLayoutParams(itemParams);

                tableRow.addView(view);
            }

            tableLayout.addView(tableRow, row);
        }
    }

    private class TableLayoutHelper implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            MainActivity.this.tableLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            double totalHeight = MainActivity.this.tableLayout.getHeight();
            Log.i(Globals.LogTag, "Total height: " + totalHeight);
            MainActivity.this.setupTableLayout(MainActivity.this.tableLayout, totalHeight);
        }
    }
}
