import java.util.*;
class Sorting {
    
    // majority element
    public static int majorityEle_1(int[] arr) {
        int count = 1, ele = arr[0], n = arr.length;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == ele) {
                count++;
            } else {
                count--;
                if (count == 0) {
                    count = 1;
                    ele = arr[i];
                } 
            }
        }
        // ele has potential majority ele
        if (count > n / 2) return ele;
        else {
            count = 0;
            for (int val: arr) {
                if (val == ele) count++;
            }
            return (count > n / 2) ? ele : -1;
        }
    }

    // Searching in an array where adjacent differ by at most k
    public static int searchAdjDifferByK(int[] arr, int k, int x) {
        int i = 0;
        while (i < arr.length) {
            if (arr[i] == x) return i;
            int jump = Math.max(1, Math.abs(arr[i] - x) / k);
            // max to handle 0 jumps edge case
            // 2, 4, 6, 8, 9, 10 -> k = 2, x = 10
            i = i + jump;
        }
        // time: o(n)
        return -1;
    }

    // Given an unsorted array of size n. Array elements are in the range from 1 to n.
    // One number from set {1, 2, …n} is missing and one number occurs twice in the array. Find these two numbers.
    public static void repeatingAndMissing(int[] arr) {
        int n = arr.length, repeating = -1, missing = -1;
        // for (int val: arr) sum += val;
        for (int i = 0; i < n; i++) {
            int idx = Math.abs(arr[i]) - 1;
            if (arr[idx] < 0) {
                repeating = Math.abs(arr[i]);
            } else arr[idx] = -(Math.abs(arr[idx]));
        }
        for (int i = 0; i < n; i++) {
            if (arr[i] > 0) {
                missing = i + 1;
            }
        }
        // int missing = ((n * (n + 1)) / 2) - sum + repeating;
        System.out.println(repeating + " , " + missing);
    }

    // Ceiling & floor in a sorted array
    // arr[0] will not have a floor and arr[len - 1] will not have a ceil in the array
    public static int[] ceilAndFloor(int[] arr, int x) {
        // floor, ceil
        // according to gfg -> ele is not in arr the ceil will be itself
        int[] ans = new int[]{(int)(1e8), -(int)(1e8)};
        int n = arr.length;

        if (arr[0] == x) {
            // only ceil
            ans[0] = arr[0];
            ans[1] = arr[1];
            return ans; 
        } else if (arr[n - 1] == x) {
            // only floor
            ans[0] = arr[n - 2];
            ans[1] = arr[n - 1];
            return ans;
        } else if (x < arr[0]) {
            // only ceil
            ans[0] = x;
            ans[1] = arr[0];
            return ans;
        } else if (x > arr[n - 1]) {
            // only floor
            ans[0] = arr[n - 1];
            ans[1] = x;
            return ans;
        }

        int si = 0, ei = n - 1;
        boolean done = false; 
        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (arr[mid] == x) {
                ans[0] = arr[mid - 1];
                ans[1] = arr[mid];
                done = true;
                break;
            } else if (x < arr[mid]) ei = mid - 1;
            else si = mid + 1;
        } 
        if (!done) {
            // now running condition was si <= ei, si > ei -> break
            ans[0] = arr[ei]; // ceil
            ans[1] = arr[si];
        }
        return ans;
    }

    // Find a pair with the given difference
    public static boolean findPairWithDiff(int[] arr, int k) {
        Arrays.sort(arr);
        int i = 0, j = 1;
        while (j < arr.length) {
            int diff = arr[j] - arr[i];
            if (diff == k) {
                return true;
            }
            else if (diff < k) j++;
            else i++;
        }
        return false;
    }

    // Given an array of integers, find 
    // anyone combination of four elements in the array whose sum is equal to a given value X.
    public static ArrayList<ArrayList<Integer>> fourSum(int[] arr, int x) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        // Arrays.sort(arr);
        int n = arr.length;
        // also prevent duplicacy
        // {1, 1, 1, 2, 2, 3, 4, 5}
        for (int i = 0; i <= n - 4; i++) {
            if (i != 0 && arr[i] == arr[i - 1]) continue;

            for (int j = i + 1; j < n; j++) {
                if (j != i + 1 && arr[j] == arr[j - 1]) continue;

                int k = j + 1, l = n - 1;
                while (k < l) {
                    int sum = arr[i] + arr[j] + arr[k] + arr[l];
                    if (sum == x) {
                        ArrayList<Integer> ans = new ArrayList<>();
                        ans.add(arr[i]); ans.add(arr[j]); ans.add(arr[k]); ans.add(arr[l]);
                        res.add(ans);
                        k++; l--;
                        while (k < l && arr[k] == arr[k - 1]) k++;
                        while (k < l && arr[l] == arr[l + 1]) l--;

                    } else if (sum < x) k++;
                    else l--;
                }
            }
        }
        // how to sort 2d arraylist internally
        // for (ArrayList<Integer> list: res) Collections.sort(list);
        // this ques has more solutions, but is uses o(n ^ 2) space
        return res;
    }

    // Median of two sorted arrays of different sizes   
    public static double medianOfTwoSortedArrays(int[] a, int[] b) {
        int len1 = a.length, len2 = b.length, total = (len1 + len2);
        int mainMid = (total >> 1), si = 0, ei = len1;
        double median = 0.0;

        while (si <= ei) {
            int m1 = (si + ei) >> 1;
            int m2 = mainMid - m1;

            int aleft = (m1 - 1 < 0) ? -(int)(1e8) : a[m1 - 1];
            int bleft = (m2 - 1 < 0) ? -(int)(1e8) : b[m2 - 1];
            int aright = (m1 >= len1) ? (int)(1e8) : a[m1];
            int bright = (m2 >= len2) ? (int)(1e8) : b[m2];

            if (aleft <= bright && bleft <= aright) {
                if ((total & 1) == 1) {
                    median = a[m1];
                }
                else {
                    int leftMax = Math.max(aleft, bleft);
                    int rightMin = Math.min(aright, bright);
                    median = (leftMax + rightMin) / (2.0);
                }
                return median;

            } else if (bleft > aright) si = m1 + 1;
            else ei = m1 - 1;
        }
        return 0.0;
    }

    // max sum of a subsequence such that no elements are adjacent
    public static int maxSumNonAdjacent_rec(int[] arr, int si, boolean flag) {
        if (si >= arr.length) return 0;

        int val = arr[si];
        int inc = maxSumNonAdjacent_rec(arr, si + 1, false);
        int exc = maxSumNonAdjacent_rec(arr, si + 1, true);

        if (flag) {
            inc += val;
            // inc and exc are 0s at all moment, this is where it adds the value
        }

        return Math.max(inc, exc);
    }

    public static int maxSumNonAdjacent(int[] arr) {
        if (arr.length == 0) return 0;
        int exc = 0, inc = arr[0];
        for (int i = 1; i < arr.length; i++) {
            int newInc = exc + arr[i];
            int newExc = Math.max(inc, exc);

            inc = newInc;
            exc = newExc;
        }
        return Math.max(inc, exc);
    }

    // common elements in 3 sorted arrays
    public static ArrayList<Integer> commonInThreeSortedArr(int[] a, int[] b, int[] c) {
        int i = 0, j = 0, k = 0, n1 = a.length, n2 = b.length, n3 = c.length;
        ArrayList<Integer> ans = new ArrayList<>();

        while (i < n1 && j < n2 && k < n3) {
            int min = Math.min(a[i], Math.min(b[j], c[k]));
            if (min == a[i] && min == b[j] && min == c[k]) {
                i++; j++; k++;
                ans.add(min);
            } else if (min == a[i]) i++;
            else if (min == b[j]) j++;
            else if (min == c[k]) k++;
        }
        return ans;
    }

    public static ArrayList<Integer> commonInThreeSortedArr_gfg(ArrayList<Integer> a, ArrayList<Integer> b) {
        int i = 0, j = 0, n1 = a.size(), n2 = b.size();
        ArrayList<Integer> ans = new ArrayList<>();

        while (i < n1 && j < n2) {
            int aval = a.get(i), bval = b.get(j);
            
            int min = Math.min(aval, bval);
            
            if (min == aval && min == bval) {
                i++; j++;
                ans.add(min);
            } else if (min == aval) i++;
            else if (min == bval) j++;
        }
        return ans;
    }

    // ***************************** (important) PREVENT EXTRA LOOP ************************

    // Count triplets with sum smaller than a given value
    // expected time: o(n ^ 2)
    // given all distinct but we will go for assuming there are dups
    public static int countTripletsWithSumLessThanVal(int[] arr, int x) {
        Arrays.sort(arr);
        int count = 0, n = arr.length;
        for (int i = 0; i <= n - 3; i++) {
            int j = i + 1, k = n - 1;
            while (j < k) {
                int sum = arr[i] + arr[j] + arr[k];
                if (sum >= x) k--;
                else {
                    count += (k - j);
                    // this will count all elements so it prevents loop for j++;
                    // also if at a post of k sum < x, then all elements between j and k will result in 
                    // sum < x only
                    j++;
                }
            }
        }
        return count;
    }

    // *************************************************************************

    // merge two sorted arrays in o(1) space
    // plogp solution -> p = m + n
    public static void mergeInConstSpace(int[] a, int[] b) {
        // if (a.length < b.length) {
        //     int[] temp = a;
        //     a = b;
        //     b = temp;
        // }
        int n = a.length, m = b.length, total = n + m;
        int gap = (int)(Math.ceil(total / (2.0)));
        while (gap > 1) {
            int i = 0, j = i + gap - 1;
            while (j < total) {
                int ith = 0, jth = 0;
                // to check in which array i and j lies to make swap 
                if (i < n) {
                    // i is in array a
                    ith = a[i];
                } else {
                    // i is in array b
                    ith = b[i - n];
                }

                if (j < n) {
                    jth = a[j];
                } else {
                    jth = b[j - n];
                }

                if (ith >= jth) {
                    if (i < n && j < n) {
                        int temp = a[i];
                        a[i] = a[j];
                        a[j] = temp;

                    } else if (i < n && j >= n) {
                        int temp = a[i];
                        a[i] = b[j - n];
                        b[j - n] = temp;

                    } else if (i >= n && j >= n) {
                        int temp = b[i - n];
                        b[i - n] = b[j - n];
                        b[j - n] = temp;
                    }
                }
                i++; j++;
            }
            gap = (int)(Math.ceil(gap / (2.0)));
        }
        // just in case there are dups, so we need to sort the array once
        Arrays.sort(a);
        Arrays.sort(b);
        print(a, b);
    }

    // mergeInConstSpace_2
    public static void mergeInConstSpace_2(int[] a, int[] b) {
        int n = a.length, m = b.length, i = 0, j = 0, k = n - 1;

        while (i <= k && j < m) {
            if (a[i] < b[j]) i++;
            else {
                int temp = b[j];
                b[j] = a[k];
                a[k] = temp;
                j++; k--;
            }
        }
        // now all the elements will be gone in their respective arrays, but they will be jumbled
        Arrays.sort(a);
        Arrays.sort(b);
    }

    // partitioning an array: segregate ele smaller than pivot at left and greater than at right
    // given pivot
    // this is the part of quicksort
    public static int partitioning(int[] arr, int pivot, int lo, int hi) {
        int i = lo, j = lo;
        // 0 , j - 1 -> < p
        // j, i - 1 -> > p
        // i, end -> unknown
        while (i <= hi) {
            if (arr[i] > pivot) i++;
            else {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++; j++;
            }
        }
        return (j - 1);
    }

    // Quicksort 
    public static void quickSortRec(int[] arr, int lo, int hi) {
        if (lo >= hi) return;
        
        int pivot = arr[hi];
        int idx = partitioning(arr, pivot, lo, hi);

        quickSortRec(arr, lo, idx - 1);
        quickSortRec(arr, idx + 1, hi);
    }
    

    public static void print(int[] a, int[] b) {
        for (int val: a) System.out.print(val + " ");
        System.out.println();
        for (int val: b) System.out.print(val + " ");
        System.out.println();
        System.out.println();
    }

    // count sort
    // it is stable and elements should be sorted in their relative order even if there are dups
    // it can be applied when data is large but range is less
    public static void countSort(int[] arr) {
        int min = (int)(1e8), max = -(int)(1e8);
        for (int val: arr) {
            min = Math.min(min, val);
            max = Math.max(max, val);
        }
        int[] farr = new int[max - min + 1];
        for (int i = 0; i < arr.length; i++) {
            farr[arr[i] - min]++;
        }
        // now make psa to get last index till where the element will come
        for (int i = 1; i < farr.length; i++) {
            farr[i] += farr[i - 1];
        }
        // we will fill from back side such that lexicographic order is followed
        int[] ans = new int[arr.length];
        for (int i = arr.length - 1; i >= 0; i--) {
            int val = arr[i];
            int pos = farr[val - min];
            // pos - 1 to get the idx
            ans[pos - 1] = val;
            // reduce the idx on freq arr to get new idx next time
            farr[val - min]--;
        }
        for (int val: ans) System.out.print(val + " ");
    }

    // Radix sort
    public static void radixSort(int[] arr) {
        // till radix sort will call count sort ?
        // the max number
        int max = -(int)(1e8);
        for (int val: arr) max = Math.max(val, max);
        //
        int exp = 1;
        while (exp <= max) {
            // we will call count sort for each of the places
            radixSortHelper(arr, exp);
            exp *= 10;
        }
        for (int val: arr) System.out.print(val + " ");
        // time: len(max) * n
    } 

    public static void radixSortHelper(int[] arr, int exp) {
        // count sort for radix sort
        // num / 10^pow = drops unit, tens etc places
        // num % 10 = gives the last digit 
        int n = arr.length;
        int[] ans = new int[n];
        // as number of digits will vary from 0 - 9
        int[] farr = new int[10];
        for (int i = 0; i < n; i++) {
            // to extract the number
            farr[(arr[i] / exp) % 10]++;
        }
        // make prefix sum of farr
        for (int i = 1; i < farr.length; i++) farr[i] += farr[i - 1];
        // 
        for (int i = n - 1; i >= 0; i--) {
            int val = arr[i];
            int pos = farr[arr[i] / exp) % 10];
            ans[pos - 1] = val;
            farr[arr[i] / exp) % 10]--;
        }
        // copy the elements from ans to arr
        for (int i = 0; i < n; i++) arr[i] = ans[i];
    }

    // sort dates (Radix sort application)
    // we will sort on basis of year at last as it is in least precedence
    public static void sortDates(String[] arr) {
        // days, month, year
        sortDatesHelper(arr, 100000, 100, 32);
        sortDatesHelper(arr, 10000, 100, 13);
        sortDatesHelper(arr, 1, 10000, 2501);
    }

    public static void sortDatesHelper(String[] arr, int div, int exp, int range) {
        // 1. make a freq map of elements of arr
        int n = arr.length;
        int[] farr = new int[range];
        for (int i = 0; i < n; i++) {
            farr[Integer.parseInt(arr[i], 10) / div % exp]++;
            // mention the base so it is clear for the machine that all numbers are to treated in base 10
        }
        // 2. make prefix sum of this farr
        for (int i = 1; i < farr.length; i++) farr[i] += farr[i - 1];

        // 3. make an aux array and place the elements into it at the pos you get from farr
        String[] ans = new String[n];
        for (int i = n - 1; i >= 0; i--) {
            int val = Integer.parseInt(arr[i], 10) / div % exp;
            int pos = farr[val];
            farr[val]--;
            ans[pos - 1] = arr[i];
        }
        // 4. copy all the elements from aux array into the original array
        for (int i = 0; i < n; i++) arr[i] = ans[i];
    }
 
    // median of a running stream of integers
    // algos running on running streams are called "ONLINE ALGO"

    static class RunningMedian {
        PriorityQueue<Integer> max, min;

        public RunningMedian() {
            max = new PriorityQueue<>(Collections.reverseOrder());
            min = new PriorityQueue<>();
        }
    
        public void addNum(int num) {

            if (max.size() == 0 || num <= max.peek()) max.add(num);
            else if (min.size() == 0 || num > max.peek()) min.add(num);


            if (min.size() - max.size() == 2) {
                max.add(min.remove());

            } else if (max.size() - min.size() == 2) {
                min.add(max.remove());
            }
        }
    
        public double findMedian() {
            double median = 0.0;

            if (max.size() == min.size()) {
                median = (max.peek() + min.peek()) / (2.0);
            } else {
                if (max.size() > min.size()) median = max.peek();
                else median = min.peek();
            }
            return median;
        }
    }

    // Given an array which contains integer values, we need to make 
    // all values of this array equal to some integer value with minimum cost
    // where the cost of changing an array value x to y is abs(x-y). 
    public static int minCostToMakeArrayEqual(int[] arr) {
        Arrays.sort(arr);
        int median = arr[arr.length / 2], cost = 0;
        // convert all elements to median
        for (int i = 0; i < arr.length; i++) {
            cost += Math.abs(median - arr[i]);
        }
        return cost;
    }

    // Given an array of distinct n integers. The task is to check whether reversing "ONE"
    // sub-array make the array sorted or not.
    // If the array is already sorted or by reversing a subarray once make it sorted, print “Yes”, else print “No”.
    public static boolean reversingSubArrMakesArrSorted(int[] arr) {
        int n = arr.length, i = 0, count = 0;
        while (i < n - 1) {
            boolean found = false;
            while (i < n - 1 && arr[i] > arr[i + 1]) {
                i++;
                found = true;
            }
            if (found) {
                count++;
            }
            i++;
        }
        // count = 0 or < 1 true (sorted, only one decreasing subarray)
        if (count <= 1) return true;
        else return false;
        // time: o(n), space: o(1)
    }

    // get si and ei all subarrays with 0 sum
    // 1. using hashmap 
    // 2. A/R strategy
    public static ArrayList<ArrayList<Integer>> getIndicesSubArrWithSum0(int[] arr, int k) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        int i = -1, j = -1, sum = 0, count = 0, n = arr.length;
        while (true) {
            boolean f1 = false, f2 = false;
            while (i < n - 1) {
                i++;
                f1 = true;
                sum += arr[i];
                if (sum == k) {
                    ArrayList<Integer> ans = new ArrayList<>();
                    ans.add(j + 1); ans.add(i);
                    res.add(ans);
                    count++;
                    break;
                } else if (sum > k) break;
            }
            while (j < i) {
                j++;
                f2 = true;
                sum -= arr[j];
                if (sum < k) break;
                else if (sum == k) {
                    // to make sure that sum does not equals k on release of same ele which has been 
                    // acquired now
                    if (i > j) {
                        ArrayList<Integer> ans = new ArrayList<>();
                        ans.add(j + 1); ans.add(i);
                        res.add(ans);
                        count++;
                    }
                    break;
                }
            }
            if (!f1 && !f2) break;
        }
        return res;
    }

    // count of subarrays with sum equals 0
    public static int getIndicesSubArrWithSum0_2(int[] arr, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        // sum, freq
        map.put(0, 1);
        int sum = 0, count = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (map.containsKey(sum)) {
                count += map.get(sum);
            } 
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    // Nearly sorted Algorithm
    // Need to sort an array in which all elements are at most K units away from their position
    // time: n log k
    public static void sortKSortedArr(int[] arr, int k) {
        // 6, 5, 3, 2, 8, 10, 9
        // 0, 1, 2, 3, 4, 5, 6 (indices)
        // 2, 3, 5, 6, 8, 9, 10 -> k = 3
        // as k <= N so we can't add complete k ele and then add and remove
        // this will give index out of bounds
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            pq.add(arr[i]);
            if (pq.size() > k) System.out.print(pq.remove() + " ");
        }
        
        while (pq.size() > 0) System.out.print(pq.remove() + " ");
        // o (n lg k)
    }

    /**
        Given an array arr[] of n integers, construct a Product Array prod[] (
        of same size) such that prod[i] is equal to the product of all the elements of arr[] except arr[i].
        Solve it without division operator in O(n) time.
    */
    public static void productOfArrExceptItself(int[] arr) {
        long left = 1;
        int n = arr.length;
        long[] right = new long[n];
        long[] ans = new long[n];
        right[n - 1] = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) right[i] = arr[i] * right[i + 1];

        for (int i = 0; i < n; i++) {
            ans[i] = left * (i + 1 < n ? right[i + 1] : 1);
            left *= arr[i];
        }
        for (long val: ans) System.out.print(val + " ");
    }

    // Find number of pairs (x, y) in an array such that x^y > y^x
    // m = x.length, n = y.length
    public static long countPairs(int[] x, int[] y) {
        int one = 0, zero = 0, three = 0, four = 0, two = 0,  m = x.length, n = y.length;
        Arrays.sort(x);
        Arrays.sort(y);
        for (int val: y) {
            if (val == 1) one++;
            else if (val == 2) two++;
            else if (val == 0) zero++;
            else if (val == 3) three++;
            else if (val == 4) four++;
        }   
        // traverse x elements
        long ans = 0;
        for (int i = 0; i < m; i++) {
            if (x[i] == 0) continue;
            else if (x[i] == 1) {
                // 1 ^ 0 > 0 > anything
                ans += zero;
            } else if (x[i] == 2) {
                // 2 ^ 3 !> 3 ^ 2
                int idx = bs_helper(y, n, 2); 
                if (idx != -1) {
                    ans += n - idx;
                }
                ans -= three;
                ans -= four;
                ans += one + zero;
            } else {
                int idx = bs_helper(y, n, x[i]);
                if (idx != -1) ans += n - idx;
                ans += one + zero;
                if (x[i] == 3) ans += two;
            }
        }
        return ans; 
    }

    // binary search to find just greater element from x'
    public static int bs_helper(int[] arr, int len, int ele) {
        int si = 0, ei = len - 1, idx = -1;
        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (ele > arr[mid]) {
                ei = mid - 1;
                idx = mid;
            } else if (ele < arr[mid]) si = mid + 1;
        }
        return idx;
    } 

    // find duplicated in o(n) time and constant space
    // arr[i] = [0, n - 1];
    // sum is used as numbers are from 0 - (n - 1) and case of is not handled by that old methd
    public static ArrayList<Integer> findDups(int[] arr) {
        ArrayList<Integer> ans = new ArrayList<>();
        int n = arr.length;
        for (int i = 0; i < arr.length; i++) {
            int idx = arr[i] % n;
            arr[idx] += n;
        }
        for (int i = 0; i < n; i++) {
            if (arr[i] / n > 1) ans.add(i);
        }
        if (ans.size() == 0) ans.add(-1);
        else Collections.sort(ans);
        return ans;
    }

    // insert an interval in set of given intervals. If insertion leads to overlapping then merge them
    // interval is sorted on start time
    public static int[][] insertInterval(int[][] arr, int[] interval) {
        ArrayList<int[]> ans = new ArrayList<>();
        // add all the non overlapping intervals
        int i = 0;
        while (i < arr.length) {
            if (interval[0] > arr[i][1]) {
                ans.add(arr[i]);
                i++;
            } else break;
        }

        if (ans.size() == 0 || ans.get(ans.size() - 1)[1] < interval[0]) {
            ans.add(interval);
        } else {
            int[] new_interval = new int[2];
            int[] last_interval = ans.get(ans.size() - 1);
            new_interval[0] = Math.min(interval[0], last_interval[0]);
            new_interval[1] = Math.max(interval[1], last_interval[1]);
        }

        while (i < arr.length) {
            int[] curr = ans.get(ans.size() - 1);
            int[] next = arr[i];
            if (next[0] < curr[1]) {
                curr[0] = Math.min(curr[0], next[0]);
                curr[1] = Math.max(curr[1], next[1]);

            } else {
                ans.add(next);
            }
            i++;
        }
        return ans.toArray(new int[ans.size()][]);
    }

    // Sort an array according to count of set bits
    static class pair {
        int count;
        int idx;
        public pair (int count, int idx) {
            this.count = count;
            this.idx = idx;
        }
    }

    public static void sortByCountSetBits(int[] arr) {
        int n = arr.length;
        pair[] ans = new pair[n];
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            int ele = arr[i], j = 0, count = 0;
            while (ele > 0) {
                ele = (ele & (ele - 1));
                count++;
            }
            ans[i] = new pair(count, i);
        }
        Arrays.sort(ans, (t, o) -> {
            if (t.count != o.count) return o.count - t.count;
            else return t.idx - o.idx;
        });
        for (int i = 0; i < n; i++) {
            res[i] = arr[ans[i].idx];
        } 
        for (int val: res) System.out.print(val + " "); 
    }

    public static void sortByCountSetBits_2(Integer[] arr) {
        Arrays.sort(arr, (t, o) -> {
            int c1 = Integer.bitCount(t);
            int c2 = Integer.bitCount(o);
            if (c1 <= c2) return 1;
            else return -1;
        });
        for (int i = 0; i < arr.length; i++) System.out.print(arr[i] + " ");
    }

    // Minimum swaps to make two arrays identical
    // we have to make b same as a
    public static int minSwapsToMakeArrIdentical(int[] a, int[] b) {
        // element, idx
        int n = a.length, count = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) map.put(b[i], i);

        for (int i = 0; i < n; i++) {
            if (a[i] != b[i]) {
                count++;
                int org_val_idx = map.get(a[i]);
                // swap b[i] and org_val
                int temp = b[i];
                b[i] = b[org_val_idx];
                b[org_val_idx] = temp;

                // also change map values
                map.put(b[i], i);
                map.put(b[org_val_idx], org_val_idx);

            }
        }
        for (int i = 0; i < n; i++) System.out.println(a[i] + " , " + b[i]);
        return count;
    }

    // Find the largest multiple of 3 from array of digits | In O(n) time and O(1) space)
    /**
    Given an array of digits (contain elements from 0 to 9). Find the largest number that can be made from some or all digits of array and is divisible by 3.
    The same element may appear multiple times in the array, but each element in the array may only be used once. 
    */
    public static String largestMultiple3(int[] arr) {
        StringBuilder sb = new StringBuilder();
        int sum = 0, n = arr.length;
        
        for (int val: arr) sum += val;
        
        Arrays.sort(arr);
        
        if (sum % 3 == 0) {
            // complete arr ele can be used to make number
            if (arr[n - 1] == 0) {
                sb.append("0");
                
            } else {
                // after sorting last element should not be a 0
                for (int i = n - 1; i >= 0; i--) {
                    sb.append(arr[i]);
                }
            }
            return sb.toString();
            
        } else if (sum % 3 == 1) {
            // we need to find an element which gives rem == 1
            boolean found = false;
            
            for (int i = 0; i < n; i++) {
                if (arr[i] % 3 == 1) {
                    found = true;
                    // mark that value so that it is not included in ans 
                    arr[i] = -1;
                    break;
                }
            }
            
            // if ele with rem = 1 is not found then we need to find 2 ele
            // with rem == 2
            if (!found) {
                int[] store = {-1, -1};
                
                for (int i = 0; i < n; i++) {
                    if (arr[i] % 3 == 2) {
                        if (store[0] == -1) {
                            store[0] = i;
                        } else {
                            store[1] = i;
                            break;
                        }
                    }
                }
                
                // now flag those 2 elements such that they are not included in ans
                if (store[1] != -1) {
                    arr[store[0]] = -1;
                    arr[store[1]] = -1;
                    
                } else {
                    return sb.toString();
                }
            }
            
        } else if (sum % 3 == 2) {
            // we need to find an element which gives rem == 2
            boolean found = false;
            
            for (int i = 0; i < n; i++) {
                if (arr[i] % 3 == 2) {
                    found = true;
                    // mark that value so that it is not included in ans 
                    arr[i] = -1;
                    break;
                }
            }
            
            // if ele with rem = 2 is not found then we need to find 2 ele
            // with rem == 1
            if (!found) {
                int[] store = {-1, -1};
                
                for (int i = 0; i < n; i++) {
                    if (arr[i] % 3 == 1) {
                        if (store[0] == -1) {
                            store[0] = i;
                        } else {
                            store[1] = i;
                            break;
                        }
                    }
                }
                
                // now flag those 2 elements such that they are not included in ans
                if (store[1] != -1) {
                    arr[store[0]] = -1;
                    arr[store[1]] = -1;
                    
                } else {
                    return sb.toString();
                }
            }
        } 
        
        // now we have unwanted elements marked as -1;
        for (int i = n - 1; i >= 0; i--) {
            if (arr[i] != -1) {
                sb.append(arr[i]);
            }
        }
        
        // in case there are many 0s at the start then we need to eliminate them
        
        if (sb.length() > 0 && sb.charAt(0) != -1 && sb.charAt(0) == '0') {
            StringBuilder ans = new StringBuilder();
            ans.append(0);
            return ans.toString();
        }
        
        return sb.toString();
    }   

    // Permute two arrays such that sum of every pair is greater or equal to K
    // find a permutation such that corresponding element of both array sums >= k
    public static boolean findPermutation(int[] a, int[] b, int k) {
        // 1.sort both arrays and then 2. apply two pointer algo
        int n = a.length;
        Arrays.sort(a);
        Arrays.sort(b);
        int i = 0, j = n - 1;
        while (i < n && j >= 0) {
            int sum = a[i] + b[j];
            if (sum >= k) return true;
            else i++;
        }
        return false;
    }

    public static boolean findPermutation2(int[] a, int[] b, int k) {
        Arrays.sort(a);
        Arrays.sort(b);
        int i = 0, j = b.length - 1;
        while (i < a.length && j >= 0) {
            if (a[i] + b[j] < k) return false;
            else {
                i++; j--;
            }
        }
        return true;
    }

    // Find pair with greatest product in array
    // Given an array of n elements, the task is to find the greatest number such that it 
    // is product of two elements of given array. If no such element exists, print -1.
    // and largest element should not be included in pair
    public static int findLargestAsProductOfArrayElements(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int val: arr) {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
        Arrays.sort(arr);
        int n = arr.length;
        for (int i = n - 1; i > 1; i--) {
            for (int j = 0; j < i && (arr[j] * arr[j] <= arr[i]); j++) {
                if (arr[i] % arr[j] == 0) {
                    int result = arr[i] / arr[j];
                    // check if result is in array 
                    // if it is there the arr[i] will be ans
                    if (result != arr[j] && result != arr[i] && map.getOrDefault(result, 0) > 0) {
                        return arr[i];
                    }
                    // to handle cases like arr[i] = 4
                    // arr[j] = 2, result = 2
                    else if (result == arr[j] && map.getOrDefault(result, 0) > 1) {
                        return arr[i];
                    
                    } else if (result == arr[i] && map.getOrDefault(result, 0) > 1) {
                        // when i is on 1 and j is on 13 
                        // and there is one more 13
                        return arr[i];
                    }
                }
            }
        }
        return -1;
    }

    // Minimum number of swaps required to sort an array
    public static int minSwapsToMakeSorted(int[] arr) {
        int n = arr.length;
        // 1. create a new array to compare pos of sorted elements
        int[] a = new int[n];
        // 2. copy all elements from arr to a
        for (int i = 0; i < n; i++) a[i] = arr[i];
        // 3. sort that array
        Arrays.sort(a);
        // 4. create map of sorted array, val vs index
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) map.put(a[i], i);
        // 5. travel on original array and see if there is a mis position of any element 
        // then swap it with it's original position till arr[i] = a[i];
        int count = 0;
        for (int i = 0; i < n; i++) {

            while (arr[i] != a[i]) {
                int actual_idx_of_arr_ele = map.get(arr[i]);
                // 6. swap them 
                int temp = arr[i];
                arr[i] = arr[actual_idx_of_arr_ele];
                arr[actual_idx_of_arr_ele] = temp;
                // 7. increase count when swapping is done
                count++;
            }
        }
        return count;
    }

    // min swaps to make an array sorted (GRAPH VIEW)
    static class pair {
        int ele;
        int idx;
        public pair (int ele, int idx) {
            this.ele = ele;
            this.idx = idx;
        }
    }

    public static int minSwapsGraphView(int[] arr) {
        int n = arr.length, count = 0;
        pair[] a = new pair[n];

        for (int i = 0; i < n; i++) a[i] = new pair(arr[i], i);

        Arrays.sort(a, (t, o) -> {
            return t.ele - o.ele;
        });

        boolean[] vis = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (vis[i] || i == a[i].idx) continue;

            int j = i, clen = 0;
            while (!vis[j]) {
                vis[j] = true;
                clen++;
                j = a[j].idx;
            }
            count += clen - 1;
        }
        return count;
    }

    // count reverse pairs: 
    // i < j and arr[i] <= 2 * arr[j]
    public static int countReversePairs(int[] arr) {
        int n = arr.length;
        int[] ans = new int[n];
        return countReversePairs_rec(arr, 0, n - 1, ans);
    }

    public static int countReversePairs_rec(int[] arr, int si, int ei, int[] ans) {
        if (si == ei) return 0;
        int mid = (si + ei) >> 1; 
        int left = countReversePairs_rec(arr, si, mid, ans);
        int right = countReversePairs_rec(arr, mid + 1, ei, ans);
        int self = countReversePairs_merge(arr, si, mid, ei, ans);
        return (left + self + right);
    }

    public static int countReversePairs_merge(int[] arr, int si, int mid, int ei, int[] ans) {
        int i, j = mid + 1, k = si, count = 0;
        for (i = si; i <= mid; i++) {
            while (j <= ei && arr[i] > 2 * (long) arr[j]) {
                j++;
            }
            // it means that from mid + 1 till j - 1 all elements satisfy the above condition
            count += (j - (mid + 1));
        }
        // re-assign after work
        i = si, j = mid + 1;
        while (i <= mid && j <= ei) {
            if (arr[i] <= arr[j]) {
                ans[k++] = arr[i++];
            } else {
                ans[k++] = arr[j++];
            }
        }
        while (i <= mid) {
            ans[k++] = arr[i++];
        }
        while (j <= ei) {
            ans[k++] = arr[j++];
        }
        int p = si;
        while (p <= ei) {
            arr[p] = ans[p];
            p++;
        }
        return count;
    }

    public static void solve() {
        int[] a = {10, 19, 6, 3, 5};
        int ans = minSwapsToMakeSorted(a);
        System.out.println(ans);
    }

    public static void main(String[] args) {
        solve();
    }
}