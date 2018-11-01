package seedu.address.logic.commands.imports;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.TransactionMath;
import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.commands.exceptions.CommandException;
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

    private Document doc;
    private Model model;
    private CommandHistory history;
    private List<CcaName> ccaList;
    private Set<Entry> entries;

    private final String HEADER = "transactions";
    private final String CCA_NAME = "name";
    private final String DATE = "date";
    private final String AMOUNT = "amount";
    private final String REMARKS = "log";

    private final int INDEX = 0;

    public ImportTransaction(Document doc, Model model, CommandHistory history) {
        this.doc = doc;
        this.model = model;
        this.history = history;
        this.ccaList = new ArrayList<>();
        this.entries = new HashSet<>();
    }

    /**
     * Execute ImportTransaction
     */
    public void execute() throws CommandException {
        List<Cca> lastShownList = model.getFilteredCcaList();
        ccaList.clear();
        NodeList nList = doc.getElementsByTagName(HEADER);

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            entries.clear();
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                CcaName ccaName = new CcaName(element.getElementsByTagName(CCA_NAME).item(INDEX).getTextContent());
                Date date = new Date(element.getElementsByTagName(DATE).item(INDEX).getTextContent());
                Amount amount = new Amount(Integer.parseInt(element.getElementsByTagName(AMOUNT).item(INDEX).getTextContent()));
                Remarks remarks = new Remarks(element.getElementsByTagName(REMARKS).item(INDEX).getTextContent());

                if (!model.hasCca(ccaName)) {
                    throw new CommandException(AddTransactionCommand.MESSAGE_NON_EXISTENT_CCA);
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

                model.updateCca(ccaToUpdate, updatedCca);
                model.updateFilteredCcaList(Model.PREDICATE_SHOW_ALL_CCAS);
            }
        }
        model.commitBudgetBook();
    }

}
