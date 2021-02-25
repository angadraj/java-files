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

    public static void solve() {
        int[] arr = {10, 20, 50, -1, 60, -1, -1, 30, 70, -1, 80, 110, -1, 120, -1, -1, 90, -1, -1, 40, 100, -1, -1, -1};
        Node root = contructIterative(arr);
        // display(root);
        // System.out.println(height(root));
        levelOrderZZ(root);
    }
    public static void main(String args[]){
       solve();
    }
}
