package me.bence056.enigma.extras;

import me.bence056.enigma.interfaces.ICommandBase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.Object;

import java.io.FileWriter;
import java.util.*;

public class RotorGenerator implements ICommandBase {

    public static List<Map<Integer, Integer>> ROTORS = new ArrayList<>();
    public static Map<Integer, Integer> REVERSER = new HashMap<>();

    public static boolean[] ROTOR_STATES = new boolean[5];



    public static void GenerateNewRotorComposition() {

        ROTORS.clear();

        Random rand = new Random();

        for(int i = 0; i<5; i++) {

            List<Integer> startConnections = new ArrayList<>();
            List<Integer> endConnections = new ArrayList<>();

            for(int c = 0; c<26; c++) {

                boolean bCanAdvance = false;
                while(!bCanAdvance) {
                    int current = rand.nextInt(26) + 1;

                    if(!startConnections.contains(current)) {
                        startConnections.add(current);
                        bCanAdvance = true;
                    }

                }

            }

            for(int c = 0; c<26; c++) {

                boolean bCanAdvance = false;
                while(!bCanAdvance) {
                    int current = rand.nextInt(26) + 1;

                    if(!endConnections.contains(current)) {
                        endConnections.add(current);
                        bCanAdvance = true;
                    }

                }

            }

            Map<Integer, Integer> CURRENT_ROTOR_COMP = new HashMap<>();

            for(int c = 0; c<26; c++) {

                CURRENT_ROTOR_COMP.put(startConnections.get(c), endConnections.get(c));

            }

            ROTORS.add(CURRENT_ROTOR_COMP);

        }

        //Reverser generation:

        List<Integer> startHalf = new ArrayList<>();
        List<Integer> endHalf = new ArrayList<>();
        List<Integer> startConnections;
        List<Integer> endConnections;



        for(int c=0; c<26; c++) {

            boolean bCanAdvance = false;
            int current = 0;

            while(!bCanAdvance) {
                current = rand.nextInt(26) + 1;

                if(!startHalf.contains(current) && !endHalf.contains(current)) {
                    if(c>12) {
                        endHalf.add(current);
                    }else {
                        startHalf.add(current);
                    }
                    bCanAdvance = true;

                }
            }
        }

        //Combining two reverser half-pairs:
        (startConnections = startHalf).addAll(endHalf);
        (endConnections = endHalf).addAll(startHalf);
        //-----------------------------------

        for(int i=0; i<26; i++) {
            REVERSER.put(startConnections.get(i), endConnections.get(i));
        }

        ROTOR_STATES[0] = true;
        ROTOR_STATES[1] = true;
        ROTOR_STATES[2] = true;
        ROTOR_STATES[3] = false;
        ROTOR_STATES[4] = false;

    }

    @Override
    public void executeCommand(String[] args) {
        System.out.println("Generating new Rotor Composition");
        GenerateNewRotorComposition();

        int i = 0;

        for(Map<Integer, Integer> M : ROTORS) {
            System.out.println("Rotor #"+(i+1));
            System.out.println(M);
            System.out.println("");
            i++;
        }

        //PRINTING REVERSER DATA:

        System.out.println("REVERSER:");
        System.out.println(REVERSER);
        System.out.println("");

        //----------------------

        if(args.length!=0) {
            if(args[0].equals("-save")) {
                saveConfigToFile();

            }
        }

    }

    public static void saveConfigToFile() {
        System.out.println("\n\nSaving Config file...");
        FileWriter file;
        JSONObject obj;
        JSONObject objWrapper = new JSONObject();
        JSONArray array = new JSONArray();
        JSONArray rotStates = new JSONArray();
        for(int c=0; c<5; c++) {
            obj = new JSONObject();
            obj.putAll(ROTORS.get(c));
            array.add(obj);
            rotStates.add(ROTOR_STATES[c]);
        }
        objWrapper.put("rotors", array);
        objWrapper.put("reverser", REVERSER);
        objWrapper.put("rotor_states", rotStates);

        try {
            file = new FileWriter("./data.json");
            file.write(objWrapper.toJSONString());
            file.flush();
            file.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> GetEnabledRotors() {


        List<Integer> enabled_rotor_numbers = new ArrayList<>();

        JSONObject dataInput = null;
        FileReader reader;
        org.json.simple.parser.JSONParser parser = new JSONParser();

        try {
            reader = new FileReader("./data.json");
            try {
                Object obj = parser.parse(reader);

                dataInput = (JSONObject) obj;

                if (dataInput.isEmpty()) {
                    dataInput = null;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Data file does not exist!");
            dataInput = null;
            return null;
        }

        if (dataInput == null) {
            System.out.println("Data not found");
        } else {

            JSONArray rotStates = (JSONArray) dataInput.get("rotor_states");

            for(int i=0; i<5; i++) {
                boolean isRotorEnabled = (Boolean) rotStates.get(i);
                ROTOR_STATES[i] = isRotorEnabled;
                if(isRotorEnabled) {
                    enabled_rotor_numbers.add(i);
                }
            }
        }

        return enabled_rotor_numbers;

    }

}
