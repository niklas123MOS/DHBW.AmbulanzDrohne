package drone;

import java.util.ArrayList;

public class Box implements ICentralUnitPart {

    Electrode electrode01;
    Electrode electrode02;

    public Box() {
        this.electrode01 = new Electrode();
        this.electrode02 = new Electrode();
    }

    @Override
    public void accept(ICentralUnitVisitor visitor) {
        visitor.visit(this);
    }

    public ArrayList<Electrode> takeElectrodes() {
        ArrayList<Electrode> electrodes = new ArrayList<>();
        electrodes.add(electrode01);
        electrodes.add(electrode02);

        this.electrode01 = null;
        this.electrode02 = null;

        return electrodes;
    }

    public void layBackElectrodes(ArrayList<Electrode> electrodes) {

        this.electrode01 = electrodes.get(0);
        this.electrode02 = electrodes.get(1);

    }

}
