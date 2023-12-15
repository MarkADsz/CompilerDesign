import grammar.Grammar;
import parser.Parser;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Grammar g = new Grammar("C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\input\\g1.txt");
        Parser p = new Parser(g);
        parserTests();
        while(true){
            System.out.println("1. Set of non terminals");
            System.out.println("2. Set of terminals");
            System.out.println("3. Set of productions");
            System.out.println("4. Productions for a given non terminal");
            System.out.println("5. CFG check");
            System.out.println("6. Compute first function for a given symbol");
            System.out.println("7. Compute follow function for a given symbol");
            System.out.println("8. Exit");
            var option = new java.util.Scanner(System.in).nextInt();
            if(option == 8) {
                break;
            }else if (option == 6){
                String symbol = new java.util.Scanner(System.in).nextLine();
                String first = p.first(symbol, new StringBuilder());
                System.out.println(first);
            }else if (option == 7){
                String symbol = new java.util.Scanner(System.in).nextLine();
                String follow = p.follow(symbol, new StringBuilder());
                System.out.println(follow);
            }else if(option > 7 || option < 1){
                System.out.println("Invalid option");
            } else{
                g.doAction(option);
            }
        }
    }


    static void parserTests(){
        Grammar g = new Grammar("inputs/g1.txt");
        Parser p = new Parser(g);

        assertEqualLists("First(S)", p.first("S", new StringBuilder()), "a");
        assertEqualLists("First(B)", p.first("B", new StringBuilder()), "c");
        assertEqualLists("First(C)", p.first("C", new StringBuilder()), "bε");
        assertEqualLists("First(D)", p.first("D", new StringBuilder()), "gε");
        assertEqualLists("First(E)", p.first("E", new StringBuilder()), "gε");
        assertEqualLists("First(F)", p.first("F", new StringBuilder()), "fε");

        assertEqualLists("Follow(S)", p.follow("S", new StringBuilder()), "$");
        assertEqualLists("Follow(B)", p.follow("B", new StringBuilder()), "gh");
        assertEqualLists("Follow(C)", p.follow("C", new StringBuilder()), "gh");
        assertEqualLists("Follow(D)", p.follow("D", new StringBuilder()), "h");
        assertEqualLists("Follow(E)", p.follow("E", new StringBuilder()), "h");
        System.out.println("All tests passed!");
    }

    private static <T> void assertEqualLists(String name, String actual, String expected) {
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
