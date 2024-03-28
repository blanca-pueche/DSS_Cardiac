package cardiac_diseases;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class downloadCSV {

    @Test
    public void downloadCSV1() throws IOException {
        //CASE 1: valid hospital
        Hospital h = new Hospital("h");
        LinkedList<Symptom> symptoms = new LinkedList<>();
        symptoms.add(Symptom.PALENESS);
        symptoms.add(Symptom.FATIGUE);
        Patient p1 = new Patient("A", "A", 30, symptoms, Disease.PERICARDITIS); // Create patients
        h.getListOfPatients().add(p1);
        FileManager fileManager = new FileManager("downloadCSV1");
        assertTrue(fileManager.downloadCSV(h));
    }

    @Test
    public void downloadCSV2() throws IOException {
        //CASE 2: empty hospital, no patients
        Hospital h = new Hospital("h");
        FileManager fileManager = new FileManager("downloadCSV2");
        assertTrue(fileManager.downloadCSV(h));
    }

    @Test(expected = NullPointerException.class)
    public void downloadCSV3() throws IOException {
        //CASE 3: null hospital
        Hospital h = null;
        FileManager fileManager = new FileManager("downloadCSV3");
        assertFalse(fileManager.downloadCSV(h));
    }

    @Test
    public void downloadCSV4() throws IOException {
        //CASE 4: no name file saves correctly
        Hospital h = new Hospital("h");
        FileManager fileManager = new FileManager(""); // Invalid file name
        assertTrue(fileManager.downloadCSV(h));
    }

    @Test
    public void downloadCSV5() throws IOException {
        //CASE 5: patient without disease
        Hospital h = new Hospital("h");
        LinkedList<Symptom> symptoms = new LinkedList<>();
        symptoms.add(Symptom.PALENESS);
        symptoms.add(Symptom.FATIGUE);
        Patient p1 = new Patient("A", "A", 30, symptoms); // Create patients
        h.getListOfPatients().add(p1);
        FileManager fileManager = new FileManager("downloadCSV5");
        assertTrue(fileManager.downloadCSV(h));
    }

}
