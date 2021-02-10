class targetPNC {
    public static void main(String args[]){
        solve();
    }
    //levels tar , options coins
    public static int infPerm(int[] coins, int idx, int tar,String asf){
        if(tar == 0){
            System.out.println(asf);
            return 1;
        }
        
        int count = 0;
        for(int i = idx; i < coins.length; i++){
            int coin = coins[i];
            if(tar - coin >= 0){
                count += infPerm(coins, 0, tar - coin, asf + coin );
            }
        }
        return count;
    }

    public static int infCombi(int[] coins, int idx, int tar, String asf){
        if(tar == 0){
            System.out.println(asf);
            return 1;
        }
        int count = 0;
        for(int i = idx; i < coins.length; i++){
            int coin = coins[i];
            if(tar - coin >= 0){
                count += infCombi(coins, i, tar - coin, asf + coin);
            }
        }   
        return count;
    }

    public static int singleCombi(int[] coins, int idx, int tar, String asf){
        if(tar == 0){
            System.out.println(asf);
            return 1;
        }
        int count = 0;
        for(int i = idx; i < coins.length; i++){
            int coin = coins[i];
            if(tar - coin >= 0){
                count += singleCombi(coins, i + 1, tar - coin, asf + coin);
            }
        }   
        return count;
    }

    public static int singlePerm(int[] coins, int idx, int tar, String asf){
        if(tar == 0){
            System.out.println(asf);
            return 1;
        }
        int count = 0;
        for(int i = idx; i < coins.length; i++){
            if(coins[i] > 0 && tar - coins[i] >= 0){
                int coin = coins[i];
                coins[i] = -coins[i];
                count += singlePerm(coins, 0, tar - coin, asf + coin);
                coins[i] = coin;
            }
        }   
        return count;
    }
    //levels coins , options yes, no
    public static int infPerm_2(int[] coins, int idx, int tar, String asf){
        if(idx == coins.length){
            if(tar == 0){
                System.out.println(asf);
                return 1;
            }
            return 0;
        }
        
        int count = 0;
        int coin = coins[idx];
        if(tar - coin >= 0){
            count += infPerm_2(coins, 0, tar - coin, asf + coin);
        }
        count += infPerm_2(coins, idx + 1, tar, asf);
        return count;
    }
    public static int infCombi_2(int[] coins, int idx, int tar, String asf){
        if(idx == coins.length){
            if(tar == 0){
                System.out.println(asf);
                return 1;
            }
            return 0;
        }
        
        int count = 0;
        int coin = coins[idx];
        if(tar - coin >= 0){
            count += infCombi_2(coins, idx, tar - coin, asf + coin);
        }
        count += infCombi_2(coins, idx + 1, tar, asf);
        return count;
    }
    public static int singleCombi_2(int[] coins, int idx, int tar, String asf){
        if(idx == coins.length){
            if(tar == 0){
                System.out.println(asf);
                return 1;
            }
            return 0;
        }
        
        int count = 0;
        int coin = coins[idx];
        if(tar - coin >= 0){
            count += singleCombi_2(coins, idx + 1, tar - coin, asf + coin);
        }
        count += singleCombi_2(coins, idx + 1, tar, asf);
        return count;
    }
    public static int singlePerm_2(int[] coins, int idx, int tar, String asf){
        if(idx == coins.length){
            if(tar == 0){
                System.out.println(asf);
                return 1;
            }
            return 0;
        }
        
        int count = 0;
        if(coins[idx] > 0 && tar - coins[idx] >= 0){
            int temp = coins[idx];
            coins[idx] = -coins[idx];
            count += singlePerm_2(coins, 0, tar - temp, asf + temp);
            coins[idx] = temp;
        }
        count += singlePerm_2(coins, idx + 1, tar, asf);
        return count;
    }
    //Queens combination and pemutations

    public static int oneDQueenCombi(int tnq, int[] boxes, int idx){
        if(tnq == 0){
            for(int i = 0; i < boxes.length; i++){
                System.out.print(boxes[i] == 1 ? "q" : "-");
            }
            System.out.println();
            return 1;
        }
        int count = 0;
        for(int i = idx; i < boxes.length; i++){
            boxes[i] = 1;
            count += oneDQueenCombi(tnq-1, boxes, i+1);
            boxes[i] = 0;
        }
        return count;
    }

    public static int oneDQueenPerm(int tnq, int[] boxes, int idx, int cq){
        if(cq > tnq){
            for(int i = 0; i < boxes.length; i++){
                System.out.print(boxes[i] != 0 ? "q" + boxes[i] + " " : "- ");
            }
            System.out.println();
            return 1;
        }
        int count = 0;
        for(int i = idx; i < boxes.length; i++){
            if(boxes[i] == 0){
                boxes[i] = cq;
                count += oneDQueenPerm(tnq, boxes, 0, cq + 1);
                boxes[i] = 0;
            }
        }
        return count;
    }
    public static void display(int[][] chess){
        for(int[] arr : chess){
            for(int val : arr){
                System.out.print(val != 0 ? "q " : "- ");
            }
            System.out.println();
        }
    }

    public static int twoDQueenCombi(int[][] boxes, int tnq, int idx){
        if(tnq == 0){
            display(boxes);
            System.out.println();
            return 1;
        }
        int count = 0;
        int n = boxes.length;
        int m = boxes[0].length;
        for(int i = idx; i < n * m; i++){
            int r = i / m;
            int c = i % m;
            if(boxes[r][c] == 0){
                boxes[r][c] = 1;
                count += twoDQueenCombi(boxes, tnq-1, 0);
                boxes[r][c] = 0;
            }
        }
        return count;
    }

    public static int twoDQueenPerm(int[][] boxes, int tnq, int cq){
        if(cq > tnq){
            for(int[] arr : boxes){
                for(int val : arr){
                    System.out.print(val != 0 ? "q" + val + " " : "- ");
                }
                System.out.println();
            }
            System.out.println();
            return 1;
        }

        int count = 0;
        int n = boxes.length;
        int m = boxes[0].length;
        for(int i = 0; i < n * m; i++){
            int r = i / m;
            int c = i % m;
            if(boxes[r][c] == 0){
                boxes[r][c] = cq;
                count += twoDQueenPerm(boxes, tnq, cq + 1);
                boxes[r][c] = 0;
            }
        }
        return count;
    }

    //n queens

    static int[][] dirs = {{-1,-1}, {-1,0}, {-1,1}};
    
    public static boolean isSafe(int[][] chess, int r, int c){
        for(int radius = 1; radius <= Math.max(chess.length, chess[0].length); radius++){
            for(int[] d : dirs){
                int x = r + radius * d[0];
                int y = c + radius * d[1];
                if(x >= 0 && y >= 0 && x < chess.length && y < chess[0].length && chess[x][y] == 1){
                    return false;
                } else {
                    continue;
                }
            }
        }
        return true;
    }
    
    public static int nQueens2(int[][] chess, int tnq, int row){
        if(row == chess.length || tnq == 0){
            if(tnq == 0){
                for(int[] arr : chess){
                    for(int val : arr){
                        System.out.print(val != 0 ? "q " : "- ");
                    }
                    System.out.println();
                }
                System.out.println();
                return 1;
            }
            return 0;
        }

        int count = 0;
        for(int col = 0; col < chess[0].length; col++){
            if(isSafe(chess, row, col)){
                chess[row][col] = 1;
                nQueens2(chess, tnq - 1, row + 1);
                chess[row][col] = 0;
            }
        }
        return count;
    }

    // n queens branch and bound , row wise 
    static boolean cols[];
    static boolean diag[];
    static boolean adiag[];

    public static int nQueen1(int[][] chess, int tnq, int row){
        if(row == chess.length || tnq == 0){
            for(int[] arr : chess){
                for(int val : arr){
                    System.out.print(val == 0 ? "- " : "q ");
                }
                System.out.println();
            }
            System.out.println();
            return 1;
        }
        // int n = chess.length;
        int m = chess[0].length;
        int count = 0;

        for(int col = 0; col < m; col++){
            if(!cols[col] && !diag[row + col] && !adiag[row - col + m] && chess[row][col] == 0){
               
                cols[col] = true;
                diag[row + col] = true;
                adiag[row - col + m] = true;
                chess[row][col] = 1;

                count += nQueen1(chess, tnq - 1, row + 1);

                chess[row][col] = 0;
                cols[col] = false;
                diag[row + col] = false;
                adiag[row - col + m] = false;
            }
        }
        return count;
    }
    // N queens bit wise
    static int colsB = 0;
    static int diagB = 0;
    static int adiagB = 0;
    
    public static int nQueen3(int[][] chess, int tnq, int row){
        if(tnq == 0){
            for(int[] arr : chess){
                for(int val : arr){
                    System.out.print(val != 0 ? "q " : "- ");
                }
                System.out.println();
            }
            System.out.println();
            return 1;
        }
        int count = 0;
        int m = chess[0].length;
        for(int col = 0; col < m; col++){
            if(
                (colsB & (1 << col)) == 0 &&
                (diagB & (1 << (row + col))) == 0 &&
                (adiagB & (1 << (row - col + m))) == 0 && 
                chess[row][col] == 0
            ){
                chess[row][col] = 1;
                colsB ^= (1 << col);
                diagB ^= (1 << (row + col));
                adiagB ^= (1 << (row - col + m));

                count += nQueen3(chess, tnq - 1, row + 1);

                chess[row][col] = 0;
                colsB ^= (1 << col);
                diagB ^= (1 << (row + col));
                adiagB ^= (1 << (row - col + m));
            }
        }
        return count;
    }

    public static void solve(){
        int[][] boxes = new int[4][4];
        int n = boxes.length; 
        int tnq = n;
        int m = boxes[0].length;
        cols = new boolean[m];
        diag = new boolean[n + m - 1];
        adiag = new boolean[n + m - 1];
        System.out.println(nQueen3(boxes, tnq, 0));
    }
}

