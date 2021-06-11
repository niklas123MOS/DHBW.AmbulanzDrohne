package drone;

public enum Direction {
    TOP, TOPRIGHT,  RIGHT, BOTTOMRIGHT, BOTTOM, BOTTOMLEFT, LEFT, TOPLEFT;

    private static Direction[] vals = values();

    public Direction next(){
        return vals[(this.ordinal()+1) % vals.length];
    }

    public Direction previous(){
        return vals[(this.ordinal()+7) % vals.length];
    }

    public Direction getOppositeDirection(){
        return vals[(this.ordinal()+4) % vals.length];
    }

}
