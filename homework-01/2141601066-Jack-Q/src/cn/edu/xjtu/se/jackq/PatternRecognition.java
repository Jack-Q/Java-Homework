package cn.edu.xjtu.se.jackq;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Provide static methods as utility to check some pattern in an array
 *
 * <p>
 * This class only contains static methods and constants,
 * don't instantiate this class.
 * </p>
 *
 * @author Jack Q (qiaobo@outlook.com)
 */
public class PatternRecognition {

    /** Null implementation of default constructor to avoid instantiation of this class */
    private PatternRecognition(){}

    /**
     * test whether a two-dimensional array has four consecutive numbers
     * of the same value, either horizontally, vertically, or diagonally.
     *
     * <p>
     * For the two-dimensional array, it must has the same number of columns in each
     * row. Otherwise, this program may raise {@link ArrayIndexOutOfBoundsException}
     * </p>
     * @param values a 2D array of integers
     * @return true if there are four consecutive numbers of the same value
     */
    public static boolean isConsecutiveFour(int[][] values) {
        int row = values.length;
        // assuming each row in values has the same number of columns
        // and use the value of first row as its value
        int column = values[0].length;

        // iterate every element in this 2D array
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                // check vertically
                if (i <= row - 4)
                    if (values[i][j] == values[i + 1][j] &&
                            values[i][j] == values[i + 2][j] &&
                            values[i][j] == values[i + 3][j])
                        return true;
                // check horizontally
                if (j <= column - 4)
                    if (values[i][j] == values[i][j + 1] &&
                            values[i][j] == values[i][j + 2] &&
                            values[i][j] == values[i][j + 3])
                        return true;
                // check diagonally towards right bottom
                if (i <= row - 4 && j <= column - 4)
                    if (values[i][j] == values[i + 1][j + 1] &&
                            values[i][j] == values[i + 2][j + 2] &&
                            values[i][j] == values[i + 3][j + 3])
                        return true;
                // check diagonally towards right top
                if (i >= 3 && j <= column - 4)
                    if (values[i][j] == values[i - 1][j + 1] &&
                            values[i][j] == values[i - 2][j + 2] &&
                            values[i][j] == values[i - 3][j + 3])
                        return true;
            }
        }
        return false;
    }

    /**
     * provide some test cases for {@link #isConsecutiveFour(int[][])}
     */
    public static void autoTest() {
        System.out.println("Check vertical consecutive: ");
        System.out.println(isConsecutiveFour(new int[][]{
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5},
        }));
        System.out.println("Check horizontal consecutive: ");
        System.out.println(isConsecutiveFour(new int[][]{
                {1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3},
                {4, 4, 4, 4, 4}
        }));
        System.out.println("Check diagonally consecutive: 1");
        System.out.println(isConsecutiveFour(new int[][]{
                {1, 2, 3, 4, 5},
                {2, 3, 4, 5, 1},
                {3, 4, 5, 1, 2},
                {4, 5, 1, 2, 3}
        }));
        System.out.println("Check diagonally consecutive: 2 ");
        System.out.println(isConsecutiveFour(new int[][]{
                {4, 5, 1, 2, 3},
                {3, 4, 5, 1, 2},
                {2, 3, 4, 5, 1},
                {1, 2, 3, 4, 5}
        }));
        System.out.println("Check no consecutive: ");
        System.out.println(isConsecutiveFour(new int[][]{
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 0},
                {0, 1, 2, 3, 4},
                {5, 6, 7, 8, 9}
        }));
    }

    /**
     * a test program that prompts the user to enter the number of rows
     * and columns of a two-dimensional array and then the values in
     * the array and displays {@code true} if the array contains four
     * consecutive numbers with the same value. This is the entry
     * point of this program.
     *
     * @param args arguments passed to this program.<br>
     *             switch {@code -a}: use existed data to test the program
     */
    public static void main(String[] args) {
        // check command line parameter
        if (args.length > 0 && args[0].contains("-a")) {
            // Use '-a' parameter to launch preset test cases
            autoTest();
            return;
        }
        // prompt user to enter an 2d array for test
        int rows, columns;
        Scanner scanner = new Scanner(System.in);
        try{
            System.out.println("Please enter the number of rows and columns:");

            System.out.print("Rows: ");
            rows = scanner.nextInt();
            System.out.print("Columns: ");
            columns = scanner.nextInt();

            int[][] testArray = new int[rows][columns];

            System.out.println("Please input these numbers in every row and column: ");
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    testArray[row][column] = scanner.nextInt();
                }
            }

            boolean result = isConsecutiveFour(testArray);
            System.out.println("Result: " + result);
            System.out.println(result ?
                    "The array contains four consecutive numbers with the same value." :
                    "The array doesn't contain four consecutive numbers with the same value.");
        }catch (InputMismatchException inputError){
            System.out.println("Please input integers!\n" +
                    "Restart the program and try again.");
        }catch (ArrayIndexOutOfBoundsException arrayError){
            System.out.println("Please input a positive integer as the number of rows and columns!\n" +
                    "Restart the program and try again.");
        }

    }
}
