package application;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class DataManager {
  
  File inputFile;
  String outputFile;
  CheeseFactory cf;
  
  public DataManager(String filePath) {
    inputFile = new File(filePath);
  }
  
  /*
  TO DO : getDataSortedByField()
  */
  
  private void readData() {
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(inputFile));
      String[] fileLines = (String[]) br.lines().toArray();
      String[] fileData = Arrays.copyOfRange(fileLines, 1, fileLines.length);
      cf = new CheeseFactory();
      cf.insertData(fileData);
      
    } 
    catch (IOException e) {
      e.printStackTrace();
    } 
    catch (NumberFormatException e2) {
      e2.printStackTrace();
    } 
    catch (IllegalArgumentException e3) {
      System.out.println(e3);
    } 
    finally {
      try {
        br.close();
      } catch (Exception e4) {
        e4.printStackTrace();
      }
    }
  }
  
  public void insertData(String[] data) {
    try {
      Integer.parseInt(data[3]);
    } catch (Exception e) {
      throw new IllegalArgumentException("invalid input");
    }
    cf.insertSingleData(sanitizeFarmInput(data[0]) + "," + sanitizeDateInput(data[1]) + "," + data[2]);
  }
  
  public void removeData(String[] data) {
    try {
      Integer.parseInt(data[3]);
    } 
    catch (Exception e) {
      throw new IllegalArgumentException("invalid input");
    }
    cf.removeSingleData(sanitizeFarmInput(data[0]) + "," + sanitizeDateInput(data[1]) + "," + data[2]);
  }
  
  public String getMonthlyAverage(String month) {
    return cf.getMonthlyAverage(sanitizeMonthInput(month));
  }
  
  public String getMonthlyMin(String month) {
    return cf.getMonthlyMin(sanitizeMonthInput(month));
  }

  public String getMonthlyMax(String month) {
    return cf.getMonthlyMax(sanitizeMonthInput(month));
  }
  
  public String getMonthlyAverageForFarm(String month, String farm) {
    return cf.getMonthlyAverageForFarm(sanitizeMonthInput(month), sanitizeFarmInput(farm));
  }
  
  public String getMonthlyMinForFarm(String month, String farm) {
    return cf.getMonthlyMinForFarm(sanitizeMonthInput(month), sanitizeFarmInput(farm));
  }
  
  public String getMonthlyMaxForFarm(String month, String farm) {
    return cf.getMonthlyMaxForFarm(sanitizeMonthInput(month), sanitizeFarmInput(farm));
  }
  
  public String getDataSortedByField() {
    return cf.getDataSortedByField();
  }
  
  public String getAverageInDateRange(String start, String end) {
    return cf.getAverageInDateRange(sanitizeDateInput(start), sanitizeDateInput(end));
  }
  
  public String getMinInDateRange(String start, String end) {
    return cf.getMinInDateRange(sanitizeDateInput(start), sanitizeDateInput(end));
  }
  
  public String getMaxInDateRange(String start, String end) {
    return cf.getMaxInDateRange(sanitizeDateInput(start), sanitizeDateInput(end));
  }
  
  private String sanitizeFarmInput(String farm) {
    try {
      try {
        return ("Farm " + Integer.parseInt(farm)); //check if just number entered
      } 
      catch (NumberFormatException e) {
        String[] farmCheck = farm.toLowerCase().split(" ");
        if (farmCheck.length != 2) throw new IllegalArgumentException("invalid input");
        try {
          int ID = Integer.parseInt(farmCheck[1]);
          if (!farmCheck[0].toLowerCase().equals("farm")) throw new IllegalArgumentException("invalid input");
          return ("Farm " + ID);
        } 
        catch (NumberFormatException f) {
          if (farmCheck.length != 2) throw new IllegalArgumentException("invalid input");
        }
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("invalid input");
    }
    throw new IllegalArgumentException("invalid input");
  }
  
  private String sanitizeDateInput(String date) {
    try {
      String[] dateCheck = date.toLowerCase().split("-");
      if (dateCheck.length != 3) throw new IllegalArgumentException("invalid input");
      if (Integer.parseInt(dateCheck[0]) < 0) 
        throw new IllegalArgumentException("only positive years");
      if (Integer.parseInt(dateCheck[1]) > 12 || Integer.parseInt(dateCheck[1]) < 1)
        throw new IllegalArgumentException("invalid input");
      if (Integer.parseInt(dateCheck[2]) > 31 || Integer.parseInt(dateCheck[2]) < 1)
        throw new IllegalArgumentException("invalid input");
      return (dateCheck[0] + "-" + dateCheck[1] + "-" + dateCheck[2]);
    } catch (Exception e) {
      throw new IllegalArgumentException("invalid input");
    }
  }
  
  private String sanitizeMonthInput(String date) {
    try {
      if (Integer.parseInt(date) > 12 || Integer.parseInt(date) < 1)
        throw new IllegalArgumentException("invalid input");
      return date;
    } catch (Exception e) {
      throw new IllegalArgumentException("invalid input");
    }
  }
}
