package seedu.souschef.logic.parser.commandparser;

import seedu.souschef.logic.commands.ShowHealthPlanDetailsCommand;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;

/**
 *  parser class for the details command
 */
public class ShowHealthPlanDetailsCommandParser {


    public ShowHealthPlanDetailsCommandParser() {

    }

    /**
     *  parser function for healthplan details
     * @param healthPlanModel
     * @param index
     * @return
     * @throws ParseException
     */
    public ShowHealthPlanDetailsCommand parseHealthPlan(Model healthPlanModel, String index) throws ParseException {

        index = index.trim();
        //throw error when length of index is 0
        if (index.length() == 0) {
            throw new ParseException(ShowHealthPlanDetailsCommand.MESSAGE_USAGE);
        }

        //check if numeric otherwise throw error
        if (!index.matches("^[0-9]+$")) {
            throw new ParseException(ShowHealthPlanDetailsCommand.MESSAGE_USAGE);
        }

        int zeroBasedIndex = Integer.parseInt(index) - 1;

        //check the range of entered index against list for validity
        if (zeroBasedIndex < 0
                || zeroBasedIndex >= healthPlanModel.getAppContent().getObservableHealthPlanList().size()) {
            throw new ParseException(ShowHealthPlanDetailsCommand.MESSAGE_USAGE);
        }


        return new ShowHealthPlanDetailsCommand(index);

    }

}
