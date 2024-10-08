import java.util.ArrayList;
import java.util.List;

public class MinMaxAI implements IAI{

    private char aiRole;
    private char enRole;
    int[] choice;
    boolean myTurn;

    public MinMaxAI(char aiRole) {
        this.aiRole = aiRole;
        this.enRole = (aiRole == 'X')? 'O' : 'X';
    }

    @Override
    public char getAiRole() {
        return aiRole;
    }

    @Override
    public int[] chooseMove(char[][] board) {
        myTurn = true;
        int depth = emptyCells(board).size();
        if (depth == 0 || gameOver(board)) return new int[] {0, 0};



        if (depth == 9){
            return new int[] {0, 0};
        }
        int[] best = minimax(board, depth, aiRole);
        int[] move = {best[0], best[1]};
        return move;
    }

    private int[] minimax(char[][] board, int depth, char role) {
        int[] best = {-1, -1, 0};
        if (role == aiRole)
            best[2] = -1000;
        else
            best[2] = 1000;

        if (depth == 0 || gameOver(board)) return new int[] {0, 0, evaluate(board)};


        List<int[]> possibs = emptyCells(board);
        for (int i = 0; i < possibs.size(); i++){
            int[] cell = possibs.get(i);
            board[cell[0]][cell[1]] = role;
            int[] score = minimax(board, depth - 1, (role == aiRole)? enRole: aiRole);
            board[cell[0]][cell[1]] = '-';
            score[0] = cell[0];
            score[1] = cell[1];

            if (role == aiRole){
                if (score[2] > best[2])
                    best = score;
            } else {
                if (score[2] < best[2])
                    best = score;
            }
        }
        return best;
    }

    private int evaluate(char[][] board){
        int score = 0;
        if (wins(board, aiRole)) score = 1;
        else if (wins(board, enRole)) score = -1;


        return score;
    }

    private boolean wins(char[][] board, char role){
        for (int i = 0; i < 3; i++){
            if (board[i][0] == role && board[i][1] == role && board[i][2] == role) return true;
            if (board[0][i] == role && board[1][i] == role && board[2][i] == role) return true;
        }
        if (board[0][0] == role && board[1][1] == role && board[2][2] == role) return true;
        if (board[0][2] == role && board[1][1] == role && board[2][0] == role) return true;
        return false;
    }

    private boolean gameOver(char[][] board){
        return wins(board, aiRole) || wins(board, enRole);
    }

    private List<int[]> emptyCells(char[][] board){
        List<int[]> cells = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                if (board[i][j] == '-') cells.add(new int[] {i, j});
            }
        }
        return cells;
    }

    private boolean validateMove(char[][] board,int[] coords){
        if (board[coords[0]][coords[1]] == '-') return true;
        return false;
    }

}
