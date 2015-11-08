package cn.edu.xjtu.se.jackq;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Connect four is a two-player board game in which the
 * players alternately drop colored disks into a seven-column,
 * six-row vertically suspended grid.
 *
 * In this implementation, there are two colors which are yellow and
 * red representing two players. The game board stored in a
 * two-dimensional array of integers.
 *
 * For determining whether one of the players have achieved the
 * goal of the game that is have four same colored disks connected,
 * another utility is used in this package
 * ({@link PatternRecognition#isConsecutiveFour(int[][])} ).
 *
 * <p>
 * To use this game class, one way is initialize an instance and then invoke
 * the {@link #start()} method to run this game in console.
 * Another method is to manage game logic in other environment
 * by invoking {@link #dropDisk(int, int)}, {@link #isFill()}, {@link #isFinish()}
 * etc.. To access inner state of the game in order to gain more flexibility,
 * you can extend this class as a foundation of higher level implementation. An example
 * can found in this package as {@link ConnectFourGUI}, which implements an swing
 * user interface for this game.
 * </p>
 *
 * @author Jack Q (qiaobo@outlook.com)
 * @see ConnectFourGUI
 * @see PatternRecognition#isConsecutiveFour(int[][])
 */
public class ConnectFour {
    /** the number of rows in the game board*/
    protected final static int ROW = 6;
    /** the number of columns in the game board */
    protected final static int COLUMN = 7;

    /**
     *  a magic number representing red color for disk and player.
     *  To use this name at string output, refer to {@link #getColorName(int)}
     */
    protected final static int RED = 1;
    /**
     *  a magic number representing yellow color for disk and player.
     *  To use this name at string output, refer to {@link #getColorName(int)}
     */
    protected final static int YELLOW = 2;

    /**
     * integer array storing the current number of disks in a specific column.
     *
     * <p>
     * It should be kept consistency with values stored in {@link #gameBoard},
     * which means when updating the content of game board, this filed should
     * also be updated.
     * </p>
     *
     * <p>
     * This filed is mainly used to simplify the state judgment process, since
     * in this game, most of actions are merely related with the number of disks
     * in a column such as determining whether a column is filled and whether
     * the game board is filled.
     * </p>
     */
    protected final int[] columnDiskCount;
    /**
     * two-dimensional array of integers which stores current state of the game.
     * <p>
     * This integers in this field will be initialized to unique negative integer
     * to satisfied the requirement of ruled in
     * {@link PatternRecognition#isConsecutiveFour(int[][])}. When checking whether
     * a specific position on game board is null or has a disk in {@link #RED} or
     * {@link #YELLOW}, use {@link #isNullPosition(int)}.
     * </p>
     */
    protected final int[][] gameBoard;

    /**
     * Initial game board ({@link #gameBoard}) and column disk count
     * ({@link #columnDiskCount}).
     */
    public ConnectFour() {

        gameBoard = new int[ROW][COLUMN];
        // Initial column disk count to all zero
        columnDiskCount = new int[COLUMN];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                gameBoard[i][j] = -(i * ROW + j);
            }
        }
    }

    /**
     * Print the current state of game to {@link System#out}.
     *
     * <p>
     * In this method, the {@link #RED} is represented as character {@code 'R'}
     * , the {@link #YELLOW} is represented as character {@code 'Y'} and the
     * null position is represented as character {@code ' '}.
     * There will be an horizontal line at the bottom of the output, which is
     * consisted of character {@code '-'}.
     * </p>
     */
    public void printGame() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                // Column separator
                System.out.print('|');
                switch (gameBoard[i][j]) {
                    case RED:
                        // Red disk
                        System.out.print('R');
                        break;
                    case YELLOW:
                        // Yellow disk
                        System.out.print('Y');
                        break;
                    default:
                        // Other cases treats as null position
                        System.out.print(' ');
                        break;
                }
            }
            System.out.println('|');
        }
        // Bottom horizontal line
        System.out.println("----------------------");
    }

    /**
     * determine whether a column can add/drop more disks.
     *
     * @param column the column number to determine (indexed from 0)
     * @return true if the column has not be filled
     */
    public boolean canDropDisk(int column) {
        return columnDiskCount[column] < ROW;
    }

    /**
     * add/drop a disk with specific color to a column in {@link #gameBoard}
     *
     * <p>This method will automatically update the {@link #columnDiskCount}</p>
     * @param color the color of the disk to be added/dropped.
     * @param column the index of column to add/drop a disk.
     * @return the number of disks in the column after dropping this disk, which is
     *        also the index of the last disk counted form bottom.
     */
    public int dropDisk(int color, int column) {
        if (RED != color && YELLOW != color) return columnDiskCount[column];
        if (columnDiskCount[column] < ROW) {
            columnDiskCount[column]++;
            gameBoard[ROW - columnDiskCount[column]][column] = color;
        }
        return columnDiskCount[column];
    }

    /**
     * determine whether the game board is filled.
     * <p>
     * Since this game cannot guarantee a winner will be found before the game board is
     * fully filled with disks, check this state before dropping another disk instead of
     * only check for {@link #isFinish()}.
     * </p>
     * <p>
     * This method determine the state by checking the values stored in
     * {@link #columnDiskCount}, which means it depends on the consistency between
     * {@link #columnDiskCount} and {@link #gameBoard}.
     * </p>
     *
     * @return true if the game board is filled with disks
     */
    public boolean isFill() {
        for (int i = 0; i < COLUMN; i++) {
            if (columnDiskCount[i] < ROW) {
                return false;
            }
        }
        return true;
    }

    /**
     * determine whether the game is finished based on the {@link #gameBoard game board}.
     * This method use {@link PatternRecognition#isConsecutiveFour(int[][])} to determine
     * the result.
     * @return true if there has four consecutive disks in same color
     * @see PatternRecognition#isConsecutiveFour(int[][])
     */
    public boolean isFinish() {
        return PatternRecognition.isConsecutiveFour(gameBoard);
    }

    public boolean isNullPosition(int gameBoardState) {
        return gameBoardState <= 0;
    }

    /**
     * run the current game based on {@link System#in} and {@link System#out},
     * then execute the game logic.
     * <p>
     * The program prompts two players to drop a {@link #RED} or {@link #YELLOW}
     * disk alternately. Whenever a disk is dropped, the program re-displays the
     * board on the console and determines the status of the game
     * (win, draw, or continue).
     * </p>
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        int col;
        // RED player goes first
        int color = RED;

        // Main game logic loop
        while (true) {
            try {
                printGame();
                System.out.print(String.format("Drop a %s disk at column (0-6): ", getColorName(color)));
                // This may raise input error, handle by the wrapper try block
                col = scanner.nextInt();
                if (col < 0 || col >= COLUMN) {
                    System.out.println("Invalid column number! Please try again.");
                    continue;
                }

                if (!canDropDisk(col)) {
                    System.out.println("Selected column is filled! Please try again.");
                    continue;
                }

                dropDisk(color, col);

                if (isFinish()) {
                    // Someone won
                    System.out.println(String.format("The %s player won", getColorName(color)));
                    break;
                }

                if (isFill()) {
                    // Game board filled, no one won
                    System.out.println("Oops! Game board filled. You both failed");
                    break;
                }

                color = color == RED ? YELLOW : RED;
            } catch (InputMismatchException inputError) {
                // handle input error when user enters letters or other symbols
                System.out.println("Only integers are allowed! Please try again.");
            }
        }
    }

    /**
     * get the {@link String} representation of the color.
     *
     * @param color the color to be converted
     * @return the {@link String} representation of the color
     */
    public String getColorName(int color) {
        if (isNullPosition(color)) return "null";
        if (color == RED) return "red";
        if (color == YELLOW) return "yellow";
        return "";
    }

    /**
     * entry point for start an new game
     * @param args command line parameters
     */
    public static void main(String[] args) {
        ConnectFour game = new ConnectFour();
        game.start();
    }
}
