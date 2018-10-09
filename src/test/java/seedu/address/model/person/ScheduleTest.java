package seedu.address.model.person;

import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;


class ScheduleTest {

    private String emptyScheduleString = "0000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "000000000000000000000000000000000000000000000000000"
        +
        "00000000";

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Schedule(null));
    }

    @Test
    void isValidSchedule() {
        assert (Schedule.isValidSchedule(VALID_SCHEDULE) == true);
    }

    @Test
    void getTimeDay() {
        Schedule s = new Schedule();
        assert (s.getTimeDay("monday", "0100") == false);
        assert (s.getTimeDay("tuesday", "0100") == false);
        assert (s.getTimeDay("wednesday", "0100") == false);
        assert (s.getTimeDay("thursday", "0100") == false);
        assert (s.getTimeDay("friday", "0100") == false);
        assert (s.getTimeDay("saturday", "0100") == false);
        assert (s.getTimeDay("sunday", "0100") == false);
        //s.getTimeDay("unknown", "0100");
        assert (s.getTimeDay("monday", "0130") == false);

    }

    @Test
    void setTimeDay() {
        Schedule s = new Schedule();
        s.setTimeDay("monday", "0100", true);
        s.setTimeDay("monday", "0130", true);
        Assert.assertThrows(ArrayIndexOutOfBoundsException.class, () -> s
            .setTimeDay("unknown", "0130", true)
        );
    }

    @Test
    void valueToString() {
        Schedule s = new Schedule();
        assert (s.valueToString().equals(emptyScheduleString));
    }

    @Test
    void maxSchedule() {
        Schedule s1 = new Schedule();
        Schedule s2 = new Schedule();
        s1.maxSchedule(s2);
        assert (s1.valueToString().equals(s2.valueToString()));
    }


    @Test
    void xor() {
        Schedule s1 = new Schedule();
        Schedule s2 = new Schedule();
        s1.xor(s2);
        assert (s1.valueToString().equals(emptyScheduleString));
    }
}
