package seedu.scheduler.logic;

import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_REMINDER_DURATION;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_REPEAT_TYPE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_REPEAT_UNTIL_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.scheduler.commons.core.ComponentManager;
import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.logic.commands.Command;
import seedu.scheduler.logic.commands.CommandResult;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.logic.parser.ArgumentMultimap;
import seedu.scheduler.logic.parser.ArgumentTokenizer;
import seedu.scheduler.logic.parser.ParserUtil;
import seedu.scheduler.logic.parser.SchedulerParser;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.event.Event;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final SchedulerParser schedulerParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        schedulerParser = new SchedulerParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = schedulerParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public String parseDateTime(String commandText) {
        logger.info("Parsing Date Time on the fly");
        StringBuilder result = new StringBuilder();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(commandText,
                PREFIX_EVENT_NAME, PREFIX_START_DATE_TIME, PREFIX_END_DATE_TIME,
                PREFIX_DESCRIPTION, PREFIX_VENUE, PREFIX_REPEAT_TYPE,
                PREFIX_REPEAT_UNTIL_DATE_TIME, PREFIX_TAG, PREFIX_EVENT_REMINDER_DURATION);
        if (argMultimap.getValue(PREFIX_START_DATE_TIME).isPresent()) {
            result.append("Start Date Time: ")
                    .append(ParserUtil.parseDateTimePretty(argMultimap.getValue(PREFIX_START_DATE_TIME).get()))
                    .append("\n");
        }
        if (argMultimap.getValue(PREFIX_END_DATE_TIME).isPresent()) {
            result.append("End Date Time: ")
                    .append(ParserUtil.parseDateTimePretty(argMultimap.getValue(PREFIX_END_DATE_TIME).get()))
                    .append("\n");
        }
        if (argMultimap.getValue(PREFIX_REPEAT_UNTIL_DATE_TIME).isPresent()) {
            result.append("Repeat Until Date Time: ")
                    .append(ParserUtil.parseDateTimePretty(argMultimap.getValue(PREFIX_REPEAT_UNTIL_DATE_TIME).get()));
        }
        return result.toString();
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        return model.getFilteredEventList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
