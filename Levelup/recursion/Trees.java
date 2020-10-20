import java.util.*;
import java.lang.*;
class Trees{
    public static void main(String args[]){
        solve();
    }
    static class Node{
        Node right=null;
        Node left=null;
        int data=0;
        public Node(int data){
            this.data=data;
        }
    }
    static int idx=0;
    public static Node construct(int[] arr){
        if(idx==arr.length || arr[idx]==-1){
            idx++;
            return null;
        }
        Node self=new Node(arr[idx++]);
        self.left=construct(arr);
        self.right=construct(arr);
        return self;
    }
    public static void display(Node root){
        if(root==null){
            return;
        }
        display(root.left);
        System.out.print(root.data + " ");
        display(root.right);
    }
    public static int height(Node root){
        if(root==null){
            return -1;
        }
        int myh=0;
        int lh=height(root.left);
        int rh=height(root.right);
        myh=Math.max(lh,rh)+1;
        return myh;
    }
    public static int[] max_min_nodes(Node node){
        if(node==null){
            int[] baseAns={Integer.MIN_VALUE,Integer.MAX_VALUE,0};
            return baseAns;
        }
        int myans[]=new int[3];
        int lans[]=max_min_nodes(node.left);
        int rans[]=max_min_nodes(node.right);
        myans[0]=Math.max(lans[0],Math.max(rans[0],node.data));
        myans[1]=Math.min(lans[1],Math.min(rans[1],node.data));
        myans[2]=1+lans[2]+rans[2];
        return myans;
    }
    public static int nodes(Node root){
        if(root==null){
            return 0;
        }
        int ans=1;
        int lans=nodes(root.left);
        int rans=nodes(root.right);
        return ans+lans+rans;
    }
    static int r_l_self=-(int)(1e8);
    public static int max_Path_Sum_Leaf(Node root){
        if(root==null){
            return -(int)(1e8);
        }
        if(root.left==null && root.right==null){
            return root.data;
        }
        int lans=max_Path_Sum_Leaf(root.left);
        int rans=max_Path_Sum_Leaf(root.right);
        int self=lans+root.data+rans;
        r_l_self=Math.max(self,r_l_self);
        int ret_val=Math.max(lans,rans);
        return ret_val+root.data;
    }
    public static void width(Node root,int level,int[] minmax){
        if(root==null){
            return;
        }
        width(root.left,level-1,minmax);
        width(root.right,level+1,minmax);
        minmax[0]=Math.min(minmax[0],level);
        minmax[1]=Math.max(minmax[1],level);
    }
    public static void KLevelDown(Node root,int k,Node block,List<Integer> ans){
        if(root==null || root==block || k<0){
            return;
        }
        if(k==0){
            ans.add(root.data);
            return;
        }
        KLevelDown(root.left,k-1,block,ans);
        KLevelDown(root.right,k-1,block,ans);
    }
    public static boolean RTNP(Node root,int data,List<Integer> ans){
        if(root==null){
            return false;
        }
        if(root.data==data){
            ans.add(data);
            return true;
        }
        ans.add(root.data);
        boolean lans=RTNP(root.left,data,ans);
        if(lans){
            return true;
        }
        boolean rans=RTNP(root.right,data,ans);
        if(rans){
            return true;
        }
        ans.remove(ans.size()-1);
        return false;
    }
    public static List<Node> NTRP(Node root,int data){
        if(root==null){
            List<Node> baseAns=new ArrayList<>();
            return baseAns;
        }
        if(root.data==data){
            List<Node> baseAns=new ArrayList<>();
            baseAns.add(root);
            return baseAns;
        }
        List<Node> lans=NTRP(root.left,data);
        if(lans.size()>0){
            lans.add(root);
            return lans;
        }
        List<Node> rans=NTRP(root.right,data);
        if(rans.size()>0){
            rans.add(root);
            return rans;
        }
        List<Node> myans=new ArrayList<>();
        return myans;
    }
    public static Node LCA(Node root,int a,int b){
        List<Node> p1=NTRP(root,a);
        List<Node> p2=NTRP(root,b);
        int i=p1.size()-1,j=p2.size()-1;
        while(i>=0 && j>=0){
            if(p1.get(i)!=p2.get(j)){
                break;
            }
            i--;j--;
        }
        return p1.get(i+1);
    }
    static Node x=null;
    public static boolean LCA_eff(Node root,int a,int b){
        if(root==null){
            return false;
        }
        boolean selfAns=false;
        if(root.data==a || root.data==b){
            selfAns=true;
        }
        boolean lans=LCA_eff(root.left,a,b);
        if(x!=null){
            return true;
        }
        boolean rans=LCA_eff(root.right,a,b);
        if(x!=null){
            return true;
        }
        if((selfAns && lans) || (selfAns && rans) || (rans && lans)){
            x=root;
        }
        return (selfAns || lans || rans);
    }
    public static Node LCA_more_eff(Node root,int a,int b){
        if(root==null) return null;
        if(root.data==a || root.data==b){
            return root;
        }
        Node lans=LCA_more_eff(root.left,a,b);
        Node rans=LCA_more_eff(root.right,a,b);
        if(lans!=null && rans!=null) return root;
        return lans==null?rans:lans;

    }
    public static List<Integer> knodesFar(Node root,int k,int x){
        List<Node> path=NTRP(root,x);
        List<Integer> ans=new ArrayList<>();
        for(int i=0;i<=k && i<path.size();i++){
            Node block=null;
            if(i>0){
                block=path.get(i-1);
            }
            KLevelDown(path.get(i),k-i,block,ans);
        }
        return ans;
    }
    public static int knodes_far_Optimized(Node root,int k,int tar,List<Integer> ans){
        if(root==null){
            return -1;
        }
        if(root.data==tar){
            KLevelDown(root,k,null,ans);
            return 1;
        }
        int lans=knodes_far_Optimized(root.left,k,tar,ans);
        if(lans!=-1){
            KLevelDown(root,k-lans,root.left,ans);
            return lans+1;
        }
        int rans=knodes_far_Optimized(root.right,k,tar,ans);
        if(rans!=-1){
            KLevelDown(root,k-rans,root.right,ans);
            return rans+1;
        }
        return -1;//if not found 
    }
    public static class masterpair{
        int height=0;
        int size=0;
        boolean find=false;
        Node pred=null,succ=null,prev=null;
    }
    public static void allSolution(Node root,int level,int data,masterpair pair){
        if(root==null){
            return;
        }
        allSolution(root.left,level+1,data,pair);
        pair.height=Math.max(level,pair.height);
        pair.size++;
        pair.find=pair.find||root.data==data;

        if(root.data==data && pair.pred==null){
            pair.pred=pair.prev;
        }
        if(pair.prev!=null && pair.prev.data==data){
            pair.succ=root;
        }
        pair.prev=root;
        allSolution(root.right,level+1,data,pair);
    }
    public static Node right_most_helper(Node next,Node cn){
        while(next.right!=null && next.right!=cn){
            next=next.right;
        }
        return next;
    }
    public static void morisInT(Node root){
        Node cn=root;
        Node next=null;
        while(cn!=null){
            next=cn.left;
            if(next==null){
                System.out.print(cn.data+" ");
                cn=cn.right;
            }
            else{
                Node rightmost=right_most_helper(next,cn);
                if(rightmost.right==null){
                    rightmost.right=cn;
                    cn=cn.left;
                }
                else{
                    System.out.print(cn.data+" ");
                    rightmost.right=null;
                    cn=cn.right;
                }
            }

        }
    }
    public static void morrisPreT(Node root){
        Node cn=root;
        Node next;
        while(cn!=null){
            next=cn.left;
            if(next==null){
                System.out.print(cn.data+" ");
                cn=cn.right;
            }
            else{
                Node rightmost=right_most_helper(next,cn);
                if(rightmost.right==null){
                    rightmost.right=cn;
                    System.out.print(cn.data+" ");
                    cn=cn.left;
                }
                else{
                    // System.out.print(cn.right.data);
                    // System.out.print(cn.data);
                    rightmost.right=null;
                    cn=cn.right;
                }
            }
        }
    }
    public static class itpair{
        boolean self=false;
        boolean left=false;
        boolean right=false;
        Node node;
        public itpair(Node node,boolean self,boolean left,boolean right){
            this.node=node;
            this.self=self;
            this.left=left;
            this.right=right;
        }
    }
    public static void iterativeIN(Node root){
        Stack<itpair> st=new Stack<>();
        st.push(new itpair(root,false,false,false));
        while(st.size()>0){
            itpair cp=st.peek();
            Node cn=cp.node;
            if(cp.left==false){
                cp.left=true;
                if(cn.left!=null){
                    st.push(new itpair(cn.left,false,false,false));
                }
            }
            else if(cp.self==false){
                cp.self=true;
                System.out.print(cn.data+" ");
                // cp.right=true;
                // if(cn.right!=null){
                //     st.push(new itpair(cn.right,false,false,false));
                // }
            }
            else if(cp.right==false){
                cp.right=true;
                if(cn.right!=null){
                    st.push(new itpair(cn.right,false,false,false));
                }
            }
            else{
                st.pop();
            }
        }
    }
    public static void iterativePre(Node root){
        Stack<itpair> st=new Stack<>();
        st.push(new itpair(root,false,false,false));
        while(st.size()>0){
            itpair cp=st.peek();
            Node cn=cp.node;
            if(cp.self==false){
                cp.self=true;
                System.out.print(cn.data+" ");
            }
            else if(cp.left==false){
                cp.left=true;
                if(cn.left!=null){
                    st.push(new itpair(cn.left,false,false,false));
                }
            }
            else if(cp.right==false){
                cp.right=true;
                cp.right=true;
                if(cn.right!=null){
                    st.push(new itpair(cn.right,false,false,false));
                }
            }
            else{
                st.pop();
            }
        }
    }
    public static void iterativePost(Node root){
        Stack<itpair> st=new Stack<>();
        st.push(new itpair(root,false,false,false));
        while(st.size()>0){
            itpair cp=st.peek();
            Node cn=cp.node;
            if(cp.left==false){
                cp.left=true;
                if(cn.left!=null){
                    st.push(new itpair(cn.left,false,false,false));
                }
            }
            else if(cp.right==false){
                cp.right=true;
                if(cn.right!=null){
                st.push(new itpair(cn.right,false,false,false));
                }
            }
            else if(cp.self==false){
                cp.self=true;
                System.out.print(cn.data+" ");
            }
            else{
                st.pop();
            }
        }
    }
    public static Node pre_in_const1(int[] preorder,int ps,int pe,int[] inorder,int ins,int ine){
        if(ins>ine){
            return null;
        }
        int root_data=preorder[ps];
        int idx=-1;
        for(int i=ins;i<=ine;i++){
            if(inorder[i]==root_data){
                idx=i;
                break;
            }
        }
        int ils=ins;
        int irs=idx+1;
        int ile=idx-1;
        int ire=ine;
        int pls=ps+1;
        int ple=ile-ils+pls;
        int prs=ple+1;
        int pre=pe;
        Node root=new Node(root_data);
        root.left=pre_in_const1(preorder,pls,ple,inorder,ils,ile);
        root.right=pre_in_const1(preorder,prs,pre,inorder,irs,ire);
        return root;
    }
    public static Node in_pre_02(int[] inorder,int isi,int iei,int[]preorder,int psi,int pei ){
        if(psi>pei){
            return null;
        }
        int idx=isi;
        while(preorder[psi]!=inorder[idx]) idx++;
        int len=idx-isi;
        Node root=new Node(preorder[psi]);
        root.left=in_pre_02(inorder,isi,idx-1,preorder,psi+1,psi+len);
        root.right=in_pre_02(inorder,idx+1,iei,preorder,psi+len+1,pei);
        return root;
    }
    public static Node in_post(int[] inorder,int isi,int iei,int[] postorder,int psi,int pei){
        if(psi>pei){
            return null;
        }
        int idx=isi;
        while(inorder[idx]!=postorder[pei]) idx++;
        int len=idx-isi;
        Node root=new Node(postorder[pei]);
        root.left=in_post(inorder,isi,idx-1,postorder,psi,psi+len-1);
        root.right=in_post(inorder,idx+1,iei,postorder,psi+len,pei-1);
        return root;
    }
   public static Node pre_post(int[] preorder,int presi,int preei,int[] postorder,int postsi,int postei){
       if(presi>preei || postsi>postei) return null;
       Node root=new Node(preorder[presi]);
       if(presi==preei) return root;
       int idx=postsi;
       while(postorder[idx]!=preorder[presi+1]) idx++;
       int len=idx-postsi;
       root.left=pre_post(preorder,presi+1,presi+len+1,postorder,postsi,idx);
       root.right=pre_post(preorder,presi+len+2,preei,postorder,idx+1,preei-1);
       return root;
    }
    public static void solve(){
        // int[] arr={3 ,4, 5 ,-10 ,4 ,-15 ,5 ,6 ,-8 ,1 ,3 ,9 ,2 ,-3 ,-1 ,-1 ,-1 ,-1 ,-1 ,0 ,-1 ,-1 ,-1 ,-1 ,4 ,-1 ,-1 ,-1 ,10 ,-1};
        
        // int[] arr={10,20,40,-1,-1,50,80,-1,-1,90,-1,-1,30,60,-1,-1,70,100,-1,-1,110,-1,-1};
        int[] preorder={1,2,4,5,3,6,7};
        int[] inorder={4,2,5,1,6,3,7};
        int[] postorder={4,5,2,6,7,3,1};
        // Node root=construct(arr);
        // Node root=pre_in_const1(preorder,0,preorder.length-1,inorder,0,inorder.length-1);
        // Node root=in_pre_02(inorder,0,inorder.length-1,preorder,0,preorder.length-1);
        // Node root=in_post(inorder,0,inorder.length-1,postorder,0,postorder.length-1);
        Node root=pre_post(preorder,0,preorder.length-1,postorder,0,postorder.length-1);
        display(root);
        System.out.println();  
        // Node y=LCA_more_eff(root,3,100);
        // System.out.println(y.data);
        // iterativePre(root);
        // morisInT(root);
        // morrisPreT(root);
        // masterpair pair=new masterpair();
        // allSolution(root,0,60,pair);
        // System.out.println(pair.pred.data+ " "+ pair.succ.data);
        // int[] warr={Integer.MAX_VALUE,Integer.MIN_VALUE};
        // width(root,0,warr);
        // System.out.println(warr[1]-warr[0]);       
        // System.out.println(max_Path_Sum_Leaf(root));
        // System.out.println(nodes(root));
        // KLevelDown(root,3);
        // List<Integer> ans=new ArrayList<>();
        // RTNP(root,100,ans);
        // System.out.print(ans);
        // List<Integer> ans2=NTRP(root,100);
        // System.out.print(ans2);
        // System.out.println(LCA(root,80,90));
        LCA_eff(root,3,100);
        System.out.println(x.data);
        // List<Integer> ans=knodesFar(root,2,20);
        // List<Integer> ans=new ArrayList<>();
        // knodes_far_Optimized(root,2,20,ans);
        // System.out.print(ans+" ");
    }
}