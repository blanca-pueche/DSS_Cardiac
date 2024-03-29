package cardiac_diseases;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class modifyPatient {
    @Test
    public void modifyPatient1() throws Exception {
        //These options can be seen in the modifyPatient function in the Main. However, as they require user input,
        //I'm essentially doing what the function does but already providing the new parameters

        //CASE 1: modify name
        Patient p1 = new Patient("A", "A", 90);
        String newName = "Pepe";
        p1.setName(newName);
        assertEquals(p1.getName(), newName);
    }
    @Test
    public void modifyPatient2() throws Exception {
        //CASE 2: modify surname
        Patient p2 = new Patient("B", "B", 27);
        String newSurname = "García";
        p2.setSurname(newSurname);
        assertEquals(p2.getSurname(), newSurname);
    }
    @Test
    public void modifyPatient3() throws Exception {
        //CASE 3: modify symptoms
        LinkedList<Symptom> symptoms = new LinkedList<>();
        symptoms.add(Symptom.CHEYNE_STOKES_BREATHING);
        symptoms.add(Symptom.WEAK_PULSE);
        symptoms.add(Symptom.ALTERED_CONSCIOUSNESS);
        Patient p3 = new Patient("B", "B", 35, symptoms);
        LinkedList<Symptom> newSymptoms = new LinkedList<>();
        newSymptoms.add(Symptom.EDEMA_CALVES_ANKLES);
        newSymptoms.add(Symptom.SYNCOPE);
        p3.setSymptoms(newSymptoms);
        assertEquals(p3.getSymptoms(), newSymptoms);
    }
    @Test
    public void modifyPatient4() throws Exception {
        //CASE 4: modify disease
        LinkedList<Symptom> symptoms = new LinkedList<>();
        symptoms.add(Symptom.CHEYNE_STOKES_BREATHING);
        symptoms.add(Symptom.WEAK_PULSE);
        symptoms.add(Symptom.ALTERED_CONSCIOUSNESS);
        Patient p4 = new Patient("C", "C", 79, symptoms, Disease.AORTIC_STENOSIS);
        Disease newDisease = Disease.PERICARDITIS;
        p4.setDisease(newDisease);
        assertEquals(p4.getDisease(), newDisease);
    }

    private String captureOutput(Runnable action) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        action.run();
        System.setOut(System.out);
        return outputStream.toString().trim();
    }
    @Test
    public void modifyPatient5() throws Exception {
        // CASE 5: the hospitals' patient list is empty. This one we can use the function that's in the Main
        Hospital hospital = new Hospital("h");
        LinkedList<Patient> list = new LinkedList<>();
        hospital.setListOfPatients(list);

        // Redirect output stream to capture console output
        String output = captureOutput(() -> {
            try {
                Main.setHospital(hospital); // Set the hospital for the Main class
                Main.modifyPatient();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        assertEquals("No patients", output);
    }
}
