import java.util.*;
class Strings {
    
    // Given a list of words with lower cases. 
    // Implement a function to find all Words that have the same unique character set . 
    public static void sameUniqueCharSet(String[] arr) {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        
        for (String val: arr) {
            String uniqueKey = giveUniqueKey(val);
            if (!map.containsKey(uniqueKey)) {
                map.put(uniqueKey, new ArrayList<>());
            } 
            map.get(uniqueKey).add(val);
        }
        for (String key: map.keySet()) {
            ArrayList<String> list = map.get(key);
            for (int i = 0; i < list.size(); i++) {
                System.out.print(list.get(i) + " ");
            }
            System.out.println();
        }
    }

    public static String giveUniqueKey(String str) {
        char[] arr = new char[26];
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            arr[ch - 'a'] = ch;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    public static void generateBinaryStringsFromPattern(String str) {
        // StringBuilder sb = new StringBuilder();
        // sb.append(str);
        // generateBSHelper(sb, 0);
        generateBSHelperBFS(str);
    }

    public static void generateBSHelper(StringBuilder sb, int idx) {
        if (idx >= sb.length()) {
            System.out.println(sb.toString());
            return;
        }
        
        char ch = sb.charAt(idx);
        if (ch == '?') {
            sb.replace(idx, idx + 1, "0");
            generateBSHelper(sb, idx + 1);

            sb.replace(idx, idx + 1, "1");
            generateBSHelper(sb, idx + 1);

            sb.replace(idx, idx + 1, "?");

        } else {
            generateBSHelper(sb, idx + 1);
        }
    }

    public static void generateBSHelperBFS(String str) {
        Queue<String> qu = new LinkedList<>();
        qu.add(str);
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                String curr = qu.remove();
                char[] arr = curr.toCharArray();
                int idx = getWildCharIndex(curr);
                if (idx != -1) {
                    arr[idx] = '0';
                    qu.add(String.valueOf(arr));

                    arr[idx] = '1';
                    qu.add(String.valueOf(arr));

                } else {
                    System.out.println(curr);
                }
            }
        }
    }

    public static int getWildCharIndex(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '?') {
                return i;
            }
        }
        return -1;
    }

    // minimum window substring
    // find min substring that contains all chars of str2 in str1
    public static int[] minWindowSubString1(String s1, String s2) {
        // 1. prepare a freq map for s2
        String ans = "";
        HashMap<Character, Integer> map2 = new HashMap<>();
        for (int i = 0; i < s2.length(); i++) {
            char ch = s2.charAt(i);
            map2.put(ch, map2.getOrDefault(ch, 0) + 1);
        }
        int mc = 0, dmc = s2.length(), si = -1, ei = -1, minLen = (int)(1e8);
        HashMap<Character, Integer> map1 = new HashMap<>();
        int i = -1, j = -1;
        while (true) {
            boolean f1 = true, f2 = true;
            // acquire
            while (i < s1.length() - 1 && mc < dmc) {
                i++;
                f1 = false;
                char ch = s1.charAt(i);
                map1.put(ch, map1.getOrDefault(ch, 0) + 1);
                if (map1.getOrDefault(ch, 0) <= map2.getOrDefault(ch, 0)) {
                    mc++;
                }
            }

            while (j < i && mc == dmc) {
                // collect ans
                f2 = false;
                if (i - j < minLen) {
                    minLen = i - j;
                    si = j + 1;
                    ei = i;
                }
                // start releasing
                j++;
                char ch = s1.charAt(j);
                if (map1.get(ch) == 1) map1.remove(ch);
                else map1.put(ch, map1.get(ch) - 1);

                // change match counts
                if (map1.getOrDefault(ch, 0) < map2.getOrDefault(ch, 0)) mc--;
            }
            if (f1 && f2) break;
        }
        // return ans;
        // if (si == -1 && ei == -1) return "-1";
        return new int[]{si, ei};
    }

    // minimum window substring 2 
    // find the minimum length substring of a string that contains all distinct character of itself
    public static String getUniqueString(String str) {
        HashSet<Character> set = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) set.add(str.charAt(i));
        for (Character ch: set) sb.append(ch);
        return sb.toString();
    }

    public static String minWindowSubString2(String s1) {
        String s2 = getUniqueString(s1);
        HashMap<Character, Integer> map1 = new HashMap<>();
        for (int i = 0; i < s2.length(); i++) {
            char ch = s2.charAt(i);
            map1.put(ch, map1.getOrDefault(ch, 0) + 1);
        }
        int i = -1, j = -1, mc = 0, dmc = s2.length(), minLen = (int)(1e8), si = -1, ei = -1;
        HashMap<Character, Integer> map2 = new HashMap<>();
        while (true) {
            boolean f1 = true, f2 = true;

            while (i < s1.length() - 1 && mc < dmc) {
                f1 = false;
                i++;
                char ch = s1.charAt(i);
                // just acquire real, extra whatever comes
                map2.put(ch, map2.getOrDefault(ch, 0) + 1);
                // now make a decision on what you have acquired
                // for ex: x in map2 will have freq = 1, and in map1 = 0
                if (map2.getOrDefault(ch, 0) <= map1.getOrDefault(ch, 0)) {
                    mc++;
                }
            }

            while (j < i && mc == dmc) {
                f2 = false;
                // collect ans before incrementing
                if (i - j + 1 < minLen) {
                    minLen = i - j + 1;
                    si = j + 1; 
                    ei = i;
                }
                j++;
                char ch = s1.charAt(j);
                // just release the character
                if (map2.get(ch) == 1) map2.remove(ch);
                else map2.put(ch, map2.get(ch) - 1);

                // now make a decision on what you have released
                if (map2.getOrDefault(ch, 0) < map1.getOrDefault(ch, 0)) {
                    mc--;
                }
            }

            if (f1 && f2) break;
        }
        return s1.substring(si, ei + 1);
    }

    // Count ways to increase LCS length of two strings by one
    // Given two strings of lower alphabet characters, we need to find the number of ways to insert 
    // a character in the first string such that length of LCS of both strings increases by one. 
    // LCS -> longest common subsequence
    
    // prerequisite LCS
    // biphurcation of making of subsequences of str1 and str2
    public static int[][] lcs(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = dp.length - 2; i >= 0; i--) {
            for (int j = dp[0].length - 2; j >= 0; j--) {
                char ch1 = s1.charAt(i);
                char ch2 = s2.charAt(j);
                if (ch1 == ch2) {
                    dp[i][j] = dp[i + 1][j + 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        return dp;
        // return findLCSString(dp, s1, s2);
    }

    // finding string from LCS
    public static String findLCSString(int[][] dp, String s1, String s2) {
        // as we know the length of our ans, so make a char arr for it
        char[] arr = new char[dp[0][0]];
        int idx = 0;
        int i = 0, j = 0;
        while (i < s1.length() && j < s2.length()) {
            char ith = s1.charAt(i), jth = s2.charAt(j);
            if (ith == jth) {
                arr[idx++] = ith;
                i++; j++;
            } else {
                if (dp[i + 1][j] > dp[i][j + 1]) i++;
                else j++;
            }
        }
        return String.valueOf(arr);
    }

    // number of ways in which we can add a char in s1 such that lcs(s1, s2) increases by 1
    

    // given a sequence of words, print all anagrams together
    public static List<List<String>> printAnagrams(String[] arr) {
        HashMap<String, ArrayList<String>> map = new HashMap<>(); 
        List<List<String>> res = new ArrayList<>();

        for (String val: arr) {
            String key = getKeyForAnagram(val);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            } 
            map.get(key).add(val);
        }
        for (String key: map.keySet()) {
            ArrayList<String> list = map.get(key);
            res.add(list);
        }

        return res;
    }

    public static String getKeyForAnagram(String str) {
        StringBuilder sb = new StringBuilder();
        boolean[] vis = new boolean[26];
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            vis[ch - 'a'] = true;
        }
        for (int i = 0; i < vis.length; i++) {
            if (vis[i]) {
                sb.append((char)(i + 'a'));
            }
        }
        return sb.toString();
    }

    // given a string & pattern, you have to find all the start indices of patt or permutations of patt
    // in that str
    public static ArrayList<Integer> searchAnagramInString(String s, String p) {
        ArrayList<Integer> res = new ArrayList<>();
        // 1. make a freq map for pattern
        HashMap<Character, Integer> pmap = new HashMap<>();
        for (int i = 0; i < p.length(); i++) {
            char ch = p.charAt(i);
            pmap.put(ch, pmap.getOrDefault(ch, 0) + 1);
        }
        HashMap<Character, Integer> smap = new HashMap<>();
        // 2. acquire p characters to init a window of length p
        int i = 0, count = 0;
        for (i = 0; i < p.length(); i++) {
            char ch = s.charAt(i);
            smap.put(ch, smap.getOrDefault(ch, 0) + 1);
        }
        // 3. travel on s with sliding window and A/R of one character per window
        while (i < s.length()) {
            // 4. check for ans as initially our window of size p is already ready
            if (compareMap(smap, pmap)) {
                System.out.println("hello");
                count++;
                // res.add(i - p.length());
            }
            // 5. add another char and remove prev, ch_a = acquiring char, ch_r = release char
            char ch_a = s.charAt(i);
            smap.put(ch_a, smap.getOrDefault(ch_a, 0) + 1);
            
            char ch_r = s.charAt(i - p.length());
            if (smap.get(ch_r) == 1) smap.remove(ch_r);
            else smap.put(ch_r, smap.get(ch_r) - 1);

            i++;
        }
        // if last window was valid but i was out of bounds
        if (compareMap(smap, pmap)) {
            count++;
            // res.add(i - p.length());
        }
        res.add(count);
        return res;
    }

    public static ArrayList<Integer> searchAnagramInString2(String s, String p) {
        // 1. create two freq arrays of length 256 (for all kind of chars)
        ArrayList<Integer> res = new ArrayList<>();
        int[] sarr = new int[256]; 
        int[] parr = new int[256]; 

        // 2. store the freq of pattern chars and also init the first window of size p
        for (int i = 0; i < p.length(); i++) {
            // init window
            sarr[s.charAt(i) - 'a']++;
            // make freq map of p chars
            parr[p.charAt(i) - 'a']++;
        }

        // 3. travel on s and start sliding window algo
        int i = p.length(), count = 0;
        while (i < s.length()) {
            // 4. compare the prev created maps / arr
            if (compareFreqArr(sarr, parr)) {
                count++;
                res.add(i - p.length());
            }
            // 5. acquire curr char and remove out of window char
            sarr[s.charAt(i) - 'a']++;
            sarr[s.charAt(i - p.length()) - 'a']--;

            i++;
        }
        // 6. for the last window in which I was out of bounds
        if (compareFreqArr(sarr, parr)) {
            count++;
            res.add(i - p.length());
        }
        return res;
    }

    public static boolean compareFreqArr(int[] sarr, int[] parr) {
        for (int i = 0; i < 256; i++) {
            if (sarr[i] != parr[i]) return false;
        }
        return true;
    }

    public static boolean compareMap(HashMap<Character, Integer> smap, HashMap<Character, Integer> pmap) {
        for (Character s: pmap.keySet()) {
            if (smap.getOrDefault(s, 0) != pmap.get(s)) return false; 
        }
        return true;
    }

    // check if two strings are k anagrams or not
    public static boolean kAnagrams(String s1, String s2, int k) {
        if (s1.length() != s2.length()) return false;

        HashMap<Character, Integer> map1 = new HashMap<>();

        for (int i = 0; i < s1.length(); i++) {
            char ch1 = s1.charAt(i);
            map1.put(ch1, map1.getOrDefault(ch1, 0) + 1);
        }

        for (int i = 0; i < s2.length(); i++) {
            char ch2 = s2.charAt(i);
            if (map1.getOrDefault(ch2, 0) > 0) {
                map1.put(ch2, map1.get(ch2) - 1);
            }
        }
        // remaining freq means there are foreign chars in string that need to be replaced
        // if all chars in s2 and s1 are same the count = 0;
        int count = 0;
        for (Character key: map1.keySet()) {
            count += map1.get(key);
        }

        if (count <= k) return true;
        else return false;
    }
    
    // Check if binary representations of two numbers are anagram
    public static boolean binaryAnagram(int a, int b) {
        int count_a = countSetBits(a);
        int count_b = countSetBits(b);

        if (count_a == count_b) return true;
        else return false;
    }

    public static int countSetBits(int n) {
        int count = 0;
        while (n > 0) {
            n = (n & (n - 1));
            count++;
        }
        return count;
    }

    // Longest Palindromic Substring 
    public static boolean[][] longestPallSubstring(String s) {
        int n = s.length(), maxLen = 0;
        boolean[][] dp = new boolean[n][n];
        for (int g = 0; g < n; g++) {
            for (int i = 0, j = g; j < n; i++, j++) {
                char ith = s.charAt(i);
                char jth = s.charAt(j);

                if (g == 0) dp[i][j] = true;
                else if (g == 1) {
                    dp[i][j] = (ith == jth) ? true : false;
                }
                else {
                    if (ith == jth && dp[i + 1][j - 1]) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = false;
                    }
                }
                if (dp[i][j]) maxLen = g + 1;
                // because gaps will be increasing always so no need to find max
            }
        }
        return dp;
    }

    // find string in longest pallindromic substring
    public static String findLongestPallSubString(String s) {
        int maxlen = 0, si = -1, ei = -1;
        // as we don't know that the pallindrome in the string is of odd or even length
        // that is why we need to find palls for both the lengths
        for (int i = 0; i < s.length(); i++) {
            // try for odd length
            int[] arrOdd = expand(s, i, i);
            int oddlen = arrOdd[1] - arrOdd[0] + 1;
            // si, ei
            if (oddlen > maxlen) {
                maxlen = oddlen;
                si = arrOdd[0];
                ei = arrOdd[1];
            }

            int[] arrEven = expand(s, i, i + 1);
            int evenlen = arrEven[1] - arrEven[0] + 1;
            // si, ei
            if (evenlen > maxlen) {
                maxlen = evenlen;
                si = arrEven[0];
                ei = arrEven[1];
            }
        }
        return s.substring(si, ei + 1);
    }

    public static int[] expand(String s, int si, int ei) {
        while (si >= 0 && ei < s.length() && s.charAt(si) == s.charAt(ei)) {
            si--;
            ei++;
        }
        return new int[]{si + 1, ei - 1};
    }

    // print permutations (single) 
    // given n print permutations 1 to n
    public static int printPermut(int n) {
        boolean[] vis = new boolean[n];
        ArrayList<Integer> ans = new ArrayList<>();
        return permutHelper(n, 0, vis, ans);
        // time : n!
    }

    public static int permutHelper(int n, int idx, boolean[] vis, ArrayList<Integer> ans) {
        if (ans.size() >= n) {
            System.out.println(ans);
            return 1;
        }
        int count = 0;
        for (int i = idx; i < n; i++) {
            if (!vis[i]) {
                vis[i] = true;
                ans.add(i);
                count += permutHelper(n, idx, vis, ans);
                vis[i] = false;
                ans.remove(ans.size() - 1);
            }
        }
        return count;
    }

    // pallindrome & permutation
    // codechef : PERMPAL
    // special case: malyalam
    public static void permPal(String s) {
        int n = s.length();
        HashMap<Character, ArrayList<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (!map.containsKey(ch)) {
                map.put(ch, new ArrayList<>());
            }
            map.get(ch).add(i);
        }
        // make an array to store all indices (1 indexing)
        int[] arr = new int[n];
        int odd = 0, si = 0, ei = arr.length - 1;
        // now parse on map and make the permutation
        for (Character key: map.keySet()) {
            ArrayList<Integer> list = map.get(key);
            if ((list.size() & 1) == 1) {
                // if the list size is odd
                arr[n >> 1] = list.get(0) + 1;
                list.remove(list.get(0));
                odd++;
            }
            // odd > 1 then pallindrome is not possible
            if (odd > 1) {
                System.out.println("-1");
                return;
            }

            // else the list is even and now we need to add chars at extremes
            for (int j = 0; j < list.size(); j++) {
                if ((j & 1) == 0) arr[si++] = list.get(j) + 1;
                else arr[ei--] = list.get(j) + 1;
            }
        }
        for (int val: arr) System.out.print(val + " ");
        System.out.println();
    }

    // Make largest palindrome by changing at most K-digits
    public static void makeLargestPall(String str, int k) {
        StringBuilder s = new StringBuilder(str);

        // 1. find the minimum number of changes to make it a pallindrome
        int count = 0, n = s.length();
        for (int i = 0, j = n - 1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) count++;
        }

        if (k < count) {
            System.out.println("Not possible");
            return;
        }
        // now k >= count
        // special case: in this the s is pall already, we just need to make it bigger
        for (int i = 0, j = n - 1; i <= j; i++, j--) {
            // changes have to be made and we have enough k
            char ith = s.charAt(i), jth = s.charAt(j);
            int maxNumber = Math.max((int)(ith - '0'), (int)(jth - '0'));
            if (k > 1) {
                // now I can make choices from 1 to 9
                for (int p = 9; p >= 0; p--) {
                    if (p > maxNumber) {
                        s.replace(i, i + 1, p + "");
                        s.replace(j, j + 1, p + "");
                        k -= 2;
                        break;
                    }
                }
            } else if (k == 1) {
                if (ith != jth) {
                    s.replace(i, i + 1, maxNumber + "");
                    s.replace(j, j + 1, maxNumber + "");
                    k -= 1;
                } else if (i == j) {
                    for (int p = 9; p >= 0; p--) {
                        if (p > maxNumber) {
                           s.replace(i, i + 1, p + "");
                           k -= 1;
                           break;
                        }
                     }
                }
            }
        }
        System.out.println(s.toString());
    }

    // Lexicographically first palindromic string
    public static void lexicoFirstPall(String s) {
        int n = s.length();
        if (!isValidPall(s)) {
            System.out.println("no palindromic string");
            return;
        }
        // now we need to arrange the char in lexico order
        int[] farr = new int[26];
        for (int i = 0; i < s.length(); i++) {
            farr[s.charAt(i) - 'a']++;
        }
        char[] arr = s.toCharArray();
        int si = 0, ei = arr.length - 1;
        for (int i = 0; i < 26; i++) {
            while (farr[i] > 0 && si < ei) {
                arr[si++] = (char)(i + 'a');
                farr[i]--;
                if (farr[i] == 0) break;
                arr[ei--] = (char)(i + 'a');
                farr[i]--;
            }
        }
        System.out.println(String.valueOf(arr));
    }

    public static boolean isValidPall(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) return false;
            i++; j--;
        } 
        return true;
    }

    // longest non pallindromic substring
    // Given a string of size n. The task is to find the length of the largest substring which is not palindrome.
    public static int longestNonPallSubstring(String s) {
        int len = 0, si = -1, ei = -1, n = s.length();
        for (int i = 0; i < n; i++) {
            // len for odd length
            int[] oddSet = expandNonPall(s, i, i);
            if (oddSet[0] != -1 && oddSet[1] != -1 && oddSet[1] - oddSet[0] + 1 > len) {
                len = oddSet[1] - oddSet[0] + 1;
                si = oddSet[0];
                ei = oddSet[1];
            }

            int[] evenSet = expandNonPall(s, i, i + 1);
            if (evenSet[0] != -1 && evenSet[1] != -1 && evenSet[1] - evenSet[0] + 1 > len) {
                len = evenSet[1] - evenSet[0] + 1;
                si = evenSet[0];
                ei = evenSet[1];
            }
        }
        if (si == -1) System.out.println(" ");
        else System.out.println(s.substring(si, ei + 1));
        return len;
        // time: o(n ^ 2), space: o(1)
    }

    public static int[] expandNonPall(String s, int si, int ei) {
        int i = si, j = ei, n = s.length();
        while ((i >= 0 && j < n && s.charAt(i) != s.charAt(j)) || (i == j)) {
            i--; j++;
        }
        // below check is when there is one char in it and one single char is also pall substring
        if (i + 1 == j - 1) return new int[]{-1, -1}; 
        else return new int[]{i + 1, j - 1};
    }

    // efficient soln
    public static int longestNonPallSubstring2(String s) {
        int n = s.length();
        // 1. check if all chars are same or not
        char oth = s.charAt(0);
        int i;
        for (i = 1; i < n; i++) {
            if (s.charAt(i) != oth) break;
        }
        // if all were equal then i = n;
        // then there is no non pall substring
        if (i == n) return 0;
        // if i is not n
        // now we can check if the complete s is pall or not
        if (isValidPall(s)) {
            // then remove one char from end
            return n - 1;
        }
        // the complete s is non pall
        return n;
        // time: o(n), space: o(1)
    }

    // string merging codechef: STRMRG
    public static int stringMerging(String s1, String s2) {
        String a = removeConsecutiveSameChars(s1);
        String b = removeConsecutiveSameChars(s2);
        int n = a.length(), m = b.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = dp.length - 2; i >= 0; i--) {
            for (int j = dp[0].length - 2; j >= 0; j--) {
                char ith = a.charAt(i), jth = b.charAt(j);
                if (ith == jth) {
                    dp[i][j] = 1 + dp[i + 1][j + 1];
                } else {
                    dp[i][j] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }
        int lcs = dp[0][0];
        int ans = n + m - lcs;
        System.out.println(lcs);
        return lcs;
    }

    public static String removeConsecutiveSameChars(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != s.charAt(i - 1)) {
                sb.append(s.charAt(i));
            }
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    // Find k’th character of decrypted string
    public static void kthDecryptedString(String s, int k) {
        int n = s.length();
        String ans = "", temp = "";
        int i = 0, freq = 0;
        while (i < n) {
            // now see till where you can collect character
            temp = "";
            while (i < n && s.charAt(i) >= 'a' && s.charAt(i) <= 'z') {
                temp += s.charAt(i);
                i++;
            }
            // now if i < n, then it i is on the number
            freq = 0;
            while (i < n && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                freq = (freq * 10) + s.charAt(i) - '0';
                i++;
            }
            System.out.println(freq);
            // now i again stands on character or n
            // add the substring collected in temp freq times
            for (int j = 1; j <= freq; j++) {
                ans += temp;
            }
        }
        // now ans will have the decrypted string
        // edge case: if there were no numbers and only chars
        if (freq == 0) {
            ans += temp;
        }
        System.out.println(ans.charAt(k - 1));
    }

    // count of words whose ith char is ith or i - 1 th or i + 1 th char of str
    public static int countOfWords(String s) {
        int count = 1, n = s.length();
        if (n == 1) return count;
        if (s.charAt(0) == s.charAt(1)) count *= 1;
        else count *= 2;

        // now travel the string and multiply the combinations
        for (int j = 1; j < n - 1; j++) {
            // all chars are equal
            if (s.charAt(j) == s.charAt(j - 1) && s.charAt(j) == s.charAt(j + 1)) count *= 1;
            // any two chars are equal
            else if (s.charAt(j) == s.charAt(j - 1) || s.charAt(j) == s.charAt(j + 1) || s.charAt(j - 1) == s.charAt(j + 1)) count *= 2;
            // if no one is equal 
            count *= 3; 
        }

        if (s.charAt(n - 1) == s.charAt(n - 2)) count *= 1;
        else count *= 2;

        return count;
    }

    // count distinct pallindromic substrings
    // 1. print all pall subtrings
    // 2. put them in set
    // print substrings
    public static void manachers(String s) {
        String str = getSpecialString(s);
        int[] lps = new int[str.length()];
        int c = 0, r = 0;
        // to find all substrings 
        // we need to do it dynamically when lps[1] is inc everytime
        // as we get a new length pall substring
        // HashSet<String> set = new HashSet<>();
        for (int i = 1; i < str.length() - 1; i++) {
            int mirror = c - (i - c);
            if (i < r) {
                // if i is in the range
                lps[i] = Math.min(r - i, lps[mirror]);
            }
            while (str.charAt(i + lps[i] + 1) == str.charAt(i - lps[i] - 1)) {
                lps[i]++;
                // add all substrings
                // int siInLps = i - lps[i] + 1;
                // int siInOrgS = (siInLps - 2) / 2;
                // set.add(s.substring(siInOrgS, siInOrgS + lps[i]));
            }
            if (i + lps[i] > r) {
                c = i;
                r = i + lps[i];
            }
        }
        // find string normal

        // max length
        int max = 0, maxIdx = -1;
        for (int i = 1; i < lps.length - 1; i++) {
            if (lps[i] > max) {
                max = lps[i];
                maxIdx = i;
            }
        }
        int firstIdx = maxIdx - max + 1;
        int orgIdx = (firstIdx - 2) / 2;
        System.out.println(s.substring(orgIdx, orgIdx + max));

        // for (String key: set) System.out.print(key + " ");
    }

    public static String getSpecialString(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append("@");
        for (int i = 0; i < s.length(); i++) {
            sb.append("#");
            sb.append(s.charAt(i));
        }
        sb.append("#");
        sb.append("$");
        return sb.toString();
    }

    // count all distinct pall substrings
    // time : o(n ^ 2)
    public static void distinctPallSubStr(String s) {
        if (s.length() == 0) return;

        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            expandPallSub(set, s, i, i);
            expandPallSub(set, s, i, i + 1);
        }
        // for (String val: set) System.out.print(val + " ");
        System.out.println(set);
    }

    // expand to find all odd or even length palls
    // time with substr = n ^ 2, else n
    public static void expandPallSub(HashSet<String> set, String s, int i, int j) {
        int si = i, ei = j;
        while (si >= 0 && ei < s.length() && s.charAt(si) == s.charAt(ei)) {
            set.add(s.substring(si, ei + 1));
            si--; ei++;
        }
    }

    // Print all distinct characters of a string in order
    public static void printAllDisChars(String str) {
        int[] arr = new int[256];
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            arr[(int)ch]++;
        }

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch != ' ' && arr[(int)ch] == 1) System.out.print(ch); 
        }
    }

    // o(n) time and 1 traversal
    public static void printAllDisChars2(String s) {
        int n = s.length();
        int[] count = new int[256];
        int[] index = new int[256];
        Arrays.fill(index, n);
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            count[ch]++;
            if (count[ch] == 1) {
                index[ch] = i;
            } else if (count[ch] == 2) {
                index[ch] = n;
            }
        }
        Arrays.sort(index);
        // sorting will be done in o(1)
        for (int i = 0; i < index.length; i++) {
            if (index[i] != n && s.charAt(index[i]) != ' ') {
                System.out.print(s.charAt(index[i]));
            }
        }
    }

    // count of substrings in a string which are anagram to each other
    public static int countAnagramSubstr(String s) {
        HashMap<String, Integer> map = new HashMap<>();
        int n = s.length();
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                String ans = s.substring(i, j + 1);
                char arr[] = ans.toCharArray();
                Arrays.sort(arr);
                String val = String.valueOf(arr);
                map.put(val, map.getOrDefault(val, 0) + 1);
            }
        }
        int count = 0;
        for (String key: map.keySet()) {
            int freq = map.get(key);
            if (freq > 1) {
                // count of unique pairs 
                // (n * (n - 1)) / 2;
                count += (freq * (freq - 1)) / 2;
                // System.out.println(key);
            }
        }
        return count;
    }

    // Min flips of continuous characters to make all characters same in a string
    // so we can flip one substring to make all the chars same in a str
    // find min number of flips
    public static int countFlipsToMakeIdentical(String s) {
        int zeros = 0;
        int ones = 0;
        char ch = s.charAt(0);
        // this is just to give a start
        if (ch == '0') zeros += 1;
        else ones += 1;

        for (int i = 1; i < s.length(); i++) {
            char curr = s.charAt(i);
            char prev = s.charAt(i - 1);
            if (curr == prev) continue;
            else {
                if (curr == '0') zeros++;
                else ones++;
            }
        }
        // min of substrings of 0s or 1s min will be the flips
        return Math.min(zeros, ones);
    }

    // Count binary strings with k times appearing adjacent two set bits
    // we have to find binary strings in which 1 comes adjacent k times of len n
    public static int stringsWithKAdj2SetBits(int n, int k) {
        int[][][] dp = new int[n + 1][k + 1][2];
        // if n = 1, k = 0
        dp[1][0][0] = 1;
        dp[1][0][1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i && j < k + 1; j++) {
                // last char is 0
                dp[i][j][0] = dp[i - 1][j][1] + dp[i - 1][j][0];
                dp[i][j][1] = dp[i - 1][j][0];
                // last char is 1
                if (j - 1 >= 0) {
                    dp[i][j][1] += dp[i - 1][j - 1][1];
                }
            }
        }
        return dp[n][k][0] + dp[n][k][1];
    }

    // Binary representation of next greater number with same number of 1’s and 0’s
    public static void nextGreaterBinary(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        boolean flag = false;
        for (int i = n - 1; i >= 0; i--) {
            if (i + 1 < n && arr[i] == '0' && arr[i + 1] == '1') {
                char x = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = x;
                // now shift all ones to right from i + 2 idx
                char temp = arr[n - 1];
                int j = n - 1;
                while (j > i + 2) {
                    arr[j] = arr[j - 1];
                    j--;
                }
                arr[i + 2] = temp;
                flag = true;
                break;
            }
        }
        if (!flag) System.out.println("no answer");
        else System.out.println(String.valueOf(arr));
    }

    // Print all longest common sub-sequences in lexicographical order
    static int lcsLen = -1;
    static int[][] dp;
    public static void LCS(String s1, String s2) {
        dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = dp.length - 2; i >= 0; i--) {
            for (int j = dp[0].length - 2; j >= 0; j--) {
                char ith = s1.charAt(i), jth = s2.charAt(j);
                if (ith == jth) {
                    dp[i][j] = dp[i + 1][j + 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i +1][j], dp[i][j + 1]);
                }
            }
        }
        lcsLen = dp[0][0];
    }

    public static void printAllLCS(String s1, String s2, ArrayList<String> res, int len, String ans, int i, int j) {
        if (len == lcsLen) {
            res.add(ans);
            return;
        }
        if (i >= s1.length() || j >= s2.length()) return;
        // to prevent duplicacy
        for (char ch = 'a'; ch <= 'z'; ch++) {
            boolean flag = false;

            for (int p = i; p < s1.length(); p++) {
                if (ch == s1.charAt(p)) {
                    for (int q = j; q < s2.length(); q++) {
                        if (ch == s2.charAt(q) && dp[i][j] == lcsLen - len) {
                            printAllLCS(s1, s2, res, len + 1, ans + ch, p + 1, q + 1);
                            flag = true;
                            // break ensures that for one p multiple q are not find
                            break;
                        }
                    }
                }
                if (flag) {
                    // if q is idle
                    break;
                }
            }
        }
    }

    public static void printingAllLCS(String s1, String s2) {
        LCS(s1, s2);
        ArrayList<String> res = new ArrayList<>();
        printAllLCS(s1, s2, res, 0, "", 0, 0);
        // System.out.println(res);
    }


    public static void solve() {
        String a = "abcabcaa", b = "acbacba";
        printingAllLCS(a, b);
        // System.out.println(ans);
    }
    
    public static void main(String args[]) {
        solve();
    }
}