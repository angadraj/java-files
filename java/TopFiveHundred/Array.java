import java.util.*;
class Array {
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

    public static int maxSumInc_2(int[] arr) {
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
    public static int chocolateDistribution(int[] arr, int m) {
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

    // inplace rotate a matrix 90 deg anticlockwise
    public static void rotate(int[][] arr, int n) {
        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < (n - i - 1); j++) {
                int temp = arr[i][j];
                arr[i][j] = arr[j][n - 1 - i];
                arr[j][n - 1 - i] = arr[n - 1 - i][n - 1 - j];
                arr[n - 1 - i][n - 1 - j] = arr[n - 1 - j][i];
                arr[n - 1 - j][i] = temp;
            }
        }
    }

    public static void print(int[][] arr) {
        for (int[] a: arr) {
            for (int val: a) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    // find k pairs with minimum sum in two sorted arrays
    public static List<List<Integer>> minSum(int[] a, int[] b, int k) {
        // pq_arr = {sum, i, j}
        int n = a.length, m = b.length;
        List<List<Integer>> res = new ArrayList<>();

        PriorityQueue<int[]> pq = new PriorityQueue<>((t, o) -> {
            return t[0] - o[0];
        });

        // insert first k elements from a
        // for valid reason the first pair in pq must be a[0] and b[0]
        for (int j = 0; j < m; j++) {
            if (j == k) break;
            pq.add(new int[]{a[0] + b[j], 0, j});
        }

        // now compete every element from b
        while (k-- > 0 && pq.size() > 0) {
            List<Integer> ans = new ArrayList<>();
            int[] pair = pq.remove();

            int i = pair[1], j = pair[2];
            ans.add(a[i]); ans.add(b[j]);
            res.add(ans);

            if (i + 1 < n) {
                pq.add(new int[]{a[i + 1] + b[j], i + 1, j});
            }
        }

        return res;
    }

    // search an element in a sorted and rotated array
    // elements are distinct here **
    public static int searchRotSortedEle(int[] arr, int k) {
        int si = 0, ei = arr.length - 1, idx = -1;
        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (arr[mid] == k) {
                idx = mid;
                break;
            }
            else if (arr[si] <= arr[mid]) {
                // = is when there is single ele
                if (k >= arr[si] && k <= arr[mid]) ei = mid - 1;
                else si = mid + 1;
            } else if (arr[mid] <= arr[ei]) {
                if (k >= arr[mid] && k <= arr[ei]) si = mid + 1;
                else ei = mid - 1;
            }
        }
        return idx;
    }

    // Given a sorted and rotated array, find if there is a pair with a given sum
    public static boolean findPairInRotArr(int[] arr, int k) {
        int pi = -1, n = arr.length;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                pi = i;
                break;
            }
        }
        int i = pi + 1, j = pi, count = 2;
        while (count <= n) {
            int sum = arr[i] + arr[j];
            if (sum == k) return true;
            else if (sum < k) {
                i++;
                count++;
                if (i == n) i = 0;
            } else {
                j--;
                count++;
                if (j < 0) j = n - 1;
            }
        }
        return false;
    }

    // max sum of i * arr[i] in all rotations (clockwise or anticlockwise)
    public static int maxSumInRotations(int[] arr) {
        int s0 = 0, sum = 0, n = arr.length, max = -(int)(1e8);
        for (int i = 0; i < n; i++) {
            sum += arr[i];
            s0 += (i * arr[i]);
        }
        max = Math.max(max, s0);
        for (int i = 0; i < n; i++) {
            int new_s = s0 + sum - (n * arr[n - i - 1]);
            max = Math.max(max, new_s);
            s0 = new_s;
        }
        return max;
    }

    // Rearrange positive and negative numbers in O(n) time and O(1) extra space
    // in this solution relative order gets changes for pos and neg elements    
    // if +ve -ve -> 01 sort => -ve +ve
    public static void sortPosNeg(int[] arr) {
        // 1. sort 0-1
        // -ves +ves
        // i = unknown, and j = start of +ves, j - 1 : end of -ves
        int i = 0, j = 0, n = arr.length;
        while (i < n) {
            if (arr[i] < 0) {
                // swap
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++; j++;
            } else {
                i++;
            }
        }

        for (int val: arr) System.out.print(val + " ");

        int nsi = 0, psi = j;
        while (nsi < psi && psi < n && arr[nsi] < 0) {
            int temp = arr[nsi];
            arr[nsi] = arr[psi];
            arr[psi] = temp;
            psi++; nsi += 2;
        }
        System.out.println("-------");
        for (int val: arr) System.out.print(val + " ");
    }

    // order matters
    public static void reArrangePosNeg(int[] arr) {
        // -, +, -, + -> even idx = -, odd idx = +
        // k traverse, i = pos, j = neg
        int n = arr.length;
        for (int k = 0; k < n; k++) {
            // work for even idx when you get +ve on that
            // we need to find -ve;
            if ((k & 1) == 0 && arr[k] < 0) {
                int j = k;
                while (j < n && arr[j] < 0) j++;
                shiftLeft(arr, n, j, k);

            } else if ((k & 1) == 1 && arr[k] >= 0) {
                // work for odd idx when you get -ve on that
                // we need to find +ve
                int i = k;
                while (i < n && arr[i] >= 0) i++;
                shiftLeft(arr, n, i, k);
            }
        }
        for (int val: arr) System.out.print(val + " ");
    }

    public static void shiftLeft(int[] arr, int n, int ptr, int k) {
        // if ele is not there and j get out of bounds
        if (ptr < n) {
            int temp = arr[ptr];
            while (ptr > k) {
                arr[ptr] = arr[ptr - 1];
                ptr--;
            }
            arr[k] = temp;
        }
    }

    // Three way partitioning of an array around a given range
    // sort 0, 1, 2
    public static void sort012(int[] arr) {
        int i = 0, j = 0, n = arr.length, k = n - 1;
        // i gets 2 -> throws at the end
        // i get 0 throws just behind it (j)
        // i gets moves
        while (i <= k) {
            int ele = arr[i];
            if (ele == 0) {
                swap_012(arr, i, j);
                i++; j++;
            } else if (ele == 2) {
                swap_012(arr, i, k);
                k--;
            } else i++;
        }
        for (int val: arr) System.out.print(val + " ");
    }

    public static void threePartition(int[] arr, int lo, int hi) {
        int i = 0, j = 0, n = arr.length, k = n - 1;
        // 0 = (lo), 1 = [lo, hi], 2 = (hi)
        while (i <= k) {
            int ele = arr[i];
            if (ele < lo) {
                swap_012(arr, i, j);
                i++; j++;
            } else if (ele > hi) {
                swap_012(arr, i, k);
                k--;
            } else i++;
        } 
        for (int val: arr) System.out.print(val + " ");
    }

    public static void swap_012(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }

    // max length of increasing subarray
    // space - n
    public static int LongestIncSubArray(int[] arr) {
        boolean[] vis = new boolean[arr.length];
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            vis[i] = true;
            int j = i + 1, len = 1;
            while (j < arr.length && arr[j] > arr[j - 1] && !vis[j]) {
                vis[j] = true;
                j++;
                len++;
            }
            ans = Math.max(ans, len);
        }
        return ans;
    }

    // space -> o(1)
    public static int LongestIncSubArray_2(int[] arr) {
        int ans = 0, len = 1;
        for (int i = 0; i < arr.length; i++) {
            if (i > 0 && arr[i] > arr[i - 1]) {
                len++;
            }
            else {
                ans = Math.max(len, ans);
                len = 1;
            }
            System.out.print(len + " ");
        }
        // is longest Inc subarray was at last
        ans = Math.max(ans, len);
        return ans;
    }

    // longest bitonic subarray
    public static int longestBitonicSubArray(int[] arr) {
        int len = 1, n = arr.length;
        int[] lis = new int[n];
        int[] lds = new int[n];
        // lisb
        for (int i = 0; i < n; i++) {
            if (i > 0 && arr[i] >= arr[i - 1]) {
                len++;
            } else {
                // ans = Math.max(ans, len);
                len = 1;
            }
            lis[i] = len;
        }

        // ldsb
        len = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (i < n - 1 && arr[i] >= arr[i + 1]) {
                len++;
            } else {
                // ans = Math.max(ans, len);
                len = 1;
            }
            lds[i] = len;
        }

        // calculate bitonic length
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            maxLen = Math.max(maxLen, lis[i] + lds[i] - 1);
        }
        return maxLen;
    }

    // Largest subarray with equal number of 0s and 1s
    // print the si and ei of such subarray
    // return len
    public static int longestSubArrWithEqual01(int[] arr) {
        // sum at ith = y and sum at i + k th is again y
        // then k elements in between constitute to sum = 0
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int sum = 0, si = -1, ei = -1, len = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += (arr[i] == 0) ? -1 : 1;
            if (map.containsKey(sum)) {
                len = Math.max(len, i - map.get(sum));
                // if you want si and ei of the subarray then you can use si and ei
            } else {
                map.put(sum, i);
            }
        }
        return len;
    }

    // maximum subarray product
    public static int maxProductSubArr(int[] arr) {
        // tc: {-3, 2, -4, 6, 0, -8, 5}
        int o_max = arr[0], min_sf = arr[0], max_sf = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > 0) {
                max_sf = Math.max(arr[i], arr[i] * max_sf);
                min_sf = Math.min(arr[i], arr[i] * min_sf);
            } else if (arr[i] == 0) {
                max_sf = 0;
                min_sf = 0;
            } else {
                // < 0
                int temp = Math.max(arr[i], arr[i] * min_sf);
                min_sf = Math.min(arr[i], max_sf * arr[i]);
                max_sf = temp;
            }
            o_max = Math.max(max_sf, o_max);
        }
        return o_max;
    }

    // sort01 
    public static void sort01(int[] arr) {
        int i = 0, j = 0;
        while (i < arr.length) {
            if (arr[i] == 0) {
                // throw 0 at end
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++; j++;
                
            } else if (arr[i] == 1) {
                i++;
            }
        }
    }

    // sort 012 
    public static void sort_2(int[] arr) {
        int i = 0, j = 0, k = arr.length - 1;
        while (i <= k) {
            if (arr[i] == 0) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++; j++;
            } else if (arr[i] == 2) {
                int temp = arr[i];
                arr[i] = arr[k];
                arr[k] = temp;
                k--;
            } else i++;
        }
    }

    // count inversions in an array
   public static int countInv(int[] arr) {
        int[] ans = new int[arr.length];
        return invHelper(arr, 0, arr.length - 1, ans);
    }

    public static int invHelper(int[] arr, int si, int ei, int[] ans) {
        if (si == ei) return 0;
        int mid = (si + ei) >> 1;
        int left = invHelper(arr, si, mid, ans);
        int right = invHelper(arr, mid + 1, ei, ans);
        int self = merge(arr, si, ei, mid, ans);
        return (left + right + self);
    }

    public static int merge(int[] arr, int si, int ei, int mid, int[] ans) {
        int i = si, j = mid + 1, count = 0, k = si;
        while (i <= mid && j <= ei) {
            if (arr[i] <= arr[j]) {
                ans[k++] = arr[i++];
            } else {
                // count inversion
                count += mid + 1 - i;
                ans[k++] = arr[j++];
            }
        }
        while (i <= mid) ans[k++] = arr[i++];
        while (j <= ei) ans[k++] = arr[j++];

        for (int p = si; p <= ei; p++) arr[p] = ans[p];
        return count;
    }

    // merge intervals;
    public static int[][] mergeIntervals(int[][] arr) {
        ArrayList<int[]> list = new ArrayList<>();
        Arrays.sort(arr, (t, o) -> {
            return t[0] - o[0];
        });
        if (arr.length >= 1) list.add(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            int[] currMeeting = list.get(list.size() - 1);
            int[] nextMeeting = arr[i];
            if (nextMeeting[0] < currMeeting[1]) {
                currMeeting[1] = Math.max(currMeeting[1], nextMeeting[1]);
            } else {
                list.add(nextMeeting);
            }
        }
        return list.toArray(new int[list.size()][]);
    }

    // buy and sell stock 2 transactions allowed
    public static int buySell2_trans(int[] arr) {
        int n = arr.length;
        int[] sellToday = new int[n];
        int bsp = -arr[0], ssp = 0;
        sellToday[0] = ssp;
        for (int i = 1; i < n; i++) {
            int nbsp = Math.max(-arr[i], bsp);
            int nssp = Math.max(ssp, bsp + arr[i]);
            bsp = nbsp;
            ssp = nssp;
            sellToday[i] = ssp;
        }

        // we buy today
        int[] buyToday = new int[n];
        int highestPrice = arr[n - 1], ssp_2 = 0;
        buyToday[n - 1] = ssp_2;
        for (int i = n - 2; i >= 0; i--) {
            // max of if you buy today and sell at highPrice or continue prev ssp_2
            ssp_2 = Math.max(ssp_2, highestPrice - arr[i]);
            buyToday[i] = ssp_2;
            highestPrice = Math.max(highestPrice, arr[i]);
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, sellToday[i] + buyToday[i]);
        }
        return ans;
    }

    // buy and sell K transactions allowed 
    public static int buySell_KTr(int[] arr, int K) {
        int[][] dp = new int[K + 1][arr.length];
        for (int t = 1; t < dp.length; t++) {
            int max = -(int)(1e8);
            for (int d = 1; d < dp[0].length; d++) {
                // find max for all previous days to ensure k - 1 trs are done
                max = Math.max(max, dp[t - 1][d - 1] - arr[d - 1]);
                // make kth transaction today
                dp[t][d] = max + arr[d];
                // now compare with k transactions done on previos day also
                dp[t][d] = Math.max(dp[t][d], dp[t][d - 1]);


                // brute force
                // for (int k = 0; k < d; k++) {
                //     max = Math.max(max, dp[t - 1][k] + (arr[d] - arr[k]));
                //     // profit = profit with k - 1 transactions on prev days and 1 transaction till today
                //     // ie: buy on previous day and sell on curr day
                // }
                // dp[t][d] = Math.max(max, dp[t][d - 1]);
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    // Maximum difference between two elements such that larger element appears after the smaller number
    // you can do this in o(n) time and o(n) space, find next greater ele on right and then find the max diff
    // for o(1) consider that curr ele is the max ele on right and in a varialble store the min element 
    // till that point. Find the max diff then
    public static int maxDiffSmBg(int[] arr) {
        int max_diff = -(int)(1e8), min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            // if this is my biggest ele, then find the diff
            max_diff = Math.max(max_diff, arr[i] - min);
            // and update the min for future, if you get another max ele
            min = Math.min(min, arr[i]);
        }
        return max_diff;
    }

    // max abs difference between elements left smaller and right smaller
    // for extreme elements smaller elements will be 0
    public static int maxDiff(int[] arr) {
        int[] sor_arr = sor(arr);
        int[] sol_arr = sol(arr);
        int ans = -(int)(1e8), n = arr.length;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, Math.abs(sor_arr[i] - sol_arr[i]));
        }
        return ans;
    }

    public static int[] sor(int[] arr) {
        int[] ans = new int[arr.length];
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (st.size() > 0 && arr[i] < arr[st.peek()]) {
                int idx = st.pop();
                ans[idx] = arr[i];
            }
            st.push(i);
        }
        while (st.size() > 0) {
            ans[st.pop()] = 0;
        }
        return ans;
    }

    public static int[] sol(int[] arr) {
        int[] ans = new int[arr.length];
        Stack<Integer> st = new Stack<>();
        for (int i = arr.length - 1; i >= 0; i--) {
            while (st.size() > 0 && arr[i] < arr[st.peek()]) {
                int idx = st.pop();
                ans[idx] = arr[i];
            }
            st.push(i);
        }
        while (st.size() > 0) {
            ans[st.pop()] = 0;
        }
        return ans;
    }

    // Minimize the maximum difference between the heights
    public static int heights_2(int[] arr, int k) {
        Arrays.sort(arr);
        int n = arr.length;
        int ans = arr[n - 1] - arr[0];
        int small = arr[0] + k, big = arr[n - 1] - k, min = (int)(1e8), max = -(int)(1e8);

        for (int i = 0; i < n - 1; i++) {
            min = Math.min(small, arr[i + 1] - k);
            max = Math.max(big, arr[i] + k);
            if (min < 0) continue;
            ans = Math.min(ans, max - min);
        }
        return ans;
    }

    // push all 0s in the end, in o(1) space and o(n) time
    public static void pushAllZeros(int[] arr) {
        int i = 0, j = arr.length - 1, count = 0;
        // brute force
        // while (i <= j) {
        //     if (arr[i] == 0) {
        //         for (int k = i; k < j; k++) {
        //             arr[k] = arr[k + 1];
        //         }
        //         arr[j] = 0;
        //         j--;
        //     } else i++;
        // }
        while (i < arr.length) {
            if (arr[i] != 0) {
                arr[count++] = arr[i];
                // basically when non zero ele will come together then all elememts will come at their 
                // right place, but when there will be one or more 0s in between then count will have the
                // index at which non zero should be there
                // therefore arr[count++] = arr[i]
            }
            i++;
        }
        while (count < arr.length) {
                arr[count++] = 0;
                // now count will have the index from which we only want 0s
                // as all non zero will come forward
        }
    }

    // Minimum swaps required to bring all elements less than or equal to k together
    public static int minSwaps_bruteForce(int[] arr, int k) {
        int n = arr.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) ans[i] = arr[i];
        Arrays.sort(ans);
        int swaps = 0;

        for (int i = 0; i < n && i <= k; i++) {
            if (ans[i] > k) break;
            else if (arr[i] > k) {
                swaps++;
            }
        }
        return swaps;
    }

    // sliding window is used when K elements are wanted together with some condition

    public static int minSwaps(int[] arr, int k) {
        int fav = 0, non_fav = 0;
        for (int val: arr) {
            if (val <= k) fav++;
        }
        // count will help to get the favourable elements
        // now consider a window of size "count", for every window you will get the number of swaps 
        // why sliding window, because the arr is not sorted and we can swap from anywhere in the arr to 
        // bring k ele closer
        // find the minimum one using that
        for (int j = 0; j < fav; j++) {
            if (arr[j] > k) {
                // these are unwanted
                non_fav++;
            }
        }
        // start the window
        // swaps = number of non fav items in my window
        int si = 0, ei = fav - 1, res = (int)(1e8);
        while (ei < arr.length) {
            res = Math.min(res, non_fav);
            ei++;
            if (ei < arr.length && arr[ei] > k) non_fav++;
            // release from si
            if (si < arr.length && arr[si] > k) non_fav--;
            si++;
        }
        return (res == (int)(1e8)) ? 0 : res;
    }

    // max subarray sum with atleast k elements
    public static int maxSumAtLeastKEle(int[] arr, int k) {
        int n = arr.length;
        int[] maxSum = new int[n];
        kadanes(arr, maxSum);
        int res = -(int)(1e8), sum = 0;
        // first add k elements
        for (int i = 0; i < k; i++) sum += arr[i];
        // to store max sum till here
        res = Math.max(res, sum);
        // now apply the window
        for (int i = k; i < n; i++) {
            sum = sum + arr[i] - arr[i - k];
            res = Math.max(res, sum);
            // now you have k ele, also add subarray sum before that window to make atleast k ele
            res = Math.max(res, sum + maxSum[i - k]);
            // as ith ele completes the window therefore maxSum will be from before this window
        }
        return res;
    }

    public static void kadanes(int[] arr, int[] ans) {
        int sum = 0, res = -(int)(1e8);
        for (int i = 0; i < arr.length; i++) {
            if (sum >= 0) {
                sum += arr[i];
            } else {
                sum = arr[i];
            }
            ans[i] = Math.max(sum, res);
        }
    }

    // form minimum number from given pattern
    public static String minNumber(String str) {
        String ans = "";
        Stack<Integer> st = new Stack<>();
        int num = 1;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == 'D') {
                st.push(num);
                num++;
            } else if (ch == 'I') {
                st.push(num);
                num++;

                while (st.size() > 0) ans += st.pop();
            }
        }
        // to match pattern, extra number is also added
        st.push(num);
        while (st.size() > 0) ans += st.pop();
        return ans;
    }

    // Find the smallest positive integer value that cannot be 
    // represented as sum of any subset of a given array
    // first missing positive int
    public static int smallestPositiveNumber(int[] arr) {
        int res = 1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > res) return res;
            else res += arr[i];
        }
        return res;
    }

    // Generate all possible sorted arrays from alternate elements of two given sorted arrays
    // Given two sorted arrays A and B, generate all possible arrays such that first 
    // element is taken from A then from B then from A and so on in increasing order till the arrays exhausted.
    // The generated arrays should end with an element from B.
    public static void generateAllSortedArr(int[] a, int[] b) {
        ArrayList<Integer> ans = new ArrayList<>();
        rec(a, b, 0, 0, true, ans);
    }

    public static void rec(int[] a, int[] b, int i, int j, boolean state, ArrayList<Integer> ans) {
        if (i == a.length || j == b.length) return;
        
        if (state) {
            // a turn to add ele
            if (a[i] > b[j]) {
                ans.add(a[i]);
                // move to B
                rec(a, b, i, j + 1, false, ans);
                // time to explore another value of A
                // remove curr A ele
                ans.remove(ans.size() - 1);
                // try next ele of A
                rec(a, b, i + 1, j, true, ans);

            } else {
                // continue with A
                rec(a, b, i + 1, j, true, ans);
            }
        } else {
            // b turn to insert ele
            if (b[j] > a[i]) {
                ans.add(b[j]);
                // printing will be done here as according to rule the last elememt of new arr 
                // should be from B
                System.out.print(ans);
                // move to A
                rec(a, b, i + 1, j, true, ans);
                // time to explore another value of B
                // remove curr ele of B
                ans.remove(ans.size() - 1);
                // try next element in B
                rec(a, b, i, j + 1, false, ans);
            } else {
                // continue with B
                rec(a, b, i, j + 1, false, ans);
            }   
        }
    }

    // nest greater element lexicographically
    // find the dip or 1st decreasing sequence from right
    // now start from end and find 1st greater ele from arr[dip]
    // swap them 
    // now you will get decreasing sequence from dip + 1
    // reverse it
    public static void nextgreater(int[] arr) {
        int i = arr.length - 2;
        while (i >= 0 && arr[i] >= arr[i + 1]) i--;

        if (i >= 0) {
            // there is some dip in between
            int j = arr.length - 1;
            while (j > i && arr[j] <= arr[i]) j++;
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        } 
        // sort from i + 1 or reverse it as you will get decreasing order from i + 1
        for (int p = i + 1, q = arr.length - 1; p < q; p++, q--) {
            int temp = arr[p];
            arr[p] = arr[q];
            arr[q] = temp;
        }
        // time: o(n)
    }

    // ************* Given an array arr[], find the maximum j – i such that arr[j] > arr[i] ***************

    // method 1: store all right max in an array, say the array is rmax
    // then apply binary search on rmax by searching next greater element on right extreme
    // why right extreme? because we have to find difference of j - i maximum
    // max j minus i

    // time: nlgn, space: n
    public static int maxjmi(int[] arr) {
        int n = arr.length;
        int[] rmax = new int[n];
        rmax[n - 1] = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rmax[i] = Math.max(arr[i], rmax[i + 1]);
        }
        int si = 0, ei = n - 1, ans = -(int)(1e8);
        for (int i = 0; i < n; i++) {
            int idx = binarySearchHelper(rmax, arr[i]);
            ans = Math.max(ans, idx - i);
        }
        return ans;
    }

    public static int binarySearchHelper(int[] arr, int ele) {
        int si = 0, ei = arr.length - 1, idx = -1;
        while (si <= ei) {
            // as the array is desc order so smaller are on right and bigger are on left
            int mid = (si + ei) >> 1;
            if (ele <= arr[mid]) {
                si = mid + 1;
                idx = mid;
            }
            else ei = mid - 1;
        }
        return idx;
    }

    // time: n, space: n
    // at any moment when lmin <= rmax then it means that the min ele is in left & max ele in right futher
    // from that point
    public static int maxJmi_2(int[] arr) {
        int n = arr.length, ans = -(int)(1e8);
        int[] rmax = new int[n];
        int[] lmin = new int[n];
        rmax[n - 1] = arr[n - 1];
        lmin[0] = arr[0];
        for (int i = 1; i < n; i++) lmin[i] = Math.min(arr[i], lmin[i - 1]);
        for (int i = n - 2; i >= 0; i--) rmax[i] = Math.max(arr[i], rmax[i + 1]);

        int i = 0, j = 0;
        while (i < n && j < n) {
            if (lmin[i] <= rmax[j]) {
                ans = Math.max(ans, j - i);
                j++;
            } else i++;
        }
        return ans;
    }

    // maximum sum of 2 non overlapping subarrays
    public static int maxSum2NonOverlapping(int[] arr, int x, int y) {
        return Math.max(maxSum2NonOverlapping_helper(arr, x, y), maxSum2NonOverlapping_helper(arr, y, x));
    }

    public static int maxSum2NonOverlapping_helper(int[] arr, int x, int y) {
        int n = arr.length, sum = 0;
        int[] dp1 = new int[n];
        int[] dp2 = new int[n];

        for (int i = 0; i < n; i++) {
            if (i < x) {
                sum += arr[i];
                dp1[i] = sum;
            } else {
                sum += arr[i] - arr[i - x];
                // sliding window
                dp1[i] = Math.max(dp1[i - 1], sum);
            }
        }
        sum = 0;
        for (int i = n - 1; i >=0; i--) {
            // n = 9, y = 3, So collect sum till 6, 7, 8
            if (i >= n - y) {
                sum += arr[i];
                dp2[i] = sum;
            } else {
                sum += arr[i] - arr[i + y];
                dp2[i] = Math.max(dp2[i + 1], sum);
            }
        }
        int ans = 0;
        for (int i = x - 1; i < n - y; i++) {
            // although it must go for < n - y
            // but for dp2 we are checking next ele, 
            ans = Math.max(ans, dp1[i] + dp2[i + 1]);
        }
        return ans;
    } 

    // Maximum Sum of 3 Non-Overlapping Subarrays of length K
    public static void maxSumOf3NonOverL(int[] arr, int k) {
        int n = arr.length;
        int[] left = new int[n];
        int[] right = new int[n];
        int[] psa = new int[n];

        // fill left array with max sum of k length subarray
        int sum = 0;
        for (int i = 0; i < n; i++) {
            if (i < k) {
                sum += arr[i];
                left[i] = sum;
            } else {
                sum += arr[i] - arr[i - k];
                left[i] = Math.max(left[i - 1], sum);
            }
        }

        // similarly fill right arr with max subarray sum
        sum = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (i >= n - k) {
                sum += arr[i];
                right[i] = sum;
            } else {
                sum += arr[i] - arr[i + k];
                right[i] = Math.max(right[i + 1], sum);
            }
        }

        // fill out psa
        psa[0] = arr[0];
        for (int i = 1; i < n; i++) {
            psa[i] = arr[i] + psa[i - 1];
        }

        // calculate sum of 3 subarrays
        int ans = -(int)(1e8), middleSubArrSi = -1;
        int leftSum = 0, rightSum = 0;
        // now process will be done on middle subarray, as right and left sum are alreay stored
        // middle range will start from k to n - 2k
        for (int i = k; i <= n - 2 * k; i++) {
            int middleArrSum = psa[i + k - 1] - psa[i - 1];
            // as middleArrSum will start from ith pos
            // so in psa the sum will be on i + k - 1
            // and to get the sum till there psa[i + k - 1] - psa[i - 1]
            if (left[i - 1] + middleArrSum + right[i + k] > ans) {
                ans = left[i - 1] + middleArrSum + right[i + k];
                middleSubArrSi = i;
                leftSum = left[i - 1];
                rightSum = right[i + k];
            }
        }

        System.out.print(ans + " ");
        for (int i = k - 1; i < middleSubArrSi; i++) {
            int currSum = psa[i] - ((i - k < 0) ? 0 : psa[i - k]);
            if (leftSum == currSum) {
                System.out.print(i - k + 1 + " ");
                break;
            }
        }
        System.out.print(middleSubArrSi + " ");
        for (int i = middleSubArrSi + (2 * k) - 1; i < n; i++) {
            int currSum = psa[i] - psa[i - k];
            if (rightSum == currSum) {
                System.out.print(i - k + 1 + " ");
                break;
            }
        }
    }

    // number of subarrays with bounded max
    // Number of subarrays with maximum values in given range
    // Given an array of N elements and L and R, print the number of sub-arrays such that the value of the 
    // maximum array element in that subarray is at least L and at most R. 
    public static int countSubArrWithMaxInRange(int[] arr, int L, int R) {
        int si = 0, ei = 0, prev = 0, ans = 0, n = arr.length;
        while (ei < n) {
            if (arr[ei] >= L && arr[ei] <= R) {
                prev = ei - si + 1;
                ans += prev;
            } else if (arr[ei] < L) {
                ans += prev;
            } else if (arr[ei] > R) {
                si = ei + 1;
                prev = 0;
            }
            ei++;
        }
        return ans;
    }

    // Print all possible combinations of r elements in a given array of size n
    // also handle dups
    public static int ncrRec(int[] arr, int r) {
        // to avoid dups: you can sort it
        // and at any level you can check that if the previous ele is same don't make the call
        // or you can create visited array at each level and check if the element is visited or not
        // space at all levels of recursion is not that good, so better sort it
        Arrays.sort(arr);
        return ncrRecHelper(arr, r, 0, "", 0);
        // subseq method
    }

    public static int ncrRecHelper(int[] arr, int r, int si, String ans, int count) {
        if (si >= arr.length) {
            if (count == r) {
                System.out.println(ans);
                return 1;
            }
            return 0;
        }
        int ways = 0;
        // include
        if (si == 0 || arr[si] != arr[si - 1]) {
           ways += ncrRecHelper(arr, r, si + 1, ans + arr[si] + " ", count + 1);
        }
        // exclude
        ways += ncrRecHelper(arr, r, si + 1, ans, count);
        return ways;
    }

    // longest subarray sum divisible by K
    public static int longestSubarrDivK(int[] arr, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        // remainder vs idx
        map.put(0, -1);
        int maxlen = 0, sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            int rem = sum % k;
            if (rem < 0) rem += k;
            // if k = 7, rem at one end = -2, and rem at other end = 5
            // -2 is same as 5 only as they are 2 points away from -7 or 7
            // -2 + 7 = 5. nk - x = nk - x + k - k = k(n - 1) + (k - x) 
            if (map.containsKey(rem)) {
                maxlen = Math.max(maxlen, i - map.get(rem));
            } else {
                map.put(rem, i);
            }
        }
        return maxlen;
    } 

    // Find minimum number of merge operations to make an array palindrome
    public static int minMergeOperations(int[] arr) {
        int i = 0, j = arr.length - 1, count = 0;
        while (i < j) {
            if (arr[i] == arr[j]) {
                i++; j--;
            } else if (arr[i] < arr[j]) {
                // start eating i
                int sum = arr[i], k = i + 1;
                while (k < arr.length && sum <= arr[j]) {
                    sum += arr[k++];
                    count++;
                    if (sum == arr[j]) break;
                }
                i = k + 1; j--;
            } else if (arr[j] < arr[i]) {
                int sum = arr[j], k = j - 1;
                while (j >= 0 && sum <= arr[i]) {
                    sum += arr[k--];
                    count++;
                    if (sum == arr[i]) break;
                }
                j = k - 1; i++;
            }
        }
        return count;
    }

    // Reorder an array according to given indexes
    // Given two integer arrays of same size, “arr[]” and “index[]”, reorder elements in 
    // “arr[]” according to given index array. It is not allowed to given array arr’s length.
    public static void reorderAccIndices(int[] A, int[] I) {
        // you need to swap both array values until i == I[i]
        // that is element is in it's correct position
        int n = A.length;
        for (int i = 0; i < n; i++) {
            while (I[i] != i) {
                // 50, 40, 70, 60, 90
                // 3, 0, 4, 1, 2
                int temp1 = A[i]; // 50
                int temp2 = I[i]; // 3

                A[i] = A[I[i]]; // 60
                I[i] = I[I[i]]; // 1

                A[temp2] = temp1; // A[3] = 50
                I[temp2] = temp2; // I[3] = 3;
            }
        }

        for (int val: A) System.out.print(val + " ");
        System.out.println();
        for (int val: I) System.out.print(val + " ");
    }

    // maximum score, codechef: MAXSC
    public static int maximumScore(int[][] arr) {
        int sum = 0, max = -(int)(1e8), n = arr.length;
        for (int j = 0; j < n; j++) {
            max = Math.max(max, arr[n - 1][j]);
        }
        sum += max;
        for (int i = n - 2; i >= 0; i--) {
            int ele = -(int)(1e8);
            for (int j = 0; j < n; j++) {
                if (arr[i][j] > ele && arr[i][j] < max) {
                    ele = arr[i][j];
                }
            }
            max = ele;
            sum += max;
        }
        return (sum < 0) ? -1 : sum;
    }

    // rearrange the array in max min form
    public static void arrangeInMaxMinForm(int[] arr) {
        int i = 0, mini = 0, maxi = arr.length - 1, me = arr[0];
        // me = max element value
        for (int val: arr) me = Math.max(me, val);
        me = me + 1;
        while (i < arr.length) {
            if ((i & 1) == 0) {
                // arr[even] = arr[even] + ((arr[maxi] % me) * me)
                arr[i] = arr[i] + ((arr[maxi] % me) * me);
                maxi--;
            } else {
                // arr[odd] = arr[odd] + ((arr[mini] % me) * me)
                arr[i] = arr[i] + ((arr[mini] % me) * me);
                mini++;
            }
            i++;
        }
        // arr = {1, 2, 3, 4, 5} -> o/p = {31, 8, 27, 16, 23}
        // now get the ans array, arr[i] = arr[i] / me
        for (int j = 0; j < arr.length; j++) arr[j] = arr[j] / me;
    }

    // make largest number from the given set of numbers
    public static String makeLargestNum(String[] arr) {
        Arrays.sort(arr, (t, o) -> {
            long n1 = Long.parseLong(o + t);
            long n2 = Long.parseLong(t + o);
            
            if (n1 > n2) {
                // number was greater due to curr ele was on last
                return 1;
            } else if (n2 > n1) return -1;
            else return 0;
        });
        StringBuilder sb = new StringBuilder();
        for (String val: arr) {
            sb.append(val + " ");
        }
        return sb.toString();
    }

    // Space optimization using bit manipulations
    // Given two numbers say a and b, mark the multiples of 2 and 5 between a and b using 
    // less than O(|b – a|) space and output each of the multiples. 
    public static void modulo2And5(int a, int b) {
        int[] arr = new int[Math.abs(b - a) + 1];
        for (int i = a; i <= b; i++) {
            if (i % 2 == 0 || i % 5 == 0) {
                arr[i - a] = 1;
            }
        }
        for (int i = a; i <= b; i++) {
            if (arr[i - a] == 1) System.out.print((i) + " ");
        }
    }

    // using bits and reducing space to less than |b - a|
    public static void modulo2And5_bits(int a, int b) {
        int range = Math.abs(b - a);
        // a = 100, b = 1; range = 99
        // we will divide this 99 into segments of 32 bits each, so number of segments required
        int n = (int)Math.ceil(((double)range / 32));

        int[] arr = new int[n];
        // 99 / 32 = 3 -> ceil = 4
        for (int i = a; i <= b; i++) {
            if (i % 2 == 0 || i % 5 == 0) {
                setBit(arr, i - a);
            }
        }
        // after all bits are set
        for (int i = a; i <= b; i++) {
            // now see which bits of the complete array is set, print that i
            if (checkBit(arr, i - a)) {
                System.out.print(i + " ");
            }
        }
    }

    public static void setBit(int[] arr, int idx) {
        // find the section in arr = idx / 32 
        // for setting a bit use or operator
        // after finding segment, which bit to set
        // first bring it into range eg = 79, segment = 2, and bit = 79 % 32 => 79 & 31
        // int bit = (idx & 31);
        // int mask = (1 << bit);
        // arr[(idx >> 5)] |=  mask;
        arr[(idx >> 5)] |= (1 << (idx & 31));
    }

    public static boolean checkBit(int[] arr, int idx) {
        // to check if a bit is set or not use and
        // int bit = (idx & 31);
        // int mask = (1 << bit);
        // int val = arr[(idx >> 5)] & (mask);
        int val = arr[(idx >> 5)] & (1 << (idx & 31));
        // System.out.println(arr[(idx >> 5)] + " " + idx + " ");
        // not everytime val will be 0 or 1, for ex idx = 4
        // arr[4 / 32] = arr[0] = 349(after setting bits)
        // 349 & (1 << (4 % 32)) => 349 & (1 << 4) => 1 1 1 ...... 1 1 & 1 0 0 = 1 0 0 
        // so in the above case val == 4
        if (val == 0) return false;
        return true;
    }

    // MO's algo or Query square root decomposition

    public static void solve() {
        // long[] arr = {20, 17, 42, 25, 32, 32, 30, 32, 37, 9, 2, 33, 31, 17, 14, 40, 9, 12, 36, 21, 8, 33, 6, 6, 10, 37, 12, 26, 21, 3};
        // int[] arr = {2, 2, 0, 2, 1};
        // int[] arr = {0, 1, 1, 0, 2, 2, 0, 1, 2, 0, 1, 1, 0, 0};
        // int[] arr = {2, 1, 5, 6, 3};
        // int k = 5;
        // // sort012(arr);
        // int ans = minSwaps(arr, k);
        // System.out.print(ans + " ");
        // int[] arr = {1, 2, 3, 4, 5};
        modulo2And5_bits(80, 100);
        // System.out.println(ans);
    }
    
    public static void main(String args[]) {
        solve();
    }
}