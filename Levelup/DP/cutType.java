import java.util.Arrays;
class cutType{
    public static void main(String args[]){
        solve();
    }
    public static int mcmRec(int[] arr,int si,int ei){
        if(si+1==ei){
            return 0;  
        }
        int min=(int)1e8;
        for(int cut=si+1;cut<ei;cut++){
            int left_tree=mcmRec(arr,si,cut);
            int right_tree=mcmRec(arr,cut,ei);
            int self_ans=left_tree+right_tree+(arr[si]*arr[cut]*arr[ei]);
            min=Math.min(self_ans,min);
        }
        return min;
    }
    public static int mcmMemo(int[] arr,int si,int ei,int[][] dp){
        if(si+1==ei){
            return dp[si][ei]=0;  
        }
        if(dp[si][ei]!=-1) return dp[si][ei];
        int min=(int)1e8;
        for(int cut=si+1;cut<ei;cut++){
            int left_tree=mcmMemo(arr,si,cut,dp);
            int right_tree=mcmMemo(arr,cut,ei,dp);
            int self_ans=left_tree+right_tree+(arr[si]*arr[cut]*arr[ei]);
            min=Math.min(self_ans,min);
        }
        return dp[si][ei]=min;
    }
    public static int mcmDp(int[] arr,int Si,int Ei,int[][] dp){
        int n=arr.length;
        for(int gap=1;gap<n;gap++){
            for(int si=0,ei=gap;ei<n;si++,ei++){
                if(si+1==ei){
                    dp[si][ei]=0;  
                    continue;
                }
                int min=(int)1e8;
                for(int cut=si+1;cut<ei;cut++){
                    int left_tree=dp[si][cut];
                    int right_tree=dp[cut][ei];
                    int self_ans=left_tree+right_tree+(arr[si]*arr[cut]*arr[ei]);
                    min=Math.min(self_ans,min);
                }
                dp[si][ei]=min;
            }
        }
        return dp[Si][Ei];
    }
    public static int mcmDpString(int[] arr,int Si,int Ei,int[][] dp){
        int n=arr.length;
        String[][] sdp=new String[n][n];
        for(int gap=1;gap<n;gap++){
            for(int si=0,ei=gap;ei<n;si++,ei++){
                if(si+1==ei){
                    dp[si][ei]=0;
                    String s=""+(char)(si+'A');  
                    sdp[si][ei]=s;
                    continue;
                }
                int min=(int)1e8;
                String s="";
                for(int cut=si+1;cut<ei;cut++){
                    int left_tree=dp[si][cut];
                    int right_tree=dp[cut][ei];
                    int self_ans=left_tree+right_tree+(arr[si]*arr[cut]*arr[ei]);
                    if(self_ans<min){
                        min=self_ans;   
                        s="("+sdp[si][cut]+","+sdp[cut][ei]+")";
                    }
                }
                dp[si][ei]=min;
                sdp[si][ei]=s;
            }
        }
        for(int i=0;i<sdp.length;i++){
            for(int j=0;j<sdp[0].length;j++){
                System.out.print(sdp[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println(sdp[Si][Ei]);
        return dp[Si][Ei];
    }
    //min-max expression
    public static class pair{
        int min=(int)(1e8);
        int max=-(int)1e8;
        public pair(int min,int max){
            this.min=min;
            this.max=max;
        }
        public pair(){

        }
        public String toString(){
            return "("+min+", "+max+")";
        }
    }
    public static int eval(char op,int val1,int val2){
        if(op=='*') return val1*val2;
        else if(op=='-') return val1-val2;
        else  return val1+val2;
    }
    public static pair minMaxEval(String str,int si,int ei,pair[][] dp) {
        if(si==ei){
            int val=str.charAt(si)-'0';
            return dp[si][ei]=new pair(val,val);
        }
        if(dp[si][ei]!=null) return dp[si][ei];
        pair myans=new pair();
        for(int cp=si+1;cp<ei;cp+=2){
            pair left_ans=minMaxEval(str,si,cp-1,dp);
            pair right_ans=minMaxEval(str,cp+1,ei,dp);
            char op=str.charAt(cp);
            myans.min=Math.min(myans.min,eval(op,left_ans.min,right_ans.min));
            myans.max=Math.max(myans.max,eval(op,left_ans.max,right_ans.max));
        }
        return dp[si][ei]=myans;
    }
    public static pair minMaxEval_02(int[] nums,char opr[],int si,int ei){
        if(si==ei){
            int val=nums[si];
            return new pair(nums[si],nums[si]);
        }
        int min=(int)(1e8);
        pair ans=new pair();
        for(int cp=si;cp<ei;cp++){
            pair left_ans=minMaxEval_02(nums,opr,si,cp);
            pair right_ans=minMaxEval_02(nums,opr,cp+1,ei);
            char op=opr[cp];
            pair p=eval_combi(op,left_ans,right_ans);

            ans.min=Math.min(p.min,ans.min);
            ans.max=Math.max(p.max,ans.max);
        }
        return ans;
    }
    public static pair eval_combi(char op,pair left_ans,pair right_ans){
        int a=eval(op,left_ans.min,right_ans.min);
        int b=eval(op,left_ans.max,right_ans.max);
        int c=eval(op,left_ans.min,right_ans.max);
        int d=eval(op,left_ans.max,right_ans.min);

        pair ans=new pair();
        ans.min=Math.min(Math.min(a,b),Math.min(c,d));
        ans.max=Math.max(Math.max(a,b),Math.max(c,d));
        return ans;
    }
    //burst balloons,letcode 312
    public static int maxCoins(int[] arr,int si,int ei,int[][] dp){
        if(dp[si][ei]!=-1) return dp[si][ei];
        int lval=(si-1==-1)?1:arr[si-1];
        int rval=(ei+1==arr.length)?1:arr[ei+1];
        int max=-(int)1e8;
        for(int cp=si;cp<=ei;cp++){
            int left_tree=(cp==si)?0:maxCoins(arr,si,cp-1,dp);
            int right_tree=(cp==ei)?0:maxCoins(arr,cp+1,ei,dp);

            int self=left_tree + right_tree + (lval*arr[cp]*rval);
            max=Math.max(self,max);
        }
        return dp[si][ei]=max;
    }
    public int maxCoinsHelper(int[] arr,int Si,int Ei,int[][] dp){
        int n=arr.length;
        for(int gap=0;gap<n;gap++){
            for(int si=0,ei=gap;ei<n;ei++,si++){
                int lval=(si-1==-1)?1:arr[si-1];
                int rval=(ei+1==arr.length)?1:arr[ei+1];
                int max=-(int)1e8;
                for(int cp=si;cp<=ei;cp++){
                    int left_tree=(cp==si)?0:dp[si][cp-1];
                    int right_tree=(cp==ei)?0:dp[cp+1][ei];

                    int self=left_tree + right_tree + (lval*arr[cp]*rval);
                    max=Math.max(self,max);
                }
                dp[si][ei]=max;
            }
        }
        return dp[Si][Ei];
    }
    public static int obst(int[] keys,int[] freq,int si,int ei,int[] prefixSum){

        int min_ans=(int)1e8;
        for(int cp=si;cp<=ei;cp++){
            int left_tree=(cp==si)?0:obst(keys,freq,si,cp-1,prefixSum);
            int right_tree=(cp==ei)?0:obst(keys,freq,cp+1,ei,prefixSum);
            int self=left_tree+right_tree+(prefixSum[ei]-(si==0?0:prefixSum[si-1]));
            min_ans=Math.min(min_ans,self);
       }
       return min_ans;
    }
    //rod cutting
    public static int rod_cutting_01(int[] arr){
        // int[] narr=new int[arr.length+1];
        // for(int i=0;i<arr.length;i++){
        //     narr[i+1]=arr[i];
        // }
        int[] dp=new int[arr.length];
        dp[0]=arr[0];
        for(int i=1;i<dp.length;i++){
            dp[i]=arr[i]; // self comparison and in while loop cartesian product 
            int li=0;
            int ri=i-1;
            while(li<=ri){
                dp[i]=Math.max(dp[i],dp[li]+dp[ri]);
                li++;ri--;
            }
        }
        return dp[dp.length-1];
    }
    public static void solve(){
        // int[] arr={1,2,3,4,3};
        // int[] arr={40, 20, 30, 10, 30}  ;
        // int[] arr={200,400,100,10,200,300,1};
        // int[][] dp=new int[arr.length][arr.length];
        // for(int[] d:dp) Arrays.fill(d,-1);
        // System.out.println(mcmRec(arr,0,arr.length-1));
        // System.out.println(mcmDp(arr,0,arr.length-1,dp));
        // System.out.println(mcmDpString(arr,0,arr.length-1,dp));
        // System.out.println(mcmMemo(arr,0,arr.length-1,dp));
        
        // String str="1+2*3";
        // int n=str.length();
        // pair[][] dp=new pair[n][n];
        // pair ans=minMaxEval(str,0,n-1,dp);  
        // System.out.println(ans.min +", "+ans.max);
        // for(pair p[]:dp){
            //     for(pair cp:p){
                //         System.out.print(cp+" ");
                //     }
                //     System.out.println();
                // }
                
                //minmax_02
                // char op[]={'-','*','-','-'};
                // int nums[]={1,2,3,4,5};
                // pair cp=minMaxEval_02(nums,op,0,nums.length-1);
                // System.out.println(cp.min+", "+cp.max);
                
                //maxcoins
                //obst
                // int[] keys={6,8,9};
                // int[] freq={60,80,90};
                // int n=keys.length;
                // int[] prefixSum=new int[n];
                // int prev=0;
                // for(int i=0;i<prefixSum.length;i++){
                //     prefixSum[i]=prev+freq[i];
                //     prev=prefixSum[i];
                // }
                // System.out.println(obst(keys,freq,0,n-1,prefixSum));

                //boolean parenthization 
                // booleanPar();

                //rod cutting
                // int[] arr={1,5,8,9,10,17,17,20};
                int[] arr={5,8,4};
                System.out.println(rod_cutting_01(arr));
    }
    //boolean parenthization : based only on faith
    static class pairParenth{
        int tc;
        int fc;
        public pairParenth(int tc,int fc){
            this.tc=tc;
            this.fc=fc;
        }
        public pairParenth(){

        }
        public String toString(){
            return "("+tc+","+fc+")";
        }
    }
    public static pairParenth booleanParenth(String str1,String str2,int si,int ei,pairParenth[][] dp){
        if(si==ei){
            pairParenth recpair=new pairParenth();
            if(str1.charAt(si)=='T'){
                recpair.tc=1;
                recpair.fc=0;
            }
            else if(str1.charAt(si)=='F'){
                recpair.tc=0;
                recpair.fc=1;
            }
            return dp[si][ei]=recpair;
        }
        if(dp[si][ei]!=null) return dp[si][ei];

        pairParenth spair=new pairParenth(0,0);
        for(int cp=si;cp<ei;cp++){
            pairParenth lpair=booleanParenth(str1,str2,si,cp,dp);
            pairParenth rpair=booleanParenth(str1,str2,cp+1,ei,dp);
            char op=str2.charAt(cp);
            evalparenth(lpair,rpair,spair,op);
        }
        return dp[si][ei]=spair;
    }
    public static void evalparenth(pairParenth lp,pairParenth rp,pairParenth sp,char op){
        int TRC=rp.tc+rp.fc;
        int TLC=lp.tc+lp.fc;
        int total=TRC*TLC;

        if(op=='|'){
            sp.tc+=total-(lp.fc*rp.fc);
            sp.fc+=(rp.fc*lp.fc);
        }
        else if(op=='&'){
            sp.tc+=(rp.tc*lp.tc);
            sp.fc+=total-(rp.tc*lp.tc);
        }   
        else{
            sp.tc+=(rp.fc*lp.tc)+(rp.tc*lp.fc);
            sp.fc+=(rp.tc*lp.tc)+(rp.fc*lp.fc);
        }

    }
    public static pairParenth bParDP(String str1,String str2,int Si,int Ei,pairParenth[][] dp){
        int n=str1.length();
        for(int gap=0;gap<n;gap++){
            for(int si=Si,ei=gap;ei<n;si++,ei++){
                if(si==ei){
                    pairParenth recpair=new pairParenth();
                    if(str1.charAt(si)=='T'){
                        recpair.tc=1;
                        recpair.fc=0;
                    }
                    else if(str1.charAt(si)=='F'){
                        recpair.tc=0;
                        recpair.fc=1;
                    }
                    dp[si][ei]=recpair;
                    continue;
                }
                pairParenth spair=new pairParenth(0,0);
                for(int cp=si;cp<ei;cp++){
                    pairParenth lpair=dp[si][cp];
                    pairParenth rpair=dp[cp+1][ei];
                    char op=str2.charAt(cp);
                    evalparenth(lpair,rpair,spair,op);
                }
                dp[si][ei]=spair;
            }
        }
        return dp[0][dp[0].length-1];
    }
    // static int mod=1003;
	// public static int[] bp_02(String str,int si,int ei,int[][][] dp){
	//     if(si==ei){
	//         int[] recans=new int[2];
	//         if(str.charAt(si)=='T'){
	//             recans[0]=0;
	//             recans[1]=1;
 	//         }
	//         else if(str.charAt(si)=='F'){
	//             recans[0]=1;
	//             recans[1]=0;
	//         }
	//         return recans;
	//     }
	    
	//     if(dp[si][ei][0]!=0 || dp[si][ei][1]!=0){
	//         return dp[si][ei];
	//     }
	    
	//     int[] myans=new int[2];
	//     for(int cp=si+1;cp<ei;cp+=2){
	//         int[] lans=bp_02(str,si,cp-1,dp);
	//         int[] rans=bp_02(str,cp+1,ei,dp); 
	        
	//         char op=str.charAt(cp);
	//         bpEval_(lans,rans,myans,op);
	//     }
	//     return dp[si][ei]=myans;
    // }
    // public static void bpEval_(int[] lans,int[] rans,int[] myans,char op){
	//     int Rtf=(rans[0] % mod + rans[1] % mod) % mod;
	//     int Ltf=(lans[0] % mod + lans[1] % mod) % mod;
	//     int Ttf=(Rtf % mod * Ltf % mod) % mod;
	    
	//     if(op=='|'){
	//         myans[0]+=(rans[0] % mod * lans[0] % mod) % mod;
	//         myans[1]+=(Ttf % mod - (rans[0] % mod * lans[0] % mod) % mod + mod) % mod;
	//     }
	//     else if(op=='&'){
	//         myans[0]+=(Ttf % mod - (rans[1] % mod * lans[1] % mod) % mod + mod) % mod;
	//         myans[1]+=(lans[1] % mod * rans[1] % mod) % mod;
	//     }
	//     else{
	//         myans[0]+=((lans[0] % mod * rans[0] % mod) % mod + (lans[1] % mod * rans[1] % mod) % mod) % mod;
	//         myans[1]+=((lans[1] % mod * rans[0] % mod) % mod + (lans[0] % mod * rans[1] % mod) % mod) % mod;
	//     }
	//     myans[0] %= mod;
	//     myans[1] %= mod;
	// }
    public static void booleanPar(){
        String str1="TTFT";
        String str2="|&^";
        int n=str1.length();
        pairParenth dp[][]=new pairParenth[n][n];
        // pairParenth ans=booleanParenth(str1,str2,0,n-1,dp);
        pairParenth ans=bParDP(str1,str2,0,n-1,dp);
        System.out.println(ans.tc+" ");
        for(pairParenth cp[]:dp){
            for(pairParenth c:cp){
                System.out.print(c+" ");
            }
            System.out.println();
        }
    }
    public static void print1D(int[] arr){
        for(int ele:arr) System.out.print(ele+" ");
        System.out.println();
    }
    public static void print2D(int[][] arr){
        for(int[] a:arr) print1D(a);
        System.out.println();
    }
}