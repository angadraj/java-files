import java.util.*;
import java.lang.*;
class Factorial{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        System.out.println(fact(n));
    }
    public static int fact(int n){
        if(n==1){
            return 1;
        }
        int res=n*fact(n-1);
        return res;
    }
}