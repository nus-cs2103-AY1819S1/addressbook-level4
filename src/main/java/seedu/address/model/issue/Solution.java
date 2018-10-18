package seedu.address.model.issue;

import java.util.Objects;

import seedu.address.model.issue.solution.Remark;
import seedu.address.model.issue.solution.SolutionLink;

/**
 * Represents a Issue's solution in saveit.
 */
public class Solution {

    public static final String SOLUTION_VALIDATION_REGEX = "[^\\s].*+";

    public final String solutionName;

    public final SolutionLink solutionLink;

    public final Remark remark;

    /**
     * Construct a new Solution.
     */
    public Solution(String solutionName) {
        int index = solutionName.indexOf(' ');
        if (index != -1) {
            this.solutionLink = new SolutionLink(solutionName.substring(0, index));
            this.remark = new Remark(solutionName.substring(index + 1));
        } else {
            if (SolutionLink.isValidLink(solutionName)) {
                this.solutionLink = new SolutionLink(solutionName);
                this.remark = null;
            } else {
                this.solutionLink = null;
                this.remark = new Remark(solutionName);
            }
        }

        this.solutionName = solutionName;
    }

    /**
     * Construct a new Solution.
     *
     * @param solutionLink url link to solution website.
     * @param remark user's notes about this solution.
     */
    public Solution(String solutionLink, String remark) {
        this.solutionLink = new SolutionLink(solutionLink);
        this.remark = new Remark(remark);
        this.solutionName = solutionLink + " " + remark;
    }

    public SolutionLink getLink() {
        return solutionLink;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns true if a given string is a valid solution name.
     */
    public static boolean isValidSolution(String test) {
        return test.matches(SOLUTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return '[' + " link: " + getLink() + " remark: " + getRemark() + ']';
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Solution // instanceof handles nulls
                && solutionLink.equals(((Solution) other).solutionLink)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(solutionLink, remark);
    }
}
