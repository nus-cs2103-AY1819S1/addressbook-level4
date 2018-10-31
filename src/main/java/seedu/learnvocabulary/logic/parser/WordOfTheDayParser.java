package seedu.learnvocabulary.logic.parser;

import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_OVERALL_ERROR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import seedu.learnvocabulary.logic.commands.WordOfTheDayCommand;
import seedu.learnvocabulary.logic.parser.exceptions.ParseException;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.Dictionary;
import seedu.learnvocabulary.model.word.Meaning;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.model.word.Word;

/**
 * Parses arguments fetched from Dictionary.com and creates a new WordOfTheDay object.
 */
public class WordOfTheDayParser implements Parser<WordOfTheDayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LearnCommand
     * and returns an LearnCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WordOfTheDayCommand parse(String args) throws ParseException {
        Word word;
        try {
            Dictionary dictionary = new Dictionary("").fetchWordOfTheDay();
            String wordOfTheDay = dictionary.getWordOfTheDay();
            String definition = dictionary.getDefinition();
            Tag defaultTag = new Tag("WordOfTheDay");
            ArrayList<String> stringArrayList = new ArrayList<>(Collections.singleton(defaultTag.tagName));

            Name name = ParserUtil.parseName(wordOfTheDay);
            Meaning meaning = ParserUtil.parseMeaning(definition);
            Set<Tag> tagList = ParserUtil.parseTags(stringArrayList);
            word = new Word(name, meaning, tagList);
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_OVERALL_ERROR);
        }
        return new WordOfTheDayCommand(word);
    }
}
