package seedu.saveit.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.saveit.commons.core.index.Index;
import seedu.saveit.logic.commands.EditCommand;
import seedu.saveit.logic.commands.EditCommand.EditIssueDescriptor;
import seedu.saveit.model.Issue;
import seedu.saveit.model.issue.Description;
import seedu.saveit.model.issue.IssueStatement;
import seedu.saveit.model.issue.Solution;
import seedu.saveit.model.issue.Tag;
import seedu.saveit.model.issue.solution.Remark;
import seedu.saveit.model.issue.solution.SolutionLink;

/**
 * A utility class to help with building EditIssueDescriptor objects.
 */
public class EditIssueDescriptorBuilder {

    private EditCommand.EditIssueDescriptor descriptor;

    public EditIssueDescriptorBuilder() {
        descriptor = new EditIssueDescriptor();
    }

    public EditIssueDescriptorBuilder(EditIssueDescriptor descriptor) {
        this.descriptor = new EditIssueDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditIssueDescriptor} with fields containing {@code issue}'s details
     */
    public EditIssueDescriptorBuilder(Issue issue) {
        descriptor = new EditIssueDescriptor();
        descriptor.setStatement(issue.getStatement());
        descriptor.setDescription(issue.getDescription());
        descriptor.setSolutions(issue.getSolutions());
        descriptor.setTags(issue.getTags());
    }

    /**
     * Returns an {@code EditIssueDescriptor} with fields containing {@code issue}'s details
     */
    public EditIssueDescriptorBuilder(Index index, Solution solution) {
        descriptor = new EditIssueDescriptor();
        descriptor.setSolution(solution);
        descriptor.setIndex(index);
    }


    /**
     * Sets the {@code IssueStatement} of the {@code EditIssueDescriptor} that we are building.
     */
    public EditIssueDescriptorBuilder withStatement(String statement) {
        descriptor.setStatement(new IssueStatement(statement));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditIssueDescriptor} that we are building.
     */
    public EditIssueDescriptorBuilder withDescription(String descriptions) {
        descriptor.setDescription(new Description(descriptions));
        return this;
    }

    /**
     * Parses the {@code solutions} into a {@code Set<Solution>} and set it to the {@code EditIssueDescriptor} that we
     * are building.
     */
    public EditIssueDescriptorBuilder withSolutions(Solution... solutions) {
        List<Solution> solutionSet = Arrays.asList(solutions);
        descriptor.setSolutions(solutionSet);
        return this;
    }

    /**
     * Parses the {@code solutions} into a {@code Set<Solution>} and set it to the {@code EditIssueDescriptor} that we
     * are building.
     */
    public EditIssueDescriptorBuilder withSolutionLink(Index index, String solutionLink) {
        List<Solution> solutions = descriptor.getSolutions().get();
        Solution updateSolution = processSolutionWithSolutionLink(index, solutionLink);
        List<Solution> updateSolutions = new ArrayList<>(solutions);
        updateSolutions.set(index.getZeroBased(), updateSolution);
        descriptor.setSolutions(updateSolutions);
        return this;
    }

    /**
     * Parses the {@code solutions} into a {@code Set<Solution>} and set it to the {@code EditIssueDescriptor} that we
     * are building.
     */
    public EditIssueDescriptorBuilder withSolutionRemark(Index index, String solutionRemark) {
        List<Solution> solutions = descriptor.getSolutions().get();
        Solution updateSolution = processSolutionWithSolutionRemark(index, solutionRemark);
        List<Solution> updateSolutions = new ArrayList<>(solutions);
        updateSolutions.set(index.getZeroBased(), updateSolution);
        descriptor.setSolutions(updateSolutions);
        return this;
    }


    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditIssueDescriptor} that we are
     * building.
     */
    public EditIssueDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Parses the {@code solutionLink} to get a new Solution for {@code index} solution in the solution list
     */
    public Solution processSolutionWithSolutionLink(Index index, String solutionLink) {
        SolutionLink link = new SolutionLink(solutionLink);
        Remark remark = descriptor.getSolutions().get().get(index.getZeroBased()).getRemark();
        return new Solution(link, remark);
    }

    /**
     * Parses the {@code solutionRemark} to get a new Solution for {@code index} solution in the solution list
     */
    public Solution processSolutionWithSolutionRemark(Index index, String solutionRemark) {
        SolutionLink link = descriptor.getSolutions().get().get(index.getZeroBased()).getLink();
        Remark remark = new Remark(solutionRemark);
        return new Solution(link, remark);
    }

    public EditCommand.EditIssueDescriptor build() {
        return descriptor;
    }
}
