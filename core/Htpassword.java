package core;

import core.util.AuthReader;
import core.util.ConfigReader;

import java.util.Scanner;
import java.util.Base64;
import java.util.Map;
import java.util.HashMap;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.io.File;

import java.io.IOException;

    public class Htpassword {

    ConfigReader configuration;
    AuthReader authReader;

    public Htpassword( String filename ) throws IOException {
        configuration = ConfigReader.getInstance();
        authReader = new AuthReader();
        authReader.readAccessFile(configuration.getAccessFile());
    }

    public boolean isAuthorized( String authInfo ) {
        if(authInfo != null) {
            String credentials = new String(
            Base64.getDecoder().decode( authInfo ),
            Charset.forName( "UTF-8" )
            );

            String[] tokens = credentials.split( ":" );
            String filePath = configuration.getAccessFile();
            authReader.readAccessFile(filePath);
            verifyPassword(tokens[0], tokens[1]);

            return authReader.userHasAccess(tokens[0]);
        } else {
            return false;
        }
    }

    private boolean verifyPassword( String username, String password ) {
        String encryptedPass = encryptClearPassword(password);        
        String passFilePath = configuration.getAuthUserFile();
        authReader.readPasswordFile(passFilePath);

        if(encryptedPass.equals(authReader.getUserPassword(username))){
            System.out.println("passwords match!");
            return true;
        }
        return false;
    }

    private String encryptClearPassword( String password ) {
        // Encrypt the cleartext password (that was decoded from the Base64 String
        // provided by the client) using the SHA-1 encryption algorithm
        try {
        MessageDigest mDigest = MessageDigest.getInstance( "SHA-1" );
        byte[] result = mDigest.digest( password.getBytes() );

        return Base64.getEncoder().encodeToString( result );
        } catch( Exception e ) {
        return "";
        }
    }
    }