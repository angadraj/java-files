import java.util.*;
class iterator_tree {

    public static class GTree implements Iterable<Integer> {
        Node root;
        GTree(Node root) {
            this.root = root;
        }
        public Iterator<Integer> iterator() {
            Iterator<Integer> obj = new preorderIterator(root);
            return obj;
        }
    }

    public static class preorderIterator implements Iterator<Integer> {
        Integer nval;   
        Stack<Pair> st;

        public preorderIterator(Node root) {
            st = new Stack<Pair>();
            st.push(new Pair(root, -1));
            next();
        }
        public boolean hasNext() {
            if(nval == null) {
                return false;
            } else {
                return true;
            }
        }
        public Integer next() {
            Integer fr = nval;
            //moves nval forward , else moves it to null
            nval = null;
            while(st.size() > 0) {
                Pair cp = st.peek();
                if(cp.state == -1) {
                    //preorder;
                    nval = cp.node.data;
                    cp.state++;
                    break;
                } else if(cp.state == cp.node.children.size()) {
                    st.pop();
                } else {
                    Node n = cp.node.children.get(cp.state);
                    st.push(new Pair(n, -1));
                    cp.state++;
                }
            }

            return fr;
        }
    }

    private static class Pair {
        Node node;
        int state;
        public Pair(Node node, int state) {
            this.node = node;
            this.state = state;
        }
    }

    static class Node {
        int data;
        ArrayList<Node> children = new ArrayList<>();
        Node(int val) {
            data = val;
        }
    }

    public static Node construct(int[] arr) {
        Stack<Node> st = new Stack<>();
        Node root = null;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == -1) {
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

    public static void display(Node root) {
        String str = "";
        str += root.data + "-> ";
        for(Node child : root.children) {
            str += child.data + ", ";
        }  
        System.out.println(str); 
        for(Node child : root.children) {
            display(child);
        }
    }

    public static void solve() {
        int[] arr = {10, 20, 50, -1, 60, -1, -1, 30, 70, -1, 80, 110, -1, 120, -1, -1, 90, -1, -1, 40, 100, -1, -1, -1};
        Node root = construct(arr); 
        GTree gt = new GTree(root);
        Iterator<Integer> pi = gt.iterator();
        //gti = preorderIterator class object

        while(pi.hasNext()) {
            System.out.print(pi.next() + " ");
        }

        // for(int val : gt) {
        //     System.out.print(val + " ");
        // }
    }

    public static void main(String args[]) {
        solve();
    }
}