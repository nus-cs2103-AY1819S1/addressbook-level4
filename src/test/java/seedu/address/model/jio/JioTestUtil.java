package seedu.address.model.jio;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Name;
import seedu.address.model.restaurant.Address;
import seedu.address.model.timetable.Date;
import seedu.address.model.timetable.Day;
import seedu.address.model.timetable.Time;
import seedu.address.model.timetable.Week;
import seedu.address.model.user.Email;
import seedu.address.model.user.Password;
import seedu.address.model.user.Phone;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.testutil.TypicalUsers;

/**
 * Sample jios for testing.
 */
public class JioTestUtil {
    public static final User JANE = new User(new Username("jane"), new Password("password"),
            new seedu.address.model.Name("jane"), new Phone("12345678"), new Email("jane@gmail.com"));

    public static final seedu.address.model.Name LUNCH_NAME = new seedu.address.model.Name("lunch");
    public static final Date LUNCH_DATE = new Date(new Week("1"), new Day("mon"), new Time("1200"));
    public static final Address LUNCH_ADDRESS = new Address("finefood");
    public static final Username CREATOR = TypicalUsers.getTypicalUsers().get(0).getUsername();
    public static final List<Username> PEOPLE = new ArrayList<>(Arrays.asList(
            TypicalUsers.getTypicalUsers().get(0).getUsername(),
            TypicalUsers.getTypicalUsers().get(1).getUsername(),
            TypicalUsers.getTypicalUsers().get(2).getUsername()
    ));

    public static final Jio LUNCH = new Jio(LUNCH_NAME, LUNCH_DATE, LUNCH_ADDRESS, PEOPLE, CREATOR);
    public static final Jio LUNCH_COPY = new Jio(LUNCH_NAME, LUNCH_DATE, LUNCH_ADDRESS, PEOPLE, CREATOR);

    public static final seedu.address.model.Name DINNER_NAME = new seedu.address.model.Name("dinner");
    public static final Date DINNER_DATE = new Date(new Week("2"), new Day("tue"), new Time("1800"));
    public static final Address DINNER_ADDRESS = new Address("foodclique");

    public static final Jio DINNER = new Jio(DINNER_NAME, DINNER_DATE, DINNER_ADDRESS, CREATOR);

    public static final String VALID_NAME_MALA = "mala";
    public static final String VALID_WEEK_MALA = "reading";
    public static final String VALID_DAY_MALA = "mon";
    public static final String VALID_TIME_MALA = "1200";
    public static final String VALID_ADDRESS_MALA = "Waa Cow";
    public static final String VALID_GROUP_MALA = "2103";

    public static final Date MALA_DATE = new Date(new Week(VALID_WEEK_MALA),
            new Day(VALID_DAY_MALA), new Time(VALID_TIME_MALA));
    public static final Jio MALA = new Jio(new Name(VALID_NAME_MALA), MALA_DATE, new Address(VALID_ADDRESS_MALA));

    public static final String NAME_DESC_MALA = " " + PREFIX_NAME + VALID_NAME_MALA;
    public static final String WEEK_DESC_MALA = " " + PREFIX_WEEK + VALID_WEEK_MALA;
    public static final String DAY_DESC_MALA = " " + PREFIX_DAY + VALID_DAY_MALA;
    public static final String TIME_DESC_MALA = " " + PREFIX_TIME + VALID_TIME_MALA;
    public static final String ADDRESS_DESC_MALA = " " + PREFIX_ADDRESS + VALID_ADDRESS_MALA;
    public static final String GROUP_DESC_MALA = " " + PREFIX_GROUP + VALID_GROUP_MALA;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "lunch&"; // '&' not allowed in names
    public static final String INVALID_WEEK_DESC = " " + PREFIX_WEEK + "exam"; // not a week
    public static final String INVALID_DAY_DESC = " " + PREFIX_DAY + "lolday"; // not a day
    public static final String INVALID_TIME_DESC = " " + PREFIX_TIME + "2567"; // not a time
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses

}
