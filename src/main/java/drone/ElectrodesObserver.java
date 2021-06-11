package drone;

import java.util.ArrayList;

public class ElectrodesObserver {

    private ArrayList<IElectrodesListener> listeners;

    public ElectrodesObserver() {
        listeners = new ArrayList<>();
    }

    public void layBackElectrodes() {
        for (IElectrodesListener listener : listeners)
            listener.laidBackElectrodes();
    }

    public void addListener(IElectrodesListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IElectrodesListener listener) {
        listeners.remove(listener);
    }
}

