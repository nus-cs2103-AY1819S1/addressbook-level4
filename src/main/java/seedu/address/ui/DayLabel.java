package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;
import seedu.address.commons.core.LogsCenter;

public class DayLabel extends UiPart<Region> {
    private static final String FXML = "DayLabel.fxml";
    private final Logger logger = LogsCenter.getLogger(DayLabel.class);

    @FXML
    private Label dayLabel;

    public DayLabel(SimpleStringProperty dayLabelText) {
        super(FXML);
        registerAsAnEventHandler(this);
        dayLabel.textProperty().bind(dayLabelText);
    }

    public DayLabel(String text) {
        super(FXML);
        registerAsAnEventHandler(this);
        dayLabel.setText(text);
    }
}
