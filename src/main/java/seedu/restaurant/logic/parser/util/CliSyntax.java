package seedu.restaurant.logic.parser.util;

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

    //@@author HyperionNKJ
    /*Prefix definitions for sales records */
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_ITEM_NAME = new Prefix("n/");
    public static final Prefix PREFIX_QUANTITY_SOLD = new Prefix("q/");
    public static final Prefix PREFIX_ITEM_PRICE = new Prefix("p/");
    //@@author

    /*Prefix definitions for user accounts */
    public static final Prefix PREFIX_ID = new Prefix("id/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("pw/");
    public static final Prefix PREFIX_NEW_PASSWORD = new Prefix("npw/");

    /* Prefix definitions for ingredient */
    public static final Prefix PREFIX_INGREDIENT_NAME = new Prefix("n/");
    public static final Prefix PREFIX_INGREDIENT_UNIT = new Prefix("u/");
    public static final Prefix PREFIX_INGREDIENT_PRICE = new Prefix("p/");
    public static final Prefix PREFIX_INGREDIENT_MINIMUM = new Prefix("m/");
    public static final Prefix PREFIX_INGREDIENT_NUM = new Prefix("nu/");
    public static final Prefix PREFIX_INGREDIENT_ORIGINAL_NAME = new Prefix("on/");

    /*Prefix definitions for menu management */
    public static final Prefix PREFIX_PRICE = new Prefix("p/");
    public static final Prefix PREFIX_PERCENT = new Prefix("dp/");
    public static final Prefix PREFIX_RECIPE = new Prefix("r/");
    public static final Prefix PREFIX_ENDING_INDEX = new Prefix("ei/");

    /*Prefix definitions for reservation management */
    public static final Prefix PREFIX_PAX = new Prefix("px/");
    public static final Prefix PREFIX_DATETIME = new Prefix("dt/");
    public static final Prefix PREFIX_TIME = new Prefix("ti/");
}
