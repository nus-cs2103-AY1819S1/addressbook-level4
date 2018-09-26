package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.ContactContainsRoomPredicate;
import seedu.address.model.person.ContactContainsTagPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Room;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * Clears the address book.
 */
public class EraseCommand extends Command {

    public static final String COMMAND_WORD = "erase";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Erases specified tag(s) from all persons in Hallper\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " basketball";
    public static final String MESSAGE_CLEAR_SUCCESS = "Erased %1$s from persons in Hallper";

    private final ContactContainsTagPredicate predicate;

    private final List<String> target;
    private ArrayList<Person> toRemove;
    private ArrayList<Person> modifiedPersons;
    private List<Person> fullList;
    private Set<Tag> tags;
    private Person temp;

    public EraseCommand(List<String> target, ContactContainsTagPredicate predicate) {
        this.target = target;
        this.predicate = predicate;
        this.toRemove = new ArrayList<>();
        this.modifiedPersons = new ArrayList<>();
        this.fullList = new ArrayList<>();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        toRemove.clear();
        modifiedPersons.clear();
        fullList = model.getAddressBook().getPersonList();

        for (Person p : fullList) {
            if (new ContactContainsTagPredicate(target).test(p)) {
                toRemove.add(p);
            }
        }

        for (Person p : toRemove) {
            tags.clear();
            for (Tag t : p.getTags()) {
                for (String tag : target) {
                    if (!t.toStringOnly().equals(tag)) {
                        tags.add(new Tag(tag));
                    }
                }
            }
            temp = new Person(p.getName(), p.getPhone(), p.getEmail(), p.getRoom(), p.getSchool(), tags);
            modifiedPersons.add(temp);
        }
        model.removeTagsFromPersons(modifiedPersons, toRemove);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_CLEAR_SUCCESS, target));
    }
}
