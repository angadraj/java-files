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

    public static void solve() {

    }

    public static void main(String args[]) {
        solve();
    }
}