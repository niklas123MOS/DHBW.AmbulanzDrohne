import java.util.List;

public class Application {
    public static void main(String... args) {

        AStar aStar = AStar.getInstance();
        char[][]city = new char[][]{{' ',' ',' ',' ',' ',' ','S',' ',' ',' '},
                                    {' ',' ',' ',' ',' ',' ','S',' ',' ',' '},
                                    {' ',' ',' ','R',' ',' ','S',' ',' ',' '},
                                    {' ',' ',' ','R',' ',' ','S',' ',' ',' '},
                                    {' ',' ',' ','R',' ',' ','S',' ',' ',' '},
                                    {' ',' ',' ','R',' ',' ','S',' ',' ',' '},
                                    {' ',' ',' ','R',' ',' ','S',' ',' ',' '},
                                    {' ',' ',' ','R',' ',' ','S',' ',' ',' '},
                                    {' ',' ',' ','R',' ',' ',' ',' ',' ',' '},
                                    {' ',' ',' ','R',' ',' ',' ',' ',' ',' '}};

        int[][] path = aStar.port.findPath(2,1,1,9, city);
        for (int i = 0; i < path.length; i++) {

            System.out.print(path[i][0]+" ");
            System.out.println(path[i][1]);
        }

    }
}