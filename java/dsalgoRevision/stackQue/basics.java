import java.util.*;
class basics {
    // duplicate brackets
    public static boolean isDuplicate(String s) {
        Stack<Character> st = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == ')') {
                if (st.peek() == '(') return true;
                else {
                    while(st.peek() != '(') {
                        st.pop();
                    }
                    // to remove (
                    st.pop();
                }
            } else {
                st.push(ch);
            }
        }
        return false;
    }

    // is balanced
    public static boolean isBalanced(String str) {
        Stack<Character> st = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '(' || ch == '{' || ch == '[') {
                st.push(ch);
            } else if (ch == ')') { 
                if (!processOpening(st, '(')) return false;
            } else if (ch == '}') {
                if (!processOpening(st, '{')) return false;
            } else if (ch == ']') {
                if (!processOpening(st, '[')) return false;
            }
        }
        // case of extra opening brackets
        return st.size() == 0;
    }

    public static boolean processOpening(Stack<Character> st, Character openBracket) {
        if (st.size() > 0 && st.peek() == openBracket) {
            // case extra closing bracket and mismatch of bracket
            st.pop();
            return true;
        } else {
            return false;
        }
    }

    // next greater element on right
    public static int[] ngor(int[] arr) {
        Stack<Integer> st = new Stack<>();
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            while (st.size() > 0 && arr[st.peek()] < arr[i]) {
                int idx = st.pop();
                ans[idx] = arr[i];
            } 
            st.push(i);
        }
        // left over elements which donn't have any greater element on right so -1 for them
        while (st.size() > 0) {
            int idx = st.pop();
            ans[idx] = -1;
        }
        return ans;
    }

    // stock span: difference of current position and nge on left's position 
    // if no nge on left then (idx + 1) will be the ans
    public static int[] stockSpan(int[] arr) {
        Stack<Integer> st = new Stack<>();
        int[] ans = new int[arr.length];
        for (int i = arr.length - 1; i >= 0; i--) {
            while (st.size() > 0 && arr[i] >= arr[st.peek()]) {
                int idx = st.pop();
                ans[idx] = idx - i;
            }
            st.push(i);
        }
        while (st.size() > 0) {
            int idx = st.pop();
            ans[idx] = idx + 1; 
        }
        return ans;
    }

    // largest area in histogram
    public static int[] nsol(int[] arr) {
        Stack<Integer> st = new Stack<>();
        int[] ans = new int[arr.length];
        for (int i = arr.length - 1; i >= 0; i--) {
            while (st.size() > 0 && arr[st.peek()] >= arr[i]) {
                int idx = st.pop();
                ans[idx] = i;
            } 
            st.push(i);
        }
        // left over elements which donn't have any greater element on right so -1 for them
        while (st.size() > 0) {
            int idx = st.pop();
            ans[idx] = -1;
        }
        return ans;
    } 

    public static int[] nsor(int[] arr) {
        Stack<Integer> st = new Stack<>();
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            while (st.size() > 0 && arr[st.peek()] >= arr[i]) {
                int idx = st.pop();
                ans[idx] = i;
                // as we are collecting boundaries 
            } 
            st.push(i);
        }
        // left over elements which donn't have any greater element on right so -1 for them
        while (st.size() > 0) {
            int idx = st.pop();
            ans[idx] = arr.length;
        }
        return ans;
    }

    public static void largestArea(int[] arr) {
        int[] lb = nsol(arr);
        int[] rb = nsor(arr); 
        int ans = -(int)(1e8);
        for (int i = 0; i < arr.length; i++) {
            int width = rb[i] - lb[i] - 1;
            int area = width * arr[i];
            ans = Math.max(ans, area);
        }
        System.out.println(ans);
    }

    // sliding window max
    public static int[] ngor(int[] arr) {
        Stack<Integer> st = new Stack<>();
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            while (st.size() > 0 && arr[i] > arr[st.peek()]) {
                ans[st.pop()] = i;
            }
            st.push(i);
        }
        while (st.size() > 0) {
            ans[st.pop()] = arr.length;
        }
        return ans;
    }

    public static void slidingWindow(int[] arr, int k) {
        int[] ans = ngor();
        // int[] max = new int[arr.length];
        for (int i = 0, j = 0; i <= arr.length - k; i++) {
            if (j < i) j = i;
            
            while (ans[j] < i + k) {
                j = ans[j];  
            }
            // max[i] = ans[j];
            System.out.println(arr[j]);
        }
    }

    // infix evaluation
    public static int infixEval(String str) {
        Stack<Integer> oprnd = new Stack<>();
        Stack<Character> optr = new Stack<>();

        for (int i = 0; i < str.length(); i++) {
            Character ch = str.charAt(i);
            if (ch - '0' >= 0 && ch - '0' <= 9) {
                oprnd.push(ch - '0');
            } else if (ch == '(') {
                optr.push(ch);
            } else if (ch == ')') {
                while (optr.peek() != '(') {
                    eval(oprnd, optr);
                }
                // now '(' is at the peek
                optr.pop();
            } else if (ch == '-' || ch == '*' || ch == '+' || ch == '/') {
                while (optr.size() > 0 && optr.peek() != '(' && (checkPrec(optr.peek()) >= checkPrec(ch))) {
                    eval(oprnd, optr);
                }
                optr.push(ch);
            }
        }
        while (optr.size() > 0) {
            eval(oprnd, optr);
        }
        return oprnd.pop();
    }

    public static int checkPrec(Character op) {
        if (op == '+' || op == '-') return 1;
        else if (op == '*' || op == '/') return 2;
        else return 0;
    }

    public static void eval(Stack<Integer> oprnd, Stack<Character> optr) {
        int v2 = oprnd.pop();
        int v1 = oprnd.pop();
        Character op = optr.pop();
        if (op == '+') oprnd.push(v1 + v2);
        else if (op == '-') oprnd.push(v1 - v2);
        else if (op == '*') oprnd.push(v1 * v2);
        else if (op == '/') oprnd.push(v1 / v2);
    }

    // infix conversions 
    // infix to prefix and postfix
    public static void infixConversion(String str) {
        Stack<String> post = new Stack<>();
        Stack<String> pre = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if ((ch - '0' >= 0 && ch - '0' <= 9) ||
                (ch >= 'a' && ch <= 'z') || 
                (ch >= 'A' && ch <= 'Z')) {
                post.push(ch + "");
                pre.push(ch + "");

            } else if (ch == '(') {
                ops.push(ch);

            } else if (ch == ')') {
                while (ops.size() > 0 && ops.peek() != '(') {
                    evalInfixConv(post, pre, ops);
                } 
                ops.pop();

            } else if (ch == '-' || ch == '+' || ch == '*' || ch == '/') {
                while (ops.size() > 0 && ops.peek() != '(' && checkPrec(ch) <= checkPrec(ops.peek())) {
                    evalInfixConv(post, pre, ops);
                }
                ops.push(ch);
            }
        }
        while (ops.size() > 0) {
            evalInfixConv(post, pre, ops);
        }
        System.out.println(post.pop());
        System.out.println(pre.pop());
    }

    public static void evalInfixConv(Stack<String> post, Stack<String> pre, Stack<Character> ops) {
        char ch = ops.pop();
        String v1_post = post.pop();
        String v2_post = post.pop();
        String v1_pre = pre.pop();
        String v2_pre = pre.pop();

        post.push(v2_post + v1_post + ch);
        pre.push(ch + v2_pre + v1_pre);
    }

    // postfix valuation and conversion
    public static void postFixEvalConv(String str) {
        Stack<Integer> val = new Stack<>();
        Stack<String> infix = new Stack<>();
        Stack<String> prefix = new Stack<>();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch - '0' >= 0 && ch - '0' <= 9) {
                infix.push(ch + "");
                prefix.push(ch + "");
                val.push(ch - '0');

            } else if (ch == '-' || ch == '+' || ch == '*' || ch == '/') {
                postFixEval(infix, prefix, val, ch);
            }
        }
        System.out.println(val.pop());
        System.out.println(infix.pop());
        System.out.println(prefix.pop());
    }

    public static void postFixEval(Stack<String> infix, Stack<String> prefix, Stack<Integer> val, char ch) {
        String val1_inf = infix.pop();
        String val2_inf = infix.pop();
        String val1_pre = prefix.pop();
        String val2_pre = prefix.pop();
        prefix.push(ch + val2_pre + val1_pre);
        infix.push("(" + val2_inf + ch + val1_inf + ")");
        int val1 = val.pop();
        int val2 = val.pop();
        if (ch == '-') val.push(val2 - val1);
        else if (ch == '+') val.push(val2 + val1);
        else if (ch == '*') val.push(val2 * val1);
        else if (ch == '/') val.push(val2 / val1);
    }

    // prefix eval and conversions
    public static void prefixEvalConv(String str) {
        Stack<Integer> val = new Stack<>();
        Stack<String> inf = new Stack<>();
        Stack<String> post = new Stack<>();

        for (int i = str.length() - 1; i >= 0; i--) {
            char ch = str.charAt(i);
            if (ch == '+' || ch == '-' || ch == '/' || ch == '*') {
                prefixEval(val, inf, post, ch);
            } else {
                val.push(ch - '0');
                inf.push(ch + "");
                post.push(ch + "");
            }
        }
        System.out.println(val.pop());
        System.out.println(inf.pop());
        System.out.println(post.pop());
    }

    public static void prefixEval(Stack<Integer> val, Stack<String> inf, Stack<String> post, char ch) {
        int val1 = val.pop();
        int val2 = val.pop();
        if (ch == '+') val.push(val1 + val2);
        else if (ch == '-') val.push(val1 - val2);
        else if (ch == '*') val.push(val1 * val2);
        else if (ch == '/') val.push(val1 / val2);

        String v1_inf = inf.pop();
        String v2_inf = inf.pop();
        String v1_post = post.pop();
        String v2_post = post.pop();

        inf.push("(" + v1_inf + ch + v2_inf + ")");
        post.push(v1_post + v2_post + ch);
    }

    // celebrity problem
    public static int celeb(int[][] arr) {
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < arr.length; i++) st.push(i);
        while (st.size() >= 2) {
            int i = st.pop();
            int j = st.pop();
            if (arr[i][j] == 1) {
                // i can't be celeb
                st.push(j);
            } else if (arr[i][j] == 0) {
                // as i don't knows j therefore j is not celeb
                st.push(i);
            }
        }

        int pot_ans = st.pop();
        for (int i = 0; i < arr.length; i++) {
            if (pot_ans != i) {
                if (arr[i][pot_ans] == 0 || arr[pot_ans][i] == 1) return -1;
            }
        }
        return pot_ans;
    }

    // merge overlapping intervals
    static class pair implements Comparable<pair>{
        int si;
        int ei;
        public pair (int si, int ei) {
            this.si = si;
            this.ei = ei;
        }

        public int compareTo (pair p) {
            if (this.si != p.si) return this.si - p.si;
            else return this.ei - p.ei;
        }
    }

    public static void mergeOverlapping(int[][] arr) {
        pair[] parr = new pair[arr.length];
        for (int i = 0; i < arr.length; i++) 
        parr[i] = new pair(arr[i][0], arr[i][1]); 
        Arrays.sort(parr);

        Stack<pair> st = new Stack<>();
        st.push(parr[0]);
        for (int i = 1; i < parr.length; i++) {
            pair cp = parr[i];
            pair pp = st.peek();

            if (cp.si <= pp.ei) {
                pp.ei = Math.max(pp.ei, cp.ei);
            } else {
                st.push(cp);
            }
        }
        Stack<pair> res = new Stack<>();
        while (st.size() > 0) res.push(st.pop());
        while (res.size() > 0) {
            pair cp = res.pop();
            System.out.println(cp.si + " " + cp.ei);
        }
    }

    // smallest integer with i and d
    public static int smallestInteger(String str) {
        Stack<Integer> st = new Stack<>();
        int count = 1;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == 'd') {
                st.push(count);
                count++;
            } else {
                st.push(count);
                count++;
                while (st.size() > 0) System.out.print(st.pop());
            }
        }
        st.push(count);
        while (st.size() > 0) System.out.print(st.pop());
    }

    // queue basics

    public static void solve() {  

    }

    public static void main(String args) {
       solve();
    }
}
