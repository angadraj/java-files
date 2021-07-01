import java.util.*;
import java.lang.*;

public class TotalJumps {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] jumps=new int[n];
        for(int i=0;i<jumps.length;i++){
            jumps[i]=sc.nextInt();
        }
        System.out.println(total_ways(n,jumps));
    }
    public static int total_ways(int n,int[] jumps){
        int dp[]=new int[n];
        dp[n-1]=1;
        for(int i=jumps.length-2;i>=0;i--){
            if(jumps[i]!=0){
                for(int j=i+1;j<=jumps[i]+i && j<jumps.length;j++){
                    dp[i]+=dp[j];
                }
            }
        }
        return dp[0];
    }
}