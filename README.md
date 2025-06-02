# 🎓 University Result Management System

This is a Java-based University Result Management System that allows administrators to manage students, courses, and marks, and allows students to view their academic results. It uses MySQL as the database backend and provides a simple GUI interface using Java Swing.

---

## 🧩 Modules

- **Admin Panel**: For adding students, courses, and entering marks.
- **Student Panel**: For students to view their results.
- **Database**: MySQL used for storing student, course, and result data.

---

## 🚀 Features

- 🔐 **Admin Login**
- 👨‍🎓 **Add Student Details**
- 📘 **Add Course Information**
- 🧮 **Enter Marks for Multiple Courses**
- 📊 **Auto-generate result status (Pass/Fail) based on marks**
- 🧑‍💻 **Student Login Portal**
- 📊 **View Academic Results**

---
## 🧑‍💼 Admin Functionalities

- Login securely using admin credentials
- Add new students with unique IDs
- Add new courses with associated codes
- Assign and manage marks per student per course

---
## 🛠️ Prerequisites

- Java JDK 23 installed
- MySQL Server installed and running
- IDE (optional): NetBeans or any Java IDE
- Git (optional) for cloning repo

---

## 🛠️ Technologies Used

* **Java Swing** – GUI development  
* **MySQL** – Database management  
* **JDBC** – Java Database Connectivity
---
## 📦 Libraries Used

### 1. **flatlaf-3.5.4.jar**
Used to provide modern, flat look-and-feel themes for Java Swing components.

### 2. **AbsoluteLayout.jar**
Supports absolute positioning of Swing components; commonly used with NetBeans GUI builder.

### 3. **mysql-connector-j-9.2.0.jar**
JDBC driver for MySQL. Enables the application to communicate with the MySQL database.

### 4. **jcalendar-1.4.jar**
Used for selecting dates (e.g., Date of Birth) in user-friendly calendar widgets.

### 5. **rs2xml.jar**
Facilitates populating `JTable` components directly from JDBC `ResultSet`.

---

## ☕ JDK Used

- **Java Development Kit (JDK) 23**  
  The application is compiled and run using JDK 23.

---
## 🖼️ Application Screenshots

### 1. Admin Login
![image](https://github.com/user-attachments/assets/4fa7e2b2-036f-4a6f-8290-165046eef6d8)

### 2. Add Student
![image](https://github.com/user-attachments/assets/f5adb960-e81e-4e04-901d-50bdc312c1fb)

### 3. Add Course
![image](https://github.com/user-attachments/assets/5b561688-39f0-4844-8308-2d6b9998de52)

### 4. Add Marks for Multiple Courses
![image](https://github.com/user-attachments/assets/6c416df6-a749-4df7-af19-51c17a9d75a9)

### 5. Student Login
![image](https://github.com/user-attachments/assets/daf60669-cc0e-485f-8de6-4a2a88857a9d)

### 6. Result View
![image](https://github.com/user-attachments/assets/d4290308-d071-44c8-ba2a-b1841fa73de3)

---

## 🗃️ Database Schema

### `course`

| Field      | Type         | Key | Description              |
|------------|--------------|-----|--------------------------|
| CourseID   | varchar(20)  | PK  | Unique identifier        |
| CourseName | varchar(100) |     | Full course name         |
| Department | varchar(100) |     | Department offering it   |

---

### `student`

| Field       | Type         | Key | Description             |
|-------------|--------------|-----|-------------------------|
| RegNo       | varchar(20)  | PK  | Student Registration No |
| StudentName | varchar(100) |     | Name of the student     |
| Department  | varchar(100) |     | Student’s department    |
| Batch       | varchar(50)  |     | Batch/Year group        |
| DateOfBirth | date         |     | DOB                     |
| BloodGroup  | varchar(10)  |     | Blood group             |
| ContactNo   | varchar(15)  |     | Phone number            |
| Address     | longtext     |     | Full address            |

---

### `studentmarks`

| Field      | Type         | Key        | Description                        |
|------------|--------------|------------|------------------------------------|
| RegNo      | varchar(20)  | PK (composite) | Foreign key to `student`         |
| CourseCode | varchar(20)  | PK (composite) | Foreign key to `course`          |
| CourseName | varchar(100) |            | Redundant copy for display        |
| Marks      | int          |            | Marks obtained                    |
| Result     | varchar(10)  | GENERATED  | Pass/Fail (computed from marks)   |

> 💡 The `Result` field is a **STORED GENERATED column** in MySQL, likely using a condition such as:
> ```sql
> GENERATED ALWAYS AS (IF(Marks >= 50, 'Pass', 'Fail')) STORED
> ```

---

### `users`

| Field      | Type         | Key | Description                  |
|------------|--------------|-----|------------------------------|
| id         | int          | PK  | Auto-incremented user ID     |
| username   | varchar(200) | UNI | Unique login name            |
| password   | varchar(255) |     | Encrypted password           |
| created_at | timestamp    |     | User creation time (default) |

---
## 📦 Installation & Setup

### 1. Clone the repository

```bash
git clone https://github.com/tamilamudan/Billing-Software.git
cd Billing-Software
```
### 2. Setup the MySQL Database

Log in to your MySQL server using terminal or MySQL Workbench:

```bash
mysql -u root -p
```

Run the following SQL commands to create the database and tables:
```sql
-- Create the database
CREATE DATABASE seminiproject;

-- Use the database
USE seminiproject;

-- Create course table
CREATE TABLE course (
    CourseID VARCHAR(20) NOT NULL PRIMARY KEY,
    CourseName VARCHAR(100) NOT NULL,
    Department VARCHAR(100) NOT NULL
);

-- Create student table
CREATE TABLE student (
    RegNo VARCHAR(20) NOT NULL PRIMARY KEY,
    StudentName VARCHAR(100) NOT NULL,
    Department VARCHAR(100) NOT NULL,
    Batch VARCHAR(50) NOT NULL,
    DateOfBirth DATE NOT NULL,
    BloodGroup VARCHAR(10) NOT NULL,
    ContactNo VARCHAR(15) NOT NULL,
    Address LONGTEXT
);

-- Create studentmarks table
CREATE TABLE studentmarks (
    RegNo VARCHAR(20) NOT NULL,
    CourseCode VARCHAR(20) NOT NULL,
    CourseName VARCHAR(100),
    Marks INT,
    Result VARCHAR(10) AS (
        CASE 
            WHEN Marks >= 40 THEN 'PASS' 
            ELSE 'FAIL' 
        END
    ) STORED,
    PRIMARY KEY (RegNo, CourseCode)
);

-- Create users table
CREATE TABLE users (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(200) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

```
### 3.🔌Configure Database Connection

Open `src/connection/ConnectionProvider.java` and update the credentials as needed:
```java
package connection;
import java.sql.*;

public class ConnectionProvider {
    public static Connection getCon() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            
            // Replace 'yourUsername' and 'yourPassword' with actual credentials
            String url = "jdbc:mysql://localhost:3306/seminiproject";
            String username = "yourUsername";
            String password = "yourPassword";

            Connection con = DriverManager.getConnection(url, username, password);
            return con;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
```
### 4. Build and Run the Application
#### Using the Command Line

Compile and run from the project root folder:

```bash
javac -d bin src/universityresultmanagementsystem/Login.java
java -cp bin Login
```
---
#### Using NetBeans IDE

1. Open NetBeans, select **File > Open Project**, and open the cloned project folder.  
2. Make sure your MySQL server is running.  
3. Update your database credentials in `ConnectionProvider.java` as shown above.  
4. Right-click on `Login.java` in the project explorer and select **Run File**.
---
## 🚦 How to Use

1. Run the Admin Panel to add students, courses, and marks.
2. Students can log in via Student Panel to check results.
3. Admin can manage student records and results.
