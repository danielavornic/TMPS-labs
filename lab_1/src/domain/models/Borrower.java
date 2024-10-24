package domain.models;

import java.util.ArrayList;
import java.util.List;

public class Borrower {
  private String id;
  private String name;
  private List<Book> borrowedBooks;

  public Borrower(String id, String name) {
    this.id = id;
    this.name = name;
    this.borrowedBooks = new ArrayList<>();
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

  @Override
  public String toString() {
    return "Borrower{id='" + id + "', name='" + name + "'}";
  }
}