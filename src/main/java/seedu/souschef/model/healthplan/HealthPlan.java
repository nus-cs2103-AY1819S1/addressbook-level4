package seedu.souschef.model.healthplan;

import static seedu.souschef.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.souschef.model.UniqueType;
import seedu.souschef.model.planner.Day;


/**
 *  class to handle the overall frame of health plans
 * Health plan enum
 */
public class HealthPlan extends UniqueType {
    // Identity fields
    private final HealthPlanName name;
    private final TargetWeight tWeight;
    private final CurrentWeight cWeight;
    private final CurrentHeight cHeight;
    private final Age age;
    private final Duration duration;
    private final Scheme scheme;

    //list of mealPlans
    private final ArrayList<Day> mealPlans;

    public HealthPlan(HealthPlanName name, TargetWeight tWeight, CurrentWeight cWeight, CurrentHeight cHeight, Age age,
                      Duration duration, Scheme scheme, List<Day> days) {
        requireAllNonNull(name, tWeight, cWeight, cHeight, age, duration, scheme);
        this.name = name;
        this.tWeight = tWeight;
        this.cWeight = cWeight;
        this.cHeight = cHeight;
        this.age = age;
        this.duration = duration;
        this.scheme = scheme;
        mealPlans = new ArrayList<>(days);
    }

    public HealthPlanName getHealthPlanName() {
        return name;
    }

    public TargetWeight getTargetWeight() {
        return tWeight;
    }

    public CurrentWeight getCurrentWeight() {
        return cWeight;
    }
    public CurrentHeight getCurrentHeight() {
        return cHeight;
    }

    public Age getAge() {
        return age;
    }

    public Duration getDuration() {
        return duration;
    }

    public Scheme getScheme() {
        return scheme;
    }

    public ArrayList<Day> getMealPlans() {
        return mealPlans;
    }

    /**
     * check if the current plan is same as provided
     * method check if plan is same
     */
    public boolean isSame(HealthPlan otherPlan) {
        if (otherPlan == this) {
            return true;
        }

        return otherPlan != null
                && otherPlan.getHealthPlanName().equals(getHealthPlanName());
    }

    /**
     * check if the current plan is same as provided
     * method check if plan is same
     */
    @Override
    public boolean isSame(UniqueType uniqueType) {
        if (uniqueType instanceof HealthPlan) {
            return isSame((HealthPlan) uniqueType);
        } else {
            return false;
        }
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, age, cWeight, cHeight, tWeight, duration, scheme);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getHealthPlanName())
                .append(" Age: ")
                .append(getAge())
                .append(" Current Weight: ")
                .append(getCurrentWeight())
                .append(" Current Height: ")
                .append(getCurrentHeight())
                .append(" Target Weight: ")
                .append(getTargetWeight())
                .append(" Duration: ")
                .append(getDuration())
                .append(" Scheme: ")
                .append(getScheme());


        return builder.toString();
    }

    /**
     * method equal
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof HealthPlan)) {
            return false;
        }

        HealthPlan otherPlan = (HealthPlan) other;
        return otherPlan.getHealthPlanName().equals(getHealthPlanName())
                && otherPlan.getCurrentHeight().equals(getCurrentHeight())
                && otherPlan.getCurrentWeight().equals(getCurrentWeight())
                && otherPlan.getTargetWeight().equals(getTargetWeight())
                && otherPlan.getDuration().equals(getDuration())
                && otherPlan.getAge().equals(getAge())
                && otherPlan.getScheme().equals(getScheme());
    }





}
