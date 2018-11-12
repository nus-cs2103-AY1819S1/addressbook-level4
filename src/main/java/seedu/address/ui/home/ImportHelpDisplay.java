package seedu.address.ui.home;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ImportHelpDisplay extends UiPart<Region> {
    private static final String FXML = "home/ImportHelpDisplay.fxml";
    private static final String IMPORT_FORMAT_LABEL_HEADER = "Import File Format";

    @FXML
    private Label importFileFormatHeader;

    public ImportHelpDisplay() {
        super(FXML);
        importFileFormatHeader.setText(IMPORT_FORMAT_LABEL_HEADER);
        registerAsAnEventHandler(this);
    }
}
