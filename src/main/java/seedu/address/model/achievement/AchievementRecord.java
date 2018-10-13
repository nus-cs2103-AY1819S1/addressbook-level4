package seedu.address.model.achievement;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a record of the user's achievements while using the task manager.
 * Achievements include the experience points(xp) earned by completing the task and the level a user has reached.
 * Guarantees: details are present and not null.
 */
public class AchievementRecord {

    private XP xp;
    private Level level;


    /**
     * Constructs a {@code AchievementRecord}.
     * XP value is initialized to 0 and Level initialized to LEVEL_1.
     */
    public AchievementRecord() {
        this.xp = new XP();
        this.level = Level.LEVEL_1;
    }

    /**
     * Constructs a {@code AchievementRecord}.
     * Both fields must be present.
     */
    public AchievementRecord(XP xp, Level level) {
        requireAllNonNull(xp, level);
        this.xp = xp;
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public XP getXp() {
        return xp;
    }

    public void setXp(XP xp) {
        this.xp = xp;
    }

    /**
     * Resets the existing data of this {@code AchievementRecord} with {@code newData}.
     */
    public void resetData(AchievementRecord newData) {
        requireNonNull(newData);

        setXp(newData.getXp());
        setLevel(newData.getLevel());
    }

    /**
     * Returns the backing object as an {@code SimpleObjectProperty}.
     */
    public SimpleObjectProperty<AchievementRecord> asSimpleObjectProperty() {
        return new SimpleObjectProperty<>(this);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AchievementRecord // instanceof handles nulls
                && xp.equals(((AchievementRecord) other).xp))
                && level.equals(((AchievementRecord) other).level);
    }
}
