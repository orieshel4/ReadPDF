import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ReadPDF {

        int targetLine = 58;
        String targetWord = "משכורת";
        String targetWord2 = "משכורת";
        String decimalNumber = "";
        String var = "";
        BigDecimal sum = BigDecimal.ZERO;


    public void readFile(File file) {

            try {
                PDDocument document = PDDocument.load(file);
                int totalPages = document.getNumberOfPages();

                for (int page = 1; page <= totalPages; page++) {
                    PDFTextStripper stripper = new PDFTextStripper();
                    stripper.setStartPage(page);
                    stripper.setEndPage(page);

                    String pageText = stripper.getText(document);
                    String[] lines = pageText.split("\\r?\\n");


                    for (int i = 0; i < lines.length; i++) {
                        if (lines[i].contains(targetWord)) {
                            targetLine = i;
                            //System.out.println("Line " + (i + 1) + ": " + lines[i]);
                            decimalNumber = extractSecondDecimalNumber(lines[i]);
                        }

                        if (lines[i].contains(targetWord2)) {
                            targetLine = i;
                            var = extractSecondDecimalNumber(lines[i]);
                        }
                    }

                    if (decimalNumber != null) {
                        BigDecimal decimal = new BigDecimal(decimalNumber);
                        sum = sum.add(decimal);
                    }

                    System.out.printf("Number: " + decimalNumber + "\t \t \t");
                    extractMonth(pageText);

                }
                System.out.println("--------------------");
                System.out.println("sum: " + sum + "\n");

                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public BigDecimal getSum(){

            return sum;
        }


    private String extractLineText(String text, int targetLine) {
        String[] lines = text.split("\\r?\\n");

        if (targetLine <= lines.length) {
            return lines[targetLine - 1];
        }

        return null;
    }

    private void extractMonth(String pageText){
        String pattern = "שכר לחודש (\\d{2}/\\d{4})";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(pageText);

        if (matcher.find()) {
            String value = matcher.group(1);
            //System.out.printf("var: " + var + "\t \t \t");
            System.out.println("Month: " + value);
        } else {
            System.out.println("Month not found.");
        }
    }

    private static String extractSecondDecimalNumber(String text) {
        String decimalPattern = "\\d+\\.\\d{2}";
        Pattern pattern = Pattern.compile(decimalPattern);
        Matcher matcher = pattern.matcher(text);

        String firstDecimal = null;
        String secondDecimal = null;

        while (matcher.find()) {
            if (firstDecimal == null) {
                firstDecimal = matcher.group();
            } else {
                secondDecimal = matcher.group();
                break;
            }
        }

        return secondDecimal;
    }
}

