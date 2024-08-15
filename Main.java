import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        Parser parser = new Parser(terminal);
        Scanner scanner = new Scanner(System.in);

        // Infinite loop to keep accepting and processing user input
        while (true) {
            System.out.print("> "); // Print the prompt
            String input = scanner.nextLine(); // Read user input
            String[] commands = input.split("\\s*&\\s*"); // Split input based on "&" operator

            for (String command : commands) {
                // Remove double quotes from each command
                Pattern pattern = Pattern.compile("\"");
                Matcher matcher = pattern.matcher(command);
                String result = matcher.replaceAll("");

                if (result.trim().equalsIgnoreCase("exit")) {
                    // Handle the exit command
                    scanner.close();
                    System.out.println("Exiting the CLI...");
                    return;
                }

                // Parse and execute the command
                parser.parseAndExecute(result);
            }
        }
    }
}
