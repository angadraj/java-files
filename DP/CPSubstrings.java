import java.util.*;
import java.lang.*;
class CPSubstrings{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        // System.out.println(rec(str,0,str.length()-1));
        System.out.println(DP(str));
    }
    public static int rec(String str,int idx1,int idx2){
        int count=0;
        if(idx1==idx2){
            return 1;
        }
        if(idx1>idx2){
            return 0;
        }
        if(str.charAt(idx1)==str.charAt(idx2) && str.length()==2){
            return 1;
        }
        if(str.charAt(idx1)!=str.charAt(idx2)){
            count+=rec(str,idx1+1,idx2)+rec(str,idx1,idx2-1);
        }
        if(str.charAt(idx1)==str.charAt(idx2)){
            count+=rec(str,idx1+1,idx2-1);
        }
        return count;
    }
    public static int DP(String str){
        int dp[][]=new int[str.length()][str.length()];
        int count=0;
        for(int k=0;k<dp[0].length;k++){
            for(int i=0,j=k;j<dp[0].length;i++,j++){
                if(k==0){
                    dp[i][j]=1;
                }
                else if(k==1){
                    if(str.charAt(i)==str.charAt(j)){
                        dp[i][j]=1;
                    }
                }
                else{
                    if(str.charAt(i)==str.charAt(j) && dp[i+1][j-1]==1){
                        dp[i][j]=1;
                    }
                }
                if(dp[i][j]==1){
                    count++;
                }
            }
        }
        return count;
    }
}