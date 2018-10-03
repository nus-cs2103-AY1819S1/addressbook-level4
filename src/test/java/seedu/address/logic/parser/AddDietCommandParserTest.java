package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;

import org.junit.Test;

import seedu.address.logic.commands.AddDietCommand;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddDietCommandParserTest {
    private AddDietCommandParser parser = new AddDietCommandParser();

    @Test
    public void parse_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NRIC_DESC_AMY + " alg/Egg", new AddDietCommand(expectedPerson));
    }
}
