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

    public String PUT(String filePath){

        try{
            File newFile = new File(filePath);

            if (newFile.createNewFile()) {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }

            FileWriter writer = new FileWriter(newFile.getName());
            // writer.write("I'm writing to a file");
            writer.close();
    
            return "201";
        //     File newFile = new File(filePath);
        //     if(newFile.createNewFile()){
        //         BufferedWriter fileOut = new BufferedWriter(new FileWriter(newFile.getName()));

        //         return "201";
        //     }
        //     else{
        //         return "500";
        //     }
        }
        catch(IOException e){
            System.out.println("File not created");
        }
        finally{
            return "500";
        }
    }

    public String DELETE(String filePath){
        File fileToDelete = new File(filePath);
        if(fileToDelete.delete()){
            System.out.println("File was deleted!");
            return "204";
        }
        else {
            return "500";
        }
    }

    public String POST(){
        System.out.println("In Operations.java POST method:");
        return "200";
    };
}