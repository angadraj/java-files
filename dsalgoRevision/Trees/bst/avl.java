import java.util.*;
class avl {
    static class Node {
        Node left = null;
        Node right = null;
        int data;
        int bal = 0;
        int height = 0;
        public Node(int data) {
            this.data = data;
        }
    }

    public static void updateHeightBalance(Node root) {
        int lh = -1;
        int rh = -1;
        if(root.left != null) lh = root.left.height;
        if(root.right != null) rh = root.right.height;
        int b = lh - rh;
        int h = Math.max(lh, rh) + 1;
        root.bal = b;
        root.height = h;
    }
    
    public static Node LLRotation(Node A) {
        Node B = A.left;
        Node BRight = B.right;
        B.right = A;
        A.left = BRight;
        updateHeightBalance(A);
        updateHeightBalance(B);
        return B;
    }

    public static Node RRRotation(Node A) {
        Node B = A.right;
        Node BLeft = B.left;
        B.left = A;
        A.right = BLeft;
        updateHeightBalance(A);
        updateHeightBalance(B);
        return B;
    }

    public static Node checkRotation(Node root) {
        updateHeightBalance(root);
        if(root.bal == 2) {
            if(root.left.bal == 1) {
                return LLRotation(root);
            } else {
                root.left = RRRotation(root.left);
                return LLRotation(root);
            }
        } else if(root.bal == -2) { 
            if(root.right.bal == -1) {
               return RRRotation(root);
            } else {
                root.right = LLRotation(root.right);
                return RRRotation(root);
            }
        }
        return root;
    }

    public static Node addNode(Node root, int data) {
        if(root == null) return new Node(data);
        if(root.data > data) root.left = addNode(root.left, data);
        else if(root.data < data) root.right = addNode(root.right, data);
        root = checkRotation(root);
        return root;
    }

    public static Node getMaxFromLeft(Node root) {
        Node ptr = root;
        while(ptr.right != null) {
            ptr = ptr.right;
        } 
        return ptr;
    }

    public static Node remove(Node root, int tar) {
       if(root == null) return null;
       if(root.data == tar) {
        if(root.left == null && root.right == null) return null;
        else if(root.left == null || root.right == null) {
            return (root.left == null) ? root.right : root.left;
        } else {
            Node potentialNode = getMaxFromLeft(root.left);
            root.data = potentialNode.data;
            root.left = remove(root.left, potentialNode.data);
        }
       } else if(root.data > tar) {
        root.left = remove(root.left, tar);
       } else if(root.data < tar) {
           root.right = remove(root.right, tar);
       }
       root = checkRotation(root);
       return root;
    }

    public static void display(Node root) {
        if(root == null) return;
        String ans = "";
        ans += root.data + " -> ";
        if(root.left != null) ans += root.left.data + ", ";
        if(root.right != null) ans += root.right.data + " ";
        ans += ".";
        System.out.println(ans);
        display(root.left);
        display(root.right);
    }

    public static void solve() {
        Node root = null;
        for(int i = 1; i < 10; i++) {
            root = addNode(root, i);
        }
        display(root);
        System.out.println("------------------------------------------");
        Node ans = remove(root, 2);
        display(ans);
    }
    public static void main(String args[]) {
        solve();
    }
}
