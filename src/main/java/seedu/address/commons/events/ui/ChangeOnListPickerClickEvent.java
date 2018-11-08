package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class ChangeOnListPickerClickEvent extends BaseEvent {
  private final int newSelection;

  public ChangeOnListPickerClickEvent (int newSelection) {
    this.newSelection = newSelection;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

  public int getNewSelection() {
    return newSelection;
  }
}
