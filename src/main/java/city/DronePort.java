package city;

import Configuration.Configuration;
import EmergencyCenter.EmergencyCenter;
import EmergencyCenter.Subscriber;
import EmergencyCenter.SendDroneEvent;
import EmergencyCenter.AES;
import EmergencyCenter.PathPlanner;
import com.google.common.eventbus.Subscribe;
import drone.Direction;
import drone.Drone;

import java.util.ArrayList;

public class DronePort extends Subscriber {

    private Drone drone;
    private int dronePortID;

    private int workingAreaAbove;
    private int workingAreaRight;
    private int workingAreaBelow;
    private int workingAreaLeft;

    int[][] route;
    String humanID;


    public DronePort(char type, int row, int col, City city, int dronePortID) {
        super(type, row, col, city);

        this.dronePortID = dronePortID;

        this.drone = new Drone.Builder().mainBoom01().mainBoom02().mainBoom03().mainBoom04().setParameters().build();
        drone.initCentralunit();
        drone.setRow(row);
        drone.setCol(col);

        EmergencyCenter.instance.addSubscriber(this);

        workingAreaAbove = row - 249;
        workingAreaRight = col + 250;
        workingAreaBelow = row + 250;
        workingAreaLeft = col - 249;

    }

    @Subscribe
    public void receive(SendDroneEvent eventSendDrone) {

        if (this.dronePortID == eventSendDrone.getDronePortID()) {

            this.route = decryptRoute(eventSendDrone.getRoute());
            this.humanID = eventSendDrone.getHumanID();
            letDroneFly(route);

        }


    }

    private int[][] decryptRoute(String[][] encryptedRoute) {
        AES aes = new AES();
        String secretKey = Configuration.instance.secretKey;

        String[][] decryptedRoute = new String[encryptedRoute.length][2];

        for (int i = 0; i < encryptedRoute.length; i++) {

            decryptedRoute[i][0] = aes.decrypt(encryptedRoute[i][0], secretKey);
            decryptedRoute[i][1] = aes.decrypt(encryptedRoute[i][1], secretKey);


        }

        int[][] route = new int[decryptedRoute.length][2];

        for (int i = 0; i < decryptedRoute.length; i++) {

            route[i][0] = Integer.parseInt(decryptedRoute[i][0]);
            route[i][1] = Integer.parseInt(decryptedRoute[i][1]);

        }

        return route;
    }

    public Drone getDrone() {
        return drone;
    }

    public void letDroneFly(int[][] route) {

        ArrayList<Direction> directionsRoute = generateRoute(route);

        drone.setRoute(directionsRoute, route[route.length - 1][0], route[route.length - 1][1]);
        drone.flyRoute();

    }

    private ArrayList<Direction> generateRoute(int[][] route) {
        ArrayList<Direction> routeCommands = new ArrayList<>();

        for (int i = 0; i < route.length - 1; i++) {

            if (route[i + 1][0] < route[i][0] && route[i + 1][1] == route[i][1]) {
                routeCommands.add(Direction.TOP);
            } else if (route[i + 1][0] < route[i][0] && route[i + 1][1] < route[i][1]) {
                routeCommands.add(Direction.TOPLEFT);
            } else if (route[i + 1][0] == route[i][0] && route[i + 1][1] < route[i][1]) {
                routeCommands.add(Direction.LEFT);
            } else if (route[i + 1][0] > route[i][0] && route[i + 1][1] < route[i][1]) {
                routeCommands.add(Direction.BOTTOMLEFT);
            } else if (route[i + 1][0] > route[i][0] && route[i + 1][1] == route[i][1]) {
                routeCommands.add(Direction.BOTTOM);
            } else if (route[i + 1][0] > route[i][0] && route[i + 1][1] > route[i][1]) {
                routeCommands.add(Direction.BOTTOMRIGHT);
            } else if (route[i + 1][0] == route[i][0] && route[i + 1][1] > route[i][1]) {
                routeCommands.add(Direction.RIGHT);
            } else if (route[i + 1][0] < route[i][0] && route[i + 1][1] > route[i][1]) {
                routeCommands.add(Direction.TOPRIGHT);
            }

        }
        return routeCommands;
    }

    public int getWorkingAreaAbove() {
        return workingAreaAbove;
    }

    public int getWorkingAreaRight() {
        return workingAreaRight;
    }

    public int getWorkingAreaBelow() {
        return workingAreaBelow;
    }

    public int getWorkingAreaLeft() {
        return workingAreaLeft;
    }

    public int getDronePortID() {
        return dronePortID;
    }

    public int[][] getRoute() {
        return route;
    }

    public String getHumanID() {
        return humanID;
    }
}
