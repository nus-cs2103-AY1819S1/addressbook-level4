package seedu.address.testutil;

import static seedu.address.testutil.TypicalMeetings.WEEKLY;

import java.util.Optional;

import seedu.address.model.group.Group;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.shared.Description;
import seedu.address.model.shared.Title;

// @@author Derek-Hardy
/**
 * A utility class to help with building Group objects.
 *
 * {@author Derek-Hardy}
 */
public class GroupBuilder {

    public static final String DEFAULT_TITLE = "CS2103T";
    public static final String DEFAULT_DESCRIPTION = "The project group for CS2103T";
    public static final Meeting DEFAULT_MEETING = WEEKLY;

    private Title title;
    private Description description;
    private Meeting meeting;
    private UniquePersonList members;
    private boolean isMemberModified = false;

    public GroupBuilder() {
        title = new Title(DEFAULT_TITLE);
        description = new Description(DEFAULT_DESCRIPTION);
        meeting = DEFAULT_MEETING;
        members = new UniquePersonList();
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        title = groupToCopy.getTitle();
        description = groupToCopy.getDescription();
        meeting = groupToCopy.getMeeting();
        members = groupToCopy.getMembers();
        isMemberModified = true;
    }

    /**
     * Sets the {@code Title} of the {@code Group} that we are building.
     */
    public GroupBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Group} that we are building.
     */
    public GroupBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Meeting} of the {@code Group} that we are building.
     */
    public GroupBuilder withMeeting(Meeting meeting) {
        this.meeting = meeting;
        return this;
    }

    /**
     * Sets the {@code UniquePersonList} of the {@code Group} that we are building.
     */
    public GroupBuilder withNewPerson(Person person) {
        this.isMemberModified = true;
        this.members.add(person);
        return this;
    }

    /**
     * Sets the {@code UniquePersonList} of the {@code Group} that we are building.
     */
    public GroupBuilder withRemovedPerson(Person person) {
        this.isMemberModified = true;
        this.members.remove(person);
        return this;
    }

    /**
     * Build a new group from GroupBuilder
     *
     * @return The new group
     */
    public Group build() {
        Group group;

        if (meeting == null && !isMemberModified) {
            group = new Group(title);
        } else if (meeting == null) {
            group = new Group(title, description, members);
        } else if (!isMemberModified) {
            group = new Group(title, description, meeting);
        } else {
            group = new Group(title, Optional.of(description), Optional.of(meeting), members);
        }

        return group;
    }
}
