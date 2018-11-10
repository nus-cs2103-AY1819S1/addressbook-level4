package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.StudentPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Payment;
import seedu.address.model.student.Student;


/**
 * Add payment details of an existing student in the TutorHelper.
 */
public class PayCommand extends Command {
    public static final String COMMAND_WORD = "paid";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates if a student has paid.\n"
            + "Parameters: "
            + "INDEX AMOUNT MONTH YEAR \n"
            + "Example: " + COMMAND_WORD + " 1 200 08 2018 ";

    public static final String MESSAGE_PAYMENT_SUCCESS = "Payment for this student is added: %1$s";
    public static final String MESSAGE_EDITPAYMENT_SUCCESS = "Payment for this student has been edited: %1$s";

    private static final int MINVALUE = -1;
    private static final int MAXPAYMENTSDISPLAYED = 5;

    private Index targetIndex;
    private Payment newPayment;

    public PayCommand(Payment payment) {
        this.targetIndex = payment.getIndex();
        this.newPayment = payment;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        boolean editEntry;
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getZeroBased() <= MINVALUE) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentTarget = lastShownList.get(targetIndex.getZeroBased());

        List<Payment> pay = studentTarget.getPayments();

        editEntry = findPaymentToUpdate(pay, newPayment);

        if (!editEntry) {
            if (pay.size() > MAXPAYMENTSDISPLAYED) {
                pay.remove(0);
            }
            pay = updatePayment(pay, newPayment);
        } else {
            pay = editPaymentField(pay, newPayment);
        }

        Student studentToPay = new Student(studentTarget.getName(), studentTarget.getPhone(),
                studentTarget.getEmail(), studentTarget.getAddress(), studentTarget.getSubjects(),
                studentTarget.getTuitionTiming(), studentTarget.getTags(), pay);

        if (editEntry) {
            model.updateStudentInternalField(studentTarget, studentToPay);
            model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
            model.commitTutorHelper();
            return new CommandResult(String.format(MESSAGE_EDITPAYMENT_SUCCESS, studentToPay));
        } else {
            model.updateStudent(studentTarget, studentToPay);
            model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
            model.commitTutorHelper();
            return new CommandResult(String.format(MESSAGE_PAYMENT_SUCCESS, studentToPay));
        }
    }

    /**
     * Update payment for this student and returns a new instance of this student.
     * @return the same student but updated with payment.
     */
    public List<Payment> updatePayment(List<Payment> oldPayments, Payment newPayment) {
        List<Payment> updatedPayment = new ArrayList<>(oldPayments);
        updatedPayment.add(newPayment);
        return updatedPayment;
    }

    /**
     * Checks if student to edit has the payment field.
     * @param payments the list of payment to check for entry with same details.
     * @param toFind the payment entry to edit.
     * @return true if payment field has already existed; false otherwise.
     */
    private boolean findPaymentToUpdate(List<Payment> payments, Payment toFind) {
        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getMonth() == toFind.getMonth() && payments.get(i).getYear() == toFind.getYear()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates payment field in list of payments.
     * @param payments the list of payment to check for entry with same details.
     * @param toEdit the payment entry to edit.
     * @return the edited list of payments.
     */
    private List<Payment> editPaymentField(List<Payment> payments, Payment toEdit) {
        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getMonth() == toEdit.getMonth() && payments.get(i).getYear() == toEdit.getYear()) {
                payments.set(i, toEdit);
                break;
            }
        }
        return payments;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PayCommand)) {
            return false;
        }

        // state check
        PayCommand e = (PayCommand) other;
        return targetIndex.equals(e.targetIndex)
                && newPayment.equals(e.newPayment);
    }
}
