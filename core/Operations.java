package core;

import java.io.IOException;
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
            if(newFile.createNewFile()){
                BufferedWriter fileOut = new BufferedWriter(new FileWriter(newFile.getName()));

                return "201";
            }
            else{
                return "500";
            }
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
            return "204";
        }
        else {
            return "500";
        }
    }

    public String POST(){
        return " ";
    };
}