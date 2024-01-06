import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {
    private int vertices;
    private List<Edge> edges;

    static class Edge {
        int src, dest;

        Edge(int src, int dest) {
            this.src = src;
            this.dest = dest;
        }
    }

    Graph(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
    }

    void addEdge(int src, int dest) {
        edges.add(new Edge(src, dest));
    }

    public static boolean isPrimePath(List<Integer> path, Graph graph) {
        if (path.size() >= 2 && path.get(0).equals(path.get(path.size() - 1))) {
            return true;
        } else if (reachHead(path, graph) && reachEnd(path, graph)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean reachHead(List<Integer> path, Graph graph) {
        List<Integer> formerNodes = graph.edges.stream()
                .filter(edge -> path.get(0).equals(edge.dest))
                .map(edge -> edge.src)
                .collect(Collectors.toList());

        for (Integer node : formerNodes) {
            if (!path.contains(node) || node.equals(path.get(path.size() - 1))) {
                return false;
            }
        }
        return true;
    }

    public static boolean reachEnd(List<Integer> path, Graph graph) {
        List<Integer> laterNodes = graph.edges.stream()
                .filter(edge -> path.get(path.size() - 1).equals(edge.src))
                .map(edge -> edge.dest)
                .collect(Collectors.toList());

        for (Integer node : laterNodes) {
            if (!path.contains(node) || node.equals(path.get(0))) {
                return false;
            }
        }
        return true;
    }
    List<List<Integer>> findPrimeTestPaths(int start, int end) {
        List<List<Integer>> primeTestPaths = new ArrayList<>();
        List<Integer> currentPath = new ArrayList<>();
        boolean[] visited = new boolean[vertices];

        findPrimeTestPathsUtil(start, end, visited, currentPath, primeTestPaths);

        return primeTestPaths;
    }

    private void findPrimeTestPathsUtil(int current, int end, boolean[] visited, List<Integer> currentPath, List<List<Integer>> primeTestPaths) {
        visited[current] = true;
        currentPath.add(current);

        if (current == end) {
            primeTestPaths.add(new ArrayList<>(currentPath));
        } else {
            for (Edge edge : edges) {
                if (edge.src == current && !visited[edge.dest]) {
                    findPrimeTestPathsUtil(edge.dest, end, visited, currentPath, primeTestPaths);
                }
            }
        }

        visited[current] = false;
        currentPath.remove(currentPath.size() - 1);
    }

    List<List<Integer>> findPrimeTR(int start, int end) {
        List<List<Integer>> primeTRs = new ArrayList<>();
        List<Integer> currentTR = new ArrayList<>();
        boolean[] visited = new boolean[vertices];
        List<List<Integer>> pathTR= new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {

                findPrimeTRUtil(i,j,visited,currentTR,primeTRs);
            }
        }

        for (List<Integer> path:primeTRs){
            if(isPrimePath(path,this)){
                pathTR.add(path);
            }
        }

        return pathTR;
    }

    private void findPrimeTRUtil(int current, int end, boolean[] visited, List<Integer> currentTR, List<List<Integer>> primeTRs) {
        visited[current] = true;
        currentTR.add(current);

        if (current == end) {
            primeTRs.add(new ArrayList<>(currentTR));
        } else {
            for (Edge edge : edges) {
                if (edge.src == current && !visited[edge.dest]) {
                    findPrimeTRUtil(edge.dest, end, visited, currentTR, primeTRs);
                }
            }
        }

        visited[current] = false;
        currentTR.remove(currentTR.size() - 1);
    }



    public static void main(String[] args) {
        Graph graph = new Graph(6);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(1,3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 3);
        graph.addEdge(3, 5);

        int startNode = 1;
        int endNode = 5;

        List<List<Integer>> primeTestPaths = graph.findPrimeTestPaths(startNode, endNode);
        System.out.println("Prime Test Paths:");
        for (List<Integer> path : primeTestPaths) {
            System.out.println(path);
        }

        //Boolean flag=true;
        List<List<Integer>> primeTRsNew = new ArrayList<>();
        List<List<Integer>> primeTRs = graph.findPrimeTR(startNode, endNode);

        System.out.println("\nPrime TRs:");
        for (List<Integer> tr : primeTRs) {
            System.out.println(tr);
        }
    }
}
