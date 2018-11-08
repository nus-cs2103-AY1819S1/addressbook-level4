package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that the leave list command has been run
 */
public class ArchiveListEvent extends BaseEvent {
  public ArchiveListEvent() {
  }

  @Override
  public String toString() {
    return "archivelist";
  }
}
