import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class DstarMap {
    private final DstarNode[][] map;
    private int rows = 0;
    private int columns = 0;
    private DstarNode start;
    private DstarNode goal;
    private DstarNode robotLocation;

    public DstarMap(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        map = new DstarNode[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public DstarNode getNode(int row, int column) {
        return map[row][column];
    }

    public DstarNode getStart() {
        return start;
    }

    public void setStart(int x, int y) {
        this.start = getNode(x, y);
        if (!this.start.getState().equals("O")) {
            throw new IllegalArgumentException("can't set Start on a blocked tile!");
        }
        robotLocation = start;
    }

    public DstarNode getGoal() {
        return goal;
    }

    public void setGoal(int x, int y) {
        this.goal = getNode(x, y);
        if (!goal.getState().equals("O")) {
            throw new IllegalArgumentException("can't set Goal on a blocked tile!");
        }
        this.goal.setK(0);
        this.goal.setH(0);
    }

    public DstarNode getRobotLocation() {
        return robotLocation;
    }

    public void setRobotLocation(DstarNode robotLocation) {
        this.robotLocation = robotLocation;
    }

    public ArrayList<DstarNode> getNeighbors(int row, int col) {
        int top = row - 1;
        int bottom = row + 1;

        ArrayList<DstarNode> neighborList = new ArrayList<>();

        int left = col - 1;
        int right = col + 1;

        if (!neighborOutOfBounds(top, col)) {
            neighborList.add(getNode(top, col));
        }

        if (!neighborOutOfBounds(bottom, col)) {
            neighborList.add(getNode(bottom, col));
        }

        if (!neighborOutOfBounds(row, right)) {
            neighborList.add(getNode(row, right));
        }

        if (!neighborOutOfBounds(row, left)) {
            neighborList.add(getNode(row, left));
        }

        return neighborList;
    }

    private boolean neighborOutOfBounds(int row, int column) {
        return row < 0 || row >= getRows() || column < 0 || column >= getColumns();
    }

    public void loadMap(String filename) {
        int row = 0;
        int column = 0;

        File file = new File(filename);

        if (file.exists()) {
            if (file.isFile() && file.canRead()) {
                try {
                    FileInputStream in = new FileInputStream(filename);
                    int read;
                    while ((read = in.read()) != -1) {
                        char c = (char) read;
                        if (c == 'O' || c == 'B' || c == 'U' || c == 'S' || c == 'G') {
                            DstarNode node = new DstarNode(row, column);
                            node.setTag("NEW");

                            char state = c;
                            if (c == 'S') {
                                state = 'O';
                                start = node;
                            } else if (c == 'G') {
                                state = 'O';
                                goal = node;
                            }

                            node.setState(String.valueOf(state));
                            map[row][column] = node;
                            column++;
                        } else {
                            row++;
                            column = 0;
                        }
                    }
                    in.close();
                    robotLocation = start;
                } catch (Exception e) {

                }
            } else {
                System.err.println(file.getName() + " cannot be read");
                System.exit(0);
            }
        } else {
            System.err.println("File Not Found...");
            System.exit(0);
        }


    }

    public void print() {
        int cell_column_count = 15;
        int row_column_count = cell_column_count * this.columns;

        char space = ' ';
        char row_seperator_char = '-';

        String row_seperator = repeatChar(row_seperator_char, row_column_count + 1);
        String cr1; // cell row 1
        String cr2; // cell row 2
        String cr3; // cell row 3
        String cr4; // cell row 4
        String cr5; // cell row 5
        String cr6; // cell row 6

        // loop through rows
        for (int i = 0; i < map.length; i++) {
            // new row, reinitialize cell row strings
            cr1 = "";
            cr2 = "";
            cr3 = "";
            cr4 = "";
            cr5 = "";
            cr6 = "";

            // loop through cells and concatenate values to cell row strings
            for (int j = 0; j < map[i].length; j++) {
                DstarNode node = getNode(i, j);

                String startOrGoal = " ";
                if (node == start) {
                    startOrGoal = "S";
                } else if (node == goal) {
                    startOrGoal = "G";
                }

                String robot = "   ";
                if (node == robotLocation) {
                    robot = "(*)";
                }

                String tag = node.getTag();
                String state = node.getState();
                String label = node.getLabel();
                String hval = "h:" + String.format("% ,.1f", node.getH());
                String kval = "k:" + String.format("% ,.1f", node.getK());

                String bp = "";
                if (node.getBackPointer() == null) {
                    bp = "b: " + repeatChar(space, 11);
                } else {
                    bp = "b: " + node.getBackPointer().getLabel() + repeatChar(space, 9);
                }

                cr1 += "|" + tag + repeatChar(space, 14 - tag.length() - state.length()) + state;
                cr2 += "|" + label + repeatChar(space, 14 - label.length());
                cr3 += "|" + hval + repeatChar(space, 14 - hval.length());
                cr4 += "|" + kval + repeatChar(space, 14 - kval.length());
                cr5 += "|" + bp;

                cr6 += "|" + repeatChar(space, 5) + robot + repeatChar(space, 5) + startOrGoal;

            }
            // close the row lines with |
            cr1 += "|";
            cr2 += "|";
            cr3 += "|";
            cr4 += "|";
            cr5 += "|";
            cr6 += "|";

            System.out.println(row_seperator);
            System.out.println(cr1);
            System.out.println(cr2);
            System.out.println(cr3);
            System.out.println(cr4);
            System.out.println(cr5);
            System.out.println(cr6);
        }

        System.out.println(row_seperator);
    }

    private String repeatChar(char c, int num) {
        String repeated = "";
        if (num > 0) {
            repeated = new String(new char[num]).replace('\0', c);
        }

        return repeated;
    }


    /**
     * DHBW-INF18A/B
     * load map from city char-array
     *
     * @param city city[col][row] city chars
     *             possible chars:
     *             ' ' or '.' empty
     *             'D' drone port
     *             'E' emergency call centre
     *             'H' human
     *             'R' restricted  = obstacle
     *             'S' skyscraper  = obstacle
     */
    public void loadMapCity(char[][] city) {
        Set<Character> obstacles = new HashSet<>(Arrays.asList('R', 'S'));

        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                char c = city[column][row];
                DstarNode node = new DstarNode(row, column);
                node.setTag("NEW");

                char state = obstacles.contains(c) ? 'B' : 'O';  //O=Open B=Block (U=unexpected)

                node.setState(String.valueOf(state));
                map[row][column] = node;
            }
        }
    }
}