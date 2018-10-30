package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
import seedu.address.logic.commands.StatsCommand.StatsMode;
import seedu.address.model.budget.Budget;
import seedu.address.model.exceptions.NoUserSelectedException;
import seedu.address.model.exceptions.NonExistentUserException;
import seedu.address.model.exceptions.UserAlreadyExistsException;
import seedu.address.model.expense.Category;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.Expense;
import seedu.address.model.notification.Notification;
import seedu.address.model.notification.NotificationHandler;
import seedu.address.model.notification.TipNotification;
import seedu.address.model.notification.Tips;
import seedu.address.model.notification.WarningNotification;
import seedu.address.model.user.Password;
import seedu.address.model.user.Username;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static Tips tips;

    private VersionedExpenseTracker versionedExpenseTracker;
    private FilteredList<Expense> filteredExpenses;
    private HashMap<String, Double> categories;
    private Username username;

    private StatsMode statsMode;
    private Predicate<Expense> expenseStatPredicate;

    private final Map<Username, ReadOnlyExpenseTracker> expenseTrackers;
    /**
     * Initializes a ModelManager with the given expenseTrackers and userPrefs.
     */
    public ModelManager(Map<Username, ReadOnlyExpenseTracker> expenseTrackers, UserPrefs userPrefs, Tips tips) {
        super();
        requireAllNonNull(expenseTrackers, userPrefs);
        this.expenseTrackers = expenseTrackers;
        logger.fine("Initializing with address book: " + expenseTrackers + " and user prefs " + userPrefs);
        this.username = null;
        this.versionedExpenseTracker = null;
        this.filteredExpenses = null;
        this.categories = initializeCategories();
        this.tips = tips;
    }

    public ModelManager(ReadOnlyExpenseTracker expenseTracker, UserPrefs userPrefs) {
        super();
        requireAllNonNull(expenseTracker, userPrefs);
        Map<Username, ReadOnlyExpenseTracker> expenseTrackers = new TreeMap<>();
        logger.fine("Initializing with address book: " + expenseTrackers + " and user prefs " + userPrefs);
        this.expenseTrackers = expenseTrackers;
        this.expenseTrackers.put(expenseTracker.getUsername(), expenseTracker);
        this.username = expenseTracker.getUsername();
        this.versionedExpenseTracker = null;
        this.filteredExpenses = null;
        this.categories = initializeCategories();
        this.tips = new Tips();
        try {
            loadUserData(expenseTracker.getUsername(), expenseTracker.getPassword());
        } catch (NonExistentUserException e) {
            throw new IllegalStateException();
        }
    }

    public ModelManager() {
        this(new HashMap<>(), new UserPrefs(), new Tips());
    }

    @Override
    public void resetData(ReadOnlyExpenseTracker newData) throws NoUserSelectedException {
        versionedExpenseTracker.resetData(newData);
        expenseTrackers.replace(this.username, this.versionedExpenseTracker);
        indicateExpenseTrackerChanged();
    }

    @Override
    public ReadOnlyExpenseTracker getExpenseTracker() throws NoUserSelectedException {
        if (versionedExpenseTracker == null) {
            throw new NoUserSelectedException();
        }
        return this.versionedExpenseTracker;
    }

    /** Raises an event to indicate the model has changed */
    protected void indicateExpenseTrackerChanged() throws NoUserSelectedException {
        if (versionedExpenseTracker == null) {
            throw new NoUserSelectedException();
        }
        raise(new ExpenseTrackerChangedEvent(versionedExpenseTracker));
    }

    @Override
    public boolean hasExpense(Expense expense) throws NoUserSelectedException {
        requireNonNull(expense);
        if (versionedExpenseTracker == null) {
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
        if (filteredExpenses == null) {
            throw new NoUserSelectedException();
        }
        return FXCollections.unmodifiableObservableList(filteredExpenses);
    }

    @Override
    public void updateFilteredExpenseList(Predicate<Expense> predicate) throws NoUserSelectedException {
        requireNonNull(predicate);
        if (filteredExpenses == null) {
            throw new NoUserSelectedException();
        }
        filteredExpenses.setPredicate(predicate);
    }

    //@@author Snookerballs
    //=========== Category HashSet ==========================================================================

    /**
     * temp
     * @param name
     * @param expenseToAdd
     */
    public void addExpensesOfCategories(String name, double expenseToAdd) {
        requireNonNull(categories);
        double newExpense = expenseToAdd;

        if (categories.containsKey(name)) {
            newExpense += categories.get(name);
        }
        categories.put(name, newExpense);
    }

    /**
     * temp
     * @param name
     * @param expenseToAdd
     */
    public void removeExpensesOfCategories(String name, double expenseToAdd) {
        requireNonNull(categories);
        double newExpense = expenseToAdd;

        if (categories.containsKey(name)) {
            newExpense -= categories.get(name);
        }

        if (newExpense == 0) {
            categories.remove(name);
        } else {
            categories.put(name, newExpense);
        }
    }

    @Override
    public Iterator getCategoryList() {
        requireNonNull(categories);
        Set<Map.Entry<String, Double>> categoriesSet = categories.entrySet();
        return categoriesSet.iterator();
    }

    /**
     * temp
     * @return
     */
    public HashMap<String, Double> initializeCategories() {
        HashMap<String, Double> newMap = new HashMap<>();
        for (String name : Category.INITIAL_CATEGORIES) {
            newMap.put(name, 0.0);
        }
        return newMap;
    }

    //@@author Snookerballs
    //=========== Notification =================================================================================
    @Override
    public void addNotification(Notification notification) throws NoUserSelectedException {
        if (versionedExpenseTracker == null) {
            throw new NoUserSelectedException();
        }
        this.versionedExpenseTracker.addNotification(notification);
        indicateExpenseTrackerChanged();
    }

    @Override
    public boolean addWarningNotification() throws NoUserSelectedException {
        if (versionedExpenseTracker == null) {
            throw new NoUserSelectedException();
        }
        boolean isNotificationAdded = this.versionedExpenseTracker.checkIfAddWarningNotification(getMaximumBudget());
        if (isNotificationAdded) {
            this.versionedExpenseTracker.addNotification(new WarningNotification(getMaximumBudget()));
            indicateExpenseTrackerChanged();
        }
        return isNotificationAdded;
    }

    @Override
    public boolean addTipNotification() throws NoUserSelectedException {
        if (versionedExpenseTracker == null) {
            throw new NoUserSelectedException();
        }

        boolean isNotificationAdded = this.versionedExpenseTracker.checkIfAddTipNotification();
        if (isNotificationAdded) {
            this.versionedExpenseTracker.addNotification(new TipNotification(tips));
            indicateExpenseTrackerChanged();
        }
        return isNotificationAdded;
    }

    @Override
    public ObservableList<Notification> getNotificationList() throws NoUserSelectedException {
        if (versionedExpenseTracker == null) {
            throw new NoUserSelectedException();
        }

        return this.versionedExpenseTracker.getNotificationList();
    }

    @Override
    public void toggleTipNotification(boolean toggleOption) throws NoUserSelectedException {
        if (versionedExpenseTracker == null) {
            throw new NoUserSelectedException();
        }
        versionedExpenseTracker.toggleTipNotification(toggleOption);
        indicateExpenseTrackerChanged();
    }

    @Override
    public void toggleWarningNotification(boolean toggleOption) throws NoUserSelectedException {
        if (versionedExpenseTracker == null) {
            throw new NoUserSelectedException();
        }
        versionedExpenseTracker.toggleWarningNotification(toggleOption);
        indicateExpenseTrackerChanged();
    }

    @Override
    public void toggleBothNotification(boolean toggleOption) throws NoUserSelectedException {
        if (versionedExpenseTracker == null) {
            throw new NoUserSelectedException();
        }

        versionedExpenseTracker.toggleTipNotification(toggleOption);
        versionedExpenseTracker.toggleWarningNotification(toggleOption);
        indicateExpenseTrackerChanged();
    }

    @Override
    public NotificationHandler getNotificationHandler() throws NoUserSelectedException {
        if (versionedExpenseTracker == null) {
            throw new NoUserSelectedException();
        }

        return versionedExpenseTracker.getNotificationHandler();
    }

    @Override
    public void modifyNotificationHandler(LocalDateTime time, boolean isTipEnabled, boolean isWarningEnabled)
            throws NoUserSelectedException {
        if (versionedExpenseTracker == null) {
            throw new NoUserSelectedException();
        }
        this.versionedExpenseTracker.modifyNotificationHandler(time, isTipEnabled, isWarningEnabled);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoExpenseTracker() throws NoUserSelectedException {
        if (versionedExpenseTracker == null) {
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
        if (versionedExpenseTracker == null) {
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
        if (this.filteredExpenses == null) {
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
    public void updateExpenseStats(Predicate<Expense> predicate) throws NoUserSelectedException {
        if (filteredExpenses == null) {
            throw new NoUserSelectedException();
        }
        expenseStatPredicate = predicate;
    }

    @Override
    public void updateStatsMode(StatsMode mode) {
        this.statsMode = mode;
    }

    @Override
    public StatsMode getStatsMode() {
        return this.statsMode;
    }

    //@@author JasonChong96
    //=========== Login =================================================================================
    @Override
    public boolean loadUserData(Username username, Optional<Password> password) throws NonExistentUserException {
        if (!isUserExists(username)) {
            throw new NonExistentUserException(username, expenseTrackers.size());
        }
        if (!expenseTrackers.get(username).isMatchPassword(password)) {
            return false;
        }
        if (hasSelectedUser()) {
            expenseTrackers.replace(this.username, this.versionedExpenseTracker);
        }
        this.versionedExpenseTracker = new VersionedExpenseTracker(expenseTrackers.get(username));

        this.filteredExpenses = new FilteredList<>(versionedExpenseTracker.getExpenseList());
        this.username = username;

        try {
            indicateUserLoggedIn();
            indicateExpenseTrackerChanged();
            checkBudgetRestart();
            addTipNotification();
        } catch (NoUserSelectedException nuse) {
            throw new IllegalStateException(nuse.getMessage());
        }
        return true;
    }

    @Override
    public void unloadUserData() {
        this.versionedExpenseTracker = null;
        this.filteredExpenses = null;
        this.username = null;
    }

    @Override
    public boolean isUserExists(Username toCheck) {
        return expenseTrackers.containsKey(toCheck);
    }

    @Override
    public boolean isMatchPassword(Optional<Password> toCheck) throws NoUserSelectedException {
        if (versionedExpenseTracker == null) {
            throw new NoUserSelectedException();
        }
        return versionedExpenseTracker.isMatchPassword(toCheck);
    }

    /** Raises an event to indicate the user has logged in and has been processed by the model*/
    protected void indicateUserLoggedIn() throws NoUserSelectedException {
        if (this.username == null) {
            throw new NoUserSelectedException();
        }
        raise(new UserLoggedInEvent(this.username));
    }

    /**
     * Checks if budget is required to restart due to recurrence
     */
    protected void checkBudgetRestart() {
        this.versionedExpenseTracker.getMaximumBudget().checkBudgetRestart();
    }


    @Override
    public Model copy(UserPrefs userPrefs) throws NoUserSelectedException {
        ModelManager copy = new ModelManager(expenseTrackers, userPrefs, tips);
        copy.versionedExpenseTracker = new VersionedExpenseTracker(this.getExpenseTracker());
        copy.filteredExpenses = new FilteredList<>(copy.versionedExpenseTracker.getExpenseList());
        copy.username = this.username;
        return copy;
    }

    @Override
    public void addUser(Username newUsername) throws UserAlreadyExistsException {
        if (expenseTrackers.putIfAbsent(newUsername, new ExpenseTracker(newUsername, Optional.empty())) != null) {
            throw new UserAlreadyExistsException(newUsername);
        }
    }

    @Override
    public boolean hasSelectedUser() {
        return versionedExpenseTracker != null && filteredExpenses != null && username != null;
    }

    @Override
    public void setPassword(Password password) throws NoUserSelectedException {
        if (this.versionedExpenseTracker == null) {
            throw new NoUserSelectedException();
        }
        versionedExpenseTracker.password = Optional.ofNullable(password);
        expenseTrackers.replace(this.username, this.versionedExpenseTracker);
    }

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
