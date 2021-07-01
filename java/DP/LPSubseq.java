import java.util.*;

import jdk.nashorn.internal.ir.ReturnNode;

import java.lang.*;

public class LPSubseq {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        String s1=sc.nextLine();
        System.out.println(rec(s1,0,s1.length()-1));
    }
    public static int rec(String s1,int idx1,int idx2){
        if(idx1==idx2){
            return 1;
        }
        if(idx1>idx2){
            return 0;
        }
        int ans=0;
        if(s1.charAt(idx1)==s1.charAt(idx2)){
            ans+=rec(s1,idx1+1,idx2-1)+2;
        }
        else{
            ans+=Math.max(rec(s1,idx1+1,idx2),rec(s1,idx1,idx2-1));
        }
        return ans;
    }
    // public static int DP(String s1){
    //     int[][] dp=new int[s1.length()][s1.length()];
    //     for(int gap=0;gap<dp[0].length;gap++){
    //         for(int i=0,j=gap;j<dp[0].length;i++,j++){
    //             if(gap==0){
    //                 dp[i][j]=1;
    //             }
    //             else if(gap==1){
    //                 dp[i][j]=1;
    //                 if(s1.charAt(i)==s1.charAt(j)){
    //                     dp[i][j]=2;
    //                 }
    //             }
    //             else if(s1.charAt(i)==s1.charAt(j)){
    //                 dp[i][j]=2+dp[i+1][j-1];
    //             }
    //             else{
    //                 dp[i][j]=Math.max(dp[i+1][j],dp[i][j-1]);
    //             }
    //         }
    //     }
    //     return dp[0][dp[0].length-1];
    // }
}