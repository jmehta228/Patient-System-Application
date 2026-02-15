# Database Setup Guide

## Error: Access Denied for User 'root'@'localhost'

This error means one of the following:
1. MySQL password is incorrect in config.properties
2. MySQL user doesn't have proper permissions
3. MySQL server is not running
4. Database doesn't exist yet

---

## Solution Steps

### Step 1: Verify MySQL is Running

```bash
# Check if MySQL is running
mysql.server status

# If not running, start it:
mysql.server start

# Alternative (if using Homebrew):
brew services list
brew services start mysql
```

---

### Step 2: Test MySQL Connection

Try connecting to MySQL with the credentials from your config.properties:

```bash
mysql -u root -p
# Enter password: Jugu9408$!
```

If this fails, you need to:
1. Reset your MySQL root password, or
2. Update config.properties with the correct password

---

### Step 3: Create Database and Table

Once you can connect to MySQL, run these commands:

```sql
-- Create the database
CREATE DATABASE IF NOT EXISTS PatientSystemApplication
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Use the database
USE PatientSystemApplication;

-- Create the PatientData table
CREATE TABLE IF NOT EXISTS PatientData (
    PatientID         INT AUTO_INCREMENT PRIMARY KEY,
    PatientFirstName  VARCHAR(255) NOT NULL,
    PatientLastName   VARCHAR(255) NOT NULL,
    PatientBirthdate  VARCHAR(50)  NOT NULL,
    PatientStatus     VARCHAR(50)  NOT NULL DEFAULT 'Sick',

    INDEX idx_name (PatientFirstName, PatientLastName),
    INDEX idx_status (PatientStatus)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Verify table was created
DESCRIBE PatientData;

-- Check if table is empty
SELECT COUNT(*) FROM PatientData;
```

---

### Step 4: Grant Permissions (if needed)

If you get permission errors, grant proper permissions:

```sql
-- Grant all privileges on the database to root
GRANT ALL PRIVILEGES ON PatientSystemApplication.* TO 'root'@'localhost';

-- Flush privileges to apply changes
FLUSH PRIVILEGES;

-- Verify permissions
SHOW GRANTS FOR 'root'@'localhost';
```

---

### Step 5: Update config.properties (if needed)

If you need to change the password or create a new user:

**Option A: Reset Root Password**
```bash
# Stop MySQL
mysql.server stop

# Start MySQL in safe mode (skip grant tables)
sudo mysqld_safe --skip-grant-tables &

# Connect without password
mysql -u root

# Reset password
FLUSH PRIVILEGES;
ALTER USER 'root'@'localhost' IDENTIFIED BY 'YourNewPassword';
FLUSH PRIVILEGES;
exit;

# Restart MySQL normally
sudo killall mysqld
mysql.server start
```

**Option B: Create New User**
```sql
-- Connect as root
mysql -u root -p

-- Create new user
CREATE USER 'patientapp'@'localhost' IDENTIFIED BY 'SecurePassword123';

-- Grant permissions
GRANT ALL PRIVILEGES ON PatientSystemApplication.* TO 'patientapp'@'localhost';
FLUSH PRIVILEGES;

-- Update config.properties with new credentials
```

---

## Quick Setup Script

Save this as `setup_database.sql` and run it:

```sql
-- Create database
CREATE DATABASE IF NOT EXISTS PatientSystemApplication;

-- Use database
USE PatientSystemApplication;

-- Create table
CREATE TABLE IF NOT EXISTS PatientData (
    PatientID         INT AUTO_INCREMENT PRIMARY KEY,
    PatientFirstName  VARCHAR(255) NOT NULL,
    PatientLastName   VARCHAR(255) NOT NULL,
    PatientBirthdate  VARCHAR(50)  NOT NULL,
    PatientStatus     VARCHAR(50)  NOT NULL DEFAULT 'Sick',

    INDEX idx_name (PatientFirstName, PatientLastName),
    INDEX idx_status (PatientStatus)
) ENGINE=InnoDB;

-- Insert sample data (optional)
INSERT INTO PatientData (PatientFirstName, PatientLastName, PatientBirthdate, PatientStatus) VALUES
    ('John', 'Smith', '01/15/1985', 'Sick'),
    ('Jane', 'Doe', '12/31/1990', 'Recover'),
    ('Robert', 'Johnson', '06/20/1975', 'Sick');

-- Verify
SELECT * FROM PatientData;
```

Run it with:
```bash
mysql -u root -p < setup_database.sql
```

---

## Verify Setup

After completing the setup, verify everything works:

```bash
# Test connection
mysql -u root -p -e "USE PatientSystemApplication; SELECT COUNT(*) FROM PatientData;"

# Test from Java application
java -cp "out/production/Patient-System-Application:lib/*" TestAutoGenerate
# Select option 4 to test without inserting data
```

---

## Common Issues and Solutions

### Issue 1: "Unknown database 'PatientSystemApplication'"
**Solution:** The database doesn't exist. Run the CREATE DATABASE command.

### Issue 2: "Table 'PatientData' doesn't exist"
**Solution:** The table hasn't been created. Run the CREATE TABLE command.

### Issue 3: "Access denied for user 'root'@'localhost'"
**Solutions:**
- Verify password in config.properties is correct
- Reset MySQL root password
- Grant proper permissions to the user

### Issue 4: "Can't connect to local MySQL server"
**Solution:** MySQL is not running. Start it with `mysql.server start`.

### Issue 5: Password contains special characters
**Solution:** If your password has special characters like `$`, `!`, or `@`, they might need escaping in the connection string or config file.

---

## Alternative: Use MySQL Workbench

If you prefer a GUI tool:

1. Open MySQL Workbench
2. Create a new connection to localhost
3. Run the SQL commands from the Quick Setup Script
4. Verify the table structure and data

---

## Security Best Practices

### 1. Don't Use Root in Production
Create a dedicated user for the application:
```sql
CREATE USER 'patientapp'@'localhost' IDENTIFIED BY 'StrongPassword123!';
GRANT SELECT, INSERT, UPDATE, DELETE ON PatientSystemApplication.* TO 'patientapp'@'localhost';
```

### 2. Use Environment Variables
Instead of storing passwords in config.properties, use environment variables:
```java
String password = System.getenv("DB_PASSWORD");
```

### 3. Restrict Permissions
Only grant necessary permissions (SELECT, INSERT, UPDATE, DELETE), not ALL PRIVILEGES.

---

## Next Steps

Once your database is set up:

1. **Test the connection:**
   ```bash
   java -cp "out/production/Patient-System-Application:lib/*" TestAutoGenerate
   ```
   Select option 4 to view sample data without inserting.

2. **Populate with test data:**
   Select option 3 to generate 10 test patients.

3. **Run the main application:**
   ```bash
   java -cp "out/production/Patient-System-Application:lib/*" PatientSystemApplication
   ```

---

## Need More Help?

If you're still having issues, check:
- MySQL error logs: `/usr/local/var/mysql/*.err` (on macOS)
- Verify MySQL version: `mysql --version`
- Check MySQL port: `lsof -i :3306`
