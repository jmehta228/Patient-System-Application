import ButtonActionFiles.PatientDataGenerator;
import ButtonActionFiles.DatabaseUtils;

import java.sql.SQLException;
import java.io.FileNotFoundException;

/**
 * Simple test program for the auto-generate feature.
 */
public class TestAutoGenerate {
    public static void main(String[] args) {
        System.out.println("=== Patient Auto-Generation Test ===\n");

        // Test 1: Generate a single patient
        System.out.println("Test 1: Generating a single patient...");
        String firstName = PatientDataGenerator.generateFirstName();
        String lastName = PatientDataGenerator.generateLastName();
        String birthdate = PatientDataGenerator.generateBirthdate();
        System.out.println("  Generated: " + firstName + " " + lastName + ", DOB: " + birthdate);

        // Test 2: Generate and insert 5 patients
        System.out.println("\nTest 2: Generating and inserting 5 patients...");
        int count = PatientDataGenerator.generateAndInsertPatients(5);
        System.out.println("  Successfully inserted: " + count + " patients");

        // Test 3: Generate 3 adult patients (ages 18-65)
        System.out.println("\nTest 3: Generating 3 adult patients (ages 18-65)...");
        count = PatientDataGenerator.generateAndInsertPatients(3, 18, 65);
        System.out.println("  Successfully inserted: " + count + " adult patients");

        // Test 4: Show current patient count
        System.out.println("\nTest 4: Checking current patient counts...");
        try {
            int[] counts = DatabaseUtils.getPatientCount();
            System.out.println("  Sick patients: " + counts[0]);
            System.out.println("  Recovered patients: " + counts[1]);
            System.out.println("  Total patients: " + counts[2]);

            double avgAge = DatabaseUtils.returnAverageAge();
            System.out.println("  Average age: " + String.format("%.2f", avgAge) + " years");
        } catch (SQLException | FileNotFoundException e) {
            System.err.println("  Error retrieving patient counts: " + e.getMessage());
        }

        // Test 5: Display sample of generated data
        System.out.println("\nTest 5: Sample of random data generation (not inserted):");
        for (int i = 0; i < 5; i++) {
            firstName = PatientDataGenerator.generateFirstName();
            lastName = PatientDataGenerator.generateLastName();
            birthdate = PatientDataGenerator.generateBirthdate(25, 75);
            System.out.println("  " + (i+1) + ". " + firstName + " " + lastName + " - " + birthdate);
        }

        System.out.println("\n=== All tests completed! ===");
    }
}
