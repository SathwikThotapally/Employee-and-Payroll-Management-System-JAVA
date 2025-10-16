
# 🧾 Employee & Payroll Management System (Java, JDBC, MySQL)

A **console-based Java application** to manage employee information and payroll data, with **MySQL** for persistent storage and **JDBC** for database connectivity. It includes secure password-based access, full CRUD functionality, and payslip generation for employees.

---

## 🚀 Features

- 🔐 **Password-based authentication** with 3 login attempts
- ➕ Add a new employee record
- 📋 View all employee records
- ✏️ Update existing employee data
- ❌ Delete an employee from the system
- 🔍 Fetch specific employee details
- 🧾 Generate and display an employee payslip
- 💾 MySQL database integration using JDBC for persistent storage

---

## 🛠️ Tech Stack

- **Java** (Core Java, OOP)
- **JDBC** (Java Database Connectivity)
- **MySQL** (Relational Database)
- **Command-Line Interface**

---

## ⚙️ Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/Employee-and-Payroll-Management-System-JAVA.git
cd Employee-and-Payroll-Management-System-JAVA
````

### 2. Configure MySQL Database

Run the following SQL commands to set up your database:

```sql
CREATE DATABASE employees;

USE employees;

CREATE TABLE EmployeeData (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    phone VARCHAR(10) UNIQUE NOT NULL,
    department VARCHAR(50),
    position VARCHAR(50),
    basic_salary DOUBLE,
    hra DOUBLE,
    da DOUBLE,
    pf DOUBLE,
    tax DOUBLE,
    net_salary
);
```

### 3. Update Database Credentials in Code

In your `DatabaseConnection.java` or wherever the JDBC URL is set, replace with your actual MySQL details:

```java
String url = "jdbc:mysql://localhost:3306/employees";
String username = "root";
String password = "your_mysql_password";
```

### 4. Compile and Run the Application

```bash
javac Main.java
java Main
```

---

## 🔐 Authentication

* The system uses a **simple authentication mechanism** before granting access.
* Users must enter a valid password (hardcoded for demo).
* A maximum of **3 attempts** is allowed.
* On failure, the system exits to prevent unauthorized access.

> 🔧 You can configure or replace the password in the code for your own setup.

---

## 🧾 Sample Console Output

```
Enter your password:
Access Granted!

===== Welcome to Employee Payroll Management =====
Please Enter your choice from below
1. Add a New Employee
2. View All Employees Records
3. Update an Employee Record
4. Delete an Employee Record
5. Fetch Details of an Employee
6. Generate payslip of an Employee
7. Exit
```

---

## 📌 Future Enhancements

* ✅ Add GUI using JavaFX or Swing
* ✅ Export payslips as PDFs
* ✅ Role-based access (Admin, HR)
* ✅ Email notifications for salary slip delivery

---

## 👤 Author

* Sathwik Thotapally
* Github: https://github.com/SathwikThotapally
* Email: sathwikthotapally@gmail.com

---
