import java.util.*;
import java.lang.*;
class Bst{
    public static void main(String args[]){
        solve();
    }
    public static class Node{
        int val;
        Node left;
        Node right;
        public Node(int val){
            this.val=val;
        }
    }
    public static Node construct(int arr[],int lo,int hi){
        if(lo>hi){
            return null;
        }
        int mid=(lo+hi)>>1;
        Node node=new Node(arr[mid]);
        node.left=construct(arr,lo,mid-1);
        node.right=construct(arr,mid+1,hi);
        return node;
    }
    public static void display(Node root){
        if(root==null){
            return;
        }
        display(root.left);
        System.out.print(root.val+" ");
        display(root.right);
    }
    static Node a=null,b=null;
    static Node prev=new Node(-(int)(1e8));
    public static void recover_bst(Node root){
        if(root==null){
            return;
        }
        recover_bst(root.left);
        if(prev.val>root.val){
            if(a==null){
                a=prev;
            }
            else{
                b=root;
                int temp=a.val;
                a.val=b.val;
                b.val=temp;
            }
        }
        prev=root;
        recover_bst(root.right);
    }
    //-------------tree form traversals
    static int idx=0;
    public static Node preorder_to_tree(int[] arr,int lr,int rr){
        if(idx>=arr.length || arr[idx]<lr || arr[idx]>rr){
            return null;
        }
        Node root=new Node(arr[idx]);
        idx++;
        root.left=preorder_to_tree(arr,lr,root.val);
        root.right=preorder_to_tree(arr,root.val,rr);
        return root;
    }
    public static Node postorder_to_tree(int[] arr,int lr,int rr){
        if(idx<0 || arr[idx]<lr || arr[idx]>rr){
            return null;
        }
        Node root=new Node(arr[idx]);
        idx--;
        root.right=postorder_to_tree(arr,root.val,rr);
        root.left=postorder_to_tree(arr,lr,root.val);
        return root;
    }
    public static Node levelorder_to_tree(int[] arr){
        if(arr.length==0){
            return null;
        }
        Node root=new Node(arr[0]);
        for(int i=1;i<arr.length;i++){
            helper(root,arr[i]);
        }
        return root;
    }
    public static void helper(Node root,int data){
        if(root==null){
            return;
        }
        if(data<root.val){
            if(root.left==null){
                root.left=new Node(data);
            }
            else{
                helper(root.left,data);
            }
        }
        if(data>root.val){
            if(root.right==null){
                root.right=new Node(data);
            }
            else{
                helper(root.right,data);
            }
        }

    }
    public static void pred_succ(Node root,int val){
        Node node=root;
        Node pred=null,succ=null;
        while(node!=null){
            if(node.val==val){
                if(node.left!=null){
                    pred=node.left;
                    while(pred.right!=null){
                        pred=pred.right;
                    }
                }
                if(node.right!=null){
                    succ=node.right;
                    while(succ.left!=null){
                        succ=succ.left;
                    }
                }
                break;
            }
            else if(node.val<val){
                pred=node;
                node=node.right;
            }
            else{
                succ=node;
                node=node.left;
            }
        }
        if(pred==null){
            System.out.println("null"+" "+succ.val+" ");
        }
        else if(succ==null){
            System.out.println(pred.val+" "+"null"+" ");
        }
        else{
            System.out.println(pred.val+" "+ succ.val+" ");
        }
    }
    // static public class pair{
    //     int lr=-(int)(1e8);
    //     int rr=(int)(1e8);
    //     Node parent=null;
    //     boolean isLeft=false;
    // }
    // public static void pre_to_tree(int[] arr){
    //     Queue<pair> qu=new LinkedList<>();
    //     qu.add(new pair());
    //     while(qu.size()>0){
    //         pair cp=qu.remove();
            
    //     }
    // }
    public static void solve(){
        // int arr[]={10,25,41,42,46,53,55,60,62,63,64,65,70,75};
        // Node root=construct(arr,0,arr.length-1);
        // Node root=postorder_to_tree(arr,-(int)(1e8),(int)(1e8));
        // Node root=levelorder_to_tree(arr);
        int arr[]={3,1,2,4};
        Node root=preorder_to_tree(arr,-(int)(1e8),(int)(1e8));
        display(root);
        System.out.println();
        pred_succ(root,2); 
        // recover_bst(root);
        // display(root);
        // int A=0,B=0;
        // for(int j=0,i=1;i<arr.length && j<arr.length;i++,j++){
        //     int prev=arr[j];
        //     int curr=arr[i];
        //     if(prev>curr){
        //         if(A==0){
        //             A=j;
        //         }
        //         else{
        //             B=i;
        //             int temp=arr[A];
        //             arr[A]=arr[B];
        //             arr[B]=temp;
        //         }
        //     }
            // prev=curr;
        //}
    }
}