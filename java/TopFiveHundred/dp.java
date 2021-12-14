import java.util.*;
class dp {

    // ugly numbers
    public static int uglyNum(int n) {
        int[] dp = new int[n + 1];
        int p2 = 1, p3 = 1, p5 = 1;
        dp[1] = 1;
        for (int i = 2; i < dp.length; i++) {
            int val2 = 2 * dp[p2];
            int val3 = 3 * dp[p3];
            int val5 = 5 * dp[p5];
            int min = Math.min(val2, Math.min(val5, val3));
            dp[i] = min;
            if (val2 == min) p2++;
            if (val3 == min) p3++;
            if (val5 == min) p5++;
        }
        return dp[n];
    }

    // ugly numbers 2
    static class uglyPair {
        int val, idx, baseVal;
        public uglyPair(int val, int idx, int baseVal) {
            this.val = val;
            this.idx = idx;
            this.baseVal = baseVal;
        }
    }
    
    public static int uglyNum2(int n, int[] primes) {
        int[] dp = new int[n + 1];
        PriorityQueue<uglyPair> pq = new PriorityQueue<>((t, o) -> {
            return t.val - o.val;
        });
        for (int i = 0; i < primes.length; i++) {
            pq.add(new uglyPair(primes[i], 1, primes[i]));
        }
        dp[1] = 1;
        int i = 2;
        while (pq.size() > 0 && i < dp.length) {
            uglyPair cp = pq.remove();
            if (dp[i - 1] != cp.val) dp[i++] = cp.val;
            // we will not multiply baseval * cp.idx + 1 because it will produce tables of factors
            // we need to multiply base vals with ugly numbers to produce new ugly numbers
            pq.add(new uglyPair(dp[cp.idx + 1] * cp.baseVal, cp.idx + 1, cp.baseVal));
        }
        return dp[n];
    }

    public static int uglyNum22(int n, int[] primes) {
        int[] ptrs = new int[primes.length];
        Arrays.fill(ptrs, 1);
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i < dp.length; i++) {
            int min = (int)(1e8);
            for (int j = 0; j < primes.length; j++) {
                min = Math.min(min, primes[j] * dp[ptrs[j]]);
            }
            dp[i] = min;
            for (int j = 0; j < primes.length; j++) {
                if (primes[j] * dp[ptrs[j]] == min) {
                    ptrs[j]++;
                }
            }
        }
        return dp[n];
    }
    
    // max size square submatrix of all ones
    // faith => considering dp[i][j] to be the top left value, how big we can make square submatrix
    public static int maxSizeSquareSubMatrix(int[][] arr) {
        int[][] dp = new int[arr.length][arr[0].length];
        int ans = 0;
        for (int i = dp.length - 1; i >= 0; i--) {
            for (int j = dp[0].length - 1; j >= 0; j--) {
                if (i == dp.length - 1 && j == dp[0].length - 1) {
                    dp[i][j] = arr[i][j];
                } else if (i == dp.length - 1) {
                    dp[i][j] = arr[i][j];
                } else if (j == dp[0].length - 1) {
                    dp[i][j] = arr[i][j];
                } else {
                    if (arr[i][j] == 0) {
                        dp[i][j] = 0;
                    } else {
                        int min = Math.min(dp[i + 1][j], Math.min(dp[i][j + 1], dp[i + 1][j + 1]));
                        dp[i][j] = min + 1;
                    }
                }
                ans = Math.max(ans, dp[i][j]);
            }
        }
        return ans;
    }

    // Subset Sum Problem
    public static boolean subSetSum(int[] arr, int tar) {
        // Boolean[][] dp = new Boolean[arr.length + 1][tar + 1];
        // return subSetSum_rec(arr, tar, arr.length, dp);
        return subSetSum_dp(arr, tar);
    }

    public static boolean subSetSum_rec(int[] arr, int tar, int n, Boolean[][] dp) {
        if (n == 0 || tar == 0) {
            if (tar == 0) return true;
            return false;
        }

        if (dp[n][tar] != null) return dp[n][tar];
        // inc
        int val = arr[n - 1];
        if (tar - val >= 0) {
            boolean recAns1 = subSetSum_rec(arr, tar - val, n - 1, dp);
            dp[n][tar] = recAns1;
            if (recAns1) return true;
        }
        // exc
        return dp[n][tar] = subSetSum_rec(arr, tar, n - 1, dp);
    }

    public static boolean subSetSum_dp(int[] arr, int T) {
        boolean[][] dp = new boolean[arr.length + 1][T + 1];
        for (int n = 0; n < dp.length; n++) {
            for (int tar = 0; tar < dp[0].length; tar++) {
                if (n == 0 || tar == 0) {
                    if (tar == 0) dp[n][tar] = true;
                    else dp[n][tar] = false;
                    continue;
                }
                
                int val = arr[n - 1];
                boolean inc = false, exc = false;
                if (tar - val >= 0) {
                    inc = dp[n - 1][tar - val];
                }
                exc = dp[n - 1][tar];
                dp[n][tar] = (inc | exc);
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    // Minimum number of jumps to reach end
    public static int minJumpsToReachEnd(int[] arr) {
        if (arr[0] == 0) return -1;
        Integer[] dp = new Integer[arr.length];
        int ans = minJumpsToReachEnd_rec(arr, 0, dp);
        if (ans >= (int)(1e8)) return -1;
        return ans;
    }

    // jump game 2 : leetcode
    public static int minJumpsToReachEnd_rec(int[] arr, int idx, Integer[] dp) {        
        if (idx == arr.length - 1) {
            if (arr[idx] == 0) return (int)(1e8);
            return 0;
        }

        if (dp[idx] != null) return dp[idx];

        int jumps = arr[idx], selfCount = (int)(1e8);
        for (int j = 1; j <= jumps && idx + j < arr.length; j++) {
            if (arr[idx + j] > 0) {
                int recAns = minJumpsToReachEnd_rec(arr, idx + j, dp);
                if (recAns != (int)(1e8) && recAns + 1 < selfCount) {
                    selfCount = recAns + 1;
                }

            }
        }
        // if (selfCount == (int)(1e8)) return dp[idx] = 0;
        return dp[idx] = selfCount;
    }

    // jump game 2 : assume you will always reach end
    public static int minJumpsToReachEnd_rec2(int[] arr, int idx, Integer[] dp) {
        if (idx == arr.length - 1) return 0;
        if (dp[idx] != null) return dp[idx];
        
        int jumps = arr[idx], min = (int)(1e8);
        if (jumps != 0) {
            for (int j = 1; j <= jumps && idx + j < arr.length; j++) {
                int self = minJumpsToReachEnd_rec2(arr, idx + j, dp);
                min = Math.min(min, self);
            }
        }
        return (dp[idx] = min != (int)(1e8) ? min + 1 : min);
    }

    public static int minJumpsToReachEnd_dp2(int[] arr) {
        int[] dp = new int[arr.length];
        for (int idx = dp.length - 1; idx >= 0; idx--) {
            if (idx == dp.length - 1) {
                dp[idx] = 0;
                continue;
            }
            int jumps = arr[idx], min = (int)(1e8);
            if (jumps != 0) {
                for (int j = 1; j <= jumps && idx + j < arr.length; j++) {
                    int self = dp[idx + j];
                    min = Math.min(min, self);
                }
            }
            return (dp[idx] = min != (int)(1e8) ? min + 1 : min);
        }
        return dp[0];
    }

    public static int minJumpsToReachEnd_opti(int[] arr) {
        if (arr.length == 1) return 0;
        int curr_ptr = 0, jumps = 0, reachable_idx = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            int j = arr[i];
            // j = current jump
            reachable_idx = Math.max(reachable_idx, i + j);
            if (i == curr_ptr) {
                j++;
                curr_ptr = reachable_idx;
            }
        }
        return jumps;
    }

    // longest bitonic subsequence
    public static int longestBitonicSubseq(int[] arr) {
        int n = arr.length;
        int[] lisdp = getLisLds(arr, true);
        int[] ldsdp = getLisLds(arr, false);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, lisdp[i] + ldsdp[i] - 1);
        }
        return ans;
    }

    public static int[] getLisLds(int[] arr, boolean flag) {
        int[] dp = new int[arr.length];
        if (flag) {
            dp[0] = 1;
            for (int i = 1; i < arr.length; i++) {
                dp[i] = 1;
                int maxLen = 0;
                for (int j = 0; j < i; j++) {
                    if (arr[j] < arr[i]) {
                        maxLen = Math.max(maxLen, dp[j]);
                    } 
                }
                dp[i] = maxLen + 1; 
            }
        } else {
            dp[arr.length - 1] = 1;
            for (int i = arr.length - 2; i >= 0; i--) {
                dp[i] = 1;
                int maxLen = 0;
                for (int j = i + 1; j < dp.length; j++) {
                    if (arr[j] < arr[i]) {
                        maxLen = Math.max(maxLen, dp[j]);
                    } 
                }
                dp[i] = maxLen + 1; 
            }
        }
        return dp;
    }

    // maximum sum bitonic subseq
    public static int longestBitonicSubseqSum(int[] arr) {
        int n = arr.length;
        int[] lisdp = getLisLdsSum(arr, true);
        int[] ldsdp = getLisLdsSum(arr, false);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, lisdp[i] + ldsdp[i] - arr[i]);
        }
        return ans;
    }

    public static int[] getLisLdsSum(int[] arr, boolean flag) {
        int[] dp = new int[arr.length];
        if (flag) {
            dp[0] = arr[0];
            for (int i = 1; i < arr.length; i++) {
                dp[i] = arr[i];
                int maxSum = 0;
                for (int j = 0; j < i; j++) {
                    if (arr[j] < arr[i]) {
                        maxSum = Math.max(maxSum, dp[j]);
                    } 
                }
                dp[i] += maxSum; 
            }
        } else {
            dp[arr.length - 1] = arr[arr.length - 1];
            for (int i = arr.length - 2; i >= 0; i--) {
                dp[i] = arr[i];
                int maxSum = 0;
                for (int j = i + 1; j < dp.length; j++) {
                    if (arr[j] < arr[i]) {
                        maxSum = Math.max(maxSum, dp[j]);
                    } 
                }
                dp[i] += maxSum; 
            }
        }
        return dp;
    }

    // lcs of three strings 
    public static int lcsThreeStrings(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length(), o = s3.length();
        Integer[][][] dp = new Integer[m + 1][n + 1][o + 1];
        return lcsThreeStrings_rec(s1, s2, s3, 0, 0, 0, dp);
    }

    public static int lcsThreeStrings_rec(String s1, String s2, String s3, int i, int j, int k, Integer[][][] dp) {
        if (i >= s1.length() || j >= s2.length() || k >= s3.length()) {
            return 0;
        }
        
        if (dp[i][j][k] != null) return dp[i][j][k];

        char chi = s1.charAt(i), chj = s2.charAt(j), chk = s3.charAt(k);
        int ans = 0;
        if ((chi == chj) && (chj == chk) && (chk == chi)) {
            int recAns = lcsThreeStrings_rec(s1, s2, s3, i + 1, j + 1, k + 1, dp) + 1;
            ans = Math.max(recAns, ans);
        } else {
            int recAns1 = lcsThreeStrings_rec(s1, s2, s3, i + 1, j, k, dp);
            int recAns2 = lcsThreeStrings_rec(s1, s2, s3, i, j + 1, k, dp);
            int recAns3 = lcsThreeStrings_rec(s1, s2, s3, i, j, k + 1, dp);
            ans = Math.max(recAns1, Math.max(recAns2, recAns3));
        }
        return dp[i][j][k] = ans;
    }

    public static int lcsThreeStrings_dp(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length(), o = s3.length();
        int[][][] dp = new int[m + 1][n + 1][o + 1];
        for (int i = m; i >= 0; i--) {
            for (int j = n; j >= 0; j--) {
                for (int k = o; k >= 0; k--) {
                    if (i >= s1.length() || j >= s2.length() || k >= s3.length()) {
                        dp[i][j][k] = 0;
                        continue;
                    }
                    char chi = s1.charAt(i), chj = s2.charAt(j), chk = s3.charAt(k);
                    int ans = 0;
                    if ((chi == chj) && (chj == chk) && (chk == chi)) {
                        int recAns = dp[i + 1][j + 1][k + 1] + 1;
                        ans = Math.max(recAns, ans);
                    } else {
                        int recAns1 = dp[i + 1][j][k];
                        int recAns2 = dp[i][j + 1][k];
                        int recAns3 = dp[i][j][k + 1];
                        ans = Math.max(recAns1, Math.max(recAns2, recAns3));
                    }
                    dp[i][j][k] = ans;
                }
            }
        }
        return dp[0][0][0];
    }

    // friends pairing problem
    public static int friendsPairing(int n) {
        int[] dp = new int[n + 1];
        for (int i = 0; i < dp.length; i++) {
            if (i <= 2) dp[i] = i;
            else {
                dp[i] = dp[i - 1] + ((i - 1) * dp[i - 2]);
            }
        }
        return dp[dp.length - 1];
    }

    // building bridges
    static class bridge {
        int nc, sc;
        public bridge(int nc, int sc) {
            this.nc = nc;
            this.sc = sc;
        }
    }
    public static int buildingBridges(int[] n, int[] s) {
        bridge[] arr = new bridge[n.length];
        for (int i = 0; i < n.length; i++) {
            arr[i] = new bridge(n[i], s[i]);
        }
        // sort on the basis of north cordinate
        Arrays.sort(arr, (t, o) -> {
            if (t.nc != o.nc) return t.nc - o.nc;
            else return t.sc - o.sc;
        });
        // now make find lis on south cordinate
        int[] dp = new int[n.length];
        dp[0] = 1;
        int ans = 0;
        for (int i = 1; i < n.length; i++) {
            dp[i] = 1;
            int maxLen = 0;
            for (int j = 0; j < i; j++) {
                if (arr[j].sc <= arr[i].sc) {
                    maxLen = Math.max(maxLen, dp[j]);
                }
            }
            dp[i] += maxLen;
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    // partition problem
    public static boolean partitionProblem(int[] arr) {
        int sum = 0;
        for (int val: arr) sum += val;
        if ((sum & 1) == 1) return false;
        // Boolean[][] dp = new Boolean[arr.length][(sum / 2) + 1];
        // recursion will find if there is any subset whose sum equals total_sum / 2;
        // return partitionProblem_rec(arr, sum / 2, 0, 0, dp);
        return partitionProblem_dp(arr, sum / 2);
    }

    public static boolean partitionProblem_rec(int[] arr, int sum, int idx, int asf, Boolean[][] dp) {
        if (idx >= arr.length || asf == sum) {
            if (asf == sum) return true;
            return false;
        }
        if (asf > sum) return false;
        if (dp[idx][asf] != null) return dp[idx][asf];
        // inc
        boolean recAns1 = partitionProblem_rec(arr, sum, idx + 1, asf + arr[idx], dp);
        if (recAns1) return dp[idx][asf] = true;
        // exc
        boolean recAns2 = partitionProblem_rec(arr, sum, idx + 1, asf, dp);
        return dp[idx][asf] = recAns2;
    }   

    public static boolean partitionProblem_dp(int[] arr, int S) {
        boolean[][] dp = new boolean[arr.length + 1][S + 1];
        for (int idx = dp.length - 1; idx >= 0; idx--) {
            for (int asf = dp[0].length - 1; asf >= 0; asf--) {
                if (idx >= arr.length || asf == S) {
                    if (asf == S) dp[idx][asf] = true;
                    else dp[idx][asf] = false;
                    continue;
                }
                dp[idx][asf] = dp[idx + 1][asf];
                if (asf + arr[idx] < dp[0].length) {
                    dp[idx][asf] |= dp[idx + 1][asf + arr[idx]];
                }
            }
        }
        return dp[0][0];
    }

    // Count number of ways to partition a set into k subsets
    public static int partitionSetIntoKSubsets(int n, int k) {
        // Integer[][] dp = new Integer[n + 1][k + 1];
        // return partitionSetIntoKSubsets_rec(n, k, dp);
        return partitionSetIntoKSubsets_dp(n, k);
    }

    public static int partitionSetIntoKSubsets_rec(int n, int k, Integer[][] dp) {
        if (n == k || k == 1) return 1;
        if (k > n) return 0;
        if (k == 0 || n == 0) return 0;

        if (dp[n][k] != null) return dp[n][k];
        // when one element makes it's own subset
        int ans1 = partitionSetIntoKSubsets_rec(n - 1, k - 1, dp);
        // when an element decides to collab with the k subsets 
        int ans2 = partitionSetIntoKSubsets_rec(n - 1, k, dp) * k;
        return dp[n][k] = (ans1 + ans2);
    }

    public static int partitionSetIntoKSubsets_dp(int N, int K) {
        int[][] dp = new int[N + 1][K + 1];
        for (int n = 1; n < dp.length; n++) {
            for (int k = 1; k < dp[0].length; k++) {
                if (n == k || k == 1) dp[n][k] = 1;
                else {
                    int ans1 = dp[n - 1][k - 1];
                    // when an element decides to collab with the k subsets 
                    int ans2 = dp[n - 1][k] * k;
                    dp[n][k] = (ans1 + ans2);
                }
            }
        }
        return dp[N][K];
    }

    // longest pallindromic subseq
    public static int longestPallSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int g = 0; g < n; g++) {
            for (int i = 0, j = g; j < n; i++, j++) {
                if (g == 0) dp[i][j] = 1;
                else if (g == 1) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j)) ? 2 : 1;
                } else {
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = dp[i + 1][j - 1] + 2;
                    } else {
                        dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                    }
                }
            }
        }
        return dp[0][dp[0].length - 1];
    }

    // egg drop
    public static int eggDrop(int f, int e) {
        Integer[][] dp = new Integer[f + 1][e + 1];
        return eggDrop_rec(f, e, dp);
    }

    public static int eggDrop_rec(int f, int e, Integer[][] dp) {
        if (f == 0 || e == 0) return 0;
        if (f == 1) return 1;
        if (e == 1) return f;
        
        if (dp[f][e] != null) return dp[f][e];

        int min = (int)(1e8);
        // this is about min of the maxes
        for (int k = 1; k <= f; k++) {
            int breaks = eggDrop_rec(f - k, e - 1, dp);
            int survives = eggDrop_rec(k - 1, e, dp);
            // if survives and you were standing on 29th floor, then proceed to 28th
            min = Math.min(min, Math.max(breaks, survives));
        }
        return dp[f][e] = min + 1;
    }

    public static int eggDrop_dp(int F, int E) {
        int[][] dp = new int[F + 1][E + 1];
        for (int f = 1; f < dp.length; f++) {
            for (int e = 1; e < dp[0].length; e++) {
                if (f == 1) dp[f][e] = 1;
                else if (e == 1) dp[f][e] = f;
                else {
                    int min = (int)(1e8);
                    // this is about min of the maxes
                    for (int k = 1; k <= f; k++) {
                        int breaks = dp[f - k][e - 1];
                        int survives = dp[k - 1][e];
                        // if survives and you were standing on 29th floor, then proceed to 28th
                        min = Math.min(min, Math.max(breaks, survives));
                    }
                    dp[f][e] = min + 1;
                }
            }
        }
        return dp[F][E];
    }

    // Box Stacking Problem
    static class boxPair {
        int h,l, w;
        public boxPair(int h, int l, int w) {
            this.h = h;
            this.l = l;
            this.w = w;
        }
    }

    public static int boxStackingProblem(int[] h, int[] l, int[] w) {
        int n = h.length;
        boxPair[] arr = new boxPair[3 * n];
        for (int i = 0; i < n; i++) {
            arr[3 * i] = new boxPair(h[i], Math.max(l[i], w[i]), Math.min(l[i], w[i]));
            arr[3 * i + 1] = new boxPair(l[i], Math.max(h[i], w[i]), Math.min(h[i], w[i]));
            arr[3 * i + 2] = new boxPair(w[i], Math.max(h[i], l[i]), Math.min(h[i], l[i]));
        }
        Arrays.sort(arr, (t, o) -> {
            return (o.l * o.w) - (t.l * t.w);
        });
        int[] dp = new int[3 * n];
        dp[0] = arr[0].h;
        int ans = 0;

        for (int i = 1; i < dp.length; i++) {
            dp[i] = arr[i].h;
            int maxHeight = 0;
            // now I have to stack ith on jth
            for (int j = 0; j < i; j++) {
                if (arr[j].w > arr[i].w && arr[j].l > arr[i].l) {
                    maxHeight = Math.max(maxHeight, dp[j]);
                }
            }
            dp[i] += maxHeight;
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    // optimal binary search tree
    public static int obst(int[] key, int[] freq) {
        int n = key.length;
        int[][] dp = new int[n][n];
        int[] psa = new int[n];
        psa[0] = freq[0];
        for (int i = 1; i < n; i++) psa[i] = psa[i - 1] + freq[i];

        for (int g = 0; g < n; g++) {
            for (int si = 0, ei = g; ei < n; ei++, si++) {
                if (g == 0) dp[si][ei] = freq[si];
                else if (g == 1) {
                    dp[si][ei] = Math.min(freq[si] + 2 * freq[ei], 2 * freq[si] + freq[ei]);
                } else {
                    int min = (int)(1e8);
                    int self = psa[ei] - ((si > 0) ? psa[si - 1] : 0);
                    for (int cp = si; cp <= ei; cp++) {
                        int left = (si > cp - 1) ? 0 : dp[si][cp - 1];
                        int right = (cp + 1 > ei) ? 0 : dp[cp + 1][ei];
                        min = Math.min(min, left + right + self);
                    }
                    dp[si][ei] = min;
                }
            }
        }
        return dp[0][dp.length - 1];
    }

    // Minimum insertions to form a palindrome
    // Given string str, the task is to find the minimum number of characters to 
    // be inserted to convert it to a palindrome.
    // it is basically the need of extra left out char from longest pall of that string
    public static int minInsertionsToMakePall(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int g = 0; g < n; g++) {
            for (int i = 0, j = g; j < n; j++, i++) {
                if (g == 0) dp[i][j] = 1;
                else {
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = dp[i + 1][j - 1] + 2;
                    } else {
                        dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                    }
                }
            }
        }
        return n - dp[0][n - 1];
    }

    // Maximum Product Cutting
    // given n, cut the rope into piecies such that product of length of piecies is max
    public static int ropeCutting(int n) {
        if (n <= 2) return 1;
        int[] dp = new int[n + 1];
        dp[1] = dp[2] = 1;
        for (int i = 3; i < dp.length; i++) {
            int max = -(int)(1e8);
            for (int j = 1, k = i - 1; j <= k; j++, k--) {
                max = Math.max(max, j * k);
                max = Math.max(max, dp[j] * dp[k]);
            }
            dp[i] = max;
        }
        return dp[n];
    }
    
    // optimal game strategy
    public static int optimalGameStr(int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n][n];
        for (int g = 0; g < n; g++) {
            for (int i = 0, j = g; j < n; i++, j++) {
                if (g == 0) dp[i][j] = arr[i];
                else if (g == 1) dp[i][j] = Math.max(arr[i], arr[j]);
                else {
                    // assume oppo picks the maximum one such that user is left with minimum one
                    // user picks ith -> oppo picks i + 1 th  -> user has min(i + 2, j)
                    // or oppo picks jth -> user picks min(i + 1, j - 1)
                    int ith = arr[i] + Math.min(dp[i + 2][j], dp[i + 1][j - 1]);
                    // user picked jth -> oppo picked ith -> user has (i + 1, j - 1)
                    // oppo picked jth -> user has (i, j - 2)
                    int jth = arr[j] + Math.min(dp[i + 1][j - 1], dp[i][j - 2]);
                    dp[i][j] = Math.max(ith, jth);
                }
            }
        }
        return dp[0][n - 1];
    }

    // word break
    public static boolean wordBreakDp(String s, ArrayList<String> set) {
        int n = s.length();
        int[] dp = new int[n];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j <= i; j++) {
                String partOfString = s.substring(j, i + 1);
                if (set.contains(partOfString)) {
                    dp[i] += (j - 1 > 0) ? dp[j - 1] : 1;
                }
            }
        }
        // basically this means if there was a point j somewhere in s, from where we cut off a substring
        // j to i + 1 and it was part of dictionary.
        // or you can if total x valid sentences formed till j - 1 and dp[j] > 0
        // then till jth we can make x + dp[j] sentences
        return dp[n - 1] > 0;
    }

    // given a keypad in which we need to count total number of n length which can be made
    // by pressing the btns on keypad
    // you can press the btn in top, left, bottom, right
    // you can't press btns -> * and #
    // to make a n length number, you need to press it n times
    public static int keyPadDp(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 10;
        char[][] arr = {{'1', '2', '3'}, 
                        {'4', '5', '6'},
                        {'7', '8', '9'},
                        {'*', '0', '#'}};
        int[][] dirs = {{0, 0}, {-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] != '*' && arr[i][j] != '#') {
                    Integer[][][] dp = new Integer[n + 1][arr.length][arr[0].length];
                    ans += keyPadDp_rec(arr, dirs, n, i, j, dp);
                }
            }
        }
        return ans;
    }

    public static int keyPadDp_rec(char[][] arr, int[][] dirs, int n, int sr, int sc, Integer[][][] dp) {
        if (n == 1) return 1;
        if (dp[n][sr][sc] != null) return dp[n][sr][sc];
        int self = 0;
        for (int[] d: dirs) {
            int dr = sr + d[0];
            int dc = sc + d[1];
            if (dr >= 0 && dr < arr.length && 
                dc >= 0 && dc < arr[0].length && 
                arr[dr][dc] != '*' && arr[dr][dc] != '#') {

                int recAns = keyPadDp_rec(arr, dirs, n - 1, dr, dc, dp);
                self += recAns;
            }
        }
        return dp[n][sr][sc] = self;
    }

    public static int keyPadDp_dp(char[][] arr, int[][] dirs, int N) {
        int[][] dp = new int[N + 1][10];
        int[][] data = {{0, 8}, 
                        {1, 2, 4}, 
                        {1, 2, 3, 5}, 
                        {2, 3, 6},
                        {1, 4, 5, 7},
                        {2, 4, 5, 6, 8},
                        {3, 5, 6, 9},
                        {4, 7, 8},
                        {5, 7, 8, 9, 0},
                        {6, 8, 9}};

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (i == 0) dp[i][j] = 0;
                else if (i == 1) dp[i][j] = 1;
                else {
                    // now consider every jth key pressed as last key
                    for (int d: data[j]) {
                        dp[i][j] += dp[i - 1][d];
                        // ex 0 pressed as last key for i = 3 
                        // 3[0] = 2[0, 8]
                        // as for 0 we can only press 0 and 8, so we need to consider 0 or 8 pressed as last
                        // key for n == 2
                    }
                }
            }
        }
        // at last we need to give sum of all ways for ith row
        int ans = 0;
        for (int j = 0; j < dp[0].length; j++) {
            ans += dp[N - 1][j];
        }
        return ans;
    }

    // Find number of solutions of a linear equation of n variables
    // infinite combination 
    public static int numberOfSolLinearEqn(int[] coins, int tar) {
        int[] dp = new int[tar + 1];
        dp[0] = 1;
        for (int i = 0; i < coins.length; i++) {
            int coin = coins[i];
            for (int t = 1; t <= tar; t++) {
                if (t - coin >= 0) {
                    dp[t] += dp[t - coin];
                }
            }
        } 
        // time : n * rhs
        return dp[tar];
    }

    // Count the number of ways to tile the floor of size n x m using 1 x m size tiles
    public static int floorTileNM(int m, int n) {
        // tile size: m X 1
        int[] dp = new int[n + 1];
        for (int i = 1; i < dp.length; i++) {
            // i is basically "n"
            if (i < m) {
                dp[i] = 1;
                /// you can not place any tile vertically
            } else if (i == m) {
                // 4 X 4 and 1 X 4 tile
                // two ways: either h or v
                dp[i] = 2;
            } else {
                // vertical + (1 * horizontal)
                dp[i] = dp[i - 1] + (1 * dp[i - m]);
            }
        }
        return dp[dp.length - 1];
    }

    // Count number of binary strings without consecutive 1’s
    public static int binaryStringWithNoConsec1s(int n) {
        if (n == 1) return 2;
        int oldZeros = 1, oldOnes = 1;
        for (int len = 2; len <= n; len++) {
            int newZeros = oldOnes + oldZeros;
            int newOnes = oldZeros;

            oldOnes = newOnes;
            oldZeros = newZeros;
        }
        return oldOnes + oldZeros;
    }

    //// ******************* IMPORTANT (BINARY SEARCH APPLICATION) **************************

    // painter's partition problem
    // similar to dividing job among workers with constraint problem
    public static int paintersPartition(int[] arr, int k) {
        int ei = 0, max = arr[0];
        for (int val: arr) {
            ei += val;
            max = Math.max(max, val);
        }
        int si = 0, dividedWork = 0;
        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (mid >= max && canDivideWork(arr, k, mid)) {
                dividedWork = mid;
                ei = mid - 1;
            } else {
                si = mid + 1;
            }
        }
        // if there were per unit time cost for workers then
        // you ans = dividedWork * T
        return dividedWork;
    }

    public static boolean canDivideWork(int[] arr, int k, int mid) {
        int workers = 1, sum = 0, i = 0;
        while (i < arr.length) {
            if (sum + arr[i] > mid) {
                workers++;
                sum = 0;
            } else {
                sum += arr[i];
                i++;
            }
        }
        if (workers <= k) return true;
        else return false;
    }

    // ***************************************************************************************

    // interleaving strings
    public static boolean interleavingStr(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) return false;
        Boolean[][] dp = new Boolean[s1.length()][s2.length()];
        return interleavingStr_rec(s1, s2, s3, 0, 0, dp);
    }

    public static boolean interleavingStr_rec(String s1, String s2, String s3, int i, int j, Boolean[][] dp) {
        if (i == s1.length() && j == s2.length()) return true;
        
        if (i < s1.length() && s1.charAt(i) == s3.charAt(i + j)) {
            boolean recAns1 = interleavingStr_rec(s1, s2, s3, i + 1, j, dp);
            if (recAns1) return dp[i][j] = true;
        } 
        if (j < s2.length() && s2.charAt(j) == s3.charAt(i + j)) {
            boolean recAns2 = interleavingStr_rec(s1, s2, s3, i, j + 1, dp);
            if (recAns2) return dp[i][j] = true;
        }
        return dp[i][j] = false;
    }

    public static boolean interleavingStr_dp(String s1, String s2, String s3) {
        boolean[][] dp = new boolean[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (i == 0 && j == 0) dp[i][j] = true;
                else if (i == 0) {
                    if (s2.charAt(j - 1) == s3.charAt(i + j - 1)) {
                        dp[i][j] = dp[i][j - 1];
                    } else dp[i][j] = false;
                } else if (j == 0) {
                    if (s1.charAt(i - 1) == s3.charAt(i + j - 1)) {
                        dp[i][j] = dp[i - 1][j];
                    } else dp[i][j] = false;
                } else {
                    if (s1.charAt(i - 1) == s3.charAt(i + j - 1)) {
                        dp[i][j] = dp[i - 1][j];
                    }
                    if (!dp[i][j] && s2.charAt(j - 1) == s3.charAt(i + j - 1)) {
                        dp[i][j] = dp[i][j - 1];
                    }
                }
            } 
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    // wildcard pattern matching
    public static boolean wildcardPattern(String s, String p) {
        Boolean[][] dp = new Boolean[s.length() + 1][p.length() + 1];
        return wildcardPattern_rec(s, p, 0, 0, dp);
    }

    public static boolean wildcardPattern_rec(String s, String p, int i, int j, Boolean[][] dp) {
        int ns = s.length(), np = p.length();
        if (i >= ns && j >= np) return true;
        if (i <= ns && j == np - 1 && p.charAt(j) == '*') return true; 
        if (i >= ns && j < np ) return false;
        if (i < ns && j >= np) return false;

        if (dp[i][j] != null) return dp[i][j];

        boolean self = false;
        
        char chs = s.charAt(i), chp = p.charAt(j);
        if ((chs == chp) || chp == '?') {
            self |= wildcardPattern_rec(s, p, i + 1, j + 1, dp);

        } else if (chp == '*') {
            self |= wildcardPattern_rec(s, p, i, j + 1, dp);
            self |= wildcardPattern_rec(s, p, i + 1, j, dp);
        } else if (chs != chp) {
            self = false;
        }
        return dp[i][j] = self;
    }

    public static boolean wildcardPattern_dp(String s, String p) {
        int n = p.length(), m = s.length();
        boolean[][] dp = new boolean[n + 1][m + 1];
        for (int i = dp.length - 1; i >= 0; i--) {
            for (int j = dp[0].length - 1; j >= 0; j--) {
                if (i == dp.length - 1 && j == dp[0].length - 1) {
                    // empty vs empty
                    dp[i][j] = true;
                } else if (i == dp.length - 1) dp[i][j] = false;
                else if (j == dp[0].length - 1) {
                    if (p.charAt(i) == '*') {
                        dp[i][j] = dp[i + 1][j];
                        // * became blank, and asked pattern to move forward ie i + 1
                    } else dp[i][j] = false;
                } else {
                    char chs = s.charAt(j), chp = p.charAt(i);
                    if (chs == chp || chp == '?') {
                        dp[i][j] = dp[i + 1][j + 1];
                    } else if (chp == '*') {
                        dp[i][j] = dp[i + 1][j] || dp[i][j + 1];
                    } else if (chs != chp) {
                        dp[i][j] = false;
                    }
                }
            }
        }
        return dp[0][0];
    }

    // probability of knight in chessboard
    public static double knightProbability(int n, int sr, int sc, int k) {
        int[][] dirs = {{-2, -1}, {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}};
        int[][] chess = new int[n][n];
        Double[][][] dp = new Double[n][n][k + 1];
        double ans = knightProbability_rec(chess, dirs, sr, sc, k, dp);
        return ans;
    }

    public static double knightProbability_rec(int[][] arr, int[][] dirs, int sr, int sc, int k, Double[][][] dp) {
        if (k == 0) return 1;

        if (dp[sr][sc][k] != null) return dp[sr][sc][k];
        
        double self = 0.0;
        for (int[] d: dirs) {
            int dr = sr + d[0];
            int dc = sc + d[1];
            if (dr >= 0 && dc >= 0 && dr < arr.length && dc < arr[0].length) {
                self += knightProbability_rec(arr, dirs, dr, dc, k - 1, dp);
            }
        }
        // for every level there will be 8 points , and p(e) = total valid pts / 8 ^ k
        return dp[sr][sc][k] = (self / 8);
    }

    public static double knightProbability_dp(int n, int sr, int sc, int K, int[][] dirs) {
        double[][][] dp = new double[n][n][K + 1];        
        for (int k = 0; k <= K; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (k == 0) {
                        dp[i][j][k] = 1;
                        continue;
                    }
                    double self = 0.0;
                    for (int[] d: dirs) {
                        int dr = i + d[0];
                        int dc = j + d[1];
                        if (dr >= 0 && dc >= 0 && dr < n && dc < n) {
                            self += dp[dr][dc][k - 1];
                        }
                    }
                    dp[i][j][k] = (self / 8);
                }
            }
        }
        return dp[sr][sc][K];
    }

    // The Two Water Jug Puzzle
    // euclids algo ??

    // Word Wrap Problem
    public static String wordWrap(String[] words, int W) {
        int n = words.length;
        int[][] cost = new int[n][n];
        for (int i = 0; i < n; i++) {
            cost[i][i] = W - words[i].length();
            // ith word used his space, 
            // cost[i][i] = remaining spaces for incoming word
            for (int j = i + 1; j < n; j++) {
                cost[i][j] = cost[i][j - 1] - words[j].length() - 1;
                // fit current jth word and also use 1 extra space to saperate it from prev 
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (cost[i][j] < 0) cost[i][j] = (int)(1e8);
                else {
                    cost[i][j] *= cost[i][j];
                }
            }
        }

        int[] minCost = new int[n];
        int[] res = new int[n];
        // mincost will have min cost and res will store the index res[i] = val
        // in one line words[i] to words[val - 1] can come together
        for (int i = n - 1; i >= 0; i--) {
            minCost[i] = cost[i][n - 1];
            res[i] = n;
            // this is the spliting loop 
            for (int j = n - 1; j > i; j--) {
                if (cost[i][j - 1] == (int)(1e8)) continue;
                else if (minCost[i] > cost[i][j - 1] + minCost[j]) {
                    minCost[i] = cost[i][j - 1] + minCost[j];
                    res[i] = j;
                }
            }
        }

        for (int val: minCost) System.out.print(val + " ");

        // min cost = minCost[0]
        StringBuilder sb = new StringBuilder();
        int i = 0, j = 0;
        while (j < n) {
            j = res[i];
            for (int k = i; k < j; k++) {
                sb.append(words[k] + " ");
            }
            sb.append("\n");
            i = j;
        }
        System.out.println("cost is " + minCost[0]);
        return sb.toString();
    }

    // Largest sum subarray with at-least k numbers
    public static int subArrSumAtleastkEle(int[] arr, int k) {
        int n = arr.length; 
        int[] maxSum = kadannes(arr);
        int sum = 0, res = -(int)(1e8);
        for (int i = 0; i < n; i++) {
            if (i < k) {
                sum += arr[i];
            } else {
                // find max sliding window sum exactly k ele
                sum += arr[i] - arr[i - k];
                res = Math.max(res, sum);
                // now consider more than k with subarray sum from maxSum
                res = Math.max(res, sum + maxSum[i - k]);
            }
        }
        return res;
    }

    public static int[] kadannes(int[] arr) {
        int n = arr.length;
        int[] ans = new int[n];
        int sum = 0, res = -(int)(1e8);
        for (int i = 0; i < n; i++) {
            if (sum >= 0) {
                sum += arr[i];
            } else {
                sum = arr[i];
            }
            ans[i] = Math.max(sum, res);
        }
        return ans;
    }

    // water in glass
    // all glasses are of 1 lt
    public static double waterInGlass(int k, int r, int c) {
        double[][] dp = new double[r + 1][c + 1];
        dp[0][0] = k;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j <= i; j++) {
                if (dp[i][j] > 1.0) {
                    // overflow
                    double spare = dp[i][j] - 1.0;
                    dp[i][j] = 1.0;
                    dp[i + 1][j] += (spare / 2);
                    dp[i + 1][j + 1] += (spare / 2);
                }
            }
        }
        return dp[r - 1][c - 1];
    }

    // Remove minimum elements from either side such that 2*min becomes more than max
    public static int removeMinEle2MinMax(int[] arr) {
        Arrays.sort(arr);
        // for (int i = 0; i < 94; i++) System.out.print(arr[i] + " ");
        int ans = (int)(1e8), n = arr.length;
        for (int i = n - 1; i >= 0; i--) {
            int j = 0;
            while (j < i && 2 * arr[j] < arr[i]) {
                j++;
            }
            // j is on desired min
            // ans = Math.min(ans, j + n - i - 1);
            if (j + n - i - 1 < ans) {
                ans = j + n - i - 1;
            }
        }
        return ans;
    }

    public static int removeMinEle2MinMax_opti(int[] arr) {
        Arrays.sort(arr);
        int ans = (int)(1e8), n = arr.length;
        for (int i = n - 1; i >= 0; i--) {
            int si = 0, ei = i - 1, idx = -1;
            while (si <= ei) {
                int mid = (si + ei) >> 1;
                if (2 * arr[mid] >= arr[i]) {
                    idx = mid;
                    ei = mid - 1;
                } else {
                    si = mid + 1;
                }
            }
            // idx is on desired min
            if (idx != -1 && ans > idx + n - i - 1) {
                ans = idx + n - i - 1;
            }
        }
        return ans;
    }

    // Unbounded Knapsack (Repetition of items allowed)
    public static int unboundedKnapSack(int[] profits, int[] wt, int W) {
        int n = profits.length;
        // Integer[][] dp = new Integer[n + 1][W + 1];
        // int ans = unboundedKnapSack_rec(profits, wt, W, n, dp);
        int ans = unboundedKnapSack_dp(profits, wt, W);
        if (ans < 0) return 0;
        return ans;
    }

    public static int unboundedKnapSack_rec(int[] profits, int[] wts, int W, int n, Integer[][] dp) {
        if (n == 0 || W < 0 || W == 0) {
            if (W == 0) return 0;
            return -(int)(1e8);
        }

        if (dp[n][W] != null) return dp[n][W];

        int profit = profits[n - 1];
        int wt = wts[n - 1];
        int inc = unboundedKnapSack_rec(profits, wts, W - wt, n, dp) + profit;
        int exc = unboundedKnapSack_rec(profits, wts, W, n - 1, dp);
        return dp[n][W] = Math.max(inc, exc);
    }

    public static int unboundedKnapSack_dp(int[] profits, int[] wts, int cap) {
        int N = profits.length;
        int[][] dp = new int[N + 1][cap + 1];
        for (int n = 0; n < dp.length; n++) {
            for (int W = 0; W < dp[0].length; W++) {
                if (n == 0 || W == 0) {
                    dp[n][W] = 0;
                    continue;
                }
                int profit = profits[n - 1];
                int wt = wts[n - 1];
                int inc = ((W - wt >= 0) ? dp[n][W - wt] : -(int)(1e8)) + profit;
                int exc = dp[n - 1][W];
                dp[n][W] = Math.max(inc, exc);
            }
        }
        return dp[N][cap];
    }

    public static int unboundedKnapSack_opti(int[] profits, int[] wts, int cap) {
        // inf combi dp
        int[] dp = new int[cap + 1];
        int n = profits.length;
        for (int i = 0; i < n; i++) {
            int currProfit = profits[i];
            int currWt = wts[i];
            int ans = 0;
            // include this as much as you can
            for (int j = 1; j < dp.length; j++) {
                if (j - currWt >= 0) {
                    dp[j] = Math.max(dp[j], dp[j - currWt] + currProfit);
                }
            }
        }
        return dp[dp.length - 1];
    }

    // Length of the longest valid substring
    public static int longestValidSubString(String s) {
        Stack<Integer> st = new Stack<>();
        st.push(-1);
        int len = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') st.push(i);
            else {
                if (st.size() != 0) st.pop();
                if (st.size() != 0) {
                    len = Math.max(len, i - st.peek());
                } else {
                    st.push(i);
                    // it will form the basis for another character
                }
            }
        }
        return len;
    }
    
    public static int longestValidSubString_opti(String s) {
        int l = 0, r = 0, len = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') l++;
            else r++;
            if (l == r) len = Math.max(len, 2 * l);
            else if (r > l) {
                // ie : (())) -> len = 2, but at last char reset l and r
                l = r = 0;
            }
        }
        l = r = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (ch == '(') l++;
            else r++;
            if (l == r) len = Math.max(len, 2 * l);
            else if (l > r) {
                // ie : (((())) -> len = 3, but at first char reset l and r
                l = r = 0;
            }
        }
        return len;
    }

    // boolean parenthization
    static class bp_pair {
        int tc = 0, fc = 0;
        public bp_pair(int tc, int fc) {
            this.tc = tc;
            this.fc = fc;
        }
    }

    public static int booleanParenthization(String s) {
        // T^F|F    
        int n = s.length();
        bp_pair[][] dp = new bp_pair[n][n];
        bp_pair ans = booleanParenthization_dp(s, dp);
        return ans.tc;
    }

    public static bp_pair booleanParenthization_rec(String s, int si, int ei,  bp_pair[][] dp) {
        if (si == ei) {
            bp_pair baseAns = new bp_pair(0, 0);
            char ch = s.charAt(ei);
            if (ch == 'T') {
                baseAns.tc = 1;
            } else if (ch == 'F') {
                baseAns.fc = 1;
            }
            return baseAns;
        }

        if (dp[si][ei] != null) return dp[si][ei];

        bp_pair self = new bp_pair(0, 0);
        for (int cp = si + 1; cp < ei; cp += 2) {
            char optr = s.charAt(cp);
            bp_pair left = booleanParenthization_rec(s, si, cp - 1, dp);
            bp_pair right = booleanParenthization_rec(s, cp + 1, ei, dp);
            booleanParenthization_eval(left, right, self, optr);
        }
        return dp[si][ei] = self;
    }

    public static bp_pair booleanParenthization_dp(String s, bp_pair[][] dp) {
        for (int g = 0; g < dp.length; g++) {
            for (int si = 0, ei = g; ei < dp[0].length; si++, ei++) {
                if (si == ei) {
                    // g == 0
                    bp_pair baseAns = new bp_pair(0, 0);
                    char ch = s.charAt(ei);
                    if (ch == 'T') {
                        baseAns.tc = 1;
                    } else if (ch == 'F') {
                        baseAns.fc = 1;
                    }
                    dp[si][ei] = baseAns;
                    continue;
                }

                bp_pair self = new bp_pair(0, 0);
                for (int cp = si + 1; cp < ei; cp += 2) {
                    char optr = s.charAt(cp);
                    bp_pair left = dp[si][cp - 1];
                    bp_pair right = dp[cp + 1][ei];
                    booleanParenthization_eval(left, right, self, optr);
                }
                dp[si][ei] = self;
            }
        }
        return dp[0][dp[0].length - 1];
    }

    public static void booleanParenthization_eval(bp_pair p1, bp_pair p2, bp_pair ans, char op) {
        if (op == '|') {
            ans.tc += (p1.tc * p2.fc) + (p1.fc * p2.tc) + (p1.tc * p2.tc);
            ans.fc += (p1.fc * p2.fc);

        } else if (op == '^') {
            ans.tc += (p1.tc * p2.fc) + (p1.fc * p2.tc);
            ans.fc += (p1.tc * p2.tc) + (p1.fc * p2.fc);

        } else if (op == '&') {
            ans.tc += (p1.tc * p2.tc);
            ans.fc += (p1.tc * p2.fc) + (p1.fc * p2.tc) + (p1.fc * p2.fc);
        }
    }

    public static void booleanParenthization_eval_mod(bp_pair p1, bp_pair p2, bp_pair ans, char op, int mod) {
        if (op == '|') {
            ans.tc += (p1.tc * p2.fc) % mod + (p1.fc * p2.tc) % mod + (p1.tc * p2.tc) % mod;
            ans.tc %= mod;
            ans.fc += (p1.fc * p2.fc) % mod;
            ans.fc %= mod;

        } else if (op == '^') {
            ans.tc += (p1.tc * p2.fc) % mod + (p1.fc * p2.tc) % mod;
            ans.tc %= mod;
            ans.fc += (p1.tc * p2.tc) % mod + (p1.fc * p2.fc) % mod;
            ans.fc %= mod;

        } else if (op == '&') {
            ans.tc += (p1.tc * p2.tc) % mod;
            ans.tc %= mod;
            ans.fc += (p1.tc * p2.fc) % mod + (p1.fc * p2.tc) % mod + (p1.fc * p2.fc) % mod;
            ans.fc %= mod;
        }
    }

    // Count Possible Decodings of a given Digit Sequence
    // s = "123" map 1 -> A, 2 -> B
    public static int countDecodings(String s) {
        // Integer[] dp = new Integer[s.length() + 1];
        // return countDecodings_rec(s, 0, dp);
        return countDecodings_dp(s);
    }

    public static int countDecodings_rec(String s, int idx, Integer[] dp) {
        if (idx >= s.length()) {
            return 1;
        }

        if (dp[idx] != null) return dp[idx];

        int self = 0;
        int num1 = s.charAt(idx) - '0';

        if (num1 == 0) return 0;

        self += countDecodings_rec(s, idx + 1, dp);
        // now make 2 digit number
        if (idx < s.length() - 1) {
            int num2 = s.charAt(idx + 1) - '0';
            int num = num1 * 10 + num2;
            if (num <= 26) {
                self += countDecodings_rec(s, idx + 2, dp);
            }
        }
        return dp[idx] = self;
    }

    public static int countDecodings_dp(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        for (int idx = dp.length - 1; idx >= 0; idx--) {
            if (idx >= s.length()) {
                dp[idx] = 1;
                continue;
            }

            int self = 0;
            int num1 = s.charAt(idx) - '0';
            if (num1 == 0) continue;
            self += dp[idx + 1];
            // now make 2 digit number
            if (idx < s.length() - 1) {
                int num2 = s.charAt(idx + 1) - '0';
                int num = num1 * 10 + num2;
                if (num <= 26) {
                    self += dp[idx + 2];
                }
            }
            dp[idx] = self;
        }
        return dp[0];
    }

    // Perfect Sum Problem (Print all subsets with given sum)
    public static int countSubsetsOfGivenSum(int[] arr, int tar) {
        Integer[][] dp = new Integer[arr.length + 1][tar + 1];
        return countSubsetsOfGivenSum_rec(arr, tar, arr.length, dp);
    }

    public static int countSubsetsOfGivenSum_rec(int[] arr, int tar, int idx, Integer[][] dp) {
        if (idx == 0 || tar == 0 || tar < 0) {
            if (tar == 0) return 1;
            return 0; 
        }

        if (dp[idx][tar] != null) return dp[idx][tar];
        int self = 0;
        int val = arr[idx - 1];
        self += countSubsetsOfGivenSum_rec(arr, tar - val, idx - 1, dp);
        self += countSubsetsOfGivenSum_rec(arr, tar, idx - 1, dp);
        return dp[idx][tar] = self;
    }    

    public static int countSubsetsOfGivenSum_dp(int[] arr, int T) {
        int[][] dp = new int[arr.length + 1][T + 1];
        for (int idx = 0; idx < dp.length; idx++) {
            for (int tar = 0; tar < dp[0].length; tar++) {
                if (idx == 0 || tar == 0 || tar < 0) {
                    if (tar == 0) dp[idx][tar] = 1;
                    else dp[idx][tar] = 0;
                    continue;
                }
                int self = 0;
                int val = arr[idx - 1];
                self += (tar - val >= 0) ? dp[idx - 1][tar - val] : 0;
                self += dp[idx - 1][tar];
                dp[idx][tar] = self;
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    // vertex cover problem
    // trees ??

    // Longest Even Length Substring such that Sum of First and Second Half is same
    // we will expand from the mid points of all substrings
    // minm length of substrings == 2 (minm even length)
    public static int maxEvenLenEqualSumSubString(String s) {
        int ans = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            int l = i, r = i + 1, lsum = 0, rsum = 0;
            while (l >= 0 && r < s.length()) {
                lsum += s.charAt(l) - '0';
                rsum += s.charAt(r) - '0';
                if (lsum == rsum) {
                    ans = Math.max(ans, r - l + 1);
                }
                l--; r++;
            }
        }
        return ans;
    }

    // Count possible ways to construct buildings
    public static int constructBuildings(int n) {
        // for n = 1, we can have either building or space
        // ie = 2 ways 
        int building = 1, space = 1;
        for (int i = 2; i <= n; i++) {
            int new_building = space;
            int new_space = space + building;
            building = new_building;
            space = new_space;
        }
        // ways for single plot
        int total = building + space;
        // for n plots 
        return total * total;
    }

    // count ways to map distinct caps to people
    // ??

    // Longest repeating subsequence
    public static int longestRepeatingSubSeq(String s) {
        int n = s.length();
        int[][] dp = new int[n + 1][n + 1];
        for (int i = dp.length - 2; i >= 0; i--) {
            for (int j = dp[0].length - 2; j >= 0; j--) {
                char chi = s.charAt(i), chj = s.charAt(j);
                if (chi == chj && i != j) {
                    dp[i][j] = dp[i + 1][j + 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        return dp[0][0];
    }

    // print LIS
    
    // Longest Common Increasing Subsequence (LCS + LIS)
    public static int longestCommonIncSubSeq(int[] a, int[] b) {
        int[] dp = new int[b.length];
        int ans = 0;
        for (int i = 0; i < a.length; i++) {
            int len = 0;
            for (int j = 0; j < b.length; j++) {
                if (a[i] == b[j] && len + 1 > dp[j]) {
                    dp[j] = len + 1;
                } 
                // now check for prev smaller val
                // case: a[i] 9, b = {3, 9} and d[3](ele) = 1 
                if (a[i] > b[j] && dp[j] > len) {
                    len = dp[j];
                }
            }
        }
        for (int val: dp) ans = Math.max(val, ans);
        return ans;
    }

    // Find if string is K-Palindrome or not 
    // A k-palindrome string transforms into a palindrome on removing at most k characters from it.
    /// if passes gfg test cases
    public static int kPallindrome(String s, int k) {
        int count = 0, n = s.length();
        for (int i = 0, j = n - 1; i <= j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) count += 2;
        }
        // even length then remove count - 1 chars 
        int ans = 0;
        if ((n & 1) == 0) ans = count - 1;
        else ans = count;

        if (ans <= k) return 1;
        else return 0;
    }

    public static int kPallindrome_dp(String str, int k) {
        int n = str.length();
        int[][] dp = new int[n][n];
        for(int gap = 0; gap < n; gap++) { 
            for(int si = 0, ei = gap; ei < n; ei++, si++) {
                if (gap == 0){
                    dp[si][ei] = 1;
                } else {
                    if (str.charAt(si) == str.charAt(ei)) {
                        dp[si][ei] = dp[si+1][ei-1] + 2;
                    } else {
                        dp[si][ei] = Math.max(dp[si][ei-1], dp[si+1][ei]);
                    }
                }
            }
        }
        return (n - dp[0][n-1]) <= k ? 1 : 0;
    }

    // Minimum Sum Path In 3-D Array
    // we have to reach from 1st cell to last cell
    // we can go to adjacent cell in fwd direction
    public static int minPathSum3D(int[][][] arr) {
        int ans = minPathSum3D_dp(arr);
        return ans;
    }

    public static int minPathSum3D_dp(int[][][] arr) {
        int l = arr.length, m = arr[0].length, n = arr[0][0].length;
        int[][][] dp = new int[l][m][n];
        dp[0][0][0] = arr[0][0][0];
        for (int i = 1; i < l; i++) dp[i][0][0] = dp[i - 1][0][0] + arr[i][0][0];
        for (int j = 1; j < m; j++) dp[0][j][0] = dp[0][j - 1][0] + arr[0][j][0];
        for (int k = 1; k < n; k++) dp[0][0][k] = dp[0][0][k - 1] + arr[0][0][k];

        for (int i = 1; i < l; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j][0] = Math.min(dp[i - 1][j][0], Math.min(dp[i][j - 1][0], (int)(1e8))) + arr[i][j][0];
            }
        }

        for (int i = 1; i < l; i++) {
            for (int k = 1; k < n; k++) {
                dp[i][0][k] = Math.min(dp[i - 1][0][k], Math.min(dp[i][0][k - 1], (int)(1e8))) + arr[i][0][k];
            }
        }

        for (int j = 1; j < m; j++) {
            for (int k = 1; k < n; k++) {
                dp[0][j][k] = Math.min(dp[0][j - 1][k], Math.min(dp[0][j][k - 1], (int)(1e8))) + arr[0][j][k];
            }
        }

        for (int i = 1; i < l; i++) {
            for (int j = 1; j < m; j++) {
                for (int k = 1; k < n; k++) {
                    dp[i][j][k] = Math.min(dp[i - 1][j][k], Math.min(dp[i][j - 1][k], dp[i][j][k - 1])) + arr[i][j][k];
                }
            }
        }

        return dp[l - 1][m - 1][n - 1];
    }

    // count distinct subseq 
    // empty subseq to be included
    public static int distinctSubseq(String s) {
        int[] map = new int[26];
        Arrays.fill(map, -1);
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i < dp.length; i++) {
            char ch = s.charAt(i - 1);
            dp[i] = 2 * dp[i - 1];
            if (map[ch - 'a'] != -1) {
                int lastOccIdx = map[ch - 'a'];
                dp[i] -= dp[lastOccIdx - 1];
            }
            map[ch - 'a'] = i;
        }
        return dp[dp.length - 1];
    }

    // Shortest Uncommon Subsequence
    // didn't got it
    public static int shortestUncommonSubSeq(String s, String t) {
        return shortestUncommonSubSeq_rec(s, t, s.length(), t.length());
    }

    public static int shortestUncommonSubSeq_rec(String s, String t, int i, int j) {
        if (i == 0 || j == 0) {
            if (j == 0) return 1;
        }
        int k;
        for (k = 0; k < i; k++) {
            if (s.charAt(0) == t.charAt(k)) break;
        }
        if (k == j) return 1;

        int exc = shortestUncommonSubSeq_rec(s.substring(1), t, i - 1, j);
        int inc = 1 + shortestUncommonSubSeq_rec(s.substring(1), t.substring(k + 1), i - 1, j - k - 1);

        return Math.min(exc, inc);
    }

    // Temple Offerings
    public static int templeOfferings(int[] arr) {
        int n = arr.length;
        int[] left = new int[n];
        left[0] = 1;
        for (int i = 1; i < n; i++) {
            if (arr[i] > arr[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }
        int[] right = new int[n];
        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] > arr[i + 1]) {
                right[i] = right[i + 1] + 1;
            } else {
                right[i] = 1;
            }
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += Math.max(left[i], right[i]);
        }
        return ans;
    }

    // highway billboard problem
    // choose the approaches according to m and n
    // approach 1: o(board) ^ 2
    public static int billboard_app1(int m, int t, int[] boards, int[] profits) {
        int n = boards.length, ans = 0;
        int[] dp = new int[n];
        dp[0] = profits[0];
        for (int i = 1; i < dp.length; i++) {
            dp[i] = profits[i];
            int maxProfit = 0;
            for (int j = 0; j < i; j++) {
                // t = 3, j is on 8 and i is on 14
                // 8 + 3 < 14 -> true
                if (boards[j] + t < boards[i]) {
                    maxProfit = Math.max(maxProfit, dp[j]);
                }
            }
            dp[i] += maxProfit;
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    } 

    // approach 2 : o(M) 
    public static int billboard_app2(int m, int t, int[] boards, int[] profits) {
        int[] dp = new int[m + 1];
        // to know where is the board
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < boards.length; i++) {
            map.put(boards[i], profits[i]);
        } 

        dp[0] = 0;
        for (int i = 1; i < dp.length; i++) {
            if (map.containsKey(i)) {
                /// no choice
                int exc = dp[i - 1];
                int inc = ((i - t - 1 >= 0) ? dp[i - t - 1] : 0) + map.get(i);
                // the t miles between t - i - 1 and i will contribute 0;
                dp[i] = Math.max(inc, exc);

            } else {
                dp[i] = dp[i - 1];
            }
        }
        return dp[m];
    }

    // max sum alternating subsequence
    public static int maxSumAlternatingSubSeq(int[] arr) {
        int n = arr.length;
        int min = arr[0];
        for (int val: arr) min = Math.min(val, min);
        if (min == arr[0]) return min;

        int[] dec = new int[n];
        int[] inc = new int[n];

        dec[0] = inc[0] = arr[0];
        int flag = 0, ans = -(int)(1e8);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // fill dec on the basis of prev inc
                if (arr[j] > arr[i]) {
                    dec[i] = Math.max(dec[i], inc[j] + arr[i]);
                    // flag = 1;
                }
                if (arr[j] < arr[i]) {
                    // going for inc and i have already found a decreasing one
                    inc[i] = Math.max(inc[i], dec[j] + arr[i]);
                }
            }
            ans = Math.max(ans, Math.max(inc[i], dec[i]));
        }
        return ans;
    }

    // Minimum and Maximum values of an expression with * and +
    static class minMaxPair {
        int min = (int)(1e8);
        int max = -(int)(1e8);

        public minMaxPair(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public minMaxPair() {

        }
    }

    public static void minMaxExpression(String s) {
        int n = s.length();
        minMaxPair[][] dp = new minMaxPair[n + 1][n + 1];
        minMaxPair ans = minMaxExpression_rec(s, 0, n - 1, dp);
        // minMaxPair ans = minMaxExpression_dp(s);
        System.out.print(ans.min + ", " + ans.max);
    }

    public static minMaxPair minMaxExpression_rec(String s, int si, int ei, minMaxPair[][] dp) {
        if (si == ei) {
            int n = s.charAt(si) - '0';
            minMaxPair baseAns = new minMaxPair(n, n);
            return baseAns;
        }

        if (dp[si][ei] != null) return dp[si][ei];
        
        minMaxPair self = new minMaxPair();
        for (int cp = si + 1; cp < ei; cp += 2) {
            char op = s.charAt(cp);
            minMaxPair left = minMaxExpression_rec(s, si, cp - 1, dp);
            minMaxPair right = minMaxExpression_rec(s, cp + 1, ei, dp);
            getMinMaxValue(self, left, right, op);
        }
        return dp[si][ei] = self;
    }

    // in gap strategy dp is formed for n length not n + 1
    public static minMaxPair minMaxExpression_dp(String s) {
        int n = s.length();
        minMaxPair[][] dp = new minMaxPair[n][n];
        for (int g = 0; g < dp[0].length; g++) {
            for (int si = 0, ei = g; ei < dp[0].length; si++, ei++) {
                if (si == ei) {
                    int num = s.charAt(si) - '0';
                    minMaxPair baseAns = new minMaxPair(num, num);
                    dp[si][ei] = baseAns;
                    continue;
                }

                minMaxPair self = new minMaxPair();
                for (int cp = si + 1; cp < ei; cp += 2) {
                    char op = s.charAt(cp);
                    minMaxPair left = dp[si][cp - 1];
                    minMaxPair right = dp[cp + 1][ei];
                    getMinMaxValue(self, left, right, op);
                }
                dp[si][ei] = self;
            }
        }
        return dp[0][dp[0].length - 1];
    }

    public static void getMinMaxValue(minMaxPair self, minMaxPair left, minMaxPair right, char op) {
        if (op == '+') {
            self.max = Math.max(self.max, left.max + right.max);
            self.min = Math.min(self.min, left.min + right.min);
        } else if (op == '*') {
            int a = left.min * right.min;
            // int b = left.min * right.max;
            // int c = left.max * right.min;
            int d = left.max * right.max;
            self.min = Math.min(Math.min(a, d), self.min);
            self.max = Math.max(Math.max(a, d), self.max);
        }
    }

    public static void solve() {
        String s = "1+2*3+4*5";
        minMaxExpression(s);
        // System.out.println(ans);
    }

    public static int[] getArrfromStringInput(String s) {
        int n = s.length();
        String[] splitArr = s.split(" ");
        int[] arr = new int[splitArr.length];
        for (int i = 0; i < splitArr.length; i++) {
            arr[i] = Integer.parseInt(splitArr[i]);
        } 
        return arr;
    } 

    public static void main(String args[]) {
        solve();
    }
}