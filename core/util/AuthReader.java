package core.util;

import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AuthReader {

    File authUserFile;
    Map<String, String> authUsers = new HashMap<String, String>();
    
    public AuthReader(String fileName){
        authUserFile = new File(fileName);
        this.load();
    }

    private void load() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(authUserFile));
            String authUser = reader.readLine();

            while(authUser != null) {
                String[] tokens = authUser.split(":");
                if( tokens.length == 2 ) {
                    authUsers.put( tokens[ 0 ], tokens[ 1 ].replace( "{SHA}", "" ).trim() );
                }
                authUser = reader.readLine();
            }
            reader.close();
        } 
        catch(FileNotFoundException e) {
            System.out.println("File not found");
        } 
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String getUserPassword(String user){
        return authUsers.get(user);
    }
}