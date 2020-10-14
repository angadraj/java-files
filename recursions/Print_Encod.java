import java.util.*;
import java.lang.*;

public class Print_Encod {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        int ans=print_encod(str,"");
        System.out.println(ans);
    }
    public static int print_encod(String str,String ans){
        if(str.length()==0){
            System.out.print(ans+" ");
            return 1;
        }

        int n1=str.charAt(0)-'0';
        char ch1=(char)(n1-1+'A');
        if(n1<=0){
            return 0 ;
        }
        int count=0;
        count+=print_encod(str.substring(1),ans+ch1);

        if(str.length()>=2){
            int n2=str.charAt(1)-'0';
            int combo=(n1*10)+n2;
            if(combo<=26){
            char ch2=(char)(combo-1+'A');
            count+=print_encod(str.substring(2),ans+ch2);
            }
        }
        return count;
    }
}