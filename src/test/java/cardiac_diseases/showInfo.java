package cardiac_diseases;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
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

    @Test
    public void showInfo1() {
        //CASE 1: no patients
        Hospital hosp = new Hospital("hospital");
        assertEquals("No patients", captureOutput(() -> Main.showPatientsInfo(hosp)));
    }
    @Test
    public void showInfo2() {
        //CASE 2: one single patient
        Hospital hosp = new Hospital("hospital");
        Patient patient = new Patient("A","A", 30);
        LinkedList<Patient> patients = hosp.getListOfPatients();
        patients.add(patient);
        assertEquals(patient.toString(), captureOutput(() -> Main.showPatientsInfo(hosp)));
    }
    @Test
    public void showInfo3() {
        //CASE 3: more than one patient
        Hospital hosp = new Hospital("hospital");
        Patient patient1 = new Patient("A", "A", 30);
        Patient patient2 = new Patient("B", "B", 25);
        LinkedList<Patient> patients = hosp.getListOfPatients();
        patients.add(patient1); patients.add(patient2);
        String expectedOutput = patient1.toString() + "\r\n" + patient2.toString();
        assertEquals(expectedOutput, captureOutput(() -> Main.showPatientsInfo(hosp)));
    }
    @Test
    public void showInfo4() {
        //CASE 4: null hospital
        assertThrows(NullPointerException.class, () -> Main.showPatientsInfo(null));
    }

    @Test
    public void showInfo5() {
        //CASE 5: null patient list
        Hospital hosp = new Hospital("hospital");
        LinkedList<Patient> patients = hosp.getListOfPatients();
        patients.add(null);
        assertEquals("null", captureOutput(() -> Main.showPatientsInfo(hosp)));
    }

}
