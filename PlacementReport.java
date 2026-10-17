import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PlacementReport {
    public static void main(String[] args) {

        int totalStudents = 0;
        int eligibleStudents = 0;
        int notEligibleStudents = 0;
        int completedStudents = 0;

        System.out.println("===== Placement Report =====");

        try {
            File file = new File("students.txt");
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                if (line.startsWith("Name: ")) {
                    totalStudents++;
                }

                if (line.equals("Placement Status: Eligible")) {
                    eligibleStudents++;
                }

                if (line.equals("Placement Status: Not Eligible")) {
                    notEligibleStudents++;
                }

                if (line.equals("Status: Completed")) {
                    completedStudents++;
                }
            }

            reader.close();

            System.out.println("Total Students: " + totalStudents);
            System.out.println("Eligible Students: " + eligibleStudents);
            System.out.println("Not Eligible Students: " + notEligibleStudents);
            System.out.println("Learning Completed Students: " + completedStudents);

        } catch (FileNotFoundException e) {
            System.out.println("Student data file not found!");
            System.out.println("Run StudentTracker.java first.");
        }
    }
}