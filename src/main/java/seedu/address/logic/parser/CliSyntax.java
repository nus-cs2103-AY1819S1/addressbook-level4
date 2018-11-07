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
    public static final Prefix PREFIX_ALLERGY = new Prefix("al/");
    public static final Prefix PREFIX_CONDITION = new Prefix("c/");
    public static final Prefix PREFIX_PATIENT_NAME = new Prefix("np/");
    public static final Prefix PREFIX_PATIENT_PHONE = new Prefix("pp/");
    public static final Prefix PREFIX_DOCTOR_NAME = new Prefix("nd/");
    public static final Prefix PREFIX_DOCTOR_PHONE = new Prefix("pd/");
    public static final Prefix PREFIX_DATE_TIME = new Prefix("d/");

    public static final Prefix PREFIX_INDEX = new Prefix("pi/");
    public static final Prefix PREFIX_MEDICINE_NAME = new Prefix("pn/");
    public static final Prefix PREFIX_DOSAGE = new Prefix("pd/");
    public static final Prefix PREFIX_CONSUMPTION_PER_DAY = new Prefix("pc/");

}
