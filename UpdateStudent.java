import java.io.*;
import java.util.*;

public class UpdateStudent {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<String> records = new ArrayList<>();

        System.out.println("===== Update Student =====");
        System.out.print("Enter student name to update: ");
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

                    System.out.println("Student Found!");
                    System.out.print("Enter new skill: ");
                    String skill = sc.nextLine();

                    System.out.print("Enter total topics: ");
                    int total = sc.nextInt();

                    System.out.print("Enter completed topics: ");
                    int completed = sc.nextInt();

                    System.out.print("Is Aptitude completed? (true/false): ");
                    boolean aptitude = sc.nextBoolean();

                    System.out.print("Is Coding completed? (true/false): ");
                    boolean coding = sc.nextBoolean();
                    sc.nextLine();

                    if (total <= 0 || completed < 0 || completed > total) {
                        System.out.println("Invalid topic count!");
                        reader.close();
                        sc.close();
                        return;
                    }

                    double progress = completed * 100.0 / total;

                    String status;
                    if (progress == 0) {
                        status = "Not Started";
                    } else if (progress < 100) {
                        status = "In Progress";
                    } else {
                        status = "Completed";
                    }

                    String placement;
                    if (progress >= 75 && aptitude && coding) {
                        placement = "Eligible";
                    } else {
                        placement = "Not Eligible";
                    }

                    records.add(line);
                    records.add("Skill: " + skill);
                    records.add("Progress: " + progress + "%");
                    records.add("Status: " + status);
                    records.add("Aptitude Completed: " + aptitude);
                    records.add("Coding Completed: " + coding);
                    records.add("Placement Status: " + placement);

                    while (reader.hasNextLine()) {
                        String detail = reader.nextLine();

                        if (detail.equals("------------------------------")) {
                            break;
                        }
                    }

                    records.add("------------------------------");

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
                System.out.println("Student details updated successfully!");
            } else {
                System.out.println("Student not found!");
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}