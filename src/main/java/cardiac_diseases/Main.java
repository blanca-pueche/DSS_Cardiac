package cardiac_diseases;

import drools.config.DroolsBeanFactory;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import drools.config.DroolsBeanFactory.*;

public class Main {
    public static Hospital hospital;
    public static FileManager file;
    public static Scanner sc;
    private static KieSession kSession;
    public static void main(String[] args) throws Exception {
            boolean program = true;
            sc = new Scanner(System.in);
            //Scanner sc = new Scanner(System.in);
            try {
                hospitalMenu();
                while (program) {
                    printMenu();
                    Integer choice = Integer.parseInt(sc.nextLine());
                    while (choice < 1 || choice > 7) {
                        System.out.println("Not a valid number... try again");
                        printMenu();
                        choice = Integer.parseInt(sc.nextLine());
                    }
                    switch (choice) {
                        case 1: { // Add patient
                            createPatient();
                            break;
                        }
                        case 2: { // Modify patient
                            modifyPatient();
                            break;
                        }
                        case 3: { // Make diagnosis
                            makeDiagnosis();
                            break;
                        }
                        case 4: { // Show info
                            showPatientsInfo();
                            break;
                        }
                        case 7: { //TODO ir actualizando la lista de pacientes del hospital
                            boolean fileCreation = file.downloadCSV(hospital);
                            if (fileCreation) {
                                System.out.print("Saved file correctly\n");
                            } else {
                                System.out.print("Couldn't save file correctly\n");
                            }
                            System.out.println("Closing app...");
                            program = false;
                        }
                    }
                }
                sc.close();
            } catch (IOException ex) {
                System.out.println(ex);
            } catch (NumberFormatException ex) {
                System.out.println("NOT A NUMBER... closing program");
                System.out.println(ex);
            }
    }

    private static void printMenu() {
        System.out.println("-----------------MENU-----------------");
        System.out.println("   1: Add patient ");
        System.out.println("   2: Modify patient");
        System.out.println("   3: Make diagnosis");
        System.out.println("   4: Show all patients");

        System.out.println("   7: Exit");
    }
    private static void hospitalMenu(){
        while(true) {
            try {
                System.out.println("-----------------WELCOME-----------------");
                System.out.println("Would you like to open a CSV with the patients or create a new one?:");
                System.out.println("   1: Open CSV ");
                System.out.println("   2: Create a new one");

                Integer num = Integer.parseInt(sc.nextLine());
                while (num != 1 && num != 2) {
                    System.out.println("Not a valid number... try again");
                    hospitalMenu();
                    num = Integer.parseInt(sc.nextLine());
                }
                switch (num) {
                    case 1: {
                        System.out.println("Enter the name of the file you want to open: ");
                        String name = sc.nextLine();
                        file = new FileManager(name);
                        hospital = file.uploadCSV(); //creates a hospital based on the file
                        break; //TODO what if the file there is not a file with that name
                    }
                    case 2: {
                        System.out.println("Enter the name of the new file: ");
                        String name = sc.nextLine();
                        System.out.print("Creating new file...\n");
                        hospital = new Hospital(name);
                        file = new FileManager(name);
                        break;
                    }
                }
                break;
            } catch (NumberFormatException ex) {
                System.out.println("Please select a number");
            }
        }
    }
    private static void createPatient() throws IOException {
        System.out.println("Introduce the name of the patient:");
        String name = sc.nextLine();
        System.out.println("Introduce the lastname of the patient:");
        String surname = sc.nextLine();
        System.out.println("Introduce the age of the patient:");
        Integer age = Integer.parseInt(sc.nextLine());
        LinkedList<Symptom> symptoms = selectSymptoms();
        Patient patient = new Patient(name, surname, age, symptoms);
        hospital.getListOfPatients().add(patient);
    }
    public static void showAllSymptoms(){
        Symptom [] valores = Symptom.values();
        int n= 0;
        for (Symptom symptom : valores) {
            System.out.println((n+1) + "." +symptom);
            n++;
        }
    }
    public static LinkedList<Symptom> selectSymptoms() throws IOException{
        showAllSymptoms();
        System.out.print("Enter the numbers of selected symptoms (separated by spaces): ");
        String input = sc.nextLine();
        Symptom [] symptoms = Symptom.values();
        // Split the input by spaces and convert them to integers
        String[] numbers = input.split("\\s+");
        LinkedList<Symptom> selectedSymptoms = new LinkedList<>();
        for (String number : numbers) {
            int index = Integer.parseInt(number) - 1;
            if (index >= 0 && index <= symptoms.length && !selectedSymptoms.contains(symptoms[index])) {
                selectedSymptoms.add(symptoms[index]);
            }
        }
        return selectedSymptoms;
    }
    public static void modifyPatient() throws IOException{
        Patient patient = choosePatient();
        System.out.println(patient);
        modifyName(patient);
        modifySurname(patient);
        modifyAge(patient);
        modifySymptoms(patient);
    }

    public static String checkYorN (String c){
        while(!c.equalsIgnoreCase("y") && !c.equalsIgnoreCase("n")){
            System.out.println("Answer not valid, please enter 'y' for yes or 'n' for no.");
            c = sc.nextLine();
        }
        return c;
    }

    public static void modifyName(Patient patient) throws IOException{
        System.out.println("Do you want to modify name?: [y/n]");
        String modify = sc.nextLine();
        modify = checkYorN(modify);
        if (modify.equalsIgnoreCase("y")) {
            System.out.println("Enter new name: ");
            String newName = sc.nextLine();
            patient.setName(newName);
        }
    }
    public static void modifySurname(Patient patient) throws IOException{
        System.out.println("Do you want to modify surname?: [y/n]");
        String modify = sc.nextLine();
        modify = checkYorN(modify);
        if (modify.equalsIgnoreCase("y")) {
            System.out.println("Enter new surname: ");
            String newSurname = sc.nextLine();
            patient.setSurname(newSurname);
        }
    }
    public static void modifyAge(Patient patient) throws IOException{
        System.out.println("Do you want to modify age?: [y/n]");
        String modify = sc.nextLine();
        modify = checkYorN(modify);
        if (modify.equalsIgnoreCase("y")) {
            System.out.println("Enter new age: ");
            Integer newAge = Integer.parseInt(sc.nextLine());
            patient.setAge(newAge);
        }
    }
    public static void modifySymptoms(Patient patient) throws IOException{
        //Scanner sc = new Scanner(System.in);
        System.out.println("Current symptoms");
        System.out.println(patient.getSymptoms());
        System.out.println("Do you want to modify symptoms?: [y/n]");
        String modify = sc.nextLine();
        modify = checkYorN(modify);
        if (modify.equalsIgnoreCase("y")) {
            System.out.println("Do you want to delete symptoms?: [y/n]");
            String del = sc.nextLine();
            del = checkYorN(del);
            if (del.equalsIgnoreCase("y")){
                LinkedList<Symptom> updated = removeSymptoms(patient);
                patient.setSymptoms(updated);
            }
            System.out.println("Do you want to add symptoms?: [y/n]");
            String add = sc.nextLine();
            add = checkYorN(add);
            if (add.equalsIgnoreCase("y")){
                LinkedList<Symptom> updated = addSymptoms(patient);
                patient.setSymptoms(updated);
            }
            System.out.println("Do you want to make a new diagnosis with the new symptoms?: [y/n]");
            modify = sc.nextLine();
            modify = checkYorN(modify);
            if (modify.equalsIgnoreCase("y")){
                patient.setDisease(null); //we do this so the previous disease doesn't appear in case the symptoms have changed
                makeNewDiagnosis(patient);
            }
        }
    }

    private static void makeNewDiagnosis(Patient patient) {
        try {
            Resource resource = ResourceFactory.newClassPathResource("cardiac_diseases/drools.drl.xlsx");
            // Create Drools session
            kSession = new DroolsBeanFactory().getKieSession(resource);
            // Fire rules
            kSession.insert(patient);
            kSession.fireAllRules();

            if (patient.getDisease()!=null) {
                System.out.println("\n\nPatient diagnosis: " + patient.getDisease());
                Summary summary = new Summary();
                summary.displaySummary(patient.getDisease());
                System.out.println("\n");
            } else {
                System.out.println("\n\nSorry but it seems like there's no matches for a cardiac disease in our system, so we can't 100% diagnose you with any disease.\nPlease consult with a different specialist for further testing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<Symptom> removeSymptoms(Patient patient){
        LinkedList<Symptom> old = patient.getSymptoms();
        int i = 0;
        for (Symptom symp : old){
        System.out.println((i+1) + ". " + symp);
        i++;
        }
        System.out.println("Enter the numbers of symptoms you want to remove (separated by spaces): ");
        String input = sc.nextLine();
        String[] numbers = input.split("\\s+");
        LinkedList<Symptom> symptomsToRemove = new LinkedList<>();

        for (String number : numbers) {
            int index = Integer.parseInt(number) - 1;
            if (index >= 0 && index < old.size()) {
                symptomsToRemove.add(old.get(index));
            } else {
                System.out.println("Invalid index: " + (index + 1));
            }
        }
        for (Symptom symptom : symptomsToRemove) {
            old.remove(symptom);
        }
        System.out.println("Updated symptoms: " + old);
        return old;
    }

    public static LinkedList<Symptom> addSymptoms(Patient patient){
        LinkedList<Symptom> old = patient.getSymptoms();
        showAllSymptoms();
        System.out.print("Enter the numbers of selected symptoms (separated by spaces): ");
        String input = sc.nextLine();
        Symptom [] symptoms = Symptom.values();
        // Split the input by spaces and convert them to integers
        String[] numbers = input.split("\\s+");
        for (String number : numbers) {
            int index = Integer.parseInt(number) - 1;
            if (index >= 0 && index <= symptoms.length && !old.contains(symptoms[index])) {
                old.add(symptoms[index]);
            }
        }
        System.out.println(old);
        return old;
    }

    public static Patient choosePatient() throws IOException{
        LinkedList<Patient> list = hospital.getListOfPatients();
        System.out.println("List of patients in this hospital: ");
        for (int i = 0; i< list.size(); i++){
            System.out.println(i + ": " + list.get(i).getName() + " " +list.get(i).getSurname());
        }
        System.out.println("Please, choose the patient you want to work with: ");
        Integer choice = Integer.parseInt(sc.nextLine());// verify that the number corresponds to a patient
        while(choice>= list.size() || choice < 0){
            System.out.println("No patient found with that ID.");
            System.out.println("Please introduce a valid ID:");
            choice = Integer.parseInt(sc.nextLine());
        }
        Patient patient = list.get(choice);
        return patient;
    }
    public static void makeDiagnosis() throws IOException{
        Patient patient = choosePatient();
        System.out.println(patient);
        patient.setDisease(null); //we do this so the previous disease doesn't appear in case the symptoms have changed
        try {
            Resource resource = ResourceFactory.newClassPathResource("cardiac_diseases/drools.drl.xlsx");
            // Create Drools session
            kSession = new DroolsBeanFactory().getKieSession(resource);
            // Fire rules
            kSession.insert(patient);
            kSession.fireAllRules();

            if (patient.getDisease()!=null) {
                System.out.println("\n\nPatient diagnosis: " + patient.getDisease());
                Summary summary = new Summary();
                summary.displaySummary(patient.getDisease());
                System.out.println("\n");
            } else {
                System.out.println("\n\nSorry but it seems like there's no matches for a cardiac disease in our system, so we can't 100% diagnose you with any disease.\nPlease consult with a different specialist for further testing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void showPatientsInfo(){
        LinkedList<Patient> list = hospital.getListOfPatients();
        for (Patient pat: list){
            System.out.println(pat);
        }
    }

}
