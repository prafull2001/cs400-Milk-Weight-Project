package application;

import java.util.ArrayList;

public class Farm {
  private String farmID;
  private String owner;
  private ArrayList<ArrayList<String>> milkWeights;
  
  public Farm(String data) {
    this.farmID = data.split(",")[1].split(" ")[1];
    milkWeights = new ArrayList<ArrayList<String>>(12);
  }
  
  public boolean insertMilkForDate(String data) {
    try {
      int month = Integer.parseInt(data.split(" ")[0].split("-")[1]);
      insertHelper(month, data);
      return true;
    } catch(Exception e) {
      e.printStackTrace();
    }
    return false;
  }
  
  public boolean editMilkForDate(String oldData, String newData) {
    try {
      int month = Integer.parseInt(oldData.split(" ")[0].split("-")[1]);
      return editHelper(month, oldData, newData);
    } catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  
  public String removeMilkForDate(String data) {
    try {
      int month = Integer.parseInt(data.split(" ")[0].split("-")[1]);
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
    int i;
    for(i=0; i<arr.size(); i++) {
      if(arr.get(i).contentEquals(data)) {
        return false;
      }
      if(Integer.parseInt(data.split(" ")[1])>Integer.parseInt(arr.get(i).split(" ")[1])) {
        break;
      }
    }
    arr.add(i-1, data);
    return true;
  }
  
  private boolean editHelper(int index, String curData, String newData) {
    ArrayList<String> arr = milkWeights.get(index);
    for(String s: arr) {
      if(s.equals(curData)) {
        s = newData;
        return true;
      }
    }
    return false;
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
}
