import java.util.*;
class Recursion {
    
    // knights tour problem
    public static void knightTour(int n) {
        int[][] dirs = {{-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}};
        int[][] arr = new int[n][n];
        knightTourRec(arr, dirs, 0, 0, 0);
    }

    public static void knightTourRec(int[][] arr, int[][] dirs, int sr, int sc, int move) {
        if (move == (arr.length * arr[0].length) - 1) {
            arr[sr][sc] = move;
            print2D(arr);
            arr[sr][sc] = 0;
            return;
        }
        arr[sr][sc] = move;
        for (int[] d: dirs) {
            int dr = sr + d[0];
            int dc = sc + d[1];
            if (dr < arr.length && dc < arr[0].length && dr >= 0 && dc >= 0 && arr[dr][dc] == 0) {
                knightTourRec(arr, dirs, dr, dc, move + 1);
            }
        }
        arr[sr][sc] = 0;
    }

    // Rat in a maze: two moves H and V. print all paths
    public static void RatInMaze() {
        int[][] arr = { { 1, 1, 1, 1 },
                         { 1, 1, 1, 1 },
                         { 0, 1, 1, 1 },
                         { 1, 1, 1, 1 } };
        if (arr[0][0] == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        RatInMazeRec(arr, 0, 0, sb);
    }

    public static void RatInMazeRec(int[][] arr, int sr, int sc, StringBuilder sb) {
        if (sr == arr.length - 1 && sc == arr[0].length) {
            System.out.println(sb);
            return;
        }
        if (sr < 0 || sc < 0 || sr >= arr.length || sc >= arr[0].length || arr[sr][sc] == 0) {
            return;
        }
        sb.append("V");
        RatInMazeRec(arr, sr + 1, sc, sb);
        sb.delete(sb.length() - 1, sb.length());
        sb.append("H");
        RatInMazeRec(arr, sr, sc + 1, sb);
        sb.delete(sb.length() - 1, sb.length());
    }
    
    // sudoku
    public static void sudoku() {
        int[][] arr = { {3, 0, 6, 5, 0, 8, 4, 0, 0}, 
         {5, 2, 0, 0, 0, 0, 0, 0, 0}, 
         {0, 8, 7, 0, 0, 0, 0, 3, 1}, 
         {0, 0, 3, 0, 1, 0, 0, 8, 0}, 
         {9, 0, 0, 8, 6, 3, 0, 0, 5}, 
         {0, 5, 0, 0, 9, 0, 6, 0, 0}, 
         {1, 3, 0, 0, 0, 0, 2, 5, 0}, 
         {0, 0, 0, 0, 0, 0, 0, 7, 4}, 
         {0, 0, 5, 2, 0, 6, 3, 0, 0} };
        ArrayList<Integer> list = new ArrayList<>();
        int[] R = new int[10];
        int[] C = new int[10];
        int[][] RC = new int[3][3];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == 0) {
                    list.add(i * 9 + j);
                } else {
                    int num = arr[i][j];
                    R[i] ^= (1 << num);
                    C[j] ^= (1 << num);
                    RC[i / 3][j / 3] ^= (1 << num);
                }
            }
        }
        sudokuSolver1(arr, list, 0, R, C, RC);
    }

    public static void sudokuSolver1(int[][] arr, ArrayList<Integer> list, int idx, int[] R, int[] C, int[][] RC) {
        if (idx == list.size()) {
            print2D(arr);
            return;
        }
        int cord = list.get(idx);
        int i = cord / 9, j = cord % 9;
        for (int num = 1; num <= 9; num++) {
            if ((R[i] & (1 << num)) == 0 && 
                (C[j] & (1 << num)) == 0 && 
                (RC[i / 3][j / 3] & (1 << num)) == 0) {

                arr[i][j] = num;
                R[i] ^= (1 << num);
                C[j] ^= (1 << num);
                RC[i / 3][j / 3] ^= (1 << num);

                sudokuSolver1(arr, list, idx + 1, R, C, RC);

                arr[i][j] = 0;
                R[i] ^= (1 << num);
                C[j] ^= (1 << num);
                RC[i / 3][j / 3] ^= (1 << num);
            }
        }
    }

    public static boolean isSudokuNumValid(int[][] arr, int num, int i, int j) {
        // row 
        for (int c = 0; c < 9; c++) {
            if (arr[i][c] == num) return false;
        }
        // col
        for (int r = 0; r < 9; r++) {
            if (arr[r][j] == num) return false;
        }
        // sub matrix of 3X3
        int si = (i / 3) * 3, ei = (j / 3) * 3;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 0; col++) {
                if (arr[si + row][ei + col] == num) return false;
            }
        }
        return true;
    }
    
    // remove minm invalid parenthesis
    // first we need to find the minm braces need to be removed
    public static void removeInvalidParenth(String s) {
        int minRemovals = getMinBracesToRemove(s);
        HashSet<String> set = new HashSet<>();
        removeInvalidParenthRec(s, set, minRemovals);
        // System.out.println(set.size());
    }

    public static void removeInvalidParenthRec(String s, HashSet<String> set, int mr) {
        if (s.length() == 0 || mr == 0) {
            if (mr == 0 && getMinBracesToRemove(s) == 0 && !set.contains(s)) {
                System.out.println(s);
                set.add(s);
            }
            return;
        }
        
        for (int i = 0; i < s.length(); i++) {
            String left = s.substring(0, i);
            // removed ith
            String right = s.substring(i + 1);
            removeInvalidParenthRec(left + right, set, mr - 1);
        }
    }

    public static int getMinBracesToRemove(String s) {
        Stack<Character> st = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') st.push(ch);
            else if (ch == ')') {
                if (st.size() > 0 && st.peek() == '(') st.pop();
                else st.push(ch);
            }
        }
        return st.size();
    }

    // word break
    public static List<String> wordbreakHelper(String s, String[] dict, HashMap<String, List<String>> dp) {
        if (dp.containsKey(s)) return dp.get(s); 
        
        List<String> self = new ArrayList<>();
        for (String w: dict) {
            if (s.length() >= w.length() && 
                w.equals(s.substring(0, w.length()))) {

                if (s.length() == w.length()) {
                    // if s is of same size as of w then we will not make a call 
                    self.add(w);
                } else {
                    List<String> recAns = wordbreakHelper(s.substring(w.length()), dict, dp);
                    for (String r: recAns) {
                        self.add(w + " " + r);
                    }
                }
            }
        }
        dp.put(s, self);
        return self;
    }

    // print pallindromic partitions of string
    public static void printPallPart(String s) {
        boolean[][] dp = longestPallindromicSubstring(s);
        List<String> ans = new ArrayList<>();
        printPallPartHelper(s, ans, dp, 0);
    }

    public static boolean[][] longestPallindromicSubstring(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        for (int g = 0; g < n; g++) {
            for (int i = 0, j = g; j < n; i++, j++) {
                char chi = s.charAt(i), chj = s.charAt(j);
                if (g == 0) dp[i][j] = true;
                else if (g == 1) {
                    if (chi == chj) dp[i][j] = true;
                    else dp[i][j] = false;
                } else {
                    if (chi == chj) dp[i][j] = dp[i + 1][j - 1];
                    else dp[i][j] = false;
                }
            }
        }
        return dp;
    }

    public static void printPallPartHelper(String s, List<String> ans , boolean[][] dp, int idx) {
        if (s.length() == 0) {
            System.out.println(ans);
            return;
        }
        
        for (int i = 0; i < s.length(); i++) {
            // check all prefixes
            if (dp[idx][idx + i]) {
                ans.add(s.substring(0, i + 1));
                printPallPartHelper(s.substring(i + 1), ans, dp, idx + 1);
                ans.remove(ans.size() - 1);
            }
        }
    }

    // Find shortest safe route in a path with landmines
    public static int shortestPathLandMines(int[][] arr) {
        int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == 0) {
                    for (int[] d: dirs) {
                        int dr = i + d[0];
                        int dc = j + d[1];
                        if (dr >= 0 && dc >= 0 && dr < arr.length && dc < arr[0].length) {
                            arr[dr][dc] = 2;
                        }
                    }
                }
            }
        }
        boolean[][] vis = new boolean[arr.length][arr[0].length];
        Queue<int[]> qu = new LinkedList<>();
        // i, j, dis
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][0] == 1) {
                qu.add(new int[]{i, 0, 0});
                vis[i][0] = true;
            }
        }
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                int[] cp = qu.remove();
                if (cp[1] == arr[0].length - 1) return cp[2];
                for (int[] d: dirs) {
                    int dr = cp[0] + d[0];
                    int dc = cp[1] + d[1];
                    if (dr >= 0 && dc >= 0 && dr < arr.length && dc < arr[0].length && arr[dr][dc] == 1 && !vis[dr][dc]) {
                        qu.add(new int[]{dr, dc, cp[2] + 1});
                        vis[dr][dc] = true;
                    }
                }
            }
        }
        return -1;
    }

    public static int shortestPathLandMines2(int[][] arr) {
        int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == 0) {
                    for (int[] d: dirs) {
                        int dr = i + d[0];
                        int dc = j + d[1];
                        if (dr >= 0 && dc >= 0 && dr < arr.length && dc < arr[0].length) {
                            arr[dr][dc] = 2;
                        }
                    }
                }
            }
        }
        // now it is a goldmine problem
        int ans = (int)(1e8);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][0] == 1) {
                ans = Math.min(ans, shortestPathLandMines2_dfs(arr, i, 0, dirs));
            }
        }
        return ans - 1;
    }

    public static int shortestPathLandMines2_dfs(int[][] arr, int sr, int sc, int[][] dirs) {
        if (sc == arr[0].length - 1) return 1;
        int self = (int)(1e8);
        int temp = arr[sr][sc];
        arr[sr][sc] = 3;
        for (int[] d: dirs) {
            int dr = sr + d[0];
            int dc = sc + d[1];
            if (dr >= 0 && dc >= 0 && dr < arr.length && dc < arr[0].length && arr[dr][dc] == 1) {
                self = Math.min(self, shortestPathLandMines2_dfs(arr, dr, dc, dirs));
            }
        }
        arr[sr][sc] = temp;
        return self + 1;
    }

    // combinational sum
    // find all unique combinations in arr whose sum is equals x
    // infinite combination
    public static void combinationalSum(int[] arr, int tar, int idx, List<Integer> ans) {
        if (tar == 0) {
            System.out.println(ans);
            return;
        } 
        for (int i = idx; i < arr.length; i++) {
            int val = arr[i];
            if ((i == 0 && tar - val >= 0) || (i > 0 && val != arr[i - 1] && tar - val >= 0)) {
                ans.add(val);
                combinationalSum(arr, tar - val, i, ans);
                ans.remove(ans.size() - 1);
            }
        }
    }

    // Partition of a set into K subsets with equal sum
    public static boolean partitionOfSetIntoKSubsets(int[] arr, int k) {
        int sum = 0;
        for (int val: arr) sum += val;
        if (sum % k != 0 || k < 1 || k >= arr.length) {
            return false;
        }
        if (k == 1) {
            for (int val: arr) System.out.print(val + " ");
            return true;
        }
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            ans.add(new ArrayList<>());
        } 
        int[] subsetsum = new int[k];
        return partitionOfSetIntoKSubsets_rec(arr, 0, arr.length, k, subsetsum, 0, ans);
    }

    public static boolean partitionOfSetIntoKSubsets_rec(int[] arr, int idx, int n, int k, int[] subsetsum, int sssf, ArrayList<ArrayList<Integer>> ans) {
        // sssf is no non empty sets at any level or use of empty sets at any level
        if (idx >= arr.length) {
            if (sssf == k) {
                // ensure if we have divided into k subsets
                for (int i = 1; i < subsetsum.length; i++) {
                    if (subsetsum[i] != subsetsum[0]) return false;
                }
                System.out.println(ans);
                return true;
            }
            return false;
        }

        for (int i = 0; i < ans.size(); i++) {
            // add in non empty sets
            if (ans.get(i).size() > 0) {
                ans.get(i).add(arr[idx]);
                subsetsum[i] += arr[idx];
                boolean left = partitionOfSetIntoKSubsets_rec(arr, idx + 1, n, k, subsetsum, sssf, ans);
                ans.get(i).remove(ans.get(i).size() - 1);
                subsetsum[i] -= arr[idx];
                if (left) return true;

            } else {
                // add in empty sets
                ans.get(i).add(arr[idx]);
                subsetsum[i] += arr[idx];
                boolean right = partitionOfSetIntoKSubsets_rec(arr, idx + 1, n, k, subsetsum, sssf + 1, ans);
                ans.get(i).remove(ans.get(i).size() - 1);
                subsetsum[i] -= arr[idx];
                if (right) return true;
                break;
            }
        }
        return false;
    }

    // longest route in matrix of hurdles between source and destination
    public static int longestRoute(int[][] arr, int sr, int sc, int er, int ec) {
        int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        if (arr[sr][sc] != 1) {
            return -1;
        }
        int ans = longestRoute_rec(arr, sr, sc, er, ec, dirs);
        return ans - 1;
    }

    public static int longestRoute_rec(int[][] arr, int sr, int sc, int er, int ec, int[][] dirs) {
        if (sr == er && sc == ec) {
            return 1;
        }
        int temp = arr[sr][sc];
        // visited cell
        arr[sr][sc] = 2;
        int ans = 0;
        for (int[] d: dirs) {
            int dr = sr + d[0];
            int dc = sc + d[1];
            if (dr >= 0 && dc >= 0 && dr < arr.length && dc < arr[0].length && arr[dr][dc] == 1) {
                ans = Math.max(ans, longestRoute_rec(arr, dr, dc, er, ec, dirs));
            }
        }
        arr[sr][sc] = temp;
        return ans + 1;
    }

    // crypto Arithmatic
    //   s e n d  
    // + m o r e
    //------------
    // m o n e y

    public static void crypto(String a, String b, String c) {
        int[] map = new int[26];
        int[] charVsNum = new int[26];
        String abc = a + b + c;
        for (int i = 0; i < abc.length(); i++) {
            char ch = abc.charAt(i);
            map[ch - 'a']++;
        }
        String abc_uni = "";
        for (int i = 0; i < map.length; i++) {
            if (map[i] > 0) abc_uni += (char)(i + 'a');
        }
        boolean[] numUsed = new boolean[10];
        assignNumbers(abc_uni, a, b, c, 0, numUsed, charVsNum);
    }

    public static String extractStringNumber(int[] charVsNum, String abc, String x) {
        String ans = "";
        for (int i = 0; i < x.length(); i++) {
            char ch = x.charAt(i);
            ans += charVsNum[ch - 'a'];
        }
        return ans;
    }

    public static void assignNumbers(String abc, String a, String b, String c, int idx, boolean[] numUsed, int[] charVsNum) {
        if (idx >= abc.length()) {
            String one = extractStringNumber(charVsNum, abc, a);
            String two = extractStringNumber(charVsNum, abc, b);
            String three = extractStringNumber(charVsNum, abc, c);
            int num1 = Integer.parseInt(one);
            int num2 = Integer.parseInt(two);
            int num3 = Integer.parseInt(three);
            if (num1 + num2 == num3 && one.charAt(0) != '0' && two.charAt(0) != '0' && three.charAt(0) != '0') {
                System.out.println(num1 + "\n+" + num2 + "\n" + num3);
            }
            return;
        }
        
        char ch = abc.charAt(idx);
        for (int num = 0; num <= 9; num++) {
            if (!numUsed[num]) {
                numUsed[num] = true;
                charVsNum[ch - 'a'] = num;
                assignNumbers(abc, a, b, c, idx + 1, numUsed, charVsNum);
                numUsed[num] = false;
                charVsNum[ch - 'a'] = 0;
            }
        }
    }

    // match a pattern without using regular expression
    // GraphTreesGraph -> aba, GraphGraphGraph -> aaa, GeeksforGeeks -> gfg
    public static boolean matchStringAndPattern(String s, String p) {
        HashMap<Character, String> map = new HashMap<>();
        return matchStringAndPattern_rec(s, p, 0, 0, map);
        // s -> i, p -> j
    }

    public static boolean matchStringAndPattern_rec(String s, String p, int i, int j, HashMap<Character, String> map) {
        if (s.length() == 0 && j >= p.length()) return true;
        if (j >= p.length() || s.length() == 0) return false;
        
        char chp = p.charAt(j);
        if (map.containsKey(chp)) {
            String previousMapping = map.get(chp);
            // if ch is alredy mapped then check if the same string mapped is there in s or not
            if (s.length() >= previousMapping.length()) {
                String left = s.substring(0, previousMapping.length());
                String right = s.substring(previousMapping.length());
                if (left.equals(previousMapping)) {
                    // skip that part
                    boolean ans = matchStringAndPattern_rec(right, p, i, j + 1, map);
                    if (ans) return true;
                }
            }
        } else {
            for (int ptr = 0; ptr < s.length(); ptr++) {
                String left = s.substring(0, ptr + 1);
                String right = s.substring(ptr + 1);
                map.put(chp, left);
                boolean ans = matchStringAndPattern_rec(right, p, i, j + 1, map);
                if (ans) return true;
                map.remove(chp);
            }
        }
        return false;
    }

    // graphs related
    // Find if there is a path of more than k length from a source

    // hamiltonian cycle

    // tug of war
    // division of arr into 2 sets
    // print that set in which set1 sum - set2 sum is minm
    // set must have n / 2 elements 
    static int tugOfWar_min = (int)(1e8);
    static String tugOfWar_ans = "";
    public static void tugOfWar(int[] arr) {
        ArrayList<Integer> s1 = new ArrayList<>();          
        ArrayList<Integer> s2 = new ArrayList<>();  
        tugOfWar_rec(arr, 0, s1, s2, 0, 0);     
        System.out.println(tugOfWar_ans); 
    }
    public static void tugOfWar_rec(int[] arr, int idx, ArrayList<Integer> s1, ArrayList<Integer> s2, int sum1, int sum2) {
        if (idx >= arr.length) {
            int delta = Math.abs(sum1 - sum2);
            if (delta < tugOfWar_min) {
                tugOfWar_min = delta;
                tugOfWar_ans = s1 + " " + s2;
            }
            return;
        }
        
        if (s1.size() < (arr.length + 1) / 2) {
            s1.add(arr[idx]);
            tugOfWar_rec(arr, idx + 1, s1, s2, sum1 + arr[idx], sum2);
            s1.remove(s1.size() - 1);
        }
        if (s2.size() < (arr.length + 1) / 2) {
            s2.add(arr[idx]);
            tugOfWar_rec(arr, idx + 1, s1, s2, sum1, sum2 + arr[idx]);
            s2.remove(s2.size() - 1);
        }
    }

    // Find Maximum number possible by doing at-most K swaps
    public static void maxNumberPossibleByKSwaps(String s, int k) {
        char[] arr = s.toCharArray();
        maxNumberPossibleByKSwaps_ans = s;
        maxNumberPossibleByKSwaps_rec(arr, k);
        System.out.println(maxNumberPossibleByKSwaps_ans);
    }

    static String maxNumberPossibleByKSwaps_ans = "";

    public static void maxNumberPossibleByKSwaps_rec(char[] arr, int k) {
        if (k == 0) return;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] < arr[j]) {
                    char temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    if (String.valueOf(arr).compareTo(maxNumberPossibleByKSwaps_ans) > 0) {
                        maxNumberPossibleByKSwaps_ans = String.valueOf(arr);
                    }

                    maxNumberPossibleByKSwaps_rec(arr, k - 1);
                    // backtrack and revert the changes to try more swaps & combinations
                    char temp1 = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp1;
                }
            }
        }
    }

    // Find paths from corner cell to middle cell in maze(n X n)
    // we have to reach in the middle cell starting from extreme corners
    // we can move exactly n steps from a cell where n = value of cell
    public static void printPathsToMiddleOfCell(int[][] arr) {
        int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int n = arr.length;
        int sr = n - 1, sc = n - 1;
        String ans = "";
        ans += "(" + sr + ", " + sc + ") -> ";
        printPathsToMiddleOfCell_rec(arr, dirs, sr, sc, ans);
    }

    public static boolean printPathsToMiddleOfCell_rec(int[][] arr, int[][] dirs, int sr, int sc, String ans) {
        if (sr == arr.length / 2 && sc == arr.length / 2) {
            ans += "MID";
            System.out.println(ans);
            return false;
        }
        int jumps = arr[sr][sc];
        // mark cell as visited
        arr[sr][sc] = -1;
        for (int[] d: dirs) {
            int dr = sr + jumps * d[0];
            int dc = sc + jumps * d[1];
            if (dr >= 0 && dc >= 0 && dr < arr.length && dc < arr[0].length && arr[dr][dc] != -1) {
                boolean recAns = printPathsToMiddleOfCell_rec(arr, dirs, dr, dc, ans + "(" + dr + ", " + dc + ") -> ");
                if (!recAns) return false;
            }
            }
        arr[sr][sc] = jumps;
        return true;
    }

    /**
        5-year-old Shinchan had just started learning mathematics. Meanwhile, one of his studious classmates, Kazama, had already written a basic calculator which supports only three operations on integers: multiplication , addition , and subtraction . Since he had just learned about these operations, he didn't know about operator precedence, and so, in his calculator, all operators had the same precedence and were left-associative.
        As always, Shinchan started to irritate him with his silly questions. He gave Kazama a list of  integers and asked him to insert one of the above operators between each pair of consecutive integers such that the result obtained after feeding the resulting expression in Kazama's calculator is divisible by . At his core, Shinchan is actually a good guy, so he only gave lists of integers for which an answer exists.
        Can you help Kazama create the required expression? If multiple solutions exist, print any one of them.
     */

    public static void arithmeticExpressions(List<Integer> arr) {
        char[] optrs = {'+', '-', '*'};
        int mod = 101;
        // last optr is initiated with + because first value will be added in 0
        arithmeticExpressions_rec(arr, 0, optrs, "", mod, 0, '+');
        System.out.println(arithmeticExpressions_res);
    }

    static String arithmeticExpressions_res = "";

    public static boolean arithmeticExpressions_rec(List<Integer> arr, int idx, char[] optrs, String ans, int mod, int val, Character lastOptr) {
        if (idx >= arr.size()) {
            if (val % mod == 0) {
                // System.out.println(ans + " -> " + val);
                arithmeticExpressions_res = ans;
                return true;
            }
            return false;
        }

        int ele = arr.get(idx);
        if (lastOptr == '+') val += ele;
        else if (lastOptr == '-') val -= ele;
        else if (lastOptr == '*') val *= ele;

        if (idx < arr.size() - 1) {
            for (char ch: optrs) {
                boolean recAns1 = arithmeticExpressions_rec(arr, idx + 1, optrs, ans + ele + ch + "", mod, val, ch);
                if (recAns1) return true;
            }
        } else {
            boolean recAns2 = arithmeticExpressions_rec(arr, idx + 1, optrs, ans + ele + "", mod, val, lastOptr);
            if (recAns2) return true;
        }
        return false;
    }
    
    // crossword
    public static void crossWord(char[][] arr, String[] words, int idx) {
        if (idx == words.length) {
            for (char[] w: arr) {
                for (char val: w) {
                    System.out.print(val + " ");
                }
                System.out.println();
            }
            return;
        }
        
        String word = words[idx];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == '-' || arr[i][j] == word.charAt(0)) {
                    if (canPlaceH(arr, word, i, j)) {
                        boolean[] loc = placeH(arr, word, i, j);
                        crossWord(arr, words, idx + 1);
                        unPlaceH(arr, word, i, j, loc);
                    }

                    if (canPlaceV(arr, word, i, j)) {
                        boolean[] loc = placeV(arr, word, i, j);
                        crossWord(arr, words, idx + 1);
                        unPlaceV(arr, word, i, j, loc);
                    }
                }
            }
        }
    }

    public static boolean canPlaceH(char[][] arr, String word, int r, int c) {
        for (int i = 0; i < word.length(); i++) {
            if (c + i >= arr[0].length || (arr[r][c + i] != '-' && arr[r][c + i] != word.charAt(i))) return false;
        }
        return true;
    }

    public static boolean[] placeH(char[][] arr, String word, int r, int c) {
        boolean[] loc = new boolean[word.length()];
        for (int i = 0; i < word.length(); i++) {
            if (arr[r][c + i] == '-') {
                arr[r][c + i] = word.charAt(i);
                loc[i] = true;
            }
        }
        return loc;
    }

    public static void unPlaceH(char[][] arr, String word, int r, int c, boolean[] loc) {
        for (int i = 0; i < word.length(); i++) {
            if (loc[i]) {
                arr[r][c + i] = '-';
            }
        }
    }

    public static boolean canPlaceV(char[][] arr, String word, int r, int c) {
        for (int i = 0; i < word.length(); i++) {
            if (r + i >= arr.length || (arr[r + i][c] != '-' && arr[r + i][c] != word.charAt(i))) return false;
        }
        return true;
    }

    public static boolean[] placeV(char[][] arr, String word, int r, int c) {
        boolean[] loc = new boolean[word.length()];
        for (int i = 0; i < word.length(); i++) {
            if (arr[r + i][c] == '-') {
                arr[r + i][c] = word.charAt(i);
                loc[i] = true;
            }
        }
        return loc;
    }

    public static void unPlaceV(char[][] arr, String word, int r, int c, boolean[] loc) {
        for (int i = 0; i < word.length(); i++) {
            if (loc[i]) {
                arr[r + i][c] = '-';
            }
        }
    }

    public static void solve() {
        char[][] arr = {{'+', '-', '+', '+', '+', '+', '+', '+', '+', '+'},
        {'+', '-', '+', '+', '+', '+', '+', '+', '+', '+'},
        {'+', '-', '-', '-', '-', '-', '-', '-', '+', '+'},
        {'+', '-', '+', '+', '+', '+', '+', '+', '+', '+'},
        {'+', '-', '+', '+', '+', '+', '+', '+', '+', '+'},
        {'+', '-', '-', '-', '-', '-', '-', '+', '+', '+'},
        {'+', '-', '+', '+', '+', '-', '+', '+', '+', '+'},
        {'+', '+', '+', '+', '+', '-', '+', '+', '+', '+'},
        {'+', '+', '+', '+', '+', '-', '+', '+', '+', '+'},
        {'+', '+', '+', '+', '+', '+', '+', '+', '+', '+'}};

        String[] words = {"agra", "norway", "england", "gwalior"};

        crossWord(arr, words, 0);
        // System.out.println(ans);
    }

    public static void print2D(int[][] arr) {
        for (int[] a: arr) {
            for (int val: a) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
        System.out.println("--------------");
    }

    public static void main(String args[]) {
        solve();
    }
}