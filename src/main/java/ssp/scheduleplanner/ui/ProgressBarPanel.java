package ssp.scheduleplanner.ui;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ssp.scheduleplanner.commons.core.LogsCenter;
import ssp.scheduleplanner.commons.events.model.SchedulePlannerChangedEvent;
import ssp.scheduleplanner.logic.Logic;
import ssp.scheduleplanner.logic.commands.ListWeekCommand;
import ssp.scheduleplanner.model.task.DateSamePredicate;
import ssp.scheduleplanner.model.task.DateWeekSamePredicate;
import ssp.scheduleplanner.model.task.Task;

/**
 * A ui for the progress bars.
 */
public class ProgressBarPanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ProgressBarPanel.class);

    private static final String FXML = "ProgressBar.fxml";

    @FXML
    private ProgressBar today;
    @FXML
    private ProgressBar week;
    @FXML
    private Text todaylabel;
    @FXML
    private Text weeklabel;

    public ProgressBarPanel(Logic logic) {
        super(FXML);
        registerAsAnEventHandler(this);
        today.setPrefWidth(Double.MAX_VALUE);
        week.setPrefWidth(Double.MAX_VALUE);
        todaylabel.setFill(Color.WHITE);
        weeklabel.setFill(Color.WHITE);
        todaylabel.setFont(Font.font ("Verdana", 20));
        weeklabel.setFont(Font.font ("Verdana", 20));

        String systemDate =
                new SimpleDateFormat("ddMMyy").format(Calendar.getInstance().getTime());

        FilteredList<Task> taskTodayList = new FilteredList<>(logic.getFilteredTaskList());
        taskTodayList.setPredicate(new DateSamePredicate(systemDate));
        FilteredList<Task> archivedTaskTodayList = new FilteredList<>(logic.getFilteredArchivedTaskList());
        archivedTaskTodayList.setPredicate(new DateSamePredicate(systemDate));

        int uncompletedToday = taskTodayList.size();
        int completedToday = archivedTaskTodayList.size();
        int totalToday = uncompletedToday + completedToday;
        double percentageToday = (double) completedToday / (double) totalToday;
        today.setProgress(percentageToday);

        FilteredList<Task> filteredWeekTasks = new FilteredList<>(logic.getFilteredTaskList());
        FilteredList<Task> filteredWeekArchivedTasks = new FilteredList<>(logic.getFilteredArchivedTaskList());
        List<String> dateList = new ArrayList<String>();
        String dateName = LocalDate.now().getDayOfWeek().name();
        ListWeekCommand.appendDateList(dateList, ListWeekCommand.numDaysTillSunday(dateName));
        filteredWeekTasks.setPredicate(new DateWeekSamePredicate(dateList));
        filteredWeekArchivedTasks.setPredicate(new DateWeekSamePredicate(dateList));

        int uncompleted = filteredWeekTasks.size();
        int completed = filteredWeekArchivedTasks.size();
        int total = uncompleted + completed;
        double percentageWeek = (double) completed / (double) total;
        week.setProgress(percentageWeek);
    }

    @Subscribe
    public void handleSchedulePlannerChangedEvent(SchedulePlannerChangedEvent e) {
        String systemDate =
                new SimpleDateFormat("ddMMyy").format(Calendar.getInstance().getTime());
        FilteredList<Task> filteredTasks = new FilteredList<>(e.data.getTaskList());
        FilteredList<Task> filteredArchivedTasks = new FilteredList<>(e.data.getArchivedTaskList());
        filteredTasks.setPredicate(new DateSamePredicate(systemDate));
        filteredArchivedTasks.setPredicate(new DateSamePredicate(systemDate));

        int uncompletedToday = filteredTasks.size();
        int completedToday = filteredArchivedTasks.size();
        int totalToday = uncompletedToday + completedToday;
        double percentageToday = (double) completedToday / (double) totalToday;
        today.setProgress(percentageToday);

        FilteredList<Task> filteredWeekTasks = new FilteredList<>(e.data.getTaskList());
        FilteredList<Task> filteredWeekArchivedTasks = new FilteredList<>(e.data.getArchivedTaskList());
        List<String> dateList = new ArrayList<String>();
        String dateName = LocalDate.now().getDayOfWeek().name();
        ListWeekCommand.appendDateList(dateList, ListWeekCommand.numDaysTillSunday(dateName));
        filteredWeekTasks.setPredicate(new DateWeekSamePredicate(dateList));
        filteredWeekArchivedTasks.setPredicate(new DateWeekSamePredicate(dateList));

        int uncompleted = filteredWeekTasks.size();
        int completed = filteredWeekArchivedTasks.size();
        int total = uncompleted + completed;
        double percentageWeek = (double) completed / (double) total;
        week.setProgress(percentageWeek);
    }
}
