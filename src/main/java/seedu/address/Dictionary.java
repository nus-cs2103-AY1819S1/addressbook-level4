package seedu.address;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * Dictionary logic Author - Russell Ong
 */
public class Dictionary {
    /**
     * Obtains definition of the word
     */
    public static String obtainDefinition(String inputWord) throws IOException {
        Document doc = Jsoup.connect("https://www.dictionary.com/browse/" + inputWord).get();
        //Get description from document object.
        String description =
                doc.select("meta[name=description]").get(0)
                        .attr("content");
        return description;
    }
    /**
     * Main method where definition is obtained
     */
    public static void mainFunction() throws IOException {
        HashMap<String, String> vocabList = new HashMap<>();
        String testWord = "slim";
        Properties properties = new Properties();

        properties.load(new FileInputStream("src/main/java/vocabList.txt"));

        for (String key : properties.stringPropertyNames()) {
            vocabList.put(key, properties.get(key).toString());
        }

        //trying to find the word now...
        //To find/add/type word: User types in angel...
        if (vocabList.get(testWord) == null) {
            String definition = obtainDefinition(testWord);
            String[] result = definition.split(" ", 2);
            String[] finalResult = result[1].split(" ", 2);
            String rest = toString(finalResult);
            vocabList.put(testWord, rest);
        } else {
            String extractedWord = vocabList.get(testWord);
            System.out.println(extractedWord);
        }

        for (Map.Entry<String, String> entry : vocabList.entrySet()) {
            properties.put(entry.getKey(), entry.getValue());
        }

        properties.store(new FileOutputStream("src/main/java/vocabList.txt"), null);
    }

    /**
     * Converts String Array to String.
     */
    public static String toString(Object[] a) {
        if (a == null) {
            return "null";
        }
        int iMax = a.length - 1;
        if (iMax == -1) {
            return "[]";
        }
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0;; i++) {
            b.append(String.valueOf(a[i]));
            if (i == iMax) {
                return b.append(']').toString();
            }
            b.append(", ");
        }
    }
}
