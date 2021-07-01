import java.util.*;
import java.lang.*;
class FloodFill{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[][] maze=new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                maze[i][j]=sc.nextInt();
            }
        }
        System.out.println("--------------------------------------------");
        int[][] ans=flood_fill(maze,1,1,2);
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                System.out.print(ans[i][j]+" ");
            }
            System.out.println();
        }
    }
    public static int[][] flood_fill(int[][] maze,int sr,int sc,int k){
        if(maze[sr][sc]==k){
            return maze;
        }
        
        int x=maze[sr][sc];
        maze[sr][sc]=k;

        if(sr-1>=0 && maze[sr-1][sc]==x){
            flood_fill(maze,sr-1,sc,k);
        }
        if(sc+1<maze[0].length && maze[sr][sc+1]==x){
            flood_fill(maze,sr,sc+1,k);
        }
        if(sr+1<maze.length && maze[sr+1][sc]==x){
            flood_fill(maze,sr+1,sc,k);
        }
        if(sc-1>=0 && maze[sr][sc-1]==x){
            flood_fill(maze,sr,sc-1,k);
        }
        return maze;
    }

}