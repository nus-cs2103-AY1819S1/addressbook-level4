package seedu.address.model.issue;

import java.util.Objects;

import seedu.address.model.issue.solution.SolutionLink;

/**
 * Represents a Issue's solution in saveit.
 */
public class Solution {
    private SolutionLink solutionLink;
    private Remark remark;

    /**
     * Construct a new Solution.
     *
     * @param solutionLink url link to solution website.
     * @param remark user's notes about this solution.
     */
    public Solution(SolutionLink solutionLink, Remark remark) {
        this.solutionLink = solutionLink;
        this.remark = remark;
    }

    public SolutionLink getLink() {
        return solutionLink;
    }

    public Remark getRemark() {
        return remark;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getLink())
                .append(" Link: ")
                .append(getRemark())
                .append(" Remark: ");
        return builder.toString();
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
