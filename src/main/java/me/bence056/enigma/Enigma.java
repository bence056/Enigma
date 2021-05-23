package me.bence056.enigma;

import me.bence056.enigma.components.RotorManager;
import me.bence056.enigma.extras.RotorGenerator;

public class Enigma {

    public static void main(String[] args) {

        CommandManager.initialize();

        //RotorGenerator.GenerateNewRotorComposition();

        //RotorManager.initializeRotors(RotorGenerator.ROTORS.get(0), RotorGenerator.ROTORS.get(1), RotorGenerator.ROTORS.get(2));


        if(args.length > 0) {
            CommandManager.checkParameters(args);
        } else {
            System.out.print("\nAvailable Commands: \n");
            for(String s : CommandManager.COMMAND_STRUCTURE.keySet()) {
                System.out.print("\n [X] - " + s +  " - " + CommandManager.COMMAND_STRUCTURE.DESC_MAP.get(s) + "\n");
            }
            System.out.println("");
        }


    }

}
