package EmergencyCenter;

import city.DronePort;
import com.google.common.eventbus.EventBus;

import java.util.ArrayList;

public enum EmergencyCenter {

    instance;
    private EventBus eventBus = new EventBus();
    private Bot bot=new Bot(this);
    private ArrayList<DronePort> dronePorts= new ArrayList<>();


    public void addSubscriber(Subscriber subscriber) {
        eventBus.register(subscriber);
        dronePorts.add((DronePort) subscriber);

    }

    public void callDronePort(int row, int col, char[][] face, String humanID) {

        int dronePortID = 0;
        int currentAbove;
        int currentRight;
        int currentBelow;
        int currentLeft;
        DronePort dronePort = null;

        for (DronePort currentDronePort: dronePorts) {
            currentAbove = currentDronePort.getWorkingAreaAbove();
            currentRight = currentDronePort.getWorkingAreaRight();
            currentBelow = currentDronePort.getWorkingAreaBelow();
            currentLeft = currentDronePort.getWorkingAreaLeft();


            if( currentAbove < row &&
                row < currentBelow &&
                currentLeft < col &&
                col < currentRight) {
                dronePortID = currentDronePort.getDronePortID();
                dronePort = currentDronePort;
            }

        }


        PathPlanner pathPlanner = new PathPlanner();
        int[][] route = pathPlanner.executeFindPath(dronePort.getRow(), dronePort.getCol(), row, col, dronePort.getCity().getCityareaChar());

        for (int i = 0; i <route.length ; i++) {

            System.out.print(route[i][0] + " ");
            System.out.println(route[i][1]);

        }


        String[][] encryptedRoute = encryptRoute(route);

        eventBus.post(new SendDroneEvent(dronePortID, encryptedRoute, humanID, face));
    }


    private String[][] encryptRoute (int[][] route){
        AES aes = new AES();
        String secretKey="dhbwmosbach2021";

        String[][] encryptedRoute = new String[route.length][2];

        for (int i = 0; i < route.length ; i++) {

            encryptedRoute[i][0]=aes.encrypt(route[i][0], secretKey);
            encryptedRoute[i][1]=aes.encrypt(route[i][1], secretKey);

        }

        return encryptedRoute;
    }


    public Bot getBot() {
        return bot;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

}
