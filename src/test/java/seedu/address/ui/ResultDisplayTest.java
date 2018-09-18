package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ResultDisplayHandle;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

public class ResultDisplayTest extends GuiUnitTest {

    private static final NewResultAvailableEvent NEW_RESULT_VALID_EVENT_STUB =
            new NewResultAvailableEvent("Stub", true);
    private static final NewResultAvailableEvent NEW_RESULT_INVALID_EVENT_STUB =
            new NewResultAvailableEvent("Stub", false);

    private ResultDisplayHandle resultDisplayHandle;

    private ArrayList<String> defaultStyleOfResultDisplay;
    private ArrayList<String> errorStyleOfResultDisplay;

    @Before
    public void setUp() {
        ResultDisplay resultDisplay = new ResultDisplay();
        uiPartRule.setUiPart(resultDisplay);

        resultDisplayHandle = new ResultDisplayHandle(getChildNode(resultDisplay.getRoot(),
                ResultDisplayHandle.RESULT_DISPLAY_ID));

        defaultStyleOfResultDisplay = new ArrayList<>(resultDisplayHandle.getStyleClass());

        errorStyleOfResultDisplay = new ArrayList<>(defaultStyleOfResultDisplay);
        errorStyleOfResultDisplay.add(ResultDisplay.ERROR_STYLE_CLASS);
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", resultDisplayHandle.getText());
        assertEquals(defaultStyleOfResultDisplay, resultDisplayHandle.getStyleClass());

        // valid event received
        assertResultDisplayStyle(NEW_RESULT_VALID_EVENT_STUB);
        assertResultDisplayStyle(NEW_RESULT_INVALID_EVENT_STUB);
    }

    /**
     * Verifies that a valid event has the default result display style
     * and an invalid event has the error style.
     * @param event is either a valid or invalid event that is posted to the event bus.
     */
    private void assertResultDisplayStyle(NewResultAvailableEvent event) {
        postNow(event);
        guiRobot.pauseForHuman();
        assertEquals(event.message, resultDisplayHandle.getText());
        if (event.isValid) {
            assertEquals(defaultStyleOfResultDisplay, resultDisplayHandle.getStyleClass());
        } else {
            assertEquals(errorStyleOfResultDisplay, resultDisplayHandle.getStyleClass());
        }
    }
}
