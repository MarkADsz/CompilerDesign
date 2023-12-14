package parser;

import grammar.Grammar;

import java.util.*;

public class Parser {

    private Grammar grammar;

    public Parser(Grammar grammar){
        this.grammar=grammar;
//        this.firsts=new ArrayList<>();

    }

    private List<String> first(String nonTerminal, List<String> firsts){
        for(String element:this.grammar.getProductions().get(nonTerminal)){
            String firstOfElement=String.valueOf(element.charAt(0));
            if(this.grammar.getTerminals().contains(firstOfElement) || firstOfElement.equals("ε")){
                firsts.add(firstOfElement);
            }
            else if(this.grammar.getNonTerminals().contains(firstOfElement)){
                    first(firstOfElement,firsts);
                }
        }
        return firsts;
    }

    public List<String> first(String nonTerminal){
        return this.first(nonTerminal,new ArrayList<>());
    }

    public List<String> follow(String nonTerminal, List<String> result) {
        if(nonTerminal.equals(this.grammar.getStartingSymbol())){
            result.add("$");
        }
        for(Map.Entry<String,List<String>> production: this.grammar.getProductions().entrySet()){
            for(String productionRHS:production.getValue()){
                if(productionRHS.contains(nonTerminal)){
                    //placement of the nonterminal
                    int index=productionRHS.indexOf(nonTerminal);
                    //if there is nothing after we add the Follow of LHS
                    if(index==productionRHS.length()-1){
                            result.addAll(follow(String.valueOf(productionRHS.charAt(index)),new ArrayList<>()));
                    }else
                        //if there is a nonterminal after the index
                        if(this.grammar.getNonTerminals().contains(String.valueOf(productionRHS.charAt(index+1)))){
                            result.addAll(this.first(String.valueOf(productionRHS.charAt(index+1)),new ArrayList<>()));
                        } else if (this.grammar.getTerminals().contains(String.valueOf(productionRHS.charAt(index+1)))) { //if there is a terminal after index
                            result.add(String.valueOf(productionRHS.charAt(index+1)));
                        }
                }
            }


        }

    return result;
    }

}
