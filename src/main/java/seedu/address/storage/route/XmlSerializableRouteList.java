package seedu.address.storage.route;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.route.ReadOnlyRouteList;
import seedu.address.model.route.Route;
import seedu.address.model.route.RouteList;

/**
 * An Immutable RouteList that is serializable to XML format
 */
@XmlRootElement(name = "routelist")
public class XmlSerializableRouteList {

    public static final String MESSAGE_DUPLICATE_ROUTE = "Route list contains duplicate route(s).";

    @XmlElement
    private List<XmlAdaptedRoute> routes;

    /**
     * Creates an empty XmlSerializableRouteList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableRouteList() {
        routes = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableRouteList(ReadOnlyRouteList src) {
        this();
        routes.addAll(src.getRouteList().stream().map(XmlAdaptedRoute::new).collect(Collectors.toList()));
    }

    /**
     * Converts this route list into the model's {@code RouteList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedRoute}.
     */
    public RouteList toModelType() throws IllegalValueException {
        RouteList routeList = new RouteList();
        for (XmlAdaptedRoute r : routes) {
            Route route = r.toModelType();
            if (routeList.hasRoute(route)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ROUTE);
            }
            routeList.addRoute(route);
        }
        return routeList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableRouteList)) {
            return false;
        }
        return routes.equals(((XmlSerializableRouteList) other).routes);
    }
}

