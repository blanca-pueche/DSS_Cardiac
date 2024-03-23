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
        Resource resource = ResourceFactory.newClassPathResource("cardiac_diseases/test.drl.xlsx", getClass());
        kSession = new DroolsBeanFactory().getKieSession(resource);
        System.out.println(new DroolsBeanFactory().getDrlFromExcel("cardiac_diseases/test.drl.xlsx"));
    }

    @Test
    public void patientTest() throws Exception {
        Patient patient = new Patient("Blanca", "Pueche", 20);
        patient.getSymptoms().add(Symptom.CHEST_PAIN);
        kSession.insert(patient);

        int rulesFired = kSession.fireAllRules();
        System.out.println(rulesFired);
        System.out.println(patient);
        assertEquals(25, patient.getAge());
        assertEquals(Disease.TEST, patient.getDisease());
    }
}