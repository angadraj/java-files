import java.util.*;
import java.lang.*;
public class Dolls {
    static class pair implements Comparable<pair>{
        int l;
        int b;
        public pair(int w,int h){
            l=w;
            b=h;
        }
        public int compareTo(pair other){
            if(this.l!=other.l){
                return this.l-other.l;//increasing order of l;
                
            }
            else{
                return this.b-other.b;   // increasing order of b;
            }
        }
    }
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        pair[] mypair=new pair[n];
        for(int i=0;i<n;i++){
            pair cp=new pair();
            cp.l=sc.nextInt();
            cp.b=sc.nextInt();
            mypair[i]=cp;
        }
        Arrays.sort(mypair);
        int[] dp=new int[n];
        dp[0]=1;
        for(int i=0;i<n;i++){
            int max=0;
            for(int j=i-1;j>=0;j--){
                if(mypair[j].b<=mypair[i].b && max<dp[j]){
                    max=dp[j];
                }
            }
            dp[i]=max+1;
        }
        for(int i=0;i<n;i++){
            int ans=0;
            if(dp[i]>ans){
                ans=dp[i];
            }
        }
        System.out.println(ans);
    }
}
// https://www.geeksforgeeks.org/maximum-size-sub-matrix-with-all-1s-in-a-binary-matrix/
// https://leetcode.com/problems/russian-doll-envelopes/
// https://leetcode.com/problems/arithmetic-slices/
// https://leetcode.com/problems/longest-arithmetic-subsequence/
// https://leetcode.com/problems/longest-arithmetic-subsequence-of-given-difference/
