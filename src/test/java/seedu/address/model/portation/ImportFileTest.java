package seedu.address.model.portation;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.ImportFileUtil.INVALID_EMPTY_FILE;
import static seedu.address.testutil.ImportFileUtil.INVALID_FILE_TYPE;
import static seedu.address.testutil.ImportFileUtil.INVALID_NOT_A_FILE;

import org.junit.Test;

import seedu.address.logic.parser.fileparser.exceptions.FileParseException;
import seedu.address.model.card.UniqueCardList;
import seedu.address.model.portation.exceptions.InvalidImportFileException;
import seedu.address.testutil.Assert;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.ImportFileUtil;

public class ImportFileTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ImportFile(null));
    }

    @Test
    public void isFileValid_invalidFileType_throwsInvalidImportFileException() {
        ImportFile invalid = new ImportFile(INVALID_FILE_TYPE.toString());
        Assert.assertThrows(InvalidImportFileException.class, () -> invalid.isFileValid());
    }

    @Test
    public void isFileValid_invalidEmptyFile_throwsInvalidImportFileException() {
        ImportFile invalid = new ImportFile(INVALID_EMPTY_FILE.toString());
        Assert.assertThrows(InvalidImportFileException.class, () -> invalid.isFileValid());
    }

    @Test
    public void isFileValid_invalidNotFile_throwsInvalidImportFileException() {
        ImportFile invalid = new ImportFile(INVALID_NOT_A_FILE.toString());
        Assert.assertThrows(InvalidImportFileException.class, () -> invalid.isFileValid());
    }

    // no cards throw, duplicate card throw, invalid format, valid, valid no topic
    @Test
    public void parseFileToCard_noCardsParsed_throwsFileParseException() {
        ImportFile noCardsFile = ImportFileUtil.getValidImportFileNoCards();
        Assert.assertThrows(FileParseException.class, () -> noCardsFile.parseFileToCards());
    }

    @Test
    public void parseFileToCard_duplicateCardParsed_throwsFileParseException() {
        ImportFile duplicateCardsFile = ImportFileUtil.getValidImportFileDuplicateCards();
        Assert.assertThrows(FileParseException.class, () -> duplicateCardsFile.parseFileToCards());
    }

    @Test
    public void parseFileToCard_invalidFileFormat_throwsFileParseException() {
        ImportFile invalidFileFormat = ImportFileUtil.getInvalidImportFileInvalidFormat();
        Assert.assertThrows(FileParseException.class, () -> invalidFileFormat.parseFileToCards());
    }

    @Test
    public void parseFileToCard_validFileWithTopic_returnsUniqueCardList() {
        ImportFile validImportFile = ImportFileUtil.getValidWithTopicImportFile();
        UniqueCardList expectedList = new UniqueCardList();
        expectedList.add(new CardBuilder().withQuestion("question").withAnswer("answer").withTopics("topic").build());
        UniqueCardList actualList = validImportFile.parseFileToCards();

        assertEquals(expectedList, actualList);
    }
}
