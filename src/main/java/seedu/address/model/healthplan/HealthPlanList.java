package seedu.address.model.healthplan;

import static java.util.Objects.requireNonNull;
import seedu.address.model.healthplan.exceptions.DuplicateHPException;
import seedu.address.model.healthplan.exceptions.HPNotFoundException;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class HealthPlanList implements Iterable<HealthPlan> {

    private final ObservableList<HealthPlan> internalList = FXCollections.observableArrayList();


    public void remove(HealthPlan toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new HPNotFoundException();
        }
    }

    public void add(HealthPlan toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateHPException();
        }
        internalList.add(toAdd);
    }


    public boolean contains(HealthPlan toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePlan);
    }

    public void setPlan(HealthPlan target, HealthPlan editedPlan) {
        requireAllNonNull(target, editedPlan);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new HPNotFoundException();
        }

        if (!target.isSamePlan(editedPlan) && contains(editedPlan)) {
            throw new DuplicateHPException();
        }

        internalList.set(index, editedPlan);
    }


    public void setPlan(List<HealthPlan> plan) {
        requireAllNonNull(plan);
        if (!plansAreUnique(plan)) {
            throw new DuplicateHPException();
        }

        internalList.setAll(plan);
    }

    public void setPersons(HealthPlanList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }



    public ObservableList<HealthPlan> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }




    @Override
    public Iterator<HealthPlan> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HealthPlanList // instanceof handles nulls
                && internalList.equals(((HealthPlanList) other).internalList));
    }

    private boolean plansAreUnique(List<HealthPlan> plans) {
        for (int i = 0; i < plans.size() - 1; i++) {
            for (int j = i + 1; j < plans.size(); j++) {
                if (plans.get(i).isSamePlan(plans.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
