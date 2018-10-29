package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Entity;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

//@@author Happytreat

/**
 * Contains helper methods for commands.
 */
public class CommandUtil {

    /**
     * Retrieves the group with index in displayed Group list
     */
    public static Group retrieveGroupFromName(Model model, Name groupName) throws CommandException {
        // Make sure that group exists
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        List<Group> lastShownList = model.getFilteredGroupList();
        Group groupToBeEdited = new Group(groupName, ""); //do not know description and groupMembers

        if (!lastShownList.contains(groupToBeEdited)) {
            throw new CommandException(Messages.MESSAGE_NO_MATCH_TO_EXISTING_GROUP);
        }

        return lastShownList.get(lastShownList.indexOf(groupToBeEdited)); //retrieves original group
    }

    /**
     * Retrieves the person with index in displayed Group list
     */
    public static Person retrievePersonFromIndex(Model model, Index targetIndex)
        throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return lastShownList.get(targetIndex.getZeroBased());
    }

    /**
     * Delete {@code groupToDelete} from {@code personToUpdate} group list and updates model of new
     * {@code personToUpdate}
     */
    public static void updatePersonDeleteGroupFromGroupList(Model model, Group groupToDelete,
        Person personToUpdate) {
        List<Group> editedGroupList = new ArrayList<>(personToUpdate.getGroups());
        editedGroupList.remove(groupToDelete);
        Person newPerson = personToUpdate;
        newPerson.setGroups(editedGroupList);
        model.update(personToUpdate, newPerson);
    }

    /**
     * Adds {@code target} to model if there it has not been added before.
     */
    public static void addTargetToModelIfNoDuplicates(Model model, Entity target)
        throws CommandException {
        if (model.has(target)) {
            if (target instanceof Person) {
                throw new CommandException(AddCommand.MESSAGE_DUPLICATE_PERSON);
            }
            if (target instanceof Group) {
                throw new CommandException(AddGroupCommand.MESSAGE_DUPLICATE_GROUP);
            }
        }
        model.add(target);
        model.commitAddressBook();
    }
}

