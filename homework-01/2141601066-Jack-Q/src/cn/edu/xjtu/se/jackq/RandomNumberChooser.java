package cn.edu.xjtu.se.jackq;

import java.util.Arrays;

/**
 *
 * Provide static methods as utility to generate random number
 *
 * <p>
 * This class only contains static methods and constants,
 * don't instantiate this class.
 * </p>
 * @author Jack Q (qiaobo@outlook.com)
 */
public class RandomNumberChooser {
    public static final int ERROR_FLAG = -1;

    public static final int MIN_RANDOM_VAL = 1;
    public static final int MAX_RANDOM_VAL = 54;

    /** Null implementation of default constructor to avoid instantiation of this class */
    private RandomNumberChooser(){}

    /**
     * Generate a random number between {@value #MAX_RANDOM_VAL} and {@value #MIN_RANDOM_VAL}
     * excluding the values passed in.
     *
     * <p>
     * This method use {@link Math#random()} to generate random number. for the randomness and
     * other constrains notes, refer to {@link Math#random()} and {@link java.util.Random}.
     * </p>
     * @param numbers varargs of number which will not be included in random returns.
     *                If the values have duplications, the will be regarded as one value.
     *                If a value doesn't belong to the range, it will be ignored.
     * @return a random number between {@value #MIN_RANDOM_VAL}
     *         and {@value #MAX_RANDOM_VAL} or {@value #ERROR_FLAG} if there is
     *         no possible random value to generate
     * @see Math#random()
     * @see java.util.Random
     */
    public static int getRandom(int... numbers) {
        // if the number of exceptions is large enough, generate an array
        // contains the possible values
        if (numbers.length > (MAX_RANDOM_VAL - MIN_RANDOM_VAL) / 2) {
            int[] leftNumbers = new int[MAX_RANDOM_VAL];
            int len = 0;

            Arrays.sort(numbers);

            for (int i = MIN_RANDOM_VAL, j = 0; i <= MAX_RANDOM_VAL; i++) {
                while (j < numbers.length && numbers[j] < i) j++;
                if (i != numbers[j]) {
                    leftNumbers[len++] = i;
                }
            }

            if (len > 0) {
                return leftNumbers[(int) (Math.random() * len)];
            } else {
                return ERROR_FLAG;
            }
        }

        // if the number of exceptions is in a acceptable size,
        // try it in a infinite loop to get the random value
        tryRandomLoop:
        while (true) {
            int rnd = (int) (Math.random() * MAX_RANDOM_VAL + MIN_RANDOM_VAL);
            for (int num : numbers) {
                if (rnd == num) {
                    // Continue the outer loop to start another test
                    continue tryRandomLoop;
                }
            }
            return rnd;
        }
    }

    /**
     * Some test cases for the method {@link RandomNumberChooser#getRandom(int...)}
     * @param args command line parameters
     */
    public static void main(String[] args) {
        // Without exception which will generate a argument array with no element
        System.out.println("Generate a random number");
        System.out.println(getRandom());

        // With exceptions
        System.out.println("Generate a random number except for 1 to 10");
        System.out.println(getRandom(new int[]{
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        }));

        // If user try to make all of integers in range as exception,
        // the function will return -1 as a flag of error
        System.out.println("Generate -1 when no possible number:");
        System.out.println(getRandom(new int[]{
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
                31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
                41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
                51, 52, 53, 54
        }));

    }
}
