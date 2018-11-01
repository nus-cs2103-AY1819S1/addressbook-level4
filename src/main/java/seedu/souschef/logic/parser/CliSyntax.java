package seedu.souschef.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_DIFFICULTY = new Prefix("d/");
    public static final Prefix PREFIX_COOKTIME = new Prefix("c/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_INGREDIENT = new Prefix("#");
    public static final Prefix PREFIX_INSTRUCTION = new Prefix("i/");
    public static final Prefix PREFIX_STEP = new Prefix("s/");

    //prefix for healthplan
    public static final Prefix PREFIX_HPNAME = new Prefix("n/");
    public static final Prefix PREFIX_TWEIGHT = new Prefix("t/");
    public static final Prefix PREFIX_CWEIGHT = new Prefix("w/");
    public static final Prefix PREFIX_CHEIGHT = new Prefix("h/");
    public static final Prefix PREFIX_AGE = new Prefix("a/");
    public static final Prefix PREFIX_DURATION = new Prefix("d/");
    public static final Prefix PREFIX_SCHEME = new Prefix("s/");

    public static final Prefix PREFIX_PLAN = new Prefix("p/");

}
