package service;

import java.util.List;

import domain.database.ILibraryDatabase;
import domain.database.LibraryDatabase;
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
import service.interfaces.IBookService;
import service.interfaces.IBorrowerService;
import service.interfaces.ILibraryFacade;
import service.interfaces.ISeriesService;
import util.LibraryException;
import util.ValidationUtils;

public class LibraryFacade implements ILibraryFacade {
  private final IBookService bookService;
  private final IBorrowerService borrowerService;
  private final ISeriesService seriesService;

  public LibraryFacade() {
    ILibraryDatabase database = LibraryDatabase.getInstance();
    this.bookService = new BookService(database);
    this.borrowerService = new BorrowerService(database);
    this.seriesService = new SeriesService(database, bookService);
  }

  @Override
  public Book addBook(String title, String author, String isbn, int year,
      BookType type, BookFormat format) {
    Book baseBook = bookService.addBook(title, author, isbn, year, type);

    return switch (format) {
      case HARDCOVER -> new HardcoverBook(baseBook);
      case PAPERBACK -> new PaperbackBook(baseBook);
      case DIGITAL -> new DigitalBook(baseBook);
    };
  }

  @Override
  public BookSeries createSeries(String title) {
    return seriesService.createSeries(title);
  }

  @Override
  public void addBookToSeries(String seriesTitle, String isbn) {
    seriesService.addBookToSeries(seriesTitle, isbn);
  }

  @Override
  public List<BookSeries> getAllSeries() {
    return seriesService.getAllSeries();
  }

  @Override
  public BookSeries findSeriesByTitle(String title) {
    return seriesService.findSeriesByTitle(title);
  }

  @Override
  public List<Book> searchBooks(String searchTerm) {
    return bookService.searchBooks(searchTerm);
  }

  @Override
  public List<Book> getAllBooks() {
    return bookService.getAllBooks();
  }

  @Override
  public Book findBookByIsbn(String isbn) {
    return bookService.findBookByIsbn(isbn);
  }

  @Override
  public Book createBookCopy(String existingIsbn, String newIsbn, int year) {
    return bookService.createBookCopy(existingIsbn, newIsbn, year);
  }

  @Override
  public Borrower addBorrower(String id, String name) {
    return borrowerService.addBorrower(id, name);
  }

  @Override
  public ILibraryItem checkoutItem(String itemId, String borrowerId, int days) {
    Borrower borrower = borrowerService.findBorrowerById(borrowerId);
    if (borrower == null) {
      throw new LibraryException("Borrower not found");
    }

    try {
      ValidationUtils.validateIsbn(itemId);
      return bookService.checkoutBook(itemId, borrowerId, days);
    } catch (LibraryException e) {
      BookSeries series = seriesService.findSeriesByTitle(itemId);
      if (series == null) {
        throw new LibraryException("Item not found");
      }

      ILibraryItemState state = series.getState();
      if (!state.isAvailable()) {
        throw new LibraryException("Series is already checked out");
      }

      state.checkOut(borrower, days);
      return series;
    }
  }

  @Override
  public ILibraryItem returnItem(String itemId) {
    try {
      ValidationUtils.validateIsbn(itemId);
      return bookService.returnBook(itemId);
    } catch (LibraryException e) {
      BookSeries series = seriesService.findSeriesByTitle(itemId);
      if (series == null) {
        throw new LibraryException("Item not found");
      }

      ILibraryItemState state = series.getState();
      if (state.isAvailable()) {
        throw new LibraryException("Series is not checked out");
      }

      state.returnItem();
      return series;
    }
  }
}