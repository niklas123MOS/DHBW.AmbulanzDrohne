package drone;

import Configuration.Configuration;
import drone.boom.Boom;
import drone.boom.MainBoom;
import drone.boom.SubBoom;
import drone.memento.DroneMemento;
import drone.memento.MementoCaretaker;
import drone.technologies.Camera;
import drone.technologies.GPS;
import drone.technologies.LIDAR;
import drone.technologies.Light;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Drone implements IElectrodesListener {

    private Boom mainBoom01;
    private Boom mainBoom02;
    private Boom mainBoom03;
    private Boom mainBoom04;

    private boolean allowedAtNight;
    private boolean antiTheftProtection;
    private boolean enableHooter;

    private CentralUnitMediator centralUnit = new CentralUnitMediator();

    private LIDAR lidar;
    private Camera camera;
    private GPS gps;
    private Light light1;
    private Light light2;
    private Box box;

    private int emergencyRow;
    private int emergencyCol;

    private int row;
    private int col;


    private Direction direction = Direction.TOP;

    private ArrayList<Direction> route = new ArrayList<Direction>();


    public static void main(String[] args) {

        try {


            if (args[0].equals("-configure")) {
                Drone drone = new Drone.Builder().mainBoom01().mainBoom02().mainBoom03().mainBoom04().setParameters().build();
                drone.initCentralunit();
                drone.menu();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("To configure Drone, please start the drone with parameter \"-configure\"!");
        }

    }

    public void flyRoute() {

        this.takeOff();
        System.out.println("Drone " + this + " flying to Position: ");
        System.out.println("         row: " + this.emergencyRow);
        System.out.println("         col: " + this.emergencyCol);

        for (Direction direction : this.route) {

            int countLeft = 0;
            int countRight = 0;
            Direction currentDirection = this.direction;


            // find out if its better to turn left or right
            char turnSide = getBestTurn(currentDirection, direction);


            //turn until direction is right
            while (this.direction != direction) {
                if (turnSide == 'l') {
                    left();
                } else if (turnSide == 'r') {
                    right();
                }

            }
            //fly forward in the right direction
            forward();

        }

        //land on landingposition
        this.land();


    }

    private void flyBackToDronePort() {

        if (route == null) return;

        this.takeOff();
        System.out.println("Drone " + this + " flying back to Droneport: ");

        for (Direction direction : this.route) {

            Direction currentDirection = this.direction;

            // find out if its better to turn left or right
            char turnSide = getBestTurn(currentDirection, direction.getOppositeDirection());

            //turn until direction is right
            while (this.direction != direction.getOppositeDirection()) {
                if (turnSide == 'l') {
                    left();
                } else if (turnSide == 'r') {
                    right();
                }

            }
            //fly forward in the right direction
            forward();

        }

        this.land();

    }

    private char getBestTurn(Direction startDirection, Direction goalDirection) {
        int countLeft = 0;
        int countRight = 0;

        Direction currentDirection = startDirection;

        // find out if its better to turn left or right
        //counts the steps until right direction is reached
        // first turning left, second turning right

        while (currentDirection != goalDirection) {
            currentDirection = currentDirection.previous();
            countLeft++;
        }

        currentDirection = startDirection;

        while (currentDirection != goalDirection) {
            currentDirection = currentDirection.next();
            countRight++;
        }

        if (countLeft < countRight) {
            return 'l';
        } else return 'r';

    }

    public void setRoute(ArrayList<Direction> route, int row, int col) {
        this.route = route;
        this.emergencyRow = row;
        this.emergencyCol = col;

    }

    public ArrayList<Electrode> getElectrodesFromBox() {
        return this.box.takeElectrodes();
    }

    public void layBackElectrodesInBox(ArrayList<Electrode> electrodes) {
        this.box.layBackElectrodes(electrodes);
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public CentralUnitMediator getCentralUnit() {
        return centralUnit;
    }

    public LIDAR getLidar() {
        return lidar;
    }

    public Camera getCamera() {
        return camera;
    }

    public GPS getGps() {
        return gps;
    }

    public Light getLight1() {
        return light1;
    }

    public Light getLight2() {
        return light2;
    }

    public Box getBox() {
        return box;
    }

    public Boom getMainBoom01() {
        return mainBoom01;
    }

    public Boom getMainBoom02() {
        return mainBoom02;
    }

    public Boom getMainBoom03() {
        return mainBoom03;
    }

    public Boom getMainBoom04() {
        return mainBoom04;
    }

    public int getEmergencyRow() {
        return emergencyRow;
    }

    public int getEmergencyCol() {
        return emergencyCol;
    }

    public ArrayList<Direction> getRoute() {
        return route;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void initCentralunit() {


        SubBoom boom1 = (SubBoom) this.mainBoom01.listUnits().get(0);
        SubBoom boom2 = (SubBoom) this.mainBoom02.listUnits().get(0);
        SubBoom boom3 = (SubBoom) this.mainBoom03.listUnits().get(0);
        SubBoom boom4 = (SubBoom) this.mainBoom04.listUnits().get(0);

        this.centralUnit.registerMotor1Top(boom1.getMotorTop());
        this.centralUnit.registerMotor1Bottom(boom1.getMotorBottom());
        this.centralUnit.registerMotor2Top(boom2.getMotorTop());
        this.centralUnit.registerMotor2Bottom(boom2.getMotorBottom());
        this.centralUnit.registerMotor3Top(boom3.getMotorTop());
        this.centralUnit.registerMotor3Bottom(boom3.getMotorBottom());
        this.centralUnit.registerMotor4Top(boom4.getMotorTop());
        this.centralUnit.registerMotor4Bottom(boom4.getMotorBottom());
        this.centralUnit.registerLidar(this.lidar);
        this.centralUnit.registerCamera(this.camera);
        this.centralUnit.registerGps(this.gps);
        this.centralUnit.registerLight1(this.light1);
        this.centralUnit.registerLight2(this.light2);
        this.centralUnit.registerBox(this.box);

    }


    private void takeOff() {

        System.out.println("Drone " + this + " take off at Position: row: " + row + " col: " + col);
        centralUnit.motor1Top.takeOff();
        centralUnit.motor1Bottom.takeOff();
        centralUnit.motor2Top.takeOff();
        centralUnit.motor2Bottom.takeOff();
        centralUnit.motor3Top.takeOff();
        centralUnit.motor3Bottom.takeOff();
        centralUnit.motor4Top.takeOff();
        centralUnit.motor4Bottom.takeOff();
    }

    private void left() {

        System.out.println("Turn left");
        direction = direction.previous();

        centralUnit.motor1Top.left();
        centralUnit.motor1Bottom.left();
        centralUnit.motor2Top.left();
        centralUnit.motor2Bottom.left();
        centralUnit.motor3Top.left();
        centralUnit.motor3Bottom.left();
        centralUnit.motor4Top.left();
        centralUnit.motor4Bottom.left();
    }

    private void right() {

        System.out.println("Turn right");
        direction = direction.next();

        centralUnit.motor1Top.right();
        centralUnit.motor1Bottom.right();
        centralUnit.motor2Top.right();
        centralUnit.motor2Bottom.right();
        centralUnit.motor3Top.right();
        centralUnit.motor3Bottom.right();
        centralUnit.motor4Top.right();
        centralUnit.motor4Bottom.right();
    }


    private void forward() {

        System.out.println("Flight Forward");

        switch (direction) {
            case TOP:
                row--;
                break;
            case TOPRIGHT:
                row--;
                col++;
                break;
            case RIGHT:
                col++;
                break;
            case BOTTOMRIGHT:
                row++;
                col++;
                break;
            case BOTTOM:
                row++;
                break;
            case BOTTOMLEFT:
                row++;
                col--;
                break;
            case LEFT:
                col--;
                break;
            case TOPLEFT:
                row--;
                col--;
                break;
        }

        centralUnit.motor1Top.forward();
        centralUnit.motor1Bottom.forward();
        centralUnit.motor2Top.forward();
        centralUnit.motor2Bottom.forward();
        centralUnit.motor3Top.forward();
        centralUnit.motor3Bottom.forward();
        centralUnit.motor4Top.forward();
        centralUnit.motor4Bottom.forward();
    }

    private void land() {

        System.out.println("Landed at position: row=" + row + " col=" + col);

        centralUnit.motor1Top.land();
        centralUnit.motor1Bottom.land();
        centralUnit.motor2Top.land();
        centralUnit.motor2Bottom.land();
        centralUnit.motor3Top.land();
        centralUnit.motor3Bottom.land();
        centralUnit.motor4Top.land();
        centralUnit.motor4Bottom.land();
    }


    private void menu() {
        Scanner intScanner = new Scanner(System.in);
        MementoCaretaker mementoCaretaker = new MementoCaretaker();
        mementoCaretaker.setMemento(this.save());
        boolean exit = true;

        while (exit) {
            System.out.println("Drone Configuration:");
            System.out.println("1 configure Parameter");
            System.out.println("2 undo");
            System.out.println("3 save");
            System.out.println("4 check");
            System.out.println("5 exit");

            int decision = intScanner.nextInt();

            switch (decision) {
                case 1:
                    System.out.println("Configure:");
                    configure();
                    System.out.println("Configuration complete:");
                    break;
                case 2:
                    undo(mementoCaretaker.getMemento());
                    System.out.println("UndoComplete");
                    break;
                case 3:
                    saveToFile();
                    break;
                case 4:
                    this.check();
                    break;
                case 5:
                    exit = false;
                    break;
                default:
                    System.out.println("Invalid Input. Please check input number!");
            }
        }

    }

    private void check() {

        ICentralUnitVisitor visitor = new CentralUnitVisitor();
        this.centralUnit.motor1Top.accept(visitor);
        this.centralUnit.motor2Top.accept(visitor);
        this.centralUnit.motor3Top.accept(visitor);
        this.centralUnit.motor4Top.accept(visitor);
        this.centralUnit.motor1Bottom.accept(visitor);
        this.centralUnit.motor2Bottom.accept(visitor);
        this.centralUnit.motor3Bottom.accept(visitor);
        this.centralUnit.motor4Bottom.accept(visitor);
        this.centralUnit.box.accept(visitor);
        this.centralUnit.camera.accept(visitor);
        this.centralUnit.lidar.accept(visitor);
        this.centralUnit.gps.accept(visitor);
        this.centralUnit.light1.accept(visitor);
        this.centralUnit.light2.accept(visitor);

    }

    private void saveToFile() {

        JSONObject obj = new JSONObject();
        obj.put("allowedAtNight", this.allowedAtNight);
        obj.put("enableHooter", this.enableHooter);
        obj.put("antiTheftProtection", this.antiTheftProtection);

        try (FileWriter file = new FileWriter(Configuration.instance.filePath)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private DroneMemento save() {
        return new DroneMemento(this.allowedAtNight, this.antiTheftProtection, this.enableHooter);
    }


    private void configure() {
        System.out.println(allowedAtNight + " " + antiTheftProtection + " " + enableHooter);
        Scanner scanner = new Scanner(System.in);
        System.out.println("allowedAtNight: [true/false]");
        this.allowedAtNight = scanner.nextBoolean();
        System.out.println("antiTheftProtection: [true/false]");
        this.antiTheftProtection = scanner.nextBoolean();
        System.out.println("enableHooter: [true/false]");
        this.enableHooter = scanner.nextBoolean();
        System.out.println(allowedAtNight + " " + antiTheftProtection + " " + enableHooter);
    }

    private void undo(DroneMemento memento) {
        System.out.println(allowedAtNight + " " + antiTheftProtection + " " + enableHooter);
        this.allowedAtNight = memento.getAllowedAtNight();
        this.antiTheftProtection = memento.getAntiTheftProtection();
        this.enableHooter = memento.getEnableHooter();
        System.out.println(allowedAtNight + " " + antiTheftProtection + " " + enableHooter);

    }


    private Drone(Builder builder) {
        this.mainBoom01 = builder.mainBoom01;
        this.mainBoom02 = builder.mainBoom02;
        this.mainBoom03 = builder.mainBoom03;
        this.mainBoom04 = builder.mainBoom04;
        this.lidar = builder.lidar;
        this.camera = builder.camera;
        this.gps = builder.gps;
        this.light1 = builder.light1;
        this.light2 = builder.light2;
        this.box = builder.box;
        this.antiTheftProtection = builder.antiTheftProtection;
        this.allowedAtNight = builder.allowedAtNight;
        this.enableHooter = builder.enableHooter;
    }

    @Override
    public void laidBackElectrodes() {

        //get notification from listener
        flyBackToDronePort();
    }


    public static class Builder {

        private Boom mainBoom01;
        private Boom mainBoom02;
        private Boom mainBoom03;
        private Boom mainBoom04;
        private LIDAR lidar = new LIDAR();
        private Camera camera = new Camera();
        private GPS gps = new GPS();
        private Light light1 = new Light();
        private Light light2 = new Light();
        private Box box = new Box();
        private boolean allowedAtNight;
        private boolean antiTheftProtection;
        private boolean enableHooter;

        public Builder setParameters() {
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader(Configuration.instance.filePath));
                JSONObject jsonObject = (JSONObject) obj;
                this.allowedAtNight = Boolean.parseBoolean(jsonObject.get("allowedAtNight").toString());
                this.antiTheftProtection = Boolean.parseBoolean(jsonObject.get("antiTheftProtection").toString());
                this.enableHooter = Boolean.parseBoolean(jsonObject.get("enableHooter").toString());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return this;

        }

        public Builder mainBoom01() {


            this.mainBoom01 = newMainBoom("mb1");
            return this;
        }

        public Builder mainBoom02() {
            this.mainBoom02 = newMainBoom("mb2");
            ;
            return this;
        }

        public Builder mainBoom03() {
            this.mainBoom03 = newMainBoom("mb3");
            ;
            return this;
        }

        public Builder mainBoom04() {
            this.mainBoom04 = newMainBoom("mb4");
            ;
            return this;
        }

        public Drone build() {
            return new Drone(this);
        }

        private MainBoom newMainBoom(String name) {
            MainBoom mb = new MainBoom(name);
            Boom mb_sb1 = new SubBoom(name + "sb1");
            Boom mb_sb2 = new SubBoom(name + "sb2");
            mb.addBoom(mb_sb1);
            mb.addBoom(mb_sb2);

            return mb;

        }


    }


}
