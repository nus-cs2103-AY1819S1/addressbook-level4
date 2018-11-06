package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_DEFAULT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_BADMINTON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARKS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCcas.BASKETBALL;
import static seedu.address.testutil.TypicalCcas.getTypicalBudgetBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.TransactionMath;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Entry;
import seedu.address.model.transaction.Remarks;
import seedu.address.testutil.CcaBuilder;

//@@author ericyjw
public class AddTransactionCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalBudgetBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullCcaName_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);

        new AddTransactionCommand(null, new Date(VALID_DATE), new Amount(Integer.valueOf(VALID_AMOUNT)),
            new Remarks(VALID_REMARKS));
    }

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);

        new AddTransactionCommand(new CcaName(VALID_CCA_NAME_BADMINTON), null,
            new Amount(Integer.valueOf(VALID_AMOUNT)), new Remarks(VALID_REMARKS));
    }

    @Test
    public void constructor_nullAmount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);

        new AddTransactionCommand(new CcaName(VALID_CCA_NAME_BADMINTON), new Date(VALID_DATE), null,
            new Remarks(VALID_REMARKS));
    }

    @Test
    public void constructor_nullRemarks_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);

        new AddTransactionCommand(new CcaName(VALID_CCA_NAME_BADMINTON), new Date(VALID_DATE),
            new Amount(Integer.valueOf(VALID_AMOUNT)), null);
    }

    @Test
    public void execute_allFieldPresentEntry_success() {
        Cca targetCca = new CcaBuilder(BASKETBALL).build();
        Integer entryNum = targetCca.getEntrySize() + 1;
        Date date = new Date(VALID_DATE);
        Amount amount = new Amount(Integer.valueOf(VALID_BUDGET_DEFAULT));
        Remarks remarks = new Remarks(VALID_REMARKS);
        Entry entry = new Entry(entryNum, date, amount, remarks);

        Cca updatedCca = targetCca.addNewTransaction(entry);
        updatedCca = TransactionMath.updateDetails(updatedCca);

        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(targetCca.getName(), date, amount,
            remarks);

        String expectedMessage = String.format(AddTransactionCommand.MESSAGE_UPDATE_SUCCESS, updatedCca);

        Model expectedModel = new ModelManager(new BudgetBook(model.getBudgetBook()), new UserPrefs());
        expectedModel.updateCca(targetCca, updatedCca);
        expectedModel.commitBudgetBook();

        assertCommandSuccess(addTransactionCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        CcaName defaultCcaName = new CcaName("Basketball");
        CcaName alternativeCcaName = new CcaName("Baseball");
        Date defaultDate = new Date("24.10.2018");
        Date alternativeDate = new Date("12.12.2018");
        Amount defaultAmount = new Amount(100);
        Amount alternativeAmount = new Amount(-100);
        Remarks defaultRemarks = new Remarks("Welfare");
        Remarks alternativeRemarks = new Remarks("Competition Fee");

        AddTransactionCommand addDefaultEntryCommand = new AddTransactionCommand(defaultCcaName, defaultDate,
            defaultAmount, defaultRemarks);
        AddTransactionCommand addAlternativeCcaNameCommand = new AddTransactionCommand(alternativeCcaName,
            defaultDate, defaultAmount, defaultRemarks);
        AddTransactionCommand addAlternativeDateCommand = new AddTransactionCommand(defaultCcaName, alternativeDate,
            defaultAmount, defaultRemarks);
        AddTransactionCommand addAlternativeAmountCommand = new AddTransactionCommand(defaultCcaName, defaultDate,
            alternativeAmount, defaultRemarks);
        AddTransactionCommand addAlternativeRemarksCommand = new AddTransactionCommand(defaultCcaName, defaultDate,
            defaultAmount, alternativeRemarks);

        // same object -> returns true
        assertTrue(addDefaultEntryCommand.equals(addDefaultEntryCommand));

        // same values -> returns true
        AddTransactionCommand addDefaultEntryCommandCopy = new AddTransactionCommand(defaultCcaName, defaultDate,
            defaultAmount, defaultRemarks);
        assertTrue(addDefaultEntryCommand.equals(addDefaultEntryCommandCopy));

        // different types -> returns false
        assertFalse(addDefaultEntryCommand.equals(1));

        // null -> returns false
        assertFalse(addDefaultEntryCommand.equals(null));

        // different Cca Name -> returns false
        assertFalse(addDefaultEntryCommand.equals(addAlternativeCcaNameCommand));

        // different Date -> returns false
        assertFalse(addDefaultEntryCommand.equals(addAlternativeDateCommand));

        // different Amount -> returns false
        assertFalse(addDefaultEntryCommand.equals(addAlternativeAmountCommand));

        // different Remarks -> returns false
        assertFalse(addDefaultEntryCommand.equals(addAlternativeRemarksCommand));

    }
}
