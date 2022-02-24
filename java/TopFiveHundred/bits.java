import java.util.*;
import java.io.*;
class bits {
    
    // find first 2 non repeating elements in array in o(n) time, o(1) space
    public static int[] first2NonRep(int[] arr) {
        // 1. find xor of all elements
        // 2. now set bit in the ans tells that the 2 unique ele have different bits at that position.
        // therefore on the basis of that bit we will divide the array into 2 sets
        int allXor = 0;
        for (int val: arr) {
            allXor ^= val;
        }
        // find the last set bit
        int mask = (allXor & -allXor);
        int ele1 = 0, ele2 = 0;
        for (int val: arr) {
            if ((val & mask) == 0) {
                ele1 ^= val;
            } else {
                ele2 ^= val;
            }
        }
        if (ele1 > ele2) {
            int temp = ele1;
            ele1 = ele2;
            ele2 = temp;
        }   
        return new int[]{ele1, ele2};
    }

    // count set bits in an int
    public static int countSetBits(int n) {
        int count = 0;
        while (n > 0) {
            count++;
            n = (n & (n - 1));
        }
        return count;
    }

    // Find the Number Occurring Odd Number of Times
    public static int firstRep(int[] arr) {
        int ans = 0;
        for (int val: arr) {
            ans ^= val;
        }
        return ans;
    }

    // Check if a number is Bleak
    // 
    public static boolean isBleak(int n) {
        int si = n - cielLog2(n);
        for (int i = si; i < n; i++) {
            boolean ans = isBleakHelper(i, n);
            if (ans) return false;
        }
        return true;
    }

    public static int cielLog2(int n) {
        int count = 0;
        n -= 1;
        while (n > 0) {
            n = (n >> 1);
            count++;
        }
        return count;
    }
    
    public static boolean isBleakHelper(int i, int n) {
        int copy = i, count = 0;
        while (copy > 0) {
            count++;
            copy = (copy & (copy - 1));
        }   
        if (i + count == n) return true;
        return false;
    }

    // Copy set bits in a range
    // The task is consider set bits of y in range [l, r] and set these bits in x also.
    public static void copySetBitsInRange(int x, int y, int l, int r) {
        // range is given from index 1 
        // so reduce it
        l -= 1; r -= 1;
        for (int i = l; i <= r; i++) {
            int mask = (1 << i);
            // find if the ith bit is set in y
            if ((y & mask) != 0) {
                x |= mask;
            }
        }
        System.out.println(x);
    }

    // given binary number (string), check if it is divisible by 3
    // check if the difference of set bits at odd & event pos, is divisible by 3 or not
    public static boolean divBy3(String s) {
        int odd = 0, even = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if ((i & 1) == 0 && ch == '1') {
                even++;
            } else if ((i & 1) != 0 && ch == '1') {
                odd++;
            }
        }
        int diff = Math.abs(even - odd);
        if (diff % 3 == 0) return true;
        else return false;
    }

    // Find position of the only set bit
    public static int findSetBitPos(int n) {
        int count = 0, copy = n;
        while (copy > 0) {
            count++;
            copy = (copy & (copy - 1));
        }
        if (count > 1) return -1;
        // for (int i = 0; i < 32; i++) {
        //     int mask = (1 << i);
        //     if ((n & mask) != 0) {
        //         return (i + 1);
        //     }
        // }
        int idx = 0;
        while (n > 0) {
            idx++;
            if ((n & 1) != 0) return idx;
            n = (n >> 1);
        }
        return -1;
    }

    // Binary representation of a given number
    public static StringBuilder decimalToBinary(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            int rem = n % 2;
            sb.append(rem);
            n = n / 2;
        }
        sb = sb.reverse();
        StringBuilder zeros = new StringBuilder();
        int zerosToAppend = 31 - sb.length();
        for (int i = 0; i < zerosToAppend; i++) {
            zeros.append("0");
        }
        zeros.append(sb);
        return zeros;
    }

    // Count number of bits to be flipped to convert A to B
    // a -> b
    public static int countBitsToFlip(int a, int b) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            int mask = (1 << i);
            int a_bit = (mask & a);
            int b_bit = (mask & b);
            if ((a_bit ^ b_bit) != 0) count++;
        }
        return count;
    }

    // count total setbits from 1 to n
    public static int totalSetBits(int n) {
        int count = 0;
        for (int i = 1; i <= n; i++) {
            int copy = i;
            while (copy > 0) {
                count++;
                copy = (copy & (copy - 1));
            }
        }
        return count;
    }

    public static int totalSetBits_2(int n) {
        if (n == 0) return 0;
        // set bits in 0 is 0;
        // find biggest power till 2
        int x = biggestPowerof2(n);
        int bitsUpto2RaiseTox = x * (1 << (x - 1));
        int bitsAfter2RaiseTox = n - (1 << x) + 1;
        int remaining = totalSetBits_2(n - (1 << x));
        return bitsUpto2RaiseTox + bitsAfter2RaiseTox + remaining;

    }

    public static int biggestPowerof2(int n) {
        int count = 0;
        while ((1 << count) <= n) {
            count++;
        }
        return count - 1;
    }

    // Swap two nibbles in a byte
    // n = [0, 255]
    public static int swapTwoNibbles(int n) {
         // 1. segregate left and right nibbles
        int left = (n & 0x0F);
        int right = (n & 0xF0);
        // 2. right shift left part by 4 and right to left by 4
        left = (left << 4);
        right = (right >> 4);
        // 3. now take OR of left and right
        int ans = (left | right);
        return ans;
    }

    // Find the element that appears once
    public static int elementAppearsOnce(int[] arr) {
        int t0 = Integer.MAX_VALUE, t1 = 0, t2 = 0;
        for (int val: arr) {
            int common_t0 = (val & t0);
            int common_t1 = (val & t1);
            int common_t2 = (val & t2);

            t0 = (t0 & (~common_t0));
            t1 = (t1 | common_t0);

            t1 = (t1 & (~common_t1));
            t2 = (t2 | common_t1);

            t2 = (t2 & (~common_t2));
            t0 = (t0 | common_t2);
        }
        return t1;
    }

    // Program to find whether a given number is power of 2
    // logN
    public static boolean isPowOf2(int n) {
        if (n == 0) return false;
        while (n > 1) {
            // check if the number is even
            if (n % 2 != 0) {
                return false;
            }
            n = n / 2;
        }
        return true;
    }

    // Sum of bit differences among all pairs
    // find the total set or unset bits at ith pos (0, 31)
    public static int sumOfBitDiff(int[] arr) {
        int ans = 0;
        int n = arr.length;
        for (int i = 0; i < 32; i++) {
            int m = (1 << i);
            int count = 0;
            for (int j = 0; j < n; j++) {
                if ((arr[j] & m) != 0) {
                    count++;
                }
            }
            // total combinations formed
            // (count C 1) * (n - count C 1) 
            // 1100 -> 10, 10, 10, 10 and 01, 01, 01, 01
            // and this can be done in two ways
            ans += (count * (n - count) * 2);
        }
        return ans;
    }

    // generate n-bit gray codes
    public static void printGrayCode(int n) {
        ArrayList<String> ans = printGrayCode_rec(n);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < ans.size(); i++) {
            String val = ans.get(i);
            list.add(Integer.parseInt(val, 2));
        }
        System.out.println(ans);
        System.out.println(list);
    }

    public static ArrayList<String> printGrayCode_rec(int n) {
        if (n == 1) {
            ArrayList<String> baseAns = new ArrayList<>();
            baseAns.add("0");
            baseAns.add("1");
            return baseAns;
        }
        ArrayList<String> recAns = printGrayCode_rec(n - 1);
        ArrayList<String> self = new ArrayList<>();
        for (String val: recAns) {
            self.add("0" + val);
        }
        for (int i = recAns.size() - 1; i >= 0; i--) {
            self.add("1" + recAns.get(i));
        }
        return self;
    }

    // josephus
    public static int josephus(int n, int k) {
        if (n == 1) return 0;
        int x = josephus(n - 1, k);
        int y = (x + k) % n;
        return y;
    }

    // gfg (1 to n) 
    public static int josephus2(int n, int k) {
        if (n == 1) return 1;
        int x = josephus2(n - 1, k);
        int y = (x + k - 1) % n + 1;
        return y;
    }

    // make both elements 0
    public static void make0(int[] arr) {
        arr[arr[1]] = arr[1 - arr[1]];
    }

    // Swap all odd and even bits

    // find 7n / 8 without multiplication or divide
    public static void find7n8(int n) {
        /// n - n/8 = 7n/8
        int ans = n - (1 >> 3);
        System.out.println(ans);
    }

    public static void solve() {
        printGrayCode(3);
        // System.out.println(ans);
    }
    
    public static void main(String[] args) {
        solve();
    }
}