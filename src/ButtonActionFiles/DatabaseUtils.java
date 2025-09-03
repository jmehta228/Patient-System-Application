package ButtonActionFiles;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;

import static ButtonActionFiles.GetPatientList.patientInformation;

public class DatabaseUtils {

    public static int insertPatientToDatabase(String patientFirstName, String patientLastName, String patientBirthdate) {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String MySQLUrl = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        try {
            final Connection connection = DriverManager.getConnection(MySQLUrl, user, password);
            final Statement statement = connection.createStatement();

            String update = String.format("INSERT INTO PatientData (PatientFirstName, PatientLastName, PatientBirthdate, PatientStatus)" +
                    "VALUES ('%s', '%s', '%s', 'Sick')", patientFirstName, patientLastName, patientBirthdate);
            int returnValue = statement.executeUpdate(update);

            connection.close();
            statement.close();

            return returnValue;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int deletePatientFromDatabase(String patientID, String patientFirstName, String patientLastName, String patientBirthdate) throws FileNotFoundException {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String MySQLUrl = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        try {
            Connection connection = DriverManager.getConnection(MySQLUrl, user, password);
            Statement statement = connection.createStatement();

            int patientIDNumber = Integer.parseInt(patientID);

            String update = String.format("DELETE FROM PatientData WHERE PatientID = '%d' AND PatientFirstName = '%s' AND PatientLastName = '%s' AND PatientBirthdate = '%s'",
                    patientIDNumber, patientFirstName, patientLastName, patientBirthdate);
            int returnValue = statement.executeUpdate(update);

            connection.close();
            statement.close();

            return returnValue;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean transferPatientDatabase(String patientFirstName, String patientLastName, String patientBirthdate) throws FileNotFoundException {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String MySQLUrl = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        try {
            Connection connection = DriverManager.getConnection(MySQLUrl, user, password);
            Statement statement = connection.createStatement();

            String update = String.format("UPDATE PatientData SET PatientStatus = 'Recover' WHERE PatientFirstName = '%s' AND PatientLastName = '%s' AND PatientBirthdate = '%s'", patientFirstName, patientLastName, patientBirthdate);
            statement.executeUpdate(update);

            connection.close();
            statement.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String[][] getPatientListFromDatabase() throws SQLException, FileNotFoundException {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String MySQLUrl = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        Connection connection = DriverManager.getConnection(MySQLUrl, user, password);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String listStatement = "SELECT * FROM PatientData";
        ResultSet entries = statement.executeQuery(listStatement);
        if (entries.next()) {
            entries.last();
            int rowCount = entries.getRow();
            entries.beforeFirst();
            String[][] patientEntries = new String[rowCount][5];

            int rowIndex = 0;
            while (entries.next()) {
                patientEntries[rowIndex][0] = entries.getString(1); // id
                patientEntries[rowIndex][1] = entries.getString(2); // firstName
                patientEntries[rowIndex][2] = entries.getString(3); // lastName
                patientEntries[rowIndex][3] = entries.getString(4); // birthdate
                patientEntries[rowIndex][4] = entries.getString(5); // status
                rowIndex++;
            }
            return patientEntries;
        }
        return null;
    }

    public static int[] getPatientCount() throws SQLException, FileNotFoundException {

        String[][] entries = getPatientListFromDatabase();
        int[] counts = new int[3];
        int sickCount = 0;
        int recoverCount = 0;
        if (entries == null) {
            counts[0] = 0;
            counts[1] = 0;
            counts[2] = 0;
            return counts;
        }
        for (int i = 0; i < entries.length; i++) {
            if (entries[i][4].equals("Sick")) {
                sickCount++;
            }
            else if (entries[i][4].equals("Recover")) {
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
        double ageSum = 0;
        int counter = 0;
        assert entries != null;
        for (String[] line : entries) { // [id, firstname, lastname, birthdate, status]
            String birthdate = line[3];
            int month = Integer.parseInt(birthdate.substring(0, 2));
            int day = Integer.parseInt(birthdate.substring(3, 5));
            int year = Integer.parseInt(birthdate.substring(6, 10));
            if (!Utils.isBirthValid(month, day, year).equals("Invalid")) {
                counter++;
                int tempAge = (int) calculateAge(month, day, year);
                System.out.println(tempAge);
                ageSum += tempAge;
            }
        }
        return (ageSum / counter);
    }

    private static double calculateAge(int month, int day, int year) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate birthDate = LocalDate.of(year, month, day);
        return Period.between(birthDate, localDate).getYears();
    }

    public static void sortPatientsByID() throws SQLException, FileNotFoundException {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String MySQLUrl = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        Connection connection = DriverManager.getConnection(MySQLUrl, user, password);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String sql = "SELECT * FROM PatientData ORDER BY PatientID";
        statement.execute(sql);

        connection.close();
        statement.close();
    }

    public static void sortPatientsByAge() throws SQLException, FileNotFoundException {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String MySQLUrl = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        Connection connection = DriverManager.getConnection(MySQLUrl, user, password);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String sql = "SELECT * FROM PatientData ORDER BY DATEDIFF(CURDATE(), PatientBirthdate) DESC";
        statement.execute(sql);

        connection.close();
        statement.close();
    }

    public static void sortPatientsByName(String firstOrLast) throws SQLException, FileNotFoundException {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String MySQLUrl = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        Connection connection = DriverManager.getConnection(MySQLUrl, user, password);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        if (firstOrLast.equals("First")) {
            String sql = "SELECT * FROM PatientData ORDER BY PatientFirstName";
            statement.execute(sql);
        }
        else if (firstOrLast.equals("Last")) {
            String sql = "SELECT * FROM PatientData ORDER BY PatientLastName";
            statement.execute(sql);
        }

        connection.close();
        statement.close();
    }

    public static boolean exportDatabaseEntriesToCSV() throws SQLException, IOException {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            props.load(input);
        }

        String MySQLUrl = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        boolean fileExported = true;
        Connection connection = DriverManager.getConnection(MySQLUrl, user, password);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM PatientData");
        ResultSet result = statement.executeQuery();
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter("Patient_Records.csv"));

        ResultSetMetaData metaData = result.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            fileWriter.write(metaData.getColumnName(i));
            if (i < columnCount) {
                fileWriter.write(",");
            }
        }
        fileWriter.newLine();

        while (result.next()) {
            for (int i = 1; i <= columnCount; i++) {
                fileWriter.write(result.getString(i));
                if (i < columnCount) {
                    fileWriter.write(",");
                }
            }
            fileWriter.newLine();
        }

        result.close();
        statement.close();
        connection.close();
        fileWriter.close();


        String userHome = System.getProperty("user.home");
        String downloadsFolderPath = userHome + File.separator + "Downloads";
        Path sourcePath = Paths.get("Patient_Records.csv");
        String fileName = sourcePath.getFileName().toString();
        Path destinationPath = Paths.get(downloadsFolderPath, fileName);
        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error copying the file: " + e.getMessage());
        }

        return fileExported;
    }

    public static void printDatabase() throws SQLException, FileNotFoundException {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String MySQLUrl = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        Connection connection = DriverManager.getConnection(MySQLUrl, user, password);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String sqlStatement = "SELECT * FROM PatientData";
        ResultSet entries = statement.executeQuery(sqlStatement);
        while (entries.next()) {
            System.out.printf("[%d %s %s %s %s]\n", entries.getInt("PatientID"), entries.getString("PatientFirstName"), entries.getString("PatientLastName"), entries.getString("PatientBirthdate"), entries.getString("PatientStatus"));
        }
    }
}