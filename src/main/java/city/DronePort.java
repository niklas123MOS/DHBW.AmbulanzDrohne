package city;

import drone.Drone;

public class DronePort {

    Drone drone;
    int x;
    int y;


    public DronePort(Drone drone, int x, int y) {
        this.drone = drone;
        this.x = x;
        this.y = y;

        this.drone.setX(x);
        this.drone.setX(y);
    }

    public Drone getDrone() {
        return drone;
    }

    public void letDroneFly(/*Route, AES verschlüsselt, evtl als String oderso*/){

        //entschlüsselung der route

        drone.flyRoute(/*Route*/);

    }

}
