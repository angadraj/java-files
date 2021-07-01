public class Sudoku{
    public static void main(String args[]){
        solve();
    }
    public static void solve(){
        char arr[][]={{".","8","7","6","5","4","3","2","1"},{"2",".",".",".",".",".",".",".","."],["3",".",".",".",".",".",".",".","."},{"4",".",".",".",".",".",".",".","."},{"5",".",".",".",".",".",".",".","."},{"6",".",".",".",".",".",".",".","."},{"7",".",".",".",".",".",".",".","."},{"8",".",".",".",".",".",".",".","."},{"9",".",".",".",".",".",".",".","."}};
        System.out.println(sudokusolver(arr,0));
    }
    public static void display(int[][] arr){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
    }
    public static boolean sudokusolver(char[][] arr,int si){
        if(si==arr.length*arr[0].length){
            // display(arr)
            // System.out.println();
            return true;
        }
        //we will operate 2d to 1 d matrix 
        //we will find the row and col of current cell
        int r=si/9;
        int c=si%9;
        if(arr[r][c]!='.'){
            return sudokusolver(arr,si+1);
        }
        //place numbers
        // int count=0;
        for(int num=1;num<=9;num++){
            if(isValid(arr,r,c,num)){
                arr[r][c]=(char)(num+'0');
                if(sudokusolver(arr,si+1)) return true;
                arr[r][c]='.';
            }
        }
        return false;
    }
    public static boolean isValid(char[][] arr,int row,int col,int num){
        for(int i=0;i<arr.length;i++){
            if(arr[i][col]-'0'==num){
                return false;
            }
        }
        for(int j=0;j<arr[0].length;j++){
            if(arr[row][j]-'0'==num){
                return false;
            }
        }
        //check in the sub 3X3 matrices
         row=(row/3)*3;
         col=(col/3)*3;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(arr[row+i][col+i]-'0'==num){
                    return false;
                }
            }
        }
        return true;
    }
}