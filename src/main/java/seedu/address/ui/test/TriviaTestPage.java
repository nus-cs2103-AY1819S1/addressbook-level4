package seedu.address.ui.test;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.UiPart;

/**
 * The page that shows up when the user is in a trivia test.
 */
public abstract class TriviaTestPage extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(getClass());

    public TriviaTestPage(String filePath) {
        super(filePath);
    }
}
