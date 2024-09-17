class TicTacToe {
    private char[][] board;
    private char currentPlayer;
    private char playerRole;

    public TicTacToe(char playerRole) {
        this.playerRole = playerRole; // Запоминаем роль игрока
        currentPlayer = 'X'; // Игра всегда начинается с 'X'
        board = new char[3][3];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean isCellEmpty(int row, int col) {
        return board[row][col] == '-';
    }

    public void placeMark(int row, int col, char mark) {
        if (isCellEmpty(row, col)) {
            board[row][col] = mark;
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // Смена текущего игрока
        }
    }
}