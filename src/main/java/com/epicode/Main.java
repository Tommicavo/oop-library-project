package com.epicode;

import com.epicode.exceptions.BookNotFoundException;
import com.epicode.exceptions.IllegalBookTypeException;
import com.epicode.factory.BookFactory;
import com.epicode.iterator.BookIterator;
import com.epicode.models.composite.BookCollection;
import com.epicode.models.entities.AudioBook;
import com.epicode.models.entities.Book;
import com.epicode.models.entities.PhisicalBook;
import com.epicode.models.enums.BookType;
import com.epicode.models.repositories.BookRepository;
import com.epicode.services.BookService;
import com.epicode.services.ImportService;
import com.epicode.utils.AppLogger;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        AppLogger.info("========== LIBRARY APP - FULL DEMO ==========");

        // ----------------------------------------------------------------
        // 1. Factory Pattern — BookFactory creates concrete subclasses
        // ----------------------------------------------------------------
        AppLogger.info("\n--- [1] Factory Pattern ---");

        Book physicalBook1 = BookFactory.createBook(BookType.PHYSICAL_BOOK, 1L, "Clean Code", "Robert C. Martin", 2008, 464, true);
        Book physicalBook2 = BookFactory.createBook(BookType.PHYSICAL_BOOK, 2L, "Design Patterns", "Gang of Four", 1994, 395, false);
        Book audioBook1    = BookFactory.createBook(BookType.AUDIO_BOOK,    3L, "The Pragmatic Programmer", "David Thomas", 1999, 360);
        Book audioBook2    = BookFactory.createBook(BookType.AUDIO_BOOK,    4L, "Refactoring", "Martin Fowler", 2018, 448);

        AppLogger.success("Created 4 books via BookFactory");
        System.out.println(physicalBook1.printInfo());
        System.out.println(audioBook1.printInfo());

        // ----------------------------------------------------------------
        // 2. OOP Polymorphism — printInfo() / getBookType() dispatched at runtime
        // ----------------------------------------------------------------
        AppLogger.info("\n--- [2] OOP Polymorphism ---");

        List<Book> mixedBooks = List.of(physicalBook1, physicalBook2, audioBook1, audioBook2);
        for (Book b : mixedBooks) {
            AppLogger.info("Type=" + b.getBookType() + " | Title=" + b.getTitle());
        }

        // ----------------------------------------------------------------
        // 3. OOP Inheritance — cast to concrete subclasses
        // ----------------------------------------------------------------
        AppLogger.info("\n--- [3] OOP Inheritance ---");

        PhisicalBook pb = (PhisicalBook) physicalBook1;
        AudioBook    ab = (AudioBook)    audioBook1;
        AppLogger.info("PhisicalBook pages=" + pb.getPages() + " hardcover=" + pb.isHardCover());
        AppLogger.info("AudioBook narration=" + ab.getNarrationMinutes() + " min");

        // ----------------------------------------------------------------
        // 4. Custom Exceptions
        // ----------------------------------------------------------------
        AppLogger.info("\n--- [4] Custom Exceptions ---");

        // IllegalBookTypeException
        try {
            throw new IllegalBookTypeException("Simulated unknown type: E_BOOK");
        } catch (IllegalBookTypeException e) {
            AppLogger.warn("Caught IllegalBookTypeException: " + e.getMessage());
        }

        // BookNotFoundException
        try {
            throw new BookNotFoundException("Book with ID 999 does not exist");
        } catch (BookNotFoundException e) {
            AppLogger.warn("Caught BookNotFoundException: " + e.getMessage());
        }

        // InputValidation via constructor
        try {
            new PhisicalBook(1L, "", "Author", 2020, 100, false);
        } catch (IllegalArgumentException e) {
            AppLogger.warn("Caught validation error: " + e.getMessage());
        }

        // ----------------------------------------------------------------
        // 5. Repository CRUD (BookRepository)
        // ----------------------------------------------------------------
        AppLogger.info("\n--- [5] BookRepository CRUD ---");

        BookRepository repo = new BookRepository();
        repo.save(physicalBook1);
        repo.save(physicalBook2);
        repo.save(audioBook1);
        repo.save(audioBook2);

        AppLogger.info("All books: " + repo.findAll().size());
        AppLogger.info("Found by ID 2: " + repo.findById(2L).getTitle());

        Book updated = BookFactory.createBook(BookType.PHYSICAL_BOOK, 2L, "Design Patterns (2nd Ed.)", "Gang of Four", 2024, 400, true);
        repo.update(updated, 2L);
        AppLogger.info("After update, ID 2 title: " + repo.findById(2L).getTitle());

        AppLogger.info("By author 'Martin Fowler': " + repo.findByAuthor("Martin Fowler").size());
        AppLogger.info("By title keyword 'code': " + repo.findByTitle("code").size());
        AppLogger.info("By type AUDIO_BOOK: " + repo.findByBookType(BookType.AUDIO_BOOK).size());

        repo.deleteById(4L);
        AppLogger.info("After deleteById(4), total: " + repo.findAll().size());

        // ----------------------------------------------------------------
        // 6. Exception Shielding (BookService)
        // ----------------------------------------------------------------
        AppLogger.info("\n--- [6] Exception Shielding (BookService) ---");

        BookService service = new BookService(repo);

        try {
            service.getBookById(999L);
        } catch (BookNotFoundException e) {
            AppLogger.warn("Service re-propagated BookNotFoundException: " + e.getMessage());
        }

        Book newBook = BookFactory.createBook(BookType.AUDIO_BOOK, 5L, "Domain-Driven Design", "Eric Evans", 2003, 560);
        service.createBook(newBook);
        AppLogger.success("Service saved book ID 5");

        service.removeBookById(5L);
        AppLogger.info("After service remove(5), total: " + service.getAllBooks().size());

        AppLogger.info("searchByAuthor: " + service.searchByAuthor("Robert C. Martin").size());
        AppLogger.info("searchByTitle 'patterns': " + service.searchByTitle("patterns").size());
        AppLogger.info("searchByBookType PHYSICAL: " + service.searchByBookType(BookType.PHYSICAL_BOOK).size());

        // ----------------------------------------------------------------
        // 7. Composite Pattern (BookCollection tree)
        // ----------------------------------------------------------------
        AppLogger.info("\n--- [7] Composite Pattern ---");

        BookCollection rootCatalog  = new BookCollection("Root Catalog");
        BookCollection techSection  = new BookCollection("Tech Section");
        BookCollection agileSection = new BookCollection("Agile Section");

        Book leaf1 = BookFactory.createBook(BookType.PHYSICAL_BOOK, 10L, "The Clean Coder", "Robert C. Martin", 2011, 256, true);
        Book leaf2 = BookFactory.createBook(BookType.AUDIO_BOOK,    11L, "Extreme Programming Explained", "Kent Beck", 1999, 224);
        Book leaf3 = BookFactory.createBook(BookType.PHYSICAL_BOOK, 12L, "Working Effectively with Legacy Code", "Michael Feathers", 2004, 456, false);

        techSection.add(leaf1);
        techSection.add(leaf2);
        agileSection.add(leaf3);
        rootCatalog.add(techSection);
        rootCatalog.add(agileSection);
        rootCatalog.add(physicalBook1); 

        AppLogger.info("Root catalog total (recursive): " + rootCatalog.getBookCount());
        AppLogger.info("Tech section count: " + techSection.getBookCount());
        AppLogger.info("isComposite(collection): " + rootCatalog.isComposite());
        AppLogger.info("isComposite(leaf): " + leaf1.isComposite());
        System.out.println(rootCatalog.printInfo());

        // ----------------------------------------------------------------
        // 8. Iterator Pattern — Single & Collection iterators
        // ----------------------------------------------------------------
        AppLogger.info("\n--- [8] Iterator Pattern ---");

        // SingleBookIterator
        BookIterator singleIt = leaf1.iterator();
        AppLogger.info("SingleBookIterator hasNext: " + singleIt.hasNext());
        AppLogger.info("  -> " + singleIt.next().getTitle());
        AppLogger.info("After next() hasNext: " + singleIt.hasNext());
        singleIt.reset();
        AppLogger.info("After reset() hasNext: " + singleIt.hasNext());

        // CollectionBookIterator (depth-first)
        AppLogger.info("CollectionBookIterator depth-first traversal:");
        BookIterator collIt = rootCatalog.iterator();
        int idx = 0;
        while (collIt.hasNext()) {
            AppLogger.info("  [" + (++idx) + "] " + collIt.next().getTitle());
        }
        collIt.reset();
        AppLogger.info("After reset, first again: " + collIt.next().getTitle());

        // ----------------------------------------------------------------
        // 9. ImportService — concurrent JSON import
        // ----------------------------------------------------------------
        AppLogger.info("\n--- [9] ImportService (concurrent JSON import) ---");

        BookRepository importRepo    = new BookRepository();
        BookService    importSvc     = new BookService(importRepo);
        ImportService  importService = new ImportService(importSvc);

        List<String> jsonRecords = Arrays.asList(
            "{\"type\":\"PHYSICAL_BOOK\",\"id\":\"100\",\"title\":\"Effective Java\",\"author\":\"Joshua Bloch\",\"year\":\"2018\",\"pages\":\"412\",\"isHardCover\":\"false\"}",
            "{\"type\":\"PHYSICAL_BOOK\",\"id\":\"101\",\"title\":\"Java Concurrency in Practice\",\"author\":\"Brian Goetz\",\"year\":\"2006\",\"pages\":\"403\",\"isHardCover\":\"true\"}",
            "{\"type\":\"AUDIO_BOOK\",\"id\":\"102\",\"title\":\"Head First Design Patterns\",\"author\":\"Eric Freeman\",\"year\":\"2004\",\"duration\":\"300\"}"
        );

        importService.concurrentJsonImport(jsonRecords);
        AppLogger.info("Books imported concurrently: " + importRepo.findAll().size());

        // edge case: empty list
        importService.concurrentJsonImport(List.of());

        // ----------------------------------------------------------------
        // 10. AppLogger — all four log levels
        // ----------------------------------------------------------------
        AppLogger.info("\n--- [10] AppLogger (CustomLogger) ---");
        AppLogger.info("INFO level message");
        AppLogger.success("SUCCESS level message");
        AppLogger.warn("WARN level message");
        AppLogger.error("ERROR level message");

        // ----------------------------------------------------------------
        // 11. deleteAll via service
        // ----------------------------------------------------------------
        AppLogger.info("\n--- [11] removeAllBooks ---");
        service.removeAllBooks();
        AppLogger.info("After removeAllBooks, total: " + service.getAllBooks().size());

        AppLogger.success("========== DEMO COMPLETE ==========");
    }
}
