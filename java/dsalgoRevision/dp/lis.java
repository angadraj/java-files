import java.util.*;
class lis {

    public static int lis_rec_helper(int[] arr, int idx, Integer[] dp) {
        if (idx == 0) return 1;
        if (dp[idx] != null) return dp[idx];
        int myans = 0;
        for (int j = idx - 1; j >= 0; j--) {
            if (arr[idx] > arr[j]) {
                myans = Math.max(myans, lis_rec_helper(arr, j, dp));
            }
        }
        return dp[idx] = myans + 1;
    }   

    public static int lis_rec(int[] arr) {
        int len = 0;
        Integer[] dp = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            len = Math.max(len, lis_rec_helper(arr, i, dp));
        }
        return len;
    }

    // min deletions to make lis
    public static int minDel(int[] arr) {
        int[] dp = new int[arr.length];
        dp[0] = 1;
        int ans = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < i; j++) {
                int len = 0;
                if (arr[j] <= arr[i]) {
                    len = Math.max(len, dp[j]);
                }
            } 
            dp[i] = len + 1;
            ans = Math.max(ans, dp[i]);
        }
        return arr.length - ans;
    }

    public static void solve() {
        int[] arr = {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15, 10};
    }

    public static void main(String args[]) {
        solve();
    }
}