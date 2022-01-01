import java.util.*;
class btree {

    static class Node {
        Node left = null, right = null, random = null, mid = null, next = null, nextRight = null;
        int data;
        char ch;
        public Node(int data) {
            this.data = data;
        }
        public Node(char ch) {
            this.ch = ch;
        }
    }

    // clone a btree with random pointers
    public static Node cloneBtreeRandomPtrs(Node root) {
        makeLeftCloned(root);
        connectRandomPtrs(root);
        Node clonedTreeRoot = extractClonedTree(root);
        return clonedTreeRoot;
    }

    public static Node extractClonedTree(Node root) {
        if (root == null) return null;

        Node cloned = root.left;
        root.left = cloned.left;
        cloned.left = null;
        Node ans1 = extractClonedTree(root.left);
        Node ans2 = extractClonedTree(root.right);
        cloned.left = ans1;
        cloned.right = ans2;
        return cloned;
    }

    public static void connectRandomPtrs(Node root) {
        if (root == null) return;
        if (root.left != null && root.random != null) {
            root.left.random = root.random.left;
        } 
        connectRandomPtrs(root.left.left);
        connectRandomPtrs(root.right);
    }

    public static void makeLeftCloned(Node root) {
        if (root == null) return;
        Node nn = new Node(root.data);
        Node leftNode = root.left;
        nn.left = leftNode;
        root.left = nn;
        makeLeftCloned(root.left.left);
        makeLeftCloned(root.right);
    }

    // Count subtrees that sum up to a given value x only using single recursive function
    static int countSubTreeSum_count = 0;
    public static int countSubTreeSum(Node root, int k) {
        if (root == null) return 0;

        int self = 0;
        int left = countSubTreeSum(root.left, k);
        int right = countSubTreeSum(root.right, k);
        
        self += left + right + root.data;
        if (self == k) countSubTreeSum_count++;
        return self;
    } 

    // iterative traversal pre, pos, in
    static class travelPair {
        Node n;
        int state;
        public travelPair(Node n, int state) {
            this.n = n;
            this.state = state;
        }
    }

    public static void iterativeTraversal(Node root) {
        String pre = "", post = "", in = "";
        Stack<travelPair> st = new Stack<>();
        st.push(new travelPair(root, -1));
        while (st.size() > 0) {
            travelPair cp = st.peek();
            if (cp.state == -1) {
                pre += cp.n.data + " ";
                if (cp.n.left != null) {
                    st.push(new travelPair(cp.n.left, -1));
                }
                cp.state++;
            } else if (cp.state == 0) {
                in += cp.n.data + " ";
                if (cp.n.right != null) {
                    st.push(new travelPair(cp.n.right, -1));
                }
                cp.state++;
            } else if (cp.state == 1) {
                post += cp.n.data + " ";
                st.pop();
            }
        }
        System.out.println(pre);
        System.out.println(in);
        System.out.println(post);
    }

    // morris preorder
    public static Node getRightMost(Node curr, Node next) {
        Node p = next;
        while (p.right != null && p.right != curr) {
            p = p.right;
        }
        return p;
    }

    public static void morrisPre(Node root) {
        Node curr = root;
        while (curr != null) {
            Node next = curr.left;
            if (next == null) {
                System.out.print(curr.data + " ");
                curr = curr.right;
            } else {
                Node rightMost = getRightMost(curr, next);
                if (rightMost.right == null) {
                    // 1st time on node
                    System.out.print(curr.data + " ");
                    // make connection
                    rightMost.right = curr;
                    curr = curr.left;
                } else {
                    // time to break connection
                    rightMost.right = null;
                    curr = curr.right;
                }
            }
        }
    }

    public static void morrisIn(Node root) {
        Node curr = root;
        while (curr != null) {
            Node next = curr.left;
            if (next == null) {
                // print
                System.out.print(curr.data + " ");
                curr = curr.right;
            } else {
                Node rightMost = getRightMost(curr, next);
                if (rightMost.right == null) {
                    // make connection
                    rightMost.right = curr;
                    curr = curr.left;
                } else {
                    // second time on node
                    System.out.print(curr.data + " ");
                    // time to break connection
                    rightMost.right = null;
                    curr = curr.right;
                }
            }
        }
    }

    // Diagonal Traversal of Binary Tree
    static class view {
        Node n;
        int level;

        public view(Node n, int level) {
            this.n = n;
            this.level = level;
        }
    }

    public static void widthOfTree(Node root, int[] w, int lvl) {
        if (root == null) return;

        w[0] = Math.min(w[0], lvl);
        w[1] = Math.max(w[1], lvl);
        widthOfTree(root.left, w, lvl - 1);
        widthOfTree(root.right, w, lvl + 1);
    }

    public static void diagonalTraversal(Node root) {
        int[] w = new int[2];
        widthOfTree(root, w, 0);
        // we need to go right side for view, then list will be of size w[0]
        ArrayList<Integer>[] list = new ArrayList[Math.abs(w[0]) + 1];
        for (int i = 0; i < list.length; i++) list[i] = new ArrayList<>();

        Queue<view> qu = new LinkedList<>();
        qu.add(new view(root, 0));
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                view cn = qu.remove();
                list[cn.level].add(cn.n.data);

                if (cn.n.left != null) {
                    qu.add(new view(cn.n.left, cn.level + 1));
                }
                if (cn.n.right != null) {
                    qu.add(new view(cn.n.right, cn.level));
                }
            }
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            for (Integer val: list[i]) {
                ans.add(val);
            }
        }
        System.out.println(ans);
    }

    public static void diagonalTraversal_opti(Node root) {
        Queue<Node> qu = new LinkedList<>();
        ArrayList<Integer> ans = new ArrayList<>();
        qu.add(root);
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                // 1. remove
                Node cn = qu.remove();
                ans.add(cn.data);
                // 2. add left child if exists
                if (cn.left != null) {
                    qu.add(cn.left);
                }
                // 3. now add all the right children
                cn = cn.right;
                while (cn != null) {
                    ans.add(cn.data);
                    // 4. if any right child has left child add it to qu
                    if (cn.left != null) {
                        qu.add(cn.left);
                    }
                    cn = cn.right;
                }
            }
        }
        System.out.println(ans);
    }

    // top view or top order
    public static void topView(Node root) {
        int[] w = new int[2];
        widthOfTree(root, w, 0);
        int mysize = w[1] - w[0];
        ArrayList<Integer>[] list = new ArrayList[mysize + 1];
        for (int i = 0; i < list.length; i++) list[i] = new ArrayList<>();
        Queue<view> qu = new LinkedList<>();
        qu.add(new view(root, Math.abs(w[0])));
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                view cn = qu.remove();
                list[cn.level].add(cn.n.data);
                if (cn.n.left != null) {
                    qu.add(new view(cn.n.left, cn.level - 1));
                }
                if (cn.n.right != null) {
                    qu.add(new view(cn.n.right, cn.level + 1));
                }
            }
        }
        for (int i = 0; i < list.length; i++) {
            System.out.println(list[i]);
        }
    }

    // left view
    public static ArrayList<Integer> leftView(Node root) {
        ArrayList<Integer> res = new ArrayList<>();
        Queue<Node> qu = new LinkedList<>();
        qu.add(root);
        while (qu.size() > 0) {
            int size = qu.size();
            // System.out.print(qu.peek().data + " ");
            res.add(qu.peek().data);
            while (size-- > 0) {
                Node cn = qu.remove();
                if (cn.left != null) qu.add(cn.left);
                if (cn.right != null) qu.add(cn.right);
            }
            // System.out.println();
        }
        return res;
    }

    // right view bottom to top
    public static ArrayList<Integer> rightView(Node root) {
        ArrayList<Integer> res = new ArrayList<>();
        Queue<Node> qu = new LinkedList<>();
        qu.add(root);
        while (qu.size() > 0) {
            int size = qu.size();
            Node cn = null;
            while (size-- > 0) {
                cn = qu.remove();
                if (cn.left != null) qu.add(cn.left);
                if (cn.right != null) qu.add(cn.right);
            }
            // System.out.print(cn.data + " ");
            res.add(cn.data);
        }
        return res;
    }

    // boundary traversal
    public static void boundaryTraversal(Node root) {
        ArrayList<Integer> ans = new ArrayList<Integer>();
        if (root == null) return;
        ans.add(root.data);
        leftBoundary(root.left, ans);
        getLeaves(root, ans);
        rightBoundary(root.right, ans);
        System.out.println(ans);
    }

    public static boolean isNodeLeaf(Node root) {
        if (root.left == null && root.right == null) return true;
        else return false; 
    }

    public static void leftBoundary(Node root, ArrayList<Integer> ans) {
        while (root != null) {
            if (!isNodeLeaf(root)) ans.add(root.data);
            if (root.left != null) root = root.left;
            else root = root.right;
        }
    }

    public static void rightBoundary(Node root, ArrayList<Integer> ans) {
        ArrayList<Integer> temp = new ArrayList<>();
        while (root != null) {
            if (!isNodeLeaf(root)) temp.add(root.data);
            if (root.right != null) root = root.right;
            else root = root.left;
        }
        // we want bottom to top, so reverse it
        for (int i = temp.size() - 1; i >= 0; i--) ans.add(temp.get(i));
    }

    public static void getLeaves(Node root, ArrayList<Integer> ans) {
        if (root == null) return;
        if (isNodeLeaf(root)) {
            ans.add(root.data);
            return;
        }
        getLeaves(root.left, ans);
        getLeaves(root.right, ans);
    }

    // print levelorder in a specific manner
    // for any level : a0 an-1 a1 an-2 etc
    public static void perfectBtreeLvlOrder(Node root) {
        Queue<Node> qu = new LinkedList<>();
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        qu.add(root);
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                Node cn = qu.remove();
                // System.out.print(cn.data + " ");
                dq.addLast(cn.data);
                if (cn.left != null) qu.add(cn.left);
                if (cn.right != null) qu.add(cn.right);
            }
            System.out.println();
            while (dq.size() > 0) {
                System.out.print(dq.removeFirst() + " ");
                if (dq.size() > 0) System.out.print(dq.removeLast() + " ");
            }
        }
    }

    public static void perfectBtreeLvlOrder_opti(Node root) {
        if (root == null) return;
        Queue<Node> qu = new LinkedList<>();
        System.out.println(root.data);
        if (root.left != null) {
            System.out.println(root.left.data + " " + root.right.data);
        }
        if (root.left.left == null) return;
        Node a = null, b = null;
        qu.add(root.left);
        qu.add(root.right);
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                a = qu.remove();
                b = qu.remove();
                System.out.print(a.left.data + " " + b.right.data);
                System.out.print(a.right.data + " " + b.left.data);
                // basically a was printed before, a.left printed above 
                // and now we are preparing to print a.left.left.data, so check it first
                if (a.left.left != null) {
                    qu.add(a.left);
                    qu.add(b.right);
                    qu.add(a.right);
                    qu.add(b.left);
                }
            }
            System.out.println();
        }
    } 

    // Construct a special tree from given preorder traversal
    static int specialTreeFromPre_idx = 0;
    public static Node specialTreeFromPre(int[] nodes, char[] LN) {
        if (specialTreeFromPre_idx >= nodes.length) {
            return null;
        }
        Node nn = new Node(nodes[specialTreeFromPre_idx]); 
        char ch = LN[specialTreeFromPre_idx];
        specialTreeFromPre_idx++;

        if (ch == 'N') {
            nn.left = specialTreeFromPre(nodes, LN);
            nn.right = specialTreeFromPre(nodes, LN);
        }
        return nn;
    }

    // Construct tree from ancestor matrix
    public static Node treeFromAncMatrix(int[][] arr) {
        int n = arr.length;
        int[] parent = new int[n];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                sum += arr[i][j];
            }
            map.put(i, sum);
        }
        Node[] nodes = new Node[n];
        // sort the map, this will tell the number of descendants a node has 
        // Map<Integer, Integer> sorted
        //     = map.entrySet()
        //           .stream()
        //           .sorted(Map.Entry.comparingByValue())
        //           .collect(Collectors.toMap(
        //               e
        //               -> e.getKey(),
        //               e
        //               -> e.getValue(),
        //               (e1, e2) -> e2, LinkedHashMap::new));

        // Collections.sort(map, new Comparator<Map.Entry<Integer, Integer> >() {
        //     public int compare(Map.Entry<Integer, Integer> o1,
        //                        Map.Entry<Integer, Integer> o2)
        //     {
        //         return (o1.getValue()).compareTo(o2.getValue());
        //     }
        // });
 
        Node root = null;
        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();

            nodes[key] = new Node(key);
            if (value != 0) {
                for (int i = 0; i < n; i++) {
                    if (parent[i] == 0 && arr[key][i] != 0) {
                        if (nodes[key].left == null) {
                            nodes[key].left = nodes[i];
                        } else {
                            nodes[key].right = nodes[i];
                        }
                        parent[i] = 1;
                    }
                    root = nodes[key];
                }
            }
        }
        return root;
    }

    // build ancestor matrix from given tree
    public static void buildAncestorMat(Node root, int[][] arr) {
        if (root == null) return;
        buildAncestorMat(root.left, arr);
        buildAncestorMat(root.right, arr);
        if (root.left != null) {
            arr[root.data][root.left.data] = 1;
            // now all the descendants of left child will become dscendants for curr node
            for (int i = 0; i < arr.length; i++) {
                if (arr[root.left.data][i] == 1) {
                    arr[root.data][i] = 1;
                }
            }
        }
        if (root.right != null) {
            arr[root.data][root.right.data] = 1;
            for (int i = 0; i < arr.length; i++) {
                if (arr[root.right.data][i] == 1) {
                    arr[root.data][i] = 1;
                }
            }
        }
    }

    // Construct Binary Tree from given Parent Array representation
    // here arr index means : node val, and arr[i] = parent of i
    public static Node constructBTFromParentArr(int[] arr) {
        Node root = null;
        int n = arr.length;
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            if (arr[i] == -1) {
                if (nodes[i] != null) root = nodes[i];
                else {
                    root = new Node(i);
                    nodes[i] = root;
                }
                continue;
            }
            Node parent = null, child = null;
            if (nodes[i] != null) {
                child = nodes[i];
            } else {
                child = new Node(i);
                nodes[i] = child;
            }
            if (nodes[arr[i]] != null) {
                parent = nodes[arr[i]];
            } else {
                parent = new Node(arr[i]);
                nodes[arr[i]] = parent;
            }
            // 
            if (parent.left == null) parent.left = child;
            else if (parent.right == null) parent.right = child;
        }
        return root;
    }

    // ****************************** (IMPORTANT) DLL from Ternary Tree ***************************
    // doubly linked list from ternary tree
    static Node dllTail = null, dllHead = null;
    public static Node dllFromTernaryTree(Node root) {
        dllTail = root;
        Node head = root;
        dllFromTernaryTree_rec(root);
        return head;
    }

    public static void dllFromTernaryTree_rec(Node root) {
        if (root == null) return;
        Node left = root.left;
        Node mid = root.mid;
        Node right = root.right;
        // check if tail is not pointing to root now
        if (dllTail != root) {
            dllTail.right = root;
            root.left = dllTail;
            root.mid = root.right = null;
            dllTail = root;
        }
        dllFromTernaryTree_rec(left);
        dllFromTernaryTree_rec(mid);
        dllFromTernaryTree_rec(right);
    }
    
    // ***************************** (IMPORTANT) DLL from Binary Tree ******************************
    public static Node dllFromBtree(Node root) {
        dllFromBtree_rec(root);
        // if you want to convert it into circular dll then
        // dllHead.left = dllTail;
        // dllTail.right = dllHead;
        return dllHead;
    }

    public static void dllFromBtree_rec(Node root) {
        if (root == null) return;
        Node left = root.left;
        Node right = root.right;
        dllFromBtree_rec(left);
        if (dllHead == null && dllTail == null) {
            dllTail = dllHead = root;
        } else {
            dllTail.right = root;
            root.left = dllTail;
            root.right = null;
            dllTail = root;
        }
        dllFromBtree_rec(right);
    }

    // Convert a given tree to its Sum Tree
    // each node should be replaced with the sum of it's left and right subtree
    public static int convertIntoSumTree(Node root) {
        if (root == null) return 0;
        int left = convertIntoSumTree(root.left);
        int right = convertIntoSumTree(root.right);
        int sum = left + right + root.data;
        root.data = left + right;
        return sum;
    }

    // Change a Binary Tree so that every node stores sum of all nodes in left subtree
    // Given a Binary Tree, change the value in each node to sum of all the values in the nodes in the left subtree including its own.
    public static int convertToSumOfLeftSubTree(Node root) {
        if (root == null) return 0;
        int left = convertToSumOfLeftSubTree(root.left);
        int right = convertToSumOfLeftSubTree(root.right);
        int sum = left + root.data + right;
        root.data = left + root.data;
        // such that sum could be some node's left subtree sum
        return sum;
    }

    // Convert a tree to forest of even nodes
    // task is to remove maximum number of edges such that after it's removal 
    // even number of nodes remain in the forest
    static int minEdges = 0;
    public static int minEdgeRemoval(Node root) {
        if (root == null) return 0;
        minEdgeRemoval_rec(root);
        return minEdges;
    }
    // this rec function will count the nodes in a subtree
    public static int minEdgeRemoval_rec(Node root) {
        if (root == null) return 0;
        int left = minEdgeRemoval_rec(root.left);
        int right = minEdgeRemoval_rec(root.right);
        // if my subtree nodes count is even then I can remove the edge 
        // as total even nodes are there and we are removing even nodes so 
        // remaining will be even nodes
        if ((left & 1) == 0) minEdges++;
        if ((right & 1) == 0) minEdges++;
        // return the nodes count till now, including self node
        return (left + right + 1);
    }

    // lca: lowest common ancestor
    public static Node lowestCommonAncestor(Node root, int a, int b) {
        if (root == null) return null;
        Node left = lowestCommonAncestor(root.left, a, b);
        Node right = lowestCommonAncestor(root.right, a, b);
        Node self = null;
        if (left != null && right != null) self = root;
        // if either of them is not null then self will be containing that node
        else if (left != null || right != null) {
            if (root.data == a || root.data == b) {
                self = root;
            } else {
                self = (left != null) ? left : right;
            }
        }
        // either left == null or right == null
        else if (root.data == a || root.data == b) self = root;
        return self;
    }

    // Find distance between two nodes of a Binary Tree
    public static boolean ntrp(Node root, int a, ArrayList<Integer> list) {
        if (root == null) return false;
        if (root.data == a) {
            list.add(a);
            return true;
        }
        boolean left = ntrp(root.left, a, list);
        if (left) {
            list.add(root.data);
            return true;
        }
        boolean right = ntrp(root.right, a, list);
        if (right) {
            list.add(root.data);
            return true;
        }
        return false;
    }

    public static int distanceBetweenNodes(Node root, int a, int b) {
        ArrayList<Integer> alist = new ArrayList<>();
        ArrayList<Integer> blist = new ArrayList<>();
        ntrp(root, a, alist);
        ntrp(root, b, blist);
        int i = alist.size() - 1, j = blist.size() - 1, count = 0;
        while (i >= 0 && j >= 0 && alist.get(i) == blist.get(j)) {
            i--; j--;
        }
        // get the last common element
        i++; j++;
        // return alist.size() + blist.size() - count;
        return i + j;
    }

    // height(a) + height(b) - 2 * height(lca(a, b))
    public static int distanceBetweenNodes_2(Node root, int a, int b) {
        int ha = depth(root, a);
        int hb = depth(root, b);
        Node lca = lowestCommonAncestor(root, a, b);
        int hlca = depth(root, lca.data);
        return ha + hb - (2 * hlca);
    }
    // depth in terms of edges
    public static int depth(Node root, int a) {
        if (root == null) return 0;
        if (root.data == a) return 1;
        int left = depth(root.left, a);
        if (left > 0) {
            return left + 1;
        }
        int right = depth(root.right, a);
        if (right > 0) {
            return right + 1;
        }
        return 0;
    }

    // Print common nodes on path from root (or common ancestors)
    public static void printCommonNodes(Node root, int a, int b) {
        ArrayList<Integer> alist = new ArrayList<>();
        ArrayList<Integer> blist = new ArrayList<>();
        ntrp(root, a, alist);
        ntrp(root, b, blist);
        int i = alist.size() - 1, j = blist.size() - 1;
        while (i >= 0 && j >= 0 && alist.get(i) == blist.get(j)) {
            System.out.print(alist.get(i) + " ");
            i--; j--;
        }
    }

    // Root to leaf path sum
    public static boolean rootToLeafPathSum(Node root, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        return rootToLeafPathSum_rec(root, list, k, 0);
    }

    public static boolean rootToLeafPathSum_rec(Node root, ArrayList<Integer> list, int k, int sum) {
        if (root == null) {
            if (sum == k) return true;
            return false;
        }

        list.add(root.data);
        boolean left = rootToLeafPathSum_rec(root.left, list, k, sum + root.data);
        if (left) return true;
        // list.remove(list.size() - 1);

        // list.add(root.data);
        boolean right = rootToLeafPathSum_rec(root.right, list, k, sum + root.data);
        if (right) return true;
        list.remove(list.size() - 1);

        return false;
    }

    // Populate Inorder Successor for all nodes
    // reverse inorder
    static Node inorderTail = null, inorderHead = null;
    public static void inorderSucc(Node root) {
        if (root == null) return;
        inorderSucc(root.right);
        if (inorderHead == null && inorderTail == null) {
            inorderHead = inorderTail = root;
        } else {
            root.next = inorderHead;
            inorderHead = root;
        }
        inorderSucc(root.left);
    }

    // Connect nodes at same level using constant extra space
    // Write a function to connect all the adjacent nodes at the same level in a binary tree. Structure of the given Binary Tree node is like following.
    public static void connectAdjacentNodeSameLevel(Node root) {
        Queue<Node> qu = new LinkedList<>();
        qu.add(root);
        while (qu.size() > 0) {
            int size = qu.size();
            Node p = null;
            while (size-- > 0) {
                Node cn = qu.remove();
                // adjust nextRight pointers
                if (p == null) {
                    p = cn;
                    p.nextRight = null;
                } else {
                    p.nextRight = cn;
                    p = p.nextRight;
                    p.nextRight = null;
                }
                //
                if (cn.left != null) qu.add(cn.left);
                if (cn.right != null) qu.add(cn.right);
            }
        }
    }

    // more optimized
    public static void connectAdjacentNodeSameLevel_opti(Node root) {
        Node p = root;
        while (p != null) {
            // q will handle the complete level
            Node q = p;
            while (q != null) {
                if (q.left != null) {
                    if (q.right != null) {
                        q.left.nextRight = q.right;
                    } else {
                        q.left.nextRight = getNextRight(q);
                    }
                } 
                if (q.right != null) {
                    q.right.nextRight = getNextRight(q);
                }
                // move to adjacent node as curr q does not have any child
                q = q.nextRight;
            }
            // move p
            if (p.left != null) p = p.left;
            else if (p.right != null) p = p.right;
            else {
                // move p to next Right's child
                p = getNextRight(p);
            }
        }
    }

    public static Node getNextRight(Node q) {
        // for a q it will return q's nextRight's child
        Node temp = q.nextRight;
        while (temp != null) {
            if (temp.left != null) return temp.left;
            else if (temp.right != null) return temp.right;
            temp = temp.nextRight;
        }
        return temp;
    }

    // isSymmetric 
    // this should be called only on left and right subtrees
    // else at root level redundant calls will be made      
    public static boolean isSymmetric(Node p, Node q) {
        if (p == null || q == null) {
            if (p == null && q == null) return true;
            else return false;
        }
        // if (p.data != q.data) return false;
        boolean left = isSymmetric(p.left, q.right);
        if (!left) return false;
        boolean right = isSymmetric(p.right, q.left);
        if (!right) return false;
        return true;
    }

    // maximum width
    // Maximum width is defined as the maximum number of nodes in any level.
    // you should not include null nodes between any node
    public static int maxWidth(Node root) {
        Queue<Node> qu = new LinkedList<>();
        int ans = 0;
        qu.add(root);
        while (qu.size() > 0) {
            int size = qu.size();
            // 
            ans = Math.max(size, ans);
            //
            while (size-- > 0) {
                Node cn = qu.remove();
                if (cn.left != null) qu.add(cn.left);
                if (cn.right != null) qu.add(cn.right);
            }
        }
        return ans;
    }

    // Double Tree
    // make a left cloned treee 
    public static void doubleTree(Node root) {
        if (root == null) return;
        Node clone = new Node(root.data);
        clone.left = root.left;
        root.left = clone;
        doubleTree(root.left.left);
        doubleTree(root.right);
    } 

    // Find the maximum sum leaf to root path in a Binary Tree
    static int maxSumLeaf = 0;
    public static int maxSumLeafPath(Node root) {
        maxSumLeafPath_rec(root, 0);
        return maxSumLeaf;
    }

    public static void maxSumLeafPath_rec(Node root, int sum) {
        if (root == null) {
            maxSumLeaf = Math.max(maxSumLeaf, sum);
            return;
        }
        maxSumLeafPath_rec(root.left, sum + root.data);
        maxSumLeafPath_rec(root.right, sum + root.data);
    }

    // Vertical Sum in a given Binary Tree
    public static void verticalSum(Node root) {
        int[] w = new int[2];
        widthOfTree(root, w, 0);
        Queue<view> qu = new LinkedList<>();
        int[] list = new int[w[1] - w[0] + 1];
        qu.add(new view(root, Math.abs(w[0])));
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                view cn = qu.remove();
                list[cn.level] += cn.n.data;
                if (cn.n.left != null) qu.add(new view(cn.n.left, cn.level - 1));
                if (cn.n.right != null) qu.add(new view(cn.n.right, cn.level + 1));
            }
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for (int val: list) {
            ans.add(val);
        }
        System.out.println(ans);
    }

    // find the next right node for a given node
    public static void printNextRightOnSameLevel(Node root, int k) {
        Queue<Node> qu = new LinkedList<>();
        qu.add(root);
        Node ans = null;
        boolean kfound = false;
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                Node cn = qu.remove();
                //
                if (kfound) {
                    ans = cn;
                    break;
                }
                if (cn.data == k) kfound = true;
                //
                if (cn.left != null) qu.add(cn.left);
                if (cn.right != null) qu.add(cn.right);
            }
            // k was there in the level but k next was not there
            if (kfound && ans == null) break; 
        }
        System.out.println(ans != null ? ans.data : "null");
    }

    // Deepest left leaf node in a binary tree
    public static void deepestLeftLeaf(Node root) {
        int[] ans = new int[2];
        // node.data, depth
        // at any node if flag: we cam through left, else we cam through right
        deepestLeftLeaf_rec(root, false, ans, 0);
        System.out.println(ans[0] + " @ " + ans[1]);
    }

    public static void deepestLeftLeaf_rec(Node root, boolean flag, int[] ans, int d) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            if (!flag) return;
            if (d > ans[1]) {
                ans[1] = d;
                ans[0] = root.data;
            }
            return;
        }
        deepestLeftLeaf_rec(root.left, true, ans, d + 1);
        deepestLeftLeaf_rec(root.right, false, ans, d + 1);
    }

    // Given a Binary Tree of size N, extract all its leaf nodes to form a Doubly Link List strating from the left most leaf. 
    // we need to remove leaves and return the head of dll
    static Node leafHead = null, leafTail = null;
    public static Node extractLeaves(Node root) {
        extractLeaves_rec(root);
        return leafHead;
    }

    public static Node extractLeaves_rec(Node root) {
        if (root == null) return null;
        if (root.left == null && root.right == null) {
            if (leafHead == null && leafTail == null) {
                leafHead = leafTail = root;
            } else {
                leafTail.right = root;
                root.left = leafTail;
                root.right = null;
                leafTail = leafTail.right;
            }
            return null;
        }
        root.left = extractLeaves_rec(root.left);
        root.right = extractLeaves_rec(root.right);
        return root;
    }
 
    // print left view
    public static ArrayList<Integer> leftViewTree(Node root) {
        Queue<Node> qu = new LinkedList<>();
        qu.add(root);
        ArrayList<Integer> ans = new ArrayList<>();
        while (qu.size() > 0) {
            int size = qu.size();
            ans.add(qu.peek().data);
            while (size-- > 0) {
                Node cn = qu.remove();
                if (cn.left != null) qu.add(cn.left);
                if (cn.right != null) qu.add(cn.right);
            }
        }
        return ans;
    }

    // custom tree
    // {"a b", "a g", "b c", "c d", "d e", "c f", "z y", "y x", "x w"};
    public static void customTree(String[] arr) {
        HashMap<Character, ArrayList<Character>> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            char s = arr[i].charAt(0), d = arr[i].charAt(2);
            if (!map.containsKey(s)) {
                map.put(s, new ArrayList<>());
            }
            map.get(s).add(d);
        }
        System.out.println(map);
    }

    // Given a Perfect Binary Tree, reverse the alternate level nodes of the binary tree. 
    // false : do nothing
    // you need to traverse the tree as done in finding if the tree is symmetric or not
    // start from root.left and root.right
    public static void reverseAlternativeLevels(Node a, Node b, int level) {
        if (a == null || b == null) return;
        if ((level & 1) == 0) {
            int temp = a.data;
            a.data = b.data;
            b.data = temp;
        }
        reverseAlternativeLevels(a.left, b.right, level + 1);
        reverseAlternativeLevels(a.right, b.left, level + 1);
    }

    public static void invertBTree(Node root) {
        if (root == null) return;
        invertBTree(root.left);
        invertBTree(root.right);
        //
        Node temp = root.left;
        root.left = root.right;
        root.right = temp;
    }

    // k level down
    // root is the given node
    public static void klevelDown(Node root, int k, ArrayList<Integer> list, Node blockage) {
        if (root == null || root == blockage || k < 0) return;
        if (k == 0) {
            list.add(root.data);
            return;
        }
        klevelDown(root.left, k - 1, list, blockage);
        klevelDown(root.right, k - 1, list, blockage);
    }

    public static boolean ntrp_2(Node root, int a, ArrayList<Node> list) {
        if (root == null) return false;
        if (root.data == a) {
            list.add(root);
            return true;
        }
        boolean left = ntrp_2(root.left, a, list);
        if (left) {
            list.add(root);
            return true;
        }
        boolean right = ntrp_2(root.right, a, list);
        if (right) {
            list.add(root);
            return true;
        }
        return false;
    }

    public static void printKDisAway(Node root, int val, int k) {
        ArrayList<Node> list = new ArrayList<>();
        ArrayList<Integer> ans = new ArrayList<>();
        if (root == null) return;
        ntrp_2(root, val, list);
        Node blockNode = null;
        int temp = k;
        for (int i = 0; i < list.size() && temp >= 0; i++) {
            if (i > 0) {
                blockNode = list.get(i - 1);
            }
            klevelDown(list.get(i), temp, ans, blockNode);
            temp -= 1;
        }
        System.out.println(ans);
    }

    // print nodes k distance away optimized
    public static void printKDisAway_2(Node root, int val, int k) {
        ArrayList<Integer> ans = new ArrayList<>();
        printKDisAway_2_rec(root, val, k, ans);
        System.out.println(ans);
    }

    public static int printKDisAway_2_rec(Node root, int val, int k, ArrayList<Integer> ans) {
        if (root == null) return -1;
        if (root.data == val) {
            klevelDown(root, k, ans, null);
            return 1;
        }
        int left = printKDisAway_2_rec(root.left, val, k, ans);
        if (left != -1) {
            klevelDown(root, k - left, ans, root.left);
            return left + 1;
        }
        int right = printKDisAway_2_rec(root.right, val, k, ans);
        if (right != -1) {
            klevelDown(root, k - right, ans, root.right);
            return right + 1;
        }
        return -1;
    }

    // diagonal sum -> diagonal be like \\
    public static void diagonalSum(Node root) {
        int[] w = new int[2];
        widthOfTree(root, w, 0);
        int[] list = new int[Math.abs(w[0]) + 1];
        Queue<view> qu = new LinkedList<>();
        qu.add(new view(root, 0));
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                view cn = qu.remove();
                list[cn.level] += cn.n.data;
                if (cn.n.left != null) qu.add(new view(cn.n.left, cn.level + 1));
                if (cn.n.right != null) qu.add(new view(cn.n.right, cn.level));
            }
        }
        for (int val: list) System.out.print(val + " ");
    }

    // tilt of binary tree
    public static int tiltOfBtree(Node root) {
        int[] ans = new int[1];
        tiltOfBtree_rec(root, ans);
        return ans[0];
    }
    public static int tiltOfBtree_rec(Node root, int[] ans) {
        if (root == null) {
            return 0;
        }
        int left = tiltOfBtree_rec(root.left, ans);
        int right = tiltOfBtree_rec(root.right, ans);
        ans[0] += Math.abs(left - right);
        return left + right + root.data;
    }

    // Find Height of Binary Tree represented by Parent array
    // find depth of each node and store them in an depth 
    public static int heightUsingParentArr(int[] par) {
        int n = par.length;
        int[] dep = new int[n];
        for (int i = 0; i < n; i++) {
            heightUsingParentArr_rec(par, dep, i);
        }
        int max = 0;
        for (int val: dep) max = Math.max(max, val);
        return max;
    }

    public static void heightUsingParentArr_rec(int[] par, int[] dep, int i) {
        if (dep[i] != 0) return;
        if (par[i] == -1) {
            // this is root
            dep[i] = 1;
            return;
        }
        // find the depth of parent indx
        // parent is arr[i], child is i
        if (dep[par[i]] == 0) {
            heightUsingParentArr_rec(par, dep, par[i]);
        }
        // now my height will be parent height + 1
        dep[i] = dep[par[i]] + 1;
    }

    // Remove nodes on root to leaf paths of length < K
    // a node is deleted if all paths going through it have length < k
    public static Node removeNTRPLessThanK(Node root, int k) {
        return removeNTRPLessThanK_rec(root, 1, k);
    }

    public static Node removeNTRPLessThanK_rec(Node root, int val, int k) {
        if (root == null) return null;
        removeNTRPLessThanK_rec(root.left, val + 1, k);
        removeNTRPLessThanK_rec(root.right, val + 1, k);
        if (root.left == null && root.right == null && val < k) {
            // if at any node val < k, then it will be checked if any one of it's child 
            // is null or not. If not ie the root is the part of path with val > k
            return null;
        }
        return root;
    }

    // Maximum Path Sum in a Binary Tree
    // the path may start & end at any node in the tree
    // res -> shows that this stores left + root + right and other maximum we get
    public static int maxPathSum_rec(Node root, int[] res) {
        if (root == null) return 0;
        int left = maxPathSum_rec(root.left, res);
        int right = maxPathSum_rec(root.right, res);
        //
        int max1 = root.data + left, max2 = root.data + right, max3 = root.data;
        int returnVal = Math.max(max1, Math.max(max2, max3));
        int max4 = root.data + left + right;
        res[0] = Math.max(res[0], Math.max(max4, returnVal));
        return returnVal;
    }

    public static int maxPathSum(Node root) {
        int[] res = new int[1];
        res[0] = -(int)(1e8);
        maxPathSum_rec(root, res);
        return res[0];
    }

    // construct expression tree
    // given a post fix expression
    public static Node expressionTree(String s) {
        Stack<Node> st = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                st.push(new Node(ch));
            } else {
                Node right = st.pop();
                Node left = st.pop();
                Node cn = new Node(ch);
                if (cn.left == null) cn.left = left;
                if (cn.right == null) cn.right = right;
                st.push(cn);
            }
        }
        return st.peek();
    }
    
    // *********************** IMPORTANT QUES ***********************
    // reverse ntrp 
    public static Node reverse(Node root, int data) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] next = new int[1];
        return reverse_rec(root, map, data, next, 0);
    }

    public static Node reverse_rec(Node root, HashMap<Integer, Integer> map, int x, int[] next, int level) {
        if (root == null) return null;
        if (root.data == x) {
            map.put(level, root.data);
            root.data = map.get(next[0]);
            next[0]++;
            return root;
        }
        map.put(level, root.data);
        Node right = null;
        Node left = reverse_rec(root.left, map, x, next, level + 1);
        if (left == null) {
            // if right is null then make call to right
            right = reverse_rec(root.right, map, x, next, level + 1);
        }
        // now curr root can be the part of path if left or right != null
        if (left != null || right != null) {
            root.data = map.get(next[0]);
            next[0]++;
            return root;
        }
        return null;
    }

    // ********************** --------------- ****************

    // height of binary tree
    public static int height(Node root) {
        if (root == null) return -1;
        int left = height(root.left);
        int right = height(root.right);
        return Math.max(left, right) + 1;
    }

    // serialize and deserialize btree
    public static Node serialDeserial(Node root) {
        StringBuilder sb = new StringBuilder();
        serializeBtree(root, sb);
        int[] idx = new int[1];
        String[] arr = sb.toString().split(" ");
        Node new_root = deserialize(arr, idx);
        return new_root;
    }

    public static void serializeBtree(Node root, StringBuilder sb) {
        if (root == null) {
            sb.append("null ");
            return;
        }
        sb.append(root.data + " ");
        serializeBtree(root.left, sb);
        serializeBtree(root.right, sb);
    }

    public static Node deserialize(String[] arr, int[] idx) {
        if (idx[0] >= arr.length || arr[idx[0]].equals("null")) {
            idx[0]++;
            return null;
        }
        Node root = new Node(Integer.parseInt(arr[idx[0]++]));
        root.left = deserialize(arr, idx);
        root.right = deserialize(arr, idx);
        return root;
    }

    // Find the maximum path sum between two leaves of a binary tree
    public static int maxPathSumLeaves(Node root) {
        int[] rootLeftRight = new int[1];
        rootLeftRight[0] = -(int)(1e8);
        maxPathSumLeaves_rec(root, rootLeftRight);
        return rootLeftRight[0];
    }

    public static int maxPathSumLeaves_rec(Node root, int[] rootLeftRight) {
        if (root == null) return -(int)(1e8);
        if (root.left == null && root.right == null) return root.data;

        int left = maxPathSumLeaves_rec(root.left, rootLeftRight);
        int right = maxPathSumLeaves_rec(root.right, rootLeftRight);
        int rv = Math.max(left, right) + root.data;
        if (root.left != null && root.right != null) {
            rootLeftRight[0] = Math.max(rootLeftRight[0], left + right + root.data);
        }
        return rv;
    }
    
    // Find height of a special binary tree whose leaf nodes are connected
    public static int heightSpecialBtree(Node root) {
        if (root == null) return 0;
        if (root.left != null && root.right != null && root.left.right == root && root.right.left == root) {
            return 1;
        }
        int left = heightSpecialBtree(root.left);
        int right = heightSpecialBtree(root.right);
        return Math.max(left, right) + 1;
    }

    public static void solve() {
        // Integer[] arr = {50, 25, 12, null, null, 37, 30, null, null, null, 75, 62, null, 70, null, null, 87, null, null};
        // Integer[] arr2 = {10, 8, 3, null, null, null, 12, null, null};
        Integer[] arr = {-15, 5, -8, 2, null, null, 6, null, null, 1, null, null, 6, 3, null, null, 9, null, 0, 4, null, null, -1, 10, null, null, null};
        Node root = construct_rec(arr);
        int ans = maxPathSumLeaves(root);
        System.out.println(ans);
    }

    public static void printInorder(Node root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.print(root.data + " ");
        printInorder(root.right);
    }

    public static void printInorderInfix(Node root) {
        if (root == null) return;
        printInorderInfix(root.left);
        System.out.print(root.ch + " ");
        printInorderInfix(root.right);
    }

    public static void printLvlOrder(Node root) {
        Queue<Node> qu = new LinkedList<>();
        qu.add(root);
        while (qu.size() > 0) {
            int size = qu.size();
            while (size-- > 0) {
                Node cn = qu.remove();
                System.out.print(cn.data + " ");
                if (cn.left != null) qu.add(cn.left);
                if (cn.right != null) qu.add(cn.right);
            }
            System.out.println();
        }
    }

    public static void printBT(Node root) {
        if (root == null) return;
        System.out.print(root.data + " ");
        printBT(root.left);
        printBT(root.right);

    }

    static int idx = 0;
    public static Node construct_rec(Integer[] arr) {
        if (idx >= arr.length || arr[idx] == null) {
            idx++;
            return null;
        }
        
        Node nn = new Node(arr[idx++]);
        nn.left = construct_rec(arr);
        nn.right = construct_rec(arr);
        return nn;
    }

    public static void printDll(Node head) {
        while (head != null) {
            System.out.print(head.data + " ");
            head = head.right;
        }
    }
    
    public static void main(String[] args) {
        solve();
    }
}