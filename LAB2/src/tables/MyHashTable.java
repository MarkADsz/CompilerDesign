package tables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyHashTable{

        Map<Integer, ArrayList<Object>> hashTable;

        public MyHashTable() {
                this.hashTable = new HashMap<>();
        }

        public ArrayList<Object> getByKey(Object key) {
                return this.hashTable.get(key);
        }

        public boolean searchByValue(Object value) {
                return this.hashTable.get(this.hash(value)).contains(value);
        }

        public void put(Object value) {
                int key=this.hash(value);
                if(this.hashTable.containsKey(key)){
                        this.hashTable.get(key).add(value);
                }
                else{
                        ArrayList<Object> values=new ArrayList<>();
                        values.add(value);
                        this.hashTable.put(key,values);
                }
        }

        private int hash(Object value){
                if(value instanceof Integer){
                        return  intHashing((Integer) value);
                }
                else{
                        return stringHashing((String) value);
                }
        }

        private int intHashing(int value){
                return value;
        }

        private int stringHashing(String value){
                // Sum up ASCII codes of characters in the string
                int hash = 0;
                for (int i = 0; i < value.length(); i++) {
                        hash += value.charAt(i);
                }
                return hash;
        }
}
