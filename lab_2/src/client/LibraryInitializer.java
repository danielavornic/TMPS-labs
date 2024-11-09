package client;

import domain.models.enums.BookFormat;
import domain.models.enums.BookType;
import service.interfaces.ILibraryFacade;
import util.LibraryException;

public class LibraryInitializer {
  private final ILibraryFacade libraryFacade;

  public LibraryInitializer(ILibraryFacade libraryFacade) {
    this.libraryFacade = libraryFacade;
  }

  public void initializeWithSampleData() {
    try {
      libraryFacade.addBorrower("B001", "John Doe");
      libraryFacade.addBorrower("B002", "Jane Smith");

      libraryFacade.addBook(
          "The Great Gatsby",
          "F. Scott Fitzgerald",
          "123-1234567890",
          1925,
          BookType.FICTION,
          BookFormat.HARDCOVER);

      libraryFacade.addBook(
          "Clean Code",
          "Robert C. Martin",
          "123-0987654321",
          2008,
          BookType.NON_FICTION,
          BookFormat.PAPERBACK);

      String seriesTitle = "The Lord of the Rings";
      libraryFacade.createSeries(seriesTitle);

      libraryFacade.addBook(
          "The Fellowship of the Ring",
          "J.R.R. Tolkien",
          "123-1111111111",
          1954,
          BookType.FICTION,
          BookFormat.HARDCOVER);
      libraryFacade.addBookToSeries(seriesTitle, "123-1111111111");

      libraryFacade.addBook(
          "The Two Towers",
          "J.R.R. Tolkien",
          "123-2222222222",
          1954,
          BookType.FICTION,
          BookFormat.HARDCOVER);
      libraryFacade.addBookToSeries(seriesTitle, "123-2222222222");

      System.out.println("Library initialized with sample data.");
    } catch (LibraryException e) {
      System.out.println("Error initializing library: " + e.getMessage());
    }
  }
}