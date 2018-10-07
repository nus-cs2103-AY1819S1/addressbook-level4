package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    // JxMusic prefixes
    public static final Prefix PREFIX_TRACK = new Prefix("t/");
    public static final Prefix PREFIX_PLAYLIST = new Prefix("p/");
    public static final Prefix PREFIX_TIME = new Prefix("ti/");
    public static final Prefix PREFIX_SECONDS = new Prefix("s/");
    public static final Prefix PREFIX_QUERY = new Prefix("q/");
    public static final Prefix PREFIX_INDEX = new Prefix("i/");
    // todo remove AddressBook prefixes when its dependencies are resolved
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("ph/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("ta/");

}
