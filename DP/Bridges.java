import java.util.*;
import java.lang.*;
class Bridges{
        static class pair implements Comparable<pair>{
            int nb;
            int sb;
            public int compareTo(pair other){
                if(this.nb!=other.nb){
                    return this.nb-other.nb;
                }
                else{
                    return this.sb-other.nb;
                }
            }

        }
        public static void main(String args[]){
            Scanner sc=new Scanner(System.in);
            int n=sc.nextInt();
            pair[] mypair=new pair[n];
            for(int i=0;i<n;i++){
                pair cp=new pair();
                cp.nb=sc.nextInt();
                cp.sb=sc.nextInt();
                mypair[i]=cp;
            }
            Arrays.sort(mypair);
            //nb sorted now apply lis on sb
            int dp[]=new int[mypair.length];
            dp[0]=1;
            for(int i=1;i<mypair.length;i++){
                int max=0;
                for(int j=i-1;j>=0;j--){
                    if(mypair[j].sb<=mypair[i].sb && dp[j]>max){
                        max=dp[j];
                    }
                }
                dp[i]=max+1;
            }
            int max=0;
            for(int i=0;i<n;i++){
                if(max<dp[i]){
                    max=dp[i];
                }
            }
            System.out.println(max);
        }
}