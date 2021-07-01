import java.util.*;

class ap {

    public static ArrayList<Integer>[] construct() {
        int n = 21;
        ArrayList<Integer>[] graph = new ArrayList[n];
        for(int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        addEdge(graph, 0, 1);
        addEdge(graph, 0, 5);
        addEdge(graph, 0, 6);
        addEdge(graph, 1, 2);
        addEdge(graph, 1, 6);
        addEdge(graph, 2, 3);
        addEdge(graph, 3, 4);
        addEdge(graph, 3, 15);
        addEdge(graph, 4, 5);
        addEdge(graph, 4, 9);
        addEdge(graph, 6, 7);
        addEdge(graph, 7, 8);
        addEdge(graph, 8, 6);
        addEdge(graph, 9, 10);
        addEdge(graph, 9, 11);
        addEdge(graph, 9, 12);
        addEdge(graph, 12, 13);
        addEdge(graph, 12, 14);
        addEdge(graph, 15, 16);
        addEdge(graph, 15, 17);
        addEdge(graph, 15, 18);
        addEdge(graph, 15, 19);
        addEdge(graph, 15, 20);
        addEdge(graph, 19, 20);

        return graph;
    }

    public static void addEdge(ArrayList<Integer>[] graph, int src, int dest) {
        graph[src].add(dest);
        graph[dest].add(src);
    }
    
    static int[] lowTime;
    static int[] dfsTime;
    static boolean[] vis;
    static boolean[] apPoint;
    static int rootcount = 0, time = 0;

    public static void dfs(ArrayList<Integer>[] graph, boolean[] vis, int src, int par) {
        lowTime[src] = dfsTime[src] = time++;
        vis[src] = true;
        
        for(int e : graph[src]) {
            if(vis[e] == false) {
                
                if(par == -1) {
                    rootcount++;
                }

                dfs(graph, vis, e, src);

                // this src will try to remove itself
                if(dfsTime[src] <= lowTime[e]) {
                    apPoint[src] = true;
                }

                if(dfsTime[src] < lowTime[e]) {
                    System.out.println("Ap bridge -> " + src + " " + e);
                }

                lowTime[src] = Math.min(lowTime[src], lowTime[e]);

            } else if(e != par) {
                lowTime[src] = Math.min(lowTime[src], dfsTime[e]);
            }
        } 
    }


    public static void getArticulationPoints(ArrayList<Integer>[] graph) {
        
        vis = new boolean[graph.length];
        apPoint = new boolean[graph.length];
        dfsTime = new int[graph.length];
        lowTime = new int[graph.length];

        for(int i = 0; i < graph.length; i++) {
            if(vis[i] == false) {
                dfs(graph, vis, i, -1);

                if(rootcount == 1) {
                    apPoint[i] = false;
                } 
            }
        }

        for(int i = 0; i < apPoint.length; i++) {
            if(apPoint[i]) System.out.print(i + " ");
        }
    }

    public static void solve() {
        ArrayList<Integer>[] graph = construct();
        getArticulationPoints(graph);
    }
    
    public static void main(String args[]) {
        solve();
    }
}
