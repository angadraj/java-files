class Client{
    public static void main(String args[]) throws Exception{
        // Dstack st=new Dstack();
        Dqueue qu=new Dqueue(5);
        for(int i=1;i<=5;i++) qu.push(i);
        System.out.print(qu.pop()+" ");
        System.out.print(qu.pop()+" ");
        System.out.print(qu.pop()+" ");
        // qu.push(100);
        // System.out.println("   "+qu.capacity());
        // System.out.println(qu.size());
        System.out.println(qu);
        qu.push(9);
        qu.push(10);
        System.out.println(qu);
        qu.pop();
        qu.pop();
        // System.out.println(qu);
        qu.display();
        
        // st.push(100);
        // int ans=st.pop();
        // System.out.println(ans);
        // System.out.println(qu.maxcapacity());

    }
}