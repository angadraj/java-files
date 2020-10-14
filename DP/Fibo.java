import java.util.*;
import java.lang.*;
class Fibo{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        System.out.print(fibodp(n));
    }
    public static int fibo(int n){
        if(n==0||n==1){
          return n;  
        }
        return fibo(n-1)+fibo(n-2);
    }
    public static int fibomem(int n){
        int[] dp=new int[n+1];
        if(n==0||n==1){
            return n;
        }
        else if(dp[n]!=0){
            return dp[n];
        }
        int ans=fibomem(n-2)+fibomem(n-1);
        dp[n]=ans;
        return ans;
    }
    public static int fibodp(int n){
        int dp[]=new int[n+1];
        dp[0]=0;dp[1]=1;
        for(int i=2;i<dp.length;i++){
            dp[i]=dp[i-1]+dp[i-2];
        }
        return dp[n];
    }
}