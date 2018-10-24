package seedu.address.commons.util;

import static j2html.TagCreator.body;
import static j2html.TagCreator.div;
import static j2html.TagCreator.h2;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.img;
import static j2html.TagCreator.link;
import static j2html.TagCreator.p;
import static j2html.TagCreator.title;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import j2html.tags.ContainerTag;

/**
 * Generates a Html page used to display ride details.
 */
public class RidePageGenerator {
    private static final String RIDE_PAGE_PATH = "src/main/resources/docs/ride.html";
    private static final String RIDE_PAGE_TITLE = "Ride Information";

    /**
     * Generates a Html report with commandEntryList as entries as fileName.
     */
    public static void generateRidePage() {
        String content = generateRidePageLayout();
        createFile(RIDE_PAGE_PATH, content);
    }

    /**
     * Returns a String representation of the generated Html report with commandEntryList as entries.
     */
    private static String generateRidePageLayout() {
        ContainerTag table = null;
        return html(
            head(
                title(RIDE_PAGE_TITLE),
                link().withRel("stylesheet").withHref("/stylesheets/asciidoctor.css")
            ),
            body(
                generateRidePageHeader(),
                generateRidePageDetails()
            )
        ).withStyle("padding: 10px 30px").renderFormatted();
    }

    private static ContainerTag generateRidePageHeader() {
        return div(
            div(
                p("Name:"),
                p(h2().withId("name"))
            ).withStyle("height: auto; min-width: 200px; width: 50%; display: inline-block; left: 0px; word-wrap: break-word"),
            div(
                img().attr("src=\"ride.png\" alt=\"ride\"").withStyle("height: auto; width: 100%")
            ).withStyle("min-width: 200px;" +
                "height: auto, width: 40%; display: inline-block; right: 0px")
        );
    }

    private static ContainerTag generateRidePageDetails() {
        return div(
            div(
                p("Status:"),
                p("Days since last maintenance:")
            ).withStyle("width: 40%; display: inline-block; left: 0px"),
            div(
                p().withId("status"),
                p().withId("waitTime")
            ).withStyle("width: 50%; display: inline-block; right: 0px")
        ).withStyle("padding: 30px 10px");
    }

    /**
     * Creates the html file and writes the html content into it. Overwrites existing files.
     * Throws IOException if file cannot be written to.
     */
    private static void createFile(String fileName, String content) {
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(fileName, false);
            fileWriter.append(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
