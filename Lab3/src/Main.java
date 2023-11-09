import scanner.MyScanner;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String TOKEN_FILE="C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\input\\token.txt";
        MyScanner scanner1=new MyScanner("C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\input\\p1.txt","C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\output\\p1",TOKEN_FILE);
        scanner1.start();

        MyScanner scanner1err=new MyScanner("C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\input\\p1err.txt","C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\output\\p1err",TOKEN_FILE);
        scanner1err.start();

        MyScanner scanner2=new MyScanner("C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\input\\p2.txt","C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\output\\p2",TOKEN_FILE);
        scanner2.start();

        MyScanner scanner3=new MyScanner("C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\input\\p3.txt","C:\\UBB_FMI\\FLCD\\GitHub\\CompilerDesign\\Lab3\\output\\p3",TOKEN_FILE);
        scanner3.start();


    }
}