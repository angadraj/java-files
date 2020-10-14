import java.lang.*;
import java.util.*;

public class FloodFill2 {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[][] arr=new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                arr[i][j]=sc.nextInt();
            }
        }
        boolean[][] visited=new boolean[n][n];
        System.out.println("------------------------------");
        floodfill2(arr,0,0,"",visited);
    }
    public static void floodfill2(int[][] arr,int sr,int sc,String ans,boolean[][] visited){
        //t,l,d,r
        if(sr==arr.length-1 && sc==arr[0].length-1){
            System.out.print(ans+" ");
            return ;
        }

        visited[sr][sc]=true;

        if(sr-1>=0 && arr[sr-1][sc]==0 && visited[sr-1][sc]==false){
            floodfill2(arr,sr-1,sc,ans+'t',visited);
        }

        if(sc-1>=0 && arr[sr][sc-1]==0 && visited[sr][sc-1]==false){
            floodfill2(arr,sr,sc-1,ans+'l',visited);
        }

        if(sr+1<arr.length && arr[sr+1][sc]==0 && visited[sr+1][sc]==false){
            floodfill2(arr,sr+1,sc,ans+'d',visited);
        }

        if(sc+1<arr[0].length && arr[sr][sc+1]==0 && visited[sr][sc+1]==false){
            floodfill2(arr,sr,sc+1,ans+'r',visited);
        }
        visited[sr][sc]=false;

    }                                                                                                           
}