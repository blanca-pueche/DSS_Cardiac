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
        String drl = new DroolsBeanFactory().getDrlFromExcel("cardiac_diseases/drools.drl.xlsx");
        System.out.println(drl);
        kSession = new DroolsBeanFactory().getKieSession(resource);
        System.out.println(new DroolsBeanFactory().getDrlFromExcel("cardiac_diseases/drools.drl.xlsx"));
    }

    @Test
    public void patientTest() throws Exception {
        Patient patient = new Patient("Blanca", "Pueche", 20);
        patient.getSymptoms().add(Symptom.CHEST_PAIN);
        patient.getSymptoms().add(Symptom.SCAPULA_PAIN);
        patient.getSymptoms().add(Symptom.HOARSENES);
        patient.getSymptoms().add(Symptom.DYSPHAGIA);
        patient.getSymptoms().add(Symptom.DYSPNEA);
        patient.getSymptoms().add(Symptom.STRIDOR);
        patient.getSymptoms().add(Symptom.DRY_COUGH);
        kSession.insert(patient);

        int rulesFired = kSession.fireAllRules();
        System.out.println(rulesFired);
        System.out.println(patient);
        //assertEquals(25, patient.getAge());
        assertEquals(Disease.DESCENDING_AORTIC_ANEURYSM, patient.getDisease());
    }
}