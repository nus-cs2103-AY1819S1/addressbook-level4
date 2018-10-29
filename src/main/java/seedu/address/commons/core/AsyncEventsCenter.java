package seedu.address.commons.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import com.google.common.eventbus.AsyncEventBus;

import seedu.address.commons.events.BaseEvent;

/**
 * Manages the event dispatching of the app.
 */
public class AsyncEventsCenter {
    private static final Logger logger = LogsCenter.getLogger(AsyncEventsCenter.class);
    private static AsyncEventsCenter instance;
    private final AsyncEventBus asyncEventBus;
    private final ExecutorService executorService;

    private AsyncEventsCenter() {
        executorService = Executors.newCachedThreadPool();
        asyncEventBus = new AsyncEventBus(executorService);
    }

    public static AsyncEventsCenter getInstance() {
        if (instance == null) {
            instance = new AsyncEventsCenter();
        }
        return instance;
    }

    public static void clearSubscribers() {
        instance = null;
    }

    public void registerHandler(Object handler) {
        asyncEventBus.register(handler);
    }

    /**
     * Posts an event to the event bus.
     */
    public <E extends BaseEvent> AsyncEventsCenter post(E event) {
        logger.info("------[Async Event Posted] " + event.getClass().getCanonicalName() + ": " + event.toString());
        asyncEventBus.post(event);
        return this;
    }

}
