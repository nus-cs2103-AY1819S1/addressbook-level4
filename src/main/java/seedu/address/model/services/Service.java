package seedu.address.model.services;

/**
 * Represents all the services that the clinic provides, along with their respective prices
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public enum Service {

    CONSULTATION(new ServicePrice("30")),
    BLOOD_TEST(new ServicePrice("20")),
    URINE_TEST(new ServicePrice("20")),
    MINOR_SURGERY(new ServicePrice("40"));

    private ServicePrice price;

    Service(final ServicePrice price) {
        this.price = price;
    }

    public ServicePrice getPrice() {
        return price;
    }
}
