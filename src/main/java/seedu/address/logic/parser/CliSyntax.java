package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {
    /* Switch Prefix definitions */
    public static final Prefix PREFIX_SWITCH = new Prefix("-");

    /* Person Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Record Prefix definitions */
    public static final Prefix PREFIX_RECORD_HOUR = new Prefix("h/");
    public static final Prefix PREFIX_RECORD_REMARK = new Prefix("r/");
}
