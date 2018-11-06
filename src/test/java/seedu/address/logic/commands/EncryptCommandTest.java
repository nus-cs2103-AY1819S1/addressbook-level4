package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDateTime;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.logic.LoginCredentials;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyExpenseTracker;
import seedu.address.model.UserPrefs;
import seedu.address.model.budget.CategoryBudget;
import seedu.address.model.budget.TotalBudget;
import seedu.address.model.exceptions.NoUserSelectedException;
import seedu.address.model.expense.Expense;
import seedu.address.model.notification.Notification;
import seedu.address.model.notification.NotificationHandler;
import seedu.address.model.user.Password;
import seedu.address.model.user.Username;

public class EncryptCommandTest {

    private static final String ENCRYPTED_STRING = "eeeeee";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullString_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EncryptCommand(null);
    }

    @Test
    public void testExecute() throws NoUserSelectedException {
        EncryptCommand commandEmpty = new EncryptCommand("");
        EncryptCommand command = new EncryptCommand("a");
        Model model = new ModelStub();
        CommandResult emptyResult = commandEmpty.execute(model, null);
        assertEquals(String.format(EncryptCommand.MESSAGE_SUCCESS, ENCRYPTED_STRING), emptyResult.feedbackToUser);
        CommandResult result = command.execute(model, null);
        assertEquals(String.format(EncryptCommand.MESSAGE_SUCCESS, ENCRYPTED_STRING), result.feedbackToUser);
    }

    @Test
    public void testEquals() {
        EncryptCommand commandEmpty = new EncryptCommand("");
        EncryptCommand command = new EncryptCommand("a");
        assertEquals(command, new EncryptCommand("a"));
        assertEquals(commandEmpty, new EncryptCommand(""));
        assertNotEquals(command, commandEmpty);
        assertNotEquals(command, new EncryptCommand("b"));
    }

    @Test
    public void testHashCode() {
        EncryptCommand commandEmpty = new EncryptCommand("");
        EncryptCommand command = new EncryptCommand("a");
        assertEquals(command.hashCode(), new EncryptCommand("a").hashCode());
        assertEquals(commandEmpty.hashCode(), new EncryptCommand("").hashCode());
    }

    /**
     * A default model stub that have all of the methods failing except for encryptString which is used by
     * EncryptCommand
     */
    private class ModelStub implements Model {
        @Override
        public boolean addExpense(Expense expense) {
            throw new AssertionError("addExpense method should not be called.");
        }

        @Override
        public void setRecurrenceFrequency(long seconds) {
            throw new AssertionError("setRecurrenceFrequency should not be called");
        }

        @Override
        public void setCategoryBudget(CategoryBudget budget) {
            throw new AssertionError("setCategoryBudget should not be called");

        }
        @Override
        public void resetData(ReadOnlyExpenseTracker newData) {
            throw new AssertionError("resetData method should not be called.");
        }

        @Override
        public void modifyMaximumBudget(TotalBudget totalBudget) {
            throw new AssertionError("modifyMaximumBudget method should not be called.");
        }

        @Override
        public ReadOnlyExpenseTracker getExpenseTracker() {
            throw new AssertionError("getExpenseTracker should not be called.");
        }

        @Override
        public boolean hasExpense(Expense expense) {
            throw new AssertionError("hasExpense method should not be called.");
        }

        @Override
        public void deleteExpense(Expense target) {
            throw new AssertionError("deleteExpense method should not be called.");
        }

        @Override
        public void updateExpense(Expense target, Expense editedExpense) {
            throw new AssertionError("updateExpense method should not be called.");
        }

        @Override
        public ObservableList<Expense> getFilteredExpenseList() {
            throw new AssertionError("getFilteredExpenseList method should not be called.");
        }

        @Override
        public void updateFilteredExpenseList(Predicate<Expense> predicate) {
            throw new AssertionError("updateFilteredExpenseList method should not be called.");
        }

        @Override
        public ObservableList<Expense> getExpenseStats() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateExpenseStatsPredicate (Predicate<Expense> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public StatsCommand.StatsMode getStatsMode() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateStatsMode(StatsCommand.StatsMode statsMode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public StatsCommand.StatsPeriod getStatsPeriod() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateStatsPeriod(StatsCommand.StatsPeriod statsPeriod) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getPeriodAmount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePeriodAmount(int x) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoExpenseTracker() {
            throw new AssertionError("canUndoExpenseTracker method should not be called.");
        }

        @Override
        public boolean canRedoExpenseTracker() {
            throw new AssertionError("canRedoExpenseTracker method should not be called.");
        }

        @Override
        public void undoExpenseTracker() {
            throw new AssertionError("undoExpenseTracker method should not be called.");
        }

        @Override
        public void redoExpenseTracker() {
            throw new AssertionError("redoExpenseTracker method should not be called.");
        }

        @Override
        public void commitExpenseTracker() {
            throw new AssertionError("commitExpenseTracker method should not be called.");
        }

        @Override
        public boolean loadUserData(LoginCredentials loginCredentials) {
            throw new AssertionError("loadUserData method should not be called.");
        }

        @Override
        public void unloadUserData() {
            throw new AssertionError("unloadUserData method should not be called.");
        }

        @Override
        public boolean isUserExists(Username username) {
            throw new AssertionError("isUserExists method should not be called.");
        }

        @Override
        public void addUser(Username username) {
            throw new AssertionError("addUser method should not be called.");
        }

        @Override
        public boolean hasSelectedUser() {
            throw new AssertionError("hasSelectedUser method should not be called.");
        }

        @Override
        public String encryptString(String toEncrypt) {
            return ENCRYPTED_STRING;
        }

        @Override
        public String decryptString(String toDecrypt) {
            throw new AssertionError("decryptString method should not be called.");
        }

        @Override
        public Model copy(UserPrefs userPrefs) {
            throw new AssertionError("copy method should not be called.");
        }

        @Override
        public void setPassword(Password newPassword, String plainPassword) {
            throw new AssertionError("setPassword method should not be called.");
        }

        @Override
        public boolean isMatchPassword(Password toCheck) {
            throw new AssertionError("isMatchPassword method should not be called.");
        }

        @Override
        public boolean addWarningNotification() {
            throw new AssertionError("addWarningNotification should not be called.");
        }

        @Override
        public boolean addTipNotification() {
            throw new AssertionError("addTipNotification should not be called.");
        }

        @Override
        public ObservableList<Notification> getNotificationList() {
            throw new AssertionError("addNotificationList should not be called.");
        }

        @Override
        public void toggleTipNotification(boolean toggleOption) {
            throw new AssertionError("toggleTipNotification should not be called.");
        }

        @Override
        public void toggleWarningNotification(boolean toggleOption) {
            throw new AssertionError("toggleWarningNotification should not be called.");
        }

        @Override
        public void toggleBothNotification(boolean toggleOption) {
            throw new AssertionError("toggleBothNotification should not be called.");
        }

        @Override
        public NotificationHandler getNotificationHandler() {
            throw new AssertionError("getNotificationHandler should not be called.");
        }

        @Override
        public void modifyNotificationHandler(LocalDateTime time, boolean isTipEnabled, boolean isWarningEnabled) {
            throw new AssertionError("modifyNotificationHandler should not be called.");
        }

        @Override
        public void clearNotifications() {
            throw new AssertionError("clearNotifications method should not be called.");
        }

        @Override
        public TotalBudget getMaximumBudget() {
            throw new AssertionError("getMaximumTotalBudget method should not be called.");
        }
    }
}
