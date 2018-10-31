package seedu.thanepark.logic.parser;

import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_MAINTENANCE;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_WAITING_TIME;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MAINTENANCE, PREFIX_WAITING_TIME);
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
     * Gets a list of predicates from the list of maintenance input if any is present
     */
    private List<AttributePredicate> getWaitTimePredicate(Optional<List<String>> waitingTimeStrings,
                                                          List<AttributePredicate> predicates) {
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
    private List<AttributePredicate> getMaintenancePredicates(Optional<List<String>> maintenanceStrings,
                                                              List<AttributePredicate> predicates) {
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
    private Pair<String, String> getOperatorAndValues(String string) {
        String predicateString = string.trim();
        char[] array = predicateString.toCharArray();
        String operator = "";
        String value = "";
        for (char c : array) {
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
}