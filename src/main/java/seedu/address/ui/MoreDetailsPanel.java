package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Person;

/**
 * The More Details Panel of the App.
 */
public class MoreDetailsPanel extends UiPart<Region> {

    private static final String FXML = "MoreDetailsPanel.fxml";
    private static final String DEFAULT_LABEL = "<No student selected>";
    private static final String DEFAULT_TEXT = "<No note found>";
    private static final String DEFAULT_STYLE = "-fx-font-size: 11pt;\n" + "-fx-font-family: \"Segoe UI Semibold\";\n"
        + "-fx-text-fill: white;\n" + "-fx-opacity: 1;";
    private static final Font DEFAULT_FONT = new Font("System", (double) 25);

    // Value that indicates that no student has been selected yet.
    private static final int NONE = -1;

    private final Logger logger = LogsCenter.getLogger(getClass());

    // List of students
    private ObservableList<Person> studentList;
    private ObservableList<Assignment> assignmentList;

    // Current student whose details are being shown
    private Person currentStudent = null;
    private int currentStudentIndex = NONE;

    @FXML
    private TextArea notesText;

    // Left side holds component name, eg. Participation, right side holds mark attained.
    @FXML
    private GridPane components;

    public MoreDetailsPanel(ObservableList<Person> listOfStudents, ObservableList<Assignment> listOfAssignments) {
        super(FXML);
        registerAsAnEventHandler(this);
        this.studentList = listOfStudents;
        this.assignmentList = listOfAssignments;

        // default label
        showDefaultLabel();

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        currentStudent = event.getNewSelection();
        currentStudentIndex = studentList.indexOf(currentStudent);
        // find student index
        display(currentStudentIndex, event); // display student's details in details panel
    }

    @Subscribe
    private void handleAddressbookChangedEvent(AddressBookChangedEvent event) {
        // No student selected yet, don't need to display.
        if (currentStudentIndex == NONE) {
            return;
        }
        display(currentStudentIndex, event); // display student's details in details panel
    }

    @Subscribe
    private void handleSelectedStudentNoteChangeEvent(NewResultAvailableEvent event) throws Exception {
        // displays student again even if note added was to other students
        // prevents further knowledge of event by parsing event message to check for which student changed
        // No student selected yet, don't need to display.
        if (currentStudentIndex == NONE) {
            return;
        }
        display(currentStudentIndex, event); // display student's details in details panel
    }

    /**
     * Displays the student's details in the Details Panel on the bottom right.
     * Student obtained using his/her index to avoid displaying the wrong student when undo/redo executed.
     */
    public void display(int studentIndex, BaseEvent event) {
        Person student = null;
        try {
            student = studentList.get(studentIndex);
            // Keep track of who was just displayed
            currentStudent = student;
        } catch (IndexOutOfBoundsException e) {
            logger.info(LogsCenter.getEventHandlingLogMessage(event, "Displaying default details"));
            showDefault();
        }
        if (student == null) {
            return;
        }
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Displaying student's details"));

        // remove old labels
        components.getChildren().clear();

        setRowsAndColumns();
        showAssignments(student);
        showNote(student);
    }

    private void showNote(Person student) {
        notesText.clear();
        notesText.setText(student.getNote().toString());
    }

    //@@author robertvictoreen
    /**
     * Show the specified student's assignments and marks.
     */
    private void showAssignments(Person student) {
        Label label;
        double assignmentWeight;
        double totalWeight = 0;
        double weightedMarks = 0;

        int row = 1;
        Assignment assignment;
        for (int i = 0; i < assignmentList.size(); i++) {
            assignment = assignmentList.get(i);
            row = i + 1;

            // adding assignment labels (name and deadline)
            String assignmentString = String.format("%d. %s", row, assignment.getName().getValue());
            addLabelToGrid(assignmentString, row, 0);
            addLabelToGrid(String.valueOf(assignment.getDeadline()), row, 1);

            // assignment weight
            assignmentWeight = assignment.getWeight().getValue();
            totalWeight += assignmentWeight;
            addLabelToGrid(String.valueOf(assignment.getWeight()), row, 2);

            weightedMarks += showMarks(student, assignment, row);

        }

        row++;
        showTotalGrade(weightedMarks, totalWeight, row);
    }

    /**
     * Shows the obtained marks and maximum marks in the given row of the GridPane.
     */
    private void showTotalGrade(double weightedMarks, double totalWeight, int row) {
        Label label = createLabel("Total", DEFAULT_STYLE);
        components.add(label, 0, row);

        String text = String.format("%.1f/%.1f", weightedMarks, totalWeight);
        label = createLabel(text, DEFAULT_STYLE);
        components.add(label, 3, row);
    }

    /**
     * Add label with specified text to GridPane at given row and column.
     */
    private void addLabelToGrid(String text, int row, int column) {
        Label label = createLabel(text, DEFAULT_STYLE);
        components.add(label, column, row);
    }

    /**
     * Shows student's marks in the display at given row for the specified assignment.
     */
    private double showMarks(Person student, Assignment assignment, int row) {
        String text;
        double result = 0;
        double assignmentMark;
        double assignmentMaxMark;
        double assignmentWeight = assignment.getWeight().getValue();
        // adding marks label
        if (student.getMarks().containsKey(assignment.getUniqueId())) {
            assignmentMark = student.getMarks().get(assignment.getUniqueId()).getValue();
            assignmentMaxMark = assignment.getMaxMark().getValue();

            text = String.format("%.1f/%.1f", assignmentMark, assignmentMaxMark);

            assignmentMark /= assignmentMaxMark;
            result += assignmentMark * assignmentWeight;
        } else {
            text = "";
        }
        Label label = createLabel(text, DEFAULT_STYLE);
        components.add(label, 3, row);
        return result;
    }

    /**
     * Set the {@code GridPane}'s rows and columns to Assignment layout.
     */
    private void setRowsAndColumns() {
        Label label = createLabel("Assignments", DEFAULT_STYLE);
        components.add(label, 0, 0);

        label = createLabel("Deadline", DEFAULT_STYLE);
        components.add(label, 1, 0);

        label = createLabel("Weight", DEFAULT_STYLE);
        components.add(label, 2, 0);

        label = createLabel("Grade", DEFAULT_STYLE);
        components.add(label, 3, 0);
    }
    //@@author spencertan96

    /**
     * Resets display to default where no student is selected.
     */
    private void showDefault() {
        showDefaultLabel();
        showDefaultText();
    }

    /**
     * Resets note display to default.
     */
    private void showDefaultText() {
        notesText.clear();
        notesText.setText(DEFAULT_TEXT);
    }

    /**
     * Resets assignments display to default.
     */
    private void showDefaultLabel() {
        components.getChildren().clear();
        Label noComponents = new Label(DEFAULT_LABEL);
        noComponents.setFont(DEFAULT_FONT);
        components.add(noComponents, 0, 0);
    }

    private Label createLabel(String text, String style) {
        Label label = new Label(text);
        label.setStyle(style);
        return label;
    }

    public ObservableList<Person> getList() {
        return studentList;
    }

    public Person getCurrentStudent() {
        return currentStudent;
    }
}
