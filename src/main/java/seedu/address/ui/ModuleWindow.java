package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

public class ModuleWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(ModuleWindow.class);
    public static final String FXML = "ModuleWindow.fxml";


    public ModuleWindow(Stage root) {
        super(FXML, root);
    }

    public ModuleWindow() {
        this(new Stage());
    }

    public void show() {
        logger.fine("Showing module page of the application");
    }

    public boolean isShowing() {
        return getRoot().isShowing();
    }

    public void focus() {
        getRoot().requestFocus();
    }
}
