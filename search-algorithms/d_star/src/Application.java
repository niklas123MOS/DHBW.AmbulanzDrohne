import java.util.ArrayList;

public class Application {
    public static void main(String... args) {
        String cityString = "";
        cityString += "...H...\n";
        cityString += "RRR  SS\n";
        cityString += "...H...\n";
        cityString += "ESSHSSE\n";
        cityString += "...H...\n";
        cityString += "RSRHHSR\n";
        cityString += "...H...\n";

        char[][] city = convertStringToCity(cityString);
        System.out.println("city size:" + city[0].length + "x" + city.length);
        DstarMap map = new DstarMap(city[0].length, city.length);
        map.loadMapCity(city);
        map.print();
        map.setStart(0, 0);
        map.setGoal(6, 6);

        DstarPathFinder pathFinder = new DstarPathFinder(map);

        ArrayList<DstarNode> path = pathFinder.traverseMap();
        System.out.println("path length:" + path.size());

        for (DstarNode n : path) {
            System.out.println("x:" + n.getColumn() + " y:" + n.getRow());
        }

        System.out.println();
        String s;

        for (int y = 0; y < city.length; y++) {
            for (int x = 0; x < city[0].length; x++) {
                s = ".";
                for (DstarNode n : path) {
                    if (n.getColumn() == x && n.getRow() == y) {
                        s = "+";
                    }
                }
                System.out.print(s);
            }
            System.out.println();
        }
        System.out.println();
    }

    static char[][] convertStringToCity(String city) {
        String[] citySplit = city.split("\n");
        int rows = citySplit.length;
        int columns = citySplit[0].length();
        char[][] retCity = new char[columns][rows];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                retCity[col][row] = citySplit[row].charAt(col);
            }
        }

        return retCity;
    }
}