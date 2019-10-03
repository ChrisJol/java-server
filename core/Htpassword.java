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

    private static Scanner authUserFile;    

    // ConfigReader configuration;
    private HashMap<String, String> passwords;
    ConfigReader configuration;
    AuthReader authReader;

    public Htpassword( String filename ) throws IOException {
        configuration = ConfigReader.getInstance();
        authReader = new AuthReader();
        authReader.readPasswordFile(configuration.getAccessFile());
    }

    public boolean isAuthorized( String authInfo ) {
        // authInfo is provided in the header received from the client
        // as a Base64 encoded string.
        String credentials = new String(
        Base64.getDecoder().decode( authInfo ),
        Charset.forName( "UTF-8" )
        );
        // The string is the key:value pair username:password
        String[] tokens = credentials.split( ":" );
        System.out.println(tokens[0] + "\n" + tokens[1]);

        //Look through the .htpassword file and check to see if tokens[1] match username in .htaccess file

        String filePath = "/Users/nicolebernardo/Documents/Documents/FALL 2019 CLASSES/CSC 667/Project1/fall-2019-web-server-bernardo_jol/public_html/.htaccess"; //for testing purposes
        authReader.readAcessFile(filePath);
       
        System.out.println(authReader.userHasAccess(tokens[0]));

        return authReader.userHasAccess(tokens[0]);
    }

    private boolean verifyPassword( String username, String password ) {
        // encrypt the password, and compare it to the password stored
        // in the password file (keyed by username)
        // TODO: implement this - note that the encryption step is provided as a
        // method, below
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
