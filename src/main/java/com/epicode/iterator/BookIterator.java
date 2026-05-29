package com.epicode.iterator;

import com.epicode.models.entities.Book;

/*
BookIterator provides a uniform way to traverse any BookComponent sequentially
We don't care about the underlying data structure.

Instead of managing manual loops or handling complex structural recursive logic, 
we just call .iterator() on any BookComponent, which return sither a SingleBookIterator or a CollectionBookIterator

BookComponent mainCatalog;
BookIterator iterator = mainCatalog.iterator();

while (iterator.hasNext()) {
    Book currentBook = iterator.next();
}
    
iterator.reset();

*/
public interface BookIterator {
    boolean hasNext();
    Book next();
    void reset();
}
