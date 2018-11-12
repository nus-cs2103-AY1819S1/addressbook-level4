package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.simplejavamail.email.Email;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import net.fortuna.ical4j.model.Calendar;
import seedu.address.MainApp;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.AllDayEventAddedEvent;
import seedu.address.commons.events.model.BudgetBookChangedEvent;
import seedu.address.commons.events.model.CalendarCreatedEvent;
import seedu.address.commons.events.model.CalendarEventAddedEvent;
import seedu.address.commons.events.model.CalendarEventDeletedEvent;
import seedu.address.commons.events.model.EmailLoadedEvent;
import seedu.address.commons.events.model.EmailSavedEvent;
import seedu.address.commons.events.model.ExportAddressBookEvent;
import seedu.address.commons.events.model.LoadCalendarEvent;
import seedu.address.commons.events.storage.CalendarLoadedEvent;
import seedu.address.commons.events.storage.EmailDeleteEvent;
import seedu.address.commons.events.storage.RemoveExistingCalendarInModelEvent;
import seedu.address.commons.events.ui.CalendarViewEvent;
import seedu.address.commons.events.ui.EmailViewEvent;
import seedu.address.commons.events.ui.ToggleBrowserPlaceholderEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final VersionedBudgetBook versionedBudgetBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Cca> filteredCcas;
    private final EmailModel emailModel;
    private final UserPrefs userPrefs;
    private final CalendarModel calendarModel;

    /**
     * Initializes a ModelManager with the given addressBook, budgetBook, userPrefs, and emailNamesSet.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyBudgetBook budgetBook, UserPrefs userPrefs,
                        Set<String> emailNamesSet) {
        super();
        requireAllNonNull(addressBook, userPrefs, emailNamesSet);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        versionedBudgetBook = new VersionedBudgetBook(budgetBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredCcas = new FilteredList<>(versionedBudgetBook.getCcaList());
        this.emailModel = new EmailModel(emailNamesSet);
        this.userPrefs = userPrefs;
        this.calendarModel = new CalendarModel(userPrefs.getExistingCalendar());

    }

    public ModelManager() {
        this(new AddressBook(), new BudgetBook(), new UserPrefs(), new HashSet<>());
    }

    /**
     * Constructor used for testing Address Book Commands.
     *
     * @param addressBook the address book with the typical {@code Person}
     * @param userPrefs the user preference
     */
    public ModelManager(AddressBook addressBook, UserPrefs userPrefs) {
        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        versionedBudgetBook = new VersionedBudgetBook(new BudgetBook());
        filteredCcas = new FilteredList<>(versionedBudgetBook.getCcaList());
        emailModel = new EmailModel();
        this.userPrefs = userPrefs;
        this.calendarModel = new CalendarModel(userPrefs.getExistingCalendar());
    }

    /**
     * Constructor used for testing Budget Book Commands.
     *
     * @param budgetBook the budget book with the typical {@code Cca}
     * @param userPrefs the user preference
     */
    public ModelManager(BudgetBook budgetBook, UserPrefs userPrefs) {
        versionedAddressBook = new VersionedAddressBook(new AddressBook());
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        versionedBudgetBook = new VersionedBudgetBook(budgetBook);
        filteredCcas = new FilteredList<>(versionedBudgetBook.getCcaList());
        emailModel = new EmailModel();
        this.userPrefs = userPrefs;
        this.calendarModel = new CalendarModel(userPrefs.getExistingCalendar());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public ReadOnlyBudgetBook getBudgetBook() {
        return versionedBudgetBook;
    }

    @Override
    public CalendarModel getCalendarModel() {
        return calendarModel;
    }

    @Override
    public Set<String> getExistingEmails() {
        return emailModel.getExistingEmails();
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    //@@author ericyjw
    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateBudgetBookChanged() {
        raise(new BudgetBookChangedEvent(versionedBudgetBook));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public boolean hasPerson(Name person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void initialiseBudgetBook() {
        Path ccabookXmlFilePath = Paths.get("data", "ccabook.xml");
        requireNonNull(ccabookXmlFilePath);

        if (!Files.exists(ccabookXmlFilePath)) {
            raise(new BudgetBookChangedEvent(versionedBudgetBook));
        }
    }


    @Override
    public boolean hasCca(Person person) {
        requireNonNull(person);
        return versionedBudgetBook.hasCca(person);
    }

    @Override
    public boolean hasCca(CcaName ccaName) {
        requireNonNull(ccaName);
        return versionedBudgetBook.hasCca(ccaName);
    }

    @Override
    public boolean hasCca(Cca cca) {
        requireNonNull(cca);
        return versionedBudgetBook.hasCca(cca);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteCca(Cca target) {
        versionedBudgetBook.removeCca(target);
        indicateBudgetBookChanged();
    }

    //@@author kengwoon
    @Override
    public void clearMultiplePersons(List<Person> target) {
        versionedAddressBook.removeMultiplePersons(target);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    //@@author kengwoon
    @Override
    public void removeTagsFromPersons(List<Person> editedPersons, List<Person> targets) {
        requireAllNonNull(editedPersons, targets);

        versionedAddressBook.updateMultiplePersons(editedPersons, targets);
        updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    //@@author kengwoon
    @Override
    public void addMultiplePersons(List<Person> personList) {
        versionedAddressBook.addMultiplePersons(personList);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    //@@author ericyjw
    @Override
    public void addCca(Cca cca) {
        versionedBudgetBook.addCca(cca);
        updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        indicateBudgetBookChanged();
    }

    //@@author kengwoon
    @Override
    public void addMultipleCcas(List<Cca> ccaList) {
        versionedBudgetBook.addMultipleCcas(ccaList);
        indicateBudgetBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void updateCca(Cca target, Cca editedCca) {
        requireAllNonNull(target, editedCca);

        versionedBudgetBook.updateCca(target, editedCca);
        indicateBudgetBookChanged();
    }

    //@@author kengwoon
    @Override
    public void updateMultipleCcas(List<Cca> target, List<Cca> editedCca) {
        for (int i = 0; i < target.size(); i++) {
            updateCca(target.get(i), editedCca.get(i));
        }
        indicateBudgetBookChanged();
    }

    //@@author kengwoon
    @Override
    public void updateMultiplePersons(List<Person> target, List<Person> editedPerson) {
        for (int i = 0; i < target.size(); i++) {
            updatePerson(target.get(i), editedPerson.get(i));
        }
        indicateAddressBookChanged();
    }

    //@@author kengwoon
    @Override
    public void exportAddressBook(Path filePath) {
        raise(new ExportAddressBookEvent(getAddressBook(), filePath));
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public ObservableList<Cca> getFilteredCcaList() {
        return FXCollections.unmodifiableObservableList(filteredCcas);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredCcaList(Predicate<Cca> predicate) {
        requireNonNull(predicate);
        filteredCcas.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public void commitBudgetBook() {
        versionedBudgetBook.commit();
    }

    //@@author GilgameshTC
    //=========== Calendar =================================================================================

    /**
     * Raises an event to indicate the calendar model has changed
     */
    private void indicateCalendarModelChanged(Calendar calendar, String calendarName) {
        raise(new CalendarCreatedEvent(calendar, calendarName));
    }

    /**
     * Raises an event to indicate a request for calendar to be loaded
     */
    private void indicateLoadCalendar(String calendarName) {
        raise(new LoadCalendarEvent(calendarName));
    }

    /**
     * Raises an event to indicate that an all day event was created
     */
    private void indicateAllDayEventCreated(Year year, Month month, int date, String title,
                                            Calendar calendarToBeLoaded) {
        raise(new AllDayEventAddedEvent(year, month, date, title, calendarToBeLoaded));
    }

    /**
     * Raises an event to indicate that an event was created
     */
    private void indicateCalendarEventCreated(Year year, Month month, int startDate, int startHour, int startMin,
                                              int endDate, int endHour, int endMin, String title,
                                              Calendar calendarToBeLoaded) {
        raise(new CalendarEventAddedEvent(year, month, startDate, startHour, startMin,
            endDate, endHour, endMin, title, calendarToBeLoaded));
    }

    /**
     * Raises an event to indicate that an event was deleted
     */
    private void indicateCalendarEventDeleted(Year year, Month month, int startDate, int endDate, String title,
                                              Calendar updatedCalendar) {
        raise(new CalendarEventDeletedEvent(year, month, startDate, endDate, title, updatedCalendar));
    }

    /**
     * Raises an event to display calendar on ui
     */
    private void indicateViewCalendar(Calendar calendar, String calendarName) {
        raise(new ToggleBrowserPlaceholderEvent(ToggleBrowserPlaceholderEvent.CALENDAR_PANEL));
        raise(new CalendarViewEvent(calendar, calendarName));
    }

    @Override
    @Subscribe
    public void handleCalendarLoadedEvent(CalendarLoadedEvent event) {
        calendarModel.loadCalendar(event.calendar, event.calendarName);
        indicateViewCalendar(event.calendar, event.calendarName);
    }

    @Override
    @Subscribe
    public void handleRemoveExistingCalendarInModelEvent(RemoveExistingCalendarInModelEvent event) {
        calendarModel.removeExistingCalendar(event.year, event.month);
        updateExistingCalendar();
    }

    @Override
    public boolean isExistingCalendar(Year year, Month month) {
        requireAllNonNull(year, month);
        return calendarModel.isExistingCalendar(year, month);
    }

    @Override
    public boolean isLoadedCalendar(Year year, Month month) {
        requireAllNonNull(year, month);
        return calendarModel.isLoadedCalendar(year, month);
    }

    @Override
    public boolean isValidDate(Year year, Month month, int date) {
        requireAllNonNull(year, month, date);
        return calendarModel.isValidDate(year, month, date);
    }

    @Override
    public boolean isValidTime(int hour, int minute) {
        requireAllNonNull(hour, minute);
        return calendarModel.isValidTime(hour, minute);
    }

    @Override
    public boolean isValidTimeFrame(int startDate, int endDate) {
        return calendarModel.isValidTimeFrame(startDate, 0, 0, endDate, 1, 0);
    }

    @Override
    public boolean isValidTimeFrame(int startDate, int startHour, int startMinute,
                                    int endDate, int endHour, int endMinute) {
        requireAllNonNull(startDate, startHour, startMinute, endDate, endHour, endMinute);
        return calendarModel.isValidTimeFrame(startDate, startHour, startMinute, endDate, endHour, endMinute);
    }

    @Override
    public void createCalendar(Year year, Month month) {
        try {
            Calendar calendar = calendarModel.createCalendar(year, month);
            updateExistingCalendar();
            String calendarName = month + "-" + year;
            indicateCalendarModelChanged(calendar, calendarName);
        } catch (IOException e) {
            logger.warning("Failed to save calendar(ics) file : " + StringUtil.getDetails(e));
        }
    }

    @Override
    public void loadCalendar(Year year, Month month) {
        requireAllNonNull(year, month);
        String calendarName = month + "-" + year;
        indicateLoadCalendar(calendarName);
    }

    @Override
    public void createAllDayEvent(Year year, Month month, int date, String title) {
        try {
            Calendar calendarToBeLoaded = calendarModel.createAllDayEvent(year, month, date, title);
            String calendarName = month + "-" + year;
            indicateAllDayEventCreated(year, month, date, title, calendarToBeLoaded);
            indicateViewCalendar(calendarToBeLoaded, calendarName);
        } catch (IOException e) {
            logger.warning("Failed to create all day event : " + StringUtil.getDetails(e));
        }
    }

    @Override
    public void createEvent(Year year, Month month, int startDate, int startHour, int startMin,
                            int endDate, int endHour, int endMin, String title) {
        try {
            Calendar calendarToBeLoaded = calendarModel.createEvent(year, month, startDate,
                startHour, startMin, endDate, endHour, endMin, title);
            String calendarName = month + "-" + year;
            indicateCalendarEventCreated(year, month, startDate, startHour, startMin, endDate, endHour, endMin, title,
                calendarToBeLoaded);
            indicateViewCalendar(calendarToBeLoaded, calendarName);
        } catch (IOException e) {
            logger.warning("Failed to create event : " + StringUtil.getDetails(e));
        }
    }

    @Override
    public boolean isExistingEvent(Year year, Month month, int startDate, int endDate, String title) {
        requireAllNonNull(year, month, startDate, endDate, title);
        return calendarModel.isExistingEvent(startDate, endDate, title);

    }

    @Override
    public void deleteEvent(Year year, Month month, int startDate, int endDate, String title) {
        Calendar updatedCalender = calendarModel.deleteEvent();
        String calendarName = month + "-" + year;
        indicateCalendarEventDeleted(year, month, startDate, endDate, title, updatedCalender);
        indicateViewCalendar(updatedCalender, calendarName);
    }

    @Override
    public void updateExistingCalendar() {
        userPrefs.setExistingCalendar(calendarModel.getExistingCalendar());
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
        if (filteredPersons == null) {
            return versionedAddressBook.equals(other.versionedAddressBook)
                    && calendarModel.equals(other.calendarModel)
                    && emailModel.equals(other.emailModel);
        } else if (calendarModel == null) {
            return versionedAddressBook.equals(other.versionedAddressBook)
                    && filteredPersons.equals(other.filteredPersons)
                    && emailModel.equals(other.emailModel);
        }
        return versionedAddressBook.equals(other.versionedAddressBook)
            && filteredPersons.equals(other.filteredPersons)
            && calendarModel.equals(other.calendarModel)
            && emailModel.equals(other.emailModel);
    }

    //@@author EatOrBeEaten
    //=========== Email =================================================================================

    @Override
    public void saveEmail(Email email) {
        emailModel.saveEmail(email);
        indicateEmailSaved();
    }

    @Override
    public void saveComposedEmail(Email email) {
        emailModel.saveComposedEmail(email);
        indicateEmailSaved();
    }

    @Override
    public void saveComposedEmailWithoutDisplay(Email email) {
        emailModel.saveComposedEmail(email);
        indicateEmailSavedWithoutDisplay();
    }

    @Override
    public void deleteEmail(String fileName) {
        emailModel.removeFromExistingEmails(fileName);
        raise(new EmailDeleteEvent(fileName));
    }

    @Override
    public boolean hasEmail(String fileName) {
        requireNonNull(fileName);
        return emailModel.hasEmail(fileName);
    }

    /**
     * Raises an event to indicate that a new email has been saved to EmailModel.
     */
    private void indicateEmailSavedWithoutDisplay() {
        raise(new EmailSavedEvent(emailModel));
    }

    /**
     * Raises an event to indicate that a new email has been saved to EmailModel, and displays it on the BrowserPanel.
     */
    private void indicateEmailSaved() {
        raise(new EmailSavedEvent(emailModel));
        raise(new ToggleBrowserPlaceholderEvent(ToggleBrowserPlaceholderEvent.BROWSER_PANEL));
        raise(new EmailViewEvent(emailModel));
    }

    @Override
    @Subscribe
    public void handleEmailLoadedEvent(EmailLoadedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Email loaded, saving to EmailModel."));
        saveEmail(event.data);
        raise(new ToggleBrowserPlaceholderEvent(ToggleBrowserPlaceholderEvent.BROWSER_PANEL));
        raise(new EmailViewEvent(emailModel));
    }

    //@@author ericyjw
    @Override
    public void readXslFile() {
        Path ccaXslFilePath = Paths.get("data", "ccabook.xsl");
        requireNonNull(ccaXslFilePath);

        if (!Files.exists(ccaXslFilePath)) {

            try {
                InputStream is = MainApp.class.getResourceAsStream("/ccabook.xsl");

                File dir = new File("data");
                dir.mkdirs();

                Files.copy(is, Paths.get("data", "ccabook.xsl"));
            } catch (IOException e) {
                logger.warning("An error occurred copying the resource!");
            }
            //            } catch (NullPointerException e) {
            //                logger.warning("Null pointer exception - no such path");
            //            }

        }
    }

}
