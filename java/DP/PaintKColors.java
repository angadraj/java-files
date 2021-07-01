import java.util.*;
import java.lang.*;

class PaintKColors{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int k=sc.nextInt();
        int cost[][]=new int[k][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<k;j++){
                cost[j][i]=sc.nextInt();
            }
        }
        int[][] dp=new int[k][n];
        int min=Integer.MAX_VALUE;
        int smin=Integer.MAX_VALUE;
        for(int i=0;i<k;i++){
            dp[i][0]=cost[i][0];
            if(dp[i][0]<=min){
                smin=min;
                min=cost[i][0];
            }
            else if(dp[i][0]<smin){
                smin=cost[i][0];
            }
        }
        //from 2 column
        for(int j=1;j<n;j++){
            int nmin=Integer.MAX_VALUE;
            int nsmin=Integer.MAX_VALUE;
            for(int i=0;i<k;i++){
                if(min==dp[i][j-1]){
                    dp[i][j]=cost[i][j]+smin;
                }
                else{
                    dp[i][j]=cost[i][j]+min;
                }
                if(dp[i][j]<=nmin){
                    nsmin=nmin;
                    nmin=dp[i][j];
                }
                else if(dp[i][j]<nsmin){
                    nsmin=dp[i][j];
                }
            }
            min=nmin;
            smin=nsmin;
        }
        System.out.println(min);
    }
}