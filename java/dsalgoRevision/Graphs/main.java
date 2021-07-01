import java.util.*;
class main {
    static class Edge {
        int v;
        int w;
        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
        public Edge(int v) {
            this.v = v;
        }
    }

    public static void addEdge(ArrayList<Edge>[] graph, int src, int dest, int w) {
        graph[src].add(new Edge(dest, w));
        graph[dest].add(new Edge(src, w));
    }

    public static int findVtxIndex(ArrayList<Edge>[] graph, int src, int dest) {
        int idx = -1;
        for(int i = 0; i < graph[src].size(); i++) {
            Edge e = graph[src].get(i);
            if(e.v == dest) {
                idx = i;
            }
        }
        return idx;
    }

    public static void removeEdge(ArrayList<Edge>[] graph, int src, int dest) {
        int idx = 0;
        idx = findVtxIndex(graph, src, dest);
        graph[src].remove(idx);
        idx = findVtxIndex(graph, dest, src);
        graph[dest].remove(idx);
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
        addEdge(graph, 3, 4, 2);
        addEdge(graph, 4, 5, 3);
        addEdge(graph, 4, 6, 8);
        addEdge(graph, 5, 6, 3);
        return graph;
    }

    public static void display(ArrayList<Edge>[] graph) {
        for(int i = 0; i < graph.length; i++) {
            String ans = "";
            ans += i + "-> ";
            ArrayList<Edge> al = graph[i];
            for(Edge e : al) {
                ans += e.v + " ";
            }
            System.out.println(ans);
        }
    }

    public static boolean hasPath(ArrayList<Edge>[] graph, int src, int dest, boolean[] vis) {
        if(src == dest) return true;
        vis[src] = true;
        for(Edge e : graph[src]) {
            if(!vis[e.v]) {
                boolean ans = hasPath(graph, e.v, dest, vis);
                if(ans) return true;
            }
        }
        return false;
    }

    public static void printAllPaths(ArrayList<Edge>[] graph, int src, int dest, boolean[] vis, String ans) {
        if(src == dest) {
            System.out.println(ans);
            return;
        }
        vis[src] = true;
        for(Edge e : graph[src]) {
            if(!vis[e.v]) {
                printAllPaths(graph, e.v, dest, vis, ans + e.v + " ");
            }
        }
        vis[src] = false;
    }

    static class customEdge implements Comparable<customEdge>{
        int wsf;
        String psf;
        public customEdge(int wsf, String psf) {
            this.wsf = wsf;
            this.psf = psf;
        }
        public int compareTo(customEdge other) {
            return this.wsf - other.wsf;
        }
    }
    static int minCost = (int)(1e8);
    static String minCostPath;
    static int maxCost = -(int)(1e8);
    static String maxCostPath;
    static int ceilCost = (int)(1e8);
    static String ceilCostPath;
    static int floorCost = -(int)(1e8);
    static String floorCostPath;
    
    public static void multisolver(ArrayList<Edge>[] graph, int src, int dest, boolean[] vis, String psf, int wsf, int val, PriorityQueue<customEdge> pq, int k) {
        
        if(src == dest) {
            if(wsf < minCost) {
                minCost = wsf;
                minCostPath = psf;
            }
            if(wsf > maxCost) {
                maxCost = wsf;
                maxCostPath = psf;
            }
            if(wsf > val && wsf < ceilCost) {
                ceilCost = wsf;
                ceilCostPath = psf;
            }
            if(wsf < val && wsf > floorCost) {
                floorCost = wsf;
                floorCostPath = psf;
            }
            if(pq.size() < k) pq.add(new customEdge(wsf, psf));
            else {
                if(pq.peek().wsf < wsf) {
                    pq.add(new customEdge(wsf, psf));
                    pq.remove();
                }
            }
            
        }
        
        vis[src] = true;
        for(Edge e : graph[src]) {
            if(vis[e.v] == false) {
                multisolver(graph, e.v, dest, vis, psf + e.v, wsf + e.w, val, pq, k);
            }
        }
        vis[src] = false;
    } 

    public static void getCCHelper(ArrayList<Edge>[] graph, int src, boolean[] vis, ArrayList<Integer> ans) {
        vis[src] = true;
        ans.add(src);
        for(Edge e : graph[src]) {
            if(vis[e.v] == false) {
                getCCHelper(graph, e.v, vis, ans);
            }
        }
    } 

    public static void getCC(ArrayList<Edge>[] graph, int src, boolean[] vis) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for(int i = 0; i < graph.length; i++) {
            if(vis[i] == false) {
                ArrayList<Integer> ans = new ArrayList<>();
                getCCHelper(graph, i, vis, ans);
                res.add(ans);
            }
        }
        System.out.println(res);
    }

    public static boolean isConnected(ArrayList<Edge>[] graph, int src, boolean[] vis) {
        int count = 0;
        for(int i = 0; i < graph.length; i++) {
            if(vis[i] == false) {
                ArrayList<Integer> ans = new ArrayList<>();
                count++;
                getCCHelper(graph, i, vis, ans);
            }
        }
        return count == 1;
    }

    public static void countIslandsHelper(int[][] graph, int r, int c, int[][] dirs) {
        int temp = graph[r][c];
        graph[r][c] = -1;
        for(int[] d : dirs) {
            int dr = r + d[0];
            int dc = c + d[1];
            if(dr >= 0 && dc >= 0 && dr < graph.length && dc < graph[0].length && graph[dr][dc] == 0 && graph[dr][dc] != -1) {
                countIslandsHelper(graph, dr, dc, dirs);
            }
        }
    }

    public static int countIslands() {
        int[][] graph = {
            {0, 0, 1, 1, 1, 1, 1, 1},
            {0, 0, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 0},
            {1, 1, 0, 0, 0, 1, 1, 0},
            {1, 1, 1, 1, 0, 1, 1, 0},
            {1, 1, 1, 1, 0, 1, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 0}
        };
        int count = 0;
        int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        for(int i = 0; i < graph.length; i++) {
            for(int j = 0; j < graph[0].length; j++) {
                if(graph[i][j] == 0) {
                    countIslandsHelper(graph, i, j, dirs);
                    count++;
                }
            }
        }
        return count;
    }

    public static int perfectFriends(ArrayList<Edge>[] graph, boolean[] vis) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for(int i = 0; i < graph.length; i++) {
            if(vis[i] == false) {
                ArrayList<Integer> ans = new ArrayList<>();
                getCCHelper(graph, i, vis, ans);
                res.add(ans);
            }
        }
        int totalWays = 0;
        for(int i = 0; i < res.size(); i++) {
            for(int j = i + 1; j < res.size(); j++) {
                totalWays += res.get(i).size() * res.get(j).size();
            }
        }
        return totalWays;
    }

    public static void dfs(ArrayList<Edge>[] graph, int src, boolean[] vis, int count, int originalSrc, String psf) {
        if(count == graph.length - 1) {
            System.out.print(psf);
            boolean isCycle = false;
            for(Edge e : graph[originalSrc]) {
                if(e.v == src) {
                    isCycle = true;
                    break;
                }
            }
            if(isCycle) System.out.println("*");
            else System.out.println(".");
            return;
        }
        vis[src] = true;
        for(Edge e : graph[src]) {
            if(vis[e.v] == false) {
                dfs(graph, e.v, vis, count + 1, originalSrc, psf + e.v);
            }
        }
        vis[src] = false;
    }

    public static void hamiltonianPathAndCycles(ArrayList<Edge>[] graph, boolean[] vis) {
        for(int i = 0; i < graph.length; i++) {
            if(vis[i] == false) {
                int count = 0;
                dfs(graph, i, vis, 0, i, "" + i);
            }
        }
    }   

    static int[][] dirs = {{-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}};
    public static void printKnightsTour(int[][] chess, int r, int c, int move) {
        if(move == (chess.length * chess[0].length)) {
            chess[r][c] = move;
            display(chess);
            chess[r][c] = 0;
            return;
        }
        chess[r][c] = move;
        for(int[] d : dirs) {
            int dr = r + d[0];
            int dc = c + d[1];
            if(dr >= 0 && dc >= 0 && dr < chess.length && dc < chess[0].length && chess[dr][dc] == 0) {
                printKnightsTour(chess, dr, dc, move + 1);
            }
        }
        chess[r][c] = 0;
    }

    public static void display(int[][] chess) {
        for(int[] arr : chess) {
            for(int val : arr) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // bfs (level order in tree)
    // remove, mark(extra step), print, add children
    
    // it will give nodes at 1 distance, 2, 3 and so on............

    static class pair implements Comparable<pair>{
        int src;
        int level;
        int wsf;
        String psf;
        int acqVtx;
        public pair(int src, int level) {
            this.src = src;
            this.level = level;
        }
        public pair(int src, String psf) {
            this.src = src;
            this.psf = psf;
        } 
        public pair(int src, int wsf, String psf) {
            this.src = src;
            this.wsf = wsf;
            this.psf = psf;
        }
        public pair(int src, int wsf, int acqVtx) {
            this.src = src;
            this.wsf = wsf;
            this.acqVtx = acqVtx;
        }
        public int compareTo(pair o) {
            return this.wsf - o.wsf;
        }
    }

    public static boolean bfs(ArrayList<Edge>[] graph, boolean[] vis, int src) {
        Queue<pair> qu = new LinkedList<>();
        int totalCycles = 0;
        qu.add(new pair(src, 0));
        // int level = 1;
        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                pair cp = qu.remove();
                if(vis[cp.src] == true) {
                    // to detect cycle
                    totalCycles++;
                }
                int level = cp.level;
                vis[cp.src] = true;
                // System.out.print(cp.src + " @ " + cp.level + " , ");
                for(Edge e : graph[cp.src]) {
                    if(vis[e.v] == false) {
                        qu.add(new pair(e.v, level + 1));
                    }
                }
            }
            // level++;
            // System.out.println();
        }
        return totalCycles > 0;
    }

    // using bfs
    public static void isGraphCyclic(ArrayList<Edge>[] graph, boolean[] vis) {
        boolean ans = false;
        for(int i = 0; i < graph.length; i++) {
            if(vis[i] == false) {
                ans = ans || bfs(graph, vis, 0);
            }
        }
        System.out.println(ans);
    }

    public static boolean bipartiteHelper(ArrayList<Edge>[] graph, int[] vis, int src) {
        Queue<pair> qu = new LinkedList<>();
        qu.add(new pair(src, 0));
        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                pair cp = qu.remove();
                if(vis[cp.src] != -1 && vis[cp.src] != cp.level) return false;
                else vis[cp.src] = cp.level;
                for(Edge e : graph[cp.src]) {
                    if(vis[e.v] == -1) {
                        qu.add(new pair(e.v, cp.level + 1));
                    }
                } 
            }
        }
        return true;
    }
    
    public static boolean isBipartite(ArrayList<Edge>[] graph) {
        int[] vis = new int[graph.length + 1];
        for(int i = 0; i < vis.length; i++) vis[i] = -1; // set level to -1;
        for(int i = 0; i < graph.length; i++) {
            if(vis[i] == -1) {
                if(bipartiteHelper(graph, vis, i) == false) return false; 
            }
        }
        return true;
    }

    public static void spreadInfection(ArrayList<Edge>[] graph, int src, int ti, int tf) {
        int[] vis = new int[graph.length + 1];
        for(int i = 0; i < vis.length; i++) vis[i] = -1;
        int countOfPeople = 0; 
        Queue<pair> qu = new LinkedList<>();
        qu.add(new pair(src, ti));
        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                pair cp = qu.remove();
                // level is the time here
                if(vis[cp.src] != -1) continue; 
                vis[cp.src] = cp.level;
                if(cp.level > tf) break;
                countOfPeople++;
                for(Edge e : graph[cp.src]) {
                    if(vis[e.v] == -1) {
                        qu.add(new pair(e.v, cp.level + 1));
                    }
                }
            }
        }
        System.out.println(countOfPeople);
    }

    // dijkstra's algo
    public static void dijkstrasAlgo(ArrayList<Edge>[] graph, int src, boolean[] vis) {
        PriorityQueue<pair> pq = new PriorityQueue<>();
        pq.add(new pair(src, 0, "" + src));
        while(pq.size() > 0) {
            int size = pq.size();
            while(size-- > 0) {
                pair cp = pq.remove();
                if(vis[cp.src] == true) continue;
                vis[cp.src] = true;
                System.out.println(cp.src + " via " + cp.psf + " @ " + cp.wsf);
                for(Edge e : graph[cp.src]) {
                    if(vis[e.v] == false) pq.add(new pair(e.v, cp.wsf + e.w, cp.psf + e.v));
                }
            }
        }
    }

    // MST : prims algo

    public static void prims(ArrayList<Edge>[] graph, int src, boolean[] vis) { 
        PriorityQueue<pair> pq = new PriorityQueue<>();
        pq.add(new pair(src, 0, -1));
        while(pq.size() > 0) {
            int size = pq.size();
            while(size-- > 0) {
                pair cp = pq.remove();
                if(vis[cp.src] == true) continue;
                vis[cp.src] = true;
                if(cp.acqVtx != -1) {
                    System.out.println(cp.src + " via " + cp.acqVtx + " @ " + cp.wsf);
                }
                for(Edge e : graph[cp.src]) {
                    if(vis[e.v] == false) {
                        pq.add(new pair(e.v, e.w, cp.src));
                    }
                }
            }
        }
    }

    // when the graph is linear you need this.

    public static void iterativeDfs(ArrayList<Edge>[] graph, int src, boolean[] vis) {
        Stack<pair> st = new Stack<>();
        st.push(new pair(src, "" + src));
        while(st.size() > 0) {
            int size = st.size();
            while(size-- > 0) {
                pair cp = st.pop();
                if(vis[cp.src]) continue;
                vis[cp.src] = true;
                System.out.println(cp.src + " @ " + cp.psf);
                for(Edge e : graph[cp.src]) {
                    if(vis[e.v] == false) {
                        st.push(new pair(e.v, cp.psf + e.v));
                    }
                }
            }
        }
    }

    public static void solve() {
        ArrayList<Edge>[] graph = construct();
        int src = 0;
        int dest = 6;
        boolean[] vis = new boolean[graph.length];
        isBipartite(graph);
    }
     public static void main(String args[]) {
        solve();
    }
}
