package ui;

import service.BookService;
import service.BorrowerService;
import domain.factory.BookType;
import domain.models.Book;
import domain.models.Borrower;
import util.LibraryException;
import java.util.List;

public class ConsoleUI {
  private final BookService bookService;
  private final BorrowerService borrowerService;

  public ConsoleUI(BookService bookService, BorrowerService borrowerService) {
    this.bookService = bookService;
    this.borrowerService = borrowerService;
  }

  public void start() {
    System.out.println("Welcome to Library Management System!");

    while (true) {
      try {
        displayMenu();
        int choice = InputHandler.readInt("Enter your choice: ", 1, 8);

        switch (choice) {
          case 1:
            handleAddBook();
            break;
          case 2:
            handleSearchBook();
            break;
          case 3:
            handleCheckoutBook();
            break;
          case 4:
            handleReturnBook();
            break;
          case 5:
            handleAddBorrower();
            break;
          case 6:
            handleViewAllBooks();
            break;
          case 7:
            handleCreateBookCopy();
            break;
          case 8:
            System.out.println("Thank you for using Library Management System!");
            return;
        }
      } catch (LibraryException e) {
        System.out.println("\nError: " + e.getMessage());
      } catch (Exception e) {
        System.out.println("\nUnexpected error: " + e.getMessage());
        e.printStackTrace();
      }

      InputHandler.pause();
    }
  }

  private void displayMenu() {
    System.out.println("\n=== Library Management System ===");
    System.out.println("1. Add Book");
    System.out.println("2. Search Book");
    System.out.println("3. Checkout Book");
    System.out.println("4. Return Book");
    System.out.println("5. Add Borrower");
    System.out.println("6. View All Books");
    System.out.println("7. Create Book Copy");
    System.out.println("8. Exit");
  }

  private void handleCreateBookCopy() {
    System.out.println("\n=== Create Book Copy ===");

    String existingIsbn = InputHandler.readIsbn("Enter ISBN of book to copy: ");
    String newIsbn = InputHandler.readIsbn("Enter ISBN for the new copy: ");
    int year = InputHandler.readInt("Enter publication year for the new copy: ", 1000, 2024);

    Book newBook = bookService.createBookCopy(existingIsbn, newIsbn, year);
    System.out.println("\nBook copy created successfully:");
    displayBook(newBook);
  }

  private void handleViewAllBooks() {
    System.out.println("\n=== All Books in Library ===");
    List<Book> books = bookService.getAllBooks();

    if (books.isEmpty()) {
      System.out.println("No books in the library.");
    } else {
      System.out.println("\nTotal books: " + books.size());
      books.forEach(this::displayBook);
    }
  }

  private void handleAddBook() {
    System.out.println("\n=== Adding New Book ===");

    String title = InputHandler.readString("Enter title: ");
    String author = InputHandler.readString("Enter author: ");
    String isbn = InputHandler.readIsbn("Enter ISBN (XXX-XXXXXXXXXX): ");
    int year = InputHandler.readInt("Enter publication year: ", 1000, 2024);

    System.out.println("\nBook Types:");
    System.out.println("1. Fiction");
    System.out.println("2. Non-Fiction");
    int typeChoice = InputHandler.readInt("Select book type (1-2): ", 1, 2);

    BookType type;
    switch (typeChoice) {
      case 1:
        type = BookType.FICTION;
        break;
      case 2:
        type = BookType.NON_FICTION;
        break;
      default:
        throw new LibraryException("Invalid book type selected");
    }

    Book book = bookService.addBook(title, author, isbn, year, type);
    System.out.println("\nBook added successfully:");
    displayBook(book);
  }

  private void handleSearchBook() {
    System.out.println("\n=== Search Books ===");

    String searchTerm = InputHandler.readString("Enter search term (title, author, or ISBN): ");
    List<Book> books = bookService.searchBooks(searchTerm);

    if (books.isEmpty()) {
      System.out.println("No books found matching your search.");
    } else {
      System.out.println("\nFound " + books.size() + " book(s):");
      books.forEach(this::displayBook);
    }
  }

  private void handleCheckoutBook() {
    System.out.println("\n=== Checkout Book ===");

    String isbn = InputHandler.readIsbn("Enter ISBN (XXX-XXXXXXXXXX): ");

    Book book = bookService.findBookByIsbn(isbn);
    if (book == null) {
      throw new LibraryException("Book not found");
    }

    String borrowerId = InputHandler.readBorrowerId("Enter borrower ID (e.g., B001): ");

    System.out.println("Maximum loan period for this book type: " + book.getMaxLoanDays() + " days");
    int days = InputHandler.readInt("Enter loan period in days (1-" + book.getMaxLoanDays() + "): ",
        1, book.getMaxLoanDays());

    book = bookService.checkoutBook(isbn, borrowerId, days);
    System.out.println("\nBook checked out successfully:");
    displayBook(book);
  }

  private void handleReturnBook() {
    System.out.println("\n=== Return Book ===");

    String isbn = InputHandler.readIsbn("Enter ISBN (XXX-XXXXXXXXXX): ");

    Book book = bookService.returnBook(isbn);
    System.out.println("\nBook returned successfully:");
    displayBook(book);
  }

  private void handleAddBorrower() {
    System.out.println("\n=== Add New Borrower ===");

    String id = InputHandler.readBorrowerId("Enter borrower ID (e.g., B001): ");
    String name = InputHandler.readString("Enter borrower name: ");

    Borrower borrower = borrowerService.addBorrower(id, name);
    System.out.println("\nBorrower added successfully:");
    displayBorrower(borrower);
  }

  private void displayBook(Book book) {
    System.out.println("\n-----------------");
    System.out.println("Title: " + book.getTitle());
    System.out.println("Author: " + book.getAuthor());
    System.out.println("ISBN: " + book.getIsbn());
    System.out.println("Year: " + book.getYear());
    System.out.println("Type: " + book.getType());

    System.out.println("Status: " + (book.isCheckedOut() ? "Checked Out" : "Available"));

    if (book.isCheckedOut()) {
      System.out.println("Borrowed by: " + book.getBorrower().getName());
      System.out.println("Due Date: " + book.getDueDate());
    }
    System.out.println("-----------------");
  }

  private void displayBorrower(Borrower borrower) {
    System.out.println("\n-----------------");
    System.out.println("ID: " + borrower.getId());
    System.out.println("Name: " + borrower.getName());
    System.out.println("Books borrowed: " + borrower.getBorrowedBooks().size());
    System.out.println("-----------------");
  }
}
