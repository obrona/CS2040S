import java.util.Arrays;
import java.util.LinkedList;

public class TSPGraph implements IApproximateTSP {

    @Override
    public void MST(TSPMap map) {
        boolean[] inMST = new boolean[map.getCount()];
        Arrays.fill(inMST, false);
        TreeMapPriorityQueue<Double, Integer> priorityQueue = new TreeMapPriorityQueue<>();
        map.setLink(0,0,false);
        priorityQueue.add(0,0.0);
        int numOfConnected = 0;
        while(numOfConnected < map.getCount()) {
            Integer currNode = priorityQueue.extractMin();

                inMST[currNode] = true;
                for (int otherNode = 0; otherNode < map.getCount(); otherNode ++) {
                    if (otherNode != currNode && !inMST[otherNode]) {
                       Double value = priorityQueue.lookup(otherNode);
                       if (value == null) {
                           priorityQueue.add(otherNode, map.pointDistance(currNode, otherNode));
                           map.setLink(otherNode, currNode, false);
                       } else {
                           Double newDistance = map.pointDistance(currNode, otherNode);
                           if (newDistance < value) {
                               map.setLink(otherNode, currNode,false);
                               priorityQueue.decreasePriority(otherNode, newDistance);
                           }
                       }
                    }
                }

        numOfConnected ++;
        }


    }


    @Override
    public void TSP(TSPMap map) {
        MST(map);
        int[][] adjacencyMatrix = createMatrix(map);
        boolean[] visited = new boolean[map.getCount()];
        Arrays.fill(visited, false);
        LinkedList<Integer> walk = new LinkedList<>();
        helperTSP(0,0,walk,map,adjacencyMatrix);

        LinkedList<Integer> tspPath = new LinkedList<>();
        int size = walk.size();
        for (int i = 0; i < size; i ++) {
            int node = walk.poll();
            if (!visited[node]) {
                tspPath.add(node);
                visited[node] = true;
            }
        }

        int tspPathLength = tspPath.size();
        int currNode = tspPath.peekLast();
        for (int i = 0; i < tspPathLength; i ++) {
            int temp = tspPath.poll();
            map.setLink(temp, currNode, false);
            currNode = temp;
        }


    }

    public int[][] createMatrix(TSPMap map) {
        int[][] matrix = new int[map.getCount()][map.getCount()];
        for (int i = 0; i < map.getCount(); i ++) {
            for (int j = 0; j < map.getCount(); j ++) {
                matrix[i][j] = 0;
            }
        }
        for (int i = 0; i < map.getCount(); i ++) {
            try {
                int otherNode = map.getLink(i);
                matrix[i][otherNode] = 1;
                matrix[otherNode][i] = 1;
            } catch (IllegalArgumentException e) {
                //Nothing to do
            }
        }
        return matrix;
    }


    public void helperTSP(Integer newNode, Integer parentNode, LinkedList<Integer> walk, TSPMap map, int[][] matrix) {
        walk.add(newNode);
        for (int i = 0; i < map.getCount(); i ++) {
            if (i != parentNode && matrix[newNode][i] == 1) {
                helperTSP(i, newNode, walk, map, matrix);
            }
            walk.add(newNode);
        }
    }

    @Override
    public boolean isValidTour(TSPMap map) {
        // Note: this function should with  *any* map, and not just results from TSP().
        boolean[] visited = new boolean[map.getCount()];
        Arrays.fill(visited, false);
        int firstNode = 0;
        int count = 0;
        int currNode = 0;
        while (count < map.getCount()) {
            if (visited[currNode]) {
                return false;
            } else {
                visited[currNode] = true;
                currNode = map.getLink(currNode);
                count ++;
            }
        }
        if (currNode == firstNode) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double tourDistance(TSPMap map) {
        // Note: this function should with  *any* map, and not just results from TSP().
        if (isValidTour(map)) {
            double sum = 0;
            int count = 0;
            int currNode = 0;
            while (count < map.getCount()) {
                int nextNode = map.getLink(currNode);
                sum += map.pointDistance(currNode, nextNode);
                currNode = nextNode;
                count ++;
            }
            return sum;
         } else {
            return - 1.0;
        }
    }

    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "../hundredpoints.txt");
        TSPGraph graph = new TSPGraph();

        graph.MST(map);
        // graph.TSP(map);
        // System.out.println(graph.isValidTour(map));
        // System.out.println(graph.tourDistance(map));
    }
}
