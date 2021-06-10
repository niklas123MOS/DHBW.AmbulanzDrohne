package EmergencyCenter;

import city.City;
import city.Citypart;

public abstract class Subscriber extends Citypart {


    public Subscriber(char type) {
        super(type);
    }

    public Subscriber(char type, int x, int y, City city) {
        super(type, x, y, city);
    }
}
