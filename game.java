import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class game extends JFrame {
    private static final int BOARD_SIZE = 5;
    private static final char SHIP_SYMBOL = 'S';
    private static final char HIT_SYMBOL = 'X';
    private static final char MISS_SYMBOL = '-';
    private static final char EMPTY_SYMBOL = 'o';
    
    private char[][] board;
    private JButton[][] buttons;
    private int shipsRemaining;

    public game() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        shipsRemaining = BOARD_SIZE; // Each ship occupies one cell
        initializeBoard();
        initializeGUI();
        placeShips();
    }

    private void initializeBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = EMPTY_SYMBOL;
            }
        }
    }

    private void placeShips() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            while (true) {
                int row = (int) (Math.random() * BOARD_SIZE);
                int col = (int) (Math.random() * BOARD_SIZE);
                if (board[row][col] != SHIP_SYMBOL) {
                    board[row][col] = SHIP_SYMBOL;
                    break;
                }
            }
        }
    }

    private void initializeGUI() {
        setTitle("Battleship Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(60, 60));
                button.setBackground(Color.pink); // Set initial background color of buttons
                button.setForeground(Color.WHITE);
                button.addActionListener(new ButtonClickListener(row, col));
                buttons[row][col] = button;
                add(button);
            }
        }
        getContentPane().setBackground(Color.CYAN);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            handleGuess(row, col);
        }
    }

    private void handleGuess(int row, int col) {
        if (board[row][col] == SHIP_SYMBOL) {
            buttons[row][col].setText(String.valueOf(HIT_SYMBOL));
            buttons[row][col].setBackground(Color.RED);
            buttons[row][col].setForeground(Color.WHITE);
            board[row][col] = HIT_SYMBOL;
            shipsRemaining--;
            if (shipsRemaining == 0) {
                JOptionPane.showMessageDialog(this, "Congratulations! You sank all the battleships!");
                dispose();
                
            }
        } else if (board[row][col] == EMPTY_SYMBOL) {
            buttons[row][col].setText(String.valueOf(MISS_SYMBOL));
            buttons[row][col].setBackground(Color.WHITE);
            buttons[row][col].setForeground(Color.BLACK);
            board[row][col] = MISS_SYMBOL;
        } else if (board[row][col] == HIT_SYMBOL || board[row][col] == MISS_SYMBOL) {
            JOptionPane.showMessageDialog(this, "You already guessed this location. Please try again.");
        }
    }

    public static void main(String[] args) {
        new game();
    }
}
