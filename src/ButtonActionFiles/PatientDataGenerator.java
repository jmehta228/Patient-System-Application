package ButtonActionFiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Utility class for generating random patient data for testing and demonstration purposes.
 */
public class PatientDataGenerator {
    private static final Random random = new Random();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    // Sample first names
    private static final String[] FIRST_NAMES = {
        "James", "Mary", "John", "Patricia", "Robert", "Jennifer", "Michael", "Linda",
        "William", "Barbara", "David", "Elizabeth", "Richard", "Susan", "Joseph", "Jessica",
        "Thomas", "Sarah", "Charles", "Karen", "Christopher", "Nancy", "Daniel", "Lisa",
        "Matthew", "Betty", "Anthony", "Margaret", "Mark", "Sandra", "Donald", "Ashley",
        "Steven", "Kimberly", "Paul", "Emily", "Andrew", "Donna", "Joshua", "Michelle",
        "Kenneth", "Dorothy", "Kevin", "Carol", "Brian", "Amanda", "George", "Melissa",
        "Edward", "Deborah", "Ronald", "Stephanie", "Timothy", "Rebecca", "Jason", "Sharon",
        "Jeffrey", "Laura", "Ryan", "Cynthia", "Jacob", "Kathleen", "Gary", "Amy",
        "Nicholas", "Shirley", "Eric", "Angela", "Jonathan", "Helen", "Stephen", "Anna",
        "Larry", "Brenda", "Justin", "Pamela", "Scott", "Nicole", "Brandon", "Emma",
        "Benjamin", "Samantha", "Samuel", "Katherine", "Raymond", "Christine", "Gregory", "Debra",
        "Frank", "Rachel", "Alexander", "Catherine", "Patrick", "Carolyn", "Raymond", "Janet",
        "Jack", "Ruth", "Dennis", "Maria", "Jerry", "Heather", "Tyler", "Diane",
        "Aaron", "Virginia", "Jose", "Julie", "Adam", "Joyce", "Henry", "Victoria",
        "Nathan", "Olivia", "Douglas", "Kelly", "Zachary", "Christina", "Peter", "Lauren",
        "Kyle", "Joan", "Walter", "Evelyn", "Ethan", "Judith", "Jeremy", "Megan",
        "Harold", "Cheryl", "Keith", "Andrea", "Christian", "Hannah", "Roger", "Martha",
        "Noah", "Jacqueline", "Gerald", "Frances", "Carl", "Gloria", "Terry", "Ann",
        "Sean", "Teresa", "Austin", "Kathryn", "Arthur", "Sara", "Lawrence", "Janice"
    };

    // Sample last names
    private static final String[] LAST_NAMES = {
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
        "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas",
        "Taylor", "Moore", "Jackson", "Martin", "Lee", "Perez", "Thompson", "White",
        "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson", "Walker", "Young",
        "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores",
        "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell",
        "Carter", "Roberts", "Gomez", "Phillips", "Evans", "Turner", "Diaz", "Parker",
        "Cruz", "Edwards", "Collins", "Reyes", "Stewart", "Morris", "Morales", "Murphy",
        "Cook", "Rogers", "Gutierrez", "Ortiz", "Morgan", "Cooper", "Peterson", "Bailey",
        "Reed", "Kelly", "Howard", "Ramos", "Kim", "Cox", "Ward", "Richardson",
        "Watson", "Brooks", "Chavez", "Wood", "James", "Bennett", "Gray", "Mendoza",
        "Ruiz", "Hughes", "Price", "Alvarez", "Castillo", "Sanders", "Patel", "Myers",
        "Long", "Ross", "Foster", "Jimenez", "Powell", "Jenkins", "Perry", "Russell",
        "Sullivan", "Bell", "Coleman", "Butler", "Henderson", "Barnes", "Gonzales", "Fisher",
        "Vasquez", "Simmons", "Romero", "Jordan", "Patterson", "Alexander", "Hamilton", "Graham",
        "Reynolds", "Griffin", "Wallace", "Moreno", "West", "Cole", "Hayes", "Bryant",
        "Herrera", "Gibson", "Ellis", "Tran", "Medina", "Aguilar", "Stevens", "Murray",
        "Ford", "Castro", "Marshall", "Owens", "Harrison", "Fernandez", "McDonald", "Woods"
    };

    /**
     * Generate a random first name.
     */
    public static String generateFirstName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    /**
     * Generate a random last name.
     */
    public static String generateLastName() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }

    /**
     * Generate a random birthdate for a patient aged between minAge and maxAge.
     * @param minAge Minimum age in years
     * @param maxAge Maximum age in years
     * @return Formatted birthdate string (MM/dd/yyyy)
     */
    public static String generateBirthdate(int minAge, int maxAge) {
        LocalDate today = LocalDate.now();

        // Calculate the range of birth years
        int minYearsAgo = minAge;
        int maxYearsAgo = maxAge;

        // Random years ago
        int yearsAgo = minYearsAgo + random.nextInt(maxYearsAgo - minYearsAgo + 1);

        // Random month (1-12)
        int month = 1 + random.nextInt(12);

        // Random day (1-28 to avoid invalid dates)
        int day = 1 + random.nextInt(28);

        LocalDate birthDate = today.minusYears(yearsAgo).withMonth(month).withDayOfMonth(day);

        return birthDate.format(DATE_FORMATTER);
    }

    /**
     * Generate a random birthdate for a patient with typical hospital age range (0-100 years).
     */
    public static String generateBirthdate() {
        return generateBirthdate(0, 100);
    }

    /**
     * Generate a random patient status (Sick or Recover).
     * @param sickProbability Probability of being sick (0.0 to 1.0)
     */
    public static String generateStatus(double sickProbability) {
        return random.nextDouble() < sickProbability ? "Sick" : "Recover";
    }

    /**
     * Generate a random patient status with 70% chance of being sick.
     */
    public static String generateStatus() {
        return generateStatus(0.7);
    }

    /**
     * Generate and insert a single random patient into the database.
     * @return The number of rows affected (1 if successful, 0 otherwise)
     */
    public static int generateAndInsertPatient() {
        String firstName = generateFirstName();
        String lastName = generateLastName();
        String birthdate = generateBirthdate();

        return DatabaseUtils.insertPatientToDatabase(firstName, lastName, birthdate);
    }

    /**
     * Generate and insert multiple random patients into the database.
     * @param count Number of patients to generate
     * @return The number of patients successfully inserted
     */
    public static int generateAndInsertPatients(int count) {
        int successCount = 0;

        for (int i = 0; i < count; i++) {
            try {
                int result = generateAndInsertPatient();
                if (result > 0) {
                    successCount++;
                }
            } catch (Exception e) {
                System.err.println("Failed to insert patient " + (i + 1) + ": " + e.getMessage());
            }
        }

        return successCount;
    }

    /**
     * Generate and insert multiple random patients with custom age range.
     * @param count Number of patients to generate
     * @param minAge Minimum age in years
     * @param maxAge Maximum age in years
     * @return The number of patients successfully inserted
     */
    public static int generateAndInsertPatients(int count, int minAge, int maxAge) {
        int successCount = 0;

        for (int i = 0; i < count; i++) {
            try {
                String firstName = generateFirstName();
                String lastName = generateLastName();
                String birthdate = generateBirthdate(minAge, maxAge);

                int result = DatabaseUtils.insertPatientToDatabase(firstName, lastName, birthdate);
                if (result > 0) {
                    successCount++;
                }
            } catch (Exception e) {
                System.err.println("Failed to insert patient " + (i + 1) + ": " + e.getMessage());
            }
        }

        return successCount;
    }
}
