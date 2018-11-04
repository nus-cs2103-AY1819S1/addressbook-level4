package seedu.address.ui;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.LoginEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class LoginFormTest extends GuiUnitTest {

    public static final String TEST_USERNAME = "testusername";
    public static final String TEST_PASSWORD = "testpassword";
    public static final String USERNAME_ID = "#usernameField";
    public static final String PASSWORD_ID = "#passwordField";
    public static final String BUTTON_ID = "#loginButton";

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private LoginForm loginForm;

    @Before
    public void setUp() {
        loginForm = new LoginForm();
        uiPartRule.setUiPart(loginForm);
    }

    @Test
    public void testButtonClick() {
        fillUpFields();
        Button loginButton = getChildNode(loginForm.getRoot(), BUTTON_ID);
        loginButton.fire();

        verifySuccessful();
    }

    @Test
    public void testUsernameEnter() {
        fillUpFields();
        guiRobot.push(KeyCode.ENTER);
        guiRobot.pauseForHuman();

        verifySuccessful();
    }

    @Test
    public void testPasswordEnter() {
        fillUpFields();
        guiRobot.push(KeyCode.TAB);
        guiRobot.push(KeyCode.ENTER);
        guiRobot.pauseForHuman();

        verifySuccessful();
    }

    /**
     * Fills up the username and the password fields in the login form with the relevant data:
     * TEST_USERNAME, TEST_PASSWORD.
     */
    private void fillUpFields() {
        TextField usernameField = getChildNode(loginForm.getRoot(), USERNAME_ID);
        usernameField.setText(TEST_USERNAME);
        guiRobot.pauseForHuman();
        TextField passwordField = getChildNode(loginForm.getRoot(), PASSWORD_ID);
        passwordField.setText(TEST_PASSWORD);
        guiRobot.pauseForHuman();
    }

    /**
     * Verifies that a LoginEvent has been successfully raised with
     * username = TEST_USERNAME and password = TEST_PASSWORD
     */
    private void verifySuccessful() {
        BaseEvent baseEvent = eventsCollectorRule.eventsCollector.getMostRecent();
        assert baseEvent instanceof LoginEvent;
        LoginEvent loginEvent = (LoginEvent) baseEvent;
        assert loginEvent.getUsername().equals(TEST_USERNAME);
        assert loginEvent.getPassword().equals(TEST_PASSWORD);
    }
}
