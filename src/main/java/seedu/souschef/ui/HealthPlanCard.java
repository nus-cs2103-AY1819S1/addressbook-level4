package seedu.souschef.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.souschef.model.healthplan.HealthPlan;

/**
 *  healthPlan individual data frame
 */
public class HealthPlanCard extends GenericCard<HealthPlan> {

    private static final String FXML = "HealthPlanListCard.fxml";

    public final HealthPlan healthPlan;


    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label cweight;
    @FXML
    private Label cheight;
    @FXML
    private Label tweight;
    @FXML
    private Label duration;
    @FXML
    private Label scheme;
    @FXML
    private Label age;


    public HealthPlanCard(HealthPlan healthPlan, int displayedIndex) {
        super(FXML);
        this.healthPlan = healthPlan;
        id.setText(displayedIndex + ". ");
        name.setText(healthPlan.getHealthPlanName().planName);
        cweight.setText(healthPlan.getCurrentWeight().value);
        cheight.setText(healthPlan.getCurrentHeight().value);
        tweight.setText(healthPlan.getTargetWeight().value);
        duration.setText(healthPlan.getDuration().value);
        scheme.setText(healthPlan.getScheme().toString());
        age.setText(healthPlan.getAge().value);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HealthPlanCard)) {
            return false;
        }

        // state check
        HealthPlanCard card = (HealthPlanCard) other;
        return id.getText().equals(card.id.getText())
                && healthPlan.equals(card.healthPlan);
    }


}
