import java.util.*;
class RangeQueries {
    
    // 1. prefix sum
    public static void prefixSumAndCount(int[] arr) {
        int n = arr.length;
        int[] psa = new int[n];
        int[] pca = new int[n];
        for (int i = 0; i < n; i++) {
            if (i == 0) psa[i] = arr[i];
            else psa[i] = arr[i] + psa[i - 1];
        }

        // this can be calculated for only one specific element of the arr
        // I choose ele is 0
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                pca[i] = (arr[i] == 0) ? 1 : 0;
            } else {
                pca[i] = (arr[i] == 0) ? pca[i - 1] + 1 : pca[i - 1];
            }
        }
    }

    // Range addition, worst time: o(n ^ 2), optimized = o(n + k)
    public static void rangeAddition(int[] arr, int[][] data) {
        int n = arr.length;
        for (int[] d: data) {
            int si = d[0], ei = d[1], val = d[2];
            arr[si] += val;
            if (ei + 1 < n) arr[ei + 1] -= val; 
        }

        for (int i = 1; i < n; i++) arr[i] = arr[i] + arr[i - 1];
        for (int val: arr) System.out.print(val + " ");
    }

    // Moksh and his girlfriend
    // basically you have to find the max occurance of M when you apply q - 1 queries

    public static void maxOccMIgnoringQuery(int[] arr, int[][] queries, int m) {
        // array will be of n + 2 length, as n queries index is 1 based
        for (int[] q: queries) {
            int si = q[0];
            int ei = q[1];
            int val = 1;
            arr[si] += val;
            arr[ei + 1] -= val;
        }
        for (int i = 1; i < arr.length; i++) {
            arr[i] += arr[i - 1];
        }
        // count of m arr
        int[] countm = new int[arr.length];
        // count of m + 1 arr
        int[] countm1 = new int[arr.length];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == m) countm[i] += countm[i - 1] + 1;
            else countm[i] = countm[i - 1];

            if (arr[i] == (m + 1)) countm1[i] += countm1[i - 1] + 1;
            else countm1[i] = countm1[i - 1];
        }

        // now start ignoring each query
        int max = 0, count = countm[countm.length - 1];
        for (int[] q: queries) {
            int si = q[0], ei = q[1];

            int loss = countm[ei] - countm[si - 1];
            int gain = countm1[ei] - countm1[si - 1];

            max = Math.max(max, count - loss + gain);
        }
        System.out.println(max);
    }

    // square root decomposition to find min 
    public static void MOsAlgo_min(int[] arr, int[][] data) {
        int len = (int)Math.ceil(Math.sqrt(arr.length));
        // preprocessing
        int[] sqrt = new int[len];
        Arrays.fill(sqrt, (int)(1e8));
        // fill each segment with the min value 
        for (int i = 0; i < arr.length; i++) {
            sqrt[i / len] = Math.min(sqrt[i / len], arr[i]);
        }

        // traverse on each query
        for (int i = 0; i < data.length; i++) {
            int[] q = data[i];
            int l = q[0], r = q[1];

            int ans = (int)(1e8);
            while (l <= r) {
                if (l % len == 0 && l + len - 1 <= r) {
                    // it means this segment is completely required
                    ans = Math.min(ans, sqrt[l / len]);
                    l += len;
                } else {
                    // this segment is partially required
                    ans = Math.min(ans, arr[l]);
                    l++;
                }
            }
            System.out.print(ans + " ");
        }
    }

    // sqrt decomposition for point update(sum)
    /**
        1. make an array of len = ceil(sqrt(arr.length))
        2. map the elements of arr with the segments as i / len
        3. now travel and find the desired output
     */
    public static void sumAndPointUpdate(int[] arr, String[][] data) {
        int len = (int)Math.ceil(Math.sqrt(arr.length));

        int[] sqrt = new int[len];
        for (int i = 0; i < arr.length; i++) {
            sqrt[i / len] += arr[i];
        }

        for (String[] q: data) {
            if (q[0].equals("f")) {
                int l = Integer.parseInt(q[1]);
                int r = Integer.parseInt(q[2]);
                int val = 0;
                while (l <= r) {
                    if (l % len == 0 && l + len - 1 <= r) {
                        val += sqrt[l];
                        l += len;
                    } else {
                        val += arr[l];
                        l++;
                    }
                }
                System.out.print(val + " ");

            } else if (q[0].equals("u")) {
                int l = Integer.parseInt(q[1]);
                int d = Integer.parseInt(q[2]);
                arr[l] += d;
                sqrt[l / len] += d;
            }
        }
    }
    
    public static void solve() {
        int[][] data = {
            {0, 5, 2},
            {4, 7, -1},
            {2, 8, 3},
            {0, 9, 1}
        };
        rangeAddition(new int[10], data);
    }

    public static void main(String[] args) {
        solve();
    }
}