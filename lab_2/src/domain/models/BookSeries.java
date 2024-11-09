package domain.models;

import java.util.ArrayList;
import java.util.List;
import util.LibraryException;

public class BookSeries implements ILibraryItem {
  private final String title;
  private final List<ILibraryItem> items;

  public BookSeries(String title) {
    this.title = title;
    this.items = new ArrayList<>();
  }

  public void addItem(ILibraryItem item) {
    items.add(item);
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public boolean isAvailable() {
    return items.stream().allMatch(ILibraryItem::isAvailable);
  }

  @Override
  public void checkOut(Borrower borrower, int loanPeriodDays) {
    if (!isAvailable()) {
      throw new LibraryException("Not all items in series are available");
    }
    items.forEach(item -> item.checkOut(borrower, loanPeriodDays));
  }

  @Override
  public void returnItem() {
    items.forEach(ILibraryItem::returnItem);
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
    items.forEach(item -> info.append("  ").append(item.getDisplayInfo()).append("\n"));
    return info.toString();
  }

  public List<ILibraryItem> getItems() {
    return new ArrayList<>(items);
  }
}