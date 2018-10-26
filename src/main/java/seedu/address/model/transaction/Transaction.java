//package seedu.address.model.transaction;
//
//import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.AppUtil.checkArgument;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
//import org.apache.commons.collections4.iterators.EntrySetMapIterator;
//
///**
// * Represent the Transaction History of a CCA.
// */
//public class Transaction {
//
//    private Set<Entry> entries;
//
//    public Transaction() {
//        this.entries = new HashSet<>();
//    }
//
//    public Transaction(Set<Entry> transactions) {
//        requireNonNull(transactions);
//        this.entries = transactions;
//    }
//
//    public Set<Entry> getTransactionList() {
//        return entries;
//    }
//
//    /**
//     * Update the transaction history by appending new entry
//     * @param entry new transaction entry
//     */
//    public void updateTransaction(Entry entry) {
//        this.entries.add(entry);
//    }
//
//    /**
//     * Returns true if a given string is a valid tag name.
//     */
//    public static boolean isValidTransaction(Set<Entry> entries) {
//        for (Entry e: entries) {
//            if (!Entry.isValidEntry(e)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * Returns true if both ccas have the same identity and data fields.
//     */
//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//
//        if (!(other instanceof Transaction)) {
//            return false;
//        }
//
//        Transaction otherBudget = (Transaction) other;
//        return otherBudget.entries.equals(entries);
//    }
//}
