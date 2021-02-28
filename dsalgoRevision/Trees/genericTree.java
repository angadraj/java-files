import java.util.*;

class genericTree {
    private static class Node {
        int data;
        ArrayList<Node> children = new ArrayList<>();
        public Node(int val) {
            data = val;
        }
    }

    public static Node contructIterative(int[] arr){
        Stack<Node> st = new Stack<>();
        Node root = null;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == -1){
                st.pop();
            } else {
                Node n = new Node(arr[i]);
                if(st.size() > 0) {
                    st.peek().children.add(n);
                } else {    
                    root = n;
                }
                st.push(n);
            }
        }
        return root;
    }

    public static void display(Node root){
        String ans = root.data + "-> ";
        for(int i = 0; i < root.children.size(); i++){
            Node n = root.children.get(i);
            ans += n.data;
            if(i != root.children.size() - 1) {
                ans += ",";
            }
        }
        System.out.println(ans + ".");
        for(Node child : root.children) {
             display(child);
        }
    }

    public static int size(Node root) {
        int count = 0;
        for(Node child : root.children){
            count += size(child);
        }
        return count + 1;
    }
    
    public static int maximum(Node root) {
        int max = root.data;
        for(Node child : root.children) {
            max = Math.max(max, maximum(child));
        }
        return max;
    }

    public static int height(Node root) {
        int h = -1;
        //for edges strore -1 , for nodes store 0;
        for(Node child : root.children) {
            h = Math.max(h, height(child));
        }
        return h + 1;
    }   

    public static void levelOrder_01(Node root) {
        Queue<Node> qu = new LinkedList<>();
        qu.add(root);
        while(qu.size() > 0) {
            //batch wise template
            int size = qu.size();
            while(size-- > 0) {
                //remove
                Node cn = qu.remove();
                //print
                System.out.print(cn.data + " ");
                //add children
                for(Node child : cn.children) {
                    qu.add(child);
                }
           }
        }
        System.out.print(".");
    }

    public static void levelOrderLineWise(Node root) {
        Queue<Node> qu = new LinkedList<>();
        qu.add(root);
        while(qu.size() > 0) {
            int size = qu.size();
            while(size-- > 0) {
                Node cn = qu.remove();
                System.out.print(cn.data + " ");
                for(Node child : cn.children) {
                    qu.add(child);
                }
            }
            System.out.println();
        }
    }

    public static void lineWiseUsingNull(Node root) {
        Queue<Node> qu = new LinkedList<>();
        qu.add(root);
        qu.add(null);
        while(qu.size() > 0) {
            Node cn = qu.remove();
            if(cn != null) {
                System.out.print(cn.data + " ");
                for(Node child : cn.children) {
                    qu.add(child);
                }
            } else {
                if(qu.size() > 0) {
                    qu.add(null);
                    System.out.println();
                }
            }
        }
    }

    private static class Pair {
        Node n;
        int lvl;
        public Pair(Node n, int lvl) {
            this.n = n;
            this.lvl = lvl;
        }
    }

    public static void lineWiseUsingPair(Node root) {
        Queue<Pair> qu = new LinkedList<>();
        qu.add(new Pair(root, 1));
        int curr_lvl = 1;
        while(qu.size() > 0) {
            Pair p = qu.remove();
            if(p.lvl > curr_lvl) {
                curr_lvl = p.lvl;
                System.out.println();
            }
            System.out.print(p.n.data + " ");
            for(Node child : p.n.children) {
                qu.add(new Pair(child, curr_lvl + 1));
            }
        }
    }

    public static void levelOrderZZ(Node root) {
        Stack<Node> ms = new Stack<>();
        Stack<Node> cs = new Stack<>();
        ms.push(root);
        int level = 0;
        while(ms.size() > 0) {
            int size = ms.size();
            while(size-- > 0) {
                Node cn = ms.pop();
                System.out.print(cn.data + " ");
                if((level & 1) == 0) {
                    for(int i = 0; i < cn.children.size(); i++) {
                        cs.push(cn.children.get(i));
                    }
                } else if((level & 1) == 1) {
                    for(int i = cn.children.size() - 1; i >= 0; i--) {
                        cs.push(cn.children.get(i));
                    }
                }
            }
            System.out.println();
            level++;
            Stack temp = ms;
            ms = cs;
            cs = temp;
        }
    }

    public static void mirrorGTree(Node root) {
        for(int i = 0, j = root.children.size() - 1; i < j; i++, j--) {
            Node temp = root.children.get(i);
            root.children.set(i, root.children.get(j));
            root.children.set(j, temp);
        }
        // you can also use Collections.reverse(ArrayList) -> O(n)
        for(Node child : root.children) {
            mirrorGTree(child);
        }
    }

    public static void removeLeaves(Node root) {
        for(int i = root.children.size() - 1; i >= 0; i--) {
            Node cn = root.children.get(i);
            if(cn.children.size() == 0) {
                root.children.remove(cn);
            }
        }
        // removal in an AL should be done in reverse order
        for(Node child : root.children) {
            removeLeaves(child);
        }
    }

    private static Node getTail(Node root) {
        while(root.children.size() == 1) {
            Node next_root = root.children.get(0);
            root = next_root;
        }
        return root;
    }
    public static void Linearize(Node root) {
        for(Node child : root.children) {
            Linearize(child);
        }
        while(root.children.size() > 1) {
            Node last_child = root.children.remove(root.children.size() - 1);
            Node second_last_child = root.children.get(root.children.size() - 1);
            Node second_last_child_tail = getTail(second_last_child);
            second_last_child_tail.children.add(last_child);
        }
    }

    public static Node Linearize_02(Node root) {
        if(root.children.size() == 0) return root;
        Node last_child_tail = Linearize_02(root.children.get(root.children.size() - 1));
        while(root.children.size() > 1) {
            Node last_child = root.children.remove(root.children.size() - 1);
            Node second_last_child = root.children.get(root.children.size() - 1);
            Node second_last_tail = Linearize_02(second_last_child);
            second_last_tail.children.add(last_child);
        }
        return last_child_tail;
    }

    public static boolean findInGTree(Node root, int val) {
        if(root.data == val) return true;
        for(Node child : root.children) {
            boolean ans = findInGTree(child, val);
            if(ans) return true;
        }
        return false;
    }

    public static ArrayList<Integer> NTRP(Node root, int val) {
        if(root.data == val) {
            ArrayList<Integer> recAns = new ArrayList<>();
            recAns.add(root.data); 
            return recAns;
        }
        for(Node child : root.children) {
            ArrayList<Integer> baseAns = NTRP(child, val);
            if(baseAns.size() > 0) {
                baseAns.add(root.data);
                return baseAns;
            }
        }
        return new ArrayList<Integer>();
    }

    public static int lca(Node root, int val1, int val2) {
        ArrayList<Integer> p1 = NTRP(root, val1);
        ArrayList<Integer> p2 = NTRP(root, val2);
        int i = p1.size() - 1, j = p2.size() - 1;
        while(i >= 0 && j >= 0 && p1.get(i) == p2.get(j)) {
           i--; 
           j--;
        }
        return p1.get(i + 1);
    }

    public static int distanceBetweenNodes(Node root, int val1, int val2) {
        ArrayList<Integer> p1 = NTRP(root, val1);
        ArrayList<Integer> p2 = NTRP(root, val2);
        int i = p1.size() - 1, j = p2.size() - 1;
        while(i >= 0 && j >= 0 && p1.get(i) == p2.get(j)) {
            i--; j--;
        }
        i++; j++;
        return (i + j);
    } 

    public static boolean isSimilarInShape(Node root1, Node root2) {
        if(root1.children.size() != root2.children.size()) {
            return false;
        }
        for(int i = 0; i < root1.children.size(); i++) {
            Node c1 = root1.children.get(i);
            Node c2 = root2.children.get(i);
            boolean ans = isSimilarInShape(c1, c2); 
            if(!ans) return false;
        }
        return true;
    }

    public static boolean isMirrorInShape(Node r1, Node r2) {
        if(r1.children.size() != r2.children.size()) {
            return false;
        }
        for(int i = 0, j = r1.children.size() - 1; i < j; i++, j--) {
            Node c1 = r1.children.get(i);
            Node c2 = r2.children.get(j);
            boolean ans = isMirrorInShape(c1, c2);
            if(!ans) return false;
        }
        return true;
    }
    //arr[] = size, min, max, height
    public static int multisolver(Node root, int[] ans) {
        int my_h = -1;
        ans[0]++;
        ans[1] = Math.min(root.data, ans[1]);
        ans[2] = Math.max(root.data, ans[2]);
        for(Node child : root.children) {
            my_h = Math.max(my_h, multisolver(child, ans));
        }
        ans[3] = my_h + 1;
        return my_h + 1;
    }

    public static boolean isSymmetric(Node root) {
        return isMirrorInShape(root, root);
    }

    static int h = -1;
    public static void height_2(Node root, int depth) {
        h = Math.max(h, depth);
        for(Node child : root.children) {
            height_2(child, depth + 1);
        }
    }

    static int pre, succ, state;
    public static void predSucc(Node root, int val) {
        if(root.data == val && state == 0) {
            state++;
        } else if(state == 1) {
            succ = root.data;
            state++;
            return;
        } else if(state == 0) {
            pre = root.data;
        }
        for(Node child : root.children) {
            predSucc(child, val);
        }
    }

    static int floor = -(int)(1e8), ceil = (int)(1e8);
    public static void ceilNFloor(Node root, int val) {
        if(root.data > val) {
            ceil = Math.min(ceil, root.data);
        } else if(root.data < val) {
            floor = Math.max(root.data, floor);
        }
        for(Node child : root.children) {
            ceilNFloor(child, val);
        }
    }

    public static int kthLargest(Node root, int k) {
        int ans = (int)(1e8);
        for(int i = 0; i < k; i++) {
            ceilNFloor(root, ans);
            ans = floor;
            floor = -(int)(1e8);
        }
        return ans;
    }

    static Node n = null;
    static int sum = -(int)(1e8);
    public static int maxSubTreeSum(Node root) {
        int mySum = 0;
        for(Node child : root.children) {
            mySum += maxSubTreeSum(child);
        }
        mySum += root.data; // make subtree sum
        if(mySum > sum) {
            sum = mySum;
            n = root;
        }
        return mySum;
    }

    static int dia = -(int)(1e8);
    public static int diameter(Node root) {
        int dc = -1;
        int sdc = -1;
        for(Node child : root.children) {
            int child_height = diameter(child);
            if(child_height > dc) {
                sdc = dc;
                dc = child_height;
            } else if(child_height > sdc) {
                sdc = child_height;
            }
        }
        if(sdc + dc + 2 > dia) {
            dia = sdc + dc + 2;
        }
        return dc + 1;
    }

    private static class nodenState {
        Node n;
        int state;
        public nodenState(Node node, int s) {
            n = node;
            state = s;
        }
    }
    public static void iterativePrePro(Node root) {
        Stack<nodenState> st = new Stack<>();
        String pre = "", post = "";
        st.push(new nodenState(root, -1));
        while(st.size() > 0) {
            nodenState ns = st.peek();
            if(ns.state == -1) {
                pre += ns.n.data + " ";
                ns.state++;
            } else if(ns.state == ns.n.children.size()) {
                post += ns.n.data + " ";
                st.pop();
            } else {
                Node child = ns.n.children.get(ns.state);
                st.push(new nodenState(child, -1));
                ns.state++;
            }
        }
        System.out.println(pre);
        System.out.println(post);
    }

    public static void solve() {
        int[] arr = {10, 20, 50, -1, 60, -1, -1, 30, 70, -1, 80, 110, -1, 120, -1, -1, 90, -1, -1, 40, 100, -1, -1, -1};
        Node root = contructIterative(arr);
        // display(root);
        iterativePrePro(root);
    }

    public static void main(String args[]){
       solve();
    }
}
