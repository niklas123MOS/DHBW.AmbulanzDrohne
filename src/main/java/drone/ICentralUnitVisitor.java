package drone;

import drone.Motor.Motor;
import drone.technologies.Camera;
import drone.technologies.GPS;
import drone.technologies.LIDAR;
import drone.technologies.Light;

public interface ICentralUnitVisitor {

    void visit(Motor motor);
    void visit(LIDAR lidar);
    void visit(Camera camera);
    void visit(GPS gps);
    void visit(Light light);
    void visit(Box box);
}
