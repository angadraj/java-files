import java.util.*;
import java.lang.*;
class KParts{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int k=sc.nextInt();
        System.out.println(dp(n,k));
    }
    public static long dp(int n,int k){
        if(n==0){
            return 0;
        }
        long dp[][]=new long[n+1][k+1];
        for(int i=1;i<dp.length;i++){
            for(int j=1;j<dp[0].length;j++){
                if(j>i){
                    dp[i][j]=0;
                }
                else if(j==i){
                    dp[i][j]=1;
                }
                else if(j==1){
                    dp[i][j]=1;
                }
                else{
                    dp[i][j]=j*dp[i-1][j]+dp[i-1][j-1];
                }
            }
        }
        return dp[n][k];
    }
    // public static int rec(int n,int k){
    //     if(n==0 || k==0 || k>n){
    //         return 0;
    //     }
    //     if(n==k || k==1){
    //         return 1;
    //     }
    //     return k*rec(n-1,k) + rec(n-1,k-1);
    // }
}