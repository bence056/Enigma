package me.bence056.enigma.commands;

import me.bence056.enigma.CommandManager;
import me.bence056.enigma.components.RotorManager;
import me.bence056.enigma.extras.RotorGenerator;
import me.bence056.enigma.interfaces.ICommandBase;


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

            RotorManager.initializeRotors(RotorGenerator.ROTORS.get(0), RotorGenerator.ROTORS.get(1), RotorGenerator.ROTORS.get(2));
            doEncryption();



        }else {
            System.out.println("No argument specified");
        }
    }

    private void doEncryption() {

        for(char c : args[0].toUpperCase().replaceAll("\\s+","").toCharArray()) {

            int resNum = feedThroughRotors(c);
            char result = RotorManager.InvertedAlphabetMap.get(resNum);
            System.out.println(resNum);
            System.out.println(result);
            finalResult += result;
            RotorManager.TryAdvanceRotors();
        }



    }

    private int feedThroughRotors(char alpha) {

        int charID = RotorManager.AlphabetMap.get(alpha);

        int retVal = RotorManager.SELECTED_ROTORS.get(0).get(
                        RotorManager.SELECTED_ROTORS.get(1).get(
                                RotorManager.SELECTED_ROTORS.get(2).get(
                                        RotorManager.SELECTED_ROTORS.get(2).get(
                                                RotorManager.SELECTED_ROTORS.get(1).get(
                                                        RotorManager.SELECTED_ROTORS.get(0).get(charID))))));

        //RotorManager.AdvanceRotors();

        return retVal;

    }

}
