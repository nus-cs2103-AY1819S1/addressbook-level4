package seedu.address.storage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;

//@@author GilgameshTC
public class IcsCalendarStorage implements CalendarStorage{
    private Path dirPath;

    public IcsCalendarStorage(Path dirPath) {
        this.dirPath = dirPath;
    }

    @Override
    public Path getCalendarPath() {
        return dirPath;
    }

    @Override
    public void createCalendar(Calendar calendar, String calendarName) throws IOException {
        String fileName = calendarName + ".ics";
        dirPath.resolve(fileName);

        FileOutputStream fout = new FileOutputStream(dirPath.toFile());
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(calendar, fout);
    }
}
