package seedu.modsuni.model.module;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Encapsulates the prerequisite data for a module.
 * A prerequisite can only have a "or" type of PrereqDetails or an "and" type of PrereqDetails.
 */
public class Prereq {
    private Optional<List<PrereqDetails>> or;
    private Optional<List<PrereqDetails>> and;

    public Prereq() {
        or = Optional.empty();
        and = Optional.empty();
    }

    public Optional<List<PrereqDetails>> getOr() {
        return or;
    }

    /**
     * Resets "and" to be empty before setting "or".
     * @param or List of "or" PrereqDetails to be set.
     */
    public void setOr(Optional<List<PrereqDetails>> or) {
        if (and.isPresent()) {
            and = Optional.empty();
        }
        this.or = or;
    }

    public Optional<List<PrereqDetails>> getAnd() {
        return and;
    }

    /**
     * Resets "or" to be empty before setting "and".
     * @param and List of "and" PrereqDetails to be set.
     */
    public void setAnd(Optional<List<PrereqDetails>> and) {
        if (or.isPresent()) {
            or = Optional.empty();
        }
        this.and = and;
    }

    /**
     * Returns the total number of or prereqs.
     */
    public int noOfOrPrereq() {
        return or.map(o -> o.size()).orElse(0);
    }

    /**
     * Returns the total number of and prereqs.
     */
    public int noOfAndPrereq() {
        return and.map(o -> o.size()).orElse(0);
    }

    /**
     * Checks if this prerequisite can be satisfied using the list of codes.
     * @param codeChecklist Codes to be checked against.
     * @return true if this prerequisite can be satisfied, false otherwise.
     */
    public boolean checkPrereq(List<Code> codeChecklist) {
        if (!or.isPresent() && !and.isPresent()) {
            return true;
        } else if (or.isPresent()) {
            for (PrereqDetails element : or.get()) {
                if (element.checkPrereq(codeChecklist)) {
                    return true;
                }
            }
            return false;
        } else if (and.isPresent()) {
            for (PrereqDetails element : and.get()) {
                if (!element.checkPrereq(codeChecklist)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (!or.isPresent() && !and.isPresent()) {
            return "No prerequisites";
        }
        StringBuilder sb = new StringBuilder();
        if (or.isPresent()) {
            sb.append("Or: ");
            sb.append(Arrays.toString(or.get().toArray()));
        } else if (and.isPresent()) {
            sb.append("And: ");
            sb.append(Arrays.toString(and.get().toArray()));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Prereq)) {
            return false;
        }

        Prereq otherPrereq = (Prereq) other;
        return otherPrereq.getAnd().equals(getAnd())
                && otherPrereq.getOr().equals(getOr());
    }

    @Override
    public int hashCode() {
        return Objects.hash(or, and);
    }
}
