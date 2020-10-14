import java.util.*;
import java.lang.*;
class All_Indices{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] arr=new int[n];
        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }
        //1 2 1 2 1 2 1
        //x=1
        //[0,2,4,6]=ans
        int x=sc.nextInt();
        int count=0;
        int ans[]=all_indices(arr,x,0,count);
        for(int i=0;i<ans.length;i++){
            System.out.print(ans[i]+" ");
        }
    }
    public static int[] all_indices(int[] arr,int x,int idx,int count){
        if(idx==arr.length){
            int recAns[]=new int[count];
            return recAns;
        }
        if(arr[idx]==x){
            count++;
        }
        int baseAns[]=all_indices(arr,x,idx+1,count);
        if(arr[idx]==x){
            baseAns[count-1]=idx;
        }
        return baseAns;
    }
}