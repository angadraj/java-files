import java.util.*;
import java.lang.*;
class GT{
    public static void main(String args[]){
        solve();
    }
    public static class Node{
        int val=0;
        ArrayList<Node> children;
        public Node(int val){
            this.val=val;
            children=new ArrayList<>();
        }
    }
    public static Node construct(int[] arr){
        Stack<Node> st=new Stack<>();
        for(int i=0;i<arr.length-1;i++){
            int x=arr[i];
            if(x!=-1){
                st.push(new Node(x));
            }
            else{
                Node y=st.pop();
                st.peek().children.add(y);
            }
        }
        return st.peek();
    }
    public static void display(Node root){
        System.out.print(root.val+"->");
        for(int i=0;i<root.children.size();i++){
            System.out.print(root.children.get(i).val+" ");
        }
        System.out.println();
        for(Node child:root.children){
            display(child);
        }
    }
    public static int height(Node root){
        int myh=-1;
        for(Node child:root.children){
             myh=Math.max(myh,height(child));
        }
        return myh+1;
    }
    public static void BFS_zz(Node root){
        Stack<Node> main=new Stack<>();
        Stack<Node> helper=new Stack<>();
        main.push(root);
        boolean lvl=false;
        while(main.size()>0){
            Node cn=main.pop();
            System.out.print(cn.val+" ");
            if(lvl==false){
                for(int i=cn.children.size()-1;i>=0;i--){
                    helper.push(cn.children.get(i));
                }
            }
            else{
                for(int i=0;i<cn.children.size();i++){
                    helper.push(cn.children.get(i));
                }
            }
            if(main.size()==0){
                System.out.println();
                lvl=true;
                Stack<Node> temp=main;
                main=helper;
                helper=temp;
            }
        }
    }
    public static boolean mirror(Node root1,Node root2){
        for(int i=0,j=root1.children.size()-1;i<=j;i++,j--){
            if(root1.children.get(i).val==root2.children.get(j).val){
                boolean ans=mirror(root1.children.get(i),root2.children.get(j));
                if(!ans){
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean lca(Node root,int a,int b){
        return false;

    }
    public static void solve(){
        int[] arr={10, 20, 50, -1, 60, -1, -1, 30, 70, -1, 80, 100, -1, 110, -1, -1, 90, -1, -1, 40, 120, 140, -1, 150, -1, -1, -1,-1};
        Node root=construct(arr);
        // display(root);
        BFS_zz(root);
        boolean ans=mirror(root,root);
        System.out.println(ans);
    }
}