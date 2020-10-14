class stringSet{
    public static void main(String args[]){
        solve();
    }
    public static void print1D(int[] arr){
        for(int ele:arr) System.out.print(ele+" ");
    }
    public static void print2D(int[][] arr){
        for(int[] a:arr){
            print1D(a);
            System.out.println();
        }
    }
    //Longest Pallindromic Subsequence
    public static int Lpss(String str,int si,int ei,int[][] dp){
        if(si>=ei){
            return dp[si][ei]=(si==ei?1:0);
        }
        if(dp[si][ei]!=0) return dp[si][ei];
        if(str.charAt(si)==str.charAt(ei)){
            return dp[si][ei]=Lpss(str,si+1,ei-1,dp)+2;
        }
        else{
            return dp[si][ei]=Math.max(Lpss(str,si+1,ei,dp),Lpss(str,si,ei-1,dp));
        }
    }
    public static int Lpss_Dp(String str,int si,int ei,int[][] dp){
        for(int gap=0;gap<=dp.length;gap++){
            for(int i=0,j=gap;j<dp[0].length;j++,i++){
                if(i==j){
                    dp[i][j]=1;
                }
                else{
                    if(str.charAt(i)==str.charAt(j)){
                        dp[i][j]=dp[i+1][j-1]+2;
                    }
                    else{
                        dp[i][j]=Math.max(dp[i+1][j],dp[i][j-1]);
                    }
                }
            }
        }
        return dp[0][dp[0].length-1];
    }
    //generate the string from the dp

    public static void solve(){
        String str="adbcd";
        int n=str.length();
        int[][] dp=new int[n][n];
        System.out.println(Lpss_Dp(str,0,n-1,dp));
        // System.out.println("g e e k s f o r g e e k s");
        print2D(dp);
    }
}