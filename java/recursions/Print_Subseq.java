import java.util.*;
import java.lang.*;

public class Print_Subseq {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        if(str.length()==0){
            System.out.println();
        }
        //abc:a,b,c,ab,bc,ac,abc,"";
        print_subseq(str,"");
    }
    public static void print_subseq(String str,String ans){
        if(str.length()==0){
            System.out.print(ans+" ");
            return;
        }

        char ch=str.charAt(0);
        String roq=str.substring(1);
        print_subseq(roq,ans+ch);
        print_subseq(roq,ans);

    }
}