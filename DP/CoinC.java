import java.util.*;
import java.lang.*;

class CoinC {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] coins = new int[n];
        for (int i = 0; i < coins.length; i++) {
            coins[i] = sc.nextInt();
        }
        int tar = sc.nextInt();
        System.out.print(coincDP(coins, tar));
    }
    public static int coincDP(int[] coins,int tar){
        int dp[]=new int[tar+1];
        dp[0]=1;
        for(int i=0;i<coins.length;i++){
            for(int j=1;j<dp.length;j++){
                if(j-coins[i]>=0){
                    dp[j]+=dp[j-coins[i]];
                }
            }
        }
        return dp[tar];
    }

    // public static int coinc(int coins[], int tar,int idx,String ans) {
    //     int myways=0;

    //     if(tar==0){
    //         System.out.print(ans+" ");
    //         return 1;
    //     }
    //     for(int i=idx;i<coins.length;i++){
    //         if(tar-coins[i]>=0){
    //             myways+=coinc(coins,tar-coins[i],i,ans+coins[i]);
    //         }
    //     }
    //     return myways;
    // }
}