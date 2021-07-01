import java.util.*;
import java.lang.*;
class MergeSort{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n1=sc.nextInt();
        int a[]=new int[n1];
        for(int i=0;i<n1;i++){
            a[i]=sc.nextInt();
        }
        int[] ans=mergesort(a);
        for(int i=0;i<ans.length;i++){
            System.out.print(ans[i]+" ");
        }
    }
    public static int[] mergesort(int[] a){
        if(a.length<=1){
            return a;
        }
        int mid=(a.length)/2;
        int[] lans=mergesort(Arrays.copyOfRange(a,0,mid));
        int[] rans=mergesort(Arrays.copyOfRange(a,mid,a.length));
        int[] baseAns=merge(lans,rans);
        return baseAns;
    }
    public static int[] merge(int[] lans,int[] rans){
        int[] myans=new int[lans.length+rans.length];
        int i=0,j=0,k=0;
        while(i<lans.length && j<rans.length){
            if(lans[i]<rans[j]){
                myans[k]=lans[i];
                i++;k++;
            }
            else{
                myans[k]=rans[j];
                j++;k++;
            }
        }
        for(;i<lans.length;i++){
            myans[k]=lans[i];
            k++;
        }
        for(;j<rans.length;j++){
            myans[k]=rans[j];
            k++;
        }
        return myans;
    }
   
}