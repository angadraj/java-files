import java.util.*;
class main2 {
    public static int boardPath(int n, int[] dp) {
        if(n < 0 || n == 0) {
            if(n == 0) {
                dp[n] = 1;
                return 1;
            }
            return 0;
        } 
        if(dp[n] != 0) return dp[n];

        int count = 0;
        for(int i = 1; i <= 6; i++) {
            count += boardPath(n - i, dp);
        }
        return dp[n] = count;
    }
    
    public static int boardPathDp(int N) {
        int[] dp = new int[N + 1];
        for(int n = 0; n <= N; n++) {
            if(n == 0) {
                dp[n] = 1;
                System.out.print(dp[n] + " ");
                continue;
            }
            int count = 0;
            for(int i = 1; i <= 6; i++) {
                count += (n - i) >= 0 ? dp[n - i] : 0;
            }
            dp[n] = count;
            System.out.print(dp[n] + " ");
        }
        return dp[N];
    }

    public static int boardPathOpti(int N) {
        LinkedList<Integer> qu = new LinkedList<>();
        qu.addLast(1);
        qu.addLast(1);

        for(int n = 2; n <= N; n++) {
            if(n <= 6) {
                int val = qu.peekLast();
                qu.addLast(val * 2);

            } else if(n > 6) {
                int firstVal = qu.removeFirst();
                int lastVal = qu.peekLast();
                qu.addLast(lastVal * 2 - firstVal);
            }
        }
        return qu.getLast();
    }

    // string set 

    // longest pallindromic subsequence 
    public static int longestPallindromicSubseq(String str, int i, int j, int[][] dp) {
        if(i >= j) {
            return dp[i][j] = (i == j ? 1 : 0);
        }
        if(dp[i][j] > 0) return dp[i][j];
        int myans = 0;
        if(str.charAt(i) == str.charAt(j)) {
            myans = longestPallindromicSubseq(str, i + 1, j - 1, dp) + 2;
        } else {
            myans = Math.max(longestPallindromicSubseq(str, i + 1, j, dp), longestPallindromicSubseq(str, i, j - 1, dp));
        }
        return dp[i][j] = myans;
    }

    public static int longestPallindromicSubseqDp(String str, int[][] dp) {
        for(int g = 0; g < dp[0].length; g++) {
            for(int i = 0, j = g; j < dp[0].length; i++, j++) {
                if(i == j) {
                    dp[i][j] = 1;
                    continue;
                }
                int myans = 0;
                if(str.charAt(i) == str.charAt(j)) {
                    myans = dp[i + 1][j - 1] + 2;
                } else {
                    myans = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
                dp[i][j] = myans;
            }
        }
        return dp[0][dp[0].length - 1];
    }

    public static void LPS_string(String str, int[][] dp, int i , int j, char[] ans, int si, int ei) {
        if(i >= j) {
            if(i == j) {
                ans[si] = str.charAt(i);
            }
            for(char ch : ans) System.out.print(ch + " ");
            return;
        }
        if(str.charAt(i) == str.charAt(j)) {
            ans[si] = ans[ei] = str.charAt(i);
            LPS_string(str, dp, i + 1, j - 1, ans, si + 1, ei - 1);
        } else if(dp[i + 1][j] > dp[i][j - 1]) {
            LPS_string(str, dp, i + 1, j, ans, si, ei);
        } else {
            LPS_string(str, dp, i, j - 1, ans, si, ei);
        }
    }

    // lcs parent question

    public static int lcs(String s1, String s2, int i, int j, int[][] dp) {
        if(i == s1.length() || j == s2.length()) {
            return dp[i][j] = 0;
        }
        if(dp[i][j] != 0) return dp[i][j];
        int ans = 0;
        if(s1.charAt(i) == s2.charAt(j)) {
            ans = lcs(s1, s2, i + 1, j + 1, dp) + 1;
        } else {
            ans = Math.max(lcs(s1, s2, i + 1, j, dp), lcs(s1, s2, i, j + 1, dp));
        }
        return dp[i][j] = ans;
    }

    public static int lcs_dp(String s1, String s2, int[][] dp) {
        for(int i = dp.length - 1; i >= 0; i--) {
            for(int j = dp[0].length - 1; j >= 0; j--) {
                if(i == dp.length - 1 || j == dp[0].length - 1) {
                    dp[i][j] = 0;
                    continue;
                }
                int ans = 0;
                if(s1.charAt(i) == s2.charAt(j)) {
                    ans = dp[i + 1][j + 1] + 1;
                } else {
                    ans = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
                dp[i][j] = ans;
            }
        }
        return dp[0][0];
    }

    // lps using lcs
    public static int lpsUsingLcs(String s1) {
        // find the lcs of s1 and reverse of s1
        String s2 = reverse(s1);
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        return lcs_dp(s1, s2, dp);
    }

    public static void lcs_String(String s1, String s2, int i, int j, int[][] dp, char[] arr, int si) {
        if(i >= s1.length() || j >= s2.length()) {
            for(char ch : arr) System.out.print(ch + " ");
            return;
        }

        if(s1.charAt(i) == s2.charAt(j)) {
            arr[si] = s1.charAt(i);
            lcs_String(s1, s2, i + 1, j + 1, dp, arr, si + 1);

        } else if(dp[i + 1][j] > dp[i][j + 1]){
            lcs_String(s1, s2, i + 1, j, dp, arr, si);
        } else {
            lcs_String(s1, s2, i, j + 1, dp, arr, si);
        }
    }

    public static void solve() {
        String s1 = "angad";
        String s2 = "pancy";
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        lcs_dp(s1, s2, dp);
        
        int ans = dp[0][0];
        char[] arr = new char[ans];
        lcs_String(s1, s2, 0, 0, dp, arr, 0);
    }

    public static void display1d(int[] arr) {
        for(int val : arr) System.out.print(val + " ");
    }

    public static void display2d(int[][] arr) {
        for(int[] a : arr) {
            display1d(a);
            System.out.println();
        }
    }

    public static String reverse(String s) {
        String ans = "";
        for(int i = s.length() - 1; i >= 0; i--) {
            ans += s.charAt(i);
        }
        return ans;
    }
    
    public static void main(String args[]) {
        solve();
    }
}