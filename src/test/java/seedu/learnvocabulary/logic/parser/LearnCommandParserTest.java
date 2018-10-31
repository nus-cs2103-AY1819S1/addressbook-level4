package seedu.learnvocabulary.logic.parser;

import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.junit.Test;

import seedu.learnvocabulary.commons.core.Messages;
import seedu.learnvocabulary.logic.commands.LearnCommand;
import seedu.learnvocabulary.logic.parser.exceptions.ParseException;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.Dictionary;
import seedu.learnvocabulary.model.word.Meaning;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.model.word.Word;


public class LearnCommandParserTest {
    private LearnCommandParser parser = new LearnCommandParser();

    @Test
    public void parse_multipleWords_fail() {
        String word1 = "happy";
        String word2 = "sad";
        String wordsCombined = word1 + " " + word2;
        String expectedMessage = Messages.MESSAGE_OVERALL_ERROR + " \n"
                + Dictionary.WORD_NOT_EXIST + "\n" + LearnCommand.MESSAGE_USAGE;
        assertParseFailure(parser, wordsCombined, expectedMessage);
    }

    @Test
    public void parse_singleWord_success() {
        String happy = "happy";
        Dictionary dictionary;
        Word word = null;
        try {
            dictionary = new Dictionary(happy).invoke();
            String wordToLearn = dictionary.getWordToLearn();
            String definition = dictionary.getDefinition();
            Tag defaultTag = new Tag("toLearn");
            ArrayList<String> stringArrayList = new ArrayList<>(Collections.singleton(defaultTag.tagName));

            Name name = ParserUtil.parseName(wordToLearn);
            Meaning meaning = ParserUtil.parseMeaning(definition);
            Set<Tag> tagList = ParserUtil.parseTags(stringArrayList);
            word = new Word(name, meaning, tagList);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertParseSuccess(parser, happy, new LearnCommand(word));
    }

}
