package core;

import java.io.*;
import java.util.*;

public class ConfigReader {
    File configFile;
    private static ConfigReader single_instance = null; //singleton instance
    Map<String, String> configParams = new HashMap<String, String>();

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

    public String getDirectoryIndex() {
        return configParams.get("DirectoryIndex");
    }

    public String getAlias(String alias){
        return configParams.get(alias);
    }

    private void load(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            
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
        }
        catch(FileNotFoundException e){
            System.out.println("File not found in classpath");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}