package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.DeselectRequestEvent;
import seedu.address.commons.events.ui.DisplayAutoMatchResultRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AutoMatchResult;
import seedu.address.model.ContactType;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Service;
import seedu.address.model.vendor.Vendor;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class AutoMatchCommand extends Command {

    public static final String COMMAND_WORD = "automatch";
    public static final String COMMAND_WORD_CLIENT = "client" + " " + COMMAND_WORD;
    public static final String COMMAND_WORD_VENDOR = "vendor" + " " + COMMAND_WORD;

    public static final String MESSAGE_USAGE_CLIENT = COMMAND_WORD + ": Finds all vendors that can fulfil any of the"
            + "service requirements of the client at his/her listed cost price."
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: #<ID> (must be a positive integer)\n"
            + "Example: client#123 " + COMMAND_WORD;
    public static final String MESSAGE_USAGE_VENDOR = COMMAND_WORD + ": Finds all clients that has service "
            + "requirements that can be served by the service provider at their listed cost price."
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: #<ID> (must be a positive integer)\n"
            + "Example: vendor#123 " + COMMAND_WORD;

    private final ContactType contactType;
    private final int contactId;

    public AutoMatchCommand(String contactType, int contactId) throws ParseException, NullPointerException {
        requireNonNull(contactType);
        this.contactType = ContactType.fromString(contactType);
        this.contactId = contactId;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // Update filtered contact list to contain all contacts and get the list
        EventsCenter.getInstance().post(new DeselectRequestEvent());
        model.updateFilteredContactList(x -> false);
        model.getFilteredContactList();

        Contact contact;

        try {
            // Find the contact for which we are going to find matches for
            contact = model
                    .getAddressBook()
                    .getContactList()
                    .stream()
                    .filter(c -> (contactType == ContactType.CLIENT && c instanceof Client)
                            || (contactType == ContactType.VENDOR && c instanceof Vendor))
                    .filter(c -> c.getId() == contactId)
                    .findFirst()
                    .get();
        } catch (NoSuchElementException | NumberFormatException exception) {
            throw new CommandException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, this.contactId));
        }

        AutoMatchResult autoMatchResult;
        if (contact instanceof Client) {
            Collection<Service> servicesRequired = contact.getServices().values();
            model.updateFilteredContactList(c -> c instanceof Vendor);

            // Perform auto-matching
            autoMatchResult = model
                    .getFilteredContactList()
                    .stream()
                    .reduce(new AutoMatchResult(contact), (accumulatedResult, c) -> {
                        accumulatedResult.put(c, servicesCanBeFulfilledByVendor((Vendor) c,
                                servicesRequired));
                        return accumulatedResult;
                    }, AutoMatchResult::mergeResults);
        } else if (contact instanceof Vendor) {
            Collection<Service> servicesProvided = contact.getServices().values();
            model.updateFilteredContactList(c -> c instanceof Client);

            // Perform auto-matching
            autoMatchResult = model
                    .getFilteredContactList()
                    .stream()
                    .reduce(new AutoMatchResult(contact), (accumulatedResult, c) -> {
                        accumulatedResult.put(c, servicesNeededToBeFulfilledByClient((Client) c,
                                servicesProvided));
                        return accumulatedResult;
                    }, AutoMatchResult::mergeResults);
        } else {
            // We should never arrive here. If we do, it means there's a Contact subclass that is not handled here.
            throw new CommandException("Unknown entity, neither client nor service provider found in database.");
        }

        // Use auto-matching results to filter contact list.
        model.updateAutoMatchResult(autoMatchResult);
        model.updateFilteredContactList(c -> autoMatchResult.getContacts().contains(c));

        // Post event to display results in table.
        EventsCenter.getInstance().post(new DisplayAutoMatchResultRequestEvent());

        // Post event to display summary in text box.
        ContactType resultContactType = contact instanceof Client ? ContactType.VENDOR
                : ContactType.VENDOR;
        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(contact));

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredContactList().size(),
                        resultContactType));
    }

    /**
     * Utility function to check if a {@code vendor} can fulfil a particular {@code serviceRequired}.
     *
     * @param vendor The service provider.
     * @param serviceRequired The service required.
     * @return Returns {@code true} if the service provider can fulfil the service, otherwise {@code false}.
     */
    private static boolean vendorCanFulfilService(Vendor vendor, Service serviceRequired) {
        return vendor
                .getServices()
                .values()
                .stream()
                .filter(serviceProvided -> serviceRequired.isSameServiceTypeAs(serviceProvided))
                .filter(serviceProvided -> serviceRequired.getCost().compareTo(serviceProvided.getCost()) >= 0)
                .count() > 0;
    }

    /**
     * Utility function to get a {@code Collection} of {@code Service} that can be fulfilled by the
     * {@code vendor}.
     * @param vendor The service provider.
     * @param servicesRequired The services required.
     * @return Returns the {@code Collection}.
     */
    private static Collection<Service> servicesCanBeFulfilledByVendor(Vendor vendor,
                                                                      Collection<Service> servicesRequired) {
        return servicesRequired
                .stream()
                .filter(serviceRequired -> vendorCanFulfilService(vendor, serviceRequired))
                .collect(Collectors.toList());
    }

    /**
     * Utility function to check if a {@code client} requires and can afford a particular {@code serviceOffered}.
     *
     * @param client         The client.
     * @param serviceOffered The service offered.
     * @return Returns true if the client requires the service and can afford it.
     */
    private static boolean clientWillPayForService(Client client, Service serviceOffered) {
        return client
                .getServices()
                .values()
                .stream()
                .filter(serviceRequired -> serviceRequired.isSameServiceTypeAs(serviceOffered))
                .filter(serviceRequired -> serviceRequired.getCost().compareTo(serviceOffered.getCost()) >= 0)
                .count() > 0;
    }

    /**
     * Utility function to get a {@code Collection} of {@code Service} needs to be fulfilled by a {@code client} given
     * the list of {@code Service} available.
     * @param client The client.
     * @param servicesProvided The services provided.
     * @return Returns the {@code Collection}.
     */
    private static Collection<Service> servicesNeededToBeFulfilledByClient(Client client, Collection<Service>
            servicesProvided) {
        return servicesProvided
                .stream()
                .filter(serviceProvided -> clientWillPayForService(client, serviceProvided))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AutoMatchCommand // instanceof handles nulls
                && contactType.equals(((AutoMatchCommand) other).contactType)
                && contactId == ((AutoMatchCommand) other).contactId); // state check
    }
}
