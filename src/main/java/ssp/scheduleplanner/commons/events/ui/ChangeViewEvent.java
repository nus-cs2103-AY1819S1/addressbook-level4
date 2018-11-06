package ssp.scheduleplanner.commons.events.ui;

import ssp.scheduleplanner.commons.events.BaseEvent;

/**
 * An event requesting to change the view of the window.
 */
public class ChangeViewEvent extends BaseEvent {

    /**
     * Types of views to display
     */
    public enum View {
        NORMAL, ARCHIVE
    }

    private View view;

    public ChangeViewEvent(View targetView) {
        this.view = targetView;
    }

    public View getView() {
        return view;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
