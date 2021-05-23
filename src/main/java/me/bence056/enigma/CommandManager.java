package me.bence056.enigma;

import me.bence056.enigma.commands.Encoder;
import me.bence056.enigma.commands.SaveInitital;
import me.bence056.enigma.extras.CommandMapper;
import me.bence056.enigma.extras.RotorGenerator;
import me.bence056.enigma.interfaces.ICommandBase;

import java.util.Arrays;

public class CommandManager {

    //public static final Map<String, ICommandBase> COMMAND_STRUCTURE = new HashMap<String, ICommandBase>();
    public static final CommandMapper<String, ICommandBase> COMMAND_STRUCTURE = new CommandMapper<>();


    public static void initialize() {

        COMMAND_STRUCTURE.put("encode", new Encoder());
        COMMAND_STRUCTURE.put("decode", new Encoder());
        COMMAND_STRUCTURE.put("generate_combination", new RotorGenerator());
        COMMAND_STRUCTURE.put("generate_rotors", new RotorGenerator());
        COMMAND_STRUCTURE.put("save_current", new SaveInitital());

        COMMAND_STRUCTURE.BindDescription("encode", "Runs the encoding algorithm.");
        COMMAND_STRUCTURE.BindDescription("decode", "Same as 'encode'. (Only decodes cypher if the correct settings are set)");
        COMMAND_STRUCTURE.BindDescription("generate_combination", "Creates a new Rotor Pool and plugboard settings. Use the -save parameter to save the config.");
        COMMAND_STRUCTURE.BindDescription("generate_rotors", "Same as 'generate_combination'.");

    }

    public static void checkParameters(String[] args) {


        if(COMMAND_STRUCTURE.containsKey(args[0])) {

            String[] shortened = Arrays.copyOfRange(args, 1, args.length);
            COMMAND_STRUCTURE.get(args[0]).executeCommand(shortened);
        }

    }


}
