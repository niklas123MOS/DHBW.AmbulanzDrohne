public class DstarNode implements Comparable<DstarNode> {
    private final int row;
    private final int column;
    private String tag = "NEW";
    private String state = "O";
    private String label = "";
    private double h;
    private double k;
    private DstarNode backPointer;

    public DstarNode(int row, int column) {
        this.row = row;
        this.column = column;
        this.setLabel(Integer.toString(row) + column);
        this.h = 0;
        this.k = 0;
        setBackPointer(null);
    }

    public DstarNode getBackPointer() {
        return backPointer;
    }

    public void setBackPointer(DstarNode backPointer) {
        this.backPointer = backPointer;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int compareTo(DstarNode o) {
        int compare;

        if (this.k < o.k) {
            compare = -1;
        } else if (this.k > o.k) {
            compare = 1;
        } else {
            compare = 0;
        }

        return compare;
    }
}