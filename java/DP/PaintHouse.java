import java.util.*;
import java.lang.*;
class PaintHouse{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[][] houses=new int[3][n];
        for(int i=0;i<n;i++){
            houses[0][i]=sc.nextInt();
            houses[1][i]=sc.nextInt();
            houses[2][i]=sc.nextInt();
        }
        int redprev=houses[0][0];
        int blueprev=houses[1][0];
        int greenprev=houses[2][0];
        for(int i=1;i<n;i++){
            int redcurr=houses[0][i]+Math.min(blueprev,greenprev);
            int bluecurr=houses[1][i]+Math.min(redprev,greenprev);
            int greencurr=houses[2][i]+Math.min(redprev,blueprev);

            redprev=redcurr;
            blueprev=bluecurr;
            greenprev=greencurr;
        }
        System.out.println(Math.min(redprev,Math.min(blueprev,greenprev)));

    }
} 