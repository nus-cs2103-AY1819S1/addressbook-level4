package seedu.address.logic.commands;

import static seedu.address.testutil.TestUtil.unblockGoogleLogin;

import org.junit.After;

public class DefaultCommandTest {
    @After
    public void cleanUp() {
        unblockGoogleLogin();
    }
}
