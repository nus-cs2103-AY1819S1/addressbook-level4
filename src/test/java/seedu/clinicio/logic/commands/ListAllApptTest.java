package seedu.clinicio.logic.commands;

//@@author gingivitiss

import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import org.junit.Before;
import org.junit.Test;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.UserPrefs;
import seedu.clinicio.model.analytics.Analytics;

public class ListAllApptTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalClinicIo(), new UserPrefs());
        expectedModel = new ModelManager(model.getClinicIo(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListAllApptCommand(), model, commandHistory,
                ListAllApptCommand.MESSAGE_SUCCESS, expectedModel, analytics);
    }
}
