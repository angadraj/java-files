import java.util.*;


// it works better on edges
class unionFind {
    public static int[][] construct() {
        int[][] graph = {{0, 2, 11}, {0, 1, 10}, {1, 3, 12}, {2, 3, 13}, {4, 5, 5}, {5, 6, 2}, {4, 6, 100}, {7, 10, 4}, {10, 9, 8}, {9, 8, 10}, {7, 8, 1}};
        return graph;
    }

    static int[] par;
    static int[] size;

    public static int findParent(int a) {
        if(a == par[a]) {
            return a;
        }
        par[a] = findParent(par[a]);
        return par[a];
    }

    public static void mergeSet(int p1, int p2) {
        if(size[p1] < size[p2]) {
            par[p1] = p2;
            size[p2] += size[p1];
        } else {
            par[p2] = p1;
            size[p1]+= size[p2];
        }
    }

    public static void kruskals(int[][] graph) {
        ArrayList<Integer>[] g = new ArrayList[graph.length];

        int cycles = 0, components = 0, maxArea = 0;

        par = new int[graph.length];
        size = new int[graph.length];
        for(int i = 0; i < par.length; i++) {
            par[i] = i;
            size[i] = 1;
            g[i] = new ArrayList<>();
        }

        Arrays.sort(graph, (a, b) -> {
            return a[2] - b[2];
        });

        int idx = 0;

        for(int[] e : graph) {
            int u = e[0];
            int v = e[1];
            int w = e[2];

            int p1 = findParent(u);
            int p2 = findParent(v);

            if(p1 != p2) {
                mergeSet(p1, p2);
                g[idx].add(u);
                g[idx].add(v);
                g[idx].add(w);
                idx++;
            } else if(p1 == p2) {
                // System.out.println("cycle present at" + u + ", " + v);
                cycles++;
            }
        }

        // display(g);

        for(int i = 0; i < par.length; i++) {
            // System.out.print(par[i] + " "); 
            if(par[i] == i) components++;
        }

        // System.out.println("---------size-----------------");

        for(int i = 0; i < size.length; i++) {
            // System.out.print(size[i] + " ");
            if(size[i] > 1) {
                maxArea = Math.max(maxArea, size[i]);
            }
        }

        System.out.println("total cycles " + cycles); 
        System.out.println("total compnents " + components);
        System.out.println("max Area is " + maxArea);

    }

    public static void display(ArrayList<Integer>[] graph) {
        for(int i = 0; i < graph.length; i++) {
            for(int e : graph[i]) {
                System.out.print(e + " ");
            }
            System.out.println();
        }
    }

    public static void solve() {
        int[][] graph = construct();
        kruskals(graph);
    }
    public static void main(String args[]) {
        solve();
    }
}