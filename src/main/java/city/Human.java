package city;

import drone.Drone;
import drone.Electrode;
import drone.ElectrodesObserver;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class Human extends Citypart{

    char[][] face ;
    String id = "";
    int age;
    boolean hasHeartAttack;
    Smartphone smartphone;

    Drone drone;
    Human partner;


    public Human(char type, int row, int col, City city) {
        super(type, row, col, city);
        createFace();
        createID();
        MersenneTwisterFast merTwi = new MersenneTwisterFast();
        this.age = merTwi.nextInt(10,100);
        smartphone = new Smartphone();
        this.hasHeartAttack = false;
    }

    public void humanGetsHeartAttack(){
        System.out.println("Human " + this + " has heart Attack");

        setHasHeartAttack(true);
    }

    public void reanimatePartner(){
        System.out.println("Human " + this + " reanimates Partner " + partner );



        System.out.println("Take electrodes from " + drone );
        ArrayList<Electrode> electrodes = drone.getElectrodesFromBox();
        partner.setHasHeartAttack(false);

        System.out.println("Reanimation successfull. Lay Back electrodes");

        ElectrodesObserver electrodesObserver = new ElectrodesObserver();
        electrodesObserver.addListener(drone);

        drone.layBackElectrodesInBox(electrodes);
        electrodesObserver.layBackElectrodes();
    }


    public char[][] getFace() {
        return face;
    }

    public String getId() {
        return id;
    }

    public int getAge() {
        return age;
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
        this.face = new char[25][25];
        MersenneTwisterFast merTwi = new MersenneTwisterFast();
        int randInt = 0;
        for (int i = 0; i <25 ; i++) {
            for (int j = 0; j < 25 ; j++) {
                randInt  = merTwi.nextInt(0,3);

                switch (randInt){
                    case 0:
                        face[i][j]='.';
                        break;
                    case 1:
                        face[i][j]='+';
                        break;
                    case 2:
                        face[i][j]='*';
                        break;
                    case 3:
                        face[i][j]='#';
                        break;
                }
            }
        }
    }

    public void setHasHeartAttack(boolean hasHeartAttack) {
        this.hasHeartAttack = hasHeartAttack;
    }

    public void setPartner(Human partner) {
        this.partner = partner;
    }

    public void callEmergencyCenter () {
        smartphone.scanFaceAndID(partner);
        smartphone.callEmergencyCenter(getRow(), getCol()-1); //to land drone next to the human

    }

}
