import java.util.*;
import java.lang.*;
class TreeView{
    public static void main(String args[]){
        solve();
    }
    public static class Node{
        Node left;
        Node right;
        int val;
        public Node(int val){
            this.val=val;
        }
    }
    static int idx=0;
    public static Node construct(int[] arr){
        if(idx==arr.length || arr[idx]==-1){
            idx++;
            return null;
        }
        Node self=new Node(arr[idx]);
        idx++;
        self.left=construct(arr);
        self.right=construct(arr);
        return self;
    }
    public static void display(Node root){
        if(root==null){
            return;
        }
        System.out.print(root.val+" ");
        display(root.left);
        display(root.right);
    }
    //bfs--------------------------------------------------
    public static void bfs_01(Node root){
        Stack<Node> main=new Stack<>();
        Stack<Node> helper=new Stack<>();
        int lvl=1;
         main.push(root);
         while(main.size()>0){  
             Node cn=main.pop();
             System.out.print(cn.val+" ");
             if((lvl & 1)==1){
                 if(cn.left!=null){
                     helper.push(cn.left);
                 }
                 if(cn.right!=null){
                     helper.push(cn.right);
                 }
             }
             else{
                 if(cn.right!=null){
                     helper.push(cn.right);
                 }
                 if(cn.left!=null){
                     helper.push(cn.left);
                 }
             }
             if(main.size()==0){
                 System.out.println();
                 lvl++;
                 Stack<Node> temp=main;
                 main=helper;
                 helper=temp;
             }
         }
    }
    //left view
    public static void Left_view(Node root){
        Queue<Node> qu=new LinkedList<>();
        qu.add(root);
        int lvl=1;
        while(qu.size()>0){
            int size=qu.size();
            System.out.print(qu.peek().val+" ");
            while(size-- > 0){
                Node cn=qu.remove();
                if(cn.left!=null){
                    qu.add(cn.left);
                }
                if(cn.right!=null){
                    qu.add(cn.right);
                }
            }
        }
    }
    public static void right_View(Node root){
        Queue<Node> qu=new LinkedList<>();
        qu.add(root);
        while(qu.size()>0){
            int size=qu.size();
            Node prev=null;
            while(size-- >0){
                Node cn=qu.remove();
                prev=cn;
                if(cn.left!=null) qu.add(cn.left);
                if(cn.right!=null) qu.add(cn.right);
            }
            System.out.print(prev.val+" ");
        }
    }
    //width
    public static void width(Node root,int[] minmax,int level){
        if(root==null){
            return;
        }
        width(root.left,minmax,level-1);
        width(root.right,minmax,level+1);
        minmax[0]=Math.min(minmax[0],level);
        minmax[1]=Math.max(minmax[1],level);
    }
    //vertical order
    public static class pair{
        Node node;
        int level;
        public pair(Node node,int level){
            this.node=node;
            this.level=level;
        }
    }
    public static void vertical_order(Node root){
        int[] minmax=new int[2];
        width(root,minmax,0);
        ArrayList<Integer>[] ans=new ArrayList[minmax[1]+Math.abs(minmax[0])+1];
        for(int i=0;i<ans.length;i++){
            ans[i]=new ArrayList<>();
        }
        Queue<pair> qu=new LinkedList<>();
        qu.add(new pair(root,Math.abs(minmax[0]))); 
        while(qu.size()>0){
            int size=qu.size();
            while(size-- > 0){
                pair cp=qu.remove();
                ans[cp.level].add(cp.node.val);
                if(cp.node.left!=null){
                    qu.add(new pair(cp.node.left,cp.level-1));
                }
                if(cp.node.right!=null){
                    qu.add(new pair(cp.node.right,cp.level+1));
                }
            }
        }
       for(int i=0;i<ans.length;i++){
           for(int j=0;j<ans[i].size();j++){
               System.out.print(ans[i].get(j)+" ");
           }
           System.out.println();
       }
    }
    public static void vertical_order_sum(Node root){
        int[] minmax=new int[2];
        width(root,minmax,0);
        Queue<pair> qu=new LinkedList<>();
        int[] ans=new int[minmax[1]+Math.abs(minmax[0])+1];
        qu.add(new pair(root,Math.abs(minmax[0])));
        while(qu.size()>0){
            int size=qu.size();
            while(size-->0){
                pair cp=qu.remove();
                int level=cp.level;
                Node node=cp.node;
                ans[level]+=node.val;
                if(node.left!=null){
                    qu.add(new pair(node.left,level-1));
                }
                if(node.right!=null){
                    qu.add(new pair(node.right,level+1));
                }
            }
        }
       for(int val:ans){
           System.out.println(val+" ");
       }
    }
    //bottom view
    public static void bottom_view_right(Node root){
        int[] minmax=new int[2];
        width(root,minmax,0);
        Queue<pair> qu=new LinkedList<>();
        int[] ans=new int[minmax[1]+Math.abs(minmax[0])+1];
        qu.add(new pair(root,Math.abs(minmax[0])));
        while(qu.size()>0){
            int size=qu.size();
            while(size-->0){
                pair cp=qu.remove();
                int level=cp.level;
                Node node=cp.node;
                ans[level]=node.val;
                if(node.left!=null){
                    qu.add(new pair(node.left,level-1));
                }
                if(node.right!=null){
                    qu.add(new pair(node.right,level+1));
                }
            }
        }
       for(int val:ans){
           System.out.println(val+" ");
       }
    }
    //bottom view with left elements
    public static class bpair{
        Node node;
        int level;
        int height;
        public bpair(Node node,int level,int height){
            this.node=node;
            this.level=level;
            this.height=height;
        }
    }
    public static void  bottom_view_left(Node root){
        int[] minmax=new int[2];
        width(root,minmax,0);
        bpair[] ans=new bpair[minmax[1]+Math.abs(minmax[0])+1];
        Queue<bpair> qu=new LinkedList<>();
        qu.add(new bpair(root,Math.abs(minmax[0]),0));
        while(qu.size()>0){
            int size=qu.size();
            while(size-->0){
                bpair cp=qu.remove();
                Node cn=cp.node;
                int level=cp.level;
                int height=cp.height;
                if(ans[level]==null){
                    ans[level]=cp;
                }   
                else if(ans[level].height<height){
                    ans[level]=cp;
                }
                if(cn.left!=null){
                    qu.add(new bpair(cn.left,level-1,height+1));
                }
                if(cp.node.right!=null){
                    qu.add(new bpair(cn.right,level+1,height+1));
                }
            }
        }
        for(int i=0;i<ans.length;i++){
            System.out.println(ans[i].node.val+" ");
        }
    }
    public static void top_view(Node root){
        int[] minmax=new int[2];
        width(root,minmax,0);
        int[] ans=new int[minmax[1]+Math.abs(minmax[0])+1];
        Queue<pair> qu=new LinkedList<>();
        qu.add(new pair(root,Math.abs(minmax[0])));
        while(qu.size()>0){
            int size=qu.size();
            while(size-->0){
                pair cp=qu.remove();
                Node cn=cp.node;
                int level=cp.level;
                if(ans[level]==0){
                    ans[level]=cn.val;
                }   
                if(cn.left!=null){
                    qu.add(new pair(cn.left,level-1));
                }
                if(cn.right!=null){
                    qu.add(new pair(cn.right,level+1));
                }
            }
        }
        for(int i=0;i<ans.length;i++){
            System.out.print(ans[i]+" ");
        }
    }
    public static void diag_view(Node root){
        int[] minmax=new int[2];
        width(root,minmax,0);
        ArrayList<Integer>[] ans=new ArrayList[Math.abs(minmax[0])+1];
        for(int i=0;i<ans.length;i++){
            ans[i]=new ArrayList<>();
        }
        Queue<pair> qu=new LinkedList<>();
        qu.add(new pair(root,0));
        while(qu.size()>0){
            int size=qu.size();
            while(size-->0){
                pair cp=qu.remove();
                Node cn=cp.node;
                int level=cp.level;
                ans[level].add(cn.val);
                if(cn.left!=null){
                    qu.add(new pair(cn.left,level+1));
                }
                if(cn.right!=null){
                    qu.add(new pair(cn.right,level));
                }
            }
        }
        for(int i=0;i<ans.length;i++){
            for(int j=0;j<ans[i].size();j++){
                System.out.print(ans[i].get(j)+" ");
            }
            System.out.println();
        }
    }
    public static void Adiag_view(Node root){
        int[] minmax=new int[2];
        width(root,minmax,0);
        ArrayList<Integer>[] ans=new ArrayList[minmax[1]+1];
        for(int i=0;i<ans.length;i++){
            ans[i]=new ArrayList<>();
        }
        Queue<pair> qu=new LinkedList<>();
        qu.add(new pair(root,0));
        while(qu.size()>0){
            int size=qu.size();
            while(size-->0){
                pair cp=qu.remove();
                Node cn=cp.node;
                int level=cp.level;
                ans[level].add(cn.val);
                if(cn.left!=null){
                    qu.add(new pair(cn.left,level));
                }
                if(cn.right!=null){
                    qu.add(new pair(cn.right,level+1));
                }
            }
        }
        for(int i=0;i<ans.length;i++){
            for(int j=0;j<ans[i].size();j++){
                System.out.print(ans[i].get(j)+" ");
            }
            System.out.println();
        }
    }
    public static void solve(){
        int[] arr = {10,20,40,-1,-1,50,80,-1,-1,90,-1,-1,30,60,100,-1,-1,-1,70,110,-1,-1,120,-1,-1};
        Node root=construct(arr);
        // display(root);//preorder
        System.out.println();
        // bfs_01(root);
        // Left_view(root);
        // right_View(root);
        vertical_order(root);
        // vertical_order_sum(root);
        // bottom_view_right(root);
        // bottom_view_left(root);
        // top_view(root);
        // diag_view(root);
        // Adiag_view(root);
    }
}