package drone.boom;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public abstract class Boom {

    protected int indentDepth = 0;
    protected String boomName;
    protected Boom parentBoom;
    protected ArrayList<Boom> booms;

    public Boom(String boomName) {
        this.boomName = boomName;
        this.booms = new ArrayList<>();
    }

    public abstract void printStaffingInformation();

    public void addBoom(Boom boom) {
        booms.add(boom);
        boom.parentBoom= this;
    }

    public final boolean isComposite() {
        return !booms.isEmpty();
    }

    public ArrayList<Boom> listUnits() {
        return this.booms;
    }

    protected String indent(int depth) {
        StringBuilder stringBuilder = new StringBuilder();

        do {
            stringBuilder.append("+ ");
        } while (depth-- > 0);

        return stringBuilder.toString();
    }

    public final void printStructure() {
        System.out.println(indent(indentDepth) + boomName +
                (isComposite() ? " (node)" : " (leaf)"));
        for (Boom area : booms) {
            area.indentDepth = indentDepth + 1;
            area.printStructure();
        }
    }

    public String getSuperiorBoom() {
        String parent;

        if (parentBoom == null)
            parent = "--- top level unit";
        else
            parent = this.parentBoom.boomName;

        return parent;
    }

}
