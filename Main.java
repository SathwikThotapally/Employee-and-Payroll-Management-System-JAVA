import java.sql.*;
import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    private static final String url = "jdbc:mysql://localhost:3306/Employees";
    private static final String username = "root";
    private static final String password = "your_password";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int tries = 3;
        while (tries > 0) {
            System.out.println("Enter your password: ");
            String inputPass = sc.next();
            if (inputPass.equals(password)) {
                System.out.println("Access Granted!");
                break;
            } else {
                tries--;
                System.out.println("Wrong password!. Try again!");
                if (tries == 0) {
                    System.out.println("Too many failed attempts. Exiting the System...");
                    System.exit(0);
                }
            }
        }

        while (true) {
            System.out.println("\n===== Welcome to Employee Payroll Management =====");
            System.out.println("Please Enter your choice from below");
            System.out.println("1. Add a New Employee");
            System.out.println("2. View All Employees Records");
            System.out.println("3. Update an Employee Record");
            System.out.println("4. Delete an Employee Record");
            System.out.println("5. Fetch Details of an Employee");
            System.out.println("6. Generate payslip of an Employee");
            System.out.println("7. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addEmployee(sc);
                case 2 -> viewEmployees();
                case 3 -> updateEmployee(sc);
                case 4 -> deleteEmployee(sc);
                case 5 -> findEmployee(sc);
                case 6 -> generatePayslip(sc);
                case 7 -> {
                    System.out.println("Exiting the console.....");
                    System.exit(0);
                }
            }
        }
    }

    static void addEmployee(Scanner sc) {
        System.out.println("Enter Name: ");
        String name = sc.next();
        System.out.println("Enter Email: ");
        String email = sc.next();
        System.out.println("Enter Phone Number: ");
        String phone = sc.next();
        System.out.println("Enter Department: ");
        String department = sc.next();
        System.out.println("Enter Position: ");
        String position = sc.next();
        System.out.println("Enter Basic Salary: ");
        double basic = sc.nextInt();

        double hra = 0.20 * basic;
        double da = 0.10 * basic;
        double pf = 0.12 * basic;
        double gross = basic + hra + da;
        double tax = 0.10 * gross;
        double netSalary = gross - (pf + tax);


        try (Connection con = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO EmployeeData(name,email,phone,department,position,basic_salary,hra,da,pf,tax,net_salary) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, department);
            ps.setString(5, position);
            ps.setDouble(6, basic);
            ps.setDouble(7, hra);
            ps.setDouble(8, da);
            ps.setDouble(9, pf);
            ps.setDouble(10, tax);
            ps.setDouble(11, netSalary);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void viewEmployees() {
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM EmployeeData;");
            System.out.println("\n===== Employee Payroll Records =====");
            while (rs.next()) {
                System.out.println(rs.getInt("id") +
                        " | " + rs.getString("name") +
                        " | " + rs.getString("email") +
                        " | " + rs.getString("phone") +
                        " | " + rs.getString("department") +
                        " | " + rs.getString("position") +
                        " | " + rs.getDouble("basic_salary") +
                        " | " + rs.getDouble("hra") +
                        " | " + rs.getDouble("da") +
                        " | " + rs.getDouble("pf") +
                        " | " + rs.getDouble("tax") +
                        " | " + rs.getDouble("net_salary"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateEmployee(Scanner sc) {
        System.out.println("Enter the ID of the employee you want to update: ");
        int id = sc.nextInt();
        String[] options = {"Name", "Email", "Phone", "Department", "Position", "Basic Salary"};
        int n = options.length;
        System.out.println("Which of the field do you want to update?");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        int choice = sc.nextInt();

        String field = "";
        String newValue = "";
        double newDoubleValue = 0;


        switch (choice) {
            case 1 -> {
                field = "name";
                System.out.println("Enter new name");
                newValue = sc.next();
            }
            case 2 -> {
                field = "email";
                System.out.println("Enter new age");
                newValue = sc.next();
            }
            case 3 -> {
                field = "phone";
                System.out.println("Enter new email");
                newValue = sc.next();
            }
            case 4 -> {
                field = "department";
                System.out.println("Enter new phone number");
                newValue = sc.next();
            }
            case 5 -> {
                field = "position";
                System.out.println("Enter new branch");
                newValue = sc.next();
            }
            case 6 -> {
                field = "basic_salary";
                System.out.println("Enter new salary");
                newDoubleValue = sc.nextDouble();
            }
            default -> {
                System.out.println("Invalid Choice");
                return;
            }
        }
        try(Connection con = DriverManager.getConnection(url, username, password)){
            String sql = "UPDATE EmployeeData SET "+field+" =? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            if(field.equals("basic_salary")){
                ps.setDouble(1,newDoubleValue);
            }else{
                ps.setString(1,newValue);
            }
            ps.setInt(2,id);
            ps.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    static void deleteEmployee(Scanner sc){
        System.out.println("Enter the ID of the Employee u want to delete");
        int id = sc.nextInt();

        try(Connection con = DriverManager.getConnection(url, username, password)){
            String sql = "DELETE FROM EmployeeData WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
            System.out.println("Record deleted successfully!");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void findEmployee(Scanner sc){
        System.out.println("Enter the field based on which u want to find the employee details: ");
        System.out.println("1. ID");
        System.out.println("2. Email");
        System.out.println("3. Phone");
        int choice = sc.nextInt();

        String field = "";
        int num = 0;
        String value = "";

        switch (choice){
            case 1 -> {
                field = "id";
                System.out.println("Enter the ID: ");
                num = sc.nextInt();
            }
            case 2 -> {
                field = "email";
                System.out.println("Enter the Email: ");
                value = sc.next();
            }
            case 3 -> {
                field = "phone";
                System.out.println("Enter the Phone Number: ");
                value = sc.next();
            }
            default -> {
                System.out.println("Invalid Choice");
            }
        }
        try(Connection con = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM EmployeeData WHERE "+field+" =?";
            PreparedStatement ps = con.prepareStatement(sql);
            if(value.equals("id")){
                ps.setInt(1,num);
            }else{
                ps.setString(1,value);
            }
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                System.out.println("\n====== Employee Details Fetched Successfully! =====");
                System.out.println(rs.getInt("id") +
                        " | " + rs.getString("name") +
                        " | " + rs.getInt("email") +
                        " | " + rs.getString("phone") +
                        " | " + rs.getString("department") +
                        " | " + rs.getString("position") +
                        " | " + rs.getDouble("basic_salary") +
                        " | " + rs.getDouble("hra") +
                        " | " + rs.getDouble("da") +
                        " | " + rs.getDouble("pf") +
                        " | " + rs.getDouble("tax") +
                        " | " + rs.getDouble("net_salary"));
            }else{
                System.out.println("Invalid "+field+" provided");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    static void generatePayslip(Scanner sc){
        System.out.println("Enter the ID of the employee to generate payslip: ");
        int id = sc.nextInt();

        try(Connection con = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT name, position, department, basic_salary FROM EmployeeData WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                String name = rs.getString("name");
                String position = rs.getString("position");
                String department = rs.getString("department");
                double basic_salary = rs.getDouble("basic_salary");

                double hra = 0.20*basic_salary;
                double da = 0.10*basic_salary;
                double ta = 0.05*basic_salary;
                double grossSalary = basic_salary+hra+da+ta;

                double pf = 0.12*basic_salary;
                double tax = 0.10*grossSalary;

                double totalDeductions = pf+tax;

                double netSalary = grossSalary-totalDeductions;

                System.out.println("\n ========== PAYSLIP ==========");
                System.out.println("Employee ID :"+id);
                System.out.println("Employee Name :"+name);
                System.out.println("Department: "+department);
                System.out.println("Position: "+position);
                System.out.println("--------------------------------");
                System.out.println("Basic Salary: ₹"+basic_salary);
                System.out.println("HRA (20%): ₹"+hra);
                System.out.println("DA (10%): ₹"+da);
                System.out.println("TA (5%): ₹"+ta);
                System.out.println("--------------------------------");
                System.out.println("Gross Salary: ₹"+grossSalary);
                System.out.println("PF (12%): ₹"+pf);
                System.out.println("Tax (10%): ₹"+tax);
                System.out.println("Total Deductions: ₹"+totalDeductions);
                System.out.println("--------------------------------");
                System.out.println("Net Salary: ₹"+netSalary);
                System.out.println("Pay Date: "+new java.util.Date());
                System.out.println("=================================");
            }else{
                System.out.println("Employee not found!");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

