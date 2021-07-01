import java.util.*;
import java.lang.*;
class Strings{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        // System.out.print(rec(str,""));
        System.out.print(dp(str));
    }
    public static int dp(String str){
        int a=0,b=0,c=0;
        for(int i=0;i<str.length();i++){
            char ch=str.charAt(i);
            if(ch=='a'){
                a=2*a+1;
            }
            else if(ch=='b'){
                b=2*b+a;
            }
            else if(ch=='c'){
                c=2*c+b;
            }
        }
        return c;
    }
    // public static int rec(String str,String ans){
    //     if(str.length()==0){
    //         // System.out.print(ans+" ");
    //         return 1;
    //     }
    //     int count=0;
    //     for(int i=0;i<str.length();i++){
    //         char ch=str.charAt(i);
    //         if(ch=='a' && (ans.length()==0 || ans.charAt(ans.length()-1)=='a')){
    //             count+=rec(str.substring(i+1),ans+'a');
    //         }
    //         else if(ch=='b' &&  ans.length()!=0 && (ans.charAt(ans.length()-1)=='a'|| ans.charAt(ans.length()-1)=='b')){
    //             count+=rec(str.substring(i+1),ans+'b');
    //         }
    //         else if(ch=='c' && ans.length()!=0 && (ans.charAt(ans.length()-1)=='b'|| ans.charAt(ans.length()-1)=='c')){
    //             count+=rec(str.substring(i+1),ans+'c');
    //         }
    //     }
    //     return count;
    // }
}