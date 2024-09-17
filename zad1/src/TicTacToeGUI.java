import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TicTacToeGUI extends JFrame {
    private TicTacToe game; // Логика игры
    private JLabel[][] cells; // Ячейки для отображения игрового поля
    private char playerRole; // Роль игрока ('X' или 'O')
    private boolean isPlayerTurn; // Флаг, проверяющий, может ли игрок сделать ход
    private AI ai; // Экземпляр AI для управления противником

    public TicTacToeGUI() {
        chooseRole();
        game = new TicTacToe(playerRole); // Инициализация игры с выбранной ролью
        ai = new AI(playerRole == 'X' ? 'O' : 'X'); // AI играет за противоположную роль
        cells = new JLabel[3][3]; // Массив для визуальных ячеек
        initializeUI(); // Метод для настройки интерфейса
        isPlayerTurn = playerRole == 'X';
        if (!isPlayerTurn) {
            aiTurn();// Игрок X всегда ходит первым
        }
    }

    // Метод для настройки графического интерфейса
    private void initializeUI() {
        setTitle("Tic-Tac-Toe"); // Название окна
        setSize(400, 400); // Размер окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Закрытие окна

        setLayout(new GridLayout(3, 3)); // Сетка 3х3 для отображения поля

        // Заполняем сетку ячейками
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int row = i;
                final int col = j;

                cells[i][j] = new JLabel("", SwingConstants.CENTER); // Создание ячейки
                cells[i][j].setFont(new Font("Arial", Font.BOLD, 60)); // Установка шрифта
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Рамка вокруг ячейки
                cells[i][j].setOpaque(true); // Сделаем фон ячейки видимым

                // Обработчик кликов по ячейке для совершения хода
                cells[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (isPlayerTurn && game.isCellEmpty(row, col)) { // Проверка, может ли игрок ходить
                            game.placeMark(row, col, playerRole); // Игрок делает ход
                            updateBoard(); // Обновляем поле
                            isPlayerTurn = false; // Передаем ход противнику

                            // Ход AI
                            aiTurn();
                        }
                    }
                });
                add(cells[i][j]); // Добавляем ячейку на экран
            }
        }

        // Обновляем начальное состояние поля
        updateBoard();
    }

    // Ход AI (заготовка)
    private void aiTurn() {
        SwingUtilities.invokeLater(() -> {
            // AI выбирает ход
            int[] move = ai.chooseMove(game.getBoard());
            if (move != null) {
                game.placeMark(move[0], move[1], ai.getAiRole()); // AI делает ход
                updateBoard(); // Обновляем поле
            }

            // Возвращаем ход игроку
            isPlayerTurn = true;
        });
    }

    // Метод для обновления отображения игрового поля
    public void updateBoard() {
        char[][] board = game.getBoard(); // Получаем текущее состояние поля

        // Проходим по всем ячейкам и обновляем их
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j].setText(String.valueOf(board[i][j])); // Устанавливаем текст в ячейке

                // Меняем фон ячеек в зависимости от значения
                if (board[i][j] == 'X') {
                    cells[i][j].setBackground(Color.CYAN); // Если 'X', делаем фон голубым
                } else if (board[i][j] == 'O') {
                    cells[i][j].setBackground(Color.PINK); // Если 'O', делаем фон розовым
                } else {
                    cells[i][j].setBackground(Color.WHITE); // Пустые ячейки — белый фон
                }
            }
        }
    }

    // Метод для вызова диалогового окна для выбора роли
    private void chooseRole() {
        String[] options = {"X", "O"}; // Варианты для выбора
        int choice = JOptionPane.showOptionDialog(this, "Выберите, за кого будете играть:",
                "Выбор роли", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        playerRole = options[choice].charAt(0);// Установка выбранной роли
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeGUI gui = new TicTacToeGUI(); // Создаем объект GUI
            gui.setVisible(true); // Отображаем окно
        });
    }
}
