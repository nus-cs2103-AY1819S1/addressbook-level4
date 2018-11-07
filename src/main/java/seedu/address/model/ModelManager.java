package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.CalendarChangedEvent;
import seedu.address.commons.events.model.StudentChangedEvent;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.mark.Mark;
import seedu.address.model.student.Student;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Student> filteredStudents;
    private final ObservableList<Mark> marks;

    private final VersionedCalendar versionedCalendar;
    private final FilteredList<Event> filteredEvents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyCalendar calendar, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredStudents = new FilteredList<>(versionedAddressBook.getStudentList());
        marks = FXCollections.observableArrayList();

        versionedCalendar = new VersionedCalendar(calendar);
        filteredEvents = new FilteredList<>(versionedCalendar.getEventList());
    }

    public ModelManager() {
        this(new AddressBook(), new Calendar(), new UserPrefs());
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

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return versionedAddressBook.hasStudent(student);
    }

    @Override
    public void deleteStudent(Student target) {
        versionedAddressBook.removeStudent(target);
        indicateAddressBookChanged();
        indicateStudentDeleted(target);
    }

    @Override
    public void addStudent(Student student) {
        versionedAddressBook.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENT);
        indicateAddressBookChanged();
    }

    @Override
    public void updateStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        versionedAddressBook.updateStudent(target, editedStudent);
        indicateAddressBookChanged();
        indicateStudentUpdated(target, editedStudent);
    }

    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return FXCollections.unmodifiableObservableList(filteredStudents);
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    //=========== Undo/Redo Address Book =====================================================================

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

    //=========== Event and Scheduling ======================================================================

    @Override
    public ReadOnlyCalendar getCalendar() {
        return versionedCalendar;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateCalendarChanged() {
        raise(new CalendarChangedEvent(versionedCalendar));
    }


    /** Raises an event to indicate a student has been changed */
    private void indicateStudentDeleted(Student target) {
        raise(new StudentChangedEvent(target, null));
    }

    /** Raises an event to indicate a student has been changed */
    private void indicateStudentUpdated(Student target, Student newStudent) {
        // raise(new StudentChangedEvent(target, newStudent));
        updateMarks(target, newStudent);
    }


    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return versionedCalendar.hasEvent(event);
    }

    @Override
    public void deleteEvent(Event event) {
        versionedCalendar.removeEvent(event);
        indicateCalendarChanged();
    }

    @Override
    public void addEvent(Event event) {
        versionedCalendar.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateCalendarChanged();
    }

    //=========== Undo/Redo Calendar =======================================================================

    @Override
    public boolean canUndoCalendar() {
        return versionedCalendar.canUndo();
    }

    @Override
    public boolean canRedoCalendar() {
        return versionedCalendar.canRedo();
    }

    @Override
    public void undoCalendar() {
        versionedCalendar.undo();
        indicateCalendarChanged();
    }

    @Override
    public void redoCalendar() {
        versionedCalendar.redo();
        indicateCalendarChanged();
    }

    @Override
    public void commitCalendar() {
        versionedCalendar.commit();
    }

    //=========== Filtered Event List Accessors ==============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the internal list of
     * {@code versionedCalendar}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
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

        if (!versionedAddressBook.equals(other.versionedAddressBook)) {
            System.out.println("Diff versionedAddressBook");
            return false;
        }

        if (!filteredStudents.equals(other.filteredStudents)) {
            System.out.println("Diff filteredStudents");
            return false;
        }
        return true;
    }

    public Mark getMark(String markName) throws IllegalArgumentException {
        Mark.checkValidMarkName(markName);
        return marks.stream().filter(m -> m.getName().equals(markName)).findFirst().orElse(Mark.EMPTY);
    }

    public void setMark(String markName, Mark mark) {
        Mark old = getMark(mark.getName());
        if (!old.equals(Mark.EMPTY)) {
            marks.remove(old);
        }
        mark.setName(markName);
        marks.add(mark);
    }

    @Override
    public ObservableList<Mark> getFilteredMarkList() {
        return marks;
    }

    public void updateMarks(Student oldStudent, Student newStudent) {
        marks.forEach((mark) -> {
            Set<Student> set = new HashSet<>(mark.getSet());
            if (set.contains(oldStudent)) {
                set.remove(oldStudent);
                if (newStudent != null) {
                    set.add(newStudent);
                }
                setMark(mark.getName(), new Mark(set, mark.getName()));
            }
        });
    }
}
