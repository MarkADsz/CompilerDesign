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
            String firstOfElement=String.valueOf(element.charAt(0));
            if(this.grammar.getTerminals().contains(firstOfElement) || firstOfElement.equals("ε")){
                firsts.append(firstOfElement);
            }
            else if(this.grammar.getNonTerminals().contains(firstOfElement)){
                first(firstOfElement,firsts);
            }
        }
        return firsts.toString();
    }

    public String follow(String nonTerminal, StringBuilder result) {
        if(nonTerminal.equals(this.grammar.getStartingSymbol())){
            result.append("$");
            return result.toString();
        }
        for(Map.Entry<String, List<String>> entry : this.grammar.getProductions().entrySet()){
            for(String production : entry.getValue()){
                for(int i=0; i<production.length(); i+=1){
                    if(String.valueOf(production.charAt(i)).equals(nonTerminal)){
                        if(i == production.length() - 1 && !Objects.equals(entry.getKey(), String.valueOf(production.charAt(i)))){
                            result.append(follow(entry.getKey(), new StringBuilder()));
                        }
                        else for(int j=i+1; j<production.length(); j+=1){
                            if (this.grammar.getNonTerminals().contains(String.valueOf(production.charAt(j)))) {
                                String first = first(String.valueOf(production.charAt(j)), new StringBuilder());
                                if(!first.contains("ε")){
                                    result.append(first);
                                    break;
                                } else{
                                    result.append(first.replace("ε",""));
                                }
                            } else if (this.grammar.getTerminals().contains(String.valueOf(production.charAt(j)))) {
                                result.append(production.charAt(j));
                                break;
                            }
                        }
                    }
                }
            }
        }
        return result.toString();
    }

}