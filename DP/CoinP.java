import java.util.*;
import java.lang.*;

public class CoinP{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] coins=new int[n];
        for(int i=0;i<n;i++){
            coins[i]=sc.nextInt();
        }
        int tar=sc.nextInt();
        System.out.println(coinDP(coins,tar));
    }
    public static int coinDP(int[] coins,int tar){
        int dp[]=new int[tar+1];
        dp[0]=1;
        for(int i=1;i<dp.length;i++){
            for(int j=0;j<coins.length;j++){
                int coin=coins[j];
                if(i-coin>=0){
                    dp[i]+=dp[i-coin];
                }
            }
        }
        return dp[tar];
    }
    // public static int ans=0;
    // public static int coinp(int[] coins,int tar){
    //     int myways=0;

    //     if(tar==0){
    //         return 1;
    //     }
    //     for(int i=0;i<coins.length;i++){
    //         int coin=coins[i];
    //         if(tar-coin>=0){
    //             int ans=coinp(coins,tar-coin);
    //             myways+=ans;
    //         }
    //     }
    //     return myways;
    // }
}