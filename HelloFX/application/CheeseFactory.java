package application;

import java.util.Collection;
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
   * @param data - multiple lines of data to insert all in one String
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
  
  /**
   * @param data - multiple lines of data to insert in one array, broken up by line
   * @return - true if all datas were successfully added
   */
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
    data = datas[0]+" "+datas[2];
    if(milkDataFromFarms.get(datas[1])==null) {
      Farm f = new Farm(datas[1]);
      f.insertMilkForDate(data);
      milkDataFromFarms.put(datas[1], f);
      return true;
    } else {
      return milkDataFromFarms.get(datas[1]).insertMilkForDate(data);
    }
  }
  
  /**
   * @param data - data to remove
   * @return - a copy of the data just removed
   */
  public String removeSingleData(String data) {
    String[] datas = data.split(",");
    data = datas[0]+" "+datas[2];
    if(milkDataFromFarms.get(datas[1])==null) {
      return null;
    } else {
      return milkDataFromFarms.get(datas[1]).removeMilkForDate(data);
    }
  }
  
  public String toString() {
    StringBuilder sb = new StringBuilder();
    Collection<Farm> setf = milkDataFromFarms.values();
    for(Farm f: setf) {
      sb.append("----------"+f.getID()+"----------\n");
      sb.append(f.toString());
    }
    return sb.toString();
  }
  
  public static void main(String[] args) {
    CheeseFactory cf = new CheeseFactory();
    cf.insertSingleData("2019-1-1,Farm 0,6760");
    cf.insertSingleData("2019-1-1,Farm 1,6760");
    cf.insertSingleData("2019-1-1,Farm 2,6760");
    cf.insertSingleData("2019-1-1,Farm 3,6760");
    cf.insertSingleData("2019-1-1,Farm 4,6760");
    cf.insertSingleData("2019-1-1,Farm 5,6760");
    cf.insertSingleData("2019-1-1,Farm 6,6760");
    cf.removeSingleData("2019-1-1,Farm 6,6760");
    System.out.println(cf);
  }
  
  public String getMonthlyAverage(String month) {    
  }
  
  public String getMonthlyMin(String month) {
  }

  public String getMonthlyMax(String month) {
  }
  
  public String getMonthlyAverageForFarm(String month, String farm) {
  }
  
  public String getMonthlyMinForFarm(String month, String farm) {
  }
  
  public String getMonthlyMaxForFarm(String month, String farm) {
  }
  
  public String getDataSortedByField() {
  }
  
  public String getAverageInDateRange(String start, String end) {
  }
  
  public String getMinInDateRange(String start, String end) {
  }
  
  public String getMaxInDateRange(String start, String end) {
  }
  
}
