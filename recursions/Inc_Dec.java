import java.util.*;
import java.lang.*;

public class Inc_Dec {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        inc_dec(n);
    }
    public static void inc_dec(int n){
        if(n==0){
            return ;
        }
        System.out.print(n+" ");
        inc_dec(n-1);
        System.out.print(n+" ");
    }
    
}