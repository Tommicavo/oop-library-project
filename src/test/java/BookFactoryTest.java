import com.epicode.factory.BookFactory;
import com.epicode.models.entities.AudioBook;
import com.epicode.models.entities.Book;
import com.epicode.models.entities.PhisicalBook;
import com.epicode.models.enums.BookType;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookFactoryTest {
    @Test
    public void createBook_physicalBook_returnsPhisicalBookInstance() {
        Book book = BookFactory.createBook(BookType.PHYSICAL_BOOK, 1L, "Title", "Author", 2020, 200, true);

        assertNotNull(book);
        assertTrue(book instanceof PhisicalBook);
        assertEquals(BookType.PHYSICAL_BOOK, book.getBookType());
        assertEquals("Title", book.getTitle());
        assertEquals(Integer.valueOf(200), ((PhisicalBook) book).getPages());
        assertTrue(((PhisicalBook) book).isHardCover());
    }

    @Test
    public void createBook_audioBook_returnsAudioBookInstance() {
        Book book = BookFactory.createBook(BookType.AUDIO_BOOK, 2L, "AudioTitle", "AuthorB", 2021, 120);

        assertNotNull(book);
        assertTrue(book instanceof AudioBook);
        assertEquals(BookType.AUDIO_BOOK, book.getBookType());
        assertEquals(Integer.valueOf(120), ((AudioBook) book).getNarrationMinutes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createBook_blankTitle_throwsIllegalArgument() {
        BookFactory.createBook(BookType.PHYSICAL_BOOK, 1L, "  ", "Author", 2020, 100, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createBook_invalidPages_throwsIllegalArgument() {
        BookFactory.createBook(BookType.PHYSICAL_BOOK, 1L, "Title", "Author", 2020, 0, false); // pages < 1
    }
}
