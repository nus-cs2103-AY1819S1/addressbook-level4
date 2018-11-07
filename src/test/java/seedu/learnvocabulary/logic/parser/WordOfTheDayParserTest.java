package seedu.learnvocabulary.logic.parser;

import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.junit.Test;

import seedu.learnvocabulary.commons.core.Messages;
import seedu.learnvocabulary.logic.commands.WordOfTheDayCommand;
import seedu.learnvocabulary.logic.parser.exceptions.ParseException;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.Dictionary;
import seedu.learnvocabulary.model.word.Meaning;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.model.word.Word;

public class WordOfTheDayParserTest {
    private WordOfTheDayParser parser = new WordOfTheDayParser();

    @Test
    public void parse_success() {
        String empty = "";
        Dictionary dictionary;
        Word word = null;
        try {
            dictionary = new Dictionary(empty).fetchWordOfTheDay();
            String wordOfTheDay = dictionary.getWordOfTheDay();
            String definition = dictionary.getDefinition();
            Tag defaultTag = new Tag("wordOfTheDay");
            ArrayList<String> stringArrayList = new ArrayList<>(Collections.singleton(defaultTag.tagName));

            Name name = ParserUtil.parseName(wordOfTheDay);
            Meaning meaning = ParserUtil.parseMeaning(definition);
            Set<Tag> tagList = ParserUtil.parseTags(stringArrayList);
            word = new Word(name, meaning, tagList);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertParseSuccess(parser, empty, new WordOfTheDayCommand(word));
    }
}
