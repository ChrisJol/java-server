package core;

import java.io.*;
import java.util.*;

public class ConfigReader {
    private static ConfigReader single_instance = null; //singleton instance

    Map<String, String> configParams = new HashMap<String, String>();

    private File configFile;
    private String serverRoot;
    private String docRoot;
    private String logFile;
    private String alias;
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
        return configParams.get("ServerRoot");
    }

    public String getDocRoot(){
        return configParams.get("DocumentRoot");
    }

    public String getLogFile(){
        return configParams.get("LogFile");
    }

    public int getDefaultPort(){
        return Integer.parseInt(configParams.get("Listen"));
    }

    public String getAlias(String alias){
        return configParams.get(alias);
    }

    private void load(){
        try {
            FileReader fileReader = new FileReader(configFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String property = reader.readLine();

            while(property != null) {
                String[] properties = property.split(" ");

                if(properties[0].equals("Alias") || properties[0].equals("ScriptAlias")) {
                    configParams.put(properties[1], properties[2].replace("\"", ""));
                } else {
                    configParams.put(properties[0], properties[1].replace("\"", ""));
                }
                property = reader.readLine();
            }
            reader.close();

            System.out.println(getServerRoot());
            System.out.println(getDocRoot());
            System.out.println(getLogFile());
            System.out.println(getDefaultPort());
            System.out.println(getAlias("/ab/"));
            System.out.println(getAlias("/cgi-bin/"));
            System.out.println(getAlias("/~traciely/"));
        }
        catch(FileNotFoundException e){
            System.out.println("File not found in classpath");
        }
        catch(IOException e){
            e.printStackTrace();
        }


        // try{
        //     FileReader reader = new FileReader(configFile);
        //     Properties properties = new Properties();
        //     properties.load(reader);

        //     serverRoot = properties.getProperty("ServerRoot").replace("\"", "");
        //     docRoot = properties.getProperty("DocumentRoot").replace("\"", "");
        //     logFile = properties.getProperty("LogFile").replace("\"", "");
        //     alias = properties.getProperty("Alias").replace("\"", "");
        //     defaultPort = Integer.parseInt(properties.getProperty("Listen"));

        //     System.out.println(alias);

        //     reader.close();

        // }catch(FileNotFoundException e){
        //     System.out.println("File not found in classpath");
        // }catch(IOException e){
        //     e.printStackTrace();
        // }


    }
}