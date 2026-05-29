package com.epicode.factory;

import com.epicode.exceptions.IllegalBookTypeException;
import com.epicode.models.entities.AudioBook;
import com.epicode.models.entities.Book;
import com.epicode.models.entities.PhisicalBook;
import com.epicode.models.enums.BookType;

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
