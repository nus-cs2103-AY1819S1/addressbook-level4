package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.game.DecreasingMode;
import seedu.address.model.game.FlatMode;
import seedu.address.model.game.GameManager;
import seedu.address.model.game.GameMode;
import seedu.address.model.game.IncreasingMode;
import seedu.address.model.game.PriorityMode;

/**
 * JAXB-friendly version of the {@code GameManager}.
 */
public class XmlAdaptedGameManager {

    @XmlElement(required = true)
    private String gameMode;

    @XmlElement
    private String period;

    @XmlElement
    private String lowXp;

    @XmlElement
    private String highXp;

    /**
     * Constructs an {@code XmlAdaptedAchievementRecord}.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGameManager() {}

    /**
     * Converts a given AchievementRecord into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created {@code XmlAdaptedAchievementRecord}.
     */
    public XmlAdaptedGameManager(GameManager source) {
        requireNonNull(source);

        GameMode mode = source.getGameMode();

        gameMode = mode.getClass().getSimpleName();
        period = Integer.toString(mode.getPeriod());
        lowXp = Integer.toString(mode.getLowXp());
        highXp = Integer.toString(mode.getHighXp());
    }

    /**
     * Converts this jaxb-friendly {@code XmlAdaptedGameManager} object into the model's
     * {@code GameManager} object.
     * @return
     * @throws IllegalValueException
     */
    public GameManager toModelType() throws IllegalValueException {
        // If game type is null, return new GameManager
        if (gameMode == null) {
            // If GameManager does not exist in storage, create new one.
            return new GameManager();
        }

        int periodInt = Integer.parseInt(period);
        int lowInt = Integer.parseInt(lowXp);
        int highInt = Integer.parseInt(highXp);

        GameMode mode;
        switch(gameMode) {
        case "FlatMode":
            mode = new FlatMode(periodInt, lowInt, highInt);
            break;
        case "DecreasingMode":
            mode = new DecreasingMode(periodInt, lowInt, highInt);
            break;
        case "IncreasingMode":
            mode = new IncreasingMode(periodInt, lowInt, highInt);
            break;
        case "PriorityMode":
            mode = new PriorityMode(periodInt, lowInt, highInt);
            break;
        default:
            // Illegal gameMode -- return a new default one
            return new GameManager();
        }

        return new GameManager(mode);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedGameManager)) {
            return false;
        }

        XmlAdaptedGameManager xmlAdaptedGameManager = (XmlAdaptedGameManager) other;

        return Objects.equals(this.gameMode, xmlAdaptedGameManager.gameMode)
                && Objects.equals(this.period, xmlAdaptedGameManager.period)
                && Objects.equals(this.lowXp, xmlAdaptedGameManager.lowXp)
                && Objects.equals(this.highXp, xmlAdaptedGameManager.highXp);
    }
}
