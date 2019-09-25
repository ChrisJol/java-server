package core;

import java.io.*;
import java.util.Properties;

public class ConfigReader {
    private static ConfigReader single_instance = null; //singleton instance

    private File configFile;
    private String serverRoot;
    private String docRoot;
    private String logFile;
    private int defaultPort;

    private ConfigReader(String fileName){
        configFile = new File(fileName);
        this.load();
    }

    public static ConfigReader getInstance(){
        if(single_instance == null){
            single_instance = new ConfigReader("./conf/httpd.conf.txt");
        }

        return single_instance;
    }

    public String getServerRoot(){
        return serverRoot;
    }

    public String getDocRoot(){
        return docRoot;
    }

    public String getLogFile(){
        return logFile;
    }

    public int getDefaultPort(){
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