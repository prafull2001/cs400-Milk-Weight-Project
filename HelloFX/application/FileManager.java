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
/**
 * Manages file reading and writing data
 * 
 * 
 *
 */
public class FileManager {
  String inputFile;
  String outputFile;
  /**
   * Constructor that takes a file as input and gets the path
   * 
   * @param file
   */
  public FileManager(File file) {
    inputFile = file.getPath();

  }
  /**
   * This method reads in a csv file and prints it, throwing errors if the file contents do not
   * correspond to the correct format
   * 
   * @return true when the method runs as intended, false otherwise
   */
  public boolean readFile() {
    BufferedReader br = null;
    try {
      String testLine;
      br = new BufferedReader(new FileReader(inputFile));
      String header = br.readLine();
      System.out.println(header);
      while ((testLine = br.readLine()) != null) {
        String[] parts = testLine.split(",");
        for (int i = 0; i < parts.length; i++) { // Checks if entry is missing
          if (parts[i].equals("-")) {
            throw new IllegalArgumentException("One of the entries is empty!");
          }
          if (i == 0) { // Checks the date for any issues
            String[] dates = parts[i].split("-");
            for (int j = 0; j < dates.length; j++) {
              if (j == 0 || j == 2) {
                int k = Integer.parseInt(dates[j]);
              } else if (j == 1) {
                int k = Integer.parseInt(dates[j]);
                if (k < 0 || k > 12) {
                  throw new IllegalArgumentException("The month in one of the entries is false!");
                }
              }
            }
          } else if (i == 2) {
            int j = Integer.parseInt(parts[i]);
          }
        }
        System.out.println(testLine);
      }
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NumberFormatException e2) {
      e2.printStackTrace();
    } catch (IllegalArgumentException e3) {
      System.out.println(e3);
    } finally {
      try {
        br.close(); // Closes the BufferedReader object
      } catch (Exception e4) {
        e4.printStackTrace();
      }
    }
    return false;

  }
  /**
   * Writes a new line in a file
   * 
   * @param f       file to be written over
   * @param newLine the new line to be written into the file
   * @return true if method runs as intended, throws an error and returns false otherwise
   */
  public boolean writeFile(File f, String newLine) {
    if (checkExisting(f, newLine)) {
      return false;
    } else {
      try {
        String filePath = f.getPath();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        writer.write(newLine);
        writer.close();
        return true;
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
    return false;

  }
  /**
   * This method checks whether a String of data exists
   * 
   * @param f       file to be checked
   * @param newLine String to be checked against
   * @return true whether string already exists in file, false otherwise
   */
  public boolean checkExisting(File f, String newLine) {
    String filePath = f.getPath();
    Scanner scanner = new Scanner(filePath);

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.equals(newLine)) {
        return true;
      }
    }
    return false;

  }

  public String getFileContents() {
    String text = "";
    try {
      text = new String(Files.readAllBytes(Paths.get(inputFile)));// This line puts all the content
                                                                  // of the file in a String
    } catch (IOException e) {
      e.printStackTrace();
    }
    return text;
  }

  /**
   * Main method for testing THIS MAY NEED TO BE DELETED
   * 
   * @param args
   */
  public static void main(String[] args) {
    File f = new File("/Users/dhruvjain/Downloads/csv/small/2019-8.csv");
    FileManager fm = new FileManager(f);
    fm.writeFile(f, "testing");
    String s = fm.getFileContents();
    System.out.println(s);

  }

}


