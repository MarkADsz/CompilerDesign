import scanner.FiniteAutomata;
import scanner.MyScanner;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String TOKEN_FILE="C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\input\\token.txt";
        FiniteAutomata intFA=new FiniteAutomata("C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\input\\integer.txt");
        FiniteAutomata idFA=new FiniteAutomata("C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\input\\identifier.txt");
        MyScanner scanner1=new MyScanner("C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\input\\p1.txt","C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\output\\p1",TOKEN_FILE,intFA,idFA);
        scanner1.start();

        MyScanner scanner1err=new MyScanner("C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\input\\p1err.txt","C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\output\\p1err",TOKEN_FILE,intFA,idFA);
        scanner1err.start();

        MyScanner scanner2=new MyScanner("C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\input\\p2.txt","C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\output\\p2",TOKEN_FILE,intFA,idFA);
        scanner2.start();

        MyScanner scanner3=new MyScanner("C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\input\\p3.txt","C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\output\\p3",TOKEN_FILE,intFA,idFA);
        scanner3.start();

        FiniteAutomata finiteAutomata=new FiniteAutomata("C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\input\\integer.txt");
        finiteAutomata.displayThings();

//        System.out.println(fa.checkAccepted("aa")) ;


    }
}