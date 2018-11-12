package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import seedu.address.MainApp;

/**
 * Represents a Person's feedback number in SSENISUB.
 * Guarantees: immutable; is valid as declared in {@link #isValidFeedback(String)}
 */
public class Feedback {

    public static final Feedback DEFAULT_INITIAL_FEEDBACK = new Feedback("-NO FEEDBACK YET-");
    public static final String MESSAGE_CONSTRAINTS = "Feedback can take any values, and it should not be blank\n"
            + "Maximum Length: 65 characters";
    public static final String MESSAGE_PROFANITY_FOUND = "Feedback input rejected, because profanity found: ";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     *
     */
    public static final String VALIDATION_REGEX = "[^\\s].{0,64}";
    public final String value;


    /**
     * Constructs a {@code Feedback}.
     *
     * @param feedback A valid feedback.
     */
    public Feedback(String feedback) {
        requireNonNull(feedback);
        checkArgument(isValidFeedback(feedback), MESSAGE_CONSTRAINTS);
        checkArgument(hasNoProfanity(feedback), MESSAGE_PROFANITY_FOUND
                + new ProfanityFilter().findProfanity(feedback));
        value = feedback;
    }

    /**
     * Returns true if a given string is a valid feedback number.
     */
    public static boolean isValidFeedback(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is has no profanities.
     */
    public static boolean hasNoProfanity(String test) {
        return (new ProfanityFilter().findProfanity(test).size() == 0);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Feedback // instanceof handles nulls
                && value.equals(((Feedback) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


    //@@author PimDeWitte
    //Reused from https://gist.github.com/PimDeWitte/c04cc17bc5fa9d7e3aee6670d4105941 with minor modifications

    /**
     * Simple profanity filter written in Java for efficient comparison.
     * Runtime grows based on string input, not list size.
     */

    public static class ProfanityFilter {
        private static Map<String, String[]> words = new HashMap<>();
        private static int largestWordLength = 0;

        public ProfanityFilter() {
            loadConfigs();
        }

        /**
         *  Load bad words from txt file
         */
        public static void loadConfigs() {
            try {
                //Path badWordsFile = Paths.get("docs", "words to ban", "Bad_Words_List.txt");
                //BufferedReader reader = new BufferedReader(new FileReader(badWordsFile.toFile()));
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        MainApp.class.getResourceAsStream("/words to ban/Bad_Words_List.txt")));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] content = null;
                    try {
                        content = line.split(",");
                        if (content.length == 0) {
                            continue;
                        }
                        String word = content[0];
                        String[] ignoreInCombinationWithWords = new String[]{};
                        if (content.length > 1) {
                            ignoreInCombinationWithWords = content[1].split("_");
                        }

                        if (word.length() > largestWordLength) {
                            largestWordLength = word.length();
                        }
                        words.put(word.replaceAll(" ", ""), ignoreInCombinationWithWords);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        /**
         * Iterates over a String input and checks whether a cuss word was found in a list,
         * then checks if the word should be ignored (e.g. bass contains the word *ss).
         * @param rawInput
         * @return list of bad words found
         */
        public ArrayList<String> badWordsFound(String rawInput) {
            String input = rawInput;

            // remove leetspeak
            input = input.replaceAll("1", "i");
            input = input.replaceAll("!", "i");
            input = input.replaceAll("3", "e");
            input = input.replaceAll("4", "a");
            input = input.replaceAll("@", "a");
            input = input.replaceAll("5", "s");
            input = input.replaceAll("7", "t");
            input = input.replaceAll("0", "o");
            input = input.replaceAll("9", "g");

            ArrayList<String> badWords = new ArrayList<>();
            input = input.toLowerCase().replaceAll("[^a-zA-Z]", "");

            // Iterate over each letter in the word
            for (int start = 0; start < input.length(); start++) {
                // From each letter, keep going to find bad words until either the end of the sentence is reached,
                // or the max word length is reached.
                for (int offset = 1; offset < (input.length() + 1 - start) && offset < largestWordLength; offset++) {
                    String wordToCheck = input.substring(start, start + offset);
                    // Hard coded because "ass" itself is a profanity but contained in many clean words
                    if ("ass".equals(wordToCheck)) {
                        badWords.add(wordToCheck);
                    }

                    if (words.containsKey(wordToCheck)) {
                        // for example, if you want to say the word bass, that should be possible.
                        String[] ignoreCheck = words.get(wordToCheck);
                        boolean ignore = false;
                        for (int s = 0; s < ignoreCheck.length; s++) {
                            if (input.contains(ignoreCheck[s])) {
                                ignore = true;
                                break;
                            }
                        }
                        if (!ignore) {
                            badWords.add(wordToCheck);
                        }
                    }
                }
            }

            return badWords;

        }

        /**
         * Looks for bad words and returns the list of them
         * @param input
         * @return list of bad words
         */
        public ArrayList<String> findProfanity(String input) {
            ArrayList<String> badWords = badWordsFound(input);
            return badWords;
        }
    }
    //@@author

}
