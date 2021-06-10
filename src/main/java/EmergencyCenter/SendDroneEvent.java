package EmergencyCenter;

public class SendDroneEvent {
    private String humanID;
    private int dronePortID;
    private String[][] route;


    public SendDroneEvent(int dronePortID, String[][] route, String humanID, char[][] face) {
        this.humanID = humanID;
        this.route = route;
        this.dronePortID = dronePortID;
    }

    public int getDronePortID() {
        return dronePortID;
    }

    public String getHumanID() {
        return humanID;
    }

    public String[][] getRoute() {
        return route;
    }
}
