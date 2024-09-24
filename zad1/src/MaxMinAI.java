public class MaxMinAI implements IAI {
    private char aiPlayer;
    private char humanPlayer;

    public MaxMinAI(char aiPlayer) {
        this.aiPlayer = aiPlayer;
        this.humanPlayer = (aiPlayer == 'X') ? 'O' : 'X';
    }
    @Override
    public char getAiRole(){
        return aiPlayer;
    }
    @Override
    public int[] chooseMove(char[][] board) {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = (aiPlayer == 'X') ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = aiPlayer;
                    int score = minimax(board, 0, false);
                    board[i][j] = '-';

                    if (aiPlayer == 'X') {
                        if (score > bestScore) {
                            bestScore = score;
                            bestMove[0] = i;
                            bestMove[1] = j;
                        }
                    } else {
                        if (score < bestScore) {
                            bestScore = score;
                            bestMove[0] = i;
                            bestMove[1] = j;
                        }
                    }
                }
            }
        }

        return bestMove;
    }

    private int minimax(char[][] board, int depth, boolean isMaximizing) {
        char result = checkWinner(board);
        if (result != '-') {
            return (result == aiPlayer) ? 10 - depth : depth - 10;
        }

        if (isBoardFull(board)) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = aiPlayer;
                        int score = minimax(board, depth + 1, false);
                        board[i][j] = '-';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = humanPlayer;
                        int score = minimax(board, depth + 1, true);
                        board[i][j] = '-';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    private char checkWinner(char[][] board) {
        // Check rows, columns and diagonals
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '-') {
                return board[i][0];
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '-') {
                return board[0][i];
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') {
            return board[0][0];
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '-') {
            return board[0][2];
        }
        return '-';
    }

    private boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }
}