package grammar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Grammar {
    private List<String> nonTerminals;
    private List<String> terminals;
    private Map<String, List<String>> productions;
    private String startingSymbol;
    private Boolean isCFG;
    private String filename;

    public Grammar(String filename) {
        this.nonTerminals = new ArrayList<>();
        this.terminals = new ArrayList<>();
        this.productions = new HashMap<>();
        this.startingSymbol = "";
        this.isCFG = false;
        this.filename = filename;
        readFromFile(this.filename);
    }

    private void readFromFile(String filename){
        try{
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                lineCount += 1;
                if(lineCount == 1){
                    String[] tokens = line.split(",");
                    this.nonTerminals = Arrays.asList(tokens);
                } else if(lineCount == 2){
                    String[] tokens = line.split(",");
                    this.terminals = Arrays.asList(tokens);
                } else if(lineCount == 3){
                    this.startingSymbol = line;
                } else {
                    String[] tokens = line.split("->");
                    String[] productions = tokens[1].split("\\|");
                    for(String s : productions){
                        String[] itemsInProduction = s.split(" ");
                        for(String item:itemsInProduction){
                            if(this.terminals.contains(item))
                                continue;
                            if(this.nonTerminals.contains(item))
                                continue;
                            throw new IOException();
                        }
                    }
                    this.productions.put(tokens[0], Arrays.asList(productions));
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Invalid production!");
        }
    }

    private boolean checkIfCFG(){
        for(String key: this.productions.keySet()){
            if(key.contains(","))
                return false;
            if(!this.nonTerminals.contains(key))
                return false;
        }
        return true;
    }

    public void doAction(int option){
        if(option == 1){
            System.out.println(this.nonTerminals);
        } else if (option == 2) {
            System.out.println(this.terminals);
        } else if (option == 3) {
            System.out.println(this.productions);
        } else if (option == 4){
            String element = new java.util.Scanner(System.in).nextLine();
            System.out.println(this.productions.get(element));
        } else if (option == 5) {
            this.checkIfCFG();
            if(this.checkIfCFG()){
                System.out.println("The given grammar is a CFG.");
            } else{
                System.out.println("The given grammar is not a CFG.");
            }
        }
    }

    public List<String> getNonTerminals() {
        return nonTerminals;
    }

    public List<String> getTerminals() {
        return terminals;
    }

    public Map<String, List<String>> getProductions() {
        return productions;
    }

    public String getStartingSymbol() {
        return startingSymbol;
    }

    public Boolean getCFG() {
        return isCFG;
    }

    public String getFilename() {
        return filename;
    }
}