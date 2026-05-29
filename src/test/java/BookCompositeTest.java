import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.NoSuchElementException;
import org.junit.Test;
import com.epicode.factory.BookFactory;
import com.epicode.iterator.BookIterator;
import com.epicode.models.composite.BookCollection;
import com.epicode.models.entities.Book;
import com.epicode.models.enums.BookType;

public class BookCompositeTest {
    @Test
    public void composite_getBookCount_recursiveCountIsCorrect() {
        BookCollection root  = new BookCollection("Root");
        BookCollection inner = new BookCollection("Inner");

        Book b1 = BookFactory.createBook(BookType.PHYSICAL_BOOK, 1L, "B1", "A", 2000, 100, false);
        Book b2 = BookFactory.createBook(BookType.AUDIO_BOOK,    2L, "B2", "B", 2001, 60);
        Book b3 = BookFactory.createBook(BookType.PHYSICAL_BOOK, 3L, "B3", "C", 2002, 200, true);

        inner.add(b1);
        inner.add(b2);
        root.add(inner);
        root.add(b3);

        assertEquals(3, root.getBookCount());
        assertEquals(2, inner.getBookCount());
        assertEquals(1, b3.getBookCount());
    }

    @Test
    public void composite_isComposite_distinguishesLeafFromCollection() {
        BookCollection collection = new BookCollection("C");
        Book leaf = BookFactory.createBook(BookType.AUDIO_BOOK, 1L, "Leaf", "Author", 2020, 90);

        assertTrue(collection.isComposite());
        assertFalse(leaf.isComposite());
    }

    @Test
    public void composite_remove_reducesCount() {
        BookCollection col = new BookCollection("Col");
        Book b1 = BookFactory.createBook(BookType.PHYSICAL_BOOK, 1L, "T1", "A", 2010, 100, false);
        Book b2 = BookFactory.createBook(BookType.PHYSICAL_BOOK, 2L, "T2", "B", 2011, 150, true);

        col.add(b1);
        col.add(b2);
        assertEquals(2, col.getBookCount());

        col.remove(b1);
        assertEquals(1, col.getBookCount());
    }

    // ---- Iterator Pattern ----

    @Test
    public void singleBookIterator_hasNextThenNext_worksCorrectly() {
        Book book = BookFactory.createBook(BookType.PHYSICAL_BOOK, 1L, "Solo", "Author", 2020, 100, false);
        BookIterator it = book.iterator();

        assertTrue(it.hasNext());
        assertEquals(book, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void singleBookIterator_reset_allowsReIteration() {
        Book book = BookFactory.createBook(BookType.AUDIO_BOOK, 1L, "Solo", "Author", 2020, 90);
        BookIterator it = book.iterator();

        it.next();
        assertFalse(it.hasNext());
        it.reset();
        assertTrue(it.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void singleBookIterator_nextWhenExhausted_throwsNoSuchElement() {
        Book book = BookFactory.createBook(BookType.AUDIO_BOOK, 1L, "Solo", "Author", 2020, 90);
        BookIterator it = book.iterator();
        it.next();
        it.next(); // should throw
    }

    @Test
    public void collectionBookIterator_traversesAllBooksDepthFirst() {
        BookCollection root  = new BookCollection("Root");
        BookCollection inner = new BookCollection("Inner");

        Book b1 = BookFactory.createBook(BookType.PHYSICAL_BOOK, 1L, "A", "Auth", 2000, 100, false);
        Book b2 = BookFactory.createBook(BookType.AUDIO_BOOK,    2L, "B", "Auth", 2001, 60);
        Book b3 = BookFactory.createBook(BookType.PHYSICAL_BOOK, 3L, "C", "Auth", 2002, 200, true);

        inner.add(b1);
        inner.add(b2);
        root.add(inner);
        root.add(b3);

        BookIterator it = root.iterator();
        int count = 0;
        while (it.hasNext()) {
            it.next();
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    public void collectionBookIterator_reset_allowsReIteration() {
        BookCollection col = new BookCollection("Col");
        Book b = BookFactory.createBook(BookType.PHYSICAL_BOOK, 1L, "T", "A", 2020, 100, false);
        col.add(b);

        BookIterator it = col.iterator();
        it.next();
        assertFalse(it.hasNext());

        it.reset();
        assertTrue(it.hasNext());
        assertEquals(b, it.next());
    }
}
