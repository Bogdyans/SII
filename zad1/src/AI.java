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
        if (aiRole == 'X'){
            return thinkX(board);
        } else return thinkO(board);

    }

    private int[] thinkX(char[][] board)
    {
        if (winable) return thinkWinnableX(board);

        switch (turn)
        {
            case 1:
                return new int[] {1, 1};
            case 2:
                if (board[0][1] == 'O' || board[1][0] == 'O'
                || board[2][1] == 'O' || board[1][2] == 'O') //В данном случае мы получаем выигрышную ситуацию в любом из ходов противника
                {
                   winable = true;
                    return thinkWinnableX(board);
                }
                else //В данном случае пмы стремимся получить ничью в худшем случае
                {
                    return new int[] {0, 1};
                }

            case 3: //Дальше особо нет смысла проверять что либо, просто навязываем мат в один ход противнику и всё, проверяя на зевки
                int[] check = checkVertical(board, 1);
                if (check != null) return check;

                if (board[0][0] == 'O' || board[2][2] == 'O')
                {
                    return new int[] {2, 0};
                }
                else return new int[] {2, 2};

            case 4:
                int[] check2 = checkDiagonal(board, 2);
                if (check2 != null) return check2;
                check2 = checkDiagonal(board, 1);
                if (check2 != null) return check2;


                if (board[0][2] == 'X') return new int[] {1, 2};
                else return new int[] {1, 0};

            case 5:
                int[] check3 = checkHorizontal(board, 1);
                if (check3 != null) return check3;

                for (int i =0; i < board.length; i++)
                {
                    for (int j =0; j < board[0].length; j++)
                    {
                        if (board[i][j] == '-') return new int[] {i, j};
                    }
                }
        }
        return null;
    }

    private int[] thinkWinnableX(char[][] board)
    {
        switch (turn)
        {
            case 2:
                if (board[0][1] == 'O' || board[1][2] == 'O')
                {
                    return new int[] {2, 0};
                }
                else
                {
                    return new int[] {0, 2};
                }

            case 3:
                int[] check = checkDiagonal(board, 2);
                if (check != null) return check; //Ловим на зевке если противник не защитил 2 диагональ

                //В обратном случае делаем вилку с обязательным выигрышем в след ходу
                if (board[0][1] == 'O' || board[1][0] == 'O')
                {
                    return new int[] {0, 0};
                }
                else
                {
                    return new int[] {2, 2};
                }


            case 4: //Какая то точно отдаст выигрышные координаты
                int[] check2 = checkDiagonal(board, 1);
                if (check2 != null) return check2;

                check2 = checkHorizontal(board, 0);
                if (check2 != null) return check2;

                check2 = checkHorizontal(board, 2);
                if (check2 != null) return check2;

                check2 = checkVertical(board, 0);
                if (check2 != null) return check2;

                check2 = checkVertical(board, 2);
                if (check2 != null) return check2;

        }
        return null;
    }



    //Не так сильно продумано, так как я только в 10 часов вечера увидел дедлайн оставшийся в 2 часа, поэтому здесь более простой алгоритм
    private int[] thinkO(char[][] board)
    {
        switch (turn)
        {
            case 1:
                if (board[1][1] == '-') return new int[] {1, 1};
                return new int[] {0, 0};
            default:
                int[] check = checkEnemyWin(board);
                if (check != null) return check;

                check = checkWin(board);
                if (check != null) return check;

                check = checkCorners(board);
                if (check != null) return check;

                for (int i =0; i < board.length; i++)
                {
                    for (int j =0; j < board[0].length; j++)
                    {
                        if (board[i][j] == '-') return new int[] {i, j};
                    }
                }
        }
        return null;
    }

    private int[] checkCorners(char[][] board)
    {

        for (int i = 0; i < 3; i += 2)
        {
            for (int j = 0; j < 3; j += 2)
            {
                if (board[i][j] == '-') return new int[] {i, j};
            }
        }
        return null;
    }

    private int[] checkEnemyWin(char[][] board)
    {
        int[] check;
        for (int i = 0; i < 3; i++) {
            check = checkVerticalEnemy(board, i);
            if (check != null) return check;
            check = checkHorizontalEnemy(board, i);
            if (check != null) return check;
        }
        check = checkEnemyDiagonal(board, 1);
        if (check != null) return check;
        check = checkEnemyDiagonal(board, 2);
        if (check != null) return check;
        return null;
    }

    private int[] checkWin(char[][] board)
    {
        int[] check;
        for (int i = 0; i < 3; i++) {
            check = checkVertical(board, i);
            if (check != null) return check;
            check = checkHorizontal(board, i);
            if (check != null) return check;
        }
        check = checkDiagonal(board, 1);
        if (check != null) return check;
        check = checkDiagonal(board, 2);
        if (check != null) return check;
        return null;
    }

    private int[] checkHorizontal(char[][] board, int num)
    {
        int[] coords = new int[2];
        int n = 0;
        for (int i = 0; i < 3; i++){
            if (board[num][i] == aiRole)
            {
                n++;
            }
            else if (board[num][i] == '-')
            {
                coords[0] = num;
                coords[1] = i;
            }
            else return null;
        }

        if (n == 2) return coords;
        return null;
    }
    private int[] checkHorizontalEnemy(char[][] board, int num)
    {
        char enemyRole = (this.aiRole == 'X')? 'O' : 'X';
        int[] coords = new int[2];
        int n = 0;
        for (int i = 0; i < 3; i++){
            if (board[num][i] == enemyRole)
            {
                n++;
            }
            else if (board[num][i] == '-')
            {
                coords[0] = num;
                coords[1] = i;
            }
            else return null;
        }
        if (n == 2) return coords;
        return null;
    }

    private int[] checkVertical(char[][] board, int num)
    {
        int[] coords = new int[2];
        int n = 0;
        for (int i = 0; i < 3; i++){
            if (board[i][num] == aiRole)
            {
                n++;
            }
            else if (board[i][num] == '-')
            {
                coords[0] = i;
                coords[1] = num;
            }
            else return null;
        }
        if (n == 2) return coords;
        return null;
    }
    private int[] checkVerticalEnemy(char[][] board, int num)
    {
        char enemyRole = (this.aiRole == 'X')? 'O' : 'X';
        int[] coords = new int[2];
        int n = 0;
        for (int i = 0; i < 3; i++){
            if (board[i][num] == enemyRole)
            {
                n++;
            }
            else if (board[i][num] == '-')
            {
                coords[0] = i;
                coords[1] = num;
            }
            else return null;
        }
        if (n == 2) return coords;
        return null;
    }

    /*
    Checks diagonal for win situation
    1 for x
           x
            x

    2 for   x
           x
          x

    return null if not winnable in one turn
    */
    private int[] checkDiagonal(char[][] board, int num)
    {
        int n = 0;
        int[] coords = new int[2];
        if (num == 1){
            for (int i = 0; i < 3; i++)
            {
                if (board[i][i] == aiRole)
                {
                    n++;
                }
                else if (board[i][i] == '-')
                {
                    coords[0] = i;
                    coords[1] = i;
                }
                else return null;
            }
        }
        else if (num == 2)
        {
            for (int i = 2; i >= 0; i--)
            {
                if (board[2-i][i] == aiRole)
                {
                    n++;
                }
                else if (board[2-i][i] == '-')
                {
                    coords[0] = 2-i;
                    coords[1] = i;
                }
                else return null;
            }
        }

        if (n == 2) return coords;
        return null;
    }

    //Люблю запах дублированного кода по утрам
    private int[] checkEnemyDiagonal(char[][] board, int num)
    {
        char enemyRole = (this.aiRole == 'X')? 'O' : 'X';
        int n = 0;
        int[] coords = new int[2];
        if (num == 1){
            for (int i = 0; i < 3; i++)
            {
                if (board[i][i] == enemyRole)
                {
                    n++;
                }
                else if (board[i][i] == '-')
                {
                    coords[0] = i;
                    coords[1] = i;
                }
                else return null;
            }
        }
        else if (num == 2)
        {
            for (int i = 2; i >= 0; i--)
            {
                if (board[2-i][i] == enemyRole)
                {
                    n++;
                }
                else if (board[2-i][i] == '-')
                {
                    coords[0] = 2-i;
                    coords[1] = i;
                }
                else return null;
            }
        }

        if (n == 2) return coords;
        return null;
    }
}
