package seedu.address.storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.notification.Tip;

/**
 * A class to access the list of tips stored in the hard disk as a json file
 */
//@@Snookerballs
public class JsonTipsStorage implements TipsStorage {

    private static InputStream fileStream = JsonTipsStorage.class.getResourceAsStream("/json/tips.json");
    @Override
    public Optional<List<Tip>> readTips() throws IOException {
        return readTips(fileStream);
    }

    /**
     * Similar to
     * @param fileStream location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<List<Tip>> readTips(InputStream fileStream) throws IOException {
        return JsonUtil.fromJsonToArray(fileStream, Tip.class);
    }
}
