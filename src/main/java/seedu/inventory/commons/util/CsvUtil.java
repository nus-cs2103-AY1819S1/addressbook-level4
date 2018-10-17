package seedu.inventory.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.inventory.commons.exceptions.UnrecognizableDataException;
import seedu.inventory.storage.CsvAdaptedData;
/**
 * Helps with reading from and writing to CSV files.
 */
public class CsvUtil {
    /**
     * Returns the csv data in the file as an object of the specified type. The format of data is constrained.
     *
     * @param file           Points to a valid csv file containing data that match the {@code dataTypeToConvert}.
     *                       Cannot be null.
     * @param dataTypeToConvert The class corresponding to the csv data.
     *                       Cannot be null.
     * @throws FileNotFoundException Thrown if the file is missing.
     * @throws UnrecognizableDataException Thrown if the file is empty or does not have the correct format.
     */
    @SuppressWarnings("unchecked")
    public static CsvAdaptedData getDataFromFile(Path file, CsvAdaptedData dataTypeToConvert)
            throws FileNotFoundException, UnrecognizableDataException {
        if (!Files.exists(file)) {
            throw new FileNotFoundException("File not found : " + file.toAbsolutePath());
        }
        if (!isDataHeaderRecognizable(file, dataTypeToConvert)) {
            throw new UnrecognizableDataException("File header format can not be recognized");
        }
        List<List<String>> contents = getDataContentFromFile(file, dataTypeToConvert);

        return dataTypeToConvert.createInstance(contents);
    }

    /**
     * Returns true if header in the csv file is recognizable.
     *
     * @param file           Points to a valid csv file containing data that match the {@code dataTypeToConvert}.
     *                       Cannot be null.
     * @param dataTypeToConvert The class corresponding to the csv data.
     *                       Cannot be null.
     */
    public static boolean isDataHeaderRecognizable(Path file, CsvAdaptedData dataTypeToConvert) {
        requireNonNull(file);
        requireNonNull(dataTypeToConvert);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file.toFile()));
            List<String> dataTypes = Arrays.stream(reader.readLine().split(",")).collect(Collectors.toList());

            if (!isDataTypeEqual(dataTypes, dataTypeToConvert)) {
                return false;
            }

            List<String> dataFields = Arrays.stream(reader.readLine().split(",")).collect(Collectors.toList());

            if (!isDataFieldsEqual(dataFields, dataTypeToConvert)) {
                return false;
            }
        } catch (IOException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if header in the csv file is recognizable.
     *
     * @param file           Points to a valid csv file containing data that match the {@code dataTypeToConvert}.
     *                       Cannot be null.
     * @param dataTypeToConvert The class corresponding to the csv data.
     *                       Cannot be null.
     */
    public static List<List<String>> getDataContentFromFile(Path file, CsvAdaptedData dataTypeToConvert)
            throws UnrecognizableDataException {
        requireNonNull(file);
        requireNonNull(dataTypeToConvert);
        BufferedReader reader;
        List<List<String>> contents = new LinkedList<>();
        try {
            reader = new BufferedReader(new FileReader(file.toFile()));
            reader.readLine();
            long fieldsNumber = Arrays.stream(reader.readLine().split(",")).count();
            String contentLine;
            while ((contentLine = reader.readLine()) != null) {
                List<String> content = getContentFromLine(contentLine);
                if (content.size() != fieldsNumber) {
                    throw new UnrecognizableDataException("File content format can not be recognized");
                }
                contents.add(content);
            }
        } catch (IOException e) {
            throw new UnrecognizableDataException("File content format can not be recognized");
        } catch (NullPointerException e) {
            throw new UnrecognizableDataException("File content format can not be recognized");
        }
        return contents;
    }

    /**
     * Returns true if data type in the csv file equals the data type of  {@code dataTypeToConvert}.
     *
     * @param dataTypes         The types of data indicated in the csv file.
     *                          Cannot be null.
     * @param dataTypeToConvert The class corresponding to the csv data.
     *                          Cannot be null.
     */
    public static boolean isDataTypeEqual(List<String> dataTypes, CsvAdaptedData dataTypeToConvert) {
        requireNonNull(dataTypes);
        requireNonNull(dataTypeToConvert);
        return dataTypes.size() == 1 && dataTypes.get(0).equals(dataTypeToConvert.getDataType());
    }

    /**
     * Returns true if fields in the csv file equals the fields of  {@code dataTypeToConvert}.
     *
     * @param dataFields         The fields of data indicated in the csv file.
     *                           Cannot be null.
     * @param dataTypeToConvert  The class corresponding to the csv data.
     *                           Cannot be null.
     */
    public static boolean isDataFieldsEqual(List<String> dataFields, CsvAdaptedData dataTypeToConvert) {
        requireNonNull(dataFields);
        requireNonNull(dataTypeToConvert);
        return dataFields.equals(new LinkedList<>(Arrays.asList(dataTypeToConvert.getDataFields())));
    }

    /**
     * Returns the content from a content line with or without comma.
     *
     * @param contentLine         The fields of data indicated in the csv file.
     *                            Cannot be null.
     */
    public static List<String> getContentFromLine(String contentLine) {
        requireNonNull(contentLine);
        boolean hasQuotesBefore = false;
        boolean hasCommaBefore = true;
        LinkedList<String> content = new LinkedList<>();
        String stringStack = "";
        for (int i = 0; i < contentLine.length(); i++) {
            if (contentLine.charAt(i) == ',') {
                if (!hasQuotesBefore) {
                    if ("".equals(stringStack)) {
                        if (hasCommaBefore) {
                            content.add(stringStack);
                        } else {
                            hasCommaBefore = true;
                        }
                    } else {
                        content.add(stringStack);
                        stringStack = "";
                        hasCommaBefore = true;
                    }
                } else {
                    stringStack += ",";
                }
            } else if (contentLine.charAt(i) == '\"') {
                if (!hasQuotesBefore) {
                    hasQuotesBefore = true;
                } else {
                    content.add(stringStack);
                    stringStack = "";
                    hasQuotesBefore = false;
                }
                hasCommaBefore = false;
            } else {
                stringStack += contentLine.charAt(i);
                hasCommaBefore = false;
            }
        }
        if ("".equals(stringStack)) {
            if (hasCommaBefore) {
                content.add(stringStack);
            }
        } else {
            content.add(stringStack);
        }
        return content;
    }

    /**
     * Saves the data in the file in csv format.
     *
     * @param file Points to a csv file address to write data.
     *             Cannot be null.
     * @param data The data to be written in the file;
     *                       Cannot be null.
     * @throws FileNotFoundException Thrown if the file is missing.
     * @throws IOException           Thrown if there is an error during writing data to the file.
     */
    public static <T> void saveDataToFile(Path file, CsvAdaptedData data) throws FileNotFoundException, IOException {

        requireNonNull(file);
        requireNonNull(data);

        if (!Files.exists(file)) {
            throw new FileNotFoundException("File not found : " + file.toAbsolutePath());
        }

        file.toFile().createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.toFile()));

        String separator = ",";
        String dataType = data.getDataType();
        String[] dataFields = data.getDataFields();
        List<List<String>> contents = data.getContents();
        int fieldsLength = dataFields.length;

        writer.write(dataType);
        for (int i = 0; i < fieldsLength - 1; i++) {
            writer.write(separator);
        }
        writer.write("\n");

        for (int i = 0; i < fieldsLength - 1; i++) {
            writer.write(dataFields[i] + separator);
        }
        writer.write(dataFields[fieldsLength - 1] + "\n");

        for (List<String> content : contents) {
            List<String> csvStandardContent = getCsvStandardContent(content);
            for (int i = 0; i < fieldsLength - 1; i++) {
                writer.write(csvStandardContent.get(i) + separator);
            }
            writer.write(csvStandardContent.get(fieldsLength - 1) + "\n");
        }
        writer.flush();
    }

    public static List<String> getCsvStandardContent(List<String> content) {
        return content.stream()
                .map(field -> field.contains(",") ? "\"" + field + "\"" : field)
                .collect(Collectors.toList());
    }
}
