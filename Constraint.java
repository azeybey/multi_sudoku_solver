import java.util.ArrayList;

public class Constraint {

    private ArrayList<Cell> cells;

    public Constraint() {
        this.cells = new ArrayList<>();
    }

    public void cellsAdd(Cell cell) {
        cells.add(cell);
    }

    public Cell getCellAt(int i) {
        return this.cells.get(i);
    }

    public int getCellValAt(int i) {
        return this.cells.get(i).getVal();
    }

    public int getCellsSize() {
        return this.cells.size();
    }

    public boolean cellsContains(Cell cell) {
        return this.cells.contains(cell);
    }
}