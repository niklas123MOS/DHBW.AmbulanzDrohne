package drone.technologies;

import drone.ICentralUnitPart;
import drone.ICentralUnitVisitor;

public class Light implements ICentralUnitPart {
    @Override
    public void accept(ICentralUnitVisitor visitor) {
        visitor.visit(this);
    }
}
