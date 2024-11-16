package service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.database.ILibraryDatabase;
import domain.factory.BookCreator;
import domain.factory.FictionSection;
import domain.factory.NonFictionSection;
import domain.models.Book;
import domain.models.Borrower;
import domain.models.enums.BookType;
import domain.models.states.ILibraryItemState;
import service.interfaces.IBookService;
import util.LibraryException;
import util.ValidationUtils;

public class BookService implements IBookService {
  private final ILibraryDatabase database;
  private final Map<BookType, BookCreator> factories;

  public BookService(ILibraryDatabase database) {
    this.database = database;
    this.factories = new HashMap<>();
    initializeFactories();
  }

  private void initializeFactories() {
    factories.put(BookType.FICTION, new FictionSection());
    factories.put(BookType.NON_FICTION, new NonFictionSection());
  }

  public Book addBook(String title, String author, String isbn, int year, BookType type) {
    ValidationUtils.validateIsbn(isbn);
    ValidationUtils.validateYear(year);
    ValidationUtils.validateName(title);
    ValidationUtils.validateName(author);

    if (database.findBookByIsbn(isbn) != null) {
      throw new LibraryException("Book with ISBN " + isbn + " already exists");
    }

    BookCreator factory = factories.get(type);
    if (factory == null) {
      throw new LibraryException("Unsupported book type: " + type);
    }

    Book book = factory.createBook(title, author, isbn, year);
    database.addBook(book);
    return book;
  }

  public Book checkoutBook(String isbn, String borrowerId, double loanPeriodDays) {
    Book book = database.findBookByIsbn(isbn);
    if (book == null) {
      throw new LibraryException("Book not found");
    }

    ILibraryItemState state = book.getState();
    if (!state.isAvailable()) {
      throw new LibraryException("Book is already checked out until " + state.getDueDate());
    }

    if (loanPeriodDays > book.getMaxLoanDays()) {
      throw new LibraryException("Maximum loan period for this book type is " +
          book.getMaxLoanDays() + " days");
    }

    Borrower borrower = database.findBorrowerById(borrowerId);
    if (borrower == null) {
      throw new LibraryException("Borrower not found");
    }
    if (borrower.getBorrowedBooks().size() >= 3) {
      throw new LibraryException("Borrower has reached maximum number of books (3)");
    }

    state.checkOut(borrower, loanPeriodDays);
    borrower.getBorrowedBooks().add(book);
    return book;
  }

  public Book returnBook(String isbn) {
    Book book = database.findBookByIsbn(isbn);
    if (book == null) {
      throw new LibraryException("Book not found");
    }

    ILibraryItemState state = book.getState();
    if (state.isAvailable()) {
      throw new LibraryException("Book is not checked out");
    }

    LocalDate dueDate = state.getDueDate();
    LocalDate today = LocalDate.now();

    if (today.isAfter(dueDate)) {
      long daysLate = ChronoUnit.DAYS.between(dueDate, today);
      double lateFee = book.calculateLateFee(daysLate);
      System.out.printf("Late fee for %d days: $%.2f%n", daysLate, lateFee);
    }

    Borrower borrower = state.getBorrower();
    state.returnItem();
    borrower.getBorrowedBooks().remove(book);
    return book;
  }

  public List<Book> searchBooks(String searchTerm) {
    if (searchTerm == null || searchTerm.trim().isEmpty()) {
      throw new LibraryException("Search term cannot be empty");
    }
    return database.searchBooks(searchTerm.trim());
  }

  public Book findBookByIsbn(String isbn) {
    ValidationUtils.validateIsbn(isbn);
    return database.findBookByIsbn(isbn);
  }

  public List<Book> getAllBooks() {
    return database.getAllBooks();
  }

  public Book createBookCopy(String existingIsbn, String newIsbn, int year) {
    Book existingBook = database.findBookByIsbn(existingIsbn);
    if (existingBook == null) {
      throw new LibraryException("Original book not found");
    }
    if (database.findBookByIsbn(newIsbn) != null) {
      throw new LibraryException("A book with the new ISBN already exists");
    }

    Book newBook = existingBook.clone();
    newBook.setISBN(newIsbn);
    newBook.setYear(year);
    database.addBook(newBook);
    return newBook;
  }

  public void checkAllDueDates() {
    List<Book> books = database.getAllBooks();
    books.stream()
        .filter(book -> !book.getState().isAvailable())
        .forEach(book -> book.getState().checkDueDate());
  }
}