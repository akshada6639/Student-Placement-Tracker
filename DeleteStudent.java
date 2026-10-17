import java.io.*;
import java.util.*;

public class DeleteStudent {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<String> records = new ArrayList<>();

        System.out.println("===== Delete Student =====");
        System.out.print("Enter student name to delete: ");
        String searchName = sc.nextLine();

        boolean found = false;

        try {
            File file = new File("students.txt");
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                if (line.startsWith("Name: ")
                        && line.substring(6).equalsIgnoreCase(searchName)) {

                    found = true;

                    while (reader.hasNextLine()) {
                        String detail = reader.nextLine();

                        if (detail.equals("------------------------------")) {
                            break;
                        }
                    }

                } else {
                    records.add(line);
                }
            }

            reader.close();

            if (found) {
                FileWriter writer = new FileWriter(file);

                for (String record : records) {
                    writer.write(record + System.lineSeparator());
                }

                writer.close();
                System.out.println("Student deleted successfully!");

            } else {
                System.out.println("Student not found!");
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}