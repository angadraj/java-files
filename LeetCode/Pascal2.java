import java.util.*;
import java.lang.*;
class Pascal2{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int k=sc.nextInt();
        ArrayList<Integer> ans=dpfn(k);
        System.out.print(ans);
    }
    public static ArrayList<Integer> dpfn(int k){
        int[][] dp=new int[k+1][k+1];
        for(int i=0;i<dp.length;i++){
            for(int j=0;j<dp[0].length;j++){
                if(j==0 || i==j){
                    dp[i][j]=1;
                }
                else if(i>j){
                    dp[i][j]=dp[i-1][j-1]+dp[i-1][j];
                }
                else{
                    dp[i][j]=0;
                }
            }
        }
        ArrayList<Integer> ans=new ArrayList<>();
        int i=k;
        for(int j=0;j<=k;j++){
            ans.add(dp[i][j]);
        }
        return ans;
    }

    // public static ArrayList<Integer> mainfn(int k){
    //     ArrayList<Integer> baseAns=new ArrayList<>();
    //     for(int i=0;i<=k;i++){
    //         int sum=recfn(k,i);
    //         baseAns.add(sum);
    //     }
    //     return baseAns;
    // }
    // public static int recfn(int i,int j){
    //    if(i==0 && j==0){
    //        return 1;
    //    }
    //    if(i<0 || j<0){
    //        return 0;
    //    }
    //     int sum=0;
    //     sum+=recfn(i-1,j-1)+recfn(i-1,j);
    //    return sum;
    // }
}