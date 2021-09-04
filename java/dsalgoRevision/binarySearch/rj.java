import java.util.*;

class rj {

    // LIS in nlogn 
    public static ArrayList<Integer> lis(int[] arr) {
        ArrayList<Integer> ans = new ArrayList<>();
        ans.add(arr[0]);
        // we will find the ele from arr in the list
        // if we got the biggest ele not in the list then we will add it
        // else the element which will fit the sequnce in between will be placed there
        // as mid finds it's position
        for (int i = 1; i < arr.length; i++) {
            int si = 0, ei = ans.size();
            // such that it covers one ele atleast
            while (si < ei) {
                int mid = (si + ei) >> 1;
                if (ans.get(mid) < arr[i]) si = mid + 1;
                else ei = mid;
            }
            if (ei == ans.size()) {
                // means the ele which we are trying to search is largest and must be added
                ans.add(arr[i]);
            } else {
                // ei will be the point where the smaller number from array fits the sequence
                ans.set(ei, arr[i]);
            }
        }
        return ans;
    }

    // merge sort in which no need to make new array again
    public static int merge(int[] arr, int[] sorted, int si, int mid, int ei) {
        int i = si, len = mid + 1, j = len, k = si, count = 0;

        while (i <= len - 1 && j <= ei) {
            if (arr[i] <= arr[j]) {
                sorted[k] = arr[i++];
            } else {
                sorted[k] = arr[j++];
                // count inv
                count += len - i;   
            }
            k++;
        }

        while (i <= len - 1) sorted[k++] = arr[i++];
        while (j <= ei) sorted[k++] = arr[j++];

        // now insert the ele from sorted arr in the window si, ei which u have sorted from above loop
        while (si <= ei) {
            arr[si] = sorted[si];
            si++;
        }
        return count;
    }

    public static int rec(int[] arr, int[] sorted, int si, int ei) {
        if (si >= ei) return 0;

        int mid = (si + ei) >> 1;
        int left = rec(arr, sorted, si, mid);
        int right = rec(arr, sorted, mid + 1, ei);
        int ans = merge(arr, sorted, si, mid, ei);
        return ans + left + right;
    }

    public static int countInversions(int[] arr) {
        if (arr.length == 0) return 0;
        int[] sorted = new int[arr.length];
        int ans = rec(arr, sorted, 0, arr.length - 1);
        for (int val: sorted) System.out.print(val + " ");
        return ans;
    }

    public static void solve() {
        int[] arr = {5, 4, 3, 2, 1};
        int ans = countInversions(arr);
        System.out.println(ans);
    }

    public static void main(String args[]) {
        solve();
    }
}