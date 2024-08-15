import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileConcatenator {

    public static void main(String[] args) {
        String inputFile1 = "file1.txt";
        String inputFile2 = "file2.txt";
        String outputFile = "output.txt";

        concatenateFiles(inputFile1, inputFile2, outputFile);
    }

    public static void concatenateFiles(String inputFile1, String inputFile2, String outputFile) {
        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(inputFile1));
            BufferedReader reader2 = new BufferedReader(new FileReader(inputFile2));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            String line;

            // Concatenate words from the first file
            while ((line = reader1.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    writer.write(word);
                    writer.write(" ");
                }
            }

            // Concatenate words from the second file
            while ((line = reader2.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    writer.write(word);
                    writer.write(" ");
                }
            }

            reader1.close();
            reader2.close();
            writer.close();

            System.out.println("Concatenation completed. Result saved to " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}