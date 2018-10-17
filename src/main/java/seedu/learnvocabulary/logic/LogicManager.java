package seedu.learnvocabulary.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.learnvocabulary.commons.core.ComponentManager;
import seedu.learnvocabulary.commons.core.LogsCenter;
import seedu.learnvocabulary.logic.commands.Command;
import seedu.learnvocabulary.logic.commands.CommandResult;
import seedu.learnvocabulary.logic.commands.exceptions.CommandException;
import seedu.learnvocabulary.logic.parser.LearnVocabularyParser;
import seedu.learnvocabulary.logic.parser.exceptions.ParseException;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.word.Word;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final LearnVocabularyParser learnVocabularyParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        learnVocabularyParser = new LearnVocabularyParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = learnVocabularyParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Word> getFilteredWordList() {
        return model.getFilteredWordList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
