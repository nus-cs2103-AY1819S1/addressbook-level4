package seedu.address.storage;

import java.util.HashMap;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.accounting.Debt;
import seedu.address.model.accounting.DebtStatus;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * JAXB-friendly version of Debts
 */
public class XmlAdaptedDebt {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Debt's %s field is missing!";
    public static final String NOT_EXIST_FIELD_MESSAGE_FORMAT = "%s does not exist in storage.";
    public static final String INVALID_INPUT_MESSAGE_FORMAT = "Not a vali input.";

    @XmlElement(required = true)
    private String creditor;
    @XmlElement(required = true)
    private String debtor;
    @XmlElement(required = true)
    private String amount;
    @XmlElement(required = true)
    private String debtId;
    @XmlElement(required = true)
    private String status;

    /**
     * Constructs an XmlAdaptedDebt.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedDebt() {}
<<<<<<< HEAD

    /**
     * Constructs an {@code XmlAdaptedDebt} with the given debt details.
     */
    public XmlAdaptedDebt( String creditor, String debtor, String amount, String debtId, String status){
=======
    /**
     * Constructs an {@code XmlAdaptedDebt} with the given debt details.
     */
    public XmlAdaptedDebt(String creditor, String debtor, String amount, String debtId, String status) {
>>>>>>> ffbf56b90dcd06d7a09b22dd5973684b606955ed
        this.creditor = creditor;
        this.debtor = debtor;
        this.amount = amount;
        this.debtId = debtId;
        this.status = status;
    }
<<<<<<< HEAD

    /**
     * Converts a given Debt into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedDebt.
     */
    public XmlAdaptedDebt( Debt source ){
        creditor = source.getCreditor().getUsername().toString();
        debtor = source.getDebtor().getUsername().toString();
        amount = String.valueOf(source.getAmount());
        debtId = source.getDebtID();
=======
    /**
     * Converts a given Debt into this class for JAXB use.
     * @param source future changes to this will not affect the created XmlAdaptedDebt.
     */
    public XmlAdaptedDebt(Debt source) {
        creditor = source.getCreditor().getUsername().toString();
        debtor = source.getDebtor().getUsername().toString();
        amount = String.valueOf(source.getAmount());
        debtId = source.getDebtId();
>>>>>>> ffbf56b90dcd06d7a09b22dd5973684b606955ed
        status = source.getDebtStatus().toString();
    }

    /**
     * Converts this jaxb-friendly adapted debt object into the model's Debt object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted debt
     */
    public Debt toModelType (HashMap<Username, User> usernameUserHashmap) throws IllegalValueException {
        if (creditor == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Debt.class.getSimpleName()));
        }
        if (debtor == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Debt.class.getSimpleName()));
        }

        if (!usernameUserHashmap.containsKey(new Username(creditor))) {
            throw new IllegalValueException(String.format(NOT_EXIST_FIELD_MESSAGE_FORMAT,
                    Debt.class.getSimpleName()));
        }

        if (!usernameUserHashmap.containsKey(new Username(debtor))) {
            throw new IllegalValueException(String.format(NOT_EXIST_FIELD_MESSAGE_FORMAT,
                    Debt.class.getSimpleName()));
        }

<<<<<<< HEAD
        if( amount == null || !(Double.valueOf(amount) > 0)){
            throw new IllegalValueException("Not a valid input amount");
=======
        if (amount == null || !(Double.valueOf(amount) > 0)) {
            throw new IllegalValueException(String.format(INVALID_INPUT_MESSAGE_FORMAT));
>>>>>>> ffbf56b90dcd06d7a09b22dd5973684b606955ed
        }

        return new Debt(usernameUserHashmap.get(new Username(creditor)),
                usernameUserHashmap.get(new Username(debtor)),
                Double.valueOf(amount), debtId, DebtStatus.valueOf(status));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedDebt)) {
            return false;
        }

        XmlAdaptedDebt otherDebt = (XmlAdaptedDebt) other;
        return otherDebt != null
                && Objects.equals(creditor, otherDebt.creditor)
                && Objects.equals(debtor, otherDebt.debtor)
                && Objects.equals(debtId, otherDebt.debtId);
    }
}
