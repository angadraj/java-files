import java.util.*;
import java.lang.*;
class Traversals{
    public static void traversal1(int[][] arr){
        //bottom left to uper right
        for(int k=arr.length-1;k>=0;k--){
            int i=k;
            int j=0;
            while(i<arr.length){
                System.out.print(arr[i][j]+" ");
                i++;j++;
            }
            System.out.println();
        }
        for(int k=1;k<arr[0].length;k++){
            int i=0;
            int j=k;
            while(j<arr[0].length){
                System.out.print(arr[i][j]+" ");
                i++;j++;
            }
            System.out.println();
        }
    }
    public static void traversal2(int[][] arr){
        for(int k=arr[0].length-1;k>=0;k--){
            int i=0;
            int j=k;
            while(j<arr[0].length){
                System.out.print(arr[i][j]+" ");
                i++;j++;
            }
            System.out.println();
        }
        for(int k=1;k<arr.length;k++){
            int i=k;
            int j=0;
            while(i<arr.length){
                System.out.print(arr[i][j]+" ");
                i++;j++;
            }
            System.out.println();
        }
    }
    public static void traversal3(int[][] arr){
        for(int k=0;k<arr.length;k++){
            int i=k;
            int j=0;
            while(i>=0){
                System.out.print(arr[i][j]+" ");
                i--;j++;
            }
            System.out.println();
        }
        for(int k=1;k<arr[0].length;k++){
            int j=k;
            int i=arr.length-1;
            while(j<arr[0].length){
                System.out.print(arr[i][j]+" ");
                i--;j++;
            }
            System.out.println();
        }
    }
    public static void traversal4(int[][] arr){
        for(int k=arr[0].length-1;k>=0;k--){
            int i=arr.length-1;
            int j=k;
            while(j<arr[0].length){
                System.out.print(arr[i][j]+" ");
                i--;j++;
            }
            System.out.println();
        }
        for(int k=1;k>=0;k--){
            int j=0;
            int i=k;
            while(i>=0){
                System.out.print(arr[i][j]+" ");
                i--;j++;
            }
            System.out.println();
        }
    }

    public static void main(String args[]){
        int[][] arr = {{1, 2, 3, 4},
                        {5, 6, 7, 8}, 
                        {9, 10, 11, 12}, 
                        {13, 14, 15, 16}};
        traversal1(arr);

    }
}