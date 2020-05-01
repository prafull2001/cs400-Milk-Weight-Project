package application;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class DataManager {
  
  String inputFile;
  String outputFile;
  CheeseFactory cf;
  
  public DataManager(String filePath) {
    inputFile = file.getPath();
    
  }
  
  private void readData() {
    try {
      BufferedReader br = new BufferedReader(new FileReader(inputFile));
      String[] fileLines = br.lines().toArray();
      String[] fileData = Arrays.copyOfRange(fileLines, 1, fileLines.length);
      cf = new CheeseFactory();
      cf.insertData(fileData);
      
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NumberFormatException e2) {
      e2.printStackTrace();
    } catch (IllegalArgumentException e3) {
      System.out.println(e3);
    } finally {
      try {
        br.close();
      } catch (Exception e4) {
        e4.printStackTrace();
      }
    }
  }
  
  public String getMonthlyAverage() {
    return cf.getMonthlyAverage();
  }
  
  public String getMonthlyMin() {
    return cf.getMonthlyAverage();
  }

  public String getMonthlyMax() {
    return cf.getMonthlyAverage();
  }
  
  public String getMonthlyAverageForFarm() {
    return cf.getMonthlyAverage();
  }
  
  public String getMonthlyMinForFarm() {
    return cf.getMonthlyAverage();
  }
  
  public String getMonthlyMaxForFarm() {
    return cf.getMonthlyAverage();
  }
  
  public String getDataSortedByField() {
    return cf.getMonthlyAverage();
  }
  
  public String getAverageInDateRange() {
    return cf.getMonthlyAverage();
  }
  
  public String getMinInDateRange() {
    return cf.getMonthlyAverage();
  }
  
  public String getMaxInDateRange() {
    return cf.getMonthlyAverage();
  }
}