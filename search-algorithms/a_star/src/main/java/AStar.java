import java.util.*;

public class AStar {
    private static AStar instance = new AStar();

    public Port port;

    private AStar(){
        this(DEFAULT_HV_COST, DEFAULT_DIAGONAL_COST);
        port = new Port();
    }

    public static AStar getInstance(){
        return instance;
    }

    public static void main(String[] args) {
        char[][]city = new char[][]{{' ',' ',' ',' ',' ',' ','S',' ',' ',' '},
                {' ',' ',' ',' ',' ',' ','S',' ',' ',' '},
                {' ',' ',' ','R',' ',' ','S',' ',' ',' '},
                {' ',' ',' ','R',' ',' ','S',' ',' ',' '},
                {' ',' ',' ','R',' ',' ','S',' ',' ',' '},
                {' ',' ',' ','R',' ',' ','S',' ',' ',' '},
                {' ',' ',' ','R',' ',' ','S',' ',' ',' '},
                {' ',' ',' ','R',' ',' ','S',' ',' ',' '},
                {' ',' ',' ','R',' ',' ',' ',' ',' ',' '},
                {' ',' ',' ','R',' ',' ',' ',' ',' ',' '}};

        int[][] path = instance.port.findPath(2,1,1,9, city);
        for (int i = 0; i < path.length; i++) {

            System.out.print(path[i][0]+" ");
            System.out.println(path[i][1]);
        }
    }


    private static final int DEFAULT_HV_COST = 10;
    private static final int DEFAULT_DIAGONAL_COST = Integer.MAX_VALUE;
    private int hvCost;
    private int diagonalCost;
    private Node[][] searchArea;
    private PriorityQueue<Node> openList;
    private Set<Node> closedSet;
    private Node initialNode;
    private Node finalNode;

    public AStar(int hvCost, int diagonalCost) {
        this.hvCost = hvCost;
        this.diagonalCost = diagonalCost;
        this.openList = new PriorityQueue<>(new Comparator<Node>() {
            public int compare(Node node0, Node node1) {
                return Integer.compare(node0.getF(), node1.getF());
            }
        });
        this.closedSet = new HashSet<>();
    }

//    public AStar() {
//        this(DEFAULT_HV_COST, DEFAULT_DIAGONAL_COST);
//    }

    private void setNodes() {
        for (int i = 0; i < searchArea.length; i++) {
            for (int j = 0; j < searchArea[0].length; j++) {
                Node node = new Node(i, j);
                node.calculateHeuristic(getFinalNode());
                this.searchArea[i][j] = node;
            }
        }
    }

    public void setBlocks(int[][] blocksArray) {
        for (int i = 0; i < blocksArray.length; i++) {
            int row = blocksArray[i][0];
            int col = blocksArray[i][1];
            setBlock(row, col);
        }
    }

    public int[][] innerFindPath(int initialRow, int initialCol, int finalRow, int finalCol, char[][] city) {

        int rows=city[0].length;
        int cols=city.length;

        this.searchArea = new Node[rows][cols];
        this.initialNode = new Node(initialRow, initialCol);
        this.finalNode = new Node(finalRow, finalCol);
        setNodes();

        int numberOfBlocks=0;
        for (int row = 0; row < city.length ; row++) {
            for (int col = 0; col < city[0].length; col++) {
                if (city[row][col]=='R'|| city[row][col]=='S'){
                    numberOfBlocks+=1;
                }
            }
        }
        //int[][] blocksArray = new int[][]{{1, 3}, {2, 3}, {3, 3}};
        int[][] blocksArray = new int[numberOfBlocks][2];
        int currentblockArrayIndex = 0;
        for (int row = 0; row < city.length; row++) {
            for (int col = 0; col < city[0].length ;col++) {
                if (city[row][col]=='R'|| city[row][col]=='S') {

                    blocksArray[currentblockArrayIndex][0]=row;
                    blocksArray[currentblockArrayIndex][1]=col;
                    currentblockArrayIndex++;
                }
            }

        }

        this.setBlocks(blocksArray);


        openList.add(initialNode);
        List<Node> nodeList = new ArrayList<>();
        while (!isEmpty(openList)) {
            Node currentNode = openList.poll();
            closedSet.add(currentNode);
            if (isFinalNode(currentNode)) {
                nodeList = getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }

        int[][]path = new int[nodeList.size()][2];

        for (int i = 0; i < nodeList.size() ; i++) {
            path[i][0] = nodeList.get(i).getRow();
            path[i][1] = nodeList.get(i).getCol();
        }

        return path;
    }

    private List<Node> getPath(Node currentNode) {
        List<Node> path = new ArrayList<>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    private void addAdjacentNodes(Node currentNode) {
        addAdjacentUpperRow(currentNode);
        addAdjacentMiddleRow(currentNode);
        addAdjacentLowerRow(currentNode);
    }

    private void addAdjacentLowerRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int lowerRow = row + 1;
        if (lowerRow < getSearchArea().length) {
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, lowerRow, getDiagonalCost());
            }
            if (col + 1 < getSearchArea()[0].length) {
                checkNode(currentNode, col + 1, lowerRow, getDiagonalCost());
            }
            checkNode(currentNode, col, lowerRow, getHvCost());
        }
    }

    private void addAdjacentMiddleRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        if (col - 1 >= 0) {
            checkNode(currentNode, col - 1, row, getHvCost());
        }
        if (col + 1 < getSearchArea()[0].length) {
            checkNode(currentNode, col + 1, row, getHvCost());
        }
    }

    private void addAdjacentUpperRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            if (col - 1 >= 0) {
                checkNode(currentNode, col - 1, upperRow, getDiagonalCost());
            }
            if (col + 1 < getSearchArea()[0].length) {
                checkNode(currentNode, col + 1, upperRow, getDiagonalCost());
            }
            checkNode(currentNode, col, upperRow, getHvCost());
        }
    }

    private void checkNode(Node currentNode, int col, int row, int cost) {
        Node adjacentNode = getSearchArea()[row][col];
        if (!adjacentNode.isBlock() && !getClosedSet().contains(adjacentNode)) {
            if (!getOpenList().contains(adjacentNode)) {
                adjacentNode.setNodeData(currentNode, cost);
                getOpenList().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
                if (changed) {
                    getOpenList().remove(adjacentNode);
                    getOpenList().add(adjacentNode);
                }
            }
        }
    }

    private boolean isFinalNode(Node currentNode) {
        return currentNode.equals(finalNode);
    }

    private boolean isEmpty(PriorityQueue<Node> openList) {
        return openList.size() == 0;
    }

    private void setBlock(int row, int col) {
        this.searchArea[row][col].setBlock(true);
    }

    public Node getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(Node initialNode) {
        this.initialNode = initialNode;
    }

    public Node getFinalNode() {
        return finalNode;
    }

    public void setFinalNode(Node finalNode) {
        this.finalNode = finalNode;
    }

    public Node[][] getSearchArea() {
        return searchArea;
    }

    public void setSearchArea(Node[][] searchArea) {
        this.searchArea = searchArea;
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public void setOpenList(PriorityQueue<Node> openList) {
        this.openList = openList;
    }

    public Set<Node> getClosedSet() {
        return closedSet;
    }

    public void setClosedSet(Set<Node> closedSet) {
        this.closedSet = closedSet;
    }

    public int getHvCost() {
        return hvCost;
    }

    public void setHvCost(int hvCost) {
        this.hvCost = hvCost;
    }

    private int getDiagonalCost() {
        return diagonalCost;
    }

    private void setDiagonalCost(int diagonalCost) {
        this.diagonalCost = diagonalCost;
    }

    public class Port implements IAStar {
        public int[][] findPath(int x1, int y1, int x2, int y2, char[][] map) {
            return innerFindPath(x1, y1, x2, y2, map);
        }
    }
}