package seedu.address.testutil;

import static seedu.address.testutil.TypicalMeetings.DISCUSSION;
import static seedu.address.testutil.TypicalMeetings.REHEARSAL;
import static seedu.address.testutil.TypicalMeetings.URGENT;
import static seedu.address.testutil.TypicalMeetings.WEEKLY;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.group.Group;


// @@author Derek-Hardy
/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 *
 * {@author Derek-Hardy}
 */
public class TypicalGroups {

    public static final Group PROJECT_2103T = new GroupBuilder().withTitle("CS2103T Project Team")
            .withDescription("Project group for module CS2103T").withMeeting(WEEKLY)
            .withNewPerson(ALICE).build();

    public static final Group GROUP_2101 = new GroupBuilder().withTitle("CS2101 Project Team")
            .withDescription("CS2101 Presentation team").withMeeting(REHEARSAL).build();

    public static final Group NUS_COMPUTING = new GroupBuilder().withTitle("COMPUTING")
            .withDescription("Discussion group for Soc students")
            .withMeeting(DISCUSSION)
            .withNewPerson(CARL).withNewPerson(DANIEL).withNewPerson(ELLE).build();

    public static final Group NUS_BASKETBALL = new GroupBuilder().withTitle("Basketball Clique")
            .withDescription("NUS basketball")
            .withMeeting(URGENT)
            .withNewPerson(CARL).withNewPerson(ELLE).build();

    public static final Group GROUP_0 = new GroupBuilder().withTitle("group").build();

    private TypicalGroups() {} // prevent instantiation

    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(PROJECT_2103T, GROUP_2101,
                NUS_COMPUTING, NUS_BASKETBALL));
    }

}
