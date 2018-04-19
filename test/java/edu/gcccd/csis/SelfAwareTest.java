package edu.gcccd.csis;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * You must first clean the appendages from running the main method in
 * SelfAware.java before you can run this test
 */
public class SelfAwareTest {

    @Before
    public void setupFile() {
        String sourceFile = System.getProperty("user.dir") + File.separator +
                "src" + File.separator + "main" + File.separator + "java" + File.separator +
                SelfAware.class.getName().replace(".", File.separator) + ".java";

        System.out.println("Your file to be scanned is " + sourceFile);

    }

    /**
     * @throws Exception something went wrong oops!
     */
    @Test
    public void testOccurrenceCounter() throws Exception {

        String sourceFile = System.getProperty("user.dir") + File.separator +
                "src" + File.separator + "main" + File.separator + "java" + File.separator +
                SelfAware.class.getName().replace(".", File.separator) + ".java";

        SelfAware sa = new SelfAware();

        assertEquals(sa.occurrences(sourceFile), 57); //Manually counted amount

        int hold = sa.occurrences(sourceFile);
        sa.append(sourceFile, "\n//Such a fun int to count when thinking only of interface");

        assertEquals(sa.occurrences(sourceFile), hold + 2); //Checks that we have added only two words though 3 exist inside the added string
    }

    /**
     * deletes the appended string element from method testOccurrencesCounter()
     * so the test can be run again without manually deleting the appendage
     */
    @After
    public void deleteAppendedLines() {

        String sourceFile = System.getProperty("user.dir") + File.separator +
                "src" + File.separator + "main" + File.separator + "java" + File.separator +
                SelfAware.class.getName().replace(".", File.separator) + ".java";

        System.out.println("The file to have its appendage deleted from is " + sourceFile);
        String[] s = new String[400];
        try (Scanner inputStream = new Scanner(new File(sourceFile))) {
            int i = 0;
            while (inputStream.hasNextLine()) {
                s[i] = inputStream.nextLine();
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file in the deleteAppendedLines method!");
        }

        try (PrintWriter outputStream = new PrintWriter(new FileOutputStream(sourceFile), true)) {
            for (int j = 0; j < s.length - 1; j++) {
                if(s[j] == null || s[j].equals("//Such a fun int to count when thinking only of interface")){
                    continue;
                }
                outputStream.println(s[j]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Something went wrong in your deleteAppendedLines method with creating the output stream");
        }

    }
}