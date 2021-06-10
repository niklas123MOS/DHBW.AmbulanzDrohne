package drone;

import drone.Motor.Motor;
import drone.technologies.Camera;
import drone.technologies.GPS;
import drone.technologies.LIDAR;
import drone.technologies.Light;

public class CentralUnitMediator implements ICentralUnitMediator {
    Motor motor1Top;
    Motor motor1Bottom;
    Motor motor2Top;
    Motor motor2Bottom;
    Motor motor3Top;
    Motor motor3Bottom;
    Motor motor4Top;
    Motor motor4Bottom;
    LIDAR lidar;
    Camera camera;
    GPS gps;
    Light light1;
    Light light2;
    Box box;

    public void registerLidar(LIDAR lidar) {
        this.lidar = lidar;
    }

    public void registerCamera(Camera camera) {
        this.camera = camera;
    }

    public void registerGps(GPS gps) {
        this.gps = gps;
    }

    public void registerLight1(Light light1) {
        this.light1 = light1;
    }

    public void registerLight2(Light light2) {
        this.light2 = light2;
    }

    public void registerBox(Box box) {
        this.box = box;
    }



    @Override
    public void registerMotor1Top(Motor motor) {
        this.motor1Top = motor;
    }

    @Override
    public void registerMotor1Bottom(Motor motor) {
        this.motor1Bottom= motor;
    }

    @Override
    public void registerMotor2Top(Motor motor) {
        this.motor2Top= motor;
    }

    @Override
    public void registerMotor2Bottom(Motor motor) {
        this.motor2Bottom = motor;
    }

    @Override
    public void registerMotor3Top(Motor motor) {
        this.motor3Top= motor;
    }

    @Override
    public void registerMotor3Bottom(Motor motor) {
        this.motor3Bottom= motor;
    }

    @Override
    public void registerMotor4Top(Motor motor) {
        this.motor4Top= motor;
    }

    @Override
    public void registerMotor4Bottom(Motor motor) {
        this.motor4Bottom= motor;
    }
}
