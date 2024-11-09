package service.interfaces;

import java.util.List;

import domain.models.BookSeries;

public interface ISeriesService {
  BookSeries createSeries(String title);

  void addBookToSeries(String seriesTitle, String isbn);

  List<BookSeries> getAllSeries();

  BookSeries findSeriesByTitle(String title);
}
