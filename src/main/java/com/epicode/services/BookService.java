package com.epicode.services;

import com.epicode.exceptions.BookNotFoundException;
import com.epicode.models.entities.Book;
import com.epicode.models.enums.BookType;
import com.epicode.models.repositories.BookRepository;
import com.epicode.utils.AppLogger;
import java.util.List;

public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository dependency cannot be null");
        }
        this.repository = repository;
    }

    public List<Book> getAllBooks() {
        try {
            return repository.findAll();
        } catch (Throwable systemError) {
            AppLogger.error("Unexpected failure fetching master collection sequence: " + systemError.getMessage());
            return List.of();
        }
    }

    public Book getBookById(Long id) {
        try {
            return repository.findById(id);
        } catch (BookNotFoundException e) {
            throw e; 
        } catch (Throwable systemError) {
            AppLogger.error("[CRITICAL SHIELD LOG] Internal system anomaly on findById: " + systemError.toString());
            throw new RuntimeException("An internal processing error occurred while retrieving the requested data record.");
        }
    }

    public Book createBook(Book book) {
        try {
            return repository.save(book);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Throwable systemError) {
            AppLogger.error("[CRITICAL SHIELD LOG] Database save routine failure: " + systemError.toString());
            throw new RuntimeException("Unable to permanently store the new book record at this time.");
        }
    }

    public Book modifyBook(Book book, Long id) {
        try {
            return repository.update(book, id);
        } catch (BookNotFoundException | IllegalArgumentException e) {
            throw e;
        } catch (Throwable systemError) {
            AppLogger.error("[CRITICAL SHIELD LOG] Update pipeline routine failure: " + systemError.toString());
            throw new RuntimeException("The book record update request could not be processed due to a system anomaly.");
        }
    }

    public boolean removeBookById(Long id) {
        try {
            return repository.deleteById(id);
        } catch (BookNotFoundException | IllegalArgumentException e) {
            throw e; 
        } catch (Throwable systemError) {
            AppLogger.error("[CRITICAL SHIELD LOG] Delete execution routine failure: " + systemError.toString());
            throw new RuntimeException("The book erasure operation was terminated by safety protocols.");
        }
    }

    public boolean removeAllBooks() {
        try {
            return repository.deleteAll();
        } catch (Throwable systemError) {
            AppLogger.error("[CRITICAL SHIELD LOG] Structural wipe failure: " + systemError.toString());
            throw new RuntimeException("Mass structural purge was aborted due to an internal system error.");
        }
    }

    public List<Book> searchByAuthor(String author) {
        try {
            return repository.findByAuthor(author);
        } catch (Throwable systemError) {
            AppLogger.error("Query pipeline failed for author search: " + systemError.getMessage());
            return List.of();
        }
    }

    public List<Book> searchByTitle(String titleKeyword) {
        try {
            return repository.findByTitle(titleKeyword);
        } catch (Throwable systemError) {
            AppLogger.error("Query pipeline failed for title search: " + systemError.getMessage());
            return List.of();
        }
    }

    public List<Book> searchByBookType(BookType type) {
        try {
            return repository.findByBookType(type);
        } catch (IllegalArgumentException e) {
            throw e; 
        } catch (Throwable systemError) {
            AppLogger.error("Query pipeline failed for type filtering: " + systemError.getMessage());
            return List.of();
        }
    }
}
