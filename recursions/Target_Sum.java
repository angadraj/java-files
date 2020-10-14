import java.util.*;
import java.lang.*;

public class Target_Sum {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] arr=new int[n];
        int sum_initial=0;
        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
            // sum_initial+=arr[i];
        }
        int k=sc.nextInt();
        // int k=sum_initial/2;
        int sum=0;
        Arrays.sort(arr);
        target_sum(arr,k,sum,0,"");
    }
    public static void target_sum(int[] arr,int k,int sum,int idx,String set){
       if(idx==arr.length||sum>k){
           if(sum==k){
               System.out.print(set+" ");
               return;
           }
           return;
       }
        
        target_sum(arr,k,sum+arr[idx],idx+1,set+arr[idx]+",");
        target_sum(arr,k,sum,idx+1,set);
    }
}