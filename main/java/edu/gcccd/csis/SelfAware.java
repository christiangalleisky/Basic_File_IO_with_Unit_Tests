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
    public int occurrences(String sourceFile) throws Exception {

        int instances = 0;
        try(Scanner inputStream = new Scanner(new File(sourceFile))){
            inputStream.useDelimiter("\\W");
            while (inputStream.hasNext()) {
                final String u = inputStream.next();
                for (String z : ReservedWords){
                    if(u.equals(z)){
                        instances++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file in the occurrences method!");
        }
        return instances;
    }

    public void occurrencesFrequency(String sourceFile) throws Exception {
        for (String z : ReservedWords){
            int i = 0;
        try (Scanner inputStream = new Scanner(new File(sourceFile))) {
            inputStream.useDelimiter("\\W");
                while (inputStream.hasNext()){
                    final String y = inputStream.next();
                    if (y == null || y.equals("")) {
                        continue;
                    } else if (z.equals(y) || z.equals("nClass")) {
                        i++;
                    }
                }
                if (i != 0) {
                    append(sourceFile, "\n//" + z + " occurred " + i + " time(s)");
                }
            } catch (FileNotFoundException e) {
                System.out.println("Error opening the file in the occurrencesFrequency method!");
            }
        }
    }

    /**
     * Appends the provided file with the provided message
     *
     * @param sourceFile {@link String} path to a java source file
     * @param message    {@link String} message to be appended
     */
    public void append(String sourceFile, String message) {

        try(PrintWriter outputStream = new PrintWriter(new FileOutputStream(sourceFile, true))) {
            outputStream.print(message);
        }catch (FileNotFoundException e) {
            System.out.println("Something went wrong in your append method with creating the output stream");
        }
    }

    public static void main(String[] args) {
        try {
            final String code = System.getProperty("user.dir") + File.separator +
                    "src" + File.separator + "main" + File.separator + "java" + File.separator +
                    SelfAware.class.getName().replace(".", File.separator) + ".java";
            SelfAware sa = new SelfAware();
            sa.append(code, "\n//Keyword occurrences: " + sa.occurrences(code));
            sa.occurrencesFrequency(code);//must call method occurrences first before using this method
        } catch (Exception e) {
            System.out.println("Something went wrong in your occurrences method");
            StackTraceElement[] elements = e.getStackTrace();
            for(StackTraceElement x : elements){
                System.out.println("\nClass: " + x.getClassName() + "\nLine Number: " + x.getLineNumber() + " " +
                                   "\nMethod: " + x.getMethodName() + "\nFile: " + x.getFileName());
            }
            System.exit(-1);
        }
    }
}

