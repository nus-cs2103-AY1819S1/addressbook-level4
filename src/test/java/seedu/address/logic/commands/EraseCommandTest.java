package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EraseCommand.MESSAGE_ERASE_SUCCESS;
import static seedu.address.logic.commands.EraseCommand.MESSAGE_NOTHING_ERASED;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ContactContainsTagPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

//@@author kengwoon
class EraseCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    void execute_eraseTag_success() {
        List<String> target = new ArrayList<>();
        List<Person> original = new ArrayList<>();
        Set<Tag> editedTags = new HashSet<>();
        List<Person> erased = new ArrayList<>();

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs());
        Person originalPerson = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        original.add(originalPerson);
        Object[] tags = originalPerson.getTags().toArray();
        target.add(tags[0].toString());
        for (int i = 1; i < tags.length; i++) {
            editedTags.add((Tag) tags[i]);
        }
        Person newPerson = new Person(originalPerson.getName(), originalPerson.getPhone(), originalPerson.getEmail(),
                originalPerson.getRoom(), originalPerson.getSchool(), editedTags);
        erased.add(newPerson);
        expectedModel.removeTagsFromPersons(erased, original);
        expectedModel.commitAddressBook();

        ContactContainsTagPredicate predicate = new ContactContainsTagPredicate(target);
        EraseCommand eraseCommand = new EraseCommand(target, predicate);

        assertCommandSuccess(eraseCommand, model, commandHistory,
                String.format(MESSAGE_NOTHING_ERASED, '[' + target.get(0) + ']'), expectedModel);
    }

}
