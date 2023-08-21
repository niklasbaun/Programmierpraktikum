import java.io.BufferedReader;
import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            //open stream, when finished close stream and print "success
            return bufferedReader.lines().skip(1);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while reading file");
        }
        return null;
    }

    // Aufgabe 3) b)

    /**
     * method to calculate the average cost of the gas billing
     * cost is found in the 11th column
     * @param lines the stream of lines
     * @return the average cost
     */
    public static double averageCost(Stream<String> lines) {
        return lines.map(e -> e.split(",")).map(e -> e[12]).mapToDouble(Double::parseDouble)
                .average().orElse(-1.0);
    }

    // Aufgabe 3) c)

    /**
     * method to count how often clean energy levy is not null or 0
     * @param stream the stream of lines
     * @return the count of times
     */
    public static long countCleanEnergyLevy(Stream<String> stream) {
        return stream.map(e -> e.split(","))
                .filter(e -> !e[10].equals("0") || !e[10].equals("")).count();
    }

    // Aufgabe 3) d)
    record NaturalGasBilling(Date invoiceDate, Date fromDate, Date toDate, long billingDay,
                             double billedGJ, double basicCharge, double deliveryCharges,
                             double StorageTransport, double commodityCharges, double Tax,
                             double cleanEnergyLevy, double carbonTax, double Amount) {

        /**
         * method to convert the record to a stream of bytes
         * @return the stream of bytes
         */
        public Stream<Byte> toBytes(){
            return Stream.of(invoiceDate, fromDate, toDate, billingDay, billedGJ, basicCharge, deliveryCharges,
                    StorageTransport, commodityCharges, Tax, cleanEnergyLevy, carbonTax, Amount).map(e -> (byte) e.hashCode());
        }
    }

    /**
     * method to order the stream by invoice date descending
     * @param stream the stream of lines
     * @return the stream of NaturalGasBilling ordered by invoice date descending
     */
    static Stream<NaturalGasBilling> orderByInvoiceDateDesc(Stream<String> stream) {
        return stream.map(e -> e.split(",")).map(e -> new NaturalGasBilling(
                new Date(e[0]), new Date(e[1]), new Date(e[2]), Long.parseLong(e[3]), Double.parseDouble(e[4]),
                Double.parseDouble(e[5]), Double.parseDouble(e[6]), Double.parseDouble(e[7]),
                Double.parseDouble(e[8]), Double.parseDouble(e[9]), Double.parseDouble(e[10]),
                Double.parseDouble(e[11]), Double.parseDouble(e[12]))).sorted(Comparator.comparing(NaturalGasBilling::invoiceDate).reversed());
    }

    /**
     * method to serialize a stream of NaturalGasBilling
     * @param stream the stream of NaturalGasBilling
     * @return the stream of bytes
     */
    static Stream<Byte> serialize(Stream<NaturalGasBilling> stream){
        //list of all header values
        List<String> header = List.of("invoiceDate", "fromDate", "toDate", "billingDay", "billedGJ", "basicCharge", "deliveryCharges",
                "StorageTransport", "commodityCharges", "Tax", "cleanEnergyLevy", "carbonTax", "Amount");
        return Stream.concat(header.stream().map(e -> (byte) e.hashCode()), stream.flatMap(NaturalGasBilling::toBytes));
    }

    /**
     * method to deserialize a stream of bytes
     * @param stream the stream of bytes
     * @return the stream of NaturalGasBilling
     */
    public Stream<NaturalGasBilling> deserialize(Stream<Byte> stream) {
        return null;
    }

    // Aufgabe 3) h)

    /**
     * method to search "dir" and all sub folders for files staring with "st" and ending with "ed" and sort them by size
     * @param dir the directory to search in
     * @param startsWith the start of the file name
     * @param endsWith the end of the file name
     * @param maxFiles the maximum number of files to return
     * @return a stream of files
     */
    public static Stream<File> findFilesWith(String dir, String startsWith, String endsWith, int maxFiles) {
        try {
            Files.walk(Paths.get(dir)).filter(Files::isRegularFile).filter(e -> e.getFileName().toString().startsWith(startsWith))
                    .filter(e -> e.getFileName().toString().endsWith(endsWith)).sorted(Comparator.comparingLong(e -> e.toFile().length()))
                    .limit(maxFiles).map(Path::toFile).forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while reading directory");
        }

        return null;
    }
}
