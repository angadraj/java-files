import java.util.*;
class BinaryStrings_02{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        // System.out.print(rec(n,""));
        System.out.print(dp(n));
    }
    public static int dp(int n){
        int a=1;
        int b=1;
        for(int i=1;i<n;i++){
            int nexta=b;
            int nextb=b+a;

            a=nexta;
            b=nextb;
        }
        return a+b;
    }
    public static int rec(int n,String ans){
        if(ans.length()==n){
            // System.out.print(ans+" ");
            return 1;
        }
        int mycount=0;
         mycount+=rec(n,ans+'0');
        if(ans.length()==0 || ans.charAt(ans.length()-1)=='0'){
            mycount+=rec(n,ans+'1');
        }
        return mycount;
    }     
}