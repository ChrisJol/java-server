package core.util;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AuthReader {

    Map<String, String> authUsers = new HashMap<String, String>();
    Set<String> users = new HashSet<String>();

    public void readPasswordFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
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
            System.out.println("AutheReader.java File not found: " + fileName);
        } 
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void readAccessFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String user = reader.readLine();

            while(user != null) {
                users.add(user);
                user = reader.readLine();
            }
            reader.close();
        } 
        catch(FileNotFoundException e) {
            System.out.println("Authreader.java readAccess: File not found");
        } 
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String getUserPassword(String user) {
        return authUsers.get(user);
    }

    public boolean userHasAccess(String user) {
        return users.contains(user);
    }


}