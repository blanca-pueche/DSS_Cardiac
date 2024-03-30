package cardiac_diseases;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
public class uploadCSV {

    @Test
    public void uploadCSV1() throws FileNotFoundException {
        //CASE 1: valid CSV file, reads it OK
        Hospital h = new Hospital("h");
        FileManager file = new FileManager("testCase1");
        assertNotNull(file.uploadCSV());
        h = file.uploadCSV();
        assertEquals(h.getListOfPatients().size(), 2);
    }

    @Test
    public void  uploadCSV2() throws FileNotFoundException {
        //CASE 2: empty file
        Hospital h = new Hospital("h");
        FileManager file = new FileManager("testCase2");
        assertNotNull(file.uploadCSV());
        assertEquals(h.getListOfPatients().size(), 0);
    }

    @Test
    public void uploadCSV3() throws FileNotFoundException {
        //CASE 3: empty hospital patient list
        Hospital h = new Hospital("h");
        FileManager file = new FileManager("testCase3");
        assertNotNull(file.uploadCSV());
        LinkedList<Patient> patients = h.getListOfPatients();
        assertTrue(patients.isEmpty());
    }

    @Test
    public void uploadCSV4() throws FileNotFoundException {
        //CASE 4: no disease
        Hospital h = new Hospital("h");
        FileManager file = new FileManager("testCase4");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // Upload the CSV file
        file.uploadCSV();
        // Check if error message contains indication of invalid disease
        assertTrue(outContent.toString().contains("Error: 'null' is not a valid disease for the enum."));
    }

    @Test(expected = FileNotFoundException.class)
    public void uploadCSV5() throws FileNotFoundException {
        //CASE 5: CSV File Not Found
        FileManager fileManager = new FileManager("testCase5");
        fileManager.uploadCSV();
    }
}
