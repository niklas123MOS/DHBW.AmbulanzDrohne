import city.DronePort;
import city.EmergencyCallCentre;
import city.Human;
import city.Mosbach;
import drone.Drone;
import drone.Motor.Motor;
import drone.boom.Boom;
import drone.boom.MainBoom;
import drone.boom.SubBoom;

import java.util.ArrayList;

public class Application {



    public static void main (String[] args){

        Mosbach mosbach = new Mosbach();
        Drone drone01 = new Drone.Builder().mainBoom01().mainBoom02().mainBoom03().mainBoom04().setParameters().build();
        drone01.initCentralunit();
        Drone drone02 = new Drone.Builder().mainBoom01().mainBoom02().mainBoom03().mainBoom04().setParameters().build();
        drone02.initCentralunit();
        Drone drone03 = new Drone.Builder().mainBoom01().mainBoom02().mainBoom03().mainBoom04().setParameters().build();
        drone03.initCentralunit();
        Drone drone04 = new Drone.Builder().mainBoom01().mainBoom02().mainBoom03().mainBoom04().setParameters().build();
        drone04.initCentralunit();

        String[][] mosbachCityarea =mosbach.getCityarea();

        EmergencyCallCentre emergencyCallCentre= new EmergencyCallCentre(499, 499, mosbach);
        DronePort dronePort01 = new DronePort(drone01, 249, 249);
        DronePort dronePort02 = new DronePort(drone02, 749, 249);
        DronePort dronePort03 = new DronePort(drone03, 749, 249);
        DronePort dronePort04 = new DronePort(drone04, 749, 749);

        Human human = new Human(1,2);





        










    }




}
