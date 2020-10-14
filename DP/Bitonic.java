import java.util.*;
import java.lang.*;

public class Bitonic {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] arr=new int[n];
        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }
        System.out.println(dp(arr));
    }
    public static int dp(int[] arr){
        int lis[]=new int[arr.length];
        lis[0]=1;
        for(int i=1;i<lis.length;i++){
            int max=0;
            for(int j=i-1;j>=0;j--){
                if(arr[j]<=arr[i] && lis[j]>max){
                    max=lis[j];
                }
            }
            lis[i]=max+1;
        }

        int lds[]=new int[arr.length];
        lds[lds.length-1]=1;
        for(int i=lds.length-2;i>=0;i--){
            int max=0;
            for(int j=i+1;j<lds.length;j++){
                if(arr[j]<=arr[i] && lds[j]>max){
                    max=lds[j];
                }
            }
            lds[i]=max+1;
        }

        int ans=0;
        for(int i=0;i<lds.length;i++){
            if(lds[i]+lis[i]>ans){
                ans=lds[i]+lis[i]-1;
            }
        }
        return ans;
    }
}