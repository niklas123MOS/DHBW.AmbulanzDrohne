package city;

public class Citypart {

    private char type;
    protected int row;
    protected int col;
    protected City city;

    public Citypart(char type) {
        this.type = type;
    }

    public Citypart(char type, int row, int col, City city) {
        this.type = type;
        this.row = row;
        this.col = col;
        this.city = city;
    }

    public char getType() {
        return this.type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public City getCity() {
        return city;
    }
}
