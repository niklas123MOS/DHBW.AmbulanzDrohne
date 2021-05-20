package drone.Motor;

import drone.ICentralUnitPart;
import drone.ICentralUnitVisitor;
import drone.boom.Boom;

public class Motor implements IMotorCommand, ICentralUnitPart {

    RotorBlade blade01 = new RotorBlade(this);
    RotorBlade blade02 = new RotorBlade(this);
    Boom boom;

    public Motor(Boom boom) {
        this.boom = boom;
    }

    @Override
    public void takeOff() {
        System.out.println("Take Off");
    }

    @Override
    public void forward() {
        System.out.println("Forward");
    }

    @Override
    public void left() {
        System.out.println("left");
    }

    @Override
    public void right() {
        System.out.println("right");
    }

    @Override
    public void land() {
        System.out.println("land");
    }

    @Override
    public void accept(ICentralUnitVisitor visitor) {
        visitor.visit(this);
    }
}
