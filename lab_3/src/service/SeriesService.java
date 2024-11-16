package service;

import java.util.List;

import domain.database.ILibraryDatabase;
import domain.models.Book;
import domain.models.BookSeries;
import service.interfaces.IBookService;
import service.interfaces.ISeriesService;
import util.LibraryException;

public class SeriesService implements ISeriesService {
  private final ILibraryDatabase database;
  private final IBookService bookService;

  public SeriesService(ILibraryDatabase database, IBookService bookService) {
    this.database = database;
    this.bookService = bookService;
  }

  @Override
  public BookSeries createSeries(String title) {
    if (database.findSeriesByTitle(title) != null) {
      throw new LibraryException("Series already exists");
    }
    BookSeries newSeries = new BookSeries(title);
    database.addSeries(newSeries);
    return newSeries;
  }

  @Override
  public void addBookToSeries(String seriesTitle, String isbn) {
    BookSeries series = database.findSeriesByTitle(seriesTitle);
    if (series == null) {
      throw new LibraryException("Series not found");
    }

    Book book = bookService.findBookByIsbn(isbn);
    if (book == null) {
      throw new LibraryException("Book not found");
    }

    series.addItem(book);
  }

  @Override
  public List<BookSeries> getAllSeries() {
    return database.getAllSeries();
  }

  @Override
  public BookSeries findSeriesByTitle(String title) {
    return database.findSeriesByTitle(title);
  }
}