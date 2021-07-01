import java.util.*;
import java.lang.*;

public class Last_Idx {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] arr=new int[n];
        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }
        int x=sc.nextInt();
        System.out.print(last_idx(arr,arr.length-1,x));
    }
    public static int last_idx(int[] arr,int idx,int x){
    //     if(idx<=0){
    //         return idx;
    //     }

    //     int ans=last_idx(arr,idx-1, x);
    //     if(arr[idx]==x){
    //         ans=idx;
    //         return ans;
    //     }
    //     return ans;
    // }
    
            if(idx<0){
                return -1;
            }   
            if(arr[idx]==x){
                return idx;
            }
            int ans=last_idx(arr,idx-1,x);
            return ans;
    }

}