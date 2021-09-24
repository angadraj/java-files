import java.util.*;
class one {
    // k - con, leetcode
    int mod = (int)(1e9 + 7);
    
    public int kConcatenationMaxSum(int[] arr, int k) {
        long sum = 0; 
        if (k < 0 || arr.length == 0) return (int)sum;      
        for (int val: arr) sum += val;
        if (k == 1) {
            return (int)(kadannes(arr, k) % mod);
        } else if (sum < 0) {
            return (int)(kadannes_2(arr, k) % mod);
        } else {
            long k_2_sum = kadannes_2(arr, k);
            return (int)((k_2_sum + ((k - 2) * sum)) % mod);
        }
    }
    
    public long kadannes(int[] arr, int k) {
        long cb = arr[0], ob = arr[0];
        for (int i = 1; i < arr.length; i++) {
            int val = arr[i];
            if (cb >= 0) cb += val;
            else cb = val;
            ob = Math.max(cb, ob);
        }
        return (ob < 0) ? 0 : ob;
    }
    
    public long kadannes_2(int[] arr, int k) {
        int n = arr.length;
        int[] narr = new int[n * 2];
        for (int i = 0; i < n; i++) narr[i] = arr[i];
        for (int i = 0; i < n; i++) narr[n + i] = arr[i];
        return kadannes(narr, k);
    }

    // max circular subarray sum, gfg
    // reasoning imp
    // sum = (+ve consecutive sum) + (-ve consecutive number sum)
    public static int maxCircularSum(int[] arr) {
        // case 1: no wrapping of req ele is there then kadanne will give result
        int ans1 = kadannes(arr);
        // case 2 : wrapping of req ele is there
        // then we will remove the non - req ele from total sum
        int ans2 = reverseKadannes(arr);
        return Math.max(ans1, ans2);
    }

    public static int kadannes(int[] arr) {
        int cb = arr[0], ob = arr[0];
        for (int i = 1; i < arr.length; i++) {
            int val = arr[i];
            if (cb >= 0) cb += val;
            else cb = val;  
            ob = Math.max(cb, ob); 
        }
        return ob;
    }

    public static int reverseKadannes(int[] arr) {
        int sum = 0;
        for (int val: arr) sum += val;

        // invert all values such that we get min subaraay sum
        for (int i = 0; i < arr.length; i++) arr[i] = -arr[i];
        int minSubArrSum = kadannes(arr);
        // if all elements were negative then sum == minSubArrSum;
        // then minSubArrSum will become +ve 
        if (sum == -minSubArrSum) return sum;
        return (sum + minSubArrSum);
    }

    // find subarray with given sum (all positive), gfg
    public static ArrayList<Integer> subArraySum(int[] arr, int k) {
        int i = -1, j = -1, sum = 0;
        ArrayList<Integer> ans = new ArrayList<>();
        
        boolean ifIndicesFound = false;
        // so that when I get my 1st pair all code ends

        while (true) {
            boolean f1 = true, f2 = true;

            while (i < arr.length - 1 && !ifIndicesFound) {
                f1 = false;
                i++;
                sum += arr[i];
                if (sum == k) {
                    // j + 2 and i + 1 because idx + 1 is required
                    ans.add(j + 2);
                    ans.add(i + 1);
                    ifIndicesFound = true;
                    break;
                }
                else if (sum > k) break;
            }

            while (j < i && !ifIndicesFound) {
                f2 = false;
                j++;
                sum -= arr[j];
                if (sum < k) break;
                else if (sum == k) {
                    if (i > j) {
                        // this will cover the case if k == 0 and sum becomes 0 by 
                        // releasing the same element (ith and jth)
                        ans.add(j + 2);
                        ans.add(i + 1);
                        ifIndicesFound = true;
                    }
                    break;
                }
            }

            if (f1 && f2) break;
        }
        if (ans.size() == 0) ans.add(-1);
        return ans;
    }

    // equilibrium index of an array
    // in gfg there is one test having wrong Eqidx
    // follow for leetcode
    public static int equilibriumIndx(int[] arr) {
        int idx = -1, rsum = 0, n = arr.length;
        for (int val: arr) rsum += val;

        int lsum = 0; 
        for (int i = 0; i < n; i++) {
            rsum -= arr[i];
            if (lsum == rsum) {
                idx = i;
                break;
            }
            lsum += arr[i];
        }
        return idx;
    }

    // maximum sum increasing sequence
    // n^2 in dp, and nlgn using binary search
    // problem in nlgn method : it will not check all combinations of sum
    // with single arraylist
    public static int maxSumInc(int[] arr) {
        ArrayList<Integer> ans = new ArrayList<>();
        int n = arr.length, len = 0, currSum = arr[0], maxSum = 0;
        ans.add(arr[0]);
        for (int i = 1; i < n; i++) {
            int si = 0, ei = ans.size() - 1;
            while (si <= ei) {
                // we will ith ele in list
                int mid = (si + ei) >> 1;
                int midEle = ans.get(mid);
                if (arr[i] < midEle) ei = mid - 1;
                else si = mid + 1;
            }
            // when to add
            if (si == ans.size()) {
                ans.add(arr[i]);
                currSum += arr[i];
                System.out.println(ans);
                System.out.println(currSum);

            } else if (ei >= 0) {
                currSum -= ans.get(si);
                ans.set(si, arr[i]);
                currSum += ans.get(si);
                System.out.println(ans);
                System.out.println(currSum);
            }
            maxSum = Math.max(maxSum, currSum);
        }
        return maxSum;
    }

    public static int maxSumInc(int[] arr) {
        int n = arr.length, ans = -(int)(1e8);
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = arr[i];
            int max = -(int)(1e8);
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    max = Math.max(dp[j], max);
                }
            }
            dp[i] += (max == -(int)(1e8)) ? 0 : max;
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    // convert array into zig zag fashion
    public static void zigzag(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if ((i & 1) == 0 && arr[i] > arr[i + 1]) {
                swap(arr, i, i + 1);
            } else if ((i & 1) == 1 && arr[i] < arr[i + 1]) {
                swap(arr, i, i + 1);
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // find a pair with given difference
    // space of n
    public static boolean findPair(int[] arr, int k) {
        Arrays.sort(arr);
        int i = 0, j = 1;
        while (j < arr.length) {
            int diff = arr[j] - arr[i];
            if (diff == k) return true;
            else if (diff > k) i++;
            else j++;
        }
        return false;
    }

    // chocolate distribution problem
    // sliding window problem
    public static int chocolateDistribution(int[] arr) {
        Arrays.sort(arr);
        // for any window you will get min at ith and max at i + m - 1
        int diff = (int)(1e8);
        for (int i = 0; i <= arr.length - m; i++) {
            int min = arr[i];
            int max = arr[i + m - 1];
            diff = Math.min(diff, max - min);
        }
        return diff;
    }

    // minimum platforms required
    public static int minPlatformRequired(int[] arr, int[] dep) {
        Arrays.sort(arr);
        Arrays.sort(dep);
        int i = 0, j = 0, platform = 0, trains = 0;
        while (i < arr.length && j < dep.length) {
            if (arr[i] < dep[i]) {
                trains++;
                i++;
            } else if (arr[i] > dep[i]) {
                trains--;
                j++;
            }
            platform = Math.max(trains, platform);
        } 
        return platform;
    }

    // trapping rain water: stack approach
    public static int trapRainWater(int[] arr) {
        int water = 0;
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (st.size() > 0 && arr[i] >= arr[st.peek()]) {
                // it is next greater for element on top of stack
                int rb = i;
                int currEleIdx = st.pop();
                if (st.size() == 0) break;
                int lb = st.peek();

                int w = rb - lb - 1;
                int h = Math.min(arr[rb], arr[lb]);

                water += (w * h);
            }
            st.push(i);
        }
        return water;
    }

    // buy and sell stock infinite transactions allowed
    // also we have to find the days of such profitable transactions
    public static ArrayList<ArrayList<Integer>> bns_infTr(int[] arr, ArrayList<ArrayList<Integer>> res) {
        int obp = -arr[0], osp = 0;
        int b_i = 0, s_i = 0;
        // buying index and selling index
        for (int i = 1; i < arr.length; i++) {
            int nbp = Math.max(Math.max(obp, -arr[i]), osp - arr[i]);
            int nsp = Math.max(osp, arr[i] + obp);
            // when we have nbp > obp there are two cases either we continue previous buy or we buy 
            // from osp;
            if (nbp > obp) b_i = i;
            if (nsp > osp) s_i = i;
            
            if (nbp >= obp && nsp > osp) {
                // valid transactions
                // System.out.println("B " + b_i + ", " + "S " + s_i);
                ArrayList<Integer> list = new ArrayList<>();
                list.add(b_i); list.add(s_i);
                res.add(list);
            }

            obp = nbp;
            osp = nsp;
        }
        return res;
    }

    public static void solve() {
        // long[] arr = {20, 17, 42, 25, 32, 32, 30, 32, 37, 9, 2, 33, 31, 17, 14, 40, 9, 12, 36, 21, 8, 33, 6, 6, 10, 37, 12, 26, 21, 3};
        int[] arr = {16, 43, 33, 14, 26, 6, 41, 25, 33, 21};
        int ans = maxSumInc(arr);
        System.out.println(ans);
    }
    
    public static void main(String args[]) {
        solve();
    }
}