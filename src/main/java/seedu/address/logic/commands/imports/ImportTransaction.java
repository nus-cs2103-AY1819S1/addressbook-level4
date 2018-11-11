package seedu.address.logic.commands.imports;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import seedu.address.logic.TransactionMath;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Entry;
import seedu.address.model.transaction.Remarks;

//@@author kengwoon
/**
 * Imports XML file as transaction to update database.
 */
public class ImportTransaction {

    private static final String HEADER = "transaction";
    private static final String CCA_NAME = "cca";
    private static final String DATE = "date";
    private static final String AMOUNT = "amount";
    private static final String REMARKS = "log";
    private static final int INDEX = 0;

    private Document doc;
    private Model model;
    private List<Cca> ccaList;
    private List<Cca> editedList;
    private Set<Entry> entries;

    public ImportTransaction(Document doc, Model model) {
        this.doc = doc;
        this.model = model;
        this.ccaList = new ArrayList<>();
        this.editedList = new ArrayList<>();
        this.entries = new HashSet<>();
    }

    /**
     * Execute ImportTransaction
     */
    public void execute() {
        List<Cca> lastShownList = model.getFilteredCcaList();
        ccaList.clear();
        editedList.clear();
        NodeList nList = doc.getElementsByTagName(HEADER);

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            entries.clear();
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                CcaName ccaName = new CcaName(element.getElementsByTagName(CCA_NAME).item(INDEX).getTextContent());
                Date date = new Date(element.getElementsByTagName(DATE).item(INDEX).getTextContent());
                Amount amount = new Amount(Integer.parseInt(element.getElementsByTagName(AMOUNT).item(INDEX)
                                                .getTextContent()));
                Remarks remarks = new Remarks(element.getElementsByTagName(REMARKS).item(INDEX).getTextContent());

                if (!model.hasCca(ccaName)) {
                    continue;
                }

                int index = 0;
                for (Cca c : lastShownList) {
                    if (c.getCcaName().equals(ccaName.getNameOfCca())) {
                        break;
                    }
                    index++;
                }

                Cca ccaToUpdate = lastShownList.get(index);
                int entryNum = ccaToUpdate.getEntrySize() + 1;
                Entry newEntry = new Entry (entryNum, date, amount, remarks);
                Cca updatedCca = ccaToUpdate.addNewTransaction(newEntry);
                updatedCca = TransactionMath.updateDetails(updatedCca);

                ccaList.add(ccaToUpdate);
                editedList.add(updatedCca);
            }
        }
        model.updateMultipleCcas(ccaList, editedList);
        model.updateFilteredCcaList(Model.PREDICATE_SHOW_ALL_CCAS);
        model.commitBudgetBook();
    }

}
