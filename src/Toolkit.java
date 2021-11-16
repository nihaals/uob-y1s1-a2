import java.util.Scanner;

public class Toolkit {
    private static final Scanner stdIn = new Scanner(System.in);
    public static final String GOODBYEMESSAGE = "Thank you for playing";

    public static String getInputForMessage(String message) {
        System.out.println(message);
        return stdIn.nextLine();
    }

    public static String printArray(String[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i].trim()).append(", ");
        }
        return sb.substring(0, sb.toString().length() - 2);
    }
}
