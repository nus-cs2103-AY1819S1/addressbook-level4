package seedu.address.model.healthplan;


import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;



public class HealthPlan {
    // Identity fields
    private final HealthPlanName name;
    private final TargetWeight tWeight;
    private final CurrentWeight cWeight;
    private final CurrentHeight cHeight;
    private final Age age;
    private final Duration duration;
    private final Scheme scheme;

    public HealthPlan(HealthPlanName name,TargetWeight tWeight, CurrentWeight cWeight, CurrentHeight cHeight,Age age, Duration duration, Scheme scheme) {
        requireAllNonNull(name, tWeight, cWeight, cHeight, age,duration,scheme);
        this.name = name;
        this.tWeight = tWeight;
        this.cWeight = cWeight;
        this.cHeight = cHeight;
        this.age=age;
        this.duration=duration;
        this.scheme=scheme;


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

    public Scheme getScheme(){return scheme;}



    public boolean isSamePlan(HealthPlan otherPlan) {
        if (otherPlan == this) {
            return true;
        }

        return otherPlan != null
                && otherPlan.getHealthPlanName().equals(getHealthPlanName());
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, age, cWeight, cHeight, tWeight,duration,scheme);
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
