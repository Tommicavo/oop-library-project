package com.epicode.iterator;

import com.epicode.models.composite.BookCollection;
import com.epicode.models.composite.BookComponent;
import com.epicode.models.entities.Book;
import java.util.Stack;
import java.util.Iterator;

public class CollectionBookIterator implements BookIterator {

    private final BookCollection rootCollection;
    private Stack<Iterator<BookComponent>> iteratorStack = new Stack<>();
    private Book nextBook = null;

    public CollectionBookIterator(BookCollection collection) {
        this.rootCollection = collection;
        reset(); 
    }

    @Override
    public boolean hasNext() {
        if (nextBook != null) {
            return true;
        }

        while (!iteratorStack.isEmpty()) {
            Iterator<BookComponent> currentIterator = iteratorStack.peek();

            if (currentIterator.hasNext()) {
                BookComponent component = currentIterator.next();

                if (component.isComposite()) {
                    BookCollection subCollection = (BookCollection) component;
                    iteratorStack.push(subCollection.getComponents().iterator());
                } else {
                    this.nextBook = (Book) component;
                    return true;
                }
            } else {
                iteratorStack.pop();
            }
        }

        return false;
    }

    @Override
    public Book next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException("No more elements in the book collection hierarchy.");
        }
        Book bookToReturn = nextBook;
        nextBook = null;
        return bookToReturn;
    }

    @Override
    public void reset() {
        iteratorStack.clear();
        nextBook = null;
        if (rootCollection != null) {
            iteratorStack.push(rootCollection.getComponents().iterator());
        }
    }
}
