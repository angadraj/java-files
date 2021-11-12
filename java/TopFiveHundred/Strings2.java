import java.util.*;

class Strings2 {
    
    // Check a given sentence for a given set of simple grammar rules
    public static boolean checkSentence(String s) {
        if (s.length() == 0) return true;

        char[] arr = s.toCharArray();
        int n = arr.length;
        if (arr[0] < 'A' || arr[n - 1] > 'Z') return false;
        if (arr[n - 1] != '.') return false;
        
        int cs = 0, ps = 0, i = 1;
        while (i < n) {
            if (arr[i] >= 'A' && arr[i] <= 'Z') cs = 0;
            else if (arr[i] == ' ') cs = 1;
            else if (arr[i] >= 'a' && arr[i] <= 'z') cs = 2;
            else if (arr[i] == '.') cs = 3;
            // case 1
            // AA(00), ..(33), __(11), aa(22)
            if (cs == ps && cs != 2) return false;
            // case 2
            // I Am Ram. -> Am -> cs at 'm' = 2, ps at 'A' = 0
            if (cs == 0 && ps == 2) return false;
            // case 3 
            // ., Ram., I am . Ram
            // cs at . = 3 and i + 1 is len as '.' should appear at the end
            // for valid cs = 3, ps != 1 why? 1 = space and space is not allowed 
            // before '.'
            if (cs == 3 && ps != 1) {
                return (i + 1 == n);
            }
            i++;
            ps = cs;
        }
        return true;
    }

    // Length of the longest valid substring
    // using stack
    public static int longestValidSubstring(String s) {
        Stack<Integer> st = new Stack<>();
        st.push(-1);
        int len = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') st.push(i);
            else {
                if (st.size() != 0) st.pop();
                if (st.size() != 0) {
                    len = Math.max(len, i - st.peek());
                } else {
                    st.push(i);
                }
            }
        }
        return len;
    }

    // in o(1) space
    public static int longestValidSubstring2(String s) {
        int l = 0, r = 0, len = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') l++;
            else r++;

            if (l == r) len = Math.max(len, 2 * l);
            else if (r > l) l = r = 0;
        }   
        l = r = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (ch == '(') l++;
            else r++;

            if (l == r) len = Math.max(len, 2 * l);
            else if (l > r) l = r = 0;
        }   
        return len;
    }

    // Print all ways to break a string in bracket form
    public static void printAllBracketForms(String s) {
        ArrayList<String> ans = new ArrayList<>();
        printAllBracketFormsRec(s, ans);
    }

    public static void printAllBracketFormsRec(String s, ArrayList<String> ans) {
        if (s.length() == 0) {
            System.out.println(ans);
            return;
        }

        // char ch = s.charAt(0);
        // ans.add("(" + ch + ")");
        // printAllBracketFormsRec(s.substring(1), ans);
        // ans.remove(ans.size() - 1);
        
        for (int i = 1; i <= s.length(); i++) {
            ans.add("(" + s.substring(0, i) + ")");
            printAllBracketFormsRec(s.substring(i), ans);
            ans.remove(ans.size() - 1);
        }
    }

    // concatenation of zig zag string in n rows
    // time: n, space n
    public static String convert(String s, int n) {
        if (n == 1) return s;
        String[] arr = new String[n];
        Arrays.fill(arr, "");
        int row = 0;
        boolean down = true;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            arr[row] += ch;
            if (row == 0) down = true;
            else if (row == n - 1) down = false;

            if (down) row++;
            else row--;
        }
        StringBuilder sb = new StringBuilder();
        for (String val: arr) {
            sb.append(val);
        }
        return sb.toString();
    }

    public static String convert2(String s, int n) {
        if (n == 1) return s;
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < n; r++) {
            int i = r;
            boolean up = true;

            while (i < s.length()) {
                sb.append(s.charAt(i));

                if (r == 0 || r == n - 1) {
                    i += (2 * n - 2);
                } else {
                    if (up) {
                        i += (2 * (n - r) - 2);
                    } else {
                        i += (r * 2);
                    }
                    up ^= true;
                }
            }
        }
        return sb.toString();
    }

    // kmp
    public static void kmp(String s, String p) {
        String st = p + "#" + s;
        int[] arr = lps(st);
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            int val = arr[i];
            if (val == p.length()) {
                count++;
                int idx = i - (2 * p.length());
                System.out.println("found @ " + idx);
            }
        }
    }

    public static int[] lps(String s) {
        int n = s.length();
        int[] arr = new int[n];
        int i = 1, len = 0;
        while (i < n) {
            char ch = s.charAt(i);
            if (ch == s.charAt(len)) {
                len++;
                arr[i] = len;
                i++;
            } else {
                if (len > 0) {
                    len = arr[len - 1];
                } else {
                    arr[i] = 0;
                    i++;
                }
            }
        }
        // the length of longest value which is proper prefix and suffix will be at arr[len - 1]
        return arr;
    }

    // minm operations to make A to B
    // operation: you can pick a char from A and add it to front
    public static int minOperations(String a, String b) {
        // let's authenticate
        if (a.length() != b.length()) return -1;

        int[] arr = new int[256];
        for (int i = 0; i < a.length(); i++) {
            arr[a.charAt(i)]++;
            arr[b.charAt(i)]--;
        }
        for (int val: arr) if (val != 0) return -1;
        int i = a.length() - 1, j = b.length() - 1, count = 0;
        while (i >= 0) {
            if (a.charAt(i) != b.charAt(j)) {
                count++;
            } else {
                j--;
            }
            i--;
        }
        return count;
    }

    // longest repeating subseq
    public static int longestRepSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n + 1][n + 1];
        for (int i = dp.length - 2; i >= 0; i--) {
            for (int j = dp[0].length - 2; j >= 0; j--) {
                char ith = s.charAt(i), jth = s.charAt(j);
                if (ith == jth && i != j) {
                    dp[i][j] = 1 + dp[i + 1][j + 1];
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        return dp[0][0];
    }
    // not working
    public static boolean longestRepSubseq2(String s) {
        int[] farr = new int[256];
        for (int i = 0; i < s.length(); i++) {
            farr[s.charAt(i)]++;
            if (farr[s.charAt(i)] > 2) return true;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (farr[s.charAt(i)] == 1) {
                sb.append(s.charAt(i));
            }
        }
        String ans = sb.toString();
        int len = ans.length();
        if (isValidPall(ans)) {
            if ((len & 1) == 1) {
                int mid = (len >> 1);
                if (mid >= 1) return (ans.charAt(mid) == ans.charAt(mid - 1));
            }
            return false;
        }
        return true;
    }

    public static boolean isValidPall(String s) {
        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) return false;
        }
        return true;
    }

    // isomorphic strings
    // mapping of s1 with s2 should be consistent
    public static boolean isIsomorphic(String a, String b) {
        if (a.length() != b.length()) return false;
        HashMap<Character, Boolean> map1 = new HashMap<>();
        HashMap<Character, Character> map2 = new HashMap<>();
        // a, b
        for (int i = 0; i < a.length(); i++) {
            char ath = a.charAt(i), bth = b.charAt(i);
            if (map2.containsKey(ath)) {
                if (bth != map2.get(ath)) return false;
            } else if (map1.containsKey(bth) && map1.get(bth)) {
                return false;
            }
            map1.put(bth, true);
            map2.put(ath, bth);
        }
        return true;
    }

    // longest common substring 
    public static int longestCommonSubString(String a, String b) {
        int n = a.length(), m = b.length();
        int[][] dp = new int[n + 1][m + 1];
        int max = 0;
        for (int i = dp.length - 2; i >= 0; i--) {
            for (int j = dp[0].length - 2; j >= 0; j--) {
                char ath = a.charAt(i), bth = b.charAt(j);
                if (ath == bth) {
                    dp[i][j] = dp[i + 1][j + 1] + 1;
                } else {
                    dp[i][j] = 0;
                }
                max = Math.max(max, dp[i][j]);
            }
        }
        return max;
    }

    // Find if a given string can be represented from a substring by iterating the substring “n” times
    public static boolean makeStringAppendingSubstring(String a) {
        int[] arr = lps(a);
        int len = arr[arr.length - 1], n = a.length();
        if (len > 0 && (n % (n - len) == 0)) return true;
        else return false;
    }

    // make possible sentences from given set of words arr
    public static void makeSentences(String[][] arr) {
        int n = arr.length, m = arr[0].length;
        ArrayList<String> ans = new ArrayList<>();
        // StringBuilder ans = new StringBuilder();
        makeSentences_rec(arr, ans, 0);
    }

    public static void makeSentences_rec(String[][] arr, ArrayList<String> ans, int row) {
        if (row == arr.length) {
            System.out.println(ans);
            return;
        }
        for (String a: arr[row]) {
            ans.add(a);
            // int temp = a.length();
            // ans.append(a + " ");
            makeSentences_rec(arr, ans, row + 1);
            ans.remove(ans.size() - 1);
            // ans.delete(ans.length() - temp - 1, ans.length());
        }
    }

    // String polynomial hashing
    public static long hashCode(String s) {
        long pr = 31, mod = (int)(1e9 + 7);
        long pow = 31;
        long ans = s.charAt(0) - 'a' + 1;
        // pr ^ 0 = 1;
        for (int i = 1; i < s.length(); i++) {
            char ch = s.charAt(i);
            ans += ((ch - 'a' + 1) * pr) % mod;
            pr = (pr * pow) % mod; 
        }
        return ans;
    }

    // Rabin Karp pattern finding
    // find the indices where pat appears in text
    public static void rabinKarpPatMatcher(String s, String p) {
        long phash = hashCode(p);
        long[] dp = new long[s.length()];
        long[] power = new long[s.length()];

        long mod = (int)(1e9 + 7), pr = 31, pow = 31;

        dp[0] = s.charAt(0) - 'a' + 1;
        power[0] = 1;

        for (int i = 1; i < s.length(); i++) {
            char ch = s.charAt(i);
            dp[i] = (dp[i - 1] + ((s.charAt(i) - 'a' + 1) * pr)) % mod;
            power[i] = pr;
            pr = (pr * pow) % mod;
        }

        // sliding window
        int si = 0, ei = p.length() - 1;
        ArrayList<Integer> ans = new ArrayList<>();

        while (ei < s.length()) {
            long window = dp[ei];
            if (si > 0) {
                window = ((window - dp[si - 1]) + mod) % mod;
            }
            if (window == (phash * power[si]) % mod) {
                ans.add(si);
            }
            si++; ei++;
        }
        System.out.println(ans);
        System.out.println(ans.size());
    }

    // Number of subsequences in a string divisible by n
    // whenever a new char comes the remainder can be find out by 
    // [old rem * 10 + new char] % n;
    public static int subseqDivisibleByN(String s, int n) {
        // Integer[][] dp = new Integer[s.length() + 1][n];
        // return subseqDivisibleByN_rec(s, n, 0, "", 0, dp);
        return subseqDivisibleByN_dp(s, n);
    }

    public static int subseqDivisibleByN_rec(String s, int n, int idx, String ans, int rem, Integer[][] dp) {
        if (dp[idx][rem] != null) {
            if (ans != "" && rem == 0) {
                System.out.println(ans);
            }
            return dp[idx][rem];
        }

        // if (dp[idx][rem] != null) return dp[idx][rem];

        if (idx >= s.length()) {
            if (ans != "" && rem == 0) {
                System.out.println(ans);
                return dp[idx][rem] = 1;
            }
            return dp[idx][rem] = 0;
        }
        
        int count = 0;
        char ch = s.charAt(idx);
        count += subseqDivisibleByN_rec(s, n, idx + 1, ans + ch, (rem * 10 + ch - '0') % n, dp);
        count += subseqDivisibleByN_rec(s, n, idx + 1, ans, rem, dp);
        return dp[idx][rem] = count;
    }

    public static int subseqDivisibleByN_dp(String s, int n) {
        int[][] dp = new int[s.length()][n];
        dp[0][(s.charAt(0) - '0') % n]++;
        for (int i = 1; i < dp.length; i++) {
            // say this character starts it's own seq
            dp[i][(s.charAt(i) - '0') % n]++;
            for (int j = 0; j < dp[0].length; j++) {
                // exclude
                dp[i][j] += dp[i - 1][j];
                // include
                dp[i][(j * 10 + (s.charAt(i) - '0')) % n] += dp[i - 1][j];
            }
        }
        // count of subseq with rem = 0;
        return dp[dp.length - 1][0];
    }

    public static void solve() {
        String s = "1234", p = "geek";
        int ans = subseqDivisibleByN(s, 4);
        System.out.println("ans is " + ans);
    }

    public static void main(String[] args) {
        solve();
    }
}