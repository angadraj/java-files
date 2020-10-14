import java.util.*;
import java.lang.*;

public class LPSubstring {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        System.out.println(DP(str));
    }
    public static int DP(String str){
        int dp[][] =new int[str.length()][str.length()];
        int len=0;
        for(int gap=0;gap<dp[0].length;gap++){
            for(int i=0,j=gap;j<dp[0].length;i++,j++){
                if(gap==0){
                    dp[i][j]=1;
                }
                else if(gap==1){
                    if(str.charAt(i)==str.charAt(j)){
                        dp[i][j]=1;
                    }
                    else{
                        dp[i][j]=0;
                    }
                }
                else if(str.charAt(i)==str.charAt(j) && dp[i+1][j-1]==1){
                    dp[i][j]=1;
                }
                if(dp[i][j]==1){
                    len=gap+1;
                }
            }
        }
        return len;
    }
}