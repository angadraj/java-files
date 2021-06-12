import java.util.*;
class Adv {
    // lis
    public static int longestIncSubseq(int[] arr) {
        int[] dp = new int[arr.length];
        int ans = 1; // 1 is that min length will be 1 even if 1 ele is there
        dp[0] = 1;
        for(int i = 1; i < dp.length; i++) {
            // dp[i] = lis ending at this point
            dp[i] = 1;
            int maxLen = 0;
            for(int j = 0; j < i; j++) {
                if(arr[j] < arr[i]) {
                    maxLen = Math.max(dp[j], maxLen);
                }
            }
            dp[i] += maxLen;
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    public static int maxSumIncSubseq(int[] arr) {
        int[] dp = new int[arr.length];
        dp[0] = arr[0];
        int ans = dp[0];
        for(int i = 1; i < dp.length; i++) {
            int maxSum = -(int)(1e8);
            dp[i] = arr[i];

            for(int j = 0; j < i; j++) {
                if(arr[j] <= arr[i]) {
                    maxSum = Math.max(maxSum, dp[j]);
                }
            }

            dp[i] += (maxSum == -(int)(1e8)) ? 0 : maxSum;
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    public static void findLis(int[] arr, int[] lis) {
        lis[0] = 1;
        for(int i = 1; i < lis.length; i++) {
            int maxLen = 0;
            lis[i] = 1;
            for(int j = 0; j < i; j++) {
                if(arr[j] < arr[i]) {
                    maxLen = Math.max(maxLen, lis[j]);
                }
            }
            lis[i] += maxLen;
        }
    }

    public static void findLds(int[] arr, int[] lds) {
        lds[arr.length - 1] = 1;
        for(int i = lds.length - 2; i >= 0; i--) {
            int maxLen = 0;
            lds[i] = 1;
            for(int j = i + 1; j < lds.length; j++) {
                if(arr[j] < arr[i]) {
                    maxLen = Math.max(maxLen, lds[j]);
                }
            }
            lds[i] += maxLen;
        }
    }

    public static int longestBitonicSubseq(int[] arr) {
        // inc --- dec  12345431
        int[] lis = new int[arr.length];
        int[] lds = new int[arr.length];
        findLis(arr, lis);
        findLds(arr, lds);
        int ans = 0;
        for(int i = 0; i < lis.length; i++) {
            ans = Math.max(lis[i] + lds[i] - 1, ans);
        }
        return ans;

    }

    static class bridge implements Comparable<bridge> {
        int n; 
        int s;
        public bridge(int n, int s) {
            this.n = n;
            this.s = s;
        }
        public int compareTo(bridge other) {
            if(this.n != other.n) return this.n - other.n;
            else return this.s - other.s;
        }
    }

    public static int overlappingBridges(bridge[] arr) {
        Arrays.sort(arr);
        int[] dp = new int[arr.length];
        dp[0] = 1;
        int ans = 1;
        for(int i = 1; i < dp.length; i++) {
            int maxLen = 0;
            dp[i] = 1;
            for(int j = 0; j < i; j++) {
                if(arr[j].s < arr[i].s) {
                    maxLen = Math.max(dp[j], maxLen);
                }
            }
            dp[i] += maxLen;
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    static class dolls implements Comparable<dolls> {
        int w;
        int h;
        public dolls(int w, int h) {
            this.w = w;
            this.h = h;
        }
        public int compareTo(dolls o) {
            return this.w - o.w;
        }
    }

    public static int russianDolls(dolls[] arr) {
        Arrays.sort(arr);
        int[] dp = new int[arr.length];
        dp[0] = 1;
        int ans = 0;
        for(int i = 1; i < dp.length; i++) {
            dp[i] = 1;
            int maxLen = 0;
            for(int j = 0; j < i; j++) {
                if(arr[j].h < arr[i].h && arr[j].w < arr[i].w) {
                    maxLen = Math.max(maxLen, dp[j]);
                }
            }
            dp[i] += maxLen; 
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    // string set

    // count pallindromic substrings 
    public static int pallSubstrings(String str) {
        boolean[][] dp = new boolean[str.length()][str.length()];
        int ans = 0;
        for(int gap = 0; gap < dp[0].length; gap++) {
            for(int i = 0, j = gap; j < dp[0].length; i++, j++) {
                if(gap == 0) {
                    dp[i][j] = true;
                } else if(gap == 1) {
                    dp[i][j] = (str.charAt(i) == str.charAt(j));
                } else {
                    if(str.charAt(i) == str.charAt(j)) {
                        dp[i][j] = dp[i + 1][j - 1];
                    } else dp[i][j] = false;
                }
                if(dp[i][j]) ans++;
            }
        }
        return ans;
    }

    public static int longestPallSubstring(String str) {
        boolean[][] dp = new boolean[str.length()][str.length()];
        int maxLen = 0;

        for(int g = 0; g < dp[0].length; g++) {
            for(int i = 0, j = g; j < dp[0].length; i++, j++) {
                if(g == 0) {
                    dp[i][j] = true;
                } else if(g == 1) {
                    dp[i][j] = (str.charAt(i) == str.charAt(j));
                } else {
                    if(str.charAt(i) == str.charAt(j)) {
                        dp[i][j] = dp[i + 1][j - 1];
                    } else dp[i][j] = false;
                }
                if(dp[i][j]) {
                    maxLen = Math.max(maxLen, g + 1);
                }
            }
        }
        return maxLen;
    }

    // longest common subsequence
    public static int lcs(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for(int i = dp.length - 2; i >= 0; i--) {
            for(int j = dp[0].length - 2; j >= 0; j--) {
                if(s1.charAt(i) == s2.charAt(j)) {
                    dp[i][j] = dp[i + 1][j + 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        return dp[0][0];
    }

    // longest pallindromic subseq
    public static int longestPallSubseq(String str) {
        int[][] dp = new int[str.length()][str.length()];
        for(int g = 0; g < dp[0].length; g++) {
            for(int i = 0, j = g; j < dp[0].length; i++, j++) {
                if(g == 0) dp[i][j] = 1;
                else if(g == 1) {
                    dp[i][j] = (str.charAt(i) == str.charAt(j)) ? 2 : 1;
                } else {
                    if(str.charAt(i) == str.charAt(j)) {
                        dp[i][j] = dp[i + 1][j - 1] + 2;
                    } else {
                        // prefix and suffix
                        dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                    }
                }
            }
        }
        return dp[0][dp[0].length - 1];
    }

    // count pallindromic subseq

    public static int countPallSubseq(String str) {
        int[][] dp = new int[str.length()][str.length()];
        for(int g = 0; g < dp[0].length; g++) {
            for(int i = 0, j = g; j < dp[0].length; i++, j++) {
                if(g == 0) {
                    dp[i][j] = 1;
                } else if(g == 1) {
                    dp[i][j] = (str.charAt(i) == str.charAt(j)) ? 3 : 2;
                    // aa = 3, ab = 2
                } else {
                    dp[i][j] = (str.charAt(i) == str.charAt(j)) ? dp[i + 1][j] + dp[i][j - 1] + 1 : dp[i][j - 1] + dp[i + 1][j] - dp[i + 1][j - 1]; 
                }
            }
        }
        return dp[0][dp[0].length - 1];
    }

    // count distinct subsequences of a string

    public static long countDistSubseq(String str) {
        long[] dp = new long[str.length() + 1];
        int[] map = new int[26];
        Arrays.fill(map, -1);
        dp[0] = 1;
        for(int i = 1; i < dp.length; i++) {
            char ch = str.charAt(i - 1);
            dp[i] = (dp[i - 1] * 2);
            if(map[ch - 'a'] != -1) {
                int lo = map[ch - 'a'];
                dp[i] -= dp[lo];
            }
            map[ch - 'a'] = (i - 1);
        }
        // if str length is upto 60 then 2^60 is 10^18 , so take long for it
        return dp[dp.length - 1] - 1;
    }

    // count distinct pall subseq
    public static int distPallSubseq(String str) {
        int[][] dp = new int[str.length()][str.length()];
        int[] next = new int[str.length()];
        int[] prev = new int[str.length()];
        HashMap<Character, Integer> map = new HashMap<>();
        // filling of prev
        for(int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(map.containsKey(ch) == false) {
                prev[i] = -1;
            } else {
                prev[i] = map.get(ch);
            }
            map.put(ch, i);
        }
        // filling next
        map.clear();
        for(int i = str.length() - 1; i >= 0; i--) {
            char ch = str.charAt(i);
            if(map.containsKey(ch) == false) {
                next[i] = -1;
            } else {
                next[i] = map.get(ch);
            }
            map.put(ch, i);
        }
        for(int g = 0; g < dp[0].length; g++) {
            for(int i = 0, j = g; j < dp[0].length; i++, j++) {
                if(g == 0) dp[i][j] = 1;
                else if(g == 1) dp[i][j] = 2;
                else {
                    char s = str.charAt(i);
                    char e = str.charAt(j);
                    if(s == e) {
                        int n = next[i];
                        int p = prev[j];
                        if(n > p) {
                            dp[i][j] = 2 * dp[i + 1][j - 1] + 2;
                        } else if(n == p) { 
                            dp[i][j] = 2 * dp[i + 1][j - 1] + 1;
                        } else {
                            dp[i][j] = 2 * dp[i + 1][j - 1] - dp[n + 1][p - 1];
                        }

                    } else {
                        dp[i][j] = dp[i + 1][j] + dp[i][j - 1] - dp[i + 1][j - 1];
                    }
                }
            }
        }
        return dp[0][dp[0].length - 1];
    }

    // print set and bfs in reverse engieering

    static class pair {
        int len;
        int idx;
        int val;
        int jumpsReq;
        int jumpsTaken;
        String psf;
        int r;
        int c;
        pair(int len, int idx, int val, String psf) {
            this.len = len;
            this.idx = idx;
            this.val = val;
            this.psf = psf; 
        }
        pair(int idx, int jumpsReq, int jumpsTaken, String psf) {
            this.idx = idx;
            this.jumpsReq = jumpsReq;
            this.jumpsTaken = jumpsTaken;
            this.psf = psf;
        }
        pair(int r, int c, String psf) {
            this.r = r;
            this.c = c;
            this.psf = psf;
        }
    } 

    // print min cost paths
    public static void printMinCostPaths(int[][] arr) {
        int[][] dp = new int[arr.length][arr[0].length];
        for(int i = dp.length - 1; i >= 0; i--) {
            for(int j = dp[0].length - 1; j >= 0; j--) {
                if(i == dp.length - 1 && j == dp[0].length - 1) {
                    dp[i][j] = arr[i][j];
                    continue;
                } 
                int rightAns = (j + 1 < dp[0].length) ? dp[i][j + 1] : (int)(1e8); 
                int bottomAns = (i + 1 < dp.length) ? dp[i + 1][j] : (int)(1e8); 
                dp[i][j] = Math.min(rightAns, bottomAns) + arr[i][j];
            }
        }

        Queue<pair> qu = new LinkedList<>();
        qu.add(new pair(0, 0, ""));
        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                pair cp = qu.remove();

                if(cp.r == dp.length - 1 && cp.c == dp[0].length - 1) {
                    System.out.println(cp.psf);
                    continue;
                }

                int rightAns = (cp.c + 1 < dp[0].length) ? dp[cp.r][cp.c + 1] : (int)(1e8);
                int bottomAns = (cp.r + 1 < dp.length) ? dp[cp.r + 1][cp.c] : (int)(1e8);
                if(rightAns == bottomAns) {
                    qu.add(new pair(cp.r + 1, cp.c, cp.psf + "V")); 
                    qu.add(new pair(cp.r, cp.c + 1, cp.psf + "H"));
                } else if(rightAns < bottomAns) {
                    qu.add(new pair(cp.r, cp.c + 1, cp.psf + "H"));
                } else {
                    qu.add(new pair(cp.r + 1, cp.c, cp.psf + "V"));
                }
            }
        }
    }

    // print lis
    public static void printLIS(int[] arr) {
        int[] dp = new int[arr.length];
        dp[0] = 1;
        int ans = 0;
        Queue<pair> qu = new LinkedList<>();

        for(int i = 0; i < dp.length; i++) {
            int maxLen = 0;
            for(int j = 0; j < i; j++) {
                if(arr[j] <= arr[i]) {
                    maxLen = Math.max(maxLen, dp[j]);
                }
            }
            dp[i] = maxLen + 1;
            ans = Math.max(ans, dp[i]);

        }
        System.out.println(ans);
        // add vals in qu
        for(int i = 0; i < dp.length; i++) {
            if(dp[i] == ans)  qu.add(new pair(ans, i, arr[i], "" + arr[i]));
        }
        // reverse engineering , bfs
        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                pair cp = qu.remove();
                if(cp.len == 1) {
                    System.out.println(cp.psf);
                }
                for(int j = cp.idx; j >= 0; j--) {
                    if(arr[j] <= cp.val && dp[j] == cp.len - 1) {
                        qu.add(new pair(dp[j], j, arr[j], arr[j] + " -> " + cp.psf));
                    }
                }
            }
        }

    }

    // print all paths with minm jumps
    public static void printPathsWithMinJumps(int[] arr) {
        int[] dp = new int[arr.length];
        dp[dp.length - 1] = 0;
        for(int i = dp.length - 2; i >= 0; i--) {
            int jump = arr[i];
            int minAns = (int)(1e8);
            for(int j = 1; j <= jump && (i + j) < dp.length; j++) {
                minAns = Math.min(dp[i + j], minAns);
            }
            dp[i] = (minAns == (int)(1e8)) ? (int)(1e8) : minAns + 1;
        }

        System.out.println(dp[0]);

        Queue<pair> qu = new LinkedList<>();
        qu.add(new pair(0, arr[0], dp[0], 0 + ""));

        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                pair cp = qu.remove();

                if(cp.jumpsTaken == 0 && cp.idx == dp.length - 1) {
                    System.out.println(cp.psf + " . ");
                }

                for(int j = 1; j <= cp.jumpsReq && (cp.idx + j) < dp.length; j++) {
                    int cidx = cp.idx + j;
                    if(dp[cidx] != (int)(1e8) && dp[cidx] == cp.jumpsTaken - 1) {
                        qu.add(new pair(cidx, arr[cidx], dp[cidx], cp.psf + " -> " + cidx));
                    }
                }

            }
        }
    }

    // print paths with max gold
    public static void printPathMaxGold(int[][] arr) {
        int[][] dp = new int[arr.length][arr[0].length];
        int myans = 0;
        for(int j = dp[0].length - 1; j >= 0; j--) {
            for(int i = dp.length - 1; i >= 0; i--) {
                if(j == dp[0].length - 1) {
                    dp[i][j] = arr[i][j];

                } else if(i == 0) {
                    dp[i][j] = Math.max(dp[i][j + 1], dp[i + 1][j + 1]) + arr[i][j];
                    
                } else if(i == dp.length - 1) {
                    dp[i][j] = Math.max(dp[i][j + 1], dp[i - 1][j + 1]) + arr[i][j];
                    
                }
                else {
                    dp[i][j] = Math.max(dp[i - 1][j + 1], Math.max(dp[i][j + 1], dp[i + 1][j + 1])) + arr[i][j];
                }
                if(j == 0) {
                    myans = Math.max(myans, dp[i][j]);
                }
            }
        }
        System.out.println(myans);

        Queue<pair> qu = new LinkedList<>();
        for(int i = 0; i < dp.length; i++) {
            if(dp[i][0] == myans) {
                qu.add(new pair(i, 0, i + " "));
            }
        }
        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                pair cp = qu.remove();
                if(cp.c == dp[0].length - 1) {
                    System.out.println(cp.psf);
                }
                else if(cp.r == 0) {
                    int ans = Math.max(dp[cp.r][cp.c + 1], dp[cp.r + 1][cp.c + 1]);
                    if(ans == dp[cp.r][cp.c + 1]) {
                        qu.add(new pair(cp.r, cp.c + 1, cp.psf + "d2 "));
                    } 
                    if(ans == dp[cp.r + 1][cp.c + 1]) {
                        qu.add(new pair(cp.r + 1, cp.c + 1, cp.psf + "d3 "));
                    }
                } else if(cp.r == dp.length - 1) {
                    int ans = Math.max(dp[cp.r][cp.c + 1], dp[cp.r - 1][cp.c + 1]);
                    if(ans == dp[cp.r - 1][cp.c + 1]) {
                        qu.add(new pair(cp.r - 1, cp.c + 1, cp.psf + "d1 "));
                    }
                    if(ans == dp[cp.r][cp.c + 1]) {
                        qu.add(new pair(cp.r, cp.c + 1, cp.psf + "d2 "));
                    } 
                } else {
                    int ans = Math.max(dp[cp.r][cp.c + 1], Math.max(dp[cp.r - 1][cp.c + 1], dp[cp.r + 1][cp.c + 1]));
                    if(ans == dp[cp.r - 1][cp.c + 1]) {
                        qu.add(new pair(cp.r - 1, cp.c + 1, cp.psf + "d1 "));
                    } 
                    if(ans == dp[cp.r][cp.c + 1]) {
                        qu.add(new pair(cp.r, cp.c + 1, cp.psf + "d2 "));
                    } 
                    if(ans == dp[cp.r + 1][cp.c + 1]) {
                        qu.add(new pair(cp.r + 1, cp.c + 1, cp.psf + "d3 "));
                    }
                }
            }
        }
    }

    // print target sum subsets paths
    public static void printTargetSumSubsets(int[] arr, int target) {
        boolean[][] dp = new boolean[arr.length + 1][target + 1];
        for(int i = 0; i < dp.length; i++) {
            for(int tar = 0; tar < dp[0].length; tar++) {
                if(tar == 0 && i == 0) dp[i][tar] = true;
                else if(tar == 0) dp[i][tar] = true;
                else if(i == 0) dp[i][tar] = false;
                else {
                    // choice of no
                    dp[i][tar] = dp[i - 1][tar];
                    // choice of yes 
                    if(tar - arr[i] >= 0) {
                        boolean yesVal = dp[i - 1][tar - arr[i - 1]];
                        dp[i][tar] = dp[i][tar] || yesVal;
                    }
                }
            }
        }
        System.out.println(dp[dp.length - 1][dp[0].length - 1]);
        if(!dp[dp.length - 1][dp[0].length - 1]) return;

        Queue<pair> qu = new LinkedList<>();
        qu.add(new pair(dp.length - 1, dp[0].length - 1, ""));

        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                pair cp = qu.remove();
                // either elements are finished or target is over
                if(cp.r == 0 || cp.c == 0) {
                    System.out.println(cp.psf);
                    continue;
                } 

                if(cp.c - arr[cp.r - 1] >= 0) {
                    boolean yesVal = dp[cp.r - 1][cp.c - arr[cp.r - 1]];
                    if(yesVal) qu.add(new pair(cp.r - 1, cp.c - arr[cp.r - 1], (cp.r - 1) + " " + cp.psf));
                }

                boolean noVal = dp[cp.r - 1][cp.c];
                if(noVal) qu.add(new pair(cp.r - 1, cp.c, cp.psf));
            }
        } 
    }

    // print paths of 0-1 knapSack 
    public static void print01KnapSackPaths(int[] P, int[] W, int cap) {
        int[][] dp = new int[P.length + 1][cap + 1];
        for(int i = 0; i < dp.length; i++) {
            for(int j = 0; j < dp[0].length; j++) {
                if(i == 0 || j == 0) {
                    dp[i][j] = 0;
                    continue;
                }
                int exc = dp[i - 1][j];
                int inc = 0;
                if(j - W[i - 1] >= 0) {
                    inc = dp[i - 1][j - W[i - 1]] + P[i - 1];
                }
                dp[i][j] = Math.max(exc, inc);
            }
        }
        System.out.println(dp[dp.length - 1][dp[0].length - 1]);
        Queue<pair> qu = new LinkedList<>();
        qu.add(new pair(dp.length - 1, dp[0].length - 1, ""));

        while(qu.size() > 0) {
            pair cp = qu.remove();
            if(cp.r == 0 || cp.c == 0) {
                System.out.println(cp.psf);
                continue;
            }
            int inc = 0;
            if(cp.c - W[cp.r - 1] >= 0) {
                inc = dp[cp.r - 1][cp.c - W[cp.r - 1]] + P[cp.r - 1];
            }
            int exc = dp[cp.r - 1][cp.c];
            if(inc > exc) qu.add(new pair(cp.r - 1, cp.c - W[cp.r - 1], (cp.r - 1) + " " + cp.psf));
            else qu.add(new pair(cp.r - 1, cp.c, cp.psf));
        }
    }

    // wild cart matching
    public static boolean wildCardMatching(String str, String pat) {
        boolean[][] dp = new boolean[pat.length() + 1][str.length() + 1];
        for(int i = dp.length - 1; i >= 0; i--) {
            for(int j = dp[0].length - 1; j >= 0; j--) {
                if(j == dp[0].length - 1 && i == dp.length - 1) dp[i][j] = true;
                else if(j == dp[0].length - 1) {
                    if(pat.charAt(i) == '*') dp[i][j] = dp[i + 1][j];
                    else dp[i][j] = false;
                } else if(i == dp.length - 1) {
                    dp[i][j] = true;
                } else {
                    if(pat.charAt(i) == '?') {
                        dp[i][j] = dp[i + 1][j + 1];
                    } else if(pat.charAt(i) == '*') {
                        dp[i][j] = (dp[i][j + 1] || dp[i + 1][j]);
                    }  else if(pat.charAt(i) == str.charAt(j)) {
                        dp[i][j] = dp[i + 1][j + 1];
                    } else {
                        dp[i][j] = false;
                    }
                }
            }
        }
        return dp[0][0];
    }

    // regex matching
    public static boolean regExMatch(String str, String pat) {
        boolean[][] dp = new boolean[pat.length() + 1][str.length() + 1];
        for(int i = 0; i < dp.length; i++) {
            for(int j = 0; j < dp[0].length; j++) {
                if(i == 0 && j == 0) {
                    dp[i][j] = true;
                } else if (i ==0) {
                    dp[i][j] = false;
                } else if (j == 0) {
                    char ch = pat.charAt(i - 1);
                    if(ch == '*') {
                        dp[i][j] = dp[i - 2][j];
                    } else {
                        dp[i][j] = false;
                    }
                } else {
                    char p = pat.charAt(i - 1);
                    char t = str.charAt(j - 1);
                    if(p == '*') {
                        dp[i][j] = dp[i - 2][j];
                        char scndLAstCharP = pat.charAt(i - 2);
                        if(scndLAstCharP == '.' || scndLAstCharP == t) {
                            dp[i][j] = dp[i][j] || dp[i][j - 1];
                        }
                    } else if (p == t || p == '.') {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = false;
                    }
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    // catalan series
    public static int catalanNum(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] += dp[j] * dp[i - j - 1];
            }
        }
        return dp[n];   
    }

    // number of bsts
    public static int numBsts(int n) {
        return catalanNum(n);
    }

    // valleys and mountains
    public static int countValleysMount(int n) {
        return catalanNum(n);
    }

    // count number of brackets
    public static int countBrackets(int n) {
        return catalanNum(n);
    }
 
    public static int NumberOfChords(int n) {
        // this must be in long
        return catalanNum(n);
    }

    public static int triangulationWays(int n) {
        if(n == 0 || n == 1) return 0;
        int[] dp = new int[n + 1];
        dp[2] = 1;
        dp[3] = 1;
        for(int i = 4; i < dp.length; i++) {
            for(int j = 2; j < i; j++) {
                dp[i] += dp[j] * dp[i - j + 1];
            }
        }
        return dp[dp.length - 1];
        // return catalanNum(n - 2);
    }

    public static int scoreOfTriangulation(int[] arr) {
        int[][] dp = new int[arr.length][arr.length];
        for(int g = 0; g < dp[0].length; g++) {
            for(int i = 0, j = g; j < dp[0].length; i++, j++) {
                if (g == 0) dp[i][j] = 0;
                else if (g == 1) dp[i][j] = 0;
                else if (g == 2) dp[i][j] = arr[i] * arr[i + 1] * arr[j]; 
                else {
                    int ans = (int)(1e8);  
                    for (int cp = i + 1; cp < j; cp++) {
                        int currTriCost = arr[i] * arr[cp] * arr[j];
                        int leftCost = dp[i][cp];
                        int rightCost = dp[cp][j];
                        ans = Math.min(ans, currTriCost + leftCost + rightCost);
                    }
                    dp[i][j] = ans;
                }
            }
        }
        return dp[0][dp[0].length - 1];
    }

    // Dyk words: cn ways
    // maze paths above diagonal: cn - 1 ways

    public static void solve() {
      
    }

    public static void main(String args[]) {
        solve();
    }
}