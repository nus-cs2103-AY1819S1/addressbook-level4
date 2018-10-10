package seedu.address.logic.commands;

/*
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.JsonConvertArgsStorage;

import java.util.Iterator;
import java.util.List;

public class CreateConvertCommand extends Command {
    private List<String> cmds;
    private String name;

    CreateConvertCommand(String name, List<String> cmds) {
        if(cmds.isEmpty()) {
            throw new IllegalArgumentException
            ("nothing inside the command arguments");
        } else {
            this.cmds = cmds;
            this.name = name;
        }
    }

    private void checkSigleValidation(String arg)
    throws IllegalArgumentException {
        //just a template, not only this
        if(arg == "") {
            throw new IllegalArgumentException();
        }
    };



    private void checkValidation() {
        Iterator<String> iter = cmds.iterator();
        while(iter.hasNext()) {
            try{
                checkSigleValidation(iter.next());
            } catch(IllegalArgumentException e) {
                System.err.println(e);
            }
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history)
    throws CommandException {
        JsonConvertArgsStorage.storeArgument(name, cmds);
        return null;
    }
}
*/
