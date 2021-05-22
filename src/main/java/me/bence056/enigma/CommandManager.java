package me.bence056.enigma;

import me.bence056.enigma.commands.Decoder;
import me.bence056.enigma.commands.Encoder;
import me.bence056.enigma.extras.RotorGenerator;
import me.bence056.enigma.interfaces.ICommandBase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    public static final Map<String, ICommandBase> COMMAND_STRUCTURE = new HashMap<String, ICommandBase>();

    public static void initialize() {

        COMMAND_STRUCTURE.put("encode", new Encoder());
        COMMAND_STRUCTURE.put("decode", new Decoder());
        COMMAND_STRUCTURE.put("generate_rotors", new RotorGenerator());

    }

    public static void checkParameters(String[] args) {


        if(COMMAND_STRUCTURE.containsKey(args[0])) {

            String[] shortened = Arrays.copyOfRange(args, 1, args.length);
            COMMAND_STRUCTURE.get(args[0]).executeCommand(shortened);
        }

    }


}
