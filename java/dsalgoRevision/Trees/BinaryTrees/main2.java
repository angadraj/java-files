import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
class main2 {
    static class Node {
        int data; 
        Node left;
        Node right;
        public Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
        public Node(int data) {
            this.data = data;
        }
    }
    static int idx = 0;
    public static Node construct(int[] arr) {
        if(arr[idx] == -1 || idx == arr.length) {
            idx++;
            return null;
        }
        Node nn = new Node(arr[idx]);
        idx++;
        nn.left = construct(arr);
        nn.right = construct(arr);
        return nn;
    }

    public static void display(Node root) {
        if(root == null) {
            return;
        }
        display(root.left);
        System.out.print(root.data + " ");
        display(root.right);
    }

    public static void levelOrder(Node root) {
        Queue<Node> qu = new LinkedList<>();
        qu.add(root);
        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                Node cn = qu.remove();
                System.out.print(cn.data + " ");
                if(cn.left != null) {
                    qu.add(cn.left);
                } 
                if(cn.right != null) {
                    qu.add(cn.right);
                }
            }
            System.out.println();
        }
    }

    public static void kdown(Node root, Node block, int k) {
        if(root == null || root == block || k < 0) return;
        if(k == 0) {
            System.out.print(root.data + " ");
            return;
        }
        kdown(root.left, block, k - 1);
        kdown(root.right, block, k - 1);
    }

    public static boolean ntrp(Node root, int val, ArrayList<Node> path) {
        if(root == null) {
            return false;
        }
        if(root.data == val) {
            path.add(root);
            return true;
        }
        boolean ans = false;
        ans = ans || ntrp(root.left, val, path);
        ans = ans || ntrp(root.right, val, path);
        if(ans) {
            path.add(root);
            return true;
        }
        return false;
    }

    public static void kFar(Node root, int data, int k) {
        ArrayList<Node> path = new ArrayList<>();
        ntrp(root, data, path);
        Node block = null;
        for(int i = 0; i <= k && i < path.size(); i++) {
            if(i > 0) {
                block = path.get(i - 1);
            }
            kdown(path.get(i), block, k - i);
        }
    }

    public static int kfar_better(Node root, int data, int k) {
        if(root == null) {
            return -1;
        }
        if(root.data == data) {
            kdown(root, null, k);
            return 1;
        }
        int lans = kfar_better(root.left,data, k);
        if(lans != -1) {
            kdown(root, root.left, k - lans);
            return lans + 1;
        }
        int rans = kfar_better(root.right,data, k);
        if(rans != -1) {
            kdown(root, root.right, k - rans);
            return rans + 1;
        }
        return -1;
    }

    public static void burnNodes(Node root, int k, ArrayList<ArrayList<Integer>> ans) { 
        if(root == null) return;
        if(ans.size() == k) {
            ans.add(new ArrayList<>());
        }
        ans.get(k).add(root.data);
        burnNodes(root.left, k + 1, ans);
        burnNodes(root.right, k + 1, ans);
    }

    public static int burnTree(Node root, int data, ArrayList<ArrayList<Integer>> ans) {
        if(root == null) {
            return -1;
        }
        if(root.data == data) {
            burnNodes(root, 0, ans);
            return 1;
        }

        int lans = burnTree(root.left, data, ans);
        if(lans != -1) {
            if(ans.size() == lans) {
                ans.add(new ArrayList<>());
            }
            ans.get(lans).add(root.data);
            burnNodes(root.right, lans + 1, ans);
            return lans + 1;
        }
        int rans = burnTree(root.right, data, ans);
        if(rans != -1) {
            if(ans.size() == rans) {
                ans.add(new ArrayList<>());
            }
            ans.get(rans).add(root.data);
            burnNodes(root.left, rans + 1, ans);
            return rans + 1;
        }
        return -1;
    }

    public static Node lca(Node root, int val1, int val2) {
        if(root == null) return null;
        if(root.data == val1 || root.data == val2) {
            return root;
        }
        Node lans = lca(root.left, val1, val2);
        Node rans = lca(root.right, val1, val2); 
        Node my_ans = null;
        if(lans == null && rans == null) my_ans = null;
        else if(lans == null || rans == null) {
            if(root.data == val1 || root.data == val2) my_ans = root;
            else my_ans = (lans != null) ? lans : rans;
        } else my_ans = root;
        return my_ans;
    }

    // view set

    public static void leftView(Node root) {
        Queue<Node> qu = new LinkedList<>();
        qu.add(root);
        while(qu.size() > 0) {
            int size = qu.size();
            System.out.print(qu.peek().data);
            while(size-- > 0) {
                Node cn = qu.remove();
                // no printing here
                if(cn.left != null) qu.add(cn.left);
                if(cn.right != null) qu.add(cn.right);
            }
            System.out.println();
        }
    }

    public static void rightView(Node root) {
        Queue<Node> qu = new LinkedList<>();
        qu.add(root);
        while(qu.size() > 0) {
            int size = qu.size();
            Node cn = null;
            while(size-- > 0) {
                cn = qu.remove();
                if(cn.left != null) qu.add(cn.left);
                if(cn.right != null) qu.add(cn.right);
            }
            System.out.println(cn.data);
        }
    }

    public static void width(Node root, int[] ans, int level) {
        if(root == null) return;
        ans[0] = Math.min(ans[0], level);
        ans[1] = Math.max(ans[1], level);
        width(root.left, ans, level - 1);
        width(root.right, ans, level + 1);
    }

    static class viewPair {
        Node node;
        int level = 0;
        int height = 0;
        public viewPair(Node node, int level) {
            this.node = node;
            this.level = level;
        }
        public viewPair(Node node, int level, int height) {
            this.node = node;
            this.level = level;
            this.height = height;
        }
    }
    public static void verticalorder(Node root) {
        int[] w = new int[2];
        width(root, w, 0);
        int my_size = w[1] - w[0];
        ArrayList<Integer>[] ans = new ArrayList[my_size + 1];
        for(int i = 0; i < ans.length; i++) {
            ans[i] = new ArrayList<>();
        }   
        Queue<viewPair> qu = new LinkedList<>();
        qu.add(new viewPair(root, Math.abs(w[0])));
        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                viewPair vp = qu.remove();
                Node nn = vp.node;
                int level = vp.level;
                ans[level].add(nn.data);
                if(nn.left != null) {
                    qu.add(new viewPair(nn.left, level - 1));
                }
                if(nn.right != null) {
                    qu.add(new viewPair(nn.right, level + 1));
                }
            }
        }
        for(ArrayList<Integer> val : ans) {
            System.out.println(val);
        }
    }

    public static void verticalorderSum(Node root) {
        int[] w = new int[2];
        width(root, w, 0);
        int my_size = w[1] - w[0];
        int[] ans = new int[my_size + 1];   
        Queue<viewPair> qu = new LinkedList<>();
        qu.add(new viewPair(root, Math.abs(w[0])));
        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                viewPair vp = qu.remove();
                Node nn = vp.node;
                int level = vp.level;
                ans[level] += nn.data;
                if(nn.left != null) {
                    qu.add(new viewPair(nn.left, level - 1));
                }
                if(nn.right != null) {
                    qu.add(new viewPair(nn.right, level + 1));
                }
            }
        }
        for(int val : ans) {
            System.out.println(val);
        }
    }

    public static void bottomViewRightPref(Node root) {
        int[] w = new int[2];
        width(root, w, 0);
        int my_size = w[1] - w[0];
        int[] ans = new int[my_size + 1];
        Queue<viewPair> qu = new LinkedList<>();
        qu.add(new viewPair(root, Math.abs(w[0])));
        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                viewPair vp = qu.remove();
                Node nn = vp.node;
                int level = vp.level;
                ans[level] = nn.data;
                if(nn.left != null) qu.add(new viewPair(nn.left, level - 1));
                if(nn.right != null) qu.add(new viewPair(nn.right, level + 1));
            }
        }
        for(int val : ans) System.out.println(val);
    }

    public static void bottomViewLeftPref(Node root) {
        int[] w = new int[2];
        width(root, w, 0);
        int my_size = w[1] - w[0];
        viewPair[] ans = new viewPair[my_size + 1];
        Queue<viewPair> qu = new LinkedList<>();
        qu.add(new viewPair(root, Math.abs(w[0]), 0));
        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                viewPair vp = qu.remove();
                Node nn = vp.node;
                int level = vp.level;
                int height = vp.height;
                
                if(ans[level] == null) ans[level] = vp;
                else if(ans[level].height < height) {
                    ans[level] = vp;
                }

                if(nn.left != null) qu.add(new viewPair(nn.left, level - 1, height + 1));
                if(nn.right != null) qu.add(new viewPair(nn.right, level + 1, height + 1));
            }
        }
        for(viewPair v : ans) {
            System.out.println(v.node.data + " ");
        }
    }

    public static void topView(Node root) {
        int[] w = new int[2];
        width(root, w, 0);
        int my_size = w[1] - w[0];
        viewPair ans[] = new viewPair[my_size + 1];
        Queue<viewPair> qu = new LinkedList<>();
        qu.add(new viewPair(root, Math.abs(w[0])));
        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                viewPair vp = qu.remove();
                Node node = vp.node;
                int level = vp.level;
                if(ans[level] == null) {
                    ans[level] = vp;
                }
                if(node.left != null) qu.add(new viewPair(node.left, level - 1));
                if(node.right != null) qu.add(new viewPair(node.right, level + 1));
            }
        }
        for(viewPair v : ans) System.out.println(v.node.data);
        System.out.println("----------------------------------------------------------");
        // topViewExtremes(ans, w);
    }

    public static void topViewExtremes(viewPair[] ans, int[] w) {
        int shiftedOrigin = Math.abs(w[0]);
        int i = shiftedOrigin;
        int j = i - 1, k = i + 1;
        System.out.println(ans[i].node.data + " ");
        while(j >= 0 || k < ans.length) {
            if(j >= 0) System.out.print(ans[j].node.data + " ");
            if(k < ans.length) System.out.print(ans[k].node.data + " ");
            System.out.println();
            j--; k++;
        }
    }

    public static void diagView(Node root) {
        int[] w = new int[2];
        width(root, w, 0);
        int my_size = Math.abs(w[0]);
        ArrayList<Integer>[] ans = new ArrayList[my_size + 1];
        for(int i = 0; i < ans.length; i++) ans[i] = new ArrayList<>(); 
        Queue<viewPair> qu = new LinkedList<>();
        qu.add(new viewPair(root, 0));
        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                viewPair vp = qu.remove();
                Node node = vp.node;
                int level = vp.level;
                ans[level].add(node.data);
                if(node.left != null) qu.add(new viewPair(node.left, level + 1));
                if(node.right != null) qu.add(new viewPair(node.right, level));
            }
        }
        for(ArrayList al : ans) {
            System.out.println(al);
        }
    }

    public static void adiagView(Node root) {
        int[] w = new int[2];
        width(root, w, 0);
        int my_size = w[1];
        ArrayList<Integer>[] ans = new ArrayList[my_size + 1];
        for(int i = 0; i < ans.length; i++) {
            ans[i] = new ArrayList<>();
        }
        Queue<viewPair> qu = new LinkedList<>();
        qu.add(new viewPair(root, 0));
        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                viewPair vp = qu.remove();
                Node node = vp.node;
                int level = vp.level;
                ans[level].add(node.data);
                if(node.left != null) qu.add(new viewPair(node.left, level));
                if(node.right != null) qu.add(new viewPair(node.right, level + 1));
            }
        }
        for(ArrayList al : ans) {
            System.out.println(al);
        }
    }
    //

    //binary tree to doubly circular List
    static Node head = null, tail = null;
    public static void btreeToClist_helper(Node root) {
        if(root == null) return;
        btreeToClist_helper(root.left);
        if(head == null) {
            head = root;
        } else {
            tail.right = root;
            root.left = tail;
        }
        tail = root;
        btreeToClist_helper(root.right);
    }

    public static Node  btreeToClist(Node root) {
        btreeToClist_helper(root);
        head.left = tail;
        tail.right = head;
        return head;
    }

    public static void clistTraverse(Node root) {
        Node h = btreeToClist(root);
        Node ptr = h.right;
        System.out.print(h.data + " ");
        while(ptr != h) {
            System.out.print(ptr.data + " ");
            ptr = ptr.right;
        }
    }

    // morris
    public static Node giveRightMost(Node next, Node curr) {
        while(next.right != null && next.right != curr) {
            next = next.right;
        }
        return next;
    }

    public static void morrisInorder(Node root) {
        Node curr = root;
        while(curr != null) {
            Node next = curr.left;
            if(next == null) {
                System.out.print(curr.data + " ");
                curr = curr.right;
            } else {
                Node rightMost = giveRightMost(next, curr);
                if(rightMost.right == null) {
                    rightMost.right = curr;
                    curr = curr.left;
                } else {
                    rightMost.right = null;
                    System.out.print(curr.data + " ");
                    curr = curr.right;
                }
            }
        }
    }

    public static void morrisPreorder(Node root) {
        Node curr = root;
        while(curr != null) {
            Node next = curr.left;
            if(next == null) {
                System.out.print(curr.data + " ");
                curr = curr.right;
            } else {
                Node rightMost = giveRightMost(next, curr);
                if(rightMost.right == null) {
                    System.out.print(curr.data + " ");
                    rightMost.right = curr;
                    curr = curr.left;
                } else {
                    rightMost.right = null;
                    curr = curr.right;
                }
            }
        }
    }

    public static void connectLeaves_helper(Node root) {
        if(root == null) {
            return;
        }
        connectLeaves_helper(root.left);
        if(root.left == null && root.right == null) {
            if(head == null) {
                head = root;
            } else {
                tail.right = root;
                root.left = tail;
            }
            tail = root;
        }
        connectLeaves_helper(root.right);
    }

    public static void connectLeaves(Node node) {
        connectLeaves_helper(node);
        head.left = tail;
        tail.right = head;
        Node ptr = head.right;
        System.out.print(head.data + " ");
        while(ptr != tail.right) {
            System.out.print(ptr.data + " ");
            ptr = ptr.right;
        }
    }

    // boundary traversal
    
    public static void boundaryTraversal(Node root) {
        int[] w = new int[2];
        width(root, w, 0);
        int my_size = w[1] - w[0];
        Node[] ans = new Node[my_size + 1];
        Queue<viewPair> qu = new LinkedList<>();
        ArrayList<Integer> leaves = new ArrayList<>();
        qu.add(new viewPair(root, Math.abs(w[0])));
        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                boolean isNodeLeaf = false;
                viewPair vp = qu.remove();
                Node node = vp.node;
                int level = vp.level;
                if(node.left == null && node.right == null) {
                    isNodeLeaf = true;
                }
                if(ans[level] == null) {
                    ans[level] = node;
                    isNodeLeaf = false;
                }
                if(isNodeLeaf) leaves.add(node.data);
                if(node.left != null) qu.add(new viewPair(node.left, level - 1));
                if(node.right != null) qu.add(new viewPair(node.right, level + 1));
            }
        }
        //print boundary 
        for(int i = Math.abs(w[0]); i >= 0; i--) {
            System.out.print(ans[i].data + " ");
        }
        for(int nn : leaves) {
            System.out.print(nn + " ");
        }
        for(int i = ans.length - 1; i > Math.abs(w[0]); i--) {
            System.out.print(ans[i].data + " ");
        }   
    }

    public static void solve() {
        int[] arr = {10,20,40,-1,-1,50,80,-1,-1,90,-1,-1,30,60,100,-1,-1,-1,70,110,-1,-1,120,-1,-1};
        // int[] arr = {10, 2, -1, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 3, -1, -1};
        Node root = construct(arr);
        boundaryTraversal(root);
    }
    public static void main(String args[]) {
        solve();
    }

    public static void preorder(Node root) {
        if(root == null) return;
        System.out.print(root.data + " ");
        preorder(root.left);
        preorder(root.right);
    }
}