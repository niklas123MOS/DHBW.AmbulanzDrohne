package drone;

import drone.Motor.Motor;
import drone.technologies.Camera;
import drone.technologies.GPS;
import drone.technologies.LIDAR;
import drone.technologies.Light;

public class CentralUnitVisitor implements ICentralUnitVisitor {
    @Override
    public void visit(Motor motor) {
        System.out.println(motor + " visited.");
    }

    @Override
    public void visit(LIDAR lidar) {
        System.out.println(lidar + " visited.");
    }

    @Override
    public void visit(Camera camera) {
        System.out.println(camera + " visited.");
    }

    @Override
    public void visit(GPS gps) {
        System.out.println(gps + " visited.");
    }

    @Override
    public void visit(Light light) {
        System.out.println(light + " visited.");
    }

    @Override
    public void visit(Box box) {
        System.out.println(box + " visited.");
    }
}
