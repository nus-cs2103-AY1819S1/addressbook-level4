package seedu.address.logic.parser;

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
    public static final Prefix PREFIX_NULL = new Prefix(null);
    public static final Prefix PREFIX_FROM = new Prefix("from/");
    public static final Prefix PREFIX_TO = new Prefix("to/");
    public static final Prefix PREFIX_SUBJECT = new Prefix("subject/");
    public static final Prefix PREFIX_CONTENT = new Prefix("content/");
    public static final Prefix PREFIX_MONTH = new Prefix("month/");
    public static final Prefix PREFIX_YEAR = new Prefix("year/");

}
