package tables;

public class MySymTable{
    MyHashTable symTable;

    public MySymTable(){
        this.symTable=new MyHashTable();
    }

    public void insert(Object key, Object value){
        this.symTable.insert(key, value);
    }

    public Object lookup(Object key){
        return this.symTable.lookup(key);
    }

    public boolean searchByKey(Object key){
        return this.lookup(key) != null;
    }


}
