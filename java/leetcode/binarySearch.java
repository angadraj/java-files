class binarySearch {
    // avoid flood in the city
    public int[] avoidFlood(int[] arr) {
        int n = arr.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        // TreeSet<Integer> bst = new TreeSet<>();
        List<Integer> bst = new ArrayList<>();
            
        int[] ans = new int[n];
        
        for (int i = 0; i < n; i++) {
            
            if (arr[i] == 0) {
                bst.add(i);
                ans[i] = 1;
                
            } else {
                // check if this causes floor or not
                int lastIdx = map.getOrDefault(arr[i], -1);
                
                if (lastIdx == -1) {
                    map.put(arr[i], i);
                    ans[i] = -1;
                    
                } else {                    
                    // find if we can dry this out
                    // Integer dryIdx = bst.higher(lastIdx);
                    Integer dryIdx = binarySearch(bst, lastIdx);
                    if (dryIdx == null) {
                        // flood !!!
                        return new int[0];
                    } 
                    ans[dryIdx] = arr[i];
                    bst.remove(dryIdx);
                    map.put(arr[i], i);
                    ans[i] = -1;
                }
            }
        }
        return ans;
    }
    
    public Integer binarySearch(List<Integer> arr, int k) {
        // we need to find ceil of k
        if (arr.size() == 0) return null;
        int si = 0, ei = arr.size();
        while (si < ei) {
            int mid = (si + ei) >> 1;
            if (arr.get(mid) > k) {
                ei = mid;
            } else {
                si = mid + 1;
            }
        }
        if (si == arr.size()) return null;
        else if (si == 0) {
            if (arr.get(si) <= k) return null;
        }
        return Integer.valueOf(arr.get(si));
    }

    // ugly number 3
    public int nthUglyNumber(int n, int a, int b, int c) {
        long si = 1, ei = Long.MAX_VALUE;
        
        while (si < ei) {
            long mid = (si + ei) >> 1;
            
            if (isUgly(n, a, b, c, mid)) {
                // this is ugly, try to find more at left 
                ei = mid;
            } else {
                si = mid + 1;
            }
        }
        return (int)(si);
    }
    
    public boolean isUgly(long n, long a, long b, long c, long mid) {
        return ((mid / a)
                    + (mid / b)
                    + (mid / c)
                    - (mid / lcm(a, b))
                    - (mid / lcm(b, c))
                    - (mid / lcm(c, a))
                    + (mid / lcm(a, lcm(b, c)))) >= n;
    }
    
    public long gcd(long a, long b) {
        if (a == 0) {
            return b;
        } 
        return gcd(b % a, a);
    }
    
    public long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    // minimum absolute sum difference
    int mod = (int)(1e9) + 7;
    
    public int minAbsoluteSumDiff(int[] A, int[] B) {
        int n = A.length;
        TreeSet<Integer> bst = new TreeSet<>();
        
        for (int val: A) bst.add(val);
        
        long sum = 0, maxDec = 0;
        for (int i = 0; i < n; i++) {
            
            Integer ceil = bst.ceiling(B[i]), floor = bst.floor(B[i]);
            
            long diff = Math.abs(A[i] - B[i]);
            
            sum += diff;
            
            if (ceil != null) {
                maxDec = Math.max(maxDec, diff - Math.abs(B[i] - ceil));
            }
            if (floor != null) {
                maxDec = Math.max(maxDec, diff - Math.abs(B[i] - floor));
            }
        }
        
        sum = (sum - maxDec) % mod;
        return (int)sum;
    }

    // min time to complete trips
    public long helper(int[] arr, int k) {
        int max = arr[0];
        for (int val: arr) {
            max = Math.max(val, max);
        }
        
        long si = 1, ei = (long)(1e14), mid = 0, ans = 0;
        while (si <= ei) {
            // mid shows the time we can consider that will result in total trips can be completed in 
            mid = (si + ei) >> 1;
            if (canMinimize(arr, k, mid)) {
                ei = mid - 1;
                ans = mid;
            } else {
                si = mid + 1;
            }
        }
        return ans;
    }
    
    public boolean canMinimize(int[] arr, int k, long mid) {
        long sum = 0;
        for (int val: arr) {
            sum += (mid / val);
        }
        if (sum >= k) return true;
        else return false;
    }

    // max value at a given index in a bounded array
    public int maxValue(int n, int index, int maxSum) {
        long si = 1, ei = maxSum;
        long rhs = n - index - 1, lhs = index, ans = -1;
        
        while (si <= ei) {
            long mid = (si + ei) >> 1;
            
            long ls = 0, rs = 0, m = mid - 1;
            
            // for rhs
            if (m >= rhs) {
                rs = (m * (m + 1)) / 2;
                long leftOverEle = m - rhs;
                rs -= (leftOverEle * (leftOverEle + 1)) / 2;
                
            } else {
                rs = (m * (m + 1)) / 2;
                long leftOverEleSum = 1 * (rhs - m);
                rs += leftOverEleSum;
            }
            
            // for lhs
            if (m >= lhs) {
                ls = (m * (m + 1)) / 2;
                long leftOverEle = m - lhs;
                ls -= (leftOverEle * (leftOverEle + 1)) / 2;
                
            } else {
                ls = (m * (m + 1)) / 2;
                long leftOverEleSum = 1 * (lhs - m);
                ls += leftOverEleSum;
            }
            
            long sum = ls + mid + rs;
            if (sum > maxSum) {
                ei = mid - 1;
            } else {
                si = mid + 1;
                ans = mid;
            }
        }
        return (int)(ans);
    }

    // count number of rect containing each point
    public int[] countRectangles(int[][] r, int[][] p) {
        int n = r.length, m = p.length;
        
        ArrayList<Integer>[] arr = new ArrayList[101];
        
        for (int i = 0; i < r.length; i++) {
            int l = r[i][0], h = r[i][1];
            if (arr[h] == null) {
                arr[h] = new ArrayList<>();
            }
            arr[h].add(l);
        }
        
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                Collections.sort(arr[i]);
            }
        }
        
        int[] ans = new int[m];
        
        for (int i = 0; i < m; i++) {
            int x = p[i][0], y = p[i][1], count = 0;
            // we need to search y in arr and then we will check L in arr[y]
            for (int j = y; j < arr.length; j++) {
                if (arr[j] == null) continue;
                int idx = lowerBound(arr[j], x);
                count += (arr[j].size() - idx);
            }
            
            ans[i] = count;
        }
        
        return ans;
    }
    
    public int lowerBound(ArrayList<Integer> arr, int k) {
        int si = 0, ei = arr.size();
        while (si < ei) {
            int mid = (si + ei) >> 1;
            if (k > arr.get(mid)) {
                si = mid + 1;
            } else {
                ei = mid;
            }
        }
        return si;
    }

    // ways to split array into 3 subarrays
    public int waysToSplit(int[] arr) {
        int count = 0, n = arr.length;
        
        for (int i = 1; i < n; i++) {
            arr[i] += arr[i - 1];
        }
        
        for (int i = 1; i < n - 1; i++) {
            // we will find the range of mid sum from [i, n - 2]
            int leftIdx = bs(arr, arr[i - 1], i, true);
            int rightIdx = bs(arr, arr[i - 1], i, false);
            
            if (leftIdx == -1 || rightIdx == -1) continue;
            
            count += (rightIdx - leftIdx + 1);
            count %= mod;
        }
        
        return count;
    }
    
    // to left tells if we have to explore left or right
    public int bs(int[] arr, int leftSum, int i, boolean toLeft) {
        int si = i, n = arr.length, ei = n - 1, ans = -1;
        
        while (si < ei) {
            int mid = (si + ei) >> 1;
            int midSum = arr[mid] - arr[i - 1];
            int rightSum = arr[n - 1] - arr[mid];
            
            if (leftSum <= midSum && midSum <= rightSum) {
                ans = mid;
                if (toLeft) {
                    ei = mid;
                } else {
                    si = mid + 1;
                }
            } else if (midSum < leftSum) {
                // expand mid
                si = mid + 1;
            } else {
                ei = mid;
            }
        }
        
        return ans;
    }

    // 132 pattern
    public boolean find132pattern_2(int[] arr) {
        ArrayDeque<Integer> st = new ArrayDeque<>();
        
        long smin = -(long)(1e10);
        
        int n = arr.length;
        for (int i = n - 1; i >= 0; i--) {
            if (arr[i] < smin && arr[i] < st.peek()) return true;
            
            while (st.size() > 0 && arr[i] > st.peek()) {
                smin = Math.max(smin, st.pop());
            }
            
            st.push(arr[i]);
        }
        return false;
    }

    // nth digit
    public int findNthDigit(int n) {
        int digit = 1;
        long count = 9;
        
        while (n - (count * digit) > 0) {
            n -= (count * digit);
            digit++;
            count *= 10;
        }
        
        int baseNum = (int)(Math.pow(10, digit - 1));
        int number = (n - 1) / digit + baseNum;
        int mod = (n - 1) % digit;
        return String.valueOf(number).charAt(mod) - '0'; 
    }

    // sum of square numbers: a^2 + b^2 = c, given c
    public boolean judgeSquareSum(int c) {
        // a^2 + b^2 = c 
        // we will fix a or b to maximum closest value to c
        
        long a = 0, b = (long)(Math.sqrt(c));
        while (a <= b) {
            long sum = (a * a) + (b * b);
            if (sum == c) return true;
            else if (sum < c) a++;
            else b--;
        }
        return false;
    }

    // maximum candles allocated to k children
    public int maximumCandies(int[] arr, long k) {
        int max = 0;
        
        int si = 1, ei = (int)(1e8);
        while (si < ei) {
            int mid = (si + ei) >> 1;
            if (predicate(arr, k, mid)) {
                max = mid;
                si = mid + 1;
            } else {
                ei = mid;
            }
        }
        
        return max;
    }
    
    public boolean predicate(int[] arr, long k, int mid) {
        long sum = 0;
        for (int val: arr) {
            sum += (val / mid);
        }
        
        return (sum >= k);
    }

    // heaters
    public int findRadius(int[] houses, int[] heaters) {
        int n = houses.length, m = heaters.length;
        
        Arrays.sort(heaters);
        
        int ans = -(int)(1e8);
        
        for (int val: houses) {
            int[] cf = ceilFloor(heaters, val);
            // ceil @ 0 & floor @ 1
            int ceil = cf[0], floor = cf[1];
            int d1 = Math.abs(ceil - val), d2 = Math.abs(floor - val);
            
            ans = Math.max(ans, Math.min(d1, d2));
        }
        return ans;
    }
    
    // ans[0] = ceil, ans[1] = floor
    public int[] ceilFloor(int[] arr, int k) {
        int n = arr.length;
        int[] ans = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        int si = 0, ei = n;
        
        while (si < ei) {
            int mid = (si + ei) >> 1;
            if (k == arr[mid]) {
                ans[0] = ans[1] = k;
                return ans;
                
            } else if (k < arr[mid]) {
                ans[0] = arr[mid];
                ei = mid;
            } else {
                ans[1] = arr[mid];
                si = mid + 1;
            }
        }
        return ans;
    }

    // min speed to arrive on time
    public int minSpeedOnTime(int[] dist, double hour) {
        int si = 0, ei = (int)(1e8), ans = -1;
        
        while (si < ei) {
            int mid = (si + ei) >> 1;
            if (predicate(dist, hour, mid)) {
                ei = mid;
                ans = mid;
            } else {
                si = mid + 1;
            }
        }
        
        return ans;
    }
    // ************* IMPORTANT LOGIC TO FIND CEIL ******************** //
    
    public boolean predicate(int[] arr, double k, int mid) {
        double sum = 0;
        for (int val: arr) {
            // why ceil, so that if a train time ends up in 2.5 then we can wait till 0.5 hrs
            // and depart with other train.
            if (sum > (int)(sum)) {
                sum = (double)(((int)(sum) + 1));
                // sum = Math.ceil(sum); // costly operation
            }
            sum += (double)(val) / (double)(mid);
        }
        
        return (sum <= k);
    }

    // Range freq query
    class RangeFreqQuery {
        HashMap<Integer, ArrayList<Integer>> map;
        
        public RangeFreqQuery(int[] arr) {
            map = new HashMap<>();
            
            for (int i = 0; i < arr.length; i++) {
                if (map.containsKey(arr[i]) == false) {
                    map.put(arr[i], new ArrayList<>());
                }
                map.get(arr[i]).add(i);
            }
        }
    
        public int query(int L, int R, int ele) {
            
            if (!map.containsKey(ele)) {
                return 0;
            }
            
            ArrayList<Integer> list = map.get(ele);
            
            int lb = lowerbound(list, L);
            int ub = upperbound(list, R);
            
            // System.out.println(lb + ", " + ub);
            return ub - lb;
        }
    
        public int lowerbound(ArrayList<Integer> arr, int k) {
            int si = 0, ei = arr.size(), mid = -1;
            while (si < ei) {
                mid = (si + ei) >> 1;
                int ele = arr.get(mid);
                
                if (k <= ele) {
                    // i want to consider that ele because it can be single as well
                    ei = mid;
                } else {
                    si = mid + 1;
                    // if the element is bigger then it must be inserted after the current ele
                }
            }
            return si;
        }
    
        public int upperbound(ArrayList<Integer> arr, int k) {
            int si = 0, ei = arr.size(), mid = -1;
            while (si < ei) {
                mid = (si + ei) >> 1;
                int ele = arr.get(mid);
                
                if (k >= ele) {
                    // I want next bigger element than the ele
                    // i don't want k == ele , that is why i neglected mid ele also and moved to mid+1
                    si = mid + 1;
                } else {
                    ei = mid;
                    // if the ele is smaller then search on left
                }
            }
            return si;
        }
    }
}