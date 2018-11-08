package seedu.address.commons.util;

import java.io.File;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Reads an xml file and returns a txt format file containing data stored in the xml file.
 */
// @@author KongZijin-reused
// Reused from
// https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
// with modifications.
public class XmlToTxtUtil {
    /**
     * Parse Xml file into a txt file.
     *
     * @param xmlFile
     * @param txtFilePath
     * @throws Exception
     */
    public static void parse(File xmlFile, String txtFilePath) throws Exception {
        try {
            File txtFile = new File(txtFilePath);

            PrintWriter printWriter = new PrintWriter(txtFile);
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            if (doc.hasChildNodes()) {
                fetchData(doc.getChildNodes(), 1, printWriter);
                printWriter.close();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * fetch data from xml File.
     *
     * @param nodeList
     * @param lv
     * @param printWriter
     */
    private static void fetchData(NodeList nodeList, int lv, PrintWriter printWriter) {

        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);

            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

                if (lv < 3) {
                    printWriter.println(tempNode.getNodeName());
                }

                if (lv == 3) {
                    printWriter.print("     " + tempNode.getNodeName() + ": ");
                    printWriter.println(tempNode.getTextContent());
                }

                if (tempNode.hasChildNodes()) {
                    // loop again if has child nodes
                    fetchData(tempNode.getChildNodes(), lv + 1, printWriter);

                }

                if (lv == 2) {
                    printWriter.println();
                }
            }

        }
    }
}
