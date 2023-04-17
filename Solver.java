import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Solver {
    private ARC ARC = new ARC();

    public ArrayList<Cell> solve(Sudoku sudoku, boolean pre, boolean forward) {
        // If preprocessing requested.
        if (pre) {
            // If forward checking didn't find insonsistency.
            if (ARC.ac3(sudoku.getConstraints())) {
                // If puzzle is complete.
                if (this.isComplete(sudoku.getCells()))
                    return sudoku.getCells();
            } else {
                return null;
            }
        }

        return backtrackSearch(sudoku, forward);
    }

    private ArrayList<Cell> backtrackSearch(Sudoku sudoku, boolean forward) {
        return backtrack(sudoku, sudoku.getCells(), forward);
    }

    private ArrayList<Cell> backtrack(Sudoku sudoku, ArrayList<Cell> cells, boolean forward) {
        Main.depth++;

        if (this.isComplete(cells))
            return cells;

        Cell var = this.selectUnassignedVar(cells, false);

        // For every value in the cell's domain.
        for(Integer val : this.orderDomainVals(sudoku, var, cells)) {
            Main.expanded++;

            // Check if assignemt will be consistent.
            if (this.isConsistent(sudoku, var, val)) {
                int ogVal = var.getVal();
                Set<Integer> ogDom = new HashSet<Integer>();
                ogDom.addAll(var.getDomain());

                var.setVal(val);
                var.domainCollapse(val);

                // Skip this value if forward check finds an issue.
                if (forward && !this.forwardCheck(sudoku, var)) {
                    var.setVal(ogVal);
                    var.setDomain(ogDom);
                    continue;
                }

                ArrayList<Cell> result = backtrack(sudoku, cells, forward);

                // If backtrack was successful.
                if (result != null) {
                    return cells;
                } else {
                    var.setVal(ogVal);
                    var.setDomain(ogDom);
                }
            }
        }
        return null;
    }

    private boolean isComplete(ArrayList<Cell> cells) {
        // for each cell in the assignment.
        for (Cell cell : cells) {
            // Check if cell is empty.
            if (cell.getVal() == 0) {
                return false;
            }
        }

        Main.isSolved = true;
        return true;
    }

    private Cell selectUnassignedVar(ArrayList<Cell> cells, boolean mrv) {
        // For each cell in the assignment.
        for (Cell cell : cells) {
            // Check if a cell is empty.
            if (cell.getVal() == 0) {
                return cell;
            }
        }
        System.out.println("No unassigned variables.");
        return null;
    }

    private SortedSet<Integer> orderDomainVals(Sudoku sudoku, Cell var, ArrayList<Cell> cells) {
        SortedSet<Integer> ordDom = new TreeSet<Integer>();

        // For each value in the domain.
        for (Integer val : var.getDomain()) {
            ordDom.add(val);
        }
        return ordDom;
    }

    private boolean isConsistent(Sudoku sudoku, Cell var, int val) {

        // For every constraint in the problem.
        for (Constraint constraint : sudoku.getConstraints()) {
            // Skip constraints that don't contain the cell.
            if (!constraint.cellsContains(var)) {
                continue;
            }

            int n = constraint.getCellsSize();

            // For every cell in the constraint.
            for (int i = 0; i < n; i++) {
                // Check if it already contains the assigned value.
                if (val == constraint.getCellValAt(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean forwardCheck(Sudoku sudoku, Cell var) {
        ArrayList<Constraint> constraints = new ArrayList<Constraint>();
        HashMap<Cell, Set<Integer>> ogDomMap = new HashMap<Cell, Set<Integer>>();
        boolean result = false;

        // For each constraint in the problem.
        for (Constraint constraint : sudoku.getConstraints()) {
            // Skip constraints that don't contain the cell.
            if (!constraint.cellsContains(var)) {
                continue;
            }
            constraints.add(constraint);
        }

        // Save domains for each cell in the problem.
        for (Cell cell : sudoku.getCells()) {
            Set<Integer> ogDom = new HashSet<Integer>();
            ogDom.addAll(cell.getDomain());
            ogDomMap.put(cell, ogDom);
        }

        result = ARC.ac3(constraints);

        // Restore domains for each cell.
        for (Cell cell : ogDomMap.keySet()) {
            cell.setDomain(ogDomMap.get(cell));
        }
        return result;
    }
}