import java.util.*;
import java.lang.*;
class Basic{
    public static void main(String args[]){
        // int[][] dirs={{-1,0},{0,1},{1,0},{0,-1}};
        // String[] d={"t","r","b","l"};
        // int dr=1;
        // int dc=1;
        // boolean[][] vis=new boolean[dr+1][dc+1];
        // int ans=mazepath(0,0,dr,dc," ",vis,d,dirs);
        // System.out.println(ans);
        // HashSet<ArrayList<Integer>> hs=new HashSet<>();
        // ArrayList<Integer> a1=new ArrayList<>();
        // ArrayList<Integer> a2=new ArrayList<>();
        // ArrayList<Integer> a3=new ArrayList<>();
        // a1.add(1);a1.add(2);a1.add(3);
        // a2.add(2);a2.add(1);a2.add(3);
        // a3.add(3);a3.add(2);a3.add(1);
        // while(true){
        //     if(hs.contains())
        // }
        // solve();
    }
    public static int mazepath(int sr,int sc,int dr,int dc,String ans,boolean[][] vis,String[] d,int[][] dirs){
        if(sr==dr && sc==dc){
            System.out.println(ans+" ");
            return 1;
        }
        int count=0;
        vis[sr][sc]=true;
        for(int i=0;i<4;i++){
            int r=sr+dirs[i][0];
            int c=sc+dirs[i][1];
            if(r>=0 && c>=0 && r<=dr && c<=dc && vis[r][c]==false){
                count+=mazepath(r,c,dr,dc,ans+d[i],vis,d,dirs);
            }
        }
        vis[sr][sc]=false;
        return count;
    }
    public static void solve(){
        String str="abaa";
        System.out.println(permBits(str,0,"",new boolean[str.length()]));
    }
    public static int permBits(String str,int idx,String ans,boolean vis[]){
        if(idx==str.length()){
            System.out.println(ans);
            return 1;
        }
        // int bits=0;
        int count=0; 
        for(int i=0;i<str.length();i++){
            char ch=str.charAt(i);
            // int cidx=ch-'a'+1;
            // int mask=1<<cidx;
            if(vis[i]==false){
                // (bits)^=mask;
                vis[i]=true;
                count+=permBits(str,idx+1,ans+ch,vis);
                // (bits)^=mask;
                vis[i]=false;
            }
        }
        return count;
    }
}
// https://github.com/rajneeshkumar146/pepcoding-Batches/tree/master/2019
// https://docs.google.com/spreadsheets/d/15Gx6u4DdR704ercqqfDszEirqoHhR6DUrDLLy1lSKsY/edit#gid=2125597933
// https://www.geeksforgeeks.org/find-maximum-path-sum-two-leaves-binary-tree/
// https://leetcode.com/problems/binary-tree-maximum-path-sum/
