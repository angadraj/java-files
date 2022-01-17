import java.util.*;
class hashmap {
    
    // k largest(or smallest) elements in an array
    public static ArrayList<Integer> kLargest(int[] arr, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            pq.add(arr[i]);
            if (pq.size() > k) {
                pq.remove();
            }
        }
        int[] ans = new int[k];
        int idx = k - 1;
        while (pq.size() > 0) {
            ans[idx--] = pq.remove();
        }
        ArrayList<Integer> res = new ArrayList<>();
        for (int val: ans) res.add(val);
        return res;
    }

    // ngor
    public static long[] ngor(long[] arr) {
        int n = arr.length;
        long[] ans = new long[n];
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (st.size() > 0 && arr[i] > arr[st.peek()]) {
                ans[st.pop()] = arr[i];
            }
            st.push(i);
        }
        while (st.size() > 0) {
            ans[st.pop()] = -1;
        }
        return ans;
    }
    
    // kth smallest ele
    public static int kthSmallest(int[] arr, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((t, o) -> {
            return o - t;
        });
        for (int i = 0; i < arr.length; i++) {
            pq.add(arr[i]);
            if (pq.size() > k) pq.remove();
        }
        return pq.peek();
    }

    // sliding window maximum
    public static ArrayList<Integer> slidingWindow(int[] arr, int k) {
        ArrayList<Integer> res = new ArrayList<>();
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < arr.length; i++) {
            if (dq.size() != 0 && dq.peek() == i - k) {
                dq.removeFirst();
            } 
            while (dq.size() != 0 && arr[i] >= arr[dq.peekLast()]) {
                dq.removeLast();
            }
            dq.addLast(i);
            if (i >= k - 1) {
                res.add(arr[dq.peek()]);
            }
        }
        return res;
    }

    // Find the smallest positive number missing from an unsorted array
    public static int smallestPosMissing(int[] arr) {
        boolean isOne = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) isOne = true;
            // mark elements bigger than range or < 1 to 1 
            if (arr[i] > arr.length || arr[i] < 1) arr[i] = 1; 
        }
        if (!isOne) return 1;
        for (int i = 0; i < arr.length; i++) {
            int idx = Math.abs(arr[i]);
            arr[idx - 1] = -Math.abs(arr[idx - 1]);
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 0) return i + 1;
        }
        return arr.length + 1;
    }

    // Find the maximum repeating number in O(n) time and O(1) extra space
    // arr[i] = 0 to k - 1, k <= n, 
    // In such situations we use hashing
    public static int maxRepeatingNumber(int[] arr, int k) {
        for (int i = 0; i < arr.length; i++) {
            int idx = arr[i];
            arr[idx % k] += k;
        }
        int max = arr[0], idx = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
                idx = i;
            }
        }
        // here number was the index, therefore max repeating ele will be index captured
        return idx;
    }

    public static int maxRepeatingNumber2(int[] arr, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
        }
        int ele = (int)(1e8), freq = -(int)(1e8);
        for (Integer key: map.keySet()) {
            int val = map.get(key);
            if (val > freq) {
                freq = val;
                ele = key;
            } else if (val == freq) {
                ele = Math.min(ele, key);
            }
        }
        return ele;
    }

    // Online algorithm for checking palindrome in a stream
    // algo is clear but how hashvalue is calculated is not clear
    static int d = 256, q = 103;
    public static void streamPall(String str) {
         // Length of input string
        int N = str.length();
       
        // A single character is always a palindrome
        System.out.println(str.charAt(0)+ " Yes");
       
        // Return if string has only one character
        if (N == 1) return;
       
        // Initialize first half reverse and second 
        // half for as firstr and second characters
        int firstr  = str.charAt(0) % q;
        int second = str.charAt(1) % q;
       
        int h = 1, i, j;
       
        // Now check for palindromes from second 
        // character onward
        for (i = 1; i < N; i++)
        {
            // If the hash values of 'firstr' and
            // 'second' match, then only check 
            // individual characters
            if (firstr == second)
            {
                /* Check if str[0..i] is palindrome
                using simple character by character 
                 match */
                for (j = 0; j < i/2; j++)
                {
                    if (str.charAt(j) != str.charAt(i 
                                               - j))
                        break;
                }
                System.out.println((j == i/2) ? 
                  str.charAt(i) + " Yes": str.charAt(i)+
                  " No");
            }
            else System.out.println(str.charAt(i)+ " No");
       
            // Calculate hash values for next iteration.
            // Don't calculate hash for next characters
            // if this is the last character of string
            if (i != N - 1)
            {
                // If i is even (next i is odd) 
                if (i % 2 == 0)
                {
                    // Add next character after first 
                    // half at beginning of 'firstr'
                    h = (h * d) % q;
                    firstr  = (firstr + h *str.charAt(i / 
                                                 2)) % q;
                       
                    // Add next character after second 
                    // half at the end of second half.
                    second = (second * d + str.charAt(i + 
                                                1)) % q;
                }
                else
                {
                    // If next i is odd (next i is even)
                    // then we need not to change firstr,
                    // we need to remove first character
                    // of second and append a character
                    // to it.
                    second = (d * (second + q - str.charAt(
                             (i + 1) / 2) * h) % q +
                               str.charAt(i + 1)) % q;
                }
            }
        }
    }

    // K’th largest element in a stream
    public static void kthLargestInStream(int[] arr, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int n = arr.length;
        // min type
        for (int i = 0; i < n; i++) {
        // If size of priority
        // queue is less than k
            if (pq.size() < k) {
                pq.add(arr[i]);
            } else {
                if (arr[i] > pq.peek()) {
                    pq.remove();
                    pq.add(arr[i]);
                }
            }
 
        // If size is less than k
            if (pq.size() < k) {
                // arr[i] = -1;
                System.out.print("-1 ");
            } else {
                // arr[i] = pq.peek();
                System.out.print(pq.peek() + " ");
            }
        }
        // return arr;
    }

    // find kth smallest after removing some elements from natural numbers
    public static int kthSmallAfterRemoval(int[] arr, int k) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] <= k) k++;
            else break;
        }
        // working: 1, 2, 3, 4, 5, 6, 7 : del 3, 5, 7 then k will increment
        // for 3: k = 5, for 5: k = 6, at 7 it will break and k = 6 will be ans
        return k;
    }

    // Find Surpasser Count of each element in array
    // similar to invesion count
    public static int[] surpasserCount(int[] arr) {
        int n = arr.length;
        int[] ans = new int[n];
        int[] res = new int[n];
        HashMap<Integer, Integer> map = new HashMap<>();
        countInversions(arr, ans, 0, n - 1, map);
        for (int i = 0; i < arr.length; i++) {
            int ic = map.getOrDefault(arr[i], 0);
            res[i] = ic;
        }
        for (int val: res) System.out.print(val + " ");
        return res;
    }

    public static void countInversions(int[] arr, int[] ans, int si, int ei, HashMap<Integer, Integer> map) {
        if (si == ei) {
            return;
        }
        int mid = (si + ei) >> 1;
        countInversions(arr, ans, si, mid, map);
        countInversions(arr, ans, mid + 1, ei, map);
        merge(arr, si, mid, ei, ans, map);
    }

    public static int merge(int[] arr, int si, int mid, int ei, int[] ans, HashMap<Integer, Integer> map) {
        int count = 0;
        int i = si, j = mid + 1, k = si;
        while (i <= mid && j <= ei) {
            if (arr[i] > arr[j]) {
                map.put(arr[i], map.getOrDefault(arr[i], 0) + count);
                ans[k++] = arr[i++];
            } else {
                ans[k++] = arr[j++];
                // count += mid - i + 1;
                count++;
            }
        }
        while (i <= mid) {
            map.put(arr[i], map.getOrDefault(arr[i], 0) + count);
            ans[k++] = arr[i++];
        }
        while (j <= ei) {
            ans[k++] = arr[j++];
        }
        // copy all elements in arr
        i = si; j = ei;
        while (i <= j) {
            arr[i] = ans[i];
            i++;
        }
        return count;
    }

    // a pancake sorting question
    public static List<Integer> pancakeSorting(int[] arr) { 
        List<Integer> ans = new ArrayList<>();
        for (int i = arr.length - 1; i >= 0; i--) {
            int maxi = i;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[maxi] < arr[j]) j = maxi;
            }
            if (maxi != i) {
                reverse(arr, 0, maxi);
                ans.add(maxi + 1);
                reverse(arr, 0, i);
                ans.add(i + 1);
            }
        }
        return ans;
    }

    public static void reverse(int[] arr, int si, int ei) {
        while (si < ei) {
            int temp = arr[si];
            arr[si] = arr[ei];
            arr[ei] = temp;
            si++; ei--;
        }
    }

    // Choose k array elements such that difference of maximum and minimum is minimized
    public static int minDiffBetweenMaxAndMin(int[] arr, int k) {
        Arrays.sort(arr);
        int min = (int)(1e8), n = arr.length;
        for (int i = 0; i <= n - k; i++) {
            int diff = arr[i + k - 1] - arr[i];
            min = Math.min(min, diff);
        }
        return min;
    }

    // Find k closest elements to a given value
    // Given a sorted array arr[] and a value X, find the k closest elements to X in arr[].
    public static int[] kClosestEle(int[] arr, int x, int k) {
        int si = 0, ei = arr.length - 1, mid = -1;
        while (si <= ei) {
            mid = (si + ei) >> 1;
            if (arr[mid] == x) {
                break;
            } else if (x < arr[mid]) ei = mid - 1;
            else si = mid + 1;
        }
        // initialize l and r
        int l = mid - 1, r = mid;
        if (arr[l] == x) l--;
        int[] ans = new int[k];
        int count = 0, j = 0;
        while (l >= 0 && r < arr.length && count < k) {
            if (Math.abs(x - arr[l]) <= Math.abs(arr[r] - x)) {
                ans[j++] = arr[l--];
            } else {
                ans[j++] = arr[r++];
            }
            count++;
        }
        while (l > 0 && count < k) {
            if (Math.abs(arr[l] - x) <= Math.abs(arr[l - 1] - x)) {
                ans[j++] = arr[l];
                count++;
            }
            l--;
        }
        while (r < arr.length - 1 && count < k) {
            if (Math.abs(arr[r] - x) <= Math.abs(arr[r + 1] - x)) {
                ans[j++] = arr[r];
                count++;
            }
            r++;
        }
        // for (int val: ans) System.out.print(val + " ");
        return ans;
    }

    // Tournament Tree (Winner Tree) and Binary Heap
    // theory and concept based

    // Connect n ropes with minimum cost
    public static int minCostToJoinNRopes(int[] arr) {
        int cost = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int val: arr) pq.add(val);

        while (pq.size() > 1) {
            int v1 = pq.remove();
            int v2 = pq.remove();
            int sum = v1 + v2;
            cost += sum;
            pq.add(sum);
        }
        return cost;
    }

    // design a efficient data structure for given operations
    // maintain DLL in sorted order

    // Cuckoo Hashing – Worst case O(1) Lookup!
    // theory and concept (see from youtube)

    // Find number of Employees Under every Manager
    public static void numberOfEmp(HashMap<String, String> map) {
        // map: emp vs manager
        HashMap<String, HashSet<String>> map2 = new HashMap<>();
        String ceo = "";
        for (String emp: map.keySet()) {
            String man = map.get(emp);
            if (man.equals(emp)) ceo = man;
            else {
                if (!map2.containsKey(man)) {
                    map2.put(man, new HashSet<>());
                }
                map2.get(man).add(emp);
            }
        }
        // to find number of emp under each manager individually
        HashMap<String, Integer> map3 = new HashMap<>();
        findSize(map2, ceo, map3);
        System.out.print(map3);
    }

    public static int findSize(HashMap<String, HashSet<String>> map2, String ceo, HashMap<String, Integer> map3) {
        if (map2.containsKey(ceo) == false) {
            map3.put(ceo, 0);
            return 1;
        }
        int mysize = 0;
        for (String emp: map2.get(ceo)) {
            mysize += findSize(map2, emp, map3);
        }
        map3.put(ceo, mysize);
        return mysize + 1;
    } 

    // Find Itinerary from a given list of tickets
    public static void itinerary(HashMap<String, String> map) {
        HashMap<String, Boolean> hm = new HashMap<>();
        String start = "";
        for (String src: map.keySet()) {
            // the destination can't be start   
            String dest = map.get(src);
            hm.put(dest, false);
            // now src can be a start, but if it comes to be someones dest then it can't be start
            if (!hm.containsKey(src)) {
                hm.put(src, true);
            }
        }
        for (String src: hm.keySet()) {
            if (hm.get(src)) start = src;
        }
        while (true) {
            if (map.containsKey(start)) {
                System.out.print(start + " -> ");
                start = map.get(start);
            } else {
                System.out.print(start);
                break;
            }
        }
    }

    // Check if an array can be divided into pairs whose sum is divisible by k
    public static boolean pairSumDivisbleByK(int[] arr, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            int rem = arr[i] % k;
            map.put(rem , map.getOrDefault(rem, 0) + 1); 
        }
        for (int i = 0; i < arr.length; i++) {
            int rem = arr[i] % k;
            int freq = map.get(rem);
            if (rem == 0) {
                if ((freq & 1) == 1) return false;
            } else if (2 * rem == k) {
                if ((freq & 1) == 1) return false;
            } else if (map.getOrDefault(k - rem, 0) != freq) {
                return false;
            }
        }
        return true;
    }

    // Find the length of largest subarray with 0 sum
    public static int largestSubArrWith0Sum(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        // sum, idx
        int len = 0, sum = 0;
        map.put(0, -1);
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (map.containsKey(sum)) {
                len = Math.max(len, i - map.get(sum));
            } else {
                map.put(sum, i);
            }
        }
        return len;
    }

    // Count distinct elements in every window of size k
    public static ArrayList<Integer> countDisEleInKWin(int[] arr, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
        }
        ans.add(map.size());
        for (int i = k; i < arr.length; i++) {
            // remove out of bound element
            int outOfBoundEle = arr[i - k];
            if (map.get(outOfBoundEle) == 1) {
                map.remove(outOfBoundEle);
            } else {
                map.put(outOfBoundEle, map.get(outOfBoundEle) - 1);
            }
            // insert current ele
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
            // calculate distinct elements till now
            ans.add(map.size());
        }
        return ans;
    }

    // Design a data structure that supports insert, delete, search and getRandom in constant time
    static class randomDs {
        HashMap<Integer, Integer> map;
        ArrayList<Integer> arr;
        public randomDs() {
            map = new HashMap<>();
            arr = new ArrayList<>();
        }

        public void add(int val) {
            if (map.containsKey(val)) return;

            int size = arr.size();
            // size is index
            map.put(val, size);
            arr.add(val);
        }

        public void remove(int val) {
            if (map.containsKey(val) == false) return;

            int vidx = map.get(val);
            map.remove(val);
            // swap the last val and val to be removed
            int temp = arr.get(arr.size() - 1);
            arr.set(vidx, temp);
            arr.remove(arr.size() - 1);
            // update the value in hasmap
            map.put(temp, vidx);
        }

        public int getRand() {
            Random r = new Random();
            int idx = r.nextInt(arr.size());
            return arr.get(idx);
        }

        public int search(int x) {
            // returns the idx at which x is present
            return map.get(x);
        }
    }

    // Length of the largest subarray with contiguous elements
    // array may have duplicates also
    public static int largestSubArrWithContigSeq(int[] arr) {
        int len = 0;
        for (int i = 0; i < arr.length; i++) {
            // to handle dups
            HashSet<Integer> set = new HashSet<>();
            set.add(arr[i]);
            int min = arr[i], max = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (set.contains(arr[j])) break;
                min = Math.min(min, arr[j]);
                max = Math.max(max, arr[j]);

                if (max - min == j - i) {
                    len = Math.max(len, j - i + 1);
                }
                set.add(arr[j]);
            }
        }
        return len;
    }

    public static void solve() {
       int[] arr = {10, 12, 12, 10, 10, 11, 10};
       int ans = largestSubArrWithContigSeq(arr);
       System.out.println(ans);
    }

    public static void main(String[] args) {
        solve();
    }
}