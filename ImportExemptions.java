/*
 * 
 * Last visited date: Oct. 14, 2021
 * Purpose: A program that check for travellers' eligibility to receive import exemptions based on their day of absence and 
 * total imported amount. It also calculates the amount that wil be subjected to regular, special taxes, and maximum amount of exemption
 * that they can receive
 * 
 * Author: Phi Nguyen
 */


import java.util.Scanner;
import java.text.NumberFormat;

public class ImportExemptions {         
    public static void main(String[] args) {
        double maxExemption=0; // all currency values are in $CAD       
        double specialDutyAndTaxes = 0, regularDutyAndTaxes=0, 
                 maxClaim=0, usableExemption=0, totalImportedGoods;
        double daysAbsence;

        String firstName, lastName, initials;
        String alcoholAllowance;

        Scanner input = new Scanner(System.in);
        NumberFormat np = NumberFormat.getCurrencyInstance();

        for (int i = 0; i <= 4; i++) {
             // prompt user's full name, day(s) absence, and imported amounts
             System.out.print("Traveller's name: (last, first, initials): ");
             lastName = input.next();
             firstName = input.next();
             initials = input.next();

             System.out.print("Length of absence (# of days): ");
             daysAbsence = input.nextDouble();

             System.out.print("Total amount of imported goods ($CAD): ");
             totalImportedGoods = input.nextDouble();

             firstName = firstName.substring(0, firstName.length() - 1);
             lastName = lastName.substring(0, lastName.length() - 1);         

             // if traveller absents from country between 1 and 2 days
              if (daysAbsence >= 1 && daysAbsence < 2) {
                 alcoholAllowance = "NO";
                 maxExemption = 200;
                 // traveller will receiver no taxes if total imported goods is less than $200 CAD
                   if (totalImportedGoods <= 200) {                    
                     usableExemption = totalImportedGoods;
                     specialDutyAndTaxes = 0;
                     regularDutyAndTaxes = 0;                
                   } 
                 // travller will receive no import exemption if total imported goods is more than $200 CAD
                  else {             
                     specialDutyAndTaxes = 0;
                     regularDutyAndTaxes = totalImportedGoods;    
                     usableExemption = 0;
                 }
             } 
             // if traveller absents for 2 more days
             else if (daysAbsence >= 2) {
                 usableExemption = 800;
                 maxExemption = 800;
                 // limited amount of alcohol and tobacco is allowed to receive exemption
                 alcoholAllowance = "YES";
                 // no regular taxes, but traveller has to pay special taxes if total imported goods is less than $1000 CAD
                 // since regular taxes only count after the cap of $300 CAD of special taxes
                 if (totalImportedGoods > 800 && totalImportedGoods <= 1100) {             
                     specialDutyAndTaxes = totalImportedGoods - 800; 
                     regularDutyAndTaxes = 0;
                 } 
                 // regular taxes is counted if special taxes reach the maximum of $300 CAD
                 else if (totalImportedGoods > 1100) {                   
                     // maximum amount of import exemption that traveller can receive
                     maxClaim =  totalImportedGoods - 800;
                     if (maxClaim > 300) {
                         specialDutyAndTaxes = 300;
                         regularDutyAndTaxes =  maxClaim - 300;                       
                     }                  
                 } 
                 // traveller is not required to pay taxes since they can use their import exemption for their goods
                 // if it is less than $800 CAD
                 else {
                     usableExemption = totalImportedGoods;
                     specialDutyAndTaxes = 0; 
                     regularDutyAndTaxes = 0;               
                 }               
             } 
             // if traveller absents less than 1 day, they are not eligible for import exemptions,
             // and their imported goods will be subjected to regular taxes
             else {   
                alcoholAllowance = "NO";
                maxExemption = 0;
                usableExemption = 0;
                specialDutyAndTaxes = 0; 
                regularDutyAndTaxes = totalImportedGoods;                    
             }
             // display bill
             System.out.printf("%n");
             System.out.printf("Import Exemption Report for %s %s %s %n", firstName, initials, lastName);
             System.out.println("-------------------------------------------------------------");
             System.out.printf("Absence Period (days)                 %10.1f \n ", daysAbsence);
             System.out.printf("...Maximum Personal Exemption         %9s \n ", np.format(maxExemption));
             System.out.printf("...Include Limited Alcohol & Tobacco? %9s  \n ", alcoholAllowance);
             System.out.printf("Usable Personal Exemption                     %15s \n ", np.format(usableExemption));
             System.out.printf("Amount Subject to Special Duty & Taxes        %15s \n ", np.format(specialDutyAndTaxes));
             System.out.printf("Amount Subject to Regular Duty & Taxes        %15s \n ", np.format(regularDutyAndTaxes));
             System.out.println("                                              ---------------");
             System.out.printf("Total Amount of Imported Goods                %16s \n ", np.format(totalImportedGoods));
             System.out.println("-------------------------------------------------------------");
             System.out.printf("%n %n");
        }
    }   
}
