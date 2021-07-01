import java.util.ArrayList;
public class priorityQueue{
    private ArrayList<Integer> pq;
    private boolean maxHeap=true;

    public priorityQueue(int[] arr,boolean ismax){
        init(ismax);
        for(int ele:arr){
            pq.add(ele);
            constructHeap();
        }
    }
    public priorityQueue(){
        init(true); //default inc heap;
    }
    private void init(boolean ismax){
        pq=new ArrayList<>();
        maxHeap=ismax;
    }
    private void constructHeap(){
        for(int i=pq.size()-1;i>=0;i--){
            downHeapify(i);
        }
    }
    private int compareTo(int i,int j){
        if(maxHeap) return pq.get(i)-pq.get(j);
        else return pq.get(j)-pq.get(i);
        //max heap :inc order,else dec order
    }
    private void swap(int i,int j){
        int v1=pq.get(i);
        int v2=pq.get(j);
        pq.set(i,v2);
        pq.set(j,v1);
    }
    private void downHeapify(int pi){
        int maxidx=pi;
        int lci=(2*pi)+1;
        int rci=(2*pi)+2;
        if(lci<pq.size() && compareTo(lci,maxidx)>0) maxidx=lci;
        if(rci<pq.size() && compareTo(rci,maxidx)>0) maxidx=rci;
        if(maxidx!=pi){
            swap(maxidx,pi);
            downHeapify(maxidx);
        }
    }
    private void upHeapify(int ci){
        int pi=(ci-1)/2;
        if(pi>=0 && compareTo(ci,pi)>0){
            //till my child is bigger
            swap(ci,pi);
            upHeapify(pi);
        }
    }
    //Basic functions
    public int size(){
        return pq.size();
    }
    public boolean isEmpty(){
        return pq.size()==0;
    }
    public void add(int val){
        pq.add(val);
        upHeapify(pq.size()-1); //from last index
    }
    public int peek() throws Exception{
        if(pq.size()==0){
            throw new Exception("Null Pointer");
        }
        return pq.get(0);
    }
    public int poll() throws Exception{
        if(pq.size()==0){
            throw new Exception("Empty queue");
        }
        int rv=pq.get(0);
        swap(0,pq.size()-1);
        pq.remove(pq.size()-1); //best from back side
        downHeapify(0);
        return rv;
    }
}