package com.zltel.broadcast.collection;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;

public class CollectionTest {

    public static final List<Integer> list = new ArrayList<>();

    static {
        IntStream.range(1, 100).forEach(list::add);
    }

    @Test
    public void test() {
        Thread t1 = new Thread(() -> {
            list.stream().filter(i -> {
                System.out.println(i);
                return i > 10;
            });
            System.out.println("迭代完成");
        });
        Thread t2 = new Thread(() -> {
            list.add(3);
            System.out.println("增加数据完成");
        });

        t1.start();
        t2.start();
    }

}
