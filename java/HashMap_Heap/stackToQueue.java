import java.util.Stack;
class stackToQueue{
    //implement qu using stack
    Stack<Integer> main=new Stack<>();;
    Stack<Integer> helper=new Stack<>();

    public int size(){
        return main.size();
    }
    public boolean isEmpty(){
        return main.size()==0;
    }
    public void add(int val){ //o(n)    
        if(main.size()>0){
            while(main.size()>0){
                helper.push(main.pop());
            }
            main.push(val);
            while(helper.size()>0){
                main.push(helper.pop())
            }
        }
        // main.push(val);
    }
    public int remove() throws Exception{
        //o(1)
        if(main.size()==0){
            throw new Exception("Null Pointer");
        }
        return remove_();
        //o(n)
        // while(main.size()>1){
        //     helper.push(main.pop());
        // }
        // int rv=main.pop();
        // while(helper.size()>0){
        //     main.push(helper.pop());
        // }
        // return rv;
    }
    private int remove_() { //o(1)
        int rv=main.pop();
        return rv;
    }
    public int peek() throws Exception{ //o(1)
        if(main.size()==0){
            throw new Exception("Null Pointer");
        }
        return peek_();
         //o(n)
        // while(main.size()>1){
        //     helper.push(main.pop());
        // }
        // int rv=main.peek();
        // while(helper.size()>0){
        //     main.push(helper.pop());
        // }
        // return rv;
    }
    private int peek_(){
        int rv=main.peek();
        return rv;
    }
}