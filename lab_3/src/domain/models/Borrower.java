package domain.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import domain.observer.ILibraryObserver;

public class Borrower implements ILibraryObserver {
  private String id;
  private String name;
  private List<Book> borrowedBooks;
  private List<String> notifications;

  public Borrower(String id, String name) {
    this.id = id;
    this.name = name;
    this.borrowedBooks = new ArrayList<>();
    this.notifications = new ArrayList<>();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<Book> getBorrowedBooks() {
    return borrowedBooks;
  }

  public List<String> getNotifications() {
    return new ArrayList<>(notifications);
  }

  public void clearNotifications() {
    notifications.clear();
  }

  @Override
  public String toString() {
    return "Borrower{id='" + id + "', name='" + name + "'}";
  }

  @Override
  public void update(String message) {
    // Do not add duplicate notifications
    if (notifications.stream().anyMatch(n -> n.contains(message))) {
      return;
    }

    String timestamp = LocalDateTime.now().toString();
    notifications.add(String.format("[%s] %s", timestamp, message));
  }
}