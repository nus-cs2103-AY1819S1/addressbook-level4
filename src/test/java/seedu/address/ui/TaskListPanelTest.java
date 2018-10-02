package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.task.Task;
import seedu.address.storage.XmlSerializableTaskCollection;

public class TaskListPanelTest extends GuiUnitTest {

    private static final ObservableList<Task> TYPICAL_TASKS =
        FXCollections.observableList(getTypicalPersons());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(
        INDEX_SECOND_PERSON);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private TaskListPanelHandle taskListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_TASKS);

        for (int i = 0; i < TYPICAL_TASKS.size(); i++) {
            taskListPanelHandle.navigateToCard(TYPICAL_TASKS.get(i));
            Task expectedTask = TYPICAL_TASKS.get(i);
            TaskCardHandle actualCard = taskListPanelHandle.getPersonCardHandle(i);

            assertCardDisplaysPerson(expectedTask, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_TASKS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        TaskCardHandle expectedPerson = taskListPanelHandle
            .getPersonCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        TaskCardHandle selectedPerson = taskListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPerson, selectedPerson);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code TaskListPanel}
     * requires lesser than {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Task> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of task cards exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code personCount} persons that is used to populate the
     * {@code TaskListPanel}.
     */
    private ObservableList<Task> createBackingList(int personCount) throws Exception {
        Path xmlFile = createXmlFileWithPersons(personCount);
        XmlSerializableTaskCollection xmlAddressBook =
            XmlUtil.getDataFromFile(xmlFile, XmlSerializableTaskCollection.class);
        return FXCollections.observableArrayList(xmlAddressBook.toModelType().getTaskList());
    }

    /**
     * Returns a .xml file containing {@code personCount} persons. This file will be deleted when
     * the JVM terminates.
     */
    private Path createXmlFileWithPersons(int personCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<taskcollection>\n");
        for (int i = 0; i < personCount; i++) {
            builder.append("<tasks>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<email>a@aa</email>\n");
            builder.append("<address>a</address>\n");
            builder.append("</tasks>\n");
        }
        builder.append("</taskcollection>\n");

        Path manyPersonsFile = Paths.get(TEST_DATA_FOLDER + "manyPersons.xml");
        FileUtil.createFile(manyPersonsFile);
        FileUtil.writeToFile(manyPersonsFile, builder.toString());
        manyPersonsFile.toFile().deleteOnExit();
        return manyPersonsFile;
    }

    /**
     * Initializes {@code taskListPanelHandle} with a {@code TaskListPanel} backed by {@code
     * backingList}. Also shows the {@code Stage} that displays only {@code TaskListPanel}.
     */
    private void initUi(ObservableList<Task> backingList) {
        TaskListPanel taskListPanel = new TaskListPanel(backingList);
        uiPartRule.setUiPart(taskListPanel);
        taskListPanelHandle = new TaskListPanelHandle(getChildNode(taskListPanel.getRoot(),
            TaskListPanelHandle.PERSON_LIST_VIEW_ID));
    }
}
