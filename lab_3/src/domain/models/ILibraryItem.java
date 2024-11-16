package domain.models;

import domain.models.states.ILibraryItemState;

public interface ILibraryItem {
  String getTitle();

  void setState(ILibraryItemState state);

  ILibraryItemState getState();

  double calculateLateFee(long daysLate);

  String getDisplayInfo();
}