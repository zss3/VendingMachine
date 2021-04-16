package com.techelevator.Log;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VendingLog {
    private static String AUDIT_FILE = "audit.txt";

    public static void log(String message) {
        try (FileOutputStream stream = new FileOutputStream(AUDIT_FILE, true);
             PrintWriter writer = new PrintWriter(stream)){

            LocalDateTime timeStamp = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/y hh:mm:ss a");
            writer.println(timeStamp.format(formatter) + " " + message);

        } catch (Exception e) {
            System.out.println("Not able to add to audit log.");
        }
    }


}
