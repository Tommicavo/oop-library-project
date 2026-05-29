package com.epicode.services;

import com.epicode.factory.BookFactory;
import com.epicode.models.entities.Book;
import com.epicode.models.enums.BookType;
import com.epicode.utils.AppLogger;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ImportService {

    private final BookService bookService;

    public ImportService(BookService bookService) {
        this.bookService = bookService;
    }

    public void concurrentJsonImport(List<String> rawJsonRecords) {
        if (rawJsonRecords == null || rawJsonRecords.isEmpty()) {
            AppLogger.warn("Import skipped: JSON dataset array is empty");
            return;
        }

        AppLogger.info("Launching Concurrent Data Ingestion Engine using Thread Pools...");
        
        int availableCores = Runtime.getRuntime().availableProcessors();
        ExecutorService threadPool = Executors.newFixedThreadPool(availableCores);

        long startTime = System.currentTimeMillis();

        for (String jsonString : rawJsonRecords) {
            threadPool.submit(() -> {
                try {
                    Book importedBook = parseJsonRecord(jsonString);
                    bookService.createBook(importedBook);
                } catch (Exception e) {
                    AppLogger.error("Worker thread failed to ingest record line: " + e.getMessage());
                }
            });
        }

        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ie) {
            AppLogger.error("Bulk migration thread structure was interrupted");
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }

        long totalTime = System.currentTimeMillis() - startTime;
        AppLogger.success("Multi-threaded catalog ingestion completed successfully in " + totalTime + " ms");
    }

    private Book parseJsonRecord(String json) {
        String clean = json.replace("{", "").replace("}", "").replace("\"", "").trim();
        String[] pairs = clean.split(",");

        String typeStr = "";
        Long id = 0L;
        String title = "";
        String author = "";
        Integer year = 0;
        String param1 = "";
        String param2 = "";

        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length < 2) continue;
            
            String key = keyValue[0].trim();
            String val = keyValue[1].trim();

            switch (key) {
                case "type": typeStr = val; break;
                case "id": id = Long.parseLong(val); break;
                case "title": title = val; break;
                case "author": author = val; break;
                case "year": year = Integer.parseInt(val); break;
                case "pages": case "duration": param1 = val; break;
                case "isHardCover": param2 = val; break;
            }
        }

        BookType type = BookType.valueOf(typeStr.toUpperCase());
        
        if (type == BookType.PHYSICAL_BOOK) {
            return BookFactory.createBook(type, id, title, author, year, param1, param2);
        } else {
            return BookFactory.createBook(type, id, title, author, year, param1);
        }
    }
}
