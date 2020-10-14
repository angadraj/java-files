import java.util.*;
import java.lang.*;
class Imptrees{
    public static void main(String args[]){
        solve();
    }
    public static void solve() {
        // int arr[]={10,1,2,7,6,1,5};
        // Arrays.sort(arr);
        // int arr[]={1,1,2,5,6,7,10};
        // int ans=coinInfPerm(arr,0,10,""); 
        // int ans=coinInfComb(arr,0,10,""); 
        // int ans=coinSinglePerm(arr,0,8,""); 
        // int ans=coinSingleCombo(arr,0,8,""); 
        // int ans=NqueensPerm(new int[6],0,3,"");
        // int[][] q=new int[4][4]; 
        // int ans=NquensComb(q,0,3,""); 
        // int ans=TwoDQueensPerm(q,0,4,""); 
        // int ans=TwoDQuennsEffPerm(q,0,4,""); 
        // int ans=TwoDQueensComb(q,0,4,""); 
        // int ans=TwoDQueensFloorWise(q,0,4,""); 
        // wordBreakCombinations();
        // System.out.println(ans);
        crossWord();
    }
    public static int coinInfPerm(int[] arr,int idx,int tar,String ans){
        if(tar==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        for(int i=0;i<arr.length;i++){
            if(tar-arr[i]>=0){
                count+=coinInfPerm(arr,idx,tar-arr[i],ans+arr[i]+" ");
            }
        }
        return count;
    }
    public static int coinInfComb(int arr[],int idx,int tar,String ans){
        if(tar==0){
            System.out.println(ans+" ");
            return 1;
        }
        int count=0;
        for(int i=idx;i<arr.length;i++){
            if(tar-arr[i]>=0){
                count+=coinInfComb(arr,i,tar-arr[i],ans+arr[i]);
            }
        }
        return count;
    }
    //******************************************************************8 */
    public static int coinSinglePerm(int arr[],int idx,int tar,String ans){
        //give one item a place and faith for others left in leftover boxes
        //in this we can use one coin only one 
        if(tar==0){
            System.out.println(ans+" ");
            return 1;
        }
        // HashSet<Integer> hs=new HashSet<>();
        int count=0;
        for(int i=0;i<arr.length;i++){
            if(arr[i]>0 && tar-arr[i]>=0){
                int usedcoin=arr[i];
                arr[i]=-arr[i];
                // hs.add(usedcoin);
                count+=coinSinglePerm(arr,0,tar-usedcoin,ans+usedcoin);
                arr[i]=usedcoin;//for use again 
            }
        }
        return count;
    }
    //************************************************************************** */
    public static int coinSingleCombo(int[] arr,int idx,int tar,String ans){
        if(tar==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        // HashSet<Integer> hs=n/ew HashSet<>();
        for(int i=idx;i<arr.length;i++){
            if(tar-arr[i]>=0){
                count+=coinSingleCombo(arr,i+1,tar-arr[i],ans+arr[i]);
            }
        }
        return count;
    }
    //given 6 boxes and 3 queens find the total permututations in which queens can 
    // be occupied
    public static int NqueensPerm(int []arr,int idx,int tnq,String ans){
        if(tnq==0){
            System.out.println(ans);
            return 1;
        }   
        int count=0;
        for(int i=0;i<arr.length;i++){
            if(arr[i]>=0){
                int temp=arr[i];
                arr[i]=-1;
                count+=NqueensPerm(arr,idx,tnq-1,ans+"q"+i+"b"+i+" ");
                arr[i]=temp;
            }
        }
        return count;
    }
    public static int NquensComb(int[] arr,int idx,int tnq,String ans){
        if(tnq==0 || idx==arr.length){
            if(tnq==0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }
        int count=0;
        for(int i=idx;i<arr.length;i++){
            count+=NquensComb(arr,i+1,tnq-1,ans+"q"+i+"b"+i+" ");
        }
        return count;
    }
    public static int TwoDQueensPerm(int[][] arr,int idx,int tnq,String ans){
        if(tnq==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                if(arr[i][j]>=0){
                    int temp=arr[i][j];
                    arr[i][j]=-1;
                    count+=TwoDQueensPerm(arr,idx,tnq-1,ans+"("+i+","+j+")"+" ");
                    arr[i][j]=temp;
                }
            }
        }
        return count;
    }
    public static int TwoDQuennsEffPerm(int[][] arr,int idx,int tnq,String ans){
        if(tnq==0){
            System.out.println(ans);
            return 1;
        }
        int m=arr.length;
        int count=0;
        for(int i=0;i<arr.length*arr[0].length;i++){
            int r=i/m;
            int c=i%m;
            if(arr[r][c]>=0){
                int temp=arr[r][c];
                arr[r][c]=-1;
                count+=TwoDQuennsEffPerm(arr,idx,tnq-1,ans+"("+r+","+c+")");
                arr[r][c]=temp;
            }
        }
        return count;
    }
    public static int TwoDQueensComb(int[][] arr,int idx,int tnq,String ans){
        if(idx==arr.length*arr[0].length || tnq==0){
            if(tnq==0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }
        int m=arr.length;
        int count=0;
        for(int i=idx;i<arr.length*arr[0].length;i++){
            int r=i/m;
            int c=i%m;
            if(arr[r][c]>=0){
                count+=TwoDQueensComb(arr,i+1,tnq-1,ans+"("+r+","+c+")");
            }
        }
        return count;
    }
    public static int TwoDQueensFloorWise(int[][] arr,int row,int tnq,String ans){
        if(tnq==0 || row==arr.length){
            if(tnq==0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }
        int count=0;
        for(int col=0;col<arr.length;col++){
            count+=TwoDQueensFloorWise(arr,row+1,tnq-1,ans+"("+row+","+col+")");
        }
        return count;
    }
    public static void wordBreakCombinations(){
        String ques="ilikesamsungandmango";
        String[] dict={"mobile","samsung","sam","sung", 
        "man","mango","icecream","and", 
         "go","i","like","ice","cream","ilike"};
        HashSet<String> hs=new HashSet<>();
        int maxlen=0;
        for(String s:dict){
            maxlen=Math.max(maxlen,s.length());
            hs.add(s);
        }
        // System.out.println(helper1(ques,hs,maxlen,0,""));
        System.out.println(helper2(ques,hs,maxlen,0,ques.length(),""));
    }
    public static int helper1(String ques,HashSet<String> hs,int maxlen,int idx,String ans){
       if(idx==ques.length()){
           System.out.println(ans);
           return 1;    
       }
        int count=0;
       for(int i=idx+1;i<=idx+maxlen+1 && i<=ques.length();i++){
           String str=ques.substring(idx,i);
           if(hs.contains(str)){
               count+=helper1(ques,hs,maxlen,i,ans+str+" ");
           }
       }
       return count;
    }
    public static int helper2(String ques,HashSet<String> hs,int maxlen,int si,int ei,String ans){
        if(si==ei){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        for(int cp=si+1;cp<=si+maxlen+1 && cp<=ei;cp++){
            String str=ques.substring(si,cp);
            if(hs.contains(str)){
                count+=helper2(ques,hs,maxlen,cp,ei,ans+str+" ");
            }
        }
        return count;
    }
    //crypto--------------------------------------------------------------
    //crossword------------------------------------------------------------------------------------
    public static void display(char[][] board){
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                System.out.print(board[i][j]+"  ");
            }
            System.out.println();
        }
    }
    public static boolean[] placeH(char[][] board,String word,int r,int c){
        boolean[] loc=new boolean[word.length()];
        for(int i=0;i<word.length();i++){
            if(board[r][c+i]=='-'){
                board[r][c+i]=word.charAt(i);
                loc[i]=true;
            }
        }
        return loc;
    }
    public static boolean[] placeV(char[][] board,String word,int r,int c){
        boolean[] loc=new boolean[word.length()];
        for(int i=0;i<word.length();i++){
            if(board[r+i][c]=='-'){
                board[r+i][c]=word.charAt(i);
                loc[i]=true;
            }
        }
        return loc;
    }
    public static void unplaceH(char[][] board,String word,int r,int c,boolean[] loc){
        for(int i=0;i<word.length();i++){
            if(loc[i]==true){
                board[r][c+i]='-';
            }
        }
    }
    public static void unplaceV(char[][] board,String word,int r,int c,boolean[] loc){
        for(int i=0;i<word.length();i++){
            if(loc[i]==true){
                board[r+i][c]='-';
            }
        }
    }
    public static boolean canplaceH(char[][] arr,String word,int r,int c){
        for(int i=0;i<word.length();i++){
           if(c+i>=arr[0].length || (arr[r][c+i]!='-' && arr[r][c+i]!=word.charAt(i))){
               return false;
           }
        }
        return true;
    }
    public static boolean canplaceV(char[][] arr,String word,int r,int c){
        for(int i=0;i<word.length();i++){
            if(r+i>=arr.length || (arr[r+i][c]!='-' && arr[r+i][c]!=word.charAt(i))){
                return false;
            }
        }
        return true;
    }
    public static int crossWordSolver(char[][] board,String[] words,int vidx ){
        if(vidx==words.length){
            display(board);
            return 1;
        }
        String word=words[vidx];
        int count=0;
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j]=='-' || board[i][j]==word.charAt(0)){
                    if(canplaceH(board,word,i,j)){
                        boolean[] loc=placeH(board,word,i,j);
                        count+=crossWordSolver(board,words,vidx+1);
                        unplaceH(board,word,i,j,loc);
                    }
                    if(canplaceV(board,word,i,j)){
                        boolean[] loc=placeV(board,word,i,j);
                        count+=crossWordSolver(board,words,vidx+1);
                        unplaceV(board,word,i,j,loc);
                    }
                }
            }
        }
        return count;
    }
    public static void crossWord(){
        char arr[][]={
        {'+', '-', '+', '+', '+', '+', '+', '+', '+', '+'},
        {'+', '-', '+', '+', '+', '+', '+', '+', '+', '+'},
        {'+', '-', '-', '-', '-', '-', '-', '-', '+', '+'},
        {'+', '-', '+', '+', '+', '+', '+', '+', '+', '+'},
        {'+', '-', '+', '+', '+', '+', '+', '+', '+', '+'},
        {'+', '-', '-', '-', '-', '-', '-', '+', '+', '+'},
        {'+', '-', '+', '+', '+', '-', '+', '+', '+', '+'},
        {'+', '+', '+', '+', '+', '-', '+', '+', '+', '+'},
        {'+', '+', '+', '+', '+', '-', '+', '+', '+', '+'},
        {'+', '+', '+', '+', '+', '+', '+', '+', '+', '+'}};

        String[] words = {"agra", "norway", "england", "gwalior"};
        System.out.println(crossWordSolver(arr,words,0));
    }
}
// https://drive.google.com/drive/u/0/folders/15E9vAooWGtLv1UlzpkawUkM1f7_mFeJs
