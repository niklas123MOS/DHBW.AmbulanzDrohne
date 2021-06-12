package drone.technologies;

import city.Human;
import drone.ICentralUnitPart;
import drone.ICentralUnitVisitor;

public class Camera implements ICentralUnitPart {
    @Override
    public void accept(ICentralUnitVisitor visitor) {
        visitor.visit(this);
    }

    public char[][] scanFace(Human human) {
        return human.getFace();
    }
}
