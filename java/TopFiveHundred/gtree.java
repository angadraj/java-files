import java.util.*;
class gtree {
    
    static class Node {
        int val;
        ArrayList<Node> children = new ArrayList<>();
        public Node(int val) {
            this.val = val;
        }
    }

    // check if n-ary tree is mirror
    public static boolean isNaryTreeMirror(int[] t1, int[] t2) {
        int n = t1.length;
        HashMap<Integer, Stack<Integer>> map = new HashMap<>();
        for (int i = 0; i <= n - 2; i += 2) {
            int u = t1[i], v = t1[i + 1];
            if (!map.containsKey(u)) {
                map.put(u, new Stack<>());
            }
            map.get(u).push(v);
        }
        for (int i = 0; i <= n - 2; i += 2) {
            int u = t2[i], v = t2[i + 1];
            if (map.containsKey(u) && map.get(u).size() > 0) {
                if (map.get(u).peek() != v) {
                    return false;
                } else {
                    map.get(u).pop();
                }
            }
        }
        return true;
    }

    // serialize
    public static void serialize(Node root, StringBuilder sb) {
        sb.append(root.val + " ");
        for (Node child: root.children) {
            serialize(child, sb);
        }
        sb.append("n ");
    }

    public static Node deserialize(String s) {
        if (s.length() == 0) return null;
        // the string u will get is of kind: left child right child null
        String[] arr = s.split(" ");
        Stack<Node> st = new Stack<>();
        // why till len - 1, so that i can get root on peek
        for (int i = 0; i < arr.length - 1; i++) {
            String val = arr[i];
            if (val.equals("n")) {
                Node child = st.pop();
                st.peek().children.add(child);
            } else {
                Node nn = new Node(Integer.parseInt(val));
                st.push(nn);
            }
        }
        return st.peek();
    }

    public static void serialzeDserialize(Node root) {
        if (root == null) return;
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        System.out.println(sb.toString());
        Node ans = deserialize(sb.toString());
        print(ans);
    }

    // immediate smaller element in N ary tree
    static int gfloor = -(int)(1e8);
    public static int getFloor(Node root, int x) {
        getFloor_rec(root, x);
        return gfloor;
    }

    public static void getFloor_rec(Node root, int x) {
        if (root.val < x) {
            gfloor = Math.max(gfloor, root.val);
        }
        for (Node child: root.children) {
            getFloor_rec(child, x);
        }
    }

    // kth largest element in tree
    // known as kth floor of infinity
    public static int kthLargest(Node root, int k) {
        int ans = (int)(1e8);
        for (int i = 0; i < k; i++) {
            int newFloor = getFloor(root, ans);
            ans = newFloor;
            gfloor = -(int)(1e8);
        }
        return ans;
    }
    
    // Node having maximum sum of immediate children and itself in n-ary tree
    static Node maxSumNode = null;
    static int maxSumVal = -(int)(1e8);
    public static Node maxSumIncRoot(Node root) {
        maxSumIncRoot_rec(root);
        System.out.println(maxSumVal);
        return maxSumNode;
    }

    public static int maxSumIncRoot_rec(Node root) {
        int self = 0;
        for (Node child: root.children) {
            self += maxSumIncRoot_rec(child);
        }
        self += root.val;
        if (self > maxSumVal) {
            maxSumVal = self;
            maxSumNode = root;
        }
        return self;
    }

    // Given a n-ary tree, count number of nodes which have more number of children than parents
    static int moreChildThanParent_count = 0;
    public static int moreChildThanParent(Node root) {
        moreChildThanParent_rec(root);
        return moreChildThanParent_count;
    }

    public static int moreChildThanParent_rec(Node root) {
        int self = root.children.size();
        for (Node child: root.children) {
            int recAns = moreChildThanParent_rec(child);
            if (recAns >= self) {
                moreChildThanParent_count++;
            }
        }
        return self;
    }

    // construct generic tree from preorder
    static int idx = 0;
    public static Node constructFromPre(int[] arr, int k) {
        int n = arr.length;
        int h = (int)Math.ceil(Math.log((double)n * (k - 1) + 1) / Math.log((double)k));
        return constructFromPre_rec(arr, k, h);
    }

    public static Node constructFromPre_rec(int[] arr, int k, int h) {
        if (h <= 0) return null;
        if (arr.length <= 0) return null;
        Node nn = new Node(arr[idx]);
        if (nn == null) {
            return null;
        }
        for (int i = 0; i < k; i++) {
            if (idx < arr.length - 1 && h > 1) {
                idx++;
                Node recAns = constructFromPre_rec(arr, k, h - 1);
                nn.children.add(recAns);
            } else {
                nn.children.add(null);
            }
        }
        return nn;
    }
    
    // left child right sibling representation
    
    // diameter of gtree
    static int mainDia = -1;
    // could be dc or sdc 
    // deepest child or second deepest child
    public static int diameter(Node root) {
        int largestDia = -1, secondLargestDia = -1;
        for (Node child: root.children) {
            int recAns = diameter(child);
            if (recAns > largestDia) {
                secondLargestDia = largestDia;
                largestDia = recAns;
            } else if (recAns > secondLargestDia) {
                secondLargestDia = recAns;
            }
        }
        if (largestDia + secondLargestDia + 2 > mainDia) {
            mainDia = largestDia + secondLargestDia + 2;
        }
        return largestDia + 1;
    }

    public static int height(Node root) {
        int self = -1;
        for (Node child: root.children) {
            int h = height(child);
            self = Math.max(self, h);
        }
        return self + 1;
    }

    public static void solve() {
        int[] arr = {10, 20, 50, -1, 60, -1, -1, 30, 70, -1, 80, 110, -1, 120, -1, -1, 90, -1, -1, 40, 100, -1, -1, -1};
        Node root = constructIterative(arr);
        int ans = height(root);
        System.out.println(ans);
    }

    public static Node constructIterative(int[] arr) {
        Stack<Node> st = new Stack<>();
        Node root = null;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == -1) st.pop();
            else {
                Node nn = new Node(arr[i]);
                if (st.size() > 0) {
                    st.peek().children.add(nn);
                } else {
                    root = nn;
                }
                st.push(nn);
            }
        }
        return root;
    }

    public static void print(Node root) {

        System.out.print(root.val + "-> ");
        for (Node child: root.children) {
            System.out.print(child.val + " ");
        }
        System.out.println();
        for (Node child: root.children) {
            print(child);
        }
    }

    public static void printPostOrder(Node root) {
        if (root == null) {
            return;
        }
        for (Node child: root.children) {
            printPostOrder(child);
        }
        System.out.print(root.val + " ");
    }

    public static void main(String[] args) {
        solve();
    }
}