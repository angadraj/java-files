import java.util.*;
import java.lang.*;

public class TOH {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int t1=sc.nextInt();
        int t2=sc.nextInt();
        int t3=sc.nextInt();
        int n=sc.nextInt();
        toh(n,t1,t2,t3);
    }
    public static void toh(int n,int src,int dest,int helper){
        if(n==1){
            System.out.println("T"+src+"->"+"T"+dest);
            return ;
        }
        
        toh(n-1,src,helper,dest);
        System.out.println("T"+src+"->"+"T"+dest);
        toh(n-1,helper,dest,src);
    }
    
}