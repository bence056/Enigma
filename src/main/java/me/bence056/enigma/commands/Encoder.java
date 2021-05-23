package me.bence056.enigma.commands;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import me.bence056.enigma.CommandManager;
import me.bence056.enigma.components.RotorManager;
import me.bence056.enigma.extras.RotorGenerator;
import me.bence056.enigma.interfaces.ICommandBase;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Encoder implements ICommandBase {

    public static String finalResult = "";

    public String[] args;

    @Override
    public void executeCommand(String[] args) {
        this.args = args;
        encode();

    }

    public void encode() {
        if(args.length>0) {

            JSONObject dataInput = null;
            FileReader reader;
            JSONParser parser = new JSONParser();

            try {
                reader = new FileReader("./data.json");
                try {
                    Object obj = parser.parse(reader);

                    dataInput = (JSONObject) obj;

                    if(dataInput.isEmpty()) {
                        dataInput = null;
                    }

                }catch (ParseException e) {
                    e.printStackTrace();
                }
            }catch (IOException e) {
                System.out.println("Data file does not exist! - Creating a new one...");
                RotorGenerator.GenerateNewRotorComposition();
                RotorGenerator.saveConfigToFile();
                dataInput = null;
                encode();
                return;
            }

            if(dataInput==null) {
                System.out.println("Data not found");
            }else {
                JSONArray array = (JSONArray) dataInput.get("rotors");
                JSONArray rotStates = (JSONArray) dataInput.get("rotor_states");
                RotorGenerator.ROTORS.clear();
                RotorGenerator.REVERSER.clear();

                List<Integer> enabled_rotor_numbers = new ArrayList<>();

                for(int i=0; i<5; i++) {
                    JSONObject currentRotor = (JSONObject) array.get(i);
                    //System.out.println(currentRotor);
                    Map<Integer, Integer> currentRotorMap = new HashMap<>();
                    for(int c=0; c<26; c++) {
                        currentRotorMap.put(c+1, ((Number) currentRotor.get(String.valueOf(c+1))).intValue());
                        //System.out.println(currentRotorMap);
                        //System.out.println(currentRotor.get(String.valueOf(c+1)));
                    }
                    RotorGenerator.ROTORS.add(currentRotorMap);

                    boolean isRotorEnabled = (Boolean) rotStates.get(i);
                    if(isRotorEnabled) {
                        enabled_rotor_numbers.add(i);
                    }

                    JSONObject reverser = (JSONObject) dataInput.get("reverser");
                    //System.out.println(currentRotor);
                    Map<Integer, Integer> currentReverserMap = new HashMap<>();
                    for(int c=0; c<26; c++) {
                        currentReverserMap.put(c+1, ((Number) reverser.get(String.valueOf(c+1))).intValue());
                        //System.out.println(currentRotorMap);
                        //System.out.println(currentRotor.get(String.valueOf(c+1)));
                    }
                    RotorGenerator.REVERSER = currentReverserMap;
                }
                RotorManager.initializeRotors(RotorGenerator.ROTORS.get(enabled_rotor_numbers.get(0)), RotorGenerator.ROTORS.get(enabled_rotor_numbers.get(1)), RotorGenerator.ROTORS.get(enabled_rotor_numbers.get(2)));
                doEncryption();
            }





        }else {
            System.out.println("No argument specified");
        }
    }

    private void doEncryption() {

        System.out.println("Decoding numerical series...\n");

        int printCounter = 0;



        for(char c : args[0].toUpperCase().replaceAll("\\s+","").toCharArray()) {

            int resNum = feedThroughRotors(c);
            char result = RotorManager.InvertedAlphabetMap.get(resNum);
            finalResult += result;
            RotorManager.TryAdvanceRotors();

            String printNum = String.valueOf(resNum);

            if(resNum<10) {
                printNum = "0" + resNum;
            }

            if(printCounter<10) {
                System.out.print(printNum + "  ");
                printCounter++;
            }else {
                System.out.println(printNum);
                printCounter = 0;
            }
        }
        RotorGenerator.saveConfigToFile();
        System.out.println("\n\nCypher: " + finalResult + "\n");


    }

    private int feedThroughRotors(char alpha) {

        int charID = RotorManager.AlphabetMap.get(alpha);

        /*int retVal = RotorManager.SELECTED_ROTORS.get(0).get(
                        RotorManager.SELECTED_ROTORS.get(1).get(
                                RotorManager.SELECTED_ROTORS.get(2).get(
                                        RotorManager.SELECTED_ROTORS.get(2).get(
                                                RotorManager.SELECTED_ROTORS.get(1).get(
                                                        RotorManager.SELECTED_ROTORS.get(0).get(charID))))));*/

        //RotorManager.AdvanceRotors();

        int retVal = RotorManager.SELECTED_ROTORS_INVERTED.get(0).get(
                        RotorManager.SELECTED_ROTORS_INVERTED.get(1).get(
                                RotorManager.SELECTED_ROTORS_INVERTED.get(2).get(
                                        RotorGenerator.REVERSER.get(
                                                RotorManager.SELECTED_ROTORS.get(2).get(
                                                        RotorManager.SELECTED_ROTORS.get(1).get(
                                                                RotorManager.SELECTED_ROTORS.get(0).get(charID))))))) ;

        return retVal;

    }

}
