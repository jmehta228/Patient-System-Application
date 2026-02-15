package ButtonActionFiles;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseUtils {

    private static final String CONFIG_FILE = "config.properties";
    private static final String PATIENT_TABLE = "PatientData";
    private static final String DEFAULT_ORDER_BY = "PatientID";
    private static final DateTimeFormatter SLASH_BIRTHDATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/uuuu");

    // Used by GetPatientList and SortPatients to control default DB ordering.
    private static volatile String patientListOrderBySql = DEFAULT_ORDER_BY;
    private static final String PARSED_BIRTHDATE_SQL =
        "COALESCE(STR_TO_DATE(PatientBirthdate, '%m/%d/%Y'), STR_TO_DATE(PatientBirthdate, '%Y-%m-%d'))";

    private static Properties loadDbProperties() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load " + CONFIG_FILE, e);
        }
        return props;
    }

    private static Connection openConnection() throws SQLException {
        Properties props = loadDbProperties();
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        if (url == null || user == null || password == null) {
            throw new IllegalStateException("Missing database configuration in " + CONFIG_FILE);
        }

        return DriverManager.getConnection(url, user, password);
    }

    private static String patientListQuery() {
        String orderBy = patientListOrderBySql;
        if (orderBy == null || orderBy.trim().isEmpty()) {
            return "SELECT * FROM " + PATIENT_TABLE;
        }
        return "SELECT * FROM " + PATIENT_TABLE + " ORDER BY " + orderBy;
    }

    private static LocalDate parseBirthdate(String birthdate) {
        if (birthdate == null) {
            return null;
        }
        String trimmed = birthdate.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        try {
            if (trimmed.contains("/")) {
                return LocalDate.parse(trimmed, SLASH_BIRTHDATE_FORMAT);
            }
            // Handles ISO dates like yyyy-MM-dd (common for SQL DATE columns).
            return LocalDate.parse(trimmed);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        boolean needsQuotes = value.contains(",") || value.contains("\"") || value.contains("\n") || value.contains("\r");
        String escaped = value.replace("\"", "\"\"");
        return needsQuotes ? ("\"" + escaped + "\"") : escaped;
    }

    public static int insertPatientToDatabase(String patientFirstName, String patientLastName, String patientBirthdate) {
        String sql = "INSERT INTO " + PATIENT_TABLE +
            " (PatientFirstName, PatientLastName, PatientBirthdate, PatientStatus) VALUES (?, ?, ?, 'Sick')";
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patientFirstName);
            statement.setString(2, patientLastName);
            statement.setString(3, patientBirthdate);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int deletePatientFromDatabase(String patientID, String patientFirstName, String patientLastName, String patientBirthdate) throws FileNotFoundException {
        String sql = "DELETE FROM " + PATIENT_TABLE +
            " WHERE PatientID = ? AND PatientFirstName = ? AND PatientLastName = ? AND PatientBirthdate = ?";
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(patientID));
            statement.setString(2, patientFirstName);
            statement.setString(3, patientLastName);
            statement.setString(4, patientBirthdate);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean transferPatientDatabase(String patientFirstName, String patientLastName, String patientBirthdate) throws FileNotFoundException {
        String sql = "UPDATE " + PATIENT_TABLE +
            " SET PatientStatus = 'Recover' WHERE PatientFirstName = ? AND PatientLastName = ? AND PatientBirthdate = ? AND PatientStatus <> 'Recover'";
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patientFirstName);
            statement.setString(2, patientLastName);
            statement.setString(3, patientBirthdate);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String[][] getPatientListFromDatabase() throws SQLException, FileNotFoundException {
        String sql = patientListQuery();
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet entries = statement.executeQuery()) {
            List<String[]> rows = new ArrayList<>();
            while (entries.next()) {
                rows.add(new String[] {
                    entries.getString(1), // id
                    entries.getString(2), // firstName
                    entries.getString(3), // lastName
                    entries.getString(4), // birthdate
                    entries.getString(5)  // status
                });
            }
            return rows.toArray(new String[0][5]);
        }
    }

    public static int[] getPatientCount() throws SQLException, FileNotFoundException {

        String[][] entries = getPatientListFromDatabase();
        int[] counts = new int[3];
        int sickCount = 0;
        int recoverCount = 0;
        for (String[] row : entries) {
            if (row.length < 5 || row[4] == null) {
                continue;
            }
            if ("Sick".equalsIgnoreCase(row[4])) {
                sickCount++;
            }
            else if ("Recover".equalsIgnoreCase(row[4])) {
                recoverCount++;
            }
        }
        counts[0] = sickCount;
        counts[1] = recoverCount;
        counts[2] = counts[0] + counts[1];

        return counts;
    }

    public static double returnAverageAge() throws SQLException, FileNotFoundException {
        String[][] entries = getPatientListFromDatabase();
        if (entries.length == 0) {
            return 0;
        }

        LocalDate today = LocalDate.now();
        double ageSum = 0;
        int counter = 0;

        for (String[] line : entries) { // [id, firstname, lastname, birthdate, status]
            if (line.length < 4) {
                continue;
            }
            LocalDate birthDate = parseBirthdate(line[3]);
            if (birthDate == null || birthDate.isAfter(today)) {
                continue;
            }
            counter++;
            ageSum += Period.between(birthDate, today).getYears();
        }

        return counter == 0 ? 0 : (ageSum / counter);
    }

    public static void sortPatientsByID() throws SQLException, FileNotFoundException {
        patientListOrderBySql = "PatientID";
    }

    public static void sortPatientsByAge() throws SQLException, FileNotFoundException {
        // Youngest -> oldest (newest birthdate first)
        patientListOrderBySql = PARSED_BIRTHDATE_SQL + " DESC, PatientID ASC";
    }

    public static void sortPatientsByName(String firstOrLast) throws SQLException, FileNotFoundException {
        if ("First".equalsIgnoreCase(firstOrLast)) {
            patientListOrderBySql = "PatientFirstName, PatientID";
        } else if ("Last".equalsIgnoreCase(firstOrLast)) {
            patientListOrderBySql = "PatientLastName, PatientID";
        } else {
            patientListOrderBySql = DEFAULT_ORDER_BY;
        }
    }

    public static boolean exportDatabaseEntriesToCSV() throws SQLException, IOException {
        Path sourcePath = Paths.get("Patient_Records.csv");

        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + PATIENT_TABLE);
             ResultSet result = statement.executeQuery();
             BufferedWriter fileWriter = Files.newBufferedWriter(sourcePath, StandardCharsets.UTF_8)) {

            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                fileWriter.write(escapeCsv(metaData.getColumnName(i)));
                if (i < columnCount) {
                    fileWriter.write(",");
                }
            }
            fileWriter.newLine();

            while (result.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    fileWriter.write(escapeCsv(result.getString(i)));
                    if (i < columnCount) {
                        fileWriter.write(",");
                    }
                }
                fileWriter.newLine();
            }
        }

        String userHome = System.getProperty("user.home");
        Path downloadsFolderPath = Paths.get(userHome, "Downloads");
        Path destinationPath = downloadsFolderPath.resolve(sourcePath.getFileName());

        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            System.err.println("Error copying the file: " + e.getMessage());
            return false;
        }
    }

    public static void printDatabase() throws SQLException, FileNotFoundException {
        String sqlStatement = "SELECT * FROM " + PATIENT_TABLE;
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(sqlStatement);
             ResultSet entries = statement.executeQuery()) {
            while (entries.next()) {
                System.out.printf("[%d %s %s %s %s]\n",
                    entries.getInt("PatientID"),
                    entries.getString("PatientFirstName"),
                    entries.getString("PatientLastName"),
                    entries.getString("PatientBirthdate"),
                    entries.getString("PatientStatus"));
            }
        }
    }
}
