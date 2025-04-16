import java.util.Scanner;

public class SafeInput {
    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString = ""; // Set this to zero length. Loop runs until it isn't
        do {
            System.out.print("\n" + prompt + ": "); // show prompt add space
            retString = pipe.nextLine();
        } while (retString.length() == 0);

        return retString;
    }

    public static int getInt(Scanner pipe, String prompt){
        int value = 0;
        boolean valid = false;

        do {
            System.out.print("\n" + prompt + ": ");

            if (pipe.hasNextInt()) {
                value = pipe.nextInt();
                valid = true;
            } else {
                System.out.println("Invalid input! Please enter a valid integer.");
                pipe.next();
            }
            pipe.nextLine();
        } while (!valid);

        return value;
    }

    public static double getDouble(Scanner pipe, String prompt){
        double value = 0;
        boolean valid = false;

        do {
            System.out.print("\n" + prompt + ": ");

            if (pipe.hasNextDouble()) {
                value = pipe.nextDouble();
                valid = true;
            } else {
                System.out.println("Invalid input! Please enter a valid double.");
                pipe.next();
            }
            pipe.nextLine();
        } while (!valid);

        return value;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high){
        int value = 0;
        boolean valid = false;

        do {
            System.out.print("\n" + prompt + " [" + low + " - " + high + "]: ");

            if (pipe.hasNextInt()) {
                value = pipe.nextInt();
                if (value >= low && value <= high) {
                    valid = true;
                } else {
                    System.out.println("Input out of range! Please enter a value between " + low + " and " + high + ".");
                }
            } else {
                System.out.println("Invalid input! Please enter a valid integer.");
                pipe.next();
            }
            pipe.nextLine();
        } while (!valid);

        return value;
    }

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high){
        double value = 0;
        boolean valid = false;

        do {
            System.out.print("\n" + prompt + " [" + low + " - " + high + "]: ");

            if (pipe.hasNextDouble()) {
                value = pipe.nextDouble();
                if (value >= low && value <= high) {
                    valid = true;
                } else {
                    System.out.println("Input out of range! Please enter a value between " + low + " and " + high + ".");
                }
            } else {
                System.out.println("Invalid input! Please enter a valid Double.");
                pipe.next();
            }
            pipe.nextLine();
        } while (!valid);

        return value;
    }

    public static boolean getYNConfirm (Scanner pipe, String prompt){
        String response;
        boolean valid = false;
        boolean result = false;

        do {
            System.out.print("\n" + prompt + " [Y/N]: ");
            response = pipe.nextLine().trim();

            if (response.equalsIgnoreCase("Y")) {
                result = true;
                valid = true;
            } else if (response.equalsIgnoreCase("N")) {
                result = false;
                valid = true;
            } else {
                System.out.println("Invalid input! Please enter Y or N.");
            }
        } while (!valid);

        return result;
    }

    public static String getRegExString (Scanner pipe, String prompt, String regEx){
        String input;
        boolean valid = false;

        do {
            System.out.print("\n" + prompt + ": ");
            input = pipe.nextLine().trim();

            if (input.matches(regEx)) {
                valid = true;
            } else {
                System.out.println("Invalid input! Please enter a valid format.");
            }
        } while (!valid);

        return input;
    }
    public static void prettyHeader(String msg) {
        int width = 60;
        int msgLength = msg.length();
        int padding = (width - msgLength - 6) / 2;
        int extraSpace = (width - msgLength - 6) % 2;

        for (int i = 0; i < width; i++) {
            System.out.print("*");
        }
        System.out.println();

        System.out.print("***");
        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
        System.out.print(msg);
        for (int i = 0; i < padding + extraSpace; i++) {
            System.out.print(" ");
        }
        System.out.println("***");

        for (int i = 0; i < width; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
}
