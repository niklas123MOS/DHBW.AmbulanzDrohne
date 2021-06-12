package city;

import random.MersenneTwisterFast;

import java.util.ArrayList;

public class City {

    Human humanWithHeartAttack;
    Human humanWithNoHeartAttack;
    private int r = 32500;
    private int s = 2500;
    private int h = 1000;
    private int emergencyRow;
    private int emergencyCol;
    private Citypart[][] cityarea = new Citypart[1000][1000];
    private ArrayList<DronePort> dronePorts = new ArrayList<>();

    private ArrayList<Human[]> humanPairs = new ArrayList<>();

    public City() {

        createCity();

    }

    public void startEmergency() {
        MersenneTwisterFast merTwi = new MersenneTwisterFast();
        int pairNumber = merTwi.nextInt(0, humanPairs.size());

        int humanWithHeartAttackID = merTwi.nextInt(0, 1);
        int humanWithNoHeartAttackID = humanWithHeartAttackID == 0 ? 1 : 0;

        //Select Random Human Pair and random human with Heart Attack
        humanWithHeartAttack = humanPairs.get(pairNumber)[humanWithHeartAttackID];
        humanWithNoHeartAttack = humanPairs.get(pairNumber)[humanWithNoHeartAttackID];

        humanWithHeartAttack.humanGetsHeartAttack();

        //Position of caller
        emergencyRow = humanWithNoHeartAttack.getRow();
        emergencyCol = humanWithNoHeartAttack.getCol();

        humanWithNoHeartAttack.callEmergencyCenter();
        humanWithNoHeartAttack.setDrone(dronePorts.get(EmergencyParameters.instance.dronePortID).getDrone());

        humanWithNoHeartAttack.reanimatePartner();

    }

    private void createCity() {
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                this.cityarea[i][j] = new Citypart(' ', i, j, this);
            }
        }


        dronePorts.add(new DronePort('D', 249, 249, this, 0));
        dronePorts.add(new DronePort('D', 249, 749, this, 1));
        dronePorts.add(new DronePort('D', 749, 249, this, 2));
        dronePorts.add(new DronePort('D', 749, 749, this, 3));

        this.cityarea[249][249] = dronePorts.get(0);
        this.cityarea[749][249] = dronePorts.get(1);
        this.cityarea[249][749] = dronePorts.get(2);
        this.cityarea[749][749] = dronePorts.get(3);

        this.cityarea[499][499] = new Citypart('E', 499, 499, this);

        MersenneTwisterFast merTwi = new MersenneTwisterFast();
        int row;
        int col;


        for (int i = 0; i < r; ) {
            row = merTwi.nextInt(0, 999);
            col = merTwi.nextInt(0, 999);
            if (this.cityarea[row][col].getType() == ' ') {
                this.cityarea[row][col] = new Citypart('R');
                i++;

            }

        }


        for (int i = 0; i < s; ) {
            row = merTwi.nextInt(0, 999);
            col = merTwi.nextInt(0, 999);

            if (this.cityarea[row][col].getType() == ' ') {
                this.cityarea[row][col] = new Citypart('S');
                i++;
            }

        }


        for (int i = 0; i < h; i++) {
            row = merTwi.nextInt(1, 997);
            col = merTwi.nextInt(1, 998);

            if (this.cityarea[row][col].getType() == ' ' &&
                    this.cityarea[row - 1][col + 1].getType() == (' ') &&
                    this.cityarea[row - 1][col].getType() == (' ') &&
                    this.cityarea[row - 1][col - 1].getType() == (' ') &&
                    this.cityarea[row][col + 1].getType() == (' ') &&
                    this.cityarea[row][col - 1].getType() == (' ') &&
                    this.cityarea[row + 1][col + 1].getType() == (' ') &&
                    this.cityarea[row + 1][col].getType() == (' ') &&
                    this.cityarea[row + 1][col - 1].getType() == (' ') &&
                    this.cityarea[row + 2][col + 1].getType() == (' ') &&
                    this.cityarea[row + 2][col].getType() == (' ') &&
                    this.cityarea[row + 2][col - 1].getType() == (' ')
            ) {
                Human human1 = new Human('H', row, col, this);
                Human human2 = new Human('H', row + 1, col, this);
                human1.setPartner(human2);
                human2.setPartner(human1);
                this.cityarea[row][col] = human1;
                this.cityarea[row + 1][col] = human2;

                humanPairs.add(new Human[]{human1, human2});

            } else i--;

        }
    }

    public Citypart[][] getCityarea() {
        return cityarea;
    }

    public char[][] getCityareaChar() {

        char[][] cityAreaChar = new char[cityarea.length][cityarea[0].length];

        for (int i = 0; i < cityarea.length; i++) {
            for (int j = 0; j < cityarea[0].length; j++) {

                cityAreaChar[i][j] = cityarea[i][j].getType();

            }

        }

        return cityAreaChar;
    }

    public int getEmergencyRow() {
        return emergencyRow;
    }

    public int getEmergencyCol() {
        return emergencyCol;
    }

    public ArrayList<DronePort> getDronePorts() {
        return dronePorts;
    }

    public Human getHumanWithHeartAttack() {
        return humanWithHeartAttack;
    }

    public Human getHumanWithNoHeartAttack() {
        return humanWithNoHeartAttack;
    }
}
