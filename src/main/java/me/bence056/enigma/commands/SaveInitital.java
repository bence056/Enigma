package me.bence056.enigma.commands;

import me.bence056.enigma.interfaces.ICommandBase;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class SaveInitital implements ICommandBase {
    @Override
    public void executeCommand(String[] args) {


        String fileName = "progsave_";
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        fileName += formatter.format(dt) + ".json";

        System.out.println("Copying current settings to saved/" + fileName);

        FileWriter writer;
        FileReader reader;

        try {
            reader = new FileReader("./data.json");

            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(reader);

                JSONObject jsonObject = (JSONObject) obj;

                String dirPath = "./saved";
                File f = new File(dirPath);
                if(!f.exists()) {
                    f.mkdir();
                }

                writer = new FileWriter("saved/" + fileName);

                writer.write(jsonObject.toJSONString());
                writer.flush();
                writer.close();

            }catch (ParseException e) {
                e.printStackTrace();
            }

        }catch (IOException e) {
            System.out.println("An Error occured.");
            return;
        }




    }
}
