package application;

import java.util.Collection;
import java.util.Hashtable;

/*
 * 
 * @author Dhruv Jain, Shrey Shah, Prafull Sharma, Arkin Katery
 * 
 * This class instantiates a "Cheese" Factory object. Tracks farms associated to it in a hashtable. Inserts and removes 
 * various data to files in various forms  
 *
 */
public class CheeseFactory {
  Hashtable<String, Farm> milkDataFromFarms;
  
  public CheeseFactory() {
    milkDataFromFarms = new Hashtable<String, Farm>();
  }
  
  public int getNumFarms() {
    return milkDataFromFarms.size();
  }
  
  /**
   * Method inserts multiple lines of data to file in a single string format
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
   * Method inserts multiple lines of data in a string array format
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
   * Method inserts single pieces of data to the hashtable
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
   * Mehtod removes single pieces of data from the hashtable
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
  
  /*
   * Method overrides the toString method for the Cheesfactory class and returns elements as strings
   *  
   * @return - farm IDs stored in hashtable
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    Collection<Farm> colF = milkDataFromFarms.values();
    for(Farm f: colF) {
      sb.append("----------"+f.getID()+"----------\n");
      sb.append(f.toString());
    }
    return sb.toString();
  }
  
  /*
   * Method calculates and returns the monthly average of farms 
   * 
   * @param month - month to calculate the average of
   * @return - the monthly average in the for of a string
   */

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
  
  /*
   * Method returns the month with the smallest yield as a string
   * 
   * @param month - month to find the smallest yield of 
   * @return - the name of the month in the form of a String 
   */

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
  
  /*
   * Method returns the month with the largest yield as a string
   * 
   * @param month - month to find the largest yield of 
   * @return - the name of the month in the form of a String 
   */
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
  
  /*
   * method returns the monthly average for a farm
   * 
   * @param month, farm - the month as well as the farm ID to calculate the average of 
   * @return - the monthly average for the specified farm as a string 
   */
  public String getMonthlyAverageForFarm(String month, String farm) {
    return Double.toString(milkDataFromFarms.get(farm).getMonthAverage(Integer.parseInt(month)));
  }
  
  /*
   * method returns the monthly minimum for a farm
   * 
   * @param month, farm - the month as well as the farm ID to calculate the minimum of 
   * @return - the monthly average for the specified farm as a string 
   */
  public String getMonthlyMinForFarm(String month, String farm) {
    return Integer.toString(milkDataFromFarms.get(farm).getMin(Integer.parseInt(month)));
  }
  
  /*
   * method returns the monthly maximum for a farm
   * 
   * @param month, farm - the month as well as the farm ID to calculate the maximum of 
   * @return - the monthly average for the specified farm as a string 
   */
  public String getMonthlyMaxForFarm(String month, String farm) {
    return Integer.toString(milkDataFromFarms.get(farm).getMax(Integer.parseInt(month)));
  }
  
  
  /*
   * method returns the average weight yield in a specified average
   * 
   * @param start, end - starting and ending date to find average of
   * @return - average as a string
   */
  public String getAverageInDateRange(String start, String end) {
    Collection<Farm> colF = milkDataFromFarms.values();
    double total=0; int count=0;
    for(Farm f: colF) {
      total += f.getAverageInRange(start, end);
      count+=1;
    }
    return Double.toString(Math.round(total/count));
  }
  
  /*
   * method returns the minimum weight yield in a specified average
   * 
   * @param start, end - starting and ending date to find minimum of
   * @return - average as a string
   */
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
  
  /*
   * method returns the maximum weight yield in a specified average
   * 
   * @param start, end - starting and ending date to find maximum of
   * @return - average as a string
   */
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
