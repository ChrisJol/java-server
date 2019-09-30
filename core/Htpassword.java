package core;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.*;
import java.io.*;

import java.io.IOException;

    public class Htpassword {

    private static Scanner authUserFile;    

    // ConfigReader configuration;
    private HashMap<String, String> passwords;

    public Htpassword( String filename ) throws IOException {
        //super( filename );
        System.out.println( "Password file: " + filename );

        this.passwords = new HashMap<String, String>();
        //this.load();
    }

    protected void parseLine( String line ) {
        String[] tokens = line.split( ":" );

        if( tokens.length == 2 ) {
        passwords.put( tokens[ 0 ], tokens[ 1 ].replace( "{SHA}", "" ).trim() );
        }
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

        //Look through the .htpassword file and check to see if tokens match username and password in file

        boolean found = false;

        String tempUser = "";
        String tempPass = "";
        String userVerified = "";
        String passVerified = "";
        String filePath = "AuthUserFile.txt"; //for testing purposes

        try {

            authUserFile = new Scanner(new File(filePath));
            authUserFile.useDelimiter(":|\n|[{}]"); // using example jrob:{SHA}cRDtpNCeBiql5KOQsKVyrA0sAiA=

            while(authUserFile.hasNext()){
                tempUser = authUserFile.next();
                tempPass = authUserFile.next();

                if(tempUser.equals(tokens[0])){
                    userVerified = tempUser;
                }

                if(tempPass.equals(tokens[1])){
                    passVerified = tempPass;
                }

                if(userVerified.equals(tokens[0]) && passVerified.equals(tokens[1])) {
                    System.out.println("User is AUTHORIZED");
                    System.out.println(userVerified + " " + passVerified);
                    found = true;
                }
            }
        } catch(Exception e) {
            System.out.println("User is not authorized");
        }
        return found;
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
