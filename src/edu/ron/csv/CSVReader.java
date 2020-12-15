package edu.ron.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    /**
     * Read lines from readable file.
     * @param CSVPath Path to CSV file
     * @return String[] of file content
     */
    public String[] readCSV(String CSVPath) {

        try (BufferedReader br = new BufferedReader(new FileReader(CSVPath))) {

            List<String> lines = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
            br.close();
            return lines.toArray(String[]::new);

        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Convert string from rows of csv format into 2d array.
     * @param arr Array of csv format String
     * @return 2d Array of String
     */

    public String[][] csvToArray(String[] arr) {

        List<String[]> lines = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {

            List<String> line = new ArrayList<>();

            /*
             * Create a box between two double quote, everything
             * inside the box will be formatted as follows:
             * every comma(,) inside the box will not count as separator
             * every dual double quote("") will be converted to one double quote(")
             * every empty line feed inside the box will be converted to ( /n)
             */
            StringBuilder box = new StringBuilder();
            String str = arr[i];
            int counter = 0;
            int quoteCounter = 0;
            boolean toggle = false;

            while (counter < str.length()) {

                if ((counter == 0 || str.charAt(counter - 1) == ',') && str.charAt(counter) == '"')
                    toggle = true;

                if (toggle) {
                    box.append(str.charAt(counter));

                    if (str.charAt(counter) == '"')
                        quoteCounter++;

                    if (counter != str.length() - 1 && (str.charAt(counter + 1) != '"' && quoteCounter % 2 == 0))
                        toggle = false;

                    else if (counter == str.length() - 1 && quoteCounter % 2 == 0)
                        toggle = false;

                    else if (counter == str.length() - 1 && i + 1 < arr.length) {
                        i++;
                        str = arr[i];
                        str = str.equals("")?" /n":str;
                        counter = 0;
                        continue;
                    }
                }
                /*
                 * Once the box complete (or not even created), we are going to format the box.
                 * Then we will add the box and remaining string before comma into a single string.
                 * Then add the string into a list.
                 */
                if (!toggle) {

                    String formattedBox = box.toString();
                    if (box.length() <= 2) {
                        formattedBox = "";
                    } else {
                        formattedBox = formattedBox.substring(1, box.length() - 1);
                        formattedBox = formattedBox.replace("\"\"", "\"");
                    }

                    counter = box.length()!=0? counter+1:counter;
                    box = new StringBuilder();

                    StringBuilder wholeString = new StringBuilder();
                    wholeString.append(formattedBox);
                    while (counter != str.length() &&  str.charAt(counter) != ',') {
                        wholeString.append(str.charAt(counter));
                        counter++;
                    }
                    line.add(wholeString.toString());
                }
                counter++;
            }
            lines.add(line.toArray(String[]::new));
        }
        return lines.toArray(String[][]::new);
    }
}
