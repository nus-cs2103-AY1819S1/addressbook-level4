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
    public static final Prefix PREFIX_REMARK = new Prefix("r/");

    /*Prefix definitions for user accounts */
    public static final Prefix PREFIX_ID = new Prefix("id/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("pw/");

    /* Prefix definitions for ingredient */
    public static final Prefix PREFIX_INGREDIENT_NAME = new Prefix("n/");
    public static final Prefix PREFIX_INGREDIENT_UNIT = new Prefix("u/");
    public static final Prefix PREFIX_INGREDIENT_PRICE = new Prefix("p/");
    public static final Prefix PREFIX_INGREDIENT_MINIMUM = new Prefix("m/");

}
