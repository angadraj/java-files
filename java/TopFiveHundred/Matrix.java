import java.util.*;
class Matrix {
    
    // Zigzag (or diagonal) traversal of Matrix
    public static void diagonalTravelgap2(int[][] arr) {
        for (int g = 0; g < arr[0].length; g++) {
            for (int i = g, j = 0; i >= 0; i--, j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    // diagonal Travel (r + c)
    public static void diagonalTravel(int[][] arr) {
        for (int g = 0; g < arr.length - 1; g++) {
            for (int i = g, j = 0; i >= 0; i--, j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        // last row diagonal is repeated twice therefore above loop ran one less time
        // for last diagonals i will not touch 0th row but j will touch last col
        for (int g = 0; g < arr[0].length; g++) {
            for (int i = arr.length - 1, j = g; j < arr[0].length; i--, j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Create a matrix with alternating rectangles of O and X
    // outer circle with X then next with O and so on
    // test case: 6, 7
    public static boolean[][] makeSpiralOX(int r, int c) {
        boolean[][] arr = new boolean[r][c];
        int rmin = 0, rmax = r - 1, cmin = 0, cmax = c - 1, total = r * c, count = 0;
        boolean flag = true;
        // true = X, false = O
        while (true) {
            for (int i = rmin; i <= rmax; i++) {
                if (flag) arr[i][cmin] = true;
                else arr[i][cmin] = false;
                count++;
            }
            cmin++;

            for (int j = cmin; j <= cmax; j++) {
                if (flag) arr[rmax][j] = true;
                else arr[rmax][j] = false;
                count++;
            }
            rmax--;

            for (int i = rmax; i >= rmin; i--) {
                if (flag) arr[i][cmax] = true;
                else arr[i][cmax] = false;
                count++;
            }
            cmax--;

            for (int j = cmax; j >= cmin; j--) {
                if (flag) arr[rmin][j] = true;
                else arr[rmin][j] = false;
                count++;
            }
            rmin++;
            flag ^= true;
            if (count == total) break;
        }
        return arr;
    }

    // Print all elements in sorted order from row and column wise sorted matrix
    static class pair {
        int i;
        int j;
        public pair (int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public static void printInSortedRCMatrix(int[][] arr) {
        PriorityQueue<pair> pq = new PriorityQueue<>((t, o) -> {
            return arr[t.i][t.j] - arr[o.i][o.j]; 
        });

        for (int i = 0; i < arr.length; i++) {
            pq.add(new pair(i, 0));
        }

        while (pq.size() > 0) {
            pair cp = pq.remove();
            System.out.print(arr[cp.i][cp.j] + " ");
            if (cp.j + 1 < arr[0].length) {
                pq.add(new pair(cp.i, cp.j + 1));
            }
        }
    }

    /// Inplace rotate square matrix by 90 degrees
    // n X n
    public static void swapInPlace(int[][] arr) {
        int n = arr.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n - i - 1; j++) {
                int temp = arr[i][j];
                arr[i][j] = arr[j][n - 1 - i];
                arr[j][n - 1 - i] = arr[n - 1 - i][n - 1 - j];
                arr[n - 1 - i][n - 1 - j] = arr[n - 1 - j][i];
                arr[n - 1 - j][i] = temp;
            }
        }

        for (int[] a: arr) {
            for (int val: a) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
    
    // count number of islands
    public static int countIslands(int[][] arr) {
        int count = 0;
        int[][] dirs = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == 1) {
                    countIslands_dfs(arr, i, j, dirs);
                    count++;
                }
            }
        }
        return count;
    }

    public static void countIslands_dfs(int[][] arr, int sr, int sc, int[][] dirs) {
        // mark;
        arr[sr][sc] = 2;

        for (int[] d: dirs) {
            int dr = d[0] + sr;
            int dc = d[1] + sc;
            if (dr >= 0 && dc >= 0 && dr < arr.length && dc < arr[0].length && arr[dr][dc] == 1) {
                countIslands_dfs(arr, dr, dc, dirs);
            }
        }
    }

    // Given a matrix of ‘O’ and ‘X’, replace ‘O’ with ‘X’ if surrounded by ‘X’
    public static void replaceOwithXIfSurroundedWithX(char[][] arr) {
        int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if ((i == 0 || i == arr.length - 1 || j == 0 || j == arr[0].length - 1) && arr[i][j] == 'O') {
                    replaceCharDfs(arr, i, j, dirs);
                }
            }
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == 'O') {
                    arr[i][j] = 'X';
                }
            }
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == '@') {
                    arr[i][j] = 'O';
                }
            }
        }
    }

    public static void replaceCharDfs(char[][] arr, int sr, int sc, int[][] dirs) {
        arr[sr][sc] = '@';
        for (int[] d: dirs) {
            int dr = sr + d[0];
            int dc = sc + d[1];
            if (dr >= 0 && dc >= 0 && dr < arr.length && dc < arr[0].length && arr[dr][dc] == 'O') {
                replaceCharDfs(arr, dr, dc, dirs);
            }
        }
    }

    // Validity of a given Tic-Tac-Toe board configuration 
    // board will be 1d 
    public static boolean ticTacToeValidation(char[] arr) {
        int count = 0, zeros = 0, cross = 0;
        for (char val: arr) {
            if (val == 'O') zeros++;
            else if (val == 'X') cross++;
            count++;
        }
        if ((count & 1) == 0 && (cross - zeros != 0)) {
            return false;
        } else if ((count & 1) != 0 && (cross - zeros != 1)) {
            return false;
        }

        // now check for multiple wins
        // 3H check for O wins or not 
        boolean ifCrossWon = isBoardValid(arr, 'X');
        boolean ifZeroWon = isBoardValid(arr, 'O');
        // if cross == 5 ie winning shot by cross then circle can't win
        if (cross == 5 && ifZeroWon) return false; 
        if (ifCrossWon && ifZeroWon) return false;
        return true;
    }

    public static boolean isBoardValid(char[] arr, char ch) {
         boolean horizontal = false;
        if ((arr[0] == ch && arr[1] == ch && arr[2] == ch) || 
            (arr[3] == ch && arr[4] == ch && arr[5] == ch) || 
            (arr[6] == ch && arr[7] == ch && arr[8] == ch)) {
                horizontal = true;
        }

        boolean vertical = false;
        if ((arr[0] == ch && arr[3] == ch && arr[6] == ch) || 
            (arr[1] == ch && arr[4] == ch && arr[7] == ch) || 
            (arr[2] == ch && arr[5] == ch && arr[8] == ch)) {
                vertical = true;
        }

        boolean diagonals = false;
        if ((arr[0] == ch && arr[4] == ch && arr[8] == ch) || 
            (arr[2] == ch && arr[4] == ch && arr[6] == ch)) {
            diagonals = true;
        }

        return (horizontal || vertical || diagonals);
    }

    // Given a matrix of ‘O’ and ‘X’, find the largest subsquare surrounded by ‘X’
    // irrespective of what is enclosed in boundary of X
    public static int largestSubsquare(char[][] arr) {
        int m = arr.length, n = arr[0].length;
        int[][] h = new int[m][n];
        int[][] v = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (arr[i][j] == 'O') {
                    h[i][j] = 0;
                    v[i][j] = 0;
                } else {
                    // for h = j - 1
                    h[i][j] = (j == 0) ? 1 : h[i][j - 1] + 1;
                    // for v = i - 1
                    v[i][j] = (i == 0) ? 1 : v[i - 1][j] + 1;
                }
            }
        }

        // as square will be found in middle part
        int ans = 0;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int min = Math.min(h[i][j], v[i][j]);
                while (min > ans) {
                    if (min <= h[i - min + 1][j] && min <= v[i][j - min + 1]) {
                        ans = Math.max(ans, min);
                    } 
                    min--;
                }
            }
        }
        return ans;
    }

    // Rotate Matrix Elements
    public static void rotateMatrixEle(int[][] arr) {
        int rmin = 0, rmax = arr.length - 1, cmin = 0, cmax = arr[0].length - 1;
        while (cmax > cmin && rmax > rmin) { 
            int temp = arr[rmin][cmax];
            // 1. top row -> left to right
            for (int j = cmax; j > cmin; j--) {
                arr[rmin][j] = arr[rmin][j - 1];
            }

            // 2. left col -> top to bottom
            for (int i = rmin; i < rmax; i++) {
                arr[i][cmin] = arr[i + 1][cmin];
            }

            // 3. last row -> left to right
            for (int j = cmin; j < cmax; j++) {
                arr[rmax][j] = arr[rmax][j + 1];
            }
            
            // 4. right col -> bottom to top
            for (int i = rmax; i >= rmin + 2; i--) {
                arr[i][cmax] = arr[i - 1][cmax];
            }
            // place temp
            arr[rmin + 1][cmax] = temp;
            rmin++; rmax--; cmin++; cmax--;
        }

        for (int[] a: arr) {
            for (int val: a) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    // find common elements in all rows in rowwise sorted matrix
    public static int findCommonEle(int[][] arr) {
        int r = arr.length, c = arr[0].length;
        int[] cols = new int[r];
        // fill this cols with last column index
        Arrays.fill(cols, c - 1);
        // travel on matrix and check if we find the elements, conditions of work
        // if we get enough elements ie count == r
        // and if cols arr has an idx which is already on 0
        int minrow = 0;
        while (cols[minrow] >= 0) {
            for (int i = 0; i < cols.length; i++) {
                if (arr[i][cols[i]] < arr[minrow][cols[minrow]]) {
                    minrow = i;
                }
            }
            int count = 0;
            for (int i = 0; i < cols.length; i++) {
                if (arr[minrow][cols[minrow]] > arr[i][cols[i]]) {
                    if (cols[i] == 0) return -1;
                    cols[i]--;
                } else {
                    count++;
                }
            }
            if (count == r) return arr[minrow][cols[minrow]];
        }
        return -1;
    }

    // Maximum size rectangle binary sub-matrix with all 1s
    public static int maxAreaOfHistogram(int[] arr) {
        Stack<Integer> st = new Stack<>();
        st.push(-1);
        int area = 0;
        for (int i = 0; i <= arr.length; i++) {
            int val = (i == arr.length) ? 0 : arr[i];
            while (st.peek() != -1 && val <= arr[st.peek()]) {
                int rb = i;
                int currVal = arr[st.pop()];
                int lb = st.peek();
                area = Math.max(area, currVal * (rb - lb - 1));
            }
            st.push(i);
        }
        return area;
    }

    public static int maxSizeRectangle(int[][] arr) {
        int r = arr.length, c = arr[0].length;
        int[] cols = new int[c];
        for (int j = 0; j < c; j++) {
            cols[j] = arr[0][j];
        }
        int ans = 0;
        ans = Math.max(ans, maxAreaOfHistogram(cols));
        for (int i = 1; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (arr[i][j] == 0) cols[j] = 0;
                else cols[j] += arr[i][j];
            }
            ans = Math.max(ans, maxAreaOfHistogram(cols));
        }
        return ans;
    }

    // Common elements in all rows of a given matrix
    // Given an m x n matrix, find all common elements 
    // present in all rows in O(mn) time and one traversal of matrix.
    public static void printCommonElementsInRows(int[][] arr) {
        int r = arr.length, c = arr[0].length;
        HashMap<Integer, Integer> map = new HashMap<>();
        // idea : distinct ele vs row no
        for (int j = 0; j < c; j++) {
            map.put(arr[0][j], 0);
        }
        for (int i = 1; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int val = arr[i][j];
                if (map.containsKey(val) && map.get(val) == (i - 1)) {
                    map.put(val, i);
                    // check if an element is present in all rows
                    if (map.get(val) == r - 1) {
                        System.out.println(val);
                    }
                }
            }
        }
    }

    // Find a specific pair in Matrix
    // Given an n x n matrix mat[n][n] of integers, find the maximum value of mat(c, d) – mat(a, b) 
    //over all choices of indexes such that both c > a and d > b.
    public static int specificPair(int[][] arr) {
        return 0;
    }

    // Find orientation of a pattern in a matrix
    public static void findPatternInMatrix(char[][] arr, String p) {
        int r = arr.length, c = arr[0].length;
        for (int i = 0; i < r; i++) {
            int idx = 0;
            for (int j = 0; j < c; j++) {
                if (p.charAt(idx) == arr[i][j]) {
                    idx++;
                } else {
                    idx = 0;
                }
                if (idx >= p.length()) {
                    System.out.println("Horizontal");
                    return;
                }
            }
        }

        for (int j = 0; j < c; j++) {
            int idx = 0;
            for (int i = 0; i < r; i++) {
                if (p.charAt(idx) == arr[i][j]) {
                    idx++;
                } else {
                    idx = 0;
                }
                if (idx >= p.length()) {
                    System.out.println("Vertical");
                    return;
                }
            }
        }
        System.out.println("Not found!");
    }

    public static int[] lps(String s) {
        int[] arr = new int[s.length()];
        int i = 1, len = 0;
        while (i < s.length()) {
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
        return arr;
    }

    public static boolean kmp(String s, String p) {
        String new_s = s + "#" + p;
        int[] arr = lps(new_s);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == p.length()) return true;
        }
        return false;
    }

    // find pattern in matrix
    public static void findPatternInMatrix2(char[][] arr, String p) {
        char[] colArr = new char[arr.length];
        for (int i = 0; i < arr.length; i++) {
            String row = String.valueOf(arr[i]);
            if (kmp(row, p)) {
                System.out.println("Horizontal");
                return;
            } 
            if (i <= arr[0].length) {
                for (int j = 0; j < arr.length; j++) {
                    colArr[j] = arr[j][i];
                }
                String col = String.valueOf(colArr);
                if (kmp(col, p)) {
                    System.out.println("Vertical");
                    return;
                }
            }
        }
        System.out.println("not found");
    }

    // Shortest path in a Binary Maze
    public static int shortestPath(int[][] arr, int sr, int sc, int er, int ec) {
        if (arr[sr][sc] != 1) return -1;
        int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        // int ans = shortestPath_rec(arr, sr, sc, dirs, er, ec) - 1;
        // if (ans >= (int)(1e8)) {
        //     System.out.println("no path");
        //     return -1;
        // }
        int ans = shortestPath_bfs(arr, sr, sc, er, ec, dirs);
        return ans;
        // - 1 is done to exculde start point as we need to find the steps;
    }

    public static int shortestPath_rec(int[][] arr, int sr, int sc, int[][] dirs, int er, int ec) {
        // 1. mark
        if (sr == er && sc == ec) {
            return 1;
        }
        int count = (int)(1e8);
        arr[sr][sc] = -1;
        for (int[] d: dirs) {
            int dr = d[0] + sr;
            int dc = d[1] + sc;
            if (dr >= 0 && dc >= 0 && dr < arr.length && dc < arr[0].length && arr[dr][dc] == 1) {
                int ans = shortestPath_rec(arr, dr, dc, dirs, er, ec);
                count = Math.min(count, ans);
            }
        }
        arr[sr][sc] = 1;
        return count + 1;
    }

    static class bfsPair {
        int pt;
        int dist;
        public bfsPair(int pt, int dist) {
            this.pt = pt;
            this.dist = dist;
        }
    }    

    public static int shortestPath_bfs(int[][] arr, int sr, int sc, int er, int ec, int[][] dirs) {
        Queue<bfsPair> qu = new LinkedList<>();

        int r = arr.length, c = arr[0].length;

        boolean[] vis = new boolean[r * c];
        qu.add(new bfsPair(sr * c + sc, 0));
        vis[sr * c + sc] = true;

        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                bfsPair cp = qu.remove();
                int x = cp.pt / c;
                int y = cp.pt % c;
                if (x == er && y == ec) return cp.dist;

                for (int[] d: dirs) {
                    int dr = x + d[0];
                    int dc = y + d[1];
                    if (dr >= 0 && dc >= 0 && dr < arr.length && dc < arr[0].length && arr[dr][dc] == 1 && !vis[dr * c + dc]) {
                        vis[dr * c + dc] = true;
                        qu.add(new bfsPair(dr * c + dc, cp.dist + 1));
                    } 
                }
            }
        }
        return -1;
    }

    // inplace rotate a square matrix by 90 deg anticlockwise
    public static void rotateAntiClockWise(int[][] arr) {
        int n = arr.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n - i - 1; j++) {
                int temp = arr[i][j];
                arr[i][j] = arr[j][n - 1 - i];
                arr[j][n - 1 - i] = arr[n - 1 - i][n - 1 - j];
                arr[n - 1 - i][n - 1 - j] = arr[n - 1 - j][i];
                arr[n - 1 - j][i] = temp;
            }
        }
        for (int[] a: arr) {
            for (int val: a) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    // Kth smallest element in a row-wise and column-wise sorted 2D array
    // expected time: klgn, space : n
    public static int kthSmallestSortedRCMatrix(int[][] arr, int k) {
        // int[] : i, j
        int n = arr.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((t, o) -> {
            int ti = t[0], tj = t[1], oi = o[0], oj = o[1];
            return arr[ti][tj] - arr[oi][oj];
        });
        for (int i = 0; i < n; i++) {
            pq.add(new int[]{i, 0});
        }
        int ans = -1;
        while (k > 0 && pq.size() > 0) {
            int[] cp = pq.remove();
            int x = cp[0], y = cp[1];
            ans = arr[x][y];
            if (y + 1 < n) {
                pq.add(new int[]{x, y + 1});
            }
            k--;
        }
        return ans;
    }

    // A Boolean Matrix Question
    // Given a boolean matrix mat[M][N] of size M X N, modify it such that if a matrix 
    // cell mat[i][j] is 1 (or true) then make all the cells of ith row and jth column as 1. 
    public static void booleanMatrixHelper(int[][] arr) {
        int r = arr.length, c = arr[0].length;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (arr[i][j] == 1) list.add(i * c + j);
            }
        }
        // if all are ones
        if (list.size() == (r * c)) return;
        for (int k = 0; k < list.size(); k++) {
            int cord = list.get(k);
            int x = cord / c, y = cord % c;
            for (int i = 0; i < r; i++) {
                arr[i][y] = 1;
            }

            for (int j = 0; j < c; j++) {
                arr[x][j] = 1;
            }
        }
    }

    public static void booleanMatrixHelper2(int[][] arr) {
        int r = arr.length, c = arr[0].length;
        boolean[] row = new boolean[r];
        boolean[] col = new boolean[c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (arr[i][j] == 1) {
                    row[i] = true;
                    col[j] = true;
                }
            }
        }

        for (int i = 0; i < row.length; i++) {
            if (row[i]) {
                for (int j = 0; j < c; j++) {
                    arr[i][j] = 1;
                }
            }
        }

        for (int j = 0; j < col.length; j++) {
            if (col[j]) {
                for (int i = 0; i < r; i++) {
                    arr[i][j] = 1;
                }
            }
        }
    }

    public static void booleanMatrixHelper3(int[][] arr) {
        boolean fr = false, fc = false;
        int r = arr.length, c = arr[0].length;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (i == 0 && arr[i][j] == 1) fr = true;
                if (j == 0 && arr[i][j] == 1) fc = true;
                if (arr[i][j] == 1) {
                    arr[0][j] = 1;
                    arr[i][0] = 1;
                }
            }
        }

        for (int i = 1; i < r; i++) {
            for (int j = 1; j < c; j++) {
                if (arr[i][0] == 1 || arr[0][j] == 1) {
                    arr[i][j] = 1;
                }
            }
        }

        // now handle row 0 and col 0
        if (fr) {
            for (int j = 0; j < c; j++) {
                arr[0][j] = 1;
            }
        }
        if (fc) {
            for (int i = 0; i < r; i++) {
                arr[i][0] = 1;
            }
        }
    }

    // Search a Word in a 2D Grid of characters
    // we can search a word in given grid in all 8 dirs
    // dfs
    public static void searchAWordInGrid(char[][] arr, String p) {
        int[][] dirs = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        int r = arr.length, c = arr[0].length;
        ArrayList<int[]> ans = new ArrayList<>();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (p.charAt(0) == arr[i][j] && searchAWordInGrid_dfs(arr, 0, dirs, p, i, j)) {
                    // arr, p_idx, 
                    System.out.println(i + ", " + j);
                    ans.add(new int[]{i, j});
                }
            }
        }
        return ans.toArray(new int[ans.size()][]);
    }

    public static boolean searchAWordInGrid_dfs(char[][] arr, int idx, int[][] dirs, String p, int sr, int sc) {
        if (idx == p.length() - 1) {
            return true;
        }
        char temp = arr[sr][sc];
        arr[sr][sc] = '0';
        boolean ans = false;
        for (int[] d: dirs) {
            int dr = sr + d[0];
            int dc = sc + d[1];
            if (dr >= 0 && dc >= 0 &&
                dr < arr.length && 
                dc < arr[0].length && 
                arr[dr][dc] != '0' && 
                idx + 1 < p.length() && 
                p.charAt(idx + 1) == arr[dr][dc]) {
                    ans = searchAWordInGrid_dfs(arr, idx + 1, dirs, p, dr, dc);
            }
        }
        arr[sr][sc] = temp;
        return ans;
    }

    public static void solve() {
        char[][] arr = {{'a', 'b', 'a', 'b'},
              {'a', 'b', 'e', 'b'},
              {'e', 'b', 'e', 'b'}};
        searchAWordInGrid(arr, "abe");
        // System.out.println(ans);
    }
    
    public static void main(String args[]) {
        solve();
    }
}