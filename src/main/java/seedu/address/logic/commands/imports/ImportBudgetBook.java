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

    private final String HEADER = "ccas";
    private final String CCA_NAME = "name";
    private final String HEAD = "head";
    private final String VICEHEAD = "viceHead";
    private final String BUDGET = "budget";
    private final String SPENT = "spent";
    private final String OUTSTANDING = "outstanding";
    private final String TRANSACTION_HEADER = "transaction";
    private final String ENTRY_NUM = "entryNum";
    private final String DATE = "date";
    private final String AMOUNT = "amount";
    private final String REMARKS = "log";

    private final int INDEX = 0;


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
        NodeList nList = doc.getElementsByTagName(HEADER);
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            entries.clear();
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                CcaName ccaname = new CcaName(element.getElementsByTagName(CCA_NAME).item(INDEX).getTextContent());
                Name head = new Name(element.getElementsByTagName(HEAD).item(INDEX).getTextContent());
                Name viceHead = new Name(element.getElementsByTagName(VICEHEAD).item(INDEX).getTextContent());
                Budget budget = new Budget(Integer.parseInt(element.getElementsByTagName(BUDGET)
                                                                    .item(INDEX).getTextContent()));
                Spent spent = new Spent(Integer.parseInt(element.getElementsByTagName(SPENT)
                                                                    .item(INDEX).getTextContent()));
                Outstanding outstanding = new Outstanding(Integer.parseInt(element.getElementsByTagName(OUTSTANDING)
                                                                                    .item(INDEX).getTextContent()));
                NodeList transactionsList = element.getElementsByTagName(TRANSACTION_HEADER);
                for (int j = 0; j < transactionsList.getLength(); j++) {
                    Node trans = transactionsList.item(j);
                    if (trans.getNodeType() == Node.ELEMENT_NODE) {
                        Element transElement = (Element) trans;

                        String entryNum = transElement.getElementsByTagName(ENTRY_NUM)
                                                                        .item(INDEX).getTextContent();
                        String date = transElement.getElementsByTagName(DATE).item(INDEX).getTextContent();
                        String amount = transElement.getElementsByTagName(AMOUNT).item(INDEX).getTextContent();
                        String log = transElement.getElementsByTagName(REMARKS).item(INDEX).getTextContent();

                        entries.add(new Entry(entryNum, date, amount, log));
                    }
                }
                ccaList.add(new Cca(ccaname, head, viceHead, budget, spent, outstanding, entries));
            }
        }
        model.addMultipleCcas(ccaList);
        model.commitBudgetBook();
    }
}
