import java.util.ArrayList;

public class Sudoku {

    private String id;
    private ArrayList<Cell> cells;
    private ArrayList<Constraint> constraints;

    public Sudoku(String id, ArrayList<Cell> cells) {
        this.id = id;
        this.cells = cells;
        this.constraints = this.createConstraints();
    }

    public String getId() {
        return this.id;
    }

    public ArrayList<Cell> getCells() {
        return this.cells;
    }

    public ArrayList<Constraint> getConstraints() {
        return this.constraints;
    }

    private ArrayList<Constraint> createConstraints() {
        ArrayList<Constraint> constraints = new ArrayList<>();

        this.addRowConstraints(constraints);

        this.addColConstraints(constraints);

        this.addSqrConstraints(constraints);

        return constraints;
    }

    private void addRowConstraints(ArrayList<Constraint> constraints) {

        // Iterate through rows.
        for (int row = 0; row < 81; row += 9) {
            Constraint constraint = new Constraint();

            // Add all cells in the row.
            for (int col = 0; col < 9; col++) {
                int index = row + col;
                constraint.cellsAdd(this.cells.get(index));
            }
            constraints.add(constraint);
        }
    }

    private void addColConstraints(ArrayList<Constraint> constraints) {

        // Iterate through columns.
        for (int row = 0; row < 9; row++) {
            Constraint constraint = new Constraint();

            // Add all cells in the col.
            for (int col = 0; col < 81; col += 9) {
                int index = row + col;
                constraint.cellsAdd(this.cells.get(index));
            }
            constraints.add(constraint);
        }
    }

    private void addSqrConstraints(ArrayList<Constraint> constraints) {

        // Iterate through square rows.
        for (int sqrR = 0; sqrR < 81; sqrR += 27) {
            // Iterate through square cols.
            for (int sqrC = 0; sqrC < 9; sqrC += 3) {
                Constraint constraint = new Constraint();

                // Iterate through each row in the seuqre.
                for (int r = 0; r < 27; r += 9) {
                    // Add all cells in the row of a square.
                    for (int c = 0; c < 3; c++) {
                        int index = sqrR + sqrC + r + c;
                        constraint.cellsAdd(this.cells.get(index));
                    }
                }
                constraints.add(constraint);
            }
        }
    }
}