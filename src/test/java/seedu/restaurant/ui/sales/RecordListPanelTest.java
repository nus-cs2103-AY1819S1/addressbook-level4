package seedu.restaurant.ui.sales;

import static org.junit.Assert.assertEquals;
import static seedu.restaurant.testutil.EventsUtil.postNow;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.restaurant.testutil.sales.TypicalRecords.getTypicalRecords;
import static seedu.restaurant.ui.testutil.GuiTestAssert.assertCardDisplaysRecord;
import static seedu.restaurant.ui.testutil.GuiTestAssert.assertRecordCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.sales.RecordCardHandle;
import guitests.guihandles.sales.RecordListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.restaurant.commons.events.ui.sales.JumpToRecordListRequestEvent;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.ui.GuiUnitTest;

//@@author HyperionNKJ
public class RecordListPanelTest extends GuiUnitTest {

    private static final ObservableList<SalesRecord> TYPICAL_RECORDS =
            FXCollections.observableList(getTypicalRecords());

    private static final JumpToRecordListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToRecordListRequestEvent(INDEX_SECOND);

    private RecordListPanelHandle recordListPanelHandle;

    @Before
    public void setUp() {
        RecordListPanel recordListPanel = new RecordListPanel(TYPICAL_RECORDS);
        uiPartRule.setUiPart(recordListPanel);

        recordListPanelHandle = new RecordListPanelHandle(getChildNode(recordListPanel.getRoot(),
                RecordListPanelHandle.RECORD_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_RECORDS.size(); i++) {
            recordListPanelHandle.navigateToCard(TYPICAL_RECORDS.get(i));
            SalesRecord expectedRecord = TYPICAL_RECORDS.get(i);
            RecordCardHandle actualCard = recordListPanelHandle.getRecordCardHandle(i);

            assertCardDisplaysRecord(expectedRecord, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        RecordCardHandle expectedCard = recordListPanelHandle.getRecordCardHandle(INDEX_SECOND.getZeroBased());
        RecordCardHandle selectedCard = recordListPanelHandle.getHandleToSelectedCard();
        assertRecordCardEquals(expectedCard, selectedCard);
    }
}
