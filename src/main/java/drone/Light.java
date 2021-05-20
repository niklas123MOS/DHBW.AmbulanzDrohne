package drone;

public class Light implements ICentralUnitPart {
    @Override
    public void accept(ICentralUnitVisitor visitor) {
        visitor.visit(this);
    }
}
