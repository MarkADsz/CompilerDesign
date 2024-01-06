package parser;

import grammar.Grammar;
import model.MyPair;
import model.Pair;
import model.ParseTable;

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
                this.parseTable.put(tableRowCol,null);
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
                    this.parseTable.put(tableRowCol,null);
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
                    Pair<String,String> key=new Pair<>(nonterminal,firstElements.get(i));
                    if (this.parseTable.get(key)==null) {
                        if(productions.size()<i){
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
                    List<String> followElements= Arrays.asList(followOfNonTerminal.split(" "));
                    for(String element: followElements){
                        Pair<String,String> key=new Pair<>(nonterminal,element);
                        if(this.parseTable.get(key)==null){
                            Pair<String,List<String>> value= new Pair<>(nonterminal,Arrays.asList("ε"));
                            this.parseTable.put(key,value);
                        }else{
                            //CONFLICT
                            System.out.println("Conflict");
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
                    first(firstOfElement,firsts);
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

//    public String follow(String nonTerminal, StringBuilder result) {
//        if(!this.grammar.getNonTerminals().contains(nonTerminal))
//            return "THIS IS NOT A NONTERMINAL";
//        if(nonTerminal.equals(this.grammar.getStartingSymbol())){
//            result.append("$");
//            return result.toString();
//        }
//        for(Map.Entry<String, List<String>> entry : this.grammar.getProductions().entrySet()){
//            for(String production : entry.getValue()){
//                String[] tokens=production.split(" ");
//                for(int i=0;i< tokens.length;i++){
//                    if(tokens[i].equals(nonTerminal)){
//                        if(tokens[i].equals(tokens[tokens.length-1])&& !Objects.equals(entry.getKey(), tokens[i])){
//                            result.append(follow(entry.getKey(), new StringBuilder()));
//                        }
//                        else for(int j=i+1; j< tokens.length; j+=1){
//                            if (this.grammar.getNonTerminals().contains(tokens[j])) {
//                                String first = first(tokens[j], new StringBuilder());
//                                if(!first.contains("ε")){
//                                    result.append(first);
//                                    break;
//                                } else{
//                                    result.append(first.replace("ε",""));
//                                }
//                            } else if (this.grammar.getTerminals().contains(tokens[j])) {
//                                result.append(tokens[j]);
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return result.toString();
//    }

}