package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.AchievementPanelHandle;
import seedu.address.commons.events.model.AchievementsUpdatedEvent;
import seedu.address.model.achievement.AchievementRecord;
import seedu.address.model.achievement.Level;
import seedu.address.model.achievement.Xp;
import seedu.address.testutil.AchievementRecordBuilder;

public class AchievementPanelTest extends GuiUnitTest {

    private static final Level OLD_LEVEL = Level.LEVEL_1;
    private static final String OLD_XP_VALUE = "100";
    private static final String OLD_LEVEL_VALUE = OLD_LEVEL.toString();
    private static final String OLD_LEVEL_MAX_XP = Integer.toString(OLD_LEVEL.getMaxXp());

    private static final Level NEW_LEVEL = Level.LEVEL_2;
    private static final String NEW_XP_VALUE = "200";
    private static final String NEW_LEVEL_VALUE = NEW_LEVEL.toString();
    private static final String NEW_LEVEL_MAX_XP = Integer.toString(NEW_LEVEL.getMaxXp());


    private static final AchievementRecord OLD_ACHIEVEMENT_STUB =
            new AchievementRecordBuilder().withXpValue(Integer.valueOf(OLD_XP_VALUE))
                    .withLevel(Level.fromString(OLD_LEVEL_VALUE)).build();

    private AchievementPanelHandle achievementPanelHandle;

    @Before
    public void setUp() {
        AchievementPanel achievementPanel = new AchievementPanel(OLD_ACHIEVEMENT_STUB);
        uiPartRule.setUiPart(achievementPanel);

        achievementPanelHandle = new AchievementPanelHandle(achievementPanel.getRoot());
    }

    private AchievementsUpdatedEvent getAchievementsUpdatedEventStub(String xp, String level) {
        Xp xpField = new Xp(Integer.valueOf(xp));
        Level levelField = Level.fromString(level);
        AchievementRecord achievementRecordStub = new AchievementRecordBuilder()
                .withXp(xpField).withLevel(levelField).build();
        return new AchievementsUpdatedEvent(achievementRecordStub);
    }

    /**
     * Asserts that {@code AchievementPanel} displays the both {@code xpValueLabel} and {@code levelValueLabel}
     * correctly and matches {@code expectedXP} and {@code expectedLevel}.
     */
    private void assertAchievementPanelContent(String expectedXp, String expectedLevel) {
        assertEquals(achievementPanelHandle.getXp(), expectedXp);
        assertEquals(achievementPanelHandle.getLevel(), expectedLevel);
    }

    @Test
    public void display() {
        // initial state
        assertAchievementPanelContent(OLD_XP_VALUE + " / " + OLD_LEVEL_MAX_XP, OLD_LEVEL_VALUE);

        // none of xp and level is updated
        postNow(getAchievementsUpdatedEventStub(OLD_XP_VALUE, OLD_LEVEL_VALUE));
        assertAchievementPanelContent(OLD_XP_VALUE + " / " + OLD_LEVEL_MAX_XP, OLD_LEVEL_VALUE);

        // only xp is updated
        postNow(getAchievementsUpdatedEventStub(NEW_XP_VALUE, OLD_LEVEL_VALUE));
        assertAchievementPanelContent(NEW_XP_VALUE + " / " + OLD_LEVEL_MAX_XP, OLD_LEVEL_VALUE);

        // only level is updated
        postNow(getAchievementsUpdatedEventStub(NEW_XP_VALUE, NEW_LEVEL_VALUE));
        assertAchievementPanelContent(NEW_XP_VALUE + " / " + NEW_LEVEL_MAX_XP, NEW_LEVEL_VALUE);

        // both xp and level are updated
        postNow(getAchievementsUpdatedEventStub(OLD_XP_VALUE, OLD_LEVEL_VALUE));
        assertAchievementPanelContent(OLD_XP_VALUE + " / " + OLD_LEVEL_MAX_XP, OLD_LEVEL_VALUE);
    }
}

