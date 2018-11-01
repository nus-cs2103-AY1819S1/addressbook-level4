package seedu.address.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;


// @@author chikchengyao

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

    private AdjustableTimeDecreasingMode atdm;
    private TaskBuilder tb;

    private int daysBefore = 7;
    private int overdueXp = 700;
    private int completedXp = 1400;

    @Before
    public void setUp() {
        atdm = new AdjustableTimeDecreasingMode(daysBefore, overdueXp, completedXp);
        tb = new TaskBuilder();
    }

    private Date createDate(int day) {
        return new GregorianCalendar(2018, Calendar.JANUARY, day).getTime();
    }

    @Test
    public void constructor_called_noException() {
        DecreasingMode dm1 = new DecreasingMode();
        DecreasingMode dm2 = new DecreasingMode(7, 50, 100);
    }

    @Test
    public void appraiseXpChange() {

        //Create three versions of one task with different statuses
        Task inProgressTask = tb.withDueDate("09-01-18 0000")
                .withStatus(Status.IN_PROGRESS)
                .build();

        Task overdueTask = tb.withDueDate("09-01-18 0000")
                .withStatus(Status.OVERDUE)
                .build();

        Task completedTask = tb.withDueDate("09-01-18 0000")
                .withStatus(Status.COMPLETED)
                .build();

        // Task completed more than 7 days early
        atdm.setCurrentDate(createDate(1));
        assertEquals(1400, atdm.appraiseXpChange(inProgressTask, completedTask));

        // Task completed exactly 7 days early
        atdm.setCurrentDate(createDate(2));
        assertEquals(1400, atdm.appraiseXpChange(inProgressTask, completedTask));

        // Task completed less than 7 days early
        atdm.setCurrentDate(createDate(3));
        assertEquals(1300, atdm.appraiseXpChange(inProgressTask, completedTask));

        atdm.setCurrentDate(createDate(8));
        assertEquals(800, atdm.appraiseXpChange(inProgressTask, completedTask));

        // Task completed exactly on time
        atdm.setCurrentDate(createDate(9));
        assertEquals(700, atdm.appraiseXpChange(inProgressTask, completedTask));

        // Task completed after due date
        atdm.setCurrentDate(createDate(10));
        assertEquals(700, atdm.appraiseXpChange(overdueTask, completedTask));

        // Task is not completed, but instead reset from "in progress" to "overdue"
        atdm.setCurrentDate(createDate(9));
        assertEquals(0, atdm.appraiseXpChange(inProgressTask, overdueTask));

    }

    @Test
    public void getCurrentDate() {
        // Create new DecreasingMode; cannot use AdjustableTime version to test this
        DecreasingMode dm = new DecreasingMode();
        dm.getCurrentDate();
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
        assertEquals(atdm.interpolateDate(daysBefore, d1, d9), 1);

        // Exactly 7 days before
        assertEquals(atdm.interpolateDate(daysBefore, d2, d9), 1);

        // Less than 7 days before
        assertEquals(atdm.interpolateDate(daysBefore, d3, d9), 6. / 7.);
        assertEquals(atdm.interpolateDate(daysBefore, d8, d9), 1. / 7.);

        // Same time as deadline
        assertEquals(atdm.interpolateDate(daysBefore, d9, d9), 0);

        // After deadline
        assertEquals(0, atdm.interpolateDate(daysBefore, d10, d9));
    }
}
