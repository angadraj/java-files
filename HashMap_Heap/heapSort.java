public class heapSort{
    public static void main(String args[]){
        int[] arr={ 10, 20, 30, -2, -3, -4, 5, 6, 7, 8, 9, 22, 11, 13 };
        int n=arr.length-1;
        boolean ismax=true;
        for(int i=n;i>=0;i--){
            downheapify(arr,i,ismax,n);
        }
        for(int i=0;i<=n;i++){
            swap(arr,0,n-i);
            downheapify(arr,0,ismax,n-i-1);
        }
        for(int ele:arr) System.out.print(ele+" ");
    }
    public static void swap(int[] arr,int i,int j){
        int temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }
    public static int compareTo(int[] arr,boolean check,int i,int j){
        if(check) return arr[i]-arr[j];
        else return arr[j]-arr[i];
    }
    public static void downheapify(int[] arr,int pi,boolean check,int range){
        int maxidx=pi;
        int lci=2*maxidx+1;
        int rci=2*maxidx+2;
        if(lci<=range && compareTo(arr,check,lci,maxidx)>0) maxidx=lci;
        if(rci<=range && compareTo(arr,check,rci,maxidx)>0) maxidx=rci;
        if(maxidx!=pi){
            swap(arr,maxidx,pi);
            downheapify(arr,maxidx,check,range);
        }
    }
}