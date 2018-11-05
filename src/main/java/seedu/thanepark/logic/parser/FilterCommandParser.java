package seedu.thanepark.logic.parser;

import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_MAINTENANCE;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG_FULL;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_WAITING_TIME;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_ZONE;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_ZONE_FULL;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.util.Pair;
import seedu.thanepark.logic.commands.FilterCommand;
import seedu.thanepark.logic.parser.exceptions.ParseException;
import seedu.thanepark.model.ride.AttributePredicate;
import seedu.thanepark.model.ride.Maintenance;
import seedu.thanepark.model.ride.RideContainsConditionPredicate;
import seedu.thanepark.model.ride.WaitTime;

/**
 * Parses input arguments and creates a new FilterCommand Object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    public static final String MESSAGE_INVALID_PREFIXES_USED = "Invalid prefixes found! '%1$s'\nPlease use use "
            + "prefixes with numeric attributes instead e.g. Maintenance: m/ or WaitTime: w/ \n";
    public static final String MESSAGE_INVALID_ARGS = "Invalid arguments found! '%1$s'\nPlease use >, <, = and "
            + "numbers only!\n";

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if user input does not conform the expected format
     */
    @Override
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MAINTENANCE, PREFIX_WAITING_TIME,
                PREFIX_NAME, PREFIX_ZONE, PREFIX_ZONE_FULL, PREFIX_TAG, PREFIX_TAG_FULL);

        checkForInvalidPrefixes(argMultimap);

        Optional<List<String>> maintenanceStrings = !argMultimap.getValue(PREFIX_MAINTENANCE).isPresent()
                ? Optional.empty()
                : Optional.of(argMultimap.getAllValues(PREFIX_MAINTENANCE));
        Optional<List<String>> waitingTimeStrings = !argMultimap.getValue(PREFIX_WAITING_TIME).isPresent()
                ? Optional.empty()
                : Optional.of(argMultimap.getAllValues(PREFIX_WAITING_TIME));

        List<AttributePredicate> predicates = new ArrayList<>();
        predicates = getMaintenancePredicates(maintenanceStrings, predicates);
        predicates = getWaitTimePredicate(waitingTimeStrings, predicates);

        return new FilterCommand(new RideContainsConditionPredicate(predicates));
    }

    /**
     * Check user input for invalid prefixes.
     */
    private void checkForInvalidPrefixes(ArgumentMultimap argMultimap) throws ParseException {
        String invalidTagsInInput = "";
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            invalidTagsInInput = invalidTagsInInput.concat(PREFIX_NAME.getPrefix() + " ");
        }
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            invalidTagsInInput = invalidTagsInInput.concat(PREFIX_TAG.getPrefix() + " ");
        }
        if (argMultimap.getValue(PREFIX_TAG_FULL).isPresent()) {
            invalidTagsInInput = invalidTagsInInput.concat(PREFIX_TAG_FULL.getPrefix() + " ");
        }
        if (argMultimap.getValue(PREFIX_ZONE).isPresent()) {
            invalidTagsInInput = invalidTagsInInput.concat(PREFIX_ZONE.getPrefix() + " ");
        }
        if (argMultimap.getValue(PREFIX_ZONE_FULL).isPresent()) {
            invalidTagsInInput = invalidTagsInInput.concat(PREFIX_ZONE_FULL.getPrefix());
        }
        if (invalidTagsInInput.length() > 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_PREFIXES_USED, invalidTagsInInput));
        }
    }

    /**
     * Gets a list of predicates from the list of maintenance input if any is present
     */
    private List<AttributePredicate> getWaitTimePredicate(
            Optional<List<String>> waitingTimeStrings, List<AttributePredicate> predicates) throws ParseException {
        List<AttributePredicate> newPredicates = new ArrayList<>();
        if (waitingTimeStrings.isPresent()) {
            for (String s : waitingTimeStrings.get()) {
                Pair<String, String> waitingTimeConditions = getOperatorAndValues(s);
                newPredicates.add(new AttributePredicate(waitingTimeConditions.getKey(),
                        new WaitTime(waitingTimeConditions.getValue())));
            }
        }
        newPredicates.addAll(predicates);
        return newPredicates;
    }

    /**
     * Gets a list of predicates from the list of wait time input if any is present
     */
    private List<AttributePredicate> getMaintenancePredicates(
            Optional<List<String>> maintenanceStrings, List<AttributePredicate> predicates) throws ParseException {
        List<AttributePredicate> newPredicates = new ArrayList<>();
        if (maintenanceStrings.isPresent()) {
            for (String s : maintenanceStrings.get()) {
                Pair<String, String> maintenanceCondition = getOperatorAndValues(s);
                predicates.add(new AttributePredicate(maintenanceCondition.getKey(),
                        new Maintenance(maintenanceCondition.getValue())));
            }
        }
        newPredicates.addAll(predicates);
        return newPredicates;
    }

    /**
     * Returns a Pair object with the operator and input value from the user input string.
     */
    private Pair<String, String> getOperatorAndValues(String string) throws ParseException {
        String predicateString = string.trim();
        char[] array = predicateString.toCharArray();
        String operator = "";
        String value = "";
        for (char c : array) {
            if (isInvalidCharacter(c)) {
                throw new ParseException(String.format(MESSAGE_INVALID_ARGS, c));
            }
            if (c == ' ') {
                continue;
            }
            if (isOperator(c)) {
                operator = operator.concat(Character.toString(c));
            }
            if (Character.isDigit(c)) {
                value = value.concat(Character.toString(c));
            }
        }
        return new Pair<>(operator, value);
    }

    /**
     * Checks if a character is an operator, i.e. >, < or =
     */
    private boolean isOperator(char c) {
        return c == '<' || c == '>' || c == '=';
    }

    /**
     * Checks for invalid character in user input string
     */
    private boolean isInvalidCharacter (char c) {
        return !isOperator(c) && c != ' ' && !Character.isDigit(c);
    }
}
