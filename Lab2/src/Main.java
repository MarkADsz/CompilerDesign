import tables.MySymTable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) {
        MySymTable symTable = new MySymTable();

        //We will print everything in the out.txt file
        PrintStream out = null;
        try {
            out = new PrintStream(new FileOutputStream("out.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.setOut(out);

        // Insert key-value pairs
        symTable.insert("name", "John");
        symTable.insert("age", 30);
        symTable.insert(123, "Sample value");
        symTable.insert(123, "Sample value 2");

        // Look up values by keys
        System.out.println("Value for 'name': " + symTable.lookup("name"));
        System.out.println("Value for 'age': " + symTable.lookup("age"));
        System.out.println("Value for '123': " + symTable.lookup(123));
        System.out.println("Value for 'unknownKey': " + symTable.lookup("unknownKey")); // This should print 'null'

        // Test searchByKey method
        System.out.println("Search 'name' key: " + symTable.searchByKey("name")); // Should print 'true'
        System.out.println("Search 'unknownKey' key: " + symTable.searchByKey("unknownKey")); // Should print 'false'

    }
}