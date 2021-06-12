import java.util.ArrayList;

public class DStar {
    private static DStar instance = new DStar();

    public Port port;

    private DStar() {
        port = new Port();
    }

    public static DStar getInstance() {
        return instance;
    }


    public static void main(String... args) {
        char[][] city = new char[][]{
                {' ', ' ', ' ', ' ', ' ', ' ', 'S', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', 'S', ' ', ' ', ' '},
                {' ', ' ', ' ', 'R', ' ', ' ', 'S', ' ', ' ', ' '},
                {' ', ' ', ' ', 'R', ' ', ' ', 'S', ' ', ' ', ' '},
                {' ', ' ', ' ', 'R', ' ', ' ', 'S', ' ', ' ', ' '},
                {' ', ' ', ' ', 'R', ' ', ' ', 'S', ' ', ' ', ' '},
                {' ', ' ', ' ', 'R', ' ', ' ', 'S', ' ', ' ', ' '},
                {' ', ' ', ' ', 'R', ' ', ' ', 'S', ' ', ' ', ' '},
                {' ', ' ', ' ', 'R', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', 'R', ' ', ' ', ' ', ' ', ' ', ' '}};

        int[][] route = instance.port.findPath(0, 0, 9, 9, city);

        for (int i = 0; i < route.length; i++) {
            System.out.print(route[i][0] + " ");
            System.out.println(route[i][1]);
        }
    }

    public int[][] innerFindPath(int initialRow, int initialCol, int finalRow, int finalCol, char[][] city) {
        DstarMap map = new DstarMap(city[0].length, city.length);
        map.loadMapCity(city);
        map.print();
        map.setStart(initialRow, initialCol);
        map.setGoal(finalRow, finalCol);

        DstarPathFinder pathFinder = new DstarPathFinder(map);

        ArrayList<DstarNode> path = pathFinder.traverseMap();

        int[][] route = new int[path.size()][2];

        for (int i = 0; i < path.size(); i++) {

            route[i][0] = path.get(i).getRow();
            route[i][1] = path.get(i).getColumn();

        }

        return route;
    }


    public class Port implements IDStar {
        public int[][] findPath(int x1, int y1, int x2, int y2, char[][] map) {
            return innerFindPath(x1, y1, x2, y2, map);
        }
    }

}