package core;

import core.util.AuthReader;
import core.util.ConfigReader;
import core.util.Logger;
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
    boolean hasAccess = false;
    boolean accessFileExists = true;
    File authFile;

    public Htpassword( String fileName ) {
        configuration = ConfigReader.getInstance();
        authReader = new AuthReader();
        authFile = new File(new File(fileName).getParent() + "/" + configuration.getAccessFile());
        if(!authFile.exists()){
            hasAccess = true;
            accessFileExists = false;
        }
    }

    public boolean isAuthorized( String authInfo ) {
        if(authInfo != null && accessFileExists) {
            authReader.readAccessFile(authFile.getAbsolutePath());
            String credentials = new String(
            Base64.getDecoder().decode( authInfo ),
            Charset.forName( "UTF-8" )
            );

            String[] tokens = credentials.split( ":" );

            Logger.user = tokens[0]; //Log username

            if(authReader.userHasAccess(tokens[0])) {
                String filePath = configuration.getAccessFile();
                hasAccess = verifyPassword(tokens[0], tokens[1]);
            }
        }
        return hasAccess;
    }

    private boolean verifyPassword( String username, String password ) {
        String encryptedPass = encryptClearPassword(password);        
        authReader.readPasswordFile(configuration.getAuthUserFile());

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