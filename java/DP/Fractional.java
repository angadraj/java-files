import java.util.*;
import java.lang.*;

public class Fractional {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int vals[]=new int[n];
        for(int i=0;i<n;i++){
            vals[i]=sc.nextInt();
        }
        int w[]=new int[n];
        for(int i=0;i<n;i++){
            w[i]=sc.nextInt();
        }
        int cap=sc.nextInt();
        System.out.println(DP(vals,w,cap));
    }

    public static double DP(int[] vals,int[] w,int cap){
        Item[] ratio=new Item[vals.length];
        for(int i=0;i<ratio.length;i++){
            ratio[i]=new Item(vals[i],w[i],i);
        }
        Arrays.sort(ratio,new Comparator<Item>(){
            @Override
            public int compare(Item i1,Item i2){
                return i2.cost.compareTo(i1.cost);
            }
        });
        double ans=0d;
        for(int i=0;i<ratio.length;i++){
            int currw=(int)w[i];
            int curval=(int)vals[i];
            if(cap-w[i]>=0){
                cap=cap-currw;
                ans+=curval;
            }
            else{
                double fraction=((double)cap/(double)w[i]);
                ans+=curval*fraction;
                cap=(int)(cap-(currw*fraction));
                break;
            }
        }
        return ans;
    }
     static class Item{
        Double cost;
        double val,w,idx;
        public Item(int val,int w,int idx){
            this.val=val;
            this.w=w;
            this.idx=idx;
            cost=new Double(val/w);
        } 
    }
    
    // public static double rec(int[] vals,int[] w,double cap,int idx){
    //     if(cap==0 || idx==vals.length){
    //         return 0;
    //     }
    //     if(w[idx]>cap){
    //         double fraction=((double)cap/(double)w[idx]);
    //         double totalVal=vals[idx]*fraction;
    //         int newCap=(int)(cap-(w[idx]*fraction));
    //         return totalVal + rec(vals,w,newCap,idx+1);
    //     }
    //     return Math.max(vals[idx]+rec(vals,w,cap-w[idx],idx+1),
    //                 rec(vals,w,cap,idx+1));
    // }
   
}