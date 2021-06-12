package drone;

import drone.Motor.Motor;
import drone.technologies.Camera;
import drone.technologies.GPS;
import drone.technologies.LIDAR;
import drone.technologies.Light;

public interface ICentralUnitMediator {

    void registerMotor1Top(Motor motor);

    void registerMotor1Bottom(Motor motor);

    void registerMotor2Top(Motor motor);

    void registerMotor2Bottom(Motor motor);

    void registerMotor3Top(Motor motor);

    void registerMotor3Bottom(Motor motor);

    void registerMotor4Top(Motor motor);

    void registerMotor4Bottom(Motor motor);

    void registerLidar(LIDAR lidar);

    void registerCamera(Camera camera);

    void registerGps(GPS gps);

    void registerLight1(Light light1);

    void registerLight2(Light light2);

    void registerBox(Box box);

}
