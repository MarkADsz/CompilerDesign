package scanner;

import tables.MyHashTable;
import tables.MyPIFTable;
import tables.MySymTable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyScanner {
    private String inputFile;
    private String outputFile;
    private String tokenFile;

    private MySymTable symTable;

    private MyHashTable  pif;

    private ArrayList<String> tokens;

    private int indexSymbolTable=0;

    private boolean isError;

    public MyScanner(String inputFile,String outputFile, String tokenFile) throws FileNotFoundException {
        this.inputFile=inputFile;
        this.outputFile=outputFile;
        this.tokenFile=tokenFile;
        this.symTable = new MySymTable();
        this.pif = new MyPIFTable();
        this.readTokens();
        this.isError=false;
        PrintStream fileStream = new PrintStream(this.outputFile);
        System.setOut(fileStream);
    }

    private void readTokens(){
        List<String> tokensList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(tokenFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    tokensList.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error reading tokens file: " + e.getMessage());
        }
        this.tokens= (ArrayList<String>) tokensList;
    }

    public void start() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.inputFile))) {
            int currentChar;
            StringBuilder currentString = new StringBuilder();
            boolean insideGroup = false;

            int lineCount = 1;
            int tokenPosition = 0;

            while ((currentChar = reader.read()) != -1) { //We take character by character
                char character = (char) currentChar;

                // Skip endlines
                if (character == '\n') {
                    lineCount++;
                    tokenPosition = 0;
                }

                else if (character == ' ') {
                    if (insideGroup) {
                        String extractedString = currentString.toString();
                        this.addToken(extractedString, lineCount, tokenPosition);
                        tokenPosition++;
                        insideGroup = false;
                        currentString.setLength(0);
                    }
                }
                // Handle separators and operators
                else if (isSeparator(character) || isOperator(character)) {
                    if (insideGroup) {
                        String extractedString = currentString.toString();
                        this.addToken(extractedString, lineCount, tokenPosition);
                        tokenPosition++;
                        insideGroup = false;
                        currentString.setLength(0);
                    }
                    // Add the separator or operator as a separate token
                    if(this.isKeyword(String.valueOf(character))){
                        this.addToken(String.valueOf(character), lineCount, tokenPosition);
                        tokenPosition++;
                    }
                } else {
                    currentString.append(character);
                    insideGroup = true;
                }
            }
            this.printTables();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToken(String token, int lineCount, int tokenPosition) {
        token=token.trim();
        if(!token.isEmpty() && !token.equals("\r")) {
            if (this.isKeyword(token)) {
                this.pif.insert(token, "-");
            } else if (this.isIdentifier(token)) {
                int symKey = addToSymTable(token);
                this.addToPIF(token, "id", symKey);
            } else if (this.isConstant(token)) {
                int symKey = addToSymTable(token);
                this.addToPIF(token, "const", symKey);
            } else{
                System.out.println("Lexical error at Line " + lineCount + ", Token " + tokenPosition + ": Invalid Token - " + token);
                this.isError=true;
            }
        }
    }

    private int addToSymTable(Object token){
        this.symTable.insert(token,this.indexSymbolTable);
        int returnedIndex=this.indexSymbolTable;
        this.indexSymbolTable++;
        return returnedIndex;
    }

    private void addToPIF(String token,String type, int symKey){
        if(this.symTable.searchByKey(token)) {
            this.pif.insert(type,this.symTable.lookup(token));
        } else{
            this.pif.insert(type, symKey);
        }
    }

    private void printTables(){
        if(!this.isError){
            System.out.println("PIF TABLE");
            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.println(this.pif);
            System.out.println("SYM TABLE");
            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.println(this.symTable);
        }

    }

    private static boolean isSeparator(char character) {
        // Check if the character is a separator (e.g., '{', '}', '(', ')', ';')
        return character == '{' || character == '}' || character == '(' || character == ')' || character == ';' || character == ',' || character == '[' || character == ']';
    }

    private static boolean isOperator(char character) {
        // Check if the character is an operator (e.g., '>', '<', '=', '+', '-', '*', '/')
        return character == '>' || character == '<' || character == '=' || character == '+' || character == '-'
                || character == '*' || character == '/' || character == '%';
    }

    private boolean isKeyword(String token) {
        return this.tokens.contains(token);
    }

    private boolean isIdentifier(String token) {
        return token.matches("^[a-zA-Z_][a-zA-Z0-9_<>,]*$");
    }

    private boolean isConstant(String token) {
        if (token.matches("^-?\\d+$")) {
            return true;
        }

        if (token.matches("^\"[a-zA-Z0-9_]*\"")) {
            return true;
        }

        if (token.matches("^true|false")) {
            return true;
        }
        return false;
    }

}
