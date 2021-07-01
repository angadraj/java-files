import java.util.*;
import java.lang.*;
class KNight{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int r=sc.nextInt();
        int c=sc.nextInt();
        int[][] chess=new int[n][n];
        knight(chess,r,c,1);
    }
    public static void display(int[][] chess){
        for(int i=0;i<chess.length;i++){
            for(int j=0;j<chess[0].length;j++){
                System.out.print(chess[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void knight(int[][] chess,int r,int c,int moves){
        if(moves==chess.length*chess[0].length){
            chess[r][c]=moves;
            display(chess);
            chess[r][c]=0;
            return;
        }

        chess[r][c]=moves;
        //1
        if(r-2>=0 && c+1<chess[0].length && chess[r-2][c+1]==0){
            knight(chess,r-2,c+1,moves+1);
        }
        //2
        if(r-1>=0 && c+2<chess[0].length && chess[r-1][c+2]==0){
            knight(chess,r-1,c+2,moves+1);
        }
        //3
        if(r+1<chess.length && c+2<chess[0].length && chess[r+1][c+2]==0){
            knight(chess,r+1,c+2,moves+1);
        }
        //4   
        if(r+2<chess.length && c+1<chess[0].length && chess[r+2][c+1]==0){
            knight(chess,r+2,c+1,moves+1);
        } 
        //5
        if(r+2<chess.length && c-1>=0 && chess[r+2][c-1]==0){
            knight(chess,r+2,c-1,moves+1);
        }  
        //6 
        if(r+1<chess.length && c-2>=0 && chess[r+1][c-2]==0){
            knight(chess,r+1,c-2,moves+1);
        }  
        //7 
        if(r-1>=0 && c-2>=0 && chess[r-1][c-2]==0){
            knight(chess,r-1,c-2,moves+1);
        }  
        //8 
        if(r-2>=0 && c-1>=0 && chess[r-2][c-1]==0){
            knight(chess,r-2,c-1,moves+1);
        }   
        chess[r][c]=0;     
        
    }
}