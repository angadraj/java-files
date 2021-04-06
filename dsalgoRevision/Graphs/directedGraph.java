import java.util.*;
class directedGraph {
    static class Edge {
        int v;
        int w;
        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }

    public static void addEdge(ArrayList<Edge>[] graph, int src, int dest, int w) {
        graph[src].add(new Edge(dest, w));
    }

    public static ArrayList<Edge>[] construct() {   
        int vtices = 7;
        ArrayList<Edge>[] graph = new ArrayList[vtices];
        for(int i = 0; i < graph.length; i++) graph[i] = new ArrayList<>();
        addEdge(graph, 0, 1, 10);
        addEdge(graph, 0, 3, 25);
        addEdge(graph, 1, 2, 10);
        addEdge(graph, 2, 3, 10);
        // addEdge(graph, 2, 5, 100);
        addEdge(graph, 4, 3, 2);
        addEdge(graph, 4, 5, 3);
        addEdge(graph, 4, 6, 8);
        addEdge(graph, 5, 6, 3);
        return graph;
    }

    public static void dfs(ArrayList<Edge>[] graph, boolean[] vis, int src, Stack<Integer> st) {
        vis[src] = true;
        for(Edge e : graph[src]) {
            if(vis[e.v] == false) {
                dfs(graph, vis, e.v, st);
            }
        }
        st.push(src);
    }

    public static void TopologicalSort(ArrayList<Edge>[] graph, boolean[] vis) {
        Stack<Integer> st = new Stack<>();
        for(int i = 0; i < graph.length; i++) {
            if(vis[i] == false) {
                dfs(graph, vis, i, st);
            }
        }
        while(st.size() > 0) {
            int val = st.pop();
            System.out.print(val + " ");
        }
    }

    public static void solve() {
        ArrayList<Edge>[] graph = construct();
        int src = 0;
        boolean[] vis = new boolean[graph.length];
        TopologicalSort(graph, vis);
    }

    public static void main(String args[]) {
        solve();
    }
}
