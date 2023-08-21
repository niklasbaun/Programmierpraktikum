import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        System.out.println(StreamingJava.averageCost(StreamingJava.fileLines("D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\Datenbanksysteme\\Tag1\\src\\NaturalGasBilling.csv")));
        System.out.println(StreamingJava.countCleanEnergyLevy(StreamingJava.fileLines("D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\Datenbanksysteme\\Tag1\\src\\NaturalGasBilling.csv")));

        StreamingJava.findFilesWith("D:\\Marburg\\Sem02", "st", "ed", 10).forEach(System.out::println);
    }

}