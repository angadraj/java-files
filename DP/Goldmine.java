import java.util.*;
import java.lang.*;
class Goldmine{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[][] maze=new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                maze[i][j]=sc.nextInt();
            }
        }
        System.out.print(goldmineDp(maze));
    }
    public static int goldmineDp(int[][] maze){
        int[][] dp=new int[maze.length][maze[0].length];
        for(int j=dp[0].length-1;j>=0;j--){
            for(int i=dp.length-1;i>=0;i--){
                if(j==dp[0].length-1){
                    dp[i][j]=maze[i][j];
                }
                else if(i==dp.length-1){
                    dp[i][j]=maze[i][j]+Math.max(dp[i-1][j+1],dp[i][j+1]);
                }
                else if(i==0){
                    dp[i][j]=maze[i][j]+Math.max(dp[i][j+1],dp[i+1][j+1]);
                }
                else{
                    dp[i][j]=maze[i][j]+Math.max(dp[i-1][j+1],Math.max(dp[i][j+1],dp[i+1][j+1]));
                }
            }
        }
        int sum=0;
        for(int i=0;i<dp.length;i++){
            if(dp[i][0]>sum){
                sum=dp[i][0];
            }
        }
        return sum;
    }
    // public static int gold(int[][] maze,int r,int c){
    //     if(c>=maze[0].length){//base case
    //         return 0;//no further calls from last column
    //     }
    //     if(r<0 || r>=maze.length){
    //         return 0;//out of bound condition
    //     }
    //     int x=gold(maze,r-1,c+1);
    //     int y=gold(maze,r,c+1);
    //     int z=gold(maze,r+1,c+1);
    //     return maze[r][c]+ Math.max(x,Math.max(y,z));
    //     //we allowed to take calls first
    // }
    // public static int goldMax(int[][] maze){
    //     int goldmax=0;
    //     for(int i=0;i<maze.length;i++){
    //         int temp=gold(maze,i,0);
    //         if(temp>goldmax){
    //             goldmax=temp;
    //         }
    //     }
    //     return goldmax;
    // }
}