package seedu.learnvocabulary.model.word;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import seedu.learnvocabulary.logic.parser.exceptions.ParseException;

/**
 * Self-created Dictionary.com "API"
 */
public class Dictionary {
    public static final String MESSAGE_NO_INTERNET = "Please connect to the Internet.";
    public static final String MESSAGE_CANNOT_BE_EMPTY = "Please don't leave the word blank.";

    public static final String MESSAGE_INVALID_WORD = "Please ensure word is valid - "
            + "no illegal characters and numbers are allowed.";
    public static final String WORD_NOT_EXIST =
            "Word cannot be located online (does not exist) - try respelling it.";
    public static final String NO_WORD_OF_THE_DAY =
            "The word of the day is not available at this moment";

    private String args;
    private String wordToLearn;
    private String definition;
    private String wordOfTheDay;

    public Dictionary(String args) {
        this.args = args;
    }

    public String getWordToLearn() {
        return wordToLearn;
    }

    public String getDefinition() {
        return definition;
    }

    public String getWordOfTheDay() {
        return wordOfTheDay;
    }

    /**
     * Parses the wordOfTheDay document if Dictionary.com is available.
     */
    public Dictionary fetchWordOfTheDay() throws ParseException {
        if (!isConnectedToInternet()) {
            throw new ParseException(MESSAGE_NO_INTERNET);
        }
        //Get wordoftheday from document object.
        Document doc;
        if ((doc = doesWordOfTheDayExist()) == null) {
            throw new ParseException(NO_WORD_OF_THE_DAY);
        }
        wordOfTheDay = doc.select("meta[property=title]").get(0)
                .attr("content");
        wordOfTheDay = wordOfTheDay.replace("Get the Word of the Day - ", "");
        wordOfTheDay = wordOfTheDay.replace(" | Dictionary.com", "");
        args = wordOfTheDay;
        this.invoke();
        return this;
    }

    /**
     * Invoke the use of "learn", to parse definition and put it into a meaning object.
     */
    public Dictionary invoke() throws ParseException {
        wordToLearn = args.trim();

        if (wordToLearn.equals("")) {
            throw new ParseException(MESSAGE_CANNOT_BE_EMPTY);
        }

        wordToLearn = convertWord(wordToLearn);

        if (!isValidWord(wordToLearn)) {
            throw new ParseException(MESSAGE_INVALID_WORD);
        }

        if (!isConnectedToInternet()) {
            throw new ParseException(MESSAGE_NO_INTERNET);
        }

        Document doc;
        if ((doc = isWordInOnlineDictionary(wordToLearn)) == null) {
            throw new ParseException(WORD_NOT_EXIST);
        }
        //Get description from document object.
        definition = parseDefinition(doc);
        return this;
    }

    /**
     * Removes all of the redundant and unrelated items and only extract definition.
     */
    private String parseDefinition(Document doc) {
        definition = doc.select("meta[name=description]").get(0)
                .attr("content");
        definition = definition.substring(definition.indexOf(" ") + 1);
        definition = definition.replace("definition, ", "");
        definition = definition.replace("(", "");
        definition = definition.replace(")", "");
        definition = definition.replace(" See more.", "");
        return definition;
    }

    /**
     * Converts the word being learnt to a readable format.
     * Eg: 'hello' and 'hEllO' both becomes 'Hello'.
     */
    public static String convertWord(String wordToLearn) {
        wordToLearn = wordToLearn.toLowerCase();
        return wordToLearn.substring(0, 1).toUpperCase() + wordToLearn.substring(1);
    }

    /**
     * Checks for Internet Connection, if its available.
     */
    public static boolean isConnectedToInternet() {
        try {
            Jsoup.connect("https://www.dictionary.com/").get();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Checks if the word contains any invalid characters.
     */
    public static boolean isValidWord(String wordToLearn) {
        char[] chars = wordToLearn.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }
    /**
     * Checks for Internet Connection, if its available.
     */
    public static Document isWordInOnlineDictionary(String wordToLearn) {
        try {
            return Jsoup.connect("https://www.dictionary.com/browse/" + wordToLearn).get();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Pulls the wordoftheday page into a document if available.
     */
    public static Document doesWordOfTheDayExist() {
        try {
            return Jsoup.connect("https://www.dictionary.com/wordoftheday/").get();
        } catch (IOException e) {
            return null;
        }
    }
}


