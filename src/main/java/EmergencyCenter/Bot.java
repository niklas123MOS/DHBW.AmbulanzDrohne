package EmergencyCenter;

public class Bot {

    private EmergencyCenter center;
    private int emergencyRow = -1;
    private int emergencyCol = -1;
    private char[][] emergencyFace;

    public Bot(EmergencyCenter center) {

        this.center = center;
    }

    public int getEmergencyRow() {
        return emergencyRow;
    }

    public int getEmergencyCol() {
        return emergencyCol;
    }

    public char[][] getEmergencyFace() {
        return emergencyFace;
    }

    public void acceptEmergencyCall(int row, int col, char[][] face, String humanID) {

        System.out.println("Emergency Center received new Emergency");
        System.out.println("    on position: row = " + row);
        System.out.println("                 col = " + col);

        emergencyRow = row;
        emergencyCol = col;
        emergencyFace = face;
        center.callDronePort(row, col, face, humanID);
    }


}
