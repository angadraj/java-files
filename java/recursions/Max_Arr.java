import java.util.*;
import java.lang.*;

public class Max_Arr {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] arr=new int[n];
        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }
        System.out.print(max_arr(arr,arr.length-1));
    }
    public static int max_arr(int[] arr,int idx){
        if(idx<=0){
            return arr[idx];
        }
        int max=max_arr(arr,idx-1);
        if(max<arr[idx]){
            max=arr[idx];
        }
        return max;
    }
    
}