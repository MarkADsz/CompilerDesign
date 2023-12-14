import grammar.Grammar;
import parser.Parser;
import scanner.FiniteAutomata;
import scanner.MyScanner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Grammar g = new Grammar("C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\input\\g1.txt");
        while(true){
            System.out.println("1. Set of non terminals");
            System.out.println("2. Set of terminals");
            System.out.println("3. Set of productions");
            System.out.println("4. Productions for a given non terminal");
            System.out.println("5. CFG check");
            System.out.println("6. Exit");
            var option = new java.util.Scanner(System.in).nextInt();
            if(option == 6) {
                break;
            }else if(option > 5 || option < 1){
                System.out.println("Invalid option");
            } else{
                g.doAction(option);
            }
        }
        Parser parser=new Parser(g);
        parserTests();

    }

    static void parserTests(){
        Grammar yourGrammar = new Grammar("C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\input\\g1.txt");

        Parser parser = new Parser(yourGrammar);

        // Test First sets
        assertEqualLists("First(E)", parser.first("E"), Arrays.asList("(", "i"));
        assertEqualLists("First(R)", parser.first("R"), Arrays.asList("+"));
        assertEqualLists("First(T)", parser.first("T"), Arrays.asList("(", "i"));
        assertEqualLists("First(Y)", parser.first("Y"), Arrays.asList("*"));
        assertEqualLists("First(F)", parser.first("F"), Arrays.asList("(", "i"));

        // Test Follow sets
//        assertEqualLists("Follow(E)", parser.follow("E", new ArrayList<>()), Arrays.asList("$", ")"));
//        assertEqualLists("Follow(R)", parser.follow("R", new ArrayList<>()), Arrays.asList("$", ")"));
//        assertEqualLists("Follow(T)", parser.follow("T", new ArrayList<>()), Arrays.asList("+", "$", ")"));
//        assertEqualLists("Follow(Y)", parser.follow("Y", new ArrayList<>()), Arrays.asList("+", "$", ")"));
//        assertEqualLists("Follow(F)", parser.follow("F", new ArrayList<>()), Arrays.asList("*", "+", "$", ")"));

        System.out.println("All tests passed!");
    }

    private static <T> void assertEqualLists(String name, List<T> actual, List<T> expected) {
        if (!actual.equals(expected)) {
            System.out.println(name + " - Test FAILED");
            System.out.println("Expected: " + expected);
            System.out.println("Actual:   " + actual);
            System.exit(1);
        } else {
            System.out.println(name + " - Test passed");
        }
    }
    }
