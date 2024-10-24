package client;

import service.BookService;
import service.BorrowerService;
import ui.ConsoleUI;

public class LibraryManagementSystem {
  public static void main(String[] args) {
    BookService bookService = new BookService();
    BorrowerService borrowerService = new BorrowerService();

    LibraryInitializer initializer = new LibraryInitializer(bookService, borrowerService);
    initializer.initializeWithSampleData();

    ConsoleUI ui = new ConsoleUI(bookService, borrowerService);
    ui.start();
  }
}