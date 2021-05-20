package drone;

public class GPS implements ICentralUnitPart {
    @Override
    public void accept(ICentralUnitVisitor visitor) {

        visitor.visit(this);
    }
}
