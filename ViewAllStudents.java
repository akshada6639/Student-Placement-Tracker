import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ViewAllStudents {
    public static void main(String[] args) {

        System.out.println("===== All Students Report =====");

        try {
            File file = new File("students.txt");
            Scanner reader = new Scanner(file);

            boolean found = false;

            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                if (line.startsWith("Name: ")) {
                    found = true;
                    System.out.println("\n" + line);

                    while (reader.hasNextLine()) {
                        String detail = reader.nextLine();

                        if (detail.equals("------------------------------")) {
                            System.out.println(detail);
                            break;
                        }

                        System.out.println(detail);
                    }
                }
            }

            reader.close();

            if (!found) {
                System.out.println("No student records found!");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Student data file not found!");
            System.out.println("Run StudentTracker.java first.");
        }
    }
}