import java.util.*;
import java.lang.*;
 class Main{
     public static class Node{
         int data;
         Node next;
     }
     public static class LinkedList{
         Node head;
         Node tail;
         int size;

         public void addLast(int val){
            Node p=new Node();
            p.data=val; 
            if(size==0){
                head=tail=p;
             }
             else{
                 tail.next=p;
                 tail=p;
             }
             size++;
         }
         public void display(){
            for(Node p=head;p!=null;p=p.next){
                System.out.print(p.data+" ");
            }
            System.out.println();
         }
         public void removeFirst(){
             if(size==0){
                 System.out.println("List is empty");
                 return;
             }
             else if(size==1){
                 head=tail=null;
             }
             else{
                 Node p=head;
                 head=head.next;
                 p.next=null;
             }
             size--;
         }
         public int getFirst(){
            if(size==0){
                System.out.println("List is empty");
                return -1;
            }
            return head.data;
         }
         public int getLast(){
            if(size==0){
                System.out.println("List is empty");
                return -1;
            }
            return tail.data;
         }
         public int getAt(int idx){
            if(idx<0 || idx>=size){
                System.out.println("Invalid arguments");
                return -1;
            }
            Node p=head;
            for(int i=0;i<idx;i++){
                p=p.next;
            }
            return p.data;
         }
         public void addFirst(int val){
            Node p=new Node();
            p.data=val;
            if(size==0){
                head=tail=p;
            }
            else{
                p.next=head;
                head=p;
            }
            size++;
         }
         public void addAt(int idx,int val){
             if(idx<0||idx>size){
                 System.out.println("Invalid arguments");
                 return ;
             }
             else if(idx==0){
                 addFirst(val);
             }
             else if(idx==size){
                 addLast(val);
             }
             else{
                 Node p=new Node();
                 p.data=val;
                 Node q=head;
                 for(int i=0;i<idx-1;i++){
                     q=q.next;
                 }
                 p.next=q.next;
                 q.next=p;
                 size++;
             }
         }
         public void removeLast(){
            if(size==0){
                System.out.println("List is empty");
                return;
            }
            else if(size==1){
                head=tail=null;
            }
            else{
                Node p=head;
                for(int i=0;i<size-2;i++){
                    p=p.next;
                }
                tail=p;
                p.next=null;
            }
            size--;
         }
         public void removeAt(int idx){
            if(idx<0 ||idx>=size){
                System.out.println("Invalid arguments");
                return;
            }
            else if(idx==0){
                removeFirst();
            }
            else if(idx==size-1){
                removeLast();
            }
            else{
                Node p=head;
                for(int i=0;i<idx-1;i++){
                    p=p.next;
                }
                Node remove=p.next;
                p.next=remove.next;
                remove.next=null;
                size--;
            }
         }
         public void reverse(){
             Node p=head.next;
             Node q=head;
             Node r=null;
             tail=head;
             while(q.next!=null){
                 q.next=r;
                 r=q;
                 q=p;
                 p=p.next;
             }
             q.next=r;
             head=q;//not p because it is null right now
         }
         public int kthLast(int k){
            Node p=head;
            Node q=head;
            for(int i=0;i<k;i++){
                p=p.next;
            }
            while(p!=tail){
                p=p.next;
                q=q.next;
            }
            return q.data;
         }
         public int mid(){
             Node p=head;
             Node q=head;
             //even list: one before tail node
             //odd node : tail node
             while(p!=tail && p.next!=tail){
                 p=p.next.next;
                 q=q.next;
             }
             return q.data;
         }
         public static LinkedList mergeTwoSortedLists(LinkedList l1,LinkedList l2){
             LinkedList ans=new LinkedList();
             Node p=l1.head;
             Node q=l2.head;
             while(p!=null && q!=null){
                 if(p.data<q.data){
                     ans.addLast(p.data);
                     p=p.next;
                 }
                 else{
                     ans.addLast(q.data);
                     q=q.next;
                 }
             }
             for(;p!=null;p=p.next){
                 ans.addLast(p.data);
             }
             for(;q!=null;q=q.next){
                 ans.addLast(q.data);
             }
             return ans;
         }
         public static Node midNode(Node head,Node tail){
             Node p=head;
             Node q=head;
             while(p!=tail && p.next!=tail){
                 p=p.next.next;
                 q=q.next;
             }
             return q;
         }
         public static LinkedList mergeSort(Node head,Node tail){
            if(head==tail){
                LinkedList recAns=new LinkedList();
                recAns.addLast(head.data);
                return recAns;
            }
            Node mid=midNode(head,tail);
            Node midNext=mid.next;
            mid.next=null;

            LinkedList l1=mergeSort(head,mid);
            LinkedList l2=mergeSort(midNext,tail);

            LinkedList baseAns=mergeTwoSortedLists(l1, l2);
            mid.next=midNext;//say 8->9:: they broke up and at return time they will join
            return baseAns;
         }
         public void removeDups(){
             LinkedList ans=new LinkedList();
             Node p=this.head;
             while(this.size>0){
                int val=this.getFirst();
                this.removeFirst();
                if(ans.size==0 || ans.tail.data!=val){
                    ans.addLast(val);
                }
             }
             this.head=ans.head;
             this.tail=ans.tail;
             this.size=ans.size;
         }
         public void oddEven(){
            LinkedList odd=new LinkedList();
            LinkedList even=new LinkedList();
            Node p=this.head;
            while(this.size>0){
                int val=this.getFirst();
                this.removeFirst();
                if(val%2==0){
                    even.addLast(val);
                }
                else{
                    odd.addLast(val);
                }
            }
            if(even.head!=null && odd.head!=null){
                odd.tail.next=even.head;
                this.head=odd.head;
                this.tail=even.tail;
            }

            else if(even.head!=null){
                this.head=even.head;
                this.tail=even.tail;
            }
            else{
                this.head=odd.head;
                this.tail=odd.tail;
            }
            this.size=even.size+odd.size;
         }
         public void kReverse(int k){
             if(this.size<k){
                 return;
             }
             int lsize=this.size;
             LinkedList ans=new LinkedList();
             while(this.size>=k){
                 LinkedList p=new LinkedList();
                 while(p.size<k){
                     int val=this.getFirst();
                     this.removeFirst();
                     p.addFirst(val);
                 }
                 if(ans.head==null){
                     ans.head=p.head;
                     ans.tail=p.tail;
                 }
                 else{
                     ans.tail.next=p.head;
                     ans.tail=p.tail;
                 }
             }
             if(this.size!=0){
                 ans.tail.next=this.head;
                 ans.tail=this.tail;
             }
             this.head=ans.head;
             this.tail=ans.tail;
             this.size=lsize;
         }
         public boolean pallindrome(){
             Node mid=midNode(head, tail);//1->2->3->2->1
             LinkedList ans=new LinkedList();
             Node p=mid.next;
             ans.head=p;
             mid.next=null;
             ans.head=p;
             ans.tail=this.tail;
             this.tail=mid;
             ans.size=this.size/2;
             ans.reverse();
             Node q=ans.head;
             Node r=this.head;
             boolean isPallin=true;
             while(q!=null && r!=null){
                 if(q.data==r.data){
                     r=r.next;
                     q=q.next;
                 }
                 else{
                     isPallin=false;
                     break;
                 }
             }
             ans.reverse();
             mid.next=ans.head;
             this.tail=ans.tail;
             return isPallin;
         }
         public void fold(){
             //1->2->3->4->5------1->5->2->4->3
             Node mid=midNode(head, tail);
             LinkedList ans=new LinkedList();
             Node p=mid.next;
             mid.next=null;
             ans.head=p;
             ans.tail=this.tail;
             ans.size=this.size/2;
             this.tail=mid;
             ans.reverse();
             //1->2->3
             //5->4
             Node t1=this.head;
             Node t2=ans.head;
             while(t1!=null && t2!=null){       
                 Node x=t1.next;
                 Node y=t2.next;
                 t1.next=t2;
                 t2.next=x;
                 t1=x;
                 t2=y;
             }
             if(ans.size%2==0){
                 this.tail=ans.tail;
             }
         }
         public static int addHelper(Node h1,Node h2,int pv1,int pv2,LinkedList ans){
            if(h1==null && h2==null){
                return 0;
            }
            if(pv1==pv2){
                int carry=addHelper(h1.next,h2.next,pv1-1,pv2-1,ans);
                int num=h1.data+h2.data+carry;
                ans.addFirst(num%10);
                return num/10;
            }
            else if(pv1>pv2){
                int carry=addHelper(h1.next,h2,pv1-1,pv2,ans);
                int num=h1.data+carry;
                ans.addFirst(num%10);
                return num/10;
            }
            else{
                int carry=addHelper(h1,h2.next,pv1,pv2-1,ans);
                int num=h2.data+carry;
                ans.addFirst(num%10);
                return num/10;
            }
         }
         public static LinkedList add(LinkedList l1,LinkedList l2){
             LinkedList ans=new LinkedList();
             int carry=addHelper(l1.head,l2.head,l1.size,l2.size,ans);
             if(carry>0){
                ans.addFirst(carry);
             }
             return ans;
         }
         public static int intersection(LinkedList l1,LinkedList l2){
            Node p=l1.head;
            Node q=l2.head;
            if(l1.size>l2.size){
                int diff=l1.size-l2.size;
                for(int i=0;i<diff;i++){
                    p=p.next;
                }
            }
            else{
                int diff=l2.size-l1.size;
                for(int i=0;i<diff;i++){
                    q=q.next;
                }
            }
            while(p!=q){
                p=p.next;
                q=q.next;
            }
            return p.data;
         }
         public void recDR(Node p,int val){
            if(p==null){
                return;
            }
            recDR(p.next,val+1);
            if(val>=size/2){
                int temp=p.data;
                p.data=q.data;
                q.data=temp;
                q=q.next;
            }
         }
         Node q;
         public void reverseDR(){
              q=this.head;
              Node p=this.head;
              recDR(p,0);
         }
     }
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        LinkedList l1=new LinkedList();
        // LinkedList l2=new LinkedList();
        l1.addLast(1);
        l1.addLast(2);
        l1.addLast(3);
        l1.addLast(4);
        // l1.addLast(4);
        l1.addLast(5);
        l1.addLast(6);
        l1.display();
        l1.reverseDR();
        l1.display();
    }
 }

//  https://drive.google.com/drive/folders/1pt7VavRYZDcMmj_YrS9yuFtLSrjnbFbt?usp=sharing
