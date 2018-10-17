package seedu.inventory.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Item prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PRICE = new Prefix("p/");
    public static final Prefix PREFIX_QUANTITY = new Prefix("q/");
    public static final Prefix PREFIX_SKU = new Prefix("s/");
    public static final Prefix PREFIX_IMAGE = new Prefix("i/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Purchase order prefix definitions */
    public static final Prefix PREFIX_REQDATE = new Prefix("d/");
    public static final Prefix PREFIX_SUPPLIER = new Prefix("sp/");

    /* Staff prefix definitions */
    public static final Prefix PREFIX_USERNAME = new Prefix("u/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("p/");
    public static final Prefix PREFIX_ROLE = new Prefix("r/");


}
