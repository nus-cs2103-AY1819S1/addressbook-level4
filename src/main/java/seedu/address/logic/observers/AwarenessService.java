package seedu.address.logic.observers;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CommandParser.BASIC_COMMAND_FORMAT;

import java.util.Optional;
import java.util.regex.Matcher;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.ContextUpdateEvent;
import seedu.address.model.Model;

/** Represents the service that continually informs the user about ResuMaker's guess */
public class AwarenessService implements CmdLineObserver {

    public static final String MESSAGE_AVAILABLE_ENTRY = "Pre-filled Resume entry available for: %s";
    public static final String MESSAGE_NO_ENTRY = "No pre-filled resume entry for: %s";
    private static final String COMMAND_WORD = "nus";

    private final Model model;

    public AwarenessService(Model model) {
        requireNonNull(model);
        this.model = model;
    }

    @Override
    public Optional<BaseEvent> observe(String currentInput) {

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(currentInput.trim());

        if (!matcher.matches()) {
            return Optional.empty();
        }

        final String commandWord = matcher.group("commandWord");
        final String trimmedArgs = matcher.group("arguments").trim();

        if (!commandWord.equals(COMMAND_WORD)) {
            return Optional.empty();
        }

        final String possibleEvent = model.getPossibleEventName(trimmedArgs);

        if (model.getContextualResumeEntry(possibleEvent).isPresent()) {
            return Optional.of(new ContextUpdateEvent(String.format(MESSAGE_AVAILABLE_ENTRY, possibleEvent)));
        } else {
            return Optional.of(new ContextUpdateEvent(String.format(MESSAGE_NO_ENTRY, possibleEvent)));
        }

    }

}
