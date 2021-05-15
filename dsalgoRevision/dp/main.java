import java.util.*;
class main {

    public static int fibo(int n, int dp[]) {
        if(n == 0 || n == 1) return dp[n] = n;
        if(dp[n] != 0) return dp[n];
        int ans = fibo(n - 1, dp) + fibo(n - 2, dp);
        dp[n] = ans;
        return ans;
    }

    public static int fiboDp(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for(int i = 2; i <=n ; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public static int climbStairs(int n, int[] dp) {
        if(n == 0) return dp[0] = 1;
        if(n < 0) return 0;
        if(dp[n] != 0) return dp[n];
        int ans = 0;
        ans += climbStairs(n - 1, dp);
        ans += climbStairs(n - 2, dp);
        ans += climbStairs(n - 3, dp);
        return dp[n] = ans;
    }

    public static int climbStairsJumps(int n, int[] jumps) {
        int dp[] = new int[n + 1];
        dp[n] = 1;
        for(int i = n - 1; i >= 0; i--) {
            int jump = jumps[i];
            for(int j = 1; j <= jump; j++) {
                if(i + j < dp.length) {
                    dp[i] += dp[i + j];
                }
            }
        }
        return dp[0];
    }

    public static void climbStairsWithMinimumMoves(int n, int[] jumps) {
        int[] dp = new int[n + 1];
        dp[n] = 0;

        for(int i = n - 1; i >= 0; i--) {
            int jump = jumps[i];
            if(jump == 0) {
                dp[i] = (int)(1e8);
                continue;
            }
            int ans = (int)(1e8);
            for(int j = 1; j <= jump; j++) {
                if(i + j < dp.length) {
                    ans = Math.min(ans, dp[i + j]);
                }
            }
            if(ans == (int)(1e8)) dp[i] = (int)(1e8);
            else dp[i] = ans + 1;
        }

        System.out.println(dp[0] != (int)(1e8) ? dp[0] : "null");
    }

    public static int minCostPath(int[][] arr, int r, int c, int[][] dp) {
        if(r >= arr.length || c >= arr[0].length) return (int)(1e8);
        if(r == arr.length - 1 && c == arr[0].length - 1) {
            return dp[r][c] = arr[r][c];
        }
        if(dp[r][c] != -1) return dp[r][c];
        int myans = 0;
        int rightAns = minCostPath(arr, r, c + 1, dp);
        int bottomAns = minCostPath(arr, r + 1, c, dp);
        myans = Math.min(rightAns, bottomAns) + arr[r][c];
        return dp[r][c] = myans;
    }

    public static int minCostPathDp(int[][] arr) {
        int[][] dp = new int[arr.length][arr[0].length];

        for(int r = dp.length - 1; r >= 0; r--) {
            for(int c = dp[0].length - 1; c >= 0; c--) {
                if(r == arr.length - 1 && c == arr[0].length - 1) {
                    dp[r][c] = arr[r][c];
                    continue;
                }
                int myans = 0;
                int rightAns = c + 1 < arr[0].length ? dp[r][c + 1] : (int)(1e8);
                int bottomAns = r + 1 < arr.length ? dp[r + 1][c] : (int)(1e8);
                myans = Math.min(rightAns, bottomAns) + arr[r][c];
                dp[r][c] = myans;
            }
        }
        return dp[0][0];
    }

    static int[][] dirs = {{-1, 1}, {0, 1}, {1, 1}};

    public static int dfs(int[][] arr, int i, int j) {
        if(j == arr[0].length - 1) return arr[i][j];
        int myans = 0;
        for(int[] d : dirs) {
            int dr = i + d[0];
            int dc = j + d[1];
            if(dr >=0 && dc >= 0 && dr < arr.length && dc < arr[0].length) {
                myans = Math.max(myans, dfs(arr, dr, dc));
            }
        }
        return myans + arr[i][j];
    }

    public static int goldMine(int[][] arr) {
        for(int i = 0; i < arr.length; i++) {
            int ans = dfs(arr, i, 0);
            arr[i][0] = ans;
        }
        int ans = arr[0][0];
        for(int i = 0; i < arr.length; i++) {
            ans = Math.max(ans, arr[i][0]);
        }
        return ans;
    }

    public static int goldMineDp(int[][] arr) {
        int dp[][] = new int[arr.length][arr[0].length];

        for(int j = dp[0].length - 1; j >= 0; j--) {
            for(int i = 0; i < dp.length; i++) {
                if(j == arr[0].length - 1) {
                    dp[i][j] = arr[i][j];
                    continue;
                }
                int myans = 0;
                for(int[] d : dirs) {
                    int dr = i + d[0];
                    int dc = j + d[1];
                    if(dr >=0 && dc >= 0 && dr < dp.length && dc < dp[0].length) {
                        myans = Math.max(myans, dp[dr][dc]);
                    }
                }
                dp[i][j] = myans + arr[i][j];
            }
        }
        int ans = arr[0][0];
        for(int i = 0; i < arr.length; i++) {
            ans = Math.max(ans, dp[i][0]);
        }
        return ans;
    }

    // target sum series 

    public static boolean targetSum(int[] arr, int tar, int idx) {
        if(idx == 0 || tar == 0) {
            if(tar == 0) {
                return true;
            }
            return false;
        }
        int ele = arr[idx];

        if(tar - ele >= 0) {
            boolean include = targetSum(arr, tar - ele, idx - 1);
            if(include) return true;
        }
        boolean notInclude = targetSum(arr, tar, idx - 1);
        if(notInclude) return true;
        return false;
    }

    public static boolean targetSumMemo(int[] arr, int idx, int tar, boolean[][] dp) {
        if(idx == 0 || tar == 0) {
            if(tar == 0) {
                return dp[idx][tar] = true;
            }
            return dp[idx][tar] = false;
        }
        if(dp[idx][tar] != false) return dp[idx][tar];
        int ele = arr[idx]; 
        if(tar - ele >= 0) {
            boolean incAns = targetSumMemo(arr, idx - 1, tar - ele, dp);
            if(incAns) return dp[idx][tar] = true;
        }
        boolean notIncAns = targetSumMemo(arr, idx - 1, tar, dp);
        if(notIncAns) return dp[idx][tar] = true;
        return dp[idx][tar] = false;
    }

    public static boolean targetSumDp(int[] arr, int tar) {
        boolean[][] dp = new boolean[arr.length + 1][tar + 1];
        for(int i = 0; i < dp.length; i++) {
            for(int j = 0; j < dp[0].length; j++) {
                if(i == 0 && j == 0) {
                    dp[i][j] = true;
                } else if(i == 0) {
                    dp[i][j] = false;
                } else if(j == 0) {
                    dp[i][j] = true;                        
                } else {
                    if(dp[i - 1][j]) {
                        dp[i][j] = dp[i - 1][j];
                    } else {
                        int val = arr[i - 1];
                        if(j - val >= 0) {
                            if(dp[i - 1][j - val]) {
                                dp[i][j] = true;
                            }
                        }
                    }
                }
            }
        }
        //  for(int i = 0; i < dp.length; i++) {
        //     for(int j = 0; j < dp[0].length; j++) {
        //         System.out.print(dp[i][j] + " ");
        //     }
        //     System.out.println();
        // }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    // coin change combination
    public static int coinChangeCombiInf(int[] arr, int tar, int idx) {
        if(tar == 0) return 1;
        int count = 0;
        for(int i = idx; i < arr.length; i++) {
            int ele = arr[i];
            if(tar - ele >= 0) {
                count += coinChangeCombiInf(arr, tar - ele, i);
            }
        }
        return count;
        
    }

    public static int coinChangeCombiInfDp(int[] arr, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for(int coin = 0; coin < arr.length; coin++) {
            for(int tar = arr[coin]; tar < dp.length; tar++) {
                if(tar - coin >= 0) {
                    dp[tar] += dp[tar - arr[coin]];
                }
            }
        }
        return dp[target];
    }

    public static int coinChangePermInfDp(int[] arr, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for(int tar = 1; tar < dp.length; tar++) {
            for(int coin = 0; coin < arr.length; coin++) {
                if(tar - arr[coin] >= 0) {
                    dp[tar] += dp[tar - arr[coin]];
                }
            }
        }
        return dp[target];
    }

    public static int knapSack01(int W, int[] items, int[] profits) {
        int[][] dp = new int[items.length + 1][W + 1];
        for(int i = 0; i < dp.length; i++) {
            for(int j = 0; j < dp[0].length; j++) {
                if(i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else {
                    // exclude 
                    int excluded = dp[i - 1][j];
                    int included = -1;
                    if(j - items[i - 1] >= 0) {
                        included = profits[i - 1] + dp[i - 1][j - items[i - 1]];
                    }
                    dp[i][j] = Math.max(excluded, included);
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    public static int unboundedKnapSack(int W, int[] items, int[] profits) {
        int dp[] = new int[W + 1];
        dp[0] = 0;
        for(int tar = 1; tar < dp.length; tar++) {
            for(int val = 0; val < items.length; val++) {
                if(tar - items[val] >= 0) {
                    dp[tar] = Math.max(dp[tar], profits[val] + dp[tar - items[val]]);
                }
            }
        }
        return dp[dp.length - 1];
    }

    // include and exclude style group 
    public static int countBinaryString(int n) {
        int prev0 = 1;
        int prev1 = 1;
        for(int i = 1; i < n; i++) {
            int curr0 = prev1;
            int curr1 = prev0 + prev1;
            
            prev0 = curr0;
            prev1 = curr1;
        }
        return prev0 + prev1;
    }

    // arange buildings 
    public static int arrangeBuildings(int n) {
        int s = 1;
        int b = 1;
        for(int i = 1; i < n; i++) {
            int curr_s = s + b;
            int curr_b = s;

            s = curr_s;
            b = curr_b;
        }
        int singleSideAns = s + b;
        // do take long for this
        // this was for one side , for both sides 
        // 1 way at either can have n permutations to arrange buildings 
        // so total ways will be n * n
        return singleSideAns * singleSideAns;
    }

    public static int countEncodings(String str) {
        int[] dp = new int[str.length()];
        dp[0] = 1;
        for(int i = 1; i < dp.length; i++) {
            if(str.charAt(i) == '0' && str.charAt(i - 1) == '0') {
                dp[i] = 0;

            } else if(str.charAt(i) != '0' && str.charAt(i - 1) == '0') {
                dp[i] = dp[i - 1];

            } else if(str.charAt(i) == '0' && str.charAt(i - 1) != '0') {
                if(Integer.parseInt(str.substring(i - 1, i + 1)) <= 26) {
                    dp[i] = i - 2 >= 0 ? dp[i - 2] : 1;
                }
            } else {
                int singleAns = dp[i - 1];
                int doubleAns = 0;
                if(Integer.parseInt(str.substring(i - 1, i + 1)) <= 26) {
                    doubleAns = i - 2 >=0 ? dp[i - 2] : 1;
                } 
                dp[i] = singleAns + doubleAns; 
            }
        }
        return dp[dp.length - 1];
    }

    // greedy 

    public static int maxNonAdjacentSum(int[] arr) {
        int yesAns = arr[0];
        int noAns = 0;
        for(int i = 1; i < arr.length; i++) {
            int newYes = noAns + arr[i];
            int newNo = Math.max(yesAns, noAns);

            yesAns = newYes;
            noAns = newNo;
        }
        return Math.max(yesAns, noAns);
    }

    public static int contABCSunseq(String str) {
        int a = 0;
        int ab = 0;
        int abc = 0;
        for(int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(ch == 'a') {
                a = 2 * a + 1;
            } else if(ch == 'b') {
                ab = 2 * ab + a;
            } else if(ch == 'c') {
                abc = 2 * abc + ab;
            }
        }
        return abc;
    }

    public static long paintHouse3Colors(int n, int[][] arr) {
        int[][] dp = new int[n][3];
        dp[0][0] = arr[0][0];
        dp[0][1] = arr[0][1];
        dp[0][2] = arr[0][2];
        for(int i = 1; i < dp.length; i++) {
            // [i][0] red, [i][1] blue, [i][2] green
            dp[i][0] = arr[i][0] + Math.min(dp[i - 1][1], dp[i - 1][2]);
            dp[i][1] = arr[i][1] + Math.min(dp[i - 1][0], dp[i - 1][2]);
            dp[i][2] = arr[i][2] + Math.min(dp[i - 1][0], dp[i - 1][1]);
        }
        return Math.min(dp[n - 1][0], Math.min(dp[n - 1][1], dp[n - 1][2]));
    }

    public static int paintHouseManyColors(int[][] arr) {
        int[][] dp = new int[arr.length][arr[0].length];
        int gmin = (int)(1e8), gsmin = (int)(1e8);
        for(int j = 0; j < dp[0].length; j++) {
            dp[0][j] = arr[0][j];
            if(dp[0][j] < gmin) {
                gsmin = gmin;
                gmin = dp[0][j];
            } else if(dp[0][j] < gsmin) {
                gsmin = dp[0][j];
            }
        }
        for(int i = 1; i < dp.length; i++) {
            int min = (int)(1e8), smin = (int)(1e8);

            for(int j = 0; j < dp[0].length; j++) {
                dp[i][j] = arr[i][j];
                if(dp[i - 1][j] != gmin) {
                    dp[i][j] += gmin;
                } else if(dp[i - 1][j] == gmin) {
                    dp[i][j] += gsmin;
                }
                if(dp[i][j] < min) {
                    smin = min;
                    min = dp[i][j];
                } else if(dp[i][j] < smin) {
                    smin = dp[i][j];
                }
            }
            gmin = min;
            gsmin = smin;
        }
        return gmin;
    }   

    public static long paintFence(int n, int k) {
        long same = k;
        long different = k * (k - 1);

        for(int i = 3; i <= n; i++) {
            long newSame = different;
            long newDiff = (same + different) * (k - 1);
            same = newSame;
            different = newDiff;
        }
        return (same + different);
    }

    public static int tilingWith2X1(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for(int i = 3; i < dp.length; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public static int tilingWithMX1(int n, int m) {
        // m x n -> floor
        // m x 1 -> tile
        int[] dp = new int[n + 1];
        for(int i = 1; i < dp.length; i++) {
            if(i < m) {
                dp[i] = 1;
            } else if(i == m) {
                dp[i] = 2;
            } else {
                dp[i] = dp[i - 1] + dp[i - m];
            }
        }
        return dp[n];
    }

    public static int friendsParing(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for(int i = 3; i < dp.length; i++) {
            dp[i] = dp[i - 1] + (dp[i - 2] * (i - 1));
        }
        return dp[n];
    }

    public static int partitionIntoSubsets(int n, int k, int[][] dp) {
        if(n == 0 || k == 0) return dp[n][k] = 0;
        if(n < k) return dp[n][k] = 0;
        if(n == k) return dp[n][k] = 1;
        if(dp[n][k] > 0) return dp[n][k];
        int ans1 = partitionIntoSubsets(n - 1, k - 1, dp);
        int ans2 = partitionIntoSubsets(n - 1, k, dp);
        return dp[n][k] = ans1 + ans2 * k;
    }

    public static int partitionDP(int N, int K) {
        if(N == 0 || K == 0 || k > N) return 0;
        int[][] dp = new int[N + 1][K + 1];
        for(int n = 1; n <= N; n++) {
            for(int k = 1; k <= K; k++) {
                if(n < k) dp[n][k] = 0;
                else if(n == k) dp[n][k] = 1;
                else {
                    dp[n][k] = dp[n - 1][k - 1] + (dp[n - 1][k] * k);
                }
            }
        }
        return dp[N][K];
    }

    // buy and sell stocks series
    // buy and sell stocks 1 trans allowed

    public static int bestTimeToBS1Tr(int[] arr) {
        int profitToday = -(int)(1e8);
        int totalProfit = 0;
        int min = (int)(1e8);
        for(int i = 0; i < arr.length; i++) {
            min = Math.min(min, arr[i]);    
            profitToday = arr[i] - min;
            totalProfit = Math.max(profitToday, totalProfit);
        }
        return totalProfit;
    } 
    // in this you need to add all the upstrokes in the graph
    // buy and sell stocks inf trans allowed

    public static int bestTimeToBSInfTr(int[] arr) {
        int b = 0, s = 0;
        int cp = 0;
        for(int i = 1; i < arr.length; i++) {
            if(arr[i] >= arr[i - 1]) s++;
            else {
                int val = arr[s] - arr[b];
                cp += val > 0 ? val : 0;
                b = s = i;
            }
        }   
        cp += (arr[s] - arr[b]) > 0 ? (arr[s] - arr[b]) : 0;
        return cp;
    }

    // buy and sell stock with inf transactions but fee amount is there

    public static int bsInfFee(int[] arr, int fee) {
        int obsp = -arr[0];
        int ossp = 0;
        for(int i = 1; i < arr.length; i++) {
            int nbsp = 0;
            int nssp = 0;
            // If i want to buy todays stock then i will try to use prev profit
            if(ossp - arr[i] > obsp) {
                nbsp = ossp - arr[i];
            } else {
                nbsp = obsp;
            }
            // If i want to sell today, either old profit will work or by selling today
            if(obsp + arr[i] - fee > ossp) {
                nssp = obsp + arr[i] - fee;
            } else {
                nssp = ossp;
            }

            obsp = nbsp;
            ossp = nssp;
        }
        return ossp;
    }

    // buy and sell stock with cooled state, INF trans allowed

    public static int bsInfCooled(int[] arr) {
        int obsp = -arr[0];
        int ossp = 0;
        int ocsp = 0;
        for(int i = 1; i < arr.length; i++) {
            int nbsp;
            int nssp;
            int ncsp;

           // bsp on the basis of csp 
            if(ocsp - arr[i] > obsp) {
               nbsp = ocsp - arr[i];
            } else {
                nbsp = obsp;
            }

            // ssp 
            if(obsp + arr[i] > ossp) {
                nssp = obsp + arr[i];
            } else {
                nssp = ossp;
            }

            // csp 
            if(ocsp > ossp) {
                ncsp = ocsp;
            } else {
                ncsp = ossp;
            }

            obsp = nbsp;
            ossp = nssp;
            ocsp = ncsp;
        }
        return ossp;
    }

    // buy and sell stocks 2 transactions allowed
    //          you are here
    // b(past) ------------s(today)
    //               b(today)------------s(future)
    // u sell today so find ur buy point in past , u buy today so u find ur sell in future...

    public static int bsTwoTr(int[] arr) {
        int[] sellToday = new int[arr.length];
        int minBuy = arr[0];
        for(int i = 1; i < arr.length; i++) {
            minBuy = Math.min(minBuy, arr[i]);
            sellToday[i] = Math.max(sellToday[i - 1], arr[i] - minBuy);
        }

        int[] buyToday = new int[arr.length];
        int maxSell = arr[arr.length - 1];
        for(int i = arr.length - 2; i >= 0; i--) {
            maxSell = Math.max(maxSell, arr[i]);
            buyToday[i] = Math.max(buyToday[i + 1], maxSell - arr[i]);
        }

        int ans = buyToday[0] + sellToday[0];
        for(int i = 1; i < arr.length; i++) {
            ans = Math.max(ans, buyToday[i] + sellToday[i]);
        }

        return ans;
    }

    // buy and sell K transactions allowed

    public static int bskTr(int[] arr, int k) {
        int[][] dp = new int[k + 1][arr.length];
        
        for(int t = 1; t < dp.length; t++) {
            int profit = -(int)(1e8);
            for(int d = 1; d < dp[0].length; d++) {
                profit = Math.max(dp[t - 1][d - 1] - arr[d - 1], profit);
                dp[t][d] = Math.max(profit + arr[d], dp[t][d - 1 ]);
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    // highway billboard problem left in advanced dp


    public static void solve() {
        int n = 5;
        int k = 4;
        int[][] dp = new int[n + 1][k + 1];
        int ans = partitionDP(n, k);
        System.out.println(ans);
    }

    public static void display1d(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
    
    public static void display2d(int[][] arr) {
        for(int[] a : arr) {
            display1d(a);
            System.out.println();
        }
    }

    public static void main(String args[]) {
        solve();
    }
}