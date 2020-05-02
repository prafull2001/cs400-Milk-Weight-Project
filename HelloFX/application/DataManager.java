package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * 
 * @author Dhruv Jain, Shrey Shah, Prafull Sharma, Arkin Katery
 * 
 * This class instantiates a DataManager class. Class generates reports that the user requests
 *
 */

public class DataManager {

  File inputFile;
  String outputFile;
  CheeseFactory cf = new CheeseFactory();


  /**
   * returns the number of farms in the cheesfactory
   * 
   * @return - size of the hashtable containing all farms in the cf
   */
  public int size() {
    return cf.getNumFarms();
  }

  /**
   * method reads the data of the inputFile and inputs them into cheese factory
   * @param filePath for csv with data to insert
   */
  public void readInsertData(String filePath) throws IOException {
	inputFile = new File(filePath);
    BufferedReader br = new BufferedReader(new FileReader(inputFile));
    String str = br.readLine();
    str = br.readLine();
    while(str!=null) {
      if(checkValidInput(str)) {
        cf.insertSingleData(str);
      }
      str = br.readLine();
    }
    try {
      br.close();
    } 
    catch (IOException e) {}
  }
  
  /**
   * get the average units a farm inputed in a given range
   * @param id Farm ID to get units for
   * @param start start date for date range
   * @param end end date of date range
   * @return average units of farm in range
   */
  public double getFarmAverageinRange(String id, String start, String end) {
	  return cf.get(sanitizeFarmInput(id)).getAverageInRange(sanitizeDateInput(start), sanitizeDateInput(end));
  }
  
  /**
   * check if csv input is valid
   * @param input one line of csv input
   * @return true if input is valid
   */
  private boolean checkValidInput(String input) {
    try {
      String[] datas = input.split(",");
      String date = datas[0];
      String farmID = datas[1];
      String weight = datas[2];
      String[] dates = date.split("-");
      Integer.parseInt(dates[0]);
      Integer.parseInt(dates[1]);
      Integer.parseInt(dates[2]);
      String[] farms = farmID.split(" ");
      if(!farms[0].equals("Farm")) {
        throw new Exception();
      }
      Integer.parseInt(farms[1]);
      Integer.parseInt(weight);
      return true;
    } catch(Exception e) {
      return false;
    }
  }
  
  /**
   * method reads the data of the inputFile and removes them from cheese factory
   * @param filepath of data to remove
   */
  public void readRemoveData(String filePath) throws IOException {
	inputFile = new File(filePath);
    BufferedReader br = new BufferedReader(new FileReader(inputFile));
    String str = br.readLine();
    str = br.readLine();
    while(str!=null) {
      if(checkValidInput(str)) {
        cf.removeSingleData(str);
      }
      str = br.readLine();
    }
    try {
      br.close();
    } 
    catch (IOException e) {}
  }

  /**
   * method inserts data into the current file
   * 
   * @param - string array of data to insert into file
   */
  public void insertData(String[] data) {
    try {
      Integer.parseInt(data[2]);
    } catch (Exception e) {
      throw new IllegalArgumentException("invalid input");
    }
    cf.insertSingleData(
        sanitizeDateInput(data[0]) + "," + sanitizeFarmInput(data[1]) + "," + data[2]);
  }

  /**
   * method removes data from current file
   * 
   * @param - string array of data to remove from file
   */
  public void removeData(String[] data) {
    try {
      Integer.parseInt(data[2]);
    } catch (Exception e) {
      throw new IllegalArgumentException("invalid input");
    }
    cf.removeSingleData(
        sanitizeDateInput(data[0]) + "," + sanitizeFarmInput(data[1]) + "," + data[2]);
  }

  /**
   * Method returns the monthly average of a specified month
   * 
   * @param month - month to return the average of
   * 
   * @return - string of the monthly average
   */
  public String getMonthlyAverage(String month) {
    return cf.getMonthlyAverage(sanitizeMonthInput(month));
  }

  /**
   * Method returns the monthly minimum of a specified month
   * 
   * @param month - month to return the minimum of
   * 
   * @return - string of the monthly minimum of
   */
  public String getMonthlyMin(String month) {
    return cf.getMonthlyMin(sanitizeMonthInput(month));
  }

  /**
   * Method returns the monthly maximum of a specified month
   * 
   * @param month - month to return the maximum of
   * 
   * @return - string of the monthly maximum
   */
  public String getMonthlyMax(String month) {
    return cf.getMonthlyMax(sanitizeMonthInput(month));
  }

  /**
   * Method returns the monthly average of a specified farm
   * 
   * @param month, farm - month and farm to return the average of
   * 
   * @return - string of the monthly average for the specified farm
   */
  public String getMonthlyAverageForFarm(String month, String farm) {
    return cf.getMonthlyAverageForFarm(sanitizeMonthInput(month), sanitizeFarmInput(farm));
  }

  /**
   * Method returns the monthly minimum of a specified farm
   * 
   * @param month, farm - month and farm to return the minimum of
   * 
   * @return - string of the monthly minimum for the specified farm
   */
  public String getMonthlyMinForFarm(String month, String farm) {
    return cf.getMonthlyMinForFarm(sanitizeMonthInput(month), sanitizeFarmInput(farm));
  }

  /**
   * Method returns the monthly maximum of a specified farm
   * 
   * @param month, farm - month and farm to return the maximum of
   * 
   * @return - string of the monthly maximum for the specified farm
   */
  public String getMonthlyMaxForFarm(String month, String farm) {
    return cf.getMonthlyMaxForFarm(sanitizeMonthInput(month), sanitizeFarmInput(farm));
  }

  /**
   * Method returns the average of a specified date range
   * 
   * @param start, end - start and end date to return the average of
   * 
   * @return - string of the average for the specified date range
   */
  public String getAverageInDateRange(String start, String end) {
    return cf.getAverageInDateRange(sanitizeDateInput(start), sanitizeDateInput(end));
  }

  /**
   * Method returns the minimum of a specified date range
   * 
   * @param start, end - start and end date to return the minimum of
   * 
   * @return - string of the minimum for the specified date range
   */
  public String getMinInDateRange(String start, String end) {
    return cf.getMinInDateRange(sanitizeDateInput(start), sanitizeDateInput(end));
  }

  /**
   * Method returns the maximum of a specified date range
   * 
   * @param start, end - start and end date to return the maximum of
   * 
   * @return - string of the maximum for the specified date range
   */
  public String getMaxInDateRange(String start, String end) {
    return cf.getMaxInDateRange(sanitizeDateInput(start), sanitizeDateInput(end));
  }

  /**
   * helper method, improve formatting of input
   * 
   * @param farm - input (farm) to "sanitize"
   * 
   * @return - newly formatted input string
   */
  private String sanitizeFarmInput(String farm) {
    try {
      try {
        return ("Farm " + Integer.parseInt(farm)); // check if just number entered
      } catch (NumberFormatException e) {
        String[] farmCheck = farm.toLowerCase().split(" ");
        if (farmCheck.length != 2)
          throw new IllegalArgumentException("invalid input");
        try {
          int ID = Integer.parseInt(farmCheck[1]);
          if (!farmCheck[0].toLowerCase().equals("farm"))
            throw new IllegalArgumentException("invalid input");
          return ("Farm " + ID);
        } catch (NumberFormatException f) {
          if (farmCheck.length != 2)
            throw new IllegalArgumentException("invalid input");
        }
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("invalid input");
    }
    throw new IllegalArgumentException("invalid input");
  }

  /**
   * helper method, improve formatting of input
   * 
   * @param farm - input (date) to "sanitize"
   * 
   * @return - newly formatted input string
   */
  private String sanitizeDateInput(String date) {
    try {
      String[] dateCheck = date.toLowerCase().split("-");
      if (dateCheck.length != 3)
        throw new IllegalArgumentException("invalid input");
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

  /**
   * helper method, improve formatting of input
   *  d
   * @param farm - input (month) to "sanitize"
   * 
   * @return - newly formatted input string
   */
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
