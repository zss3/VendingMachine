package com.techelevator.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SalesReport {

    public static String SALES_REPORT_FILE = "SalesReport.txt";

    public static void delete() {
        File salesReport = new File(SALES_REPORT_FILE);
        salesReport.delete();
    }

    public static void log(String message) {
        try (FileOutputStream stream = new FileOutputStream("SalesReport.txt", true);
             PrintWriter writer = new PrintWriter(stream)){


            writer.println(message);


        } catch (Exception e) {
            System.out.println("Not able to add to Sales Report.");
        }
    }

}
