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
    private static final String HAPPY = "happy";
    private static final String SAD = "sad";
    private static final String ILLEGAL_CHARACTER_NUMBER = "1happy";
    private static final String ILLEGAL_CHARACTER_EXCLAIMATION = "happy!";
    private static final String BLANK = " ";

    private LearnCommandParser parser = new LearnCommandParser();


    @Test
    public void parse_multipleWords_fail() {
        String wordsCombined = HAPPY + " " + SAD;
        String expectedMessage = Messages.MESSAGE_OVERALL_ERROR + " \n"
                + Dictionary.MESSAGE_INVALID_WORD + "\n" + LearnCommand.MESSAGE_USAGE;
        assertParseFailure(parser, wordsCombined, expectedMessage);
    }

    @Test
    public void parse_singleWordIllegalCharacter_fail() {
        String expectedMessage = Messages.MESSAGE_OVERALL_ERROR + " \n"
                + Dictionary.MESSAGE_INVALID_WORD + "\n" + LearnCommand.MESSAGE_USAGE;
        assertParseFailure(parser, ILLEGAL_CHARACTER_NUMBER, expectedMessage);

        expectedMessage = Messages.MESSAGE_OVERALL_ERROR + " \n"
                + Dictionary.MESSAGE_INVALID_WORD + "\n" + LearnCommand.MESSAGE_USAGE;
        assertParseFailure(parser, ILLEGAL_CHARACTER_EXCLAIMATION, expectedMessage);
    }

    @Test
    public void parse_leaveWordBlank_fail() {
        String expectedMessage = Messages.MESSAGE_OVERALL_ERROR + " \n"
                + Dictionary.MESSAGE_CANNOT_BE_EMPTY + "\n" + LearnCommand.MESSAGE_USAGE;
        assertParseFailure(parser, BLANK, expectedMessage);
    }

    @Test
    public void parse_blankFollowedByWord_success() {
        String combinedWord = BLANK + HAPPY;
        Dictionary dictionary;
        Word word = null;
        try {
            dictionary = new Dictionary(combinedWord).invoke();
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
        assertParseSuccess(parser, combinedWord, new LearnCommand(word));
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
            Tag defaultTag = new Tag(Tag.DEFAULT_TAG);
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
