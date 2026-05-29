package com.epicode.models.repositories;

import com.epicode.exceptions.BookNotFoundException;
import com.epicode.models.entities.Book;
import com.epicode.models.enums.BookType;
import com.epicode.utils.AppLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap; 
import java.util.stream.Collectors;

public class BookRepository implements AppRepository<Book> {

    private final Map<Long, Book> bookData = new ConcurrentHashMap<>();

    @Override
    public List<Book> findAll() {
        AppLogger.info("Fetching all books from the thread-safe repository");
        return new ArrayList<>(bookData.values());
    }

    @Override
    public Book findById(Long id) {
        AppLogger.info("Searching for book with ID: " + id);
        Book book = bookData.get(id);
        
        if (book == null) {
            String errorMsg = "Book with ID " + id + " does not exist";
            AppLogger.error(errorMsg);
            throw new BookNotFoundException(errorMsg);
        }
        
        AppLogger.success("Found book: '" + book.getTitle() + "'");
        return book;
    }

    @Override
    public Book save(Book entity) {
        if (entity == null) {
            AppLogger.error("Cannot save a null book entity");
            throw new IllegalArgumentException("Entity cannot be null");
        }
        
        if (bookData.containsKey(entity.getId())) {
            AppLogger.error("Save rejected: A book with ID " + entity.getId() + " already exists");
            throw new IllegalArgumentException("Book with ID " + entity.getId() + " already exists");
        }

        bookData.put(entity.getId(), entity);
        AppLogger.success("Successfully saved new book: '" + entity.getTitle() + "' [ID: " + entity.getId() + "] on thread: " + Thread.currentThread().getName());
        return entity;
    }

    @Override
    public Book update(Book entity, Long id) {
        if (entity == null || id == null) {
            AppLogger.error("Update failed: Entity or ID is null");
            throw new IllegalArgumentException("Entity and ID cannot be null");
        }

        if (!bookData.containsKey(id)) {
            String errorMsg = "Update failed: Cannot update. Book with ID " + id + " does not exist";
            AppLogger.error(errorMsg);
            throw new BookNotFoundException(errorMsg);
        }

        entity.setId(id);
        bookData.put(id, entity);
        AppLogger.success("Successfully updated book ID " + id + ": '" + entity.getTitle() + "'");
        return entity;
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) {
            AppLogger.error("Delete failed: Provided ID is null");
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!bookData.containsKey(id)) {
            String errorMsg = "Delete failed: Cannot delete. Book with ID " + id + " does not exist";
            AppLogger.error(errorMsg);
            throw new BookNotFoundException(errorMsg);
        }

        Book removedBook = bookData.remove(id);
        AppLogger.success("Successfully deleted book: '" + removedBook.getTitle() + "' [ID: " + id + "]");
        return true;
    }

    @Override
    public boolean deleteAll() {
        int recordCount = bookData.size();
        AppLogger.warn("Executing structural wipe: Deleting all " + recordCount + " books...");
        bookData.clear();
        AppLogger.success("Repository cleared successfully");
        return true;
    }

    public List<Book> findByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            AppLogger.warn("findByAuthor called with an empty author name");
            return List.of();
        }
        
        AppLogger.info("Filtering catalog for author: '" + author + "'");
        
        return bookData.values().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author.trim()))
                .collect(Collectors.toList());
    }

    public List<Book> findByTitle(String titleKeyword) {
        if (titleKeyword == null || titleKeyword.trim().isEmpty()) {
            AppLogger.warn("findByTitle called with an empty keyword");
            return List.of();
        }

        String keyword = titleKeyword.toLowerCase().trim();
        AppLogger.info("Searching titles containing keyword: '" + keyword + "'");

        return bookData.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
    }

    public List<Book> findByBookType(BookType type) {
        if (type == null) {
            AppLogger.error("findByBookType called with a null type");
            throw new IllegalArgumentException("BookType cannot be null");
        }

        AppLogger.info("Filtering catalog for format type: " + type);

        return bookData.values().stream()
                .filter(book -> book.getBookType() == type)
                .collect(Collectors.toList());
    }
}
