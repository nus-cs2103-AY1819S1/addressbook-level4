package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * A UI component that is used to represent a box that provided tips for the user.
 */
public class TipBox extends UiPart<Region> {

    private static final String FXML = "TipBox.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    @FXML
    private Label tip;

    public TipBox(String tipText) {
        super(FXML);

        tip.setText(tipText);
    }
}
