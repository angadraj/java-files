import java.util.*;
import java.lang.*;

public class CPSubseq {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        System.out.println(rec(str,0,str.length()-1));
    }
    public static int rec(String str,int idx1,int idx2){
        if(idx1==idx2){
            return 1;
        }
        if(idx1>idx2){
            return 0;
        }
        int ans=0;
        if(str.charAt(idx1)==str.charAt(idx2)){
            ans+=rec(str,idx1+1,idx2)+rec(str,idx1,idx2-1)+1;
            //aa=>a,a,aa
        }
        else{
            ans+=rec(str,idx1+1,idx2)+rec(str,idx1,idx2-1)-rec(str,idx1+1,idx2-1);
        }
        return ans;
    }
    // public static int DP(String str){
    //     int[][] dp=new int[str.length()][str.length()];
    //     // int count=0;
    //     for(int gap=0;gap<dp[0].length;gap++){
    //         for(int i=0,j=gap;j<dp[0].length;i++,j++){
    //             if(gap==0){
    //                 dp[i][j]=1;
    //             }
    //             else if(str.charAt(i)==str.charAt(j)){ 
    //                 dp[i][j]=dp[i+1][j]+dp[i][j-1]+1;
    //             }
    //             else{
    //                 dp[i][j]=dp[i+1][j]+dp[i][j-1]-dp[i+1][j-1];
    //             }
    //         }
    //     }
    //     return dp[0][dp[0].length-1];
    //}
}