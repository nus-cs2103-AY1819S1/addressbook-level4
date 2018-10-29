package seedu.address.model.portation;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayImportHelpEvent;
import seedu.address.logic.parser.fileparser.FileParserUtil;
import seedu.address.logic.parser.fileparser.exceptions.FileParseException;
import seedu.address.model.card.Card;
import seedu.address.model.card.UniqueCardList;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.model.topic.Topic;

/**
 * Represents a file to be imported.
 */
public class ImportFile {
    public static final String MESSAGE_INVALID_FILE = "Invalid file.";
    public static final String MESSAGE_INVALID_FILE_FORMAT = "Invalid file format.";
    public static final String MESSAGE_INVALID_FILE_UNABLE_TO_READ = "Unable to read file.";
    public static final String MESSAGE_DUPLICATE_CARDS_FOUND = "Duplicate questions in file.";

    private final File importFile;

    public ImportFile(String filePath) {
        requireNonNull(filePath);
        importFile = new File(filePath);
    }

    /**
     * Ensures the file to be imported is valid, readable and non empty.
     */
    public boolean isFileValid() {
        if (!isValidFile()) {
            return false;
        } else if (!isValidFileType()) {
            return false;
        } else if (!isValidFileFormat()) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if the import file is a file and is not empty.
     */
    private boolean isValidFile() {
        if (!importFile.isFile()) {
            return false;
        } else if (importFile.length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * Checks if file is a readable .txt format.
     * @return true if file is a readable text file.
     */
    private boolean isValidFileType() {
        try {
            String fileType = Files.probeContentType(importFile.toPath());
            if (!fileType.equals("text/plain")) {
                return false;
            }
        } catch (IOException ioe) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if the file is in the correct import file format.
     */
    private boolean isValidFileFormat() {
        try (BufferedReader br = new BufferedReader(new FileReader(importFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (FileParserUtil.isEmpty(line) || FileParserUtil.isTopicValidFormat(line)
                        || FileParserUtil.isQuestionAnswerValidFormat(line)) {
                    continue;
                }

                raiseExtraInformationDisplayEvent();
                return false;
            }
        } catch (IOException ioe) {
            return false;
        }
        return true;
    }

    // TODO: write the format here
    /**
     * Parses a file in a specific format into a set of cards.
     * @return The set of cards to be imported.
     * @throws FileParseException If the format of the file is different from expected.
     */
    public UniqueCardList parseFileToCards() throws FileParseException {
        UniqueCardList cards = new UniqueCardList();
        Set<Topic> topicSet = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(importFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (FileParserUtil.isEmpty(line)) { // allows new line between cards and topics
                    continue;
                }

                if (FileParserUtil.isTopic(line)) {
                    topicSet = FileParserUtil.parseLineToTopicSet(line);
                } else {
                    String[] cardString = FileParserUtil.parseLineToQuestionAnswerPair(line);
                    Card cardToAdd = FileParserUtil.stringToCard(cardString, topicSet);
                    cards.add(cardToAdd);
                }
            }

        } catch (IOException ioe) {
            throw new FileParseException(MESSAGE_INVALID_FILE_UNABLE_TO_READ, ioe);
        } catch (FileParseException fpe) {
            throw new FileParseException(MESSAGE_INVALID_FILE_FORMAT, fpe);
        } catch (DuplicateCardException dce) {
            throw new FileParseException(MESSAGE_DUPLICATE_CARDS_FOUND, dce);
        }

        return cards;
    }

    /**
     * Raises an new event to display the import help display UI.
     */
    private void raiseExtraInformationDisplayEvent() {
        EventsCenter.getInstance()
                    .post(new DisplayImportHelpEvent());
    }

    /**
     * Returns name of import file.
     */
    public String getFileName() {
        return importFile.getName();
    }

    @Override
    public String toString() {
        return importFile.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportFile
                && importFile.equals(((ImportFile) other).importFile));
    }
}
