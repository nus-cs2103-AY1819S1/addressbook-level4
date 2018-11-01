package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.SelfEditCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.project.Project;

public class SelfEditCommandParserBuilder {
    private EditPersonDescriptor editPersonDescriptor;

    public SelfEditCommandParserBuilder() {
        editPersonDescriptor = new EditPersonDescriptor();
    }

    public SelfEditCommandParserBuilder withPhone(String phone) throws ParseException {
        editPersonDescriptor.setPhone(ParserUtil.parsePhone(phone));
        return this;
    }

    public SelfEditCommandParserBuilder withEmail(String email) throws ParseException {
        editPersonDescriptor.setEmail(ParserUtil.parseEmail(email));
        return this;
    }

    public SelfEditCommandParserBuilder withAddress(String address) throws ParseException {
        editPersonDescriptor.setAddress(ParserUtil.parseAddress(address));
        return this;
    }

    public SelfEditCommandParserBuilder withProjects(String... projects) throws ParseException {
        Set<Project> projectSet = Stream.of(projects).map(Project::new).collect(Collectors.toSet());
        return withProjects(projectSet);
    }

    public SelfEditCommandParserBuilder withProjects(Set<Project> projects) throws ParseException {
        editPersonDescriptor.setProjects(projects);
        return this;
    }

    public SelfEditCommand getCommand() throws ParseException {
        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                SelfEditCommand.MESSAGE_USAGE));
        }

        return new SelfEditCommand(editPersonDescriptor);
    }
}
