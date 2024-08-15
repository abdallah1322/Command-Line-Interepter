import java.util.Arrays;

public record Parser(Terminal terminal) {

    public void parseAndExecute(String input) {
        // Split the input into command and arguments
        String[] parts = input.trim().split("\\s+");
        String command = parts[0];
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);

        // Check for redirection operators > and >>
        boolean appendToFile = false;
        if (args.length >= 2 && (args[args.length - 2].equals(">") || args[args.length - 2].equals(">>"))) {
            appendToFile = args[args.length - 2].equals(">>");
            args = Arrays.copyOfRange(args, 0, args.length - 2);
        }

        // Execute the command
            switch (command) {
                case "clear" -> terminal.clear();
                case "cd" -> terminal.changeDirectory(args);
                case "ls" -> terminal.listDirectory(args);
                case "cp" -> terminal.copyFile(args);
                case "mv" -> terminal.moveFile(args);
                case "rm" -> terminal.deleteFile(args);
                case "mkdir" -> terminal.createDirectory(args);
                case "rmdir" -> terminal.removeDirectory(args);
                case "cat" -> terminal.concatenateFiles(args, appendToFile);
                case "more" -> terminal.showFileContent(args);
                case "pwd" -> terminal.printWorkingDirectory();
                case "date" -> terminal.printDate();
                case "help" -> terminal.showHelp(args);
                case "exit" -> terminal.exit();
                default -> terminal.printError("Unknown command: " + command);
            }
        }
    }


