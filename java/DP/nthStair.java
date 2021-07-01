import java.util.*;
import java.lang.*;

public class nthStair {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        System.out.print(no_of_ways(n));
    }
    public static int no_of_ways(int n){
        int dp[]=new int[n+1];
        dp[0]=1;
        for(int i=1;i<dp.length;i++){
            int jumps=0;
            if(i-1>=0){
                jumps+=dp[i-1];
            }
            if(i-2>=0){
                jumps+=dp[i-2];
            }
            if(i-3>=0){
                jumps+=dp[i-3];
            }
            dp[i]=jumps;
        }
        return dp[n];
    }
}