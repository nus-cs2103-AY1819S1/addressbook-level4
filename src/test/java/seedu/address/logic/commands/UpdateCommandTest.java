package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TRACK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_DEFAULT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_HOCKEY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCcas.BADMINTON;
import static seedu.address.testutil.TypicalCcas.BASKETBALL;
import static seedu.address.testutil.TypicalCcas.HOCKEY;
import static seedu.address.testutil.TypicalCcas.TRACK;
import static seedu.address.testutil.TypicalCcas.getTypicalBudgetBook;
import static seedu.address.testutil.TypicalEntries.getEntryCompetition1;
import static seedu.address.testutil.TypicalEntries.getEntryPrize2;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.UpdateCommand.EditCcaDescriptor;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Entry;
import seedu.address.model.transaction.Remarks;
import seedu.address.testutil.CcaBuilder;
import seedu.address.testutil.EditCcaDescriptorBuilder;

//@@author ericyjw

/**
 * Contains integration tests and unit tests for UpdateCommand.
 */
public class UpdateCommandTest {

    private Model model = new ModelManager(getTypicalBudgetBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    /**
     * Update all fields including transaction fields.
     * Update {@code CcaName}, {@code Name} of head, {@code Name} of vice-head and {@code Budget}.
     * Update the {@code Date}, {@code Amount} and {@code Remarks} of the first transaction {@code Entry} in the
     * specified
     * {@code Cca}.
     */
    @Test
    public void execute_allFieldsSpecifiedCcaList_success() {
        Cca targetCca = BASKETBALL;
        // Adding Carl and Daniel from Basketball into the empty model with no persons
        model.addPerson(CARL);
        model.commitAddressBook();
        model.addPerson(DANIEL);
        model.commitAddressBook();

        Cca editedCca = new CcaBuilder().build();
        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder(editedCca)
            .withEntries(getEntryCompetition1(), getEntryPrize2())
            .withEntryNum(1)
            .withDate(VALID_DATE)
            .withAmount(VALID_AMOUNT)
            .withRemarks(VALID_REMARKS)
            .build();
        UpdateCommand updateCommand = new UpdateCommand(targetCca.getName(), descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_SUCCESS, editedCca);

        // Adding Carl and Daniel into the empty model
        Model expectedModel = new ModelManager(new BudgetBook(model.getBudgetBook()), new UserPrefs());
        expectedModel.addPerson(CARL);
        expectedModel.commitAddressBook();
        expectedModel.addPerson(DANIEL);
        expectedModel.commitAddressBook();
        expectedModel.updateCca(targetCca, editedCca);
        expectedModel.commitBudgetBook();

        assertCommandSuccess(updateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Update all fields, excluding transaction fields.
     * Update {@code CcaName}, {@code Name} of head, {@code Name} of vice-head and {@code Budget}.
     */
    @Test
    public void execute_allCcaFieldsSpecifiedCcaList_success() {
        Cca targetCca = BASKETBALL;
        // Adding Carl, Bob, Fiona and Daniel from Basketball into the empty model with no persons
        model.addPerson(BOB);
        model.commitAddressBook();
        model.addPerson(CARL);
        model.commitAddressBook();
        model.addPerson(DANIEL);
        model.commitAddressBook();
        model.addPerson(FIONA);
        model.commitAddressBook();

        Cca editedCca = new CcaBuilder(targetCca)
            .withCcaName(VALID_CCA_NAME_HOCKEY)
            .withHead(FIONA.getName().fullName)
            .withViceHead(VALID_NAME_BOB)
            .withBudget(Integer.valueOf(VALID_BUDGET_DEFAULT)).build();

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder()
            .withCcaName(VALID_CCA_NAME_HOCKEY)
            .withHead(FIONA.getName().fullName)
            .withViceHead(VALID_NAME_BOB)
            .withBudget(VALID_BUDGET_DEFAULT).build();
        UpdateCommand updateCommand = new UpdateCommand(targetCca.getName(), descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_SUCCESS, editedCca);

        // Adding Carl, Daniel, Fiona and Bob into the empty model
        Model expectedModel = new ModelManager(new BudgetBook(model.getBudgetBook()), new UserPrefs());
        expectedModel.addPerson(BOB);
        expectedModel.commitAddressBook();
        expectedModel.addPerson(CARL);
        expectedModel.commitAddressBook();
        expectedModel.addPerson(DANIEL);
        expectedModel.commitAddressBook();
        expectedModel.addPerson(FIONA);
        expectedModel.commitAddressBook();
        expectedModel.updateCca(targetCca, editedCca);
        expectedModel.commitBudgetBook();

        assertCommandSuccess(updateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Update some fields excluding transaction fields.
     * Update {@code CcaName}, {@code Name} of vice-head and {@code Budget}.
     */
    @Test
    public void execute_someCcaFieldsSpecifiedCcaList_success() {
        Cca targetCca = BASKETBALL;
        // Adding Carl, Bob and Daniel from Basketball into the empty model with no persons
        model.addPerson(BOB);
        model.commitAddressBook();
        model.addPerson(CARL);
        model.commitAddressBook();
        model.addPerson(DANIEL);
        model.commitAddressBook();

        Cca editedCca = new CcaBuilder(targetCca)
            .withCcaName(VALID_CCA_NAME_HOCKEY)
            .withViceHead(VALID_NAME_BOB)
            .withBudget(Integer.valueOf(VALID_BUDGET_DEFAULT))
            .build();

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder()
            .withCcaName(VALID_CCA_NAME_HOCKEY)
            .withViceHead(VALID_NAME_BOB)
            .withBudget(VALID_BUDGET_DEFAULT)
            .build();
        UpdateCommand updateCommand = new UpdateCommand(targetCca.getName(), descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_SUCCESS, editedCca);

        // Adding Carl, Daniel and Bob into the empty model
        Model expectedModel = new ModelManager(new BudgetBook(model.getBudgetBook()), new UserPrefs());
        expectedModel.addPerson(BOB);
        expectedModel.commitAddressBook();
        expectedModel.addPerson(CARL);
        expectedModel.commitAddressBook();
        expectedModel.addPerson(DANIEL);
        expectedModel.commitAddressBook();
        expectedModel.updateCca(targetCca, editedCca);
        expectedModel.commitBudgetBook();

        assertCommandSuccess(updateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Updates only transaction fields. Update all transaction fields.
     * Updates {@code Date}, {code Amount} and {@code Remarks}.
     */
    @Test
    public void execute_allTransactionFieldOnlySpecifiedCcaList_success() {
        Cca targetCca = BASKETBALL;
        // Adding Carl and Daniel from Basketball into the empty model with no persons
        model.addPerson(CARL);
        model.commitAddressBook();
        model.addPerson(DANIEL);
        model.commitAddressBook();

        // Changing the first entry of the basketball transaction
        // New entry have a different date, amount and remarks
        Set<Entry> basketballEntries = BASKETBALL.getEntries();
        Entry[] basketballEntryArr = new Entry[basketballEntries.size()];
        basketballEntries.toArray(basketballEntryArr);
        basketballEntryArr[0] = getEntryCompetition1();
        basketballEntries = Set.of(basketballEntryArr);

        Cca editedCca = new CcaBuilder(targetCca)
            .withTransaction(basketballEntries)
            .build();

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder()
            .withEntries(getEntryCompetition1(), getEntryPrize2())
            .build();
        UpdateCommand updateCommand = new UpdateCommand(targetCca.getName(), descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_SUCCESS, editedCca);

        // Adding Carl and Bob into the empty model
        Model expectedModel = new ModelManager(new BudgetBook(model.getBudgetBook()), new UserPrefs());
        expectedModel.addPerson(CARL);
        expectedModel.commitAddressBook();
        expectedModel.addPerson(DANIEL);
        expectedModel.commitAddressBook();
        expectedModel.updateCca(targetCca, editedCca);
        expectedModel.commitBudgetBook();

        assertCommandSuccess(updateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Update only transaction fields. Update some transaction fields.
     * Update {@code Amount} only.
     */
    @Test
    public void execute_someTransactionFieldOnlySpecifiedCcaList_success() {
        Cca targetCca = BASKETBALL;
        // Adding Carl and Daniel from Basketball into the empty model with no persons
        model.addPerson(CARL);
        model.commitAddressBook();
        model.addPerson(DANIEL);
        model.commitAddressBook();

        // Changing some fields of the first entry of the basketball transaction
        // New entry have a different amount
        // Original first entry: "14.03.2018", -200, "Purchase of Equipment"
        // Edited first entry: "14.03.2018", -100, "Purchase of Equipment"
        Set<Entry> basketballEntries = BASKETBALL.getEntries();
        Entry[] basketballEntryArr = new Entry[basketballEntries.size()];
        basketballEntries.toArray(basketballEntryArr);
        basketballEntryArr[0] = new Entry(
            1,
            new Date("14.03.2018"),
            new Amount(-100),
            new Remarks("Purchase of Equipment"));
        basketballEntries = Set.of(basketballEntryArr);

        Cca editedCca = new CcaBuilder(targetCca)
            .withTransaction(basketballEntries)
            .build();

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder()
            .withEntries(getEntryCompetition1(), getEntryPrize2())
            .build();
        UpdateCommand updateCommand = new UpdateCommand(targetCca.getName(), descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_SUCCESS, editedCca);

        // Adding Carl and Bob into the empty model
        Model expectedModel = new ModelManager(new BudgetBook(model.getBudgetBook()), new UserPrefs());
        expectedModel.addPerson(CARL);
        expectedModel.commitAddressBook();
        expectedModel.addPerson(DANIEL);
        expectedModel.commitAddressBook();
        expectedModel.updateCca(targetCca, editedCca);
        expectedModel.commitBudgetBook();

        assertCommandSuccess(updateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Update some fields including transaction fields. Update all transaction fields.
     */
    @Test
    public void execute_someFieldsAndAllTransactionFieldOnlySpecifiedCcaList_success() {
        Cca targetCca = BASKETBALL;
        // Adding Carl, Bob and Daniel from Basketball into the empty model with no persons
        model.addPerson(BOB);
        model.commitAddressBook();
        model.addPerson(CARL);
        model.commitAddressBook();
        model.addPerson(DANIEL);
        model.commitAddressBook();

        // Changing some fields of the first entry of the basketball transaction
        // New entry have a different amount
        // Original first entry: "14.03.2018", -200, "Purchase of Equipment"
        // Edited first entry: "14.03.2018", -100, "Purchase of Equipment"
        Set<Entry> basketballEntries = BASKETBALL.getEntries();
        Entry[] basketballEntryArr = new Entry[basketballEntries.size()];
        basketballEntries.toArray(basketballEntryArr);
        basketballEntryArr[0] = getEntryCompetition1();
        basketballEntries = Set.of(basketballEntryArr);

        Cca editedCca = new CcaBuilder(targetCca)
            .withCcaName(VALID_CCA_NAME_HOCKEY)
            .withViceHead(VALID_NAME_BOB)
            .withBudget(Integer.valueOf(VALID_BUDGET_DEFAULT))
            .withTransaction(basketballEntries)
            .build();

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder()
            .withCcaName(VALID_CCA_NAME_HOCKEY)
            .withViceHead(VALID_NAME_BOB)
            .withBudget(VALID_BUDGET_DEFAULT)
            .withEntries(getEntryCompetition1(), getEntryPrize2())
            .build();

        UpdateCommand updateCommand = new UpdateCommand(targetCca.getName(), descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_SUCCESS, editedCca);

        // Adding Carl and Bob into the empty model
        Model expectedModel = new ModelManager(new BudgetBook(model.getBudgetBook()), new UserPrefs());
        expectedModel.addPerson(BOB);
        expectedModel.commitAddressBook();
        expectedModel.addPerson(CARL);
        expectedModel.commitAddressBook();
        expectedModel.addPerson(DANIEL);
        expectedModel.commitAddressBook();
        expectedModel.updateCca(targetCca, editedCca);
        expectedModel.commitBudgetBook();

        assertCommandSuccess(updateCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    /**
     * Update some fields including transaction fields. Update some transaction fields.
     * Update {@code CcaName}, {@code Name} of vice-head, and {@code Budget}.
     * Update only {@code Amount}.
     */
    @Test
    public void execute_someFieldsAndSomeTransactionFieldOnlySpecifiedCcaList_success() {
        Cca targetCca = BASKETBALL;
        // Adding Carl, Bob and Daniel from Basketball into the empty model with no persons
        model.addPerson(BOB);
        model.commitAddressBook();
        model.addPerson(CARL);
        model.commitAddressBook();
        model.addPerson(DANIEL);
        model.commitAddressBook();

        // Changing some fields of the first entry of the basketball transaction
        // New entry have a different amount
        // Original first entry: "14.03.2018", -200, "Purchase of Equipment"
        // Edited first entry: "14.03.2018", -100, "Purchase of Equipment"
        Set<Entry> basketballEntries = BASKETBALL.getEntries();
        Entry[] basketballEntryArr = new Entry[basketballEntries.size()];
        basketballEntries.toArray(basketballEntryArr);
        basketballEntryArr[0] = new Entry(
            1,
            new Date("14.03.2018"),
            new Amount(-100),
            new Remarks("Purchase of Equipment"));
        basketballEntries = Set.of(basketballEntryArr);

        Cca editedCca = new CcaBuilder(targetCca)
            .withCcaName(VALID_CCA_NAME_HOCKEY)
            .withViceHead(VALID_NAME_BOB)
            .withBudget(Integer.valueOf(VALID_BUDGET_DEFAULT))
            .withTransaction(basketballEntries)
            .build();

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder()
            .withCcaName(VALID_CCA_NAME_HOCKEY)
            .withViceHead(VALID_NAME_BOB)
            .withBudget(VALID_BUDGET_DEFAULT)
            .withEntries(getEntryCompetition1(), getEntryPrize2())
            .build();

        UpdateCommand updateCommand = new UpdateCommand(targetCca.getName(), descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_SUCCESS, editedCca);

        // Adding Carl and Bob into the empty model
        Model expectedModel = new ModelManager(new BudgetBook(model.getBudgetBook()), new UserPrefs());
        expectedModel.addPerson(BOB);
        expectedModel.commitAddressBook();
        expectedModel.addPerson(CARL);
        expectedModel.commitAddressBook();
        expectedModel.addPerson(DANIEL);
        expectedModel.commitAddressBook();
        expectedModel.updateCca(targetCca, editedCca);
        expectedModel.commitBudgetBook();

        assertCommandSuccess(updateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCcaUnfilteredList_failure() {
        Cca targetCca = TRACK;
        Person head = ALICE;
        Person viceHead = BENSON;
        model.addPerson(head);
        model.commitAddressBook();
        model.addPerson(viceHead);
        model.commitAddressBook();

        model.addPerson(CARL);
        model.commitAddressBook();
        model.addPerson(DANIEL);
        model.commitAddressBook();

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder(BASKETBALL).build();
        UpdateCommand updateCommand = new UpdateCommand(targetCca.getName(), descriptor);

        assertCommandFailure(updateCommand, model, commandHistory, UpdateCommand.MESSAGE_DUPLICATE_CCA);
    }

    @Test
    public void execute_nonExistentHeadCcaUnfilteredList_failure() {
        Cca targetCca = TRACK;
        Person viceHead = BENSON;
        model.addPerson(viceHead);
        model.commitAddressBook();
        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder(TRACK).build();
        UpdateCommand updateCommand = new UpdateCommand(targetCca.getName(), descriptor);

        assertCommandFailure(updateCommand, model, commandHistory, UpdateCommand.MESSAGE_INVALID_HEAD_NAME);
    }

    @Test
    public void execute_nonExistentViceHeadCcaUnfilteredList_failure() {
        Cca targetCca = BASKETBALL;
        Person head = CARL;
        model.addPerson(head);
        model.commitAddressBook();
        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder(BASKETBALL).build();
        UpdateCommand updateCommand = new UpdateCommand(targetCca.getName(), descriptor);

        assertCommandFailure(updateCommand, model, commandHistory, UpdateCommand.MESSAGE_INVALID_VICE_HEAD_NAME);
    }

    @Test
    public void execute_invalidCcaUnfilteredList_failure() {
        Cca targetCca = HOCKEY;
        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder().withCcaName(VALID_CCA_NAME_HOCKEY).build();
        UpdateCommand updateCommand = new UpdateCommand(targetCca.getName(), descriptor);

        assertCommandFailure(updateCommand, model, commandHistory, Messages.MESSAGE_INVALID_CCA);
    }

    @Test
    public void execute_updateTransactionCcaUnfilteredList_success() {
        Cca targetCca = BADMINTON;
        model.addPerson(FIONA);
        model.commitAddressBook();
        model.addPerson(GEORGE);
        model.commitAddressBook();

        CcaBuilder duplicateCca = new CcaBuilder(targetCca);
        Set<Entry> entrySet = new LinkedHashSet<>();
        entrySet.add(getEntryCompetition1());
        Cca editedCca = duplicateCca
            .withCcaName(VALID_CCA_NAME_BADMINTON)
            .withTransaction(entrySet)
            .build();

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder()
            .withCcaName(VALID_CCA_NAME_BADMINTON)
            .withEntries(getEntryCompetition1())
            .build();

        UpdateCommand updateCommand = new UpdateCommand(targetCca.getName(), descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_SUCCESS, editedCca);

        Model expectedModel = new ModelManager(new BudgetBook(model.getBudgetBook()), new UserPrefs());
        expectedModel.addPerson(FIONA);
        expectedModel.commitAddressBook();
        expectedModel.addPerson(GEORGE);
        expectedModel.commitAddressBook();
        expectedModel.updateCca(targetCca, editedCca);
        expectedModel.commitBudgetBook();

        assertCommandSuccess(updateCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    /**
     * Update invalid entry number with all valid transaction field.
     */
    @Test
    public void execute_allTransactionFieldWithInvalidEntryNumSpecifiedCcaList_success() {
        Cca targetCca = BASKETBALL;
        // Adding Carl and Daniel from Basketball into the empty model with no persons
        model.addPerson(CARL);
        model.commitAddressBook();
        model.addPerson(DANIEL);
        model.commitAddressBook();

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder()
            .withEntryNum(targetCca.getEntrySize() + 1)
            .withEntries(getEntryCompetition1(), getEntryPrize2())
            .build();
        UpdateCommand updateCommand = new UpdateCommand(targetCca.getName(), descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_INVALID_ENTRY_NUM);

        assertCommandFailure(updateCommand, model, commandHistory, expectedMessage);
    }

    /**
     * Update invalid entry number with some valid transaction field.
     */
    @Test
    public void execute_someTransactionFieldWithInvalidEntryNumSpecifiedCcaList_success() {
        Cca targetCca = BASKETBALL;
        // Adding Carl and Daniel from Basketball into the empty model with no persons
        model.addPerson(CARL);
        model.commitAddressBook();
        model.addPerson(DANIEL);
        model.commitAddressBook();

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder()
            .withEntryNum(targetCca.getEntrySize() + 1)
            .withDate(VALID_DATE)
            .withEntries(getEntryCompetition1())
            .build();
        UpdateCommand updateCommand = new UpdateCommand(targetCca.getName(), descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_INVALID_ENTRY_NUM);

        assertCommandFailure(updateCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_transactionFieldWithInvalidEntryNumSpecifiedCcaList_success() {
        Cca targetCca = BASKETBALL;
        // Adding Carl and Daniel from Basketball into the empty model with no persons
        model.addPerson(CARL);
        model.commitAddressBook();
        model.addPerson(DANIEL);
        model.commitAddressBook();

        EditCcaDescriptor descriptor = new EditCcaDescriptorBuilder()
            .withEntryNum(targetCca.getEntrySize() + 1)
            .withAmount(VALID_AMOUNT)
            .withRemarks(VALID_REMARKS)
            .withEntries(getEntryCompetition1())
            .build();
        UpdateCommand updateCommand = new UpdateCommand(targetCca.getName(), descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_INVALID_ENTRY_NUM);

        assertCommandFailure(updateCommand, model, commandHistory, expectedMessage);
    }


    @Test
    public void equals() {
        Cca validCca = new CcaBuilder().withCcaName(VALID_CCA_NAME_BASKETBALL).build();
        Cca alternativeCca = new CcaBuilder().withCcaName(VALID_CCA_NAME_HOCKEY).build();
        final UpdateCommand standardCommand = new UpdateCommand(validCca.getName(), DESC_BASKETBALL);

        // same values -> returns true
        EditCcaDescriptor copyDescriptor = new EditCcaDescriptor(DESC_BASKETBALL);
        UpdateCommand commandWithSameValues = new UpdateCommand(validCca.getName(), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new HelpCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(alternativeCca.getName(), DESC_BASKETBALL)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(validCca.getName(), DESC_TRACK)));
    }

}
