package seedu.address.logic.commands.imports;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import seedu.address.model.Model;
import seedu.address.model.cca.Budget;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.Outstanding;
import seedu.address.model.cca.Spent;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Entry;

//@@author kengwoon
/**
 * Imports XML file as budgetbook to update database.
 */
public class ImportBudgetBook {

    private Document doc;
    private Model model;
    private List<Cca> ccaList;
    private Set<Entry> entries;


    public ImportBudgetBook(Document doc, Model model) {
        this.doc = doc;
        this.model = model;
        this.ccaList = new ArrayList<>();
        this.entries = new HashSet<>();
    }

    /**
     * Executes ImportBudgetBook
     */

    public void execute() {
        //List<Cca> fullList = model.getBudgetBook().getCcaList();
        ccaList.clear();
        NodeList nList = doc.getElementsByTagName("ccas");
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            entries.clear();
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                //(CcaName name, Name head, Name viceHead, Budget budget, Spent spent, Outstanding outstanding,
                //               Set<Entry> transactionEntries)
                CcaName ccaname = new CcaName(element.getElementsByTagName("name").item(0).getTextContent());
                Name head = new Name(element.getElementsByTagName("head").item(0).getTextContent());
                Name viceHead = new Name(element.getElementsByTagName("viceHead").item(0).getTextContent());
                Budget budget = new Budget(Integer.parseInt(element.getElementsByTagName("budget")
                                                                    .item(0).getTextContent()));
                Spent spent = new Spent(Integer.parseInt(element.getElementsByTagName("spent")
                                                                    .item(0).getTextContent()));
                Outstanding outstanding = new Outstanding(Integer.parseInt(element.getElementsByTagName("outstanding")
                                                                                    .item(0).getTextContent()));
                NodeList transactionsList = element.getElementsByTagName("transaction");
                for (int j = 0; j < transactionsList.getLength(); j++) {
                    Node trans = transactionsList.item(j);
                    if (trans.getNodeType() == Node.ELEMENT_NODE) {
                        Element transElement = (Element) trans;

                        String entryNum = transElement.getElementsByTagName("entryNum")
                                                                        .item(0).getTextContent();
                        String date = transElement.getElementsByTagName("date").item(0).getTextContent();
                        String amount = transElement.getElementsByTagName("amount").item(0).getTextContent();
                        String log = transElement.getElementsByTagName("log").item(0).getTextContent();

                        entries.add(new Entry(entryNum, date, amount, log));
                    }
                }
                ccaList.add(new Cca(ccaname, head, viceHead, budget, spent, outstanding, entries));
            }
        }
        model.addMultipleCcas(ccaList);
    }
}
