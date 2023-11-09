package tables;

import model.MyPair;

import java.util.ArrayList;

public class MyPIFTable extends MyHashTable{
    @Override
    public void insert(Object key, Object value) {
        int index = super.hash(key);
        ArrayList<MyPair<Object, Object>> indexArray = hashTable.get(index);
        indexArray.add(new MyPair<>(key, value));
    }
}
