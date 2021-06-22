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

    // rod cutting
    public static int rodCutting(int[] arr) {
        // by cartesian product ans or by left right method
        int[] n_arr = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            n_arr[i + 1] = arr[i];
        }
        int[] dp = new int[n_arr.length];
        dp[0] = 0;
        dp[1] = n_arr[1];
        for (int i = 2; i < dp.length; i++) {
            dp[i] = n_arr[i];
            for (int j = 1, k = i - 1; j <= k; j++, k--) {
                dp[i] = Math.max(dp[i], dp[j] + dp[k]);
            }
        }
        return dp[dp.length - 1];
    }

    // pallindrome partitioning with min cuts
    public static int minPallCut(String str) {
        boolean[][] dp1 = new boolean[str.length()][str.length()];
        for (int g = 0; g < dp1[0].length; g++) {
            for (int i = 0, j = g; j < dp1[0].length; j++, i++) {
                if (g == 0) dp1[i][j] = true;
                else if (g == 1) dp1[i][j] = (str.charAt(i) == str.charAt(j));
                else {
                    if(str.charAt(i) == str.charAt(j)) {
                        dp1[i][j] = dp1[i + 1][j - 1];
                    } else {
                        dp1[i][j] = false;
                    }
                }
            }
        }
        // O(n ^ 2)

        int[] dp2 = new int[str.length()];
        dp2[0] = 0;
        for (int j = 1; j < dp2.length; j++) {
            if (dp1[0][j]) {
                dp2[j] = 0;
                continue;
            }
            int min = (int)(1e8);
            for (int i = j; i >= 1; i--) {
                // if suffix is pall
                if (dp1[i][j]) {
                    min = Math.min(min, dp2[i - 1]);
                }
            }
            dp2[j] = min + 1;
        }
        return dp2[dp2.length - 1];

        // O(n ^ 3)
        // int[][] dp2 = new int[str.length()][str.length()]; 
        // for (int g = 0; g < dp2[0].length; g++) {
        //     for (int i = 0, j = g; j < dp2[0].length; i++, j++) {
        //         if (g == 0) dp2[i][j] = 0;
        //         else if (g == 1) {
        //             dp2[i][j] = (str.charAt(i) == str.charAt(j)) ? 0 : 1;
        //         } else {
        //             if (dp1[i][j] == false) {
        //                 int min = (int)(1e8);
        //                 for (int cp = i; cp < j; cp++) {
        //                     min = Math.min(min, dp2[i][cp] + dp2[cp + 1][j]);
        //                 }
        //                 dp2[i][j] = min + 1;
        //             } else {
        //                 dp2[i][j] = 0;
        //             }
        //         }
        //     }
        // }
        // return dp2[0][dp1[0].length - 1];
    }

    // matrix chain multiplication
    public static int mcm(int[] arr) {
        int[][] dp = new int[arr.length - 1][arr.length - 1];
        for (int g = 0; g < dp[0].length; g++) {
            for (int i = 0, j = g; j < dp[0].length; i++, j++) {
                if (g == 0) dp[i][j] = 0;
                else if (g == 1) dp[i][j] = arr[i] * arr[i + 1] * arr[j + 1];
                else {
                    int min = (int)(1e8);
                    for (int cp = i; cp < j; cp++) {
                        int left = dp[i][cp];
                        int right = dp[cp + 1][j];
                        int selfCost = arr[i] * arr[cp + 1] * arr[j + 1];
                        min = Math.min(min, left + selfCost + right);
                    }   
                    dp[i][j] = min;
                }
            }
        }
        return dp[0][dp[0].length - 1];
    }

    // boolean parenthization
    // s1 = operands, s2 = operators
    public static int booleanParenth(String s1, String s2) {
        int[][] trues = new int[s1.length()][s1.length()];
        int[][] falses = new int[s1.length()][s1.length()];

        for (int g = 0; g < trues[0].length; g++) {
            for (int i = 0, j = g; j < trues[0].length; j++, i++) {
                if (g == 0) {
                    char ch = s1.charAt(i);
                    if (ch == 'T') {
                        trues[i][j] = 1;
                        falses[i][j] = 0;
                    } else if (ch == 'F') {
                        trues[i][j] = 0;
                        falses[i][j] = 1;
                    }
                } else {
                    int ansT = 0, ansF = 0;
                    for (int cp = i; cp < j; cp++) {
                        int ltc = trues[i][cp];
                        int lfc = falses[i][cp];
                        int rtc = trues[cp + 1][j];
                        int rfc = falses[cp + 1][j];
                        char operator = s2.charAt(cp);
                        if (operator == '|') {
                            ansT += (ltc * rfc) + (lfc * rtc) + (ltc * rtc);
                            ansF += (lfc * rfc);
                        } else if (operator == '&') {
                            ansT += (ltc * rtc);
                            ansF += (lfc * rtc) + (ltc * rfc) + (lfc * rfc);
                        } else if (operator == '^') {
                            ansT += (lfc * rtc) + (ltc * rfc);
                            ansF += (ltc * rtc) + (lfc * rfc);
                        }
                        trues[i][j] = ansT;
                        falses[i][j] = ansF;
                    }
                }
            }
        }
        return trues[0][trues[0].length - 1];
    }

    // obst 
    public static int obst(int[] keys, int[] freq, int n) {
        int[] prefixSum = new int[freq.length];
        prefixSum[0] = freq[0];
        for (int i = 1; i < prefixSum.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + freq[i];
        }

        int[][] dp = new int[freq.length][freq.length];
        for (int g = 0; g < dp[0].length; g++) {
            for (int i = 0, j = g; j < dp[0].length; i++, j++) {
                if (g == 0) {
                    dp[i][j] = freq[i]; 
                } else if (g == 1) {
                    dp[i][j] = Math.min(freq[i] + 2 * freq[j], 2 * freq[i] + freq[j]);
                } else {
                    int min = (int)(1e8);
                    int fs = prefixSum[j] - ((i == 0) ? 0 : prefixSum[i - 1]);
                    for (int cp = i; cp <= j; cp++) {
                        int left = (cp == i) ? 0 : dp[i][cp - 1];
                        int right = (cp == j) ? 0 : dp[cp + 1][j];
                        min = Math.min(min, left + right + fs);
                    }
                    dp[i][j] = min;
                }
            }
        }
        return dp[0][dp[0].length - 1];
    }

    // burst balloons
    public static int burstBalloons(int[] arr) {
        int[][] dp = new int[arr.length][arr.length];
        for (int g = 0; g < dp[0].length; g++) {
            for (int i = 0, j = g; j < dp[0].length; j++, i++) {
                int max = -(int)(1e8);
                for (int cp = i; cp <= j; cp++) {
                    int left = (i == cp) ? 0 : dp[i][cp - 1];
                    int right = (j == cp) ? 0 : dp[cp + 1][j];
                    int self = arr[cp] * (i == 0 ? 1 : arr[i - 1]) * (j == arr.length - 1 ? 1 : arr[j + 1]);
                    max = Math.max(max, self + left + right);
                }
                dp[i][j] = max;
            }
        }
        return dp[0][dp[0].length - 1];
    }

    // //////////

    // probability of knight in chess board
    static int[][] dirs = {{-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}};

    public static void knightProb(int n, int k, int sr, int sc) {
        double[][] curr = new double[n][n];
        double[][] next = new double[n][n];
        curr[sr][sc] = 1;
        for (int m = 1; m <= k; m++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (curr[i][j] != 0) {
                        // knight is there in curr, then move for k + 1
                        for (int[] d : dirs) {
                            int dr = i + d[0];
                            int dc = j + d[1];
                            if (dr >= 0 && dc >= 0 && dr < n && dc < n) {
                                next[dr][dc] += curr[i][j] / 8.0;
                            }
                        }
                    }
                }
            }
            curr = next; 
            next = new double[n][n];
        }
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sum += curr[i][j];
            }
        }
        System.out.println(sum);
    }

    // optimal strategy of a game
    public static int optimalGameStr(int[] arr) {
        int[][] dp = new int[arr.length][arr.length];
        for (int g = 0; g < dp[0].length; g++) {
            for (int i = 0, j = g; j < dp[0].length; i++, j++) {
                if (g == 0) {
                    dp[i][j] = arr[i];
                } else if (g == 1) {
                    dp[i][j] = Math.max(arr[i], arr[j]);
                } else {
                    // me choose ith
                    int ansIth = arr[i] + Math.min(dp[i + 2][j], dp[i + 1][j - 1]);
                    // me choose jth 
                    int ansJth = arr[j] + Math.min(dp[i + 1][j - 1], dp[i][j - 2]);
                    dp[i][j] = Math.max(ansIth, ansJth);
                }
            }
        }
        return dp[0][dp[0].length - 1];
    }

    // Egg drop
    public static int eggDrop(int E, int F) {
        int[][] dp = new int[E + 1][F + 1];
        for (int e = 0; e < dp.length; e++) {
            for (int f = 0; f < dp[0].length; f++) {
                if (e == 1) dp[e][f] = f;
                else if (f == 0 || e == 0) dp[e][f] = 0;
                else if (f == 1) dp[e][f] = 1;
                else {
                    int min = (int)(1e8);
                    for (int mc = f - 1, pc = 0; mc >= 0; pc++, mc--) {
                        int mVal = dp[e][mc]; // egg survives : e/f - k
                        int pVal = dp[e - 1][pc]; // egg breaks: e - 1/k - 1
                        min = Math.min(min, Math.max(mVal, pVal));
                    }
                    dp[e][f] = min + 1;
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    // longest common substring 
    public static int longestCommonSubstring(String s1, String s2) {
        // comparing of both strings prrfixes and finding longest common suffix
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        int ans = -(int)(1e8);
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = 0;
                }
                ans = Math.max(dp[i][j], ans);
            }
        }
        return ans;
    }

    // longest repeating subsequence
    public static int lrs(String str) {
        int[][] dp = new int[str.length() + 1][str.length() + 1];
        for (int i = dp.length - 2; i >= 0; i--) {
            for (int j = dp[0].length - 2; j >= 0; j--) {
                if (str.charAt(i) == str.charAt(j) && i != j) {
                    dp[i][j] = dp[i + 1][j + 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        return dp[0][0];    
    }

    // min sum of ascii to delete to make both strings equal
    // same reasoning and derivation as lcs, lps
    public static int delAscii(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = dp.length - 1; i >= 0; i--) {
            for (int j = dp[0].length - 1; j >= 0; j--) {
                if (i == dp.length - 1 && j == dp[0].length - 1) {
                    dp[i][j] = 0;
                } else if(i == dp.length - 1) {
                    dp[i][j] = (int)(s2.charAt(j)) + dp[i][j + 1]; 
                } else if (j == dp[0].length - 1) {
                    dp[i][j] = (int)(s1.charAt(i)) + dp[i + 1][j];
                } else {
                    if (s1.charAt(i) == s2.charAt(j)) {
                        dp[i][j] = dp[i + 1][j + 1];
                    } else {
                        dp[i][j] = Math.min(dp[i + 1][j] + (int)(s1.charAt(i)), dp[i][j + 1] + (int)(s2.charAt(j)));
                    }
                }
            }
        }
        return dp[0][0];
    }

    // min cost to make string identical by deleting char
    public static int minCostToMakeIdentical(String s1, String s2, int c1, int c2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = dp.length - 2; i >= 0; i--) {
            for (int j = dp[0].length - 2; j >= 0; j--) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    dp[i][j] = dp[i + 1][j + 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        int lenOfLcs = dp[0][0];
        int sum = (s1.length() - lenOfLcs) * c1 + (s2.length() - lenOfLcs) * c2;
        return sum;
    }

    // distinct transformations
    public static int distinctTransformations(String src, String dest, int idx1, int idx2, int[][] dp) {
        // as 0 is also an ans, fill dp with - 1;
        if (idx1 == src.length()) {
            if (idx2 == dest.length()) {
                // src and dest both ends
                return dp[idx1][idx2] = 1;
            } else {
                return dp[idx1][idx2] = 0;
            }
        } else if (idx2 == dest.length()) {
            return dp[idx1][idx2] = 1;
        }

        if (dp[idx1][idx2] != -1) return dp[idx1][idx2];

        int totalWays = 0;
        if (src.charAt(idx1) == dest.charAt(idx2)) {
            totalWays += distinctTransformations(src, dest, idx1 + 1, idx2 + 1, dp);
            totalWays += distinctTransformations(src, dest, idx1 + 1, idx2, dp);
        } else {
            totalWays += distinctTransformations(src, dest, idx1 + 1, idx2, dp);
        }
        return dp[idx1][idx2] = totalWays;
    }

    public static int distTransDP(String src, String dest) {
        int[][] dp = new int[src.length() + 1][dest.length() + 1];
        for (int idx1 = dp.length - 1; idx1 >= 0; idx1--) {
            for (int idx2 = dp[0].length - 1; idx2 >= 0; idx2--) {
                if (idx1 == dp.length - 1) {
                    if (idx2 == dp[0].length - 1) {
                        dp[idx1][idx2] = 1;
                    } else {
                        dp[idx1][idx2] = 0;
                    }
                } else if (idx2 == dp[0].length - 1) {
                    dp[idx1][idx2] = 1;
                } else {
                    int totalWays = 0;
                    if (src.charAt(idx1) == dest.charAt(idx2)) {
                        totalWays += dp[idx1 + 1][idx2 + 1];
                        totalWays += dp[idx1 + 1][idx2];
                    } else {
                        totalWays += dp[idx1 + 1][idx2];
                    }
                    dp[idx1][idx2] = totalWays;
                }
            }
        }
        return dp[0][0];
    }

    // edit distance: s1 -> s2 
    // replace, insert, delete a character from s1 to make it s2
    public static int editDist(String s1, String s2, int i, int j, int[][] dp) {
        if (i == s1.length()) {
            if (j == s2.length()) {
                return dp[i][j] = 0;
            } else {
                return dp[i][j] = s2.length() - j;
                // by inserting one char
            }
        } else if (j == s2.length()) {
            return dp[i][j] = s1.length() - i;
        }

        if (dp[i][j] != -1) return dp[i][j];

        int minOps = 0;
        if (s1.charAt(i) == s2.charAt(j)) {
            minOps += editDist(s1, s2, i + 1, j + 1, dp);
        } else {
            // delete
            int del = editDist(s1, s2, i + 1, j, dp);
            // insert 
            int ins = editDist(s1, s2, i, j + 1, dp);
            // replace
            int rep = editDist(s1, s2, i + 1, j + 1, dp);
            minOps += Math.min(del, Math.min(ins, rep)) + 1;
        }
        return dp[i][j] = minOps;
    }

    public static int editDistDp(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = dp.length - 1; i >= 0; i--) {
            for (int j = dp[0].length - 1; j >= 0; j--) {
                if (i == dp.length - 1) {
                    if (j == dp[0].length - 1) {
                        dp[i][j] = 0;
                    } else {
                        dp[i][j] = s2.length() - j;
                    }   
                } else if (j == s2.length()) {
                    dp[i][j] = s1.length() - i;
                } else {
                    int minOps = 0;
                    if (s1.charAt(i) == s2.charAt(j)) {
                        minOps += dp[i + 1][j + 1];
                    } else {
                        // delete
                        int del = dp[i + 1][j];
                        // insert 
                        int ins = dp[i][j + 1];
                        // replace
                        int rep = dp[i + 1][j + 1];
                        minOps += Math.min(del, Math.min(ins, rep)) + 1;
                    }
                    dp[i][j] = minOps;
                }
            }
        }
        return dp[0][0];
    }

    // kadannes algo for maximum subarray sum (continuous subarray)
    public static int kadanesAlgo(int[] arr) {
        int currB = arr[0];
        int overallB = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (currB >= 0) {
                currB += arr[i];
            } else {
                currB = arr[i];
            }
            overallB = Math.max(currB, overallB);
        }
        return overallB;
    }

    // k concatenation
    public static long kadanes(int[] arr) {
        int cb = arr[0];
        int ob = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (cb >= 0) {
                cb += arr[i];
            } else {
                cb = arr[i];
            }
            ob = Math.max(cb, ob);
        }
        return ob;
    }

    public static long kadanesOf2(int[] arr) {
        int[] narr = new int[arr.length * 2];
        for (int i = 0; i < arr.length; i++) narr[i] = arr[i];
        for (int i = 0; i < arr.length; i++) narr[i + arr.length] = arr[i];
        return kadanes(narr);
    }

    public static long kCon(int[] arr, int k) {
        long sum = arr[0];
        for (int val : arr) sum += val;
        if (k == 1) {
            return kadanes(arr);
        } else if (sum < 0) {
            return kadanesOf2(arr);
        } else {
            return kadanesOf2(arr) + (sum * (k - 2));
        }
    }

    // max subarray with size k
    public static int maxSubArrWithSizeK(int[] arr, int k) {
        int ans = -(int)(1e8);
        int cb = arr[0];
        int ob = arr[0];
        int[] maxSumSubArr = new int[arr.length];
        maxSumSubArr[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (cb >= 0) {
                cb += arr[i];
            } else {
                cb = arr[i];
            }
            maxSumSubArr[i] = cb;
        }
        int exactK = 0;
        for (int i = 0; i < k; i++) {
            exactK += arr[i];

        }
        if (exactK > ans) ans = exactK;
        for (int i = k; i < arr.length; i++) {
            exactK += arr[i] - arr[i - k];
            if (exactK > ans) ans = exactK;
            int moreThanK = maxSumSubArr[i - k] + exactK;
            if (moreThanK > ans) ans = moreThanK;
        }
        return ans;
    }

    // Numeric keypad
    public static int numericKeypad(int n) {
        int[][] dp = new int[n + 1][10];
        int[][] data = {
            {0, 8},
            {1, 2, 4},
            {1, 2, 3, 5},
            {2, 3, 6},
            {1, 4, 5, 7},
            {2, 4, 5, 6, 8},
            {3, 5, 6, 9},
            {4, 7, 8},
            {5, 7, 8, 9, 0},
            {6, 8, 9}
        };
        int sum = 0;
        for (int i = 0; i < dp.length; i++) {
            sum = 0;
            for (int j = 0; j < dp[0].length; j++) {
                if (i == 0) dp[i][j] = 0;
                else if (i == 1) {
                    dp[i][j] = 1;
                } else {
                    for (int val : data[j]) {
                        dp[i][j] += dp[i - 1][val];
                    }
                }  
                sum += dp[i][j]; 
            }
        }
        return sum;
    }

    // max diff of 0s and 1s in binary string
    public static int maxDiff(String str) {
        int cb = 0, ob = -(int)(1e8);
        for (int i = 0; i < str.length(); i++) {
            int val = 0;
            char ch = str.charAt(i);
            if (ch == '1') val -= 1;
            else if (ch == '0') val += 1;
            if(cb >= 0) {
                cb += val;
            } else {
                cb = val;
            }
            ob = Math.max(ob, cb);
        }
        // or if ans == 0 , then return -1; 
        // int cb = (str.charAt(0) == '1') ? -1 : 1;
        // int ob = -(int)(1e8);
        // for (int i = 1; i < str.length(); i++) {
        //     char ch = str.charAt(i);
        //     if (cb >= 0) {
        //         cb += (ch == '1') ? -1 : 1;
        //     } else {
        //         cb = (ch == '1') ? -1 : 1;
        //     }
        //     ob = Math.max(ob, cb);
        // }
        return ob;
    }

    // arithmatic slices 1
    public static int apSlices1(int[] arr) {
        // edge cases not covered
        int[] dp = new int[arr.length];
        int ans = 0;
        for (int i = 2; i < arr.length; i++) {
            if (arr[i] - arr[i - 1] == arr[i - 1] - arr[i - 2]) {
                dp[i] = dp[i - 1] + 1;
                ans += dp[i];
            }
        }
        return ans;
    }

    public static void solve() {
      
    }

    public static void main(String args[]) {
        solve();
    }
}