import java.util.*;
class stackQueue {

    // implement two stacks in an array
    // done in gfg

    // implement a stack that performs operations for middle element
    static class Dll {
        Dll next = null, prev = null;
        int val;

        public Dll(int val) {
            this.val = val;
        }
    }

    static class midOperationStack {
        int size = 0; 
        Dll head = null, mid = null;

        public void push(int x) {
            Dll nn = new Dll(x);
            if (this.head == null && this.mid == null) {
                this.head = nn;
                this.mid = nn;
            } else {
                this.head.next = nn;
                nn.prev = head;
                this.head = nn;
            }
            this.size++;
            if (this.size % 2 == 0) {
                this.mid = this.mid.next;
            } 
        } 

        public int pop() {
            if (this.head == null && this.mid == null) {
                return -1;
            }
            
            if (this.size == 1) {
                int rv = this.head.val;
                this.head = null;
                this.mid = null;
                this.size--;
                return rv;
            } 

            Dll temp = this.head;
            this.head = this.head.prev;
            this.head.next = null;
            temp.prev = null;
            this.size--;
            if (this.size % 2 == 1) {
                this.mid = this.mid.prev;
            }
            return temp.val;
        }

        public int getMiddle() {
            if (this.head == null && this.mid == null) return -1;
            return this.mid.val;
        }

        public int deleteMiddle() {
            if (this.head == null && this.mid == null) return -1;
            int rv = this.mid.val;
            Dll nodeBeforeMid = this.mid.prev;
            Dll nodeAfterMid = this.mid.next;
            this.mid.next = null;
            this.mid.prev = null;
            nodeBeforeMid.next = nodeAfterMid;
            nodeAfterMid.prev = nodeBeforeMid;
            this.size--;
            if (this.size % 2 == 1) {
                this.mid = nodeBeforeMid;
            }
            return rv;
        }

        public void print() {
            Dll temp = this.head;
            while (temp != null) {
                System.out.print(temp.val + " ");
                temp = temp.prev;
            }
        }
    }
    
    // implement k stacks in an array
    static class kStacks {
        int[] arr;
        int next[];
        int top[];

        int free;

        public kStacks(int n, int k) {
            arr = new int[n];
            next = new int[n];
            top = new int[k];

            // initialize all stacks as empty
            for (int i = 0; i < top.length; i++) top[i] = -1;
            // initialize all spaces as free and link them 
            for (int i = 0; i < next.length; i++) {
                if (i < next.length - 1) next[i] = i + 1;
                else next[i] = -1;
            }
        }

        public boolean isFull() {
            return (free == -1);
        }

        public boolean isEmpty(int sn) {
            return (top[sn] == -1);
        }

        public void push(int val, int sn) {
            if (this.isFull()) {
                System.out.println("overflow");
                return;
            }
            int currFree = free;
            free = next[currFree];
            next[currFree] = top[sn];
            top[sn] = currFree;
            arr[currFree] = val;
        }

        public int pop(int sn) {
            if (isEmpty(sn)) {
                System.out.println("underflow");
                return -1;
            }
            int currTop = top[sn];
            top[sn] = next[currTop];
            // free the space
            next[currTop] = free;
            free = currTop;
            return arr[currTop];
        }
    }

    // How to implement stack using priority queue or heap?
    static class stackByHeap {
        PriorityQueue<int[]> pq;
        // in[]{} = {ele, count}
        int count;

        public stackByHeap() {
            pq = new PriorityQueue<>((t, o) -> {
                return o[1] - t[1];
            });
            count = 0;
        }

        public void push(int val) {
            this.pq.add(new int[]{val, this.count});
            this.count++;
        }

        public int pop() {
            if (this.pq.size() == 0) {
                System.out.println("Underflow");
                return -1;
            }
            int[] rv = pq.remove();
            return rv[0];
        }

        public int peek() {
            if (this.pq.size() == 0) {
                System.out.println("Underflow");
                return -1;
            }
            int[] rv = pq.peek();
            return rv[0];
        }

        public int size() {
            return this.pq.size();
        }
    }

    // infix to postfix
    public static void infixToPostFix(String s) {
        Stack<String> nums = new Stack<>();
        Stack<Character> optrs = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) nums.push(ch + "");
            else if (ch == '(') optrs.push(ch);
            else if (ch == ')') {
                while (optrs.size() > 0 && optrs.peek() != '(') {
                    String num2 = nums.pop();
                    String num1 = nums.pop();
                    char op = optrs.pop();
                    String ans = num1 + num2 + op + "";
                    nums.push(ans);
                }   
                // remove '('
                optrs.pop();
            } else if (ch == '^' || ch == '*' || ch == '/' || ch == '+' || ch == '-') {
                while (optrs.size() > 0 && optrs.peek() != '(' && getPred(optrs.peek()) >= getPred(ch)) {
                    String num2 = nums.pop();
                    String num1 = nums.pop();
                    char op = optrs.pop();
                    String ans = num1 + num2 + op + "";
                    nums.push(ans);
                }
                optrs.push(ch);
            }
        }
        while (optrs.size() > 0) {
            String num2 = nums.pop();
            String num1 = nums.pop();
            char op = optrs.pop();
            String ans = num1 + num2 + op + "";
            nums.push(ans);
        }

        System.out.println(nums.peek());
    }

    public static int getPred(char ch) {
        if (ch == '^') return 3;
        else if (ch == '*' || ch == '|') return 2;
        else if (ch == '+' || ch == '-') return 1;
        else return -1;
    }

    // the celebrity problem
    public static int celebrityProblem(int[][] arr) {
        Stack<Integer> st = new Stack<>();
        int n = arr.length;
        for (int i = 0; i < n; i++) st.push(i);
        // when you will pop i and j , you will be eliminating options which couldn't be 
        // the desired ans
        while (st.size() >= 2) {
            int i = st.pop();
            int j = st.pop();
            if (arr[i][j] == 1) {
                // i knows j -> j could be a celeb
                st.push(j);
            } else {
                // i doesn't knows j -> i could be a celeb
                st.push(i);
            }
        }
        int potIdx = st.pop();
        for (int i = 0; i < n; i++) {
            if (i != potIdx) {
                if (arr[i][potIdx] == 0 || arr[potIdx][i] == 1) return -1;
            }
        }
        return potIdx;
    }

    // tower of hanoi iterative: from gfg

    // min stack 
    static class minStack {
        Stack<Integer> st;
        int min;

        public minStack() {
            st = new Stack<>();
            min = 0;
        }

        public int pop() {
            if (st.size() == 0) {
                return -1;
            }
            int rv = st.pop();
            if (rv < min) {
                int nextMin = 2 * min - rv;
                rv = min;
                min = nextMin;
            }
            return rv;
        }

        public void push(int x) {
            if (st.size() == 0) {
                st.push(x);
                min = x;
            } else if (st.size() > 0 && x > min) {
                st.push(x);
            } else if (st.size() > 0 && x < min) {
                int falseVal = 2 * x - min;
                min = x;
                st.push(falseVal);
            }
        }

        public int getMin() {
            return min;
        }

        public int peek() {
            if (st.size() == 0) return -1;
            int rv = st.peek();
            if (rv > min) return rv;
            else return min;
        }
    }

    // Find maximum of minimum for every window size in a given array
    public static void maxOfTheMinOfAllWindowSizes(int[] arr) {
        int[] left = nsolOrNsor(arr, true);
        int[] right = nsolOrNsor(arr, false);

        int[] ans = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            int len = right[i] - left[i] - 1;
            ans[len] = Math.max(arr[i], ans[len]);
        }
        for (int i = arr.length - 1; i >= 1; i--) {
            ans[i] = Math.max(ans[i], ans[i + 1]);
        }
        for (int val: ans) System.out.print(val + " ");
    }

    public static int[] nsolOrNsor(int[] arr, boolean check) {
        int n = arr.length;
        int[] dp = new int[n];
        // if check is true we will return nsor, else return nsol;
        Stack<Integer> st = new Stack<>();
        if (!check) {
            // nsor
            for (int i = 0; i < n; i++) {
                while (st.size() > 0 && arr[i] < arr[st.peek()]) {
                    int idx = st.pop();
                    dp[idx] = i;
                }
                st.push(i);
            }
            while (st.size() > 0) {
                dp[st.pop()] = n;
            } 

        } else {
            // nsol
            for (int i = n - 1; i >= 0; i--) {
                while (st.size() > 0 && arr[i] < arr[st.peek()]) {
                    int idx = st.pop();
                    dp[idx] = i;
                }
                st.push(i);
            }
            while (st.size() > 0) {
                dp[st.pop()] = -1;
            } 
        }
        return dp;
    }

    // check if an expression has duplicate brackets or not
    // the experessio is valid
    public static boolean ifDupsInExpression(String s) {
        Stack<Character> st = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == ')') {
                if (st.peek() == '(') return true;
                else {
                    while (st.size() > 0 && st.peek() != '(') {
                        st.pop();
                    }
                    st.pop();
                }
            } else {
                st.push(ch);
            }
        }
        return false;
    }

    static class LRU {
        HashMap<Integer, Node> map;
        int size, maxSize;
        Node head = null, tail = null;

        static class Node {
            int key, val;
            Node prev = null, next = null;

            public Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        public void removeNode(Node node) {
            if (this.size == 1) {
                this.head = null;
                this.tail = null;
            } else if (this.head == node) {
                // least recent used one
                Node temp = this.head;
                this.head = temp.next;
                temp.next = null;
                this.head.prev = null;

            } else if (this.tail == node) {
                // recent used
                Node temp = this.tail;
                this.tail = temp.prev;
                this.tail.next = null;
                temp.prev = null;

            } else {
                Node prevNode = node.prev;
                Node nextNode = node.next;
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
                node.next = null;
                node.prev = null;
            }
            this.size--;
        }

        public void addLast(Node node) {
            if (this.head == null && this.tail == null) {
                this.head = this.tail = node;
            } else {
                this.tail.next = node;
                node.prev = this.tail;
                this.tail = node;
            }
            this.size++;
        }

        public LRU(int cap) {
            this.map = new HashMap<>();  
            this.maxSize = cap;
            this.size = 0;
        }

        public int get(int key) {
            if (this.map.containsKey(key)) {
                // 1. get the node
                Node cn = map.get(key);
                // 2. remove the node from cache
                removeNode(cn);
                // 3. add it at last (make it recent used)
                addLast(cn);
                return cn.val;

            } else {
                return -1;
            }
        }

        public void put(int key, int value) {
            if (!this.map.containsKey(key)) {
                // 1. create a new key
                Node nn = new Node(key, value);
                // add to map
                this.map.put(key, nn);
                // 2. add it to last to make it recent
                addLast(nn);
                // 3. check size
                if (this.size > this.maxSize) {
                    // 4. remove the node from front ie head
                    Node cn = this.map.get(this.head.key);
                    removeNode(cn);
                    map.remove(cn.key);
                }
            } else {
                // 1. take the node and reorder it
                Node cn = this.map.get(key);
                removeNode(cn);
                addLast(cn);
                // 2. now check if the value needs to update or not
                if (cn.val != value) cn.val = value;
            }
        }
    }   

    // How to efficiently implement k Queues in a single array?
    static class nQueInArr {
        int n, k, free;
        int[] arr, next, front, rear;

        public nQueInArr(int n, int k) {
            this.n = n;
            this.k = k;
            arr = new int[n];
            next = new int[n];
            front = new int[k];
            rear = new int[k];
            free = 0;
            for (int i = 0; i < n - 1; i++) next[i] = i + 1;
            next[n - 1] = -1;
            for (int i = 0; i < k; i++) front[i] = rear[i] = -1;
        }

        public boolean isEmpty(int qn) {
            return (front[qn] == -1);
        }

        public boolean isFull() {
            return (free == -1);
        }

        public void add(int val, int qn) {
            if (this.isFull()) {
                System.out.println("overlfow");
                return;
            }
            int nextFree = next[free];

            if (this.isEmpty(qn)) {
                rear[qn] = front[qn] = free;
            } else {
                next[rear[qn]] = free;
                rear[qn] = free;
            }
            next[free] = -1;
            arr[free] = val;
            free = nextFree;
        }

        public int remove(int qn) {
            if (this.isEmpty(qn)) {
                System.out.println("underfow");
                return -1;
            }
            int frontIdx = front[qn];
            front[qn] = next[frontIdx];
            next[frontIdx] = free;
            free = frontIdx;
            return arr[frontIdx];
        }
    }

    // Find the first circular tour that visits all petrol pumps
    public static int firstCircularTour(int[] p, int[] s) {
        int si = 0, cp = 0, n = p.length;
        for (int i = 0; i < n; i++) {
            if (p[i] >= s[i]) {
                si = i;
                break;
            }
        }
        int i = si;
        while (i < n) {
            cp += p[i] - s[i];
            if (cp < 0) {
                i++;
                while (i < n) {
                    if (p[i] >= s[i]) {
                        si = i;
                        cp = 0;
                        break;
                    }
                    i++;
                }

            } else {
                i++;
            }
        }
        // assume from any pump the journey has been completed if
        // below is not satisfied
        if (cp < 0) return -1;
        // from point p we have reached n, now check if it is possible to 
        // reach from 0th index to that point
        for (int j = 0; j < si; j++) {
            cp += p[j] - s[j];
            if (cp < 0) return -1;
        }
        return si;
    }

    // rot oranges
    // graph ques
    public static int rotOranges(int[][] arr) {
        int rows = arr.length, cols = arr[0].length;
        int freshCount = 0;
        Queue<Integer> qu = new LinkedList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (arr[i][j] == 1) freshCount++;
                else if (arr[i][j] == 2) {
                    qu.add(i * cols + j);
                }
            }
        }
        if (freshCount == 0) return 0;
        int time = 0;
        int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                int cord = qu.remove();
                int sr = cord / cols;
                int sc = cord % cols;

                for (int[] d: dirs) {
                    int dr = sr + d[0];
                    int dc = sc + d[1];
                    if (dr >= 0 && dc >= 0 && dr < rows && dc < cols && arr[dr][dc] == 1) {
                        freshCount--;
                        arr[dr][dc] = 2;
                        qu.add(dr * cols + dc);

                        if (freshCount == 0) {
                            time++;
                            return time;
                        }
                    }
                }
            }
            time++;
        }
        return -1;
    }

    // Sum of minimum and maximum elements of all subarrays of size k.
    // time: nlogn, space : n
    public static int sumMinMaxOfAllSubArrSizeK(int[] arr, int k) {
        int n = arr.length;
        PriorityQueue<Integer> min = new PriorityQueue<>();
        PriorityQueue<Integer> max = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i < k; i++) {
            min.add(arr[i]);
            max.add(arr[i]);
        }
        // check for min
        int gMin = min.peek(), gMax = max.peek();
        for (int i = k; i <= n - k; i++) {
            if (min.peek() == arr[i - k]) {
                min.remove();
            }
            min.add(arr[i]);
            gMin += min.peek();
        }
        for (int i = k; i <= n - k; i++) {
            if (max.peek() == arr[i - k]) {
                max.remove();
            }
            max.add(arr[i]);
            gMax += max.peek();
        }
        return (gMin + gMax);
    }

    // ######################## SLIDING WINDOW MAX/MIN (**IMPORTANT**) #################
    // used optimal apprach

    // sliding window maximum; optimal approach
    // time: n, space: k
    public static int slidingWindowMax(int[] arr, int k) {
        int n = arr.length;
        // int[] res = new int[n - k + 1];
        // int idx = 0;
        int sum = 0;
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        // peek: gets first value, does not remove it
        // peekLast: gets last value, does not remove it
        for (int i = 0; i < n; i++) {
            // check for out of bounds
            if (dq.size() != 0 && dq.peek() == i - k) {
                dq.removeFirst();
            }
            // as we are making qu in decreasing order, therefore  
            while (dq.size() != 0 && arr[dq.peekLast()] <= arr[i]) {
                dq.removeLast();
            }
            dq.addLast(i);
            if (i >= k - 1) {
                // res[idx++] = arr[dq.peek()];
                sum += arr[dq.peek()];
            }
        }
        // for (int val: res) System.out.print(val + " ");
        return sum;
    }

    public static int slidingWindowMin(int[] arr, int k) {
        int n = arr.length;
        // int[] res = new int[n - k + 1];
        // int idx = 0;
        int sum = 0;
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        // peek: gets first value, does not remove it
        // peekLast: gets last value, does not remove it
        for (int i = 0; i < n; i++) {
            // check for out of bounds
            if (dq.size() != 0 && dq.peek() == i - k) {
                dq.removeFirst();
            }
            // as we are making qu in increasing order, therefore  
            while (dq.size() != 0 && arr[dq.peekLast()] >= arr[i]) {
                dq.removeLast();
            }
            dq.addLast(i);
            if (i >= k - 1) {
                // res[idx++] = arr[dq.peek()];
                sum += arr[dq.peek()];
            }
        }
        // for (int val: res) System.out.print(val + " ");
        return sum;
    }

    public static int sumMinMaxOfAllSubArrSizeK_opti(int[] arr, int k) {
        int min = slidingWindowMin(arr, k);
        int max = slidingWindowMax(arr, k);
        return min + max;
    }
    
    // stack permutation
    // basically, we are given an array and we need to tell that if we can convert it 
    // to output array when we are only allowed to use a stack for the push/pop
    public static boolean stackPermutation(int[] ip, int[] op) {
        Queue<Integer> input = new LinkedList<>();
        Queue<Integer> output = new LinkedList<>();
        for (int val: ip) input.add(val);
        for (int val: op) output.add(val);

        Stack<Integer> st = new Stack<>();
        while (input.size() > 0) {
            // remove from front
            int ele = input.poll();
            if (ele == output.peek()) {
                output.poll();
                while (st.size() > 0) {
                    if (st.peek() == output.peek()) {
                        st.pop();
                        output.poll();
                    } else break;
                }
            } else {
                st.push(ele);
            }
        }
        return (input.size() == 0 && st.size() == 0);
    }

    // Count natural numbers whose all permutation are greater than that number
    public static int naturalNumPermutationGreaterThanNum(int n) {
        Stack<Integer> st = new Stack<>();
        int count = 0;
        for (int i = 1; i <= 9; i++) {
            if (i <= n) {
                st.push(i);
                // so that we start adding digits to it, to form an increasing sequence
                count++;
            }
            while (st.size() > 0) {
                int topel = st.pop();
                for (int j = topel % 10; j <= 9; j++) {
                    int x = topel * 10 + j;
                    if (x <= n) {
                        // push it in stack to form a seq from that
                        st.push(x);
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // sort a stack using recursion
    // top to bottom: decreasing order
    public static void sortStack(Stack<Integer> s) {
        if (s.size() > 0) {
            int x = s.pop();
            sortStack(s);
            sortedInsert(s, x);
        }
    }

    public static void sortedInsert(Stack<Integer> s, int val) {
        if (s.size() == 0 || val > s.peek()) {
            s.push(val);
            return;
        }
        int x = s.pop();
        sortedInsert(s, val);
        s.push(x);
    }

    // postfix evaluation
    public static int postFixEval(String s) {
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') {
                st.push(ch - '0');
            } else {
                int val2 = st.pop();
                int val1 = st.pop();
                evaluatePostFix(st, val1, val2, ch);
            }
        }
        return st.pop();
    }

    public static void evaluatePostFix(Stack<Integer> st, int val1, int val2, char ch) {
        if (ch == '+') st.push(val1 + val2);
        else if (ch == '-') st.push(val1 - val2);
        else if (ch == '*') st.push(val1 * val2);
        else st.push(val1 / val2);
    }

    // Queue based approach for first non-repeating character in a stream
    public static String firstNonRepInStream(String s) {
        int[] map = new int[26];
        StringBuilder sb = new StringBuilder();
        Queue<Character> qu = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            map[ch - 'a']++;
            qu.add(ch);
            // now we have to find last non repeating char
            while (qu.size() > 0) {
                if (map[qu.peek() - 'a'] > 1) qu.remove();
                else {
                    sb.append(qu.peek());
                    break;
                }
            }
            if (qu.size() == 0) {
                sb.append("#");
            }
        }
        return sb.toString();
    }

    public static void solve() {
        String s = "aabc";
        String ans = firstNonRepInStream(s);
        System.out.println(ans);
    }
    
    public static void main(String args[]) {
        solve();
    }
}