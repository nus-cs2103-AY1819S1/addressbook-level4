package seedu.scheduler.ui;

import static org.junit.Assert.assertEquals;
import static seedu.scheduler.testutil.EventsUtil.postNow;
import static seedu.scheduler.testutil.TypicalEvents.getTypicalEvents;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.scheduler.ui.testutil.GuiTestAssert.assertCardDisplaysEvent;
import static seedu.scheduler.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.EventListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.scheduler.commons.events.ui.JumpToListRequestEvent;
import seedu.scheduler.commons.util.FileUtil;
import seedu.scheduler.commons.util.XmlUtil;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.storage.XmlSerializableScheduler;

public class EventListPanelTest extends GuiUnitTest {
    private static final ObservableList<Event> TYPICAL_EVENTS =
            FXCollections.observableList(getTypicalEvents());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_EVENT);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private EventListPanelHandle eventListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_EVENTS);

        for (int i = 0; i < TYPICAL_EVENTS.size(); i++) {
            eventListPanelHandle.navigateToCard(TYPICAL_EVENTS.get(i));
            Event expectedEvent = TYPICAL_EVENTS.get(i);
            EventCardHandle actualCard = eventListPanelHandle.getEventCardHandle(i);

            assertCardDisplaysEvent(expectedEvent, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_EVENTS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        EventCardHandle expectedEvent = eventListPanelHandle.getEventCardHandle(INDEX_SECOND_EVENT.getZeroBased());
        EventCardHandle selectedEvent = eventListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedEvent, selectedEvent);
    }

    /**
     * Verifies that creating and deleting large number of events in {@code EventListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    /**
    @Test
    public void performanceTest() throws Exception {
        ObservableList Event backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of event cards exceeded time limit");
    }
    **/
    /**
     * Returns a list of events containing {@code eventCount} events that is used to populate the
     * {@code EventListPanel}.
     */
    private ObservableList<Event> createBackingList(int eventCount) throws Exception {
        Path xmlFile = createXmlFileWithEvents(eventCount);
        XmlSerializableScheduler xmlScheduler =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableScheduler.class);
        return FXCollections.observableArrayList(xmlScheduler.toModelType().getEventList());
    }

    /**
     * Returns a .xml file containing {@code eventCount} events. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithEvents(int eventCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<scheduler>\n");
        for (int i = 0; i < eventCount; i++) {
            builder.append("<events>\n");
            builder.append("<uuid>").append(UUID.randomUUID()).append("</uuid>\n");
            builder.append("<eventName>a</eventName>\n");
            builder.append("<startDateTime>2018-09-21T11:00</startDateTime>\n");
            builder.append("<endDateTime>2018-09-21T12:00</endDateTime>\n");
            builder.append("<description>b</description>\n");
            builder.append("<priority>LOW</priority>\n");
            builder.append("<venue>c</venue>\n");
            builder.append("<repeatType>NONE</repeatType>\n");
            builder.append("<repeatUntilDateTime>2018-09-21T12:00</repeatUntilDateTime>\n");
            builder.append("</events>\n");
        }
        builder.append("</scheduler>\n");

        Path manyEventsFile = Paths.get(TEST_DATA_FOLDER + "manyEvents.xml");
        FileUtil.createFile(manyEventsFile);
        FileUtil.writeToFile(manyEventsFile, builder.toString());
        manyEventsFile.toFile().deleteOnExit();
        return manyEventsFile;
    }

    /**
     * Initializes {@code eventListPanelHandle} with a {@code EventListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code EventListPanel}.
     */
    private void initUi(ObservableList<Event> backingList) {
        EventListPanel eventListPanel = new EventListPanel(backingList);
        uiPartRule.setUiPart(eventListPanel);

        eventListPanelHandle = new EventListPanelHandle(getChildNode(eventListPanel.getRoot(),
                EventListPanelHandle.EVENT_LIST_VIEW_ID));
    }
}
