package seedu.learnvocabulary.logic.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import seedu.learnvocabulary.logic.commands.LearnCommand;
import seedu.learnvocabulary.logic.parser.exceptions.ParseException;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.Dictionary;
import seedu.learnvocabulary.model.word.Meaning;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.model.word.Word;



/**
 * Parses input arguments and creates a new LearnCommand object
 */
public class LearnCommandParser implements Parser<LearnCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LearnCommand
     * and returns an LearnCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LearnCommand parse(String args) throws ParseException, IOException {
        Dictionary dictionary = new Dictionary(args).invoke();
        String wordToLearn = dictionary.getWordToLearn();
        String definition = dictionary.getDefinition();
        ArrayList<String> stringArrayList = new ArrayList<>();
        //ArrayList<Tag> tagArrayList = dictionary.getTagList();
        //ArrayList<String> defaultTagList = dictionary.convertStringList(tagArrayList);

        Name name = ParserUtil.parseName(wordToLearn);
        Meaning meaning = ParserUtil.parseMeaning(definition);
        Set<Tag> tagList = ParserUtil.parseTags(stringArrayList);

        Word word = new Word(name, meaning, tagList);

        return new LearnCommand(word);
    }
}
