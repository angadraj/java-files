import java.util.*;

class adv {
    
    // marks of pcm
    static class marks {
        int p, c, m;
        public marks(int p, int c, int m) {
            this.p = p;
            this.c = c;
            this.m = m;
        }
    }

    public static void sortMarks(int[] phy, int[] chem, int[] maths) {
        marks[] m = new marks[phy.length];
        for (int i = 0; i < phy.length; i++) {
            m[i] = new marks(phy[i], chem[i], maths[i]);
        }
        Arrays.sort(m, (t, o) -> {
            if (t.p != o.p) return t.p - o.p;
            else if (t.c != o.c) return o.c - t.c;
            else return t.m - o.m;
        });

        for (int i = 0; i < m.length; i++) {
            marks cm = m[i];
            phy[i] = cm.p;
            chem[i] = cm.c;
            maths[i] = cm.m;
        }
    }

    // union of two sorted arr
    // in o(n + m) without hashset
    public static ArrayList<Integer> union(int[] a, int[] b) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        int i = 0, j = 0;
        // we are accepting small vals because we can get big vals later on in either arr
        while (i < a.length && j < b.length) {
            int ele = -1;
            if (a[i] == b[j]) {
                ele = a[i];
                i++; 
                j++;
            } else if (a[i] < b[j]) {
                ele = a[i];
                i++;
            } else {
                ele = b[j];
                j++;
            }
            if (list.size() == 0 || list.get(list.size() - 1) != ele) list.add(ele);    
        }

        while (i < a.length) {
            if (list.size() == 0 || list.get(list.size() - 1) != a[i]) list.add(a[i]);
            i++;
        }

        while (j < b.length) {
            if (list.size() == 0 || list.get(list.size() - 1) != b[j]) list.add(b[j]);
            j++;
        }   
        return list;    
    }
    
    // search in 2D matrix
    // rows are sorted and 1s ele of each row is bigger than last ele of prev row
    // o(lg n * m)
    public static int search(int[][] arr, int ele) {
        int si = 0, ei = (arr.length * arr[0].length) - 1, idx = -1, m = arr[0].length;
        while (si <= ei) {
            int mid = (si + ei) >> 1;
            int mid_ele = arr[mid / m][mid % m];
            if (ele == mid_ele) {
                idx = mid;
                break;
            } else if (ele < mid_ele) ei = mid - 1;
            else si = mid + 1;
        }
        return idx;
    }

    // search in sorted 2D matrix_2
    // rows are sorted from left to right 
    // cols are sorted from top to bottom
    // in this on the basis of one element we are discarding a complete row or col so
    // time: o(m + n)
    public static int search_2(int[][] arr, int tar) {
        int r = 0, c = arr[0].length - 1, idx = -1, m = arr[0].length;
        while (r < arr.length && c >= 0) {
            if (arr[r][c] == tar) {
                idx = r * m + c;
                break;
            } else if (arr[r][c] > tar) c--;
            else r++;
        }
        return idx;
    }

    // pivot or equillibrium index
    public static int pivotIndex(int[] arr) {
        int sum = 0;
        for (int val: arr) sum += val;

        int lsum = 0, rsum = sum;
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                lsum += arr[i - 1];
            }
            rsum -= arr[i];
            if (rsum == lsum) return i;
            // if condition was checked before then u can simply add lsum += arr[i]
        }
        return -1;
    }

    // find k closest elements
    static class pair {
        int g;
        int e;
        public pair(int e, int g) {
            this.e = e;
            this.g = g;
        }
        public void log() {
            System.out.println(this.e + " " + this.g);
        }
    }

    public static ArrayList<Integer> kClosest(int[] arr, int k, int ele) {
        PriorityQueue<pair> pq = new PriorityQueue<>((t, o) -> {
            if (t.g != o.g) return o.g - t.g;
            else return o.e - t.e;
        });
        for (int i = 0; i < arr.length; i++) {
            pq.add(new pair(arr[i], Math.abs(ele - arr[i])));
            if (pq.size() > k) {
                pq.remove();
            }
        }
        ArrayList<Integer> ans = new ArrayList<>();
        while (pq.size() > 0) {
            ans.add(pq.remove().e);
        }
        Collections.sort(ans);
        // pq = nlogk -> n for loop and k for add and remove;
        // time : nlogk + sort(klogk); -> o(nlogk)
        return ans;
    }

    // k closest optimized
    // find closest ele using bs and then loop on ele to get the other ele
    public static ArrayList<Integer> kClosest_opti(int[] arr, int k, int x) {
        int si = 0, ei = arr.length - 1, mid = -1;
        while (si <= ei) {
            mid = (si + ei) >> 1;
            if (x < arr[mid]) ei = mid - 1;
            else if (x > arr[mid]) si = mid + 1;
            else {
                // we got the ele else mid will placed at right pos
                break;
            }
        }

        int left = mid - 1, right = mid;
        // now select k ele
        ArrayList<Integer> ans = new ArrayList<>();
        while (left >= 0 && right < arr.length && k > 0) {
            if (Math.abs(x - arr[left]) <= Math.abs(arr[right] - x)) {
                ans.add(arr[left]);
                left--;
            } else {
                ans.add(arr[right]);
                right++;
            }
            k--;
        }

        while (k > 0 && left > 0) {
            // right is at len - 1;
            if (Math.abs(arr[left] - x) < Math.abs(arr[left - 1] - x)) {
                ans.add(arr[left]);
                k--;
            }
            left--;
        }

        while (k > 0 && right < arr.length - 1) {
            // left < 0;
            if (Math.abs(arr[right] - x) < Math.abs(arr[right + 1] - x)) {
                ans.add(arr[right]);
                k--;
            }
            right++;
        }
        Collections.sort(ans);
        // lgn + k + klgk -> bs + loop + sort
        return ans;
    }

    // find pair with given diff
    public static void pairWithDiff(int[] arr, int k) {
        Arrays.sort(arr);
        // if we do si = 0, ei = len - 1;
        // arr[j] - arr[i] => if arr[j] decs then diff decs
        // if arr[i] inc then diff decs
        // ie i++ or j-- diff will decs
        //
        // start j from i + 1 so that we can dec and inc diff 
        // decs diff -> i++, inc diff -> j++
        int si = 0, ei = 1;
        while (ei < arr.length) {
            int diff = arr[ei] - arr[si];
            if (diff > k) si++;
            else if (diff < k) ei++;
            else {
                System.out.println(arr[si] + " " + arr[ei]);
                return;
            }
        }
        System.out.println(-1);
    }

    // roof top
    // find max steps such that whenever u take a step u go high in altitude
    // bascially at each step u find greater val the present one 
    public static int maxSteps(int[] arr) {
        int ans = 0, si = 0;
        while (si < arr.length) {
            int count = 0;
            while (si < arr.length - 1 && arr[si + 1] > arr[si]) {
                count++;
                si++;
            }
            ans = Math.max(ans, count);
        }
        return ans;
    }

    // Maximize the sum of arr[i]*i 
    public static int maxSum(int[] arr) {
        // simply sort it to get the above combi
        int sum = 0;
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) sum += arr[i] * i;
        return sum;
    }

    // count inversions
    // using merge sort we get the chance to break each ele into single part and then we can count the inv
    // if left[i] > right[i], then inv += len - left(i);
    // time : nlgn
    // exact merge sort
    static int count = 0;
    public static int[] countInv(int[] arr, int si, int ei) {
        if (si == ei) return new int[]{arr[si]};
        int mid = (si + ei) >> 1;
        int[] left = countInv(arr, si, mid);
        int[] right = countInv(arr, mid + 1, ei);
        int[] ans = mergeAndCount(left, right);
        return ans;
    }

    public static int[] mergeAndCount(int[] left, int[] right) {
        int[] ans = new int[left.length + right.length];
        int i = 0, j = 0, idx = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                ans[idx] = left[i];
                i++;
            } else {
                ans[idx] = right[j];
                count += (left.length - i); // **
                j++;
            }
            idx++;
        }
        while (i < left.length) ans[idx++] = left[i++];
        while (j < right.length) ans[idx++] = right[j++];
        return ans;
    }

    // find 1st and last position in a sorted arr
    // time -> lg(n)
    public static int[] firstAndLast(int[] arr, int x) {
        int si = 0, ei = arr.length - 1, idx = -1;
        int[] ans = new int[2];
        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (x < arr[mid]) ei = mid - 1;
            else if (x > arr[mid]) si = mid + 1;
            else {
                idx = mid;
                ei = mid - 1; // **
            }
        }

        ans[0] = idx;
        si = 0;
        ei = arr.length - 1;
        idx = -1;

        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (x < arr[mid]) ei = mid - 1;
            else if (x > arr[mid]) si = mid + 1;
            else {
                idx = mid;
                si = mid + 1; // **
            }
        }
        ans[1] = idx;
        return ans;
    }

    // max sum in config
    // find max i * a[i] where u can rotate(clock-wise or counter clock-wise) the array any number of times.
    /**
        a, b, c, d
        s0 = 0a + 1b + 2c + 3d
        s1 = 1a + 2b + 3c + 0d(4d)
        s2 = 2a + 3b + 4c + 1d  ... so on
        it means if we take s0 = a0 + 1b + 2c + 3d
        then later config can be made by s0 + sum 
        but not all elements are added the last ele will become 0e 1e 2e 3e 4e and then 5e
        for len = 5
        there fore when an ele's coeff becomes equal to len then we have to make it 0
        it becomes -> s(i + 1) = si + sum - n * arr[n - i - 1];

        s0 = a0 + 1b + 2c + 3d + 4e
        s  = a  + b  + c  + d  + e 
        == 1a  + 2b + 3c + 4d + 5e  but 5e should be 0e so we will subtract n * a[n - i - 1]
        5 * a[5 - 0 - 1] = 5e
        this is how we can make sum when rotations are allowed

        in rotation each element changes it's coeff by one in every configuration
        time : o(n)
     */
    public static int maxSum_2(int[] arr) {
        int sum = 0, s0 = 0, ans = -(int)(1e8), n = arr.length;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            s0 += arr[i] * i;
        }

        ans = Math.max(s0, ans);
        for (int i = 0; i < arr.length - 1; i++) {
            int si = s0 + sum - (n * arr[n - i - 1]);
            ans = Math.max(si, ans);
            // s1 from s0, s2 from s1 and so on
            s0 = si;
        }
        return ans;
    }

    // Search in Rotated Sorted Array
    public static int find(int[] arr, int x) {
        int si = 0, ei = arr.length - 1;
        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (arr[mid] == x) return mid;
            else if (arr[si] <= arr[mid]) {
                if (x >= arr[si] && x < arr[mid]) {
                    ei = mid - 1;
                    // x < arr[mid] as we will take the ptr to mid - 1;
                } else si = mid + 1;
            } else if (arr[mid] <= arr[ei]) {
                if (x > arr[mid] && x <= arr[ei]) {
                    si = mid + 1;
                    // x > arr[mid] as we will take the ptr to mid + 1;
                } else ei = mid - 1;
            }
        }
        return -1;
    }

    // ******************** IMP SEARCH IN SORTED ROTATED ARR - 2 *******************

    // search in rotated sorted arr: this one also includes duplicates
    // [1,0,1,1,1]
    // find - 0
    // in this left = mid = right -> then we don't know where to go and this is what will inc the time 
    // from lgn to n sometimes
    public static int searchRotatedSortedArr_2(int[] arr, int x) {
        int si = 0, ei = arr.length - 1, idx = -1;
        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (arr[mid] == x) {
                idx = mid;
                break;
            }  else if (arr[si] == arr[mid] && arr[mid] == arr[ei]) {
                // tricky case that will lead to o(n) sometimes
                // we don't which part to search
                si++; ei--;
            } else if (arr[si] <= arr[mid]) {
                if (arr[si] <= x && x < arr[mid]) ei = mid - 1;
                else si = mid + 1; 
            } else if (arr[mid] <= arr[ei]) {
                if (arr[mid] <= x && x < arr[si]) si = mid + 1;
                else ei = mid - 1;
            }
        }
        return idx;
    }

    // ******************************************************************** //

    // Find Minimum in Rotated Sorted Array
    public static int minInRotArr(int[] arr) {
        int si = 0, ei = arr.length - 1;
        if (arr[si] <= arr[ei]) return arr[0];
        // above condition was if arr was not sorted
        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (mid - 1 >= 0 && arr[mid] < arr[mid - 1]) return arr[mid]; // ** 1
            else if (mid + 1 < arr.length && arr[mid] > arr[mid + 1]) return arr[mid + 1]; // ** 2
            else if (arr[si] <= arr[mid]) si = mid + 1;
            else if (arr[mid] <= arr[ei]) ei = mid - 1;
        }
        // the above switch in order is due to if array is not rot then there will atleast one ele at right
        // if ** 1 is at right pos then there can be a case where it may give a run time error
        // for example if mid was on 0th indx then mid - 1 will give exception
        // or u can apply check for it too
        return -1;
    }
    
    // find rotation count
    // this means we have to find the size of part 1 in k sorted arr
    public static int rotCount(int[] arr) {
        int si = 0, ei = arr.length - 1, idx = -1;
        if (arr[si] <= arr[ei]) return 0;

        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (mid - 1 >= 0 && arr[mid] < arr[mid - 1]) {
                idx = mid;
                break;
            } else if (mid + 1 < arr.length && arr[mid] > arr[mid + 1]) {
                idx = mid + 1;
                break;
            } else if (arr[si] <= arr[mid]) si = mid + 1;
            else if (arr[mid] <= arr[ei]) ei = mid - 1;
        }
        return idx;
    }
    //  /\   
    // /
    // mid mid + 1  (visualisation for above code)

    // median of 2 sorted arrs
    public static double median(int[] a, int[] b) {
        int i = 0, j = 0, idx = 0;
        int[] ans = new int[a.length + b.length];
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) ans[idx] = a[i++];
            else ans[idx] = b[j++];
            idx++;
        }

        while (i < a.length) ans[idx++] = a[i++];
        while (j < b.length) ans[idx++] = b[j++];

        double median = 0.0;
        int mid = (ans.length) >> 1;

        if ((ans.length & 1) == 0) {
            median = (ans[mid] + ((mid - 1 >= 0) ? ans[mid - 1] : 0)) / 2.0;
        } else {
            median = ans[mid];  
        }
        return median;
    }

    // time : o(n + m), space: o(n + m)

    // median of two sorted arrays optimized time: lg(n + m) , space o(1)
    public static double median_2(int[] a, int[] b) {
        if (a.length > b.length) {
            int[] temp = a;
            a = b;
            b = temp;
        }

        int si = 0, ei = a.length, total = a.length + b.length;
        // ei = a.length so that we can keep an extra ele
        while (si <= ei) {
            int a_left = (si + ei) >> 1;
            int b_left = (((total & 1) == 1) ? ((total + 1) >> 1) : (total >> 1)) - a_left;
            // to find mid in sec arr -> total on left -> total / 2 out which mid elements are found
            // remaining will total / 2 - mid;
            // in this we are dealing with mid - 1 and mid for both arrays
            // we have added 1 so that if we have odd elements then in 2 arr we have 1 extra at left

            int alm = (a_left == 0) ? -(int)(1e8) : a[a_left - 1];
            int al = (a_left == a.length) ? (int)(1e8) : a[a_left];
            int bl = (b_left == b.length) ? (int)(1e8) : b[b_left];
            int blm = (b_left == 0) ? -(int)(1e8) :b[b_left - 1];

            // if ei is not taken at length then at last you have to manage a case when u dont find any
            // median for ex your loop breaks at si > ei in first array
            // then median should be b[0];
            
            // valid segr
            if (alm <= bl && blm <= al) {  
                double median = 0.0;
                if (total % 2 == 0) {
                    int lmax = Math.max(alm, blm);
                    int rmin = Math.min(al, bl);
                    median = (lmax + rmin) / 2.0;
                } else {
                    int lmax = Math.max(alm, blm);
                    median = lmax;
                }
                return median;
            } else if (alm > bl) {
                // there are more ele in b array 
                ei = a_left - 1;

            } else if (blm > al) {
                // there are more element to be picked in left part from 'a' array
                si = a_left + 1;
            }
        }
        return -1;
    }

    // median
    public double helper(int[] a, int[] b) {
        int total = a.length + b.length;
        boolean isTotalEven = ((total & 1) == 0) ? true : false; 
        int rem = ((total & 1) == 1) ? ((total + 1) >> 1) : (total >> 1);
        
        if (a.length > b.length) {
            int[] temp = a;
            a = b;
            b = temp;
        }
        
        int si = 0, ei = a.length;
        
        while (si <= ei) {
            
            // a -> x, y where x = a[mid - 1], y will be a[mid]
            // b -> p, q where p = b[mid - 1], q will be b[mid]
            
            int m1 = (si + ei) >> 1;
            int m2 = rem - m1;
            
            int x = (m1 == 0) ? -(int)(1e8) : a[m1 - 1];
            int y = (m1 == a.length) ? (int)(1e8) : a[m1];
            
            int p = (m2 == 0) ? -(int)(1e8) : b[m2 - 1];
            int q = (m2 == b.length) ? (int)(1e8) : b[m2];
            
            if (x <= q && p <= y) {
                double median = 0.0;
                if (isTotalEven) {
                    median = (Math.max(x, p) + Math.min(y, q)) / 2.0;
                } else {
                    median = (Math.max(x, p));
                }
                return median;
            
            }  else if (p > y) {
                // make m2 smaller ie m1 bigger such that rem - m1 is smaller
                si = m1 + 1;
            } else {
                // x > q
                // make m2 bigger ie m1 smaller such that rem - m1 is bigger
                ei = m1 - 1;
            }
        }
        
        return -1;
        
    }

    // set in which bs is applied on value not on idx
    // in such probs when u want to inc or desc by 1, then u can use binary search

    // koko eating bananas
    // basically we were finding speed from max to min one by one
    // for this we used binary search to find correct speed
    public static int koko(int[] arr, int h) {
        int max = -(int)(1e8);
        for (int val: arr) max = Math.max(val, max);
        if (h == arr.length) return max;
        // o(n)
        int si = 0, ei = max, ans = -1;
        while (si <= ei) {
            // int mid = (si + ei) >> 1;
            // if bs is applied on index then mid = (si + ei) >> 1;
            // but if bs is applied on vals then this mid can overflow
            int mid = si + ((ei - si) >> 1);
            boolean k = isPossibleToEat(arr, h, mid);
            if (k) {
                ans = mid;
                ei = mid - 1;
            } else si = mid + 1;
        }
        return ans;
    }

    public static boolean isPossibleToEat(int[] arr, int h, int mid) {
        int time = 0;
        for (int val: arr) {
            time += (int)(Math.ceil((val * 1.0) / (mid * 1.0)));
            // to find ceil
        }
        return time <= h;
    }

    // find a smallest divisor for given threshold
    public static int minDivisor(int[] arr, int k) {
        int max = -(int)(1e8);
        for (int val: arr) max = Math.max(val, max);
        int si = 1, ei = max, ans = -1;
        while (si <= ei) {
            int mid = si + ((ei - si) >> 1);
            boolean isDiv = isPossibleToDivide(arr, k, mid);
            if (isDiv) {
                ans = mid;
                ei = mid - 1;
            }
            else si = mid + 1;
        }
        return ans;
    }

    public static boolean isPossibleToDivide(int[] arr, int k, int mid) {
        int sum = 0;
        for (int val: arr) {
            sum += (int)Math.ceil((val * 1.0) / (mid * 1.0));
        }
        return sum <= k;
    }

    // chocolate distribution: divide choco among students such that each student has 1 packet 
    // and diff of max - min is minimum
    // based on sliding window 
    // we need to sort so that min and max come closer to each other
    // time : o(nlgn)
    public static int choco(int[] arr, int m) {
        int ans = (int)(1e8);
        Arrays.sort(arr);
        for (int i = 0; i < arr.length - m; i++) {
            int min = arr[i];
            int max = arr[i + m - 1];
            // for every window u will get min on first indx and max on i + m - 1 idx 
            ans = Math.min(ans, max - min);
        }
        return ans;
    }

    // ************ MIN OF THE MAXES SERIES (IMPORTANT) **************

    // find Min Stress Such That We Can Divide Pages
    // allocate min number of pages
    // you have to divide the books among m students such that
    // atleast one book to to each student
    // allocation should be continous (for any student books allocated must be continuous)
    // you can not divide book into page for a student
    /** 
        In each permutation, one of the M students will be allocated the maximum number of pages. Out of all these permutations, the task is to find that particular permutation in which the maximum number of pages allocated to a student is minimum of those in all the other permutations and print this minimum value. 
        we have to find min of the maxes

        si = max of arr because there will be one student who will get that book
        ei = sum of arr, say if one student gets all books (we are finding ans in range)
    */
    // ques is we have to find the max pressure of books on a student and then minimize that pressure
    // we have to find the all max student stress from all division and then the min of them is ans
    public static int minAllocation(int[] arr, int m) {
        int max = -(int)(1e8), sum = 0, ans = 0;
        for (int val: arr) {
            max = Math.max(max, val);
            sum += val;
        }

        int si = max, ei = sum;
        while (si <= ei) {
            int mid = si + ((ei - si) >> 1);
            boolean k = ifStudentCanHandle(arr, m, mid);
            if (k) {
                ans = mid;
                ei = mid - 1;
            } else si = mid + 1;
        }
        // time: n + nlg(sum);
        return ans;
    }

    public static boolean ifStudentCanHandle(int[] arr, int m, int mid) {
        int sum = 0, count = 1;
        for (int val: arr) {
            sum += val;
            if (sum > mid) {
                sum = val;
                count++;
            }
        }
        return count <= m;
    }

    // split array largest sum, divide the array into m subarrays
    // Write an algorithm to minimize the largest sum among these m subarrays.
    //* arr: 7 2 5  10 8: s1 = (14, 18), s2 = (9, 23)
    // where s1 and s2 are 2 subarrays and out of these max sum from these s are (18, 23)
    // and we have to find min of these so ans will be -> 18
    // this is same as min page allocation
    // using bs we will decide subarray sum in the range to see if m subarrays can make that sum 

    // basically we are trying to divide the given sum in m subarrays and that sum is calculated by bs
    public static int spitBigSum(int[] arr, int m) {
        // in this lo -> max of arr as there will 
        int max = -(int)(1e8), sum = 0, ans = 0;
        for (int val: arr) {
            max = Math.max(max, val);
            sum += val;
        }

        if (arr.length == m) return max;

        int si = max, ei = sum;
        while (si <= ei) {
            int mid = si + ((ei - si) >> 1);
            boolean k = ifSumCanBeDivided(arr, m, mid);
            if (k) {
                ans = mid;
                ei = mid - 1;
            } else si = mid + 1;
        }
        // time: n + nlg(sum);
        return ans;
    }

    public static boolean ifSumCanBeDivided(int[] arr, int m, int mid) {
        int sum = 0, count = 1;
        // atleast one subarray is needed
        for (int val: arr) {
            sum += val;
            if (sum > mid) {
                sum = val;
                count++;
            }
        }
        return count <= m;
    }

    // capacity to ship pacakges within D days
    // min of the maxes
    // so we have to ship all packets within D days
    // si = max because belt have to bear that load atleast 
    // ei = sum because max load can be when we put all the packets together
    public static int findLoad(int[] arr, int m) {
        int max = -(int)(1e8), sum = 0, ans = 0;
        for (int val: arr) {
            max = Math.max(max, val);
            sum += val;
        }

        if (arr.length == m) return max;

        int si = max, ei = sum;
        while (si <= ei) {
            int mid = si + ((ei - si) >> 1);
            boolean k = ifLoadCanBeDiv(arr, m, mid);
            if (k) {
                ans = mid;
                ei = mid - 1;
            } else si = mid + 1;
        }
        // time: n + nlg(sum);
        return ans;
    }

    public static boolean ifLoadCanBeDiv(int[] arr, int m, int mid) {
        int sum = 0, days = 1;
        for (int val: arr) {
            sum += val;
            if (sum > mid) {
                days++;
                sum = val;
            }
        }
        return days <= m;
    }

    // ************** COUNT SET ****************

    // count the triplets
    // such that a + b == c
    // strategy : sort -> freeze one num and then use 2 ptr approach such that a + b == freezed num
    public static int countTriplets(int[] arr) {
        if (arr.length == 0) return 0;
        // to apply 2 ptr we need to sort it
        Arrays.sort(arr);
        int count = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            int l = 0, r = i - 1;
            while (l < r) {
                int sum = arr[l] + arr[r];
                if (sum == arr[i]) {
                    count++;
                    l++; r--;
                } else if (sum < arr[i]) l++;
                else r--;
            }
        }
        // time: o(n ^ 2)
        return count;
    }

    // count possible triangles
    // triangle prop: sum of any two sides should be greater that third side
    // so we will sort and start finding the 2 sides from the behind as we will get big sides and 
    // it will be greater than 3 side which will be from front
    // we will fix larger side on back
    public static int countTriangles(int[] arr) {
        Arrays.sort(arr);
        int i = arr.length - 1, count = 0;
        while (i >= 2) {
            int si = 0, j = i - 1;
            while (si < j) {
                int sum = arr[si] + arr[j];
                if (sum > arr[i]) {
                    count += j - si;
                    j--;
                } else {
                    si++;
                }
            }
            i--;
        }
        // time: o(n ^ 2)
        return count;
    }

    // count 0s in a sorted matrix;
    // rows and cols are sorted find number of 0s
    public static int countZeros(int[][] arr) {
        int count = 0, r = 0, c = arr[0].length - 1;
        while (r < arr.length && c >= 0) {
            int ele = arr[r][c];
            if (ele == 1) c--;
            else if (ele == 0) {
                count += c + 1;
                r++;
            }
        }
        return count;
    }

    // count ele in 2 arrays. They may have dups
    // For each element in arr1 count elements less than or equal to it in array arr2.
    // we can optimize it using a freq map and then making a psa of that map
    // psa will tell us that how many number freq added till a particular number
    // ie number of elements smaller than or equal to that number
    // so instead of getting count in lgn we can do it in o(1)
    public static int countEle(int[] a, int[] b) {
        Arrays.sort(b);
        for (int i = 0; i < a.length; i++) {
            int count = bs_helper(b, a[i]);
            a[i] = count;
        }
        return a;
    }

    public static int bs_helper(int[] b, int ele) {
        int si = 0, ei = b.length - 1, count = 0;
        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (b[mid] <= ele) {
                count += mid - si + 1;
                si = mid + 1;
                // we will move towards finding last occ of the ele
            } else if (ele < b[mid]) ei = mid - 1;
        }
        return count;
    }

    // count ele optimized
    public static int[] countEle_opti(int[] a, int[] b) {
        int max1 = -(int)(1e8);
        for (int val: a) max1 = Math.max(max1, val);

        int max2 = -(int)(1e8);
        for (int val: b) max2 = Math.max(max2, val);

        int omax = Math.max(max1, max2);
        
        int[] farr = new int[100000];
        // u can also try omax + 1
        int[] psa = new int[100000];
        // u can also try omax + 1 for size
        // 1e5 is taken as a rough figure
        for (int val: b) farr[val]++;

        psa[0] = farr[0];
        for (int i = 1; i <= Math.max(max1, max2); i++) psa[i] = farr[i] + psa[i - 1];
        // here it will be psa.length

        for (int i = 0; i < a.length; i++) {
            int val = a[i];
            a[i] = psa[val];
        }
        return a;
    }

    // count zeros xor pairs
    // if a == b then a ^ b = 0
    public static int countXorPairs(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int val: arr) map.put(val, map.getOrDefault(val, 0) + 1);

        int count = 0;
        for (Integer key: map.keySet()) {
            int freq = map.get(key);
            // n * (n + 1) / 2 -> for this i have tweaked for the scenario
            count += (freq * (freq - 1)) / 2;
        }
        // time: o(n), space: o(n)
        return count;
    }

    // facing the sun
    public static int seeSunRise(int[] arr) {
        int count = 0, max = -(int)(1e8);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
                count++;
            }
        }
        return count;
    }

    // count distinct absolute array ele
    // can be done using hashmap but need to make space o(1)
    public static int count(int[] arr) {
        int prev = -(int)(1e8), next = (int)(1e8), count = 0, i = 0, j = arr.length - 1;
        while (i <= j) {
            int ith = Math.abs(arr[i]);
            int jth = Math.abs(arr[j]);

            if (ith == jth) {  
                if (jth != next && ith != prev) count++;
                i++;
                j--;
                prev = ith;
                next = jth;
            } else if (ith < jth) {
                if (jth != next) count++;
                next = arr[j];
                j--;
            } else {
                if (ith != prev) count++;
                prev = ith;
                i++;
            }
        }
        return count;
    }

    // find transition point
    // given an arr of 0 and 1, tp is defined as the index at which 1 comes and before this idx 0 comes
    // last occ idx of 0 + 1 or 1st occ of 1 idx
    public static int transitionPoint(int[] arr) {
        int si = 0, ei = arr.length - 1, idx = -1, ele = 1;
        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (arr[mid] == ele) {
                idx = mid;
                ei = mid - 1;
            } else if (ele < arr[mid]) {
                ei = mid - 1;
            } else si = mid + 1;
        }
        return idx;
    }

    // first bad version
    // if a version is bad then next version will be also bad 
    // so it is an app of bs as we have to check all versions if they pass QA
    // instead of checking one by one use bs
    public static int badVersion(int n) {
        int si = 1, ei = n, idx = -1;
        while (si <= ei) {
            int mid = si + ((ei - si) >> 1);
            boolean isBad = isBadVersion(mid);
            if (isBad) {
                // go to previous version
                ei = mid - 1;
                idx = mid;
            } else {
                // version is good, try next generation
                si = mid + 1;
            }
        }
        return idx;
    }

    // guess the number higher or lower
    public static int guessGame(int n) {
        int si = 1, ei = n;
        while (si <= ei) {
            int mid = si + ((ei - si) >> 1);
            int k = guess(mid);
            // guessed num is less than picked num
            if (k < 0) ei = mid - 1;
            // guessed num is greater than picked one
            else if (k > 0) si = mid + 1;
            else {
                return mid;
            }
        }
        return -1;
    }

    // the above 2 ques are used in pressure testing in tests

    // Find the Element that Appears Once in Sorted Array
    // All other ele appear exactly twice
    // 1, 1, 2, 2, 4, 4, 6, 6, 8, 9, 9
    // one method is 
    // int ans = 0;
    // for (int val: arr) {
    //     ans ^= val;
    // }
    // return ans;
    public static int findEle(int[] arr) {
        // acc to ques the size of arr must be odd as all ele are 2 * x + 1(single one)
        // we will apply bs in order where there are odd elements, as we can get our ele in that grp
        // keeping same ele in same grp
        int si = 0, ei = arr.length - 1;
        if (arr[si] != arr[si + 1]) return arr[si];
        else if (arr[ei] != arr[ei - 1]) return arr[ei];
        else if (arr[si] == arr[ei]) return arr[si];
        // if one ele is there

        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (arr[mid] != arr[mid - 1] && arr[mid] != arr[mid + 1]) return arr[mid];
            else if (arr[mid] == arr[mid - 1]) {
                int lc = mid - si + 1;
                if ((lc & 1) == 0) {
                    si = mid + 1;
                } else ei = mid - 2;
                // mid and mid - 1 are calculated
            } else if (arr[mid] == arr[mid + 1]) {
                int rc = ei - mid + 1;
                if ((rc & 1) == 0) ei = mid - 1;
                else si = mid + 2;
                // mid and mid + 1 are calculated
            }
            // we have kept same ele together because then we will be able to check easily if total 
            // even or odd & we will be able to reject a complete subarray on this basis
            // time: lgn
        }
        return -1; 
    }

    // given a 2d binary sorted matrix, find the row with max ones, we have to return idx of that row
    // in this matrix each row is sorted
    // go in each row and find 1st one, and then count = col - idx + 1;
    public static int countOnes(int[][] arr) {
        int ans = -(int)(1e8), idx = -1;
        for (int i = 0; i < arr.length; i++) {
            int count = bs_helper(arr[i]);
            if (count > ans) {
                ans = count;
                idx = i;
            }
        }
        return idx;
    }

    public static int bs_helper(int[] arr) {
        int si = 0, ei = arr.length - 1, idx = -1, ele = 1;
        if (arr[si] == 1) return arr.length;
        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (arr[mid] == ele) {
                idx = mid;
                ei = mid - 1;
            } else if (ele < arr[mid]) {
                ei = mid - 1;
            } else si = mid + 1;
        }
        return arr.length - idx;
    }        
    // time: rows(log(cols))

    // heaters
    /*
    Winter is coming! During the contest, your first job is to design a standard heater with a fixed warm radius to warm all the houses.
    Every house can be warmed, as long as the house is within the heater's warm radius range. 
    Given the positions of houses and heaters on a horizontal line, return the minimum radius standard of heaters so that those heaters could cover all houses.
    Notice that all the heaters follow your radius standard, and the warm radius will the same. 

    we have to apply binary search on radius
    so for every house we will find the heater at smaller and big pos than the pos of house
    it is about finding ceil and floor for each house in heater arr, so sort that arr
    and at the end max of the dist will be the ans
    */

    static class pair {
        int c = -1;
        int f = -1;
        public pair (int c, int f) {
            this.c = c;
            this.f = f;
        }
    }

    public static int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(heaters);
        int ans = -(int)(1e8);
        // max of the mins
        // to find least dist
        for (int i = 0; i < houses.length; i++) {
            // we want ceil and floor together
            int house = houses[i];
            pair cp = findCeilAndFloor(heaters, house);
            int d1 = cp.f == -1 ? (int)(1e8) : house - cp.f;
            int d2 = cp.c == -1 ? (int)(1e8) : cp.c - house;
            ans = Math.max(ans, Math.min(d1, d2));
        }
        return ans;
    }
    
    public static pair findCeilAndFloor(int[] arr, int ele) {
        int si = 0, ei = arr.length - 1;
        pair cp = new pair(-1, -1);

        while (si <= ei) {
            int mid = (si + ei) >> 1;
            if (arr[mid] == ele) {
                // heater is in the house
                cp.c = ele;
                cp.f = ele;
                break;
            } else if (ele > arr[mid]) {
                cp.f = arr[mid];
                // u have a smaller val than ele
                si = mid + 1;
            } else {
                // u have the greater one
                cp.c = arr[mid];
                ei = mid - 1;
            }
        }
        // time: (sort) heaters.length log (heaters.length) + house.length log (heater.length)
        return cp;
    }

    // punish the students
    // we will not find swaps for single marks, we will find total swaps & subtract them from sum
    // and we have applied bubble sort as in ques it was mentioned for this
    public static boolean canPunish(int[] marks, int[] roll, double avg) {
        int sum = 0, swaps = 0;
        for (int val: marks) sum += val;
        // bubble sort for swaps
        for (int i = 0; i < roll.length - 1; i++) {
            for (int j = 0; j < roll.length - i - 1; j++) {
                if (roll[j] > roll[j + 1]) {
                    int temp = roll[i];
                    roll[i] = roll[i + 1];
                    roll[i + 1] = temp;
                    swaps += 2;
                }
            }
        }
        double newMarks = sum - swaps;
        return ((newMarks * 1.0) / marks.length) >= avg;
    }

    // largest number
    // we have to make a largest number from given arr elements
    public static String largestNum(int[] a) {
        String[] arr = new String[a.length];
        for (int i = 0; i < a.length; i++) {
            arr[i] = a[i] + "";
        } 
        // in strings sort performs in lexicographic order
        Arrays.sort(arr, (t, o) -> {
            // as string char can go upto 1e15, so we should take long
            long n1 = Long.parseLong(t + o);
            long n2 = Long.parseLong(o + t);
            if (n1 > n2) return 1;
            else if (n2 > n1) return -1;
            else return 0;
        });
        StringBuilder sb = new StringBuilder();
        for (int i = arr.length - 1; i >= 0; i--) sb.append(arr[i]);

        // if max val in arr is 0 then at the front it will be 0 only
        if (sb.charAt(0) == '0') return "0";

        return sb.toString(); 
        // time n + nlgn + n = nlgn
        // space n;
    }

    // largest perimeter
    // sort the sides arr and start from last the first 3 sides you get in the order will have the max 
    // perimeter and u don't need to move backward anymore
    public static int largestPeri(int[] arr) {
        Arrays.sort(arr);
        int i = arr.length - 1, peri = 0;
        while (i >= 2) {
            if (arr[i - 1] + arr[i - 2] > arr[i]) {
                int sum = arr[i] + arr[i - 1] + arr[i - 2];
                peri = sum;
                break;
                // no need to check more, u have the largest possible one
            }
            i--;
        }
        return peri;
    }
 
    // leaders in an array
    // Your task is to find the leaders in the array. An element of array 
    // is leader if it is greater than or equal to all the elements to its right side. 
    // The rightmost element is always a leader. 
    public static ArrayList<Integer> leaders(int[] arr) {
        int maxLeader = arr[arr.length - 1];
        ArrayList<Integer> ans = new ArrayList<>();
        ans.add(arr[arr.length - 1]);
        for (int i = arr.length - 2; i >= 0; i--) {
            int ele = arr[i];
            if (ele >= maxLeader) {
                maxLeader = ele;
                ans.add(0, ele);
            }
        }
        return ans;
    }

    // ishaan and his sticks
    /*
    Ishaan has a craving for sticks. He has N sticks. 
    He observes that some of his sticks are of the same length, 
    and thus he can make squares out of those.
    He wants to know how big a square he can make using those sticks as sides. 
    Since the number of sticks is large, he can't do that manually. 
    Can you tell him the maximum area of the biggest square that can be formed?
    Also, calculate how many such squares can be made using the sticks.
    */
    public static ArrayList<Integer> ishaan(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int val: arr) map.put(val, map.getOrDefault(val, 0) + 1);
        int count = 0, ans = -(int)(1e8);

        for (Integer key: map.keySet()) {
            int freq = map.get(key);
            int area = key * key;
            if (freq >= 4 && area >= ans) {
                count = freq / 4;
                ans = Math.max(ans, area);
            }
        }
        ArrayList<Integer> res = new ArrayList();
        if (count > 0) {
            res.add(ans);
            res.add(count);
        } else res.add(-1);
        return res;
    }

    // toppers of class
    // required time: nlgk, space; k
    static class pair {
        int ele;
        int idx;
        public pair (int ele, int idx) {
            this.ele = ele;
            this.idx = idx;
        }
    }
    public static void toppers(int[] arr, int k) {
        PriorityQueue<pair> pq = new PriorityQueue<>((t, o) -> {
            if (t.ele != o.ele) return t.ele - o.ele;
            else o.idx - t.idx;
            // because we will be filling from right hand side therefore for indx we need big to come out
            // first as we need them in ascending order
        });
        for (int i = 0; i < arr.length; i++) {
            pq.add(new pair(ele, i));
            if (pq.size() > k) pq.remove();
        }
        int[] ans = new int[pq.size()];
        int i = ans.length - 1;
        while (pq.size() > 0) ans[i--] = pq.remove().idx;
        return ans;
    }

    /*
    You are given four integers row, cols, rCenter, and cCenter. 
    There is a rows x cols matrix and you are on the cell with the coordinates (rCenter, cCenter).
    Return the coordinates of all cells in the matrix, 
    sorted by their distance from (rCenter, cCenter) from the smallest distance to the largest distance.
    Manhattan dist: (r1, c1) (r2, c2) -> |r2 - r1| + |c2 - c1|
    */

    public static int[][] dist(int n, int m, int r, int c) {
        // bfs
        Queue<Integer> qu = new LinkedList<>();
        int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        boolean[][] vis = new boolean[n][m];
        int[][] ans = new int[n * m][2];
        int i = 0;
        qu.add(r * m + c);
        vis[r][c] = true;
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                int cord = qu.remove();
                int x = cord / m;
                int y = cord % m;

                if (i < ans.length) {
                    ans[i][0] = x;
                    ans[i][1] = y;
                    i++;
                }

                for (int[] d: dirs) {
                    int dr = x + d[0];
                    int dc = y + d[1];
                    if (dr >= 0 && dc >= 0 && dr < n && dc < m && !vis[dr][dc]) {
                        qu.add(dr * m + dc);
                        vis[dr][dc] = true;
                    }
                }
            }
        }
        return ans;
    }

    // using sorting on the basis of manhattan distance
    public static int[][] dist(int n, int m, int r, int c) {
        int[][] ans = new int[n * m][2];
        // first we will add the cords into an array and then we will sort them on the basis
        // of manhattan distance
        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ans[idx][0] = i;
                ans[idx][1] = j;
                idx++;
            }
        }
        Arrays.sort(ans, (t, o) -> {
            // cord1, cord2 are t, o and cord2 are given r and c
            // we have find the dist from t
            int d1 = Math.abs(t[0] - r) + Math.abs(t[1] - c); 
            // we have find the dist from o
            int d2 = Math.abs(o[0] - r) + Math.abs(o[1] - c); 
            // and then we will find the minm dis
            return d1 - d2;
        });
        return ans;
    }

    // kth smallest prime fraction
    public static int[] kthSmallestPrimeFr(int[] arr, int k) {
        // [num, den]
        // [1, 2, 3, 5]
        PriorityQueue<int[]> pq = new PriorityQueue<>((t, o) -> {
            return (arr[t[0]] * arr[o[1]]) - (arr[t[1]] * arr[o[0]]);
        });

        for (int i = 0; i < arr.length; i++) {
            // we will add num, den
            // in this num will start from 0 so pq will look like (in terms of elements)
            // 1/1, 1/2, 1/3, 1/5
            pq.add(new int[]{0, i});
        }

        // as we have added all pairs so we can directly get k smallest 
        // but to make ans we will extract k - 1 times in a loop and kth one at last
        k -= 1;
        while (k-- > 0) {
            int[] cp = pq.remove();
            // you will get idx of nr and dr of minm fraction
            // as we have added nrs for all fraction from 0, so we need to add new ones with inc
            // of nr idx and make sure while inc nr idx < dr idx
            cp[0]++;
            if (cp[0] < cp[1]) pq.add(cp);
        }
        // now we have the kth one at top
        int[] ans = pq.remove();
        ans[0] = arr[ans[0]];
        ans[1] = arr[ans[1]];
        // ele extracted from idx
        return ans;
        // time: adding (nlgn) + removal (k - 1 lgn) -> nlogn
        // space: n
    }

    // 

    public static void solve() {
        
    }

    public static void main(String args[]) {
        solve();
    }
}