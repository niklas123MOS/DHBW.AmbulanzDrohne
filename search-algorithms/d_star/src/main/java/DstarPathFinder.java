import java.util.ArrayList;
import java.util.PriorityQueue;

public class DstarPathFinder {
    public static final double NORMAL_COST = 1.0;
    public static final double ADJACENT_COST = 1.4;
    public static final double INFINITY_COST = 10000;
    private final CostTable costs;
    private final DstarMap map;
    private final PriorityQueue<DstarNode> openList;
    private final DstarNode start;
    private final DstarNode goal;
    private DstarNode currentRobotPosition;

    public DstarPathFinder(DstarMap map) {
        costs = new CostTable();
        openList = new PriorityQueue<>();
        this.map = map;

        start = map.getStart();
        currentRobotPosition = map.getRobotLocation();
        goal = map.getGoal();

        buildCostTable();
    }

    public ArrayList<DstarNode> traverseMap() {
        double minimum_K;
        insert(goal, 0);

        ArrayList<DstarNode> path = new ArrayList<>();

        do {
            minimum_K = processState();
        } while (minimum_K != -1.0 && !start.getTag().equalsIgnoreCase("CLOSED"));

        if (minimum_K == -1.0) {
            System.out.println("goal unreachable");
            return path;
        } else {
            do {
                DstarNode next;
                DstarNode here;
                boolean unknownFound;

                do {
                    here = map.getRobotLocation();
                    path.add(here);
                    next = here.getBackPointer();
                    unknownFound = next.getState().equals("U");

                    if (!unknownFound) {
                        map.setRobotLocation(next);
                        currentRobotPosition = map.getRobotLocation();
                    }
                } while (currentRobotPosition != goal && !unknownFound);

                if (currentRobotPosition == goal) {
                    System.out.println("goal has been reached");
                    path.add(goal);
                    return path;
                } else {
                    modifyCost(here, next, INFINITY_COST);
                    do {
                        minimum_K = processState();
                    } while (minimum_K < next.getH() && minimum_K != -1.0 && !currentRobotPosition.getTag().equalsIgnoreCase("CLOSED"));
                    if (minimum_K == -1.0) {
                        System.out.println("goal unreachable");
                        return new ArrayList<>();
                    }
                }

            } while (true);
        }
    }

    private double modifyCost(DstarNode current, DstarNode neighbor, double newVal) {
        ArrayList<DstarNode> neighborList = map.getNeighbors(neighbor.getRow(), neighbor.getColumn());

        for (DstarNode each : neighborList) {
            costs.updateValue(neighbor.getLabel(), each.getLabel(), INFINITY_COST);
        }

        if (neighbor.getTag().equalsIgnoreCase("CLOSED")) {
            insert(neighbor, newVal);
        }

        DstarNode current_Min = openList.peek();
        return current_Min.getK();
    }

    private double processState() {
        double k_Old;
        ArrayList<DstarNode> neighbors;

        if (openList.isEmpty()) {
            return -1.0;
        }

        if (currentRobotPosition.getK() == 10000 && currentRobotPosition.getH() == 10000) {
            return -1.0;
        }

        DstarNode currentNode = openList.remove();

        k_Old = currentNode.getK();

        currentNode.setTag("CLOSED");

        if (k_Old < currentNode.getH()) {
            System.out.println("Step 2 - rerouting - node: " + currentNode.getLabel());

            neighbors = map.getNeighbors(currentNode.getRow(), currentNode.getColumn());
            for (DstarNode neighbor : neighbors) {
                System.out.println("Updating Neighbor: " + neighbor.getLabel());
                double costXthroughY = neighbor.getH() + costs.getValue(neighbor.getLabel(), currentNode.getLabel());

                if (neighbor.getH() <= k_Old &&
                        currentNode.getH() > costXthroughY) {
                    System.out.println("-- step 2 -- set back pointer to " + neighbor.getLabel());
                    System.out.println("current: " + currentNode.getLabel());

                    currentNode.setBackPointer(neighbor);
                    currentNode.setH(costXthroughY);
                }
            }
        }

        if (k_Old == currentNode.getH()) {
            neighbors = map.getNeighbors(currentNode.getRow(), currentNode.getColumn());

            for (DstarNode neighbor : neighbors) {
                double costThroughX = currentNode.getH() + costs.getValue(currentNode.getLabel(), neighbor.getLabel());
                double roundedNumber = (double) Math.round(costThroughX * 10) / 10;
                if (neighbor.getTag().equalsIgnoreCase("NEW") ||
                        (neighbor.getBackPointer() == currentNode && neighbor.getH() != costThroughX) ||
                        (neighbor.getBackPointer() != currentNode && neighbor.getH() > costThroughX)) {

                    neighbor.setBackPointer(currentNode);
                    insert(neighbor, roundedNumber);
                }
            }
        } else {
            neighbors = map.getNeighbors(currentNode.getRow(), currentNode.getColumn());
            for (DstarNode neighbor : neighbors) {
                double costThroughX = currentNode.getH() + costs.getValue(currentNode.getLabel(), neighbor.getLabel());
                double costXthroughY = neighbor.getH() + costs.getValue(neighbor.getLabel(), currentNode.getLabel());

                double roundedCostThroughX = (double) Math.round(costThroughX * 10) / 10;
                double roundedCostXThroughY = (double) Math.round(costXthroughY * 10) / 10;
                double roundedH;

                if (neighbor.getTag().equalsIgnoreCase("NEW") || (neighbor.getBackPointer() == currentNode && neighbor.getH() != costThroughX)) {
                    neighbor.setBackPointer(currentNode);
                    insert(neighbor, roundedCostThroughX);
                } else if (neighbor.getBackPointer() != currentNode && neighbor.getH() > roundedCostThroughX) {
                    roundedH = (double) Math.round(currentNode.getH() * 10) / 10;
                    insert(currentNode, roundedH);
                } else if (neighbor.getBackPointer() != currentNode && currentNode.getH() > roundedCostXThroughY && neighbor.getTag().equalsIgnoreCase("CLOSED") && neighbor.getH() > k_Old) {
                    roundedH = (double) Math.round(neighbor.getH() * 10) / 10;
                    insert(neighbor, roundedH);
                }
            }
        }

        double minK = -1.0;
        if (!openList.isEmpty()) {
            minK = openList.peek().getK();
        }

        return minK;
    }

    private void insert(DstarNode someNode, double newH) {
        if (newH > 10000) {
            newH = 10000;
        }
        double roundedH;
        double currentK;

        if (someNode.getTag().equalsIgnoreCase("NEW")) {
            someNode.setK(newH);
            someNode.setH(newH);
            someNode.setTag("OPEN");
            openList.add(someNode);
        }

        if (someNode.getTag().equalsIgnoreCase("OPEN")) {
            currentK = someNode.getK();
            openList.remove(someNode);
            someNode.setK(Math.min(currentK, newH));
            openList.add(someNode);
        }

        if (someNode.getTag().equalsIgnoreCase("CLOSED")) {
            roundedH = (double) Math.round(someNode.getH() * 10) / 10;
            someNode.setK(Math.min(roundedH, newH));
            someNode.setH(newH);
            someNode.setTag("OPEN");
            openList.add(someNode);

        }
    }

    private void buildCostTable() {
        DstarNode neighbor;
        for (int i = 0; i < map.getRows(); i++) {
            for (int j = 0; j < map.getColumns(); j++) {
                DstarNode current = map.getNode(i, j);
                int top = i - 1;
                int bottom = i + 1;
                int left = j - 1;
                int right = j + 1;

                if (!neighborOutOfBounds(top, left)) {
                    neighbor = map.getNode(top, left);

                    if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
                        costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
                    } else {
                        costs.setValue(current.getLabel(), neighbor.getLabel(), ADJACENT_COST);
                    }
                }

                if (!neighborOutOfBounds(top, j)) {
                    neighbor = map.getNode(top, j);

                    if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
                        costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
                    } else {
                        costs.setValue(current.getLabel(), neighbor.getLabel(), NORMAL_COST);
                    }
                }

                if (!neighborOutOfBounds(top, right)) {
                    neighbor = map.getNode(top, right);

                    if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
                        costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
                    } else {
                        costs.setValue(current.getLabel(), neighbor.getLabel(), ADJACENT_COST);
                    }
                }

                if (!neighborOutOfBounds(i, left)) {
                    neighbor = map.getNode(i, left);

                    if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
                        costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
                    } else {
                        costs.setValue(current.getLabel(), neighbor.getLabel(), NORMAL_COST);
                    }
                }

                if (!neighborOutOfBounds(i, right)) {
                    neighbor = map.getNode(i, right);

                    if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
                        costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
                    } else {
                        costs.setValue(current.getLabel(), neighbor.getLabel(), NORMAL_COST);
                    }
                }

                if (!neighborOutOfBounds(bottom, left)) {
                    neighbor = map.getNode(bottom, left);

                    if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
                        costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
                    } else {
                        costs.setValue(current.getLabel(), neighbor.getLabel(), ADJACENT_COST);
                    }
                }

                if (!neighborOutOfBounds(bottom, j)) {
                    neighbor = map.getNode(bottom, j);

                    if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
                        costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
                    } else {
                        costs.setValue(current.getLabel(), neighbor.getLabel(), NORMAL_COST);
                    }
                }

                if (!neighborOutOfBounds(bottom, right)) {
                    neighbor = map.getNode(bottom, right);

                    if (current.getState().equalsIgnoreCase("B") || neighbor.getState().equalsIgnoreCase("B")) {
                        costs.setValue(current.getLabel(), neighbor.getLabel(), INFINITY_COST);
                    } else {
                        costs.setValue(current.getLabel(), neighbor.getLabel(), ADJACENT_COST);
                    }
                }
            }
        }
    }

    private boolean neighborOutOfBounds(int row, int column) {
        return row < 0 || row >= map.getRows() || column < 0 || column >= map.getColumns();
    }
}