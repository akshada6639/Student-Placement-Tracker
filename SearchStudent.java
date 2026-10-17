import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SearchStudent {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== Search Student =====");
        System.out.print("Enter student name to search: ");
        String searchName = sc.nextLine();

        boolean found = false;

        try {
            File file = new File("students.txt");
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                if (line.startsWith("Name: ")) {
                    String name = line.substring(6);

                    if (name.equalsIgnoreCase(searchName)) {
                        found = true;

                        System.out.println("\nStudent Found!");
                        System.out.println(line);

                        for (int i = 0; i < 6 && reader.hasNextLine(); i++) {
                            String detail = reader.nextLine();

                            if (!detail.equals("------------------------------")) {
                                System.out.println(detail);
                            } else {
                                break;
                            }
                        }

                        break;
                    }
                }
            }

            reader.close();

            if (!found) {
                System.out.println("Student not found!");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Student data file not found!");
            System.out.println("Run StudentTracker.java first to save student data.");
        }

        sc.close();
    }
}