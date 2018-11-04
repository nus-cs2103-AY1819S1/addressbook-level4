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
    public static final Prefix PREFIX_PERSONINDEX = new Prefix("pi/");

    public static final Prefix PREFIX_MODULECODE = new Prefix("mc/");
    public static final Prefix PREFIX_MODULETITLE = new Prefix("mt/");
    public static final Prefix PREFIX_ACADEMICYEAR = new Prefix("ay/");
    public static final Prefix PREFIX_SEMESTER = new Prefix("sem/");
    public static final Prefix PREFIX_MODULEINDEX = new Prefix("mi/");

    public static final Prefix PREFIX_OCCASIONNAME = new Prefix("on/");
    public static final Prefix PREFIX_OCCASIONDATE = new Prefix("od/");
    public static final Prefix PREFIX_OCCASIONLOCATION = new Prefix("loc/");
    public static final Prefix PREFIX_OCCASIONINDEX = new Prefix("oi/");

}
