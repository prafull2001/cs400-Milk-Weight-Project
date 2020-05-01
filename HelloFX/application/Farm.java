package application;

import java.util.ArrayList;
/**
 * 
 * @author dhruvjain
 *
 */
public class Farm {
  private String farmID;
  private ArrayList<ArrayList<String>> milkWeights;
  
  public Farm(String id) {
    this.farmID = id;
    milkWeights = new ArrayList<ArrayList<String>>(12);
    for(int i=0; i<12; i++) {
      milkWeights.add(new ArrayList<String>());
    }
  }
  
  public boolean insertMilkForDate(String data) {
    try {
      int month = Integer.parseInt(data.split(" ")[0].split("-")[1]);
      return insertHelper(month-1, data);
    } catch(Exception e) {
      e.printStackTrace();
    }
    return false;
  }
  
  public String removeMilkForDate(String data) {
    try {
      int month = Integer.parseInt(data.split(" ")[0].split("-")[1])-1;
      milkWeights.get(month).remove(data);
      return data;
    } catch(Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  
  public String clearData() {
    String str = milkWeights.toString();
    milkWeights = new ArrayList<ArrayList<String>>(12);
    return str;
  }
     
  private boolean insertHelper(int index, String data) {
    ArrayList<String> arr = milkWeights.get(index);
    String date = data.split(" ")[0];
    int i;
    for(i=0; i<arr.size(); i++) {
      if(arr.get(i).split(" ")[0].equals(date)) {
        System.out.println("called");
        arr.set(i, data);
        return true;
      }
    }
    arr.add(data);
    return true;
  }
  
  @Override
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
  
  public String getID() {
    return farmID;
  }
  
  public double getMonthAverage(int month) {
    ArrayList<String> arr = milkWeights.get(month);
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
  
  public int getMin(int month) {
    ArrayList<String> arr = milkWeights.get(month);
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
  public int getMax(int month) {
    ArrayList<String> arr = milkWeights.get(month);
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
  
  private boolean dateCompare(String date1, String date2, String date3) {
    return date1.compareTo(date2)>=0&&date1.compareTo(date3)<=0;
  }
}
