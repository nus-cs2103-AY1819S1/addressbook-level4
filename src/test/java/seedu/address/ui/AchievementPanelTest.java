package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.AchievementPanelHandle;
import seedu.address.commons.events.model.AchievementsUpdatedEvent;
import seedu.address.model.achievement.AchievementRecord;
import seedu.address.model.achievement.Level;
import seedu.address.testutil.AchievementRecordBuilder;

public class AchievementPanelTest extends GuiUnitTest {

    private static final int XP = 3000;
    private static final Level LEVEL = Level.LEVEL_4;
    private static final int NUM_TASK = 60;

    private static final int XP_BY_DAY = 200;
    private static final int NUM_TASK_BY_DAY = 8;

    private static final int XP_BY_WEEK = 1000;
    private static final int NUM_TASK_BY_WEEK = 40;

    private static final int NEW_XP = 100001;
    private static final Level NEW_LEVEL = Level.LEVEL_5;
    private static final int NEW_NUM_TASK = 120;

    private static final int NEW_XP_BY_DAY = 400;
    private static final int NEW_NUM_TASK_BY_DAY = 16;

    private static final int NEW_XP_BY_WEEK = 2000;
    private static final int NEW_NUM_TASK_BY_WEEK = 80;

    private final AchievementRecord record = new AchievementRecordBuilder()
            .withXpValue(XP)
            .withLevel(LEVEL)
            .withNumTaskCompleted(NUM_TASK)
            .withXpValueByDay(XP_BY_DAY)
            .withNumTaskCompletedByDay(NUM_TASK_BY_DAY)
            .withXpValueByWeek(XP_BY_WEEK)
            .withNumTaskCompletedByWeek(NUM_TASK_BY_WEEK)
            .build();

    private final AchievementRecord newRecord = new AchievementRecordBuilder()
            .withXpValue(NEW_XP)
            .withLevel(NEW_LEVEL)
            .withNumTaskCompleted(NEW_NUM_TASK)
            .withXpValueByDay(NEW_XP_BY_DAY)
            .withNumTaskCompletedByDay(NEW_NUM_TASK_BY_DAY)
            .withXpValueByWeek(NEW_XP_BY_WEEK)
            .withNumTaskCompletedByWeek(NEW_NUM_TASK_BY_WEEK)
            .build();

    private final String today = "12-10-19";

    private AchievementPanelHandle achievementPanelHandle;

    @Before
    public void setUp() {
        AchievementPanel achievementPanel = new AchievementPanel(new AchievementRecordBuilder().build());
        uiPartRule.setUiPart(achievementPanel);

        achievementPanelHandle = new AchievementPanelHandle(achievementPanel.getRoot());
    }

    /**
     * Asserts that {@code AchievementPanel} displays the both {@code xpValueLabel} and {@code levelValueLabel}
     * correctly and matches {@code expectedXP} and {@code expectedLevel}.
     */
    private void assertAchievementPanelContent(String expectedTimeSpan, String expectedXp, String expectedLevel,
                                               String expectedNumTask) {
        assertEquals(achievementPanelHandle.getTimeSpan(), expectedTimeSpan);
        assertEquals(achievementPanelHandle.getXp(), expectedXp);
        assertEquals(achievementPanelHandle.getLevel(), expectedLevel);
        assertEquals(achievementPanelHandle.getNumTask(), expectedNumTask);
    }

    @Test
    public void display_allTimeAchievements() {
        postNow(new AchievementsUpdatedEvent(record));
        assertAchievementPanelContent(AchievementPanel.ALL_TIME_TIME_SPAN,
                String.format(AchievementPanel.CURRENT_TO_MAX_FORMAT, Integer.toString(XP),
                        Integer.toString(LEVEL.getMaxXp())),
                LEVEL.toString(),
                Integer.toString(NUM_TASK));

        // reflect updated achievement record
        // new record has xp above level 5 max xp, xp display format is updated
        postNow(new AchievementsUpdatedEvent(newRecord));
        assertAchievementPanelContent(AchievementPanel.ALL_TIME_TIME_SPAN,
                Integer.toString(NEW_XP),
                NEW_LEVEL.toString(),
                Integer.toString(NEW_NUM_TASK));
    }

    @Test
    public void display_todayAchievements() {
        AchievementRecord record = new AchievementRecordBuilder(this.record)
                .withDisplayOption(AchievementRecord.DISPLAY_TODAY)
                .build();
        postNow(new AchievementsUpdatedEvent(record));
        assertAchievementPanelContent(String.format(AchievementPanel.TODAY_TIME_SPAN_FORMAT, today),
                Integer.toString(XP_BY_DAY),
                LEVEL.toString(),
                Integer.toString(NUM_TASK_BY_DAY));

        // reflect updated achievement record
        AchievementRecord newRecord = new AchievementRecordBuilder(this.newRecord)
                .withDisplayOption(AchievementRecord.DISPLAY_TODAY)
                .build();
        postNow(new AchievementsUpdatedEvent(newRecord));
        assertAchievementPanelContent(String.format(AchievementPanel.TODAY_TIME_SPAN_FORMAT, today),
                Integer.toString(NEW_XP_BY_DAY),
                NEW_LEVEL.toString(),
                Integer.toString(NEW_NUM_TASK_BY_DAY));
    }

    @Test
    public void display_thisWeekAchievements() {
        AchievementRecord record = new AchievementRecordBuilder(this.record)
                .withDisplayOption(AchievementRecord.DISPLAY_THIS_WEEK)
                .build();
        postNow(new AchievementsUpdatedEvent(record));
        assertAchievementPanelContent(String.format(AchievementPanel.THIS_WEEK_TIME_SPAN_FORMAT, today),
                Integer.toString(XP_BY_WEEK),
                LEVEL.toString(),
                Integer.toString(NUM_TASK_BY_WEEK));

        // reflect updated achievement record
        AchievementRecord newRecord = new AchievementRecordBuilder(this.newRecord)
                .withDisplayOption(AchievementRecord.DISPLAY_THIS_WEEK)
                .build();
        postNow(new AchievementsUpdatedEvent(newRecord));
        assertAchievementPanelContent(String.format(AchievementPanel.THIS_WEEK_TIME_SPAN_FORMAT, today),
                Integer.toString(NEW_XP_BY_WEEK),
                NEW_LEVEL.toString(),
                Integer.toString(NEW_NUM_TASK_BY_WEEK));
    }
}

