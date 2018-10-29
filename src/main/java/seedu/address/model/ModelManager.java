package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.encryption.EncryptionUtil.DEFAULT_ENCRYPTION_KEY;
import static seedu.address.model.encryption.EncryptionUtil.createEncryptionKey;

import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ExpenseTrackerChangedEvent;
import seedu.address.commons.events.model.UserLoggedInEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.StatsCommand.StatsMode;
import seedu.address.logic.commands.StatsCommand.StatsPeriod;
import seedu.address.model.budget.Budget;
import seedu.address.model.encryption.EncryptedExpenseTracker;
import seedu.address.model.encryption.EncryptionUtil;
import seedu.address.model.exceptions.NoUserSelectedException;
import seedu.address.model.exceptions.NonExistentUserException;
import seedu.address.model.exceptions.UserAlreadyExistsException;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.Expense;
import seedu.address.model.user.Password;
import seedu.address.model.user.Username;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private VersionedExpenseTracker versionedExpenseTracker;
    private FilteredList<Expense> filteredExpenses;

    //Stats related variables
    private StatsPeriod statsPeriod;
    private StatsMode statsMode;
    private Predicate<Expense> expenseStatPredicate;
    private int periodAmount;

    private final Map<Username, EncryptedExpenseTracker> expenseTrackers;

    /**
     * Initializes a ModelManager with the given expenseTrackers and userPrefs.
     */
    public ModelManager(Map<Username, EncryptedExpenseTracker> expenseTrackers, UserPrefs userPrefs) {
        super();
        requireAllNonNull(expenseTrackers, userPrefs);
        this.expenseTrackers = expenseTrackers;
        logger.fine("Initializing with address book: " + expenseTrackers + " and user prefs " + userPrefs);
        this.versionedExpenseTracker = null;
        this.filteredExpenses = null;
        this.statsPeriod = defaultStatsPeriod();
        this.statsMode = defaultStatsMode();
        this.expenseStatPredicate = defaultExpensePredicate();
        this.periodAmount = defaultPeriodAmount();
    }

    public ModelManager(ReadOnlyExpenseTracker expenseTracker, UserPrefs userPrefs) {
        super();
        requireAllNonNull(expenseTracker, userPrefs);
        Map<Username, EncryptedExpenseTracker> expenseTrackers = new TreeMap<>();
        logger.fine("Initializing with address book: " + expenseTrackers + " and user prefs " + userPrefs);
        this.expenseTrackers = expenseTrackers;
        try {
            this.expenseTrackers.put(expenseTracker.getUsername(),
                    EncryptionUtil.encryptTracker(expenseTracker));
            this.versionedExpenseTracker = null;
            this.filteredExpenses = null;
            this.statsPeriod = defaultStatsPeriod();
            this.statsMode = defaultStatsMode();
            this.expenseStatPredicate = defaultExpensePredicate();
            loadUserData(expenseTracker.getUsername(), expenseTracker.getPassword().orElse(null),
                    expenseTracker.getEncryptionKey());
        } catch (NonExistentUserException | IllegalValueException e) {
            throw new IllegalStateException();
        }
    }

    public ModelManager() {
        this(new HashMap<>(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyExpenseTracker newData) throws NoUserSelectedException {
        versionedExpenseTracker.resetData(newData);
        try {
            expenseTrackers.replace(this.versionedExpenseTracker.getUsername(),
                    EncryptionUtil.encryptTracker(this.versionedExpenseTracker));
        } catch (IllegalValueException e) {
            throw new IllegalStateException("Illegal values in reset Expense Tracker");
        }
        indicateExpenseTrackerChanged();
    }

    @Override
    public ReadOnlyExpenseTracker getExpenseTracker() throws NoUserSelectedException {
        if (!hasSelectedUser()) {
            throw new NoUserSelectedException();
        }
        return this.versionedExpenseTracker;
    }

    /** Raises an event to indicate the model has changed */
    protected void indicateExpenseTrackerChanged() throws NoUserSelectedException {
        if (!hasSelectedUser()) {
            throw new NoUserSelectedException();
        }
        try {
            raise(new ExpenseTrackerChangedEvent(EncryptionUtil.encryptTracker(versionedExpenseTracker)));
        } catch (IllegalValueException e) {
            throw new IllegalStateException("Illegal value in expense tracker");
        }
    }

    @Override
    public boolean hasExpense(Expense expense) throws NoUserSelectedException {
        requireNonNull(expense);
        if (!hasSelectedUser()) {
            throw new NoUserSelectedException();
        }
        return versionedExpenseTracker.hasExpense(expense);
    }

    @Override
    public void deleteExpense(Expense target) throws NoUserSelectedException {
        versionedExpenseTracker.removeExpense(target);
        indicateExpenseTrackerChanged();
    }

    @Override
    public boolean addExpense(Expense expense) throws NoUserSelectedException {
        boolean budgetNotExceeded = versionedExpenseTracker.addExpense(expense);
        updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
        indicateExpenseTrackerChanged();
        return budgetNotExceeded;
    }

    @Override
    public void updateExpense(Expense target, Expense editedExpense) throws NoUserSelectedException {
        requireAllNonNull(target, editedExpense);

        versionedExpenseTracker.updateExpense(target, editedExpense);
        indicateExpenseTrackerChanged();
    }

    //=========== Filtered Expense List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Expense} backed by the internal list of
     * {@code versionedExpenseTracker}
     */
    @Override
    public ObservableList<Expense> getFilteredExpenseList() throws NoUserSelectedException {
        if (!hasSelectedUser()) {
            throw new NoUserSelectedException();
        }
        return FXCollections.unmodifiableObservableList(filteredExpenses);
    }

    @Override
    public void updateFilteredExpenseList(Predicate<Expense> predicate) throws NoUserSelectedException {
        requireNonNull(predicate);
        if (!hasSelectedUser()) {
            throw new NoUserSelectedException();
        }
        filteredExpenses.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoExpenseTracker() throws NoUserSelectedException {
        if (!hasSelectedUser()) {
            throw new NoUserSelectedException();
        }
        return versionedExpenseTracker.canUndo();
    }

    @Override
    public boolean canRedoExpenseTracker() {
        return versionedExpenseTracker.canRedo();
    }

    @Override
    public void undoExpenseTracker() throws NoUserSelectedException {
        versionedExpenseTracker.undo();
        indicateExpenseTrackerChanged();
    }

    @Override
    public void redoExpenseTracker() throws NoUserSelectedException {
        versionedExpenseTracker.redo();
        indicateExpenseTrackerChanged();
    }

    @Override
    public void commitExpenseTracker() throws NoUserSelectedException {
        if (!hasSelectedUser()) {
            throw new NoUserSelectedException();
        }
        versionedExpenseTracker.commit();
    }

    //@author winsonhys
    //========== Budget ====================================================================

    @Override
    public void modifyMaximumBudget(Budget budget) throws NoUserSelectedException {
        this.versionedExpenseTracker.modifyMaximumBudget(budget);
        indicateExpenseTrackerChanged();
    }

    @Override
    public void setRecurrenceFrequency(long seconds) throws NoUserSelectedException {
        this.versionedExpenseTracker.setRecurrenceFrequency(seconds);
        indicateExpenseTrackerChanged();
    }


    @Override
    public Budget getMaximumBudget() {
        return this.versionedExpenseTracker.getMaximumBudget();
    }

    //@@author jonathantjm
    //=========== Stats =================================================================================
    /**
     * Returns an unmodifiable view of the list of {@code Expense} backed by the internal list of
     * {@code versionedExpenseTracker}, filtered by {@code expenseStatPredicate} and sorted by expense date.
     */
    @Override
    public ObservableList<Expense> getExpenseStats() throws NoUserSelectedException {
        if (!hasSelectedUser()) {
            throw new NoUserSelectedException();
        }
        FilteredList<Expense> filteredList = new FilteredList<>(versionedExpenseTracker.getExpenseList());
        filteredList.setPredicate(expenseStatPredicate);

        SortedList<Expense> sortedList = new SortedList<>(filteredList);
        Comparator<Expense> byDate = (Expense a, Expense b) -> (-1 * Date.compare(a.getDate(), b.getDate()));
        sortedList.setComparator(byDate);

        return FXCollections.unmodifiableObservableList(sortedList);
    }

    @Override
    public void updateExpenseStatsPredicate (Predicate<Expense> predicate) throws NoUserSelectedException {
        if (!hasSelectedUser()) {
            throw new NoUserSelectedException();
        }
        expenseStatPredicate = predicate;
    }

    @Override
    public void updateStatsPeriod(StatsPeriod period) {
        this.statsPeriod = period;
    }

    @Override
    public StatsPeriod getStatsPeriod() {
        return this.statsPeriod;
    }

    @Override
    public void updateStatsMode(StatsMode mode) {
        this.statsMode = mode;
    }

    @Override
    public StatsMode getStatsMode() {
        return this.statsMode;
    }

    @Override
    public void updatePeriodAmount(int periodAmount) {
        this.periodAmount = periodAmount;
    }

    @Override
    public int getPeriodAmount() {
        return this.periodAmount;
    }

    private StatsPeriod defaultStatsPeriod() {
        return StatsPeriod.DAY;
    }

    private StatsMode defaultStatsMode() {
        return StatsMode.TIME;
    }

    private int defaultPeriodAmount() {
        return 7;
    }

    private Predicate <Expense> defaultExpensePredicate() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH, 7 * -1);
        return e -> e.getDate().fullDate.after(now);
    }

    //@@author JasonChong96
    //=========== Login =================================================================================
    @Override
    public boolean loadUserData(Username username, Password password, String plainPassword)
            throws NonExistentUserException {
        requireAllNonNull(username);
        if (!isUserExists(username)) {
            throw new NonExistentUserException(username, expenseTrackers.size());
        }
        if (!expenseTrackers.get(username).isMatchPassword(password)) {
            return false;
        }
        if (hasSelectedUser()) {
            try {
                expenseTrackers.replace(versionedExpenseTracker.getUsername(),
                        EncryptionUtil.encryptTracker(this.versionedExpenseTracker));
            } catch (IllegalValueException e) {
                throw new IllegalStateException("Illegal value in old expense tracker.");
            }
        }

        EncryptedExpenseTracker encryptedTracker = expenseTrackers.get(username);
        String encryptionKey;
        if (!encryptedTracker.getPassword().isPresent()) {
            encryptionKey = DEFAULT_ENCRYPTION_KEY;
        } else {
            requireNonNull(plainPassword);
            encryptionKey = createEncryptionKey(plainPassword);
        }
        try {
            this.versionedExpenseTracker = new VersionedExpenseTracker(encryptedTracker.decryptTracker(encryptionKey));
        } catch (IllegalValueException e) {
            // Either wrong password or invalid expense tracker data associated with the user.
            return false;
        }

        this.filteredExpenses = new FilteredList<>(versionedExpenseTracker.getExpenseList());

        try {
            indicateUserLoggedIn();
            indicateExpenseTrackerChanged();
            checkBudgetRestart();
        } catch (NoUserSelectedException nuse) {
            throw new IllegalStateException(nuse.getMessage());
        }
        return true;
    }

    @Override
    public void unloadUserData() {
        this.versionedExpenseTracker = null;
        this.filteredExpenses = null;
    }

    @Override
    public boolean isUserExists(Username toCheck) {
        return expenseTrackers.containsKey(toCheck);
    }

    @Override
    public boolean isMatchPassword(Optional<Password> toCheck) throws NoUserSelectedException {
        if (!hasSelectedUser()) {
            throw new NoUserSelectedException();
        }
        return versionedExpenseTracker.isMatchPassword(toCheck);
    }

    /** Raises an event to indicate the user has logged in and has been processed by the model*/
    protected void indicateUserLoggedIn() throws NoUserSelectedException {
        if (!hasSelectedUser()) {
            throw new NoUserSelectedException();
        }
        raise(new UserLoggedInEvent(this.versionedExpenseTracker.getUsername()));
    }

    /**
     * Checks if budget is required to restart due to recurrence
     */
    protected void checkBudgetRestart() {
        this.versionedExpenseTracker.getMaximumBudget().checkBudgetRestart();
    }


    @Override
    public Model copy(UserPrefs userPrefs) throws NoUserSelectedException {
        ModelManager copy = new ModelManager(expenseTrackers, userPrefs);
        copy.versionedExpenseTracker = new VersionedExpenseTracker(this.getExpenseTracker());
        copy.filteredExpenses = new FilteredList<>(copy.versionedExpenseTracker.getExpenseList());
        return copy;
    }

    @Override
    public void addUser(Username newUsername) throws UserAlreadyExistsException {
        if (expenseTrackers.putIfAbsent(newUsername,
                new EncryptedExpenseTracker(newUsername, Optional.empty())) != null) {
            throw new UserAlreadyExistsException(newUsername);
        }
    }

    @Override
    public boolean hasSelectedUser() {
        return versionedExpenseTracker != null && filteredExpenses != null;
    }

    @Override
    public void setPassword(Password password, String plainPassword) throws NoUserSelectedException {
        if (!hasSelectedUser()) {
            throw new NoUserSelectedException();
        }
        versionedExpenseTracker.setPassword(password);
        versionedExpenseTracker.setEncryptionKey(createEncryptionKey(plainPassword));
        indicateExpenseTrackerChanged();
        try {
            expenseTrackers.replace(this.versionedExpenseTracker.getUsername(),
                    EncryptionUtil.encryptTracker(this.versionedExpenseTracker));
        } catch (IllegalValueException e) {
            throw new IllegalStateException("Illegal key created for current expense tracker.");
        }
    }
    //@@author

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedExpenseTracker.equals(other.versionedExpenseTracker)
                && filteredExpenses.equals(other.filteredExpenses);
    }

}
