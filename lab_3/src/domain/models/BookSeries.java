package domain.models;

import java.util.ArrayList;
import java.util.List;

import domain.models.states.ILibraryItemState;
import domain.models.states.SeriesAvailableState;

public class BookSeries implements ILibraryItem {
  private final String title;
  private final List<ILibraryItem> items;
  private ILibraryItemState state;

  public BookSeries(String title) {
    this.title = title;
    this.items = new ArrayList<>();
    this.state = new SeriesAvailableState();
    this.state.setContext(this);
  }

  public void addItem(ILibraryItem item) {
    items.add(item);
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public void setState(ILibraryItemState state) {
    this.state = state;
    state.setContext(this);
  }

  @Override
  public ILibraryItemState getState() {
    return state;
  }

  @Override
  public double calculateLateFee(long daysLate) {
    return items.stream()
        .mapToDouble(item -> item.calculateLateFee(daysLate))
        .sum();
  }

  @Override
  public String getDisplayInfo() {
    StringBuilder info = new StringBuilder();
    info.append(String.format("Series: %s\n", title));
    info.append(String.format("Status: %s\n", state.getStatusDisplay()));
    items.forEach(item -> info.append("  ").append(item.getDisplayInfo()).append("\n"));
    return info.toString();
  }

  public List<ILibraryItem> getItems() {
    return new ArrayList<>(items);
  }

  public boolean areAllItemsAvailable() {
    return items.stream().allMatch(item -> item.getState().isAvailable());
  }
}