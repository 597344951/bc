package com.zltel.broadcast.collectors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.Test;

public class CollectorsTest {


    @Test
    public void test() {
        List<Book> books = Arrays.asList(new Book(12, "Sun Java"), new Book(12, "Oracle Java"), new Book(15, "Scala"));

        List<Book> unique = books.stream().collect(Collectors.collectingAndThen(
                // 将Set集合 转换为List集合
                Collectors.toCollection(// 收集指定集合
                        () -> new TreeSet<>(Comparator.comparing(o -> o.id))),
                ArrayList::new));

        unique.forEach(book -> System.out.printf("book[id: %s, name: %s]\n", book.id, book.name));

        TreeSet<Book> tsb = books.stream().collect(Collectors.toCollection(() -> new TreeSet<>(new Comparator<Book>() {

            @Override
            public int compare(Book o1, Book o2) {
                return o1.id.compareTo(o2.id);
            }

        }

        )));
        tsb.forEach(book -> System.out.printf("book[id: %s, name: %s]\n", book.id, book.name));

        TreeSet<Book> tsb2 = books.stream().collect(Collectors.toCollection(() -> new TreeSet<>((o1, o2) -> {
            return o1.id.compareTo(o2.id);
        })));
        tsb2.forEach(book -> System.out.printf("book[id: %s, name: %s]\n", book.id, book.name));
    }

    class Book {
        public final Integer id;
        public final String name;

        public Book(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
