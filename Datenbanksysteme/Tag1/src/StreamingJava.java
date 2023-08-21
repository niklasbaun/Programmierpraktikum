import java.io.BufferedReader;
import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamingJava {
    // Aufgabe 2) a)
    /**
     * method to convert a list of lists to a stream
     * @param list of List<E>
     * @return stream
     * @param <E> type of the list
     */
    public static <E> Stream<E> flatStreamOf(List<List<E>> list) {
        return list.stream().flatMap(List::stream);
    }

    // Aufgabe 2) b)
    /**
     * method to convert a stream of streams to a stream
     * @param stream of streams
     * @return a stream
     * @param <E> type of the stream
     */
    public static <E> Stream<E> mergeStreamsOf(Stream<Stream<E>> stream) {
        return stream.reduce(Stream::concat).orElse(null);
    }

    // Aufgabe 2) c)

    /**
     * method to find the minimum of a list,throw exception if no min found
     * @param list list to search through
     * @param <E>  type of the list
     * @return the minimum value
     */
    public static <E extends Comparable<? super E>> E minOf(List<List<E>> list) throws Exception {
        return flatStreamOf(list).min(Comparator.naturalOrder()).orElseThrow(NoSuchElementException::new);
    }

    // Aufgabe 2) d)
    /**
     * method to find the last element with a given predicate
     * @param stream the stream to work with
     * @param predicate what to search for
     * @return the last element
     * @param <E> the type of the stream
     */
    public static <E> E lastWithOf(Stream<E> stream, Predicate<? super E> predicate) {
        return stream.filter(predicate).reduce((first, second) -> second).orElse(null);
    }

    // Aufgabe 2) e)

    /**
     * method to find elements which occur exactly count times
     * @param stream the stream to work with
     * @param count the number of occurrences
     * @return a set of elements
     * @param <E> the type of the stream
     */
    public static <E> Set<E> findOfCount(Stream<E> stream, int count) {

        return stream.collect(Collectors.groupingBy(e -> e, Collectors.counting())).entrySet().stream()
                .filter(e -> e.getValue() == count).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    // Aufgabe 2) f)

    /**
     * method to transform a string array into an int stream
     * @param strings the string array
     * @return an int stream
     */
    public static IntStream makeStreamOf(String[] strings) {
        return Arrays.stream(strings).mapToInt(Integer::parseInt);
    }

//-------------------------------------------------------------------------------------------------

    // Aufgabe 3) a)

    /**
     * method to read a file line by line
     * @param path the path to the file
     * @return a stream of strings(lines)
     */
    public static Stream<String> fileLines(String path) {
        try {
            //implement reader
            Reader reader = Files.newBufferedReader(new File(path).toPath());
            BufferedReader bufferedReader = new BufferedReader(reader);
            //
            return bufferedReader.lines();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Aufgabe 3) b)
    public static double averageCost(Stream<String> lines) {
        // TODO

        return 0d;
    }

    // Aufgabe 3) c)
    public static long countCleanEnergyLevy(Stream<String> stream) {
        // TODO

        return 0L;
    }

    // Aufgabe 3) d)
    // TODO:
    //  1. Create record "NaturalGasBilling".
    //  2. Implement static method: "Stream<NaturalGasBilling> orderByInvoiceDateDesc(Stream<String> stream)".

    // Aufgabe 3) e)
    // TODO: Implement object method: "Stream<Byte> toBytes()" for record "NaturalGasBilling".

    // Aufgabe 3) f)
    // TODO: Implement static method: "Stream<Byte> serialize(Stream<NaturalGasBilling> stream)".

    // Aufgabe 3) g)
    // TODO: Implement static method: "Stream<NaturalGasBilling> deserialize(Stream<Byte> stream)".
    // TODO: Execute the call: "deserialize(serialize(orderByInvoiceDateDesc(fileLines(Datei aus f))))"
    // TODO: in a main Method and print the output to the console.

    // Aufgabe 3) h)
    public static Stream<File> findFilesWith(String dir, String startsWith, String endsWith, int maxFiles) {
        // TODO

        return null;
    }
}
