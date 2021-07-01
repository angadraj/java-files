import java.util.*;
import java.lang.*;
class MinCostMaze{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int m=sc.nextInt();
        int[][] maze=new int[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                maze[i][j]=sc.nextInt();
            }
        }
        // int cost=0;
        int ans=mincost(maze);
        System.out.println(ans);
    }
    // static int ans1=0;
    // static int ans2=0;
    public static int mincost(int[][] maze){
        
        // if(r==maze.length-1 && c==maze[0].length-1){
        //     return maze[maze.length-1][maze[0].length-1];
        // }
        // if(r>=maze.length || c>=maze[0].length){
        //     return Integer.MAX_VALUE;
        // }
        // return Math.min(mincost(maze,r+1,c),mincost(maze,r,c+1))+maze[r][c];
        int[][] dp=new int[maze.length][maze[0].length];
        for(int i=dp.length-1;i>=0;i--){
            for(int j=dp[0].length-1;j>=0;j--){
                if(i==dp.length-1 && j==dp[0].length-1){
                    dp[i][j]=maze[i][j];
                }
                else if(i==dp.length-1){
                    dp[i][j]=maze[i][j]+dp[i][j+1];
                }
                else if(j==dp[0].length-1){
                    dp[i][j]=maze[i][j]+dp[i+1][j];
                }
                else{
                    dp[i][j]+=Math.min(dp[i+1][j],dp[i][j+1])+maze[i][j];
                }
            }
        }
        return dp[0][0];
  
   }

       
}