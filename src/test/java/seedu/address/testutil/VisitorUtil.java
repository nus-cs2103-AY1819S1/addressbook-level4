package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISITOR;

import seedu.address.logic.commands.VisitorInCommand;
import seedu.address.model.person.Name;
import seedu.address.model.visitor.Visitor;

/**
 * utility class for {@code visitor}.
 */
public class VisitorUtil {
    public static String getVisitorInCommand(Name name, Visitor visitor) {
        StringBuilder sb = new StringBuilder();
        sb.append(VisitorInCommand.COMMAND_WORD).append(" ")
                .append(PREFIX_NAME).append(name.toString()).append(" ")
                .append(PREFIX_VISITOR).append(visitor.getVisitorName());

        return sb.toString();
    }
}
