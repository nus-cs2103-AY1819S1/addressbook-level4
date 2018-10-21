package seedu.clinicio.logic.commands;

import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import org.junit.Before;
import org.junit.Test;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.UserPrefs;
import seedu.clinicio.model.analytics.Analytics;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getClinicIo(), new UserPrefs());
        expectedModel.addPerson(validPerson);
        expectedModel.commitClinicIo();

        assertCommandSuccess(new AddCommand(validPerson), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel, analytics);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getClinicIo().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, commandHistory,
                analytics, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
