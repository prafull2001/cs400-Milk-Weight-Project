package application;

import java.util.Hashtable;

/**
 * 
 * @author Dhruv Jain
 * 
 * This class 
 *
 */
public class CheeseFactory {
  Hashtable<String, Farm> milkDataFromFarms;
  
  public CheeseFactory() {
    milkDataFromFarms = new Hashtable<String, Farm>();
  }
  
  /**
   * @param data - multiple lines of data to insert
   * @return - true if all datas were successfully added
   */
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
  
  public boolean insertData(String[] datas) {
    boolean ret = true;
    for(String s: datas) {
      if(ret&&!insertSingleData(s)) {
        ret = false;
      } else {
        insertSingleData(s);
      }
    }
    return ret;
  }
  
  /**
   * @param data - Single data to add to hash table
   * @return - true if data was inserted successfully
   */
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
  
  /**
   * @param data - data to remove
   * @return - a copy of the data just removed
   */
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
