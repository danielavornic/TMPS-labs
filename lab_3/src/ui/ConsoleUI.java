package ui;

import java.util.List;

import domain.models.Book;
import domain.models.BookSeries;
import domain.models.Borrower;
import domain.models.ILibraryItem;
import domain.models.decorators.DigitalBook;
import domain.models.decorators.HardcoverBook;
import domain.models.decorators.PaperbackBook;
import domain.models.enums.BookFormat;
import domain.models.enums.BookType;
import domain.models.states.ILibraryItemState;
import service.interfaces.ILibraryFacade;
import util.LibraryException;

public class ConsoleUI {
  private final ILibraryFacade libraryFacade;

  public ConsoleUI(ILibraryFacade libraryFacade) {
    this.libraryFacade = libraryFacade;
  }

  public void start() {
    System.out.println("Welcome to Library Management System!");

    while (true) {
      try {
        displayMenu();
        int choice = InputHandler.readInt("Enter your choice: ", 1, 11);

        switch (choice) {
          case 1 -> handleViewAllBooks();
          case 2 -> handleViewAllSeries();
          case 3 -> handleSearchBook();
          case 4 -> handleAddBook();
          case 5 -> handleCreateBookCopy();
          case 6 -> handleCreateSeries();
          case 7 -> handleAddToExistingSeries();
          case 8 -> handleAddBorrower();
          case 9 -> handleCheckoutItem();
          case 10 -> handleReturnItem();
          case 11 -> exit();
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
    System.out.println("1. View All Books");
    System.out.println("2. View All Series");
    System.out.println("3. Search Items");
    System.out.println("4. Add Book");
    System.out.println("5. Create Book Copy");
    System.out.println("6. Create Book Series");
    System.out.println("7. Add Book to Series");
    System.out.println("8. Add Borrower");
    System.out.println("9. Checkout Item");
    System.out.println("10. Return Item");
    System.out.println("11. Exit");
  }

  private void exit() {
    System.out.println("\nExiting Library Management System. Goodbye!");

    System.exit(0);
    return;
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

    System.out.println("\nBook Format:");
    System.out.println("1. Hardcover");
    System.out.println("2. Paperback");
    System.out.println("3. Digital");
    int formatChoice = InputHandler.readInt("Select book format (1-3): ", 1, 3);

    BookType type = switch (typeChoice) {
      case 1 -> BookType.FICTION;
      case 2 -> BookType.NON_FICTION;
      default -> throw new LibraryException("Invalid book type selected");
    };

    BookFormat format = switch (formatChoice) {
      case 1 -> BookFormat.HARDCOVER;
      case 2 -> BookFormat.PAPERBACK;
      case 3 -> BookFormat.DIGITAL;
      default -> throw new LibraryException("Invalid format selected");
    };

    Book book = libraryFacade.addBook(title, author, isbn, year, type, format);
    System.out.println("\nBook added successfully:");
    displayBook(book);
  }

  private void handleCreateSeries() {
    System.out.println("\n=== Create Book Series ===");
    String title = InputHandler.readString("Enter series title: ");
    BookSeries series = libraryFacade.createSeries(title);

    while (true) {
      System.out.println("\nAdd book to series?");
      System.out.println("1. Add existing book by ISBN");
      System.out.println("2. Finish");

      int choice = InputHandler.readInt("Enter choice: ", 1, 2);
      if (choice == 2)
        break;

      String isbn = InputHandler.readIsbn("Enter book ISBN: ");
      libraryFacade.addBookToSeries(title, isbn);
      System.out.println("Book added to series successfully");
    }

    System.out.println("\nSeries created successfully:");
    displaySeries(series);
  }

  private void handleViewAllSeries() {
    System.out.println("\n=== All Book Series ===");
    List<BookSeries> allSeries = libraryFacade.getAllSeries();

    if (allSeries.isEmpty()) {
      System.out.println("No series found in the library.");
    } else {
      System.out.println("\nTotal series: " + allSeries.size());
      allSeries.forEach(this::displaySeries);
    }
  }

  private void handleCheckoutItem() {
    System.out.println("\n=== Checkout Item ===");
    System.out.println("1. Checkout Individual Book");
    System.out.println("2. Checkout Book Series");

    int choice = InputHandler.readInt("Select option: ", 1, 2);
    String itemId;

    if (choice == 1) {
      itemId = InputHandler.readIsbn("Enter ISBN: ");
    } else {
      itemId = InputHandler.readString("Enter series title: ");
    }

    String borrowerId = InputHandler.readBorrowerId("Enter borrower ID (e.g., B001): ");
    int days = InputHandler.readInt("Enter loan period in days: ", 1, 30);

    ILibraryItem item = libraryFacade.checkoutItem(itemId, borrowerId, days);
    System.out.println("\nItem checked out successfully:");

    if (item instanceof Book) {
      displayBook((Book) item);
    } else {
      displaySeries((BookSeries) item);
    }
  }

  private void handleReturnItem() {
    System.out.println("\n=== Return Item ===");
    System.out.println("1. Return Individual Book");
    System.out.println("2. Return Book Series");

    int choice = InputHandler.readInt("Select option: ", 1, 2);
    String itemId;

    if (choice == 1) {
      itemId = InputHandler.readIsbn("Enter ISBN: ");
    } else {
      itemId = InputHandler.readString("Enter series title: ");
    }

    ILibraryItem item = libraryFacade.returnItem(itemId);
    System.out.println("\nItem returned successfully:");

    if (item instanceof Book) {
      displayBook((Book) item);
    } else {
      displaySeries((BookSeries) item);
    }
  }

  private void handleSearchBook() {
    System.out.println("\n=== Search Books ===");
    String searchTerm = InputHandler.readString("Enter search term (title, author, or ISBN): ");
    List<Book> books = libraryFacade.searchBooks(searchTerm);

    if (books.isEmpty()) {
      System.out.println("No books found matching your search.");
    } else {
      System.out.println("\nFound " + books.size() + " book(s):");
      books.forEach(this::displayBook);
    }
  }

  private void handleViewAllBooks() {
    System.out.println("\n=== All Books in Library ===");
    List<Book> books = libraryFacade.getAllBooks();

    if (books.isEmpty()) {
      System.out.println("No books in the library.");
    } else {
      System.out.println("\nTotal books: " + books.size());
      books.forEach(this::displayBook);
    }
  }

  private void handleAddToExistingSeries() {
    System.out.println("\n=== Add Book to Series ===");

    // First select the book
    String isbn = InputHandler.readIsbn("Enter ISBN of the book to add: ");
    Book book = libraryFacade.findBookByIsbn(isbn);
    if (book == null) {
      throw new LibraryException("Book not found");
    }
    System.out.println("\nSelected book:");
    displayBook(book);

    // Then select the series
    List<BookSeries> allSeries = libraryFacade.getAllSeries();
    if (allSeries.isEmpty()) {
      throw new LibraryException("No series exist. Please create a series first.");
    }

    System.out.println("\nAvailable Series:");
    for (int i = 0; i < allSeries.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, allSeries.get(i).getTitle());
    }

    int seriesChoice = InputHandler.readInt("Select series (1-" + allSeries.size() + "): ",
        1, allSeries.size());

    String seriesTitle = allSeries.get(seriesChoice - 1).getTitle();
    libraryFacade.addBookToSeries(seriesTitle, isbn);

    System.out.println("\nBook successfully added to series: " + seriesTitle);
    displaySeries(libraryFacade.findSeriesByTitle(seriesTitle));
  }

  private void handleCreateBookCopy() {
    System.out.println("\n=== Create Book Copy ===");

    String existingIsbn = InputHandler.readIsbn("Enter ISBN of book to copy: ");
    String newIsbn = InputHandler.readIsbn("Enter ISBN for the new copy: ");
    int year = InputHandler.readInt("Enter publication year for the new copy: ", 1000, 2024);

    Book newBook = libraryFacade.createBookCopy(existingIsbn, newIsbn, year);
    System.out.println("\nBook copy created successfully:");
    displayBook(newBook);
  }

  private void handleAddBorrower() {
    System.out.println("\n=== Add New Borrower ===");
    String id = InputHandler.readBorrowerId("Enter borrower ID (e.g., B001): ");
    String name = InputHandler.readString("Enter borrower name: ");

    Borrower borrower = libraryFacade.addBorrower(id, name);
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
    System.out.println("Format: " + getBookFormat(book));
    System.out.println("Max Loan Period: " + book.getMaxLoanDays() + " days");

    ILibraryItemState state = book.getState();
    System.out.println("Status: " + state.getStatusDisplay());

    if (!state.isAvailable()) {
      System.out.println("Borrowed by: " + state.getBorrower().getName());
      System.out.println("Due Date: " + state.getDueDate());
    }
    System.out.println("-----------------");
  }

  private void displaySeries(BookSeries series) {
    System.out.println("\n=================");
    System.out.println("Series: " + series.getTitle());

    ILibraryItemState state = series.getState();
    System.out.println("Status: " + state.getStatusDisplay());

    System.out.println("Books in series:");
    for (ILibraryItem item : series.getItems()) {
      if (item instanceof Book) {
        Book book = (Book) item;
        String status = book.getState().getStatusDisplay();
        System.out.println("  - " + book.getTitle() + " by " + book.getAuthor() +
            " (" + status + ")");
      }
    }
    System.out.println("=================");
  }

  private void displayBorrower(Borrower borrower) {
    System.out.println("\n-----------------");
    System.out.println("ID: " + borrower.getId());
    System.out.println("Name: " + borrower.getName());
    System.out.println("Books borrowed: " + borrower.getBorrowedBooks().size());
    System.out.println("-----------------");
  }

  private String getBookFormat(Book book) {
    if (book instanceof HardcoverBook)
      return "Hardcover";
    if (book instanceof PaperbackBook)
      return "Paperback";
    if (book instanceof DigitalBook)
      return "Digital";
    return "Unknown";
  }
}