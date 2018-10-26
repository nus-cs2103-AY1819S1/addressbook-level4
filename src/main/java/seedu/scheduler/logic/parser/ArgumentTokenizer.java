package seedu.scheduler.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tokenizes arguments string of the form: {@code preamble <prefix>value <prefix>value <flag>flag ...}<br>
 *     e.g. {@code some preamble text t/ 11.00 t/12.00 k/ m/ July -a -u}  where prefixes are {@code t/ k/ m/}
 *     and flags are {@code -a -u}.<br>
 * 1. An argument's value can be an empty string e.g. the value of {@code k/} in the above example.<br>
 * 2. Leading and trailing whitespaces of an argument value will be discarded.<br>
 * 3. An argument may be repeated and all its values will be accumulated e.g. the value of {@code t/}
 *    in the above example.<br>
 */
public class ArgumentTokenizer {

    /**
     * Tokenizes an arguments string and returns an {@code ArgumentMultimap} object that maps prefixes and flags
     * to their respective argument values. Only the given prefixes will be recognized in the arguments string.
     *
     * @param argsString Arguments string of the form: {@code preamble <prefix>value <prefix>value <flag> value ...}
     * @param prefixes   Prefixes to tokenize the arguments string with
     * @return           ArgumentMultimap object that maps prefixes and flags to their arguments
     */
    public static ArgumentMultimap tokenize(String argsString, Prefix... prefixes) {
        List<IdentityPosition> prefixPositions = findAllPrefixPositions(argsString, prefixes);
        List<IdentityPosition> flagPositions = findAllFlagPositions(argsString);
        return extractArguments(argsString, prefixPositions, flagPositions);
    }

    /**
     * Finds all zero-based prefix positions in the given arguments string.
     *
     * @param argsString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixes   Prefixes to find in the arguments string
     * @return           List of zero-based prefix positions in the given arguments string
     */
    private static List<IdentityPosition> findAllPrefixPositions(String argsString, Prefix... prefixes) {
        return Arrays.stream(prefixes)
                .flatMap(prefix -> findPrefixPositions(argsString, prefix).stream())
                .collect(Collectors.toList());
    }

    /**
     * Finds all zero-based flag positions in the given arguments string.
     *
     * @param argsString Arguments string of the form: {@code preamble <flag>value <flag>value ...}
     * @return           List of zero-based flag positions in the given arguments string
     */
    private static List<IdentityPosition> findAllFlagPositions(String argsString) {
        List<IdentityPosition> flagPositions = new ArrayList<>();
        Pattern flagsPattern = Pattern.compile("-{1,2}[a-z]+");
        Matcher matcher = flagsPattern.matcher(argsString);
        while (matcher.find()) {
            flagPositions.add(new IdentityPosition(new Flag(matcher.group(0)), matcher.start(0)));
        }
        return flagPositions;
    }

    /**
     * {@see findAllPrefixPositions}
     */
    private static List<IdentityPosition> findPrefixPositions(String argsString, Prefix prefix) {
        List<IdentityPosition> positions = new ArrayList<>();

        int prefixPosition = findPrefixPosition(argsString, prefix.getPrefix(), 0);
        while (prefixPosition != -1) {
            IdentityPosition extendedPrefix = new IdentityPosition(prefix, prefixPosition);
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
    private static ArgumentMultimap extractArguments(String argsString, List<IdentityPosition> prefixPositions,
                                                     List<IdentityPosition> flagPositions) {

        // Concat flag and prefixes and sort by start position
        List<IdentityPosition> identityPositions = Stream.concat(prefixPositions.stream(), flagPositions.stream())
                .sorted((identity1, identity2) -> identity1.getStartPosition() - identity2.getStartPosition())
                .collect(Collectors.toList());

        // Insert a IdentityPosition to represent the preamble
        IdentityPosition preambleMarker = new IdentityPosition(new Prefix(""), 0);
        identityPositions.add(0, preambleMarker);

        // Add a dummy IdentityPosition to represent the end of the string
        IdentityPosition endPositionMarker = new IdentityPosition(new Prefix(""), argsString.length());
        identityPositions.add(endPositionMarker);

        // Map prefixes and flags to their argument values (if any)
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        for (int i = 0; i < identityPositions.size() - 1; i++) {
            // Extract and store prefixes and their arguments
            Identity argPrefix = identityPositions.get(i).getIdentity();
            String argValue = extractArgumentValue(argsString, identityPositions.get(i),
                    identityPositions.get(i + 1));
            argMultimap.put(argPrefix, argValue);
        }

        return argMultimap;
    }

    /**
     * Returns the trimmed value of the argument in the arguments string specified by {@code currentIdentityPosition}.
     * The end position of the value is determined by {@code nextIdentityPosition}.
     */
    private static String extractArgumentValue(String argsString,
                                        IdentityPosition currentIdentityPosition,
                                               IdentityPosition nextIdentityPosition) {
        Identity prefix = currentIdentityPosition.getIdentity();

        int valueStartPos = currentIdentityPosition.getStartPosition() + prefix.getIdentity().length();
        String value = argsString.substring(valueStartPos, nextIdentityPosition.getStartPosition());

        return value.trim();
    }

    /**
     * Represents a identity's position in an arguments string.
     */
    private static class IdentityPosition {
        private int startPosition;
        private final Identity identity;

        IdentityPosition(Identity identity, int startPosition) {
            this.identity = identity;
            this.startPosition = startPosition;
        }

        private int getStartPosition() {
            return startPosition;
        }

        private Identity getIdentity() {
            return identity;
        }
    }

}
