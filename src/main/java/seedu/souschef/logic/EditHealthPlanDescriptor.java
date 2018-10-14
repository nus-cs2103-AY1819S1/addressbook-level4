package seedu.souschef.logic;

import java.util.Optional;

import seedu.souschef.commons.util.CollectionUtil;
import seedu.souschef.model.healthplan.Age;
import seedu.souschef.model.healthplan.CurrentHeight;
import seedu.souschef.model.healthplan.CurrentWeight;
import seedu.souschef.model.healthplan.Duration;
import seedu.souschef.model.healthplan.HealthPlanName;
import seedu.souschef.model.healthplan.Scheme;
import seedu.souschef.model.healthplan.TargetWeight;

/**
 *  class for edit healthplan logic handler
 */
public class EditHealthPlanDescriptor {
    private HealthPlanName healthPlanName;
    private CurrentWeight currentWeight;
    private CurrentHeight currentHeight;
    private Age age;
    private Duration duration;
    private TargetWeight targetWeight;
    private Scheme scheme;

    public EditHealthPlanDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditHealthPlanDescriptor(EditHealthPlanDescriptor toCopy) {
        setHealthPlanName(toCopy.healthPlanName);
        setCurrentWeight(toCopy.currentWeight);
        setCurrentHeight(toCopy.currentHeight);
        setAge(toCopy.age);
        setDuration(toCopy.duration);
        setTargetWeight(toCopy.targetWeight);
        setScheme(toCopy.scheme);

    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(healthPlanName, currentWeight, currentHeight, age,
                duration, targetWeight, scheme);
    }

    public void setHealthPlanName(HealthPlanName healthPlanName) {
        this.healthPlanName = healthPlanName;
    }

    public Optional<HealthPlanName> getHealthPlanName() {
        return Optional.ofNullable(healthPlanName);
    }

    public void setCurrentWeight(CurrentWeight currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Optional<CurrentWeight> getCurrentWeight() {
        return Optional.ofNullable(currentWeight);
    }

    public void setCurrentHeight(CurrentHeight currentHeight) {
        this.currentHeight = currentHeight;
    }

    public Optional<CurrentHeight> getCurrentHeight() {
        return Optional.ofNullable(currentHeight);
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public Optional<Age> getAge() {
        return Optional.ofNullable(age);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Optional<Duration> getDuration() {
        return Optional.ofNullable(duration);
    }

    public void setTargetWeight(TargetWeight targetWeight) {
        this.targetWeight = targetWeight;
    }

    public Optional<TargetWeight> getTargetWeight() {
        return Optional.ofNullable(targetWeight);
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    public Optional<Scheme> getScheme() {
        return Optional.ofNullable(scheme);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditHealthPlanDescriptor)) {
            return false;
        }

        // state check
        EditHealthPlanDescriptor e = (EditHealthPlanDescriptor) other;

        return getHealthPlanName().equals(e.getHealthPlanName())
                && getAge().equals(e.getAge())
                && getCurrentHeight().equals(e.getCurrentHeight())
                && getCurrentWeight().equals(e.getCurrentWeight())
                && getTargetWeight().equals(e.getTargetWeight())
                && getScheme().equals(e.getScheme())
                && getDuration().equals(e.getDuration());
    }


}
