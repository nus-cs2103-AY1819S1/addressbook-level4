package seedu.modsuni.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_EMPLOYMENT_DATE = new Prefix("employ/");
    public static final Prefix PREFIX_USERNAME = new Prefix("user/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("pass/");
    public static final Prefix PREFIX_USERDATA = new Prefix("userdata/");
    public static final Prefix PREFIX_SALARY = new Prefix("salary/");
    public static final Prefix PREFIX_STUDENT_ENROLLMENT_DATE = new Prefix(
        "enroll/");
    public static final Prefix PREFIX_STUDENT_MAJOR = new Prefix("maj/");
    public static final Prefix PREFIX_STUDENT_MINOR = new Prefix("min/");
    public static final Prefix PREFIX_MODULE_CODE = new Prefix("code/");
    public static final Prefix PREFIX_MODULE_DEPARTMENT = new Prefix("department/");
    public static final Prefix PREFIX_MODULE_TITLE = new Prefix("title/");
    public static final Prefix PREFIX_MODULE_DESCRIPTION = new Prefix("description/");
    public static final Prefix PREFIX_MODULE_CREDIT = new Prefix("credit/");
    public static final Prefix PREFIX_MODULE_AVAILABLE = new Prefix("available/");
    public static final Prefix PREFIX_MODULE_PREREQ = new Prefix("prereq/");
    public static final Prefix PREFIX_SAVE_PATH = new Prefix("sp/");
    public static final Prefix PREFIX_SWITCH_TAB = new Prefix("tab/");
}
