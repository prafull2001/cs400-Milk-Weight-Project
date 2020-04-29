package application;

import java.util.Hashtable;

public class CheeseFactory {
  private String name;
  Hashtable<String, Farm> milkDataFromFarms;
  
  public CheeseFactory(String name) {
    this.name = name;
    milkDataFromFarms = new Hashtable<String, Farm>();
  }
  
  public boolean insertData(String data) {
    boolean ret = true;
    String[] datas = data.split("\n");
    for(String s: datas) {
      if(ret&&!insertSingleData(s)) {
        ret = false;
      } else {
        insertSingleData(s);
      }
    }
    return ret;
  }
  
  public boolean insertSingleData(String data) {
    String[] datas = data.split(",");
    int index = datas[1].hashCode();
    data = datas[0]+" "+datas[2];
    if(milkDataFromFarms.get(index)==null) {
      milkDataFromFarms.put(datas[1], new Farm(data));
      return true;
    } else {
      return milkDataFromFarms.get(index).insertMilkForDate(data);
    }
  }
  
  public boolean editSingleData(String oldData, String newData) {
    String[] oldDatas = oldData.split(",");
    int index = oldDatas[1].hashCode();
    oldData = oldDatas[0]+" "+oldDatas[2];
    String[] newDatas = newData.split(",");
    newData = newDatas[0]+" "+newDatas[2];
    if(milkDataFromFarms.get(index)==null) {
      return false;
    } else {
      return milkDataFromFarms.get(index).editMilkForDate(oldData, newData);
    }
  }
  
  public String removeSingleData(String data) {
    String[] datas = data.split(",");
    int index = datas[1].hashCode();
    data = datas[0]+" "+datas[2];
    if(milkDataFromFarms.get(index)==null) {
      return null;
    } else {
      return milkDataFromFarms.get(index).removeMilkForDate(data);
    }
  }
}
