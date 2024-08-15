public class CommandParameters {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No command parameters provided.");
            return;
        }

        System.out.println("Command parameters:");
        for (String arg : args) {
            System.out.println(parseParameter(arg));
        }
    }

    public static String parseParameter(String input) {
        input = input.toLowerCase(); // Convert the input to lowercase
        if (input.startsWith("\"") && input.endsWith("\"") && input.length() > 1) {
            return input.substring(1, input.length() - 1);
        }
        return input;
    }
}
