import java.util.*;
import java.lang.*;
class LCS{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        String s1=sc.nextLine();
        String s2=sc.nextLine();
        System.out.println(rec(s1,s2,0,0));
    }
    public static int rec(String s1,String s2,int idx1,int idx2){
        int ans=0;
        if(idx1==s1.length() || idx2==s2.length()){
            return 0;
        }
        if(s1.charAt(idx1)==s2.charAt(idx2)){
            ans+=rec(s1,s2,idx1+1,idx2+1)+1;
        }
        else{
            ans+=Math.max(rec(s1,s2,idx1+1,idx2),rec(s1,s2,idx1,idx2+1));
        }
        return ans;
    }
    // public static int dp(String s1,String s2){
    //     int[][] dp=new int[s1.length()+1][s2.length()+1];
    //     for(int i=1;i<dp.length;i++){
    //         for(int j=1;j<dp[0].length;j++){
    //             if(s1.charAt(i-1)==s2.charAt(j-1)){
    //                 dp[i][j]=1+dp[i-1][j-1];
    //             }
    //             else{
    //                 dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
    //             }
    //         }
    //     }
    //     return dp[dp.length-1][dp[0].length-1];
    //}
}
