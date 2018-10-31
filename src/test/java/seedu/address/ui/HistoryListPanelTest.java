package seedu.address.ui;

//@@author chivent
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.Test;

import guitests.guihandles.HistoryListPanelHandle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.TransformationEvent;

/**
 * Contains tests for editing list of {@code HistoryListPanel}.
 */
public class HistoryListPanelTest extends GuiUnitTest {

    private static final ArrayList<String> TRANSFORMATION_SAMPLE = new ArrayList<>(
            Arrays.asList("first", "second", "third"));

    private static List<String> addSample = new ArrayList<>(
            Arrays.asList("new 1", "new 2", "new 3", "new 4"));

    private static ObservableList<String> sampleList =
            FXCollections.observableList(TRANSFORMATION_SAMPLE);

    private HistoryListPanelHandle historyListPanelHandle;

    private PriorityQueue<String> redoQueue = new PriorityQueue<>();

    @Test
    public void add() {
        initUi();

        // Assert regular add
        assertAddItem(addSample.get(0));

        // Assert add after add
        assertAddItem(addSample.get(1));

        // Assert add after undo
        undoItem();
        assertAddItem(addSample.get(2));

        // TODO: Assert add after redo
    }

    @Test
    public void undo() {
        initUi();

        // Assert regular undo
        assertUndoItem();

        // Assert undo after undo
        assertUndoItem();

        // Assert undo after add
        addItem(addSample.get(0));
        assertUndoItem();

        // TODO: assert undo after redo
        // TODO: assert undo after redo x2
    }


    //  TODO: add cases for redo
    //  TODO: check selected if possible

    /**
     * Restore item to {@code HistoryListView} and {@code sampleList}
     */
    private void redoItem() {
        sampleList.add(redoQueue.poll());
        postNow(new TransformationEvent(""));
    }

    /**
     * Removes item from {@code HistoryListView} and {@code sampleList}
     */
    private void undoItem() {
        redoQueue.add(sampleList.get(sampleList.size() - 1));
        sampleList.remove(sampleList.size() - 1, sampleList.size());
        postNow(new TransformationEvent(true));
    }

    /**
     * Removes item and asserts to check if added correctly
     */
    private void assertUndoItem() {
        undoItem();
        assertEquals(sampleList, historyListPanelHandle.getItems());
    }

    /**
     * Adds item to {@code HistoryListView} and {@code sampleList}
     */
    private void addItem(String item) {
        sampleList.add(item);
        postNow(new TransformationEvent(item));
    }

    /**
     * Adds item and asserts to check if added correctly
     */
    private void assertAddItem(String item) {
        addItem(item);
        assertEquals(sampleList, historyListPanelHandle.getItems());
    }

    /**
     * Initializes {@code historyListPanelHandle} with a {@code HistoryListPanel} and fills {@code historyListView}
     */
    private void initUi() {
        redoQueue.clear();
        HistoryListPanel historyListPanel = new HistoryListPanel();
        uiPartRule.setUiPart(historyListPanel);
        historyListPanelHandle = new HistoryListPanelHandle(historyListPanel.getRoot());

        for (String item : sampleList) {
            postNow(new TransformationEvent(item));
        }

    }
}
