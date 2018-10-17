package seedu.address.ui;

import static seedu.address.testutil.EventsUtil.postNow;

import org.junit.Before;
import org.junit.Test;

class LoginFormTest extends GuiUnitTest {
    @Before
    public void setUp() {
        ResultDisplay resultDisplay = new ResultDisplay();
        uiPartRule.setUiPart(resultDisplay);


    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        //assertEquals("", resultDisplayHandle.getText());

        // new result received
        //postNow(NEW_RESULT_EVENT_STUB);
        guiRobot.pauseForHuman();
        //assertEquals(NEW_RESULT_EVENT_STUB.message, resultDisplayHandle.getText());
    }
}