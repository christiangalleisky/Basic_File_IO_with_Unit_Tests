package edu.gcccd.csis;

import java.io.*;
import java.util.Scanner;

public class SelfAware implements Language {

    public SelfAware (){
        Language.sort();
    }

    /**
     * Counts the total number of occurrences of all Java keyword in the
     * file.
     *
     * @param sourceFile {@link String} path to a java source file
     * @return {@link int} number of times java keyword occur in the
     * source file.
     * @throws Exception not a java file or no file maybe ...
     */
    public int occurrences(String sourceFile) {

        Scanner inputStream = null;
        try {
            inputStream = new Scanner(new File(sourceFile));
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file in the occurrences method!");
        }

        String[] s = new String[100];
        int i = 0;
        while (inputStream.hasNextLine()) {
            s[i] = inputStream.nextLine();
            i++;
        }

        String[][] h = new String[100][25];
        for (int u = 0; u < i; u++) {
            h[u] = s[u].split("\\W");
        }

        int instances = 0;
        for (String[] x : h) {
            for (String y : x) {
                if (y == null) {
                    continue;
                }
                for (String z : ReservedWords) {
                    if (y.equals(z)) {
                        instances++;
                    }
                }
            }
        }
        inputStream.close();

        return instances;
    }

    /**
     * Appends the provided file with the provided message
     *
     * @param sourceFile {@link String} path to a java source file
     * @param message    {@link String} message to be appended
     * @throws IOException things didn't go too well ...
     */
    public void append(String sourceFile, String message) throws IOException {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream(sourceFile, true));

        } catch (FileNotFoundException e) {
            System.out.println("Something went wrong in your append method with creating the output stream");
        }
        outputStream.print(message);
        outputStream.close();
    }

    public static void main(String[] args) {
        try {
            final String code = System.getProperty("user.dir") + File.separator +
                    "src" + File.separator + "main" + File.separator + "java" + File.separator +
                    SelfAware.class.getName().replace(".", File.separator) + ".java";
            SelfAware sa = new SelfAware();
            sa.append(code, "\n//Keyword occurrences: " + sa.occurrences(code));
        } catch (IOException e) {
            System.out.println("Something went wrong in your append method");
            System.out.println(e.getStackTrace());
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("Something went wrong in your occurrences method");
            System.out.println(e.getStackTrace());
            System.exit(-1);
        }
    }
}
