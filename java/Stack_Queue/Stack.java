class Stack{
    private int tos;
    private int size;
    private int capacity;
    private int[] st;
    public Stack(){
        init(10);
    }
    public Stack(int n){
        init(n);
    }
    protected void init(int n){
        this.tos=-1;
        this.size=0;
        this.capacity=n;
        st=new int[capacity];
    }
    public void push(int val) throws Exception{
        if(this.size==this.capacity){
            throw new Exception("Stack Overflow");
        }
        push_pr(val);
    }   
    protected void push_pr(int val){
        this.st[++this.tos]=val;
        this.size++;
    }
    public int pop() throws Exception{
        if(this.size==0){
            throw new Exception("Stack underflow");
        }
        int rv=pop_pr();
        return rv;
    }
    protected int pop_pr(){
        int val=top_pr();
        this.st[this.tos]=0;
        this.tos--;
        this.size--;
        return val;
    }
    public int size(){
        return this.size;
    }
    public int maxsize(){
        return this.capacity;
    }
    public int top() throws Exception{
        if(this.size==0){
            throw new Exception("Stack is Empty");
        }
        return top_pr();
    }
    protected int top_pr(){
        return this.st[this.tos];
    }
    public boolean isEmpty(){
        return this.size==0;
    }
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("[");
        for(int i=this.tos;i>=0;i--){
            sb.append(this.st[i]);
            if(i!=0){
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}