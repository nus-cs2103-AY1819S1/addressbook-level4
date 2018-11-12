package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.SelfEditCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.project.Project;

/**
 * A class used in tests to build a SelfEditCommand, similar to {@link PersonBuilder}.
 */
public class SelfEditCommandParserBuilder {
    private EditPersonDescriptor editPersonDescriptor;

    /**
     * Creates a new self edit command parser builder with no changes.
     */
    public SelfEditCommandParserBuilder() {
        editPersonDescriptor = new EditPersonDescriptor();
    }

    /**
     * Modifies it to change the phone number of the currently logged in user
     * @param phone The new phone number
     * @return itself, for chaining
     * @throws ParseException if an error occurs with parsing the phone number.
     */
    public SelfEditCommandParserBuilder withPhone(String phone) throws ParseException {
        editPersonDescriptor.setPhone(ParserUtil.parsePhone(phone));
        return this;
    }

    /**
     * Modifies it to change the email of the currently logged in user
     * @param email The new email
     * @return itself, for chaining
     * @throws ParseException if an error occurs with parsing the email.
     */
    public SelfEditCommandParserBuilder withEmail(String email) throws ParseException {
        editPersonDescriptor.setEmail(ParserUtil.parseEmail(email));
        return this;
    }

    /**
     * Modifies it to change the address of the currently logged in user
     * @param address The new address
     * @return itself, for chaining
     * @throws ParseException if an error occurs with parsing the address.
     */
    public SelfEditCommandParserBuilder withAddress(String address) throws ParseException {
        editPersonDescriptor.setAddress(ParserUtil.parseAddress(address));
        return this;
    }

    /**
     * Modifies it to change the projects of the currently logged in user
     * @param projects The new project(s)
     * @return itself, for chaining
     * @throws ParseException if an error occurs with parsing the projects.
     */
    public SelfEditCommandParserBuilder withProjects(String... projects) throws ParseException {
        Set<Project> projectSet = Stream.of(projects).map(Project::new).collect(Collectors.toSet());
        return withProjects(projectSet);
    }

    /**
     * Modifies it to change the projects of the currently logged in user
     * @param projects The new project(s)
     * @return itself, for chaining
     * @throws ParseException if an error occurs with parsing the projects.
     */
    public SelfEditCommandParserBuilder withProjects(Set<Project> projects) throws ParseException {
        editPersonDescriptor.setProjects(projects);
        return this;
    }

    /**
     * Gets the SelfEditCommand that will perform the necessary changes
     * @return the self edit command
     * @throws ParseException if there is actually no changes that will be done.
     */
    public SelfEditCommand getCommand() throws ParseException {
        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                SelfEditCommand.MESSAGE_USAGE));
        }

        return new SelfEditCommand(editPersonDescriptor);
    }
}
