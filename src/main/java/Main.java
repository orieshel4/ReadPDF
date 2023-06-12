import java.io.File;
import java.math.BigDecimal;


public class Main {
    public static void main(String[] args) {
        BigDecimal sumHours = BigDecimal.ZERO;
        //ReadPDF readPDF = new ReadPDF();
        String filePath = "C:\\\\Users\\\\ories\\\\Desktop\\\\mom\\\\ex (1).pdf";

        String folderPath = "C:\\\\Users\\\\ories\\\\Desktop\\\\mom"; // Specify the folder path

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    ReadPDF readPDF = new ReadPDF();
                    System.out.println("Reading file: " + file.getName());
                    readPDF.readFile(file);
                    sumHours = sumHours.add(readPDF.getSum());
                }
            }
        }
        System.out.println("=============");
        System.out.printf("Total hours: " + sumHours);
    }
}



