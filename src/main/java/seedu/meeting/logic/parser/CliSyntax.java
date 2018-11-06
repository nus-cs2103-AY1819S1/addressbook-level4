package seedu.meeting.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_GROUP = new Prefix("g/");
    public static final Prefix PREFIX_PATH = new Prefix("f/");
    public static final Prefix PREFIX_TIMESTAMP = new Prefix("t/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_PERSON = new Prefix("p/");
    public static final Prefix PREFIX_MEETING = new Prefix("m/");

    /* Prefix definitions for FindCommandParser */
    public static final Prefix PREFIX_ALL = new Prefix("a/");
    public static final Prefix PREFIX_SOME = new Prefix("s/");
    public static final Prefix PREFIX_NONE = new Prefix("n/");
}
