package model;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ParseTable {
    private Map<Pair<String, String>, Pair<String, List<String>>> table = new HashMap<>();

    public void put(Pair<String, String> key, Pair<String, List<String>> value) {
        table.put(key, value);
    }

    public Pair<String, String> getKey(String nonTerminal, String element) {
        return this.table.keySet()
                .stream()
                .filter(pair -> pair.getKey().equals(nonTerminal) && pair.getValue().equals(element))
                .findFirst()
                .orElse(null);  // or handle the case when no match is found
    }


    public Pair<String, List<String>> get(Pair<String, String> key) {
        for (Map.Entry<Pair<String, String>, Pair<String, List<String>>> entry : table.entrySet()) {
            if (entry.getValue() != null) {
                Pair<String, String> currentKey = entry.getKey();
                Pair<String, List<String>> currentValue = entry.getValue();

                if (currentKey.getKey().equals(key.getKey()) && currentKey.getValue().equals(key.getValue())) {
                    return currentValue;
                }
            }
        }

        return null;
    }

    public boolean containsKey(Pair<String, String> key) {
        boolean result = false;
        for (Pair<String, String> currentKey : table.keySet()) {
            if (currentKey.getKey().equals(key.getKey()) && currentKey.getValue().equals(key.getValue())) {
                result = true;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Pair<String, String>, Pair<String, List<String>>> entry : table.entrySet()) {
            if (entry.getValue() != null) {
                Pair<String, String> key = entry.getKey();
                Pair<String, List<String>> value = entry.getValue();

                sb.append("M[").append(key.getKey()).append(",").append(key.getValue()).append("] = ")
                        .append(value.getKey()).append(" --> ").append(value.getValue()).append("\n");
            }
        }

        return sb.toString();
    }

}