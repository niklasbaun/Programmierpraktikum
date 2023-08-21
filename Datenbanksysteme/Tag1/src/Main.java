import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        System.out.println(StreamingJava.averageCost(StreamingJava.fileLines("D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\Datenbanksysteme\\Tag1\\src\\NaturalGasBilling.csv")));
        System.out.println(StreamingJava.countCleanEnergyLevy(StreamingJava.fileLines("D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\Datenbanksysteme\\Tag1\\src\\NaturalGasBilling.csv")));

        StreamingJava.findFilesWith("D:\\Marburg", "st", "ed", 10).forEach(System.out::println);
    }
    private static void writeCSV(){
        //create new file
        File file = new File("D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\Datenbanksysteme\\Tag1\\src\\NewNaturalGasBilling.csv");
        //use serialize method to get stream of bytes and write into file
        StreamingJava.serialize(StreamingJava.orderByInvoiceDateDesc(StreamingJava.fileLines("D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\Datenbanksysteme\\Tag1\\src\\NaturalGasBilling.csv"))).forEach(e -> {
            try {
                file.createNewFile();
                Files.write(file.toPath(), e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

}