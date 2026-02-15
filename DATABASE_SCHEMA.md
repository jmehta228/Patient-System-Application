# Database Schema Documentation

## Overview
The Patient System Application uses a MySQL database with a single main table for storing patient records.

---

## Connection Details

**Database Name:** `PatientSystemApplication`
**Host:** `localhost:3306`
**Connection String:** `jdbc:mysql://localhost:3306/PatientSystemApplication`

Configuration is stored in: `config.properties`

---

## Table: PatientData

### Schema

```sql
CREATE TABLE PatientData (
    PatientID         INT AUTO_INCREMENT PRIMARY KEY,
    PatientFirstName  VARCHAR(255) NOT NULL,
    PatientLastName   VARCHAR(255) NOT NULL,
    PatientBirthdate  VARCHAR(50)  NOT NULL,
    PatientStatus     VARCHAR(50)  NOT NULL DEFAULT 'Sick'
);
```

### Column Details

| Column Name        | Data Type     | Constraints          | Description                                    |
|-------------------|---------------|----------------------|------------------------------------------------|
| `PatientID`       | INT           | PRIMARY KEY, AUTO_INCREMENT | Unique identifier for each patient (auto-generated) |
| `PatientFirstName`| VARCHAR(255)  | NOT NULL             | Patient's first name                           |
| `PatientLastName` | VARCHAR(255)  | NOT NULL             | Patient's last name                            |
| `PatientBirthdate`| VARCHAR(50)   | NOT NULL             | Birthdate in MM/dd/yyyy format                 |
| `PatientStatus`   | VARCHAR(50)   | NOT NULL, DEFAULT 'Sick' | Patient status: 'Sick' or 'Recover'       |

---

## Data Formats

### PatientBirthdate
- **Primary Format:** `MM/dd/yyyy` (e.g., `12/25/1990`)
- **Alternative Format:** `yyyy-MM-dd` (ISO format, also supported)
- **Examples:**
  - `01/15/1985`
  - `12/31/2000`
  - `1990-06-15` (also valid)

### PatientStatus
- **Values:** `'Sick'` or `'Recover'`
- **Default:** `'Sick'` (when new patient is inserted)
- **Case:** Case-insensitive in queries but typically stored as shown

---

## SQL Operations Used in Application

### 1. INSERT (Add Patient)
```sql
INSERT INTO PatientData
    (PatientFirstName, PatientLastName, PatientBirthdate, PatientStatus)
VALUES (?, ?, ?, 'Sick')
```

### 2. DELETE (Delete Patient)
```sql
DELETE FROM PatientData
WHERE PatientID = ?
    AND PatientFirstName = ?
    AND PatientLastName = ?
    AND PatientBirthdate = ?
```

### 3. UPDATE (Transfer Patient to Recovered)
```sql
UPDATE PatientData
SET PatientStatus = 'Recover'
WHERE PatientFirstName = ?
    AND PatientLastName = ?
    AND PatientBirthdate = ?
    AND PatientStatus <> 'Recover'
```

### 4. SELECT (Get All Patients)
```sql
SELECT * FROM PatientData
```

### 5. SELECT with Ordering
```sql
-- By ID (default)
SELECT * FROM PatientData ORDER BY PatientID

-- By First Name
SELECT * FROM PatientData ORDER BY PatientFirstName, PatientID

-- By Last Name
SELECT * FROM PatientData ORDER BY PatientLastName, PatientID

-- By Age (youngest to oldest)
SELECT * FROM PatientData
ORDER BY COALESCE(
    STR_TO_DATE(PatientBirthdate, '%m/%d/%Y'),
    STR_TO_DATE(PatientBirthdate, '%Y-%m-%d')
) DESC, PatientID ASC
```

---

## Query Result Format

When querying the database, results are returned as arrays:

```java
String[][] results = {
    {id, firstName, lastName, birthdate, status},
    // ... more rows
};
```

**Example:**
```java
{
    {"1", "John", "Smith", "01/15/1985", "Sick"},
    {"2", "Jane", "Doe", "12/31/1990", "Recover"},
    {"3", "Robert", "Johnson", "06/20/1975", "Sick"}
}
```

---

## Indexes and Optimization

### Recommended Indexes (if not already created)

```sql
-- Primary key (already exists via AUTO_INCREMENT)
-- PatientID is automatically indexed

-- For name-based queries
CREATE INDEX idx_patient_name ON PatientData(PatientFirstName, PatientLastName);

-- For status filtering
CREATE INDEX idx_patient_status ON PatientData(PatientStatus);

-- For birthdate sorting (if needed)
CREATE INDEX idx_patient_birthdate ON PatientData(PatientBirthdate);
```

---

## Database Setup SQL

### Create Database
```sql
CREATE DATABASE IF NOT EXISTS PatientSystemApplication
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

### Use Database
```sql
USE PatientSystemApplication;
```

### Create Table
```sql
CREATE TABLE IF NOT EXISTS PatientData (
    PatientID         INT AUTO_INCREMENT PRIMARY KEY,
    PatientFirstName  VARCHAR(255) NOT NULL,
    PatientLastName   VARCHAR(255) NOT NULL,
    PatientBirthdate  VARCHAR(50)  NOT NULL,
    PatientStatus     ENUM('Sick', 'Recover') NOT NULL DEFAULT 'Sick',

    INDEX idx_name (PatientFirstName, PatientLastName),
    INDEX idx_status (PatientStatus)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Note:** The application currently uses `VARCHAR(50)` for `PatientStatus`, but using `ENUM('Sick', 'Recover')` would be more efficient.

---

## Sample Data

### Example Records
```sql
INSERT INTO PatientData (PatientFirstName, PatientLastName, PatientBirthdate, PatientStatus) VALUES
    ('John', 'Smith', '01/15/1985', 'Sick'),
    ('Jane', 'Doe', '12/31/1990', 'Recover'),
    ('Robert', 'Johnson', '06/20/1975', 'Sick'),
    ('Mary', 'Williams', '03/10/1982', 'Sick'),
    ('Michael', 'Brown', '09/05/1995', 'Recover');
```

### Query Sample Data
```sql
-- All patients
SELECT * FROM PatientData;

-- Only sick patients
SELECT * FROM PatientData WHERE PatientStatus = 'Sick';

-- Only recovered patients
SELECT * FROM PatientData WHERE PatientStatus = 'Recover';

-- Count by status
SELECT PatientStatus, COUNT(*) as Count
FROM PatientData
GROUP BY PatientStatus;
```

---

## Statistics Queries

### Patient Counts
```sql
-- Total patients
SELECT COUNT(*) as TotalPatients FROM PatientData;

-- Sick patients
SELECT COUNT(*) as SickPatients FROM PatientData WHERE PatientStatus = 'Sick';

-- Recovered patients
SELECT COUNT(*) as RecoveredPatients FROM PatientData WHERE PatientStatus = 'Recover';
```

### Average Age Calculation
The application calculates average age programmatically in Java using:
```java
LocalDate birthDate = LocalDate.parse(birthdateString, DateTimeFormatter);
int age = Period.between(birthDate, LocalDate.now()).getYears();
```

Equivalent MySQL query:
```sql
SELECT AVG(
    TIMESTAMPDIFF(YEAR,
        STR_TO_DATE(PatientBirthdate, '%m/%d/%Y'),
        CURDATE()
    )
) as AverageAge
FROM PatientData;
```

---

## Data Validation

### Application-Level Validation
- **FirstName:** Required, non-empty
- **LastName:** Required, non-empty
- **Birthdate:** Must be in MM/dd/yyyy format, validated on input
- **Status:** Automatically set to 'Sick' on insert

### Database-Level Validation
- All fields are `NOT NULL`
- `PatientID` is auto-generated and unique
- Status defaults to 'Sick'

---

## CSV Export Format

When exporting to CSV, the format is:
```csv
PatientID,PatientFirstName,PatientLastName,PatientBirthdate,PatientStatus
1,John,Smith,01/15/1985,Sick
2,Jane,Doe,12/31/1990,Recover
3,Robert,Johnson,06/20/1975,Sick
```

---

## Auto-Population Tool Schema Usage

The auto-population tool generates data matching this schema:

```java
// Generated data format
PatientFirstName: Random from 150+ names
PatientLastName: Random from 140+ names
PatientBirthdate: Random date in MM/dd/yyyy format
PatientStatus: Always 'Sick' (default)
PatientID: Auto-generated by database
```

**Example Generation:**
```java
PatientDataGenerator.generateAndInsertPatients(10);
// Inserts 10 patients with:
// - Random names
// - Random birthdates (ages 0-100)
// - Status = 'Sick'
// - Auto-generated IDs
```

---

## Connection Management

### JDBC Configuration
```properties
# config.properties
db.url=jdbc:mysql://localhost:3306/PatientSystemApplication
db.user=root
db.password=your_password_here
```

### Connection Code Pattern
```java
Connection connection = DriverManager.getConnection(url, user, password);
PreparedStatement statement = connection.prepareStatement(sql);
// Execute query
connection.close();
```

---

## Backup and Restore

### Backup Database
```bash
mysqldump -u root -p PatientSystemApplication > backup.sql
```

### Restore Database
```bash
mysql -u root -p PatientSystemApplication < backup.sql
```

### Backup Single Table
```bash
mysqldump -u root -p PatientSystemApplication PatientData > patientdata_backup.sql
```

---

## Common Queries for Maintenance

### Clear All Data
```sql
DELETE FROM PatientData;
ALTER TABLE PatientData AUTO_INCREMENT = 1;
```

### Reset Auto-Increment
```sql
ALTER TABLE PatientData AUTO_INCREMENT = 1;
```

### Check Table Status
```sql
SHOW TABLE STATUS LIKE 'PatientData';
```

### View Table Structure
```sql
DESCRIBE PatientData;
-- or
SHOW CREATE TABLE PatientData;
```

---

## Performance Considerations

- **Primary Key:** `PatientID` is indexed automatically
- **Recommended:** Add indexes on frequently queried columns (FirstName, LastName, Status)
- **Storage:** VARCHAR columns use only the space needed for actual data
- **Engine:** InnoDB recommended for ACID compliance and foreign key support

---

## Migration Notes

If you need to modify the schema:

### Add Column
```sql
ALTER TABLE PatientData
ADD COLUMN PatientPhone VARCHAR(20) AFTER PatientBirthdate;
```

### Modify Column
```sql
ALTER TABLE PatientData
MODIFY COLUMN PatientStatus ENUM('Sick', 'Recover') NOT NULL DEFAULT 'Sick';
```

### Add Index
```sql
CREATE INDEX idx_birthdate ON PatientData(PatientBirthdate);
```

---

## Summary

**Table Name:** `PatientData`
**Primary Key:** `PatientID` (auto-increment)
**Total Columns:** 5
**Required Fields:** All (no nullable columns)
**Status Values:** 'Sick', 'Recover'
**Date Format:** MM/dd/yyyy (primary), yyyy-MM-dd (supported)
**Character Set:** utf8mb4 (recommended)
**Engine:** InnoDB (recommended)
