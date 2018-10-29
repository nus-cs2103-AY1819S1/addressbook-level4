package seedu.address.ui.home;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ImportHelpDisplay extends UiPart<Region> {
    private static final String FXML = "home/ImportHelpDisplay.fxml";
    private static final String IMPORT_FORMAT = " t/topic1 t/topic2\n"
            + "Question1 \tAnswer1\n"
            + "Question2 \tAnswer2\n"
            + "Question3 \tAnswer3\n";

    @FXML
    private TextArea importHelp;

    public ImportHelpDisplay() {
        super(FXML);
        importHelp.setText(IMPORT_FORMAT);
        registerAsAnEventHandler(this);
    }
}
