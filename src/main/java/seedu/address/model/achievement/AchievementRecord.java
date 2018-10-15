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
     * Update the XP field of this {@code AchievementRecord} with new xp value.
     * Triggers the update of Level with xp.
     */
    public void updateXP(Integer xp) {
        requireNonNull(xp);

        Integer newXpValue = this.xp.getXp() + xp;
        this.setXp(new XP(newXpValue));
        updateLevelWithXP(newXpValue);
    }

    /**
     * Update the Level field of this {@code AchievementRecord} with new xp value.
     */
    public void updateLevelWithXP(Integer xp) {
        Level newLevel = getMatchingLevel(xp);
        if(!this.level.equals(newLevel)) {
            setLevel(newLevel);
        }
    }

    /**
     * Returns the corresponding {@code Level} of the current XP value.
     * Maximum level is level 5.
     */
    public Level getMatchingLevel(Integer xp) {
        if(xp < Level.LEVEL_1.getMaxXP()) {
            return Level.LEVEL_1;
        }
        if(xp < Level.LEVEL_2.getMaxXP()) {
            return level.LEVEL_2;
        }
        if(xp < Level.LEVEL_3.getMaxXP()) {
            return level.LEVEL_3;
        }
        if(xp < Level.LEVEL_4.getMaxXP()) {
            return level.LEVEL_4;
        }
        return Level.LEVEL_5;
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
