class Dqueue extends Queue{
    Dqueue(){
        super();
    }
    Dqueue(int n){
        super(n);
    }
    @Override
    public void push(int val){
        if(super.size()==super.capacity()){
            int temp[]=new int[super.size()];
            int i=0;
            while(super.size()!=0){
                temp[i++]=super.pop_();
            }
            super.init(temp.length*2);
            for(int value:temp) super.push_(value);
        }
        super.push_(val);
    }
}