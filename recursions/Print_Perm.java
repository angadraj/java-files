import java.util.*;
import java.lang.*;
class Print_Perm{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        print_perm(str,"");
    }
    public static void print_perm(String str,String ans){
        if(str.length()==0){
            System.out.print(ans+" ");
            return;
        }
        
        for(int i=0;i<str.length();i++){
            char ch=str.charAt(i);
            String roq=str.substring(0,i)+str.substring(i+1);
            print_perm(roq,ans+ch);
        }
    }
}