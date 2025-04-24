package Projects;
import java.util.*;
public class TicTacToeFinal {
    public static void main(String[] args) {
        System.out.println("\t\t\tTIC TAC TOE : "); // created greets

        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.print("Do You Wanna Play Tic Tac Toe : (Type Yes or No)");
            String p = sc.next();
            // created different scenarios
            if(p.equalsIgnoreCase("Yes")){
                System.out.println("Do You Wanna Play Against 1. Human or 2. Computer ( Write 1 or 2 Accordingly )");
                int x = sc.nextInt();
                if(x == 1) twoplayergame();
                else playagainstcomputer();

            }
            else if(p.equalsIgnoreCase("no")){
                System.out.println("Thanks for visiting !!!");
                break;
            }
            else{
                System.out.println("You entered something wrong");
                break;
            }
        }
        sc.close();
    }

    public static void twoplayergame(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Player names ");
        System.out.print("Player 1 Name : ");
        String p1 = sc.next();
        System.out.print("Player 2 Name : ");
        String p2 = sc.next();
        System.out.println("Take reference of below board to play : \n");
        board();
        char[][] Board = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        System.out.println("\n\n");
        int ans = 0;

        for(int i = 1 ; i <= 9 ; i++){
            System.out.print("Enter " + ((i % 2 == 1) ? p1 : p2) + " : ");
            int n = sc.nextInt();
            if(n < 1 || n > 9){
                System.out.println("Invalid Move !! please retry");
                i--;
                continue;
            }
//             clear();
            ans = board2(n, Board, i);
            if(ans == 2) {
                System.out.println("Try again !!!");
                i--;
                continue;
            }
            if(ans == 1){
                System.out.print((i % 2 == 1) ? p1 : p2);
                System.out.println(" is Winner");
                break;
            }
            System.out.println();
        }
        if(ans == 0) System.out.println("Tie");
        return;
    }

    public static void board() {
        int a = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++,a++) {
                System.out.print(" " + a + " ");
                if (j != 2) System.out.print("|");
            }
            System.out.println();
            if (i != 2) System.out.println("---+---+---");
        }
    }

    public static int board2(int n,char[][] board,int idx){

        int row = (n-1)/3;
        int col = (n-1)%3;
        if(board[row][col] == 'O' || board[row][col] == 'X') {
            System.out.println("Wrong input");
            return 2;
        }

        board[row][col] = (idx%2==1)? 'O':'X';

        for(int i = 0 ;  ; i++){
            for(int j = 0 ; j < 3 ; j++){
                System.out.print("  " + board[i][j]+ "  ");
                if(j != 2) System.out.print("|");
            }
            System.out.println();
            if(i == 2) break;
            for (int j = 0; j < 16; j++) {
                if(j != 0 && j % 5 == 0 && j!= 15) System.out.print(".+");
                else {
                    if(j == 4){
                        continue;
                    }
                    System.out.print(".");
                }
            }
            System.out.println();
        }

        boolean flag = check(board,row,col,(idx%2==1?'O':'X')); // false -> winner is found , true -> winner not found
        if(!flag) {
            return 1;
        }
        return 0;
    }

    public static boolean check(char[][] grid,int row,int col,char ch){
        // check for row
        int count = 0;
        for(int i = 0 ; i < 3 ; i++){
            if(grid[row][i] == ch) count++;
        }
        if(count == 3) return false;

        // check for col
        count = 0;
        for(int i = 0 ; i < 3 ; i++){
            if(grid[i][col] == ch) count++;
        }
        if(count == 3) return false;

        // check for principal diagonal
        count = 0;
        for(int i = 0 ; i < 3 ; i++){
            if(grid[i][i] == ch) count++;
        }
        if(count == 3) return false;
        count = 0;
        // check for secondary diagonal
        for(int j = 0 ; j < 3 ; j++){
            if(grid[j][3-j-1] == ch) count++;
        }
        return count != 3;

    }

    public static void playagainstcomputer() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String player = sc.next();
        System.out.println("Use below board as reference to play against computer:");
        board();

        char[][] board = {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };

        for (int move = 1; move <= 9; move++) {
            if (move % 2 == 1) { // player's move
                System.out.print("Enter your move (" + player + "): ");
                int pos = sc.nextInt();
                int row = (pos - 1) / 3;
                int col = (pos - 1) % 3;
                if (pos < 1 || pos > 9 || board[row][col] != ' ') {
                    System.out.println("Invalid move! Try again.");
                    move--;
                    continue;
                }
                board[row][col] = 'O';
            } else {
                System.out.println("Computer is thinking...\nAnd Computer makes move");
                int[] best = minimax(board, true);
                board[best[1]][best[2]] = 'X';
            }

            printBoard(board);
            char winner = getWinner(board);
            if (winner == 'O') {
                System.out.println("\n\n\n\n\n\t\t\t\t\t\t"+player + " wins!");
                return;
            } else if (winner == 'X') {
                System.out.println("\n\n\n\n\n\t\t\t\t\t\tComputer wins!\n\n\n\n\n");
                return;
            }
        }
        System.out.println("It's a Tie!");
    }


    public static int[] minimax(char[][] board, boolean isMax) {
        char winner = getWinner(board);
        if (winner == 'X') return new int[] {1, -1, -1};
        if (winner == 'O') return new int[] {-1, -1, -1};
        if (isboardfilled(board)) return new int[] {0, -1, -1};

        int bestScore = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] bestMove = {-1, -1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = isMax ? 'X' : 'O';
                    int score = minimax(board, !isMax)[0];
                    board[i][j] = ' ';

                    if ((isMax && score > bestScore) || (!isMax && score < bestScore)) {
                        bestScore = score;
                        bestMove = new int[] {score, i, j};
                    }
                }
            }
        }
        return bestMove;
    }


    public static boolean isboardfilled(char[][] board) {
        for (char[] row : board)
            for (char cell : row)
                if (cell == ' ') return false;
        return true;
    }

    public static char getWinner(char[][] b) {
        for (int i = 0; i < 3; i++) {
            if (b[i][0] != ' ' && b[i][0] == b[i][1] && b[i][1] == b[i][2]) return b[i][0];
            if (b[0][i] != ' ' && b[0][i] == b[1][i] && b[1][i] == b[2][i]) return b[0][i];
        }
        if (b[0][0] != ' ' && b[0][0] == b[1][1] && b[1][1] == b[2][2]) return b[0][0];
        if (b[0][2] != ' ' && b[0][2] == b[1][1] && b[1][1] == b[2][0]) return b[0][2];
        return ' ';
    }

    public static void printBoard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + board[i][j] + " ");
                if (j != 2) System.out.print("|");
            }
            System.out.println();
            if (i != 2) System.out.println("---+---+---");
        }
    }

}
