package seedu.address.commons.core;

import static j2html.TagCreator.attrs;
import static j2html.TagCreator.body;
import static j2html.TagCreator.h1;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.link;
import static j2html.TagCreator.table;
import static j2html.TagCreator.tbody;
import static j2html.TagCreator.td;
import static j2html.TagCreator.th;
import static j2html.TagCreator.thead;
import static j2html.TagCreator.title;
import static j2html.TagCreator.tr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

import j2html.TagCreator;
import j2html.tags.ContainerTag;
import seedu.address.model.logging.CommandEntry;

/**
 * Class for generating command history report formatted as a Html page.
 */
public class HtmlReportGenerator {
    private static final String COMMAND_ENTRY_REPORT_TITLE = "Command History Report";
    private static final String DEFAULT_REPORT_NAME = "report.html";
    private static final String MESSAGE_TIME_GENERATED = "report generated at: %1$s";

    private static final DateTimeFormatter formatter =
        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withLocale(Locale.UK)
            .withZone(ZoneId.systemDefault());

    /**
     * Generates a Html report with commandEntryList as entries.
     */
    public static void generateCommandEntryReport(List<CommandEntry> commandEntryList) throws IOException {
        generateCommandEntryReport(commandEntryList, DEFAULT_REPORT_NAME);
    }

    /**
     * Generates a Html report with commandEntryList as entries as fileName.
     */
    public static void generateCommandEntryReport(List<CommandEntry> commandEntryList, String fileName)
        throws IOException {
        String content = generateCommandEntryReportLayout(commandEntryList);
        createFile(fileName, content);
    }

    /**
     * Returns a String representation of the generated Html report with commandEntryList as entries.
     */
    private static String generateCommandEntryReportLayout(List<CommandEntry> commandEntryList) {
        ContainerTag table = generateCommandEntryReportTable(commandEntryList);
        return html(
            head(
                title(COMMAND_ENTRY_REPORT_TITLE),
                link().withRel("stylesheet").withHref("docs/stylesheets/asciidoctor.css")
            ),
            body(
                TagCreator.main(attrs("#main.header"),
                h1(COMMAND_ENTRY_REPORT_TITLE)),
                h3(String.format(MESSAGE_TIME_GENERATED, formatter.format(Instant.now()))),
                table
            )
        ).withStyle("padding: 0px 30px").renderFormatted();
    }

    /**
     * Returns a ContainerTag representation of the generated table with commandEntryList as entries.
     */
    private static ContainerTag generateCommandEntryReportTable(List<CommandEntry> commandEntryList) {
        ContainerTag tableBody = generateCommandEntryReportTableBody(commandEntryList);
        return table(
            thead(tr(
                th("Time"),
                th("CommandWord"),
                th("Arguments")
            )),
            tableBody
        ).withStyle("width: 100%");
    }

    /**
     * Returns a ContainerTag representation of the generated table body with commandEntryList as entries.
     */
    private static ContainerTag generateCommandEntryReportTableBody(List<CommandEntry> commandEntryList) {
        ContainerTag result = tbody();
        for (CommandEntry commandEntry : commandEntryList) {
            result = result.with(tr(
                td(commandEntry.getTimeOfEntryString()),
                td(commandEntry.getExecutedCommand().getCommandWord()),
                td(commandEntry.getExecutedCommand().getCommandArgs())
            ));
        }
        return result;
    }

    /**
     * Creates the html file and writes the html content into it. Overwrites existing files.
     * Throws IOException if file cannot be written to.
     */
    private static void createFile(String fileName, String content) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(fileName, false);
        fileWriter.append(content);
        fileWriter.close();
    }

}
