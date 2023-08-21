import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class StreamingAufg2JavaTest {
//setup vars
    static List<Integer> list1;
    static List<Integer> list2;
    static List<Integer> list3;
    static List<Integer> list4;
    @BeforeAll
    static void setUp() {
        //create test data
        //lists
        list1 = new ArrayList<>();
        list1.addAll(List.of(1, 2, 3, 4, 5));
        list2 = new ArrayList<>();
        list2.addAll(List.of(6, 7, 8, 9, 10));
        list3 = new ArrayList<>();
        list3.addAll(List.of(11, 12, 13, 14, 15));
        list4 = new ArrayList<>();
        list4.addAll(List.of(16, 17, 18, 19, 20));


    }

    @org.junit.jupiter.api.Test
    void flatStreamOf() {
        List<List<Integer>> list = new ArrayList<>();
        list.addAll(List.of(list1, list2, list3, list4));
        List<Integer> result = StreamingJava.flatStreamOf(list).collect(Collectors.toList());
        List<Integer> expected = new ArrayList<>();
        expected.addAll(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15,16, 17, 18, 19, 20));
        assert(result.equals(expected));
    }

    @org.junit.jupiter.api.Test
    void mergeStreamsOf() {
        List<List<Integer>> list = new ArrayList<>();
        list.addAll(List.of(list1, list2, list3, list4));
        Stream<Stream<Integer>> stream = list.stream().map(List::stream);
        List<Integer> result = StreamingJava.mergeStreamsOf(stream).collect(Collectors.toList());
        List<Integer> expected = new ArrayList<>();
        expected.addAll(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15,16, 17, 18, 19, 20));
        assert(result.equals(expected));
    }

    @org.junit.jupiter.api.Test
    void minOf() throws Exception {
        List<List<Integer>> list = new ArrayList<>();
        list.addAll(List.of(list1, list2, list3, list4));
        Integer result = StreamingJava.minOf(list);
        Integer expected = 1;
        assert(result.equals(expected));
    }

    @org.junit.jupiter.api.Test
    void lastWithOf() {
        List<List<Integer>> list = new ArrayList<>();
        list.addAll(List.of(list1, list2, list3, list4));
        Stream<Integer> stream = StreamingJava.flatStreamOf(list);
        Integer result = StreamingJava.lastWithOf(stream, (x) -> x % 4 == 0);
        Integer expected = 20;
        assert(result.equals(expected));
    }

    @org.junit.jupiter.api.Test
    void findOfCount() {
        List<List<Integer>> list = new ArrayList<>();
        list.addAll(List.of(list1, list2, list3, list4));
        Stream<Integer> stream = StreamingJava.flatStreamOf(list);
        Set<Integer> result = StreamingJava.findOfCount(stream, 2);
        Set<Integer> expected = Set.of();
        assert(result.equals(expected));
    }

    @org.junit.jupiter.api.Test
    void makeStreamOf() {
    String[] s = {"ab", "b", "c", "d", "e"};
    IntStream result = StreamingJava.makeStreamOf(s);
    IntStream expected = IntStream.of(97, 98, 99, 100, 101);
    assert(result.equals(expected));



    }
}