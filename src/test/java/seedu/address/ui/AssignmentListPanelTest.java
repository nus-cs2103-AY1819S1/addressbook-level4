package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalAssignment.getTypicalAssignments;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ASSIGNMENT;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysAssignment;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Ignore;
import org.junit.Test;

import guitests.guihandles.AssignmentCardHandle;
import guitests.guihandles.AssignmentListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.project.Assignment;
import seedu.address.storage.XmlSerializableAssignmentList;

public class AssignmentListPanelTest extends GuiUnitTest {

    private static final ObservableList<Assignment> TYPICAL_ASSIGNMENTS =
            FXCollections.observableList(getTypicalAssignments());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToListRequestEvent(INDEX_SECOND_ASSIGNMENT);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private AssignmentListPanelHandle assignmentListPanelHandle;

    @Ignore @Test
    public void display() {
        initUi(TYPICAL_ASSIGNMENTS);

        for (int i = 0; i < TYPICAL_ASSIGNMENTS.size(); i++) {
            assignmentListPanelHandle.navigateToCard(TYPICAL_ASSIGNMENTS.get(i));
            Assignment expectedAssignment = TYPICAL_ASSIGNMENTS.get(i);
            AssignmentCardHandle actualCard = assignmentListPanelHandle.getAssignmentCardHandle(i);

            assertCardDisplaysAssignment(expectedAssignment, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Ignore @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_ASSIGNMENTS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        AssignmentCardHandle expectedAssignment =
                assignmentListPanelHandle.getAssignmentCardHandle(INDEX_SECOND_ASSIGNMENT.getZeroBased());
        AssignmentCardHandle selectedAssignment = assignmentListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedAssignment, selectedAssignment);
    }

    /**
     * Verifies that creating and deleting large number of assignments in {@code AssignmentListPanel} requires
     * lesser than {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Assignment> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of assignment cards exceeded time limit");
    }

    /**
     * Returns a list of assignments containing {@code assignmentCount} assignments that is used to populate the
     * {@code AssignmentListPanel}.
     */
    private ObservableList<Assignment> createBackingList(int assignmentCount) throws Exception {
        Path xmlFile = createXmlFileWithAssignments(assignmentCount);
        XmlSerializableAssignmentList xmlAssignmentList =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableAssignmentList.class);
        return FXCollections.observableArrayList(xmlAssignmentList.toModelType().getAssignmentList());
    }

    /**
     * Returns a .xml file containing {@code assignmentCount} assignments.
     * This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithAssignments(int assignmentCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<assignmentlist>\n");
        for (int i = 0; i < assignmentCount; i++) {
            builder.append("<assignments>\n");
            builder.append("<assignmentName>").append(i).append("a</assignmentName>\n");
            builder.append("<author>Amy Bee</author>\n");
            builder.append("<description>a@aa</description>\n");
            builder.append("</assignments>\n");
        }
        builder.append("</assignmentlist>\n");

        Path manyAssignmentsFile = TEST_DATA_FOLDER.resolve("manyAssignments.xml");
        FileUtil.createFile(manyAssignmentsFile);
        FileUtil.writeToFile(manyAssignmentsFile, builder.toString());
        manyAssignmentsFile.toFile().deleteOnExit();
        return manyAssignmentsFile;
    }

    /**
     * Initializes {@code assignmentsListPanelHandle} with a {@code AssignmentListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code AssignmentListPanel}.
     */
    private void initUi(ObservableList<Assignment> backingList) {
        AssignmentListPanel assignmentListPanel = new AssignmentListPanel(backingList);
        uiPartRule.setUiPart(assignmentListPanel);

        assignmentListPanelHandle = new AssignmentListPanelHandle(getChildNode(assignmentListPanel.getRoot(),
                AssignmentListPanelHandle.ASSIGNMENT_LIST_VIEW_ID));
    }
}
