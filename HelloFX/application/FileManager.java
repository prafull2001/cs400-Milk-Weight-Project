package application;
  import java.io.File;
  import java.io.FileReader;
  import java.io.FileWriter;
  import java.io.IOException;
  import java.nio.file.Files;
  import java.nio.file.Paths;
  import java.io.BufferedReader;

  public class FileManager {
    String inputFile;
    String outputFile;

    public FileManager(File file) {
      inputFile = file.getPath();

    }

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
          br.close();
        } catch (Exception e4) {
          e4.printStackTrace();
        }
      }
      return false;

    }

    public boolean writeFile() {
      try {
        FileWriter writer = new FileWriter("newReport.csv");
        writer.append("date");
        writer.append(",");
        writer.append("farm_id");
        writer.append(",");
        writer.append("weight");
        writer.append("\n");

      } catch (IOException e) {
        e.printStackTrace();
      }
      return false;
    }

    public String getFileContents() {
      String text = "";
      try {
        text = new String(Files.readAllBytes(Paths.get(inputFile)));
      } catch (IOException e) {
        e.printStackTrace();
      }
      return text;
    }
    
    public static void main(String[] args) {
      File f = new File("/Users/dhruvjain/Downloads/csv/large/2019-8.csv");
      FileManager fm = new FileManager(f);
      String s = fm.getFileContents();
      System.out.println(s);
    }

  }

