package me.bence056.enigma.extras;

import java.util.HashMap;
import java.util.Map;

public class CommandMapper<K, V> extends HashMap<K, V> {

    public Map<K, String> DESC_MAP = new HashMap<>();

    public void BindDescription(K commandName, String description) {
        DESC_MAP.put(commandName, description);
    }

}
