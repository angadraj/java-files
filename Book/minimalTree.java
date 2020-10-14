import java.util.*;
import java.lang.*;
class minimalTree{
    public static void main(String args[]){
        solve();
    }
    public static int minTree(int[] arr,int si,int ei){
        if(si>ei){
            return 0;
        }
        if(si==ei){
            return 1;
        }
        int myans=(int)(1e8);
        for(int cp=si;cp<=si;cp++){
            int leftans=minTree(arr,si,cp-1);
            int rightans=minTree(arr,cp+1,ei);
            myans=Math.min(myans,Math.min(rightans,leftans));
        }
        return myans+1;
    }
    public static void solve(){
        int arr[]={2,4,6};
        System.out.println(minTree(arr,0,arr.length-1));
        //minimal tree will be of equal nodes in left and right......
    }
}