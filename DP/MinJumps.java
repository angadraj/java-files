import java.util.*;
import java.lang.*;

public class MinJumps {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] jumps=new int[n];
        for(int i=0;i<n;i++){
            jumps[i]=sc.nextInt();
        }
        int ans=min_jumps(jumps,n);
        if(ans==Integer.MAX_VALUE){
            System.out.println("null");
        }
        else{
            System.out.println(ans);
        }
    }
    public static int min_jumps(int[] jumps,int n){
        int dp[]=new int[n];
        dp[n-1]=0;
        for(int i=jumps.length-2;i>=0;i--){
            int min=Integer.MAX_VALUE;
            if(jumps[i]==0){
                dp[i]=Integer.MAX_VALUE;
            }
            else{
                for(int j=i+1;j<jumps.length && j<=jumps[i]+i;j++){
                    if(min>dp[j]){
                        min=dp[j];
                    }
                }
            }
            if(min==Integer.MAX_VALUE){
                dp[i]=Integer.MAX_VALUE;
            }
            else{
                dp[i]=min+1;
            }
        }
        return dp[0];
    }
}