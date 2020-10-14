public class linkedlist{
    private class Node{
        Node next=null;
        int data=0;
        Node(int data){
            this.data=data;
        }
    }
    private Node head=null;
    private Node tail=null;
    private int size=0;
    
    public int size(){
        return this.size;
    }
    public boolean isEmpty(){
        return this.size==0;
    }
    public void display(){
        Node p=this.head;
        while(p!=null){
            System.out.print(p.data+" ");
            p=p.next;
        }
    }
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        Node p=this.head;
        sb.append("[");
        while(p!=null){
            sb.append(p.data);
            if(p.next!=null){
                sb.append(", ");
            }
            p=p.next;
        }
        sb.append("]");
        return sb.toString();
    }
    //get --------------------------------------------
    public int getFirst() throws Exception{
        if(this.size==0){
            throw new Exception("Empty List");
        }
        else{
            return this.head.data;
        }
    }
    public int getLast() throws Exception{
        if(this.size==0){
            throw new Exception("Empty List");
        }
        else{
            return this.tail.data;
        }
    }
    public int getAt(int idx) throws Exception{
        if(idx<0 || idx>=this.size){
            throw new Exception("index out of bounds");
        }
        else{
            return getAt_pr(idx).data;
        }
    }
    private Node getAt_pr(int idx){
        Node cn=this.head;
        for(int i=0;i<idx;i++){
            cn=cn.next; 
        }
        return cn;
    }
    //add functions-----------------------
    public void addFirst(int data){
        Node cn=new Node(data);
        addfirst_pr(cn);
    }
    private void addfirst_pr(Node cn){
        if(this.size==0){
            this.head=cn;
            this.tail=cn;
        }
        else{
            cn.next=this.head;
            this.head=cn;
        }
        this.size++;
    }
    public void addLast(int data){
        Node cn=new Node(data);
        addlast_pr(cn);
    }
    private void addlast_pr(Node cn){
        if(this.size==0){
            this.head=cn;
            this.tail=cn;
        }
        else{
            this.tail.next=cn;
            this.tail=cn;
        }
        this.size++;
    }
    public void addAt(int idx,int data) throws Exception{
        if(idx<0 || idx>size){
            throw new Exception("index out of bounds");
        }
        Node cn=new Node(data);
        addAt_pr(cn,idx);
    }
    private void addAt_pr(Node cn,int idx){
        if(idx==0){
            addfirst_pr(cn);
        }
        else if(idx==size){
            addlast_pr(cn);
        }
        else{
            Node p=getAt_pr(idx-1);
            cn.next=p.next;
            p.next=cn;
            this.size++;
        }
    }
    //remove------------------------------------------------------------
    public int removeFirst() throws Exception{
        if(this.size==0){
            throw new Exception("List is empty");
        }
        Node rn=removeFirst_pr();
        return rn.data;
    }
    private Node removeFirst_pr(){
        Node rn=this.head;
        if(size==1){
            this.head=null;
            this.tail=null;
        }
        else{
            this.head=this.head.next;
        }
        rn.next=null;
        this.size--;
        return rn;
    }
    public int removeLast() throws Exception{
        if(this.size==0){
            throw new Exception("List is Empty");
        }
        Node rn=removeLast_pr();
        return rn.data;
    }
    private Node removeLast_pr(){
        Node rn=this.tail;
        if(size==1){
            this.tail=null;
            this.head=null;
        }
        else{
            Node p=getAt_pr(this.size-2);
            this.tail=p;
            p.next=null;
        }
        rn.next=null;
        this.size--;
        return rn;
    }
    public int removeAt(int idx) throws Exception{
        if(idx<0 || idx>=this.size){
            throw new Exception("index out of bounds");
        }
        return removeAt_pr(idx).data;
    }   
    private Node removeAt_pr(int idx){
        if(idx==0){
            return removeFirst_pr();
        }
        else if(idx==this.size-1){
            return removeLast_pr();
        }
        else{
            Node prev=getAt_pr(idx-1);
            Node rn=prev.next;
            prev.next=rn.next;
            rn.next=null;
            this.size--;
            return rn   ;
        }
    }
}   