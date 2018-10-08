package seedu.address.model.person;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;

class TimeTableTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TimeTable(null));
    }

    @Test
    void convertToSchedule() {
    }

    @Test
    void getLessonList() {
    }

    @Test
    void setLessonList() {
    }
}
