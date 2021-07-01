import java.util.*;
import java.lang.*;

public class First_Idx {
    public static void main(final String args[]) {
        final Scanner sc = new Scanner(System.in);
        final int n = sc.nextInt();
        final int[] arr = new int[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }
        int x = sc.nextInt();
        int ans = first_idx(arr, 0, x);
        System.out.print(ans + " ");
    }

    public static int first_idx(int[] arr,int idx,int x) {
        if(idx==arr.length){
            return -1;
        }

        if(arr[idx]==x){
            return idx;
        }
        int ans=first_idx(arr,idx+1,x);
        return ans;
        
        // if(idx==arr.length){
        //     return idx-1;
        // }

        // int ans=first_idx(arr, idx+1,x);
        // if(arr[idx]==x){
        //     ans=idx;
        // }
        // return ans;
    }
}