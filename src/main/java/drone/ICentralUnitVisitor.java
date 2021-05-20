package drone;

import drone.Motor.Motor;

public interface ICentralUnitVisitor {

    void visit(Motor motor);
    void visit(LIDAR lidar);
    void visit(Camera camera);
    void visit(GPS gps);
    void visit(Light light);
    void visit(Box box);
}
