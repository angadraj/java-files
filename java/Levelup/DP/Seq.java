import java.util.ArrayDeque;
class Seq{
    public static void main(String args[]){
        solve();
    }
    public static int Lis_rec_helper(int[] arr,int idx,int[] dp){
        if(idx==0){
            return dp[idx]=1;
        }
        int maxlen=1;
        if(dp[idx]!=0) return dp[idx];
        for(int j=idx-1;j>=0;j--){
            if(arr[j]<arr[idx]){
                maxlen=Math.max(maxlen,Lis_rec_helper(arr,j,dp)+1);
                //maxlen=Math.max(maxlen,dp[j]+1);
            }
        }
        return dp[idx]=maxlen;
    }
    public static int Lis_rec(int[] arr,int[] dp){
        int len=0;
        for(int i=0;i<arr.length;i++){
            len=Math.max(len,Lis_rec_helper(arr,i,dp));
        }
        display(dp);
        return len;
    }
    //left to right
    public static int Lis_DP_01(int[] arr,int[] dp){
        int len=0;
        for(int i=0;i<arr.length;i++){
            dp[i]=1;
            for(int j=i-1;j>=0;j--){
                if(arr[j]<arr[i]){
                    dp[i]=Math.max(dp[i],dp[j]+1);
                }
            }
            len=Math.max(len,dp[i]);
        }
        return len;
    }
    //right tO LEFT
    public static int Lis_DP_02(int[] arr,int[] dp){
        int len=0,n=arr.length;
        for(int i=n-1;i>=0;i--){
            dp[i]=1;
            for(int j=i-1;j>=0;j--){
                if(arr[j]>arr[i]){
                    dp[i]=Math.max(dp[i],dp[j]+1);
                }
            }
            len=Math.max(len,dp[i]);
        }
        return len;
    }
    //right to left
    public static int Lds_DP_01(int[] arr,int[] dp){
        int len=0,n=arr.length;
        for(int i=n-1;i>=0;i--){
            dp[i]=1;
            for(int j=i-1;j>=0;j--){
                if(arr[j]<arr[i]){
                    dp[i]=Math.max(dp[i],dp[j]+1);
                }
            }
            len=Math.max(len,dp[i]);
        }
        return len;        
    }
    //left to right
    public static int Lds_DP_02(int[] arr,int[] dp){
        int len=0,n=arr.length-1;
        for(int i=0;i<arr.length;i++){
            dp[i]=1;
            for(int j=i-1;j>=0;j--){
                if(arr[j]>arr[i]){
                    dp[i]=Math.max(dp[i],dp[j]+1);
                }
            }
            len=Math.max(len,dp[i]);
        }
        return len;
    } 
    public static class pair{
        int len;
        int idx;
        int val;
        String psf;
        public pair(int len,int idx,int val,String psf){
            this.len=len;
            this.idx=idx;
            this.val=val;
            this.psf=psf;
        }
    }
    public static int printLS(int[] arr,int[] dp){
        int count=0;
        int omax=Lis_DP_01(arr,dp);
        // int omax=Lis_DP_02(arr,dp);
        // int omax=Lds_DP_01(arr,dp);
        // int omax=Lds_DP_02(arr,dp);
        ArrayDeque<pair> qu=new ArrayDeque<>();
        for(int i=0;i<dp.length;i++){
            if(dp[i]==omax){
                qu.add(new pair(omax,i,arr[i],arr[i]+" "));
            }
        }

        while(qu.size()!=0){
            pair cp=qu.remove();
            if(cp.len==1){
                // System.out.println(cp.psf);
                count++;
            }
            for(int j=0;j<cp.idx;j++){
                if(dp[j]==cp.len-1 && arr[j]<cp.val){
                    qu.add(new pair(dp[j],j,arr[j],arr[j]+"->"+cp.psf));
                }
            }
        }
        return count;
    }
    public static void solve(){
        // int[] arr={10,22,43,33,21,50,41,60,59,3};
        // int[] arr={0,8,4,12,2,10,6,14,1,9,5,13,3,11,7,15,10};
        int[] arr={2,2,2,2,2};
        int[] dp=new int[arr.length];
        // Lis_DP_01(arr,dp);
        // Lis_DP_02(arr,dp);
        // Lds_DP_01(arr,dp);
        // System.out.println(Lds_DP_02(arr,dp));
        // int ans=printLS(arr,dp);
        int ans=Lis_rec(arr,dp);
        System.out.println(ans);
        // display(dp);
    }
    public static void display(int[] arr){
        for(int ele:arr) System.out.print(ele+" ");
        System.out.println();
    }
}