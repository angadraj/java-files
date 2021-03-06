import java.util.*;
class main {
    private static class Node {
        int data;
        Node left;
        Node right;
        Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    static int idx = 0;
    public static Node construct_rec(Integer arr[]) {
        if(idx == arr.length || arr[idx] == null) {
            idx++;
            return null;
        }
        Node node = new Node(arr[idx], null, null);
        idx++;
        node.left = construct_rec(arr);
        node.right = construct_rec(arr);
        return node;
    }

    private static class constrPair {
        Node node;
        int state;
        constrPair(Node node, int state) {
            this.node = node;
            this.state = state;
        }
    }   

    public static Node construct_iter(Integer arr[]) {
        Stack<constrPair> st = new Stack<>();
        Node root = new Node(arr[0], null, null);
        st.push(new constrPair(root, 1));
        int idx = 0;
        while(st.size() > 0) {
            constrPair cp = st.peek();
            if(cp.state == 1) {
                idx++;
                if(arr[idx] != null) {
                    cp.node.left = new Node(arr[idx], null, null);
                    st.push(new constrPair(cp.node.left, 1));
                } else {
                    cp.node.left = null;
                }
                cp.state++;
            } else if(cp.state == 2) {
                idx++;
                if(arr[idx] != null) {
                    cp.node.right = new Node(arr[idx], null, null);
                    st.push(new constrPair(cp.node.right, 1));
                } else {
                    cp.node.right = null;
                }   
                cp.state++;
            } else if(cp.state == 3) {
                st.pop();
            }
        }
        return root;
    } 

    public static void display(Node root) {
        if(root == null) return;

        String str = "";
        if(root.left != null) {
            str += root.left.data + " - ";
        } else if(root.left == null) {
            str += " . ";
        }
        str += root.data;
        if(root.right != null) {
            str += " - " + root.right.data;
        } else {
            str += " . ";
        }
        System.out.println(str);
        display(root.left);
        display(root.right);
    }
    //size, sum, max, height
    public static int multisolver(Node root, int[] arr) {
        if(root == null) {
            return -1;
            //for edges return -1, else for nodes return 0;
        }
        arr[0]++;
        arr[1] += root.data;
        arr[2] = Math.max(arr[2], root.data);
        int ch1 = multisolver(root.left, arr);
        int ch2 = multisolver(root.right, arr);
        int my_h = Math.max(ch1, ch2);
        return arr[3] = my_h + 1; 
    }
    static String pre = "", post = "", in = "";
    public static void traversals(Node root) {
        if(root == null) return; 
        pre += root.data + " ";
        traversals(root.left);
        in += root.data + " ";
        traversals(root.right);
        post += root.data + " ";
    }

    public static void levelOrder_01(Node root) {
        Queue<Node> qu = new ArrayDeque<>();
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

    public static void iterativePrePro(Node root) {
        Stack<constrPair> st = new Stack<>();
        String preorder = "", postorder = "", inorder = "";
        st.push(new constrPair(root, -1));
        while(st.size() > 0) {
            constrPair cp = st.peek();
            if(cp.state == -1) {
                preorder += cp.node.data + " ";
                if(cp.node.left != null) {
                    st.push(new constrPair(cp.node.left, -1));
                }
                cp.state++;
            } else if(cp.state == 0) {
                inorder += cp.node.data + " ";
                if(cp.node.right != null) {
                    st.push(new constrPair(cp.node.right, -1));
                }
                cp.state++;
            } else if(cp.state == 1) {
                postorder += cp.node.data + " ";
                st.pop();
            } 
        }
        System.out.println(preorder);
        System.out.println(inorder);
        System.out.println(postorder);
    }

    public static ArrayList<Integer> ntrp(Node root, int val) {
        if(root == null) {
            return new ArrayList<Integer>();
        }
        if(root.data == val) {
            ArrayList<Integer> recAns = new ArrayList<>();
            recAns.add(root.data);
            return recAns;
        }
        ArrayList<Integer> lpath = ntrp(root.left, val);
        if(lpath.size() > 0) {
            lpath.add(root.data);
            return lpath;
        }
        ArrayList<Integer> rpath = ntrp(root.right, val);
        if(rpath.size() > 0) {
            rpath.add(root.data);
            return rpath;
        }
        return new ArrayList<Integer>();
    }

    static ArrayList<Node> path = new ArrayList<>();
    public static boolean find(Node root, int val) {
        if(root == null) return false;
        if(root.data == val) {
            path.add(root);
            return true;
        }
        if(find(root.left, val)) {
            path.add(root);
            return true;
        }
        if(find(root.right, val)) {
            path.add(root);
            return true;
        }
        return false;
    }

    public static void printKLevelDown(Node root, int k) {
        if(root == null || k < 0) return;
        if(root != null && k == 0) {
            System.out.print(root.data + " ");
            return;
        }
        printKLevelDown(root.left, k - 1);
        printKLevelDown(root.right, k - 1);
    }   

    public static void printKLevelDown_02(Node root, int k, Node prev) {
        if(root == null || root == prev || k < 0) return;
        if(k == 0) {
            System.out.println(root.data + " ");
            return;
        }
        printKLevelDown_02(root.left, k - 1, prev);
        printKLevelDown_02(root.right, k - 1, prev);
    }

    public static void printKLevelFar(Node root, int k, int val) {
        if(root == null) return;
        if(k < 0) return;
        boolean ans = find(root, val);
        if(!ans) return;

        Node prev = null;
        for(int i = 0; i <= k; i++) {
            if(i > 0) {
                prev = path.get(i - 1);
            }
            printKLevelDown_02(path.get(i), k - i, prev);
        }
    }

    public static void printRootToLeafInRange(Node root, int lo, int hi) {
            ArrayList<Integer> res = new ArrayList<>();
            rootToLeafHelper(root, lo, hi, res, 0);
    }

    public static void rootToLeafHelper(Node root, int lo, int hi, ArrayList<Integer> res, int sum) {
        if(root == null) return;
        if(root.left == null && root.right == null) {
            sum += root.data;
            res.add(root.data);
            if(sum >= lo && sum <= hi) {
                System.out.print(res);
            }
            res.remove(res.size() - 1);
            return;
        }

        res.add(root.data);
        rootToLeafHelper(root.left, lo, hi, res, sum + root.data);
        // res.remove(res.size() - 1);
        // res.add(root.data);
        rootToLeafHelper(root.right, lo, hi, res, sum + root.data);
        res.remove(res.size() - 1);
    }

    public static Node leftCloned(Node root) {
        if(root == null) return null;
        Node lans = leftCloned(root.left);
        Node rans = leftCloned(root.right);
        Node clone = new Node(root.data, null, null);
        root.left = clone;
        clone.left = lans;
        return root;
    }

    public static Node backToNormal(Node root) {
        if(root == null) return null;
        Node lans = backToNormal(root.left.left);
        Node rans = backToNormal(root.right);
        Node clone_root = root.left;
        root.left = lans;
        clone_root.left = null;
        return root;
    }

    public static void printSingleNodes(Node root) {
        if(root == null) return;
        if(root.left == null && root.right != null) {
            System.out.println(root.right.data);
        } else if(root.left != null && root.right == null) {
            System.out.println(root.left.data);
        }   
        printSingleNodes(root.left);
        printSingleNodes(root.right);
    }

    public static Node removeLeaves(Node root) {
        if(root == null) return null;
        if(root.left == null && root.right == null) {
            return null;
        }
        root.left = removeLeaves(root.left);
        root.right = removeLeaves(root.right);
        return root;
    }

    public static int[] diameter_helper(Node root) {
        if(root == null) {
            //h[0] = h , [1] = dia
            // from null h = 0, dia = 0 -> 
            // h = max(-1, -1) + 1 = 0
            //dia = max(ld, rd, lh + rh +2) -> max(0, 0, -2 + 2) => max(0, 0, 0) == 0
            return new int[]{-1, 0};
        }
        int lans[] = diameter_helper(root.left);
        int rans[] = diameter_helper(root.right);
        int[] baseAns = new int[2];
        baseAns[0] = Math.max(rans[0], lans[0]) + 1;    
        baseAns[1] = Math.max(lans[1], Math.max(rans[1], lans[0] + rans[0] + 2));
        return baseAns;
    }

    public static int diameter(Node root) {
        int[] ans = diameter_helper(root);
        return ans[1];
    }

    public static int[] tilt_h(Node root) {
        if(root == null) {
            return new int[]{0, 0};
        }
        int lans[] = tilt_h(root.left);
        int rans[] = tilt_h(root.right);
        int[] my_ans = new int[2];
        my_ans[0] = lans[0] + rans[0] + root.data;
        my_ans[1] = Math.abs(lans[0] - rans[0]) + lans[1] + rans[1];
        return my_ans;
    }

    public static int tilt(Node root) {
        int[] ans = tilt_h(root);
        return ans[1];
    }
    static Integer prev = null;
    public static boolean isBST(Node root) {
        if(root == null) {
            return true;
        }
        boolean lans = isBST(root.left);
        if(!lans) return false;
        if(prev != null && root.data <= prev) return false;
        prev = root.data;
        boolean rans = isBST(root.right);
        if(!rans) return false;
        return true;
    }

    public static int[] isBst_02_h(Node root) {
        if(root == null) {
            return new int[]{1, (int)(1e8), -(int)(1e8)};
        }
        
        int[] lans = isBst_02_h(root.left);
        int[] rans = isBst_02_h(root.right);
        int my_ans[] = new int[3];
        my_ans[0] = (lans[0] == 1 && rans[0] == 1 && root.data >= lans[2] && root.data <= rans[1] ? 1 : 0);
        my_ans[1] = Math.min(lans[1], Math.min(rans[1], root.data));
        my_ans[2] = Math.max(lans[2], Math.max(rans[2], root.data));
        return my_ans;
    }

    public static boolean isBst_02(Node root) {
        int[] ans = isBst_02_h(root);
        return (ans[0] == 1 ? true : false);
    }

    public static int[] isBal_h(Node root) {
        if(root == null) {
            return new int[]{1, -1};
        }
        int[] lans = isBal_h(root.left);
        int[] rans = isBal_h(root.right);
        int[] my_ans = new int[2];
        my_ans[0] = ((lans[0] == 1 && rans[0] == 1 && (Math.abs(lans[1] - rans[1])) <= 1) ? 1 : 0);
        my_ans[1] = Math.max(lans[1], rans[1]) + 1;
        return my_ans; 
    }

    public static boolean isBal(Node root) {
        int[] ans = isBal_h(root);
        return (ans[0] == 1 ? true : false);
    }

    static class lbpair {
        Node node;
        boolean isbst;
        int size;
        int min; 
        int max;
        public lbpair(){

        }
        public lbpair(Node node, boolean isbst, int size, int min, int max){
            this.node = node;
            this.isbst = isbst;
            this.size = size;
            this.min = min;
            this.max = max;
        }
    }
    public static lbpair largestBST_h(Node root) {
        if(root == null) {
            return new lbpair(null, true, 0, (int)(1e8), -(int)(1e8));
        }
        lbpair lans = largestBST_h(root.left);
        lbpair rans = largestBST_h(root.right);
        lbpair ans = new lbpair();
        ans.isbst = (lans.isbst && rans.isbst && root.data >= lans.max && root.data <= rans.min);
        ans.min = Math.min(root.data, Math.min(lans.min, rans.min));
        ans.max = Math.max(root.data, Math.max(lans.max, rans.max));
        if(ans.isbst) {
            ans.node = root;
            ans.size = rans.size + lans.size + 1;
        } else if(lans.size > rans.size) {
            ans.size = lans.size;
            ans.node = lans.node;
        } else if(lans.size < rans.size) {
            ans.size = rans.size;
            ans.node = rans.node;
        }
        return ans;
    }

    public static void largestBST(Node root) {
        lbpair ans = largestBST_h(root);
        System.out.println(ans.node.data + "@" + ans.size);
    }

    public static void solve() {
        Integer[] keys = {50, 25, 12, null, null, 37, 30, null, null, null, 75, 62, null, 70, null, null, 87, null, null};
        Node root = construct_rec(keys);
        largestBST(root);
    }
    public static void main(String args[]) {
        solve();
    }
}