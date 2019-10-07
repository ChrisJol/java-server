package core;

import core.response.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import core.util.ConfigReader;

public class Operations {
    ConfigReader configuration;

    public Operations(){
        configuration = ConfigReader.getInstance();
    }

    public String POST(){
        System.out.println("In Operations.java POST method:");
        return "200";
    };
}