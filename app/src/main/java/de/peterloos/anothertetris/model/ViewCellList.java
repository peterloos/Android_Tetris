package de.peterloos.anothertetris.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 08.10.2015.
 */
public class ViewCellList {

    private List<ViewCell> cells;

    public ViewCellList() {
        this.cells = new ArrayList<ViewCell>();
    }

    public int getLength() {
        return this.cells.size();
    }

    public ViewCell getAt(int index) {
        return this.cells.get(index);
    }

    public void Add(ViewCell cell) {

        // is location of this cell already present
        for (int i = 0; i < this.cells.size(); i++) {

            ViewCell tmp = this.cells.get(i);
            if (tmp.getPoint().equals(cell.getPoint())) {

                // replace this point with new cell (old one is obsolete)
                this.cells.set(i, cell);
                return;
            }
        }

        // cell not found, just add it
        this.cells.add(cell);
    }
}
