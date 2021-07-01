import java.util.*;
import java.lang.*;

public class Print_MazeJumps {
    public  static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[][] maze=new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                maze[i][j]=sc.nextInt();
            }
        }
        //maze,sr,sc,dr,dc,ans
        print_mazejumps(maze,0,0,maze.length-1,maze[0].length-1,"");
    }
    public static void print_mazejumps(int[][] maze,int sr,int sc,int dr,int dc,String ans){
        //now ,we can take any jump so we need a loop;
        if(sc==dc && sr==dr){
            System.out.println(ans+maze[sr][sc]);
            return;
        }
        //every one is free to take as much valid moves they can
        //this can be applied if there are n*m matrices also
        for(int jumps=1;sc+jumps<=dc;jumps++){
            if(sc+jumps<=dc){
                print_mazejumps(maze,sr,sc+jumps,dr,dc,ans+maze[sr][sc]);
            }
        }

        for(int jumps=1;sr+jumps<=dr;jumps++){
            if(sr+jumps<=dr){
                print_mazejumps(maze,sr+jumps,sc,dr,dc,ans+maze[sr][sc]);
            }
        }            

        for(int jumps=1;sr+jumps<=dr && sc+jumps<=dc;jumps++){
            if(sc+jumps<=dc && sr+jumps<=dr){
                print_mazejumps(maze,sr+jumps,sc+jumps,dr,dc,ans+maze[sr][sc]);
            }
        }

    }
    
}