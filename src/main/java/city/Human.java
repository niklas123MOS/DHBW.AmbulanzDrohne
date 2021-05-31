package city;

import drone.Drone;
import drone.Electrode;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class Human {

    String[][] face = new String[25][25];
    String id = "";
    int age;
    boolean hasHeartAttack = false;

    int x;
    int y;

    Smartphone smartphone;

    Drone drone;


    public Human(int coordinateX, int coordinateY) {

        createFace();
        createID();
        MersenneTwisterFast merTwi = new MersenneTwisterFast();
        this.age = merTwi.nextInt(10,100);
        this.x = coordinateX;
        this.y = coordinateY;
        smartphone = new Smartphone();
    }

    public void reanimateHuman (Human human){
        ArrayList<Electrode> electrodes = drone.getElectrodesFromBox();
        human.setHasHeartAttack(false);
        drone.layBackElectrodesInBox(electrodes);
    }

    public void callEmergencyCenter(Human human){

        this.smartphone.callEmergencyCenter(human);

    }

    public String[][] getFace() {
        return face;
    }

    public String getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public void setCoordinateX(int coordinateX) {
        this.x = coordinateX;
    }

    public void setCoordinateY(int coordinateY) {
        this.y = coordinateY;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    private void createID() {
        String pool = "abcdefghijklmnopqrstuvwxyz0123456789";
        int randInt = 0;
        MersenneTwisterFast merTwi = new MersenneTwisterFast();
        for (int i = 0; i < 25 ; i++) {
            randInt=merTwi.nextInt(0,pool.length()-1);
            id += pool.charAt(randInt);

        }


    }

    private void createFace() {
        MersenneTwisterFast merTwi = new MersenneTwisterFast();
        int randInt = 0;
        for (int i = 0; i <25 ; i++) {
            for (int j = 0; j < 25 ; j++) {
                randInt  = merTwi.nextInt(0,3);

                switch (randInt){
                    case 0:
                        face[i][j]=".";
                        break;
                    case 1:
                        face[i][j]="+";
                        break;
                    case 2:
                        face[i][j]="*";
                        break;
                    case 3:
                        face[i][j]="#";
                        break;
                }
            }
        }
    }

    public void setHasHeartAttack(boolean hasHeartAttack) {
        this.hasHeartAttack = hasHeartAttack;
    }
}
