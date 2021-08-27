import java.util.*;
class basics {
    
    // highest freq character
    public static char highestFreqChar(String str) {
        HashMap<Character, Integer> hm = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            hm.put(ch, hm.getOrDefault(ch, 0) + 1);
        }
        int ans = 0;
        char ch = '.';
        for (Character key : hm.keySet()) {
            if (hm.get(key) > ans) {
                ans = hm.get(key);
                ch = key;
            }
        }
        return ch;
    }

    // get common element 1
    public static void getCommonEle_1(int[] a, int[] b) {
        HashMap<Integer, Integer> hm = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            hm.put(a[i], hm.getOrDefault(a[i], 0) + 1);
        }
        // because we have to follow the relative order of b
        for (int i = 0; i < b.length; i++) {
            if (hm.containsKey(b[i])) {
                System.out.println(b[i]);
                hm.remove(b[i]);
            }
        }
    }

    // get common ele 2
    public static void getCommonEle_2(int[] a, int[] b) {
        HashMap<Integer, Integer> hm = new HashMap<>();

        for (int i = 0; i < a.length; i++) {
            hm.put(a[i], hm.getOrDefault(a[i], 0) + 1);
        }

        for (int i = 0; i < b.length; i++) {
            if (hm.containsKey(b[i]) && hm.get(b[i]) > 0) {
                System.out.println(b[i]);
                hm.put(b[i], hm.get(b[i]) - 1);
            }
        }
    }

    // longest seq of consecutive elements
    public static void longestConsecEle(int[] a) {
        HashMap<Integer, Boolean> hm = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            hm.put(a[i], true);
        }

        for (int i = 0; i < a.length; i++) {
            int val = a[i];
            if (hm.containsKey(val - 1)) {
                hm.put(val, false);
            }
        }

        int len = 0;
        int si = -1;
        for (int i = 0; i < a.length; i++) {
            if (hm.get(a[i]) == true) {
                int templen = 1;

                while (hm.containsKey(a[i] + templen)) {
                    templen++;
                }
                // this while is covering all elements in total therefore it will run o(n) only 
                // the sigma of this while will  <= n;

                if (templen > len) {
                    len = templen;
                    si = a[i];
                }
            }
        }
        for (int i = 0; i < len; i++) {
            System.out.println(si + i);
        }
    }

    // heap

    //k lasgest elements
    public static void klargestEle(int[] a, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < a.length; i++) {
            if (pq.size() < k) {
                pq.add(a[i]);
            } else {
                if (a[i] > pq.peek()) {
                    pq.remove();
                    pq.add(a[i]);
                }
            }
        }
        while (pq.size() > 0) {
            System.out.println(pq.remove());
        }
    }

    // sort k sorted arr
    public static void sortKSorted(int[] a, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i <= k; i++) pq.add(a[i]);
        for (int i = k + 1; i < a.length; i++) {
            System.out.println(pq.remove());
            pq.add(a[i]);
        }
        while (pq.size() > 0) {
            System.out.println(pq.remove());
        }
    }

    // level 2 hashmap and heaps
    public static void empInMan(HashMap<String, String> data) {
        HashMap<String, HashSet<String>> tree = new HashMap<>();

        String ceo = "";
        for (String emp : data.keySet()) {
            String man = data.get(emp);
            if (man == emp) {
                ceo = man;
            } else {
                if (tree.containsKey(man)) {
                    tree.get(man).add(emp);
                } else {
                    HashSet<String> hs = new HashSet<>();
                    hs.add(emp);
                    tree.put(man, hs);
                }
            }
        }
        HashMap<String, Integer> ans = new HashMap<>();
        getSize(ans, ceo, tree);

        for (String man : ans.keySet()) {
            System.out.println(man + " " + ans.get(man));
        }
    }

    public static int getSize(HashMap<String, Integer> res, String man, HashMap<String, HashSet<String>> tree) {
        if (tree.containsKey() == false) {
            res.put(manager, 0); 
            return 0;
        }
        int size = 0;
        for (String manager : tree.keySet()) {
            size += getSize(res, manager, tree);
        }
        res.put(manager, size);
        return size + 1;
    } 

    // itinary 
    public static void itinary(HashMap<String, String> map) {
        HashMap<String, Boolean> hm = new HashMap<>();
        for (String src : map.keySet()) {
            String dest = map.get(src);
            hm.put(dest, false);
            if (!hm.containsKey(src)) hm.put(src, true);
        }

        String org_src = "";

        for (String src : hm.keySet()) {
            if (hm.get(src)) {
                org_src = src;
                break;
            }
        }

        while (true) {
            if (map.containsKey(org_src)) {
                System.out.print(org_src + " -> ");
                org_src = map.get(org_src);
            } else {
                // end point
                System.out.print(org_src + ".");
                break;
            }
        }
    }

    // k divisible in an array (hashmap of freq of remainders)

    // check if array can be divided into pairs whose sum is div by k
    public static boolean canDivideByK(int[] arr, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int val : arr) {
            map.put(val % k, map.getOrDefault(val % k, 0) + 1);
        }
        
        for (int val : arr) {
            int rem = val % k;

            if (rem == 0 || 2 * rem == k) {
                if (map.getOrDefault(rem, 0) % 2 != 0) return false;
            } else {
                int f1 = map.getOrDefault(rem, 0);
                int f2 = map.getOrDefault(k - rem, 0);
                if (f1 != f2) return false;
            }
        }
        return true;
    }

    // count subarrays whose sum is divisble by k
    public static int countSumDivisibleByK(int[] arr, int k) {
        HashMap<Integer, Integer> map  = new HashMap<>();
        // base case for ex: the sum at any pt becomes k and k % k == 0
        // so 0 must be there as our previous vals contribute in present vals
        int sum = 0, count = 0;
        map.put(0, 1);
        for (int val: arr) {
            sum += val;
            int rem = sum % k;
            if (rem < 0) rem += k;
            // make if from kn - y to kn' + (k - y)
            if (map.containsKey(rem)) count += map.get(rem);
            map.put(rem, map.getOrDefault(rem, 0) + 1);
        }
        return count;
    }

    // longest subarray divisible by k
    public static int longestSubArrSumDivisbleByK(int[] arr, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        // remainder vs len
        map.put(0, -1);
        // sum becomes k and k % k == 0;
        int len = 0, sum = 0;
        for (int i = 0; i < arr.length; i++) {
            int val = arr[i];
            sum += val;
            int rem = sum % k;
            if (rem < 0) rem += k;

            if (map.containsKey(rem)) {
                len = Math.max(len, i - map.get(rem));
            }
            map.putIfAbsent(rem, i);
        }
        return len;
    }

    // ACQUIRE AND RELEASE GROUP (covers majority of haashmap)

    // distinct elements in window of size k
    public static ArrayList<Integer> distinctEle(int[] arr, int k) {
        ArrayList<Integer> ans = new ArrayList<>();

        HashMap<Integer, Integer> hm = new HashMap<>();
        for (int i = 0; i < k - 1; i++) {
            hm.put(arr[i], hm.getOrDefault(arr[i], 0) + 1);
        }

        for (int i = k - 1, j = 0; i < arr.length; i++, j++) {
            // acquire
            hm.put(arr[i], hm.getOrDefault(arr[i], 0) + 1);
            // work
            ans.add(hm.size());
            // release
            int freq_j = hm.get(arr[j]);
            if (freq_j == 1) hm.remove(arr[j]);
            else hm.put(arr[j], hm.get(arr[j]) - 1);
        }
        return ans;
    }

    // largest subarray with 0 sum
    public static int largestSubArr(int[] arr) {
        // sum vs idx
        HashMap<Integer, Integer> hm = new HashMap<>();
        int p_sum = 0;
        int len = 0;
        hm.put(0, -1);
        for (int i = 0; i < arr.length; i++) {
            p_sum += arr[i];
            if (hm.containsKey(p_sum)) {
                len = Math.max(len, i - hm.get(p_sum));
            } else hm.put(p_sum, i);
        }
        return len; 
    }

    // count all subarrays with 0 sum
    public static int countSubArr(int[] arr) {
        HashMap<Integer, Integer> hm = new HashMap<>();
        // sum, freq
        int p_sum = 0, count = 0;
        // base case 
        hm.put(p_sum, 1);
        for (int i = 0; i < arr.length; i++) {
            p_sum += arr[i];
            if (hm.containsKey(p_sum)) {
                count += hm.get(p_sum);
            }
            hm.put(p_sum, hm.getOrDefault(p_sum, 0) + 1);
        }
        return count;
    }

    // string set

    // min substring of str1 which contains all chars of str2
    // based on acquire till the ans is formed and release to make ans shorter
    public static String minWindowSubStr(String s1, String s2) {
        String ans = "";
        // h2 is freq map for s2
        HashMap<Character, Integer> h2 = new HashMap<>();
        for (int i = 0; i < s2.length(); i++) {
            char ch = s2.charAt(i);
            h2.put(ch, h2.getOrDefault(ch, 0) + 1);
        }

        int mc = 0; // match count
        int dmc = s2.length(); // desired match count

        // char, freq map for s1
        HashMap<Character, Integer> h1 = new HashMap<>();
        int i = -1, j = -1;

        while (true) {
            boolean f1 = false, f2 = false;
            // acquire
            while (i < s1.length() - 1 && mc < dmc) {
                i++;
                char ch = s1.charAt(i);
                // acquire
                h1.put(ch, h1.getOrDefault(ch, 0) + 1);
                if (h1.getOrDefault(ch, 0) <= h2.getOrDefault(ch, 0)) {
                    mc++;
                }
                f1 = true;
            }
            // work
            // release
            while (j < i && mc == dmc) {
                j++;
                String pans = s1.substring(j, i + 1);
                
                if (ans.length() == 0 || pans.length() < ans.length()) {
                    ans = pans;
                }

                // release
                char ch = s1.charAt(j);
                if (h1.get(ch) == 1) h1.remove(ch);
                else h1.put(ch, h1.get(ch) - 1);

                if (h1.getOrDefault(ch, 0) < h2.getOrDefault(ch, 0)) {
                    mc--;
                }
                f2 = true;
            }

            if (!f1 && !f2) break;
        }

        return ans;
    }

    // minimum substring of str1 which contains all unique chars of str2
    public static int minWindowSubStr_2(String s1) {
        HashSet<Character> hs = new HashSet<>();
        
        for (int i = 0; i < s1.length(); i++) {
            hs.add(s1.charAt(i));
        }

        int i = -1, j = -1, ans = s1.length();

        HashMap<Character, Integer> hm = new HashMap<>();

        while (true) {
            boolean f1 = false, f2 = false;

            while (i < s1.length() - 1 && hm.size() < hs.size()) {
                i++;
                char ch = s1.charAt(i);
                hm.put(ch, hm.getOrDefault(ch, 0) + 1);
                f1 = true;
            }

            while (j < i && hm.size() == hs.size()) {
                ans = Math.min(ans, i - j);
                j++;
                char ch = s1.charAt(j);
                if (hm.get(ch) == 1) hm.remove(ch);
                else hm.put(ch, hm.get(ch) - 1);
                f2 = true;
            }

            if (!f1 && !f2) break;
        }
        return ans;
    }

    // longest substring without repeating characters
    public static int solution_LSWRC(String str) {
        int ans = 0;
        int i = -1, j = -1;
        HashMap<Character, Integer> map = new HashMap<>();
        while (true) {
            boolean f1 = false, f2 = false;
            while (i < str.length() - 1) {
                i++;
                f1 = true;
                char ch = str.charAt(i);
                map.put(ch, map.getOrDefault(ch, 0) + 1);

                if (map.get(ch) == 2) {
                    break;
                } else {
                    ans = Math.max(ans, i - j);
                }
            }
            
            while (j < i) {
                j++;
                char ch = str.charAt(j);
                f2 = true;
                map.put(ch, map.get(ch) - 1);
                if (map.get(ch) == 1) {
                    break;
                }
            }

            if (!f1 && !f2) {
                break;
            }
        }
        return ans;
    }   

    // count substrings with no repeating characters
    public static int solution_cswnr(String str) {
        int ans = 0;
        int i = -1, j = -1;
        HashMap<Character, Integer> map = new HashMap<>();
        while (true) {
            boolean f1 = false, f2 = true;

            while (i < str.length() - 1) {
                f1 = true;
                i++;
                char ch = str.charAt(i);
                map.put(ch, map.getOrDefault(ch, 0) + 1);
                if (map.get(ch) == 2) break;
                else ans += i - j;
            }

            while (j < i) {
                f2 = true;
                j++;
                char ch = str.charAt(j);
                map.put(ch, map.get(ch) - 1);
                if (map.get(ch) == 1) {
                    ans += i - j;
                    break;
                }
            }
            if (!f1 && !f2) break;
        }
        return ans;
    }

    // longest substring with K distinct chars
    public static int solution_lskdc(String str, int k) {
        int ans = 0;
        int i = -1, j = -1;
        HashMap<Character, Integer> map = new HashMap<>();
        while (true) {
            boolean f1 = false, f2 = false;
            while (i < str.length() - 1) {
                i++;
                f1 = true;

                char ch = str.charAt(i);
                map.put(ch, map.getOrDefault(ch, 0) + 1);
                if (map.size() < k) continue;
                else if (map.size() == k) {
                    ans = Math.max(ans, i - j);
                    // find len, and size of map is total unique chars 
                } else break;
            }

            while (j < i) {
                j++;
                f2 = true;
                char ch = str.charAt(j);
                if (map.get(ch) == 1) map.remove(ch);
                else map.put(ch, map.get(ch) - 1);

                // prev loop broke when size > k
                if (map.size() > k) continue;
                else if (map.size() == k) {
                    ans = Math.max(ans, i - j);
                    break;
                }
                // map.size will not go < k
            }

            if (!f1 && !f2) break;
        }
        return ans;
    }

    // count substrings with exactly K distinct characters
    public static int solution_cskd(String str, int k) {
        HashMap<Character, Integer> mapb = new HashMap<>();
        if (k == 1) {
            return solution_cskd_k1(mapb, str);
        }

        HashMap<Character, Integer> maps = new HashMap<>();

        int is = -1, ib = -1, j = -1;
        int ans = 0;

        while (true) {
            boolean f1 = false, f2 = false, f3 = false;

            while (ib < str.length() - 1) {
                ib++;
                f1 = true;
                char ch = str.charAt(ib);
                mapb.put(ch, mapb.getOrDefault(ch, 0) + 1);
                if (mapb.size() == k + 1) {
                    removeFromMap(mapb, ch);
                    ib--;
                    break;
                }
            }

            while (is < ib) {
                is++;
                f2 = true;
                char ch = str.charAt(is);
                maps.put(ch, maps.getOrDefault(ch, 0) + 1);
                if (maps.size() == k) {
                    removeFromMap(maps, ch);
                    is--;
                    break;
                }
            }

            // release
            while (j < is) {
                f3 = true;
                if (mapb.size() == k || maps.size() == k - 1) {
                    ans += ib - is;
                }
                j++;
                char ch = str.charAt(j);
                removeFromMap(mapb, ch);
                removeFromMap(maps, ch);
                if (maps.size() < k - 1 || mapb.size() < k) {
                    break;
                }
            }

            if (!f1 && !f2 && !f3) break;
        }
        return ans;
    }

    public static int solution_cskd_k1(HashMap<Character, Integer> map, String str) {
        int ans = 0;
        int i = -1, j = -1;

        while (true) {
            boolean f1 = false, f2 = false;

            while (i < str.length() - 1) {
                i++;
                f1 = true;
                char ch = str.charAt(i);
                map.put(ch, map.getOrDefault(ch, 0) + 1);
                if (map.size() == 2) {
                    removeFromMap(map, ch);
                    i--;
                    break;
                }
            }

            while (j < i) {
                f2 = true;
                if (map.size() == 1) {
                    ans += i - j;
                }
                j++;
                char ch = str.charAt(j);
                removeFromMap(map, ch);
                if (map.size() == 0) break;
            }

            if (!f1 && !f2) break;
        }
        return ans;
    }

    public static void removeFromMap(HashMap<Character, Integer> map, Character ch) {
        if (map.get(ch) == 1) map.remove(ch);
        else map.put(ch, map.get(ch) - 1);
    }   

    // count of equivalent subarrays -> count of subarrays that contain all distinct ele which are in the arr
    public static int solution_ceqSubarr(int[] arr) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < arr.length; i++) set.add(arr[i]);
        int k = set.size();

        HashMap<Integer, Integer> map = new HashMap<>();
        int i = -1, j = -1, ans = 0;

        while (true) {
            boolean f1 = false, f2 = false;

            while (i < arr.length - 1) {
                i++;
                f1 = true;
                map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
                if (map.size() == k) {
                    ans += arr.length - i;
                    break;
                }
            }

            while (j < i) {
                j++;
                f2 = true;
                if (map.get(arr[j]) == 1) map.remove(arr[j]);
                else map.put(arr[j], map.get(arr[j]) - 1);

                if (map.size() == k) ans += arr.length - i;
                else if (map.size() < k) break;
            }

            if (!f1 && !f2) break;
        }
        return ans;
    }

    // max consecutive ones with only k (0) flipped
    public static int solution_maxones_K(int[] arr, int k) {
        // with this test case failed when there are no 0s
        // for that we have to calculate length independently
        int ans = 0, count = 0, i = -1, j = -1;

        while (true) {
            boolean f1 = false, f2 = false;
            while (i < arr.length - 1) {
                i++;
                f1 = true;
                if (arr[i] == 0) count++;
                if (count == k) {
                    ans = Math.max(ans, i - j);
                } else if (count > k) break;
            }
            // adding extra condition here
            while (j < i && count > k) {
                j++;
                f2 = true;
                if (arr[j] == 0) count--;
                if (count == k) {
                    ans = Math.max(ans, i - j);
                    break;
                }
            }
            if (!f1 && !f2) break;
        }
        return (j == -1) ? arr.length : ans;
    }

    public static int solution_maxones_K(int[] arr, int k) {
        int ans = 0, j = -1, i = -1, count = 0;
        while (i < arr.length - 1) {
            i++;
            if (arr[i] == 0) count++;

            while (count > k) {
                j++;
                if (arr[j] == 0) count--;
            }

            ans = Math.max(ans, i - j);
        }
        return ans;
    }

    // max consecutive ones with only 1 (0) flip allowed
    // call above soln with k = 1;
    solution_maxones_K(arr, 1);

    // largest subarray with contigious elements without repetition
    // ex : 5, 4, 9, 8, 7
    // max - min = last_idx - first_idx
    public static int solution_longestcsubarr(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            HashSet<Integer> set = new HashSet<>();
            int min = arr[i];
            int max = arr[i];
            set.add(arr[i]);

            for (int j = i + 1; j < arr.length; j++) {
                if (set.contains(arr[j])) break;
                set.add(arr[j]);
                min = Math.min(min, arr[j]);
                max = Math.max(max, arr[j]);

                if (max - min == j - i) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }
        return ans;
    }

    // longest substring with at most k unique chars
    // acquire release strategy
    public static int solution_longestSkUChars(String str, int k) {
        int ans = 0, i = -1, j = -1;
        HashMap<Character, Integer> map = new HashMap<>();
        while (true) {
            boolean f1 = false, f2 = false;

            while (i < str.length() - 1) {
                i++;
                f1 = true;
                char ch = str.charAt(i);
                map.put(ch, map.getOrDefault(ch, 0) + 1);
                if (map.size() <= k) {
                    ans = Math.max(ans, i - j);
                } else {
                    break;
                }
            }

            while (j < i) {
                j++;
                f2 = true;
                char ch = str.charAt(j);
                if (map.get(ch) == 1) map.remove(ch);
                else map.put(ch, map.get(ch) - 1);

                if (map.size() <= k) {
                    ans = Math.max(ans, i - j);
                    break;
                } 
            }

            if (!f1 && !f2) break;
        }
        return ans;
    }

    // count of substrings having at most K unique chars
    // a b b d -> abbd, bbd, bd, d (same principle and using A/R)
    // at the end of string a b c d ........ b c a 
    // here we have bca, ca, a
    // here u have the count as b = 1, c = 1, a = 1, i is at 'a' and now u release b ans you count 'ca' and 'a' again
    // don't do this mistake
    public static int soln_csatmostKuniqueChars(String str,int k) {
        int ans = 0, i = -1, j = -1;
        HashMap<Character, Integer> map = new HashMap<>();
        while (true) {
            boolean f1 = false, f2 = false;
            while (i < str.length() - 1) {
                i++;
                // f1 = true;
                char ch = str.charAt(i);
                map.put(ch, map.getOrDefault(ch, 0) + 1);
                if (map.size() <= k) {
                    ans += i - j;
                } else break;
            }

            if (i == str.length() - 1 && map.size() <= k) break;
            // this condition prevents from that mistake 
            // and this should come before j loop to prevent extra release that could count duplicate too

            while (j < i) {
                j++;
                // f2 = true;
                char ch = str.charAt(j);
                if (map.get(ch) == 1) map.remove(ch);
                else map.put(ch, map.get(ch) - 1);
                
                if (map.size() > k) continue;
                else  {
                    ans += i - j;
                    break;
                }
            }
            // if (!f1 && !f2) break;
        }
        return ans;
    }

    // count of subarrays whose sum equals k 
    // use -> y + (y - k) = k
    // sum till any point is y then we check if y - k is there, if yes from point of y - k till y sum is k

    // sum at any point in the array is (y - k) and in future it becomes y then the subarray will be equal to k
    public static int solution_countsubarr(int[] arr, int k) {
        HashMap<Integer,Integer> map = new HashMap<>();
        int y = 0, ans = 0;
        map.put(0, 1);
        
        for (int i = 0; i < arr.length; i++) {
            y += arr[i];
            if (map.containsKey(y - k)) {
                ans += map.get(y - k);
                // y - k
            }
            map.put(y, map.getOrDefault(y, 0) + 1);
            // store y
        }
        return ans;
    }

    // find all anagrams in string  
    // strings whose character freq is same : ababbcddc and aabbbccdd irrespective of relative order
    // give s & p: find with how many substrings of 's', 'p' is it's anagram
    // based on A/R and we will slide a window on 's' of len = 'p' and doing a/r together
    public static void findAnagrams(String s, String p) {
        HashMap<Character, Integer> smap = new HashMap<>();
        HashMap<Character, Integer> pmap = new HashMap<>();

        for (int i = 0; i < p.length; i++) {
            char ch = p.charAt(i);
            char ch_2 = s.charAt(i);
            pmap.put(ch, pmap.getOrDefault(ch, 0) + 1);
            // add in smap too
            smap.put(ch_2, smap.getOrDefault(ch_2, 0) + 1);
        }

        String ans = "";
        int count = 0;
        // a/r together
        for (int i = p.length(); i < s.length(); i++) {
            if (compareMap(smap, pmap)) {
                count++;
                ans += (i - p.length()) + " ";
            }
            // acquire
            char ch_a = s.charAt(i);
            smap.put(ch_a, smap.getOrDefault(ch_a, 0) + 1);
            // release
            char ch_r = s.charAt(i - p.length());
            if (smap.get(ch_r) == 1) smap.remove(ch_r);
            else smap.put(ch_r, smap.get(ch_r) - 1);
        } 
        // i is out of bounds leavinf behind last window
        if (compareMap(smap, pmap)) {
            count++;
            ans += (s.length() - p.length()) + " ";
        }
        System.out.println(count);
        System.out.println(ans);
    }

    public static boolean compareMap(HashMap<Character, Integer> smap, HashMap<Character, Integer> pmap) {
        for (Character key: pmap.keySet()) {
            if (smap.getOrDefault(key, 0) != pmap.get(key)) return false;
        }
        return true;
    }

    // k anagrams
    // two strings are k anagrams if on replacing at most k char in str2 it beocmes anagram of str1
    // a2 b3 c1 & a3 b2 d1 -> a -> b and c -> d
    // basically the suppy and demand of chars in both string must be balanced
    public static boolean kanagram(String s, String p, int k) {
        if (s.length() != p.length()) return false;

        HashMap<Character, Integer> smap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            smap.put(ch, smap.getOrDefault(ch, 0) + 1);
        }

        for (int i = 0; i < p.length(); i++) {
            char ch = p.charAt(i);
            if (smap.getOrDefault(ch, 0) > 0) {
                smap.put(ch, smap.get(ch) - 1);
            }
        }

        int ans = 0;
        for (Character key: smap.keySet()) {
            ans += smap.get(key);
        }
        return ans <= k;
    }

    // find anagram mappings
    public static int[] anagramMappings(int[] A, int[] B) {
        // we have to return array incuding index of elements of A in B
        // search A in B
        HashMap<Integer, pair> map = new HashMap<>();
        for (int i = 0; i < B.length; i++) {
            if (map.containsKey(B[i])) {
                map.get(B[i]).list.add(i);
            } else {
                pair cp = new pair();
                cp.list.add(i);
                map.put(B[i], cp);
            }
        }

        int[] ans = new int[A.length];
        for (int i = 0; i < A.length; i++) {
            int ele = A[i];
            pair cp = map.get(ele);
            ans[i] = cp.list.get(cp.idx);
            cp.idx++;
        }
        return ans;
    }

    static class pair {
        int idx = 0;
        ArrayList<Integer> list;
        public pair() {
            list = new ArrayList<>();
        }
    }

    // valid anagram
    public static boolean validAnagram(String s, String p) {
        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        for (int i = 0; i < p.length(); i++) {
            char ch = p.charAt(i);
            if (!map.containsKey(ch)) return false;
            else if (map.get(ch) == 1) {
                map.remove(ch);
            } else {
                map.put(ch, map.get(ch) - 1);
            }
        }

        return map.size() == 0;
    }

    // group anagrams
    // given arr of strs group the anagrams
    public static ArrayList<ArrayList<String>> groupAnagram(String[] arr) {
        // we will store same strings in front of all freq maps
        HashMap<HashMap<Character, Integer>, ArrayList<String>> bmap = new HashMap<>();
        for (String str: arr) {
            HashMap<Character, Integer> fmap = new HashMap<>();
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                fmap.put(ch, fmap.getOrDefault(ch, 0) + 1);
            }

            if (bmap.containsKey(fmap)) {
                bmap.get(fmap).add(str);
            } else {
                ArrayList<String> list = new ArrayList<>();
                list.add(str);
                bmap.put(fmap, list);
            }
        }
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        for (ArrayList<String> val: bmap.values()) {
            ans.add(val);
        }
        return ans;
    }
    
    // group shifted strings
    public static ArrayList<ArrayList<String>> groupShiftedStr(String[] arr) {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        for (String str: arr) {
            String key = getKey(str);
            if (map.containsKey(key)) {
                map.get(key).add(str);
            } else {
                ArrayList<String> list = new ArrayList<>();
                list.add(str);
                map.put(key, list);
            }
        }
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        for (ArrayList<String> val: map.values()) {
            ans.add(val);
        }
        return ans;
    }

    public static String getKey(String str) {
        String key = "";
        for (int i = 1; i < str.length(); i++) {
            char curr = str.charAt(i);
            char prev = str.charAt(i - 1);
            int diff = curr - prev;
            if (diff < 0) diff += 26;
            key += diff + "@";
            // for eg: a - x
        }
        key += ".";
        return key;
    }

    // **** when there are grouping ques make hashmap of common key on the basis of which u can group al strings ***

    // word pattern
    public static boolean wordPattern(String s, String p) {
        // p -> abab, s = str1 str2 str3 ..
        String[] words = s.split(" ");
        HashMap<Character, String> map = new HashMap<>();
        HashMap<String, Boolean> vis = new HashMap<>();

        for (int i = 0; i < p.length(); i++) {
            char ch = p.charAt(i);

            if (map.containsKey(ch) == false) {
                if (vis.containsKey(words[i]) == true) {
                    // no char but word is vis
                    return false;
                } else {
                    vis.put(words[i], true);
                    map.put(ch, words[i]);
                }
            } else {
                // ch is present then check the word is valid or not
                if (words[i].equals(map.get(ch)) == false) return false; 
            }
        }
        return true;
    }

    // longest contiguous subarray with equal number of 0 and 1
    // let 0 = -1 and 1 = 1: find sum at each point and follow
    // sum at 'i' is y and later sum is y at 'j' : then sum = 0 in region i - j;
    // make hashmap of sum and indx
    public static int longestSubArrayEqual0and1(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int ans = 0, sum = 0;
        map.put(0, -1);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) sum += (-1);
            else if (arr[i] == 1) sum += 1;

            if (map.containsKey(sum)) {
                int idx = map.get(sum);
                ans = Math.max(ans, i - idx);
            } else {
                // we dont have to update regularly so as to get big len
                map.put(sum, i);
            }
        }
        return ans;
    }

    // count of subarrays with equal 0s and 1s
    // same approach as above
    public static int countSubArraysWithEqual0sAnd1s(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int sum = 0, ans = 0;
        map.put(0, 1);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) sum += (-1);
            else if (arr[i] == 1) sum += 1;

            if (map.containsKey(sum)) {
                ans += map.get(sum);
            } 
            // we have to count so we need to update the freq at each sum
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }

    // longest subarray with equal number of 0s, 1s and 2s
    public static int longestSubarrayWithEqual012(int[] arr) {
        HashMap<String, Integer> map = new HashMap<>();
        int c_0 = 0, c_1 = 0, c_2 = 0, ans = 0;
        String key = (c_1 - c_0) + "@" + (c_2 - c_1);
        map.put(key, -1);

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) c_0++;
            else if (arr[i] == 1) c_1++;
            else c_2++;

            key = (c_1 - c_0) + "@" + (c_2 - c_1);

            if (map.containsKey(key)) {
                int idx = map.get(key);
                ans = Math.max(ans, i - idx);
            } else {
                map.put(key, i);
            }
        }
        return ans;
    }

    // count of subarrays with equal 0s, 1s and 2s
    public static int countSubarraysWithEqual012(int[] arr) {
        HashMap<String, Integer> map = new HashMap<>();
        int c_0 = 0, c_1 = 0, c_2 = 0, ans = 0;
        String key = (c_1 - c_0) + "@" + (c_2 - c_1);
        // base case for 0 is 1;
        map.put(key, 1);

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) c_0++;
            else if (arr[i] == 1) c_1++;
            else c_2++;

            key = (c_1 - c_0) + "@" + (c_2 - c_1);

            if (map.containsKey(key)) {
                ans += map.get(key);
            }
            // update the freq of subarray at each point
            map.put(key, map.getOrDefault(key, 0) + 1);
        }
        return ans;
    }

    // pairs with equal sum
    // total pairs = nc2 => to select 2 ele from n
    // this could be done in n^2 complexity as we have to check all pairs
    public static boolean pairsWithEqualSum(int[] arr) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int sum = arr[i] + arr[j];
                if (set.contains(sum)) return true;
                set.add(sum);
            }
        }
        return false;
    }

    // isomorphic strings (same as word pattern)
    public static boolean isomorphic(String s, String p) {
        if (s.length() != p.length()) return false;

        HashMap<Character, Character> map = new HashMap<>();
        HashMap<Character, Boolean> vis = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c_s = s.charAt(i);
            char c_p = p.charAt(i);
            if (map.containsKey(c_s) == false) {
                if (vis.containsKey(c_p)) return false;
                else {
                    map.put(c_s, c_p);
                    vis.put(c_p, true);
                }
            } else {
                if (map.get(c_s) != c_p) return false;
            }
        }
        return true;
    }

    // recurring sequence in a fraction
    public static String recurringSeq(int N, int D) {
        StringBuilder sb = new StringBuilder();
        int q = N / D;
        int r = N % D;

        sb.append(q);
        if (r == 0) {
            return sb.toString();
        } else {
            sb.append(".");
            HashMap<Integer, Integer> map = new HashMap<>();
            while (r != 0) {
                if (map.containsKey(r)) {
                    int idx = map.get(r);
                    sb.insert(idx, "(");
                    sb.append(")");
                    break;
                } else {
                    map.put(r, sb.length());
                    r *= 10;
                    q = r / D;
                    r = r % D;
                    sb.append(q);
                }
            }
        }
        return sb.toString();
    }

    // rabbits in forest
    // for its theory refer notes
    public static int rabbitsInForest(int[] arr) {
        // create the fmap
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
        }
        int count = 0;
        for (Integer key: map.keySet()) {
            int groupSize = key + 1;
            int totalGroups = (int)Math.ceil((map.get(key) * 1.0) / (groupSize * 1.0));
            count += totalGroups * groupSize;
        }
        return count;
    }

    // check if given arr ele form AP sequence
    // an = a0 + (n - 1)d;
    // d = an - a0 / n - 1
    // an = max, a0 = min
    public static boolean isAp(int[] arr) {
        if (arr.length == 1) return true;
        int min = (int)(1e8), max = -(int)(1e8);
        HashSet<Integer> set = new HashSet<>();
        for (int val: arr) {
            min = Math.min(min, val);
            max = Math.max(max, val);
            set.add(val);
        }
        int d = (max - min) / (arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            // ele = a + d, a + 2d, a + 3d ....
            int ele = min + (i * d);
            if (!set.contains(ele)) return false;
        }
        return true;
    }

    // smallest subarray with all occurances of highest frequent ele
    public static void smallestSubArrWithOcc(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        HashMap<Integer, Integer> smap = new HashMap<>();

        int max_freq = 0, si = 0, ei = 0, len = ei - si + 1;
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(arr[i])) {
                map.put(arr[i], map.get(arr[i]) + 1);
            } else {
                map.put(arr[i], 1);
                smap.put(arr[i], i);
            }

            if (map.get(arr[i]) > max_freq) {
                si = smap.get(arr[i]);
                ei = i;
                max_freq = map.get(arr[i]);
                len = ei - si + 1;
            } else if (map.get(arr[i]) == max_freq) {
                // we have a new challenger to prev len
                int new_len = i - smap.get(arr[i]) + 1;
                if (new_len < len) {
                    si = smap.get(arr[i]);
                    ei = i;
                    len = new_len;
                }
            }
        }
        System.out.println(arr[si]);
        System.out.println(si);
        System.out.println(ei);
    }

    // task completion
    public static void taskCompletion(int n, int marr[]) {
        HashSet<Integer> set = new HashSet<>();
        for (int val: marr) set.add(val);
        String s1 = "", s2 = "";

        boolean sw = true;
        // true -> s1, false -> s2
        for (int i = 1; i <= n; i++) {
            if (!set.contains(i)) {
                if (sw) {
                    s1 += i + " ";
                }
                else {
                    s2 += i + " ";
                }
                sw = !sw;
            }
        }
        System.out.println(s1);
        System.out.println(s2);
    }

    // pairs with given sum in 2 sorted matrices

    // same approach as we did in finding 2 elements in arr that sum upto given tar: space o(n^2) time: o(n^2)
    // better approach: traverse them as 1d arr on matrices arr1 : start and arr2 : end point
    // now follow : if (sum > tar) light++ else heavy--;
    // same as we find tar pair sum in sorted arr
    public static int countValidPairs(int[][] m1, int[][] m2, int tar) {
        // both sorted
        // but in this for duplcate ele this will fail
        // HashSet<Integer> set = new HashSet<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int[] arr: m1) {
            for (int val: arr) map.put(val, map.getOrDefault(val, 0) + 1);
        }

        int count = 0;
        for (int[] arr: m2) {
            for (int val: arr) {
                if (map.containsKey(tar - val)) {
                    count += map.get(tar - val);
                }
            }
        }
        return count;
    }
    // two pointer approach
    // pairs with given sum in 2 sorted matrices
    public static int countValidPairs_2(int[][] m1, int[][] m2, int tar) {
        // it did not passed one test case, now passed
        // in this when same elements appear together from both matrices then the count += ele1 * ele2
        // where ele1 and ele2 are duplicates with sum = k
        int a = m1.length, b = m1[0].length;
        int p = m2.length, q = m2[0].length;
        int count = 0;
        int i = 0, j = (p * q) - 1;
        // time : o(n ^ 2)
        while (i < (a * b) && j >= 0) {
            int x = m1[i / b][i % b];
            int y = m2[j / q][j % q];
            if (x + y == tar) {
                // try that case in 1d
                // if (i + 1 < (a * b) && x == m1[(i + 1) / b][(i + 1) % b]) i++;
                // else if (j - 1 >= 0 && y == m2[(j - 1) / q][(j - 1) % q]) j--;
                // else {
                //     i++;
                //     j--;
                // }
                int ele_i = 1, ele_j = 1;
                while (i + 1 < (a * b) && x == m1[(i + 1) / b][(i + 1) % b]) {
                    ele_i++;
                    i++;
                }
                while (j - 1 >= 0 && y == m2[(j - 1) / q][(j - 1) % q]) {
                    ele_j++;
                    j--;
                }
                count += (ele_i * ele_j);
                // such that it comes to different value after trying all sum pairs which equals k
                i++; j--;
            }
            else if (x + y < tar) i++;
            else j--;
        }
        return count;
    }

    // pair with given sum : binary search but time is o(n^2 lgn)
    public static int firstIndex(int[][] arr, int data) {
        int n = arr.length, si = 0, ei = (n * n) - 1, idx = -1;
        while (si <= ei) {
            int mid = (si + ei) / 2;
            int mid_ele = arr[mid / n][mid % n];
            if (mid_ele == data) {
                idx = mid;
                // as we have to find first idx, ie move to left if we can
                // no check required for ei as it will be handled from while loop condition
                ei = mid - 1;
            } else if (mid_ele > data) {
                ei = mid - 1;
            } else {
                si = mid + 1;
            }
        }
        return idx;
    }

    public static int lastIndex(int[][] arr, int data) {
        int n = arr.length, si = 0, ei = (n * n) - 1, idx = -1;
        while (si <= ei) {
            int mid = (si + ei) / 2;
            int mid_ele = arr[mid / n][mid % n];
            if (mid_ele == data) {
                idx = mid;
                // as we have to find last idx, ie move to right if we can
                // no check required for ei as it will be handled from while loop condition
                si = mid + 1;
            } else if (mid_ele > data) {
                ei = mid - 1;
            } else {
                si = mid + 1;
            }
        }
        return idx;
    }

    public static int pairsWithGivenSum_binarySearch(int[][] m1, int[][] m2, int k) {
        int count = 0;
        for (int[] arr: m1) {
            for (int val: arr) {
                int fi = firstIndex(m2, k - val);
                if (fi == -1) continue;
                int li = lastIndex(m2, k - val);
                if (li == -1) continue;
                count += (li - fi + 1);
            }
        }
        return count;
    }


    // first non repeating character
    public static int firstNonRep(String str) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (map.get(ch) == 1) return i;
        }
        return -1;
    }

    // double pair array 
    public static boolean doublePair(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int val: arr) map.put(val, map.getOrDefault(val, 0) + 1);

        Integer[] n_arr = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) n_arr[i] = arr[i];

        // why sort? say it is not sorted, then for a value say 8 u will find 16 and let's say u won't get it
        // u may assume that it can't be paired even though 4 was there before 16 so it can be paired with that
        // therefore all ele get their 2 * x in sequence we sorted them
        Arrays.sort(n_arr, (th, o) -> {
            return Math.abs(th) - Math.abs(o);
        });

        for (Integer ele: n_arr) {
            // case 1: ele for which we look for double does not exist ie: it must have been used
            // then move fwd
            if (map.get(ele) == 0) continue;
            else if (map.getOrDefault(2 * ele, 0) == 0) {
                // the 2 * x is not there ie I have x but not able to pair with 2 * x
                return false;
            } else {
                // i have x and 2x, so use them
                map.put(ele, map.get(ele) - 1);
                map.put(2 * ele, map.get(2 * ele) - 1);
            }
        }
        return true;
    }

    //quadruplet sum (generic)
    static Integer three_val = null, four_val = null;

    public static ArrayList<ArrayList<Integer>> twoSum(int[] arr, int si, int ei, int k) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        while (si < ei) {
            int sum = arr[si] + arr[ei];
            if (sum < k) si++;
            else if (sum > k) ei--;
            else {
                ArrayList<Integer> ans = new ArrayList<>();
                ans.add(arr[si]);
                ans.add(arr[ei]);
                res.add(ans);
                si++; ei--;
                while (si < ei && arr[si] == arr[si - 1]) si++;
                while (si < ei && arr[ei] == arr[ei + 1]) ei--;
            }
        }
        return res;
    }

    public static ArrayList<ArrayList<Integer>> threeSum(int[] arr, int si, int ei, int k) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (int i = si; i <= ei; i++) {
            if (i != si && arr[i] == arr[i - 1]) continue;
            ArrayList<ArrayList<Integer>> ans = twoSum(arr, i + 1, ei, k - arr[i]);
            addIntoList(res, ans, arr[i]);
        }
        return res;
    }

    public static ArrayList<ArrayList<Integer>> fourSum_sol(int[] arr, int k) {
        Arrays.sort(arr);   
        int si = 0, ei = arr.length - 1;
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (int i = si; i <= ei; i++) {
            if (i != si && arr[i] == arr[i - 1]) continue;
            ArrayList<ArrayList<Integer>> ans = threeSum(arr, i + 1, ei, k - arr[i]);
            addIntoList(res, ans, arr[i]);
        }
        return res;
    }

    public static void addIntoList(ArrayList<ArrayList<Integer>> res, ArrayList<ArrayList<Integer>> ans, int val) {
        for (ArrayList<Integer> list: ans) {
            list.add(0, val);
            res.add(list);
        }
    }

    // quadruplet sum method - 2
    public static ArrayList<ArrayList<Integer>> quad_sum_2(int[] arr, int k) {
        Arrays.sort(arr);
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (i != 0 && arr[i] == arr[i - 1]) continue;

            for (int j = i + 1; j < arr.length; j++) {
                if (j != i + 1 && arr[j] == arr[j - 1]) continue;

                int si = j + 1, ei = arr.length - 1;
                while (si < ei) {
                    int sum = arr[i] + arr[j] + arr[si] + arr[ei];
                    if (sum > k) ei--;
                    else if (sum < k) si++;
                    else {
                        res.add(new ArrayList(Arrays.asList(arr[i], arr[j], arr[si], arr[ei])));
                        si++;
                        ei--;
                        
                        while (si < ei && arr[si] == arr[si - 1]) si++;
                        while (si < ei && arr[ei] == arr[ei + 1]) ei--;
                    }
                }
            }
        }
        return res;
    }

    // quadruplet sum using hashmap
    // given 4 arrays find a1 + a2 + a3 + a4 == k
    public static int count_quadruplet_combi(int[] a, int[] b, int[] c, int[] d, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (int a1: a) {
            for (int b1: b) {
                // as two pairs can have same value therefore we have to make freq map
                map.put(a1 + b1, map.getOrDefault(a1 + b1, 0) + 1);
            }
        }
        
        for (int c1: c) {
            for (int d1: d) {
                int sum = c1 + d1;
                if (map.containsKey(k - sum)) {
                    count += map.get(k - sum);
                }
            }
        }
        return count;
    }

    // powerful numbers
    public static ArrayList<Integer> powerfulNum(int x, int y, int bound) {
        HashSet<Integer> set = new HashSet<>();
        // according to given constraints i and j < 20
        for (int i = 1; i < bound; i *= x) {
            // logx (b)
            for (int j = 1; i + j <= bound; j *= y) {
                // logy (b)
                set.add(i + j);
                if (y == 1) break;
            }
            if (x == 1) break;
        }
        // time : o(logx (b) * logy (b))
        // space: totalcombi = o(logx b * logy b)
        return new ArrayList<>(set);
    }
    

    public static void solve() {

    }
    
    public static void main(String args[]) {
        solve();
    }
}