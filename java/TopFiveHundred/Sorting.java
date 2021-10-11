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

    public static void solve() {
        int[] arr = {1, 2, 8, 10, 10, 12, 19};
        int x = 12;
        int[] ans = ceilAndFloor(arr, x);
        System.out.println("floor " + ans[0] + ", ceil " + ans[1]);
    }

    public static void main(String[] args) {
        solve();
    }
}