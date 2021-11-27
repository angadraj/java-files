import java.util.*;
class Greedy {
    
    // activity selection problem
    static class activitySel_pair {
        int st;
        int et;
        public activitySel_pair(int st, int et) {
            this.st = st;
            this.et = et;
        }
    }

    public static void activitySel(int[] s, int[] f) {
        // sort on the basis of finishing time
        int n = s.length;
        activitySel_pair[] arr = new activitySel_pair[n];
        for (int i = 0; i < n; i++) {
            arr[i] = new activitySel_pair(s[i], f[i]);
        }
        Arrays.sort(arr, (t, o) -> {
            return t.et - o.et;
        });
        activitySel_pair prev = arr[0];
        System.out.println(prev.st + ", " + prev.et);
        for (int i = 1; i < n; i++) {
            activitySel_pair cp = arr[i];
            // submission done for strictly big
            if (prev.et < cp.st) {
                System.out.println(cp.st + ", " + cp.et);
                prev = cp;
            }
        }
    }

    public static void activitySel2(int[] s, int[] f) {
        int i = 0, j = 0, n = s.length, count = 0;
        System.out.println(s[i] + ", " + f[i]);
        count++;
        for (i = 1; i < n; i++) {
            if (s[i] >= f[j]) {
                System.out.println(s[i] + ", " + f[i]);
                j = i;
                count++;
            }
        }
    }

    // job sequencing problem
    static class Job {
        int id, profit, deadline;
        public Job(int id, int profit, int deadline) {
            this.id = id;
            this.profit = profit;
            this.deadline = deadline;
        }
    }

    public static void jobSequencing(Job[] arr) {
        int n = arr.length, totalProfit = 0;
        boolean[] slots = new boolean[n];
        Arrays.sort(arr, (t, o) -> {
            return o.profit - t.profit;
        });
        for (int i = 0; i < n; i++) {
            int curr_deadline = arr[i].deadline;
            for (int j = Math.min(n - 1, curr_deadline - 1); j >= 0; j--) {
                if (!slots[j]) {
                    totalProfit += arr[i].profit;
                    slots[j] = true;
                    System.out.println(arr[i].deadline + ", " + arr[i].profit);
                    break;
                }
            }
        }
    }

    // egyptian fraction
    // dr > nr
    public static void egyptionFraction(int nr, int dr) {
        if (nr == 0 || dr == 0) {
            return;
        }

        if (nr % dr == 0) {
            System.out.print(nr / dr);
            return;
        }

        if (dr % nr == 0) {
            System.out.print("1/" + (dr / nr));
            return;
        }

        if (nr > dr) {
            System.out.print(nr % dr + " + ");
            egyptionFraction(nr % dr, dr);
        }
        
        int n = dr / nr + 1;
        System.out.print("1/" + n + " + ");
        egyptionFraction(nr * n - dr, n * dr);
    }

    public static void solve() {
        egyptionFraction(6, 14);
    }
    
    public static void main(String args[]) {
        solve();
    }
}