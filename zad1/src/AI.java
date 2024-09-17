import java.util.Random;

public class AI {
    private char aiRole;
    private Random random = new Random();
    private int turn = 0;
    private boolean winable = false;


    public AI(char aiRole) {
        this.aiRole = aiRole;
    }

    public char getAiRole() {
        return aiRole;
    }

    // Метод для выбора хода
    public int[] chooseMove(char[][] board) {
        turn += 1;
        if (turn == 1 ) { //Правило первого хода
            if (aiRole == 'X')
                return new int[] {1, 1};
            else
                return new int[] {0, 0};
        }



        return think(board); // Если нет свободных клеток
    }

    private int[] think(char[][] board){
        if (winable) return winThink(board);
        if (aiRole == 'O') return circleThink();

            if (turn == 2){
                if (board[0][0] == 'O' || board[0][2] == 'O')
                    return new int[] {0, 1};
                if (board[2][2] == 'O' || board[2][0] == 'O')
                    return new int[] {2, 1};

                winable = true;
                if (board[0][1] == 'O' || board[1][2] == 'O')
                    return new int[] {0, 2};
                if (board[1][0] == 'O' || board[2][1] == 'O')
                    return new int[] {2, 0};

            } else if (turn == 3){
                //win
                if (board[0][1] == '-')
                    return  new int[] {0, 1};
                if (board[2][1] == '-')
                    return new int[] {2, 1};

                //not win
                if (board[0][0] == 'O')
                    return new int[] {2, 0};
                if (board[0][2] == 'O')
                    return new int[] {2, 2};
                if (board[2][0] == 'O')
                    return new int[] {0, 0};
                if (board[2][2] == 'O')
                    return new int[] {0, 2};

            } else if (turn == 4){
                if (board[0][0] == 'X') {
                    if (board[2][2] == '-')
                        return  new int[] {2, 2};
                    return  new int[] {1, 2};
                }

                if (board[2][0] == 'X') {
                    if (board[0][2] == '-')
                        return  new int[] {0, 2};
                    return  new int[] {1, 2};
                }

                if (board[2][2] == 'X') {
                    if (board[0][0] == '-')
                        return  new int[] {2, 0};
                    return  new int[] {1, 0};
                }
                if (board[0][2] == 'X') {
                    if (board[2][0] == '-')
                        return  new int[] {2, 0};
                    return  new int[] {1, 0};
                }
            } else if (turn == 5){
                if (board[1][0] == 'X' && board[1][1] == 'X') {
                    if (board[1][2] == '-')
                        return  new int[] {1, 2};

                    if (board[0][0] == 'O') return new int[] {2, 0};
                    return  new int[] {0, 0};
                }

                if (board[1][0] == '-')
                    return  new int[] {1, 0};

                if (board[0][2] == 'O') return new int[] {2, 2};
                return  new int[] {0, 2};
            }



        return new int[] {0, 0};
    }

    private int[] circleThink() {
        return new int[] {0, 1};
    }

    private int[] winThink(char[][] board) {
        if (turn == 3){
            if (board[2][0] == 'X'){
                if (board[0][2] == '-')
                    return new int[] {0, 2};

                if (board[1][0] == 'O')
                    return new int[] {2, 2};

                return  new int[] {0, 0};
            }

            if (board[2][0] == '-')
                return new int[] {2, 0};

            if (board[0][1] == 'O')
                return new int[] {2, 2};

            return new int[] {0, 0};
        }
        if (board[0][1] == 'O'){
            if (board[0][0] == 'O')
                return new int[] {1, 2};
            if (board[2][2] == '-')
                return new int[] {2, 2};

            return  new int[] {0, 0};
        }
        if (board[1][0] == 'O'){
            if (board[0][0] == 'O')
                return new int[] {2, 1};
            if (board[2][2] == '-')
                return new int[] {2, 2};

            return  new int[] {0, 0};
        }
        if (board[1][2] == 'O'){
            if (board[2][2] == 'O')
                return new int[] {0, 1};
        }

        return new int[] {1, 0};

    }
}
