package com.epicode.models.composite;

import java.util.ArrayList;
import java.util.List;

public class BookCollection implements BookComponent {
    
    private String collectionName;
    private List<BookComponent> children = new ArrayList<>();

    public BookCollection(String collectionName) {
        this.collectionName = collectionName;
    }

    public void add(BookComponent component) {
        children.add(component);
    }

    public void remove(BookComponent component) {
        children.remove(component);
    }

    @Override
    public int getBookCount() {
        int total = 0;
        for (BookComponent child : children) {
            total += child.getBookCount(); 
        }
        return total;
    }

    @Override
    public String printInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("COLLECTION: ").append(collectionName).append("\n");
        for (BookComponent child : children) {
            sb.append(child.printInfo()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean isComposite() {
        return true;
    }
}
