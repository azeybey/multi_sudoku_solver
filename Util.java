import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Util {

    public ArrayList<Cell> readFile(String filepath) {
        ArrayList<Cell> cells;

        try {
            File file = new File(filepath);
            Scanner sc = new Scanner(file);

            cells = parse(sc);

            sc.close();
            return cells;

        } catch(Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return null;
        }
    }

    private ArrayList<Cell>  parse(Scanner sc) {
        ArrayList<Cell> cells = new ArrayList<>();

        while(sc.hasNext()){
            Cell cell = new Cell(sc.nextInt());
            cells.add(cell);
        }

        return cells;
    }
}