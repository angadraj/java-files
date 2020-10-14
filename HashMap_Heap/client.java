public class client{
    public static void main(String args[]) throws Exception{
        int[] arr={10,0,4,5,1,-2,-4,100};
        priorityQueue pq=new priorityQueue(arr,true);
        int n=arr.length;
        for(int i=0;i<arr.length;i++){
          System.out.print(pq.poll()+" ");
        }
        for(int ele:arr) System.out.print(ele+" ");
    }
}