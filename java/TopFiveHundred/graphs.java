import java.util.*;
import java.io.*;
class graphs {
    
    // Roads and Libraries
    public static int dfs(ArrayList<Integer>[] graph, int src, boolean[] vis) {
        vis[src] = true;
        int self = 0;
        for (int nbr: graph[src]) {
            if (vis[nbr] == false) {
                self += dfs(graph, nbr, vis);
            }
        }
        return self + 1;
    }
    
    public static long roadAndLib(int n, int libCost, int roadCost, List<List<Integer>> arr) {
        // 1. construct graph
        ArrayList<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        for (List<Integer> a: arr) {
            graph[a.get(0)].add(a.get(1));
            graph[a.get(1)].add(a.get(0));
        }
        // 2. find component size
        boolean[] vis = new boolean[graph.length + 1];
        ArrayList<Integer> size = new ArrayList<>();
        for (int i = 1; i < graph.length; i++) {
            if (vis[i] == false) {
                int count = dfs(graph, i, vis);
                size.add(count);
            }
        }
        long min = 0;
        for (int val: size) {
            int nLib = libCost * val;
            int oneLibRemRoads = libCost + ((val - 1) * roadCost);
            min += Math.min(nLib, oneLibRemRoads);
        }
        return min;
    }

    // journey to the moon
    public static long journeyToMoon(int n, List<List<Integer>> arr) {
        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        for (List<Integer> a: arr) {
            graph[a.get(0)].add(a.get(1));
            graph[a.get(1)].add(a.get(0));
        }
        boolean[] vis = new boolean[graph.length];
        ArrayList<Integer> size = new ArrayList<>();
        for (int i = 1; i < graph.length; i++) {
            if (vis[i] == false) {
                int count = dfs(graph, i, vis);
                size.add(count);
            }
        }
        int totalCombos = (n * (n - 1)) / 2;
        for (int val: size) {
            int x = (val * (val - 1)) / 2;
            totalCombos -= x;
        }
        return totalCombos;
    }

    // max edges that can be removed from tree such that forest has event number
    // of nodes in it
    static int maxEdges = 0;
    public static int removeEdges(int n, int[][] arr) {
        ArrayList<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] a: arr) {
            graph[a[0]].add(a[1]);
            graph[a[1]].add(a[0]);
        }
        boolean[] vis = new boolean[n + 1];
        removeEdges_helper(graph, 1, vis);
        return maxEdges;
    }

    public static int removeEdges_helper(ArrayList<Integer>[] graph, int src, boolean[] vis) {
        vis[src] = true;
        int count = 0;
        for (int nbr: graph[src]) {
            if (vis[nbr] == false) {
                int ans = removeEdges_helper(graph, nbr, vis);
                if ((ans & 1) == 0) maxEdges++;
                count += ans;
            }
        }
        return count + 1;
    }

    // 210. Course Schedule II
    // in this dfs solution can not work in case of cycle or deadlock
    // kahn's algo works in all cases
    public static int[] findOrder(int n, int[][] arr) {
        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] a: arr) {
            graph[a[1]].add(a[0]);
        }
        int[] indeg = new int[n];
        for (int i = 0; i < n; i++) {
            for (int nbr: graph[i]) {
                indeg[nbr]++;
            }
        }
        Queue<Integer> qu = new LinkedList<>();
        for (int i = 0; i < indeg.length; i++) {
            if (indeg[i] == 0) qu.add(i);
        }
        int[] ans = new int[n];
        int idx = 0;
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                int rv = qu.remove();
                ans[idx++] = rv;

                for (int nbr: graph[rv]) {
                    indeg[nbr]--;
                    if (indeg[nbr] == 0) qu.add(nbr);
                }
            }
        }
        if (idx != n) return new int[0];
        return ans;
    }

    public static void findOrder2(int n, int[][] arr) {
        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] a: arr) {
            graph[a[1]].add(a[0]);
        }
        Stack<Integer> st = new Stack<>();
        boolean[] vis = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (vis[i] == false) {
                topologicalSort_dfs(graph, st, vis, i);
            }
        }
        while (st.size() > 0) {
            System.out.print(st.pop() + " ");
        }
        // return new int[0];
    }

    public static void topologicalSort_dfs(ArrayList<Integer>[] graph, Stack<Integer> st, boolean[] vis, int src) {
        vis[src] = true;
        for (int nbr: graph[src]) {
            if (vis[nbr] == false) {
                topologicalSort_dfs(graph, st, vis, nbr);
            }
        }
        st.push(src);
    }

    // Mancunian And Liverbird Go Bar Hopping
    static int[] a = new int[1000005];
    static int[] a1 = new int[1000005];
    static int[] a2 = new int[1000005];
    static int[] a3 = new int[1000005];
    static int[] a4 = new int[1000005];

    public static void barHopping() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n - 1; i++) a[i] = sc.nextInt();

        barHelper(n);

        int Q = sc.nextInt();
        sc.nextLine();

        ArrayList<Integer> ans = new ArrayList<>();
        int totalUpdates = 0;
        while (Q-- > 0) {
            String s = sc.nextLine();
            char ch = s.charAt(0);
            if (ch == 'U') totalUpdates++;
            else {
                int pos = Character.getNumericValue(s.charAt(2));
                if (totalUpdates % 2 == 0) {
                    // System.out.print(a1[pos - 1] + a2[pos - 1] + 1);
                    ans.add(a1[pos - 1] + a2[pos - 1] + 1);
                } else {
                    // System.out.print(a3[pos - 1] + a4[pos - 1] + 1);
                    ans.add(a3[pos - 1] + a4[pos - 1] + 1);
                }
            }
        }
        System.out.println(ans);
    }

    public static void barHelper(int n) {
        int x = 0; // how many bars can we reach
        a1[n - 1] = 0; // a1 is "->" on 1
        for (int i = n - 2; i >= 0; i--) {
            if (a[i] == 1) x++;
            else x = 0;
            a1[i] = x;
        }
        x = 0;
        a2[0] = 0; // a2 is "<-" on 0
        for (int i = 1; i < n; i++) {
            if (a[i - 1] == 0) x++;
            else x = 0;
            a2[i] = x;
        }
        // when graph is reversed
        x = 0; // how many bars can we reach
        a3[n - 1] = 0; // a3 is "->" on 0
        for (int i = n - 2; i >= 0; i--) {
            if (a[i] == 0) x++;
            else x = 0;
            a3[i] = x;
        }
        x = 0;
        a4[0] = 0; // a2 is "<-" on 1
        for (int i = 1; i < n; i++) {
            if (a[i - 1] == 1) x++;
            else x = 0;
            a4[i] = x;
        }
    }

    // cheapest flights within k stops
    static class Edge {
        int u, v, w;
        public Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    static class dijPair {
        int dest, k, cost;
        public dijPair(int dest, int cost, int k) {
            this.dest = dest;
            this.k = k;
            this.cost = cost;
        }
    }

    public static int cheapestFlightWithinKStops(int n, int[][] arr, int k, int S, int D) {
        ArrayList<Edge>[] graph = new ArrayList[n];
        for (int i = 0; i < graph.length; i++) graph[i] = new ArrayList<>();
        for (int[] a: arr) {
            graph[a[0]].add(new Edge(a[0], a[1], a[2]));
        }

        PriorityQueue<dijPair> pq = new PriorityQueue<>((t, o) -> {
            return t.cost - o.cost;
        });
        // we need to regulate k over all stops so we will pass it as a parameter
        // to control states at all stops
        int[] cost = new int[n];
        Arrays.fill(cost, (int)(1e8));
        pq.add(new dijPair(S, 0, k + 1));

        while (pq.size() > 0) {
            int size = pq.size();
            while (size-- > 0) {
                dijPair e = pq.remove();
                if (e.k < 0) continue;
                if (e.dest == D) return e.cost;
                // nbrs
                for (Edge nbr: graph[e.dest]) {
                    if (e.cost + nbr.w < cost[nbr.v]) {
                        pq.add(new dijPair(nbr.v, e.cost + nbr.w, e.k - 1));
                    }
                }
            }
        }
        return -1;
    }

    // dfs + dp
    public static int cheapestFightsKStops(int n, int[][] arr, int src, int dest, int k) {
        List<List<List<Integer>>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (int[] a: arr) {
            graph.get(a[0]).add(Arrays.asList(a[1], a[2]));
        }
        int price = cheapestPriceDfs(graph, src, dest, k + 1, new Integer[n + 1][k + 2]);
        return (price == (int)(1e8) ? -1 : price);
    }

    public static int cheapestPriceDfs(List<List<List<Integer>>> graph, int src, int dest, int k, Integer[][] dp) {
        if (src == dest) {
            if (k >= 0) return 0; // k >= 0 because we can reach a dest with less than k steps,
            // so at that point it is not necassary that k will be 0;
            else return (int)(1e8);
        } else if (k < 0) return (int)(1e8);

        if (dp[src][k] != null) return dp[src][k];

        int ans = (int)(1e8);
        for (List<Integer> e: graph.get(src)) {
            int nbr = e.get(0);
            int cost = e.get(1);
            int recAns = cheapestPriceDfs(graph, nbr, dest, k - 1, dp);
            if (recAns != (int)(1e8)) ans = Math.min(ans, recAns + cost);
        }
        return dp[src][k] = ans;
    }

    // Dhoom 4
    public static int dhoom4(int[] arr, int src, int dest) {
        Queue<Long> qu = new LinkedList<>();
        int[] vis = new int[100001];
        Arrays.fill(vis, -1);
        vis[src] = 0;
        int mod = 100000;
        qu.add((long)src);
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                long curr = qu.remove();
                if (curr == dest) break;
                for (int i = 0; i < arr.length; i++) {
                    long product = ((curr % mod) * (arr[i] % mod)) % mod ;
                    if (vis[(int)product] == -1) {
                        qu.add(product);
                        vis[(int)product] = vis[(int)curr] + 1;
                    }
                }   
            }
        }
        return vis[dest];
    }

    // monks and bridges
    public static int monksAndBridges(int src, int dest, int[][] arr) {
        // as dest == n
        ArrayList<Integer>[] graph = new ArrayList[dest + 1];
        for (int i = 1; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] a: arr) {
            graph[a[0]].add(a[1]);
            graph[a[1]].add(a[0]);
        }

        Queue<int[]> qu = new LinkedList<>();  
        // src, count      
        qu.add(new int[]{src, 0});

        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                int[] cp = qu.remove();
                if (cp[0] == dest) {
                    return cp[1];
                }
                // add nbrs
                for (int nbr: graph[cp[0]]) {
                    qu.add(new int[]{nbr, cp[1] + 1});
                }
            }
        }
        return -1;
    }

    // connected horses
    static int mod = (int)(1e9) + 7;
    public static long connectedHorses(int[][] chess) {
        int n = chess.length, m = chess[0].length;

        long[] fact = new long[1000003];
        fact[0] = 1;
        for (int i = 1; i < fact.length; i++) fact[i] = (fact[i - 1] * i) % mod;

        boolean[][] vis = new boolean[n][m];
        int[][] dirs = {{-2, -1}, {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {-1, -2}, {1, -2}};

        long ans = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (chess[i][j] == 1 && !vis[i][j]) {
                    long count = connectedHorses_bfs(chess, vis, dirs, i, j);
                    ans = (ans * fact[(int)count]) % mod;
                }
            }
        }
        return ans;
    }

    public static long connectedHorses_bfs(int[][] chess, boolean[][] vis, int[][] dirs, int r, int c) {
        Queue<Integer> qu = new LinkedList<>();
        int n = chess.length, m = chess[0].length;
        qu.add(r * m + c);
        long count = 1;
        vis[r][c] = true;
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                int cord = qu.remove();
                int sr = cord / m;
                int sc = cord % m;

                for (int[] d: dirs) {
                    int dr = sr + d[0];
                    int dc = sc + d[1];
                    if (dr >= 0 && dc >= 0 && dr < chess.length && dc < chess[0].length && chess[dr][dc] == 1 && !vis[dr][dc]) {
                        qu.add(dr * m + dc);
                        vis[dr][dc] = true;
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // weAreOnFire
    // same as spread infection
    static int nations = 0;
    public static void weAreOnFire(int[][] arr, int[][] dirs, int r, int c) {
        // check if attack is made on water
        if (arr[r][c] == 0) return;
        
        int n = arr.length, m = arr[0].length;
        Queue<Integer> qu = new LinkedList<>();
        qu.add(r * m + c);
        // as attack is initiated on a nation, destroy it and start destroying adjacent ones
        arr[r][c] = 0;
        nations -= 1;

        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                int cord = qu.remove();
                int sr = cord / m;
                int sc = cord % m;

                for (int[] d: dirs) {
                    int dr = sr + d[0];
                    int dc = sc + d[1];
                    if (dr >= 0 && dc >= 0 && dr < n && dc < m && arr[dr][dc] == 1) {
                        // we can destroy only adjacent nations
                        arr[dr][dc] = 0;
                        nations -= 1;
                        qu.add(dr * m + dc);
                    }
                }
            }
        }
    }

    // mrinal and 3 musketeers
    public static void mrinalAndMusksInit() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        // aux
        ArrayList<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();
        int[] indeg = new int[n + 1];
        boolean[][] vis = new boolean[n + 1][n + 1];
        // 
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            graph[u].add(v);
            graph[v].add(u);

            vis[u][v] = true;
            vis[v][u] = true;

            indeg[u]++;
            indeg[v]++;
        }
        int ans = mrinalAndMusks(graph, indeg, vis);
        System.out.println(ans);
    }

    public static int mrinalAndMusks(ArrayList<Integer>[] graph, int[] indeg, boolean[][] vis) {
        int res = (int)(1e8), n = graph.length;
        for (int i = 1; i < n; i++) {
            if (graph[i].size() > 1) {
                // why > 1, as all musks need to know each other
                for (int j = 1; j < n; j++) {
                    if (i != j && graph[j].size() > 1 && vis[i][j]) {
                        for (int k = 1; k < n; k++) {
                            if (k != i && k != j && graph[k].size() > 1 && vis[k][i] && vis[k][j]) {
                                int ans = (indeg[i] - 2) + (indeg[j] - 2) + (indeg[k] - 2);
                                res = Math.min(ans, res);
                            }
                        }
                    }
                }
            }
        }
        if (res == (int)(1e8)) return -1;
        return res;
    }

    // mr president
    static class dsu {
        static int[] par;
        static int[] size;

        public dsu(int n) {
            par = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                par[i] = i;
            }
            Arrays.fill(size, 1);
        }

        public int findPar(int u) {
            if (u == par[u]) {
                return u;
            }
            // my parent
            par[u] = findPar(par[u]);
            return par[u];
        }

        public void union(int u, int v) {
            int pu = findPar(u);
            int pv = findPar(v);
            if (pu == pv) return;
            if (size[pu] < size[pv]) {
                par[pu] = pv;
                size[pv] += size[pu];
            } else {
                par[pv] = pu;
                size[pu] += size[pv];
            }
        }
    }

    public static void mrPresidentInit() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        long k = sc.nextLong();
        if (k < n - 1) {
            // here n - 1 denotes the mst. Because if k will be less tham
            // that, it will mean that we have to remove one edge from 
            // mst which is not possible
            System.out.println("-1");
            return;
        }
        // aux
        PriorityQueue<int[]> pq = new PriorityQueue<>((t, o) -> {
            return t[2] - o[2];
        });
        dsu obj = new dsu(n + 1);
        //
        while (m-- > 0) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            pq.add(new int[]{u, v, w});
        }

        long totalMstCost = 0;
        // i just need to store cost, so I can do it int 0 based indexing
        int[] cost = new int[n - 1];
        int idx = 0;

        while (pq.size() > 0 && idx < n - 1) {
            int[] cp = pq.remove();
            int u = cp[0], v = cp[1], w = cp[2];
            
            int pu = obj.findPar(u), pv = obj.findPar(v);
            if (pu != pv) {
                obj.union(pu, pv);
                totalMstCost += w;
                cost[idx++] = w;
            }
        }

        // check if mst covers complete graph
        // if total n nodes are there then in mst n - 1 edges will be there
        if (idx != n - 1) {
            System.out.println("-1");
            return;
        }

        // now check how many roads we can convert to super roads
        long count = 0;
        while (totalMstCost > k) {
            // we are starting from back to process big costs first
            totalMstCost -= cost[idx - 1];
            totalMstCost += 1;
            // superRoad = w + 1;
            idx--;
            count++;
        }

        System.out.println(count);
    }

    // Chocolate journey
    public static void chocolateJourney() throws IOException {
        // Scanner sc = new Scanner(System.in);
        Reader sc = new Reader();
        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();
        int x = sc.nextInt();

        boolean[] cities = new boolean[n + 1];
        for (int i = 0; i < k; i++) {
            int c = sc.nextInt();
            cities[c] = true;
        }
        // aux
        ArrayList<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        //
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            graph[u].add(new int[]{v, w});
            graph[v].add(new int[]{u, w});
        }
        // positon of friend and mine 
        int src = sc.nextInt();
        int dest = sc.nextInt();
        // process
        int[] frndToAll = new int[n + 1];
        int[] meToAll = new int[n + 1];
        Arrays.fill(frndToAll, (int)(1e9));
        Arrays.fill(meToAll, (int)(1e9));
        // we will find single sourse shortest path to all vertices
        // src is friend and dest is me
        dijkstras(graph, src, frndToAll);
        dijkstras(graph, dest, meToAll);

        int ans = (int)(1e9);
        for (int i = 1; i <= n; i++) {
            // we will consider chocolate cities
            if (cities[i]) {
                // that city must can be visited from frnd and then dist of that city till me should be
                // less than expiry time
                if (meToAll[i] < x && frndToAll[i] != (int)(1e9)) {
                    // total time: 
                    ans = Math.min(ans, frndToAll[i] + meToAll[i]);
                }
            }
        }
        if (ans != (int)(1e9)) System.out.println(ans);
        else System.out.println(-1);
    }

    public static void dijkstras(ArrayList<int[]>[] graph, int src, int[] dis) {
        int n = graph.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((t, o) -> {
            return t[1] - o[1];
        });

        pq.add(new int[]{src, 0});
        dis[src] = 0;

        while (pq.size() > 0) {
            int size = pq.size();
            while (size-- > 0) {
                int[] cp = pq.remove();
                if (cp[1] > dis[cp[0]]) continue;

                for (int[] e: graph[cp[0]]) {
                    if (e[1] + cp[1] < dis[e[0]]) {
                        dis[e[0]] = e[1] + cp[1];
                        pq.add(new int[]{e[0], dis[e[0]]});
                    }
                }
            }
        }
    }

    // minimizing path cost
    // given Q (src, dest) we have to o/p the minm distance between them
    static class stringEdge {
        String u;
        int w;  
        public stringEdge(String u, int w) {
            this.u = u;
            this.w = w;
        }
    }

    public static void minimizingCost() throws IOException {
        Reader sc = new Reader();
        int n = sc.nextInt();
        int m = sc.nextInt();

        String[] cities = sc.readLine().split(" ");

        HashMap<String, ArrayList<stringEdge>> graph = new HashMap<>();
        // src -> [dest, distance]
        HashMap<String, HashMap<String, Integer>> map = new HashMap<>();

        for (int i = 0; i < m; i++) {
            String[] s = sc.readLine().split(" ");
            String u = s[0], v = s[1];
            int w = Integer.parseInt(s[2]);

            if (!graph.containsKey(u)) {
                graph.put(u, new ArrayList<>());
            }
            if (!graph.containsKey(v)) {
                graph.put(v, new ArrayList<>());
            }
            graph.get(u).add(new stringEdge(v, w));
            graph.get(v).add(new stringEdge(u, w));
        }
        //
        for (String val: cities) {
            if (map.containsKey(val) == false) {
                map.put(val, new HashMap<>());
            }
            minimizingCost_helper(val, graph, map, cities);
        }
        //
        int q = sc.nextInt();
        while (q-- > 0) {
            String[] s = sc.readLine().split(" ");
            String src = s[0], dest = s[1];
            int ans = map.get(src).get(dest);
            System.out.println(ans);
        }   
    }

    public static void minimizingCost_helper(String src, HashMap<String, ArrayList<stringEdge>> graph, HashMap<String, HashMap<String, Integer>> map, String[] cities) {

        PriorityQueue<stringEdge> pq = new PriorityQueue<>((t, o) -> {
            return t.w - o.w;
        });

        HashMap<String, Integer> dis = new HashMap<>();
        for (String val: cities) {
            if (val.equals(src)) {
                dis.put(val, 0);
            } else {
                dis.put(val, (int)(1e9));
            }
        }

        pq.add(new stringEdge(src, 0));

        while (pq.size() > 0) {
            // cp : u, w
            stringEdge cp = pq.remove();

            if (cp.w > dis.get(cp.u)) continue;

            for (stringEdge e: graph.get(cp.u)) {
                int newCost = e.w + cp.w;
                if (newCost < dis.get(e.u)) {
                    dis.put(e.u, newCost);
                    pq.add(new stringEdge(e.u, newCost));
                }
            }
        }
        // collect all min cost from original src to all points
        // HashMap<String, HashMap<String, Integer>> map = new HashMap<>();
        HashMap<String, Integer> minCostMap = map.get(src);

        for (String key: dis.keySet()) {
            int cost = dis.get(key);
            minCostMap.put(key, cost);
            // System.out.println(key + " -> " + cost);
        }
        // System.out.println("**************");
    }

    // oliver and the game
    // euler tree concept
    public static void oliverAndGame() throws IOException {
        Reader sc = new Reader();
        int n = sc.nextInt();
        // 
        ArrayList<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        //
        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            graph[u].add(v);
            graph[v].add(u);
        }
        int[] preTime = new int[n + 1];
        int[] postTime = new int[n + 1];
        boolean[] vis = new boolean[n + 1];
        fillPrePostTime(graph, preTime, postTime, 1, new int[]{1}, vis);
        //
        int q = sc.nextInt();
        while (q-- > 0) {
            int option = sc.nextInt(); // 1 is away and 0 is towards
            int dest = sc.nextInt(); // x
            int src = sc.nextInt(); // y
            // check if oliver & bob lie in different subtrees
            if (!isSubTree(src, dest, preTime, postTime) && !isSubTree(dest, src, preTime, postTime)) {
                // System.out.println("NO");
                continue;
            }
            if (option == 0) {
                // towards king
                if (isSubTree(src, dest, preTime, postTime)) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO"); 
                }

            } else if (option == 1) {
                // away from king
                if (isSubTree(dest, src, preTime, postTime)) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
            }
        }
    }

    public static void fillPrePostTime(ArrayList<Integer>[] graph, int[] preTime, int[] postTime, int src, int[] time, boolean[] vis) {
        vis[src] = true;
        preTime[src] = time[0]++;
        for (int nbr: graph[src]) {
            if (vis[nbr] == false) {
                fillPrePostTime(graph, preTime, postTime, nbr, time, vis);
            }
        }
        postTime[src] = time[0]++;
    }

    public static boolean isSubTree(int b, int a, int[] preTime, int[] postTime) {
        // is b substree of a
        if (preTime[b] > preTime[a] && postTime[b] < postTime[a]) {
            return true;
        } else return false;
    }

    // Madmax
    public static void madMax() throws IOException {
        Reader sc = new Reader();
        int n = sc.nextInt();
        int m = sc.nextInt();
        //
        ArrayList<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i < graph.length; i++) graph[i] = new ArrayList<>();
        //
        while (m-- > 0) {
            String[] s = sc.readLine().split(" ");
            int u = Integer.parseInt(s[0]), v = Integer.parseInt(s[1]);
            int w = (int)(s[2].charAt(0) - 'a');
            // directed graph
            graph[u].add(new int[]{v, w});
        }
        // process
        Boolean[][][] dp = new Boolean[n + 1][n + 1][26];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                // i = position of max and j is position of lucas
                // we will check if max wins or not
                boolean willMaxWin = madMaxHelper(graph, i, j, 0, dp);
                if (willMaxWin) {
                    System.out.print("A");
                } else {
                    System.out.print("B");
                }
            }
            System.out.println();
        }
    }

    public static boolean madMaxHelper(ArrayList<int[]>[] graph, int p1, int p2, int lastCharAscii, Boolean[][][] dp) {
        // THIS FUNCTION TELLS THE WINNING OF P1
        if (dp[p1][p2][lastCharAscii] != null) return dp[p1][p2][lastCharAscii];
        boolean ans = false;
        // default value. So if any player does not runs it's children loop it will return false
        for (int[] nbr: graph[p1]) {
            // check if nbr char's is >= last char
            if (nbr[1] >= lastCharAscii) {
                boolean recAns = madMaxHelper(graph, p2, nbr[0], nbr[1], dp);
                if (recAns) {
                    // means p2 is winning, so p1 looses
                    ans |= false;
                } else {
                    ans |= true;
                }
            }
        }
        return dp[p1][p2][lastCharAscii] = ans;
    }

    // number of provinces or count of friend circles
    public static int numberOfProvinces(int[][] arr) {
        int n = arr.length;
        dsu obj = new dsu(n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arr[i][j] == 1) {
                    int p1 = obj.findPar(i);
                    int p2 = obj.findPar(j);
                    if (p1 != p2) {
                        obj.union(i, j);
                    }
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < obj.par.length; i++) {
            if (obj.par[i] == i) {
                ans++;
            }
        }
        return ans;
    }

    // SCC: strongly connected components
    // kosaraju algo
    public static int kosarajuHelper(int n, ArrayList<ArrayList<Integer>> graph) {
        boolean[] vis = new boolean[n];
        Stack<Integer> st = new Stack<>();
        boolean flag = true;
        // 1. random order dfs
        // flag is for checking if dfs is called 1st time or 2nd
        // according to which we will not push anything in stack when called 2nd time
        for (int i = 0; i < n; i++) {
            if (vis[i] == false) {
                kosaraju_dfs(graph, i, vis, st, flag);
            }
        }   
        // 2. reverse existing graph
        ArrayList<ArrayList<Integer>> ngraph = kosaraju_reverse(graph);
        vis = new boolean[n];
        // 3. call dfs again from the points in stack but on reversed graph
        int count = 0;
        flag = false;
        while (st.size() > 0) {
            int src = st.pop();
            if (vis[src] == false) {
                kosaraju_dfs(ngraph, src, vis, st, flag);
                count++;
            }
        }
        return count;
    }

    public static void kosaraju_dfs(ArrayList<ArrayList<Integer>> graph, int src, boolean[] vis, Stack<Integer> st, boolean flag) {
        vis[src] = true;
        for (int nbr: graph.get(src)) {
            if (!vis[nbr]) {
                kosaraju_dfs(graph, nbr, vis, st, flag);
            }
        }
        if (flag) st.push(src);
    }

    public static ArrayList<ArrayList<Integer>> kosaraju_reverse(ArrayList<ArrayList<Integer>> graph) {
        int n = graph.size();
        ArrayList<ArrayList<Integer>> ng = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ng.add(new ArrayList<>());
        }
        for (int i = 0; i < graph.size(); i++) {
            for (int j: graph.get(i)) {
                ng.get(j).add(i);
            }
        }
        return ng;
    }

    // Minimum number of trials to reach from source word to destination word
    // it says that after performing any operation the next word formed should be also in dict
    // it means an edge from w1 to w2 represents edit distance of 1 between them and so on the edit distance
    // increases as distance between 2 words increase
    static class minTrialsPair {
        String u;
        int w;
        public minTrialsPair(String u, int w) {
            this.u = u;
            this.w = w;
        }
    }

    public static int minTrials(String[] arr, String src, String dest) {
        int n = arr.length;
        HashMap<String, ArrayList<minTrialsPair>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int trials = editDistance(arr[i], arr[j], 0, 0, new Integer[arr[i].length() + 1][arr[j].length() + 1]);
                if (trials == 1) {
                    if (graph.containsKey(arr[i]) == false) {
                        graph.put(arr[i], new ArrayList<>());
                    }
                    if (graph.containsKey(arr[j]) == false) {
                        graph.put(arr[j], new ArrayList<>());
                    }
                    graph.get(arr[i]).add(new minTrialsPair(arr[j], trials));
                    graph.get(arr[j]).add(new minTrialsPair(arr[i], trials));
                }
            }
        }
        // disjkstras algo
        PriorityQueue<minTrialsPair> pq = new PriorityQueue<>((t, o) -> {
            return t.w - o.w;
        });
        pq.add(new minTrialsPair(src, 0));
        HashSet<String> vis = new HashSet<>();
        vis.add(src);
        while (pq.size() > 0) {
            minTrialsPair cp = pq.remove();
            //
            if (cp.u.equals(dest)) {
                return cp.w;
            }
            for (minTrialsPair nbr: graph.get(cp.u)) {
                if (vis.contains(nbr.u) == false) {
                    pq.add(new minTrialsPair(nbr.u, cp.w + nbr.w));
                    vis.add(nbr.u);
                }
            }
        }    
        return -1;
    }

    public static int editDistance(String s1, String s2, int i, int j, Integer[][] dp) {
        // convert s1 to s2
        if (i >= s1.length() && j >= s2.length()) {
            return 0;
        } else if (i >= s1.length() && j < s2.length()) {
            // insert in s1
            return s2.length() - j;
        } else if (i < s1.length() && j >= s2.length()) {
            return s1.length() - i;
        }

        if (dp[i][j] != null) return dp[i][j];

        char ith = s1.charAt(i);
        char jth = s2.charAt(j);
        int count = (int)(1e8);
        if (ith == jth) {
            int same = editDistance(s1, s2, i + 1, j + 1, dp);
            count = Math.min(count, same);
        } else {
            int del = editDistance(s1, s2, i + 1, j, dp);
            int replace = editDistance(s1, s2, i + 1, j + 1, dp);
            int insert = editDistance(s1, s2, i, j + 1, dp);
            count = Math.min(count, Math.min(del, Math.min(replace, insert)));
            // add my operation too
            count += 1;
        }
        return dp[i][j] = count;
    }

    // Longest Path in a weighted Directed Acyclic Graph
    public static void longestPathDag(int src, ArrayList<int[]>[] graph) {
        int n = graph.length;
        boolean[] vis = new boolean[n];
        Stack<Integer> st = new Stack<>();

        for (int i = 0; i < n; i++) {
            if (vis[i] == false) {  
                longestPath_topo(graph, vis, src, st);
            }
        }

        int[] dis = new int[n];
        Arrays.fill(dis, (int)(1e8));

        dis[src] = 0;

        while (st.size() > 0) {
            int node = st.pop();
            if (dis[node] != (int)(1e8)) {
                for (int[] nbr: graph[node]) {
                    int newDis = nbr[1] + dis[node];
                    if (newDis < dis[nbr[0]]) {
                        dis[nbr[0]] = newDis;
                    }
                }
            }
        }

        int ans = -(int)(1e8);
        for (int d: dis) {
            ans = Math.max(ans, d);
        }
        System.out.println(ans);
    }

    public static void longestPath_topo(ArrayList<int[]>[] graph, boolean[] vis, int src, Stack<Integer> st) {
        vis[src] = true;
        for (int[] nbr: graph[src]) {
            if (vis[nbr[0]] == false) {
                longestPath_topo(graph, vis, nbr[0], st);
            }
        }
        st.push(src);
    }

    // find mother vertex in a graph
    public static void motherVtx(ArrayList<Integer>[] graph) {
        int n = graph.length;
        Stack<Integer> st = new Stack<>();
        boolean[] vis = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (vis[i] == false) {
                topologicalSort_dfs(graph, st, vis, i);
            }
        }
        int potAns = st.pop();
        int[] count = {0};
        vis = new boolean[n];
        motherVtx_dfs(graph, vis, potAns, count);
        if (count[0] == n) {
            System.out.println(potAns);
        } else {
            System.out.println(-1);
        }
    }

    public static void motherVtx_dfs(ArrayList<Integer>[] graph, boolean[] vis, int src, int[] count) {
        vis[src] = true;
        count[0]++;
        for (int nbr: graph[src]) {
            if (vis[nbr] == false) {
                motherVtx_dfs(graph, vis, nbr, count);
            }
        }
    }

    // Count all possible paths between two vertices in a dag
    public static int countPathsDag(int n, ArrayList<ArrayList<Integer>> graph, int src, int dest) {
        boolean[] vis = new boolean[n + 1];
        return countPaths_dfs(graph, src, dest, vis);
    }

    public static int countPaths_dfs(ArrayList<ArrayList<Integer>> graph, int src, int dest, boolean[] vis) {
        if (src == dest) return 1;
        vis[src] = true;    
        int count = 0;
        for (int nbr: graph.get(src)) {
            if (vis[nbr] == false) {
                count += countPaths_dfs(graph, nbr, dest, vis);
            }
        }
        vis[src] = false;
        return count;
    }

    // water jug problem
    public static boolean waterJugProblem(int j1, int j2, int tar) {
        if (j1 + j2 < tar) return false; 
        Queue<Integer> qu = new LinkedList<>();
        HashSet<Integer> vis = new HashSet<>();
        // now the operations are +j1, +j2, -j1, -j2
        int[] options = {j1, j2, -j1, -j2};
        qu.add(0); 
        vis.add(0);
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                int cp = qu.remove();
                //
                if (cp == tar) return true;
                // add children
                for (int o: options) {
                    int nbr = o + cp;
                    if (nbr >= 0 && nbr <= (j1 + j2) && vis.contains(nbr) == false) {
                        qu.add(nbr);
                        vis.add(nbr);
                    }
                }
            }
        }
        return false;
    }

    // detect cycle in an undirected graph
    public static boolean detectCycleUn(int n, ArrayList<ArrayList<Integer>> graph) {
        boolean[] vis = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (vis[i] == false) {
                boolean ans = detectCycleUnHelper(graph, i, vis);
                if (ans) return true;
            }
        }
        return false;
    }

    public static boolean detectCycleUnHelper(ArrayList<ArrayList<Integer>> graph, int src, boolean[] vis) {
        Queue<Integer> qu = new LinkedList<>();
        qu.add(src);
        while (qu.size() > 0) {
            int cp = qu.remove();
            if (vis[cp]) return true;
            vis[cp] = true;
            for (int nbr: graph.get(cp)) {
                if (vis[nbr] == false) {
                    qu.add(nbr);
                }
            }
        }
        return false;
    }

    // Detect cycle in directed graph
    // kahn's algo
    public static boolean cycleInDir(int n, ArrayList<ArrayList<Integer>> graph) {
        Stack<Integer> st = new Stack<>();
        boolean[] vis = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (vis[i] == false) {
                cycleInDir_dfs(graph, i, vis, st);
            }
        }
        int[] indeg = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j: graph.get(i)) {
                indeg[j]++;
            }
        }
        Queue<Integer> qu = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indeg[i] == 0) qu.add(i);
        }
        int idx = 0;
        while (qu.size() > 0) {
            int cp = qu.remove();
            idx++;

            for (int nbr: graph.get(cp)) {
                indeg[nbr]--;
                if (indeg[nbr] == 0) {
                    qu.add(nbr);
                }
            }
        }
        if (idx != n) return true;
        else return false;
    }

    public static void cycleInDir_dfs(ArrayList<ArrayList<Integer>> g, int src, boolean[] vis, Stack<Integer> st) {
        vis[src] = true;
        for (int nbr: g.get(src)) {
            if (vis[nbr] == false) {
                cycleInDir_dfs(g, nbr, vis, st);
            }
        }
        st.push(src);
    }

    // snake and ladder
    public static int snakeAndLadder(int n, int[] arr) {
        int src = 1, dest = 30;
        boolean[] vis = new boolean[31];
        HashMap<Integer, Integer> snakes = new HashMap<>();
        HashMap<Integer, Integer> ladders = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int u = arr[2 * i], v = arr[2 * i + 1];
            if (u < v) {
                ladders.put(u, v);
            } else {
                snakes.put(u, v);
            }
        }
        Queue<int[]> qu = new LinkedList<>();
        qu.add(new int[]{src, 0});

        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                int[] cp = qu.remove();
                if (cp[0] == dest) {
                    return cp[1];
                } 
                for (int i = 1; i <= 6; i++) {
                    int next = cp[0] + i;
                    if (next > 30) break;
                    if (vis[next] == false) {
                        int[] vtx = {-1, cp[1] + 1};
                        vis[next] = true;

                        if (ladders.containsKey(next)) {
                            vtx[0] = ladders.get(next);
                        } else if (snakes.containsKey(next)) {
                            vtx[0] = snakes.get(next);
                        } else {
                            vtx[0] = next;
                        }
                        qu.add(vtx);
                    }
                }
            }
        }
        return -1;
    }

     // Given a sorted dictionary of an alien language, find order of characters
    // alien dict
    public static String alientDict(String[] words) {
        HashMap<Character, HashSet<Character>> map = new HashMap<>();
        HashMap<Character, Integer> indeg = new HashMap<>();

        for (String st: words) {
            for (char ch: st.toCharArray()) {
                indeg.put(ch, 0);
            }
        }

        for (int i = 0; i < words.length - 1; i++) {
            String curr = words[i];
            String next = words[i + 1];
            boolean flag = false;
            int len = Math.min(curr.length(), next.length());

            for (int j = 0; j < len; j++) {
                char ch1 = curr.charAt(j), ch2 = next.charAt(j);
                if (ch1 != ch2) {
                    /// map: ch1 -> ch2
                    // and raise the indegree of ch2
                    if (map.containsKey(ch1)) {
                        HashSet<Character> set = map.get(ch1);
                        if (set.contains(ch2) == false) {
                            set.add(ch2);
                            indeg.put(ch2, indeg.getOrDefault(ch2, 0) + 1);
                            map.put(ch1, set);
                        }
                    } else {
                        HashSet<Character> set = new HashSet<>();
                        set.add(ch2);
                        indeg.put(ch2, indeg.getOrDefault(ch2, 0) + 1);
                        map.put(ch1, set);
                    }
                    flag = true;
                    // this flag is only to see if the mismatched has occur
                    break;
                }
            }
            if (!flag && curr.length() > next.length()) {
                // then the 2 strings were same
                // exception is when curr.len() < next.len() then ans is not possible
                // as according to dict order those 2 words can't come like that 
                return "";
            }
        }

        // kahn's algo for topological sort
        Queue<Character> qu = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        for (Character key: indeg.keySet()) {
            if (indeg.get(key) == 0) {
                qu.add(key);
            }
        }

        int count = 0;
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                char rem = qu.remove();
                sb.append(rem);
                count++;
                // explore all nbrs and reduce their indegree
                if (map.containsKey(rem) == true) {
                    HashSet<Character> set = map.get(rem);
                    for (Character nb: set) {
                        indeg.put(nb, indeg.get(nb) - 1);
                        if (indeg.get(nb) == 0) qu.add(nb);
                    }
                }
            }
        }

        if (count == indeg.size()) {
            return sb.toString();
        } else return "";
    }

    // Print all Jumping Numbers smaller than or equal to a given value
    public static int jumpingNumbers(int val) {
        int[] max = {-(int)(1e8)};
        for (int i = 0; i <= 9; i++) {
            jumpingNumbers_bfs(val, i, max);
        }
        return max[0];
    }

    public static void jumpingNumbers_bfs(int dest, int src, int[] max) {
        Queue<Integer> qu = new LinkedList<>();
        qu.add(src); 
        while (qu.size() > 0) {
            int cp = qu.remove();
            //
            if (cp > dest) continue;
            // System.out.print(cp + " ");
            max[0] = Math.max(max[0], cp);
            //
            int lastD = cp % 10;
            int num1 = cp * 10 + (lastD - 1);
            int num2 = cp * 10 + (lastD + 1);
            if (lastD == 0) {
                qu.add(num2);
            } else if (lastD == 9) {
                qu.add(num1);
            } else {
                qu.add(num1);
                qu.add(num2);
            }
        }
    }

    // Optimal read list for given number of days
    public static long lotsofWork(int m, long[] arr, int n) {
        long si = 0, ei = 0, max = -(int)(1e8);
        for (long val: arr) {
            ei += val;
            max = Math.max(val, max);
        }
        long ans = (int)(1e8);
        while (si <= ei) {
            long mid = (si + ei) >> 1;
            // mid >= max, so that all elements fit in the limit
            if (mid >= max && lotsofWork_helper(arr, mid, m)) {
                ans = Math.min(ans, mid);
                mid = mid - 1;
            } else {
                si = mid + 1;
            }
        }
        return ans;
    }   

    public static boolean lotsofWork_helper(long[] arr, long mid, int m) {
        int count = 1;
        long sum = 0;
        for (int i = 0; i < arr.length;) {
            if (arr[i] + sum > mid) {
                count++;
                sum = 0;
            } else {
                sum += arr[i];
                i++;
            }
        }
        if (count <= m) return true;
        else return false;
    }

    // count number of trees in a forest
    // count components
    public static int countTrees(int[][] arr) {
        int n = arr.length;
        dsu obj = new dsu(n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arr[i][j] == 1) {
                    obj.union(i, j);
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (obj.par[i] == i) ans++;
        }
        return ans;
    }

    // Construct binary palindrome by repeated appending and trimming


    //  Find Eventual Safe States: lc:802
    // in this safe nodes are those which do not come in any cycle
    public static List<Integer> safeStates(int[][] arr) {
        int n = arr.length;
        int[] vis = new int[n + 1];
        // 0 = unvis, 1 = vis, 2 = included in path
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            boolean isCycle = cycleCheckSafeState(arr, i, vis);
            // we can add it in our path 
            if (isCycle == false && vis[i] == 2) {
                // vis[i] = 2 when it will reach to a point and then it will backtrack
                ans.add(i);
            }
        }
        Collections.sort(ans);
        return ans;
    }

    public static boolean cycleCheckSafeState(int[][] arr, int src, int[] vis) {
        vis[src] = 1;
        for (int nbr: arr[src]) {
            if (vis[nbr] == 0) {
                boolean recAns = cycleCheckSafeState(arr, nbr, vis);
                if (recAns) return true;
            } else if (vis[nbr] == 1) {
                // already visited now it will form cycle
                return true;
            }
        }
        // mark 2 ie this src is in the path now
        vis[src] = 2;
        // false is for no cycle till now
        return false;
    }

    // Equal 
    public static int equal(List<Integer> arr) {
        int n = arr.size();
        int[] posi = new int[5];
        int min = arr.get(0);
        for (int val: arr) min = Math.min(val, min);
        for (int i = 0; i < 5; i++) {
            for (int k: arr) {
                int diff = k - min;
                int steps = (diff / 5) + ((diff % 5) / 2) + (((diff % 5) % 2) / 1);
                posi[i] += steps;
            }
            min--;
        }
        int ans = posi[0];
        for (int val: posi) ans = Math.min(ans, val);
        return ans;
    }

    public static void solve() throws Exception {
        int ans = jumpingNumbers(50);
        System.out.println(ans);
    }

    // fast IO
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;
 
        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }
 
        public Reader(String file_name) throws IOException {
            din = new DataInputStream(
                new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }
 
        public String readLine() throws IOException {
            byte[] buf = new byte[1024]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n') {
                    if (cnt != 0) {
                        break;
                    }
                    else {
                        continue;
                    }
                }
                buf[cnt++] = (byte)c;
            }
            return new String(buf, 0, cnt);
        }
 
        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
 
            if (neg)
                return -ret;
            return ret;
        }
 
        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }
 
        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
 
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
 
            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }
 
            if (neg)
                return -ret;
            return ret;
        }
 
        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0,
                                 BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }
 
        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }
 
        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

    public static ArrayList<Integer>[] constructGraph(int n, int[][] arr) {
        ArrayList<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 0; i < graph.length; i++) graph[i] = new ArrayList<>();
        for (int[] a: arr) {
            graph[a[0]].add(a[1]);
            graph[a[1]].add(a[0]);
        }
        return graph;
    }

    public static void display(ArrayList<Integer>[] graph) {
        for (int i = 0; i < graph.length; i++) {
            System.out.print(i + " -> ");
            for (int nbr: graph[i]) {
                System.out.print(nbr + " ");
            }
            System.out.println();
        }
    }

    public static int[] stringToArr(String s) {
        String[] arr = s.split(" ");
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = Integer.parseInt(arr[i]);
        }
        return ans;
    }

    public static void main(String[] args) throws Exception {
        solve();
    }
}