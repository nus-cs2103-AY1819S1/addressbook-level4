package seedu.restaurant.logic.parser.util;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.restaurant.logic.commands.ingredient.StockUpCommand;
import seedu.restaurant.logic.parser.exceptions.ParseException;

/**
 * Tokenizes arguments string of the form: {@code preamble <prefix>value <prefix>value ...}<br>
 *     e.g. {@code some preamble text t/ 11.00 t/12.00 k/ m/ July}  where prefixes are {@code t/ k/ m/}.<br>
 * 1. An argument's value can be an empty string e.g. the value of {@code k/} in the above example.<br>
 * 2. Leading and trailing whitespaces of an argument value will be discarded.<br>
 * 3. An argument may be repeated and all its values will be accumulated e.g. the value of {@code t/}
 *    in the above example.<br>
 */
public class ArgumentTokenizer {

    /**
     * Tokenizes an arguments string and returns an {@code ArgumentMultimap} object that maps prefixes to their
     * respective argument values. Only the given prefixes will be recognized in the arguments string.
     *
     * @param argsString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixes   Prefixes to tokenize the arguments string with
     * @return           ArgumentMultimap object that maps prefixes to their arguments
     */
    public static ArgumentMultimap tokenize(String argsString, Prefix... prefixes) {
        List<PrefixPosition> positions = findAllPrefixPositions(argsString, prefixes);
        return extractArguments(argsString, positions);
    }

    /**
     * Tokenizes an arguments string and returns an {@code ArgumentPairMultimap} object that maps {@code String}
     * arguments to their following {@code Integer} arguments. The argument value of the {@code firstPrefix} is
     * mapped with the next argument of {@code secondPrefix}, and so on repetitively.
     *
     * @param argsString    Arguments string of the form: {@code <prefix>firstValue <prefix>secondValue}
     * @param firstPrefix   One prefix to tokenize the arguments string with
     * @param secondPrefix  Another prefix to tokenize the arguments string with
     * @return              ArgumentPairMultimap object that maps a string argument to the subsequent integer argument
     */
    public static ArgumentPairMultimap tokenizeToPair(
            String argsString, Prefix firstPrefix, Prefix secondPrefix) throws ParseException {
        List<PrefixPosition> positions = findAllPrefixPositions(argsString, firstPrefix, secondPrefix);
        return extractArgumentsToPair(argsString, positions, firstPrefix, secondPrefix);
    }

    /**
     * Tokenizes an arguments string and returns an {@code ArgumentPairMultimap} object that maps integer indices to
     * their respective argument pair values. The indices are one-based and argument pairs are formed by pairing
     * the argument value of the {@code firstPrefix} with the following argument of {@code secondPrefix},
     * and so on repetitively.
     *
     * @param argsString    Arguments string of the form: {@code <prefix>firstValue <prefix>secondValue}
     * @param firstPrefix   One prefix to tokenize the arguments string with
     * @param secondPrefix  Another prefix to tokenize the arguments string with
     * @return              ArgumentPairMultimap object that maps integer indices to their argument pairs
     */
    public static ArgumentIndexAndPairMultimap tokenizeToIndexAndPair(
            String argsString, Prefix firstPrefix, Prefix secondPrefix) {
        List<PrefixPosition> positions = findAllPrefixPositions(argsString, firstPrefix, secondPrefix);
        return extractArgumentsToIndexAndPair(argsString, positions);
    }

    /**
     * Finds all zero-based prefix positions in the given arguments string.
     *
     * @param argsString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixes   Prefixes to find in the arguments string
     * @return           List of zero-based prefix positions in the given arguments string
     */
    private static List<PrefixPosition> findAllPrefixPositions(String argsString, Prefix... prefixes) {
        return Arrays.stream(prefixes)
                .flatMap(prefix -> findPrefixPositions(argsString, prefix).stream())
                .collect(Collectors.toList());
    }

    /**
     * {@see findAllPrefixPositions}
     */
    private static List<PrefixPosition> findPrefixPositions(String argsString, Prefix prefix) {
        List<PrefixPosition> positions = new ArrayList<>();

        int prefixPosition = findPrefixPosition(argsString, prefix.getPrefix(), 0);
        while (prefixPosition != -1) {
            PrefixPosition extendedPrefix = new PrefixPosition(prefix, prefixPosition);
            positions.add(extendedPrefix);
            prefixPosition = findPrefixPosition(argsString, prefix.getPrefix(), prefixPosition);
        }

        return positions;
    }

    /**
     * Returns the index of the first occurrence of {@code prefix} in
     * {@code argsString} starting from index {@code fromIndex}. An occurrence
     * is valid if there is a whitespace before {@code prefix}. Returns -1 if no
     * such occurrence can be found.
     *
     * E.g if {@code argsString} = "e/hip/900", {@code prefix} = "p/" and
     * {@code fromIndex} = 0, this method returns -1 as there are no valid
     * occurrences of "p/" with whitespace before it. However, if
     * {@code argsString} = "e/hi p/900", {@code prefix} = "p/" and
     * {@code fromIndex} = 0, this method returns 5.
     */
    private static int findPrefixPosition(String argsString, String prefix, int fromIndex) {
        int prefixIndex = argsString.indexOf(" " + prefix, fromIndex);
        return prefixIndex == -1 ? -1
                : prefixIndex + 1; // +1 as offset for whitespace
    }

    /**
     * Extracts prefixes and their argument values, and returns an {@code ArgumentMultimap} object that maps the
     * extracted prefixes to their respective arguments. Prefixes are extracted based on their zero-based positions in
     * {@code argsString}.
     *
     * @param argsString      Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixPositions Zero-based positions of all prefixes in {@code argsString}
     * @return                ArgumentMultimap object that maps prefixes to their arguments
     */
    private static ArgumentMultimap extractArguments(String argsString, List<PrefixPosition> prefixPositions) {

        // Sort by start position
        prefixPositions.sort((prefix1, prefix2) -> prefix1.getStartPosition() - prefix2.getStartPosition());

        // Insert a PrefixPosition to represent the preamble
        PrefixPosition preambleMarker = new PrefixPosition(new Prefix(""), 0);
        prefixPositions.add(0, preambleMarker);

        // Add a dummy PrefixPosition to represent the end of the string
        PrefixPosition endPositionMarker = new PrefixPosition(new Prefix(""), argsString.length());
        prefixPositions.add(endPositionMarker);

        // Map prefixes to their argument values (if any)
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        for (int i = 0; i < prefixPositions.size() - 1; i++) {
            // Extract and store prefixes and their arguments
            Prefix argPrefix = prefixPositions.get(i).getPrefix();
            String argValue = extractArgumentValue(argsString, prefixPositions.get(i), prefixPositions.get(i + 1));
            argMultimap.put(argPrefix, argValue);
        }

        return argMultimap;
    }

    /**
     * Extracts prefixes and their respective arguments, forms argument pairs and returns an {@code
     * ArgumentPairMultimap} object that maps integer indices to argument pairs. Prefixes are
     * extracted based on their zero-based positions in {@code argsString}.
     *
     * @param argsString      Arguments string of the form: {@code <prefix>value <prefix>value ...}
     * @param prefixPositions Zero-based positions of all prefixes in {@code argsString}
     * @return                ArgumentPairMultimap object that maps indices to argument pairs
     * @throws ParseException If the prefixes are in the incorrect format
     */
    private static ArgumentPairMultimap extractArgumentsToPair(String argsString,
            List<PrefixPosition> prefixPositions, Prefix expectedFirstPrefix,
            Prefix expectedSecondPrefix) throws ParseException {

        // No prefixes
        if (prefixPositions.size() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StockUpCommand.MESSAGE_USAGE));
        }

        // Sort by start position
        prefixPositions.sort((prefix1, prefix2) -> prefix1.getStartPosition() - prefix2.getStartPosition());

        // Add a dummy PrefixPosition to represent the end of the string
        PrefixPosition endPositionMarker = new PrefixPosition(new Prefix(""), argsString.length());
        prefixPositions.add(endPositionMarker);

        // Map first argument to next argument
        ArgumentPairMultimap argMultimap = new ArgumentPairMultimap();
        for (int i = 0; i < prefixPositions.size() - 1; i = i + 2) {
            // Check prefixes to see if they follow the correct format
            String firstPrefix = prefixPositions.get(i).getPrefix().toString();
            String nextPrefix = prefixPositions.get(i + 1).getPrefix().toString();
            if (!(firstPrefix.equals(expectedFirstPrefix.toString())
                    && nextPrefix.equals(expectedSecondPrefix.toString()))) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StockUpCommand.MESSAGE_USAGE));
            }

            // Extract and store prefixes and their arguments
            String firstArgValue = extractArgumentValue(argsString, prefixPositions.get(i), prefixPositions.get(i + 1));
            String secondArgValue = extractArgumentValue(argsString, prefixPositions.get(i + 1),
                    prefixPositions.get(i + 2));

            argMultimap.put(firstArgValue, secondArgValue);
        }

        return argMultimap;
    }

    /**
     * Extracts prefixes and their respective arguments, forms argument pairs and returns an {@code
     * ArgumentPairMultimap} object that maps integer indices to argument pairs. Prefixes are
     * extracted based on their zero-based positions in {@code argsString}.
     *
     * @param argsString      Arguments string of the form: {@code <prefix>value <prefix>value ...}
     * @param prefixPositions Zero-based positions of all prefixes in {@code argsString}
     * @return                ArgumentPairMultimap object that maps indices to argument pairs
     * @throws ParseException If the prefixes are in the incorrect format
     */
    private static ArgumentIndexAndPairMultimap extractArgumentsToIndexAndPair(String argsString,
            List<PrefixPosition> prefixPositions) {

        // Sort by start position
        prefixPositions.sort(Comparator.comparingInt(PrefixPosition::getStartPosition));

        // Insert a PrefixPosition to represent the preamble
        PrefixPosition preambleMarker = new PrefixPosition(new Prefix(""), 0);
        prefixPositions.add(0, preambleMarker);

        // Add a dummy PrefixPosition to represent the end of the string
        PrefixPosition endPositionMarker = new PrefixPosition(new Prefix(""), argsString.length());
        prefixPositions.add(endPositionMarker);

        // Map indices to argument pairs
        ArgumentIndexAndPairMultimap argMultimap = new ArgumentIndexAndPairMultimap();

        // Extract out the Index
        Prefix argPrefix = prefixPositions.get(0).getPrefix();
        String argValue = extractArgumentValue(argsString, prefixPositions.get(0), prefixPositions.get(1));
        argMultimap.put(0, argPrefix, argValue);


        int index = 1;
        for (int i = 1; i < prefixPositions.size() - 1; i = i + 2) {
            // Extract and store prefixes and their arguments
            Prefix firstPrefix = prefixPositions.get(i).getPrefix();
            String firstArgValue = extractArgumentValue(argsString, prefixPositions.get(i), prefixPositions.get(i + 1));
            argMultimap.put(index, firstPrefix, firstArgValue);

            if (i + 2 >= prefixPositions.size()) {
                break;
            }
            Prefix secondPrefix = prefixPositions.get(i + 1).getPrefix();
            String secondArgValue = extractArgumentValue(argsString, prefixPositions.get(i + 1),
                    prefixPositions.get(i + 2));
            argMultimap.put(index, secondPrefix, secondArgValue);

            index++;
        }

        return argMultimap;
    }

    /**
     * Returns the trimmed value of the argument in the arguments string specified by {@code currentPrefixPosition}.
     * The end position of the value is determined by {@code nextPrefixPosition}.
     */
    private static String extractArgumentValue(String argsString,
                                        PrefixPosition currentPrefixPosition,
                                        PrefixPosition nextPrefixPosition) {
        Prefix prefix = currentPrefixPosition.getPrefix();

        int valueStartPos = currentPrefixPosition.getStartPosition() + prefix.getPrefix().length();
        String value = argsString.substring(valueStartPos, nextPrefixPosition.getStartPosition());

        return value.trim();
    }

    /**
     * Represents a prefix's position in an arguments string.
     */
    private static class PrefixPosition {
        private int startPosition;
        private final Prefix prefix;

        PrefixPosition(Prefix prefix, int startPosition) {
            this.prefix = prefix;
            this.startPosition = startPosition;
        }

        int getStartPosition() {
            return startPosition;
        }

        Prefix getPrefix() {
            return prefix;
        }
    }

}
