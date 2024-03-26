package cardiac_diseases;

import drools.config.DroolsBeanFactory;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import static org.junit.Assert.assertEquals;

public class PatientTest {


    private KieSession kSession;

    @Before
    public void setup() {
        Resource resource = ResourceFactory.newClassPathResource("cardiac_diseases/drools.drl.xlsx", getClass());
        kSession = new DroolsBeanFactory().getKieSession(resource);
        System.out.println(new DroolsBeanFactory().getDrlFromExcel("cardiac_diseases/drools.drl.xlsx"));
    }

    @Test
    public void patientTest1() throws Exception {
        //CASE 1: All the symptoms match with a disease, we have descending aortic aneurysm
        Patient p1 = new Patient("A", "A", 20);
        p1.getSymptoms().add(Symptom.CHEST_PAIN);
        p1.getSymptoms().add(Symptom.SCAPULA_PAIN);
        p1.getSymptoms().add(Symptom.DRY_COUGH);
        p1.getSymptoms().add(Symptom.HOARSENES);
        p1.getSymptoms().add(Symptom.DYSPHAGIA);
        p1.getSymptoms().add(Symptom.DYSPNEA);
        p1.getSymptoms().add(Symptom.STRIDOR);
        kSession.insert(p1);
        int rulesFired = kSession.fireAllRules();
        System.out.println(rulesFired);
        System.out.println(p1);
        assertEquals(Disease.DESCENDING_AORTIC_ANEURYSM, p1.getDisease());
    }
    @Test
    public void patientTest2() throws Exception {
            //CASE 2: None of the symptoms match a disease
            Patient p2 = new Patient("B", "B", 39);
            p2.getSymptoms().add(Symptom.PARADOXIC_PULSE);
            p2.getSymptoms().add(Symptom.DECREASED_FEMORAL_PULSE);
            p2.getSymptoms().add(Symptom.HYPOXEMIA);
            p2.getSymptoms().add(Symptom.RALES);
            kSession.insert(p2);
            int rulesFired = kSession.fireAllRules();
            System.out.println(rulesFired);
            System.out.println(p2);
            assertEquals(null, p2.getDisease());
    }
    @Test
    public void patientTest3() throws Exception {
        //CASE 3: The patient doesn't have symptoms
        Patient p3 = new Patient("C", "C", 87);
        kSession.insert(p3);
        int rulesFired = kSession.fireAllRules();
        System.out.println(rulesFired);
        System.out.println(p3);
        assertEquals(null, p3.getDisease());
    }
    @Test
    public void patientTest4() throws Exception {
        //CASE 4: All symptoms except one of them match
        //if instead HOARSENES we had SWEAT  we would be diagnosed with myocardial infarction)
        Patient p4 = new Patient("D", "D", 54);
        p4.getSymptoms().add(Symptom.CHEST_PAIN);
        p4.getSymptoms().add(Symptom.SCAPULA_PAIN);
        p4.getSymptoms().add(Symptom.DISTENDED_JUGULAR_VEINS);
        p4.getSymptoms().add(Symptom.WEAKNESS);
        p4.getSymptoms().add(Symptom.ANXIETY);
        p4.getSymptoms().add(Symptom.DYSPNEA);
        p4.getSymptoms().add(Symptom.FATIGUE);
        p4.getSymptoms().add(Symptom.NAUSEA);
        p4.getSymptoms().add(Symptom.VOMITING);
        p4.getSymptoms().add(Symptom.COLD_EXTREMITIES);
        p4.getSymptoms().add(Symptom.CHEST_PRESSURE);
        p4.getSymptoms().add(Symptom.PAIN_LEFT_ARM);
        p4.getSymptoms().add(Symptom.NECK_PAIN);
        p4.getSymptoms().add(Symptom.JAW_PAIN);
        p4.getSymptoms().add(Symptom.HOARSENES);
        kSession.insert(p4);
        int rulesFired = kSession.fireAllRules();
        System.out.println(rulesFired);
        System.out.println(p4);
        assertEquals(null, p4.getDisease());
    }
    @Test
    public void patientTest5() throws Exception {
        //CASE 5: It has more symptoms than expected for a specific disease
        //if we didn't have COREA and NAUSEA, we would be 100% diagnosed with compensatory crash
        //but as it has two additional symptoms, our expert system can't 100ç5 determine the patient has the disease
        Patient p5 = new Patient("E", "E", 26);
        p5.getSymptoms().add(Symptom.TACHYPNEA);
        p5.getSymptoms().add(Symptom.COLD_SKIN);
        p5.getSymptoms().add(Symptom.DECREASES_URINARY_OUTPUT);
        p5.getSymptoms().add(Symptom.PALPITATIONS);
        p5.getSymptoms().add(Symptom.TACHYCARDIA);
        p5.getSymptoms().add(Symptom.PALENESS);
        p5.getSymptoms().add(Symptom.COREA);
        p5.getSymptoms().add(Symptom.NAUSEA);
        kSession.insert(p5);
        int rulesFired = kSession.fireAllRules();
        System.out.println(rulesFired);
        System.out.println(p5);
        assertEquals(null, p5.getDisease());
    }
}