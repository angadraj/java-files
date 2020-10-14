import java.util.*;
import java.lang.*;

public class LISum {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int a[]=new int[n];
        for(int i=0;i<n;i++){
            a[i]=sc.nextInt();
        }
        System.out.println(lisum(a));
    }
    public static int lisum(int[] a){
        int dp[]=new int[a.length];
        // String ans="";
        // ans+=a[0];
        dp[0]=a[0];
        for(int i=1;i<dp.length;i++){
            int max=0;
            for(int j=i-1;j>=0;j--){
                if(a[j]<=a[i] && dp[j]>max){
                    max=dp[j];
                }
            }
            dp[i]=max+a[i];
            // ans+=a[i];
        }
        int sum=Integer.MIN_VALUE;
        for(int i=0;i<dp.length;i++){
            if(dp[i]>sum){
                sum=dp[i];
            }
        }
        // System.out.println(ans);
        return sum;
    }
}