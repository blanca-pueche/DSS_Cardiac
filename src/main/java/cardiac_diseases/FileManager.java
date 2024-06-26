package cardiac_diseases;

import java.io.*;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class FileManager {
    private String name;

    public FileManager(String name) {
        this.name = name;
    }

    public boolean downloadCSV(Hospital hospital) {
        boolean check = true;
        try {
            File file = new File(name + ".csv");
            FileWriter fileWriter = new FileWriter(file); // Open file in write mode

            // Write the header
            fileWriter.write("\"Hospital\",\"Patient name\",\"Patient Lastname\",\"Patient Age\",\"Symptoms\",\"Disease\"\n");

            // Write data for each patient
            for (Patient patient : hospital.getListOfPatients()) {
                String row = "\"" + hospital.getName() + "\",\"" + patient.getName() + "\",\"" +
                        patient.getSurname() + "\",\"" + patient.getAge() + "\",\"" +
                        symptomsToString(patient.getSymptoms()) + "\",\"" +
                        patient.getDisease() + "\"\n";
                fileWriter.write(row);
            }
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            check = false;
        }
        return check;
    }


    private String symptomsToString(LinkedList<Symptom> symptoms) {
        StringBuilder builder = new StringBuilder();
        for (Symptom symptom : symptoms) {
            builder.append(symptom).append(",");
        }
        // Remove the trailing comma
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    public Hospital uploadCSV() throws FileNotFoundException {
        Hospital hospital = null;
        LinkedList<Patient> listPatients = new LinkedList<>();
        try {
            FileReader fileCSV = new FileReader(name + ".csv");
            BufferedReader reader = new BufferedReader(fileCSV);

            // Skip the header
            reader.readLine();

            String row;
            while ((row = reader.readLine()) != null) {
                String[] data = row.split("\",\"");

                // Remove the double quotes
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].replaceAll("\"", "");
                }

                String[] symptomsArray = data[4].split(",");
                LinkedList<Symptom> symptoms = new LinkedList<>();

                for (String symptom : symptomsArray) {
                    try {
                        Symptom demoType = Symptom.valueOf(symptom);
                        symptoms.add(demoType);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: '" + symptom + "' is not a valid symptom for the enum.");
                    }
                }

                Disease disease = null;
                try {
                    disease = Disease.valueOf(data[5]);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: '" + data[5] + "' is not a valid disease for the enum.");
                }

                Patient patient = new Patient(data[1], data[2], Integer.parseInt(data[3]),
                        symptoms, disease);
                listPatients.add(patient);
                System.out.println(patient);
            }
            // Set hospital name based on file name
            String hospitalName = name; // You might need to adjust this based on your file naming convention
            hospital = new Hospital(hospitalName);
            hospital.setListOfPatients(listPatients);
            reader.close();

        } catch (IOException e) {
            System.out.println("Error: " + e);
            throw new FileNotFoundException("File not found: " + name + ".csv");
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
            ex.printStackTrace();
        }
        return hospital;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
