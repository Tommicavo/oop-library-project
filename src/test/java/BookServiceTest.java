import com.epicode.exceptions.BookNotFoundException;
import com.epicode.factory.BookFactory;
import com.epicode.models.entities.Book;
import com.epicode.models.enums.BookType;
import com.epicode.models.repositories.BookRepository;
import com.epicode.services.BookService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    private BookRepository mockRepo;
    private BookService    bookService;
    private Book           sampleBook;

    @Before
    public void setUp() {
        mockRepo    = Mockito.mock(BookRepository.class);
        bookService = new BookService(mockRepo);
        sampleBook  = BookFactory.createBook(BookType.PHYSICAL_BOOK, 1L, "Clean Code", "Robert C. Martin", 2008, 464, true);
    }

    // --- constructor ---

    @Test(expected = IllegalArgumentException.class)
    public void constructor_nullRepository_throwsIllegalArgument() {
        new BookService(null);
    }

    // --- getAllBooks ---

    @Test
    public void getAllBooks_delegatesToRepo_returnsList() {
        when(mockRepo.findAll()).thenReturn(List.of(sampleBook));

        List<Book> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        verify(mockRepo, times(1)).findAll();
    }

    @Test
    public void getAllBooks_repoThrowsRuntimeException_returnsEmptyList() {
        when(mockRepo.findAll()).thenThrow(new RuntimeException("DB down"));

        List<Book> result = bookService.getAllBooks();

        assertTrue(result.isEmpty());
    }

    // --- getBookById ---

    @Test
    public void getBookById_existingId_returnsBook() {
        when(mockRepo.findById(1L)).thenReturn(sampleBook);

        Book result = bookService.getBookById(1L);

        assertEquals(sampleBook, result);
    }

    @Test(expected = BookNotFoundException.class)
    public void getBookById_missingId_propagatesBookNotFoundException() {
        when(mockRepo.findById(99L)).thenThrow(new BookNotFoundException("Not found"));

        bookService.getBookById(99L);
    }

    @Test(expected = RuntimeException.class)
    public void getBookById_unexpectedError_shieldsToRuntimeException() {
        when(mockRepo.findById(1L)).thenThrow(new Error("Unexpected JVM error"));

        bookService.getBookById(1L);
    }

    // --- createBook ---

    @Test
    public void createBook_validBook_savedAndReturned() {
        when(mockRepo.save(sampleBook)).thenReturn(sampleBook);

        Book result = bookService.createBook(sampleBook);

        assertEquals(sampleBook, result);
        verify(mockRepo).save(sampleBook);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createBook_duplicateId_propagatesIllegalArgument() {
        when(mockRepo.save(sampleBook)).thenThrow(new IllegalArgumentException("Duplicate ID"));

        bookService.createBook(sampleBook);
    }

    // --- removeBookById ---

    @Test
    public void removeBookById_existingId_returnsTrue() {
        when(mockRepo.deleteById(1L)).thenReturn(true);

        assertTrue(bookService.removeBookById(1L));
    }

    @Test(expected = BookNotFoundException.class)
    public void removeBookById_missingId_propagatesBookNotFoundException() {
        when(mockRepo.deleteById(99L)).thenThrow(new BookNotFoundException("Not found"));

        bookService.removeBookById(99L);
    }

    // --- searchByAuthor ---

    @Test
    public void searchByAuthor_matchFound_returnsList() {
        when(mockRepo.findByAuthor("Robert C. Martin")).thenReturn(List.of(sampleBook));

        List<Book> result = bookService.searchByAuthor("Robert C. Martin");

        assertEquals(1, result.size());
    }

    @Test
    public void searchByAuthor_repoThrows_returnsEmptyList() {
        when(mockRepo.findByAuthor(anyString())).thenThrow(new RuntimeException("error"));

        List<Book> result = bookService.searchByAuthor("anyone");

        assertTrue(result.isEmpty());
    }

    // --- searchByBookType ---

    @Test
    public void searchByBookType_validType_returnsList() {
        when(mockRepo.findByBookType(BookType.PHYSICAL_BOOK)).thenReturn(List.of(sampleBook));

        List<Book> result = bookService.searchByBookType(BookType.PHYSICAL_BOOK);

        assertEquals(1, result.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void searchByBookType_nullType_propagatesIllegalArgument() {
        when(mockRepo.findByBookType(null)).thenThrow(new IllegalArgumentException("null type"));

        bookService.searchByBookType(null);
    }
}
