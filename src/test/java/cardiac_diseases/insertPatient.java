package cardiac_diseases;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class insertPatient {

    @Test
    public void insertPatient1() throws Exception {
        //CASE 1: patient with name, surname and age
        Patient p1 = new Patient("A", "A", 90);
        assertEquals(p1.getName(), "A");
        assertEquals(p1.getSurname(), "A");
        assertEquals(p1.getAge(), 90);
        assertEquals(p1.getDisease(), null);
        assertEquals(p1.getSymptoms(), new LinkedList<>());
    }
    @Test
    public void insertPatient2() throws Exception {
        //CASE 2: patient with name, surname, age and symptoms
        LinkedList<Symptom> symptoms = new LinkedList<>();
        symptoms.add(Symptom.WEAKNESS);
        symptoms.add(Symptom.EDEMA_CALVES_ANKLES);
        Patient p2 = new Patient("B", "B", 27, symptoms);
        assertEquals(p2.getName(), "B");
        assertEquals(p2.getSurname(), "B");
        assertEquals(p2.getAge(), 27);
        assertEquals(p2.getDisease(), null);
        assertEquals(p2.getSymptoms(), symptoms);
    }
    @Test
    public void insertPatient3() throws Exception {
        //CASE 3: patient with name, surname, age, symptoms and disease
        LinkedList<Symptom> symptoms2 = new LinkedList<>();
        symptoms2.add(Symptom.CHEYNE_STOKES_BREATHING);
        symptoms2.add(Symptom.WEAK_PULSE);
        symptoms2.add(Symptom.ALTERED_CONSCIOUSNESS);
        Patient p3 = new Patient("B", "B", 35, symptoms2, Disease.IRREVERSIBLE_CRASH);
        assertEquals(p3.getName(), "B");
        assertEquals(p3.getSurname(), "B");
        assertEquals(p3.getAge(), 35);
        assertEquals(p3.getDisease(), Disease.IRREVERSIBLE_CRASH);
        assertEquals(p3.getSymptoms(), symptoms2);
    }
    @Test
    public void insertPatient4() throws Exception {
        //CASE 4: modify disease
        Patient p4 = new Patient();
        assertEquals(p4.getName(), null);
        assertEquals(p4.getSurname(), null);
        assertEquals(p4.getAge(), 0);
        assertEquals(p4.getDisease(), null);
        assertEquals(p4.getSymptoms(), new LinkedList<>());
    }
}
