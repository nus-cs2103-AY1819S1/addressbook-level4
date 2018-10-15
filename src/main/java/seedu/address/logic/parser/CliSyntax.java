package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_ICNUMBER = new Prefix("ic/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix PREFIX_BLOODTYPE = new Prefix("b/");
    public static final Prefix PREFIX_DIESEASE = new Prefix("d/");
    public static final Prefix PREFIX_DRUGALLERGY = new Prefix("da/");
    public static final Prefix PREFIX_NOTE = new Prefix("m/");

    public static final Prefix PREFIX_MEDICINE_NAME = new Prefix("mn/");
    public static final Prefix PREFIX_MINIMUM_STOCK_QUANTITY = new Prefix("msq/");
    public static final Prefix PREFIX_PRICE_PER_UNIT = new Prefix("ppu/");
    public static final Prefix PREFIX_STOCK = new Prefix("s/");
    public static final Prefix PREFIX_SERIAL_NUMBER = new Prefix("sn/");

}
