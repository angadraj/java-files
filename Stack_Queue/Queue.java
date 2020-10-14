class Queue{
    private int[] arr;
    private int head;
    private int tail;
    private int size;
    private int maxsize;
    public Queue(){
        init(10);
    }
    public Queue(int n){
        init(n);
    }
    protected void init(int n){
        this.arr=new int[n];
        this.head=0;
        this.tail=0;
        this.size=0;
        this.maxsize=n;
    }
    public int size(){
        return this.size;
    }
    public boolean isEmpty(){
        return this.size==0;
    }
    public int capacity(){
        return this.maxsize;
    }
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("[");
        for(int i=0;i<this.size;i++){
            int idx=(this.head+i)%this.maxsize;
            sb.append(this.arr[idx]);   
            if(i!=this.size-1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
    public void push(int val) throws Exception{
        if(this.size==this.maxsize){
            throw new Exception("Overflow");
        }
        push_(val);
    }
    public void display(){
        for(int i=0;i<this.size;i++){
            int idx=(this.head+i)%this.maxsize;
            System.out.print(this.arr[idx]+" ");
        }
    }
    protected void push_(int val){
        this.arr[this.tail]=val;
        this.tail=(this.tail+1)%this.maxsize;
        this.size++;
    }
    public int pop() throws Exception{
        if(this.size==0) throw new Exception("underflow");
        return pop_();
    }
    protected int pop_(){
        int val=this.arr[this.head];
        this.arr[this.head]=0;
        this.head=(this.head+1)%this.maxsize;
        this.size--;
        return val;
    }
    public int peek() throws Exception{
        if(this.size==0) throw new Exception("underflow");
        return peek_();

    }
    protected int peek_(){
        return this.arr[this.head];
    }
}