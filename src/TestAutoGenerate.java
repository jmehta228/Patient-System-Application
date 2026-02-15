import ButtonActionFiles.PatientDataGenerator;
import ButtonActionFiles.DatabaseUtils;

import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Standalone tool for populating the database with test patient data.
 * This is a developer/admin utility and is not exposed in the user interface.
 *
 * Usage:
 *   java -cp "out/production/Patient-System-Application:lib/*" TestAutoGenerate
 */
public class TestAutoGenerate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║     PATIENT DATABASE AUTO-POPULATION TOOL                 ║");
        System.out.println("║     Developer/Admin Utility - Not User-Facing            ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println();

        // Show current database status
        showCurrentStatus();

        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ Options:                                                │");
        System.out.println("│  1. Generate patients with default age range (0-100)   │");
        System.out.println("│  2. Generate patients with custom age range            │");
        System.out.println("│  3. Quick test - Generate 10 sample patients           │");
        System.out.println("│  4. Show sample data (without inserting)               │");
        System.out.println("│  5. Exit                                                │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        System.out.print("\nSelect option (1-5): ");

        int option = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (option) {
            case 1:
                generateWithDefaultAge(scanner);
                break;
            case 2:
                generateWithCustomAge(scanner);
                break;
            case 3:
                quickTest();
                break;
            case 4:
                showSampleData();
                break;
            case 5:
                System.out.println("\nExiting...");
                break;
            default:
                System.out.println("\nInvalid option!");
        }

        scanner.close();
    }

    private static void showCurrentStatus() {
        System.out.println("Current Database Status:");
        System.out.println("─────────────────────────");
        try {
            int[] counts = DatabaseUtils.getPatientCount();
            double avgAge = DatabaseUtils.returnAverageAge();

            System.out.println("  Sick patients:      " + counts[0]);
            System.out.println("  Recovered patients: " + counts[1]);
            System.out.println("  Total patients:     " + counts[2]);
            System.out.println("  Average age:        " + String.format("%.2f", avgAge) + " years");
        } catch (SQLException | FileNotFoundException e) {
            System.err.println("  Error: Cannot retrieve database status");
            System.err.println("  " + e.getMessage());
        }
    }

    private static void generateWithDefaultAge(Scanner scanner) {
        System.out.print("\nEnter number of patients to generate (1-10000): ");
        int count = scanner.nextInt();

        if (count < 1 || count > 10000) {
            System.out.println("Error: Count must be between 1 and 1000");
            return;
        }

        System.out.println("\nGenerating " + count + " patients with ages 0-100...");
        int successCount = PatientDataGenerator.generateAndInsertPatients(count);

        System.out.println("✓ Successfully inserted " + successCount + " of " + count + " patients");
        showCurrentStatus();
    }

    private static void generateWithCustomAge(Scanner scanner) {
        System.out.print("\nEnter number of patients to generate (1-1000): ");
        int count = scanner.nextInt();

        if (count < 1 || count > 1000) {
            System.out.println("Error: Count must be between 1 and 1000");
            return;
        }

        System.out.print("Enter minimum age: ");
        int minAge = scanner.nextInt();

        System.out.print("Enter maximum age: ");
        int maxAge = scanner.nextInt();

        if (minAge < 0 || maxAge < 0 || minAge > maxAge) {
            System.out.println("Error: Invalid age range");
            return;
        }

        System.out.println("\nGenerating " + count + " patients with ages " + minAge + "-" + maxAge + "...");
        int successCount = PatientDataGenerator.generateAndInsertPatients(count, minAge, maxAge);

        System.out.println("✓ Successfully inserted " + successCount + " of " + count + " patients");
        showCurrentStatus();
    }

    private static void quickTest() {
        System.out.println("\nQuick Test: Generating 10 sample patients...");
        int successCount = PatientDataGenerator.generateAndInsertPatients(10);
        System.out.println("✓ Successfully inserted " + successCount + " patients");
        showCurrentStatus();
    }

    private static void showSampleData() {
        System.out.println("\nSample Data (not inserted into database):");
        System.out.println("─────────────────────────────────────────");
        for (int i = 0; i < 10; i++) {
            String firstName = PatientDataGenerator.generateFirstName();
            String lastName = PatientDataGenerator.generateLastName();
            String birthdate = PatientDataGenerator.generateBirthdate();
            System.out.println("  " + (i+1) + ". " + firstName + " " + lastName + " - DOB: " + birthdate);
        }
        System.out.println("\nNote: This is sample data only. Nothing was inserted into the database.");
    }
}
