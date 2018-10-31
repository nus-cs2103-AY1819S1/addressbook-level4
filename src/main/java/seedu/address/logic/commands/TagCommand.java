package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.PersonContainsTagPredicate;
import seedu.address.model.tag.Tag;

//@@author A19Sean
/**
 * Finds and lists all persons in address book whose tag matches the argument keyword.
 * Keyword matching is case insensitive.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": View, edit or delete all contacts with a specified (case-sensitive) tag.\n"
            + "Parameters for view or delete functionality: TAG [MORE TAGS]... [delete]\n"
            + "Parameters for edit functionality: edit EXISTING_TAG NEW_TAG\n"
            + "Example: " + COMMAND_WORD + " Work Friends Important\n"
            + "will list out all users with \"Work\", \"Friends\" or \"Important\" tags.\n"
            + "Example: " + COMMAND_WORD + " Work Friends Important delete\n"
            + "will delete all \"Work\", \"Friends\" and \"Important\" tags from contacts.\n"
            + "Example: " + COMMAND_WORD + " edit Work Colleague\n"
            + "will change the \"Work\" tag to \"Colleage\" for all tagged contacts.";

    /**
     * Enums for the possible actions that can be performed from a tag command.
     */
    public enum Action {
        FIND,
        DELETE,
        EDIT
    }

    private final PersonContainsTagPredicate predicate;

    private final Action action;

    private final List<String> tags;

    public TagCommand(PersonContainsTagPredicate predicate, Action action, List<String> tags) {
        this.predicate = predicate;
        this.action = action;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        String message;
        if (this.action == Action.DELETE) {
            message = Messages.MESSAGE_TAG_DELETED_OVERVIEW;
            model.updateFilteredPersonList(predicate);
            List<Person> currentList = new ArrayList<>(model.getFilteredPersonList());
            List<Person> updatedList = new ArrayList<>();
            currentList.forEach(person -> {
                Set<Tag> personTags = new HashSet<>(person.getTags());
                for (String tag: tags) {
                    Tag tagToBeDeleted = new Tag(tag);
                    if (personTags.contains(tagToBeDeleted)) {
                        personTags.remove(tagToBeDeleted);
                    }
                }
                Person editedPerson = new Person(person.getName(), person.getPhone(), person.getEmail(),
                        person.getAddress(), personTags, person.getMeeting(), person.getPicture());
                updatedList.add(editedPerson);
                model.updatePerson(person, editedPerson);
            });
            model.updateFilteredPersonList(new PersonIsUntaggedPredicate(updatedList));
            model.commitAddressBook();
        } else if (this.action == Action.EDIT) {
            message = Messages.MESSAGE_TAG_EDITED_OVERVIEW;
            model.updateFilteredPersonList(predicate);
            List<Person> currentList = new ArrayList<>(model.getFilteredPersonList());
            List<Person> updatedList = new ArrayList<>();
            currentList.forEach(person -> {
                Set<Tag> personTags = new HashSet<>(person.getTags());
                Tag tagToBeDeleted = new Tag(tags.get(0));
                Tag tagToBeAdded = new Tag(tags.get(1));
                if (personTags.contains(tagToBeDeleted)) {
                    personTags.remove(tagToBeDeleted);
                    personTags.add(tagToBeAdded);
                }
                Person editedPerson = new Person(person.getName(), person.getPhone(), person.getEmail(),
                        person.getAddress(), personTags, person.getMeeting());
                updatedList.add(editedPerson);
                model.updatePerson(person, editedPerson);
            });
            model.updateFilteredPersonList(new PersonIsUntaggedPredicate(updatedList));
            model.commitAddressBook();
        } else {
            message = Messages.MESSAGE_TAGGED_PERSONS_LISTED_OVERVIEW;
            model.updateFilteredPersonList(predicate);
        }
        if (this.action == Action.DELETE || this.action == Action.FIND) {
            return new CommandResult(String.format(message, model.getFilteredPersonList().size()));
        } else {
            return new CommandResult(String.format(message, model.getFilteredPersonList().size(), tags.get(0),
                    tags.get(1)));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagCommand // instanceof handles nulls
                && predicate.equals(((TagCommand) other).predicate)); // state check
    }

    /**
     * Tests that a {@code Person} has just been untagged.
     */
    private class PersonIsUntaggedPredicate implements Predicate<Person> {
        private final List<Person> persons;

        private PersonIsUntaggedPredicate(List<Person> persons) {
            this.persons = persons;
        }

        @Override
        public boolean test(Person person) {
            return persons.stream()
                    .anyMatch(p -> p.equals(person));
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof PersonIsUntaggedPredicate // instanceof handles nulls
                    && persons.equals(((PersonIsUntaggedPredicate) other).persons)); // state check
        }
    }
}
