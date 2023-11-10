package tables;

import model.MyPair;

import java.util.ArrayList;

public class MyPIFTable{

    ArrayList<MyPair<Object,Object>> pifTable;

    public MyPIFTable(){
        this.pifTable=new ArrayList<>();
    }

    public void insert(Object key, Object value) {
       pifTable.add(new MyPair<>(key, value));
    }

    @Override
    public String toString(){
        String returnString="";
        int index=0;
        for(MyPair pair:this.pifTable){
            index++;
            returnString+=index+".        "+pair+"\n";
        }
        return returnString;
    }
}
