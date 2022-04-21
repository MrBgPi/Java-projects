import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

/*
 * Last visited date: Nov. 5, 2021
 * Purpose: A program that read Employee.csv and EmpSal2021 files and display pay schedule based on first and last name.
 */

public class EmployeeSalary {
    
    static String employID;
    static double grossSal, fedTax, abTax, CPP, EI;
    
    /**
     * A method that look up employee ID based on employee first and last name.
     * @param employeeInfoFile This parameter takes Employee.csv file and read information of the chosen employee.
     * @param firstName This parameter is to compare whether input employee first name matches recorded first name.
     * @param lastName This parameter is to compare whether input employee last name matches recorded last name.
     * @return employID Return employee ID if employee information matches input information
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static String lookupEmpID(String employeeInfoFile, String firstName, String lastName) throws FileNotFoundException, IOException {        
        String line;
        
        BufferedReader br = new BufferedReader(new FileReader(employeeInfoFile));
        
        while ((line = br.readLine()) != null) {
            String[] cols = line.split(",");
            
            // Check if employee first and last name macth the data in the file
            if (firstName.equalsIgnoreCase(cols[0]) && lastName.equalsIgnoreCase(cols[1])) {
                employID=cols[3];           
            }        
        }
        return employID;       
    }
    
    /**
     * This method retrieves pay schedule data from String to double
     * @param employeeSalFile
     * @param firstName
     * @param lastName
     * @param employeeID
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void printEmployeeSalary(String employeeSalFile, String firstName, String lastName, String employeeID) throws FileNotFoundException, IOException {
        String line;
        
        BufferedReader br = new BufferedReader(new FileReader(employeeSalFile));
        
        // Read employee salary file
        while ((line = br.readLine()) != null) {
            String[] cols = line.split(",");
            
            // Since employee ID is the first column, the condition is whether the retrived employee ID from lookupEmpID method macthes
            // employee ID in Employee Salary file.
            if (employeeID.equals(cols[0])) {
                // If it matches, retrieves String data to double value.
                grossSal = Double.parseDouble(cols[1]);
                fedTax = Double.parseDouble(cols[2]);
                abTax = Double.parseDouble(cols[3]);
                CPP = Double.parseDouble(cols[4]);
                EI = Double.parseDouble(cols[5]);
            }
        }
    }
    
    /**
     * This method displays employee pay schedule in the table form
     * @param firstName
     * @param lastName
     * @param employeeID 
     */
    public static void printPaySchedule(String firstName, String lastName, String employeeID) {
        final double MAX_CPP = 3166.45, MAX_EI = 889.54;
        double totalGrossSal=0, totalTax=0, totalCPP=0, totalEI=0, totalNetSal=0;
        double tax, netSal;
        
        DecimalFormat df = new DecimalFormat("#,##0.00");
        
        System.out.printf("Salary Schedule for %s %s (%s) \n", firstName.toUpperCase(), lastName.toUpperCase(), employeeID);
        System.out.printf("%s %16s %15s %9s %10s %15s \n", "Month", "Gross Salary", "Tax", "CPP", "EI", "Net Salary");
        System.out.println("---------------------------------------------------------------------------");
        
        tax = fedTax + abTax;        
        
        // Display table of salary of a year
        for (int i = 1; i <= 12; i++) {            
            double CPP1 = MAX_CPP - CPP * (i - 1);
            double EI1 = MAX_EI - EI * (i - 1);
            double displayCPP = 0;
            double displayEI = 0;
            
            // Calculate CPP
            if (CPP1 >= 0 && CPP1 >= CPP) {
                displayCPP = CPP;
            } else if (CPP1 >= 0 && CPP1 < CPP) {
                displayCPP = CPP1;
            } else {
                displayCPP = 0;
            }
            // Calculate EI
            if (EI1 >= 0 && EI1 >= EI) {
                displayEI = EI;
            } else if (EI1 >= 0 && EI1 < EI) {
                displayEI = EI1;
            } else {
                displayEI = 0;
            }
            
            totalGrossSal += grossSal;
            totalTax += tax;
            totalCPP += displayCPP;
            totalEI += displayEI;            
            netSal = grossSal - tax - displayCPP - displayEI;
            totalNetSal += netSal;
            
            // Display pay schedule
            System.out.printf("%5d %16s %15s %9s %10s %15s \n"
                    , i, df.format(grossSal), df.format(tax), df.format(displayCPP), df.format(displayEI), df.format(netSal));
        }
        // Display total
        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("Total %16s %15s %9s %10s %15s \n"
                , df.format(totalGrossSal), df.format(totalTax), df.format(totalCPP), df.format(totalEI), df.format(totalNetSal));
    }
    
    public static void main(String[] args) throws IOException {
        String employeeInfoFile, employeeSalFile;
        String firstName, lastName, employeeID;
        
        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter employee filename: ");
        employeeInfoFile = input.nextLine();
        System.out.print("Enter employee salary filename: ");
        employeeSalFile = input.nextLine();
        System.out.print("Enter last name: ");
        lastName = input.nextLine();
        System.out.print("Enter first name: ");
        firstName = input.nextLine();       
             
        employeeID = lookupEmpID(employeeInfoFile, firstName, lastName);
        
        // If employee ID does not exist, display no information found
        if (employeeID == null) {
            System.out.printf("No employee information found for %s %s \n", firstName.toUpperCase(), lastName.toUpperCase());
        }
        // Retrive employee data and salary if employee information exists in the file
        else {
            printEmployeeSalary(employeeSalFile, firstName, lastName, employeeID);
            printPaySchedule(firstName, lastName, employeeID);
        }
    }
}
