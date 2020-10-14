import java.util.*;
import java.lang.*;

public class Print_MazePaths {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[][] maze=new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                maze[i][j]=sc.nextInt();
            }
        }
        //sr,sc,dr,dc;
        //you can move right or down but 1 at a time
        //right=col++,down=row--
        print_mazepaths(maze,"",0,0,maze.length-1,maze[0].length-1);
    }
    public static void print_mazepaths(int[][] maze,String ans,int sr,int sc,int dr,int dc){
        if(sr==dr && sc==dc){
            ans=ans+maze[sr][sc]+" ";
            System.out.print(ans+" ");
            return;
        }

        if(sr+1<=dr){
            print_mazepaths(maze,ans+maze[sr][sc]+" ",sr+1,sc,dr,dc);
        }
        if(sc+1<=dc){
            print_mazepaths(maze,ans+maze[sr][sc]+" ",sr,sc+1,dr,dc);
        }
    
    }
}