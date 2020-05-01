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
    Collection<Farm> colF = milkDataFromFarms.values();
    for(Farm f: colF) {
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
    cf.insertSingleData("2019-1-1,Farm 6,63460");
    System.out.println(cf);
    cf.removeSingleData("2019-1-1,Farm 6,6760");
    System.out.println(cf);
  }
  
  public String getMonthlyAverage(String month) {
    Collection<Farm> colF = milkDataFromFarms.values();
    int m = Integer.parseInt(month);
    double total=0; int count=0;
    for(Farm f: colF) {
      total+=f.getMonthAverage(Integer.parseInt(month));
      count+=1;
    }
    return Double.toString(Math.round(total/count));
  }
  
  public String getMonthlyMin(String month) {
    Collection<Farm> colF = milkDataFromFarms.values();
    int m = Integer.parseInt(month);
    int min=Integer.MAX_VALUE;
    for(Farm f: colF) {
      int cur = f.getMin(Integer.parseInt(month));
      if(cur<min) {
        min = cur;
      }
    }
    return Integer.toString(min);
  }
  
  public String getMonthlyMax(String month) {
    Collection<Farm> colF = milkDataFromFarms.values();
    int m = Integer.parseInt(month);
    int max=0;
    for(Farm f: colF) {
      int cur = f.getMax(m);
      if(cur>max) {
        max = cur;
      }
    }
    return Integer.toString(max);
  }
  
  public String getMonthlyAverageForFarm(String month, String farm) {
    String farmID = farm.split(",")[1];
    return Double.toString(milkDataFromFarms.get(farmID).getMonthAverage(Integer.parseInt(month)));
  }
  
  public String getMonthlyMinForFarm(String month, String farm) {
    String farmID = farm.split(",")[1];
    return Integer.toString(milkDataFromFarms.get(farmID).getMin(Integer.parseInt(month)));
  }
  
  public String getMonthlyMaxForFarm(String month, String farm) {
    String farmID = farm.split(",")[1];
    return Integer.toString(milkDataFromFarms.get(farmID).getMax(Integer.parseInt(month)));
  }
  
  public String getDataSortedByField() {
    return "";
  }
  
  public String getAverageInDateRange(String start, String end) {
    Collection<Farm> colF = milkDataFromFarms.values();
    double total=0; int count=0;
    for(Farm f: colF) {
      total += f.getAverageInRange(start, end);
      count+=1;
    }
    return Double.toString(Math.round(total/count));
  }
  
  public String getMinInDateRange(String start, String end) {
    Collection<Farm> colF = milkDataFromFarms.values();
    int min=Integer.MAX_VALUE;
    for(Farm f: colF) {
      int cur = f.getMinInRange(start, end);
      if(cur<min) {
        min = cur;
      }
    }
    return Integer.toString(min);
  }
  
  public String getMaxInDateRange(String start, String end) {
    Collection<Farm> colF = milkDataFromFarms.values();
    int max=0;
    for(Farm f: colF) {
      int cur = f.getMinInRange(start, end);
      if(cur>max) {
        max = cur;
      }
    }
    return Integer.toString(max);
  }
  
}
