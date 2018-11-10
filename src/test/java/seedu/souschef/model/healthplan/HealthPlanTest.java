package seedu.souschef.model.healthplan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import seedu.souschef.model.planner.Day;
import seedu.souschef.testutil.HealthPlanBuilder;

public class HealthPlanTest {

    //plan A details
    private HealthPlanName aName = new HealthPlanName("A");
    private TargetWeight aTargetWeight = new TargetWeight("60.0");
    private CurrentWeight aCurrentWeight = new CurrentWeight("50.0");
    private CurrentHeight aCurrentHeight = new CurrentHeight("100");
    private Age aAge = new Age("15");
    private Duration aDuration = new Duration("10");

    //plan B details
    private HealthPlanName bName = new HealthPlanName("B");
    private TargetWeight bTargetWeight = new TargetWeight("70.0");
    private CurrentWeight bCurrentWeight = new CurrentWeight("60.0");
    private CurrentHeight bCurrentHeight = new CurrentHeight("150");
    private Age bAge = new Age("20");
    private Duration bDuration = new Duration("15");


    private HealthPlan healthPlanA = new HealthPlan(aName, aTargetWeight, aCurrentWeight, aCurrentHeight, aAge,
            aDuration, Scheme.GAIN, new ArrayList<Day>());

    private HealthPlan healthPlanB = new HealthPlan(bName, bTargetWeight, bCurrentWeight, bCurrentHeight, bAge,
            bDuration, Scheme.GAIN, new ArrayList<Day>());
    @Test
    public void isSameHealthPlan() {

        assertTrue(healthPlanA.isSame(healthPlanA));

        assertFalse(healthPlanA.isSame(null));

        //modify the name no longer the same
        HealthPlan editedPlan = new HealthPlanBuilder(healthPlanA).withName("VALID B").build();
        assertFalse(healthPlanA.isSame(editedPlan));
    }
    @Test
    public void equals() {

        //assert true for equating to self
        assertTrue(healthPlanA.equals(healthPlanA));

        assertFalse(healthPlanA.equals(null));

        //type difference test
        assertFalse(healthPlanA.equals("hello"));

        //different plan test
        assertFalse(healthPlanA.equals(healthPlanB));

        //value modification tests
        HealthPlan editedPlan = new HealthPlanBuilder(healthPlanA).withName("VALID B").build();
        assertFalse(healthPlanA.equals(editedPlan));

        editedPlan = new HealthPlanBuilder(healthPlanA).withAge("20").build();
        assertFalse(healthPlanA.equals(editedPlan));

        editedPlan = new HealthPlanBuilder(healthPlanA).withDuration("15").build();
        assertFalse(healthPlanA.equals(editedPlan));

        editedPlan = new HealthPlanBuilder(healthPlanA).withCurrentHeight("200").build();
        assertFalse(healthPlanA.equals(editedPlan));

        editedPlan = new HealthPlanBuilder(healthPlanA).withCurrentWeight("60.0").build();
        assertFalse(healthPlanA.equals(editedPlan));

        editedPlan = new HealthPlanBuilder(healthPlanA).withTargetWeight("70.0").build();
        assertFalse(healthPlanA.equals(editedPlan));

        editedPlan = new HealthPlanBuilder(healthPlanA).withScheme("LOSS").build();
        assertFalse(healthPlanA.equals(editedPlan));
        
    }


}
