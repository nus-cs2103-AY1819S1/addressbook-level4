package seedu.address.commons.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * To convert XML files to HTML
 *
 * @author ericyjw
 */
public class XmlToHtml {
    private static final String CCABOOK_FILEPATH = "./data/ccabook.xml";
    private static final String CCABOOK_XSL_FILEPATH = "data/ccabook.xsl";
    private static final String CCABOOK_HTML_FILEPATH = "data/ccabook.html";

    /**
     * Convert ccabook.xml into ccabook.hmtl to view on the Webview of FXML
     */
    public static void convertCcaBook(String chosenCca) {
        changeChosenCcaTo(chosenCca);

        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();

            File xmlFile = new File(CCABOOK_FILEPATH);
            File xslFile = new File(CCABOOK_XSL_FILEPATH);

            Transformer transformer = tFactory.newTransformer
                (new StreamSource(xslFile));
            transformer.transform(new StreamSource(xmlFile),
                new StreamResult(new FileOutputStream(CCABOOK_HTML_FILEPATH)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the xsl file to display chosen CCA
     *
     * @param chosenCca budget information that the chosen CCA
     */
    private static void changeChosenCcaTo(String chosenCca) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(CCABOOK_XSL_FILEPATH);

            // Set the display information of the chosen CCA
            Node chosen = doc.getElementsByTagName("xsl:if").item(0);
            NamedNodeMap attribute = chosen.getAttributes();
            Node nodeAttr = attribute.getNamedItem("test");
            String expr = "name='" + chosenCca + "'";
            nodeAttr.setTextContent(expr);

            // Write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result =
                new StreamResult(new File(CCABOOK_XSL_FILEPATH));
            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }
}
