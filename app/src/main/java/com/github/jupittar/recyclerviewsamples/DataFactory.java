package com.github.jupittar.recyclerviewsamples;


import com.github.javafaker.Faker;
import com.github.jupittar.recyclerviewsamples.entity.Book;
import com.github.jupittar.recyclerviewsamples.entity.BookHolder;

import java.util.ArrayList;
import java.util.List;

public class DataFactory {
    private static Faker sFaker = new Faker();

    public static List<BookHolder> generateBooks(int count) {
        List<BookHolder> books = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            final Book book = new Book();
            book.title = sFaker.book().title();
            book.author = sFaker.book().author();
            books.add(new BookHolder(book, 0));
        }
        return books;
    }
}
