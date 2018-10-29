package seedu.address.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

import java.awt.event.AdjustmentEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DecreasingModeTest {

    static class AdjustableTimeDecreasingMode extends DecreasingMode {

        protected Date currentDate;

        AdjustableTimeDecreasingMode(int daysBefore, int overdueXp, int completedXp) {
            super(daysBefore, overdueXp, completedXp);
        }

        @Override
        protected Date getCurrentDate() {
            if (currentDate == null) {
                throw new RuntimeException("Set currentDate before using");
            }

            return currentDate;
        }

        protected void setCurrentDate(Date date) {
            this.currentDate = date;
        }
    }

    private AdjustableTimeDecreasingMode dm;
    TaskBuilder tb;

    private int daysBefore = 7;
    private int overdueXp = 700;
    private int completedXp = 1400;

    @Before
    public void setUp() {
        dm = new AdjustableTimeDecreasingMode(daysBefore, overdueXp, completedXp);
        tb = new TaskBuilder();
    }

    private Date createDate(int day) {
        return new GregorianCalendar(2018, Calendar.JANUARY, day).getTime();
    }

    @Test
    public void appraiseXpChange() {
        Task inProgressTask = tb.withDueDate("09-01-18 0000")
                .withStatus(Status.IN_PROGRESS)
                .build(); // Creates task due 9th Jan 2018

        Task completedTask = tb.withDueDate("09-01-18 0000")
                .withStatus(Status.COMPLETED)
                .build();

        // Task completed more than 7 days early
        dm.setCurrentDate(createDate(1));
        assertEquals(1400, dm.appraiseXpChange(inProgressTask, completedTask));

        // Task completed exactly 7 days early
        dm.setCurrentDate(createDate(2));
        assertEquals(1400, dm.appraiseXpChange(inProgressTask, completedTask));

        // Task completed less than 7 days early
        dm.setCurrentDate(createDate(3));
        assertEquals(1300, dm.appraiseXpChange(inProgressTask, completedTask));

        dm.setCurrentDate(createDate(8));
        assertEquals(800, dm.appraiseXpChange(inProgressTask, completedTask));

        // Task completed exactly on time
        dm.setCurrentDate(createDate(9));
        assertEquals(700, dm.appraiseXpChange(inProgressTask, completedTask));

        // Task completed after due date
        dm.setCurrentDate(createDate(10));
        assertEquals(700, dm.appraiseXpChange(inProgressTask, completedTask));

    }

    @Test
    public void interpolateDate() {
        Date d1 = createDate(1);
        Date d2 = createDate(2);
        Date d3 = createDate(3);
        Date d8 = createDate(8);
        Date d9 = createDate(9); // The due date
        Date d10 = createDate(10);

        // Earlier than 7 (daysBefore)
        assertEquals(dm.interpolateDate(daysBefore, d1, d9), 1);

        // Exactly 7 days before
        assertEquals(dm.interpolateDate(daysBefore, d2, d9), 1);

        // Less than 7 days before
        assertEquals(dm.interpolateDate(daysBefore, d3, d9), 6. / 7.);
        assertEquals(dm.interpolateDate(daysBefore, d8, d9), 1. / 7.);

        // Same time as deadline
        assertEquals(dm.interpolateDate(daysBefore, d9, d9), 0);

        // After deadline
        assertEquals(0, dm.interpolateDate(daysBefore, d10, d9));
    }
}