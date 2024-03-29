package cardiac_diseases;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class showInfo {
    private String captureOutput(Runnable action) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        action.run();
        System.setOut(System.out);
        return outputStream.toString().trim();
    }

    @org.junit.Test(expected = NullPointerException.class)
    public void showInfo1() {
        // CASE 1: no patients
        assertEquals("No patients", captureOutput(() -> Main.showPatientsInfo()));
    }

    @Test
    public void showInfo2() {
        // CASE 2: one single patient
        Patient patient = new Patient("A", "A", 30);
        LinkedList<Patient> patients = new LinkedList<>();
        patients.add(patient);
        Main.setHospital(new Hospital(patients, "hospital"));
        assertEquals(patient.toString(), captureOutput(() -> Main.showPatientsInfo()));
    }

    @Test
    public void showInfo3() {
        // CASE 3: more than one patient
        Patient patient1 = new Patient("A", "A", 30);
        Patient patient2 = new Patient("B", "B", 25);
        LinkedList<Patient> patients = new LinkedList<>();
        patients.add(patient1);
        patients.add(patient2);
        Main.setHospital(new Hospital(patients, "hospital"));
        String expectedOutput = patient1.toString() + "\r\n" + patient2.toString();
        assertEquals(expectedOutput, captureOutput(() -> Main.showPatientsInfo()));
    }

    @Test
    public void showInfo4() {
        // CASE 4: null hospital
        Main.setHospital(null);
        assertThrows(NullPointerException.class, () -> Main.showPatientsInfo());
    }

    @Test
    public void showInfo5() {
        // CASE 5: null patient list
        LinkedList<Patient> patients = new LinkedList<>();
        patients.add(null);
        Main.setHospital(new Hospital(patients, "hospital"));
        assertEquals("null", captureOutput(() -> Main.showPatientsInfo()));
    }
}
