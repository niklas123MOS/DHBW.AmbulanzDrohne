import city.City;
import city.Citypart;
import city.DronePort;
import city.Human;
import drone.Drone;

public class Application {



    public static void main (String[] args){

        City mosbach = new City();

        Citypart[][] mosbachCityarea = mosbach.getCityarea();

        mosbach.startEmergency();



    }





}
