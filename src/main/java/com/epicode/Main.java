package com.epicode;

import java.util.List;
import com.epicode.models.repositories.BookRepository;
import com.epicode.services.BookService;
import com.epicode.services.ImportService;

public class Main {
public static void main(String[] args) {
        BookRepository repo = new BookRepository();
        BookService service = new BookService(repo);
        ImportService importer = new ImportService(service);

        List<String> mockJsonFileLines = List.of(
            "{\"type\":\"PHYSICAL_BOOK\",\"id\":\"1\",\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"year\":\"2008\",\"pages\":\"464\",\"isHardCover\":\"true\"}",
            "{\"type\":\"AUDIO_BOOK\",\"id\":\"2\",\"title\":\"The Hobbit\",\"author\":\"J.R.R. Tolkien\",\"year\":\"1937\",\"duration\":\"660\"}",
            "{\"type\":\"PHYSICAL_BOOK\",\"id\":\"3\",\"title\":\"Design Patterns\",\"author\":\"GoF\",\"year\":\"1994\",\"pages\":\"540\",\"isHardCover\":\"false\"}",
            "{\"type\":\"AUDIO_BOOK\",\"id\":\"4\",\"title\":\"Dune\",\"author\":\"Frank Herbert\",\"year\":\"1965\",\"duration\":\"1200\"}"
        );

        importer.concurrentJsonImport(mockJsonFileLines);

        System.out.println("\nFinal Database Items Inserted Total: " + service.getAllBooks().size());
    }
}
