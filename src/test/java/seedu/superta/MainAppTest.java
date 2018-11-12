package seedu.superta;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MainAppTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void initConfigFile_null() {
        MainApp app = getApp();
        app.initConfig(null);
    }

    private MainApp getApp() {
        return new TestApp();
    }
}
