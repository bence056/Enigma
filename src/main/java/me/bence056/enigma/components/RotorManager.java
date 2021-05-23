package me.bence056.enigma.components;

import me.bence056.enigma.extras.RotorGenerator;

import java.util.*;

public class RotorManager {


    public static List<Map<Integer, Integer>> SELECTED_ROTORS = new ArrayList<>();
    public static List<Map<Integer, Integer>> SELECTED_ROTORS_INVERTED = new ArrayList<>();
    public static Map<Character, Integer> AlphabetMap = new HashMap<>();
    public static Map<Integer, Character> InvertedAlphabetMap = new HashMap<>();
    public static Map<Integer, Integer> FirstRotorInverted = new HashMap<>();
    public static Map<Integer, Integer> SecondRotorInverted = new HashMap<>();
    public static Map<Integer, Integer> ThirdRotorInverted = new HashMap<>();
    private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static int fastRotorCounter = 0;
    private static int medRotorCounter = 0;
    private static int slowRotorCounter = 0;

    public static void initializeRotors(Map<Integer, Integer> FirstRotor, Map<Integer, Integer> SecondRotor, Map<Integer, Integer> ThirdRotor) {

        SELECTED_ROTORS.clear();


        for(int i=0; i<alphabet.length(); i++) {
            AlphabetMap.put((alphabet.toCharArray())[i], i+1);
            InvertedAlphabetMap.put(i+1, (alphabet.toCharArray())[i]);
        }

        SELECTED_ROTORS.add(FirstRotor);
        SELECTED_ROTORS.add(SecondRotor);
        SELECTED_ROTORS.add(ThirdRotor);

        createInvertedRotors();

        //System.out.println(FirstRotor);
        //System.out.println(FirstRotorInverted);
        //System.out.println(SecondRotor);
        //System.out.println(SecondRotorInverted);
        //System.out.println(ThirdRotor);
        //System.out.println(ThirdRotorInverted);



    }

    public static void TryAdvanceRotors() {

        AdvanceRotor(SELECTED_ROTORS.get(0));
        fastRotorCounter++;
        if(fastRotorCounter==26) {
            fastRotorCounter = 0;
            AdvanceRotor(SELECTED_ROTORS.get(1));
            medRotorCounter++;
            if(medRotorCounter==26) {
                medRotorCounter = 0;
                AdvanceRotor(SELECTED_ROTORS.get(2));
                slowRotorCounter++;
                if(slowRotorCounter==26) {
                    slowRotorCounter = 0;
                }
            }
        }
        createInvertedRotors();
        //System.out.println(SELECTED_ROTORS.get(0));
        //System.out.println(FirstRotorInverted);
        //System.out.println(SELECTED_ROTORS.get(1));
        //System.out.println(SecondRotorInverted);
        //System.out.println(SELECTED_ROTORS.get(2));
        //System.out.println(ThirdRotorInverted);
    }

    private static void AdvanceRotor(Map<Integer, Integer> RotorToAdvance) {


        Integer[] advanced = new Integer[26];

        for(int i=0; i<26; i++) {

            advanced[i] = RotorToAdvance.get(i+1);
        }

        List<Integer> AdvancedList = new ArrayList<>(Arrays.asList(advanced));

        int removedDigit = AdvancedList.get(AdvancedList.size()-1);
        AdvancedList.remove(AdvancedList.size()-1);
        Collections.reverse(AdvancedList);
        AdvancedList.add(removedDigit);
        Collections.reverse(AdvancedList);

        for(int i=0; i<26; i++) {
            RotorToAdvance.put(i+1, AdvancedList.get(i));
        }

        createInvertedRotors();

        List<Integer> enabled_rots = RotorGenerator.GetEnabledRotors();

        //System.out.println(enabled_rots.get(1));

        //RotorGenerator.ROTORS.get(enabled_rots.get(0)).clear();
        //RotorGenerator.ROTORS.get(enabled_rots.get(1)).clear();
        //RotorGenerator.ROTORS.get(enabled_rots.get(2)).clear();

            //System.out.println(RotorGenerator.ROTORS.get(enabled_rots.get(0)));

        //RotorGenerator.ROTORS.get(enabled_rots.get(0)).putAll(SELECTED_ROTORS.get(0));
        //RotorGenerator.ROTORS.get(enabled_rots.get(1)).putAll(SELECTED_ROTORS.get(1));
        //RotorGenerator.ROTORS.get(enabled_rots.get(2)).putAll(SELECTED_ROTORS.get(2));

    }

    private static void createInvertedRotors() {

        SELECTED_ROTORS_INVERTED.clear();
        FirstRotorInverted.clear();
        SecondRotorInverted.clear();
        ThirdRotorInverted.clear();

        //System.out.println("Empty? "+FirstRotorInverted);

        for(Map.Entry<Integer, Integer> entry : SELECTED_ROTORS.get(0).entrySet()) {
            FirstRotorInverted.put(entry.getValue(), entry.getKey());
            //System.out.println(entry.getKey());
            //System.out.println(entry.getValue()+"\n");
        }

        for(Map.Entry<Integer, Integer> entry : SELECTED_ROTORS.get(1).entrySet()) {
            SecondRotorInverted.put(entry.getValue(), entry.getKey());
        }
        for(Map.Entry<Integer, Integer> entry : SELECTED_ROTORS.get(2).entrySet()) {
            ThirdRotorInverted.put(entry.getValue(), entry.getKey());
        }

        //System.out.println("INVERTED "+FirstRotorInverted);

        SELECTED_ROTORS_INVERTED.add(FirstRotorInverted);
        SELECTED_ROTORS_INVERTED.add(SecondRotorInverted);
        SELECTED_ROTORS_INVERTED.add(ThirdRotorInverted);
    }

}
