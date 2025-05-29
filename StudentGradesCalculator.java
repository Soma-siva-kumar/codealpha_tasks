import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradesCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> grades = new ArrayList<>();

        System.out.println("=== Student Grades Entry System ===");

        while (true) {
            System.out.print("Enter student grade (or type 'done' to finish): ");
            String input = scanner.next();

            if (input.equalsIgnoreCase("done")) {
                break;
            }

            try {
                double grade = Double.parseDouble(input);
                if (grade < 0 || grade > 100) {
                    System.out.println("Please enter a valid grade between 0 and 100.");
                } else {
                    grades.add(grade);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        if (grades.isEmpty()) {
            System.out.println("No grades entered. Exiting program.");
        } else {
            double sum = 0;
            double highest = grades.get(0);
            double lowest = grades.get(0);

            for (double grade : grades) {
                sum += grade;
                if (grade > highest) highest = grade;
                if (grade < lowest) lowest = grade;
            }

            double average = sum / grades.size();

            System.out.println("\n=== Grade Statistics ===");
            System.out.printf("Average Grade: %.2f%n", average);
            System.out.printf("Highest Grade: %.2f%n", highest);
            System.out.printf("Lowest Grade : %.2f%n", lowest);
        }

        scanner.close();
    }
}
