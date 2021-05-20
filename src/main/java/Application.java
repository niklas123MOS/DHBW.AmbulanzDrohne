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

        Human human = new Human();

        










    }




}
