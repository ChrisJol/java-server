package core;

import java.io.*;
import java.util.Properties;

public class ConfigReader {
    private File configFile;
    private String serverRoot;
    private String docRoot;
    private String logFile;
    private int defaultPort;

    ConfigReader(String fileName){
        configFile = new File(fileName);
    }

    String getServerRoot(){
        return serverRoot;
    }

    String getDocRoot(){
        return docRoot;
    }

    String getLogFile(){
        return logFile;
    }

    int getDefaultPort(){
        return defaultPort;
    }

    public void load(){
        try{
            FileReader reader = new FileReader(configFile);
            Properties properties = new Properties();
            properties.load(reader);
        
            serverRoot = properties.getProperty("ServerRoot").replace("\"", "");
            docRoot = properties.getProperty("DocumentRoot").replace("\"", "");
            logFile = properties.getProperty("LogFile").replace("\"", "");
            defaultPort = Integer.parseInt(properties.getProperty("Listen"));

            reader.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found in classpath");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}