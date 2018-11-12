package seedu.saveit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.saveit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.saveit.commons.util.StringUtil.arePrefixesNotPresent;
import static seedu.saveit.commons.util.StringUtil.arePrefixesPresent;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_SOLUTION_LINK;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_STATEMENT;
import static seedu.saveit.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.saveit.commons.core.index.Index;
import seedu.saveit.logic.commands.EditCommand;
import seedu.saveit.logic.commands.EditCommand.EditIssueDescriptor;
import seedu.saveit.logic.parser.exceptions.ParseException;
import seedu.saveit.model.issue.Description;
import seedu.saveit.model.issue.IssueStatement;
import seedu.saveit.model.issue.Solution;
import seedu.saveit.model.issue.Tag;


/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an EditCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_STATEMENT, PREFIX_DESCRIPTION, PREFIX_SOLUTION_LINK,
                PREFIX_REMARK, PREFIX_TAG);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        // check if the command is correct
        if (isEditIssueCommand(args)) {
            return getIssueLevelEditCommand(argMultimap, index);
        } else if (isEditSolutionCommand(args)) {
            return getSolutionLevelEditCommand(argMultimap, index);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    }

    private boolean isEditSolutionCommand(String args) {
        return arePrefixesNotPresent(args, PREFIX_STATEMENT, PREFIX_DESCRIPTION, PREFIX_TAG)
            && (arePrefixesPresent(args, PREFIX_SOLUTION_LINK, PREFIX_REMARK));
    }

    private boolean isEditIssueCommand(String args) {
        return (arePrefixesPresent(args, PREFIX_STATEMENT, PREFIX_DESCRIPTION, PREFIX_TAG))
            && arePrefixesNotPresent(args, PREFIX_SOLUTION_LINK, PREFIX_REMARK);
    }

    private EditCommand getIssueLevelEditCommand(ArgumentMultimap argMultimap, Index index) throws ParseException {
        EditIssueDescriptor editIssueDescriptor = new EditIssueDescriptor();
        if (argMultimap.getValue(PREFIX_STATEMENT).isPresent()) {
            IssueStatement statement = ParserUtil.parseStatement(argMultimap.getValue(PREFIX_STATEMENT).get());
            editIssueDescriptor.setStatement(statement);
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
            editIssueDescriptor.setDescription(description);
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editIssueDescriptor::setTags);

        return new EditCommand(index, editIssueDescriptor);
    }

    private EditCommand getSolutionLevelEditCommand(ArgumentMultimap argMultimap, Index index) throws ParseException {
        String solutionLink = argMultimap.getValue(PREFIX_SOLUTION_LINK).isPresent()
            ? argMultimap.getValue(PREFIX_SOLUTION_LINK).get() : EditCommand.DUMMY_SOLUTION_LINK;

        String solutionRemark = argMultimap.getValue(PREFIX_REMARK).isPresent()
            ? argMultimap.getValue(PREFIX_REMARK).get() : EditCommand.DUMMY_SOLUTION_REMARK;

        Solution solution = parseSolutionForEdit(solutionLink, solutionRemark);
        EditIssueDescriptor editSolutionDescriptor = new EditIssueDescriptor(index, solution);

        return new EditCommand(index, editSolutionDescriptor);
    }

    /**
     * Parses {@code Collection<String> solutions} into a {@code Set<Solution>} if {@code solutions} is non-empty. If
     * {@code solutions} contain only one element which is an empty string, it will be parsed into a {@code
     * Set<Solution>} containing zero solutions.
     */
    private Solution parseSolutionForEdit(String solutionLink, String solutionRemark) throws ParseException {
        Solution solution = ParserUtil.parseSolution(solutionLink, solutionRemark);
        return solution;
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty. If {@code tags}
     * contain only one element which is an empty string, it will be parsed into a {@code Set<Tag>} containing zero
     * tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
