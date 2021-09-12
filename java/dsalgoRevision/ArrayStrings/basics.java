import java.util.*;
class basics {

    // faulty keyboard
    // name : m a n n u , typed: m m a a n n u -> o/p -> true
    public static boolean isNameThere(String name, String typed) {
        if (name.length() > typed.length()) return false;
        int i = 0, j = 0;
        while (i < name.length() && j < typed.length()) {
            char ch_i = name.charAt(i);
            char ch_j = typed.charAt(j);

            if (ch_i == ch_j) {
                i++; j++;
            } else if (i > 0 && ch_j == name.charAt(i - 1)) {
                j++;
            } else {
                return false;
            }
        }
        // ideally i and j will end together if they are valid strings
        // but if typed has a char which is extra than name then j will not end but i will
        // if i has ended but j last char will be same as i - 1 so below loop
        while (j < typed.length()) {
            if (name.charAt(i - 1) != typed.charAt(j)) return false;
            j++;
        }
        // what if j ended but i < name.length()
        if (i < name.length()) return false;
        return true;
    }

    // *************************** RANGE ADDITION ***********************
    // brute force -> o(n . q) where n is arr length, q is queries
    // o(n) -> impact on si of +inc and on ei + 1 of -inc
    // now calculate the prefix sum array of the arr produced
    public static int[] rangeAddition(int[][] data, int n) {
        // if u make psa for every query then u will make psa from si to end
        // but we want to impact only given ei
        // so we add -ve inc at given ei + 1 so that when they add at ei + 1 impact becomes 0
        // AS PS[EI + 1] += PS[EI - 1]
        // and from ei + 1 till end the impact will continue to be 0;
        // now if u make psa for individual arr or at last the ans will be same
        int[] arr = new int[n];
        for (int[] d: data) {
            int si = d[0];
            int ei = d[1];
            int inc = d[2];

            arr[si] += inc;
            if (ei + 1 < arr.length) arr[ei + 1] -= inc;
        }
        int sum = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sum += arr[i];
            arr[i] = sum;
        }
        return arr;
        // if q > n -> o(n) upper bound
    }

    // car pooling
    public static boolean carPooling(int[][] arr, int cap) {
        if (arr.length == 0 || cap == 0) return true;
        int max = -(int)(1e8);
        for (int[] a: arr) max = Math.max(max, a[1]);
        int[] psa = new int[max + 1];

        for (int[] a: arr) {
            int si = a[0];
            int ei = a[1];
            int pgr = a[2];
            // passenger
            psa[si] += pgr;
            psa[ei] -= pgr;
        }

        for (int i = 0; i < psa.length; i++) {
            if (i == 0) {
                if (psa[i] > cap) return false;
                // if first trip's passenger count is > cap
            } else {
                psa[i] += psa[i - 1];
                if (psa[i] > cap) return false;}
        }
        // time: n
        // according to constraints ei <= 1000, so constant space
        return true;
    }   

    // majority element - 1
    // Moore's Vooting Algorithm
    // we will try to pair up elements and at last element with highest freq will pe present in val
    public static void MVA(int[] arr) {
        int val = potentialCandi(arr);
        // now u have the potential val
        int count = 0;
        for (int ele: arr) {
            if (ele == val) count++;
        }
        if (count > (arr.length >> 1)) System.out.println(val);
        else System.out.println("No Majority Element exist");
    }

    public static int potentialCandi(int[] arr) {
        int count = 1, ans = arr[0];
        for (int val: arr) {
            if (val == ans) count++;
            else count--;

            if (count == 0) {
                // we assume that this element can have dups and form a valid candidate
                count = 1;
                ans = val;
            }
            // so basically we will be able to filter out the ele with high freq as count will always 
            // increment for that and ans will retain that ele
        }
        return ans;
    }

    // majority element - 2
    // Find all elements that appear more than n / 3 times and return it in an arraylist.
    public static ArrayList<Integer> majEle_2(int[] arr) {
        // there is no pairing of 3 ele, but we can merge them
        int val1 = arr[0], count1 = 1, val2 = arr[0], count2 = 0;

        for (int i = 1; i < arr.length; i++) {
            int ele = arr[i];
            if (ele == val1) count1++;
            else if (ele == val2) count2++;
            else {
                if (count1 == 0) {
                    val1 = ele;
                    count1 = 1;
                } else if (count2 == 0) {
                    val2 = ele;
                    count2 = 1;
                } else {
                    // we have the 3 ele of the triplet so merge it
                    count1--; count2--; 
                }
            }
        }
        // val1 and val2 are potential candidates
        // freq > n / 3
        ArrayList<Integer> res = new ArrayList<>();
        if (isGreater(arr, val1)) res.add(val1);
        // let's say arr = {1, 1, 1, 1} -> val1 = 1, count = 4, val2 = 1, count2 = 1
        // but when we will check for is freq > n / 3 it will add 1 again
        if (val1 != val2 && isGreater(arr, val2)) res.add(val2);
        return res;
    }

    public static boolean isGreater(int[] arr, int val) {
        int count = 0;
        for (int ele: arr) {
            if (ele == val) count++;
        }
        return (count > arr.length / 3);
    }

    // majority element GENERAL
    public static ArrayList<Integer> majority_gen(int[] arr, int k) {
        int f = arr.length / k;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int val: arr) map.put(val, map.getOrDefault(val, 0) + 1);
        ArrayList<Integer> ans = new ArrayList<>();
    
        for (Integer key: map.keySet()) {
            if (map.get(key) > f) ans.add(key);
        }
        Collections.sort(ans);
        return ans;
    }

    // *********** for majority element if u need to find n / k then k - 1 vars are needed **************

    // container with most water
    /*
        Given n non-negative integers a1, a2, ..., an. 
        Each represents a point at coordinate (i, ai). 
        'n' vertical lines are drawn such that the two endpoints of the line i is at (i, ai) and (i, 0). 
        Find two lines, which, together with the x-axis forms a container, 
        such that the container contains the most water.
        consider area not volume as it is in 2d

        water = Min(h1, h2) * (j - i)
        now if I inc i then water may be dec or inc
        if dec j the water will dec as if min comes then water will dec for sure, 
        if max will come then reduction in width will reduce water
        ie height minimum in nature should be inc or dec
        because we are trying for more height if there is reduction in width
    */
    public static int maxWater(int[] arr) {
        int i = 0, j = arr.length - 1, water = -(int)(1e8);
        while (i < j) {
            water = Math.max(water, (j - i) * (Math.min(arr[i], arr[j])));
            if (arr[i] <= arr[j]) i++;
            else j--;
        }
        return water;
    }
    
    // Product of Array Except Itself Without Using Division Operator
    // idea -> left[i - 1] * right[i + 1]
    // left and right are prefix and suffix products
    public static int[] product(int[] arr) {
        int n = arr.length, lp = 1;
        // lp is left product
        int[] ans = new int[n];
        int[] right = new int[n];

        right[n - 1] = arr[n - 1];
        for (int j = n - 2; j >= 0; j--) right[j] = right[j + 1] * arr[j];

        for (int i = 0; i < n; i++) {
            ans[i] = (i + 1 < n) ? lp * right[i + 1] : lp;
            lp *= arr[i];
        }        
        return ans;
    }

    // next greater element 3
    // start from right and find the 1 decreasing point, then start again from right and find 
    // just bigger ele than the dip element, now reverse the elements after the dip ele
    public static String nextGreater_3(String str) {
        char arr[] = str.toCharArray();
        int i = arr.length - 2;

        while (i >= 0 && arr[i] >= arr[i + 1]) i--;
        // if you don't get any dip then return -1;
        if (i == -1) return -1;
        // find greater ele on right 
        int j = arr.length - 1;
        while (arr[i] >= arr[j]) j--;
        // swap dip and just greater ele than that
        swap(arr, i, j);

        j = arr.length - 1;
        int k = i + 1;
        while (j > k) {
            swap(arr, i, j);
            j--;
            k++;
        }
        String ans = String.valueOf(arr);
        return ans;
    }

    // ************************* CHAINING TECHNIQUE **********************

    // max chunks to make an array sorted
    // if min chunks are required then the whole array is a segment so ans = 1;
    public static int maxChunks_1(int[] arr) {
        // arr ele are 0 to (n - 1)
        int count = 0, max = -(int)(1e8);
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(arr[i], max);
            if (max == i) count++;
            // so basically when ele == i it is a chunk which contains max ele, 
            // and max ele == i so in this chunk till here max and min are covered
            // and sorting them would do good
        }
        return count;
    }

    // max chunks 2
    // left[i] = [0, i] max
    // right[i] = [i, len - 1] min
    // chunk is created when you contain the max from left max which will be samaller than
    // or equal to min from i + 1;
    // in right arr at last the ele u should get is +oo because it will lead to left ele contain into 
    // a chunk 
    public static int chunks_2(int[] arr) {
        int n = arr.length, count = 0, leftMax = -(int)(1e8);
        int[] rightMin = new int[n];
        
        // find rightmin, leftmax will be calculated on the way 

        rightMin[n - 1] = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) rightMin[i] = Math.min(arr[i], rightMin[i + 1]);

        // now traverse and find chunks if leftMax is contained as it will become min from the 
        // min on right

        for (int i = 0; i < n; i++) {
            leftMax = Math.max(leftMax, arr[i]);
            int rmin = (i + 1 < n) ? rightMin[i + 1] : (int)(1e8);
            if (leftMax <= rmin) count++; 
        }
        return count;
    }

    // Partition Array Into Disjoint Intervals in o(n) space, n time
    public static int partitionIntoDisjoint(int[] arr) {
        int leftMax = -(int)(1e8), n = arr.length;
        int rightMin[] = new int[n];
        rightMin[n - 1] = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) rightMin[i] = Math.min(arr[i], rightMin[i + 1]);
        
        int idx = -1;
        
        for (int i = 0; i < n; i++) {
            leftMax = Math.max(leftMax, arr[i]);
            int r_min = (i + 1 < n) ? rightMin[i + 1] : (int)(1e8);
            if (leftMax <= r_min) {
                idx = i;
                break;
            }
        }
        return (idx + 1);
    }

    // same question in o(1) space, lc-915
    public static int partitionIntoDisjoint_2(int[] arr) {
        int greater = arr[0], leftMax = arr[0], idx = 0;
        for (int i = 1; i < arr.length; i++) {
            greater = Math.max(greater, arr[i]);
            // partition is when leftmax is captured by a min value on right
            if (arr[i] < leftMax) {
                leftMax = greater;
                idx = i;
            }
            // eg : leftmax comes out to be 10 - 13, 14, 15, 11
            // when 11 comes it will tend to go left but it won't disturb leftMax as it is bigger than
            // that. This is how we will be able to capture leftMax with a min value on right
        }
        return idx + 1;
    }

    // *************************** CHAINING TECH IN STRINGS *********************

    // partition labels
    // We want to partition this string into as many parts as 
    // possible so that each letter appears in at most one part.
    // character maximum impact theoram based
    public static ArrayList<Integer> chunks_str(String str) {
        // make a hashmap to know every character last idx
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) map.put(str.charAt(i), i);

        // now for any partition find the maxm impact till an index
        // when the maxm impact indx is eqaul to curr index, you have one chunk
        // we have to find length for each chunk
        int j = -1, impact_idx = -1;
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < str.length(); i++) {
            impact_idx = Math.max(impact_idx, map.get(str.charAt(i)));
            if (i == impact_idx) {
                list.add(i - j);
                // j will be on chunk start - 1 such that length can be calculated as i - j
                // basically j will act as -1 for every chunk
                j = i;
            }
        }
        return list;
    }

    //

    // maximum product of 3 numbers
    // Find three numbers whose product is maximum and return the maximum product.
    // finding 3 big numbers won't work as there can be negative numbers    
    public static int product(int[] arr) {
        // this will work only for positive numbers
        int fmax = -(int)(1e8), smax = -(int)(1e8), tmax = -(int)(1e8), smin = (int)(1e8), fmin = (int)(1e8);
        for (int val: arr) {
            // max check
            if (val > fmax) {
                tmax = smax;
                smax = fmax;
                fmax = val;
            } else if (val > smax) {
                tmax = smax;
                smax = val;
            } else if (val > tmax) {
                tmax = val;
            } 
            // min check
            if (val < fmin) {
                smin = fmin;
                fmin = val;
            } else if (val < smin) {
                smin = val;
            }
        }
        // trying out both combinations
        int positive = fmax * smax * tmax;
        int negative = fmin * smin * fmax;
        return Math.max(positive, negative);
    }

    // ******************* SCHEDULING PROBLEMS ********************

    // Merge Intervals
    // lc 56
    public static int[][] merge(int[][] arr) {
        Arrays.sort(arr, (t, o) -> {
            if (t[0] != o[0]) return t[0] - o[0];
            else return t[1] - o[1];
        });
        
        ArrayList<int[]> list = new ArrayList<>();
        
        list.add(arr[0]);

        for (int[] t2: arr) {
            int[] t1 = list.get(list.size() - 1);
            // merge 
            if (t2[0] < t1[1]) {
                // update the ending points
                t1[0] = Math.min(t1[0], t2[0]);
                t1[1] = Math.max(t1[1], t2[1]);
            } else {
                // curr time is more than prev
                list.add(t2);
            }
        }
        // int[][] narr = new int[list.size()][2];
        // for (int i = 0; i < narr.length; i++) {
        //     narr[i] = list.get(i);
        // }
        int[][] narr = list.toArray(new int[list.size()][]);
        return narr;
    }

    // meeting rooms - 1
    // a person can't be present in 2 meetings together
    public static boolean meetingsRooms_1(int[][] arr) {
        // sort on the basis of start time
        Arrays.sort(arr, (t, o) -> {
            if (t[0] != o[0]) return t[0] - o[0];
            else return t[1] - o[1]; 
        });
        // travel and see if a meeting's start point is less than the end point of 
        // current meeting, in this case it will return false
        // for ex: prev -: 2, 5  and curr 4, 10
        for (int i = 1; i < arr.length; i++) {
            int[] prev = arr[i - 1];
            int[] curr = arr[i];
            // int[] -> start, end
            // is prev meeting's end point is greater than curr meeting's start point
            if (prev[1] > curr[0]) return false;
        }
        return true;
    }

    // meeting rooms-2
    // Task is to "figure out, how many minimum number of meeting rooms are required to schedule all 
    // meetings?".
    public static int meetingsRooms_2(int[][] arr) {
        if (arr.length == 0 || arr[0].length == 0) return 0;
        Arrays.sort(arr, (t, o) -> {
            return t[0] - o[0];
        });
        // problem is to check in the rooms that if any meeting gets over so that we can use that room
        // for that we can use min heap 
        // basis of heap will be, we will add ei in it such that heap will tell what is the minm
        // ei for any meeting to get over
        // heap will give a time at which any meeting will get over and if curr meeting's si > heap.peek()
        // add it or you can say we alotted a room for it or size is the number of rooms
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(arr[0][1]);
        for (int i = 1; i < arr.length; i++) {
            int[] m = arr[i];
            int si = m[0];
            int ei = m[1];
            // if curr meeting start before any meeting in a room, then alot a room
            if (si < pq.peek()) {
                pq.add(ei);
            } else {
                // if a meeting starts after any meeting ends then empty that room and allocate that room
                // to curr meeting
                pq.remove();
                pq.add(ei);
            }
        }
        // space : n (worst case)
        // time: nlgn + nlgn 
        return pq.size();
    }   

    // meeting rooms_2 chronological approach
    // order in which everything appears according to their occurance in time
    public int meetinRooms_chr(int[][] arr) {
        // create si_arr, and ei_arr
        // si_arr will tell that at what time new meeting will start
        // ei_arr will tell that till that at what time ei of meeting will end
        int[] left = new int[arr.lenght];
        int[] right = new int[arr.length];
        for (i = 0; i < arr.length; i++) {
            left[i] = arr[i][0];
            right[i] = arr[i][1];
        }
        Arrays.sort(left);
        Arrays.sort(right);
         
         // so if si is less that ei then count++;
         // else 

        int count = 0, i = 0, j = 0; 
        while (i < left.length) {
            int si = left[i];
            int ei = right[j];
            if (si < ei) {
                count++;
                i++
            } else {
                i++; j++;
                // reuse the room
            }
        }
        // time: o(n + nlgn + n) -> o(nlgn)
        // space o(n)
        return count;
    }

    // best meeting point distance
    // theory in copy
    public static int bestMeetingPont(int[][] arr) {
        // get rows and cols first in n^2 to get x and y in sequence
        int n = arr.length, m = arr[0].length;

        ArrayList<Integer> rows = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (arr[i][j] == 1) rows.add(i);
            }
        }
        // get cols in sequence
        ArrayList<Integer> cols = new ArrayList<>();
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                if (arr[i][j] == 1) cols.add(j);
            }
        }
        // as we have x,y in seq now get the middle point
        // for even or odd we will take first mid
        int mx = rows.get(rows.size() >> 1);
        int my = cols.get(cols.size() >> 1);

        // calculate manhattan distance wrt to median x mx and median y my
        int dist = 0;
        for (Integer val: rows) {
            dist += Math.abs(mx - val);
        }

        for (Integer val: cols) {
            dist += Math.abs(my - val);
        }

        return dist;
    }

    // insert intervals
    public static int[][] insertInterval(int[][] Intervals, int[] interval) {
        // first add all the old intervals such that they don't overlap
        ArrayList<int[]> list = new ArrayList<>();
        int idx = 0;
        while (idx < Intervals.length) {
            if (Intervals[idx][1] < interval[0]) {
                // 1, 5 and 12, 20
                list.add(Intervals[idx]);
                idx++;
            } else {
                // if they overlap then
                // 10, 13 and 12, 20
                break;
            }
        }
        // if interval is the first one to come or 8, 10 in list and 12, 20
        if (list.size() == 0 || list.get(list.size() - 1)[1] < interval[0]) list.add(interval);
        // codition for overlap -> prev[ei] >= curr[si], ideally prev[ei] < curr[si]
        else  {
            // merge 
            // 8, 13 and 12, 20
            int lastInterval[] = list.get(list.size() - 1);
            lastInterval[1] = Math.max(lastInterval[1], interval[1]);
            lastInterval[0] = Math.min(lastInterval[0], interval[0]);
        }

        // and if prev[si] < curr[si] && prev[ei] >= curr[si], it means that curr is already part of prev
        // add the remaning intervals
        while (idx < Intervals.length) {
            int[] prev = list.get(list.size() - 1);
            int[] curr = Intervals[idx];
            if (prev[1] >= curr[0]) {
                // merge
                // prev 10, 20 and curr 20, 20
                prev[1] = Math.max(prev[1], curr[1]);
                prev[0] = Math.min(prev[0], curr[0]);
            } else {
                list.add(curr);
            }
            idx++;
        }
        return list.toArray(new int[list.size()][]);
    }

    // interval List intersection
    public static int[][] intersection(int[][] a, int[][] b) {
        // case 1 : a[ei] > b[si] : 2, 10 & 8, 15
        // case 2 : b[ei] > a[si] : 8, 15 & 2, 20
        // case 2 : a[ei] < b[si] : 2, 10 & 12, 20
        // movement will be based upon whose ei is less, so that we can cover small intervals common time 
        // with big intervals
        int i = 0, j = 0;
        ArrayList<int[]> ans = new ArrayList<>();
        while (i < a.length && j < b.length) {
            int[] int_1 = a[i];
            int[] int_2 = b[j];
            int[] common = new int[]{-1, -1};
            common[0] = Math.max(int_1[0], int_2[0]);
            common[1] = Math.min(int_1[1], int_2[1]);
            if (common[0] <= common[1]) ans.add(common);
            if (int_1[1] < int_2[1]) i++;
            else if (int_1[1] > int_2[1]) j++;
        }
        return ans.toArray(new int[ans.size()][]);
    }

    // ********* IMPORTANT minm number of platforms ****************
    /*
    1. We have arrival and departure times of all trains that reach a railway station. 
    2. We have to find the minimum number of platforms required for the railway station so that no train is kept waiting.
    3. Consider that all the trains arrive on the same day and leave on the same day. 
    4. Arrival and departure time can never be the same for a train but we can have arrival time of one train equal to departure time of the other. 
    5. At any given instance of time, same platform can not be used for both departure of a train and arrival of another train. In such cases, we need different platforms.
    */

    public static int minPlatforms(int[] arr, int[] dep) {
        // instead of finding minm platforms, if we find max platforms required at a time
        // we can achieve that
        Arrays.sort(arr);
        Arrays.sort(dep);
        // we need trains at a time and platforms
        // now if arr[i] < dep[j] then a train comes, train++; (acc to ques if arr[i] <= dep[j], then also t++)
        // if arr[i] > dep[j] then train is going , train--;
        int trains = 0, platform = 0, i = 0, j = 0;
        while (i < arr.length && j < dep.length) {
            if (arr[i] <= dep[j]) {
                trains++;
                i++;
            }
            else {
                trains--;
                j++;
            }
            platform = Math.max(trains, platform);
        }
        return platform;
    }

    //

    // sort array by parity
    // same as sort 0 1 in an array
    public static int[] sortEvenOdd(int[] arr) {
        // even -> odd
        // i -> unsolved one
        // j -> first odd such that j - 1 -> even
        int i = 0, j = 0;
        while (i < arr.length) {
            if ((arr[i] & 1) == 1) {
                // odd 
                i++;
            } else {
                swap(arr, i, j);
                i++; // to get another unknown
                j++; // to get first odd
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    // Min Jumps With +i -i Moves 
    // try to reach within max distance to poiint X
    // note the distance ie how much forward are you from the target
    // either this dist will come out to be odd or even
    // dist = currPoint - dest
    // if even -> reverse the x / 2th jump
    // if odd take one more jump = p
    // after p dist is even -> even -> reverse dist / 2 jump (odd + even = odd)
    // afer p dist is odd -> take one more jump q and then dist will come even, find dist / 2th jump
    // (odd + odd = even)
    public static int jumps(int x) {
        int jumps = 1, sum = 0;
        while (sum < x) {
            sum += jumps;
            jumps++;
        }

        int d = sum - x;
        // sum will either exceed or be equal
        if ((d & 1) == 0) {
            // as jumps has the value for next step at present moment, so we need to -1 for current one
            return jumps - 1;
        } else if (((sum + jumps - x) & 1) == 0) {
            // d was odd so we took a jump 
            // if this is even then present moment jump is the ans
            return jumps;
        } else {
            // need to take just one more jump as odd + odd = even
            return jumps + 1;
        }
    }

    // sieve of eratosthenes
    // useful to find prime numbers in a given range
    // in a specific range i apply many queries
    // as prime number can be solved in root n
    // to check if a number is prime or not we can do it in root n
    // for q queries and checking for ech number-> q root n
    // and we need to reduce it to o(1) for each number

    /*
    steps: given 'n' build an arr of n + 1 size;
    mark all true;
    iterate from i = 2 to root n
    if it comes out to be a factor then mark it false
    why above step -> because a prime number has a factor 1 and itself
    so if a number is made of 1 , itself or more factors then it is not prime
    */ 
    public static void seiveEra(int n) {
        boolean[] arr = new boolean[n + 1];
        Arrays.fill(arr, true);
        // to prevent this also
        // i < root n -> take sq -> i * i < n
        for (int i = 2; i * i < arr.length; i++) {
            if (arr[i]) {
                // make factors marked false as not prime
                for (int j = 2 * i; j < arr.length; j += i) {
                    arr[j] = false;
                    // arr[j] = true;
                }
            }
        }

        for (int i = 2; i < arr.length; i++) {
            if (arr[i]) System.out.print(i + " ");
        }
    }
    // this is the basic sieve, to produce in range segmented sieve is used

    // Segmented Sieve: in this range is given (a, b) all primes
    // In simple sieve just upper range is given
    // if (b - a == n) then finding primes till b can used but
    // constraints : a, b <= 10^9 : 10^9 array is not possible
    // b - a <= 10^5 - we will make arr of this size

    // make an arr of H - L such that any number in the range of L to H is foud by (idx + L)
    // generate primes from 2 to root H
    // how to get si of prime multiples
    // multiple = (L / prime[i]) ceil -> to get exact multiple eg: L = 22, prime = 7
    // 22 / 7 -> 3.(...) -> ceil = 4 now the index?
    // prime * multiple => 7 * 4 = 28, as our L sits on a value, so to bring it in range
    // idx = (p * m) - L = 28 - 22 = 6
    // marking using primes 
    // if m == 1, then m++ (then start value must be a prime and we are going to mark it not prime)
    // because our actual number is L + idx 
    // for ex: L = 2, H = 100 => primes = (2, 3, 5, 7) 
    // m for 2 : 2 / 2 = 1 -> ceil = 1 * 2 - 2 = 0 then marking this would mean that I am marking
    // primes number to not prime

    public static void segmentedSieve(int a, int b) {
        // make root of b
        int rootb = (int)(Math.sqrt(b));
        boolean[] arr = new boolean[b - a + 1];
        // get all primes till root b using sieve
        ArrayList<Integer> primes = sieve(rootb);
        // nlg(lgn)
        // marking primes factors
        for (int prime: primes) {
            // find starting index for marking
            int m = (int)(Math.ceil((a * 1.0) / prime));
            if (m == 1) m++;
            int idx = (m * prime) - a;

            for (int j = idx; j < arr.length; j += prime) {
                arr[j] = true; // not prime
            }
            // sigma of 1 / primes
            // n lg (lgn)
        }

        for (int i = 0; i < arr.length; i++) {
            if (!arr[i] && i + a != 1) {
                System.out.println(i + a);
            }
        }

        /*
        if n = 10 ^ 9 = 2 ^ 32
        lgn = 32 = x
        lgx = lg (2 ^ 32) = 5
        n (5) -> we are generating primes in o(n)
        */
    }

    public static ArrayList<Integer> sieve(int n) {
        boolean[] arr = new boolean[n + 1];
        // prime = false
        for (int i = 2; i * i < arr.length; i++) {
            // i is basically prime number
            if (!arr[i]) {
                for (int j = 2 * i; j < arr.length; j += i) {
                    arr[j] = true; // not prime
                }
            }
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 2; i < arr.length; i++) {
            if (!arr[i]) ans.add(i);
        }   
        return ans;
    }

    // Transpose Of Matrix With Dimension M x N , lc-867
    // for a n * n matrix we can do it inline
    // for n * m we need a new array m * n

    public static int[][] transpose_mn(int[][] arr) {
        int n = arr.length, m = arr[0].length;
        int[][] ans = new int[m][n];
        // for one column of arr1 I have to fill 1 row of ans
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                ans[j][i] = arr[i][j];
            }
        }
        return ans;
    }

    public static void transpose_nn(int[][] arr) {
        for (int g = 0; g < arr[0].length; g++) {
            for (int i = 0, j = g; j < arr[0].length; j++, i++) {
                int temp = arr[i][j];
                arr[i][j] = arr[j][i];
                arr[j][i] = temp;
            }
        }   
    }

    // rotate image
    public static void rotateImage(int[][] arr) {
        transpose_nn(arr);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0, k = arr[0].length -1; j < k; j++, k--) {
                int temp = arr[i][j];
                arr[i][j] = arr[i][k];
                arr[i][k] = temp;
            }
        }
    }

    // reverse vowels in a string
    public static String reverseVowels(String str) {
        char[] arr = str.toCharArray();
        int i = 0, j = arr.length - 1;
        while (i < j) {
            while (i < j && isConsonant(arr, i)) {
                i++;
                // if there will be no vowel then this loop alone will handle it
            }

            while (i < j && isConsonant(arr, j)) {
                j--;
            }

            char temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            // if there are no vowels then i == j and swapping will be of j with j
            i++; j--;
        }
        return String.valueOf(arr);
    }

    public static boolean isConsonant(char[] arr, int i) {
        return (arr[i] != 'A' && arr[i] != 'E' && arr[i] != 'I' && arr[i] != 'O' && arr[i] != 'U' && arr[i] != 'a' && arr[i] != 'e' && arr[i] != 'i' && arr[i] != 'o' && arr[i] != 'u');
    }

    // wiggle sort
    // 0 1 2 3 4 5 6 7 8 9
    // left <= odd[ele] >= right
    // left >= even[ele] <= right: 
    // we just need to improvise equalities on right side left side will be automatically done
    public static void wiggleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if ((i & 1) == 0 && arr[i] > arr[i + 1]) {
                swap(arr, i, i + 1);
            } else if ((i & 1) == 1 && arr[i] < arr[i + 1]) {
                swap(arr, i, i + 1);
            }
        }
    }

    // wiggle sort 2
    // kind of folding an array list given in a sorted order
    public static void wiggleSort_2(int[] a) {
        // sort the array
        Arrays.sort(a);
        // make an extra array to manage equality in it
        int[] arr = new int[a.length];
        int i = 1, j = arr.length - 1;
        while (i < arr.length) {
            arr[i] = a[j];
            // inserting all big values at odd indices from start
            i += 2;
            j--;
        }
        i = 0;
        while (i < arr.length) {
            arr[i] = a[j];
            i += 2;
            j--;
            // inserting all small values from end
        }
        for (int k = 0; k < arr.length; k++) a[k] = arr[k];
        // time: n, space: n
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // multiply strings
    // you can not use built in method to convert it into integer
    public static String multiply(String a, String b) {
        if (a.equals("0") || b.equals("0")) return "0";

        int[] res = new int[a.length() + b.length()];
        int i = b.length() - 1, pf = 0;
        while (i >= 0) {
            int ival = b.charAt(i) - '0';
            int j = a.length() - 1;
            int k = res.length - 1 - pf;
            int carry = 0;

            while (j >= 0 || carry != 0) {
                int jval = (j >= 0) ? a.charAt(j) - '0' : 0;
                int prod = (ival * jval) + carry + res[k];
                
                res[k] = prod % 10;
                carry = prod / 10;

                k--; j--;
            }
            pf++;
            i--;
        }
        // remove leading zeros (general way for addition, subtraction etc)
        // get a flag when 1st non zero encounter
        // iterate using that flag
        boolean flag = false;
        String ans = "";
        for (int val: res) {
            if (val == 0 && !flag) {
                // leading 0's case
                continue;
            } else {
                // 0 is in between
                flag = true;
                ans += val;
            }
        }
        return ans;
    }

    // pascal's triangle 2
    // given a row idx (0 based) print or return the pattern of that row
    // ex: i/p: 3, o/p: 1 3 3 1
    // icj * (i - j) / (j + 1) = i c j + 1;

    public static int pascalTr_2(int i) {
        ArrayList<Integer> ans = new ArrayList<>();
        int val = 1;
        for (int j = 0; j <= i; j++) {
           // first add the val
           ans.add(val);
           // now prepare for next j's val
           val = ((i - j) * val) / (j + 1);
        }
        return ans;
    }

    public static void printPascalTr(int R) {
        for (int i = 0; i <= R; i++) {
            int val = 1;
            for (int j = 0; j <= i; j++) {
                System.out.print(val + " ");
                val = ((i - j) * val) / (j + 1);
            }
            System.out.println();
        }
    }

    // valid pallindrome 2
    // Return true if the s can be palindrome after deleting at most one character from it
    public static boolean validPallindrome_2(String str) {
        int i = 0, j = str.length() - 1;
        while (i < j) {
            if (str.charAt(i) == str.charAt(j)) {
                i++; j--;
            } else {
                // skip one char from left or right
                return isPall(str, i + 1, j) || isPall(str, i, j - 1);
            }
        }
        // the str was alredy a pall
        return true;
    }

    public static boolean isPall(String s, int i, int j) {
        while (i < j) {
            if (s.charAt(i) == s.charAt(j)) {
                i++;
                j--;
            } else return false;
        }
        return true;
    }

    // Number Of Subarrays With Bounded Maximum, lc-795 
    // max of subarray must be - left <= max <= right
    public static int countSubArrays(int[] arr, int left, int right) {
        int count = 0, i = 0, j = 0, prev = 0;
        // left > ele or ele > right are break points
        // subarrays ending at an ele if it is range (i - j)
        while (i < arr.length) {
            int ele = arr[i];
            if (ele >= left && ele <= right) {
                prev = i - j + 1;
                count += prev;

            } else if (ele < left) {
                count += prev;

            } else if (ele > right) {
                // reset
                j = i + 1;
                // make prev = 0, as this point is a breakpoint
                prev = 0;
            }
            i++;
        }
        return count;
    }

    // maximum swap
    /** 
    1. You are given a number in form of String. 
    2. You can swap two digits at most once to get the maximum valued number in that string.
    3. Return the maximum valued number you can get in form of string.
    */

    // approaches
    // 1. put i on left and traverse j for values & find the max value to swap with - n ^ 2
    // 2. instead of finding max index or val again & again, make a right max arr and save all index for 
    // vals greater than it at the right. And traverse on right max arr if arr[i] < arr[rightmax[i]], then swap
    // 3. make a 10 size array for 0 - 9 and store all digits last occurance index, this will in o(1) space
    // and for every val find last index of value greater than that
    // for ex i is at ele = 4, then try for 9, 8, 7, 6, 5 if u get last index which is greater than i, 
    // then swap 
    // app 3 is best for "strings" and app-2 for "int"  
    public static String maxSwap(String str) {
        // prepare last index
        char arr[] = str.toCharArray();
        int[] li = new int[10];
        for (int i = 0; i < arr.length; i++) {
            li[arr[i] - '0'] = i;
        }

        // find suitable position for swapping
        boolean flag = false;
        for (int i = 0; i < arr.length; i++) {
            int digit = arr[i] - '0';
            for (int j = 9; j > digit; j--) {
                if (li[j] > i) {
                    // swapping and break
                    char temp = arr[i];
                    arr[i] = arr[li[j]];
                    arr[li[j]] = temp;
                    flag = true;
                    break;
                }
            }
            if (flag) break;
        }
        return String.valueOf(arr);
    }

    // add strings
    public static String addStrings(String s1, String s2) {
        String res = "";
        int i = s1.length() - 1, j = s2.length() - 1, carry = 0;
        
        while (i >= 0 || j >= 0 || carry != 0) {
            int ival = i >= 0 ? s1.charAt(i) - '0' : 0;
            int jval = j >= 0 ? s2.charAt(j) - '0' : 0;

            int sum = ival + jval + carry;
            res = (sum % 10) + res;
            carry = sum / 10;
            i--; j--;
        }
        return res;
    }

    // minimum arrows to burst balloons
    public static int minArrows(int[][] arr) {
        // why on the basis of ei -> such that we get same areas with same ei, or some areas which
        // come after or areas which have some common area
        Arrays.sort(arr, (t, o) -> {
            return t[1] - o[1];
        });

        // Arrays.sort(arr, (t, o) -> Integer.compare(t[1], o[1]));

        // we will be making a ref point such that when we hit this point all balloons with area commmon to 
        // this point will be burst all together 

        // if sp <= ep, one arrow is enough
        // if sp > ep -> then there is no common area so count++
        int count = 1, end = arr[0][1];
        // end will be the reference point
        for (int i = 1; i < arr.length; i++) {
            int[] curr = arr[i];
            if (curr[0] > end) {
                count++;
                // this is the moment when we change our ref point 
                end = curr[1];
            }
        } 
        return count;
    }

    // Minimum Domino Rotations For Equal Row
    // given 2 arrays find the minm rotations in which u can make one of those arrays same
    // 5 3 5 6 2 5 
    // 4 5 4 5 5 5 ans = 2;
    // we just need to make any number from dom1 or dom2 because if this is not possible then we can't make
    // same row with any number of swaps
    public static int dominoRot(int[] tops, int[] bottoms) {
        int num1 = tops[0], num2 = bottoms[0];
        // make num1 in top and bottoms
        int count1 = dominoHelper(tops, bottoms, num1);
        // make num2 in top and bottoms
        int count2 = dominoHelper(tops, bottoms, num2);
        int ans = Math.min(count1, count2); 
        return (ans == (int)(1e8)) ? -1 : ans;
        // time: n
    }

    public static int dominoHelper(int[] tops, int[] bottoms, int num) {
        int count1 = 0, count2 = 0, n = tops.length;
        // count1 = top, count2 = bottom
        for (int i = 0; i < n; i++) {
            if (count1 != (int)(1e8)) {
                if (tops[i] == num) {
                    // pass
                } else if (bottoms[i] == num) count1++;
                else count1 = (int)(1e8); 
                // now values can't be made same in both arrays
            }
            if (count2 != (int)(1e8)) {
                if (bottoms[i] == num) {
                    // pass
                } else if (tops[i] == num) count2++;
                else count2 = (int)(1e8);
                // now values can't be made same in both arrays
            }
        }
        return Math.min(count1, count2);
    }

    // push dominoes, lc - 838
    // add L at the left and R at the right
    // now this will create some segments
    // L ........ L
    // R ........ R
    // L ........ R
    // R ........ L
    public static String pushDominoes(String str) {
        int n = str.length();
        char arr[] = new char[n + 2];
        arr[0] = 'L'; arr[n + 1] = 'R';
        for (int i = 1; i < arr.length - 1; i++) {
            arr[i] = str.charAt(i - 1);
        }
        int j = 0, k = 1;
        while (k < arr.length) {
            while (arr[k] == '.') k++;

            if (k - j > 1) solveConfig(arr, j, k);

            j = k; k++;
        }
        // String ans = "";
        // for (int i = 1; i < arr.length - 1; i++) {
        //     ans += arr[i];
        //     // this creates a new String in pool leads to TLE
        // }
        StringBuilder ans = new StringBuilder();
        for (int i = 1; i < arr.length - 1; i++) ans.append(arr[i]);
        return ans.toString();
    }

    public static void solveConfig(char[] arr, int i, int j) {
        if (arr[i] = 'L' && arr[j] == 'L') {
            // make all dots 'L'
            for (int k = i + 1; k < j; k++) arr[k] = 'L';

        } else if (arr[i] == 'L' && arr[j] == 'R') {
            // do nothing as all are safe in between or equal force is there

        } else if (arr[i] == 'R' && arr[j] == 'R') {
            // make all R
            for (int k = i + 1; k < j; k++) arr[k] = 'R';

        } else if (arr[i] == 'R' && arr[j] == 'L') {
            int dots = j - i - 1;
            if ((dots & 1) == 0) {
                // even half L, half 
                int mid = (i + j) >> 1;
                for (int si = i + 1; si <= mid; si++) arr[si] = 'R';
                for (int si = mid + 1; si < j; si++) arr[si] = 'L';
            } else {
                // odd
                int mid = (i + j) >> 1;
                for (int si = i + 1; si < mid; si++) arr[si] = 'R';
                arr[mid] = '.';
                for (int si = mid + 1; si < j; si++) arr[si] = 'L';
            }
        }
    }

    // ****************************** TARGET SET *****************************

    // boats to save people
    public static int minBoats(int[] arr, int lim) {
        Arrays.sort(arr);
        int i = 0, j = arr.length - 1, count = 0;
        while (i <= j) {
            // why till i <= j because it can happen that i and j point to same person
            // and that person could be of w[i] > lim
            int sum = arr[i] + arr[j];
            if (sum <= lim) {
                i++; j--;
                count++;
                // curr pair is ready to go now try for the next pair
            } else {
                // allow the heavier person to sit and reduce j so that next person could be clubbed 
                // with someone else
                count++;
                j--;
            }
        }
        // time: nlgn
        return count;
    }

    // 2 Sum - Target Sum Unique Pairs 
    // arr can have dups too
    public static List<List<Integer>> twoSum(int[] arr, int k) {
        Arrays.sort(arr);
        List<List<Integer>> res = new ArrayList<>();

        int i = 0, j = arr.length - 1;
        while (i < j) {
            // unique pairs are wanted so i < j not <=
            int sum = arr[i] + arr[j];
            if (sum == k) {
                List<Integer> ans = new ArrayList<>();
                ans.add(arr[i]); ans.add(arr[j]);
                res.add(ans);
                i++; j--;
                // this is to prevent dups
                while (i < j && arr[i] == arr[i - 1]) i++;
                while (i < j && arr[j] == arr[j + 1]) j--;

            } else if (sum < k) i++;
            else j--;
        }
        return res;
    }

    // 3 Sum - Target Sum Unique Triplet
    public static List<List<Integer>> threeSum(int[] arr, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (arr.length <= 2) return res;    

        Arrays.sort(arr);
        for (int si = 0; si <= arr.length - 3; si++) {
            if (si != 0 && arr[si] == arr[si - 1]) continue;

            int new_k = k - arr[si];

            int i = si + 1, j = arr.length - 1;

            while (i < j) {
                int sum = arr[i] + arr[j];
                if (sum == new_k) {
                    List<Integer> ans = new ArrayList<>();
                    ans.add(arr[si]); ans.add(arr[i]); ans.add(arr[j]);
                    i++; j--;

                    res.add(ans);
                    
                    while (i < j && arr[i] == arr[i - 1]) i++;
                    while (i < j && arr[j] == arr[j + 1]) j--;

                } else if (sum < new_k) i++;
                else j--;
            }
        }
        //time: n^2 + nlgn
        return res;
    }

    // 4 Sum - Target Sum Unique Quadruplet
    public static List<List<Integer>> fourSum(int[] arr, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (arr.length < 4) return res;

        Arrays.sort(arr);

        for (int si = 0; si <= arr.length - 4; si++) {
            if (si != 0 && arr[si] == arr[si - 1]) continue;

            for (int i = si + 1; i <= arr.length - 3; i++) {
                // the range for this is si + 1
                if (i != si + 1 && arr[i] == arr[i - 1]) continue;
                int j = i + 1, ei = arr.length - 1;
                
                while (j < ei) {
                    int sum = arr[si] + arr[i] + arr[j] + arr[ei];
                    if (sum == k) {
                        List<Integer> ans = new ArrayList<>();
                        ans.add(arr[si]); ans.add(arr[i]); ans.add(arr[j]); ans.add(arr[ei]);
                        j++; ei--;

                        res.add(ans);

                        while (j < ei && arr[j] == arr[j - 1]) j++;
                        while (j < ei && arr[ei] == arr[ei + 1]) ei--;

                    } else if (sum < k) j++;
                    else ei--;
                }
            } 
        }
        return res;
    }

    // K Sum - Target Sum Unique Set
    /**
        You have to return an array of all the unique set [nums[a], nums[b], nums[c], nums[d] . . . K Elements] such that: 
        2.1 a, b, c, d . . . K Elements are less than 'n' and greater than 0.
        2.2 a,. b, c, d upto K different indexes are Unique.
        2.3 nums[a] + nums[b] + nums[c] + nums[d] + . . . + nums[K distinct indexes] == target.
     */
     // this is recursion as on lvl-1 : 1st ele selected remaining k - 1
     // on lvl 2 : 2 nd ele is selected then remaining k - 2 and so on
    public static List<List<Integer>> kSum(int[] arr, int tar, int k) {
        Arrays.sort(arr);
        return ksum_helper(arr, k, tar, 0);
    }

    public static List<List<Integer>> ksum_helper(int[] arr, int k, int tar, int si) {
        if (k == 2) {
            return twoSum_rec(arr, tar, si);
        }
        
        List<List<Integer>> res = new ArrayList<>();

        if (arr.length - si < k) return res;

        for (int i = si; i <= arr.length - k; i++) {
            if (i != si && arr[i] == arr[i - 1]) continue;

            int val = arr[i];
            List<List<Integer>> subRes = ksum_helper(arr, k - 1, tar - val, i + 1);
            for (List<Integer> l: subRes) {
                l.add(val);
                res.add(l);
            }
        }
        return res;
    }

    public static List<List<Integer>> twoSum_rec(int[] arr, int k, int si) {
        List<List<Integer>> res = new ArrayList<>();

        if (arr.length - si < 2) return res;

        int i = si, j = arr.length - 1;
        while (i < j) {
            int sum = arr[i] + arr[j];
            if (sum == k) {
                List<Integer> ans = new ArrayList<>();
                ans.add(arr[i]); ans.add(arr[j]);
                res.add(ans);

                i++; j--;

                while (i < j && arr[i] == arr[i - 1]) i++;
                while (i < j && arr[j] == arr[j + 1]) j--;
            } else if (sum < k) i++;
            else j--;
        }
        return res;
    }


    // minimum length of a string after removing similar ends
    // basically, till u have similar characters at both ends remove them 
    public static int minLengthString(String str) {
        if (str.length() == 0) return 0;
        int i = 0, j = str.length() - 1;
        while (i < j && str.charAt(i) == str.charAt(j)) {
            char ith = str.charAt(i);

            while (i < j && str.charAt(i) == ith) i++;
            while (i <= j && str.charAt(j) == ith) j--;
            // now j will run one extra eg: i = 3, j = 2, 2 - 3 + 1 = 0
        }
        return (j - i + 1);
    }

    // maximum average subarray, lc 643
    // SLIDING WINDOW MAXIMUM
    // as k was constant so ques reduced to find max sum of subarrays
    public static double maxAvgSubArray(int[] arr, int k) {
        if (arr.length < k) return 0.0;

        int max = -(int)(1e8), sum = 0;
        for (int i = 0; i < k; i++) sum += arr[i];

        max = Math.max(max, sum);

        for (int i = k; i < arr.length; i++) {
            sum += (arr[i] - arr[i - k]);
            max = Math.max(max, sum);
        }
        double ans = (max * 1.0) / (k);
        return ans;
    }

    // first missing positive
    // ap0 - n2
    // ap1 - sort, then check for 1st one if yes, then check further for arr[i + 1] = arr[i] + 1; nlgn
    // *************** idx mapping arr[idx - 1] = -Math.abs(arr[idx - 1]); *********************

    public static int firstPositiveInt(int[] arr) {
        // mark the ele out of range and manage presence of 1
        boolean isOne = false;
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int val = arr[i];
            if (val == 1) isOne = true;
            else if (val > n || val < 1) arr[i] = 1;
        }
        if (!isOne) return 1;
        // 1 <= ele <= n -> if any no. is not present then that will be ans
        // map elements with index
        for (int i = 0; i < n; i++) {
            int idx = Math.abs(arr[i]);
            arr[idx - 1] = -Math.abs(arr[idx - 1]);
        }
        // finding out the missing int
        for (int i = 0; i < n; i++) {
            // first positive number, then idx + 1 is missing
            if (arr[i] > 0) return i + 1;
        }
        // if all are present then n + 1 will be the ans
        return n + 1;
        // time: 3n = n
    } 

    // find all duplicates in an array
    // Each integer appears once or twice, return an array of all the integers that appears twice.
    // ****************** index mapping ************************
    public static List<Integer> findDups(int[] arr) {
        // find index and mark it's presence
        // if already marked that means dups is encountered
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int idx = Math.abs(arr[i]);
            // as the range in [1, n]
            if (arr[idx - 1] < 0) {
                ans.add(idx);
                continue;
            }
            arr[idx - 1] = -(Math.abs(arr[idx - 1]));
        }
        // time: n, space: o(1)
        return ans;
    }

    // multiplication of complex number
    // main problem will be to extract a, b, c, d and i
    // a + ib * c + id = (a * c - b * d) + (a * d + b * c)i;
    public static String complexMulti(String s1, String s2) {
        int idx_of_plus_s1 = s1.indexOf('+')
        int idx_of_plus_s2 = s2.indexOf('+');
        // at len - 1 = i will be present

        int a = Integer.parseInt(s1.substring(0, idx_of_plus_s1));
        int b = Integer.parseInt(s1.substring(idx_of_plus_s1 + 1, s1.length() - 1));
        int c = Integer.parseInt(s2.substring(0, idx_of_plus_s2));
        int d = Integer.parseInt(s2.substring(idx_of_plus_s2 + 1, s2.length() - 1));

        return "" + ((a * c) - (b * d)) + "+" + ((a * d) + (b * c)) + "i";
    }

    // Find and replace pattern
    public static List<String> findAndReplace(String[] words, String p) {
        List<String> ans = new ArrayList<>();
        for (String w: words) {
            if (isMatching(w, p)) ans.add(w);
        }
        return ans;
    }

    public static boolean isMatching(String w, String p) {
        HashMap<Character, Character> map = new HashMap<>();
        HashMap<Character, Boolean> vis = new HashMap<>();
        // you can use hashset for this too..
        // p vs w
        for (int i = 0; i < p.length(); i++) {
            char w_ch = w.charAt(i);
            char p_ch = p.charAt(i);

            if (map.containsKey(p_ch)) {
                // if pch is already mapped then check
                // if present char and mapped ch are same
                if (map.get(p_ch) != w_ch) return false;
                 
            } else {
                // check for birectional mapping, if the w_ch is visited then return false
                if (vis.getOrDefault(w_ch, false)) return false;
                map.put(p_ch, w_ch);
                vis.put(w_ch, true);
            }
        }
        return true;
    }

    // given n
    // Return the number of ways you can write n as the sum of consecutive positive integers.
    // 15 -> 15, 1 + 2 + 3 + 4 + 5,  7 + 8, 4 + 5 + 6
    public static int consecutiveNumbersWays(int n) {
        int count = 0;
        for (int k = 1; 2 * n > k * (k - 1); k++) {
            int nr = n - (k * (k - 1) / 2);
            if (nr % k == 0) count++;
        }
        return count;
    }

    // Given an integer array 'nums' sorted in non-decreasing order.
    // Return an array of the squares of each number sorted in non-decreasing order.

    // ap1: make squares then sort that but time: n + nlgn
    // allowed o(n)

    public static int[] squaresOfSortedArr(int[] arr) {
        int i = 0, j = arr.length - 1, k = arr.length - 1;
        int[] ans = new int[arr.length];

        while (i <= j) {
            int ith = arr[i] * arr[i], jth = arr[j] * arr[j];

            if (jth > ith) {
                ans[k] = jth;
                j--;
            } else {
                ans[k] = ith;
                i++;
            }
            k--;
        }
        return ans;
    }
    

    public static void solve() {
        
    }

    public static void main(String args[]) {
        solve();
    }
}