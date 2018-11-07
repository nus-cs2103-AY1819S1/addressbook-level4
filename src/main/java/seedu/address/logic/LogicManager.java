package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.QueueUpdatedEvent;
import seedu.address.commons.events.ui.ShowDefaultBrowserEvent;
import seedu.address.commons.events.ui.ShowQueueInformationEvent;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddMedicineCommand;
import seedu.address.logic.commands.CheckStockCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DisplayQueueCommand;
import seedu.address.logic.commands.DisplayServedPatientsCommand;
import seedu.address.logic.commands.FindMedicineCommand;
import seedu.address.logic.commands.FinishCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.InsertCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListStockCommand;
import seedu.address.logic.commands.QueueCommand;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.commands.RemoveCommand;
import seedu.address.logic.commands.RestockCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.PatientQueueManager;
import seedu.address.model.ServedPatientList;
import seedu.address.model.ServedPatientListManager;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.Patient;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private final PatientQueue patientQueue;
    private final ServedPatientList servedPatientList;
    private CurrentPatient currentPatient;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
        patientQueue = new PatientQueueManager();
        servedPatientList = new ServedPatientListManager();
        currentPatient = new CurrentPatient();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = null;
        try {
            command = addressBookParser.parseCommand(commandText);
            if (command instanceof QueueCommand) {
                return ((QueueCommand) command).execute(model, patientQueue, currentPatient,
                        servedPatientList, history);
            }
            return command.execute(model, history);
        } finally {
            history.add(commandText);
            if (command instanceof AddCommand
                    || command instanceof AddMedicineCommand
                    || command instanceof CheckStockCommand
                    || command instanceof FindMedicineCommand
                    || command instanceof HelpCommand
                    || command instanceof HistoryCommand
                    || command instanceof ListCommand
                    || command instanceof ListStockCommand
                    || command instanceof RestockCommand) {
                raise(new ShowDefaultBrowserEvent());
            }
            if (command instanceof QueueCommand) {
                raise(new QueueUpdatedEvent(patientQueue, servedPatientList, currentPatient));
            }
            if (command instanceof DisplayQueueCommand
                    || command instanceof RegisterCommand
                    || command instanceof DisplayServedPatientsCommand
                    || command instanceof InsertCommand
                    || command instanceof RemoveCommand
                    || command instanceof FinishCommand) {
                raise(new ShowQueueInformationEvent(patientQueue, servedPatientList, currentPatient));
            }
        }
    }

    @Override
    public ObservableList<Patient> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Medicine> getFilteredMedicineList() {
        return model.getFilteredMedicineList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
