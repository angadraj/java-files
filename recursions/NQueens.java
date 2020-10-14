import java.util.*;
import java.lang.*;
class NQueens{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[][] board=new int[n][n];
        nqueens(board,0,"");
    }
    public static boolean isQueenSafe(int[][] board,int row,int col){
        //left diag
        for(int i=row-1,j=col-1;i>=0 && j>=0;i--,j--){
            if(board[i][j]==1){
                return false;
            }
        }
        //right diag
        for(int i=row-1,j=col+1;i>=0 && j<board[0].length;i--,j++){
            if(board[i][j]==1){
                return false;
            }
        }
        //same upper col
        for(int i=row-1;i>=0;i--){
            if(board[i][col]==1){
                return false;
            }
        }
        return true;
    }



    public static void nqueens(int[][] board,int row,String ans){
        if(row==board.length){
            System.out.println(ans+" ");
            return;
        }

        for(int col=0;col<board[0].length;col++){
            if(isQueenSafe(board,row,col)){
                board[row][col]=1;
                nqueens(board,row+1,ans+row+"-"+col+",");
                board[row][col]=0;//for next solution
            }
        }
    }
}