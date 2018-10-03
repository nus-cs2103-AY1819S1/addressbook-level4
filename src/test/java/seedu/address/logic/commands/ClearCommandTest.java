package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

//@@author kengwoon
public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ModelManager expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs());

    @Test
    public void execute_emptyAddressBook_success() {
        Model modelEmpty = new ModelManager();
        Model expectedModelEmpty = new ModelManager();
        List<String> target = new ArrayList<>();
        target.add("basketball");

        String expectedMessage = String.format(ClearCommand.MESSAGE_CLEAR_NOTHING, '[' + target.get(0) + ']');

        assertCommandSuccess(new ClearCommand(target), modelEmpty, commandHistory, expectedMessage, expectedModelEmpty);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        List<String> target = new ArrayList<>();
        target.add("track");

        Person p = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<Person> persons = new ArrayList<>();
        persons.add(p);

        expectedModel.clearMultiplePersons(persons);
        expectedModel.commitAddressBook();

        String expectedMessage = String.format(ClearCommand.MESSAGE_CLEAR_SPECIFIC_SUCCESS, '[' + target.get(0) + ']');

        assertCommandSuccess(new ClearCommand(target), model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clearSpecific_success() {
        List<String> target = new ArrayList<>();
        target.add("track");
        ClearCommand clearCommand = new ClearCommand(target);

        String expectedMessage = String.format(ClearCommand.MESSAGE_CLEAR_SPECIFIC_SUCCESS, '[' + target.get(0) + ']');

        Person p = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<Person> persons = new ArrayList<>();
        persons.add(p);
        expectedModel.clearMultiplePersons(persons);
        expectedModel.commitAddressBook();

        assertCommandSuccess(clearCommand, model, commandHistory, expectedMessage, expectedModel);
    }

}
