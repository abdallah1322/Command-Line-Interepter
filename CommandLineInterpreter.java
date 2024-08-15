import java.util.Scanner;

public class CommandLineInterpreter {
    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        Parser parser = new Parser(terminal);
        Scanner scanner = new Scanner(System.in);

        // Infinite loop to keep accepting and processing user input
        while (true) {
            System.out.print("> "); // Print the prompt
            String input = scanner.nextLine();// Read user input


            if (input.trim().equalsIgnoreCase("exit")) {
                // Handle the exit command
                break;
            }

            // Parse and execute the command
            parser.parseAndExecute(input);
        }

        System.out.println("Exiting the CLI...");
        scanner.close();
    }
}
