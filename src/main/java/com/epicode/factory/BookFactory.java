package com.epicode.factory;

import com.epicode.exceptions.IllegalBookTypeException;
import com.epicode.models.entities.AudioBook;
import com.epicode.models.entities.Book;
import com.epicode.models.entities.PhisicalBook;
import com.epicode.models.enums.BookType;

/*
With BookFactory we can create Book without specifying the exact class
Instead of doing:
Book book1 = new PhisicalBook(1L, "title", "author", 2026, 150, true);
Book book2 = new AudioBook(2L, "title", "author", 2026, 180);

We can just use createBook method of BookFactory passing the BookType:
Book book1 = BookFactory.createBook(1L, "title", "author", 2026, 150, true);
Book book2 = BookFactory.createBook(2L, "title", "author", 2026, 180);
*/
public class BookFactory {
    
    public static Book createBook(BookType type, Long id, String title, String author, Integer publicationYear, Object... extraParams) {
        switch (type) {
            case PHYSICAL_BOOK:
                Integer pages = (extraParams[0] instanceof Integer) ? (Integer) extraParams[0] : Integer.parseInt(extraParams[0].toString());
                boolean isHardCover = (extraParams[1] instanceof Boolean) ? (Boolean) extraParams[1] : Boolean.parseBoolean(extraParams[1].toString());
                return new PhisicalBook(id, title, author, publicationYear, pages, isHardCover);
            case AUDIO_BOOK:
                Integer narrationMinutes = (extraParams[0] instanceof Integer) ? (Integer) extraParams[0] : Integer.parseInt(extraParams[0].toString());
                return new AudioBook(id, title, author, publicationYear, narrationMinutes);
            default:
                throw new IllegalBookTypeException(
                    "Unknown book type: '" + type + "''. Supported types:" +
                    "[" + BookType.PHYSICAL_BOOK + ", " + BookType.AUDIO_BOOK + "]"
                );
        }
    }
}
