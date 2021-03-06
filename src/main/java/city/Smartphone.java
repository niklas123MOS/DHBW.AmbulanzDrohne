package city;

import EmergencyCenter.EmergencyCenter;
import drone.technologies.Camera;

public class Smartphone {

    Camera camera;
    char[][] scannedface;
    String humanID;


    public Smartphone() {
        this.camera = new Camera();
    }

    public void scanFaceAndID(Human human) {
        scannedface = camera.scanFace(human);
        humanID = human.getId();

    }


    public void callEmergencyCenter(int row, int col) {

        System.out.println("Calling emergency center...");

        EmergencyCenter.instance.getBot().acceptEmergencyCall(row, col, scannedface, humanID);

    }

    public char[][] getScannedface() {
        return scannedface;
    }

    public String getHumanID() {
        return humanID;
    }
}
