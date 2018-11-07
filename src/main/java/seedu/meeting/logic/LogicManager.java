package seedu.meeting.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.meeting.commons.core.ComponentManager;
import seedu.meeting.commons.core.LogsCenter;
import seedu.meeting.logic.commands.Command;
import seedu.meeting.logic.commands.CommandResult;
import seedu.meeting.logic.commands.exceptions.CommandException;
import seedu.meeting.logic.parser.MeetingBookParser;
import seedu.meeting.logic.parser.exceptions.ParseException;
import seedu.meeting.model.Model;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.meeting.Meeting;
import seedu.meeting.model.person.Person;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final MeetingBookParser meetingBookParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        meetingBookParser = new MeetingBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = meetingBookParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return model.getFilteredGroupList();
    }

    @Override
    public ObservableList<Person> getSortedPersonList() {
        return model.getSortedPersonList();
    }

    @Override
    public ObservableList<Meeting> getFilteredMeetingList() {
        return model.getFilteredMeetingList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
