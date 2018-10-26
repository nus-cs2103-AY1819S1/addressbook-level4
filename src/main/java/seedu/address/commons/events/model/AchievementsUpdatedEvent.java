package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.achievement.AchievementRecord;

/**
 * Indicates the achievements to be reflected on UI is changed.
 */
public class AchievementsUpdatedEvent extends BaseEvent {

    public final AchievementRecord data;

    public AchievementsUpdatedEvent(AchievementRecord data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Achievements are updated";
    }
}

