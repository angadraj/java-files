import java.util.*;
import java.lang.*;
public class Pow_Log {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int base=sc.nextInt();
        int exp=sc.nextInt();
        System.out.println(pow_log(base,exp));
    }
    public static int pow_log(int base,int exp){
        if(exp==1){
            return base;
        }
        int  ans=pow_log(base,exp/2);
        if(exp%2==0){
            return ans*ans;
        }
        else{
            return base*ans*ans;
        }
    }

    
}