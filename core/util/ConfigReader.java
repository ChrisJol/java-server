package core.util;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class ConfigReader {
    private final int DEFAULT_PORT = 8080;
    private final String DEFAULT_DIRECTORY_INDEX = "index.html";
    private final String DEFAULT_ACCESS_FILE = ".htaccess.txt";
    private final String DEFAULT_AUTHUSER_FILE = "/auth/.htpasswd.txt";
    private final String DEFAULT_LOG_FILE = "/logs/log.txt";
    private static ConfigReader single_instance = null; //singleton instance

    File configFile;
    Map<String, String> configParams = new HashMap<String, String>();
    Map<String, String> scriptAlii = new HashMap<String, String>();

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
        String logFile = configParams.get("LogFile");
        return (logFile == null) ? DEFAULT_LOG_FILE : logFile;
    }

    public int getDefaultPort(){
        String defaultPort = configParams.get("Listen");
        return ( defaultPort == null ) ? DEFAULT_PORT : Integer.parseInt(defaultPort);
    }

    public String getDirectoryIndex(){
        String directoryIndex = configParams.get("DirectoryIndex");
        return ( directoryIndex == null ) ? DEFAULT_DIRECTORY_INDEX : directoryIndex;
    }

    public String getAlias(String alias){
        return configParams.get(alias);
    }

    public String getScriptAlias(String scriptAlias){
        return scriptAlii.get(scriptAlias);
    }

    public String getAccessFile(){
        String accessFile = configParams.get("AccessFile");
        return (accessFile == null) ? DEFAULT_ACCESS_FILE : accessFile;
    }

    public String getAuthUserFile(){
        String authUserFile = configParams.get("AuthUserFile");
        return (authUserFile == null) ? DEFAULT_AUTHUSER_FILE : authUserFile;
    }

    private void load(){ //throws indexOutOfBound error when conf isn't formatted properly, we should try to come up with a fix
        try {
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            String property = reader.readLine();

            while(property != null) {
                String[] properties = property.split(" ");

                if(properties[0].equals("ScriptAlias")) {
                    scriptAlii.put(properties[1], properties[2].replace("\"", ""));
                }
                else if(properties[0].equals("Alias")){
                    configParams.put(properties[1], properties[2].replace("\"", ""));
                }
                else {
                    configParams.put(properties[0], properties[1].replace("\"", ""));
                }
                property = reader.readLine();
            }
            reader.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found in classpath");
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}