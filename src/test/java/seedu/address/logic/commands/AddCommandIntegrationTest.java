package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalTriviaBundle;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.Card;
import seedu.address.model.person.Person;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTriviaBundle(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddCommand(validPerson), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_newCard_success() {
        Card validCard = new CardBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getTriviaBundle(), new UserPrefs());
        expectedModel.addCard(validCard);
        expectedModel.commitTriviaBundle();

        assertCommandSuccess(new AddCCommand(validCard), model, commandHistory,
                String.format(AddCCommand.MESSAGE_SUCCESS, validCard), expectedModel);
    }


    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateCard_throwsCommandException() {
        Card cardInList = model.getTriviaBundle().getCardList().get(0);
        assertCommandFailure(new AddCCommand(cardInList), model, commandHistory,
                AddCCommand.MESSAGE_DUPLICATE_CARD);
    }

}
