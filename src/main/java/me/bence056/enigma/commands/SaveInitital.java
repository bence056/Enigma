package me.bence056.enigma.commands;

import me.bence056.enigma.interfaces.ICommandBase;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveInitital implements ICommandBase {
    @Override
    public void executeCommand(String[] args) {


        System.out.println("Copying current settings to data_initial.json");

        FileWriter writer;
        FileReader reader;

        try {
            reader = new FileReader("./data.json");

            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(reader);

                JSONObject jsonObject = (JSONObject) obj;

                writer = new FileWriter("./data_initial.json");

                writer.write(jsonObject.toJSONString());
                writer.flush();
                writer.close();

            }catch (ParseException e) {
                e.printStackTrace();
            }

        }catch (IOException e) {
            System.out.println("File not found!");
            return;
        }




    }
}
