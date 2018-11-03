package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("-n");
    public static final Prefix PREFIX_PHONE = new Prefix("-p");
    public static final Prefix PREFIX_EMAIL = new Prefix("-e");
    public static final Prefix PREFIX_SALARY = new Prefix("-s");
    public static final Prefix PREFIX_ADDRESS = new Prefix("-a");
    public static final Prefix PREFIX_PROJECT = new Prefix("-t");
    public static final Prefix PREFIX_USERNAME = new Prefix("-u");

    /* Prefix for assignment*/
    public static final Prefix PREFIX_ASSIGNMENT_NAME = new Prefix("-an");
    public static final Prefix PREFIX_AUTHOR = new Prefix("-au");
    public static final Prefix PREFIX_ASSIGNMENT_DESCRIPTION = new Prefix("-d");
    public static final Prefix PREFIX_PROJECT_TAG = new Prefix("-pj");

    /* Prefix definitions for leave application feature */
    public static final Prefix PREFIX_LEAVE_DESCRIPTION = new Prefix("-de");
    public static final Prefix PREFIX_LEAVE_DATE = new Prefix("-da");

    /* Prefix for permission*/
    public static final Prefix PREFIX_ADD_PERMISSION = new Prefix("-a");
    public static final Prefix PREFIX_REMOVE_PERMISSION = new Prefix("-r");
}
