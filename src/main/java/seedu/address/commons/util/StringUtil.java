package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    //@@author LZYAndy
    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, and allow fuzzy matching.
     *   <br>examples:<pre>
     *       containsWordIgnoreCaseAndFuzzyName("ABc def", "abc") == true
     *       containsWordIgnoreCaseAndFuzzyName("ABc def", "DEF") == true
     *       containsWordIgnoreCaseAndFuzzyName("ABc def", "AB") == true
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCaseAndFuzzyName(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim().toLowerCase();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence.toLowerCase();
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(w -> w.length() < 4 ? preppedWord.equals(w) : calculate(preppedWord, w) < 3);
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, and allow fuzzy matching.
     *   <br>examples:<pre>
     *       containsWordIgnoreCaseAndFuzzyEmail("ABc def", "abc") == true
     *       containsWordIgnoreCaseAndFuzzyEmail("ABc def", "DEF") == true
     *       containsWordIgnoreCaseAndFuzzyEmail("ABc def", "AB") == true
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCaseAndFuzzyEmail(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim().toLowerCase();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence.toLowerCase();
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(w -> calculate(preppedWord, w) < 4);
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, and allow fuzzy matching.
     *   <br>examples:<pre>
     *       containsWordIgnoreCaseAndFuzzyPhone("12345678", "123456") == true
     *       containsWordIgnoreCaseAndFuzzyPhone("12345678", "123467") == true
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCaseAndFuzzyPhone(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(w -> calculate(preppedWord, w) < 3);
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCaseAndFuzzyAddress("ABc def", "abc") == true
     *       containsWordIgnoreCaseAndFuzzyAddress("ABc def", "DEF") == true
     *       containsWordIgnoreCaseAndFuzzyAddress("ABc def", "AB") == false //length too short, must be totally match
     *       containsWordIgnoreCaseAndFuzzyAddress("ABCdefg sdf", "AcDfg") == true
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCaseAndFuzzyAddress(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim().toLowerCase();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence.toLowerCase();
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(w -> containsDigit(w) || w.length() < 4 ? preppedWord.equals(w)
                        : w.length() < 8 ? calculate(preppedWord, w) < 3 : calculate(preppedWord, w) < 4);
    }

    /**
     * Check whether a string contains digit.
     * @param s String
     * @return Boolean
     */
    public static final boolean containsDigit(String s) {
        boolean containsDigit = false;

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }

        return containsDigit;
    }

    /**
     * A function used to support fuzzy matching
     * @param x the first string
     * @param y the second string
     * @return Levenshtein Distance
     */
    public static int calculate(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                                    + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[x.length()][y.length()];
    }

    /**
     * Get cost of substitution.
     * @param a char
     * @param b char
     * @return integer
     */
    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    /**
     * Get min.
     * @param numbers integer
     * @return integer
     */
    public static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }
    //@@author LZYAndy

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
