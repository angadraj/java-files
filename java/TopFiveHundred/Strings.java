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
    // special case: malayalam
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
            // case like A M M A M M A : odd = 3, when 1 char is at middle 2 are remaining
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
                // this is for odd freq, because at this point it will be 0;
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
                            // dp[i][j] == lcsLen - len => we are checking remaining length
                            // of lcs required to find
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

    // length of shortest common supersequence
    public static int[][] SCSlen(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = dp.length - 2; i >= 0; i--) {
            for (int j = dp[0].length - 2; j >= 0; j--) {
                char ith = s1.charAt(i), jth = s2.charAt(j);
                if (ith == jth) {
                    dp[i][j] = 1 + dp[i + 1][j + 1];
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        System.out.println(n + m - dp[0][0]);
        return dp;
    }

    // Printing Shortest Common Supersequence
    // supersequence -> a string which contains both strings as subsequence
    public static void printSCS(String s1, String s2) {
        int[][] dp = SCSlen(s1, s2);
        char[] arr = new char[s1.length() + s2.length() - dp[0][0]];
        int i = 0, j = 0, idx = 0;
        while (i < s1.length() && j < s2.length()) {
            char ith = s1.charAt(i), jth = s2.charAt(j);
            if (ith == jth) {
                arr[idx++] = ith;
                i++; j++;
            } else {
                if (dp[i + 1][j] > dp[i][j + 1]) {
                    arr[idx++] = ith;
                    i++;
                } else {
                    arr[idx++] = jth;
                    j++;
                }
            }
        }
        while (i < s1.length()) {
            arr[idx++] = s1.charAt(i);
            i++;
        }
        while (j < s2.length()) {
            arr[idx++] = s2.charAt(j);
            j++;
        }
        System.out.println(String.valueOf(arr));
    }

    // find if string1 is subsequence of string 2
    // basically if it is true then lcs of s1 and s2 will be equal to s1
    public static boolean isS1SubSeqOfS2(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = dp.length - 2; i >= 0; i--) {
            for (int j = dp[0].length - 2; j >= 0; j--) {
                char ith = s1.charAt(i), jth = s2.charAt(j);
                if (ith == jth) {
                    dp[i][j] = 1 + dp[i + 1][j + 1];
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        // print lcs 
        int i = 0, j = 0, idx = 0;
        char[] arr = new char[dp[0][0]];
        while (i < n && j < m) {
            char ith = s1.charAt(i), jth = s2.charAt(j);
            if (ith == jth) {
                arr[idx++] = ith;
                i++; j++;
            } else {
                if (dp[i + 1][j] > dp[i][j + 1]) {
                    i++;
                } else j++;
            }
        }
        String lcs = String.valueOf(arr);
        if (s1.equals(lcs)) return true;
        else return false;
    }

    // more efficient
    public static boolean isS1SubSeqOfS2_2(String s1, String s2) {
        int n = s1.length(), m = s2.length(), i = 0, j = 0;
        while (i < n && j < m) {
            char ith = s1.charAt(i), jth = s2.charAt(j);
            if (ith == jth) {
                i++; j++;
            } else j++;
        }
        if (i == n) return true;
        else return false;
    }

    // Find largest word in dictionary by deleting some characters of given string
    public static String largestInDict(String s, List<String> list) {
        HashSet<String> set = new HashSet<>();
        String ans[] = {""};
        for (String val: list) set.add(val);
        recHelper(set, s, ans, 0, "");
        return ans[0];
    }

    public static void recHelper(HashSet<String> set, String s, String[] ans, int idx, String temp) {
        if (idx >= s.length()) {
            if (set.contains(temp) && temp.length() > ans[0].length()) {
                ans[0] = temp;
            }
            return;
        }

        char ch = s.charAt(idx);
        recHelper(set, s, ans, idx + 1, temp + ch);
        recHelper(set, s, ans, idx + 1, temp);
    }

    // this is nothing but extension of : if one string is subseq of other string
    // in this s1 will be from dict and s2 will be given string
    public static String largestInDict2(String s, List<String> list) {
        String ans = "";
        for (String val: list) {
            if (isS1SubSeqOfS2_2(val, s)) {
                // for lexicographically
                int x = val.compareTo(ans);
                // x == 0: both are same
                // x < 0 : ans is shorter in len and lexico wise
                // x > 0: ans is bigger
                if (val.length() > ans.length()) ans = val;
                else if (val.length() == ans.length() && x < 0) {
                    // x < 0 shows that val is lexic wise smaller
                    ans = val;
                } 
            }
        }
        return ans;
    }

    // Search a Word in a 2D Grid of characters
    // basically a graph problem
    // search a word in 8 dirs
    public static void printAllOccurances(char[][] arr, String word) {
        int[][] dirs = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        ArrayList<int[]> res = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == word.charAt(0) && ifWordInGrid(arr, dirs, i, j, word, 0)) {
                    System.out.println(i + " " + j);
                    // res.add(new int[]{i, j});
                }
            }
        }
        // int[][] ans = res.toArray(new int[res.size()][]);
    }

    public static boolean ifWordInGrid(char[][] arr, int[][] dirs, int sr, int sc, String word, int idx) {
        // 1.mark
        char temp = arr[sr][sc];
        arr[sr][sc] = '0';
        // 2. work
        // 3. visit all children
        boolean ans = false;
        for (int[] d: dirs) {
            int dr = sr + d[0];
            int dc = sc + d[1];
            if (dr >= 0 && dc >= 0 && 
                    dr < arr.length && 
                    dc < arr[0].length && 
                    arr[dr][dc] != '0' && 
                    idx + 1 < word.length() && 
                    arr[dr][dc] == word.charAt(idx + 1)) {
                ans = ifWordInGrid(arr, dirs, dr, dc, word, idx + 1);
                // if (ans) break;
                // why break: so that all characters are replaced with original ones again
            }
        }
        // why this if: because for last char of word above if will not work and for that case ans will 
        // not change and it will be false, whereas ans should be true
        if (idx == word.length() - 1) ans = true;
        arr[sr][sc] = temp;
        return ans;
    }

    // Number of subsequences of the form a^i b^j c^k
    public static int seqOfABCForm(String s) {
        int a = 0, b = 0, c = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == 'a') a = (2 * a) + 1;
            else if (ch == 'b') b = (2 * b) + a;
            else if (ch == 'c') c = (2 * c) + b;
            System.out.println(a + " " + b + " " + c);
        }
        return c;
    }

    // Find number of times a string occurs as a subsequence in given string
    // given two strings, find the number of times second string comes in str1
    public static int countBIsSubSeqOfA(String a, String b) {
        int m = a.length(), n = b.length();
        // return countBIsSubSeqOfA_rec(a, b, 0, "");
        return countBIsSubSeqOfA_rec2(a, b, m - 1, n - 1);
        // return countBIsSubSeqOfA_dp(a, b, m, n);
    }

    public static int countBIsSubSeqOfA_rec(String a, String b, int idx, String ans) {
        if (idx >= a.length()) {
            if (ans.equals(b)) return 1;
            return 0;
        }
        if (ans.equals(b)) return 1;
        int count = 0;
        char ch = a.charAt(idx);
        count += countBIsSubSeqOfA_rec(a, b, idx + 1, ans + ch);
        count += countBIsSubSeqOfA_rec(a, b, idx + 1, ans);
        return count;
    }

    public static int countBIsSubSeqOfA_rec2(String a, String b, int m, int n) {
        if ((m < 0 && n < 0) || n < 0) return 1;
        if (m < 0) return 0;
        char ath = a.charAt(m), bth = b.charAt(n);
        int ans = 0;
        int count1 = countBIsSubSeqOfA_rec2(a, b, m - 1, n - 1);
        int count2 = countBIsSubSeqOfA_rec2(a, b, m - 1, n);
        if (ath == bth) {
            ans = count1 + count2;
        } else {
            ans = count2;
        }
        return ans;
    }

    public static int countBIsSubSeqOfA_dp(String a, String b, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < dp.length; i++) dp[i][dp[0].length - 1] = 1;

        for (int i = dp.length - 2; i >= 0; i--) {
            for (int j = dp[0].length - 2; j >= 0; j--) {
                char ith = a.charAt(i), bth = b.charAt(j);
                if (ith == bth) {
                    dp[i][j] = dp[i + 1][j + 1] + dp[i + 1][j]; 
                } else {
                    dp[i][j] = dp[i + 1][j];
                }
            }
        }
        return dp[0][0];
    }

    // count of distinct subseq
    public static int distinctSubSeq(String s) {
        int[] dp = new int[s.length() + 1];
        int[] map = new int[26];

        Arrays.fill(map, -1);
        dp[0] = 1;
        for (int i = 1; i < dp.length; i++) {
            char ch = s.charAt(i - 1);
            dp[i] = 2 * dp[i - 1];
            int lastOcc = map[ch - 'a'];
            if (lastOcc != -1) {
                dp[i] -= dp[lastOcc];
            }
            map[ch - 'a'] = i - 1;
            // why i - 1 here because we are traversing on dp not string
            // so indices on dp are +1;
        }
        return dp[dp.length - 1];
    }
    
    // count subseq of form 1(0+)1
    public static int countSubSeq101(String s) {
        int i = 0, count = 0, n = s.length();
        while (i < n) {
            char ch = s.charAt(i);
            if (ch == '1') {
                while (i + 1 < n && s.charAt(i + 1) == '0') {
                    i++;
                    if (i + 1 < n && s.charAt(i + 1) == '1') {
                        count++;
                        break;
                    } 
                }
            }
            i++;
        }
        return count;
    }

    // Given a string, find its first non-repeating character
    public static void firstNonRepChar(String s) {
        int[] map = new int[256];
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            map[ch]++;
        }
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (map[ch] == 1) {
                System.out.println(ch);
                break;
            }
        }
    }

    // Rearrange a string so that all same characters become d distance away
    public static String rearrangeRep(String s, int d) {
        int[] map = new int[26];
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            map[ch - 'a']++;
        }
        int max = 0;
        for (int i = 0; i < map.length; i++) {
            max = Math.max(map[i], max); 
        }
        // if max is one then there is no rep char
        if (max == 1) return s;
        // now we have some repeating values > 1
        // Arrays.sort(map, (t, o) -> {
        //     return o - t;
        // });
        char[] arr = new char[n];

        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            int freq = map[ch - 'a'];

            if (freq == 0) continue;
            // find first empty spot in arr
            int es = 0;
            while (es < n && arr[es] != '\u0000') {
                es++;
            }
            // now we have a spot
            for (int j = 1; j <= freq; j++) {
                if (es >= n) {
                    System.out.println("not possible");
                    return "";
                }
                arr[es] = ch;
                es += d;
                // reduce the freq so that a char is not visited again and again
                map[ch - 'a']--;
            }
        }
        // time: o(n)
        return String.valueOf(arr);
    }

    // Recursively remove all adjacent duplicates
    // using stack
    public static String removeAllAdjDups(String s) {
        Stack<Character> st = new Stack<>();
        int i = 0, n = s.length();
        char lastRemoved = '$';
        while (i < n) {
            char ch = s.charAt(i);
            if (st.size() > 0 && (st.peek() == ch || lastRemoved == ch)) {
                if (st.peek() == ch) {
                    st.pop();
                    lastRemoved = ch;
                    i++;
                }
                else if (ch == lastRemoved) i++;
            } else {
                st.push(ch);
                i++;
            }
        }
        StringBuilder sb = new StringBuilder();
        while (st.size() > 0) {
            sb.append(st.pop());
        }
        return sb.reverse().toString();
    }

    // recursive
    public static String removeAllDups(String s) {
        int n = s.length();
        if (n == 0 || n == 1) return s;

        if (s.charAt(0) == s.charAt(1)) {
            // find till where we need to extract
            int idx = 0;
            while (idx < n && s.charAt(0) == s.charAt(idx)) {
                idx++;
            }
            // now idx is on : aaab -> 'a' before 'b', need to remove that too
            return removeAllDups(s.substring(idx));
        } 
        String recAns = s.charAt(0) + removeAllDups(s.substring(1));
        if (recAns.length() > 1) {
            if (recAns.charAt(0) == recAns.charAt(1)) {
                return removeAllDups(recAns);
            }
        } 
        return recAns;
    }

    // to pass gfg
    public static String removeAllDups2(String s) {
        int n = s.length();
        if (n == 0 || n == 1) return s;

        if (s.charAt(0) == s.charAt(1)) {
            int idx = 0;
            while (idx < n && s.charAt(0) == s.charAt(idx)) idx++;
            return s.charAt(0) + removeAllDups2(s.substring(idx));
        } 
        return s.charAt(0) + removeAllDups2(s.substring(1));
    }

    // wildcard matching
    // ? -> any single char, * -> empty or char set
    public static boolean wildCardMatcher(String s, String p, int i, int j) {
        int ns = s.length(), np = p.length();
        if (i >= ns && j >= np) return true;
        if (i <= ns && j == np - 1 && p.charAt(j) == '*') return true; 
        if (i >= ns && j < np ) return false;


        char chs = s.charAt(i);
        char chp = p.charAt(j);
        boolean ans = false;
        if (chs == chp || chp == '?') {
            ans |= wildCardMatcher(s, p, i + 1, j + 1);
        } else if (chp == '*') {
            ans |= wildCardMatcher(s, p, i + 1, j);
            // * becomes blank, try next char of pattern
            ans |= wildCardMatcher(s, p, i, j + 1);
        } else if (chs != chp) ans = false;
        return ans;
    } 

    public static boolean wildCardDp(String s, String p) {
        int n = p.length(), m = s.length();
        boolean[][] dp = new boolean[n][m];
        // i = pattern, j = str
        for (int i = dp.length - 1; i >= 0; i--) {
            for (int j = dp[0].length - 1; j >= 0; j--) {
                if (i == dp.length - 1 && j == dp[0].length - 1) {
                    dp[i][j] = true;
                } else if (j == dp[0].length - 1) {
                    if (p.charAt(i) == '*') {
                        // * will say I have only one choice to be blank, so control goes to i + 1, j
                        dp[i][j] = dp[i + 1][j];

                    } else dp[i][j] = false;
                    // because no match for empty string except *
                } else if (i == dp.length - 1) {
                    dp[i][j] = false;
                    // empty pattern can't match string
                } else {
                    char chs = s.charAt(j), chp = p.charAt(i);
                    if (chs == chp || chp == '?') dp[i][j] = dp[i + 1][j + 1];
                    else if (chp == '*') dp[i][j] = dp[i][j + 1] || dp[i + 1][j];
                    else dp[i][j] = false; 
                }
            }
        }
        return dp[0][0];
    }

    // Lexicographic rank of a string
    public static int rankOfString(String s) {
        int n = s.length();
        long[] fact = new long[26];
        // max length of dist chars can be 26
        int[] arr = new int[256];
        fact[0] = 1;
        fact[1] = 1;
        for (int i = 2; i < 26; i++) fact[i] = i * fact[i - 1];
        for (int i = 0; i < n; i++) {
            arr[s.charAt(i)]++;
        }

        for (int val: arr) {
            if (val > 1) return 0;
        }
        // now I have to find letters smaller than the curr char
        /// smaller chars or the vals > 0 in arr
        int smallerChars = 0, op = 0;
        for (int i = 0; i < s.length(); i++) {
            smallerChars = 0;
            for (int j = 0; j < arr.length; j++) {
                if (j == s.charAt(i)) break;
                // basically when we are finding smaller than i then
                // if j comes on 'ith' index we need to break;
                if (arr[j] == 1) {
                    smallerChars++;
                } 
            }
            // mark that visited
            arr[s.charAt(i)] = 0;
            op += smallerChars * fact[n - i - 1];
        }
        return op + 1;
        // op has all combinations before it, so itself must be at op + 1
    }

    // Print number in ascending order which contains 1, 2 and 3 in their digits.
    // taking: sorting after finding out the desired numbers instead of sorting initially and wasting 
    // time on unwanted numbers
    public static void printNumberHaving123(int[] arr) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int val: arr) {
            if (contains123(val)) list.add(val);
        }
        Collections.sort(list);
        for (int val: list) {
            System.out.print(val + " , ");
        }
    }

    public static boolean contains123(int val) {
        String ans = val + "";
        if (ans.contains("1") && ans.contains("2") && ans.contains("3")) return true;
        return false;
    }

    // Given a sorted dictionary of an alien language, find order of characters
    // alien dict
    public static String alientDict(String[] words) {
        HashMap<Character, HashSet<Character>> map = new HashMap<>();
        HashMap<Character, Integer> indeg = new HashMap<>();

        for (String st: words) {
            for (char ch: st.toCharArray()) {
                indeg.put(ch, 0);
            }
        }

        for (int i = 0; i < words.length - 1; i++) {
            String curr = words[i];
            String next = words[i + 1];
            boolean flag = false;
            int len = Math.min(curr.length(), next.length());

            for (int j = 0; j < len; j++) {
                char ch1 = curr.charAt(j), ch2 = next.charAt(j);
                if (ch1 != ch2) {
                    /// map: ch1 -> ch2
                    // and raise the indegree of ch2
                    if (map.containsKey(ch1)) {
                        HashSet<Character> set = map.get(ch1);
                        if (set.contains(ch2) == false) {
                            set.add(ch2);
                            indeg.put(ch2, indeg.getOrDefault(ch2, 0) + 1);
                            map.put(ch1, set);
                        }
                    } else {
                        HashSet<Character> set = new HashSet<>();
                        set.add(ch2);
                        indeg.put(ch2, indeg.getOrDefault(ch2, 0) + 1);
                        map.put(ch1, set);
                    }
                    flag = true;
                    // this flag is only to see if the mismatched has occur
                    break;
                }
            }
            if (!flag && curr.length() > next.length()) {
                // then the 2 strings were same
                // exception is when curr.len() < next.len() then ans is not possible
                // as according to dict order those 2 words can't come like that 
                return "";
            }
        }

        // kahn's algo for topological sort
        Queue<Character> qu = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        for (Character key: indeg.keySet()) {
            if (indeg.get(key) == 0) {
                qu.add(key);
            }
        }

        int count = 0;
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                char rem = qu.remove();
                sb.append(rem);
                count++;
                // explore all nbrs and reduce their indegree
                if (map.containsKey(rem) == true) {
                    HashSet<Character> set = map.get(rem);
                    for (Character nb: set) {
                        indeg.put(nb, indeg.get(nb) - 1);
                        if (indeg.get(nb) == 0) qu.add(nb);
                    }
                }
            }
        }

        if (count == indeg.size()) {
            return sb.toString();
        } else return "";
    }

    // Find Excel column name from a given column number
    public static void printExcelColName(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            int rem = n % 26;
            if (rem == 0) {
                sb.append('z');
                n = (n / 26) - 1;
                // for ex n = 26, so at this stage we need to add z but after this process should end
            } else {
                sb.append((char)((rem - 1) + 'a'));
                n = n / 26;
            }
        }
        System.out.println(sb.reverse().toString());
    }

    // Find the longest substring with k unique characters in a given string
    public static void longestSubStringWithKUniqueChars(String s, int k) {
        HashMap<Character, Integer> map = new HashMap<>();
        int i = -1, j = -1, count = 0, len = 0, n = s.length();
        while (true) {
            boolean f1 = true, f2 = true;
            while (i < n - 1) {
                f1 = false;
                i++;
                char ch = s.charAt(i);
                map.put(ch, map.getOrDefault(ch, 0) + 1);
                if (map.size() < k) continue;
                else if (map.size() == k) {
                    if (i - j > len) {
                        len = i - j;
                        count++;
                    }
                } else {
                    break;
                }
            }

            while (j < i) {
                f2 = false;
                j++;
                char ch = s.charAt(j);
                if (map.get(ch) == 1) map.remove(ch);
                else {
                    map.put(ch, map.get(ch) - 1);
                }
                if (map.size() == k) {
                    if (i - j > len) {
                        len = i - j;
                        count++;
                    }
                    break;
                } else if (map.size() > k) continue;
            }

            if (f1 && f2) break;
        }
        System.out.println(len + " " + count);
    }

    // Function to find Number of customers who could not get a computer
    public static void runCustomerSimulation(int k, String s) {
        int[] map = new int[256];
        int comp = k, count = 0;
        String ans = "";
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            map[ch]++;
            if (map[ch] == 1) {
                // first occ
                if (comp > 0) {
                    comp--;
                } else if (comp == 0) {
                    count++;
                    map[ch] = -1;
                    ans += ch + " ";
                }
            } else if (map[ch] == 2) {
                // free the computers
                comp++;
            } 
        }
        System.out.println(count);
    }

    public static void solve() {
        String s = "malayalam";
        permPal(s);
        
        // System.out.println(ans);
    }
    
    public static void main(String args[]) {
        solve();
    }
}