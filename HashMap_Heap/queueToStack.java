import java.util.Queue;
class queueToStack{
    //use qu to make stack
    Queue<Integer> main=new LinkedList<>();
    Queue<Integer> helper=new LinkedList<>();

    public int size(){
        return main.size();
    }
    public boolean isEmpty(){
        return main.size()==0;
    }
    public void push(int val){ 
        //o(n)
        if(main.size()>0){
            while(main.size()>0){
                helper.add(main.remove());
            }
            main.add(val);
            while(helper.size()>0){
                main.add(helper.remove());
            }
        }
        //o(1)
        // main.add(val);
    }
    public int pop() throws Exception{ //  o(1)
        if(main.size()==0){
            throw new Exception("Null Pointer");
        }
        return pop_();
        //o(n)
        // while(main.size()>1){
        //     helper.add(main.remove());
        // }
        // int rv=main.remove();
        // while(helper.size()>0){
        //     main.add(helper.remove());
        // }
        //return rv;
    }
    private int pop_(){
        return main.remove();
    }
    public int peek() throws Exception{
        if(main.size()==0){
            throw new Exception("Null pointer");
        }
        return peek_();
        //o(n)
        // while(main.size()>1){
        //     helper.add(main.remove());
        // }
        // int rv=main.peek();
        // while(helper.size()>0){
        //     main.add(helper.remove());
        // }
        //return rv;
    }
    private peek_(){
        return main.peek();
    }
}