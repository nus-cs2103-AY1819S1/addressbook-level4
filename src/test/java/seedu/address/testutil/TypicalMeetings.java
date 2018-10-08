package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.group.Meeting;

/**
 * A utility class containing a list of {@code Meeting} objects to be used in tests.
 */
public class TypicalMeetings {

    public static final Meeting WEEKLY = new MeetingBuilder().withTitle("Weekly meetup")
            .withDate("22-02-2017").withLocation("COM1-0202")
            .withDescription("Weekly report of progress").build();

    public static final Meeting URGENT = new MeetingBuilder().withTitle("Urgent affair")
            .withDate("18-08-2017").withLocation("COM2-0202")
            .withDescription("Urgent meeting on the project direction").build();

    public static final Meeting REHEARSAL = new MeetingBuilder().withTitle("Rehearse presentation")
            .withDate("01-01-2018").withLocation("BIZ2-0304")
            .withDescription("Practice product demo").build();

    public static final Meeting DISCUSSION = new MeetingBuilder().withTitle("Settle project features")
            .withDate("08-10-2018").withLocation("AS1, level 3")
            .withDescription("Discuss individual components").build();


    private TypicalMeetings() {} // prevents instantiation


    public static List<Meeting> getTypicalMeetings() {
        return new ArrayList<>(Arrays.asList(WEEKLY, URGENT, REHEARSAL, DISCUSSION));
    }

}
