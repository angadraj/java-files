import java.util.*;
class tough {
    
    // min time to finish race 
    public static long minTimeToFinishRace(int[][] arr, int ct, int laps) {
        int n = arr.length;
        // preprocessing
        // as any tire can not be used continuously till 20 laps as 2 ^ 20 = 10 ^ 6 and ct is 10 ^ 5
        long[][] pre = new long[n][20];
        for (long[] p: pre) {
            Arrays.fill(p, 200000000);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < pre[0].length; j++) {
                if (j == 0) {
                    pre[i][j] = arr[i][0]; // arr[i] = [f, r] => for x = 0, f * r ^ 0 = f
                    continue;
                }
                long time = pre[i][j - 1] * arr[i][1]; // f, fr, frr, frrr, frrrr so on
                if (time >= 200000000) {
                    break;
                } else {
                    pre[i][j] = time;
                }
            }
            for (int j = 1; j < pre[0].length; j++) {
                pre[i][j] += pre[i][j - 1];
                if (pre[i][j] > 200000000) {
                    pre[i][j] = 200000000;
                    break;
                }
            }
        }
        //unbounded knapsack
        long[] dp = new long[laps + 1];
        Arrays.fill(dp, 200000000);
        // dp[i] = min time take by any tire either by changing it or using it again and again
        for (int l = 1; l <= laps; l++) {
            // check if the lap is < 20, only then we can think to use that tire again and again
            if (l < 20) {
                // check all tires time to cover lth lap without changing it
                for (int i = 0; i < pre.length; i++) {
                    dp[l] = Math.min(dp[l], pre[i][l - 1]);
                }
            }
            // also we can change the tire, 
            // since we are storing the mininum time take by any tire in dp, so we need to check all min time in prev laps or 
            // that is we will start from lap 1 till lap x - 1 and split it such that if dp[100] = Min(dp[1] + d[99], dp[2][98]) and so on
            for (int x = 1; x <= (l / 2); x++) {
                dp[l] = Math.min(dp[l], dp[x] + dp[l - x] + ct);
            }
        }
        return dp[laps];
    }

    // count pairs divisible by k
    // (a[i] * a[j]) % k == 0
    // gcd(a, k) * gcd(b, k) % k == 0;
    public static int gcd(int a, int b) {
        while (a % b != 0) {
            int rem = a % b;
            a = b;
            b = rem;
        }
        return b;
    }

    public static long pairsDivisibleByK(int[] arr, int k) {
        long count = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            int gcd = gcd(arr[i], k);
            int want = k / gcd;
            // maximum number in array can be 10 ^ 5
            // and it's factors can be till 100 or 200
            for (Integer key: map.keySet()) {
                if (key % want == 0) {
                    count += map.get(key);
                }
            }
            map.put(gcd, map.getOrDefault(gcd, 0) + 1);
        }
        return count;
    }

    // leetcode 2182
    // construct string with repeated limit 
    public static String constructStringWithLim(String s, int lim) {
        int[] freq = new int[26];
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            freq[ch - 'a']++;
        }

        StringBuilder sb = new StringBuilder();
        int idx = 25;

        while (idx >= 0) {
            // reduce idx until you get a value > 0
            while (idx >= 0 && freq[idx] == 0) {
                idx--;
            }

            // we have value now and append it in the sb until lim
            int count = 0;
            while (idx >= 0 && freq[idx] > 0 && count < lim) {
                sb.append((char)('a' + idx));
                count++;
                freq[idx]--;
            }

            // check if the freq of char used above is 0 or not
            if (idx >= 0 && freq[idx] == 0) {
                idx--;
                continue;
            }

            // now there are some chars remaining also we need to add another char in between as a blank
            int sidx = idx - 1;
            while (sidx >= 0 && freq[sidx] == 0) sidx--;

            // let say there is no char in freq map
            if (sidx < 0) break;

            // we have a value in sidx
            sb.append((char)('a' + sidx));
            freq[sidx]--;
        }
        return sb.toString();
    }

    // leetcode 2165
    // n can be +ve or -ve
    public static long smallestValueRearranged(long n) {
        if (n == 0) return 0;
        boolean isNeg = (n < 0);
        n = (n < 0) ? (n * -1) : n;

        String s = "" + n;
        char arr[] = s.toCharArray();
        Arrays.sort(arr);
        String ans;

        if (isNeg == false) {
            // i want the min number, it could also contain 0 at the front
            int i = 0;
            while (arr[i] == '0') {
                i++;
            }
            char temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            ans = new String(arr);

        } else {
            // as the num was -ve, so we will give them biggest number
            ans = new String(arr);
            StringBuilder sb = new StringBuilder(ans);
            sb.reverse();
            ans = sb.toString();
            ans = "-" + ans;
        }
        return Long.parseLong(ans);
    }

    // leetcode 2166, design bit set
    static class Bitset {
        int n;
        HashSet<Integer> ones;
        HashSet<Integer> zeros;
        
        public Bitset(int size) {
            n = size;
            ones = new HashSet<>();
            zeros = new HashSet<>();
            for (int i = 0; i < size; i++) zeros.add(i);
        }
        
        public void fix(int idx) {
            // 0 -> 1
            zeros.remove(idx);
            ones.add(idx);
        }
        
        public void unfix(int idx) {
            // 1 > 0
            ones.remove(idx);
            zeros.add(idx);
        }
        
        public void flip() {
            // 0s are 1
            HashSet<Integer> temp = ones;
            ones = zeros;
            zeros = temp;
        }
        
        public boolean all() {
            // if all bits are 1 or not
            if (ones.size() == n) return true;
            else return false;
        }
        
        public boolean one() {
            if (ones.size() >= 1) return true;
            else return false;
        }
        
        public int count() {
            // total setbits
            return ones.size();
        }
        
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if (ones.contains(i)) {
                    sb.append("1");
                } else if (zeros.contains(i)) {
                    sb.append("0");
                }
            }
            return sb.toString();
        }
    }   

    // 2167. Minimum Time to Remove All Cars Containing Illegal Goods
    public static int minTimeToRemoveIllegalCars(String s) {
        int n = s.length();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = s.charAt(i) - '0';

        int left[] = new int[n];
        int right[] = new int[n];

        left[0] = arr[0];
        right[n - 1] = arr[n - 1];

        for (int i = 1; i < n; i++) {
            if (arr[i] == 0) {
                left[i] = left[i - 1];
            } else {
                left[i] = Math.min(i + 1, left[i - 1] + 2);
            }
        }

        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] == 0) {
                right[i] = right[i + 1];
            } else {
                right[i] = Math.min(n - i, right[i + 1] + 2);
            }
        }

        int ans = (int)(1e8);
        for (int i = 0; i < n; i++) {
            int r = (i == n - 1) ? 0 : right[i + 1];
            int l = left[i];
            ans = Math.min(ans, r + l);
        }
        return ans;
    }

    // 2156
    // find hash value of substring of length k
    public String hashValueOfSubString(String s, int p, int mod, int k, int hv) {
        int k_1 = k - 1;
        long p_1 = 1;
        while (k_1 > 0) {
            p_1 = (p_1 * p) % mod;
            k_1--;
        }
        
        int n = s.length(), idx = 0, ei = n - 1;
        
        long hash = 0;
        
        for (int i = n - 1; i >= 0; i--) {
            // calculate hash
            int sval = (s.charAt(i) - 'a' + 1);
            hash = ((hash * p) % mod + sval) % mod;
            // now check if this is a valid window of length k
            if (ei - i + 1 == k) {
                // remove last char
                if (hash == hv) {
                    idx = i;
                }
                hash = (hash - ((s.charAt(ei) - 'a' + 1) * p_1) % mod + mod) % mod;
                ei--;
            }
        }
        
        return s.substring(idx, idx + k);
    }

    // leetcode 2157
    // group of strings
    
    // 2141: parallel processing
    // binary search

    // leetcode 2081
    public static void kMirror(int k) {
        ArrayList<Long> ans = new ArrayList<>();
        long i = 1;
        int total = 33, count = 0;
        while (i <= Long.MAX_VALUE) {
            if (pall(i)) {
                long kBaseNum = getKBaseNum(i, k);
                if (pall(kBaseNum)) {
                    ans.add(i);
                    count++;
                    if (count == total) break;
                }
            }
            i++;
        }    
        System.out.println(ans);
    }

    public static long getKBaseNum(long x, int k) {
        long ans = 0;
        long pow = 1;

        while (x != 0) {
            long rem = x % k;
            x = x / k;
            ans = (rem * pow) + ans;
            pow = pow * 10;
        }   
        return ans;
    }

    public static boolean pall(long x) {
       long copy = x;
       long ans = 0;

       while (x != 0) {
           long rem = x % 10;
           ans = (ans * 10) + rem;
           x = x / 10;
       }
       return (ans == copy);
    }

    public static void solve() {
        kMirror(2);
    }
    
    public static void main(String args[]) {
        solve();
    }
}