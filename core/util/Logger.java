package core.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.FileOutputStream;

import core.util.ConfigReader;

public class Logger{
    ConfigReader configuration;
    static File logFile;
    public static String ipAddress;
    public static String user;
    public static Date timeOfCompletion;
    public static String request;
    public static int statusCode;
    public static int size;

    public Logger(){
        configuration = ConfigReader.getInstance();
        logFile = new File(configuration.getLogFile());
        if(!logFile.exists()){
            try{
                logFile.createNewFile();
            }
            catch(IOException e){
                System.out.println("Log File could not be created");
            }
        }
    }

    public static void log(){
        String log = " - ";
        log += user + " ";
        log += "[" + new SimpleDateFormat("dd/MMM/yyy:HH::mm:ss -z").format(timeOfCompletion) + "] ";
        log += "\"" + request + "\" ";
        log += String.valueOf(statusCode) + " ";
        log += String.valueOf(size);
        log += "\n";

        try{
            FileOutputStream outputStream = new FileOutputStream(logFile, true);
            byte[] strToBytes = log.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
        }
        catch(IOException e){}
    }
}