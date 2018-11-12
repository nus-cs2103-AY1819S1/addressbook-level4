package seedu.modsuni.testutil;

import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_EMPLOY_DATE_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_SALARY_AMY;

import seedu.modsuni.model.user.Admin;
import seedu.modsuni.model.user.Role;

/**
 * A list of typical admin.
 */
public class TypicalAdmins {

    public static final String MASTER_DATA = "masterconfig.xml";

    public static final Admin MASTER = new AdminBuilder()
        .withUsername("master")
        .withName("master")
        .withEmployedDate("01/11/2001")
        .withRole(Role.ADMIN)
        .withSalary("9999")
        .build();

    public static final Admin ALICE = new AdminBuilder().withName("Alice Pauline")
            .withRole(Role.ADMIN)
            .withSalary("3000")
            .withEmployedDate("01/01/2000").build();

    public static final Admin BRAD = new AdminBuilder().withName("Brad Bread")
            .withRole(Role.ADMIN)
            .withSalary("3500")
            .withEmployedDate("01/01/2006").build();

    public static final Admin MORGAN = new AdminBuilder().withName("Morgan More")
            .withRole(Role.ADMIN)
            .withSalary("4000")
            .withEmployedDate("01/04/2010").build();

    public static final Admin AMY = new AdminBuilder().withName(VALID_NAME_AMY)
            .withRole(Role.ADMIN)
            .withSalary(VALID_SALARY_AMY)
            .withEmployedDate(VALID_EMPLOY_DATE_AMY).build();
}
