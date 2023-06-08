import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args) {
        try (PDDocument document = PDDocument.load(new File("C:\\\\Users\\\\ories\\\\Desktop\\\\mom\\\\exa.pdf"))) {
            PDFTextStripper textStripper = new PDFTextStripper();
            String pdfText = textStripper.getText(document);

            int lineNumber = 57;

            String[] lines = pdfText.split("\\r?\\n");
            String lineToExtract = lines[lineNumber - 1]; // Adjust the line number to match the array index

            String regex = "\\d+\\.\\d{2}"; // Regular expression to match decimal numbers with 2 digits after the dot

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(lineToExtract);

            int decimalNumberCount = 0;
            while (matcher.find()) {
                decimalNumberCount++;
                if (decimalNumberCount == 2) {
                    String extractedNumber = matcher.group();
                    System.out.println("Extracted Number: " + extractedNumber);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}