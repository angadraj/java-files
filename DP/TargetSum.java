import java.util.*;
import java.lang.*;

public class TargetSum {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] arr=new int[n];
        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }
        int tar=sc.nextInt();
        System.out.print(targetsum(arr,tar));
    }
    public static boolean targetsum(int[] arr,int tar){
        boolean dp[][]=new boolean[arr.length+1][tar+1];
        dp[0][0]=true;
        for(int i=1;i<dp.length;i++){
            for(int j=0;j<dp[0].length;j++){
                //no call 
                dp[i][j]=dp[i-1][j];
                // yes call : check if the balance remained is solved or not
                if(j-arr[i-1]>=0 && dp[i-1][j-arr[i-1]]==true){
                    dp[i][j]=true;
                }
            }
        }
        return dp[arr.length][tar];
    }
}