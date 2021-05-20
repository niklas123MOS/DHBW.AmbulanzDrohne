package city;

import random.MersenneTwisterFast;

public class Human {

    String[][] face = new String[25][25];
    String id = "";
    int age;

    int coordinateX;
    int coordinateY;


    public Human() {

        createFace();
        createID();
        MersenneTwisterFast merTwi = new MersenneTwisterFast();
        this.age = merTwi.nextInt(10,100);


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
        this.coordinateX = coordinateX;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
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
}
