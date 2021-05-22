package me.bence056.enigma.commands;

import me.bence056.enigma.components.RotorManager;
import me.bence056.enigma.extras.RotorGenerator;
import me.bence056.enigma.interfaces.ICommandBase;

public class Decoder implements ICommandBase {


    public String[] args;

    @Override
    public void executeCommand(String[] args) {
        this.args = args;
        decode();
    }

    public void decode() {
        if(args.length>0) {
            doDecryption();
        }else {
            System.out.println("No argument specified");
        }
    }

    private void doDecryption() {


        for(char c : args[0].toUpperCase().replaceAll("\\s+","").toCharArray()) {

            System.out.println(feedThroughRotors(c) + "\n");
            RotorManager.TryAdvanceRotors();
        }



    }

    private int feedThroughRotors(char alpha) {

        int charID = RotorManager.AlphabetMap.get(alpha);

        int retVal = RotorManager.SELECTED_ROTORS_INVERTED.get(0).get(
                RotorManager.SELECTED_ROTORS_INVERTED.get(1).get(
                        RotorManager.SELECTED_ROTORS_INVERTED.get(2).get(
                                RotorManager.SELECTED_ROTORS_INVERTED.get(2).get(
                                        RotorManager.SELECTED_ROTORS_INVERTED.get(1).get(
                                                RotorManager.SELECTED_ROTORS_INVERTED.get(0).get(charID))))));

        //RotorManager.AdvanceRotors();

        return retVal;

    }

}
