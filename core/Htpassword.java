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
        boolean hasAccess = false;
        if(authInfo != null) {
            String credentials = new String(
            Base64.getDecoder().decode( authInfo ),
            Charset.forName( "UTF-8" )
            );

            String[] tokens = credentials.split( ":" );
            if(authReader.userHasAccess(tokens[0])) {
                String filePath = configuration.getAccessFile();
                authReader.readAccessFile(filePath);
                hasAcess = verifyPassword(tokens[0], tokens[1]);
            }
        } else {
            return hasAccess;
        }
    }

    private boolean verifyPassword( String username, String password ) {
        String encryptedPass = encryptClearPassword(password);        
        String passFilePath = configuration.getAuthUserFile();
        authReader.readPasswordFile(passFilePath);

        return encryptedPass.equals(authReader.getUserPassword(username));
    }

    private String encryptClearPassword(String password ) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance( "SHA-1" );
            byte[] result = mDigest.digest( password.getBytes() );

            return Base64.getEncoder().encodeToString( result );
        } catch( Exception e ) {
            return "";
        }
    }
    }