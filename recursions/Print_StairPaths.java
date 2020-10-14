import java.util.*;
import java.lang.*;

public class Print_StairPaths {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        print_stairpaths(n,"");
    }
    public static void print_stairpaths(int n,String ans){
        //you can take 1,2,or 3 steps at a time
        if(n==0){
            System.out.print(ans+" ");
            return;
        }
        if(n-1>=0){
            print_stairpaths(n-1,ans+1);
        }
        if(n-2>=0){
            print_stairpaths(n-2,ans+2);
        }
        if(n-3>=0){
            print_stairpaths(n-3,ans+3);
        }

        // for(int jumps=1;jumps<=3;jumps++){
        //     if(n-jumps>=0){
        //         print_stairpaths(n-jumps,ans+jumps);
        //     }
        // }
    }
}