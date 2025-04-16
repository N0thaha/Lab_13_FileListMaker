import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final ArrayList<String> list = new ArrayList<>();
    private static String currentFileName = "";
    private static boolean needsToBeSaved = false;

    public static void main(String[] args) {
        SafeInput.prettyHeader("Welcome to File List Maker!");

        try {
            runListMaker();
        } catch (IOException e) {
            System.out.println("⚠ An error occurred with file operations: " + e.getMessage());
        }
    }

    private static void runListMaker() throws IOException {
        boolean done = false;

        while (!done) {
            displayList();
            String choice = SafeInput.getRegExString(in, "Choose [A]dd [D]elete [I]nsert [V]iew [M]ove [C]lear [O]pen [S]ave [Q]uit", "[AaDdIiVvMmCcOoSsQq]").toUpperCase();

            switch (choice) {
                case "A" -> addItem();
                case "D" -> deleteItem();
                case "I" -> insertItem();
                case "V" -> printList();
                case "M" -> moveItem();
                case "C" -> clearList();
                case "O" -> openList();
                case "S" -> saveList();
                case "Q" -> {
                    if (needsToBeSaved) {
                        boolean saveBeforeQuit = SafeInput.getYNConfirm(in, "You have unsaved changes. Save before quitting?");
                        if (saveBeforeQuit) {
                            saveList();
                        }
                    }
                    if (SafeInput.getYNConfirm(in, "Are you sure you want to quit?")) {
                        done = true;
                        System.out.println("Goodbye!");
                    }
                }
            }
        }
    }

    private static void addItem() {
        String item = SafeInput.getNonZeroLenString(in, "Enter item to add");
        list.add(item);
        needsToBeSaved = true;
        System.out.println("✔ Item added to the list.");
    }

    private static void deleteItem() {
        if (list.isEmpty()) {
            System.out.println("⚠ The list is empty. Nothing to delete.");
            return;
        }

        displayList();
        int index = SafeInput.getRangedInt(in, "Enter the number of the item to delete", 1, list.size()) - 1;
        String removed = list.remove(index);
        needsToBeSaved = true;
        System.out.println("✔ Removed: " + removed);
    }

    private static void insertItem() {
        String item = SafeInput.getNonZeroLenString(in, "Enter item to insert");

        if (list.isEmpty()) {
            list.add(item);
            System.out.println("✔ List was empty. Item added at position 1.");
        } else {
            displayList();
            int position = SafeInput.getRangedInt(in, "Enter the position to insert the item", 1, list.size() + 1) - 1;
            list.add(position, item);
            System.out.println("✔ Item inserted at position " + (position + 1));
        }
        needsToBeSaved = true;
    }

    private static void moveItem() {
        if (list.isEmpty()) {
            System.out.println("⚠ The list is empty. Nothing to move.");
            return;
        }

        displayList();
        int fromIndex = SafeInput.getRangedInt(in, "Enter the number of the item to move", 1, list.size()) - 1;
        int toIndex = SafeInput.getRangedInt(in, "Enter the new position for the item", 1, list.size()) - 1;

        String item = list.remove(fromIndex);
        list.add(toIndex, item);
        needsToBeSaved = true;
        System.out.println("✔ Moved item to position " + (toIndex + 1));
    }

    private static void clearList() {
        if (SafeInput.getYNConfirm(in, "Are you sure you want to clear the entire list?")) {
            list.clear();
            needsToBeSaved = true;
            System.out.println("✔ List cleared.");
        }
    }

    private static void openList() throws IOException {
        if (needsToBeSaved) {
            boolean saveFirst = SafeInput.getYNConfirm(in, "You have unsaved changes. Save before opening a new list?");
            if (saveFirst) {
                saveList();
            }
        }

        String filename = SafeInput.getNonZeroLenString(in, "Enter filename to open (without .txt)") + ".txt";
        Path filePath = Paths.get(filename);

        if (!Files.exists(filePath)) {
            System.out.println("⚠ File does not exist.");
            return;
        }

        list.clear();
        list.addAll(Files.readAllLines(filePath));
        currentFileName = filename;
        needsToBeSaved = false;
        System.out.println("✔ List loaded from " + filename);
    }

    private static void saveList() throws IOException {
        if (currentFileName.isEmpty()) {
            currentFileName = SafeInput.getNonZeroLenString(in, "Enter filename to save as (without .txt)") + ".txt";
        }
        Path filePath = Paths.get(currentFileName);
        Files.write(filePath, list);
        needsToBeSaved = false;
        System.out.println("✔ List saved to " + currentFileName);
    }

    private static void printList() {
        if (list.isEmpty()) {
            System.out.println("📝 The list is currently empty.");
        } else {
            System.out.println("\n📝 Current List:");
            for (int i = 0; i < list.size(); i++) {
                System.out.println(" " + (i + 1) + ": " + list.get(i));
            }
        }
    }

    private static void displayList() {
        System.out.println("\n========== Current List ==========");
        if (list.isEmpty()) {
            System.out.println("   [The list is empty]");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println("   " + (i + 1) + ". " + list.get(i));
            }
        }
        System.out.println("==================================");
    }
}
