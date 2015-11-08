package cn.edu.xjtu.se.jackq;

/**
 * A personal implementation of {@link StringBuilder} but it is
 * a simplified version of {@link java.lang.StringBuilder}.
 * This implementation also implemented the {@link CharSequence}
 * interface.
 * <p>
 * Comparing to {@link java.lang.String}, a {@code StringBuilder}
 * provides more flexibility on modification to the character sequence.
 * Here in this implementation, the flexibility is based on an
 * character array.
 * </p>
 * <p>
 * As for the capacity consideration during this implementation,
 * Firstly, choose a generally acceptable capacity which is enough for
 * most common scenarios and here is 80. When the character array isn't
 * capable to contain all of characters, it will generate a new array
 * with another 80-character capacity.
 * </p>
 *
 * @author Jack Q (qiaobo@outlook.com)
 * @see java.lang.StringBuilder
 * @see java.lang.CharSequence
 */
public class MyStringBuilder1 implements CharSequence {
    /**
     * Default capacity of the internal character array
     *
     * <p>
     * This value is used to initial objects. Besides, this
     * value is also minimum capacity expanded during expansion.
     * </p>
     *
     * <p>
     * See the use of this field in {@link #ensureCapacity(int)}
     * </p>
     */
    private static final int MINIMUM_CAPACITY = 80;

    /**
     * Current character count in buffer
     */
    private int characterLength;

    /**
     * Character buffer which stores tha data
     */
    private char[] characters;

    /**
     * Construct a new {@code MyStringBuilder1} with no content.
     */
    public MyStringBuilder1() {
        this.characters = new char[MINIMUM_CAPACITY];
        this.characterLength = 0;
    }

    /**
     * Construct a new {@code MyStringBuilder1} with the string
     * as its initial content
     * @param s the {@link String} to initialize the
     *          new {@code MyStringBuilder1}
     */
    public MyStringBuilder1(String s) {
        // Initialize with a String value can be implemented
        // as initialize an null container and then copy content
        // from argument s
        this();
        this.append(s);
    }

    /**
     * Construct a new {@code MyStringBuilder1} from an exist one
     * @param src another {@link MyStringBuilder1} to initialize the
     *            new {@code MyStringBuilder1}
     * @param begin the start index of the character in that buffer.
     *              The {@code begin}-th character is included
     * @param end the end index of the character in that buffer.
     *            The {@code end}-th character in excluded
     */
    private MyStringBuilder1(MyStringBuilder1 src, int begin, int end) {
        this();
        this.append(src, begin, end);
    }

    /**
     * Ensure the code after invoking current method will have access to
     * the character buffer which has at least as large capacity as the
     * required value passed via parameter.
     *
     * @param spaceDemand the minimum requirement of capacity of
     *                    character buffer
     */
    private void ensureCapacity(int spaceDemand) {
        // Check and ensure the buffer is large enough to store the data
        if(spaceDemand > characters.length){
            // Expand the char string
            // Here use some extra space for some case in which an operation
            //  on data append is followed by some small modification
            int newSize = Math.max(characters.length + MINIMUM_CAPACITY,
                    spaceDemand + MINIMUM_CAPACITY / 2);
            char[] newCharacters = new char[newSize];
            System.arraycopy(characters, 0, newCharacters, 0, this.length());
            // Replace the exist buffer with an larger buffer
            characters = newCharacters;
        }
    }

    /**
     * Append characters to current buffer form a class which
     * implemented the {@link CharSequence} interface.
     *
     * Some commonly used classes have already implemented this
     * interface including {@link String}, {@link StringBuilder},
     * {@link StringBuffer}, etc
     *
     * @param s the source of character from which characters will
     *          be appended to current buffer
     * @param begin the start index of the character in that buffer.
     *              The {@code begin}-th character is included
     * @param end the end index of the character in that buffer.
     *            The {@code end}-th character in excluded
     * @return a reference to current object
     * @throws NegativeArraySizeException if the {@code end} argument
     *         isn't larger than {@code begin}
     */
    public MyStringBuilder1 append(CharSequence s, int begin, int end) {
        // Raise exception indicating the error of argument
        if(begin > end){
            throw new NegativeArraySizeException();
        }
        int spaceDemand = this.length() + end - begin;
        ensureCapacity(spaceDemand);

        // Append char sequence
        for(int i = begin; i < end; i++ ){
            this.characters[characterLength] = s.charAt(i);
            characterLength++;
        }
        return this;
    }


    /**
     * Append characters to current buffer form another
     * {@link MyStringBuilder1}
     *
     * @param s the source of character from which characters will
     *          be appended to current buffer
     * @return a reference to current object
     */
    public MyStringBuilder1 append(MyStringBuilder1 s) {
        this.append(s, 0, s.length());
        return this;
    }

    /**
     * Append characters to current buffer form the decimal string
     * representation of the integer passed in
     *
     * @param i the value of an integer which decimal form will
     *          be appended
     * @return a reference to current object
     */
    public MyStringBuilder1 append(int i){
        return this.append(Integer.toString(i));
    }

    /**
     * Append a string to the end of current buffer
     *
     * @param strToAppend the source of character from which
     *                    characters will be appended to current
     *                    buffer
     * @return a reference to current object
     */
    public MyStringBuilder1 append(String strToAppend) {
        this.append(strToAppend,0,strToAppend.length());
        return this;
    }

    /**
     * @return the number of characters in current buffer
     */
    public int length() {
        return characterLength;
    }

    /**
     * @param index the index of character in buffer
     * @return the {@code index}-th character in buffer
     * @throws StringIndexOutOfBoundsException if the index passed
     *         in isn't a valid index which is an positive integer
     *         or zero which is less than the length of buffer content
     */
    public char charAt(int index) {
        if (index < 0 || index > characterLength) {
            throw new StringIndexOutOfBoundsException("Char index " + index + "is out of bound");
        }
        return characters[index];
    }

    /**
     * Convert the all of characters in current buffer to lower
     * case letter.
     * @return a reference to current object
     */
    public MyStringBuilder1 toLowerCase() {
        for(int i = 0; i < characterLength; i++){
            characters[i] = Character.toLowerCase(characters[i]);
        }
        return this;
    }

    /**
     * Get the substring of current buffer in the form of
     * {@link MyStringBuilder1}.
     * This method is merely used to satisfied the {@link CharSequence}
     * interface.
     * @param start the start index of the character in current buffer.
     *              The {@code begin}-th character is included
     * @param end the end index of the character in current buffer.
     *            The {@code end}-th character in excluded
     * @return a reference to current object
     */
    @Override
    public MyStringBuilder1 subSequence(int start, int end) {
        return this.substring(start, end);
    }


    /**
     * Get the substring of current buffer in the form of
     * {@link MyStringBuilder1}
     * @param begin the start index of the character in current buffer.
     *              The {@code begin}-th character is included
     * @param end the end index of the character in current buffer.
     *            The {@code end}-th character in excluded
     * @return a new {@link MyStringBuilder1} contains a substring
     *         of current one
     */
    public MyStringBuilder1 substring(int begin, int end) {
        return new MyStringBuilder1(this, begin, end);
    }

    /**
     * Get {@link String} from current buffer
     * @return the string form of this buffer
     */
    @SuppressWarnings("NullableProblems") // Suppress warning in IDE
    @Override
    public String toString() {
        return new String(characters, 0, characterLength);
    }

    /**
     * Entry point which provides some test cases of the
     * string builder
     * @param args command line parameters
     */
    public static void main(String[] args) {
        // Basic operation test
        MyStringBuilder1 stringBuilder1 = new MyStringBuilder1("Test string builder");
        MyStringBuilder1 stringBuilder2 = new MyStringBuilder1("ANOTHER Test string builder");
        System.out.println("Initial: " + stringBuilder1);
        System.out.println("Another: " + stringBuilder2);
        System.out.println("Another Lower: " + stringBuilder2.toLowerCase());
        System.out.println("Append: " + stringBuilder1.append(stringBuilder2));
        System.out.println("Append Number: " + stringBuilder1.append(2015));
        System.out.println("Get length: " + stringBuilder1.length());
        System.out.println("Get sub-string: " + stringBuilder1.substring(1, 10));
        // Extremely long string operation
        MyStringBuilder1 longStr = new MyStringBuilder1();
        StringBuilder longStrCmp = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            String s = Double.toString(Math.random()*1e30);
            longStr.append(s);
            longStrCmp.append(s);
        }
        System.out.println("Large string (compare result with jdk implementation):"
                + longStr.length() + ":" + longStrCmp.length()
                + longStr.toString().equals(longStrCmp.toString()));

    }
}
