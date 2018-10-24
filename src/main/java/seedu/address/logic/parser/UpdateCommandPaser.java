package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OUTSTANDING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VICE_HEAD;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.EditCcaDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cca.CcaName;
import seedu.address.model.transaction.Entry;

/**
 * Parses input arguments and creates a new UpdateCommand object
 *
 * @author ericyjw
 */
public class UpdateCommandPaser implements Parser<UpdateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_NAME, PREFIX_HEAD, PREFIX_VICE_HEAD, PREFIX_BUDGET,
                PREFIX_SPENT, PREFIX_OUTSTANDING, PREFIX_TRANSACTION, PREFIX_ENTRY, PREFIX_DATE, PREFIX_AMOUNT,
                PREFIX_REMARKS);

        if (!argMultimap.getValue(PREFIX_TAG).isPresent()) {
            throw new ParseException(UpdateCommand.MESSAGE_NO_SPECIFIC_CCA);
        }
        CcaName ccaName = ParserUtil.parseCcaName((argMultimap.getValue(PREFIX_TAG).get()));

        EditCcaDescriptor editCcaDescriptor = new EditCcaDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editCcaDescriptor.setCcaName(ParserUtil.parseCcaName((argMultimap.getValue(PREFIX_NAME).get())));
        }
        if (argMultimap.getValue(PREFIX_HEAD).isPresent()) {
            editCcaDescriptor.setHead(ParserUtil.parseName(argMultimap.getValue(PREFIX_HEAD).get()));
        }
        if (argMultimap.getValue(PREFIX_VICE_HEAD).isPresent()) {
            editCcaDescriptor.setViceHead(ParserUtil.parseName(argMultimap.getValue(PREFIX_VICE_HEAD).get()));
        }
        if (argMultimap.getValue(PREFIX_BUDGET).isPresent()) {
            editCcaDescriptor.setBudget(ParserUtil.parseBudget(argMultimap.getValue(PREFIX_BUDGET).get()));
        }
        if (argMultimap.getValue(PREFIX_SPENT).isPresent()) {
            editCcaDescriptor.setSpent(ParserUtil.parseSpent(argMultimap.getValue(PREFIX_SPENT).get()));
        }
        if (argMultimap.getValue(PREFIX_OUTSTANDING).isPresent()) {
            editCcaDescriptor.setOutstanding(
                ParserUtil.parseOutstanding(argMultimap.getValue(PREFIX_OUTSTANDING).get())
            );
        }






/*
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editCcaDescriptor.setDate(ParserUtil.parseEntryDate(argMultimap.getValue(PREFIX_DATE).get())
            );
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editCcaDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get())
            );
        }
        if (argMultimap.getValue(PREFIX_REMARKS).isPresent()) {
            editCcaDescriptor.setRemarks(ParserUtil.parseRemarks(argMultimap.getValue(PREFIX_REMARKS).get())
            );
        }
 */

//        if (argMultimap.getValue(PREFIX_TRANSACTION).isPresent()) {
//            String entryNum = argMultimap.getValue(PREFIX_TRANSACTION).get();
//            String date = null;
//            String amount = null;
//            String remarks = null;
//
//            if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
//                date = argMultimap.getValue(PREFIX_DATE).get();
//            }
//            if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
//                amount = argMultimap.getValue(PREFIX_AMOUNT).get();
//            }
//            if (argMultimap.getValue(PREFIX_REMARKS).isPresent()) {
//                remarks = argMultimap.getValue(PREFIX_REMARKS).get();
//            }
//
//            parseTransactionEntriesForEdit(argMultimap.getAllEntries(PREFIX_TRANSACTION)).ifPresent
//             (editCcaDescriptor::setTransaction);
//
//            editCcaDescriptor.setTransaction(
//                ParserUtil.parseTransaction(new Entry(entryNum, date, amount, remarks))
//            );
//        }

        parseTransactionEntriesForEdit(argMultimap, argMultimap.getAllEntries(PREFIX_TRANSACTION)).ifPresent
         (editCcaDescriptor::setTransaction);


        if (!editCcaDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOT_UPDATED);
        }

        return new UpdateCommand(ccaName, editCcaDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Entry>> parseTransactionEntriesForEdit(ArgumentMultimap map, Collection<String> entries) throws ParseException {
        assert entries != null;

        if (entries.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> transactionEntrySet = entries.size() == 1 && entries.contains("") ? Collections.emptySet()
            : entries;
        return Optional.of(ParserUtil.parseTransaction(map, transactionEntrySet));
    }

}

