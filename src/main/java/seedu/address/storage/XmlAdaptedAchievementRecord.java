package seedu.address.storage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.achievement.AchievementRecord;
import seedu.address.model.achievement.Level;
import seedu.address.model.achievement.Xp;

/**
 * JAXB-friendly version of the {@code AchievementRecord}.
 */
public class XmlAdaptedAchievementRecord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Achievement record's %s field is missing!";

    @XmlElement(required = true)
    private String xp;
    @XmlElement(required = true)
    private String level;

    /**
     * Constructs an {@code XmlAdaptedAchievementRecord}.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAchievementRecord() {}

    /**
     * Converts a given AchievementRecord into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created {@code XmlAdaptedAchievementRecord}.
     */
    public XmlAdaptedAchievementRecord(AchievementRecord source) {
        requireNonNull(source);

        xp = source.getXp().getXp().toString();
        level = source.getLevel().toString();
    }

    /**
     * Constructs an {@code XmlAdaptedAchievementRecord} with the given achievement details.
     */
    public XmlAdaptedAchievementRecord(String xp, String level) {
        requireAllNonNull(xp, level);

        this.xp = xp;
        this.level = level;
    }

    /**
     * Converts this jaxb-friendly {@code XmlAdaptedAchievementRecord} object into the model's
     * {@code AchievementRecord} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted achievement record.
     */
    public AchievementRecord toModelType() throws IllegalValueException {
        if (xp == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Xp.class.getSimpleName()));
        }
        Integer xpValue;
        try {
            xpValue = Integer.valueOf(xp);
        } catch (NumberFormatException nfex) {
            throw new IllegalValueException(Xp.MESSAGE_XP_CONSTRAINTS);
        }
        if (!Xp.isValidXp(xpValue)) {
            throw new IllegalValueException(Xp.MESSAGE_XP_CONSTRAINTS);
        }
        final Xp modelXp = new Xp(xpValue);

        if (level == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Level.class.getSimpleName()));
        }
        if (!Level.isValidLevel(level)) {
            throw new IllegalValueException(Level.MESSAGE_LEVEL_CONSTRAINTS);
        }
        final Level modelLevel = Level.fromString(level);

        return new AchievementRecord(modelXp, modelLevel);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAchievementRecord)) {
            return false;
        }

        XmlAdaptedAchievementRecord otherRecord = (XmlAdaptedAchievementRecord) other;
        return Objects.equals(xp, otherRecord.xp)
                && Objects.equals(level, otherRecord.level);
    }
}
