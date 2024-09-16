import javax.swing.*;
import java.awt.*;

public class TicTacToeGUI extends JFrame {
    private TicTacToe game; // Логика игры
    private JLabel[][] cells; // Ячейки для отображения игрового поля

    public TicTacToeGUI() {
        game = new TicTacToe(); // Инициализация игры
        cells = new JLabel[3][3]; // Массив для визуальных ячеек
        initializeUI(); // Метод для настройки интерфейса
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
                cells[i][j] = new JLabel("", SwingConstants.CENTER); // Создание ячейки
                cells[i][j].setFont(new Font("Arial", Font.BOLD, 60)); // Установка шрифта
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Рамка вокруг ячейки
                add(cells[i][j]); // Добавляем ячейку на экран
            }
        }

        // Обновляем начальное состояние поля
        updateBoard();
    }

    // Метод для обновления отображения игрового поля
    public void updateBoard() {
        char[][] board = game.getBoard(); // Получаем текущее состояние поля

        // Проходим по всем ячейкам и обновляем их
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j].setText(String.valueOf(board[i][j])); // Устанавливаем текст в ячейке
            }
        }
    }
}
