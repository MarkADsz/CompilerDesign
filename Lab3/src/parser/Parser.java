package parser;

import grammar.Grammar;

import java.util.*;

public class Parser {

    private Grammar grammar;

    public Parser(Grammar grammar){
        this.grammar=grammar;
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