package cardiac_diseases;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static Hospital hospital;
    public static FileManager file;
    public static Scanner sc;
    public static void main(String[] args) throws Exception {
            boolean program = true;
            sc = new Scanner(System.in);
            //Scanner sc = new Scanner(System.in);
            try {
                hospitalMenu();
                while (program) {
                    printMenu();
                    Integer choice = Integer.parseInt(sc.nextLine());
                    while (choice < 1 && choice > 7) {
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
                        case 4: {
                            showPatientsInfo();
                            break;
                        }
                        case 7: {
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
                System.out.println("NOT A NUMBER... closing progra");
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
                switch (num) { //TODO solve problem with file
                    case 1: {
                        System.out.println("Enter the name of the file you want to open: ");
                        String name = sc.nextLine();
                        file = new FileManager(name);
                        hospital = file.uploadCSV(); //creates a hospital based on the file
                        break;
                    }
                    case 2: {
                        System.out.println("Enter the name of the new file: ");
                        String name = sc.nextLine();
                        System.out.print("Creating new file...");
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
       // Scanner sc = new Scanner(System.in);
        System.out.println("Introduce the name of the patient:");
        String name = sc.nextLine();
        System.out.println("Introduce the lastname of the patient:");
        String surname = sc.nextLine();
        System.out.println("Introduce the age of the patient:");
        Integer age = Integer.parseInt(sc.nextLine());
        showAllSymptoms();
        LinkedList<Symptom> symptoms = selectSymptoms();
        Patient patient = new Patient(name, surname, age, symptoms);
        hospital.getListOfPatients().add(patient);
        //sc.close();
    }
    public static void showAllSymptoms(){
        Symptom [] valores = Symptom.values();
        int n= 0;
        for (Symptom symptom : valores) {
            System.out.println(n + "." +symptom);
            n++;
        }
    }
    public static LinkedList<Symptom> selectSymptoms() throws IOException{
        //Scanner sc = new Scanner(System.in);
        System.out.print("Enter the numbers of selected symptoms (separated by spaces): ");
        String input = sc.nextLine();
        Symptom [] symptoms = Symptom.values();
        // Split the input by spaces and convert them to integers
        String[] numbers = input.split("\\s+");
        LinkedList<Symptom> selectedSymptoms = new LinkedList<>();
        int numberOfSymptoms = 156;
        for (String number : numbers) {
            int index = Integer.parseInt(number) - 1;
            if (index >= 0 && index < symptoms.length && !selectedSymptoms.contains(symptoms[index]) && index <= numberOfSymptoms ) {
                selectedSymptoms.add(symptoms[index]);
            }
        }
        //sc.close();
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
    public static void modifyName(Patient patient) throws IOException{
        //Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to modify name?: [y/n]");
        String modify = sc.nextLine();
        if (modify.equalsIgnoreCase("y")) {
            System.out.println("Enter new name: ");
            String newName = sc.nextLine();
            patient.setName(newName);
        }
        //sc.close();
    }
    public static void modifySurname(Patient patient) throws IOException{
        //Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to modify surname?: [y/n]");
        String modify = sc.nextLine();
        if (modify.equalsIgnoreCase("y")) {
            System.out.println("Enter new surname: ");
            String newSurname = sc.nextLine();
            patient.setSurname(newSurname);
        }
        //sc.close();
    }
    public static void modifyAge(Patient patient) throws IOException{
        //Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to modify age?: [y/n]");
        String modify = sc.nextLine();
        if (modify.equalsIgnoreCase("y")) {
            System.out.println("Enter new age: ");
            Integer newAge = Integer.parseInt(sc.nextLine());
            patient.setAge(newAge);
        }
        //sc.close();
    }
    public static void modifySymptoms(Patient patient) throws IOException{
        //Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to modify symptoms?: [y/n]");
        System.out.println("Current symptoms");
        System.out.println(patient.getSymptoms());
        String modify = sc.nextLine(); //TODO make functions to change only selected symptoms!?
        if (modify.equalsIgnoreCase("y")) {
            showAllSymptoms();
            LinkedList<Symptom> newSymptoms = selectSymptoms();
            patient.setSymptoms(newSymptoms);
            System.out.println("Do you want to make a new diagnosis with the new symptoms?: [y/n]");
            modify = sc.nextLine();
            if (modify.equalsIgnoreCase("y")){
                //TODO call the makeDiagnosis function
            }
        }
        //sc.close();
    }
    public static Patient choosePatient() throws IOException{
        //Scanner sc = new Scanner(System.in);
        LinkedList<Patient> list = hospital.getListOfPatients();
        System.out.println("List of patients in this hospital: ");
        for (int i = 0; i< list.size(); i++){
            System.out.println(i + ": " + list.get(i).getName() + list.get(i).getSurname());
        }
        System.out.println("Please, choose the patient to modify: ");
        Integer choice = Integer.parseInt(sc.nextLine());
        Patient patient = list.get(choice);
        //sc.close();
        return patient;
    }
    public static void makeDiagnosis() throws IOException{
        Patient patient = choosePatient();
        System.out.println(patient);
        //TODO finish function
    }
    public static void showPatientsInfo(){
        LinkedList<Patient> list = hospital.getListOfPatients();
        for (Patient pat: list){
            System.out.println(pat);
        }
    }


}
