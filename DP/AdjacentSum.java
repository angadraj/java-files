import java.util.*;
import java.lang.*;
class AdjacentSum{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] arr=new int[n];
        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }
        boolean flag=false;
        // System.out.println(rec(arr,0,Integer.MIN_VALUE));
        System.out.println(dp(arr));
    }
    public static int dp(int[] arr){
        int inc=arr[0];
        int excl=0;
        for(int i=1;i<arr.length;i++){
            int ninc=excl+arr[i];
            int nexc=Math.max(inc,excl);

            inc=ninc;
            excl=nexc;
        }
        return inc;
    }
    // public static int rec(int[] arr,int idx,int prev){
    //     if(idx==arr.length){
    //         // System.out.print(ans+" ");
    //         return 0;
    //     }
    //     int mysum=0;
    //     if(prev+1!=idx){
    //        mysum+=rec(arr,idx+1,idx)+arr[idx];
    //     }
    //     int excl=rec(arr,idx+1,prev);
    //     return Math.max(mysum,excl);
    // }
}   