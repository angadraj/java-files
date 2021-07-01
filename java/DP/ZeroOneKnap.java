import java.util.*;
import java.lang.*;

public class ZeroOneKnap {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] vals=new int[n];
        for(int i=0;i<n;i++){
            vals[i]=sc.nextInt();
        }
        int[] w=new int[n];
        for(int i=0;i<n;i++){
            w[i]=sc.nextInt();
        }
        int cap=sc.nextInt();
        System.out.print(kdp(vals,w,cap));
    }
    public static int kdp(int vals[],int w[],int cap){
        int dp[][]=new int[vals.length+1][cap+1];
        for(int i=1;i<dp.length;i++){
            for(int j=1;j<dp[0].length;j++){
                if(w[i-1]>j){
                    dp[i][j]=dp[i-1][j];
                }
                else{
                    dp[i][j]=Math.max(dp[i-1][j],vals[i-1]+dp[i-1][j-w[i-1]]);
                }
            }
        }
        print2D(dp);
        return dp[vals.length][cap];
    }
    public static int knap(int[] vals,int[] w,int cap,int n){
        //n will acts as index of item
        if(n==vals.length || cap==0){
            return 0;
        }
        if(w[n]>cap){
            return knap(vals,w,cap,n+1); //not included because of wt;
        }
        else{
            return Math.max(vals[n]+knap(vals,w,cap-w[n],n+1),
                    knap(vals,w,cap,n+1));
        }
    }
    public static void print2D(int[][] dp){
        for(int i=0;i<dp.length;i++){
            for(int j=0;j<dp[0].length;j++){
                System.out.print(dp[i][j]+" ");
            }
            System.out.println();
        }
    }
}