import java.util.*;
import java.lang.*;

public class Disp_Arr {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] arr=new int[n];
        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }
        disp(arr,0);
    }
    public static void disp(int[] arr,int idx){
            if(idx==arr.length){
                return ;
            }
        //   System.out.print(arr[idx]+" "); 
          disp(arr,idx+1); 
          System.out.print(arr[idx]+" ");  

    }
    
}