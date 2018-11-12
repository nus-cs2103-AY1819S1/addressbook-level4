package seedu.address.ui.test;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.UiPart;

/**
 * The page that shows up after the user has completed a trivia test.
 */
public class TriviaTestResultPage extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(getClass());

    public TriviaTestResultPage(String filePath) {
        super(filePath);
    }
}
