package com.epicode.iterator;

import com.epicode.models.entities.Book;

public class SingleBookIterator implements BookIterator {

    private final Book book;
    private boolean hasBeenRead = false;

    public SingleBookIterator(Book book) {
        this.book = book;
    }

    @Override
    public boolean hasNext() {
        return !hasBeenRead && book != null;
    }

    @Override
    public Book next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException("No more books available in this single iterator");
        }
        hasBeenRead = true;
        return book;
    }

    @Override
    public void reset() {
        hasBeenRead = false;
    }
}
