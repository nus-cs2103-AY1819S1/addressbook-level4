package seedu.modsuni.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.modsuni.model.module.Module;

/**
 * An UI component that displays information of a {@code Module}.
 */
public class ModuleCard extends UiPart<Region> {

    private static final String FXML = "ModuleListCard.fxml";
    private static final String SEM1 = "Sem1";
    private static final String SEM2 = "Sem2";
    private static final String SPECIAL_TERM1 = "SpecialTerm1";
    private static final String SPECIAL_TERM2 = "SpecialTerm2";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Module module;

    @FXML
    private HBox cardPane;
    @FXML
    private Label code;
    @FXML
    private Label id;
    @FXML
    private Label department;
    @FXML
    private Label title;
    @FXML
    private Label credit;
    @FXML
    private FlowPane availability;

    public ModuleCard(Module module, int displayedIndex) {
        super(FXML);
        this.module = module;
        id.setText(displayedIndex + ". ");
        code.setText(module.getCode().code);
        department.setText(module.getDepartment());
        title.setText(module.getTitle());
        credit.setText(String.valueOf(module.getCredit()).concat(" MCs"));
        if (module.isAvailableInSem1()) {
            availability.getChildren().add(new Label(SEM1));
        }
        if (module.isAvailableInSem2()) {
            availability.getChildren().add(new Label(SEM2));
        }
        if (module.isAvailableInSpecialTerm1()) {
            availability.getChildren().add(new Label(SPECIAL_TERM1));
        }
        if (module.isAvailableInSpecialTerm2()) {
            availability.getChildren().add(new Label(SPECIAL_TERM2));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleCard)) {
            return false;
        }

        // state check
        ModuleCard card = (ModuleCard) other;
        return id.getText().equals(card.id.getText())
                && module.equals(card.module);
    }
}
