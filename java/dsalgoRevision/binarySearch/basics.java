import java.util.*;
class basics {
    // binary search to find a given ele
    public static int bs(int[] arr, int ele) {
        int si = 0, ei = arr.length - 1;
        // why till si <= ei because if the ele will be on extreme left or right we have to run the loop 
        // and find the mid again, and that mid will be our ans
        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (ele < arr[mid]) ei = mid - 1;
            else if (ele > arr[mid]) si = mid + 1;
            else return mid;
        }
        return -1;
    }

    // first occurance
    public static int firstOcc(int[] arr, int ele) {
        int idx = -1, si = 0, ei = arr.length - 1;
        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (ele < arr[mid]) ei = mid - 1;
            else if (ele > arr[mid]) si = mid + 1;
            else {
                idx = mid;
                // we have found the ele now for first occ move to left as much as u can
                ei = mid - 1;   
            }
        }
        return idx;
    }

    public static int lastOcc(int[] arr, int ele) {
        int idx = -1, si = 0, ei = arr.length - 1;
        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (ele < arr[mid]) ei = mid - 1;
            else if (ele > arr[mid]) si = mid + 1;
            else {
                idx = mid;
                // for last occ move to right as much as u can
                si = mid + 1;
            }
        }
        return idx;
    }

    // nearest element idx
    public static void nearestEle(int[] arr, int ele) {
        int idx = -1, si = 0, ei = arr.length - 1;
        
        if (ele <= arr[0]) idx = 0;
        else if (ele >= arr[arr.length - 1]) idx = arr.length - 1;
        else {
            while (si <= ei) {
                int mid = (si + ei) >> 1;
                if (arr[mid] == ele) {
                    idx = mid;
                    break;
                }
                else if (ele < arr[mid]) ei = mid - 1;
                else si = mid + 1;
            }   
        }
        // 
        if (idx != -1) System.out.println(idx);
        else {
            idx = (ele - arr[ei] < arr[si] - ele) ? ei : si;
            System.out.println(arr[idx]);
        }
    }

    // ceil and floor in sorted array 
    // ceil, floor
    public static int[] findCeilAndFloor(int[] arr, int ele) {
        int si = 0, ei = arr.length - 1;
        int[] ans = {-1, -1};

        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (arr[mid] == ele) {
                // heater is in the house
                ans[0] = ele;
                ans[1] = ele;
                break;
            } else if (ele > arr[mid]) {
                ans[1] = arr[mid];
                // u have a smaller val than ele
                si = mid + 1;
            } else {
                // u have the greater one
                ans[0] = arr[mid];
                ei = mid - 1;
            }
        }
        // time: house.length log (heater.length)
        return ans;
    }

    // bubble sort
    public static void bubbleSort(int[] roll) {
        for (int i = 0; i < roll.length - 1; i++) {
            for (int j = 0; j < roll.length - i - 1; j++) {
                if (roll[j] > roll[j + 1]) {
                    int temp = roll[i];
                    roll[i] = roll[i + 1];
                    roll[i + 1] = temp;
                }
            }
        }
    }
    
    // find k closest
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        // List<Integer> res = new ArrayList<>();
        int start = 0;
        int end = arr.length - k - 1;
        while (start <= end) {
            // int mid = (end - start) / 2 + start;
            int mid = (end + start) >> 1;
            if ((x - arr[mid]) > (arr[mid + k] -x)) {
                start = mid + 1;
            } else {
                end = mid - 1;                
            }
        }
        for (int i = start; i < start + k; i++) {
            ans.add(arr[i]);
        }
        return ans;
    }

    public static void solve() {
        int[] arr = {-15, -10, -5, 20, 30, 40, 55, 70};
        // System.out.println(nearestEle(arr, 4));
        nearestEle(arr, 1);
    }

    public static void main(String args[]) {
        solve();
    }
}