package com.example.d3sshoppos.helpers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    public final static String FILE_LOG_PATH = "C:\\Kareem\\log.txt";

    public static void logError(String error, String tag) {
        if(error == null || tag == null)
            return;

        writeToFile(getTimeAndDate()+": Error: " + tag + " : " + error);
    }

    public static void logWarn(String warn, String tag) {
        if(warn == null || tag == null)
            return;

        writeToFile(getTimeAndDate()+": warn: " + tag + " : " + warn);
    }

    public static void logConfirmation(String conf, String tag) {
        if(conf == null || tag == null)
            return;

        writeToFile(getTimeAndDate()+": Conf: " + tag + " : " + conf);
    }


    private static void writeToFile(String txt) {
        try {
            FileWriter fileWriter = new FileWriter(FILE_LOG_PATH, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(txt);
            bufferedWriter.newLine();

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getTimeAndDate() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Define the desired date and time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");

        // Display the formatted date and time
        return currentDateTime.format(formatter);
    }

}
