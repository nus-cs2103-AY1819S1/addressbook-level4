package ssp.scheduleplanner.ui;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
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
        today.setPrefHeight(30);
        week.setPrefWidth(Double.MAX_VALUE);
        week.setPrefHeight(30);
        todaylabel.setFill(Color.WHITE);
        weeklabel.setFill(Color.WHITE);
        todaylabel.setFont(Font.font ("Verdana", 20));
        weeklabel.setFont(Font.font ("Verdana", 20));

        updateProgressBars(logic.getFilteredTaskList(), logic.getFilteredArchivedTaskList());
    }


    /**
     * Updates both today and this week's progress bars.
     * @param taskList
     * @param archivedTaskList
     */
    private void updateProgressBars(ObservableList<Task> taskList, ObservableList<Task> archivedTaskList) {
        String systemDate =
                new SimpleDateFormat("ddMMyy").format(Calendar.getInstance().getTime());
        FilteredList<Task> taskTodayList = new FilteredList<>(taskList);
        taskTodayList.setPredicate(new DateSamePredicate(systemDate));
        FilteredList<Task> archivedTaskTodayList = new FilteredList<>(archivedTaskList);
        archivedTaskTodayList.setPredicate(new DateSamePredicate(systemDate));

        int uncompletedToday = taskTodayList.size();
        int completedToday = archivedTaskTodayList.size();
        int totalToday = uncompletedToday + completedToday;
        double percentageToday = (double) completedToday / (double) totalToday;
        today.setProgress(percentageToday);

        FilteredList<Task> filteredWeekTasks = new FilteredList<>(taskList);
        FilteredList<Task> filteredWeekArchivedTasks = new FilteredList<>(archivedTaskList);
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
        updateProgressBars(e.data.getTaskList(), e.data.getArchivedTaskList());
    }
}
