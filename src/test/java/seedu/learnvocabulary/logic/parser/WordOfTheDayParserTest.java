package seedu.learnvocabulary.logic.parser;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.junit.Test;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.logic.commands.CommandResult;
import seedu.learnvocabulary.logic.commands.WordOfTheDayCommand;

import seedu.learnvocabulary.logic.parser.exceptions.ParseException;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.ModelManager;
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
        Word word;
        try {
            dictionary = new Dictionary(empty);
            dictionary.fetchWordOfTheDay();
            String wordOfTheDay = dictionary.getWordOfTheDay();
            String definition = dictionary.getDefinition();
            Tag defaultTag = new Tag("WordOfTheDay");
            ArrayList<String> stringArrayList = new ArrayList<>(Collections.singleton(defaultTag.tagName));

            Name name = ParserUtil.parseName(wordOfTheDay);
            Meaning meaning = ParserUtil.parseMeaning(definition);
            Set<Tag> tagList = ParserUtil.parseTags(stringArrayList);
            word = new Word(name, meaning, tagList);

            Model model = new ModelManager();
            CommandHistory history = new CommandHistory();
            CommandResult commandResult1 = parser.parse(empty).execute(model, history);
            CommandResult commandResult2 = new WordOfTheDayCommand(word).execute(model, history);
            assertEquals(commandResult1.feedbackToUser, commandResult2.feedbackToUser);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
