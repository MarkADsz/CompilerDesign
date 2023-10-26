package tables;

import model.MyPair;

import java.util.ArrayList;

public class MyHashTable{

        private ArrayList<ArrayList<MyPair<Object,Object>>> hashTable;
        private static final int TABLE_SIZE = 10;

        public MyHashTable() {
                this.hashTable = new ArrayList<>(TABLE_SIZE);
                for (int i = 0; i < TABLE_SIZE; i++) {
                        this.hashTable.add(new ArrayList<>());
                }
        }

        public void insert(Object key, Object value) {
                int index = this.hash(key);
                ArrayList<MyPair<Object, Object>> indexArray = hashTable.get(index);
                indexArray.add(new MyPair<>(key, value));
        }

        public Object lookup(Object key) {
                int index = this.hash(key);
                ArrayList<MyPair<Object, Object>> indexArray = hashTable.get(index);
                for (MyPair<Object, Object> pair : indexArray) {
                        if (pair.getKey() == key) {
                                return pair.getValue();
                        }
                }
                return null; // key was not found
        }


        private int hash(Object value){
                if(value instanceof Integer){
                        return  intHashing((Integer) value) % TABLE_SIZE;
                }
                else{
                        return stringHashing((String) value) % TABLE_SIZE;
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
