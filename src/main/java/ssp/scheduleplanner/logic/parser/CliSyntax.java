package ssp.scheduleplanner.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_PRIORITY = new Prefix("p/");
    public static final Prefix PREFIX_VENUE = new Prefix("v/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REPEAT = new Prefix("r/");
    public static final Prefix PREFIX_INTERVAL = new Prefix("i/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/");
}
