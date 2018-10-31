package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PRICE = new Prefix("p/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_AGE = new Prefix("a/");
    public static final Prefix PREFIX_URL = new Prefix("u/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_SAVING = new Prefix("s/");

    /* Preambles */
    public static final String PREAMBLE_EXACT_MATCH = "-e";
}
