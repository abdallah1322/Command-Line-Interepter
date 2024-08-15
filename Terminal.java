import java.io.*;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Objects;
import java.io.FileReader;

public class Terminal {
    private File currentDirectory;

    public Terminal() {
        // Initialize the current directory to the user's home directory
        this.currentDirectory = new File(System.getProperty("user.dir"));
    }

    public void clear() {
        // Code to clear the terminal screen using ANSI escape codes
        // This should work on most Unix-like systems and some Windows terminal emulators
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void changeDirectory(String[] args) {
        // Code to handle the cd command
        if (args.length == 1) {
            File newDir = new File(args[0]);
            if (newDir.isDirectory()) {
                this.currentDirectory = newDir;
            } else {
                printError("Directory not found: " + args[0]);
            }
        } else {
            printError("Invalid arguments for cd command.");
        }
    }

    public void listDirectory(String[] args) {
        // Code to handle the ls command
        // List the files/directories in the current directory
        File[] files = currentDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                System.out.println(file.getName());
            }
        } else {
            printError("Failed to list directory contents.");
        }
    }

    public void printWorkingDirectory() {
        // Code to handle the pwd command
        System.out.println(currentDirectory.getAbsolutePath());
    }

    public void printDate() {
        // Code to handle the date command
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
    }

    public void printError(String message) {
        System.out.println("Error: " + message);
    }

    public void copyFile(String[] args) {
        // Code to handle the cp command (copy file)
        if (args.length != 2) {
            System.out.println("Invalid arguments for cp command. Usage: cp <source_file> <destination_file>");
            return;
        }

        File sourceFile = new File(args[0]);
        File destinationFile = new File(args[1]);

        if (!sourceFile.exists()) {
            System.out.println("Source file not found: " + args[0]);
            return;
        }

        if (destinationFile.isDirectory()) {
            // If the destination is a directory, create a new file inside it with the same name as the source file
            destinationFile = new File(destinationFile, sourceFile.getName());
        }

        if (destinationFile.exists()) {
            System.out.println("Destination file already exists: " + args[1]);
            return;
        }

        try (InputStream inputStream = new FileInputStream(sourceFile);
             OutputStream outputStream = new FileOutputStream(destinationFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
            System.out.println("File copied successfully.");
        } catch (IOException e) {
            System.out.println("Failed to copy file: " + e.getMessage());
        }
    }

    public void moveFile(String[] args) {
        if (args.length != 2) {
            printError("Invalid arguments for mv command. Usage: mv <source_file> <destination_file>");
            return;
        }

        String sourceFileName = args[0];
        String destinationFileName = args[1];

        File sourceFile = new File(currentDirectory, sourceFileName);
        File destinationFile = new File(currentDirectory, destinationFileName);

        if (!sourceFile.exists()) {
            printError("Source file not found: " + sourceFileName);
            return;
        }

        if (destinationFile.isDirectory()) {
            destinationFile = new File(destinationFile, sourceFileName);
        }

        try {
            boolean success = sourceFile.renameTo(destinationFile);
            if (success) {
                System.out.println("File moved successfully.");
            } else {
                printError("Failed to move file.");
            }
        } catch (SecurityException e) {
            printError("Permission denied: " + e.getMessage());
        }
    }


    public void deleteFile(String[] args) {
        // Code to handle the rm command (delete file)
        if (args.length == 1) {
            File file = new File(args[0]);
            if (file.delete()) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete file: " + args[0]);
            }
        } else {
            System.out.println("Invalid arguments for rm command.");
        }
    }

    public void createDirectory(String[] args) {
        // Code to handle the mkdir command (create directory)
        if (args.length == 1) {
            File dir = new File(args[0]);
            if (dir.mkdir()) {
                System.out.println("Directory created successfully.");
            } else {
                System.out.println("Failed to create directory: " + args[0]);
            }
        } else {
            System.out.println("Invalid arguments for mkdir command.");
        }
    }

    public void removeDirectory(String[] args) {
        // Code to handle the rmdir command (remove directory)
        if (args.length == 1) {
            File dir = new File(args[0]);
            if (dir.isDirectory() && Objects.requireNonNull(dir.list()).length == 0) {
                if (dir.delete()) {
                    System.out.println("Directory removed successfully.");
                } else {
                    System.out.println("Failed to remove directory: " + args[0]);
                }
            } else {
                System.out.println("Directory it is not empty but it has been deleted");
            }
        } else {
            System.out.println("Invalid arguments for rmdir command.");
        }
    }
    public void concatenateFiles(String[] args , boolean appendToFile) {
            if (args.length < 1) {
                printError("Invalid arguments for cat command. Usage: cat <source_file> [<source_file> ...] [>> <destination_file>]");
                return;
            }

            try {
                File destinationFile = null;
                PrintWriter writer = null;

                if (appendToFile) {
                    destinationFile = new File(currentDirectory, args[args.length - 1]);
                    writer = new PrintWriter(new FileWriter(destinationFile, true));
                }

                for (int i = 0; i < args.length - (appendToFile ? 2 : 0); i++) {
                    String sourceFileName = args[i];
                    File sourceFile = new File(currentDirectory, sourceFileName);

                    if (!sourceFile.exists()) {
                        printError("Source file not found: " + sourceFileName);
                        if (writer != null) {
                            writer.close();
                        }
                        return;
                    }

                    try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (appendToFile) {
                                writer.println(line);
                            } else {
                                System.out.println(line);
                            }
                        }
                    }
                }

                if (writer != null) {
                    writer.close();
                    System.out.println("Files concatenated successfully.");
                }
            } catch (IOException e) {
                printError("Error concatenating files: " + e.getMessage());
            }
        }


    public void showFileContent(String[] args) {
        if (args.length != 1) {
            printError("Invalid arguments for more command. Usage: more <file>");
            return;
        }

        String fileName = args[0];
        File file = new File(currentDirectory, fileName);

        if (!file.exists()) {
            printError("File not found: " + fileName);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            printError("Error reading file: " + e.getMessage());
        }
    }

    public void showHelp(String[] args) {
        // Code to handle the help command
        // List all user commands and the syntax of their arguments
        if (args.length == 0) {
            System.out.println("Available commands:");
            System.out.println("clear");
            System.out.println("cd <directory>");
            System.out.println("ls [> <filename>]");
            System.out.println("cp <source_file> <destination_file>");
            System.out.println("mv <source_file> <destination_file>");
            System.out.println("rm <file>");
            System.out.println("mkdir <directory>");
            System.out.println("rmdir <directory>");
            System.out.println("cat <destination_file> >> <source_file>");
            System.out.println("more <file>");
            System.out.println("pwd");
            System.out.println("date");
            System.out.println("help <command>");
            System.out.println("exit");
        } else if (args.length == 1) {
            // Provide help for a specific command if available
            switch (args[0]) {
                case "clear" -> System.out.println("clear - Clear the terminal screen.");
                case "cd" -> System.out.println("cd <directory> - Change the current directory.");
                case "ls" ->
                        System.out.println("ls [> <filename>] - List the files and directories in the current directory.");

                // Add help information for other commands here
                // ...
                default -> System.out.println("Help not available for command: " + args[0]);
            }
        } else {
            System.out.println("Invalid arguments for help command.");
        }
    }

    public void exit() {
        // Code to handle the exit command
        System.out.println("Exiting the CLI...");
        System.exit(0);
    }
}
