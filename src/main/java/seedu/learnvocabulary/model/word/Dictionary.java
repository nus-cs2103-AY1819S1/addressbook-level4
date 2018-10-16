//package seedu.learnvocabulary.model.word;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//
//
///**
// * Dictionary logic
// */
//public class Dictionary {
//    private Name name;
//    private Meaning meaning;
//
//    public Dictionary(String input) throws IOException {
//        name.setFullName(input);
//        if ((input = this.obtainDefinition(input)) != null) {
//            meaning.setFullMeaning(input);
//        }
//    }
//
//    /**
//     * Obtains definition of the word
//     */
//    private String obtainDefinition(String inputWord) throws IOException {
//        Document doc = Jsoup.connect("https://www.dictionary.com/browse/" + inputWord).get();
//        //Get description from document object.
//        return doc.select("meta[name=description]").get(0)
//                        .attr("content");
//    }
//    /**
//     * Main method where definition is obtained
//     */
//    public static void mainFunction() throws IOException {
//        //HashMap<String, String> vocabList = new HashMap<>();
//
//        Properties properties = new Properties();
//
//        //properties.load(new FileInputStream("src/main/java/vocabList.txt"));
//
//        //for (String key : properties.stringPropertyNames()) {
//        //    vocabList.put(key, properties.get(key).toString());
//        //}
//
//        //trying to find the word now...
//        //To find/add/type word: User types in angel...
//        if (vocabList.get(testWord) == null) {
//            String definition = obtainDefinition(testWord);
//            System.out.println(definition);
//            definition = definition.replace("definition, ", "");
//            definition = definition.replace("(", "");
//            definition = definition.replace(")", "");
//            definition = definition.replace(" See more.", "");
//            System.out.println(definition);
//            vocabList.put(testWord, definition);
//        } else {
//            String extractedWord = vocabList.get(testWord);
//            System.out.println(extractedWord);
//        }
//
//        //for (Map.Entry<String, String> entry : vocabList.entrySet()) {
//        //    properties.put(entry.getKey(), entry.getValue());
//        //}
//
//        //properties.store(new FileOutputStream("src/main/java/vocabList.txt"), null);
//    }
//}
