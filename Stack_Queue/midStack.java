class midStack{
    static class Node{
        int val;
        Node prev;
        Node next;
        Node(int val){
            this.val=val;
        }
    }
    static class stack{
        Node head;
        Node mid;
        int count;
    }
    public static Stack createStack(){
        Stack st=new Stack();
        count=0;
        return st;
    }
    public static void push(Stack st,int val){
        Node nn=new Node(val);
        nn.next=null;
        nn.prev=head;
        st.count+=1;
        if(st.count==1){
            st.mid=nn;
        }
        else{
            st.head.next=nn;
            nn.prev=st.head;
            if(count%2==0){
                st.mid=st.mid.next;
            }
        }
        st.head=nn;
    }
    public static int pop(Stack st){
        if(st.count==0){
            return -1;
        }
        Node x=st.head;
        int rv=x.val;
        st.head=st.head.prev;
        if(st.head!=null){
            st.head.next=null;
        }
        x.prev=null;
        st.count--;
        if(st.count%2!=0){
            st.mid=st.mid.prev;
        }   
        return rv;
    }
    public static int getMid(Stack st){
        if(st.count==0) return -1;
        int rv=st.mid.val;
        return rv;
    }
    public static int deleteMid(Stack st){
        if(count==0) return -1;
        Node p=st.mid.prev;
        Node q=st.mid.next;
        int rv=mid.val;
        st.mid=next=prev=null;
        st.count-=1;
        p.next=q;
        q.prev=p;
        if(st.count%2==0){
            st.mid=q;
        }
        else{
            st.mid=p;
        }
        return rv;
    }
    public static void main(String args[]){
        Stack st=createStack();
    }
}