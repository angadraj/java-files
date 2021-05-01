import java.util.*;

import javax.script.ScriptContext;
class main2 {
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
        graph[dest].add(new Edge(src, w));
    }

    public static ArrayList<Edge>[] construct() {
        int vtices = 9;
        ArrayList<Edge>[] graph = new ArrayList[vtices];
        for(int i = 0; i < graph.length; i++) graph[i] = new ArrayList<>();
        // addEdge(graph, 0, 1, 10);
        // addEdge(graph, 0, 3, 25);
        // addEdge(graph, 1, 2, 10);
        // addEdge(graph, 2, 3, 10);
        // // addEdge(graph, 2, 5, 100);
        // addEdge(graph, 3, 4, 2);
        // addEdge(graph, 4, 5, 3);
        // addEdge(graph, 4, 6, 8);
        // addEdge(graph, 5, 6, 3);

        addEdge(graph, 8, 0, 4);
        addEdge(graph, 8, 4, 8);
        addEdge(graph, 0, 3, 2);
        addEdge(graph, 3, 4, 1);
        addEdge(graph, 0, 1, 3);
        addEdge(graph, 3, 2, 6);
        addEdge(graph, 1, 7, 4);
        addEdge(graph, 2, 7, 2);
        addEdge(graph, 2, 5, 1);
        addEdge(graph, 5, 6, 8);
        return graph;
    }

    public static void display(ArrayList<Edge>[] graph) {
        for(int i = 0; i < graph.length; i++) {
            ArrayList<Edge> al = graph[i];
            System.out.print(i + "-> ");
            for(Edge e : al) {
                System.out.print(e.v + " ");
            }
            System.out.println();
        }
    }

    static class pair implements Comparable<pair>{
        int wsf;
        String psf;
        int src;
        int par;
        int wt;
        public pair(int wsf, String psf) {
            this.wsf = wsf;
            this.psf = psf;
        }
        public pair(int src, int par, int wt) {
            this.src = src;
            this.par = par;
            this.wt = wt;
        }
        public pair(int src, int wsf, String psf) {
            this.src = src;
            this.psf = psf;
            this.wsf = wsf;
        }
        public int compareTo(pair o) {
            return this.wsf - o.wsf;
        }
    }

    public static pair largeCostDfs(ArrayList<Edge>[] graph, int src, int dest, boolean[] vis) {
        if(src == dest) return new pair(0, "" + src);
        vis[src] = true;
        pair ans = new pair(0, "" + src);
        for(Edge e : graph[src]) {
            if(vis[e.v] == false) {
                pair cp = largeCostDfs(graph, e.v, dest, vis);
                if(ans.wsf < cp.wsf + e.w) {
                    ans.wsf = cp.wsf + e.w;
                    ans.psf = src + cp.psf + "";
                }
            } 
        }
        if(ans.wsf == 0) ans.wsf = -(int)(1e8);
        vis[src] = false;
        return ans;
    }

    public static int dfs(ArrayList<Edge>[] graph, int src, boolean[] vis) {
        vis[src] = true;
        int count = 1;
        for(Edge e : graph[src]) {
            if(vis[e.v] == false) {
                count += dfs(graph, e.v, vis);
            }
        }
        return count;
    }

    public static int getCC(ArrayList<Edge>[] graph, boolean[] vis) {
        int cc = 0;
        int area = -(int)(1e8);
        for(int i = 0; i < graph.length; i++) {
            if(vis[i] == false) {
                cc++;
                area = Math.max(area, dfs(graph, i, vis));
            }
        }
        // area in terms of vertices
        return area;
    }

    public static void prims(ArrayList<Edge>[] graph, int src, boolean[] vis) {
        ArrayList<Edge>[] primsgraph = new ArrayList[graph.length];
        for(int i = 0; i < primsgraph.length; i++) primsgraph[i] = new ArrayList<>();
        PriorityQueue<pair> pq = new PriorityQueue<>();
        pq.add(new pair(src, -1, 0));
        while(pq.size() > 0) {
            int size = pq.size();
            while(size-- > 0) {
                pair cp = pq.remove();
                if(vis[cp.src]) continue;
                if(cp.par != -1) {
                    addEdge(primsgraph, cp.src, cp.par, cp.wt);
                }
                vis[cp.src] = true;
                for(Edge e : graph[cp.src]) {
                    if(vis[e.v] == false) {
                        pq.add(new pair(e.v, cp.src, e.w));
                    }
                }
            }
        }
        display(primsgraph);
    }

    //if u want to make a graph then add parent vtx in ur pair
    public static void dijkstras(ArrayList<Edge>[] graph, int src, boolean[] vis) {
       PriorityQueue<pair> pq = new PriorityQueue<>();
       pq.add(new pair(src, 0, "" + src));
       while(pq.size() > 0) {
           int size = pq.size();
           while(size-- > 0) {
               pair cp = pq.remove();
               if(vis[cp.src] == true) continue;
               vis[cp.src] = true;
               System.out.println(cp.psf + " @ " + cp.wsf);
               for(Edge e : graph[cp.src]) {
                   if(vis[e.v] == false) {
                       pq.add(new pair(e.v, cp.wsf + e.w, cp.psf + e.v + ""));
                   }
               }
           }
       }
    }

    public static void solve() {
        ArrayList<Edge>[] graph = construct();
        boolean[] vis = new boolean[graph.length];
        int src = 0;
        int dest = 6;
        dijkstras(graph, src, vis);
    }
    public static void main(String args[]) {
        solve();
    }
}
