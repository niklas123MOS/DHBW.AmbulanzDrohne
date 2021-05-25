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

        String[][] city =mosbach.getCityarea();

        for (int i = 0; i < city.length; i++) {
            for (int j = 0; j <city[0].length ; j++) {

        System.out.print(city[i][j]);
            }
            System.out.println();
        }

        










    }




}
