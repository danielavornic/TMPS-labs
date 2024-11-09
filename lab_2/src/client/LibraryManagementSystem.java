package client;

import service.LibraryFacade;
import service.interfaces.ILibraryFacade;
import ui.ConsoleUI;

public class LibraryManagementSystem {
  public static void main(String[] args) {
    ILibraryFacade libraryFacade = new LibraryFacade();

    LibraryInitializer initializer = new LibraryInitializer(libraryFacade);
    initializer.initializeWithSampleData();

    ConsoleUI ui = new ConsoleUI(libraryFacade);
    ui.start();
  }
}
