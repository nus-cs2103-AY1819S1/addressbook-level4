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

    private Xp xp;
    private Level level;


    /**
     * Constructs a {@code AchievementRecord}.
     * Xp value is initialized to 0 and Level initialized to LEVEL_1.
     */
    public AchievementRecord() {
        this.xp = new Xp();
        this.level = Level.LEVEL_1;
    }

    /**
     * Constructs a {@code AchievementRecord}.
     * Both fields must be present.
     */
    public AchievementRecord(Xp xp, Level level) {
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

    public Xp getXp() {
        return xp;
    }

    public void setXp(Xp xp) {
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
     * Updates the Xp field of this {@code AchievementRecord} with new xp value.
     * Triggers the update of Level with xp.
     */
    public void updateXp(Integer xp) {
        requireNonNull(xp);

        Integer newXpValue = this.xp.getXp() + xp;
        this.setXp(new Xp(newXpValue));
        updateLevelWithXp(newXpValue);
    }

    /**
     * Updates the Level field of this {@code AchievementRecord} with new xp value.
     */
    public void updateLevelWithXp(Integer xp) {
        Level newLevel = getMatchingLevel(xp);
        if (!this.level.equals(newLevel)) {
            setLevel(newLevel);
        }
    }

    /**
     * Returns the corresponding {@code Level} of the current Xp value.
     * Maximum level is level 5.
     */
    public Level getMatchingLevel(Integer xp) {
        if (xp < Level.LEVEL_1.getMaxXp()) {
            return Level.LEVEL_1;
        } else if (xp < Level.LEVEL_2.getMaxXp()) {
            return level.LEVEL_2;
        } else if (xp < Level.LEVEL_3.getMaxXp()) {
            return level.LEVEL_3;
        } else if (xp < Level.LEVEL_4.getMaxXp()) {
            return level.LEVEL_4;
        } else {
            return Level.LEVEL_5;
        }
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
