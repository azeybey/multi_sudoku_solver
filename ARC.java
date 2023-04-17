import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class ARC {

    public boolean ac3(ArrayList<Constraint> constraints) {
        LinkedList<Cell[]> arcs = this.makeArcQueue(constraints);

        while (!arcs.isEmpty()) {
            Cell[] arc = arcs.poll();

            if (this.revise(arc)) {
                if (arc[0].getDomainSize() == 0) {
                    return false;
                }

                LinkedList<Cell> neighbors = this.getNeighbors(constraints, arc[0]);
                neighbors.remove(arc[1]);

                for (Cell neighbor : neighbors) {
                    Cell[] newArc = {neighbor, arc[0]};
                    arcs.add(newArc);
                }
            }
        }
        return true;
    }

    private LinkedList<Cell[]> makeArcQueue(ArrayList<Constraint> constraints) {
        LinkedList<Cell[]> arcs = new LinkedList<>();

        for (Constraint constraint : constraints) {
            int n = constraint.getCellsSize();

            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    Cell[] arc1 = new Cell[2];
                    arc1[0] = constraint.getCellAt(i);
                    arc1[1] = constraint.getCellAt(j);
                    arcs.add(arc1);

                    Cell[] arc2 = new Cell[2];

                    arc2[0] = constraint.getCellAt(j);
                    arc2[1] = constraint.getCellAt(i);
                    arcs.add(arc2);
                }
            }
        }
        return arcs;
    }

    public boolean revise(Cell[] arc) {
        boolean revised = false;

        Set<Integer> found = new HashSet<>();

        for (Integer val1 : arc[0].getDomain()) {
            for (Integer val2 : arc[1].getDomain()) {
                if (val1 == val2 && arc[1].getDomain().size() == 1) {
                    found.add(val1);
                    revised = true;
                }
            }
        }
        arc[0].getDomain().removeAll(found);

        return revised;
    }

    public LinkedList<Cell> getNeighbors(ArrayList<Constraint> constraints, Cell cell) {
        LinkedList<Cell> neighbors = new LinkedList<>();

        for (Constraint constraint : constraints) {
            if (!constraint.cellsContains(cell)) {
                continue;
            }

            for (int i = 0; i < constraint.getCellsSize(); i++) {
                Cell curr = constraint.getCellAt(i);
                if (curr.equals(cell)) {
                    continue;
                }
                neighbors.add(curr);
            }
        }
        return neighbors;
    }
}