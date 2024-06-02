package ButtonActionFiles;

import java.util.*;

public class Patient {
    private String name;
    private String birthdate;
    private boolean isSick;

    public Patient() {}

    public Patient(String name, String birthdate, boolean isSick) {
        this.name = name;
        this.birthdate = birthdate;
        this.isSick = isSick;
    }

    public Patient(String line) {
        String[] lineArray = line.split("\\s+");
        this.name = lineArray[0] + " " + lineArray[1];
        this.birthdate = lineArray[2];
        this.isSick = lineArray[3].equals("sick");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return this.getName() + " " + this.getBirthdate() + " " + (this.isSick() ? "sick" : "recover");
    }
}
