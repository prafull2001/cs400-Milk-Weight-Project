package application;

import java.util.ArrayList;
import java.util.Date;
/**
 * 
 * @author Dhruv Jain, Shrey Shah, Prafull Sharma, Arkin Katery
 * 
 * This class instantiates a Farm class. used by Cheesefactory class
 *
 */
public class Farm {
  private String farmID;
  private ArrayList<ArrayList<String>> milkWeights;
  
  /**
   * constructor for the Farm class
   * 
   * @param - id for new farm
   */
  public Farm(String id) {
    this.farmID = id;
    milkWeights = new ArrayList<ArrayList<String>>(12);
    for(int i=0; i<12; i++) {
      milkWeights.add(new ArrayList<String>());
    }
  }
  
  /**
   * method inserts milk data for a specified date
   * 
   * @param data - string input to be parsed then used to input data
   * @return - true if operation successful, false if not
   */
  public boolean insertMilkForDate(String data) {
    try {
      int month = Integer.parseInt(data.split(" ")[0].split("-")[1]);
      return insertHelper(month-1, data);
    } catch(Exception e) {
    }
    return false;
  }
  
  /**
   * method removes milk data for a specified date
   * 
   * @param data - string input to be parsed then used to remove data
   * @return - true if operation successful, false if not
   */
  public String removeMilkForDate(String data) {
    try {
      int month = Integer.parseInt(data.split(" ")[0].split("-")[1])-1;
      milkWeights.get(month).remove(data);
      return data;
    } catch(Exception e) {
      return null;
    }
  }
  
  /**
   * method clears data for the current farm
   * 
   * @return - empty arraylist
   */
  public String clearData() {
    String str = milkWeights.toString();
    milkWeights = new ArrayList<ArrayList<String>>(12);
    return str;
  }
   
  /**
   * helper method for inserting data
   * 
   * @param index, data - index to enter data as well as data to insert
   * @return true if operation is successful, false if not
   */
  private boolean insertHelper(int index, String data) {
    ArrayList<String> arr = milkWeights.get(index);
    String date = data.split(" ")[0];
    int i;
    for(i=0; i<arr.size(); i++) {
      if(arr.get(i).split(" ")[0].equals(date)) {
        arr.set(i, data);
        return true;
      }
    }
    arr.add(data);
    return true;
  }
  
  @Override
  /**
   * method overrides the toString method for custom output
   * 
   * @return - newly formatted output
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(int i=0; i<milkWeights.size(); i++) {
      sb.append(i+1+": ");
      for(int j=0; j<milkWeights.get(i).size(); j++) {
        sb.append(milkWeights.get(i).get(j)+", ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
  
  /**
   * method returns the farm ID
   * 
   * @return - ID of the farm
   */
  public String getID() {
    return farmID;
  }
  
  /**
   * method returns the monthly average of the current farm
   * 
   * @param month - month to find average of 
   * @return - calculated average 
   */
  public double getMonthAverage(int month) {
    ArrayList<String> arr = milkWeights.get(month-1);
    if(arr==null) {
      return 0;
    }
    int total=0, count=0;
    for(String s: arr) {
      total+=Integer.parseInt(s.split(" ")[1]);
      count+=1;
    }
    return Math.round((double)total/count);
  }
  
  /**
   * method returns the monthly min of the current farm
   * 
   * @param month - month to find min of 
   * @return - calculated min 
   */
  public int getMin(int month) {
    ArrayList<String> arr = milkWeights.get(month-1);
    if(arr==null) {
      return Integer.MAX_VALUE;
    }
    int min = Integer.MAX_VALUE;
    for(String s: arr) {
      int cur = Integer.parseInt(s.split(" ")[1]);
      if(cur<min) {
        min = cur;
      }
    }
    return min;
  }
  
  /**
   * method returns the monthly max of the current farm
   * 
   * @param month - month to find max of 
   * @return - calculated max 
   */
  public int getMax(int month) {
    ArrayList<String> arr = milkWeights.get(month-1);
    if(arr==null) {
      return 0;
    }
    int max = 0;
    for(String s: arr) {
      int cur = Integer.parseInt(s.split(" ")[1]);
      if(cur>max) {
        max = cur;
      }
    }
    return max;
  }
  
  /**
   * method returns the date range average of the current farm
   * 
   * @param start, end - start and end date to find average of 
   * @return - calculated average 
   */
  public double getAverageInRange(String start, String end) {
    int startMonth = Integer.parseInt(start.split("-")[1])-1;
    int endMonth = Integer.parseInt(end.split("-")[1])-1;
    int total=0, count=0;
    for(int i=startMonth; i<=endMonth; i++) {
      for(String s: milkWeights.get(i)) {
        String date = s.split(" ")[0];
        int cur = Integer.parseInt(s.split(" ")[1]);
        if(dateCompare(date, start, end)) {
          total += cur;
          count += 1;
        }
      }
    }
    return Math.round((double)total/count);
  }
  
  /**
   * method returns the date range min of the current farm
   * 
   * @param start, end - start and end date to find min  of 
   * @return - calculated min 
   */
  public int getMinInRange(String start, String end) {
    int startMonth = Integer.parseInt(start.split("-")[1])-1;
    int endMonth = Integer.parseInt(end.split("-")[1])-1;
    int min = Integer.MAX_VALUE;
    for(int i=startMonth; i<=endMonth; i++) {
      for(String s: milkWeights.get(i)) {
        String date = s.split(" ")[0];
        int cur = Integer.parseInt(s.split(" ")[1]);
        if(dateCompare(date, start, end)&&cur<min) {
          min=cur;
        }
      }
    }
    return min;
  }
  
  /**
   * method returns the date range max of the current farm
   * 
   * @param start, end - start and end date to find  max of 
   * @return - calculated average 
   */
  public int getMaxInRange(String start, String end) {
    int startMonth = Integer.parseInt(start.split("-")[1])-1;
    int endMonth = Integer.parseInt(end.split("-")[1])-1;
    int max = 0;
    for(int i=startMonth; i<=endMonth; i++) {
      for(String s: milkWeights.get(i)) {
        String date = s.split(" ")[0];
        int cur = Integer.parseInt(s.split(" ")[1]);
        if(dateCompare(date, start, end)&&cur>max) {
          max=cur;
        }
      }
    }
    return max;
  }
  
  /**
   * helper method to compare dates
   * 
   * @param date1, date2, date3 - dates to compare
   * 
   * @return true if date 1 is between both values
   */
  private boolean dateCompare(String date1, String date2, String date3) {
    int month1 = Integer.parseInt(date1.split("-")[1]);
    int day1 = Integer.parseInt(date1.split("-")[2]);
    int month2 = Integer.parseInt(date1.split("-")[1]);
    int day2 = Integer.parseInt(date1.split("-")[2]);
    int month3 = Integer.parseInt(date1.split("-")[1]);
    int day3 = Integer.parseInt(date1.split("-")[2]);
    boolean lower=false, higher=false;
    if(month1>=month2) {
      lower = true;
    }
    else {
      if(day1>=day2) {
        lower = true;
      }
      else {
        lower = false;
      }
    }
    if(month1<=month3) {
      higher = true;
    }
    else {
      if(day1<=day3) {
        higher = true;
      }
      else {
        higher = false;
      }
    }
    return lower&&higher;
  }
}
