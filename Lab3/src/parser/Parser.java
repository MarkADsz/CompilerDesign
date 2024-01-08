package parser;

import grammar.Grammar;
import model.MyPair;
import model.Pair;
import model.ParseTable;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Parser {

    private ParseTable parseTable;

    private Grammar grammar;

    public Parser(Grammar grammar){
        this.grammar=grammar;
        this.parseTable=new ParseTable();

    }


    public void initializeTable(List<String> nonTerminals, List<String> terminals){

        for(String nonTerminal:nonTerminals){
            for(String terminal: terminals){
                Pair<String,String> tableRowCol= new Pair<>(nonTerminal,terminal);
                this.parseTable.put(tableRowCol,new Pair<>("",Arrays.asList()));
            }
        }

        for(String t1:terminals){
            for(String t2:terminals){
                Pair<String,String> tableRowCol= new Pair<>(t1,t2);
                if(t1.equals(t2)){
                    if(t1.equals("$")){
                        this.parseTable.put(tableRowCol,new Pair<>("",Arrays.asList("accept")));
                    }else{
                        this.parseTable.put(tableRowCol,new Pair<>("",Arrays.asList("pop")));
                    }
                }
                else{
                    this.parseTable.put(tableRowCol,new Pair<>("",Arrays.asList()));
                }

            }
        }
    }


    public void createParseTable(){
        List<String> nonTerminals=new ArrayList<>(this.grammar.getNonTerminals());
        List<String> terminals=new ArrayList<>(this.grammar.getTerminals());
        terminals.add("$");

        this.initializeTable(nonTerminals,terminals);

        nonTerminals.forEach(nonterminal -> {
            String firstOfNonTerminal = this.first(nonterminal,new StringBuilder());
            List<String> firstElements= Arrays.asList(firstOfNonTerminal.split(" "));

            List<String> productions= this.grammar.getProductions().get(nonterminal);

            for(int i=0;i<firstElements.size();i++){
                if(!firstElements.get(i).equals("ε")){
                    Pair<String,String> key=this.parseTable.getKey(nonterminal,firstElements.get(i));
                    if (this.parseTable.get(key).getKey().equals("") && this.parseTable.get(key).getValue().size()==0) {
                        if(productions.size()>i){
                            Pair<String,List<String>> value= new Pair<>(nonterminal,Arrays.asList(productions.get(i)));
                            this.parseTable.put(key,value);
                        }

                    }else{
                        //CONFLICT
                        System.out.println("Conflict!");
                    }
                }
                else{
                    String followOfNonTerminal = this.follow(nonterminal,new StringBuilder());

                    List<String> followElements= Arrays.asList(followOfNonTerminal.strip().split(" "));
                    for(String element: followElements){
                        if(!element.equals("")){
                            Pair<String,String> key=this.parseTable.getKey(nonterminal,element);
                            if(this.parseTable.get(key).getKey().equals("") && this.parseTable.get(key).getValue().size()==0){
                                Pair<String,List<String>> value= new Pair<>(nonterminal,Arrays.asList("ε"));
                                this.parseTable.put(key,value);
                            }else{
                                //CONFLICT
                                System.out.println("Conflict");
                            }
                            }
                    }

                }
            }
        });
    }


    public ParseTable getParseTable() {
        return this.parseTable;
    }

    public String first(String nonTerminal, StringBuilder firsts){
        return this.first(nonTerminal,firsts,new HashSet<>());
    }

    public String first(String nonTerminal, StringBuilder firsts, Set<String> visited){
        if(visited.contains(nonTerminal)){
            return firsts.toString();
        }
        visited.add(nonTerminal);
        for(String element:this.grammar.getProductions().get(nonTerminal)){
            String[] tokens=element.split(" ");
            String firstOfElement=tokens[0];
            if(!firstOfElement.equals(nonTerminal)){
                if(this.grammar.getTerminals().contains(firstOfElement) || firstOfElement.equals("ε")){
                    if(!firsts.toString().contains(firstOfElement))
                    {
                        firsts.append(firstOfElement);
                        firsts.append(" ");
                    }
                }
                else if(this.grammar.getNonTerminals().contains(firstOfElement)){
                    first(firstOfElement,firsts,visited);
                }
            }

        }
        return firsts.toString();
    }

    public String follow(String nonTerminal, StringBuilder result) {
        if (!this.grammar.getNonTerminals().contains(nonTerminal))
            return "THIS IS NOT A NONTERMINAL";

        if (nonTerminal.equals(this.grammar.getStartingSymbol())) {
            result.append("$");
            return result.toString();
        }

        Set<String> visited = new HashSet<>(); // Track visited non-terminals to avoid infinite loops

        return followHelper(nonTerminal, result, visited);
    }

    private String followHelper(String nonTerminal, StringBuilder result, Set<String> visited) {
        visited.add(nonTerminal);

        for (Map.Entry<String, List<String>> entry : this.grammar.getProductions().entrySet()) {
            for (String production : entry.getValue()) {
                String[] tokens = production.split(" ");
                boolean foundNonTerminal = false;

                for (int i = 0; i < tokens.length; i++) {
                    if (foundNonTerminal) {
                        if (this.grammar.getNonTerminals().contains(tokens[i])) {
                            String first = first(tokens[i], new StringBuilder());
                            if (!first.contains("ε")) {
                                if(!result.toString().contains(first)) {
                                    result.append(first);
                                    result.append(" ");
                                }
                                break;
                            } else {
                                result.append(first.replace("ε", ""));
                            }
                        } else if (this.grammar.getTerminals().contains(tokens[i])) {
                            if(!result.toString().contains(tokens[i]))
                            {
                                result.append(tokens[i]);
                                result.append(" ");
                            }
                            break;
                        }
                    }

                    if (tokens[i].equals(nonTerminal)) {
                        foundNonTerminal = true;

                        if (i == tokens.length - 1 && !Objects.equals(entry.getKey(), nonTerminal)) {
                            if (!visited.contains(entry.getKey())) {
                                result.append(followHelper(entry.getKey(), new StringBuilder(), visited));
                            }
                        }
                    }
                }
            }
        }

        return result.toString();
    }



    public void printTable(String file){
        if(file.equals("g1")){
            try (FileWriter fileWriter = new FileWriter("C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\output\\g1-parseTable.txt")) {
                fileWriter.write(this.parseTable.toString());
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception according to your needs
            }
        }
        else {
            try (FileWriter fileWriter = new FileWriter("C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\output\\g2-parseTable.txt")) {
                fileWriter.write(this.parseTable.toString());
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception according to your needs
            }
        }
    }

}