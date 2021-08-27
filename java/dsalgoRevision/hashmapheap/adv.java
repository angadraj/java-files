import java.util.*;
class adv {
    // is ap 
    public static boolean isAp(int[] arr) {
        HashSet<Integer> set = new HashSet<>();
        int min = (int)(1e8), max = -(int)(1e8);
        for (int val: arr) {
            min = Math.min(min, val);
            max = Math.max(max, val);
            set.add(val);
        }
        int d = (max - min) / (arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            int ele = min + (d * i);
            if (!set.contains(ele)) return false;
        }
        return true;
    }

    // maximum consecutive ones in a binary array
    public static int maxConsecutiveOnes(int[] arr) {
        int maxlen = 0, sum = 0;
        for (int val: arr) {
            if (val == 0) {
                maxlen = Math.max(maxlen, sum);
                sum = 0;
            } else {
                sum += 1;
            }
        }
        maxlen = Math.max(maxlen, sum);
        return maxlen;
    }

    // check if any anagram of string is pallindrome or not 
    public static int isAnagramPall(String str) {
        int len = str.length(), count_odd_len = 0, count_even_len = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        
        for (Character ch: map.keySet()) {
            if ((len & 1) == 1) {
                if ((map.get(ch) & 1) == 1) count_odd_len++;
                if (count_odd_len > 1) return 0;
                // in odd len string we can not adjust more than 1 char with odd frequency

            } else {
                if ((map.get(ch) & 1) == 1) return 0;
            }
        }
        return 1;
    }

    // check if freq of all elements can become same by "at most" one removal
    public static boolean canBeSameByOneRemoval(String str) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        if (isSame(map)) return true;

        for (Character ch: map.keySet()) {
            map.put(ch, map.get(ch) - 1);
            if (isSame(map)) return true;
            map.put(ch, map.get(ch) + 1);
        }

        return false;
    }
    
    public static boolean isSame(HashMap<Character, Integer> map) {
        int max = -(int)(1e8), min = (int)(1e8);
        // check if all freq are same or not
        for (Character ch: map.keySet()) {
            int val = map.get(ch);
            if (val == 0) continue;
            max = Math.max(max, val);
            min = Math.min(min, val);
        }
        return (max == min);
    }

    public int trapRainWater(int[][] arr) {
        
        // basic plan is to surround the smallest values within the largest possible value so that it can store maximum water. The biggest values should be min {max of all values}
        if (arr.length == 0 || arr[0].length == 0) return 0;
        
        int m = arr[0].length;
        
        int max_bound = 0;
        boolean[][] vis = new boolean[arr.length][arr[0].length];
        
        PriorityQueue<Integer> pq = new PriorityQueue<>((t, o) -> {
            return arr[t / m][t % m] - arr[o / m][o % m];
        });
        
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; i++) {
                pq.add(i * m + j);
                vis[i][j] = true;
            }
        }
        
        int water = 0;
        int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        
        while (pq.size() > 0) {
            int idx = pq.remove();
            int r = idx / m;
            int c = idx % m;
            
            int val = arr[r][c];
            
            max_bound = Math.max(max_bound, val);
            // as we have max_bound till now, so calculate water believing it will form a pocket. The bound can be euqal or greater than the removed val
            
            water += (max_bound - val);
            
            for (int[] d: dirs) {
                int dr = r + d[0];
                int dc = c + d[1];
                
                if (dr >= 0 && dc >= 0 && dc < arr[0].length && dr < arr.length && !vis[dr][dc]) {
                    pq.add(dr * m + dc);
                    // vis could have been marked outside but it would have increased the size of pq for no reason. So marking true when adding in will prevent duplicacies
                    vis[dr][dc] = true;
                }
            }
            
        }
        
        return water;
    }

    // number of subarrays sum less than k
    public static int subArrSumLessThanK(int[] arr, int k) {
        int sum = 0, i = -1, j = -1, count = 0;

        while (true) {
            boolean f1 = true, f2 = true;

            while (i < arr.length - 1) {
                i++;
                f1 = false;
                sum += arr[i];
                if (sum < k) {
                    // if less than or equal to k is required the  sum <= k
                    count += (i - j);
                    printArr(j, i, arr);

                } else if (sum >= k) break;
                // sum <= k 
            }

            // when i will come at last idx then acquiring will not be done but j < i
            // so j will release unneccassarily;

            if (i == arr.length - 1 && f1 && f2) break;

            while (j < i) {
                j++;
                f2 = false;
                sum -= arr[j];
                if (sum < k) {
                    count += (i - j);
                    printArr(j, i, arr);
                    break;
                }
            }
        }
        return count;
    }

    public static void printArr(int j, int i, int[] arr) {
        for (int p = j + 1; p <= i; p++) {
            for (int q = p; q <= i; q++) {
                System.out.print(arr[q] + " ");
            }
            System.out.println();
        }
    }

    // minimum window substring lc 76
    public static int minWindowSub(String str, String p) {
        if (str.length() == 0 || p.length() == 0) return -1;
        HashMap<Character, Integer> fmap = new HashMap<>();
        for (int i = 0; i < p.length(); i++) {
            char ch = p.charAt(i);
            fmap.put(ch, fmap.getOrDefault(ch, 0) + 1);
        }

        int len = (int)(1e8), dmc = p.length(), mc = 0, i = -1, j = -1, idx = -1;
        HashMap<Character, Integer> smap = new HashMap<>();

        while (true) {
            boolean f1 = true, f2 = true;

            while (i < str.length() - 1 && mc < dmc) {
               i++;
               f1 = false;
               char ch = str.charAt(i);
               smap.put(ch, smap.getOrDefault(ch, 0) + 1);

               if (smap.get(ch) <= fmap.getOrDefault(ch, 0)) mc++;
            }   

            while (j < i && mc == dmc) {
                j++;
                f2 = false;
                char ch = str.charAt(j);
                if (len > (i - j + 1)) {
                    len = i - j + 1;
                    idx = j;
                }
                if (smap.get(ch) == 1) smap.remove(ch);
                else smap.put(ch, smap.get(ch) - 1);
                
                if (smap.getOrDefault(ch, 0) < fmap.getOrDefault(ch, 0)) mc--;
            }

            if (f1 && f2) break;
        }
        // idx = 2, len = 8 -> substring(j + 1, i + 1);
        System.out.println(str.substring(idx, idx + len));
        return len;
    }

    // min window that contains all distinct chars of itself
    public static String minWindow_2(String s) {
        HashSet<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) set.add(s.charAt(i));

        int len = (int)(1e8), i = -1, j = -1, idx = -1, k = set.size();
        HashMap<Character, Integer> map = new HashMap<>();

        while (true) {
            boolean f1 = true, f2 = true;

            while (i < s.length() - 1 && map.size() < k) {
                i++;
                f1 = false;
                char ch = s.charAt(i);
                map.put(ch, map.getOrDefault(ch, 0) + 1);
            }

            while (j < i && map.size() == k) {
                j++;
                f2 = false;
                if ((i - j + 1) < len) {
                    len = (i - j + 1);
                    idx = j;
                }
                char ch = s.charAt(j);
                if (map.get(ch) == 1) map.remove(ch);
                else map.put(ch, map.get(ch) - 1);
            }

            if (f1 && f2) break;
        }
        // btw we are making child string from parent string so len != 10^8;
        return s.substring(idx, idx + len);
    }

    // longest substring with at most k distinct characters
    public static int subStringWithAtMostkDistinct(String str, int k) {
        HashMap<Character, Integer> map = new HashMap<>();
        int len = 0, i = -1, j = -1, idx = -1;

        while (true) {
            boolean f1 = true, f2 = true;

            while (i < str.length() - 1 && map.size() <= k) {
                i++;
                f1 = false;
                char ch = str.charAt(i);
                map.put(ch, map.getOrDefault(ch, 0) + 1);
                if (map.size() <= k) {
                    if (len < i - j) {
                        len = i - j;
                        idx = j;
                    }
                }
            }

            while (j < i && map.size() > k) {
                j++;
                f2 = false;
                char ch = str.charAt(j);
                if (map.get(ch) == 1) map.remove(ch);
                else map.put(ch, map.get(ch) - 1);
                
                // if after release size becomes equal
                if (map.size() <= k) {
                    if (map.size() <= k) {
                        if (len < i - j) {
                            len = i - j;
                            idx = j;
                        }
                    }
                }
            }

            if (f1 && f2) break;
        }
        System.out.println(str.substring(idx + 1, idx + 1 + len));
        return len;
    }

    // sliding window maximum
    public static int[] slidiingWindowMax(int[] arr, int k) {
        // ele, index
        PriorityQueue<int[]> pq = new PriorityQueue<>((t, o) -> {
            return o[0] - t[0];
        });

        int[] ans = new int[arr.length - k + 1];
        int idx = 0;

        for (int i = 0; i < arr.length; i++) {
            int ele = arr[i];
            // suppose i now makes the current window of size k
            // clear out all the maximum which don't belong to curr window
            while (pq.size() > 0 && pq.peek()[1] <= i - k) {
                // let i = 6, k = 3 -> curr window should be -> 4, 5, 6 : so max_idx ele in pq of 0 to 3(i - k) should be removed
                pq.remove();
            }

            // insert curr ele 
            pq.add(new int[]{ele, i});
            // now find out the max of curr window only if i >= k - 1; for 0, 1 and at 2 it will form 1 window (k = 3)
            if (i >= k - 1) ans[idx++] = pq.peek()[0];
        }
        return ans;
    }

    // longest subarray which contains all contigious ele (no dups) 
    public static int contiSubArr_1(int[] arr) {
        int len = 1; // default case minm length should bw 1
        for (int i = 0; i < arr.length; i++) {
            int max = arr[i];
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                min = Math.min(arr[j], min);
                max = Math.max(arr[j], max);

                if (max - min > arr.length) break;

                if (j - i == max - min) len = Math.max(len, j - i);
            }
        }
        return len;
    }

    public static int contiSubArr_2(int[] arr) {
        int len = 1;
        HashSet<Integer> set = new HashSet<>();

        for (int i = 0; i < arr.length; i++) {
            int min = arr[i];
            int max = arr[i];

            set.add(arr[i]);

            for (int j = i + 1; j < arr.length; j++) {
                if (set.contains(arr[j])) {
                    // 10(i) 10(j)
                    break;
                }

                max = Math.max(arr[j], max);
                min = Math.min(arr[j], min);

                set.add(arr[j]);    
                if (max - min > arr.length) break;
                if (j - i == max - min) {
                    len = Math.max(len, j - i);
                }
            }
            set.clear();
            // set = new HashSet<>();
            // this will be more faster
        }
        return len;
    }

    public static void solve() {
        // String str = "eceba", p = "ABC";
        // int ans = subStringWithAtMostkDistinct(str, 2);
        // System.out.println(ans);
        int[] arr = {10, 20, 30};
        int ans = contiSubArr_1(arr);
        System.out.println(ans);
    }

    public static void main(String args[]) {
        solve();
    }
}