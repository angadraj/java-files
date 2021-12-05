import java.util.*;
class Greedy {
    
    // activity selection problem
    static class activitySel_pair {
        int st;
        int et;
        public activitySel_pair(int st, int et) {
            this.st = st;
            this.et = et;
        }
    }

    public static void activitySel(int[] s, int[] f) {
        // sort on the basis of finishing time
        int n = s.length;
        activitySel_pair[] arr = new activitySel_pair[n];
        for (int i = 0; i < n; i++) {
            arr[i] = new activitySel_pair(s[i], f[i]);
        }
        Arrays.sort(arr, (t, o) -> {
            return t.et - o.et;
        });
        activitySel_pair prev = arr[0];
        System.out.println(prev.st + ", " + prev.et);
        for (int i = 1; i < n; i++) {
            activitySel_pair cp = arr[i];
            // submission done for strictly big
            if (prev.et < cp.st) {
                System.out.println(cp.st + ", " + cp.et);
                prev = cp;
            }
        }
    }

    public static void activitySel2(int[] s, int[] f) {
        int i = 0, j = 0, n = s.length, count = 0;
        System.out.println(s[i] + ", " + f[i]);
        count++;
        for (i = 1; i < n; i++) {
            if (s[i] >= f[j]) {
                System.out.println(s[i] + ", " + f[i]);
                j = i;
                count++;
            }
        }
    }

    // job sequencing problem
    static class Job {
        int id, profit, deadline;
        public Job(int id, int profit, int deadline) {
            this.id = id;
            this.profit = profit;
            this.deadline = deadline;
        }
    }

    public static void jobSequencing(Job[] arr) {
        int n = arr.length, totalProfit = 0;
        boolean[] slots = new boolean[n];
        Arrays.sort(arr, (t, o) -> {
            return o.profit - t.profit;
        });
        for (int i = 0; i < n; i++) {
            int curr_deadline = arr[i].deadline;
            for (int j = Math.min(n - 1, curr_deadline - 1); j >= 0; j--) {
                if (!slots[j]) {
                    totalProfit += arr[i].profit;
                    slots[j] = true;
                    System.out.println(arr[i].deadline + ", " + arr[i].profit);
                    break;
                }
            }
        }
    }

    // job sequencing using disjoint set
    static int[] par;

    public static int findPar(int x) {
        if (par[x] == x) return x;
        int ans = findPar(par[x]);
        par[x] = ans;
        return ans;
    }

    public static void merge(int tm1, int t) {
        // two slots: t and tm1: t and t minus 1
        // we will make tm1 parent of t such that every time we get an empty slot
        par[t] = tm1;
    }

    public static void jobSequencingDSU(Job[] arr) {
        int maxDeadline = arr[0].deadline;
        for (Job j: arr) maxDeadline = Math.max(maxDeadline, j.deadline);

        par = new int[maxDeadline + 1];
        for (int i = 0; i < par.length; i++) par[i] = i;

        Arrays.sort(arr, (t, o) -> {
            return o.profit - t.profit;
        });

        int pr = 0;
        for (int i = 0; i < arr.length; i++) {
            int curr_deadline = arr[i].deadline;
            int available_slot = findPar(curr_deadline);
            if (available_slot > 0) {
                pr += arr[i].profit;
                merge(findPar(available_slot - 1), available_slot);
            }
        }
        System.out.println(pr);
    }

    // egyptian fraction
    // dr > nr
    public static void egyptionFraction(int nr, int dr) {
        if (nr == 0 || dr == 0) {
            return;
        }

        if (nr % dr == 0) {
            System.out.print(nr / dr);
            return;
        }

        if (dr % nr == 0) {
            System.out.print("1/" + (dr / nr));
            return;
        }

        if (nr > dr) {
            System.out.print(nr % dr + " + ");
            egyptionFraction(nr % dr, dr);
        }
        
        int n = dr / nr + 1;
        System.out.print("1/" + n + " + ");
        egyptionFraction(nr * n - dr, n * dr);
    }

    // fractional knapsack coding
     static class fkp_pair implements Comparable<fkp_pair> {
        int v, w;
        double ratio;

        public fkp_pair(int v, int w, double ratio) {
            this.v = v;
            this.w = w;
            this.ratio = ratio;
        }
        
        public int compareTo(fkp_pair o) {
            if (this.ratio > o.ratio) return -1;
            else if (this.ratio < o.ratio) return 1;
            else return 0;
        }
    }

    public static double fractionalKnapSack(int[] vals, int[] weight, int W) {
        int n = vals.length;
        fkp_pair[] arr = new fkp_pair[n];

        for (int i = 0; i < n; i++) {
            double r = (vals[i] * 1.0) / weight[i];
            arr[i] = new fkp_pair(vals[i], weight[i], r);
        }
        Arrays.sort(arr);
        
        // for (int i = 0; i < n; i++) {
        //     System.out.println(arr[i].v + ", " + arr[i].w + ", " + arr[i].ratio);
        // }
        // value in bag, remaining capacity 
        
        double vib = 0.0;
        int rc = W;
        for (int i = 0; i < n; i++) {
            if (rc == 0) break;
            else if (arr[i].w <= rc) {
                vib += arr[i].v;
                rc -= arr[i].w;
            } else {
                vib += (arr[i].ratio) * rc;
                // vib += arr[i].v * ((rc * 1.0) / arr[i].w);
                rc = 0;
                break;
            }
        }
        return vib;
    }   

    // coins, target infinite combination
    // coins: { 1, 2, 5, 10, 20, 50, 100, 500, 1000}  
    public static int targetCoin(int[] coins, int target) {
        // Arrays.sort(coins);
        Integer[][] dp = new Integer[coins.length][target + 1];
        int ans = targetCoin_rec(coins, target, 0, dp);
        if (ans >= (int)(1e8)) return -1;
        else return ans - 1;
    }

    public static int targetCoin_rec(int[] coins, int tar, int idx, Integer[][] dp) {
        if (idx >= coins.length || tar == 0) {
            if (tar == 0) {
                return 1;
            }
            return 0;
        }

        if (dp[idx][tar] != null) return dp[idx][tar];

        int ans = (int)(1e8);
        for (int i = idx; i < coins.length; i++) {
            if (tar - coins[i] >= 0) {
                ans = Math.min(ans, targetCoin_rec(coins, tar - coins[i], i, dp));
            }
        }
        return dp[idx][tar] = (ans + 1);
    }

    public static int targetCoin_dp(int[] coins, int tar) {
        return 0;
    }

    // Maximum Length Chain of Pairs | DP-20
    static class Pair {
        int x, y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static int maxLengthChain(int[] arr) {
        // pair : x, y 
        // ab cd then b < c or arr[j].y < arr[i].x
        int n = arr.length, res = -(int)(1e8);
        int[] dp = new int[n];
        
        // Arrays.sort(arr, (t, o) -> {
        //     return t.y - o.y;    
        // });

        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            int max = -(int)(1e8);
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    // arr[j].y < arr[i].x
                    max = Math.max(max, dp[j]);
                }
            }
            dp[i] += (max == -(int)(1e8)) ? 0 : max;
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    // max length with activity selection logic
    public static int activitySelectionLogic(int[] arr) {  
        // make it Pair arr
        int n = arr.length;
        // Arrays.sort(arr, (t, o) -> {
        //     return t.y - o.y;
        // });
        int count = 1, i = 0, j = 1;
        while (j < n) {
            if (arr[i] < arr[j]) {
                // arr[i].y < arr[j].x
                i = j;
                count++;
                j++;
            } else j++;
        }
        return count;
    }

    // Find minimum time to finish all jobs with given constraints

    // Minimum sum of two numbers formed from digits of an array
    public static long minSumOfTwoNums(int[] arr) {
        Arrays.sort(arr);
        long n1 = 0, n2 = 0;
        for (int i = 0; i < arr.length; i++) {
            if ((i & 1) == 0) {
                n1 = (n1 * 10) + arr[i];
            } else {
                n2 = (n2 * 10) + arr[i];
            }
        }
        return n1 + n2;
    }

    // Find smallest number with given number of digits and sum of digits
    public static void smallestNumberWithGivenDAndS(int d, int s) {
        // smallestNumberWithGivenDAndS_rec(d, s, 0, 0, "");
        // System.out.println(smallestNumberWithGivenDAndS_ans);
        smallestNumberWithGivenDAndS_eff(d, s);
    }

    static int smallestNumberWithGivenDAndS_ans = (int)(1e8);

    public static void smallestNumberWithGivenDAndS_rec(int d, int s, int sum, int digits, String ans) {
        if (digits > d || sum > s) return; 

        if (digits == d && sum == s) {
            smallestNumberWithGivenDAndS_ans = Math.min(smallestNumberWithGivenDAndS_ans, Integer.parseInt(ans));
            return;
        } 
        
        for (int num = 1; num <= 9; num++) {
            smallestNumberWithGivenDAndS_rec(d, s, sum + num, digits + 1, ans + num);
        }
    }

    public static void smallestNumberWithGivenDAndS_eff(int d, int s) {
        if (s == 0) {
            System.out.println(d == 1 ? "0" : "not possible");
        }

        if (s > 9 * d) {
            // 3 digits : 999 -> sum = 27, but given sum = 40
            System.out.println("not possible");
        }

        int[] res = new int[d];
        for (int i = 0; i < res.length; i++) {
            if (s > 9) {
                res[i] = 9;
                s -= 9;
            } else {
                res[i] = s;
                s = 0;
            }
        }

        for (int i = res.length - 1; i >= 0; i--) System.out.print(res[i]);
    }

    // Minimum sum of absolute difference of pairs of two arrays
    public static int minSumOfAbsDiff(int[] a, int[] b) {
        Arrays.sort(a);
        Arrays.sort(b);
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.abs(a[i] - b[i]);
        }
        return sum;
    }

    // Maximize sum of consecutive differences in a circular array
    public static int maxiSumOfConsecutiveDiffCirArr(int[] arr) {
        int n = arr.length, sum = 0;
        Arrays.sort(arr);
        for (int i = 0; i < n / 2; i++) {
            sum -= (2 * arr[i]);
            sum += (2 * arr[n - i - 1]);
        }
        return sum;
    }

    // paper cut into minimum number of squares
    public static int cutIntoMinNumSquares(int l, int b) {
        int count = 0;
        while (l > 0 && b > 0) {
            int min = Math.min(l, b);
            // 4 X 4 = total will be 4 squares of 1 X 1
            if (min == l && min == b) {
                count += l; // l or b
                l = 0; b = 0;
            } else if (min == l) {
                count++;
                b -= min;
            } else if (min == b) {
                count++;
                l -= min;
            }
        }
        return count;
    }

    // if they have asked to cut squares of max size
    public static int cutIntoMinNumSquares2(int l, int b) {
        int count = 0, maxSizeOfSquareCut = -1;
        while (l > 0 && b > 0) {
            int min = Math.min(l, b);
            maxSizeOfSquareCut = Math.max(maxSizeOfSquareCut, min);
            // 4 X 4 = total will be 4 squares of 1 X 1
            if (min == l && min == b) {
                count += 1; // if 3 X 3 is left, then we will cut complete 3 X 3
                // square
                l = 0; b = 0;
            } else if (min == l) {
                count++;
                b -= min;
            } else if (min == b) {
                count++;
                l -= min;
            }
        }
        return count;
    }

    // Lexicographically smallest array after at-most K consecutive swaps
    public static void smallestLexicoArrayAfterKSwaps(int[] arr, int k) {
        int n = arr.length;
        for (int i = 0; i < n && k > 0; i++) {
            int pos = i;
            for (int j = i + 1; j < n; j++) {
                if (i - j > k) break;
                if (arr[j] < arr[i]) pos = j;
            }
            // now start swapping from i + 1 to pos
            int temp;
            for (int j = pos; j > i; j--) {
                temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
            }   
            // no of swaps done = diff of pos - i
            k -= (pos - i);
        }
    }

    // Rearrange characters in a string such that no two adjacent are same
    static class rearrangeCharsWithNoAdjSamePair {
        char ch;
        int freq;
        public rearrangeCharsWithNoAdjSamePair(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }
    }

    public static String rearrangeCharsWithNoAdjSame(String s) {
        PriorityQueue<rearrangeCharsWithNoAdjSamePair> pq = new PriorityQueue<>((t, o) -> {
            return o.freq - t.freq;
        });
        int[] fmap = new int[26];
        for (int i = 0; i < s.length(); i++) {
            fmap[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < fmap.length; i++) {
            if (fmap[i] > 0) {
                pq.add(new rearrangeCharsWithNoAdjSamePair((char)(i + 'a'), fmap[i]));
            }
        }   
        rearrangeCharsWithNoAdjSamePair prev = new rearrangeCharsWithNoAdjSamePair('#', -1);
        String ans = "";
        while (pq.size() > 0) {
            rearrangeCharsWithNoAdjSamePair cp = pq.remove();
            ans += cp.ch;
            if (prev.freq > 0) pq.add(prev);
            cp.freq -= 1;
            prev = cp;
        } 
        if (ans.length() != s.length()) {
            return "-1";
        }
        // System.out.println(ans);
        return ans;
    }

    // max height of pyramid formed from array elements
    public static int maxPyramidHeight(int[] arr) {
        Arrays.sort(arr);
        int maxHeight = 1, prevCount = 1, prevWidth = arr[0];
        int currCount = 0, currWidth = 0;
        for (int i = 1; i < arr.length; i++) {
            currWidth += arr[i];
            currCount++;
            if (currWidth > prevWidth && currCount > prevCount) {
                prevWidth = currWidth;
                prevCount = currCount;

                maxHeight++;

                currWidth = 0; currCount = 0;
            }
        }
        return maxHeight;
    }

    // Minimum cost for acquiring all coins with k extra coins allowed with every coin
    public static int minCostForAcquiringAllCoins(int[] arr, int k) {
        Arrays.sort(arr);
        k++;
        // because for every amount paid we can acquire k + 1 coins
        int i = 0, j = arr.length, amt = 0;
        while (i < j) {
            // gather the min amount from beginning
            amt += arr[i];
            i++;
            // acquire k coins including that ith coin
            j -= k;
        }
        return amt;
    }

    // Find maximum equal sum of every three stacks
    public static int maxEqualSumOfThreeStacks(int[] a, int[] b, int[] c) {
        int sum1 = 0, sum2 = 0, sum3 = 0;
        for (int val: a) sum1 += val;
        for (int val: b) sum2 += val;
        for (int val: c) sum3 += val;

        int i = 0, j = 0, k = 0, max = 0;
        // all are positive nums so min will be 0
        while (i < a.length && j < b.length && k < c.length) {
            max = Math.max(sum1, Math.max(sum2, sum3));
            if (max == sum1 && max == sum2 && max == sum3) return max;

            if (max == sum1) {
                sum1 -= a[i];
                i++;
            } else if (max == sum2) {
                sum2 -= b[j];
                j++;
            } else if (max == sum3) {
                sum3 -= c[k];
                k++;
            }
        }
        // stack sum was not equal 
        return 0;
    }

    // Maximize array sum after K negations 
    public static long maxSumAfterKNegations(int[] arr, int k) {
        // sort on the basis of abs value
        Arrays.sort(arr);
        long sum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0 && k > 0) {
                arr[i] *= (-1);
                k--;
            }
        }
        int min = (int)(1e8);
        for (int val: arr) {
            sum += val;
            min = Math.min(val, min);
        }
        if ((k & 1) == 1) {
            sum -= (2 * min);
            // as sum also includes an element, so first we have to exclude it from sum
            // then reduce it's effect also
        }
        return sum;
    }

    // Minimum Cost to cut a board into squares
    public static int minCostToCutBoard(int[] rcost, int[] ccost) {
        int i = rcost.length - 1, j = ccost.length - 1;
        Arrays.sort(rcost);
        Arrays.sort(ccost);
        int vcount = 1, hcount = 1, ans = 0;
        while (i >= 0 && j >= 0) {
            if (rcost[i] > ccost[j]) {
                ans += (rcost[i] * vcount);
                hcount++;
                i--;
            } else {
                ans += (ccost[j] * hcount);
                vcount++;
                j--;
            }
        }

        while (i >= 0) {
            ans += rcost[i] * vcount;
            hcount++;
            i--;
        }

        while (j >= 0) {
            ans += ccost[j] * hcount;
            vcount++;
            j--;
        }

        return ans;
    }

    // Minimize Cash Flow among a given set of friends who have borrowed money from each other
    public static void minimizeCashFlow(int[][] graph) {
        int[] amt = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                amt[i] += (graph[j][i] - graph[i][j]);
            }
        }
        minimizeCashFlow_rec(amt);
    }

    public static void minimizeCashFlow_rec(int[] amt) {
        int[] store = getMaxCreditDebitAmtIdx(amt);
        int max_credt_idx = store[0];
        int max_debit_idx = store[1];
        if (amt[max_credt_idx] == 0 && amt[max_credt_idx] == 0) return;
        int min_amt = Math.min(amt[max_credt_idx], -amt[max_debit_idx]);
        System.out.println("person " + max_debit_idx + " pays to " + max_credt_idx);
        amt[max_debit_idx] += min_amt;
        amt[max_credt_idx] -= min_amt;
        minimizeCashFlow_rec(amt);
    }

    public static int[] getMaxCreditDebitAmtIdx(int[] amt) {
        int max = 0, min = 0;
        for (int i = 1; i < amt.length; i++) {
            if (amt[i] < amt[min]) {
                min = i;
            } 
            if (amt[i] > amt[max]) {
                max = i;
            }
        }
        // credit, debit idx
        return new int[]{max, min};
    }

    // Minimum edges to reverse to make path from a source to a destination
    static class Edge {
        int vtx, cost;

        public Edge(int vtx, int cost) {
            this.vtx = vtx;
            this.cost = cost;
        }
    }

    public static int minEdgesToReverseToMakePath(ArrayList<Edge>[] graph, int src, int dest) {
        // boolean[] vis = new boolean[graph.length];
        // minEdgesToReverseToMakePath_rec(graph, vis, src, dest);
        // return 0;
        reverseAndAddWeight1(graph);
        return dijkstras(graph, src, dest);
        
    }

    public static void minEdgesToReverseToMakePath_rec(ArrayList<Integer>[] graph, boolean[] vis, int s, int d) {
        vis[s] = true;
        if (s == d) {
            System.out.println("reached");
            return;
        }
        // find nbrs which are not visited
        int reversedEdges = 0;
        
        ArrayList<Integer> nbrList = graph[s];
        if (nbrList.size() > 0) {
            for (int nbr: nbrList) {
                if (!vis[nbr]) {
                    minEdgesToReverseToMakePath_rec(graph, vis, nbr, d);
                }
            }
        } else if (nbrList.size() == 0) {
            for (int i = 0; i < graph.length; i++) {
                if (!vis[i]) {
                    for (int val: graph[i]) {
                        if (val == s) {
                            System.out.println(i + " -> " + val + " reversed to " + val + " -> " + i);
                            minEdgesToReverseToMakePath_rec(graph, vis, i, d);
                        }
                    }
                }
            }
        }
    }

    public static int dijkstras(ArrayList<Edge>[] graph, int src, int dest) {
        PriorityQueue<Edge> pq = new PriorityQueue<>((t, o) -> {
            return t.cost - o.cost;
        });
        boolean[] vis = new boolean[graph.length];

        pq.add(new Edge(src, 0));
        while (pq.size() > 0) {
            int size = pq.size();
            while (size-- > 0) {
                Edge ce = pq.remove();
                if (vis[ce.vtx]) continue;
                vis[ce.vtx] = true;
                if (ce.vtx == dest) return ce.cost;
                
                for (Edge e: graph[ce.vtx]) {
                    if (!vis[e.vtx]) {
                        pq.add(new Edge(e.vtx, e.cost + ce.cost));
                    }
                }
            }
        } 
        return -1;
    }

    // best way to solve the problem is adding a reverse edge for each edge with weight 1
    // and add weight 0 to all old edges
    // find shortest path src -> dest using dijkstras
    public static void reverseAndAddWeight1(ArrayList<Edge>[] graph) {
        for (int i = 0; i < graph.length; i++) {
            for (Edge nbr: graph[i]) {
                addEdge(graph, nbr.vtx, i, 1);
            }
        }
    }

    public static void addEdge(ArrayList<Edge>[] graph, int src, int dest, int w) {
        graph[src].add(new Edge(dest, w));
    }

    public static void displayGraph(ArrayList<Edge>[] graph) {
        for (int i = 0; i < graph.length; i++) {
            System.out.print(i + " -> ");
            for (Edge val: graph[i]) {
                System.out.print(val.vtx + " @ " + val.cost + ", ");
            }
            System.out.println();
        }
    }

    public static void solve() {
        int n = 7;
        ArrayList<Edge>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        addEdge(graph, 0, 1, 0);
        addEdge(graph, 2, 1, 0);
        addEdge(graph, 2, 3, 0);
        addEdge(graph, 4, 5, 0);
        addEdge(graph, 5, 1, 0);
        addEdge(graph, 6, 3, 0);
        addEdge(graph, 6, 4, 0);

        int ans = minEdgesToReverseToMakePath(graph, 0, 6);
        System.out.println(ans);
    }
    
    public static void main(String args[]) {
        solve();
    }
}