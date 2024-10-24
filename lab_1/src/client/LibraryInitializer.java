package client;

import domain.factory.BookType;
import service.BookService;
import service.BorrowerService;
import util.LibraryException;

public class LibraryInitializer {
  private final BookService bookService;
  private final BorrowerService borrowerService;

  public LibraryInitializer(BookService bookService, BorrowerService borrowerService) {
    this.bookService = bookService;
    this.borrowerService = borrowerService;
  }

  public void initializeWithSampleData() {
    try {
      borrowerService.addBorrower("B001", "John Doe");
      borrowerService.addBorrower("B002", "Jane Smith");

      bookService.addBook("The Great Gatsby", "F. Scott Fitzgerald",
          "123-1234567890", 1925, BookType.FICTION);
      bookService.addBook("Clean Code", "Robert C. Martin",
          "123-0987654321", 2008, BookType.NON_FICTION);

      System.out.println("Library initialized with sample data.");
    } catch (LibraryException e) {
      System.out.println("Error initializing library: " + e.getMessage());
    }
  }
}
