package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.person.Person;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.model.module.Module;
import seedu.address.testutil.OccasionBuilder;
import seedu.address.testutil.PersonBuilder;

public class InsertPersonCommandParserTest {
    private InsertPersonCommandParser parser = new InsertPersonCommandParser();

    @Test
    public void parse_allFields_success() {
        Person validPerson = new PersonBuilder().build();
        Module validModule = new ModuleBuilder().build();
        Occasion validOccasion = new OccasionBuilder().build();
        
    }
}
