package seedu.parking.logic;

import static seedu.parking.logic.commands.NotifyCommand.MESSAGE_DOWN_CHANGE;
import static seedu.parking.logic.commands.NotifyCommand.MESSAGE_ERROR;
import static seedu.parking.logic.commands.NotifyCommand.MESSAGE_ERROR_CARPARK;
import static seedu.parking.logic.commands.NotifyCommand.MESSAGE_ERROR_NODATA;
import static seedu.parking.logic.commands.NotifyCommand.MESSAGE_NO_CHANGE;
import static seedu.parking.logic.commands.NotifyCommand.MESSAGE_UP_CHANGE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import seedu.parking.commons.core.EventsCenter;
import seedu.parking.commons.core.Messages;
import seedu.parking.commons.core.index.Index;
import seedu.parking.commons.events.model.DataFetchExceptionEvent;
import seedu.parking.commons.events.ui.JumpToListRequestEvent;
import seedu.parking.commons.events.ui.NewResultAvailableEvent;
import seedu.parking.commons.events.ui.NotifyCarparkRequestEvent;
import seedu.parking.commons.util.GsonUtil;
import seedu.parking.logic.commands.exceptions.CommandException;
import seedu.parking.model.Model;
import seedu.parking.model.carpark.Carpark;
import seedu.parking.model.carpark.CarparkNumber;
import seedu.parking.model.carpark.LotsAvailable;
import seedu.parking.model.carpark.TotalLots;
import seedu.parking.ui.CarparkListPanel;

/**
 * A class to handle timer task but checking if its running or not.
 */
public class NotifyTimeTask extends TimerTask {
    private Model model;
    private int targetTime;

    public NotifyTimeTask(Model model, int targetTime) {
        this.model = model;
        this.targetTime = targetTime;
    }

    @Override
    public void run() {
        //System.out.println("Task performed on " + new Date());

        try {
            int validity = CarparkListPanel.getSelectedIndex();
            if (validity < 0) {
                throw new CommandException(MESSAGE_ERROR);
            }

            Index notifyIndex = Index.fromZeroBased(validity);
            List<Carpark> filteredCarparkList = model.getFilteredCarparkList();

            if (notifyIndex.getZeroBased() >= filteredCarparkList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CARPARK_DISPLAYED_INDEX);
            }

            CarparkNumber selectedNumber = CarparkListPanel.getSelectedCarpark().getCarparkNumber();
            int oldValue = Integer.parseInt(CarparkListPanel.getSelectedCarpark().getLotsAvailable().value);
            List<String> updateData = new ArrayList<>(GsonUtil.getSelectedCarparkInfo(
                    selectedNumber.toString()));

            model.getCarparkFinder().getCarparkList().parallelStream()
                    .filter(carpark -> carpark.getCarparkNumber().equals(selectedNumber))
                    .findFirst().ifPresent(carpark -> carpark.setLots(new LotsAvailable(updateData.get(1)),
                            new TotalLots(updateData.get(2))));
            EventsCenter.getInstance().post(new NotifyCarparkRequestEvent());
            model.commitCarparkFinder();
            //System.out.println("Lots Available: " + updateData.get(1) + " Total Lots: "
            //        + updateData.get(2));

            if (CarparkListPanel.getTimeInterval() > 0) {
                EventsCenter.getInstance().post(new JumpToListRequestEvent(notifyIndex));

                int newValue = Integer.parseInt(updateData.get(1));
                int diffValue = newValue - oldValue;

                if (diffValue == 0) {
                    EventsCenter.getInstance().post(new NewResultAvailableEvent(
                            String.format(MESSAGE_NO_CHANGE, selectedNumber.toString(), newValue, targetTime)));
                } else if (diffValue > 0) {
                    EventsCenter.getInstance().post(new NewResultAvailableEvent(
                            String.format(MESSAGE_UP_CHANGE, selectedNumber.toString(), newValue, Math.abs(diffValue),
                                    targetTime)));
                } else {
                    EventsCenter.getInstance().post(new NewResultAvailableEvent(
                            String.format(MESSAGE_DOWN_CHANGE, selectedNumber.toString(), newValue, Math.abs(diffValue),
                                    targetTime)));
                }
            }
        } catch (CommandException e) {
            cancel();
            if (CarparkListPanel.getTimeInterval() > 0) {
                EventsCenter.getInstance().post(new DataFetchExceptionEvent(e));
            }
        } catch (IOException e) {
            cancel();
            if (CarparkListPanel.getTimeInterval() > 0) {
                EventsCenter.getInstance().post(new DataFetchExceptionEvent(
                        new CommandException(MESSAGE_ERROR_CARPARK)));
            }
        } catch (Exception e) {
            cancel();
            if (CarparkListPanel.getTimeInterval() > 0) {
                EventsCenter.getInstance().post(new DataFetchExceptionEvent(
                        new CommandException(MESSAGE_ERROR_NODATA)));
            }
        }
    }
}
