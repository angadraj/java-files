public class Dstack extends Stack{
    Dstack(){
        super();
    }
    Dstack(int n){
        super(n);
    }
    Dstack(int[] arr){
        super(arr.length);
        for(int ele:arr) super.push_pr(ele);
    }
    @Override 
    public void push(int val){
        if(super.size()==super.maxsize()){
            int[] temp=new int[super.size()];
            int i=super.size()-1;
            while(super.size()!=0){
                temp[i--]=super.pop_pr();
            }
            //temp has all vals;
            super.init(2*temp.length);
            //now copy all elements
            for(i=0;i<temp.length;i++){
                super.push_pr(temp[i]);
            }
        }
        super.push_pr(val);
    }
}