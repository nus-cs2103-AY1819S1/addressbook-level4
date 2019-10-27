package seedu.address.commons.events.model;

import java.nio.file.Path;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.resume.Resume;

/** Indicates that a resume is being saved. */
public class ResumeSaveEvent extends BaseEvent {

    public final Resume data;

    public final Path filePath;

    public ResumeSaveEvent(Resume data, Path filePath) {
        this.data = data;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "save resume to " + filePath.toString();
    }
}
