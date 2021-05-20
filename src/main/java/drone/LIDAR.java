package drone;

public class LIDAR implements ICentralUnitPart {
    @Override
    public void accept(ICentralUnitVisitor visitor) {
        visitor.visit(this);
    }
}
