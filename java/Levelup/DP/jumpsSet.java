import java.util.LinkedList;
class jumpsSet{
    public static void main(String args[]){
        solve();
    }
    public static void print1D(int[] arr){
        for(int ele:arr) System.out.print(ele+" ");
        System.out.println();
    }
    public static void print2D(int[][] arr){
        for(int[] ele:arr){
            print1D(ele);
        }
        System.out.println();
    }
    public static int jumps_01(int sr,int sc,int dr,int dc){
        if(sr<0 || sc<0 || sr>dr || sc>dc) return 0;
        if(sr==dr && sc==dc) return 1;
        int myans=0;
        int vans=jumps_01(sr+1,sc,dr,dc);
        int hans=jumps_01(sr,sc+1,dr,dc);
        myans=hans+vans;
        return myans;
    }
    public static int jumps_01_memo(int[][] dp,int sr,int sc,int dr,int dc){
        if(sr<0 || sc<0 || sr>dr || sc>dc) return 0;
        if(sr==dr && sc==dc) return dp[sr][sc]=1;
        if(dp[sr][sc]!=0) return dp[sr][sc];
        int myans=0;
        int vans=jumps_01(sr+1,sc,dr,dc);
        int hans=jumps_01(sr,sc+1,dr,dc);
        myans=hans+vans;
        return dp[sr][sc]=myans;
    }
    public static  int helper_03(int[] arr,int idx,int[] dp){
        if(idx==0) return dp[idx]=arr[idx];
        if(idx<0) return 0;
        if(idx<arr.length && dp[idx]!=0) return dp[idx]; 
        int myans=0;
        int lans=helper_03(arr,idx-1,dp);
        int rans=helper_03(arr,idx-2,dp);
        myans=Math.min(lans,rans);
        if(idx<arr.length){
            myans+=arr[idx];
        }
        return dp[idx]=myans;
    }
    static int[][] dirs={{0,1},{1,0},{1,1}};
    public static int mazePath_HVD(int[][] arr,int sr,int sc,int er,int ec,int[][] dp){
        if(sr==er && sc==ec) return dp[sr][sc]=1;
        if(dp[sr][sc]!=0) return dp[sr][sc];
        int count=0;
        for(int d[]:dirs){
            int dr=sr+d[0];
            int dc=sc+d[1];
            if(dr>=0 && dc>=0 && dr<arr.length && dc<arr[0].length){
                count+=mazePath_HVD(arr,dr,dc,er,ec,dp);
            }
        }
        return dp[sr][sc]=count;
    }
    public static int mazePathDP(int[][] arr){
       int[][] dp=new int[arr.length][arr[0].length];
       for(int r=dp.length-1;r>=0;r--){
           for(int c=dp[0].length-1;c>=0;c--){
               if(r==dp.length-1) dp[r][c]=1;
               else if(c==dp[0].length-1)dp[r][c]=1;
               else{
                   dp[r][c]=dp[r+1][c]+dp[r][c+1]+dp[r+1][c+1];
               }
           }
       }
       print2D(dp);
       return dp[0][0];
    }
    //h,v,d
    public static int mazeMJ(int[][] arr,int sr,int sc,int er,int ec,int[][] dp){ 
        if(sr==er && sc==ec){
            return dp[sr][sc]=1;
        }
        if(dp[sr][sc]!=0) return dp[sr][sc];
        int count=0;
        for(int jumps=1;jumps<=er;jumps++){
            if(sr+jumps<=er) count+=mazeMJ(arr,sr+jumps,sc,er,ec,dp);
        }
        for(int jumps=1;jumps<=ec;jumps++){
            if(sc+jumps<=ec) count+=mazeMJ(arr,sr,sc+jumps,er,ec,dp);
        }
        for(int jumps=1;jumps<=er && jumps<=ec;jumps++){
            if(sc+jumps<=ec && sr+jumps<=er) count+=mazeMJ(arr,sr+jumps,sc+jumps,er,ec,dp);
        }
        return dp[sr][sc]=count;
    }
    public static int mazeMJ_02(int sr,int sc,int er,int ec,int[][] dp){
        if(sr==er && sc==ec) return dp[sr][sc]=1;
        if(dp[sr][sc]!=0) return dp[sr][sc];
        int count=0;
        for(int[] d:dirs){
            for(int jumps=1;jumps<=Math.max(er,ec);jumps++){
                int dr=sr+jumps*d[0];
                int dc=sc+jumps*d[1];
                if(dr>=0 && dc>=0 && dr<=er && dc<=ec){
                    count+=mazeMJ_02(dr,dc,er,ec,dp);
                }
            }
        }
        return dp[sr][sc]=count;
    }
    public static int mazeMJDP(int er,int ec,int[][] dp){
        int count=0;
        for(int r=er;r>=0;r--){
            for(int c=ec;c>=0;c--){
                if(r==er && c==ec) dp[r][c]=1;
                else{
                    count=0;
                    for(int[] d:dirs){
                        for(int jumps=1;jumps<=Math.max(er,ec);jumps++){
                            int dr=r+jumps*d[0];
                            int dc=c+jumps*d[1];
                            if(dr>=0 && dc>=0 && dr<=er && dc<=ec){
                                count+=dp[dr][dc];
                            }
                        }
                    }
                    dp[r][c]=count;
                }
            }
        }
        System.out.println(count);
        return dp[0][0];
    }
    public static int boardPath_01(int si,int ei,int[] dp){
        if(si==ei) return dp[si]=1;
        if(dp[si]!=0) return dp[si];
        int count=0;
        for(int jumps=1;jumps<=6;jumps++){
            if(si+jumps<=ei) count+=boardPath_01(si+jumps,ei,dp);
        }
        return dp[si]=count;
    }
    public static int boardPath_02(int si,int ei,int dp[]){
        for(si=ei;si>=0;si--){
            if(si==ei) dp[si]=1;
            else{
                for(int jumps=1;jumps<=6;jumps++){
                    if(si+jumps<=ei) dp[si]+=dp[si+jumps];
                }
            }
        }
        return dp[0];
    }
    public static int boardPath_03(int si,int ei){
        LinkedList<Integer> list=new LinkedList<>();
        for( si=ei;si>=0;si--){
            if(si>=ei-1){
                list.addFirst(1);
                continue;
            }
            if(list.size()<=6){
                list.addFirst(list.getFirst()*2);
            }
            else{
                list.addFirst(list.getFirst()*2-list.removeLast());
            }
        }
        return list.getFirst();
    }
    public static int friendsPairing_01(int n){
        if(n<=2) return n;
        return friendsPairing_01(n-1) + (n-1)*friendsPairing_01(n-2);
    }
    public static int friendsPairing_02(int n,int[] dp){
        if(n<=2) return dp[n]=n;
        if(dp[n]!=0) return dp[n];
        int single=friendsPairing_02(n-1,dp);
        int pair=friendsPairing_02(n-2,dp)*(n-1);
        int total=single+pair;
        return dp[n]=total;
    }
    public static int friendsPairing_03(int n,int[] dp){
        dp[1]=1;
        dp[2]=2;
        for(int i=3;i<=n;i++){
            int single=dp[i-1];
            int pair=dp[i-2]*(i-1);
            dp[i]=single+pair;
        }
        return dp[n];
    }
    static int mod=(int)1e9+7;
    public static int friendsPairing_03_opti(int n){
        int a=1;
        int b=2;
        int c=0;
        for(int i=3;i<=n;i++){
            c=(b+(i-1)*a)%mod;
            a=b;
            b=c;
        }
        return b%mod;
    }
    public static int K_parts_01(int n,int k){
        if(n==k || k==1) return 1;
        int own_subset=K_parts_01(n-1,k-1);
        int come_along=K_parts_01(n-1,k)*k;
        return own_subset+come_along;
    }
    public static int K_parts_02(int n,int k,int[][] dp){
        if(n==k || k==1) return dp[n][k]=1;
        if(dp[n][k]!=0) return dp[n][k];
        int own_subset=K_parts_02(n-1,k-1,dp);
        int come_along=K_parts_02(n-1,k,dp)*k;
        return dp[n][k]=own_subset+come_along;
    }
    public static int K_parts_03(int N,int K,int[][] dp){
        if(N<K) return 0;
        for(int n=1;n<dp.length;n++){
            for(int k=1;k<dp[0].length;k++){
                if(n==k || k==1){
                    dp[n][k]=1;
                    continue;
                }
                int own_subset=dp[n-1][k-1];
                int come_along=dp[n-1][k]*k;
                dp[n][k]=own_subset+come_along;
            }
        }
        return dp[N][K];
    }
    public static void solve(){
        int n=3,k=2;
        int[][] dp=new int[n+1][k+1];
        // System.out.println(friendsPairing_03(5,dp));
        // System.out.println(friendsPairing_03_opti(200));
        System.out.println(K_parts_03(n,k,dp));
        // System.out.println(boardPath_03(0,10));
        // print1D(dp);
        // System.out.println(mazeMJ(new int[3][3],0,0,2,2,dp));
        // System.out.println(mazeMJ_02(0,0,4,4,dp));
        // System.out.println(mazeMJDP(4,4,dp));
        // System.out.println(mazePath_HVD(new int[5][5],0,0,4,4,dp));
        // System.out.println(mazePathDP(new int[5][5]));
        print2D(dp);
        // System.out.println(jumps_01(0,0,2,2));
        // int[][] dp=new  int[3][3];
        // System.out.println(jumps_01_memo(dp,0,0,2,2));
        // int[] cost={10,15,20};
        // int[] dp=new int[cost.length+1];
        // System.out.println(helper_03(cost,cost.length,dp));
        // display(dp);
    }
}