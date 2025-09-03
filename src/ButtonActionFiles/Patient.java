package ButtonActionFiles;

public class Patient {
    private int id;
    private String firstName;
    private String lastName;
    private String birthdate;
    private boolean isSick;

    public Patient() {}

    public Patient(String firstName, String lastName, String birthdate, boolean isSick) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.isSick = isSick;
    }

    public Patient(String line) {
        String[] lineArray = line.split("\\s+");
        this.lastName = lineArray[0] + " " + lineArray[1];
        this.birthdate = lineArray[2];
        this.isSick = lineArray[3].equals("Sick");
    }

    public Patient(int id, String name, String birthdate, boolean isSick) {
        this.id = id;
        this.lastName = name;
        this.birthdate = birthdate;
        this.isSick = isSick;
    }

    public String getName() {
        return lastName;
    }

    public void setName(String name) {
        this.lastName = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isSick() {
        return isSick;
    }

    public void setSick(boolean sick) {
        isSick = sick;
    }

    @Override
    public String toString() {
        return this.id + " " + this.getName() + " " + this.getBirthdate() + " " + (this.isSick() ? "Sick" : "Recover");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
