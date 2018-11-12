package seedu.saveit.ui;

import static seedu.saveit.model.util.SampleDataUtil.isCommandWordNeedIndex;
import static seedu.saveit.model.util.SampleDataUtil.isPrefixParameter;

import org.fxmisc.richtext.InlineCssTextArea;

/**
 * Manager of command highlighter
 */
public class CommandHighlightManager {

    public static final String STYLE_COMMAND_WORD = "-fx-fill: #e2a03d;";
    public static final String STYLE_PARAMETER_KEY = "-fx-fill: #ffff00;";
    public static final String STYLE_INDEX = "-fx-fill: #55ae47;";
    public static final String STYLE_NORMAL_VALUE = "-fx-fill: #42c3f4;";


    /**
     * highlight user input in different colors.
     */
    public static void highlight(InlineCssTextArea commandTextField) {
        String userInput = commandTextField.getText();
        StringBuilder commandWord = new StringBuilder();
        int position = 0;

        // if there are space chars before command word, pos++
        while (isShorterThanInput(userInput, position) && isSpace(userInput, position)) {
            position++;
        }

        // highlight command word
        while (isShorterThanInput(userInput, position) && !isSpace(userInput, position)) {
            commandWord.append(userInput.charAt(position));
            commandTextField.setStyle(position, position + 1, STYLE_COMMAND_WORD);
            position++;
        }

        highlightCommandKeyValue(commandTextField, userInput, commandWord, position);

    }

    /**
     * This is to highlight the command index, parameters and values
     */
    private static void highlightCommandKeyValue(InlineCssTextArea commandTextField, String userInput,
        StringBuilder commandWord, int position) {

        boolean indexHighlighted = false;
        boolean indexNeedToHighlight;
        // some command words do not require the index, to avoid confusing, the index will not be highlighted
        indexNeedToHighlight = checkCommandWord(commandWord);

        // highlight the following parameters, which are key-value pairs
        while (isShorterThanInput(userInput, position)) {
            // highlight index
            while (indexNeedToHighlight && !indexHighlighted && isIndex(userInput, position) && !isSpace(userInput,
                position)) {
                commandTextField.setStyle(position, position + 1, STYLE_INDEX);
                position++;
            }

            // highlight parameters
            if (isShorterThanInput(userInput, position) && isParameter(userInput, position)) {
                commandTextField.setStyle(position - 1, position + 1, STYLE_PARAMETER_KEY);
                position++;
                indexHighlighted = true;
            }

            if (!isShorterThanInput(userInput, position)) {
                break;
            }

            commandTextField.setStyle(position, position + 1, STYLE_NORMAL_VALUE);
            position++;
        }
    }

    /**
     * check if the commandWord requires index, return true if yes, otherwise false
     */
    private static boolean checkCommandWord(StringBuilder userInput) {
        return isCommandWordNeedIndex(userInput.toString());
    }

    /**
     * check if the this position character is space, return true if yes, otherwise false
     */
    private static boolean isSpace(String userInput, int position) {
        return userInput.charAt(position) == ' ';
    }

    /**
     * check if the position is within the userInput length, return true if yes, otherwise false
     */
    private static boolean isShorterThanInput(String userInput, int position) {
        return position < userInput.length();
    }

    /**
     * check if the parameter is index
     */
    private static boolean isIndex(String userInput, int position) {
        return isShorterThanInput(userInput, position) && Character.isDigit(userInput.charAt(position));
    }

    /**
     * check if the character is parameter
     * @return true if parameter, otherwise false
     */
    private static boolean isParameter(String userInput, int position) {
        if (userInput.charAt(position) == '/' && userInput.charAt(position - 2) == ' ') {
            StringBuilder input = new StringBuilder();
            input.append(userInput.charAt(position - 1));
            input.append(userInput.charAt(position));
            String inputCheck = input.toString();
            if (isPrefixParameter(inputCheck)) {
                return true;
            }
        }
        return false;
    }
}
