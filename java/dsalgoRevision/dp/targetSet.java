import java.uti.*;
class targetSet {

    // coin change permutation (inf)
    public static PermInf(int[] arr, int idx, int tar, Integer[] dp) {
        if (tar == 0) return dp[tar] = 1;
        if (dp[tar] != null) return dp[tar];
        int count = 0;
        for (int i = idx; i < arr.length; i++) {
            int coin = arr[i];
            if (tar - coin >= 0) {
                count += PermInf(arr, 0, tar - coin);
            }
        }
        return dp[tar] = count;
    }

    public static int PermInfDp(int[] arr, int tar) {
        int[] dp = new int[tar + 1];
        dp[0] = 1;
        for (int i = 1; i < dp.length; i++) {
            int count = 0;
            for (int i = idx; i < arr.length; i++) {
                int coin = arr[i];
                if (tar - coin >= 0) {
                    count += dp[tar - coin];
                }
            }
            dp[tar] = count;
        }
        return dp[dp.length - 1];
    }

    // combination inf
    public static combiInf(int[] arr, int idx, int tar, Integer[] dp) {
        if (tar == 0) return dp[tar] = 1;
        if (dp[tar] != null) return dp[tar];
        int count = 0;
        for (int i = idx; i < arr.length; i++) {
            int coin = arr[i];
            if (tar - coin >= 0) {
                count += combiInf(arr, i, tar - coin);
            }
        }
        return dp[tar] = count;
    }

    public static int combiInfDp(int[] arr, int tar) {
        int[] dp = new int[tar + 1];
        dp[0] = 1;
        for (int coin = 0; coin < arr.length; coin++) {
            int count = 0;
            for (int i = 1; i < dp.length; i++) {
                if (i - coin >= 0) {
                    count += dp[i - coin];
                }
            }
            dp[i] = count;
        }
        return dp[dp.length - 1];
    }

    // fn(arr, t, arr.length, new Boolean[arr.length + 1][tar + 1])
    public static boolean targetSumSubset(int[] arr, int tar, int len) {
        if (len == 0 || tar == 0) {
            return tar == 0;
        }
        if (dp[len][tar] != null) return dp[len][tar];
        boolean ans = false;
        int ele = arr[len - 1];
        // yes call
        ans = targetSumSubset(arr, tar - ele, len - 1);
        if (ans) return true;
        ans = targetSumSubset(arr, tar, len - 1);

        return dp[len][tar] = ans;
    }

    public static boolean targetSumDp(int[] arr, int T) {
        boolean[][] dp = new boolean[arr.length + 1][T + 1];
        for (int len = 0; len < dp.length; len++) {
            for (int tar = 0; tar < dp[0].length; tar++) {
                if (len == 0 || tar == 0) {
                    dp[len][tar] = (tar == 0);
                } else {
                    boolean ans = false;
                    int ele = arr[len - 1];
                    ans = tar - ele >= 0 ? dp[len - 1][tar - ele] : false;
                    if (ans) {
                        dp[len][tar] = true;
                        continue;
                    }
                    dp[len][tar] = dp[len - 1][tar];
                }
            }
        }
        return dp[arr.length][T];
    }

    // 0-1 knapsack
    // wts => arr, cap = tar, len = len
    // Integer[][] dp = new Integer[len + 1][cap + 1];
    public static int rec(int[] profits, int[] wts, int cap, int len, Integer[][] dp) {
        if (len == 0 || cap == 0 || cap < 0) {
            return cap < 0 ? -(int)(1e8) : 0;
        }

        if (dp[len][cap] != null) return dp[len][cap];
        
        int profit = profits[len - 1];
        int wt = wts[len - 1];
        
        // int inc = 0;
        int inc = rec(profits, wts, cap - wt, len - 1, dp) + profit;
        int exc = rec(profits, wts, cap, len - 1, dp);
        return dp[len][cap] = Math.max(inc, exc);
    }

    public static int knapsack01Dp(int[] wts, int[] profits, int C) {
        int[][] dp = new int[profits.length + 1][C + 1];
        
        for (int len = 0; len < dp.length; len++) {
            for (int cap = 0; cap < dp[0].length; cap++) {
                if (len == 0 || cap == 0) {
                    dp[len][cap] = 0;
                } else {
                    int profit = profits[len - 1];
                    int wt = wts[len - 1];
                    int inc = (cap - wt >= 0 ? dp[len - 1][cap - wt] : -(int)(1e8)) + profit;
                    int exc = dp[len - 1][cap];
                    
                    dp[len][cap] = Math.max(inc, exc);
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    // unbounded knapSack
    // inf combination target sum
    public static int unboundedKS(int[] profits, int[] wts, int C, int len, Integer[][] dp) {
        if (len == 0 || C == 0 || C < 0) {
            return C < 0 ? -(int)(1e8) : 0;
        }

        if (dp[len][C] != null) return dp[len][C];

        int profit = profits[len - 1];
        int wt = wts[len - 1];
        int inc = unboundedKS(profits, wts, C - wt, len, dp) + profit;
        int exc = unboundedKS(profits, wts, C, len - 1, dp);

        return dp[len][C] = Math.max(inc, exc);
    }

    public static int unboundedKpDp(int[] profits, int[] wts, int C) {
        int[][] dp = new int[profits.length + 1][C + 1];
        for (int len = 0; len < dp.length; len++) {
            for (int cap = 0; cap < dp[0].length; cap++) {
                if (len == 0 || cap == 0) {
                    dp[len][cap] = 0;
                } else {
                    int profit = profits[len - 1];
                    int wt = wts[len - 1];
                    int inc = (cap - wt >= 0 ? dp[len][cap - wt] : -(int)(1e8)) + profit;
                    int exc = dp[len - 1][cap];
                    dp[len][cap] = Math.max(inc, exc);
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    // unbounded knapsack is same as inf coin comb
    public static int unboundedKs_2(int[] profits, int[] wts, int C) {
        int[] dp = new int[C + 1];
        // max profit at each dp[c]
        dp[0] = 0;
        // cap = 0, then profit = 0;
        for (int p = 0; p < profits.length; p++) {
            int profit = profits[p];
            int wt = wts[p];
            int ans = 0;
            for (int cap = 0; cap < dp.length; cap++) {
                if (cap - wt >= 0) {
                    int currProfit = profit + dp[cap - wt];
                    dp[cap] = Math.max(dp[cap], currProfit);
                }
            }
        }
        return dp[dp.length - 1];
    }

    public static void solve() {

    }

    public static void main(String args[]) {
        solve();
    }
}