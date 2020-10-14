import java.util.*;
class PnC{
    public static void main(String args[]){
        solve();
    }
    //oo permutation
    public static int infinitePerm(int[] coins,int tar,int idx){
        if(tar==0) return 1;
        int count=0;
        for(int i=idx;i<coins.length;i++){
            if(tar-coins[i]>=0){
                count+=infinitePerm(coins,tar-coins[i],0);
            }
        }
        return count;
    }
    public static int infinitePermMemo(int[] coins,int tar,int idx,int[][] dp){
        if(idx==coins.length || tar==0){
            if(tar==0) return dp[idx][tar]=1;
            else return dp[idx][tar]=0;
        }
       int count=0;
       if(tar-coins[idx]>=0){
           count+=infinitePermMemo(coins,tar-coins[idx],0,dp);
       }
       count+=infinitePermMemo(coins,tar,idx+1,dp);
       return dp[idx][tar]=count;
    } 
    public static int infiCombo(int[] coins,int tar,int idx){
        if(tar==0) return 1;
        int count=0;
        for(int i=idx;i<coins.length;i++){
            if(tar-coins[i]>=0){
                count+=infiCombo(coins,tar-coins[i],i);
            }
        }
        return count;
    }
    public static int infiComboMemo(int[] coins,int tar,int idx,int[][] dp){
        if(idx==coins.length || tar==0) {
            return dp[idx][tar]=(tar==0)?1:0;
        }
        int count=0;
        if(tar-coins[idx]>=0){
            count+=infiComboMemo(coins,tar-coins[idx],idx,dp);
        }
        count+=infiComboMemo(coins,tar,idx+1,dp);
        return dp[idx][tar]=count;
    }
    public static int singleComboMemo(int[] coins,int tar,int idx,int[][] dp){
        if(idx==coins.length || tar==0) {
            return dp[idx][tar]=(tar==0)?1:0;
        }
        int count=0;
        if(tar-coins[idx]>=0){
            count+=singleComboMemo(coins,tar-coins[idx],idx+1,dp);
        }
        count+=singleComboMemo(coins,tar,idx+1,dp);
        return dp[idx][tar]=count;
    }
    //0/1 knapsack
    public static int knapsack_01(int[] vals,int[] wt,int cap,int idx){
        if(idx==vals.length || cap==0){
            return 0;
        }
        int count=0;
        if(cap-wt[idx]>=0){
            count=Math.max(count,knapsack_01(vals,wt,cap-wt[idx],idx+1))+vals[idx];
        }
        count=knapsack_01(vals,wt,cap,idx+1);
        return count;
    }
    public static int knapsack_02(int[] weight,int[] price,int n,int cap){
        if(n == 0 || cap == 0){
            return 0;
        }
        
        // if(dp[n][cap] != -1) return dp[n][cap];
        
        int maxVal = 0;
        if(cap - weight[n-1] >=0 ){
            maxVal = Math.max(maxVal, knapsack_02(weight,price,n-1,cap-weight[n-1])+  price[n-1]);
        }
        maxVal = Math.max(maxVal, knapsack_02(weight,price,n-1,cap)); 
    
        return maxVal;
    }
    public static void solve(){
        // int coins[]={2,3,5,7};
        // int target=10;
        // int[][] dp=new int[coins.length+1][target+1];
        // for(int[] d:dp) Arrays.fill(d,-1);
        // infinitePermMemo(coins,target,0,dp);
        // infiComboMemo(coins,target,0,dp);
        // singleComboMemo(coins,target,0,dp);
        int[] vals={60,100,120};
        int[] wt={10,20,30};
        int cap=50;
        int n=vals.length;  
        // System.out.println(knapsack_01(vals,wt,cap,0));
        System.out.println(knapsack_02(wt,vals,n,cap));
        // print2D(dp);
    }
    public static void print2D(int[][] dp){
        for(int[] d:dp) print1D(d);
        System.out.println();
    }
    public static void print1D(int[] dp){
        for(int ele:dp) System.out.print(ele+" ");
        System.out.println();
    }
}