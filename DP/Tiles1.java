import java.util.*;
import java.lang.*;
class Tiles1{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] dp=new int[n];
        dp[0]=1;
        dp[1]=2;
        for(int i=2;i<dp.length;i++){
            dp[i]=dp[i-1]+dp[i-2];
        }
        System.out.println(dp[dp.length-1]);
    }
}