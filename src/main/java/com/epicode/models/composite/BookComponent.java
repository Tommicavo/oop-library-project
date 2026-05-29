package com.epicode.models.composite;

import com.epicode.iterator.BookIterator;

/*
Book is a leaf -> it can not have children -> single component
BookCollection is a composite -> it can have children -> it can contain both Book and BookCollection

We can add both a single leaf and a composite to  composite:

BookCollection generalCollection = new BookCollection("General Collection");

BookCollection specificCollection = new BookCollection("Specific Collection");
BookComponent specific1 = BookFactory.createBook(type, 1L, "title", "author", 2026, 150, true);
specificCollection.add(specific1);

BookComponent specific2 = BookFactory.createBook(type, 2L, "title", "author", 2026, 180);
specificCollection.add(specific2);

BookComponent leaf = BookFactory.createBook(type, 3L, "title", "author", 2026, 180);

generalCollection.add(specificCollection);
generalCollection.add(leaf);
*/
public interface BookComponent {
    
    String printInfo();
    int getBookCount();
    boolean isComposite();
    BookIterator iterator();
}
