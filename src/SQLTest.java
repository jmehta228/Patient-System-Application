import ButtonActionFiles.DatabaseUtils;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class SQLTest {
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        DatabaseUtils.sortPatientsByAge();
        DatabaseUtils.printDatabase();
    }
}
