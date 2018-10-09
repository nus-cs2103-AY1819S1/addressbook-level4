package seedu.address.model.person;

import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;


class ScheduleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Schedule(null));
    }

    @Test
    void isValidSchedule() {
        Schedule.isValidSchedule(VALID_SCHEDULE);
    }

    @Test
    void getTimeDay() {
        Schedule s = new Schedule();
        s.getTimeDay("monday", "0100");
        s.getTimeDay("tuesday", "0100");
        s.getTimeDay("wednesday", "0100");
        s.getTimeDay("thursday", "0100");
        s.getTimeDay("friday", "0100");
        s.getTimeDay("saturday", "0100");
        s.getTimeDay("sunday", "0100");
        //s.getTimeDay("unknown", "0100");
        s.getTimeDay("monday", "0130");
    }

    @Test
    void setTimeDay() {
        Schedule s = new Schedule();
        s.setTimeDay("monday", "0100", true);
        s.setTimeDay("monday", "0130", true);
    }

    @Test
    void valueToString() {
        Schedule s = new Schedule();
        s.valueToString();
    }

    @Test
    void maxSchedule() {
        Schedule s1 = new Schedule();
        Schedule s2 = new Schedule();
        s1.maxSchedule(s2);
    }


    @Test
    void xor() {
        Schedule s1 = new Schedule();
        Schedule s2 = new Schedule();
        s1.xor(s2);
    }
}
