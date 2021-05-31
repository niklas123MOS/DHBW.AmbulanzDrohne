package drone;

public enum CompasDirection {
    NORTH, EAST, SOUTH, WEST;

    private static CompasDirection[] vals = values();

    public CompasDirection next(){
        return vals[(this.ordinal()+1) % vals.length];
    }

    public CompasDirection previous(){
        return vals[(this.ordinal()+3) % vals.length];
    }
}
