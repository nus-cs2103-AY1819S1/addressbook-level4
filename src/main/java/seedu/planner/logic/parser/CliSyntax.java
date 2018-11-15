package seedu.planner.logic.parser;

import java.util.Set;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_CODE = new Prefix("c/");
    public static final Prefix PREFIX_YEAR = new Prefix("y/");
    public static final Prefix PREFIX_SEMESTER = new Prefix("s/");
    public static final Prefix PREFIX_MAJOR = new Prefix("m/");
    public static final Prefix PREFIX_FOCUS_AREA = new Prefix("f/");

    private static final Set<Prefix> prefixesLimitedToOne = Set.of(
            PREFIX_YEAR, PREFIX_SEMESTER, PREFIX_MAJOR);

    public static boolean isPrefixLimitedToOne(Prefix prefix) {
        return prefixesLimitedToOne.stream().anyMatch(x -> x.equals(prefix));
    }
}
