package seedu.parking.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.parking.commons.core.EventsCenter;
import seedu.parking.commons.events.ui.NoSelectionRequestEvent;
import seedu.parking.commons.util.GsonUtil;
import seedu.parking.logic.CommandHistory;
import seedu.parking.logic.commands.exceptions.CommandException;
import seedu.parking.model.Model;
import seedu.parking.model.ModelManager;
import seedu.parking.model.UserPrefs;
import seedu.parking.model.carpark.Address;
import seedu.parking.model.carpark.Carpark;
import seedu.parking.model.carpark.CarparkNumber;
import seedu.parking.model.carpark.CarparkType;
import seedu.parking.model.carpark.Coordinate;
import seedu.parking.model.carpark.FreeParking;
import seedu.parking.model.carpark.LotsAvailable;
import seedu.parking.model.carpark.NightParking;
import seedu.parking.model.carpark.PostalCode;
import seedu.parking.model.carpark.ShortTerm;
import seedu.parking.model.carpark.TotalLots;
import seedu.parking.model.carpark.TypeOfParking;
import seedu.parking.ui.CarparkListPanel;

public class NotifyCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_selectedCarpark_success() throws Exception {
        Model model = new ModelManager();
        CommandHistory commandHistory = new CommandHistory();
        QueryCommand command = new QueryCommand();
        command.execute(model, commandHistory);
        command.getFuture().get();

        Model expectedModel = new ModelManager(model.getCarparkFinder(), new UserPrefs());

        CarparkNumber selectedNumber = new CarparkNumber("TJ39");
        List<String> updateData = new ArrayList<>(GsonUtil.getSelectedCarparkInfo(selectedNumber.toString()));

        model.getCarparkFinder().getCarparkList().stream()
                .filter(carpark -> carpark.getCarparkNumber().equals(selectedNumber))
                .findFirst().ifPresent(carpark -> carpark.setLots(new LotsAvailable(updateData.get(1)),
                new TotalLots(updateData.get(2))));
        model.commitCarparkFinder();

        Carpark firstCarpark = model.getCarparkFinder().getCarparkList().stream()
                .filter(carpark -> carpark.getCarparkNumber().equals(selectedNumber))
                .findFirst()
                .get();

        Carpark newCarpark = new Carpark(new Address(firstCarpark.getAddress().toString()),
                new CarparkNumber(firstCarpark.getCarparkNumber().toString()),
                new CarparkType(firstCarpark.getCarparkType().toString()),
                new Coordinate(firstCarpark.getCoordinate().toString()),
                new FreeParking(firstCarpark.getFreeParking().toString()),
                new LotsAvailable(firstCarpark.getLotsAvailable().toString()),
                new NightParking(firstCarpark.getNightParking().toString()),
                new ShortTerm(firstCarpark.getShortTerm().toString()),
                new TotalLots(firstCarpark.getTotalLots().toString()),
                new TypeOfParking(firstCarpark.getTypeOfParking().toString()),
                new PostalCode(firstCarpark.getPostalCode().toString()),
                null);

        CarparkListPanel.setSelectedCarpark(newCarpark);

        NotifyCommand noteCommand = new NotifyCommand(10);
        noteCommand.execute(expectedModel, commandHistory);
        Thread.sleep(1000);
        EventsCenter.getInstance().post(new NoSelectionRequestEvent());

        Carpark secondCarpark = expectedModel.getCarparkFinder().getCarparkList().stream()
                .filter(carpark -> carpark.getCarparkNumber().equals(selectedNumber))
                .findFirst()
                .get();

        assertEquals(firstCarpark, secondCarpark);
    }

    @Test
    public void execute_turnOffNotify_success() throws Exception {
        Model model = new ModelManager();
        CommandHistory commandHistory = new CommandHistory();
        CarparkListPanel.setTimeInterval(10);
        NotifyCommand noteCommand = new NotifyCommand(0);
        noteCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_turnOffNotifyButAlreadyDisabled_exceptionThrown() throws Exception {
        Model model = new ModelManager();
        CommandHistory commandHistory = new CommandHistory();
        CarparkListPanel.setTimeInterval(0);
        NotifyCommand noteCommand = new NotifyCommand(0);
        thrown.expect(CommandException.class);
        thrown.expectMessage(NotifyCommand.MESSAGE_ERROR_OFF);
        noteCommand.execute(model, commandHistory);
    }
}
