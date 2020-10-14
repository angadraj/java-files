import java.util.*;
import java.lang.*;
class UnboundedK{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] vals=new int[n];
        for(int i=0;i<n;i++){
            vals[i]=sc.nextInt();
        }
        int w[]=new int[n];
        for(int i=0;i<n;i++){
            w[i]=sc.nextInt();
        }
        int cap=sc.nextInt();
        System.out.println(DP(vals,w,cap));
    }
    public static int DP(int[] vals,int[] w,int cap){
        int dp[][]=new int[vals.length+1][cap+1];
        dp[0][0]=0;
        for(int i=1;i<dp.length;i++){
            for(int j=1;j<dp[0].length;j++){
                if(w[i-1]>j){
                    dp[i][j]=dp[i-1][j];
                }
                else{
                    dp[i][j]=Math.max(dp[i-1][j],vals[i-1]+dp[i][j-w[i-1]]);
                }
            }
        }
        return dp[vals.length][cap];
    }
    public static int unkp(int[] vals,int[] w,int cap,int idx){
        if(cap==0 || idx==vals.length){
            return 0;
        }
        else if(w[idx]>cap){    
            return unkp(vals,w,cap,idx+1);
            // return 0;
        }
        return Math.max(vals[idx]+unkp(vals,w,cap-w[idx],idx),unkp(vals,w,cap,idx+1));
    }
}