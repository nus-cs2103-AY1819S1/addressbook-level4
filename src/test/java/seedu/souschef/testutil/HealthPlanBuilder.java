package seedu.souschef.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.souschef.model.healthplan.Age;
import seedu.souschef.model.healthplan.CurrentHeight;
import seedu.souschef.model.healthplan.CurrentWeight;
import seedu.souschef.model.healthplan.Duration;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.healthplan.HealthPlanName;
import seedu.souschef.model.healthplan.Scheme;
import seedu.souschef.model.healthplan.TargetWeight;
import seedu.souschef.model.planner.Day;

/**
 *  builder utility class for healthplans
 */
public class HealthPlanBuilder {


    public static final String DEFAULT_NAME = "LOSE WEIGHT";
    public static final String DEFAULT_AGE = "15";
    public static final String DEFAULT_CURRENT_HEIGHT = "100";
    public static final String DEFAULT_CURRENT_WEIGHT = "60.0";
    public static final String DEFAULT_TARGET_WEIGHT = "50.0";
    public static final String DEFAULT_DURATION = "10";
    public static final Scheme DEFAULT_SCHEME = Scheme.LOSS;

    private HealthPlanName healthPlanName;
    private Age age;
    private CurrentHeight currentHeight;
    private CurrentWeight currentWeight;
    private TargetWeight targetWeight;
    private Duration duration;
    private Scheme scheme;
    private List<Day> days;

    /**
     *  default constructor for healthplan builder using default values
     */
    public HealthPlanBuilder() {
        healthPlanName = new HealthPlanName(DEFAULT_NAME);
        age = new Age(DEFAULT_AGE);
        currentHeight = new CurrentHeight(DEFAULT_CURRENT_HEIGHT);
        currentWeight = new CurrentWeight(DEFAULT_CURRENT_WEIGHT);
        targetWeight = new TargetWeight(DEFAULT_TARGET_WEIGHT);
        duration = new Duration(DEFAULT_DURATION);
        scheme = DEFAULT_SCHEME;
        days = new ArrayList<>();

    }

    /**
     * clone builder using values from given plan
     */
    public HealthPlanBuilder(HealthPlan plan) {
        healthPlanName = plan.getHealthPlanName();
        age = plan.getAge();
        currentHeight = plan.getCurrentHeight();
        currentWeight = plan.getCurrentWeight();
        targetWeight = plan.getTargetWeight();
        duration = plan.getDuration();
        scheme = plan.getScheme();
        days = plan.getMealPlans();
    }

    /**
     * modify name of builder for testing purposes
     */
    public HealthPlanBuilder withName(String name) {
        this.healthPlanName = new HealthPlanName(name);
        return this;
    }

    /**
     * modify age of builder for testing purposes
     */
    public HealthPlanBuilder withAge(String age) {
        this.age = new Age(age);
        return this;
    }

    /**
     * modify duration of builder for testing purposes
     */
    public HealthPlanBuilder withDuration(String duration) {
        this.duration = new Duration(duration);
        return this;
    }

    /**
     * modify currentHeight of builder for testing purposes
     */
    public HealthPlanBuilder withCurrentHeight(String height) {
        this.currentHeight = new CurrentHeight(height);
        return this;
    }

    /**
     * modify currentWeight of builder for testing purposes
     */
    public HealthPlanBuilder withCurrentWeight(String weight) {
        this.currentWeight = new CurrentWeight(weight);
        return this;
    }

    /**
     * modify targetWeight of builder for testing purposes
     */
    public HealthPlanBuilder withTargetWeight(String weight) {
        this.targetWeight = new TargetWeight(weight);
        return this;
    }

    /**
     * modify scheme of builder for testing purposes
     */
    public HealthPlanBuilder withScheme(String scheme) {
        this.scheme = Scheme.valueOf(scheme);
        return this;
    }


    /**
     * build a healthplan object with the specified values
     */
    public HealthPlan build() {
        return new HealthPlan(healthPlanName, targetWeight, currentWeight, currentHeight, age, duration, scheme, days);


    }



}
