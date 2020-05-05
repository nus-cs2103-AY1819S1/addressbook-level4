package ssp.scheduleplanner.logic.commands;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ssp.scheduleplanner.commons.core.EventsCenter;
import ssp.scheduleplanner.commons.events.ui.ChangeViewEvent;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.logic.commands.exceptions.CommandException;
import ssp.scheduleplanner.logic.parser.CliSyntax;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.task.Date;
import ssp.scheduleplanner.model.task.Interval;
import ssp.scheduleplanner.model.task.Repeat;
import ssp.scheduleplanner.model.task.Task;

/**
 * Adds a repeated task to the Schedule Planner.
 */
public class AddRepeatCommand extends Command {
    public static final String COMMAND_WORD = "repeat";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a repeated task to the Schedule Planner. "
            + "Parameters: "
            + CliSyntax.PREFIX_REPEAT + "REPEAT "
            + CliSyntax.PREFIX_INTERVAL + "INTERVAL "
            + CliSyntax.PREFIX_NAME + "NAME "
            + CliSyntax.PREFIX_DATE + "DATE "
            + CliSyntax.PREFIX_PRIORITY + "PRIORITY "
            + CliSyntax.PREFIX_VENUE + "VENUE "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_REPEAT + "10 "
            + CliSyntax.PREFIX_INTERVAL + "7 "
            + CliSyntax.PREFIX_NAME + "CS2103T Tutorial "
            + CliSyntax.PREFIX_DATE + "111018 "
            + CliSyntax.PREFIX_PRIORITY + "3 "
            + CliSyntax.PREFIX_VENUE + "COM1-0210 "
            + CliSyntax.PREFIX_TAG + "Tutorial";

    public static final String MESSAGE_SUCCESS = "New repeated task added: %1$s";
    private final Task toAdd;
    private final Repeat repeat;
    private final Interval repeatInterval;

    /**
     * Creates an AddRepeatCommand to add the specified {@code Task}
     */
    public AddRepeatCommand(Task task, Repeat times, Interval interval) {
        requireNonNull(task);
        toAdd = task;
        repeat = times;
        repeatInterval = interval;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // Add the first task.
        int interval = Integer.parseInt(repeatInterval.value);
        DateFormat schedulerFormat = new SimpleDateFormat("ddMMyy");
        Calendar baseDate = toAdd.getDate().calendar;
        String newDate = schedulerFormat.format(baseDate.getTime());
        Date date = new Date(newDate);
        Task newTask = new Task(toAdd.getName(), date,
                toAdd.getPriority(), toAdd.getVenue(), toAdd.getTags());
        // If the first task is not a duplicate task, add it.
        if (!model.hasTask(newTask)) {
            model.addTask(newTask);
        }
        // Loop through to add the rest of the tasks.
        for (int i = 1; i < Integer.parseInt(repeat.value); i++) {
            baseDate.add(Calendar.DAY_OF_YEAR, interval);
            newDate = schedulerFormat.format(baseDate.getTime());
            date = new Date(newDate);
            newTask = new Task(toAdd.getName(), date,
                    toAdd.getPriority(), toAdd.getVenue(), toAdd.getTags());
            // Add the task only if there is no duplicate task within the model.
            if (!model.hasTask(newTask)) {
                model.addTask(newTask);
            }
        }
        model.commitSchedulePlanner();
        EventsCenter.getInstance().post(new ChangeViewEvent(ChangeViewEvent.View.NORMAL));

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }


}

