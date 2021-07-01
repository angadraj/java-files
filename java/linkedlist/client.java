class client{
    public static void main(String args[]) throws Exception{
        linkedlist l1=new linkedlist();
        for(int i=0;i<=9;i++){
            l1.addLast(i);
        }
    }
    public static Node odd_even_val(Node head){
        Node odd=new Node(-1);
        Node even=new Node(-1);
        Node c=head;
        Node o=odd;
        Node e=even;
        while(c.next!=null){
            if((c.val & 1)==0){
                e.next=c;
                e=e.next;
            }
            else{
                o.next=c;
                o=o.next;
            }
            c=c.next;
        }
        o=null;e=null;
        e.next=odd.next;
        return even.next;
    }   
    public static Node mid(Node head){
        Node fast=head;
        Node slow=head;
        while(fast.next!=null && fast.next.next!=null){
            fast=fast.next.next;
            slow=slow.next;
        }
        return slow;
    }
    public static Node reverse(Node head){
        
    }
    //Leetcode 146
class LRUCache {
    private class Node{
        int key = 0;
        int value = 0;

        Node prev = null;
        Node next = null;

        Node(int key,int value){
            this.key = key;
            this.value = value;
        }
    }

    private Node head = null;
    private Node tail = null;
    private int maxSize = 0;
    private int size = 0;

    
    private HashMap<Integer,Node> map = new HashMap<>();

    public LRUCache(int capacity) {
        this.maxSize = capacity;
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) return -1;

        Node node = map.get(key);
        removeNode(node);
        addLast(node); 
        return node.value;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            int val = get(key);
            if(val != value)
               this.tail.value = value;
        }else{
            Node node = new Node(key,value);
            map.put(key,node);
            addLast(node);

            if(this.size > this.maxSize) {
                Node rnode = this.head; 
                removeNode(rnode);
                map.remove(rnode.key);
            }
        }
    }
}

}