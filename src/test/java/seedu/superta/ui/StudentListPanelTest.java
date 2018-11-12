package seedu.superta.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.superta.testutil.EventsUtil.postNow;
import static seedu.superta.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalStudents;
import static seedu.superta.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.superta.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.superta.commons.events.ui.JumpToListRequestEvent;
import seedu.superta.commons.util.FileUtil;
import seedu.superta.commons.util.XmlUtil;
import seedu.superta.model.student.Student;
import seedu.superta.storage.XmlSerializableSuperTaClient;

public class StudentListPanelTest extends GuiUnitTest {
    private static final ObservableList<Student> TYPICAL_STUDENTS =
            FXCollections.observableList(getTypicalStudents());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PERSON);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private PersonListPanelHandle personListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_STUDENTS);

        for (int i = 0; i < TYPICAL_STUDENTS.size(); i++) {
            personListPanelHandle.navigateToCard(TYPICAL_STUDENTS.get(i));
            Student expectedStudent = TYPICAL_STUDENTS.get(i);
            PersonCardHandle actualCard = personListPanelHandle.getPersonCardHandle(i);

            assertCardDisplaysPerson(expectedStudent, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_STUDENTS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        PersonCardHandle expectedPerson = personListPanelHandle.getPersonCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        PersonCardHandle selectedPerson = personListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPerson, selectedPerson);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code StudentListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Student> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of student cards exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code personCount} persons that is used to populate the
     * {@code StudentListPanel}.
     */
    private ObservableList<Student> createBackingList(int personCount) throws Exception {
        Path xmlFile = createXmlFileWithPersons(personCount);
        XmlSerializableSuperTaClient xmlAddressBook =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableSuperTaClient.class);
        return FXCollections.observableArrayList(xmlAddressBook.toModelType().getStudentList());
    }

    /**
     * Returns a .xml file containing {@code personCount} persons. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithPersons(int personCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<addressbook>\n");
        for (int i = 0; i < personCount; i++) {
            builder.append("<persons>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<email>a@aa</email>\n");
            builder.append("<address>a</address>\n");
            builder.append("<studentId>A0123456Y</studentId>");
            builder.append("</persons>\n");
        }
        builder.append("</addressbook>\n");

        Path manyPersonsFile = Paths.get(TEST_DATA_FOLDER + "manyPersons.xml");
        FileUtil.createFile(manyPersonsFile);
        FileUtil.writeToFile(manyPersonsFile, builder.toString());
        manyPersonsFile.toFile().deleteOnExit();
        return manyPersonsFile;
    }

    /**
     * Initializes {@code personListPanelHandle} with a {@code StudentListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code StudentListPanel}.
     */
    private void initUi(ObservableList<Student> backingList) {
        StudentListPanel studentListPanel = new StudentListPanel(backingList);
        uiPartRule.setUiPart(studentListPanel);

        personListPanelHandle = new PersonListPanelHandle(getChildNode(studentListPanel.getRoot(),
                                                                       PersonListPanelHandle.PERSON_LIST_VIEW_ID));
    }
}
