package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class AutoCompleteCommandHelperTest {

    private Set<String> emptySet = new TreeSet<>();
    private String partialValidCommand = "modifypermissi"; //Partial valid command for "modifypermission"
    private Set<String> expectedResultForPartialValidCommand = new TreeSet<>(Arrays.asList("modifypermission"));
    private String partialInvalidCommand = "ppp";

    @Test
    public void autoCompleteWord() {

        //null -> empty set
        Set<String> result = AutoCompleteCommandHelper.autoCompleteWord(null);
        assertEquals(result, emptySet);

        //empty string -> empty set
        result = AutoCompleteCommandHelper.autoCompleteWord("");
        assertEquals(result, emptySet);

        //" " -> set of all commands.
        result = AutoCompleteCommandHelper.autoCompleteWord(" ");
        assertEquals(result, AutoCompleteCommandHelper.getAllCommands());

        //Partial valid command -> return expected result.
        result = AutoCompleteCommandHelper.autoCompleteWord(partialValidCommand);
        assertEquals(result, expectedResultForPartialValidCommand);

        //Partial invalid command -> empty set
        result = AutoCompleteCommandHelper.autoCompleteWord(partialInvalidCommand);
        assertEquals(result, emptySet);
    }
}
