package me.bence056.enigma.extras;

import jdk.nashorn.internal.parser.JSONParser;
import jdk.nashorn.internal.runtime.JSONFunctions;
import me.bence056.enigma.interfaces.ICommandBase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.lang.Object;

import java.io.FileWriter;
import java.util.*;

public class RotorGenerator implements ICommandBase {

    public static List<Map<Integer, Integer>> ROTORS = new ArrayList<>();


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

        if(args.length!=0) {
            if(args[0].equals("-save")) {
                System.out.println("Saving to file...");
                FileWriter file;
                JSONObject obj;
                JSONObject objWrapper = new JSONObject();
                JSONArray array = new JSONArray();
                for(int c=0; c<5; c++) {
                    obj = new JSONObject();
                    obj.putAll(ROTORS.get(c));
                    array.add(obj);
                }
                objWrapper.put("rotors", array);

                try {
                    file = new FileWriter("./data.json");
                    file.write(objWrapper.toJSONString());
                    file.flush();
                    file.close();

                }catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
