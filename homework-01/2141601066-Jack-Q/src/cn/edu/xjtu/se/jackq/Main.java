package cn.edu.xjtu.se.jackq;

import java.lang.reflect.Method;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * A convenient class expose entry point for other
 * classes in this package for homework one.
 *
 * <p>
 *     Task listed in this class including:
 *     <ol>
 *         <li> {@link RandomNumberChooser Random Number Chooser} </li>
 *         <li> {@link PatternRecognition Pattern Recognition} </li>
 *         <li> {@link MyStringBuilder1 String Builder (MyStringBuilder1)} </li>
 *         <li> {@link ConnectFour Game: Connect Four} </li>
 *         <li> [EXT] {@link ConnectFourGUI GUI Game: Connect Four} </li>
 *     </ol>
 *
 * @author Jack Q (qiaobo@outlook.com)
 * @see RandomNumberChooser
 * @see PatternRecognition
 * @see MyStringBuilder1
 * @see ConnectFour
 * @see ConnectFourGUI
 */
public class Main {
    /**
     * task list in homework one
     * <p>
     *     Task listed in this class including:
     *     <ol>
     *         <li>{@link RandomNumberChooser Random Number Chooser}</li>
     *         <li>{@link PatternRecognition Pattern Recognition}</li>
     *         <li>{@link MyStringBuilder1 String Builder (MyStringBuilder1)}</li>
     *         <li>{@link ConnectFour Game: Connect Four}</li>
     *         <li>[EXT] {@link ConnectFourGUI GUI Game: Connect Four}</li>
     *     </ol>
     */
    public static final String[] taskList = {
            "RandomNumberChooser",
            "PatternRecognition",
            "MyStringBuilder1",
            "ConnectFour",
            "ConnectFourGUI"
    };

    /**
     * Entry point of Homework 1
     * @param args Command line argument
     */
    public static void main(String[] args) {
        out.println("Select Task Number:");
        for (int i = 0; i < taskList.length; i++) {
            out.println("[" + (i + 1) + "] " + taskList[i]);
        }
        Scanner scanner = new Scanner(System.in);
        try {
            int selection = scanner.nextInt() - 1;
            if (selection < 0 || selection >= taskList.length) {
                throw new Exception("Wrong index number");
            }
            // Invoke main function
            Method main = Class.forName(Main.class.getPackage().getName() + "." + taskList[selection])
                    .getMethod("main", String[].class);
            out.println("Invoking Main Method");
            // Cast the array of string to object to prevent
            // explanation as a non-varargs call
            main.invoke(null, (Object) new String[]{});
        } catch (NoSuchMethodException e) {
            out.println("The class you've selected doesn't have a main method");
        } catch (ClassNotFoundException e) {
            out.println("The class you've selected doesn't exist");
        } catch (Exception e) {
            out.println("Error:" + e.toString());
        }

    }
}
