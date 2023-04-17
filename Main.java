import java.io.File;
import java.util.ArrayList;

public class Main {

    static boolean isSolved = false;
    private static String id;
    private static double time = 0;
    static int expanded = 0;
    static int depth = 0;

    public static void main(String[] args){

        Util util = new Util();
        Solver solver = new Solver();
        File puzzles = new File("data");

        long startTime;
        long stopTime;

        ArrayList<Cell> cells;

        for (File puzzle : puzzles.listFiles()) {

                parseFileName(puzzle.getName());

                cells = util.readFile(puzzle.getPath());
                Sudoku sudoku = new Sudoku(id, cells);

                System.out.println("Puzzle #" + id + "-------------");
                writeCells(cells);

                startTime = System.nanoTime();
                solver.solve(sudoku, false, false);
                stopTime  = System.nanoTime();

                time = (stopTime - startTime) / 10000000.0;

                System.out.println("For Puzzle #" + id + " Depth First Search - Expanded:" + expanded + " Depth:" + depth + " Time(ms):" + time );

                expanded = 0;
                depth = 0;

                cells = util.readFile(puzzle.getPath());
                sudoku = new Sudoku(id, cells);

                startTime = System.nanoTime();
                solver.solve(sudoku, true, false);
                stopTime  = System.nanoTime();

                time = (stopTime - startTime) / 10000000.0;

                System.out.println("For Puzzle #" + id + " AC-3 PreProcessing - Expanded : " + expanded + " Depth :" + depth + " Time(ms) : " + time );

                expanded = 0;
                depth = 0;

                cells = util.readFile(puzzle.getPath());
                sudoku = new Sudoku(id, cells);

                startTime = System.nanoTime();
                solver.solve(sudoku, false, true);
                stopTime  = System.nanoTime();

                time = (stopTime - startTime) / 1000000.0;

                System.out.println("For Puzzle #" + id + " AC-3 with Forward Checking - Expanded : " + expanded + " Depth :" + depth + " Time(ms) : " + time + "\n");

                expanded = 0;
                depth = 0;

                if (!isSolved) System.out.println("There is no solution for Puzzle #" + id + "\n\n\n");
                else {
                    System.out.println("Solution is : ");
                    writeCells(cells);
                }

                isSolved = false;
        }

    }

    private static void parseFileName(String name) {
        id = name.substring(0, name.length() - 4);
    }

    private static void writeCells(ArrayList<Cell> cells) {
        for (int i = 0; i < 81; i++) {
            System.out.print(cells.get(i).getVal() + " ");

            if ((i + 1) % 3 == 0) System.out.print(" ");

            if (i % 9 == 8) System.out.print("\n");

            if ((i + 1) % 27 == 0 && i != 0) System.out.print("\n");
        }
    }


}