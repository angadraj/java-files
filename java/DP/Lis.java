import java.util.*;
import java.lang.*;

public class Lis {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] arr=new int[n];
        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }
        System.out.println(dp(arr));
    }
    public static int dp(int[] arr){
        int dp[]=new int[arr.length];
        dp[0]=1;
        for(int i=1;i<dp.length;i++){
            int max=0;
            for(int j=i-1;j>=0;j--){
                if(arr[j]<arr[i] && max<dp[j]){
                    max=dp[j];
                }
            }
            dp[i]=max+1;
        }
        int ans=Integer.MIN_VALUE;
        for(int i=0;i<dp.length;i++){
            if(dp[i]>ans){
                ans=dp[i];
            }
        }
        return ans;
    }
}