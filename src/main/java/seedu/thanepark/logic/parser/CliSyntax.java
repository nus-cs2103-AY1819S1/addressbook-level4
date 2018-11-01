package seedu.thanepark.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_MAINTENANCE = new Prefix("m/");
    public static final Prefix PREFIX_WAITING_TIME = new Prefix("w/");
    public static final Prefix PREFIX_ZONE = new Prefix("z/");
    public static final Prefix PREFIX_ZONE_FULL = new Prefix("zone");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_TAG_FULL = new Prefix("tag");
}
