package seedu.learnvocabulary.model.word;

import java.io.IOException;
//import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import seedu.learnvocabulary.logic.parser.exceptions.ParseException;

/**
 * Self-created Dictionary.com "API"
 */
public class Dictionary {
    public static final String MESSAGE_NO_INTERNET = "Please connect to the Internet to learn about words.";
    public static final String WORD_NOT_EXIST =
            "Word cannot be located online (does not exist) - try respelling it.";
    //private ArrayList<Tag> tagArrayList;
    private String args;
    private String wordToLearn;
    private String definition;

    public Dictionary(String args) {
        this.args = args;
    }

    public String getWordToLearn() {
        return wordToLearn;
    }

    public String getDefinition() {
        return definition;
    }

    /**
     * Invoke the use of "learn", to parse definition and put it into a meaning object.
     */
    public Dictionary invoke() throws ParseException {
        wordToLearn = args;
        if (!isConnectedToInternet()) {
            throw new ParseException(MESSAGE_NO_INTERNET);
        }

        Document doc;
        if ((doc = isWordInOnlineDictionary(wordToLearn)) == null) {
            throw new ParseException(WORD_NOT_EXIST);
        }
        //Get description from document object.
        definition = doc.select("meta[name=description]").get(0)
                .attr("content");
        definition = definition.substring(definition.indexOf(" ") + 1);
        definition = definition.replace("definition, ", "");
        definition = definition.replace("(", "");
        definition = definition.replace(")", "");
        definition = definition.replace(" See more.", "");
        System.out.println(definition);
        return this;
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
     * Checks for Internet Connection, if its available.
     */
    public static Document isWordInOnlineDictionary(String wordToLearn) {
        try {
            return Jsoup.connect("https://www.dictionary.com/browse/" + wordToLearn).get();
        } catch (IOException e) {
            return null;
        }
    }
    //
    //public ArrayList<Tag> getTagList() {
    //    Tag tag = new Tag("toLearn");
    //    tagArrayList.add(tag);
    //    return tagArrayList;
    //}
    //
    ///**
    // * Converts the tag array list into suitable string array list for utilization.
    // */
    //public ArrayList<String> convertStringList(ArrayList<Tag> tagArrayList){
    //    ArrayList<String> stringArrayList = new ArrayList<>();
    //    for (Tag tag: tagArrayList){
    //        stringArrayList.add(tag.tagName);
    //    }
    //    return stringArrayList;
    //}
}
