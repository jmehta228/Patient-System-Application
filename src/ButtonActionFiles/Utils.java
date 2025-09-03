package ButtonActionFiles;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Utils {
    final static String FILE_NAME = "patientRecords.txt";

    static Date date = new Date();
    static LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    final static int CURRENT_MONTH = localDate.getMonthValue();
    final static int CURRENT_DAY = localDate.getDayOfMonth();
    final static int CURRENT_YEAR = localDate.getYear();

    public static String isBirthValid(int month, int day, int year) {
        //check if the given birth is valid.
        String stringMonth = "";
        String stringDay = "";
        String stringYear = "";
        String stringBirthdate = "";

        // checking base range of month
        if (month >= 1 && month <= 12) {
            // check is month is one/two digit number
            if (month <= 9) {
                stringMonth += "0" + month + "/";
            }
            else {
                stringMonth += month + "/";
            }
        }
        else {
            stringMonth += "Invalid";
        }

        // checking base range of days in month
        if (day >= 1 && day <= 31) {
            // checking if day is one/two digit number
            if (day <= 9) {
                stringDay += "0" + day + "/";
            }
            else {
                stringDay += day + "/";
            }
        }
        else {
            stringDay += "Invalid";
        }

        // checking base range of year
        if (year >= 1922 && year <= 2022) {
            stringYear += year;
        }
        else {
            stringYear += "Invalid";
        }

        // combining month/day/year string
        stringBirthdate += stringMonth + stringDay + stringYear;

        // accurate examples of invalid birthdates
        if ((stringBirthdate.startsWith("02/29/")) && (Integer.parseInt(stringYear) % 4 != 0)) {
            stringBirthdate = "Invalid";
        }

        if (stringBirthdate.startsWith("04/31") || stringBirthdate.startsWith("06/31") || stringBirthdate.startsWith("09/31") || stringBirthdate.startsWith("11/31")) {
            stringBirthdate = "Invalid";
        }

        if (stringBirthdate.contains("Invalid")) {
            stringBirthdate = "Invalid";
        }
        // return statement for birthdate, either returns birthdate in MM/DD/YYYY or "Invalid"
        return stringBirthdate;
    }

    public static String addPatient(String name, String birth, String fileName) throws IOException {
        // Add a new patient record to the file.
        // if given birth is not valid, then patient will not be added into the file.
        // Birth must save in a format of Month/Day/Year, in total length of 10, such
        // as “02/03/2022”, “11/12/2001”, “01/24/1998”, “12/01/1980” and so on.
        String patientAddStatus;
        // check if first digit of 'month' in string is zero or non-zero
        int month;
        int firstDigitOfMonth = Integer.parseInt(birth.substring(0, 1));
        if (firstDigitOfMonth == 0) {
            month = Integer.parseInt(birth.substring(1, 2));
        }
        else {
            month = Integer.parseInt(birth.substring(0, 2));
        }
        // check if first digit of 'day' in string is zero or non-zero
        int day;
        int firstDigitOfDay = Integer.parseInt(birth.substring(3, 4));
        if (firstDigitOfDay == 0) {
            day = Integer.parseInt(birth.substring(4, 5));
        }
        else {
            day = Integer.parseInt(birth.substring(3, 5));
        }
        int year = Integer.parseInt(birth.substring(6, 10));
        List<String> patientList = new ArrayList<>();
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            String patient = scan.nextLine();
            patientList.add(patient);
        }
        if (isBirthValid(month, day, year).equals("Invalid")) {
            patientAddStatus = "Patient not added";
        }
        else {
            if (patientList.contains(name + " " + isBirthValid(month, day, year) + " Sick")) {
                return "Patient not added";
            }
            patientList.add(name + " " + isBirthValid(month, day, year) + " Sick");
            PrintWriter out = new PrintWriter(file);
            for (String s : patientList) {
                out.println(s);
            }
            patientAddStatus = "Patient added";
            out.close();
        }
        scan.close();
        return patientAddStatus;
    }


    public static boolean deletePatient(String name, String birthdate, String fileName) throws IOException {
        // Delete an existing patient record from the file
        // can’t delete if the patient still sick.
        // if there are two patientList occur with the same name,
        // and they are both not sick, only delete the first one.
        // Otherwise, delete the first patient who is not sick.
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        List<String> patientList = new ArrayList<>();
        boolean output = false;

        while (scan.hasNextLine()) {
            String patient = scan.nextLine();
            patientList.add(patient);
        }

        for (int i = 0; i < patientList.size(); i++) {
            if ((patientList.get(i).contains("recover")) && (patientList.get(i).contains(name)) && (patientList.get(i).contains(birthdate))) {
                patientList.remove(i);
                output = true;
                break;
            }
        }
        PrintWriter out = new PrintWriter(fileName);
        for (String p : patientList) {
            out.println(p);
        }
        out.close();
        scan.close();
        return output;
    }

    public static boolean transferPatient(String name, String birthdate, String fileName) throws IOException {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        List<String> patientList = new ArrayList<>();
        boolean output = false;

        while (scan.hasNextLine()) {
            String patient = scan.nextLine();
            patientList.add(patient);
        }

        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).contains("Sick") && patientList.get(i).contains(name) && patientList.get(i).contains(birthdate)) {
                String patientLine = patientList.get(i);
                String[] patientLineArray = patientLine.split("\\s+");
                patientLineArray[3] = "Recover";
                String recoveredPatientLine = patientLineArray[0] + " " + patientLineArray[1] + " " + patientLineArray[2] + " " + patientLineArray[3];
                patientList.set(i, recoveredPatientLine);
                output = true;
                break;
            }
        }
        PrintWriter out = new PrintWriter(fileName);
        for (String p : patientList) {
            out.println(p);
        }
        out.close();
        scan.close();
        return output;
    }

    public static int countPatients(String status, String fileName) throws FileNotFoundException {
        // return numbers of sick patients or recovery patients in the file.
        // if client given “” (empty string), then return the total number of patients.
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        List<String> patientList = new ArrayList<>();
        int sickCount = 0;
        int recoverCount = 0;
        int totalCount = 0;

        while (scan.hasNextLine()) {
            String patient = scan.nextLine();
            patientList.add(patient);
            totalCount++;
        }

        for (String s : patientList) {
            if (s.contains("Sick")) {
                sickCount++;
            }
            if (s.contains("Recover")) {
                recoverCount++;
            }
        }

        if (status.equals("Sick")) {
            return sickCount;
        }
        else if (status.equals("Recover")) {
            return recoverCount;
        }
        else if (status.equals("total")) {
            return totalCount;
        }
        else {
            return 0;
        }
    }

    public static double averageAge(String fileName) throws FileNotFoundException {
        // find the average age for all patients in the file
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File not found: " + fileName);
            throw new FileNotFoundException("File not found: " + fileName);
        }

        Scanner scan = new Scanner(file);
        List<String> patientList = new ArrayList<>();
        List<String> birthDateList = new ArrayList<>();
        List<Integer> ageList = new ArrayList<>();

        while (scan.hasNextLine()) {
            String patient = scan.nextLine();
            patientList.add(patient);
        }

        for (String record : patientList) {
            try {
                if (record.contains("Sick")) {
                    birthDateList.add(record.substring(record.length() - 15, record.length() - 5));
                }
                else if (record.contains("Recover")) {
                    birthDateList.add(record.substring(record.length() - 18, record.length() - 8));
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Error parsing line: " + record);
                continue; // Skip this entry if there's an error
            }
        }

        int age;
        int parsedYear;
        int parsedMonth;
        int parsedDay;

        for (String s : birthDateList) {
            try {
                parsedYear = Integer.parseInt(s.substring(6, 10));
                parsedMonth = Integer.parseInt(s.substring(0, 2));
                parsedDay = Integer.parseInt(s.substring(3, 5));
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                System.out.println("Error parsing date: " + s);
                continue;
            }

            if (parsedMonth > CURRENT_MONTH) {
                age = (CURRENT_YEAR - parsedYear) - 1;
            } else if (parsedMonth == CURRENT_MONTH) {
                if (parsedDay > CURRENT_DAY) {
                    age = (CURRENT_YEAR - parsedYear) - 1;
                } else {
                    age = CURRENT_YEAR - parsedYear;
                }
            } else {
                age = CURRENT_YEAR - parsedYear;
            }
            ageList.add(age);
        }
        scan.close();

        int sumOfPatientAges = 0;
        for (Integer integer : ageList) {
            sumOfPatientAges += integer;
        }

        if (ageList.isEmpty()) {
            return 0;
        }

        return (double) sumOfPatientAges / ageList.size();
    }


    private static int[] parseBirthdate(String birthdate) {
        String[] parts = birthdate.split("/");
        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        return new int[]{year, month, day};
    }

    private static int compareBirthdates(String birthdate1, String birthdate2) {
        int[] date1 = parseBirthdate(birthdate1);
        int[] date2 = parseBirthdate(birthdate2);
        for (int i = 0; i < 3; i++) {
            if (date1[i] != date2[i]) {
                return Integer.compare(date1[i], date2[i]);
            }
        }
        return 0;
    }

    private static int partition(List<Patient> patients, int low, int high) {
        String pivot = patients.get(high).getBirthdate();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compareBirthdates(patients.get(j).getBirthdate(), pivot) <= 0) {
                i++;
                Patient temp = patients.get(i);
                patients.set(i, patients.get(j));
                patients.set(j, temp);
            }
        }
        Patient temp = patients.get(i + 1);
        patients.set(i + 1, patients.get(high));
        patients.set(high, temp);
        return i + 1;
    }

    public static void quickSort(List<Patient> patients, int low, int high) {
        if (low < high) {
            int pi = partition(patients, low, high);
            quickSort(patients, low, pi - 1);
            quickSort(patients, pi + 1, high);
        }
    }

    public static void sortPatientsByAge(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        List<String> patientList = new ArrayList<>();
        List<Patient> patientObjList = new ArrayList<>();

        if (scan.hasNextLine()) {
            scan.nextLine();
        }

        while (scan.hasNextLine()) {
            String patient = scan.nextLine();
            patientList.add(patient);
        }
        scan.close();

        for (String s : patientList) {
            patientObjList.add(new Patient(s));
        }

        quickSort(patientObjList, 0, patientObjList.size() - 1);

        PrintWriter out = new PrintWriter(file);
        for (Patient patient : patientObjList) {
            out.println(patient.toString());
        }
        out.close();
    }

    public static void sortPatientsByName(String firstOrLast, String fileName) throws FileNotFoundException {
        // modify file by sorting patients by first name or last name for all patients from a-z
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        List<String> patientList = new ArrayList<>(); // initial patient arraylist

        List<String> patientFirstLetterFirstNameList = new ArrayList<>();
        List<Character> patientFirstNameFirstCharList = new ArrayList<Character>();
        List<Integer> patientFirstNameFirstCharDecList = new ArrayList<>();

        List<String> patientFirstLetterLastNameList = new ArrayList<>();
        List<Character> patientLastNameFirstCharList = new ArrayList<Character>();
        List<Integer> patientLastNameFirstCharDecList = new ArrayList<>();

        List<String> newPatientList = new ArrayList<>();

        if (firstOrLast.equals("first")) {

            while (scan.hasNextLine()) {
                String patient = scan.nextLine();
                patientList.add(patient);
            }

            for (String s : patientList) { // taking the first letter of patients first name and adding to arraylist (String)
                patientFirstLetterFirstNameList.add(s.substring(0, 1));
            }

            for (String s : patientFirstLetterFirstNameList) { // taking the first letter of patients first name and adding to arraylist (char)
                patientFirstNameFirstCharList.add(s.charAt(0));
            }

            for (int i = 0; i < patientList.size(); i++) { // adding first letter of f.n. (String) to patientList
                patientList.set(i, patientList.get(i) + " " + patientFirstLetterFirstNameList.get(i));
            }

            for (Character character : patientFirstNameFirstCharList) {
                patientFirstNameFirstCharDecList.add((int) character);
            }

            for (int i = 0; i < patientFirstNameFirstCharDecList.size(); i++) { // sorting values in ascending order
                for (int k = 0; k < patientFirstNameFirstCharDecList.size(); k++) {
                    int tempOne = patientFirstNameFirstCharDecList.get(i);
                    int tempTwo = patientFirstNameFirstCharDecList.get(k);
                    if (tempOne < tempTwo) {
                        patientFirstNameFirstCharDecList.set(i, tempTwo);
                        patientFirstNameFirstCharDecList.set(k, tempOne);
                    }
                }
            }

            for (Integer integer : patientFirstNameFirstCharDecList) {
                for (String s : patientList) {
                    if (integer == ((int) (s.substring(s.length() - 1)).charAt(0))) {
                        newPatientList.add(s);
                    }
                }
            }

            for (int i = 0; i < newPatientList.size(); i++) {
                for (int k = i + 1; k < newPatientList.size(); k++) {
                    if (newPatientList.get(i).equals(newPatientList.get(k))) {
                        newPatientList.remove(k);
                        k--;
                    }
                }
            }

            for (int i = 0; i < newPatientList.size(); i++) {
                newPatientList.set(i, newPatientList.get(i).substring(0, newPatientList.get(i).length() - 2));
            }

            PrintWriter out = new PrintWriter(fileName);
            for (String s : newPatientList) {
                out.println(s);
            }
            out.close();
            scan.close();
        } else if (firstOrLast.equals("last")) {
            List<String> lastNamesOfPatients = new ArrayList<>();
            while (scan.hasNextLine()) {
                String patient = scan.nextLine();
                patientList.add(patient);
                String[] info = patient.split(" ");
                lastNamesOfPatients.add(info[1]);
            }

            for (String s : lastNamesOfPatients) { // taking the first letter of patients last name and adding to arraylist (String)
                patientFirstLetterLastNameList.add(s.substring(0, 1));
            }

            for (String s : patientFirstLetterLastNameList) { // taking the first letter of patients last name and adding to arraylist (char)
                patientLastNameFirstCharList.add(s.charAt(0));
            }

            for (int i = 0; i < patientList.size(); i++) { // adding first letter of f.n. (String) to patientList
                patientList.set(i, patientList.get(i) + " " + patientFirstLetterLastNameList.get(i));
            }

            for (Character character : patientLastNameFirstCharList) {
                patientLastNameFirstCharDecList.add((int) character);
            }

            for (int i = 0; i < patientLastNameFirstCharDecList.size(); i++) { // sorting values in ascending order
                for (int k = 0; k < patientLastNameFirstCharDecList.size(); k++) {
                    int tempOne = patientLastNameFirstCharDecList.get(i);
                    int tempTwo = patientLastNameFirstCharDecList.get(k);
                    if (tempOne < tempTwo) {
                        patientLastNameFirstCharDecList.set(i, tempTwo);
                        patientLastNameFirstCharDecList.set(k, tempOne);
                    }
                }
            }

            for (Integer integer : patientLastNameFirstCharDecList) {
                for (String s : patientList) {
                    //char c = patientList.get(i).charAt(0);
                    if (integer == ((int) (s.substring(s.length() - 1)).charAt(0))) {
                        newPatientList.add(s);
                    }
                }
            }

            for (int i = 0; i < newPatientList.size(); i++) {
                for (int k = i + 1; k < newPatientList.size(); k++) {
                    if (newPatientList.get(i).equals(newPatientList.get(k))) {
                        newPatientList.remove(k);
                        k--;
                    }
                }
            }

            for (int i = 0; i < newPatientList.size(); i++) {
                newPatientList.set(i, newPatientList.get(i).substring(0, newPatientList.get(i).length()-2));
            }

            PrintWriter out = new PrintWriter(fileName);
            for (String s : newPatientList) {
                out.println(s);
            }
            out.close();
            scan.close();
        }
    }

    public static String[][] getPatientList(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        List<String> patientList = new ArrayList<>();
        while (scan.hasNextLine()) {
            String patientInfo = scan.nextLine();
            patientList.add(patientInfo);
        }
        patientList.remove(0);
        String[][] patientInformation = new String[patientList.size()][4];
        for (int i = 0; i < patientList.size(); i++) {
            String[] patientInfo = patientList.get(i).split("\\s+");
            patientInformation[i] = patientInfo;
        }
        return patientInformation;
    }

    public static boolean convertTextToCSV(String inputFilePath, String outputFilePath) {
        boolean output = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] columns = line.split("\\s+");
                String csvLine = String.join(",", columns);
                writer.write(csvLine);
                writer.newLine();
            }
            output = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static void copyFileToDownloads(String sourceFilePath) {
        String userHome = System.getProperty("user.home");
        String downloadsFolderPath = userHome + File.separator + "Downloads";
        Path sourcePath = Paths.get(sourceFilePath);
        String fileName = sourcePath.getFileName().toString();
        Path destinationPath = Paths.get(downloadsFolderPath, fileName);
        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error copying the file: " + e.getMessage());
        }
    }
}
