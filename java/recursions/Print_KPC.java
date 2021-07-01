import java.util.*;
import java.lang.*;

public class Print_KPC {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        print_kpc(str,"");
    }
    static String[] codes={".;","abc","def","ghi","jkl","mno","pqrs","tu","vw","xyz"};

    public static void print_kpc(String str,String ans){
        if(str.length()==0){
            System.out.print(ans+" ");
            return;
        }

        char ch=str.charAt(0);
        String roq=str.substring(1);
        int idx=ch-'0';
        String word=codes[idx];
        for(int i=0;i<word.length();i++){
            char w=word.charAt(i);
            print_kpc(roq,ans+w);
        }
    }
}