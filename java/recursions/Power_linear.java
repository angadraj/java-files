
import java.util.*;
import java.lang.*;

public class Power_linear {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int base=sc.nextInt();
        int exp=sc.nextInt();
        System.out.println(pow(base,exp));
    }
    public static int pow(int base,int exp){
        if(exp==0){
            return 1;
        }
        int ans=base*pow(base,exp-1);
        return ans;
    }
    
}