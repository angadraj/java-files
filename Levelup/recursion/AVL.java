import java.util.*;
import java.lang.*;
class AVL{
    public static void main(String args[]){
        solve();
    }
    public static class Node{
        int data=0;
        Node left=null;
        Node right=null;
        int height=0;
        int bal=0;
        public Node(int data){
            this.data=data;
        }
    }
    public static void display(Node root){
        if(root==null){
            return;
        }
        display(root.left);
        System.out.print(root.data+" ");
        display(root.right);
    }
    public static void updateHeightBalance(Node node){
        int lh=-1;int rh=-1;
        if(node.left!=null){
            lh=node.left.height;
        }
        if(node.right!=null){
            rh=node.right.height;
        }
        int bal=lh-rh;
        int height=Math.max(lh,rh)+1;
        node.height=height;
        node.bal=bal;
    }
    public static Node rightRotation(Node A){
        Node B=A.left;
        Node Bright=B.right;
        B.right=A;
        A.left=Bright;
        updateHeightBalance(A);
        updateHeightBalance(B);
        return B;
    }
    public static Node leftRotation(Node A){
        Node B=A.right;
        Node Bleft=B.left;
        B.left=A;
        A.right=Bleft;
        updateHeightBalance(A);
        updateHeightBalance(B);
        return B;
    }
    public static Node getRotation(Node node){
        updateHeightBalance(node);
        if(node.bal==2){
            if(node.left.bal==1){
                return rightRotation(node);
            }
            else{
                node.left=leftRotation(node.left);
                return rightRotation(node);
            }
        }
        if(node.bal==-2){
            if(node.right.bal==-1){
                return leftRotation(node);
            }
            else{
                node.right=rightRotation(node.right);
                return leftRotation(node);
            }
        }
        return node;
    }
    public static Node addNode(Node root,int val){
        if(root==null){
           return new Node(val);
        }
        if(val<root.data){
            root.left=addNode(root.left,val);
        }
        else{
            root.right=addNode(root.right,val);
        }
        root=getRotation(root);
        return root;
    }
    public static Node removeNode(Node root,int val){
        if(root==null){
            return null;
        }
        if(val==root.data){
            if(root.left==null && root.right==null){
                return null;
            }
            else if(root.left==null){
                Node rc=root.right;
                root.right=null;
                return rc;
            }
            else if(root.right!=null){
                Node lc=root.left;
                root.left=null;
                return lc;
            }
            else{
                int potcandid=min(root.left);
                root.data=potcandid;
                root.left=removeNode(root,potcandid);
            }
        }
        else if(val<root.data){
            root.left=removeNode(root.left,val);
        }
        else{
            root.right=removeNode(root.right,val);
        }
        root=getRotation(root);
        return root;
    }
    public static int min(Node root){
        while(root.right!=null){
            root=root.right;
        }
        return root.data;
    }
    public static void solve(){
        Node root=null;
        // for(int i=1;i<=8;i++){
        //     root=addNode(root,i);
        // }
        root=addNode(root,9);
        root=addNode(root,10);
        root=addNode(root,12);
        display(root);
        // removeNode(root,140);
        // removeNode(root,3);
        // System.out.println();
        // display(root);
    }
}